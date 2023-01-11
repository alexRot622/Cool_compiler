package cool.structures.AST;

import org.antlr.v4.runtime.Token;

public class AstType extends AstNode {
    Token token;

    public AstType(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
