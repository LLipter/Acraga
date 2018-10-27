package token;

import type.OperatorType;
import type.TokenType;
import type.SeparatorType;

public class Separator extends Operator {
    protected SeparatorType separatorType;


    public Separator(SeparatorType sType) {
        super();
        separatorType = sType;
        operatorType = OperatorType.SEPARATOR;
    }

    @Override
    public String toString() {
        return String.format("<Separator,%s>", separatorType);
    }

    public SeparatorType getSeparatorType() {
        return separatorType;
    }


}
