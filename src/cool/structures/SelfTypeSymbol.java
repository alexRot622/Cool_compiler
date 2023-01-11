package cool.structures;

public class SelfTypeSymbol extends TypeSymbol {
    public SelfTypeSymbol(Scope parent) {
        super(parent, "SELF_TYPE");
    }

    public TypeSymbol infer(Scope scope) {
        TypeSymbol result = null;

        if (scope instanceof TypeSymbol)
            result = (TypeSymbol) scope;
        while (scope != parent && scope != null) {
            scope = scope.getParent();
            if (result == null && scope instanceof TypeSymbol)
                result = (TypeSymbol) scope;
        }

        return result;
    }
}
