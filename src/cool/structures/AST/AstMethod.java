package cool.structures.AST;

import cool.structures.MethodSymbol;

import java.util.List;

public class AstMethod extends AstFeature {
    List<AstFormal> parameters;

    MethodSymbol symbol;

    public List<AstFormal> getParameters() {
        return parameters;
    }

    public AstMethod(AstId id, AstType type, AstExpression expr, List<AstFormal> parameters) {
        this.id = id;
        this.type = type;
        this.expr = expr;
        this.parameters = parameters;
    }

    public MethodSymbol getSymbol() {
        return symbol;
    }

    public void setSymbol(MethodSymbol symbol) {
        this.symbol = symbol;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
