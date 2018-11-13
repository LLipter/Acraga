package component.statement;

import component.context.DataStack;
import component.signal.ControlSignal;
import exception.RTException;
import token.exprtoken.ExpressionToken;
import token.exprtoken.Value;
import type.StatementType;

public class Expression extends Statement {

    private ExpressionToken root;

    public Expression() {
        statementType = StatementType.EXPRESSION;
    }

    public ExpressionToken getRoot() {
        return root;
    }

    public void setRoot(ExpressionToken root) {
        this.root = root;
    }

    @Override
    public Value execute(DataStack context) throws RTException, ControlSignal {
        return root.execute(context);
    }

    @Override
    public void print(int indent) {
        printWithIndent(indent, "[Expression Statement]");
        root.print(indent + 4);
        printWithIndent(indent, "[End of Expression Statement]");
    }
}
