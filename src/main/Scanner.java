package main;

import exception.SyntaxException;
import token.*;
import type.KeywordType;
import type.OperatorType;
import type.SeparatorType;

import javax.naming.NamingEnumeration;
import java.util.LinkedList;

public class Scanner {

    private Preprocessor preprocessor;
    private LinkedList<Token> tokens;

    public Scanner(Preprocessor preprocessor) throws SyntaxException {
        this.preprocessor = preprocessor;
        tokens = new LinkedList<Token>();

        while (!this.preprocessor.iseof()) {
            this.preprocessor.nextNotWhiteSpace();
            if (this.preprocessor.iseof())
                break;

            // detect separators
            Separator separator = detectSeparator();
            if (separator != null) {
                tokens.addLast(separator);
                continue;
            }

            // detect operators
            Operator op = detectOperator();
            if (op != null) {
                tokens.addLast(op);
                continue;
            }

            // detect values
            Value value = detectValue();
            if (value != null) {
                tokens.addLast(value);
                continue;
            }

            // detect keywords
            Keyword keyword = detectKeyword();
            if (keyword != null) {
                tokens.addLast(keyword);
                continue;
            }

            // detect identifier
            Identifier identifier = detectIdentifier();
            if (identifier != null) {
                tokens.addLast(identifier);
                continue;
            }

            throw new SyntaxException(this.preprocessor.getLine(), this.preprocessor.getPos(), "invalid token");

        }
    }

    // detect operators
    public Operator detectOperator() {
        Operator op;

        if (preprocessor.isOperator("<<="))
            op = new BinaryOperator(OperatorType.LEFTSHIFTINGASSIGN);
        else if (preprocessor.isOperator(">>="))
            op = new BinaryOperator(OperatorType.RIGHTSHIFTINGASSIGN);
        else if (preprocessor.isOperator(">="))
            op = new BinaryOperator(OperatorType.LESSTHANOREQUAL);
        else if (preprocessor.isOperator("<="))
            op = new BinaryOperator(OperatorType.LESSTHANOREQUAL);
        else if (preprocessor.isOperator("=="))
            op = new BinaryOperator(OperatorType.EQUAL);
        else if (preprocessor.isOperator("!=") || preprocessor.isOperator("<>"))
            op = new BinaryOperator(OperatorType.NOTEQUAL);
        else if (preprocessor.isOperator("&&"))
            op = new BinaryOperator(OperatorType.LOGICALAND);
        else if (preprocessor.isOperator("||"))
            op = new BinaryOperator(OperatorType.LOGICALOR);
        else if (preprocessor.isOperator("<<"))
            op = new BinaryOperator(OperatorType.LEFTSHIFTING);
        else if (preprocessor.isOperator(">>"))
            op = new BinaryOperator(OperatorType.RIGHTSHIFTING);
        else if (preprocessor.isOperator("+="))
            op = new BinaryOperator(OperatorType.ADDASSIGN);
        else if (preprocessor.isOperator("-="))
            op = new BinaryOperator(OperatorType.SUBASSIGN);
        else if (preprocessor.isOperator("*="))
            op = new BinaryOperator(OperatorType.MULASSIGN);
        else if (preprocessor.isOperator("/="))
            op = new BinaryOperator(OperatorType.DIVASSIGN);
        else if (preprocessor.isOperator("%="))
            op = new BinaryOperator(OperatorType.MODASSIGN);
        else if (preprocessor.isOperator("&="))
            op = new BinaryOperator(OperatorType.BITWISEANDASSIGN);
        else if (preprocessor.isOperator("|="))
            op = new BinaryOperator(OperatorType.BITWISEORASSIGN);
        else if (preprocessor.isOperator("^="))
            op = new BinaryOperator(OperatorType.BITWISEXORASSIGN);
        else if (preprocessor.isOperator("*"))
            op = new BinaryOperator(OperatorType.MUL);
        else if (preprocessor.isOperator("/"))
            op = new BinaryOperator(OperatorType.DIV);
        else if (preprocessor.isOperator("%"))
            op = new BinaryOperator(OperatorType.MOD);
        else if (preprocessor.isOperator(">"))
            op = new BinaryOperator(OperatorType.GREATERTHAN);
        else if (preprocessor.isOperator("<"))
            op = new BinaryOperator(OperatorType.LESSTHAN);
        else if (preprocessor.isOperator("="))
            op = new BinaryOperator(OperatorType.ASSIGN);
        else if (preprocessor.isOperator("~"))
            op = new UnaryOperator(OperatorType.BITWISENEGATE);
        else if (preprocessor.isOperator("&"))
            op = new BinaryOperator(OperatorType.BITWISEAND);
        else if (preprocessor.isOperator("|"))
            op = new BinaryOperator(OperatorType.BITWISEOR);
        else if (preprocessor.isOperator("^"))
            op = new BinaryOperator(OperatorType.BITWISEXOR);
        else if (preprocessor.isOperator("!"))
            op = new UnaryOperator(OperatorType.NOT);
        else if (preprocessor.isOperator("+")){
            if(tokens.isEmpty() || (tokens.getLast() instanceof Operator))
                op = new UnaryOperator(OperatorType.POSITIVESIGN);
            else if((tokens.getLast() instanceof Separator)
                    &&((Separator) tokens.getLast()).getSeparatorType()!= SeparatorType.RIGHTPARENTHESES
                    &&((Separator) tokens.getLast()).getSeparatorType()!= SeparatorType.RIGHTBRACKET
                    &&((Separator) tokens.getLast()).getSeparatorType()!= SeparatorType.RIGHTBRACE)
                op = new UnaryOperator(OperatorType.POSITIVESIGN);
            else
                op = new BinaryOperator(OperatorType.ADD);
        }
        else if (preprocessor.isOperator("-")) {
            if(tokens.isEmpty() || (tokens.getLast() instanceof Operator))
                op = new UnaryOperator(OperatorType.NEGATIVESIGN);
            else if((tokens.getLast() instanceof Separator)
                    &&((Separator) tokens.getLast()).getSeparatorType() != SeparatorType.RIGHTPARENTHESES
                    &&((Separator) tokens.getLast()).getSeparatorType() != SeparatorType.RIGHTBRACKET
                    &&((Separator) tokens.getLast()).getSeparatorType() != SeparatorType.RIGHTBRACE)
                op = new UnaryOperator(OperatorType.NEGATIVESIGN);
            else
                op = new BinaryOperator(OperatorType.SUB);
        }
        else
            return null;

        op.setLines(preprocessor.getLine());
        op.setPos(preprocessor.getPos());
        return op;
    }

