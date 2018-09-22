package main;

import exception.SyntaxException;
import node.Function;
import node.FunctionSignature;
import token.Identifier;
import token.Keyword;
import token.Separator;
import token.Token;
import token.Value;
import type.Casting;
import type.KeywordType;
import type.SeparatorType;
import type.TokenType;
import type.ValueType;

import java.util.HashMap;
import java.util.Stack;

public class Interpreter {
	
	private Scanner scanner;
	private HashMap<FunctionSignature, Function> functionMap;
	
	public Interpreter(InputReader inputReader) throws SyntaxException {
		scanner = new Scanner(inputReader);
		functionMap = new HashMap<FunctionSignature, Function>();
		interpret();
	}
	
	private void interpret() throws SyntaxException {
		while(!scanner.iseof()) {
			Function function = detectFunction();
			functionMap.put(function.getFunctionSignature(), function);
		}
		
		if(!functionMap.containsKey(FunctionSignature.mainFunctionSignature))
			throw new SyntaxException("main function not found");
		Function main = functionMap.get(FunctionSignature.mainFunctionSignature);
		Value exitCode = main.run();
		String format = "Program ends with '%";
		if(exitCode.isInt())
			System.out.println(String.format(format + "d'", exitCode.getIntValue()));
		else if(exitCode.isDouble())
			System.out.println(String.format(format + "f'", exitCode.getDoubleValue()));
		else if(exitCode.isBool())
			System.out.println(String.format(format + "b'", exitCode.getBoolValue()));
		else if(exitCode.isString())
			System.out.println(String.format(format + "s'", exitCode.getStringValue()));
		else
			System.out.println("unknown exit code type");
	}
	
	private Function detectFunction() throws SyntaxException {
		Function function;
		
		// check return type
		Token retToken = scanner.getToken();
		int lines = retToken.getLines();
		int pos = retToken.getPos();
		if(retToken.getTokenType() != TokenType.KEYWORD)
			throw new SyntaxException(lines, pos, "function decleration - missing return type");
		Keyword keyword = (Keyword) retToken;
		ValueType returnType = Casting.keywordType2ValueType(keyword.getKeywordType());
		if(returnType == null)
			throw new SyntaxException(lines, pos, "function decleration - missing return type");
		scanner.next();
		
		// check function name
		Token functionNameToken = scanner.getToken();
		if(functionNameToken == null)
			throw new SyntaxException(lines, pos, "function decleration - missing function name");
		lines = functionNameToken.getLines();
		pos = functionNameToken.getPos();
		if(functionNameToken.getTokenType() != TokenType.IDENTIFIER)
			throw new SyntaxException(lines, pos, "missing function name");
		String functionName = ((Identifier)functionNameToken).getId();
		function = new Function(functionName, returnType);
		scanner.next();
		
		// check left-parentheses
		Token leftParentheses = scanner.getToken();
		if(leftParentheses == null)
			throw new SyntaxException(lines, pos, "function decleration - missing left-parentheses");
		lines = leftParentheses.getLines();
		pos = leftParentheses.getPos();
		if(leftParentheses.getTokenType() != TokenType.SEPARATOR)
			throw new SyntaxException(lines, pos, "function decleration - missing left-parentheses");
		Separator separator = (Separator)leftParentheses;
		if(separator.getSeparatorType() != SeparatorType.LEFTPARENTHESES)
			throw new SyntaxException(lines, pos, "function decleration - missing left-parentheses");
		scanner.next();
		
		// check parameters
		Token dataTypeToken = scanner.getToken();
		if(dataTypeToken == null)
			throw new SyntaxException(lines, pos, "function decleration - missing right-parentheses");
		lines = dataTypeToken.getLines();
		pos = dataTypeToken.getPos();
		while(dataTypeToken.getTokenType() != TokenType.SEPARATOR) {
			// check data type
			if(dataTypeToken.getTokenType() != TokenType.KEYWORD)
				throw new SyntaxException(lines, pos, "function decleration - missing parameter data type");
			lines = dataTypeToken.getLines();
			pos = dataTypeToken.getPos();
			Keyword key = (Keyword) dataTypeToken;
			ValueType dataType = Casting.keywordType2ValueType(key.getKeywordType());
			if(dataType == null)
				throw new SyntaxException(lines, pos, "function decleration - missing parameter data type");
			scanner.next();
			
			// check parameter name
			Token idToken = scanner.getToken();
			if(idToken == null)
				throw new SyntaxException(lines, pos, "function decleration - missing parameter name");
			lines = idToken.getLines();
			pos = idToken.getPos();
			if(idToken.getTokenType() != TokenType.IDENTIFIER)
				throw new SyntaxException(lines, pos, "function decleration - missing parameter name");
			String parameterName = ((Identifier)idToken).getId();
			function.addParameter(dataType, parameterName);
			scanner.next();
			
			// check comma
			Token commaToken = scanner.getToken();
			if(commaToken == null)
				throw new SyntaxException(lines, pos, "function decleration - missing right-parentheses");
			lines = commaToken.getLines();
			pos = commaToken.getPos();
			if(commaToken.getTokenType() != TokenType.SEPARATOR)
				throw new SyntaxException(lines, pos, "function decleration - missing comma");
			Separator comma = (Separator) commaToken;
			if(comma.getSeparatorType() == SeparatorType.RIGHTPARENTHESES) {
				dataTypeToken = commaToken;
				continue;
			}
			if(comma.getSeparatorType() != SeparatorType.COMMA)
				throw new SyntaxException(lines, pos, "function decleration - invalid separator");
			scanner.next();
			
			dataTypeToken = scanner.getToken();
		}
		
		// check right-parentheses
		Token rightParentheses = scanner.getToken();
		if(rightParentheses == null)
			throw new SyntaxException(lines, pos, "function decleration - missing right-parentheses");
		lines = rightParentheses.getLines();
		pos = rightParentheses.getPos();
		if(rightParentheses.getTokenType() != TokenType.SEPARATOR)
			throw new SyntaxException(lines, pos, "function decleration - missing right-parentheses");
		separator = (Separator)rightParentheses;
		if(separator.getSeparatorType() != SeparatorType.RIGHTPARENTHESES)
			throw new SyntaxException(lines, pos, "function decleration - missing right-parentheses");
		scanner.next();
		
		
		// TODO: detect statements 
		
		
			
		return function;
			
			
	}
}
