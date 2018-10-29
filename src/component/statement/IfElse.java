package component.statement;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.ExpressionToken;
import token.exprtoken.Value;
import type.Casting;
import type.StatementType;
import type.ValueType;

import java.util.LinkedList;

public class IfElse extends Statement {

    ExpressionToken condition;
    LinkedList<Statement> ifBranch;
    LinkedList<Statement> elseBranch;

    public IfElse(){
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
    public Value execute(DataStack context) throws RTException, ReturnValue {

        Value cond = condition.execute(context);
        Value castedValue = Casting.casting(cond, ValueType.BOOLEAN);
        if(castedValue == null)
            throw new RTException(condition.getLines(), condition.getPos(), "condition not compatible with boolean type");
        context.createFrame();
        if(castedValue.getBoolValue()){
            for (Statement s : ifBranch)
                s.execute(context);
        }else{
            for (Statement s : elseBranch)
                s.execute(context);
        }
        context.releaseFrame();
        // always return void
        return new Value(ValueType.VOID);
    }
}
