package main;

import exception.SyntaxException;
import node.Function;
import node.FunctionSignature;
import token.Identifier;
import token.Keyword;
import token.Separator;
import token.Token;
import type.Casting;
import type.SeparatorType;
import type.TokenType;
import type.ValueType;

import java.util.HashMap;

public class Parser {

    private Scanner scanner;
    private HashMap<FunctionSignature, Function> functionMap;
    private int line;
    private int pos;

    public Parser(Scanner scanner) throws SyntaxException {
        this.scanner = scanner;
        functionMap = new HashMap<FunctionSignature, Function>();
        updateLinePos();
        parse();
    }

    private void parse() throws SyntaxException {
        while (!scanner.iseof()) {
            Function function = detectFunction();
            functionMap.put(function.getFunctionSignature(), function);
        }
    }

    private Function detectFunction() throws SyntaxException {
        Function function;

        // check return type
        ValueType returnType = detectDataType();
        if (returnType == null)
            throwException("missing return type in function declaration");

        // check function name
        String functionName = detectIdentifier();
        if (functionName == null)
            throwException("missing function identifier in function declaration");
        function = new Function(functionName, returnType);


        // check left-parentheses
        if (!detectSeparator(SeparatorType.LEFTPARENTHESES))
            throwException("missing left-parentheses in function declaration");

        // check parameters and right-parentheses
        while (!detectSeparator(SeparatorType.RIGHTPARENTHESES)) {
            // check data type
            ValueType dataType = detectDataType();
            if (dataType == null)
                throwException("missing parameter data type in function declaration");

            // check parameter name
            String parameterName = detectIdentifier();
            if (parameterName == null)
                throwException("missing parameter identifier in function declaration");
            function.addParameter(dataType, parameterName);

            // check comma
            if (detectSeparator(SeparatorType.COMMA) && detectSeparator(SeparatorType.RIGHTPARENTHESES))
                throwException("missing parameter in function declaration");

        }

        // check left-brace
        if (!detectSeparator(SeparatorType.LEFTBRACE))
            throwException("missing left-brace in function declaration");


        // TODO: detect statements


        // check right-brace
        if (!detectSeparator(SeparatorType.RIGHTBRACE))
            throwException("missing right-brace in function declaration");


        return function;


    }

    private void throwException(String msg) throws SyntaxException {
        throw new SyntaxException(line, pos, msg);
    }

    private Token getToken() {
        return scanner.getToken();
    }

    private void next() {
        scanner.next();
        updateLinePos();
    }

    private void updateLinePos() {
        Token token = getToken();
        if (token == null)
            line = pos = -1;
        else {
            line = token.getLines();
            pos = token.getPos();
        }
    }

    // check whether current token is certain separator
    private boolean detectSeparator(SeparatorType type) {
        Token token = getToken();
        if (token == null)
            return false;
        if (token.getTokenType() != TokenType.SEPARATOR)
            return false;
        Separator separator = (Separator) token;
        if (separator.getSeparatorType() != type)
            return false;
        next();
        return true;
    }

    // check whether current token indicates a data type
    private ValueType detectDataType() {
        Token token = getToken();
        if (token == null)
            return null;
        if (token.getTokenType() != TokenType.KEYWORD)
            return null;
        Keyword keyword = (Keyword) token;
        ValueType result = Casting.keywordType2ValueType(keyword.getKeywordType());
        if (result == null)
            return null;
        next();
        return result;
    }

    // check whether current token is a indicator
    private String detectIdentifier() {
        Token token = getToken();
        if (token == null)
            return null;
        if (token.getTokenType() != TokenType.IDENTIFIER)
            return null;
        String id = ((Identifier) token).getId();
        next();
        return id;
    }

    public HashMap<FunctionSignature, Function> getFunctionMap() {
        return functionMap;
    }

}
