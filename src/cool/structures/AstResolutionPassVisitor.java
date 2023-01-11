package cool.structures;

import cool.structures.AST.*;
import org.antlr.v4.runtime.ParserRuleContext;

public class AstResolutionPassVisitor implements AstVisitor<TypeSymbol> {
    Scope currentScope = null;
    ParserRuleContext ctx = null;

    @Override
    public TypeSymbol visit(AstProgram program) {
        currentScope = SymbolTable.globals;
        for (var classs : program.getClasses()) {
            classs.accept(this);
        }
        return null;
    }

    // Check inheritance cycles for classes
    @Override
    public TypeSymbol visit(AstClass classs) {
        ctx = classs.getCtx();
        currentScope = classs.getSymbol();

        var name = classs.getName().getToken().getText();
        if (classs.hasInheritanceCycle()) {
            SymbolTable.error(ctx, classs.getName().getToken(), "Inheritance cycle for class " + name);
            currentScope = currentScope.getParent();
            return null;
        }

        for (var feature : classs.getFeatures())
            feature.accept(this);

        currentScope = SymbolTable.globals;
        return null;
    }

    @Override
    public TypeSymbol visit(AstMethod method) {
        String methodName = method.getId().getToken().getText();

        var definedMethod = currentScope.getParent().lookup(methodName);
        if (definedMethod == null) {
            TypeSymbol expectedReturnType = method.getSymbol().getTypeSymbol();
            if (expectedReturnType == null)
                return null;

            if (method.getSymbol() != null) {
                currentScope = method.getSymbol();
                for (var formal : method.getParameters())
                    formal.accept(this);
                TypeSymbol returnType = method.getExpr().accept(this);
                currentScope = currentScope.getParent();

                if (returnType == null)
                    return null;
                if (!returnType.conformsTo(expectedReturnType)) {
                    SymbolTable.error(ctx, method.getExpr().getToken(), "Type " +
                                      returnType.name + " of the body of method " +
                                      methodName + " is incompatible with declared return type " +
                                      expectedReturnType.name);
                    return null;
                }
            }
            return expectedReturnType;
        }
        if (!(definedMethod instanceof MethodSymbol)) {
            return null;
        }

        var params = method.getSymbol().getFormals();
        var definedParams = ((MethodSymbol) definedMethod).getFormals();

        if (params.size() != definedParams.size()) {
            SymbolTable.error(ctx, method.getId().getToken(), "Class " +
                    ((TypeSymbol) currentScope).name + " overrides method " + methodName +
                    " with different number of formal parameters");
            return null;
        }

        String declaredTypeName = method.getType().getToken().getText();
        TypeSymbol declaredReturnType = method.getSymbol().getTypeSymbol();
        if (declaredReturnType == null)
            return null;
        TypeSymbol expectedReturnType = ((MethodSymbol) definedMethod).getTypeSymbol();
        if (expectedReturnType == null)
            return null;

        if (!declaredReturnType.conformsTo(expectedReturnType)) {
            SymbolTable.error(ctx, method.getType().getToken(), "Class " +
                    ((TypeSymbol) currentScope).name + " overrides method " + methodName +
                    " but changes return type from " + expectedReturnType.name + " to " + declaredTypeName);
            return null;
        }

        var entryIterator = definedParams.values().iterator();
        if (method.getSymbol() != null) {
            method.getSymbol().setTypeSymbol(declaredReturnType);
            currentScope = method.getSymbol();
            for (var formal : method.getParameters()) {
                String formalName = formal.getId().getText();
                TypeSymbol formalType = formal.accept(this);
                if (formalType == null) {
                    currentScope = currentScope.getParent();
                    return null;
                }
                IdSymbol definedParam = ((IdSymbol) entryIterator.next());
                if (definedParam != null) {
                    TypeSymbol expectedType = definedParam.getTypeSymbol();
                    if (formalType != expectedType) {
                        SymbolTable.error(ctx, formal.getType(), "Class " +
                                ((TypeSymbol) currentScope.getParent()).name + " overrides method " + methodName +
                                " but changes type of formal parameter " + formalName +
                                " from " + expectedType.name + " to " + formalType.name);
                        currentScope = currentScope.getParent();
                        return null;
                    }
                }
            }

            TypeSymbol returnType = method.getExpr().accept(this);
            if (returnType == null) {
                currentScope = currentScope.getParent();
                return null;
            }
            if (!returnType.conformsTo(declaredReturnType)) {
                SymbolTable.error(ctx, method.getExpr().getToken(), "Type " +
                        returnType.name + " of the body of method " +
                        methodName + " is incompatible with declared return type " +
                        expectedReturnType.name);
                currentScope = currentScope.getParent();
                return null;
            }
            currentScope = currentScope.getParent();
        }
        return expectedReturnType;
    }

