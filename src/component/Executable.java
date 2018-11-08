package component;

import component.context.DataStack;
import component.signal.ControlSignal;
import exception.RTException;
import token.exprtoken.Value;

public interface Executable {

    Value execute(DataStack context) throws RTException, ControlSignal;
}
