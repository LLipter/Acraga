package type;

import token.exprtoken.Value;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Casting {

    private Casting() {
    }

    public static ValueType keywordType2ValueType(KeywordType type) {
        if (type == KeywordType.INT)
            return ValueType.INTEGER;
        else if (type == KeywordType.DOUBLE)
            return ValueType.DOUBLE;
        else if (type == KeywordType.STRING)
            return ValueType.STRING;
        else if (type == KeywordType.BOOL)
            return ValueType.BOOLEAN;
        else if (type == KeywordType.VOID)
            return ValueType.VOID;
        else
            return null;
    }

    public static Value safeCasting(Value value, ValueType type) {
        if (value.getValueType() == type)
            return new Value(value);
        if (value.isInt() && type == ValueType.DOUBLE) {
            Value ret = new Value(ValueType.DOUBLE);
            ret.setDoubleValue(new BigDecimal(value.getIntValue()));
        }
        return null;
    }

    // cast value to a given data type
    public static Value casting(Value value, ValueType type) {
        if (value.getValueType() == type)
            return new Value(value);
        if (type == ValueType.VOID)
            return new Value(ValueType.VOID);
        Value ret = null;
        switch (type) {
            case INTEGER:
                ret = new Value(ValueType.INTEGER);
                switch (value.getValueType()) {
                    case DOUBLE:
                        ret.setIntValue(value.getDoubleValue().toBigInteger());
                        break;
                    case BOOLEAN:
                        if (value.getBoolValue())
                            ret.setIntValue(BigInteger.ONE);
                        else
                            ret.setIntValue(BigInteger.ZERO);
                        break;
                    case STRING:
                        try {
                            BigInteger bigValue = new BigInteger(value.getStringValue());
                            ret.setIntValue(bigValue);
                        } catch (NumberFormatException e) {
                            return null;
                        }
                        break;
                    default:
                        System.err.println("unknown data type in the process of casting");
                        return null;
                }
                break;
            case DOUBLE:
                ret = new Value(ValueType.DOUBLE);
                switch (value.getValueType()) {
                    case INTEGER:
                        ret.setDoubleValue(new BigDecimal(value.getIntValue()));
                        break;
                    case BOOLEAN:
                        if (value.getBoolValue())
                            ret.setDoubleValue(BigDecimal.ONE);
                        else
                            ret.setDoubleValue(BigDecimal.ZERO);
                        break;
                    case STRING:
                        try {
                            BigDecimal bigValue = new BigDecimal(value.getStringValue());
                            ret.setDoubleValue(bigValue);
                        } catch (NumberFormatException e) {
                            return null;
                        }
                        break;
                    default:
                        System.err.println("unknown data type in the process of casting");
                        return null;
                }
                break;
            case BOOLEAN:
                ret = new Value(ValueType.BOOLEAN);
                switch (value.getValueType()) {
                    case INTEGER:
                        if (value.getIntValue().compareTo(BigInteger.ZERO) != 0)
                            ret.setBoolValue(true);
                        else
                            ret.setBoolValue(false);
                        break;
                    case DOUBLE:
                        if (value.getDoubleValue().compareTo(BigDecimal.ZERO) != 0)
                            ret.setBoolValue(true);
                        else
                            ret.setBoolValue(false);
                        break;
                    case STRING:
                        if (value.getStringValue().length() != 0)
                            ret.setBoolValue(true);
                        else
                            ret.setBoolValue(false);
                        break;
                    default:
                        System.err.println("unknown data type in the process of casting");
                        return null;
                }
                break;
            case STRING:
                ret = new Value(ValueType.STRING);
                switch (value.getValueType()) {
                    case INTEGER:
                        ret.setStringValue(value.getIntValue().toString());
                        break;
                    case DOUBLE:
                        ret.setStringValue(value.getDoubleValue().toString());
                        break;
                    case BOOLEAN:
                        Boolean tmp = value.getBoolValue();
                        ret.setStringValue(tmp.toString());
                        break;
                    default:
                        System.err.println("unknown data type in the process of casting");
                        return null;
                }
                break;
        }
        if (ret != null) {
            ret.setLines(value.getLines());
            ret.setPos(value.getPos());
        }
        return ret;
    }

}
