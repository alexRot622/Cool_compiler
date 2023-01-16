package cool.structures;

import cool.compiler.Compiler;
import cool.structures.AST.*;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class CodeGenVisitor implements AstVisitor<ST> {
    static STGroupFile templates = new STGroupFile("cgen.stg");

    ST mainSection;
    ST dataSection;
    ST funcSection;

    ST classPrototype;
    ST classDispTable;
    ST classInit;

    int labelIndex = 0;
    int dispIndex = 0;
    int strConstTag = 0;
    HashMap<String, Integer> strTagMap = new HashMap<>();

    int intConstTag = 0;
    HashMap<Integer, Integer> intTagMap = new HashMap<>();

    // Mapping between class tag and the tag of the string constant corresponding to the class name
    ArrayList<Integer> classToName;
    private Scope currentScope = null;

    private final HashMap<Symbol, Location> environment = new HashMap<>();
    private final HashMap<Location, Object> store = new HashMap<>();
    private IdSymbol currentIdSym = null;
    String filename;

    private String IdSymbolGen(IdSymbol sym, int offset, int size) {
        TypeSymbol type = sym.getTypeSymbol();
        if (!environment.containsKey(sym)) {
            Location loc = new Location(currentScope, offset, size);
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
            else if (type == TypeSymbol._BOOL || type == TypeSymbol.BOOL) {
                store.put(loc, 0);
                return ".word bool_const0";
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
        else if (type == TypeSymbol._BOOL || type == TypeSymbol.BOOL)
            return ".word bool_const0";
        else
            return ".word 0";
    }

    private void typeSymbolGen(TypeSymbol type) {
        if (classToName.get(type.tag) >= 0)
            return;
        classPrototype = templates.getInstanceOf("classProt");
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
        currentScope = type;
        HashSet<MethodSymbol> addedMethods = new HashSet<>();
        for (TypeSymbol c : types)
            for (var sym : c.symbols.values())
                if (sym instanceof MethodSymbol && !addedMethods.contains(sym)) {
                    MethodSymbol methodSymbol = (MethodSymbol) type.lookup(sym.getName());
                    classDispTable.add("classes", ((TypeSymbol) methodSymbol.getParent()).getName())
                                  .add("methods", methodSymbol.getName());
                    environment.put(methodSymbol, new Location(methodSymbol.getParent(), addedMethods.size() * 4, 4));
                    //TODO: store??
                    addedMethods.add(methodSymbol);
                }
                else if (sym instanceof IdSymbol && !(sym instanceof MethodSymbol)) {
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

        // Visit class nodes
        for (AstClass classs : program.getClasses())
           classs.accept(this);

        // Prepare array of class names ordered by their tag for the object table
        ArrayList<String> classNames = new ArrayList<>();
        for (int i = 0; i < classTagCount; i++) {
            classNames.add(TypeSymbol.tagMap.get(i).getName());
        }
        dataSection.add("nameTab", templates.getInstanceOf("classNameTab").add("names", classToName));
        dataSection.add("objTab", templates.getInstanceOf("objTab").add("classes", classNames));

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
            st.add("constTag", intConstTag).add("intTag", TypeSymbol.INT.tag).add("value", i);
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
            st.add("constTag", strConstTag)
              .add("strTag", TypeSymbol.STRING.tag)
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
        ST stFalse = templates.getInstanceOf("boolConst").add("constTag", 0)
                                                               .add("boolTag", TypeSymbol.BOOL.tag)
                                                               .add("value", 0);
        ST stTrue = templates.getInstanceOf("boolConst").add("constTag", 1)
                                                              .add("boolTag", TypeSymbol.BOOL.tag)
                                                              .add("value", 1);
        dataSection.add("consts", stFalse).add("consts", stTrue);
    }

    @Override
    public ST visit(AstClass classs) {
        filename = new File(Compiler.fileNames.get(classs.getCtx())).getName();
        currentScope = classs.getSymbol();
        typeSymbolGen(classs.getSymbol());
        for (var feature : classs.getFeatures())
            feature.accept(this);
        return null;
    }

    @Override
    public ST visit(AstMethod method) {
        currentScope = method.getSymbol();

        // define formals
        int offset = 12;
        for (AstFormal formal : method.getParameters()) {
            Location loc = new Location(method.getSymbol(), offset, 4);
            environment.put(formal.getIdSymbol(), loc);
            offset += 4;
        }
        var st = templates.getInstanceOf("method");
        st.add("class", ((TypeSymbol) currentScope.getParent()).name)
          .add("name", method.getId().getToken().getText())
          .add("e", method.getExpr().accept(this));
        if (!method.getParameters().isEmpty())
            st.add("paramsSize", method.getParameters().size() * 4);
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
        boolean b = bool.getToken().getText().equalsIgnoreCase("true");
        String addr = "bool_const" + (b ? "1" : "0");
        if (currentIdSym != null)
            store.put(environment.get(currentIdSym), addr);
        return templates.getInstanceOf("literal").add("addr", addr);
    }

    @Override
    public ST visit(AstAssign assign) {
        currentIdSym = (IdSymbol) currentScope.lookup(assign.getId().getToken().getText());
        var st = templates.getInstanceOf("sequence");
        st.add("e", assign.getExpr().accept(this));
        Location loc = environment.get(currentIdSym);
        if (loc == null) {
            System.err.println(assign.getId().getToken().getLine() + ": runtime error");
            currentIdSym = null;
            return null;
        }

        // TODO (CRISTI): Replace assignVar, assignParam with a single template, assign; same for loadVar, loadParam
        if (loc.getScope() instanceof TypeSymbol) // On heap
            st.add("e", templates.getInstanceOf("assignVar").add("offset", loc.getOffset()));
        else // On stack
            st.add("e", templates.getInstanceOf("assignParam").add("offset", loc.getOffset()));

        currentIdSym = null;
        return st;
    }

    @Override
    public ST visit(AstDispatch dispatch) {
        var st = templates.getInstanceOf("dispatch");

        // Pushing arguments to the stack, in reverse order
        var params = dispatch.getParams();
        for (int i = params.size() - 1; i >= 0; i--) {
            var push = templates.getInstanceOf("push")
                    .add("arg", params.get(i).accept(this));
            st.add("args", push);
        }

        st.add("caller", dispatch.getCaller().accept(this))
          .add("dispIdx", dispIndex++)
          .add("filenameTag", stringConstant(filename))
          .add("line", dispatch.getToken().getLine());
        if (dispatch.getType().getToken() != null) {
            st.add("typename", dispatch.getType().getToken().getText());
        }

        // Obtaining the method offset
        String methodName = dispatch.getCallee().getToken().getText();
        TypeSymbol typeSymbol = dispatch.getCaller().getTypeSymbol();
        if (typeSymbol instanceof SelfTypeSymbol)
            typeSymbol = (TypeSymbol) typeSymbol.getParent();
        typeSymbolGen(typeSymbol);
        Location loc = environment.get(typeSymbol.lookup(methodName));
        if (loc == null) {
            System.err.println(dispatch.getCallee().getToken().getLine() + ": runtime error");
            return null;
        }
        return st.add("methodOffset", loc.getOffset());
    }

    @Override
    public ST visit(AstIf iff) {
        var st = templates.getInstanceOf("iff");
        st.add("cond", iff.getCond().accept(this))
          .add("e1", iff.getThenBranch().accept(this))
          .add("e2", iff.getElseBranch().accept(this))
          .add("index", labelIndex++);
        return st;
    }

    @Override
    public ST visit(AstDivide divide) {
        var st = templates.getInstanceOf("arithmetic");
        st.add("op", "div")
          .add("e1", divide.getLeft().accept(this))
          .add("e2", divide.getRight().accept(this));
        return st;
    }

    @Override
    public ST visit(AstEquals equals) {
        var st = templates.getInstanceOf("eq");
        st.add("e1", equals.getLeft().accept(this))
          .add("e2", equals.getRight().accept(this))
          .add("index", labelIndex++);
        return st;
    }

    @Override
    public ST visit(AstLess less) {
        var st = templates.getInstanceOf("compare");
        st.add("e1", less.getLeft().accept(this))
          .add("e2", less.getRight().accept(this))
          .add("op", "blt")
          .add("index", labelIndex++);
        return st;
    }

    @Override
    public ST visit(AstLessEquals lessEquals) {
        var st = templates.getInstanceOf("compare");
        st.add("e1", lessEquals.getLeft().accept(this))
          .add("e2", lessEquals.getRight().accept(this))
          .add("op", "ble")
          .add("index", labelIndex++);
        return st;
    }

    @Override
    public ST visit(AstMinus minus) {
        var st = templates.getInstanceOf("arithmetic");
        st.add("op", "sub")
                .add("e1", minus.getLeft().accept(this))
                .add("e2", minus.getRight().accept(this));
        return st;
    }

    @Override
    public ST visit(AstMultiply multiply) {
        var st = templates.getInstanceOf("arithmetic");
        st.add("op", "mul")
          .add("e1", multiply.getLeft().accept(this))
          .add("e2", multiply.getRight().accept(this));
        return st;
    }

    @Override
    public ST visit(AstPlus plus) {
        var st = templates.getInstanceOf("arithmetic");
        st.add("op", "add")
          .add("e1", plus.getLeft().accept(this))
          .add("e2", plus.getRight().accept(this));
        return st;
    }

    @Override
    public ST visit(AstId id) {
        var st = templates.getInstanceOf("sequence");
        if (id.getToken().getText().equals("self"))
            st.add("e", "\tmove $a0 $s0");
        else {
            Symbol sym = currentScope.lookup(id.getToken().getText());
            if (!(sym instanceof IdSymbol)) {
                System.err.println(id.getToken().getLine() + ": runtime error");
                return null;
            }
            Location loc = environment.get(sym);
            if (loc == null) {
                System.err.println(id.getToken().getLine() + ": runtime error");
                return null;
            }

            if (loc.getScope() instanceof TypeSymbol) // On heap
                st.add("e", templates.getInstanceOf("loadVar").add("offset", loc.getOffset()));
            else // On stack
                st.add("e", templates.getInstanceOf("loadParam").add("offset", loc.getOffset()));
        }
        return st;
    }

    @Override
    public ST visit(AstParantheses parantheses) {
        return parantheses.getExpr().accept(this);
    }

    @Override
    public ST visit(AstNegate negate) {
        return templates.getInstanceOf("neg").add("e", negate.getExpr().accept(this));
    }

    @Override
    public ST visit(AstNot not) {
        var st = templates.getInstanceOf("sequence");
        st.add("e", not.getExpr().accept(this))
          .add("e", templates.getInstanceOf("not").add("index", labelIndex++));
        return st;
    }

    @Override
    public ST visit(AstIsVoid isVoid) {
        var st = templates.getInstanceOf("sequence");
        st.add("e",isVoid.getExpr().accept(this))
          .add("e", templates.getInstanceOf("isvoid").add("index", labelIndex++));
        return st;
    }

    @Override
    public ST visit(AstNew neww) {
        // TODO: is this correct?
        TypeSymbol type = neww.getTypeSymbol();
        if (type instanceof SelfTypeSymbol) {
            return templates.getInstanceOf("newSelfObj");
        }
        return templates.getInstanceOf("newObj").add("class", type.getName());
    }

    @Override
    public ST visit(AstCase casee) {
        var st = templates.getInstanceOf("case");

        var ids = casee.getIds();
        var types = casee.getTypes();
        var exprs = casee.getExprs();
        int caseIndex = labelIndex++;
        Location loc = new Location(currentScope, -4, 4);

        // Comparable branch class, for ordering by how specific their types are
        class Branch implements Comparable<Branch> {
            final ST st; // ST representation of the branch
            final int width; // how many types are included in this branch?

            public Branch(ST st, int width) {
                this.st = st;
                this.width = width;
            }

            @Override
            public int compareTo(Branch b) {
                return width - b.width;
            }
        }
        PriorityQueue<Branch> branchQueue = new PriorityQueue<>();

        for (int i = 0; i < ids.size(); i++) {
            // Assign the location to the current representation of the id
            currentScope = ids.get(i).getScope();
            environment.put(ids.get(i).getSymbol(), loc);

            TypeSymbol typeSymbol = (TypeSymbol) SymbolTable.globals.lookup(types.get(i).getToken().getText());
            // Highest tag value in the current type's inheritance subtree
            int upperTag = typeSymbol.upperTag();

            var branchSt = templates.getInstanceOf("caseBranch");
            branchSt.add("e", exprs.get(i).accept(this))
                    .add("lowerTag", typeSymbol.tag)
                    .add("upperTag", upperTag)
                    .add("caseIndex", caseIndex);
            branchQueue.add(new Branch(branchSt, upperTag - typeSymbol.tag));
            currentScope = currentScope.getParent();
        }
        // Add first branch without a label
        var top = branchQueue.poll();
        top.st.add("nextBranchIndex", labelIndex + 1);
        st.add("caseBranches", top.st);
        labelIndex++;

        // Chaining of the rest of the branches
        for (var branch : branchQueue) {
            branch.st.add("branchIndex", labelIndex)
                     .add("nextBranchIndex", labelIndex + 1);
            st.add("caseBranches", branch.st);
            labelIndex++;
        }

        // End branch, will call abort if reached
        var endSt = templates.getInstanceOf("endCaseBranch");
        endSt.add("branchIndex", labelIndex++);

        st.add("cond", casee.getCond().accept(this))
          .add("caseBranches", endSt)
          .add("index", caseIndex)
          .add("filenameTag", stringConstant(filename))
          .add("line", casee.getToken().getLine());
        return st;
    }

    @Override
    public ST visit(AstCall call) {
        // Check if callee is null
        var st = templates.getInstanceOf("dispatch");

        // Pushing arguments to the stack, in reverse order
        var params = call.getParams();
        for (int i = params.size() - 1; i >= 0; i--) {
            var push = templates.getInstanceOf("push")
                    .add("arg", params.get(i).accept(this));
            st.add("args", push);
        }

        st.add("caller", 	"\tmove $a0 $s0")
                .add("dispIdx", dispIndex++)
                .add("filenameTag", stringConstant(filename))
                .add("line", call.getToken().getLine());

        // Obtaining the method offset
        String methodName = call.getMethod().getToken().getText();
        Location loc = environment.get(currentScope.lookup(methodName));
        if (loc == null) {
            System.err.println(call.getMethod().getToken().getLine() + ": runtime error");
            return null;
        }
        return st.add("methodOffset", loc.getOffset());
    }

    @Override
    public ST visit(AstWhile whilee) {
        var st = templates.getInstanceOf("while");
        st.add("cond", whilee.getCond().accept(this))
                .add("e", whilee.getExpr().accept(this))
                .add("index", labelIndex++);
        return st;
    }

    @Override
    public ST visit(AstBlock block) {
        var st = templates.getInstanceOf("sequence");
        for (var expr : block.getExprs())
            st.add("e", expr.accept(this));
        return st;
    }

    @Override
    public ST visit(AstLet let) {
        // TODO: operational semantics state that let is recursively evaluated...
        currentScope = let.getScope();
        var st = templates.getInstanceOf("sequence");
        // Allocate memory
        st.add("e", templates.getInstanceOf("mem").add("n", -4 * let.size()));

        for (int i = 0; i < let.size(); i++) {
            currentScope = let.getScope();
            var assignSt = templates.getInstanceOf("assignParam");
            if (let.isAssigned(i))
                st.add("e", let.getValue(i).accept(this));
            else {
                TypeSymbol typeSymbol = (TypeSymbol) SymbolTable.globals.lookup(let.getType(i).getToken().getText());
                if (typeSymbol == TypeSymbol.INT)
                    st.add("e", "\tla $a0 int_const" + intConstant(0));
                else if (typeSymbol == TypeSymbol.STRING)
                    st.add("e", "\tla $a0 str_const" + stringConstant(""));
                else if (typeSymbol == TypeSymbol.BOOL)
                    st.add("e", "\tla $a0 bool_const0");
                else
                    st.add("e", "\tli $a0 0");
            }
            Location loc = new Location(currentScope, -4 * (i + 1), 4);
            environment.put(let.getId(i).getSymbol(), loc);
            assignSt.add("offset", loc.getOffset());
            st.add("e", assignSt);
        }

        currentScope = let.getScope();
        st.add("e", let.getBlock().accept(this));

        // Free memory
        st.add("e", templates.getInstanceOf("mem").add("n", 4 * let.size()));

        currentScope = currentScope.getParent();
        return st;
    }

    @Override
    public ST visit(AstType type) {
        return null;
    }

    private class Location {
        private final Scope scope;
        private final int offset;
        private final int size;

        public Location(Scope scope, int offset, int size) {
            this.scope = scope;
            this.offset = offset;
            this.size = size;
        }

        public Scope getScope() {
            return scope;
        }

        public int getOffset() {
            return offset;
        }

        public int getSize() {
            return size;
        }
    }
}
