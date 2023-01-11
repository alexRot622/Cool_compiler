package cool.structures.AST;

import org.antlr.v4.runtime.Token;

public abstract class AstFeature extends AstNode {
    AstId id;
    AstType type;
    AstExpression expr;

    public AstId getId() {
        return id;
    }

    public AstType getType() {
        return type;
    }

    public AstExpression getExpr() {
        return expr;
    }
}