    @Override
    public TypeSymbol visit(AstAttribute attribute) {
        String idName = attribute.getId().getToken().getText();
        String typeName = attribute.getType().getToken().getText();

        // Symbol not defined, so there was an error during the definition pass and the attribute must be skipped
        if (attribute.getId().getSymbol() == null)
            return null;

        TypeSymbol declType = (TypeSymbol) currentScope.lookup(typeName);
        if (declType == null) {
            SymbolTable.error(ctx, attribute.getType().getToken(), "Class " +
                    ((TypeSymbol) currentScope).name + " has attribute " + idName +
                    " with undefined type " + typeName);
            return null;
        }
        attribute.getId().setTypeSymbol(declType);
        IdSymbol idSymbol = (IdSymbol) currentScope.lookup(idName);
        if (idSymbol == null) {
            return null;
        }
        idSymbol.setTypeSymbol(declType);

        if (attribute.isAssigned()) {
            TypeSymbol valueType = attribute.getExpr().accept(this);
            if (valueType == null)
                return null;

            if (!valueType.conformsTo(declType)) {
                SymbolTable.error(ctx, attribute.getExpr().getToken(), "Type " + valueType.name +
                        " of initialization expression of attribute " + idName +
                        " is incompatible with declared type " + declType.name);
                return null;
            }
            return valueType;
        }
        return declType;
    }

    @Override
    public TypeSymbol visit(AstFormal formal) {
        MethodSymbol method = (MethodSymbol) currentScope;
        TypeSymbol classs = (TypeSymbol) method.getParent();

        String idName = formal.getId().getText();
        String typeName = formal.getType().getText();
        if (typeName.equals("SELF_TYPE")) {
            SymbolTable.error(ctx, formal.getType(), "Method " + method.name +
                    " of class " + classs.name + " has formal parameter " + idName +
                    " with illegal type " + typeName);
            return null;
        }

        TypeSymbol type = (TypeSymbol) currentScope.lookup(typeName);
        if (type == null) {
            SymbolTable.error(ctx, formal.getType(), "Method " + method.name +
                    " of class " + classs.name + " has formal parameter " + idName +
                    " with undefined type " + typeName);
            return null;
        }

        IdSymbol id = (IdSymbol) method.lookup(idName);
        if (id == null) {
            return null;
        }
        id.setTypeSymbol(type);
        return type;
    }

    @Override
    public TypeSymbol visit(AstInt intt) {
        return TypeSymbol.INT;
    }

    @Override
    public TypeSymbol visit(AstString string) {
        return TypeSymbol.STRING;
    }

    @Override
    public TypeSymbol visit(AstBool bool) {
        return TypeSymbol.BOOL;
    }

    @Override
    public TypeSymbol visit(AstAssign assign) {
        String idName = assign.getId().getToken().getText();

        if (idName.equals("self")) {
            SymbolTable.error(ctx, assign.getId().getToken(), "Cannot assign to self");
            return null;
        }

        IdSymbol idSymbol = ((IdSymbol) currentScope.lookup(idName));
        if (idSymbol == null) {
            return null;
        }

        TypeSymbol declType = idSymbol.getTypeSymbol();
        if (declType == null)
            return null;

        TypeSymbol exprType = assign.getExpr().accept(this);
        if (exprType == null)
            return null;

        if (!exprType.conformsTo(declType)) {
            SymbolTable.error(ctx, assign.getExpr().getToken(), "Type " + exprType.name +
                              " of assigned expression is incompatible with declared type " + declType.name +
                              " of identifier " + idName);
            return null;
        }

        assign.setTypeSymbol(exprType);
        return exprType;
    }

