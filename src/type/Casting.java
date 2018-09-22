package type;

public class Casting {
	
	public static ValueType keywordType2ValueType (KeywordType type) {
		if(type == KeywordType.INT)
			return ValueType.INTEGER;
		else if(type == KeywordType.DOUBLE)
			return ValueType.DOUBLE;
		else if(type == KeywordType.STRING)
			return ValueType.STRING;
		else if(type == KeywordType.BOOL)
			return ValueType.BOOLEAN;
		else 
			return null;
	}

}
