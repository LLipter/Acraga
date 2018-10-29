package component.function.predefined;

import component.context.DataStack;
import component.function.Function;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import type.ValueType;

public class Print extends Function {

    public Print(){
        super(new Identifier("print"), ValueType.VOID);
        addParameter(ValueType.STRING, new Identifier("msg"));
    }

    @Override
    public Value execute(DataStack context) throws RTException {
        String msg = arguments.get(0).getStringValue();
        System.out.print(msg);
        return new Value(ValueType.VOID);
    }
}
