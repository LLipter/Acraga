package component.context;

import component.function.Function;
import component.function.FunctionSignature;
import component.signal.ReturnValue;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.ArrayId;
import token.exprtoken.identifier.FunctionId;
import token.exprtoken.identifier.Identifier;
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

    public DataStack() {
        dataStack = new LinkedList<>();
    }

    public HashMap<FunctionSignature, Function> getFunctionMap() {
        return functionMap;
    }

    public void setFunctionMap(HashMap<FunctionSignature, Function> functionMap) {
        this.functionMap = functionMap;
    }

    public void createFrame() {
        dataStack.addFirst(new HashMap<>());
    }

    public void releaseFrame() {
        dataStack.removeFirst();
    }

    private void throwException(Identifier identifier, String msg) throws RTException {
        throw new RTException(identifier.getLines(), identifier.getPos(), msg);
    }

    private void throwException(Value value, String msg) throws RTException {
        throw new RTException(value.getLines(), value.getPos(), msg);
    }

    public Value getValue(Identifier identifier) throws RTException {
        if (identifier instanceof FunctionId)
            throwException(identifier, "left value required");
        // get array variable
        if (identifier instanceof ArrayId) {
            Iterator<HashMap<String, Object>> it = dataStack.iterator();
            while (it.hasNext()) {
                HashMap<String, Object> frame = it.next();
                int index = ((ArrayId) identifier).getIntIndex();
                if (frame.containsKey(identifier.getId())) {
                    Object obj = frame.get(identifier.getId());
                    if (!(obj instanceof ArrayList))
                        throwException(identifier, String.format("%s is not an array, thus, not indexable", identifier.getId()));
                    ArrayList<Value> array = (ArrayList<Value>) obj;
                    if (array.size() < index + 1)
                        throwException(identifier, String.format("%s index out of range", identifier.getId()));
                    return array.get(index);
                }
            }
            throwException(identifier, String.format("%s not defined", identifier.getId()));
        }
        // get simple variable
        else {
            Iterator<HashMap<String, Object>> it = dataStack.iterator();
            while (it.hasNext()) {
                HashMap<String, Object> frame = it.next();
                if (frame.containsKey(identifier.getId())) {
                    Object obj = frame.get(identifier.getId());
                    if (obj instanceof Value)
                        return (Value) obj;
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
        if (frame.containsKey(identifier.getId()))
            throwException(identifier, String.format("%s defined multiple times", identifier.getId()));
        Value castedValue = Casting.safeCasting(value, type);
        if (castedValue == null)
            throwException(value, String.format("cannot implicitly cast from %s to %s", value.getValueString(), type));
        frame.put(identifier.getId(), castedValue);
    }

    // declare array variable
    public void declareValue(Identifier identifier, int length, ValueType type, ArrayList<Value> elements) throws RTException {
        HashMap<String, Object> frame = dataStack.getFirst();
        if (frame.containsKey(identifier.getId()))
            throwException(identifier, String.format("%s defined multiple times", identifier.getId()));
        ArrayList<Value> array = new ArrayList<>();
        if (length < elements.size())
            throwException(identifier, "array length must be larger than the number of elements in initialization list");
        int i;
        for (i = 0; i < elements.size(); i++) {
            Value value = elements.get(i);
            Value castedValue = Casting.safeCasting(value, type);
            if (castedValue == null)
                throwException(value, String.format("cannot implicitly cast from %s to %s", value.getValueString(), type));
            array.add(castedValue);
        }
        for (; i < length; i++)
            array.add(new Value(type));
        frame.put(identifier.getId(), array);
    }

    public void setValue(Identifier identifier, Value value) throws RTException, ReturnValue {
        if (identifier instanceof FunctionId)
            throwException(identifier, "left value required");
        // set one element in an array variable
        if (identifier instanceof ArrayId) {
            Iterator<HashMap<String, Object>> it = dataStack.iterator();
            while (it.hasNext()) {
                HashMap<String, Object> frame = it.next();
                if (!frame.containsKey(identifier.getId()))
                    continue;
                Object obj = frame.get(identifier.getId());
                if (!(obj instanceof ArrayList))
                    throwException(identifier, String.format("%s is not an array, thus, not indexable", identifier.getId()));
                ArrayList<Value> array = (ArrayList<Value>) obj;
                int index = ((ArrayId) identifier).getIntIndex();
                if (array.size() < index + 1)
                    throwException(identifier, String.format("%s index out of range", identifier.getId()));
                Value value_in_array = array.get(0);
                Value castedValue = Casting.safeCasting(value, value_in_array.getValueType());
                if (castedValue == null)
                    throwException(value, String.format("cannot implicitly cast from %s to %s", value.getValueString(), value_in_array.getValueType()));
                array.set(index, castedValue);
                return;
            }
            throwException(identifier, String.format("%s not defined", identifier.getId()));
        }
        // set simple variable
        else {
            Iterator<HashMap<String, Object>> it = dataStack.iterator();
            while (it.hasNext()) {
                HashMap<String, Object> frame = it.next();
                if (!frame.containsKey(identifier.getId()))
                    continue;
                Object obj = frame.get(identifier.getId());
                if (!(obj instanceof Value))
                    throwException(identifier, String.format("%s is an array", identifier.getId()));
                Value oldValue = (Value) obj;
                Value castedValue = Casting.safeCasting(value, oldValue.getValueType());
                if (castedValue == null)
                    throwException(identifier, String.format("cannot implicitly cast from %s to %s", value.getValueString(), oldValue.getValueType()));
                frame.put(identifier.getId(), castedValue);
                return;
            }
            throwException(identifier, String.format("%s not defined", identifier.getId()));
        }


    }
}
