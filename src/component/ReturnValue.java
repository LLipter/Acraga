package component;

import token.exprtoken.Value;

public class ReturnValue extends Throwable {
    private Value returnValue;

    public ReturnValue(Value value){
        returnValue = value;
    }

    public Value getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Value returnValue) {
        this.returnValue = returnValue;
    }
}
