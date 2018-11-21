package component.statement;

import component.context.DataStack;
import component.signal.BreakRequest;
import component.signal.ContinueRequest;
import component.signal.ControlSignal;
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
    public Value execute(DataStack context) throws RTException, ControlSignal {
        try {
            context.createFrame();
            Value cond = condition.execute(context);
            Value castedValue = Casting.casting(cond, ValueType.BOOLEAN);
            if (castedValue == null)
                throw new RTException(condition.getLines(), condition.getPos(), "condition not compatible with boolean type");
            while (castedValue.getBoolValue()) {
                try {
                    for (Statement s : loopStatements)
                        s.execute(context);
                    cond = condition.execute(context);
                    castedValue = Casting.casting(cond, ValueType.BOOLEAN);
                    if (castedValue == null)
                        throw new RTException(condition.getLines(), condition.getPos(), "condition not compatible with boolean type");
                } catch (BreakRequest br) {
                    break;
                } catch (ContinueRequest cr) {
                    cond = condition.execute(context);
                    castedValue = Casting.casting(cond, ValueType.BOOLEAN);
                    if (castedValue == null)
                        throw new RTException(condition.getLines(), condition.getPos(), "condition not compatible with boolean type");
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
        printWithIndent(sb, indent, "[While Statement]");
        printWithIndent(sb, indent, "[Condition Statement]");
        condition.print(sb, indent + 4);
        printWithIndent(sb, indent, "[End of Condition Statement]");
        printWithIndent(sb, indent, "[Loop Statement]");
        for (Statement s : loopStatements)
            s.print(sb, indent + 4);
        printWithIndent(sb, indent, "[End of Loop Statement]");
        printWithIndent(sb, indent, "[End of While Statement]");
    }
}
