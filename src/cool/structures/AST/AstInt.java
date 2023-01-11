package cool.structures.AST;

import org.antlr.v4.runtime.Token;

// Integer literal node
public class AstInt extends AstExpression {
    public AstInt(Token token) {
        super(token);
    }

    public Token getToken() {
        return token;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
