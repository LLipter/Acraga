package token;

import type.TokenType;

public abstract class Token {

	protected TokenType tokenType;
	private int lines;
	private int pos;
	
	public abstract ReturnValue run();
	
	
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

}
