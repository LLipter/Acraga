package main;

import exception.AcragaException;
import token.Token;
import java.util.Stack;

public class Interpreter {
	private InputReader input;
	private AcragaException error;
	
	public Interpreter(InputReader inputReader) {

	}
	
	public int run() {
//		Token result = expression();
//		ReturnValue ret = result.run();
//		if(ret.isDouble()) {
//			System.out.println(ret.getDoubleValue());
//		}else if(ret.isInt()){
//			System.out.println(ret.getIntValue());
//		}else if(ret.isError()) {
//			error.runtime(ret.getErrorMsg());
//		}else {
//			error.runtime("unknow return type");
//		}
		
		// return code
		// 0 represents the program ends normally without errors
		return 0;
	}
	
	private Token expression() {

		
		
		return null;
	}
}
