package cool.structures.AST;

import org.antlr.v4.runtime.Token;

// If construct node
public class AstIf extends AstExpression {
    AstExpression cond;
    AstExpression thenBranch;
    AstExpression elseBranch;

    public AstExpression getCond() {
        return cond;
    }

    public AstExpression getThenBranch() {
        return thenBranch;
    }

    public AstExpression getElseBranch() {
        return elseBranch;
    }

    public AstIf(Token token, AstExpression cond,
                 AstExpression thenBranch,
                 AstExpression elseBranch) {
        super(token);
        this.cond = cond;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
