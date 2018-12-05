package main;

import component.function.Function;
import component.function.FunctionSignature;
import component.statement.*;
import exception.SyntaxException;
import token.Keyword;
import token.Separator;
import token.Token;
import token.exprtoken.ExpressionToken;
import token.exprtoken.Value;
import token.exprtoken.identifier.ArrayId;
import token.exprtoken.identifier.FunctionId;
import token.exprtoken.identifier.Identifier;
import token.exprtoken.operator.Operator;
import token.exprtoken.operator.binary.BinaryOperator;
import token.exprtoken.operator.unary.CastOperator;
import token.exprtoken.operator.unary.SelfDecrement;
import token.exprtoken.operator.unary.SelfIncrement;
import token.exprtoken.operator.unary.UnaryOperator;
import type.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class Parser {

    private Scanner scanner;
    private HashMap<FunctionSignature, Function> functionMap;
    private LinkedList<Statement> globalStatements;
    private ExpressionToken expressionRoot;
    private int line;
    private int pos;
    private StringBuilder sb;

    public StringBuilder getSb() {
        return sb;
    }

    public Parser(Scanner scanner) throws SyntaxException {
        this.scanner = scanner;
        functionMap = new HashMap<>();
        globalStatements = new LinkedList<>();
        sb=new StringBuilder();
        updateLinePos();
    }

    public void parseExpression() throws SyntaxException {
        expressionRoot = detectExpression(false);
        if (!scanner.iseof())
            throwException("redundant token");
        if (expressionRoot == null)
            throwException("no expression detected");
        expressionRoot.print(sb,0);
    }

    public void parseProgram() throws SyntaxException {
        while (!scanner.iseof()) {
            //detect functions
            Function function = detectFunction();
            if (function != null) {
                //locate main function
                if (function.getFunctionSignature().equals(FunctionSignature.mainFunctionSignature))
                    globalStatements.addLast(null);
                //add functions to function map
                if (functionMap.containsKey(function.getFunctionSignature()))
                    throw new SyntaxException(function.getId().getLines(), function.getId().getPos(), "function with the same signature exists");
                functionMap.put(function.getFunctionSignature(), function);
                continue;
            }
            //detect global statements
            Statement statement = detectStatement();
            if (statement != null)
                globalStatements.addLast(statement);
        }

        addGlobalStat2Sb();
        for (Function func : functionMap.values()) {
            sb.append(func.print());
            sb.append("\n");
        }
        addGlobalStat2Sb();
    }

    private void addGlobalStat2Sb(){
        for (Statement statement : globalStatements) {
            if (statement != null) {
                statement.print(sb,0);
                sb.append("\n");
            }
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

    //for function detection
    private Token getThirdToken() {
        return scanner.getThirdToken();
    }

    public ExpressionToken getExpressionRoot() {
        return expressionRoot;
    }

    public HashMap<FunctionSignature, Function> getFunctionMap() {
        return functionMap;
    }

    public LinkedList<Statement> getGlobalStatements() {
        return globalStatements;
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
    private boolean isOperator() {
        return isOperator(getToken());
    }

    // check whether a given token is certain operator
    private boolean isOperator(Token token, OperatorType type) {
        if (!isOperator(token))
            return false;
        return ((Operator) token).getOperatorType() == type;
    }

    // check whether current token is certain operator
    private boolean isOperator(OperatorType type) {
        return isOperator(getToken(), type);
    }

    // check whether current token is certain operator. if so, remove it
    private boolean detectOperator(OperatorType type) {
        if (!isOperator(type))
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
    private boolean isSeparator() {
        return isSeparator(getToken());
    }

    // check whether a given token is certain separator
    private boolean isSeparator(Token token, SeparatorType type) {
        if (!isSeparator(token))
            return false;
        return ((Separator) token).getSeparatorType() == type;
    }

    // check whether current token is certain separator
    private boolean isSeparator(SeparatorType type) {
        return isSeparator(getToken(), type);
    }

    // check whether current token is certain separator. if so, remove it
    private boolean detectSeparator(SeparatorType type) {
        if (!isSeparator(type))
            return false;
        next();
        return true;
    }

    // check whether current token is a keyword
    private boolean isKeyword() {
        Token token = getToken();
        if (token == null)
            return false;
        return token.getTokenType() == TokenType.KEYWORD;
    }

    // check whether current token is a given type keyword
    private boolean isKeyword(KeywordType type) {
        if (!isKeyword())
            return false;
        return ((Keyword) getToken()).getKeywordType() == type;
    }

    // check whether current token is a given type keyword. if so, remove it
    private boolean detectKeyword(KeywordType type) {
        if (!isKeyword(type))
            return false;
        next();
        return true;
    }

    // check whether current token is a data type
    private boolean isDataType() {
        Token token = getToken();
        if (token == null)
            return false;
        if (token.getTokenType() != TokenType.KEYWORD)
            return false;
        Keyword keyword = (Keyword) token;
        ValueType result = Casting.keywordType2ValueType(keyword.getKeywordType());
        if (result == null)
            return false;
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

    private boolean isIdentifier() {
        Token token = getToken();
        if (token == null)
            return false;
        return token instanceof Identifier;
    }

    // check whether current token is a identifier
    private Identifier detectIdentifier() {
        Token token = getToken();
        if (token == null)
            return null;
        if (token.getTokenType() != TokenType.IDENTIFIER)
            return null;
        next();
        return (Identifier) token;
    }

    // check whether current token belongs to expression
    private boolean isExpressionToken() {
        Token token = getToken();
        if (token == null)
            return false;
        if (token instanceof ExpressionToken)
            return true;
        if (token instanceof Separator) {
            Separator separator = (Separator) token;
            return separator.getSeparatorType() == SeparatorType.LEFTPARENTHESES
                    || separator.getSeparatorType() == SeparatorType.RIGHTPARENTHESES
                    || separator.getSeparatorType() == SeparatorType.LEFTBRACKET
                    || separator.getSeparatorType() == SeparatorType.RIGHTBRACKET;
        }
        return false;
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

    private ExpressionToken detectExpression(boolean hasSimicolon) throws SyntaxException {
        Token token = getToken();
        Token lastToken = null;
        if (token == null)
            return null;

        Stack<ExpressionToken> operandSt = new Stack<>();
        Stack<Operator> operatorSt = new Stack<>();

        while (isExpressionToken()) {
            Token tk = getToken();
            // Whenever meets "(" go recursion
            if (detectSeparator(SeparatorType.LEFTPARENTHESES)) {
                //detect explicit casting
                if (isDataType() && isSeparator(getNextToken(), SeparatorType.RIGHTPARENTHESES)) {
                    Token typeTk = getToken();
                    CastOperator castOp = new CastOperator();
                    castOp.setDesType(detectDataType());
                    castOp.setLines(typeTk.getLines());
                    castOp.setPos(typeTk.getPos());
                    operatorSt.push(castOp);
                    next();
                    continue;
                }
                //go recursion
                operandSt.push(detectExpression(false));
                if (!isSeparator(SeparatorType.RIGHTPARENTHESES))
                    throw new SyntaxException(tk.getLines(), tk.getPos(), "unmatched left parentheses");
                else {
                    lastToken = getToken();
                    next();
                }
            }
            // Whenever meets ")" or "]" break recursion
            else if (isSeparator(SeparatorType.RIGHTPARENTHESES) || isSeparator(SeparatorType.RIGHTBRACKET))
                break;
            // operator
            else if (isOperator()) {
                Token nextToken=getNextToken();
                if(nextToken!=null) {
                    if (nextToken instanceof Operator)
                        throw new SyntaxException(nextToken.getLines(), nextToken.getPos(), "redundant operator");
                    if (!(nextToken instanceof Value || nextToken instanceof Identifier || isSeparator(nextToken,SeparatorType.LEFTPARENTHESES)))
                        throwException("missing operand");
                }
                Operator op = (Operator) getToken();
                if (operatorSt.isEmpty()) {
                    operatorSt.push(op);
                    lastToken=op;
                    next();
                    continue;
                }
                Operator oldOp = operatorSt.peek();
                if (Operator.isPrioriThan(op, oldOp)) {
                    operatorSt.push(op);
                    lastToken=op;
                    next();
                } else {
                    while (!operatorSt.empty() && !Operator.isPrioriThan(op, operatorSt.peek())) {
                        StackOperation(operandSt, operatorSt.peek());
                        operatorSt.pop();
                    }
                    operatorSt.push(op);
                    lastToken=op;
                    next();
                }
            }
            //identifier or value
            else {
                Token nextToken = getNextToken();
                //detect identifier
                if (isIdentifier()) {
                    // detect array
                    if (isSeparator(nextToken, SeparatorType.LEFTBRACKET)) {
                        Identifier id = detectIdentifier();//next
                        ArrayId aid = new ArrayId();
                        aid.setId(id.getId());
                        aid.setLines(id.getLines());
                        aid.setPos(id.getPos());
                        operandSt.push(aid);
                        next();
                        ExpressionToken index = detectExpression(false);
                        if (index == null)
                            throw new SyntaxException(tk.getLines(), tk.getPos(), "missing array index");
                        aid.setIndex(index);
                        lastToken=getToken();
                        if (!detectSeparator(SeparatorType.RIGHTBRACKET))
                            throw new SyntaxException(tk.getLines(), tk.getPos(), "unmatched left bracket");
                    }
                    // detect function
                    else if (isSeparator(nextToken, SeparatorType.LEFTPARENTHESES)) {
                        Identifier id = detectIdentifier();//next
                        FunctionId fid = new FunctionId();
                        fid.setId(id.getId());
                        fid.setLines(id.getLines());
                        fid.setPos(id.getPos());
                        operandSt.push(fid);
                        next();
                        ExpressionToken para = detectExpression(false);
                        if (para != null) {
                            fid.addParameter(para);
                            while (detectSeparator(SeparatorType.COMMA)) {
                                para = detectExpression(false);
                                if (para == null)
                                    throw new SyntaxException(tk.getLines(), tk.getPos(), "missing parameter");
                                fid.addParameter(para);
                            }
                        }
                        lastToken=getToken();
                        if (!detectSeparator(SeparatorType.RIGHTPARENTHESES))
                            throw new SyntaxException(tk.getLines(), tk.getPos(), "unmatched left parentheses");

                    }
                    //post ++/--
                    else if (isOperator(nextToken, OperatorType.SELFINCREMENT)) {
                        SelfIncrement si = (SelfIncrement) nextToken;
                        si.setPre(false);
                        si.setChild((Identifier) getToken());
                        operandSt.push(si);
                        next();
                        lastToken=getToken();
                        next();
                    } else if (isOperator(getNextToken(), OperatorType.SELFDECREMENT)) {
                        SelfDecrement sd = (SelfDecrement) nextToken;
                        sd.setPre(false);
                        sd.setChild((Identifier) getToken());
                        operandSt.push(sd);
                        next();
                        lastToken=getToken();
                        next();
                    }
                    //just identifier
                    else {
                        operandSt.push((ExpressionToken) getToken());
                        lastToken=getToken();
                        next();
                    }
                }
                //detect value
                else {
                    operandSt.push((ExpressionToken) getToken());
                    lastToken=getToken();
                    next();
                }

                Token theNext=getToken();
                if(theNext!=null) {
                    if (theNext instanceof Value || theNext instanceof Identifier) {
                        if (theNext.getLines()!=lastToken.getLines())
                            throw new SyntaxException(lastToken.getLines(),lastToken.getPos(),"missing semicolon after this token");
                        else
                            throw new SyntaxException(theNext.getLines(),theNext.getPos(),"redundant operand");
                    }
                }
            }
        }

        if(hasSimicolon){
            Token Tk=getToken();
            if(!isSeparator(Tk,SeparatorType.SEMICOLON))
                throw new SyntaxException(lastToken.getLines(),lastToken.getPos(),"missing semicolon after this token");
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

    // used to debug
    // check whether all structures are detected correctly
    public void print() {
        System.out.println(sb.toString());
    }

    private Initialization detectInitialization() throws SyntaxException {
        Initialization initialization = new Initialization();
        ValueType dataType = detectDataType();
        if (dataType == null)
            return null;
        Identifier id = detectIdentifier();
        if (id == null)
            throwException("missing identifier");
        id.setDataType(dataType);
        initialization.setId(id);
        if (detectSeparator(SeparatorType.SEMICOLON))
            initialization.setValue(new Value(dataType));
        else if (detectOperator(OperatorType.ASSIGN)) {
            initialization.setValue(detectExpression(true));
            if (!detectSeparator(SeparatorType.SEMICOLON))
                throwException("missing semicolon");
        } else if (detectSeparator(SeparatorType.LEFTBRACKET)) {
            // array initialization statement
            ArrayId aid = new ArrayId();
            aid.setDataType(dataType);
            aid.setId(id.getId());
            initialization.setId(aid);
            ExpressionToken arrayLength = detectExpression(false);
            if (arrayLength == null) {
                // deduce array length from initialization list
                if (!detectSeparator(SeparatorType.RIGHTBRACKET))
                    throwException("missing right bracket");
                if (!detectOperator(OperatorType.ASSIGN))
                    throwException("missing assign operator");
                if (!detectSeparator(SeparatorType.LEFTBRACE))
                    throwException("missing left brace");
                ExpressionToken element = detectExpression(false);
                if (element == null)
                    throwException("can not declare an array of length 0");
                initialization.addElement(element);
                int cnt = 1;
                while (detectSeparator(SeparatorType.COMMA)) {
                    element = detectExpression(false);
                    if (element == null)
                        throwException("missing array element");
                    initialization.addElement(element);
                    cnt++;
                }
                if (!detectSeparator(SeparatorType.RIGHTBRACE))
                    throwException("missing right brace");
                if (!detectSeparator(SeparatorType.SEMICOLON))
                    throwException("missing semicolon");
                Value len = new Value(ValueType.INTEGER);
                len.setIntValue(cnt);
                aid.setLength(len);
            } else {
                // array length is given
                aid.setLength(arrayLength);
                if (!detectSeparator(SeparatorType.RIGHTBRACKET))
                    throwException("missing right bracket");
                if (detectSeparator(SeparatorType.SEMICOLON))
                    return initialization;
                if (!detectOperator(OperatorType.ASSIGN))
                    throwException("missing assign operator");
                if (!detectSeparator(SeparatorType.LEFTBRACE))
                    throwException("missing left brace");
                ExpressionToken element = detectExpression(false);
                if (element != null)
                    initialization.addElement(element);
                while (detectSeparator(SeparatorType.COMMA)) {
                    element = detectExpression(false);
                    if (element == null)
                        throwException("missing array element");
                    initialization.addElement(element);
                }
                if (!detectSeparator(SeparatorType.RIGHTBRACE))
                    throwException("missing right brace");
                if (!detectSeparator(SeparatorType.SEMICOLON))
                    throwException("missing semicolon");
            }
        } else
            throwException("missing semicolon");
        return initialization;
    }

    private IfElse detectIfElse() throws SyntaxException {
        IfElse ifElse = new IfElse();
        if (!detectKeyword(KeywordType.IF))
            return null;
        if (!detectSeparator(SeparatorType.LEFTPARENTHESES))
            throwException("missing left parentheses");
        ExpressionToken condition = detectExpression(false);
        if (condition == null)
            throwException("missing condition");
        if (!detectSeparator(SeparatorType.RIGHTPARENTHESES))
            throwException("missing right parentheses");

        LinkedList<Statement> ifbranch = detectCodeBlock();
        ifElse.setCondition(condition);
        ifElse.setIfBranch(ifbranch);

        // exist else branch
        if (detectKeyword(KeywordType.ELSE)) {
            LinkedList<Statement> elsebranch = detectCodeBlock();
            ifElse.setElseBranch(elsebranch);
        }

        return ifElse;
    }

    private While detectWhile() throws SyntaxException {
        While wStatemengt = new While();
        if (!detectKeyword(KeywordType.WHILE))
            return null;
        if (!detectSeparator(SeparatorType.LEFTPARENTHESES))
            throwException("missing left parentheses");
        ExpressionToken condition = detectExpression(false);
        if (condition == null)
            throwException("missing condition");
        if (!detectSeparator(SeparatorType.RIGHTPARENTHESES))
            throwException("missing right parentheses");

        LinkedList<Statement> loopStatement = detectCodeBlock();
        wStatemengt.setCondition(condition);
        wStatemengt.setLoopStatements(loopStatement);

        return wStatemengt;
    }

    private For detectFor() throws SyntaxException {
        For fStatement = new For();
        if (!detectKeyword(KeywordType.FOR))
            return null;
        if (!detectSeparator(SeparatorType.LEFTPARENTHESES))
            throwException("missing left parentheses");
        Initialization definition = detectInitialization();
        if (definition != null)
            fStatement.setInitialization(definition);
        else {
            ExpressionToken init = detectExpression(true);
            fStatement.setInit(init);
            if (!detectSeparator(SeparatorType.SEMICOLON))
                throwException("missing semicolon");
        }
        ExpressionToken condition = detectExpression(true);
        if (!detectSeparator(SeparatorType.SEMICOLON))
            throwException("missing semicolon");
        ExpressionToken incr = detectExpression(false);
        if (!detectSeparator(SeparatorType.RIGHTPARENTHESES))
            throwException("missing right parentheses");

        LinkedList<Statement> loopStatement = detectCodeBlock();
        fStatement.setCondition(condition);
        fStatement.setIncr(incr);
        fStatement.setLoopStatements(loopStatement);

        return fStatement;
    }

    private Break detectBreak() throws SyntaxException {
        Break bStatement = new Break();
        bStatement.setLine(line);
        bStatement.setPos(pos);
        if (!detectKeyword(KeywordType.BREAK))
            return null;
        if (!detectSeparator(SeparatorType.SEMICOLON))
            throwException("missing semicolon after break");
        return bStatement;
    }

    private Continue detectContinue() throws SyntaxException {
        Continue cStatement = new Continue();
        cStatement.setLine(line);
        cStatement.setPos(pos);
        if (!detectKeyword(KeywordType.CONTINUE))
            return null;
        if (!detectSeparator(SeparatorType.SEMICOLON))
            throwException("missing semicolon after continue");
        return cStatement;
    }

    private Return detectReturn() throws SyntaxException {
        Return rStatement = new Return();
        rStatement.setLine(line);
        rStatement.setPos(pos);
        if (!detectKeyword(KeywordType.RETURN))
            return null;
        ExpressionToken returnValue = detectExpression(true);
        if (returnValue == null)
            throwException("missing returned value");

        rStatement.setReturnValue(returnValue);

        if (!detectSeparator(SeparatorType.SEMICOLON))
            throwException("missing semicolon");

        return rStatement;
    }

    private Statement detectStatement() throws SyntaxException {
        // ignore all empty statements
        while (detectSeparator(SeparatorType.SEMICOLON))
            ;

        Initialization initialization = detectInitialization();
        if (initialization != null)
            return initialization;

        IfElse ifElse = detectIfElse();
        if (ifElse != null)
            return ifElse;

        While wStatement = detectWhile();
        if (wStatement != null)
            return wStatement;

        For fStatement = detectFor();
        if (fStatement != null)
            return fStatement;

        Break bStatement = detectBreak();
        if (bStatement != null)
            return bStatement;

        Continue cStatement = detectContinue();
        if (cStatement != null)
            return cStatement;

        Return rStatement = detectReturn();
        if (rStatement != null)
            return rStatement;

        Expression expression = new Expression();
        ExpressionToken root = detectExpression(true);
        if (root != null) {
            expression.setRoot(root);
            if (!detectSeparator(SeparatorType.SEMICOLON))
                throw new SyntaxException(root.getLines(), root.getPos(), "missing semicolon");
            return expression;
        }
        return null;
    }

    private LinkedList<Statement> detectStatements() throws SyntaxException {
        // check statements
        LinkedList<Statement> statements = new LinkedList<>();
        Statement statement;
        while ((statement = detectStatement()) != null) {
            statements.add(statement);
        }
        return statements;
    }

    private LinkedList<Statement> detectCodeBlock() throws SyntaxException {
        // check left-brace
        if (!detectSeparator(SeparatorType.LEFTBRACE))
            throwException("missing left brace");

        LinkedList<Statement> statements = detectStatements();

        if (!detectSeparator(SeparatorType.RIGHTBRACE))
            throwException("missing right brace");

        return statements;
    }


    private Function detectFunction() throws SyntaxException {
        if (!(isDataType()
                && (getNextToken() instanceof Identifier)
                && isSeparator(getThirdToken(), SeparatorType.LEFTPARENTHESES)))
            return null;

        Function function;
        // check return type
        ValueType returnType = detectDataType();
        if (returnType == null)
            throwException("missing return type");

        // check function name
        Identifier fid = detectIdentifier();
        if (fid == null)
            throwException("missing function identifier");
        function = new Function(fid, returnType);


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
            Identifier parameterID = detectIdentifier();
            if (parameterID == null)
                throwException("missing parameter identifier");
            function.addParameter(dataType, parameterID);

            // check comma
            if (detectSeparator(SeparatorType.COMMA) && detectSeparator(SeparatorType.RIGHTPARENTHESES))
                throwException("missing parameter");

        }

        function.setStatements(detectCodeBlock());

        return function;
    }


}
