package component;

import component.context.DataStack;
import exception.RTException;
import token.Value;

public interface Executable {

    Value execute(DataStack context) throws RTException,ReturnValue;
}
