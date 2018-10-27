package token;

public class ArrayId extends Identifier {
    private ExpressionToken index;

    public void setIndex(ExpressionToken ex){
        index=ex;
    }

    public ExpressionToken getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return String.format("<ArrayId,%s,%s>", id, index);
    }
}
