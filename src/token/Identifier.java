package token;

import type.TokenType;

public class Identifier extends Token {

    private String id;

    public Identifier() {
        tokenType = TokenType.IDENTIFIER;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("<Identifier,%s>", id);
    }


}
