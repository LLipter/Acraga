package token;

public class ArrayId extends Identifier {
    private String aid;
    private ExpressionToken index;

    public ArrayId(String arrayId){
        aid=arrayId;
    }

    public void setIndex(ExpressionToken ex){
        index=ex;
    }

    public ExpressionToken getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return String.format("<ArrayId,%s>", aid);
    }
}
