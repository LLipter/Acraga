package component.statement;

import component.context.DataStack;
import exception.Runtime;
import token.Value;

public interface Executable {

    public Value execute(DataStack context) throws Runtime;
}
