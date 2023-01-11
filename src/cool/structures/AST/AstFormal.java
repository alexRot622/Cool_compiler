package cool.structures.AST;

import org.antlr.v4.runtime.Token;

public class AstFormal extends AstNode {
    Token id;
    Token type;

    public Token getId() {
        return id;
    }

    public Token getType() {
        return type;
    }

    public AstFormal(Token id, Token type) {
        this.id = id;
        this.type = type;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
