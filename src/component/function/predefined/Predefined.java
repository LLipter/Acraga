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
    public StringBuilder print() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Predefined Function]\n");
        sb.append(String.format("[Function Signature] %s\n", functionSignature));
        sb.append(String.format("[Return Type] %s\n", returnType));
        sb.append("[End of Function]\n");
        return sb;
    }
}
