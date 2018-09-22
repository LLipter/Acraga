package token;

import type.OperatorType;
import type.ValueType;

public class UnaryOperator extends Operator {
	
	private Token operand1;
	
	public UnaryOperator(OperatorType type) {
		super();
		operatorType = type;
	}
	
	public void setOp1(Token op1) {
		operand1 = op1;
	}
	
	@Override
	public String toString() {
		return String.format("<UnaryOperator,%s>", operatorType);
	}

}
