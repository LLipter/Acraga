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
    public void setInitialization(Initialization definition){
        this.definition=definition;
    }

    public ExpressionToken getIncr() {
        return incr;
    }

    public void setIncr(ExpressionToken incr) {
        this.incr = incr;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
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
                }
                finally {
                    context.releaseFrame();
                }
            }
        }
        finally {
            context.releaseFrame();
        }
        // always return void
        return new Value(ValueType.VOID);
    }

    @Override
    public void print(int indent) {
        printWithIndent(indent, "[For Statement]");
        printWithIndent(indent, "[Initialization Statement]");
        if(definition != null)
            definition.print(indent + 4);
        else
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
