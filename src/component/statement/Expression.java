package component.statement;

import token.ExpressionToken;
import token.Value;
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

    @Override
    public Value execute() {
        return null;
    }
}
