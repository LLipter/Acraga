package token;

import component.context.DataStack;
import exception.Runtime;
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
    public Value execute(DataStack context) throws Runtime {
        return context.getValue(this);
    }
}
