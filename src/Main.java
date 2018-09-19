
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		InputReader input = new InputReader("input1.acg");
		while(!input.eof()) {
			int ch = input.getChCur();
			System.out.println(ch);
			System.out.println((char)ch);
			input.next();
		}
	}

}
