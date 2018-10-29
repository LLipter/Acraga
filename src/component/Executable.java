package component;

import component.context.DataStack;
import exception.RTException;
import token.exprtoken.Value;

public interface Executable {

    Value execute(DataStack context) throws RTException,ReturnValue;
}
