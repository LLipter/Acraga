package token;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import type.TokenType;
import type.ValueType;

public class Identifier extends ExpressionToken {

    protected String id;
    protected ValueType dataType;

    public ValueType getDataType() {
        return dataType;
    }

    public void setDataType(ValueType dataType) {
        this.dataType = dataType;
    }

    public Identifier() {
        tokenType = TokenType.IDENTIFIER;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    @Override
    public String toString() {
        return String.format("<Identifier,%s>", id);
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        return context.getValue(this);
    }
}
