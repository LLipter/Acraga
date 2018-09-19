
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		InputReader.initWithFilename("input1.acg");
		while(!InputReader.eof()) {
			System.out.println(InputReader.getChCur());
			InputReader.next();
		}
	}

}
