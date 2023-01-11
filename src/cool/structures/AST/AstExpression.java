package cool.structures.AST;

import cool.structures.TypeSymbol;
import org.antlr.v4.runtime.Token;

// Expression node
public abstract class AstExpression extends AstNode {
    Token token;
    TypeSymbol typeSymbol;

    public AstExpression(Token token) {
        this.token = token;
    }

    public TypeSymbol getTypeSymbol() {
        return typeSymbol;
    }

    public void setTypeSymbol(TypeSymbol typeSymbol) {
        this.typeSymbol = typeSymbol;
    }

    public Token getToken() {
        return token;
    }
}
