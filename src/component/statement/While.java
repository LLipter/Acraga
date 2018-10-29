package component.statement;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.Value;
import type.Casting;
import type.StatementType;
import type.ValueType;

public class While extends Loop {

    public While() {
        statementType = StatementType.WHILE;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        context.createFrame();
        Value cond = condition.execute(context);
        Value castedValue = Casting.casting(cond, ValueType.BOOLEAN);
        if (castedValue == null)
            throw new RTException(condition.getLines(), condition.getPos(), "condition not compatible with boolean type");
        while (castedValue.getBoolValue()) {
            for (Statement s : loopStatements)
                s.execute(context);
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
        printWithIndent(indent, "[While Statement]");
        printWithIndent(indent,"[Condition Statement]");
        condition.print(indent+4);
        printWithIndent(indent,"[End of Condition Statement]");
        printWithIndent(indent,"[Loop Statement]");
        for (Statement s : loopStatements)
            s.print(indent+4);
        printWithIndent(indent,"[End of Loop Statement]");
        printWithIndent(indent,"[End of While Statement]");
    }
}
