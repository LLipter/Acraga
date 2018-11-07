package main;

import component.ReturnValue;
import exception.AcragaException;

public class Acraga {

    public static void main(String[] args) throws AcragaException, ReturnValue {
        // TODO Auto-generated method stub

        // test for interpret the whole program
//        Preprocessor input = new Preprocessor("input1.acg");
//        Scanner scanner = new Scanner(input);
//        scanner.print();
//        Parser parser = new Parser(scanner);
//        parser.parseProgram();
//        parser.print();
//        Interpreter interpreter = new Interpreter(parser);
//        interpreter.interpretProgram();

        // simple way to do it
        Interpreter.interpretProgram("input2.acg");


        // test for interpret expression
        //Value value = Interpreter.interpretExpression("(1+3)*7");
        //System.out.println(value);

    }

}