    @Override
    public TypeSymbol visit(AstDispatch dispatch) {
        TypeSymbol exprType = dispatch.getCaller().accept(this);
        if (exprType == null)
            return null;

        String typeName;
        TypeSymbol callerType;
        if (dispatch.getType().getToken() != null) {
            typeName = dispatch.getType().getToken().getText();
            if (typeName.equals("SELF_TYPE")) {
                SymbolTable.error(ctx, dispatch.getType().getToken(), "Type of static dispatch cannot be SELF_TYPE");
                return null;
            }

            var castSymbol = currentScope.lookup(typeName);
            if (castSymbol == null) {
                SymbolTable.error(ctx, dispatch.getType().getToken(), "Type " + typeName +
                        " of static dispatch is undefined");
                return null;
            }
            if (!(castSymbol instanceof TypeSymbol))
                return null;
            callerType = (TypeSymbol) castSymbol;

            if(!exprType.conformsTo(callerType)) {
                SymbolTable.error(ctx, dispatch.getType().getToken(), "Type " + typeName +
                                  " of static dispatch is not a superclass of type " + exprType.name);
                return null;
            }
        }
        else {
            typeName = exprType.name;
            callerType = exprType;
        }

        // Print actual class name instead of SELF_TYPE
        if (exprType instanceof SelfTypeSymbol)
            typeName = ((TypeSymbol) exprType.parent).name;

        String methodName = dispatch.getCallee().getToken().getText();
        var methodSymbol = callerType.lookup(methodName);
        if (methodSymbol == null) {
            SymbolTable.error(ctx, dispatch.getCallee().getToken(), "Undefined method " +
                              methodName + " in class " + typeName);
            return null;
        }
        if (!(methodSymbol instanceof MethodSymbol))
            return null;
        MethodSymbol method = (MethodSymbol) methodSymbol;

        var callParams = dispatch.getParams();
        var definedParams = method.getFormals();

        if (callParams.size() != definedParams.size()) {
            SymbolTable.error(ctx, dispatch.getCallee().getToken(), "Method " +
                    methodName + " of class " + typeName + " is applied to wrong number of arguments");
            return null;
        }

        var entryIterator = definedParams.values().iterator();
        for (var param : callParams) {
            TypeSymbol paramType = param.accept(this);
            if (paramType == null) {
                return null;
            }
            IdSymbol definedParam = ((IdSymbol) entryIterator.next());
            if (definedParam != null) {
                TypeSymbol expectedType = definedParam.getTypeSymbol();
                if (!paramType.conformsTo(expectedType)) {
                    SymbolTable.error(ctx, param.getToken(), "In call to method " +
                                      methodName + " of class " + typeName + ", actual type " +
                                      paramType.name + " of formal parameter " +
                                      definedParam.name + " is incompatible with declared type " +
                                      expectedType.name);
                    return method.getTypeSymbol();
                }
            }
        }

        if (method.getTypeSymbol() instanceof SelfTypeSymbol)
            return exprType;
        else
            return method.getTypeSymbol();
    }

    @Override
    public TypeSymbol visit(AstIf iff) {
        TypeSymbol thenType = iff.getThenBranch().accept(this);
        if (thenType == null)
            return null;

        TypeSymbol elseType = iff.getElseBranch().accept(this);
        if (elseType == null)
            return null;

        TypeSymbol condType = iff.getCond().accept(this);
        if (condType == null) {
            return null;
        }

        if (condType != TypeSymbol.BOOL)
            SymbolTable.error(ctx, iff.getCond().getToken(), "If condition has type " +
                              condType.name + " instead of Bool");

        return thenType.join(elseType);
    }

    @Override
    public TypeSymbol visit(AstDivide divide) {
        TypeSymbol result = TypeSymbol.INT;

        TypeSymbol leftType = divide.getLeft().accept(this);
        if (leftType == null)
            result = null;
        else if (leftType != TypeSymbol.INT) {
            SymbolTable.error(ctx, divide.getLeft().getToken(), "Operand of / has type " +
                    leftType.name + " instead of Int");
            result = null;
        }

        TypeSymbol rightType = divide.getRight().accept(this);
        if (rightType == null)
            result = null;
        else if (rightType != TypeSymbol.INT) {
            SymbolTable.error(ctx, divide.getRight().getToken(), "Operand of / has type " +
                    rightType.name + " instead of Int");
            result = null;
        }

        return result;
    }

