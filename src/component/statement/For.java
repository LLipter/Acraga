package component.statement;

import component.context.DataStack;
import component.signal.BreakRequest;
import component.signal.ContinueRequest;
import component.signal.ControlSignal;
import exception.RTException;
import token.exprtoken.ExpressionToken;
import token.exprtoken.Value;
import type.Casting;
import type.StatementType;
import type.ValueType;

public class For extends Loop {

    private ExpressionToken init;
    private Initialization definition;
    private ExpressionToken incr;

    public For() {
        statementType = StatementType.FOR;
    }

    public ExpressionToken getInit() {
        return init;
    }

    public void setInit(ExpressionToken init) {
        this.init = init;
    }

    public Initialization getInitialization() {
        return definition;
    }

    public void setInitialization(Initialization definition) {
        this.definition = definition;
    }

    public ExpressionToken getIncr() {
        return incr;
    }

    public void setIncr(ExpressionToken incr) {
        this.incr = incr;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ControlSignal {
        try {
            context.createFrame();
            if (definition != null)
                definition.execute(context);
            else if (init != null)
                init.execute(context);

            Value cond;
            Value castedValue;
            if (condition != null) {
                cond = condition.execute(context);
                castedValue = Casting.casting(cond, ValueType.BOOLEAN);
                if (castedValue == null)
                    throw new RTException(condition.getLines(), condition.getPos(), "condition not compatible with boolean type");
            } else {
                castedValue = new Value(ValueType.BOOLEAN);
                castedValue.setBoolValue(true);
            }

            while (castedValue.getBoolValue()) {
                try {
                    context.createFrame();
                    for (Statement s : loopStatements)
                        s.execute(context);
                    if (incr != null)
                        incr.execute(context);
                    if (condition != null) {
                        cond = condition.execute(context);
                        castedValue = Casting.casting(cond, ValueType.BOOLEAN);
                        if (castedValue == null)
                            throw new RTException(condition.getLines(), condition.getPos(), "condition not compatible with boolean type");
                    }
                } catch (BreakRequest br) {
                    break;
                } catch (ContinueRequest cr) {
                    if (incr != null)
                        incr.execute(context);
                    if (condition != null) {
                        cond = condition.execute(context);
                        castedValue = Casting.casting(cond, ValueType.BOOLEAN);
                        if (castedValue == null)
                            throw new RTException(condition.getLines(), condition.getPos(), "condition not compatible with boolean type");
                    }
                } finally {
                    context.releaseFrame();
                }
            }
        } finally {
            context.releaseFrame();
        }
        // always return void
        return new Value(ValueType.VOID);
    }

    @Override
    public void print(StringBuilder sb, int indent) {
        printWithIndent(sb, indent, "[For Statement]");
        printWithIndent(sb, indent, "[Initialization Statement]");
        if (definition != null)
            definition.print(sb, indent + 4);
        else if (init != null)
            init.print(sb, indent + 4);
        printWithIndent(sb, indent, "[End of Initialization Statement]");
        printWithIndent(sb, indent, "[Condition Statement]");
        if (condition != null)
            condition.print(sb, indent + 4);
        printWithIndent(sb, indent, "[End of Condition Statement]");
        printWithIndent(sb, indent, "[Increment Statement]");
        if (incr != null)
            incr.print(sb, indent + 4);
        printWithIndent(sb, indent, "[End of Increment Statement]");
        printWithIndent(sb, indent, "[Loop Statement]");
        for (Statement s : loopStatements)
            s.print(sb, indent + 4);
        printWithIndent(sb, indent, "[End of Loop Statement]");
        printWithIndent(sb, indent, "[End of For Statement]");
    }
}
