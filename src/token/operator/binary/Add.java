package token.operator.binary;

import token.Value;
import type.OperatorType;

public class Add extends BinaryOperator {

    public Add(){
        operatorType = OperatorType.ADD;
    }

    @Override
    public Value execute() {
        return null;
    }
}
