package main;

import exception.SyntaxException;
import token.ExpressionToken;
import token.Value;
import type.ValueType;

public class Main {

    public static void main(String[] args) throws SyntaxException {
        // TODO Auto-generated method stub

        Preprocessor input = new Preprocessor("input1.acg");
        Scanner scanner = new Scanner(input);
        scanner.print();
//        Parser parser=new Parser(scanner);

    }

}
