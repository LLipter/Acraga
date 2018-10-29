package component.context;

import component.ReturnValue;
import component.function.Function;
import component.function.FunctionSignature;
import exception.RTException;
import token.ArrayId;
import token.FunctionId;
import token.Identifier;
import token.Value;
import type.Casting;
import type.ValueType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;


public class DataStack {

    // Object maybe Value for simple variables or ArrayList<Value> for array variables
    private LinkedList<HashMap<String, Object>> dataStack;
    // all available function
    private HashMap<FunctionSignature, Function> functionMap;

    public HashMap<FunctionSignature, Function> getFunctionMap() {
        return functionMap;
    }

    public void setFunctionMap(HashMap<FunctionSignature, Function> functionMap) {
        this.functionMap = functionMap;
    }

    public DataStack(){
        dataStack = new LinkedList<>();
    }

    public void createFrame(){
        dataStack.addFirst(new HashMap<>());
    }

    public void releaseFrame(){
        dataStack.removeFirst();
    }

    private void throwException(Identifier identifier, String msg) throws RTException {
        throw new RTException(identifier.getLines(), identifier.getPos(), msg);
    }


    public Value getValue(Identifier identifier) throws RTException {
        if (identifier instanceof FunctionId)
            throwException(identifier, "left value required");
        // get array variable
        if (identifier instanceof ArrayId){
            Iterator<HashMap<String, Object>> it = dataStack.iterator();
            while(it.hasNext()){
                HashMap<String, Object> frame = it.next();
                int index = ((ArrayId)identifier).getIntIndex();
                if (frame.containsKey(identifier.getId())){
                    Object obj = frame.get(identifier.getId());
                    if(!(obj instanceof ArrayList))
                        throwException(identifier, String.format("%s is not an array, thus, not indexable", identifier.getId()));
                    ArrayList<Value> array = (ArrayList<Value>)obj;
                    if(array.size() < index + 1)
                        throwException(identifier, String.format("%s index out of range", identifier.getId()));
                    return array.get(index);
                }
            }
            throwException(identifier, String.format("%s not defined", identifier.getId()));
        }
        // get simple variable
        else{
            Iterator<HashMap<String, Object>> it = dataStack.iterator();
            while(it.hasNext()){
                HashMap<String, Object> frame = it.next();
                if (frame.containsKey(identifier.getId())){
                    Object obj = frame.get(identifier.getId());
                    if(obj instanceof Value)
                        return (Value)obj;
                    throwException(identifier, String.format("%s is an array", identifier.getId()));

                }
            }
            throwException(identifier, String.format("%s not defined", identifier.getId()));
        }

        return null; // never used
    }


    // declare simple variable
    public void declareValue(Identifier identifier, Value value, ValueType type) throws RTException {
        HashMap<String, Object> frame = dataStack.getFirst();
        if(frame.containsKey(identifier.getId()))
            throwException(identifier, String.format("%s defined multiple times", identifier.getId()));
        Value castedValue = Casting.casting(value, type);
        if(castedValue == null)
            throwException(identifier, String.format("%s is not compatible with type %s", identifier.getId(), value.getValueType()));
        frame.put(identifier.getId(), value);
    }

    // declare array variable
    public void declareValue(Identifier identifier, int length, ValueType type) throws RTException {
        HashMap<String, Object> frame = dataStack.getFirst();
        if(frame.containsKey(identifier.getId()))
            throwException(identifier, String.format("%s defined multiple times", identifier.getId()));
        ArrayList<Value> array = new ArrayList<>();
        for(int i=0;i<length;i++)
            array.add(new Value(type));
        frame.put(identifier.getId(), array);
    }

    public void setValue(Identifier identifier, Value value) throws RTException, ReturnValue {
        if(identifier instanceof FunctionId)
            throwException(identifier, "left value required");
        // set one element in an array variable
        if(identifier instanceof ArrayId){
            HashMap<String, Object> frame = dataStack.getFirst();
            if(!frame.containsKey(identifier.getId()))
                throwException(identifier, String.format("%s not defined", identifier.getId()));
            Object obj = frame.get(identifier.getId());
            if(!(obj instanceof ArrayList))
                throwException(identifier, String.format("%s is not an array, thus, not indexable", identifier.getId()));
            ArrayList<Value> array = (ArrayList<Value>)obj;
            int index = ((ArrayId)identifier).getIntIndex();
            if(array.size() < index + 1)
                throwException(identifier, String.format("%s index out of range", identifier.getId()));
            Value value_in_array = array.get(0);
            Value castedValue = Casting.casting(value, value_in_array.getValueType());
            if (castedValue == null)
                throwException(identifier, String.format("%s is not compatible with type %s", identifier.getId(), value.getValueType()));
            array.set(index, castedValue);
        }
        // set simple variable
        else{
            HashMap<String, Object> frame = dataStack.getFirst();
            if(!frame.containsKey(identifier.getId()))
                throwException(identifier, String.format("%s not defined", identifier.getId()));
            Object obj = frame.get(identifier.getId());
            if(!(obj instanceof Value))
                throwException(identifier, String.format("%s is an array", identifier.getId()));
            Value oldValue = (Value)obj;
            Value castedValue = Casting.casting(value, oldValue.getValueType());
            if (castedValue == null)
                throwException(identifier, String.format("%s is not compatible with type %s", identifier.getId(), value.getValueType()));
            frame.put(identifier.getId(), castedValue);
        }


    }
}
