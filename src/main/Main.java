package main;

import node.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		InputReader input = new InputReader("input1.acg");
		AcragaDecimalInteger integer = new AcragaDecimalInteger(input);
		System.out.println(integer.getValue());
	}

}
