package component.function.predefined;

import component.context.DataStack;
import component.function.Function;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import type.ValueType;

public class Print extends Function {

    public Print() {
        super(new Identifier("print"), ValueType.VOID);
        addParameter(ValueType.BOOLEAN, new Identifier("msg"));
    }

    @Override
    public Value execute(DataStack context) throws RTException {
        Boolean msg = arguments.get(0).getBoolValue();
        System.out.print(msg);
        return new Value(ValueType.VOID);
    }

    @Override
    public void print() {
        System.out.println("[Function]");
        System.out.println(String.format("[Function Signature] %s", functionSignature));
        System.out.println(String.format("[Return Type] %s", returnType));
        System.out.println("[Statements]");
        System.out.println("    System.out.print(msg)");
        System.out.println("[End of Statements]");
        System.out.println("[End of Function]");
    }
}