    @Override
    public TypeSymbol visit(AstEquals equals) {
        TypeSymbol result = TypeSymbol.BOOL;
        boolean freelyCompared = true;

        TypeSymbol leftType = equals.getLeft().accept(this);
        if (leftType == null)
            result = null;
        if (leftType == TypeSymbol.INT || leftType == TypeSymbol.BOOL || leftType == TypeSymbol.STRING)
            freelyCompared = false;

        TypeSymbol rightType = equals.getRight().accept(this);
        if (rightType == null)
            result = null;
        if (rightType == TypeSymbol.INT || rightType == TypeSymbol.BOOL || rightType == TypeSymbol.STRING)
            freelyCompared = false;

        if (result != null && !freelyCompared && leftType != rightType) {
            SymbolTable.error(ctx, equals.getToken(), "Cannot compare " + leftType.name +
                              " with " + rightType.name);
            result = null;
        }

        return result;
    }

    @Override
    public TypeSymbol visit(AstLess less) {
        TypeSymbol result = TypeSymbol.BOOL;

        TypeSymbol leftType = less.getLeft().accept(this);
        if (leftType == null)
            result = null;
        else if (leftType != TypeSymbol.INT) {
            SymbolTable.error(ctx, less.getLeft().getToken(), "Operand of < has type " +
                    leftType.name + " instead of Int");
            result = null;
        }

        TypeSymbol rightType = less.getRight().accept(this);
        if (rightType == null)
            result = null;
        else if (rightType != TypeSymbol.INT) {
            SymbolTable.error(ctx, less.getRight().getToken(), "Operand of < has type " +
                    rightType.name + " instead of Int");
            result = null;
        }

        return result;
    }

    @Override
    public TypeSymbol visit(AstLessEquals lessEquals) {
        TypeSymbol result = TypeSymbol.BOOL;

        TypeSymbol leftType = lessEquals.getLeft().accept(this);
        if (leftType == null)
            result = null;
        else if (leftType != TypeSymbol.INT) {
            SymbolTable.error(ctx, lessEquals.getLeft().getToken(), "Operand of <= has type " +
                    leftType.name + " instead of Int");
            result = null;
        }

        TypeSymbol rightType = lessEquals.getRight().accept(this);
        if (rightType == null)
            result = null;
        else if (rightType != TypeSymbol.INT) {
            SymbolTable.error(ctx, lessEquals.getRight().getToken(), "Operand of <= has type " +
                    rightType.name + " instead of Int");
            result = null;
        }

        return result;
    }

    @Override
    public TypeSymbol visit(AstMinus minus) {
        TypeSymbol result = TypeSymbol.INT;

        TypeSymbol leftType = minus.getLeft().accept(this);
        if (leftType == null)
            result = null;
        else if (leftType != TypeSymbol.INT) {
            SymbolTable.error(ctx, minus.getLeft().getToken(), "Operand of - has type " +
                    leftType.name + " instead of Int");
            result = null;
        }

        TypeSymbol rightType = minus.getRight().accept(this);
        if (rightType == null)
            result = null;
        else if (rightType != TypeSymbol.INT) {
            SymbolTable.error(ctx, minus.getRight().getToken(), "Operand of - has type " +
                    rightType.name + " instead of Int");
            result = null;
        }

        return result;
    }

    @Override
    public TypeSymbol visit(AstMultiply multiply) {
        TypeSymbol result = TypeSymbol.INT;

        TypeSymbol leftType = multiply.getLeft().accept(this);
        if (leftType == null)
            result = null;
        else if (leftType != TypeSymbol.INT) {
            SymbolTable.error(ctx, multiply.getLeft().getToken(), "Operand of * has type " +
                    leftType.name + " instead of Int");
            result = null;
        }

        TypeSymbol rightType = multiply.getRight().accept(this);
        if (rightType == null)
            result = null;
        else if (rightType != TypeSymbol.INT) {
            SymbolTable.error(ctx, multiply.getRight().getToken(), "Operand of * has type " +
                    rightType.name + " instead of Int");
            result = null;
        }

        return result;
    }

