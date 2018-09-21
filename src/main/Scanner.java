package main;

import java.util.LinkedList;

import exception.AcragaException;
import token.BinaryOperator;
import token.Token;
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
			if(input.getCh() == '+') {
				BinaryOperator op = new BinaryOperator(OperatorType.Add);
				op.setLines(input.getLine());
				op.setPos(input.getPos());
				input.next();
			}else if(input.getCh() == '-') {
				BinaryOperator op = new BinaryOperator(OperatorType.Subtract);
				op.setLines(input.getLine());
				op.setPos(input.getPos());
				input.next();
			}else if(input.getCh() == '*') {
				BinaryOperator op = new BinaryOperator(OperatorType.Multiply);
				op.setLines(input.getLine());
				op.setPos(input.getPos());
				input.next();
			}else if(input.getCh() == '/') {
				BinaryOperator op = new BinaryOperator(OperatorType.Divide);
				op.setLines(input.getLine());
				op.setPos(input.getPos());
				input.next();
			}else if(input.getCh() == '~') {
				BinaryOperator op = new BinaryOperator(OperatorType.BitwiseNegate);
				op.setLines(input.getLine());
				op.setPos(input.getPos());
				input.next();
			}
			
			// detect keywords
			
			
		}
	}
}
