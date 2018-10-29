package component.statement;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.ExpressionToken;
import token.exprtoken.Value;
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
    public Value execute(DataStack context) throws RTException, ReturnValue {
        return root.execute(context);
    }
}
