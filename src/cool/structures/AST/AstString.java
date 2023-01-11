package cool.structures.AST;

import org.antlr.v4.runtime.Token;

public class AstString extends AstExpression {
    public AstString(Token token) {
        super(token);
    }

    public Token getToken() {
        return token;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
