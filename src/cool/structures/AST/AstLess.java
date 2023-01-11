package cool.structures.AST;

import org.antlr.v4.runtime.Token;

public class AstLess extends AstRelational {
    public AstLess(Token token, AstExpression left, AstExpression right) {
        super(token, left, right);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
