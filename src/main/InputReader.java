package main;

import exception.SyntaxException;
import token.Identifier;
import token.Value;
import type.ValueType;

import java.io.*;
import java.util.LinkedList;

public class InputReader {

    private LinkedList<Integer> buffer;
    private int line;
    private int pos;


    public InputReader(String inputFile) {
        try {
            Reader reader = new InputStreamReader(new FileInputStream(inputFile));
            buffer = new LinkedList<Integer>();
            line = 1;
            pos = 0;

            int ch_cur = reader.read();
            int ch_next = reader.read();
            while (ch_cur != -1) {
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
            reader.close();

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

    public void nextNotWhiteSpace() {
        while (isWhiteSpace() && !iseof())
            next();
    }

    public boolean isKeyword(String keyword) {
        int len = keyword.length();
        if (buffer.size() < len)
            return false;

        int[] chs = new int[len + 1];
        for (int i = 0; i < len; i++)
            chs[i] = buffer.get(i);
        if (buffer.size() == len)
            chs[len] = -1;
        else
            chs[len] = buffer.get(len);

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

    public Value isHexInteger() {
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

    public Value isDecInteger() {
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

    public Value isInteger() {
        Value value;

        value = isHexInteger();
        if (value != null)
            return value;


        value = isDecInteger();
        if (value != null)
            return value;


        return null;
    }


    public Value isDouble() throws SyntaxException {
        Value value = isNormalDouble();
        if (value == null)
            return null;

        // check scientific representation
        int ch = getCh();
        if (ch == 'e' || ch == 'E') {
            next();
            Value exp = isDecInteger();
            if (exp == null)
                throw new SyntaxException(line, pos, "invalid scientific representation");
            value.setDoubleValue(value.getDoubleValue() * Math.pow(10, exp.getIntValue()));
        }

        return value;
    }

    public Value isNormalDouble() {
        boolean isPositive;
        int i = 0;
        if (getCh() == '+' && isDigit(buffer.get(1))) {
            isPositive = true;
            i++;
        } else if (getCh() == '-' && isDigit(buffer.get(1))) {
            isPositive = false;
            i++;
        } else if (isDigit())
            isPositive = true;
        else
            return null;

        int len = buffer.size();
        for (; i < len; i++) {
            int ch = buffer.get(i);

            if (ch == '.' && i < len - 1 && isDigit(buffer.get(i + 1)))
                break;

            if (!isDigit(ch))
                return null;
        }

        if (i == len)
            return null;

        int intPart = isDecInteger().getIntValue();
        if (intPart < 0)
            intPart = -intPart;
        next();
        int fractionPart = isDecInteger().getIntValue();

        double doubleValue = fractionPart;
        while (doubleValue > 1)
            doubleValue /= 10;
        doubleValue += intPart;

        if (!isPositive)
            doubleValue *= -1;

        Value value = new Value(ValueType.DOUBLE);
        value.setDoubleValue(doubleValue);
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

    public Value isString() {
        if (getCh() != '"')
            return null;

        int len = buffer.size();
        int i = 1;
        for (; i < len; i++) {
            int ch = buffer.get(i);
            if (ch == '\n')
                return null;
            if (ch == '"')
                break;
        }
        if (i == len)
            return null;

        Value value = new Value(ValueType.STRING);
        char[] chs = new char[i - 1];
        next();
        for (int j = 0; j < i - 1; j++) {
            chs[j] = (char) getCh();
            next();
        }
        next();
        value.setStringValue(String.valueOf(chs));
        return value;

    }

    public boolean isLetter(int ch) {
        return Character.isLetter(ch);
    }

    public boolean isLetter() {
        return isLetter(getCh());
    }

    public boolean isIdLetter(int ch) {
        return isLetter(ch) || isDigit() || ch == '_';
    }

    public boolean isIdLetter() {
        return isIdLetter(getCh());
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
