package cool.structures.AST;

import cool.structures.IdSymbol;
import org.antlr.v4.runtime.Token;

public class AstFormal extends AstNode {
    // TODO (CRISTI): make this class use AstId instead of id Token and IdSymbol
    Token id;
    Token type;
    IdSymbol idSymbol;

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

    public IdSymbol getIdSymbol() {
        return idSymbol;
    }

    public void setIdSymbol(IdSymbol idSymbol) {
        this.idSymbol = idSymbol;
    }
}
