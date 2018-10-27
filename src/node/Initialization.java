package node;

import token.ExpressionToken;
import type.StatementType;
import type.ValueType;
import token.Value;

public class Initialization extends Statement {

    private String id;
    private ExpressionToken value;

    public Initialization(){
        statementType = StatementType.INITIALIZATION;
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
}
