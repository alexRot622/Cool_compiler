package cool.structures.AST;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class AstProgram extends AstNode {
    List<AstClass> classes;

    public List<AstClass> getClasses() {
        return classes;
    }

    public AstProgram(List<AstClass> classes) {
        this.classes = classes;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
