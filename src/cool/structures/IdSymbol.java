package cool.structures;

public class IdSymbol extends Symbol {
    protected TypeSymbol typeSymbol;

    public IdSymbol(TypeSymbol typeSymbol, String name) {
        super(name);
        this.typeSymbol = typeSymbol;
    }

    public void setTypeSymbol(TypeSymbol typeSymbol) {
        this.typeSymbol = typeSymbol;
    }

    public TypeSymbol getTypeSymbol() {
        return typeSymbol;
    }
}