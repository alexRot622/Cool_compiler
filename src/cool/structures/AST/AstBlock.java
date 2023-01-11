package cool.structures.AST;

import org.antlr.v4.runtime.Token;

import java.util.List;

public class AstBlock extends AstExpression {
    List<AstExpression> exprs;

    public AstBlock(Token token, List<AstExpression> exprs) {
        super(token);
        this.exprs = exprs;
    }

    public List<AstExpression> getExprs() {
        return exprs;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
