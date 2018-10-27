package node;

import token.ExpressionToken;
import type.StatementType;

import java.util.LinkedList;

public class While extends Statement {

    ExpressionToken condition;
    LinkedList<Statement> loopStatements;

    public ExpressionToken getCondition() {
        return condition;
    }

    public void setCondition(ExpressionToken condition) {
        this.condition = condition;
    }

    public LinkedList<Statement> getLoopStatements() {
        return loopStatements;
    }

    public void setLoopStatements(LinkedList<Statement> loopStatements) {
        this.loopStatements = loopStatements;
    }

    public While(){
        statementType = StatementType.WHILE;

    }
}
