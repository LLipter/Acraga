package type;

import token.Value;

public class Casting {

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

    // cast value to a given data type
    public static Value casting(Value value, ValueType type) {
        if (from.asSameTypeas(to))
    }

}
