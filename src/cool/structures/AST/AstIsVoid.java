package cool.structures.AST;

import org.antlr.v4.runtime.Token;

public class AstIsVoid extends AstExpression {
    AstExpression expr;

    public AstIsVoid(Token token, AstExpression expr) {
        super(token);
        this.expr = expr;
    }

    public AstExpression getExpr() {
        return expr;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
