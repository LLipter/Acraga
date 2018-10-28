package main;

import exception.Syntax;

public class Main {

    public static void main(String[] args) throws Syntax {
        // TODO Auto-generated method stub

        Preprocessor input = new Preprocessor("input1.acg");
        Scanner scanner = new Scanner(input);
        scanner.print();
        Parser parser=new Parser(scanner);

    }

}
