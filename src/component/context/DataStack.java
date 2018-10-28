package component.context;

import token.Identifier;
import token.Value;
import exception.RuntimeException;
import type.Casting;
import type.ValueType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;


public class DataStack {

    // Object maybe Value for simple variables or ArrayList<Value> for array variables
    private LinkedList<HashMap<String, Object>> dataStack;

    public DataStack(){
        dataStack = new LinkedList<>();
    }

    public void createFrame(){
        dataStack.addFirst(new HashMap<>());
    }

    public void releaseFrame(){
        dataStack.removeFirst();
    }

    private void throwException(Identifier identifier, String msg) throws RuntimeException{
        throw new RuntimeException(identifier.getLines(), identifier.getPos(), msg);
    }

    // get simple variable
    public Value getValue(Identifier identifier) throws RuntimeException{
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
        return null; // never used
    }

    // get array variable
    public Value getValue(Identifier identifier, int index) throws RuntimeException{
        Iterator<HashMap<String, Object>> it = dataStack.iterator();
        while(it.hasNext()){
            HashMap<String, Object> frame = it.next();
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
        return null; // never used 
    }

    // declare simple variable
    public void declareValue(Identifier identifier, Value value) throws RuntimeException{
        HashMap<String, Object> frame = dataStack.getFirst();
        if(frame.containsKey(identifier.getId()))
            throwException(identifier, String.format("%s defined multiple times", identifier.getId()));
        frame.put(identifier.getId(), value);
    }

    // declare array variable
    public void declareValue(Identifier identifier, int length, ValueType type) throws RuntimeException{
        HashMap<String, Object> frame = dataStack.getFirst();
        if(frame.containsKey(identifier.getId()))
            throwException(identifier, String.format("%s defined multiple times", identifier.getId()));
        ArrayList<Value> array = new ArrayList<>();
        for(int i=0;i<length;i++)
            array.add(new Value(type));
        frame.put(identifier.getId(), array);
    }

    // set simple variable
    public void setValue(Identifier identifier, Value value) throws RuntimeException{
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

    // set one element in an array variable
    public void setValue(Identifier identifier, int index, Value value) throws RuntimeException{
        HashMap<String, Object> frame = dataStack.getFirst();
        if(!frame.containsKey(identifier.getId()))
            throwException(identifier, String.format("%s not defined", identifier.getId()));
        Object obj = frame.get(identifier.getId());
        if(!(obj instanceof ArrayList))
            throwException(identifier, String.format("%s is not an array, thus, not indexable", identifier.getId()));
        ArrayList<Value> array = (ArrayList<Value>)obj;
        if(array.size() < index + 1)
            throwException(identifier, String.format("%s index out of range", identifier.getId()));
        Value value_in_array = array.get(0);
        Value castedValue = Casting.casting(value, value_in_array.getValueType());
        if (castedValue == null)
            throwException(identifier, String.format("%s is not compatible with type %s", identifier.getId(), value.getValueType()));
        array.set(index, castedValue);
    }
}
