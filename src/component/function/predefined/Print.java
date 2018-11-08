package component.function.predefined;

import component.context.DataStack;
import component.function.Function;
import component.function.FunctionSignature;
import component.signal.ControlSignal;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import type.ValueType;

public class Print extends Function {
    private boolean isNextLine;
    private ValueType type;

    public Print(boolean isNextLine,ValueType type) {
        if(isNextLine) {
            FunctionSignature fs = new FunctionSignature("println");
            setId(new Identifier("println"));
            setFunctionSignature(fs);
        }
        else {
            FunctionSignature fs = new FunctionSignature("print");
            setId(new Identifier("print"));
            setFunctionSignature(fs);
        }
        this.isNextLine=isNextLine;
        this.type=type;
        if(type==ValueType.STRING)
            addParameter(ValueType.STRING, new Identifier("msg"));
        else if(type==ValueType.BOOLEAN)
            addParameter(ValueType.BOOLEAN,new Identifier("msg"));
        else if(type==ValueType.INTEGER)
            addParameter(ValueType.INTEGER,new Identifier("msg"));
        else if(type==ValueType.DOUBLE)
            addParameter(ValueType.DOUBLE,new Identifier("msg"));
    }

    @Override
    public Value execute(DataStack context) throws RTException {
        if(arguments.isEmpty()){
            System.out.println();
            return new Value(ValueType.VOID);
        }
        Value value=arguments.get(0);
        String msg="";
        if(type==ValueType.STRING)
            msg=value.getStringValue();
        else if(type==ValueType.INTEGER)
            msg=value.getIntValue().toString();
        else if(type==ValueType.DOUBLE)
            msg=value.getDoubleValue().toString();
        else if(type==ValueType.BOOLEAN)
            msg=Boolean.toString(value.getBoolValue());
        if(isNextLine)
            System.out.println(msg);
        else
            System.out.print(msg);
        return new Value(ValueType.VOID);
    }

    @Override
    public void print() {
        System.out.println("[Function]");
        System.out.println(String.format("[Function Signature] %s", functionSignature));
        System.out.println(String.format("[Return Type] %s", returnType));
        System.out.println("[Statements]");
        if(isNextLine)
            System.out.println("    System.out.println(msg)");
        else
            System.out.println("    System.out.print(msg)");
        System.out.println("[End of Statements]");
        System.out.println("[End of Function]");
    }
}
