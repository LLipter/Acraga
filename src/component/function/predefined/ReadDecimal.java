package component.function.predefined;

import component.context.DataStack;
import component.function.Function;
import component.signal.ControlSignal;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import type.ValueType;

import java.math.BigDecimal;
import java.util.Scanner;

public class ReadDecimal extends Function {

    public ReadDecimal() {
        super(new Identifier("readDecimal"), ValueType.DOUBLE);
    }

    @Override
    public Value execute(DataStack context) throws RTException {
        Scanner sc = new Scanner(System.in);
        BigDecimal bigDecimal = new BigDecimal("0.0");
        while (sc.hasNext()) {
            if (sc.hasNextBigDecimal()) {
                bigDecimal = sc.nextBigDecimal();
                break;
            }
            sc.next();
        }
        sc.close();
        Value value = new Value(ValueType.DOUBLE);
        value.setDoubleValue(bigDecimal);
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
