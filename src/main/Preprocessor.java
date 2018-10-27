package main;

import exception.SyntaxException;
import token.Identifier;
import token.Value;
import type.ValueType;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Preprocessor {

    private LinkedList<Integer> buffer;
    private int line;
    private int pos;

    private void throwException(String msg) throws SyntaxException{
        throw new SyntaxException(line, pos, msg);
    }

    public Preprocessor(String inputFile) throws SyntaxException{
        try {
            Reader reader = new InputStreamReader(new FileInputStream(inputFile));
            buffer = new LinkedList<Integer>();
            line = 1;
            pos = 0;

            int ch_cur = reader.read();
            int ch_next = reader.read();
            while (ch_cur != -1) {
                // ignore /r in windows system
                if (ch_cur == '\r') {
                    ch_cur = ch_next;
                    ch_next = reader.read();
                    continue;
                }

                // ignore all comments
                if (ch_cur == '/' && ch_next == '/') {
                    while (ch_cur != '\n' && ch_cur != -1) {
                        ch_cur = ch_next;
                        ch_next = reader.read();
                    }
                    if (ch_cur == -1)
                        return;
                }

                buffer.addLast(ch_cur);
                ch_cur = ch_next;
                ch_next = reader.read();
            }


        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public int getLine() {
        return line;
    }

    public int getPos() {
        return pos;
    }

    public boolean iseof() {
        return buffer.isEmpty();
    }

    public boolean isWhiteSpace() {
        return isWhiteSpace(getCh());
    }

    public boolean isWhiteSpace(int ch) {
        return ch == ' ' || ch == '\t' || ch == '\n' || ch == -1;
    }

    public void next() {
        if (iseof())
            return;
        int ch = buffer.pollFirst();
        if (ch == '\n') {
            line++;
            pos = 0;
        }
        pos++;
    }

    public int getCh() {
        if (iseof())
            return -1;
        return buffer.getFirst();
    }

    public int getNextCh() {
        if (iseof())
            return -1;
        if(buffer.size()<2)
            return -1;
        return buffer.get(1);
    }

    public void nextNotWhiteSpace() {
        while (isWhiteSpace() && !iseof())
            next();
    }

    public boolean isKeyword(String keyword) {
        int len = keyword.length();
        if (buffer.size() < len)
            return false;

        int[] chs = new int[len + 1];
        Iterator<Integer> it = buffer.iterator();
        for (int i = 0; i < len; i++)
            chs[i] = it.next();
        if (buffer.size() == len)
            chs[len] = -1;
        else
            chs[len] = it.next();

        for (int i = 0; i < len; i++) {
            if ((int) keyword.charAt(i) != chs[i])
                return false;
        }
        if (isIdAlphabet(chs[len]))
            return false;

        // if detect keyword successfully, remove all of these characters from input stream
        for (int i = 0; i < len; i++)
            next();

        return true;

    }

    public boolean isDigit(int ch) {
        return Character.isDigit(ch);
    }

    public boolean isDigit() {
        return isDigit(getCh());
    }

    public boolean isHexDigit(int ch) {
        if (isDigit(ch))
            return true;
        if (ch >= 'A' && ch <= 'F')
            return true;
        if (ch >= 'a' && ch <= 'f')
            return true;
        return false;
    }

    public boolean isHexDigit() {
        return isHexDigit(getCh());
    }

    public boolean isUpper(int ch) {
        return Character.isUpperCase(ch);
    }

    public boolean isUpper() {
        return isUpper(getCh());
    }

    private Value isHexInteger() {
        if (buffer.size() < 3)
            return null;
        if (buffer.get(0) != '0')
            return null;
        if (buffer.get(1) != 'x' && buffer.get(1) != 'X')
            return null;
        if (!isHexDigit(buffer.get(2)))
            return null;

        next();
        next();
        Value value = new Value(ValueType.INTEGER);
        int intValue = 0;
        while (isHexDigit()) {
            intValue *= 16;
            if (isDigit())
                intValue += getCh() - '0';
            else if (isUpper())
                intValue += getCh() - 'A' + 10;
            else
                intValue += getCh() - 'a' + 10;
            next();
        }
        value.setIntValue(intValue);
        return value;

    }

    private Value isDecInteger() {
        boolean isPositive;
        if (getCh() == '+' && isDigit(buffer.get(1))) {
            isPositive = true;
            next();
        } else if (getCh() == '-' && isDigit(buffer.get(1))) {
            isPositive = false;
            next();
        } else if (isDigit())
            isPositive = true;
        else
            return null;

        Value value = new Value(ValueType.INTEGER);
        int intValue = 0;

        while (isDigit()) {
            intValue *= 10;
            intValue += getCh() - '0';
            next();
        }

        if (!isPositive)
            intValue *= -1;
        value.setIntValue(intValue);
        return value;
    }

    private Value isCharInteger() throws SyntaxException{
        int ch = getCh();
        if(ch != '\'')
            return null;
        next();
        ch = getCh();
        if (ch == -1 || ch == '\n')
            throwException("missing character");
        if (ch == '\\'){
            next();
            ch = getCh();
            if (ch == -1 || ch == '\n')
                throwException("missing escape character");
            if (ch == 'n')
                ch = '\n';
            else if (ch == 't')
                ch = '\t';
            else if (ch != '"' && ch != '\'' && ch != '\\')
                throwException("undefined escape character");
        }
        Value value = new Value(ValueType.INTEGER);
        value.setIntValue(ch);

        next();
        ch = getCh();
        if(ch == '\n' || ch == -1 || ch != '\'')
            throwException("missing right quote");
        next();

        return value;
    }

    private Value isInteger() throws SyntaxException{
        Value value;

        value = isHexInteger();
        if (value != null)
            return value;

        value = isDecInteger();
        if (value != null)
            return value;

        value = isCharInteger();
        if (value != null)
            return value;

        return null;
    }

    public Value isNumber() throws SyntaxException{
        Value intPart = isInteger();
        if(intPart == null)
            return null;

        // integer number
        if(getCh() != '.' && getCh() != 'e' && getCh() != 'E'){
            if(isIdAlphabet())
                throw new SyntaxException(line, pos, "identifier can not starts with number");
            return intPart;
        }

        double result = intPart.getIntValue();

        // decimal number
        if(getCh() == '.'){
            next();
            Value fractionPart = isDecInteger();
            if(fractionPart == null)
                throw new SyntaxException(line, pos, "missing fraction part number");
            if(getCh() != 'e' && getCh() != 'E' && isIdAlphabet())
                throw new SyntaxException(line, pos, "invalid float point number");
            double fractionValue = fractionPart.getIntValue();
            while(fractionValue >= 1)
                fractionValue /= 10;
            if(intPart.getIntValue() >= 0)
                result += fractionValue;
            else
                result -= fractionValue;
        }

        // scientific number
        if(getCh() == 'e' || getCh() == 'E'){
            next();
            Value expPart = isDecInteger();
            if(expPart == null)
                throw new SyntaxException(line, pos, "missing exponent part number");
            if(isIdAlphabet())
                throw new SyntaxException(line, pos, "invalid scientific representation");
            result = result * Math.pow(10, expPart.getIntValue());
        }

        Value value = new Value(ValueType.DOUBLE);
        value.setDoubleValue(result);
        return value;
    }

    public Value isBool() {
        Value value = new Value(ValueType.BOOLEAN);
        if (isKeyword("true"))
            value.setBoolValue(true);
        else if (isKeyword("false"))
            value.setBoolValue(false);
        else
            return null;

        return value;
    }

    public Value isString() throws SyntaxException{
        if (getCh() != '"')
            return null;

        next();
        Value value = new Value(ValueType.STRING);
        StringBuffer sb = new StringBuffer();
        while(true){
            int ch = getCh();
            if (ch == '"')
                break;
            if (ch == '\n' || ch == -1)
                throwException("missing right quote");
            if (ch == '\\'){
                next();
                ch = getCh();
                if (ch == -1 || ch == '\n')
                    throwException("missing escape character");
                if (ch == 'n')
                    ch = '\n';
                else if (ch == 't')
                    ch = '\t';
                else if (ch != '"' && ch != '\'' && ch != '\\')
                    throwException("undefined escape character");
            }
            sb.append((char)ch);
            next();
        }
        value.setStringValue(sb.toString());
        next();
        return value;

    }

    public boolean isLetter(int ch) {
        return Character.isLetter(ch);
    }

    public boolean isLetter() {
        return isLetter(getCh());
    }

    public boolean isIdAlphabet(int ch) {
        return isDigit(ch) || isLetter(ch) || ch == '_';
    }

    public boolean isIdAlphabet() {
        return isIdAlphabet(getCh());
    }

    public Identifier isIdentifier() {
        if (getCh() != '_' && !isLetter())
            return null;

        StringBuffer bString = new StringBuffer();
        while (isIdAlphabet()) {
            bString.append((char) getCh());
            next();
        }

        Identifier identifier = new Identifier();
        identifier.setId(bString.toString());
        return identifier;
    }


}
