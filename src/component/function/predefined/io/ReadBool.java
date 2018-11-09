package component.function.predefined.io;

import component.context.DataStack;
import component.function.Function;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import type.ValueType;

import java.util.Scanner;

public class ReadBool extends Function {

    public ReadBool() {
        super(new Identifier("readBool"), ValueType.BOOLEAN);
    }

    @Override
    public Value execute(DataStack context) throws RTException {
        Scanner sc = new Scanner(System.in);
        Boolean bool = false;
        while (sc.hasNext()) {
            if (sc.hasNextBoolean()) {
                bool = sc.nextBoolean();
                break;
            }
            sc.next();
        }
        //sc.close();
        Value value = new Value(ValueType.BOOLEAN);
        value.setBoolValue(bool);
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
