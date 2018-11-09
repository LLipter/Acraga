package component.function.predefined.io;

import component.context.DataStack;
import component.function.Function;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import type.ValueType;

import java.math.BigInteger;
import java.util.Scanner;

public class ReadInt extends Function {


    public ReadInt() {
        super(new Identifier("readInt"), ValueType.INTEGER);
    }

    @Override
    public Value execute(DataStack context) throws RTException {
        Scanner sc = new Scanner(System.in);
        BigInteger bigInt = new BigInteger("0");
        while (sc.hasNext()) {
            if (sc.hasNextBigInteger()) {
                bigInt = sc.nextBigInteger();
                break;
            }
            sc.next();
        }
        //sc.close();
        Value value = new Value(ValueType.INTEGER);
        value.setIntValue(bigInt);
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
