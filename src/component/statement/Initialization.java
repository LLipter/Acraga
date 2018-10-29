package component.statement;

import component.ReturnValue;
import component.context.DataStack;
import exception.RTException;
import token.exprtoken.ExpressionToken;
import token.exprtoken.Value;
import token.exprtoken.identifier.ArrayId;
import token.exprtoken.identifier.FunctionId;
import token.exprtoken.identifier.Identifier;
import type.StatementType;
import type.ValueType;

import java.util.ArrayList;
import java.util.Iterator;

public class Initialization extends Statement implements Iterable<ExpressionToken> {

    private Identifier id;
    private ExpressionToken value;
    private ArrayList<ExpressionToken> elements;

    public Initialization() {
        statementType = StatementType.INITIALIZATION;
        elements = new ArrayList<>();
    }

    public Identifier getId() {
        return id;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    public ExpressionToken getValue() {
        return value;
    }

    public void setValue(ExpressionToken value) {
        this.value = value;
    }

    public void addElement(ExpressionToken expressionToken) {
        elements.add(expressionToken);
    }

    @Override
    public Iterator<ExpressionToken> iterator() {
        return elements.iterator();
    }

    @Override
    public Value execute(DataStack context) throws RTException, ReturnValue {
        if (id instanceof FunctionId)
            throw new RTException(id.getLines(), id.getPos(), "function identifier cannot be used to declare an array");
        // declare an array
        if (id instanceof ArrayId) {
            ArrayId aid = (ArrayId) id;
            Value len_value = aid.getLength().execute(context);
            if (!len_value.isInt())
                throw new RTException(id.getLines(), id.getPos(), "array length must be integer");
            int len = len_value.getIntValue().intValue();
            ArrayList<Value> value_elements = new ArrayList<>();
            for (int i = 0; i < elements.size(); i++)
                value_elements.add(elements.get(i).execute(context));
            context.declareValue(aid, len, aid.getDataType(), value_elements);
        }
        // declare a simple variable
        else
            context.declareValue(id, value.execute(context), id.getDataType());

        // the result of initialization is always void
        return new Value(ValueType.VOID);
    }

    @Override
    public void print(int indent) {
        printWithIndent(indent, "[Initialization Statement]");
        printWithIndent(indent, String.format("[Data Type] %s", id.getDataType()));
        if (id instanceof FunctionId) {
            printWithIndent(indent, "{!!!ILLEGAL FUNCTION IDENTIFIER INITIALIZATION!!!}");
        } else if (id instanceof ArrayId) {
            printWithIndent(indent, "[Array Initialization]");
            printWithIndent(indent, "[Array Length]");
            ((ArrayId) id).getLength().print(indent + 4);
            printWithIndent(indent, "[End of Array Length]");
            printWithIndent(indent, "[Explicitly Initialized Elements]");
            for (int i = 0; i < elements.size(); i++) {
                printWithIndent(indent, String.format("[Index %d]", i));
                elements.get(i).print(indent + 4);
                printWithIndent(indent, String.format("[End of Index %d]", i));
            }
            printWithIndent(indent, "[End of Explicitly Initialized Elements]");
        } else {
            printWithIndent(indent, "[Simple Variable Initialization]");
            printWithIndent(indent, "[Value]");
            value.print(indent + 4);
            printWithIndent(indent, "[End of Value]");
        }
        printWithIndent(indent, "[End of Initialization Statement]");
    }
}
