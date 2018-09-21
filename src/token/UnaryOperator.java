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

//	@Override
//	public ReturnValue run() {
//		
//		ReturnValue subValue = operand1.run();
//		ReturnValue retValue;
//		
//		
//		
//		if(subValue.isDouble()) {
//			retValue = new ReturnValue(ValueType.RuntimeError);
//			String msg = String.format("%s Operator cannot receive a literal double value", operatorType);
//			retValue.setErrorMsg(msg);
//		}else if(subValue.isInt()) {
//			retValue = new ReturnValue(ValueType.Integer);
//			retValue.setIntValue(~subValue.getIntValue());
//		}
//		else if(subValue.isIdentifier()) {
//			// TODO:
//			retValue = new ReturnValue(ValueType.Double);
//		}else if(subValue.isError()) {
//			return subValue;
//		}else {
//			retValue = new ReturnValue(ValueType.RuntimeError);
//			String msg = String.format("Unknown return value");
//			retValue.setErrorMsg(msg);
//		}
//		
//		return retValue;
//	}

}
