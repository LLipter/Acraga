package component.function.predefined.io;

import component.context.DataStack;
import component.function.FunctionSignature;
import component.function.predefined.Predefined;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import type.ValueType;

public class Print extends Predefined {
    private boolean isNextLine;
    private ValueType type;

    public Print(boolean isNextLine, ValueType type) {
        if (isNextLine) {
            FunctionSignature fs = new FunctionSignature("println");
            setId(new Identifier("println"));
            setFunctionSignature(fs);
        } else {
            FunctionSignature fs = new FunctionSignature("print");
            setId(new Identifier("print"));
            setFunctionSignature(fs);
        }
        this.isNextLine = isNextLine;
        this.type = type;
        if (type == ValueType.STRING)
            addParameter(ValueType.STRING, new Identifier("msg"));
        else if (type == ValueType.BOOLEAN)
            addParameter(ValueType.BOOLEAN, new Identifier("msg"));
        else if (type == ValueType.INTEGER)
            addParameter(ValueType.INTEGER, new Identifier("msg"));
        else if (type == ValueType.DOUBLE)
            addParameter(ValueType.DOUBLE, new Identifier("msg"));
    }

    @Override
    public Value execute(DataStack context) throws RTException {
        if (arguments.isEmpty()) {
            System.out.println();
            return new Value(ValueType.VOID);
        }
        Value value = arguments.get(0);
        String msg = "";
        if (type == ValueType.STRING)
            msg = value.getStringValue();
        else if (type == ValueType.INTEGER)
            msg = value.getIntValue().toString();
        else if (type == ValueType.DOUBLE)
            msg = value.getDoubleValue().toString();
        else if (type == ValueType.BOOLEAN)
            msg = Boolean.toString(value.getBoolValue());
        if (isNextLine)
            System.out.println(msg);
        else
            System.out.print(msg);
        return new Value(ValueType.VOID);
    }

}
