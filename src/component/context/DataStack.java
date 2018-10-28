package component.context;

import token.Identifier;
import token.Value;
import exception.RuntimeException;
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

    // get simple variable
    public Value getValue(Identifier identifier) throws RuntimeException{
        Iterator<HashMap<String, Object>> it = dataStack.iterator();
        while(it.hasNext()){
            HashMap<String, Object> frame = it.next();
            if (frame.containsKey(identifier.getId())){
                Object obj = frame.get(identifier.getId());
                if(obj instanceof Value)
                    return (Value)obj;
                throw new RuntimeException(identifier.getLines(), identifier.getPos(), String.format("%s is an array", identifier.getId()));
            }
        }
        throw new RuntimeException(identifier.getLines(), identifier.getPos(), String.format("%s not defined", identifier.getId()));
    }

    // get array variable
    public Value getValue(Identifier identifier, int index) throws RuntimeException{
        Iterator<HashMap<String, Object>> it = dataStack.iterator();
        while(it.hasNext()){
            HashMap<String, Object> frame = it.next();
            if (frame.containsKey(identifier.getId())){
                Object obj = frame.get(identifier.getId());
                if(!(obj instanceof ArrayList))
                    throw new RuntimeException(identifier.getLines(), identifier.getPos(), String.format("%s is not an array, thus, not indexable", identifier.getId()));
                ArrayList<Value> array = (ArrayList<Value>)obj;
                if(array.size() < index + 1)
                    throw new RuntimeException(identifier.getLines(), identifier.getPos(), String.format("%s index out of range", identifier.getId()));
                return array.get(index);
            }
        }
        throw new RuntimeException(identifier.getLines(), identifier.getPos(), String.format("%s not defined", identifier.getId()));
    }

    // declare simple variable
    public void declareValue(Identifier identifier, Value value) throws RuntimeException{
        HashMap<String, Object> frame = dataStack.getFirst();
        if(frame.containsKey(identifier.getId()))
            throw new RuntimeException(identifier.getLines(), identifier.getPos(), String.format("%s defined multiple times", identifier.getId()));
        frame.put(identifier.getId(), value);
    }

    // declare array variable
    public void declareValue(Identifier identifier, int length, ValueType type) throws RuntimeException{
        HashMap<String, Object> frame = dataStack.getFirst();
        if(frame.containsKey(identifier.getId()))
            throw new RuntimeException(identifier.getLines(), identifier.getPos(), String.format("%s defined multiple times", identifier.getId()));
        ArrayList<Value> array = new ArrayList<>();
        for(int i=0;i<length;i++)
            array.add(new Value(type));
        frame.put(identifier.getId(), array);
    }

    // set simple variable
    public void setValue(Identifier identifier, Value value) throws RuntimeException{
        HashMap<String, Object> frame = dataStack.getFirst();
        if(!frame.containsKey(identifier.getId()))
            throw new RuntimeException(identifier.getLines(), identifier.getPos(), String.format("%s not defined", identifier.getId()));
        Object obj = frame.get(identifier.getId());
        if(!(obj instanceof Value))
            throw new RuntimeException(identifier.getLines(), identifier.getPos(), String.format("%s is an array", identifier.getId()));
        frame.put(identifier.getId(), value);
    }

    // set one element in an array variable
    public void setValue(Identifier identifier, int index, Value value) throws RuntimeException{
        HashMap<String, Object> frame = dataStack.getFirst();
        if(!frame.containsKey(identifier.getId()))
            throw new RuntimeException(identifier.getLines(), identifier.getPos(), String.format("%s not defined", identifier.getId()));
        Object obj = frame.get(identifier.getId());
        if(!(obj instanceof ArrayList))
            throw new RuntimeException(identifier.getLines(), identifier.getPos(), String.format("%s is not an array, thus, not indexable", identifier.getId()));
        ArrayList<Value> array = (ArrayList<Value>)obj;
        if(array.size() < index + 1)
            throw new RuntimeException(identifier.getLines(), identifier.getPos(), String.format("%s index out of range", identifier.getId()));
        Value value_in_array = array.get(0);
        if(!value_in_array.asSameTypeAs(value))
            throw new RuntimeException(identifier.getLines(), identifier.getPos(), String.format("%s index out of range", identifier.getId()));

        array.set(index, value);
    }
}
