package token;

public class UnaryOperator extends Operator {
	
	private Token operand1;
	
	public UnaryOperator(OperatorType type) {
		operatorType = type;
	}
	
	public void setOp1(Token op1) {
		operand1 = op1;
	}

	@Override
	ReturnValue run() {
		// TODO Auto-generated method stub
		return null;
	}

}
