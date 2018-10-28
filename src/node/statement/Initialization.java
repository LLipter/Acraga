package node.statement;

import token.ExpressionToken;
import type.StatementType;
import type.ValueType;
import token.Value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Initialization extends Statement implements Iterable<ExpressionToken> {

    private String id;
    private ExpressionToken value;
    private boolean isArray;
    private LinkedList<ExpressionToken> elements;
    private ExpressionToken arrayLength;

    public Initialization(){
        statementType = StatementType.INITIALIZATION;
        elements = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public ExpressionToken getValue() {
        return value;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValue(ExpressionToken value) {
        this.value = value;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        isArray = array;
    }

    public void addElement(ExpressionToken expressionToken){
        elements.add(expressionToken);
    }

    public ExpressionToken getArrayLength() {
        return arrayLength;
    }

    public void setArrayLength(ExpressionToken arrayLength) {
        this.arrayLength = arrayLength;
    }

    @Override
    public Iterator<ExpressionToken> iterator() {
        return elements.iterator();
    }


}
