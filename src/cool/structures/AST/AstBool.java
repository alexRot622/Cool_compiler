package cool.structures.AST;

import org.antlr.v4.runtime.Token;

public class AstBool extends AstExpression {
    public AstBool(Token token) {
        super(token);
    }

    public Token getToken() {
        return token;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
