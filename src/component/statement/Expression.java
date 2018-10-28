package component.statement;

import token.ExpressionToken;
import type.StatementType;

public class Expression extends Statement {
    private ExpressionToken root;

    public Expression(){
        statementType = StatementType.EXPRESSION;
    }

    public ExpressionToken getRoot() {
        return root;
    }

    public void setRoot(ExpressionToken root) {
        this.root = root;
    }
}
