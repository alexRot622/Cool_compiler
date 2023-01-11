package cool.structures.AST;

import org.antlr.v4.runtime.Token;

public abstract class AstRelational extends AstExpression {
    AstExpression left, right;

    public AstExpression getLeft() {
        return left;
    }

    public AstExpression getRight() {
        return right;
    }

    public AstRelational(Token token, AstExpression left, AstExpression right) {
        super(token);
        this.left = left;
        this.right = right;
    }
}
