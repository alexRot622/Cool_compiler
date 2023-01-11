package cool.structures.AST;

import org.antlr.v4.runtime.Token;

public class AstPlus extends AstArithmetic {
    public AstPlus(Token token, AstExpression left, AstExpression right) {
        super(token, left, right);
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
