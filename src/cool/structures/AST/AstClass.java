package cool.structures.AST;

import cool.parser.CoolParser;
import cool.structures.Scope;
import cool.structures.TypeSymbol;

import java.util.List;

// Class node
public class AstClass extends AstNode {
    CoolParser.ClassContext ctx;
    AstType name;
    AstType parent;
    List<AstFeature> features;
    TypeSymbol symbol;

    public AstType getName() {
        return name;
    }

    public AstType getParent() {
        return parent;
    }

    public List<AstFeature> getFeatures() {
        return features;
    }

    public AstClass(CoolParser.ClassContext ctx, List<AstFeature> features, AstType className, AstType parentName) {
        this.ctx = ctx;
        this.features = features;
        this.name = className;
        this.parent = parentName;
    }

    public TypeSymbol getSymbol() {
        return symbol;
    }

    public CoolParser.ClassContext getCtx() {
        return ctx;
    }

    public void setSymbol(TypeSymbol symbol) {
        this.symbol = symbol;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean hasInheritanceCycle() {
        if (getParent().getToken() == null)
            return false;

        // Turtle and Hare algorithm
        Scope slow = symbol, fast = symbol;
        while (slow != null && fast != null) {
            // Slow takes 1 step
            slow = slow.getParent();

            // Fast takes 2 steps
            fast = fast.getParent();
            if (fast != null)
                fast = fast.getParent();

            if (slow == fast && slow != null)
                return true;
        }
        return false;
    }
}
