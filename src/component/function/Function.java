package component.function;

import component.Executable;
import component.signal.BreakRequest;
import component.signal.ContinueRequest;
import component.signal.ControlSignal;
import component.signal.ReturnValue;
import component.context.DataStack;
import component.statement.Statement;
import exception.RTException;
import token.exprtoken.Value;
import token.exprtoken.identifier.Identifier;
import type.Casting;
import type.ValueType;

import java.util.ArrayList;
import java.util.LinkedList;

public class Function implements Executable {
    protected Identifier id;
    protected FunctionSignature functionSignature;
    protected ArrayList<Value> arguments;
    protected ValueType returnType;
    protected LinkedList<Statement> statements;

    public Function(Identifier fid, ValueType type) {
        id = fid;
        functionSignature = new FunctionSignature(fid.getId());
        returnType = type;
    }

    public Function(){

    }

    public Identifier getId() {
        return id;
    }

    public void setId(Identifier id){
        this.id=id;
    }

    public void setFunctionSignature(FunctionSignature fs){
        functionSignature=fs;
    }

    public ArrayList<Value> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayList<Value> arguments) {
        this.arguments = arguments;
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
    public Value execute(DataStack context) throws RTException {
        try {
            context.createFrame();
            // create argument in dataStack
            ArrayList<Parameter> parameters = functionSignature.getParameters();
            for (int i = 0; i < parameters.size(); i++) {
                context.declareValue(parameters.get(i).getParameterID(), arguments.get(i), parameters.get(i).getDataType());
            }
            // execute all statements
            for (Statement s : statements) {
                s.execute(context);
            }
            //main
            if(functionSignature.equals(FunctionSignature.mainFunctionSignature) && returnType==ValueType.VOID)
                return new Value(ValueType.INTEGER);
            // no return statement meet
            if (returnType != ValueType.VOID)
                throw new RTException(id.getLines(), id.getPos(), String.format("function %s missing return statement", id.getId()));
            else
                return new Value(ValueType.VOID);

        } catch (BreakRequest br){
            throw new RTException(br.getLine(),br.getPos(),"cannot break outside loop");
        } catch (ContinueRequest cr){
            throw new RTException(cr.getLine(),cr.getPos(),"cannot continue outside loop");
        } catch (ReturnValue retValue) {
            Value castedValue = Casting.casting(retValue.getReturnValue(), returnType);
            if (castedValue == null)
                throw new RTException(retValue.getLine(), retValue.getPos(), String.format("incompatible return type", id.getId()));
            return castedValue;
        } catch(ControlSignal cs){
            throw new RTException("Unknown signal error");
        }
        finally {
            context.releaseFrame();
        }
    }

    @Override
    public String toString() {
        return String.format("<Function,%s,%s>", returnType, functionSignature);
    }

    public void print() {
        System.out.println("[Function]");
        System.out.println(String.format("[Function Signature] %s", functionSignature));
        System.out.println(String.format("[Return Type] %s", returnType));
        System.out.println("[Statements]");
        for (Statement s : statements)
            s.print(4);
        System.out.println("[End of Statements]");
        System.out.println("[End of Function]");
    }
}
