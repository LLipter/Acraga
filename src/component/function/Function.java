package component.function;

import component.ReturnValue;
import component.context.DataStack;
import component.statement.Statement;
import component.Executable;
import exception.RTException;
import token.exprtoken.identifier.Identifier;
import token.exprtoken.Value;
import type.Casting;
import type.ValueType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Function implements Iterable<Statement>, Executable{
    private Identifier id;
    private FunctionSignature functionSignature;
    private ArrayList<Value> arguments;
    private ValueType returnType;
    private LinkedList<Statement> statements;

    public ArrayList<Value> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayList<Value> arguments) {
        this.arguments = arguments;
    }

    public Function(Identifier fid, ValueType type) {
        id = fid;
        functionSignature = new FunctionSignature(fid.getId());
        returnType = type;
    }

    public void addParameter(ValueType type, Identifier pid) {
        functionSignature.addParameters(type, pid);
    }

    public FunctionSignature getFunctionSignature() {
        return functionSignature;
    }

    public ValueType getReturnType() {
        return returnType;
    }

    public LinkedList<Statement> getStatements() {
        return statements;
    }

    public void setStatements(LinkedList<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public Iterator<Statement> iterator() {
        return statements.iterator();
    }

    @Override
    public Value execute(DataStack context) throws RTException {
        try{
            context.createFrame();
            // create argument in dataStack
            ArrayList<Parameter> parameters = functionSignature.getParameters();
            for(int i=0;i<parameters.size();i++){
                context.declareValue(parameters.get(i).getParameterID(), arguments.get(i), parameters.get(i).getDataType());
            }
            // execute all statements
            for(Statement s : statements){
                s.execute(context);
            }

            // no return statement meet
            if(returnType != ValueType.VOID)
                throw new RTException(id.getLines(), id.getPos(),String.format("function %s missing return statement", id.getId()));
            else
                return new Value(ValueType.VOID);

        }catch (ReturnValue retValue){
            Value castedValue = Casting.casting(retValue.getReturnValue(), returnType);
            if(castedValue == null)
                throw new RTException(id.getLines(), id.getPos(),String.format("incompatible return type", id.getId()));
            return castedValue;
        }finally {
            context.releaseFrame();
        }


    }

    @Override
    public String toString() {
        return String.format("<Function,%s,%s>",returnType, functionSignature);
    }
}
