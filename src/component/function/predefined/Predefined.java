package component.function.predefined;

import component.function.Function;
import token.exprtoken.identifier.Identifier;
import type.ValueType;

public abstract class Predefined extends Function {

    public Predefined() {
    }

    public Predefined(Identifier fid, ValueType type) {
        super(fid, type);
    }

    @Override
    public void print() {
        System.out.println("[Predefined Function]");
        System.out.println(String.format("[Function Signature] %s", functionSignature));
        System.out.println(String.format("[Return Type] %s", returnType));
        System.out.println("[End of Function]");
    }
}
