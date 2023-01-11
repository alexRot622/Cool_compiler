package cool.structures.AST;

import cool.parser.CoolParser;
import cool.structures.Scope;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public class AstLet extends AstExpression {
    private static class Declaration {
        AstId id;
        AstType type;
        Boolean isAssigned;
        AstExpression value;
    }
    List<Declaration> decls;
    AstExpression block;
    Scope scope;

    public AstLet(Token token, List<Token> decls, List<AstExpression> values, AstExpression block) {
        super(token);
        int valueNum = 0;

        this.decls = new ArrayList<>();
        try {
            for (int declNum = 0; declNum < decls.size();) {
                var decl = new Declaration();
                decl.id = new AstId(decls.get(declNum++));
                decl.type = new AstType(decls.get(declNum++));
                if (declNum < decls.size() && decls.get(declNum).getType() == CoolParser.ASSN) {
                    decl.isAssigned = true;
                    decl.value = values.get(valueNum++);
                    declNum++;
                } else decl.isAssigned = false;
                this.decls.add(decl);
            }
        }
        catch (IndexOutOfBoundsException ignored) {}
        this.block = block;
    }

    public AstId getId(int i) {
        return decls.get(i).id;
    }

    public AstType getType(int i) {
        return decls.get(i).type;
    }

    public boolean isAssigned(int i) {
        return decls.get(i).isAssigned;
    }

    public AstExpression getValue(int i) {
        return decls.get(i).value;
    }

    public int size() {
        return decls.size();
    }

    public AstExpression getBlock() {
        return block;
    }


    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