    // detect separator
    public Separator detectSeparator(){
        Separator separator;
        if(preprocessor.getCh() == ';')
            separator = new Separator(SeparatorType.SEMICOLON);
        else if(preprocessor.getCh() == '{')
            separator = new Separator(SeparatorType.LEFTBRACE);
        else if(preprocessor.getCh() == '}')
            separator = new Separator(SeparatorType.RIGHTBRACE);
        else if(preprocessor.getCh() == '[')
            separator = new Separator(SeparatorType.LEFTBRACKET);
        else if(preprocessor.getCh() == ']')
            separator = new Separator(SeparatorType.RIGHTBRACKET);
        else if(preprocessor.getCh() == '(')
            separator = new Separator(SeparatorType.LEFTPARENTHESES);
        else if(preprocessor.getCh() == ')')
            separator = new Separator(SeparatorType.RIGHTPARENTHESES);
        else if(preprocessor.getCh() == ',')
            separator = new Separator(SeparatorType.COMMA);
        else
            return null;

        separator.setLines(preprocessor.getLine());
        separator.setPos(preprocessor.getPos());
        next();
        return separator;
    }

    // detect keyword
    public Keyword detectKeyword() {
        Keyword keyword;
        int lines = preprocessor.getLine();
        int pos = preprocessor.getPos();
        if (preprocessor.isKeyword("if"))
            keyword = new Keyword(KeywordType.IF);
        else if (preprocessor.isKeyword("else"))
            keyword = new Keyword(KeywordType.ELSE);
        else if (preprocessor.isKeyword("while"))
            keyword = new Keyword(KeywordType.WHILE);
        else if (preprocessor.isKeyword("for"))
            keyword = new Keyword(KeywordType.FOR);
        else if (preprocessor.isKeyword("int"))
            keyword = new Keyword(KeywordType.INT);
        else if (preprocessor.isKeyword("double"))
            keyword = new Keyword(KeywordType.DOUBLE);
        else if (preprocessor.isKeyword("string"))
            keyword = new Keyword(KeywordType.STRING);
        else if (preprocessor.isKeyword("bool"))
            keyword = new Keyword(KeywordType.BOOL);
        else
            return null;
        keyword.setLines(lines);
        keyword.setPos(pos);

        return keyword;
    }

    // detect value
    public Value detectValue() throws SyntaxException {
        Value value;
        int lines = preprocessor.getLine();
        int pos = preprocessor.getPos();

        if ((value = preprocessor.isNumber()) == null)
                if ((value = preprocessor.isBool()) == null)
                    if ((value = preprocessor.isString()) == null)
                        return null;

        value.setLines(lines);
        value.setPos(pos);

        return value;
    }

    // detect identifier
    public Identifier detectIdentifier() {
        int lines = preprocessor.getLine();
        int pos = preprocessor.getPos();
        Identifier identifier = preprocessor.isIdentifier();
        if (identifier == null)
            return null;
        identifier.setLines(lines);
        identifier.setPos(pos);
        return identifier;
    }

    public void print() {
        for (Token token : tokens) {
            String msg = String.format("line %-3d pos %-2d : %s", token.getLines(), token.getPos(), token.toString());
            System.out.println(msg);
        }
    }

    public Token getToken() {
        return tokens.peekFirst();
    }

    public Token getNextToken(){
        if(tokens.size() < 2)
            return null;
        return tokens.get(1);
    }

    public boolean iseof() {
        return tokens.isEmpty();
    }

    public void next() {
        tokens.pollFirst();
    }
}
