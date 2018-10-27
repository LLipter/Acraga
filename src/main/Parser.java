package main;

import exception.SyntaxException;
import node.*;
import token.*;
import type.*;

import java.util.Stack;
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
        //parse();

        //printExpressionTree function detectExpression()
        Initialization test = detectInitialization();
        System.out.println();
        System.out.println("Tree:");
        printExpressionTree(test.getArrayLength());
        for(ExpressionToken ext : test){
            printExpressionTree(ext);
        }
    }

    private void parse() throws SyntaxException {
        while (!scanner.iseof()) {
            Function function = detectFunction();
            functionMap.put(function.getFunctionSignature(), function);
        }
    }

    private void throwException(String msg) throws SyntaxException {
        throw new SyntaxException(line, pos, msg);
    }

    private Token getToken() {
        return scanner.getToken();
    }

    private Token getNextToken() {
        return scanner.getNextToken();
    }

    public HashMap<FunctionSignature, Function> getFunctionMap() {
        return functionMap;
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

    // check whether a given token is operator
    private boolean isOperator(Token token) {
        if (token == null)
            return false;
        return token.getTokenType() == TokenType.OPERATOR;
    }

    // check whether current token is operator
    private boolean isOperator(){
        return isOperator(getToken());
    }

    // check whether a given token is certain operator
    private boolean isOperator(Token token, OperatorType type){
        if(!isOperator(token))
            return false;
        return ((Operator)token).getOperatorType() == type;
    }

    // check whether current token is certain operator
    private boolean isOperator(OperatorType type) {
        return isOperator(getToken(), type);
    }

    // check whether current token is certain operator. if so, remove it
    private boolean detectOperator(OperatorType type) {
        if(!isOperator(type))
            return false;
        next();
        return true;
    }

    // check whether a given token is separator
    private boolean isSeparator(Token token) {
        if (token == null)
            return false;
        return token.getTokenType() == TokenType.SEPARATOR;
    }

    // check whether current token is separator
    private boolean isSeparator(){
        return isSeparator(getToken());
    }

    // check whether a given token is certain separator
    private boolean isSeparator(Token token, SeparatorType type){
        if(!isSeparator(token))
            return false;
        return ((Separator)token).getSeparatorType() == type;
    }

    // check whether current token is certain separator
    private boolean isSeparator(SeparatorType type) {
        return isSeparator(getToken(), type);
    }

    // check whether current token is certain separator. if so, remove it
    private boolean detectSeparator(SeparatorType type) {
        if(!isSeparator(type))
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

    // check whether current token is a identifier
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

    // check whether current token belongs to expression
    private boolean isExpressionToken() {
        Token token = getToken();
        if (token == null)
            return false;
        if (token instanceof ExpressionToken)
            return true;
        if (token instanceof Separator){
            Separator separator = (Separator)token;
            return separator.getSeparatorType() == SeparatorType.LEFTPARENTHESES
                    || separator.getSeparatorType() == SeparatorType.RIGHTPARENTHESES
                    || separator.getSeparatorType() == SeparatorType.LEFTBRACKET
                    || separator.getSeparatorType() == SeparatorType.RIGHTBRACKET;
        }
        return false;
    }

    // check whether current token is a identifier, and next token is a certain separator
    private boolean isIdSeparator(SeparatorType type){
        Token token = getToken();
        if (token == null)
            return false;
        if (token.getTokenType() != TokenType.IDENTIFIER)
            return false;
        token = getNextToken();
        if (token == null)
            return false;
        if (token.getTokenType() != TokenType.SEPARATOR)
            return false;
        Separator operator = (Separator) token;
        if(operator.getSeparatorType() != type)
            return false;
        return true;
    }

    // stack operation (condition is checked in detectExpression)
    private void StackOperation(Stack<ExpressionToken> operandSt, Operator op) throws SyntaxException {
        if (op instanceof BinaryOperator) {
            BinaryOperator BinaryOp = (BinaryOperator) op;
            if (operandSt.size() >= 2) {
                ExpressionToken ExToken2 = operandSt.pop();
                ExpressionToken ExToken1 = operandSt.pop();
                BinaryOp.setlChild(ExToken1);
                BinaryOp.setrChild(ExToken2);
                operandSt.push(BinaryOp);
            } else
                throw new SyntaxException(op.getLines(), op.getPos(), "missing operand");
        } else {
            if (!operandSt.isEmpty()) {
                UnaryOperator UnaryOp = (UnaryOperator) op;
                ExpressionToken ExToken = operandSt.pop();
                UnaryOp.setChild(ExToken);
                operandSt.push(UnaryOp);
            } else
                throw new SyntaxException(op.getLines(), op.getPos(), "missing operand");
        }
    }

    private ExpressionToken detectExpression() throws SyntaxException {
        Token token = getToken();
        if (token == null)
            return null;

        Stack<ExpressionToken> operandSt = new Stack<>();
        Stack<Operator> operatorSt = new Stack<>();

        while (isExpressionToken()) {
            // Whenever meets "(" go recursion
            Token tk = getToken();
            if (detectSeparator(SeparatorType.LEFTPARENTHESES)) {
                operandSt.push(detectExpression());
                if (!detectSeparator(SeparatorType.RIGHTPARENTHESES))
                    throw new SyntaxException(tk.getLines(), tk.getPos(), "unmatched left parentheses");
            }
            // Whenever meets ")" or "]" break recursion
            else if (isSeparator(SeparatorType.RIGHTPARENTHESES) || isSeparator(SeparatorType.RIGHTBRACKET))
                break;
            // Operator
            else if (isOperator()) {
                Operator op = (Operator) getToken();
                if (operatorSt.isEmpty()) {
                    operatorSt.push(op);
                    next();
                    continue;
                }
                Operator oldOp = operatorSt.peek();
                if (Operator.isPrioriThan(op, oldOp)) {
                    operatorSt.push(op);
                    next();
                } else {
                    while (!operatorSt.empty() && !Operator.isPrioriThan(op, operatorSt.peek())) {
                        StackOperation(operandSt, operatorSt.peek());
                        operatorSt.pop();
                    }
                    operatorSt.push(op);
                    next();
                }
            }
            // detect array
            else if (isIdSeparator(SeparatorType.LEFTBRACKET)){
                String id= detectIdentifier();
                ArrayId aid = new ArrayId();
                aid.setId(id);
                operandSt.push(aid);
                next();
                ExpressionToken index = detectExpression();
                if(index == null)
                    throw new SyntaxException(tk.getLines(),tk.getPos(),"missing array index");
                aid.setIndex(index);
                if(!detectSeparator(SeparatorType.RIGHTBRACKET))
                    throw new SyntaxException(tk.getLines(),tk.getPos(),"unmatched left bracket");
            }
            // detect function
            else if (isIdSeparator(SeparatorType.LEFTPARENTHESES)) {
                String id = detectIdentifier();
                FunctionId fid = new FunctionId();
                fid.setId(id);
                operandSt.push(fid);
                next();
                ExpressionToken para = detectExpression();
                if(para != null){
                    fid.addParameter(para);
                    while(detectSeparator(SeparatorType.COMMA)){
                        para = detectExpression();
                        if(para == null)
                            throw new SyntaxException(tk.getLines(), tk.getPos(), "missing parameter");
                        fid.addParameter(para);
                    }
                }
                if (!detectSeparator(SeparatorType.RIGHTPARENTHESES))
                    throw new SyntaxException(tk.getLines(), tk.getPos(), "unmatched left parentheses");

            }
            // Operand(value or identifier)
            else {
                operandSt.push((ExpressionToken)getToken());
                next();
            }
        }

        while (!operatorSt.isEmpty()) {
            Operator op = operatorSt.pop();
            StackOperation(operandSt, op);
        }
        if (operandSt.size() > 1)
            throw new SyntaxException(token.getLines(), token.getPos(), "redundant operand");
        if (operandSt.empty())
            return null;
        return operandSt.pop();
    }

    //print the tree(just for testing)
    private void printExpressionTree(ExpressionToken ex) {
        if (ex != null) {
            System.out.println(ex);
            if (ex instanceof BinaryOperator) {
                printExpressionTree(((BinaryOperator) ex).getlChild());
                printExpressionTree(((BinaryOperator) ex).getrChild());
            } else if (ex instanceof UnaryOperator)
                printExpressionTree(((UnaryOperator) ex).getChild());
        }
    }

    private Statement detectStatement() {
        // TODO:
        return null;
    }

    private Initialization detectInitialization() throws SyntaxException {
        Initialization initialization = new Initialization();
        initialization.setArray(false);
        ValueType dataType = detectDataType();
        if (dataType == null)
            return null;
        String id = detectIdentifier();
        if (id == null)
            throwException("missing identifier");
        initialization.setId(id);
        if (detectSeparator(SeparatorType.SEMICOLON))
            initialization.setValue(new Value(dataType));
        else if (detectOperator(OperatorType.ASSIGN))
            initialization.setValue(detectExpression());
        else if (detectSeparator(SeparatorType.LEFTBRACKET)){
            // array initialization statement
            initialization.setArray(true);
            ExpressionToken arrayLength = detectExpression();
            if(arrayLength == null){
                // deduce array length from initialization list
                if(!detectSeparator(SeparatorType.RIGHTBRACKET))
                    throwException("missing right bracket");
                if (!detectOperator(OperatorType.ASSIGN))
                    throwException("missing assign operator");
                if (!detectSeparator(SeparatorType.LEFTBRACE))
                    throwException("missing left brace");
                ExpressionToken element = detectExpression();
                if(element == null)
                    throwException("can not declare an array of length 0");
                initialization.addElement(element);
                int cnt = 1;
                while(detectSeparator(SeparatorType.COMMA)){
                    element = detectExpression();
                    if(element == null)
                        throwException("missing array element");
                    initialization.addElement(element);
                    cnt++;
                }
                if (!detectSeparator(SeparatorType.RIGHTBRACE))
                    throwException("missing right brace");
                Value len = new Value(ValueType.INTEGER);
                len.setIntValue(cnt);
                initialization.setArrayLength(len);
            }else{
                // array length is given
                initialization.setArrayLength(arrayLength);
                if(!detectSeparator(SeparatorType.RIGHTBRACKET))
                    throwException("missing right bracket");
                if(detectSeparator(SeparatorType.SEMICOLON))
                    return initialization;
                if (!detectOperator(OperatorType.ASSIGN))
                    throwException("missing assign operator");
                if (!detectSeparator(SeparatorType.LEFTBRACE))
                    throwException("missing left brace");
                ExpressionToken element = detectExpression();
                if(element == null)
                    throwException("can not declare an array of length 0");
                initialization.addElement(element);
                while(detectSeparator(SeparatorType.COMMA)){
                    element = detectExpression();
                    if(element == null)
                        throwException("missing array element");
                    initialization.addElement(element);
                }
                if (!detectSeparator(SeparatorType.RIGHTBRACE))
                    throwException("missing right brace");
            }
        }else
            throwException("missing semicolon");
        return initialization;
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
        while (!detectSeparator(SeparatorType.RIGHTBRACE)) {
            // TODO:
        }

        if (scanner.iseof())
            throwException("missing right-brace");


        return function;


    }


}
