package component.function.predefined.io;

import component.context.DataStack;
import component.function.predefined.Predefined;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import type.ValueType;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Read extends Predefined {

    private static Scanner sc = new Scanner(System.in);
    private static HashMap<ValueType, String> functionNameDict;

    static {
        functionNameDict = new HashMap<>();
        functionNameDict.put(ValueType.INTEGER, "Int");
        functionNameDict.put(ValueType.DOUBLE, "Double");
        functionNameDict.put(ValueType.BOOLEAN, "Bool");
        functionNameDict.put(ValueType.STRING, "String");
    }

    private ValueType readType = null;

    // readline
    public Read() {
        super(new Identifier("readLine"), ValueType.STRING);
    }

    public Read(ValueType type) {
        super(new Identifier("read" + functionNameDict.get(type)), type);
        readType = type;
    }

    @Override
    public Value execute(DataStack context) throws RTException {
        Scanner sc = new Scanner(System.in);
        Value value = null;
        try {
            // readline
            if (readType == null) {
                value = new Value(ValueType.STRING);
                value.setStringValue(sc.nextLine());
            } else {
                value = new Value(readType);
                switch (readType) {
                    case INTEGER:
                        value.setIntValue(sc.nextBigInteger());
                        break;
                    case DOUBLE:
                        value.setDoubleValue(sc.nextBigDecimal());
                        break;
                    case BOOLEAN:
                        value.setBoolValue(sc.nextBoolean());
                        break;
                    case STRING:
                        value.setStringValue(sc.next());
                        break;
                }
            }
        } catch (InputMismatchException e) {
            throw new RTException("not compatible input data");
        }

        return value;
    }

    @Override
    public void print() {
        System.out.println("[Function]");
        System.out.println(String.format("[Function Signature] %s", functionSignature));
        System.out.println(String.format("[Return Type] %s", returnType));
        System.out.println("[End of Function]");
    }


}
