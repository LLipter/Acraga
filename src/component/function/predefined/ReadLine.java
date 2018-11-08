package component.function.predefined;

import component.context.DataStack;
import component.function.Function;
import component.signal.ControlSignal;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import type.ValueType;

import java.util.Scanner;

public class ReadLine extends Function {

    public ReadLine() {
        super(new Identifier("readLine"), ValueType.STRING);
    }

    @Override
    public Value execute(DataStack context) throws RTException {
        Scanner sc = new Scanner(System.in);
        String msg = "";
        if (sc.hasNext()) {
            msg = sc.nextLine();
        }
        sc.close();
        Value value = new Value(ValueType.STRING);
        value.setStringValue(msg);
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
