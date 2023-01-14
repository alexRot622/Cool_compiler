package cool.structures;

import org.stringtemplate.v4.ST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.Math.min;

public class TypeSymbol extends Symbol implements Scope {
    protected Map<String, Symbol> symbols = new LinkedHashMap<>();
    protected Scope parent;
    protected ArrayList<TypeSymbol> children;
    protected int tag = -1;

    public TypeSymbol(Scope parent, String name) {
        super(name);
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public static TypeSymbol getClass(Scope scope) {
        while (scope != null && !(scope instanceof TypeSymbol))
            scope = scope.getParent();

        if (scope == null)
            return null;

        return (TypeSymbol) scope;
    }

    public void addChild(TypeSymbol symbol) {
        children.add(symbol);
    }

    public ArrayList<TypeSymbol> getChildren() {
        return children;
    }

    @Override
    public boolean add(Symbol sym) {
        // Ne asigurăm că simbolul nu există deja în domeniul de vizibilitate
        // curent.
        if (symbols.containsKey(sym.getName()))
            return false;

        symbols.put(sym.getName(), sym);

        return true;
    }

    @Override
    public Symbol lookup(String s) {
        if (s.equals("SELF_TYPE"))
            return new SelfTypeSymbol(this);

        var sym = symbols.get(s);

        if (sym != null)
            return sym;

        if (s.equals(name))
            return this;

        // Dacă nu găsim simbolul în domeniul de vizibilitate curent, îl căutăm
        // în domeniul de deasupra.
        if (parent != null)
            return parent.lookup(s);

        return null;
    }

    @Override
    public Scope getParent() {
        return parent;
    }

    public void setParent(Scope parent) {
        this.parent = parent;
    }

    public Map<String, Symbol> getSymbols() {
        return symbols;
    }

    public boolean conformsTo(TypeSymbol p) {
        TypeSymbol c = this;
        if (c instanceof SelfTypeSymbol && p instanceof SelfTypeSymbol)
            return ((TypeSymbol) c.parent).conformsTo((TypeSymbol) p.parent);
        else if (c instanceof SelfTypeSymbol)
            c = (TypeSymbol) c.parent;

        // Everything conforms to Object
        if (p == TypeSymbol.OBJECT)
            return true;

        while (c != null) {
            if (c == p)
                return true;
            if (!(c.parent instanceof TypeSymbol))
                return false;
            c = (TypeSymbol) c.parent;
        }

        return false;
    }

    private ArrayList<TypeSymbol> getInheritanceList() {
        ArrayList<TypeSymbol> list = new ArrayList<>();

        TypeSymbol t = this;
        do {
            list.add(0, t);
            if (!(t.parent instanceof TypeSymbol))
                return list;
            t = (TypeSymbol) t.parent;
        } while (t != TypeSymbol.OBJECT);

        return list;
    }

    public TypeSymbol join(TypeSymbol typeSymbol) {
        ArrayList<TypeSymbol> list1 = this.getInheritanceList();
        ArrayList<TypeSymbol> list2 = typeSymbol.getInheritanceList();

        TypeSymbol result = TypeSymbol.OBJECT;
        for (int i = 0; i < min(list1.size(), list2.size()); i++) {
            if (list1.get(i) != list2.get(i))
                return result;
            result = list1.get(i);
        }

        return result;
    }

    public static HashMap<Integer, TypeSymbol> tagMap = new HashMap<>();
    public int setTags() {
        if (this != OBJECT)
            return 0;

        int tag = 1;
        for (var sym : children)
            tag = sym.setTags(tag);
        return tag;
    }

    private int setTags(int tag) {
        if (this.tag < 0) {
            this.tag = tag;
            tagMap.put(tag, this);
            tag++;
        }
        for (var sym : children)
            tag = sym.setTags(tag);
        return tag;
    }

    public static final TypeSymbol OBJECT = new TypeSymbol(null,"Object");
    public static final TypeSymbol IO     = new TypeSymbol(TypeSymbol.OBJECT, "IO");
    public static final TypeSymbol INT    = new TypeSymbol(TypeSymbol.OBJECT, "Int");
    public static final TypeSymbol STRING = new TypeSymbol(TypeSymbol.OBJECT, "String");
    public static final TypeSymbol BOOL   = new TypeSymbol(TypeSymbol.OBJECT, "Bool");

    // Special type SELF_TYPE

    // Add methods to basic classes, as defined in section 8 of the manual.
    // Object Class
    static {
        // abort() : Object
        MethodSymbol abort = new MethodSymbol(OBJECT, OBJECT, "abort");
        OBJECT.add(abort);

        // type_name() : String
        MethodSymbol type_name = new MethodSymbol(OBJECT, STRING, "type_name");
        OBJECT.add(type_name);

        // copy() : SELF_TYPE
        MethodSymbol copy = new MethodSymbol(OBJECT, new SelfTypeSymbol(OBJECT),"copy");
        OBJECT.add(copy);

        // TODO: is this ok?
        OBJECT.add(IO);
        OBJECT.add(INT);
        OBJECT.add(STRING);
        OBJECT.add(BOOL);
        OBJECT.tag = 0;
        tagMap.put(0, OBJECT);

        OBJECT.addChild(IO);
        OBJECT.addChild(INT);
        OBJECT.addChild(STRING);
        OBJECT.addChild(BOOL);
    }

    // IO Class
    static {
        // out_string(x : String) : SELF_TYPE
        MethodSymbol out_string = new MethodSymbol(IO, new SelfTypeSymbol(IO),"out_string");
        out_string.add(new IdSymbol(STRING,"x"));
        IO.add(out_string);

        // out_int(x : Int) : SELF_TYPE
        MethodSymbol out_int = new MethodSymbol(IO, new SelfTypeSymbol(IO),"out_int");
        out_int.add(new IdSymbol(INT,"x"));
        IO.add(out_int);

        // in_string() : String
        MethodSymbol in_string = new MethodSymbol(IO, STRING, "in_string");
        IO.add(in_string);

        MethodSymbol in_int = new MethodSymbol(IO, INT, "in_int");
        IO.add(in_int);
    }

    // String Class
    static {
        // length() : Int
        MethodSymbol length = new MethodSymbol(STRING, INT, "length");
        STRING.add(length);

        // concat(s : String) : String
        MethodSymbol concat = new MethodSymbol(STRING, STRING, "concat");
        concat.add(new IdSymbol(STRING, "s"));
        STRING.add(concat);

        // substr(i : Int, l : Int) : String
        MethodSymbol substr = new MethodSymbol(STRING, STRING, "substr");
        substr.add(new IdSymbol(INT, "i"));
        substr.add(new IdSymbol(INT, "l"));
        STRING.add(substr);
    }

    // For code generation only
    public static final TypeSymbol _INT    = new TypeSymbol(null, "Int");
    public static final TypeSymbol _STRING = new TypeSymbol(null, "String");
    public static final TypeSymbol _BOOL   = new TypeSymbol(null, "Bool");

}
