package cool.structures.AST;

import org.antlr.v4.runtime.Token;

public class AstWhile extends AstExpression {
    AstExpression cond, expr;

    public AstWhile(Token token, AstExpression cond, AstExpression expr) {
        super(token);
        this.cond = cond;
        this.expr = expr;
    }

    public AstExpression getCond() {
        return cond;
    }

    public AstExpression getExpr() {
        return expr;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
