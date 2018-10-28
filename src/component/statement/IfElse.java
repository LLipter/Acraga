package component.statement;

import token.ExpressionToken;
import type.StatementType;

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
}
