package component.function;

import token.Identifier;
import type.ValueType;

public class Parameter {

    private ValueType dataType;
    private Identifier parameterID;

    public Parameter(ValueType dataType, Identifier parameterID) {
        super();
        this.dataType = dataType;
        this.parameterID = parameterID;
    }

    public Parameter(ValueType dataType){
        super();
        this.dataType = dataType;
    }

    public ValueType getDataType() {
        return dataType;
    }

    public void setDataType(ValueType dataType) {
        this.dataType = dataType;
    }

    public Identifier getParameterID() {
        return parameterID;
    }

    public void setParameterID(Identifier parameterID) {
        this.parameterID = parameterID;
    }


    @Override
    public String toString() {
        return String.format("%s %s", dataType, parameterID.getId());
    }
}
