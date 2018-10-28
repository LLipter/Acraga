package component.statement;

import token.ExpressionToken;
import type.StatementType;

import java.util.LinkedList;

public class For extends Loop {

    private ExpressionToken init;
    private ExpressionToken incr;

    public For(){
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
}