    @Override
    public TypeSymbol visit(AstPlus plus) {
        TypeSymbol result = TypeSymbol.INT;

        TypeSymbol leftType = plus.getLeft().accept(this);
        if (leftType == null)
            result = null;
        else if (leftType != TypeSymbol.INT) {
            SymbolTable.error(ctx, plus.getLeft().getToken(), "Operand of + has type " +
                              leftType.name + " instead of Int");
            result = null;
        }

        TypeSymbol rightType = plus.getRight().accept(this);
        if (rightType == null)
            result = null;
        else if (rightType != TypeSymbol.INT) {
            SymbolTable.error(ctx, plus.getRight().getToken(), "Operand of + has type " +
                    rightType.name + " instead of Int");
            result = null;
        }

        return result;
    }

    @Override
    public TypeSymbol visit(AstId id) {
        String idName = id.getToken().getText();

        if (idName.equals("self"))
            return new SelfTypeSymbol(TypeSymbol.getClass(currentScope));

        IdSymbol idSymbol = (IdSymbol) currentScope.lookup(idName);
        id.setScope(currentScope);

        if (idSymbol == null) {
            SymbolTable.error(ctx, id.getToken(), "Undefined identifier " + idName);
            return null;
        }

        id.setSymbol(idSymbol);
        id.setTypeSymbol(idSymbol.typeSymbol);
        return idSymbol.typeSymbol;
    }

    @Override
    public TypeSymbol visit(AstParantheses parantheses) {
        TypeSymbol type = parantheses.getExpr().accept(this);
        parantheses.setTypeSymbol(type);
        return type;
    }

    @Override
    public TypeSymbol visit(AstNegate negate) {
        TypeSymbol exprType = negate.getExpr().accept(this);
        if (exprType == null)
            return null;
        if (exprType != TypeSymbol.INT) {
            SymbolTable.error(ctx, negate.getExpr().getToken(), "Operand of ~ has type " + exprType.name + " instead of Int");
            return null;
        }
        return exprType;
    }

    @Override
    public TypeSymbol visit(AstNot not) {
        TypeSymbol exprType = not.getExpr().accept(this);
        if (exprType == null)
            return null;
        if (exprType != TypeSymbol.BOOL) {
            SymbolTable.error(ctx, not.getExpr().getToken(), "Operand of not has type " + exprType.name + " instead of Bool");
            return null;
        }
        return exprType;
    }

    @Override
    public TypeSymbol visit(AstIsVoid isVoid) {
        return null;
    }

    @Override
    public TypeSymbol visit(AstNew neww) {
        String typeName = neww.getType().getToken().getText();

        Symbol typeSymbol = currentScope.lookup(typeName);
        if (typeSymbol == null) {
            SymbolTable.error(ctx, neww.getType().getToken(), "new is used with undefined type " + typeName);
            return null;
        }
        if (!(typeSymbol instanceof TypeSymbol)) {
            return null;
        }

        TypeSymbol returnType;
        returnType = (TypeSymbol) typeSymbol;

        return returnType;
    }

    @Override
    public TypeSymbol visit(AstCase casee) {
        var ids = casee.getIds();
        var types = casee.getTypes();
        var exprs = casee.getExprs();

        TypeSymbol joined = null;
        casee.getCond().accept(this);
        for (int i = 0; i < ids.size(); i++) {
            AstId id = ids.get(i);
            String idName = id.getToken().getText();

            AstType type = types.get(i);
            String typeName = type.getToken().getText();
            if (typeName.equals("SELF_TYPE")) {
                SymbolTable.error(ctx, type.getToken(), "Case variable " + idName +
                        " has illegal type " + typeName);
                continue;
            }

            TypeSymbol typeSymbol = (TypeSymbol) currentScope.lookup(typeName);
            if (typeSymbol == null) {
                SymbolTable.error(ctx, type.getToken(), "Case variable " + idName +
                        " has undefined type " + typeName);
                continue;
            }

            id.setTypeSymbol(typeSymbol);
            if (id.getSymbol() != null)
                id.getSymbol().setTypeSymbol(typeSymbol);

            currentScope = id.getScope();
            TypeSymbol exprType = exprs.get(i).accept(this);
            if (i == 0)
                joined = exprType;
            else if (joined != null) {
                if (exprType == null)
                    joined = null;
                else
                    joined = joined.join(exprType);
            }
            currentScope = currentScope.getParent();
        }
        return joined;
    }

