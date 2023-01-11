package cool.structures;

import cool.structures.AST.*;
import org.antlr.v4.runtime.ParserRuleContext;

public class AstDefinitionPassVisitor implements AstVisitor<Void> {
    Scope currentScope = null;
    ParserRuleContext ctx = null;

    @Override
    public Void visit(AstProgram program) {
        SymbolTable.defineBasicClasses();
        currentScope = SymbolTable.globals;

        for (var classs : program.getClasses())
            classs.accept(this);

        return null;
    }

    // Add typeSymbol to AST, check illegal names and redefinitions
    @Override
    public Void visit(AstClass classs) {
        ctx = classs.getCtx();

        String name = classs.getName().getToken().getText();
        var typeSymbol = new TypeSymbol(TypeSymbol.OBJECT, name);
        classs.setSymbol(typeSymbol);

        if (name.equals("SELF_TYPE")) {
            SymbolTable.error(ctx, classs.getName().getToken(), "Class has illegal name SELF_TYPE");
            return null;
        }
        if (!currentScope.add(typeSymbol)) {
            SymbolTable.error(ctx, classs.getName().getToken(), "Class " + name + " is redefined");
            return null;
        }

        currentScope = typeSymbol;
        for (var feature : classs.getFeatures())
            feature.accept(this);

        currentScope = SymbolTable.globals;
        return null;
    }

    @Override
    public Void visit(AstMethod method) {
        String methodName = method.getId().getToken().getText();
        MethodSymbol methodSymbol = new MethodSymbol(currentScope, null, methodName);
        method.setSymbol(methodSymbol);

        var definedMethod = currentScope.lookup(methodName);
        if (definedMethod instanceof MethodSymbol &&
            ((MethodSymbol) definedMethod).parent == currentScope) {
                SymbolTable.error(ctx, method.getId().getToken(), "Class " +
                                  ((TypeSymbol) currentScope).name + " redefines method " + methodName);
                return null;
        }

        currentScope.add(methodSymbol);

        currentScope = methodSymbol;
        for (var formal : method.getParameters())
            formal.accept(this);
        method.getExpr().accept(this);
        currentScope = currentScope.getParent();

        return null;
    }

    @Override
    public Void visit(AstAttribute attribute) {
        String idName = attribute.getId().getToken().getText();
        if (idName.equals("self")) {
            SymbolTable.error(ctx, attribute.getId().getToken(), "Class " +
                    ((TypeSymbol) currentScope).name + " has attribute with illegal name self");
            return null;
        }

        var id = currentScope.lookup(idName);
        if (id != null) {
            SymbolTable.error(ctx, attribute.getId().getToken(), "Class " +
                    ((TypeSymbol) currentScope).name + " redefines attribute " + idName);
            return null;
        }

        var idSymbol = new IdSymbol(null, idName);

        attribute.getId().setSymbol(idSymbol);
        attribute.getId().setScope(currentScope);
        currentScope.add(idSymbol);

        if (attribute.isAssigned()) {
            attribute.getExpr().accept(this);
        }
        return null;
    }

    @Override
    public Void visit(AstFormal formal) {
        MethodSymbol method = (MethodSymbol) currentScope;
        TypeSymbol classs = (TypeSymbol) method.getParent();

        String idName = formal.getId().getText();
        if (idName.equals("self") || idName.equals("SELF_TYPE")) {
            SymbolTable.error(ctx, formal.getId(), "Method " + method.name +
                    " of class " + classs.name + " has formal parameter with illegal name " + idName);
            return null;
        }

        var idSymbol = new IdSymbol(null, idName);
        if (!currentScope.add(idSymbol)) {
            SymbolTable.error(ctx, formal.getId(), "Method " + method.name +
                    " of class " + classs.name + " redefines formal parameter " + idName);
            return null;
        }

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
        assign.getExpr().accept(this);
        return null;
    }

    @Override
    public Void visit(AstDispatch dispatch) {
        dispatch.getCaller().accept(this);
        for (var param : dispatch.getParams())
            param.accept(this);
        return null;
    }

