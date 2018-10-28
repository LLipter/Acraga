package token;

import component.context.DataStack;
import exception.RTException;
import type.TokenType;

public class Identifier extends ExpressionToken {

    protected String id;

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
    public Value execute(DataStack context) throws RTException {
        return context.getValue(this);
    }
}