    @Override
    public TypeSymbol visit(AstCall call) {
        String methodName = call.getMethod().getToken().getText();
        String className = TypeSymbol.getClass(currentScope).getName();
        var methodSymbol = currentScope.lookup(methodName);
        if (methodSymbol == null) {
            SymbolTable.error(ctx, call.getMethod().getToken(), "Undefined method " +
                    methodName + " in class " + className);
            return null;
        }
        if (!(methodSymbol instanceof MethodSymbol))
            return null;
        MethodSymbol method = (MethodSymbol) methodSymbol;

        var callParams = call.getParams();
        var definedParams = method.getFormals();

        if (callParams.size() != definedParams.size()) {
            SymbolTable.error(ctx, call.getMethod().getToken(), "Method " +
                    methodName + " of class " + className + " is applied to wrong number of arguments");
            return null;
        }

        var entryIterator = definedParams.values().iterator();
        for (var param : callParams) {
            TypeSymbol paramType = param.accept(this);
            if (paramType == null) {
                return null;
            }
            IdSymbol definedParam = ((IdSymbol) entryIterator.next());
            if (definedParam != null) {
                TypeSymbol expectedType = definedParam.getTypeSymbol();
                if (!paramType.conformsTo(expectedType)) {
                    SymbolTable.error(ctx, param.getToken(), "In call to method " +
                            methodName + " of class " + className + ", actual type " +
                            paramType.name + " of formal parameter " +
                            definedParam.name + " is incompatible with declared type " +
                            expectedType.name);
                    return method.getTypeSymbol();
                }
            }
        }

        return method.getTypeSymbol();
    }

    @Override
    public TypeSymbol visit(AstWhile whilee) {
        TypeSymbol condType = whilee.getCond().accept(this);

        if (condType == null)
            return TypeSymbol.OBJECT;
        if (condType != TypeSymbol.BOOL) {
            SymbolTable.error(ctx, whilee.getCond().getToken(), "While condition has type " +
                              condType.name + " instead of Bool");
            return TypeSymbol.OBJECT;
        }

        return TypeSymbol.OBJECT;
    }

    @Override
    public TypeSymbol visit(AstBlock block) {
        TypeSymbol exprType = null;

        for (var expr : block.getExprs())
            exprType = expr.accept(this);

        return exprType;
    }

    @Override
    public TypeSymbol visit(AstLet let) {
        boolean noErrors = true;
        currentScope = let.getScope();
        for (int i = 0; i < let.size(); i++) {
            AstId id = let.getId(i);
            String idName = id.getToken().getText();

            AstType type = let.getType(i);
            String typeName = type.getToken().getText();
            TypeSymbol declType = (TypeSymbol) let.getScope().lookup(typeName);
            if (declType == null) {
                SymbolTable.error(ctx, type.getToken(), "Let variable " + idName +
                        " has undefined type " + typeName);
                noErrors = false;
                continue;
            }

            if (let.getValue(i) != null) {
                var valueType = let.getValue(i).accept(this);
                if (valueType == null) {
                    noErrors = false;
                    continue;
                }
                if (id.getSymbol() != null) {
                    id.setTypeSymbol(declType);
                    if (id.getSymbol() != null)
                        id.getSymbol().setTypeSymbol(declType);
                    let.getScope().add(id.getSymbol());
                }
                if (!valueType.conformsTo(declType)) {
                    SymbolTable.error(ctx, let.getValue(i).getToken(), "Type " + valueType.name +
                                      " of initialization expression of identifier " + idName +
                                      " is incompatible with declared type " + declType.name);
                    //noErrors = false;
                    continue;
                }
                if (id.getSymbol() != null)
                    id.setTypeSymbol(valueType);
            }
            else if (id.getSymbol() != null) {
                id.setTypeSymbol(declType);
                if (id.getSymbol() != null)
                    id.getSymbol().setTypeSymbol(declType);

                let.getScope().add(id.getSymbol());
            }
        }
        TypeSymbol returnType = null;
        currentScope = let.getScope();
        if (noErrors)
            returnType = let.getBlock().accept(this);
        let.setTypeSymbol(returnType);
        currentScope = currentScope.getParent();
        return returnType;
    }

    @Override
    public TypeSymbol visit(AstType type) {
        return null;
    }
}
