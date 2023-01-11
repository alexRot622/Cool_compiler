package cool.structures.AST;

// AST Node abstract class.
// Accepts a visitor.
public abstract class AstNode {
    public <T> T accept(AstVisitor<T> visitor) {
        return null;
    }
}

