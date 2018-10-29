package component.statement;

import token.exprtoken.ExpressionToken;

import java.util.LinkedList;

public abstract class Loop extends Statement {

    protected ExpressionToken condition;
    protected LinkedList<Statement> loopStatements;

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
}
