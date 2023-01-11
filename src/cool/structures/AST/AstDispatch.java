package cool.structures.AST;

import org.antlr.v4.runtime.Token;

import java.util.List;

public class AstDispatch extends AstExpression {
    AstExpression caller;
    AstType type;
    AstId callee;
    List<AstExpression> params;

    public AstExpression getCaller() {
        return caller;
    }

    public AstType getType() {
        return type;
    }

    public AstId getCallee() {
        return callee;
    }

    public List<AstExpression> getParams() {
        return params;
    }

    public AstDispatch(Token token, AstExpression caller, AstType type, AstId callee, List<AstExpression> params) {
        super(token);
        this.caller = caller;
        this.type = type;
        this.callee = callee;
        this.params = params;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
