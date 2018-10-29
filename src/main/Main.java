package main;

import exception.RTException;
import exception.SyntaxException;

public class Main {

    public static void main(String[] args) throws SyntaxException, RTException {
        // TODO Auto-generated method stub

        Preprocessor input = new Preprocessor("input1.acg");
        Scanner scanner = new Scanner(input);
//        scanner.print();
        Parser parser = new Parser(scanner);
        parser.print();
        Interpreter interpreter = new Interpreter(parser);

    }

}
