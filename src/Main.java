
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		InputReader.initWithFilename("input1.acg");
		while(!InputReader.eof()) {
			int ch = InputReader.getChCur();
			System.out.println(ch);
			System.out.println((char)ch);
			InputReader.next();
		}
	}

}
