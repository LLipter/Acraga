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

        //test function detectExpression()
        ExpressionToken ex = detectExpression();
        System.out.println();
        System.out.println("Tree:");
        test(ex);
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
        while (!detectSeparator(SeparatorType.RIGHTBRACE)) {
            // TODO:
        }

        if (scanner.iseof())
            throwException("missing right-brace");


        return function;


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

    //detect whether token belongs to operator
    private boolean detectOperator() {
        Token token = getToken();
        return (token != null && (token.getTokenType() == TokenType.OPERATOR));
    }

    //detect tokens in expression
    private boolean detectExpressionPart() {
        Token token = getToken();
        if (token == null)
            return false;
        return ((token instanceof ExpressionToken)
                || (token instanceof Separator && ((Separator) token).getSeparatorType() == SeparatorType.LEFTPARENTHESES)
                || (token instanceof Separator && ((Separator) token).getSeparatorType() == SeparatorType.RIGHTPARENTHESES)
                || (token instanceof Separator && ((Separator) token).getSeparatorType() == SeparatorType.LEFTBRACKET)
                || (token instanceof Separator && ((Separator) token).getSeparatorType() == SeparatorType.RIGHTBRACKET));
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

    public HashMap<FunctionSignature, Function> getFunctionMap() {
        return functionMap;
    }

    private Statement detectStatement() {
        // TODO:
        return null;
    }

    private boolean detectFunctionId() {
        return (getToken() != null
                && getToken().getTokenType() == TokenType.IDENTIFIER
                && getNextToken() != null
                && getNextToken().getTokenType() == TokenType.SEPARATOR
                && ((Separator) getNextToken()).getSeparatorType() == SeparatorType.LEFTPARENTHESES);
    }

    private boolean detectArrayId(){
        return (getToken() != null
                && getToken().getTokenType() == TokenType.IDENTIFIER
                && getNextToken() != null
                && getNextToken().getTokenType() == TokenType.SEPARATOR
                && ((Separator) getNextToken()).getSeparatorType() == SeparatorType.LEFTBRACKET);
    }

    private boolean detectRightParentheses() {
        return (getToken() != null
                && getToken().getTokenType() == TokenType.SEPARATOR
                && ((Separator) getToken()).getSeparatorType() == SeparatorType.RIGHTPARENTHESES);
    }

    private boolean detectLeftParentheses() {
        return (getToken() != null
                && getToken().getTokenType() == TokenType.SEPARATOR
                && ((Separator) getToken()).getSeparatorType() == SeparatorType.LEFTPARENTHESES);
    }

    private boolean detectRightBracket(){
        return (getToken() !=null
                && getToken().getTokenType() == TokenType.SEPARATOR
                && ((Separator) getToken()).getSeparatorType() == SeparatorType.RIGHTBRACKET);
    }

    private boolean detectComma() {
        return (getToken() != null
                && getToken().getTokenType() == TokenType.SEPARATOR
                && ((Separator) getToken()).getSeparatorType() == SeparatorType.COMMA);
    }

    //stack operation (condition is checked in detectExpression)
    private void StackOperation(Stack<ExpressionToken> OperandSt, Operator op) throws SyntaxException {
        if (op instanceof BinaryOperator) {
            BinaryOperator BinaryOp = (BinaryOperator) op;
            if (OperandSt.size() >= 2) {
                ExpressionToken ExToken2 = OperandSt.pop();
                ExpressionToken ExToken1 = OperandSt.pop();
                BinaryOp.setlChild(ExToken1);
                BinaryOp.setrChild(ExToken2);
                OperandSt.push(BinaryOp);
            } else
                throw new SyntaxException(op.getLines(), op.getPos(), "Syntax Error");
        } else {
            if (!OperandSt.isEmpty()) {
                UnaryOperator UnaryOp = (UnaryOperator) op;
                ExpressionToken ExToken = OperandSt.pop();
                UnaryOp.setChild(ExToken);
                OperandSt.push(UnaryOp);
            } else
                throw new SyntaxException(op.getLines(), op.getPos(), "Syntax Error");
        }
    }

    //build tree
    private ExpressionToken detectExpression() throws SyntaxException {
        Token token = getToken();
        if (token == null)
            return null;

        Stack<ExpressionToken> OperandSt = new Stack<>();
        Stack<Operator> OperatorSt = new Stack<>();

        while (detectExpressionPart()) {
            // Whenever meets "(" go recursion
            if (detectLeftParentheses()) {
                Token tk = getToken();
                next();
                OperandSt.push(detectExpression());
                if (!detectRightParentheses())
                    throw new SyntaxException(tk.getLines(), tk.getPos(), "Syntax Error");
                next();
            }
            // Whenever meets ")" or "]" break recursion
            else if (detectRightParentheses() || detectRightBracket())
                break;
                // Operator
            else if (detectOperator()) {
                Operator op = (Operator) getToken();
                if (OperatorSt.isEmpty()) {
                    OperatorSt.push(op);
                    next();
                    continue;
                }
                Operator oldOp = OperatorSt.peek();
                if (Operator.isPrioriThan(op.getOperatorType(), oldOp.getOperatorType())) {
                    OperatorSt.push(op);
                    next();
                } else {
                    while (!Operator.isPrioriThan(op.getOperatorType(), oldOp.getOperatorType())) {
                        if (!OperatorSt.isEmpty()) {
                            oldOp = OperatorSt.pop();
                            StackOperation(OperandSt, oldOp);
                        } else
                            break;
                    }
                    OperatorSt.push(op);
                    next();
                }
            }
            //detect array
            else if (detectArrayId()){
                Token tk=getToken();
                ArrayId aid=new ArrayId(detectIdentifier());
                OperandSt.push(aid);
                next();
                aid.setIndex(detectExpression());
                if(!detectRightBracket())
                    throw new SyntaxException(tk.getLines(),tk.getPos(),"Syntax Error");
                next();
            }
            //detect function
            else if (detectFunctionId()) {
                Token tk = getToken();
                FunctionId fid = new FunctionId(detectIdentifier());
                OperandSt.push(fid);
                //no parameters
                if (getNextToken() != null
                        && getNextToken().getTokenType() == TokenType.SEPARATOR
                        && ((Separator) getNextToken()).getSeparatorType() == SeparatorType.RIGHTPARENTHESES) {
                    next();
                    continue;
                }
                do {
                    next();
                    fid.addParameter(detectExpression());
                } while (detectComma());
                if (!detectRightParentheses())
                    throw new SyntaxException(tk.getLines(), tk.getPos(), "Syntax Error");
                next();
            }
            //Operand(value or identifier)
            else {
                ExpressionToken ExToken = ((ExpressionToken) getToken());
                OperandSt.push(ExToken);
                next();
            }
        }

        while (!OperatorSt.isEmpty()) {
            Operator op = OperatorSt.pop();
            StackOperation(OperandSt, op);
        }
        if (OperandSt.size() != 1 || !OperatorSt.isEmpty()) {
            throw new SyntaxException(token.getLines(), token.getPos(), "Syntax Error");
        }
        return OperandSt.pop();
    }

    //print the tree(just for testing)
    private void test(ExpressionToken ex) {
        if (ex != null) {
            System.out.println(ex);
            if (ex instanceof BinaryOperator) {
                test(((BinaryOperator) ex).getlChild());
                test(((BinaryOperator) ex).getrChild());
            } else if (ex instanceof UnaryOperator)
                test(((UnaryOperator) ex).getChild());
        }
    }


    private Initialization detectInitialization() throws SyntaxException {
        ValueType dataType = detectDataType();
        if (dataType == null)
            return null;
        String id = detectIdentifier();
        if (id == null)
            throwException("missing identifier");
        Value value = new Value(dataType);
        if (detectSeparator(SeparatorType.SEMICOLON))
            value.setDefaultValue();
        else if (detectOperator(OperatorType.ASSIGN)) {
            // TODO: detect expression
        } else
            throwException("missing semicolon");


        return new Initialization(id, value);
    }

}
