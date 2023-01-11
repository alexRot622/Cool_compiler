package cool.structures;

import cool.structures.AST.*;
import org.antlr.v4.runtime.ParserRuleContext;

public class AstInheritancePassVisitor implements AstVisitor<Void> {
    TypeSymbol currentClass;
    ParserRuleContext ctx = null;

    @Override
    public Void visit(AstProgram program) {
        for (var classs : program.getClasses())
            classs.accept(this);

        return null;
    }

    // Add parent typeSymbol to AST, check illegal parent names and undefined parents
    @Override
    public Void visit(AstClass classs) {
        ctx = classs.getCtx();
        currentClass = classs.getSymbol();

        if (classs.getParent().getToken() != null) {
            String name = classs.getName().getToken().getText();
            String parentName = classs.getParent().getToken().getText();

            if (parentName.equals(TypeSymbol.INT.name) || parentName.equals(TypeSymbol.STRING.name) ||
                    parentName.equals(TypeSymbol.BOOL.name) || parentName.equals("SELF_TYPE")) {
                SymbolTable.error(ctx, classs.getParent().getToken(), "Class " + name +
                        " has illegal parent " + parentName);
                return null;
            }

            var parent = (TypeSymbol) SymbolTable.globals.lookup(parentName);

            if (parent == null) {
                SymbolTable.error(ctx, classs.getParent().getToken(), "Class " + name +
                        " has undefined parent " + parentName);
                return null;
            }

            classs.getSymbol().setParent(parent);
            parent.addChild(classs.getSymbol());
        }
        else TypeSymbol.OBJECT.addChild(classs.getSymbol());

        for (var feature : classs.getFeatures())
            feature.accept(this);

        return null;
    }

    @Override
    public Void visit(AstMethod method) {
        String typeName = method.getType().getToken().getText();

        TypeSymbol expectedReturnType = (TypeSymbol) currentClass.lookup(typeName);

        if (expectedReturnType == null)
            return null;
        method.getSymbol().setTypeSymbol(expectedReturnType);

        for (var formal : method.getParameters()) {
            TypeSymbol typeSymbol = (TypeSymbol) currentClass.lookup(formal.getType().getText());
            IdSymbol idSymbol = (IdSymbol) method.getSymbol().lookup(formal.getId().getText());
            if (idSymbol != null)
                idSymbol.setTypeSymbol(typeSymbol);
        }
        return null;
    }

    @Override
    public Void visit(AstAttribute attribute) {
        var attrName = attribute.getId().getToken().getText();
        var parent = currentClass.getParent();
        if (parent != null && parent.lookup(attrName) != null) {
            SymbolTable.error(ctx, attribute.getId().getToken(), "Class " + currentClass.name +
                    " redefines inherited attribute " + attrName);
            return null;
        }
        return null;
    }

    // Other AST nodes do not need to be visited.

    @Override
    public Void visit(AstFormal formal) {
        return null;
    }

    @Override
    public Void visit(AstInt intt) {
        return null;
    }

    @Override
    public Void visit(AstString string) {
        return null;
    }

    @Override
    public Void visit(AstBool bool) {
        return null;
    }

    @Override
    public Void visit(AstAssign assign) {
        return null;
    }

    @Override
    public Void visit(AstDispatch dispatch) {
        return null;
    }

    @Override
    public Void visit(AstIf iff) {
        return null;
    }

    @Override
    public Void visit(AstDivide divide) {
        return null;
    }

    @Override
    public Void visit(AstEquals equals) {
        return null;
    }

    @Override
    public Void visit(AstLess less) {
        return null;
    }

    @Override
    public Void visit(AstLessEquals lessEquals) {
        return null;
    }

    @Override
    public Void visit(AstMinus minus) {
        return null;
    }

    @Override
    public Void visit(AstMultiply multiply) {
        return null;
    }

    @Override
    public Void visit(AstPlus plus) {
        return null;
    }

    @Override
    public Void visit(AstId id) {
        return null;
    }

    @Override
    public Void visit(AstParantheses parantheses) {
        return null;
    }

    @Override
    public Void visit(AstNegate negate) {
        return null;
    }

    @Override
    public Void visit(AstNot not) {
        return null;
    }

    @Override
    public Void visit(AstIsVoid isVoid) {
        return null;
    }

    @Override
    public Void visit(AstNew neww) {
        return null;
    }

    @Override
    public Void visit(AstCase casee) {
        return null;
    }

    @Override
    public Void visit(AstCall call) {
        return null;
    }

    @Override
    public Void visit(AstWhile whilee) {
        return null;
    }

    @Override
    public Void visit(AstBlock block) {
        return null;
    }

    @Override
    public Void visit(AstLet let) {
        return null;
    }

    @Override
    public Void visit(AstType type) {
        return null;
    }
}
