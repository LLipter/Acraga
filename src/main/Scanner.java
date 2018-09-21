package main;

import java.util.LinkedList;

import exception.AcragaException;
import token.BinaryOperator;
import token.Identifier;
import token.Keyword;
import token.Operator;
import token.Token;
import token.UnaryOperator;
import token.Value;
import token.Separator;
import type.KeywordType;
import type.OperatorType;
import type.SeparatorType;

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
			if(input.iseof())
				break;

			

			
			// detect separator
			Separator separator = detectSeparator();
			if(separator != null) {
				tokens.addLast(separator);
				continue;
			}
			
			// detect values
			Value value = detectValue();
			if(value != null) {
				tokens.addLast(value);
				continue;
			}
			
			// detect keywords
			Keyword keyword = detectKeyword();
			if(keyword != null) {
				tokens.addLast(keyword);
				continue;
			}
			
			
			// detect identifier
			Identifier identifier = detectIdentifier();
			if(identifier != null) {
				tokens.addLast(identifier);
				continue;
			}
			
			// detect operators
			Operator op = detectOperator();
			if(op != null) {
				tokens.addLast(op);
				continue;
			}
			

			error.syntax("invalid token");
			
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
	
	// detect separator
	public Separator detectSeparator() {
		Separator separater;
		if(input.getCh() == '(') 
			separater = new Separator(SeparatorType.LEFTPARENTHESES);
		else if(input.getCh() == ')')
			separater = new Separator(SeparatorType.RIGHTPARENTHESES);
		else if(input.getCh() == '[')
			separater = new Separator(SeparatorType.LEFTBRACKET);
		else if(input.getCh() == ']')
			separater = new Separator(SeparatorType.RIGHTBRACKET);
		else if(input.getCh() == '{')
			separater = new Separator(SeparatorType.LEFTBRACE);
		else if(input.getCh() == '}')
			separater = new Separator(SeparatorType.RIGHTBRACE);
		else if(input.getCh() == ';')
			separater = new Separator(SeparatorType.SEMICOLON);
		else
			return null;
		separater.setLines(input.getLine());
		separater.setPos(input.getPos());
		input.next();
		
		return separater;
	}
	
	
	public Value detectValue() {
		Value value;
		int lines = input.getLine();
		int pos = input.getPos();
		
		if((value = input.isDouble()) == null)
			if((value = input.isInteger()) == null) 
				if((value = input.isBool()) == null)
					if((value = input.isString()) == null)
						return null;
		
		value.setLines(lines);
		value.setPos(pos);
		
		return value;
			
	}
	
	public Identifier detectIdentifier() {
		int lines = input.getLine();
		int pos = input.getPos();
		Identifier identifier = input.isIdentifier();
		if(identifier == null)
			return null;
		identifier.setLines(lines);
		identifier.setPos(pos);
		return identifier;
	}
	
	public void print() {
		for(Token token : tokens)
			System.out.println(token);
	}
}
