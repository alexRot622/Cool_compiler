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

    int ifIndex = 0;
    int strConstTag = 0;
    HashMap<String, Integer> strTagMap = new HashMap<String, Integer>();

    int intConstTag = 0;
    HashMap<Integer, Integer> intTagMap = new HashMap<Integer, Integer>();

    // Mapping between class tag and the tag of the string constant corresponding to the class name
    ArrayList<Integer> classToName;
    private TypeSymbol currentClass = null;

//    private ST IdSymbolGen(IdSymbol sym) {
//        TypeSymbol type = sym.getTypeSymbol();
//
//        if (type == TypeSymbol.INT) {
//            return new ST"int_const%[0]", intConstant(0));
//        }
//        else {
//            return ST.format("0");
//        }
//    }

    private void typeSymbolGen(TypeSymbol type) {
        classPrototype = templates.getInstanceOf("classProt");
        classDispTable = templates.getInstanceOf("classDispTab");

        classPrototype.add("name", type.getName())
                      .add("tag", type.tag)
                      .add("size", 3);
        classDispTable.add("name", type.getName());
        classToName.set(type.tag, stringConstant(type.name));

        // Add attributes and methods, starting from the root of the inheritance tree
        ArrayList<TypeSymbol> types = new ArrayList<>();
        Scope scope = type;
        while (scope instanceof TypeSymbol) {
            types.add(0, (TypeSymbol) scope);
            scope = scope.getParent();
        }

        for (TypeSymbol c : types)
            for (var sym : c.symbols.values())
                if (sym instanceof MethodSymbol)
                    classDispTable.add("classes", c.getName())
                                  .add("methods", sym.getName());
//                else if (sym instanceof IdSymbol)
//                    classPrototype.add("attrs", IdSymbolGen((IdSymbol) sym);

        dataSection.add("globals", ".globl " + type.name + "_protObj\n");
        dataSection.add("prots", classPrototype);
        dataSection.add("dispTabs", classDispTable);

        var stInit = templates.getInstanceOf("classInit");
        stInit.add("name", type.name);
        if (type != TypeSymbol.OBJECT)
            stInit.add("parentName", ((TypeSymbol) type.getParent()).name);
        funcSection.add("e", stInit);
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
        typeSymbolGen(currentClass);
        for (var feature : classs.getFeatures())
            funcSection.add("e", feature.accept(this));
        currentClass = null;
        return null;
    }

    @Override
    public ST visit(AstMethod method) {
        var st = templates.getInstanceOf("method");
        st.add("class", currentClass.name)
          .add("name", method.getId().getToken().getText())
          .add("e", method.getExpr().accept(this));
        return st;
    }

    @Override
    public ST visit(AstAttribute attribute) {
        return null;
    }

    @Override
    public ST visit(AstFormal formal) {
        return null;
    }

    @Override
    public ST visit(AstInt intt) {
        int i = Integer.parseInt(intt.getToken().getText());
        return templates.getInstanceOf("literal").add("addr", "int_const" + intConstant(i));
    }

    @Override
    public ST visit(AstString string) {
        String s = string.getToken().getText();
        return templates.getInstanceOf("literal").add("addr", "str_const" + stringConstant(s));
    }

    @Override
    public ST visit(AstBool bool) {
        boolean b = bool.getToken().getText().toLowerCase().equals("true");
        return templates.getInstanceOf("literal").add("addr", "bool_const" + (b ? "1" : "0"));
    }

    @Override
    public ST visit(AstAssign assign) {
        return null;
    }

    @Override
    public ST visit(AstDispatch dispatch) {
        return null;
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
        return null;
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
}
