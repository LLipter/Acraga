import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class InputReader {
	
	private static Reader reader ;
	private static int ch_cur;
	private static int ch_next;
	private static int line;
	private static int pos;
	
	
	public static int getChCur() {
		return ch_cur;
	}
	
	public static int getChNext() {
		return ch_next;
	}

	public static int getLine() {
		return line;
	}

	public static int getPos() {
		return pos;
	}
	
	public static void initWithFilename(String inputFile){
		try {
			reader = new InputStreamReader(new FileInputStream(inputFile));
			ch_cur = -1;
			ch_next = -1;
			line = 1;
			pos = 0;
			next();
			next();
		} catch (FileNotFoundException e) {
			System.err.println("no such file as " + inputFile);
		}
	}
	
	public static void close(){
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void next(){
		try {
			if(ch_cur == '\n') {
				line++;
				pos = 0;
			}
			ch_cur = ch_next;
			ch_next = reader.read();
			pos++;
			
			if(ch_cur == '/' && ch_next == '/') { // ignore all comments
				while(ch_cur != '\n' && !eof())
					next();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// eof stands for 'end of file'
	public static boolean eof() {
		return InputReader.getChCur() == -1;
	}

}
