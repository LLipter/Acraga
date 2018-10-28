package token.operator.binary;

import component.context.DataStack;
import token.Value;
import type.OperatorType;

public class Add extends BinaryOperator {

    public Add(){
        operatorType = OperatorType.ADD;
    }

    @Override
    public Value execute(DataStack context) {
        Value lvalue = lChild.execute(context);
        Value rvalue
    }
}
