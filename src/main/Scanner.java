package main;

import java.util.LinkedList;

import exception.AcragaException;
import token.BinaryOperator;
import token.Keyword;
import token.Operator;
import token.Token;
import token.UnaryOperator;
import type.KeywordType;
import type.OperatorType;

public class Scanner {

	private InputReader input;
	private AcragaException error;
	private LinkedList<Token> tokens;
	
	public Scanner(InputReader inputReader) {
		input = inputReader;
		error = new AcragaException(inputReader);
		tokens = new LinkedList<Token>();
		
		while(!input.iseof()) {
			input.nextNotWhiteSpace();
			
			// detect operators
			Operator op = detectOperator();
			if(op != null) {
				tokens.addLast(op);
				continue;
			}

			
			// detect keywords
			
			
		}
	}
	
	// detect operators
	public Operator detectOperator() {
		Operator op;
		if(input.getCh() == '+') 
			op = new BinaryOperator(OperatorType.Add);
		else if(input.getCh() == '-')
			op = new BinaryOperator(OperatorType.Subtract);
		else if(input.getCh() == '*')
			op = new BinaryOperator(OperatorType.Multiply);
		else if(input.getCh() == '/')
			op = new BinaryOperator(OperatorType.Divide);
		else if(input.getCh() == '~')
			op = new UnaryOperator(OperatorType.BitwiseNegate);
		else
			return null;
		op.setLines(input.getLine());
		op.setPos(input.getPos());
		input.next();
		
		return op;
	}
	
	// detect keyword
	public Keyword detectKeyword() {
		Keyword keyword;
		int lines = input.getLine();
		int pos = input.getPos();
		if(input.isKeyword("if"))
			keyword = new Keyword(KeywordType.If);
		else if(input.isKeyword("else"))
			keyword = new Keyword(KeywordType.Else);
		else if(input.isKeyword("while"))
			keyword = new Keyword(KeywordType.While);
		else
			return null;
		keyword.setLines(lines);
		keyword.setPos(pos);
		
		return keyword;
	}
}
