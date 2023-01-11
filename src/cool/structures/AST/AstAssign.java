package cool.structures.AST;

import org.antlr.v4.runtime.Token;

public class AstAssign extends AstExpression {
    AstId id;
    AstExpression expr;

    public AstExpression getExpr() {
        return expr;
    }

    public AstId getId() {
        return id;
    }

    public AstAssign(Token token, AstId id, AstExpression expr) {
        super(token);
        this.id = id;
        this.expr = expr;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
