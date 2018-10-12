package main;

import exception.SyntaxException;
import node.*;
import token.*;
import type.*;

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
            throwException("missing return type");

        // check function name
        String functionName = detectIdentifier();
        if (functionName == null)
            throwException("missing function identifier");
        function = new Function(functionName, returnType);


        // check left-parentheses
        if (!detectSeparator(SeparatorType.LEFTPARENTHESES))
            throwException("missing left-parentheses");

        // check parameters and right-parentheses
        while (!detectSeparator(SeparatorType.RIGHTPARENTHESES)) {
            // check data type
            ValueType dataType = detectDataType();
            if (dataType == null)
                throwException("missing parameter data type");

            // check parameter name
            String parameterName = detectIdentifier();
            if (parameterName == null)
                throwException("missing parameter identifier");
            function.addParameter(dataType, parameterName);

            // check comma
            if (detectSeparator(SeparatorType.COMMA) && detectSeparator(SeparatorType.RIGHTPARENTHESES))
                throwException("missing parameter");

        }

        // check left-brace
        if (!detectSeparator(SeparatorType.LEFTBRACE))
            throwException("missing left-brace");


        // check statements
        while(!detectSeparator(SeparatorType.RIGHTBRACE)){
            // TODO:
        }

        if(scanner.iseof())
            throwException("missing right-brace");



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

    // check whether current token is certain operator
    private boolean detectOperator(OperatorType type) {
        Token token = getToken();
        if (token == null)
            return false;
        if (token.getTokenType() != TokenType.OPERATOR)
            return false;
        Operator operator = (Operator) token;
        if (operator.getOperatorType() != type)
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

    private Statement detectStatement(){
        // TODO:
        return null;
    }

    private Expression detectExpression() throws SyntaxException{
        // TODO:


        return null;
    }


    private Initialization detectInitialization() throws SyntaxException{
        ValueType dataType = detectDataType();
        if(dataType == null)
            return null;
        String id = detectIdentifier();
        if(id == null)
            throwException("missing identifier");
        Value value = new Value(dataType);
        if(detectSeparator(SeparatorType.SEMICOLON))
            value.setDefaultValue();
        else if(detectOperator(OperatorType.ASSIGN)){
            // TODO: detect expression
        }else
            throwException("missing semicolon");


        return new Initialization(id, value);
    }

}