    @Override
    public Void visit(AstIf iff) {
        iff.getCond().accept(this);
        iff.getThenBranch().accept(this);
        iff.getElseBranch().accept(this);
        return null;
    }

    @Override
    public Void visit(AstDivide divide) {
        divide.getLeft().accept(this);
        divide.getRight().accept(this);
        return null;
    }

    @Override
    public Void visit(AstEquals equals) {
        equals.getLeft().accept(this);
        equals.getRight().accept(this);
        return null;
    }

    @Override
    public Void visit(AstLess less) {
        less.getLeft().accept(this);
        less.getRight().accept(this);
        return null;
    }

    @Override
    public Void visit(AstLessEquals lessEquals) {
        lessEquals.getLeft().accept(this);
        lessEquals.getRight().accept(this);
        return null;
    }

    @Override
    public Void visit(AstMinus minus) {
        minus.getLeft().accept(this);
        minus.getRight().accept(this);
        return null;
    }

    @Override
    public Void visit(AstMultiply multiply) {
        multiply.getLeft().accept(this);
        multiply.getRight().accept(this);
        return null;
    }

    @Override
    public Void visit(AstPlus plus) {
        plus.getLeft().accept(this);
        plus.getRight().accept(this);
        return null;
    }

    @Override
    public Void visit(AstId id) {
        return null;
    }

    @Override
    public Void visit(AstParantheses parantheses) {
        parantheses.getExpr().accept(this);
        return null;
    }

    @Override
    public Void visit(AstNegate negate) {
        negate.getExpr().accept(this);
        return null;
    }

    @Override
    public Void visit(AstNot not) {
        not.getExpr().accept(this);
        return null;
    }

    @Override
    public Void visit(AstIsVoid isVoid) {
        isVoid.getExpr().accept(this);
        return null;
    }

    @Override
    public Void visit(AstNew neww) {
        return null;
    }

    @Override
    public Void visit(AstCase casee) {
        var ids = casee.getIds();
        var exprs = casee.getExprs();

        casee.getCond().accept(this);
        for (int i = 0; i < ids.size(); i++) {
            AstId id = ids.get(i);
            Scope scope = new DefaultScope(currentScope);
            id.setScope(scope);

            String idName = id.getToken().getText();
            if (idName.equals("self")) {
                SymbolTable.error(ctx, id.getToken(), "Case variable has illegal name " + idName);
                continue;
            }

            var idSymbol = new IdSymbol(null, idName);
            id.setSymbol(idSymbol);
            scope.add(idSymbol);
            currentScope = scope;
            exprs.get(i).accept(this);
            currentScope = currentScope.getParent();
        }
        return null;
    }

    @Override
    public Void visit(AstCall call) {
        for (var expr : call.getParams())
            expr.accept(this);
        return null;
    }

    @Override
    public Void visit(AstWhile whilee) {
        whilee.getCond().accept(this);
        whilee.getExpr().accept(this);
        return null;
    }

    @Override
    public Void visit(AstBlock block) {
        for (var expr : block.getExprs())
            expr.accept(this);
        return null;
    }

    @Override
    public Void visit(AstLet let) {
        let.setScope(new DefaultScope(currentScope));
        for (int i = 0; i < let.size(); i++) {
            AstId id = let.getId(i);
            id.setScope(let.getScope());

            String idName = id.getToken().getText();
            if (idName.equals("self")) {
                SymbolTable.error(ctx, id.getToken(), "Let variable has illegal name " + idName);
                continue;
            }

            if (let.getValue(i) != null)
                let.getValue(i).accept(this);

            var idSymbol = new IdSymbol(null, idName);
            id.setSymbol(idSymbol);
            //let.getScope().add(idSymbol);new
        }
        currentScope = let.getScope();
        let.getBlock().accept(this);
        currentScope = currentScope.getParent();
        return null;
    }

    @Override
    public Void visit(AstType type) {
        if (currentScope.lookup(type.getToken().getText()) == null)
            currentScope.add(new TypeSymbol(currentScope, type.getToken().getText()));
        return null;
    }
}
