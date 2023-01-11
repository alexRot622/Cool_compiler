package cool.structures.AST;

import org.antlr.v4.runtime.Token;

public class AstNew extends AstExpression {
    AstType type;

    public AstNew(Token token, AstType type) {
        super(token);
        this.type = type;
    }

    public AstType getType() {
        return type;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
