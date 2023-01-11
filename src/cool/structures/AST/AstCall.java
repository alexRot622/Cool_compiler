package cool.structures.AST;

import org.antlr.v4.runtime.Token;

import java.util.List;

public class AstCall extends AstExpression {
    AstId method;
    List<AstExpression> params;

    public AstCall(Token token, AstId method, List<AstExpression> params) {
        super(token);
        this.method = method;
        this.params = params;
    }

    public AstId getMethod() {
        return method;
    }

    public List<AstExpression> getParams() {
        return params;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
