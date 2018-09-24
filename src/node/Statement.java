package node;

import type.StatementType;
import type.NodeType;


public abstract class Statement extends Node {

    protected StatementType statementType;

    public Statement() {
        nodeType = NodeType.STATEMENT;
    }
}
