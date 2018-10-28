package component.statement;

import component.context.DataStack;
import token.Value;

public interface Executable {

    public Value execute(DataStack context);
}
