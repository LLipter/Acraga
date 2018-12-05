package token;

import type.TokenType;

public abstract class Token {

    protected TokenType tokenType;
    private int lines;
    private int pos;

    public Token(){
        super();
        tokenType = null;
        lines = -1;
        pos = -1;
    }

    public Token(Token otherToken){
        super();
        this.tokenType = otherToken.tokenType;
        this.lines = otherToken.lines;
        this.pos = otherToken.pos;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public TokenType getTokenType() {
        return tokenType;
    }
}
