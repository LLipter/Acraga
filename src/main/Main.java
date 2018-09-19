package main;

import node.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		InputReader input = new InputReader("input1.acg");
		while(!input.iseof()) {
			System.out.print((char)input.getChCur());
			input.next();
		}
	}

}
