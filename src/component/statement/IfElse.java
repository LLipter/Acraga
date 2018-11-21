package component.statement;

import component.context.DataStack;
import component.signal.ControlSignal;
import exception.RTException;
import token.exprtoken.ExpressionToken;
import token.exprtoken.Value;
import type.Casting;
import type.StatementType;
import type.ValueType;

import java.util.LinkedList;

public class IfElse extends Statement {

    ExpressionToken condition;
    LinkedList<Statement> ifBranch = null;
    LinkedList<Statement> elseBranch = null;

    public IfElse() {
        statementType = StatementType.IFELSE;
    }

    public ExpressionToken getCondition() {
        return condition;
    }

    public void setCondition(ExpressionToken condition) {
        this.condition = condition;
    }

    public LinkedList<Statement> getIfBranch() {
        return ifBranch;
    }

    public void setIfBranch(LinkedList<Statement> ifBranch) {
        this.ifBranch = ifBranch;
    }

    public LinkedList<Statement> getElseBranch() {
        return elseBranch;
    }

    public void setElseBranch(LinkedList<Statement> elseBranch) {
        this.elseBranch = elseBranch;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ControlSignal {

        Value cond = condition.execute(context);
        Value castedValue = Casting.casting(cond, ValueType.BOOLEAN);
        if (castedValue == null)
            throw new RTException(condition.getLines(), condition.getPos(), "condition not compatible with boolean type");
        try {
            context.createFrame();
            if (castedValue.getBoolValue()) {
                for (Statement s : ifBranch)
                    s.execute(context);
            } else if (elseBranch != null) {
                for (Statement s : elseBranch)
                    s.execute(context);
            }
        } finally {
            context.releaseFrame();
        }
        // always return void
        return new Value(ValueType.VOID);
    }

    @Override
    public void print(StringBuilder sb, int indent) {
        printWithIndent(sb, indent, "[If Statement]");
        printWithIndent(sb, indent, "[Condition Statement]");
        condition.print(sb, indent + 4);
        printWithIndent(sb, indent, "[End of Condition Statement]");

        if (ifBranch != null) {
            printWithIndent(sb, indent, "[If Branch Statement]");
            for (Statement s : ifBranch)
                s.print(sb, indent + 4);
            printWithIndent(sb, indent, "[End of If Branch Statement]");
        }
        if (elseBranch != null) {
            printWithIndent(sb, indent, "[Else Branch Statement]");
            for (Statement s : elseBranch)
                s.print(sb, indent + 4);
            printWithIndent(sb, indent, "[End of Else Branch Statement]");
        }
        printWithIndent(sb, indent, "[End of If Statement]");
    }
}
