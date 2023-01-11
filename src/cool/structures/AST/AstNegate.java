package cool.structures.AST;

import org.antlr.v4.runtime.Token;

public class AstNegate extends AstExpression {
    AstExpression expr;

    public AstNegate(Token token, AstExpression expr) {
        super(token);
        this.expr = expr;
    }

    public AstExpression getExpr() {
        return expr;
    }

    public Token getToken() {
        return token;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
