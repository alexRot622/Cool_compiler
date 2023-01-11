package cool.structures;

import cool.parser.CoolParser;
import cool.parser.CoolParserBaseVisitor;
import cool.structures.AST.*;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public class AstConstructionVisitor extends CoolParserBaseVisitor<AstNode> {
    @Override
    public AstNode visitProgram(CoolParser.ProgramContext ctx) {
        List<AstClass> classes = new ArrayList<>();
        for (CoolParser.ClassContext classContext : ctx.classes)
            classes.add((AstClass) visit(classContext));
        return new AstProgram(classes);
    }

    @Override
    public AstNode visitNew(CoolParser.NewContext ctx) {
        return new AstNew(ctx.NEW().getSymbol(), new AstType(ctx.TYPE().getSymbol()));
    }

    @Override
    public AstNode visitMinus(CoolParser.MinusContext ctx) {
        return new AstMinus(ctx.MINUS().getSymbol(), (AstExpression) ctx.left.accept(this),
                (AstExpression) ctx.right.accept(this));
    }

    @Override
    public AstNode visitMultiply(CoolParser.MultiplyContext ctx) {
        return new AstMultiply(ctx.MULT().getSymbol(), (AstExpression) ctx.left.accept(this),
                (AstExpression) ctx.right.accept(this));
    }

    @Override
    public AstNode visitDispatch(CoolParser.DispatchContext ctx) {
        List<AstExpression> params = new ArrayList<>();
        Token type = (ctx.TYPE() == null ? null : ctx.TYPE().getSymbol());

        for (CoolParser.ExprContext exprContext : ctx.params)
            params.add((AstExpression) visit(exprContext));

        return new AstDispatch(ctx.getStart(), (AstExpression) ctx.caller.accept(this),
                new AstType(type),
                new AstId(ctx.callee),
                params);
    }

    @Override
    public AstNode visitIsvoid(CoolParser.IsvoidContext ctx) {
        return new AstIsVoid(ctx.ISVOID().getSymbol(), (AstExpression) ctx.expr().accept(this));
    }

    @Override
    public AstNode visitLess(CoolParser.LessContext ctx) {
        return new AstLess(ctx.LESS().getSymbol(), (AstExpression) ctx.left.accept(this),
                (AstExpression) ctx.right.accept(this));
    }

    @Override
    public AstNode visitWhile(CoolParser.WhileContext ctx) {
        return new AstWhile(ctx.getStart(), (AstExpression) ctx.cond.accept(this),
                (AstExpression) ctx.block.accept(this));
    }

    @Override
    public AstNode visitEquals(CoolParser.EqualsContext ctx) {
        return new AstEquals(ctx.EQ().getSymbol(), (AstExpression) ctx.left.accept(this),
                (AstExpression) ctx.right.accept(this));
    }

    @Override
    public AstNode visitPlus(CoolParser.PlusContext ctx) {
        return new AstPlus(ctx.PLUS().getSymbol(), (AstExpression) ctx.left.accept(this),
                (AstExpression) ctx.right.accept(this));
    }

    @Override
    public AstNode visitCall(CoolParser.CallContext ctx) {
        List<AstExpression> params = new ArrayList<>();

        for (CoolParser.ExprContext exprContext : ctx.params)
            params.add((AstExpression) visit(exprContext));

        return new AstCall(ctx.getStart(), new AstId(ctx.caller), params);
    }

    @Override
    public AstNode visitDivide(CoolParser.DivideContext ctx) {
        return new AstDivide(ctx.DIV().getSymbol(), (AstExpression) ctx.left.accept(this),
                (AstExpression) ctx.right.accept(this));
    }

    @Override
    public AstNode visitNegate(CoolParser.NegateContext ctx) {
        return new AstNegate(ctx.NEG().getSymbol(), (AstExpression) ctx.expr().accept(this));
    }

    @Override
    public AstNode visitNot(CoolParser.NotContext ctx) {
        return new AstNot(ctx.getStart(), (AstExpression) ctx.expr().accept(this));
    }

    @Override
    public AstNode visitLessEquals(CoolParser.LessEqualsContext ctx) {
        return new AstLessEquals(ctx.getStart(), (AstExpression) ctx.left.accept(this),
                (AstExpression) ctx.right.accept(this));
    }

    @Override
    public AstNode visitParan(CoolParser.ParanContext ctx) {
        return new AstParantheses(ctx.getStart(), (AstExpression) ctx.expr().accept(this));
    }

    @Override
    public AstNode visitBlock(CoolParser.BlockContext ctx) {
        List<AstExpression> exprs = new ArrayList<>();

        for (var exprContext : ctx.exprs)
            exprs.add((AstExpression) exprContext.accept(this));

        return new AstBlock(ctx.getStart(), exprs);
    }

    @Override
    public AstNode visitLet(CoolParser.LetContext ctx) {
        List<AstExpression> values = new ArrayList<>();

        for (CoolParser.ExprContext exprContext : ctx.values)
            values.add((AstExpression) visit(exprContext));

        return new AstLet(ctx.getStart(), ctx.decls, values,
                (AstExpression) ctx.block.accept(this));
    }

    @Override
    public AstNode visitId(CoolParser.IdContext ctx) {
        return new AstId(ctx.ID().getSymbol());
    }

    @Override
    public AstNode visitIf(CoolParser.IfContext ctx) {
        return new AstIf(ctx.getStart(), (AstExpression) ctx.cond.accept(this),
                (AstExpression) ctx.thenBranch.accept(this),
                (AstExpression) ctx.elseBranch.accept(this));
    }

    @Override
    public AstNode visitCase(CoolParser.CaseContext ctx) {
        List<AstExpression> exprs = new ArrayList<>();
        List<AstId> ids = new ArrayList<>();
        List<AstType> types = new ArrayList<>();

        for (var exprContext : ctx.exprs)
            exprs.add((AstExpression) exprContext.accept(this));
        for (var id : ctx.ids)
            ids.add(new AstId(id));
        for (var type : ctx.types)
            types.add(new AstType(type));

        return new AstCase(ctx.getStart(), (AstExpression) ctx.cond.accept(this),
                ids,
                types,
                exprs);
    }

    @Override
    public AstNode visitAssign(CoolParser.AssignContext ctx) {
        return new AstAssign(ctx.getStart(), new AstId(ctx.ID().getSymbol()),
                (AstExpression) ctx.expr().accept(this));
    }

    @Override
    public AstNode visitClass(CoolParser.ClassContext ctx) {
        List<AstFeature> features = new ArrayList<>();

        for (CoolParser.FeatureContext featureContext : ctx.content)
            features.add((AstFeature) visit(featureContext));
        return new AstClass(ctx, features,
                new AstType(ctx.className),
                new AstType(ctx.parentName));
    }

    @Override
    public AstNode visitMethod(CoolParser.MethodContext ctx) {
        List<AstFormal> formals = new ArrayList<>();

        for (var formalContext : ctx.params)
            formals.add((AstFormal) visitFormal(formalContext));

        return new AstMethod(new AstId(ctx.ID().getSymbol()),
                new AstType(ctx.TYPE().getSymbol()),
                (AstExpression) visit(ctx.expr()),
                formals);
    }

    @Override
    public AstNode visitFormal(CoolParser.FormalContext ctx) {
        return new AstFormal(ctx.ID().getSymbol(), ctx.TYPE().getSymbol());
    }

    @Override
    public AstNode visitAttribute(CoolParser.AttributeContext ctx) {
        AstExpression expr = (ctx.expr() == null ? null : (AstExpression) visit(ctx.expr()));
        return new AstAttribute(new AstId(ctx.ID().getSymbol()),
                new AstType(ctx.TYPE().getSymbol()),
                expr);
    }

    @Override
    public AstNode visitString(CoolParser.StringContext ctx) {
        return new AstString(ctx.STRING().getSymbol());
    }

    @Override
    public AstNode visitInt(CoolParser.IntContext ctx) {
        return new AstInt(ctx.INT().getSymbol());
    }

    @Override
    public AstNode visitBool(CoolParser.BoolContext ctx) {
        if (ctx.FALSE() != null)
            return new AstBool(ctx.FALSE().getSymbol());
        else if (ctx.TRUE() != null)
            return new AstBool(ctx.TRUE().getSymbol());
        else
            return null;
    }
}
