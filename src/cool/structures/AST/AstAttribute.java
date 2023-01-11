package cool.structures.AST;

public class AstAttribute extends AstFeature {
    Boolean assigned;

    public Boolean isAssigned() {
        return assigned;
    }

    public AstAttribute(AstId id, AstType type, AstExpression expr) {
        this.id = id;
        this.type = type;
        this.expr = expr;
        this.assigned = expr != null;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
