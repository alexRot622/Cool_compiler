package cool.structures.AST;

import cool.structures.Scope;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public class AstCase extends AstExpression {
    AstExpression cond;
    List<AstId> ids;
    List<AstType> types;
    List<AstExpression> exprs;
    List<Scope> scopes;

    public AstCase(Token token, AstExpression cond, List<AstId> ids, List<AstType> types, List<AstExpression> exprs) {
        super(token);
        this.cond = cond;
        this.ids = ids;
        this.types = types;
        this.exprs = exprs;
        this.scopes = new ArrayList<>();
    }

    public AstExpression getCond() {
        return cond;
    }

    public List<AstId> getIds() {
        return ids;
    }

    public List<AstType> getTypes() {
        return types;
    }

    public List<AstExpression> getExprs() {
        return exprs;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
