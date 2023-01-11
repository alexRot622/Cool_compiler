package cool.structures;

import cool.structures.AST.*;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeGenVisitor implements AstVisitor<ST> {
    static STGroupFile templates = new STGroupFile("cgen.stg");

    ST mainSection;
    ST dataSection;
    ST funcSection;

    ST classPrototype;
    ST classDispTable;
    ST classInit;

    int ifIndex = 0;
    int strConstTag = 0;
    HashMap<String, Integer> strTagMap = new HashMap<>();

    int intConstTag = 0;
    HashMap<Integer, Integer> intTagMap = new HashMap<>();

    // Mapping between class tag and the tag of the string constant corresponding to the class name
    ArrayList<Integer> classToName;
    private TypeSymbol currentClass = null;

    private HashMap<IdSymbol, Location> environment = new HashMap<>();
    private HashMap<Location, Object> store = new HashMap<>();
    private IdSymbol currentIdSym = null;

    private String IdSymbolGen(IdSymbol sym, int offset, int size) {
        TypeSymbol type = sym.getTypeSymbol();
        if (!environment.containsKey(sym)) {
            Location loc = new Location(currentClass, offset, size);
            environment.put(sym, loc);

            if (type == TypeSymbol._STRING) {
                store.put(loc, "");
                return ".asciiz \"\"";
            }
            else if (type == TypeSymbol.STRING) {
                store.put(loc, "");
                return ".word str_const" + stringConstant("");
            }
            else if (type == TypeSymbol.INT) {
                store.put(loc, 0);
                return ".word int_const" + intConstant(0);
            }
            else {
                store.put(loc, null);
                return ".word 0";
            }
        }

        if (type == TypeSymbol._STRING)
            return ".asciiz \"\"";
        else if (type == TypeSymbol.STRING)
            return ".word str_const" + stringConstant("");
        else if (type == TypeSymbol.INT)
            return ".word int_const" + intConstant(0);
        else
            return ".word 0";
    }

    private void typeSymbolGen(TypeSymbol type) {
        if (classPrototype == null)
            classPrototype = templates.getInstanceOf("classProt");
        if (classDispTable == null)
            classDispTable = templates.getInstanceOf("classDispTab");

        classPrototype.add("name", type.getName())
                      .add("tag", type.tag);
        classDispTable.add("name", type.getName());
        classToName.set(type.tag, stringConstant(type.name));

        // Add attributes and methods, starting from the root of the inheritance tree
        ArrayList<TypeSymbol> types = new ArrayList<>();
        Scope scope = type;
        while (scope instanceof TypeSymbol) {
            types.add(0, (TypeSymbol) scope);
            scope = scope.getParent();
        }

        int size = 3;
        currentClass = type;
        for (TypeSymbol c : types)
            for (var sym : c.symbols.values())
                if (sym instanceof MethodSymbol && sym == type.lookup(sym.getName())) {
                    classDispTable.add("classes", c.getName())
                                  .add("methods", sym.getName());
                }
                else if (sym instanceof IdSymbol) {
                    classPrototype.add("attrs", IdSymbolGen((IdSymbol) sym, size * 4, 4));
                    size++;
                }

        classPrototype.add("size", size);

        dataSection.add("globals", ".globl " + type.name + "_protObj\n");
        dataSection.add("prots", classPrototype);
        dataSection.add("dispTabs", classDispTable);

        classInit = templates.getInstanceOf("classInit");
        classInit.add("name", type.name);
        if (type != TypeSymbol.OBJECT)
            classInit.add("parentName", ((TypeSymbol) type.getParent()).name);
        funcSection.add("e", classInit);

        classPrototype = null;
        classDispTable = null;
    }

    @Override
    public ST visit(AstProgram program) {
        dataSection = templates.getInstanceOf("dataSection");
        funcSection = templates.getInstanceOf("sequence");
        mainSection = templates.getInstanceOf("sequence");

        // Preparing Symbol table for code generation
        int classTagCount = TypeSymbol.OBJECT.setTags();

        classToName = new ArrayList<>(classTagCount);
        for (int i = 0; i < classTagCount; i++)
            classToName.add(-1);

        // Tag declarations for basic classes
        ST intTag = templates.getInstanceOf("tag");
        intTag.add("type", "int").add("val", TypeSymbol.INT.tag);
        dataSection.add("tags", intTag)
                .add("globals", ".globl _int_tag\n");

        ST stringTag = templates.getInstanceOf("tag");
        stringTag.add("type", "string").add("val", TypeSymbol.STRING.tag);
        dataSection.add("tags", stringTag)
                .add("globals", ".globl _string_tag\n");

        ST boolTag = templates.getInstanceOf("tag");
        boolTag.add("type","bool").add("val", TypeSymbol.BOOL.tag);
        dataSection.add("tags", boolTag)
                .add("globals", ".globl _bool_tag\n");

        // Add the only 2 constants for bool to the globals
        dataSection.add("globals", ".globl bool_const0\n");
        dataSection.add("globals", ".globl bool_const1\n");
        // Class name table is global
        dataSection.add("globals", ".globl class_nameTab\n");

        // Add the constants for default values of basic types
        intConstant(0);
        stringConstant("");
        addBoolsConstants();

        // Add basic attributes to default classes
        TypeSymbol.STRING.add(new IdSymbol(TypeSymbol.INT, "i"));
        TypeSymbol.STRING.add(new IdSymbol(TypeSymbol._STRING, "s"));
        TypeSymbol.INT.add(new IdSymbol(TypeSymbol._INT, "i"));
        TypeSymbol.BOOL.add(new IdSymbol(TypeSymbol._BOOL, "b"));
        // Generate code for default classes
        typeSymbolGen(TypeSymbol.OBJECT);
        typeSymbolGen(TypeSymbol.IO);
        typeSymbolGen(TypeSymbol.INT);
        typeSymbolGen(TypeSymbol.STRING);
        typeSymbolGen(TypeSymbol.BOOL);

        for (AstClass classs : program.getClasses())
           classs.accept(this);

        dataSection.add("nameTab", templates.getInstanceOf("classNameTab").add("names", classToName));

        var programST = templates.getInstanceOf("program");
        programST.add("data", dataSection);
        programST.add("textFuncs", funcSection);
        programST.add("textMain", mainSection);

        return programST;
    }

    // Retrieves the tag of an int.
    // Adds the int to the tag map and to the constants if it does not exist already
    private int intConstant(int i) {
        if (!intTagMap.containsKey(i)) {
            ST st = templates.getInstanceOf("intConst");
            st.add("tag", intConstTag).add("value", i);
            dataSection.add("consts", st);
            intTagMap.put(i, intConstTag);
            intConstTag++;
        }
        return intTagMap.get(i);
    }

    // Retrieves the tag of a string.
    // Adds the string to the tag map and to the constants if it does not exist already
    private int stringConstant(String s) {
        if (!strTagMap.containsKey(s)) {
            ST st = templates.getInstanceOf("strConst");
            st.add("tag", strConstTag)
              .add("size", Math.round(Math.ceil(4 + s.length() / 4.0)))
              .add("lenTag", intConstant(s.length()))
              .add("value", s);
            dataSection.add("consts", st);
            strTagMap.put(s, strConstTag);
            strConstTag++;
        }
        return strTagMap.get(s);
    }

    private void addBoolsConstants() {
        ST stFalse = templates.getInstanceOf("boolConst").add("tag", 0).add("value", 0);
        ST stTrue = templates.getInstanceOf("boolConst").add("tag", 1).add("value", 1);
        dataSection.add("consts", stFalse).add("consts", stTrue);
    }

    @Override
    public ST visit(AstClass classs) {
        currentClass = classs.getSymbol();
        classPrototype = templates.getInstanceOf("classProt");
        classDispTable = templates.getInstanceOf("classDispTab");
        typeSymbolGen(currentClass);
        for (var feature : classs.getFeatures())
            feature.accept(this);
        return null;
    }

    @Override
    public ST visit(AstMethod method) {
        var st = templates.getInstanceOf("method");
        st.add("class", currentClass.name)
          .add("name", method.getId().getToken().getText())
          .add("e", method.getExpr().accept(this));
        funcSection.add("e", st);
        return null;
    }

    @Override
    public ST visit(AstAttribute attribute) {
        if (attribute.isAssigned()) {
            var st = templates.getInstanceOf("initAttr");
            currentIdSym = attribute.getId().getSymbol();
            classInit.add("attrs", attribute.getExpr().accept(this));
            st.add("offset", environment.get(currentIdSym).getOffset());
            classInit.add("attrs",st );
            currentIdSym = null;
        }
        return null;
    }

    @Override
    public ST visit(AstFormal formal) {
        return null;
    }

    @Override
    public ST visit(AstInt intt) {
        int i = Integer.parseInt(intt.getToken().getText());
        String addr = "int_const" + intConstant(i);
        if (currentIdSym != null)
            store.put(environment.get(currentIdSym), addr);
        return templates.getInstanceOf("literal").add("addr", addr);
    }

    @Override
    public ST visit(AstString string) {
        String s = string.getToken().getText();
        String addr = "str_const" + stringConstant(s);
        if (currentIdSym != null)
            store.put(environment.get(currentIdSym), addr);
        return templates.getInstanceOf("literal").add("addr", addr);
    }

    @Override
    public ST visit(AstBool bool) {
        boolean b = bool.getToken().getText().toLowerCase().equals("true");
        String addr = "bool_const" + (b ? "1" : "0");
        if (currentIdSym != null)
            store.put(environment.get(currentIdSym), addr);
        return templates.getInstanceOf("literal").add("addr", addr);
    }

    @Override
    public ST visit(AstAssign assign) {
        return null;
    }

    @Override
    public ST visit(AstDispatch dispatch) {
        var st = templates.getInstanceOf("sequence");
        st.add("e", dispatch.getCaller().accept(this));

        // Check if callee is null
        var callSt = templates.getInstanceOf("call");

        // Pushing arguments to the stack, in reverse order
        var it = dispatch.getParams().listIterator();
        while (it.hasPrevious()) {
            var arg = it.previous();
            var push = templates.getInstanceOf("push")
                    .add("arg", arg.accept(this));
            callSt.add("args", push);
        }

        // Obtaining the method name
        String methodName = dispatch.getCallee().getToken().getText();
        String callId;
        if (dispatch.getType() != null)
            callId = dispatch.getType().getToken().getText() + "." + methodName;
        else
            callId = dispatch.getCallee().getSymbol().getTypeSymbol().getName() + "." + methodName;
        callSt.add("id", callId);
        return st.add("e", callSt);
    }

    @Override
    public ST visit(AstIf iff) {
        return null;
    }

    @Override
    public ST visit(AstDivide divide) {
        return null;
    }

    @Override
    public ST visit(AstEquals equals) {
        return null;
    }

    @Override
    public ST visit(AstLess less) {
        return null;
    }

    @Override
    public ST visit(AstLessEquals lessEquals) {
        return null;
    }

    @Override
    public ST visit(AstMinus minus) {
        return null;
    }

    @Override
    public ST visit(AstMultiply multiply) {
        return null;
    }

    @Override
    public ST visit(AstPlus plus) {
        return null;
    }

    @Override
    public ST visit(AstId id) {
        var st = templates.getInstanceOf("sequence");
        if (id.getToken().getText().equals("self"))
            st.add("e", "move $a0 $s0");
        else {
            // TODO: retrieve id value
        }
        return st;
    }

    @Override
    public ST visit(AstParantheses parantheses) {
        return null;
    }

    @Override
    public ST visit(AstNegate negate) {
        return null;
    }

    @Override
    public ST visit(AstNot not) {
        return null;
    }

    @Override
    public ST visit(AstIsVoid isVoid) {
        return null;
    }

    @Override
    public ST visit(AstNew neww) {
        return null;
    }

    @Override
    public ST visit(AstCase casee) {
        return null;
    }

    @Override
    public ST visit(AstCall call) {
        return null;
    }

    @Override
    public ST visit(AstWhile whilee) {
        return null;
    }

    @Override
    public ST visit(AstBlock block) {
        return null;
    }

    @Override
    public ST visit(AstLet let) {
        return null;
    }

    @Override
    public ST visit(AstType type) {
        return null;
    }

    private class Location {
        private TypeSymbol type;
        private int offset, size;

        public Location(TypeSymbol type, int offset, int size) {
            this.type = type;
            this.offset = offset;
            this.size = size;
        }

        public TypeSymbol getType() {
            return type;
        }

        public int getOffset() {
            return offset;
        }

        public int getSize() {
            return size;
        }
    }
}
