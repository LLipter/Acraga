package node;

import type.StatementType;
import type.ValueType;
import token.Value;

public class Initialization extends Statement {

    private String id;
    private Value value;

    public Initialization(String id, Value value){
        statementType = StatementType.INITIALIZATION;
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public Value getValue() {
        return value;
    }
}
