package cool.structures.AST;

public interface AstVisitor<T> {
    T visit(AstProgram program);
    T visit(AstClass classs);
    T visit(AstMethod method);
    T visit(AstAttribute attribute);
    T visit(AstFormal formal);
    T visit(AstInt intt);
    T visit(AstString string);
    T visit(AstBool bool);
    T visit(AstAssign assign);
    T visit(AstDispatch dispatch);
    T visit(AstIf iff);
    T visit(AstDivide divide);
    T visit(AstEquals equals);
    T visit(AstLess less);
    T visit(AstLessEquals lessEquals);
    T visit(AstMinus minus);
    T visit(AstMultiply multiply);
    T visit(AstPlus plus);
    T visit(AstId id);
    T visit(AstParantheses parantheses);
    T visit(AstNegate negate);
    T visit(AstNot not);
    T visit(AstIsVoid isVoid);
    T visit(AstNew neww);
    T visit(AstCase casee);
    T visit(AstCall call);
    T visit(AstWhile whilee);
    T visit(AstBlock block);
    T visit(AstLet let);
    T visit(AstType type);
}
