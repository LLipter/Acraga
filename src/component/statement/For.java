package component.statement;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.ExpressionToken;
import token.exprtoken.Value;
import type.Casting;
import type.StatementType;
import type.ValueType;

public class For extends Loop {

    private ExpressionToken init;
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

    public ExpressionToken getIncr() {
        return incr;
    }

    public void setIncr(ExpressionToken incr) {
        this.incr = incr;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        context.createFrame();
        init.execute(context);
        Value cond = condition.execute(context);
        Value castedValue = Casting.casting(cond, ValueType.BOOLEAN);
        if (castedValue == null)
            throw new RTException(condition.getLines(), condition.getPos(), "condition not compatible with boolean type");
        while (castedValue.getBoolValue()) {
            for (Statement s : loopStatements)
                s.execute(context);
            incr.execute(context);
            cond = condition.execute(context);
            castedValue = Casting.casting(cond, ValueType.BOOLEAN);
            if (castedValue == null)
                throw new RTException(condition.getLines(), condition.getPos(), "condition not compatible with boolean type");
        }
        context.releaseFrame();
        // always return void
        return new Value(ValueType.VOID);
    }

    @Override
    public void print(int indent) {
        printWithIndent(indent, "[For Statement]");
        printWithIndent(indent, "[Initialization Statement]");
        init.print(indent + 4);
        printWithIndent(indent, "[End of Initialization Statement]");
        printWithIndent(indent, "[Condition Statement]");
        condition.print(indent + 4);
        printWithIndent(indent, "[End of Condition Statement]");
        printWithIndent(indent, "[Increment Statement]");
        incr.print(indent + 4);
        printWithIndent(indent, "[End of Increment Statement]");
        printWithIndent(indent, "[Loop Statement]");
        for (Statement s : loopStatements)
            s.print(indent + 4);
        printWithIndent(indent, "[End of Loop Statement]");
        printWithIndent(indent, "[End of For Statement]");
    }
}
