package main;

import exception.SyntaxException;

public class Main {

    public static void main(String[] args) throws SyntaxException {
        // TODO Auto-generated method stub

        InputReader input = new InputReader("input1.acg");
        Scanner scanner = new Scanner(input);
        scanner.print();
        Parser parser=new Parser(scanner);
    }

}
