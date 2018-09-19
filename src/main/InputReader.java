package main;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class InputReader {
	
	private Reader reader;
	private int ch_cur;
	private int ch_next;
	private int line;
	private int pos;
	
	
	public int getChCur() {
		return ch_cur;
	}
	
	public int getChNext() {
		return ch_next;
	}

	public int getLine() {
		return line;
	}

	public int getPos() {
		return pos;
	}
	
	public InputReader(String inputFile) {
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
	
	
	public void close(){
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void next(){
		try {
			ch_cur = ch_next;
			ch_next = reader.read();
			pos++;
			
			// ignore all new lines
			while(ch_cur == '\n') {
				line++;
				pos = 0;
				next();
			}
			
			// ignore all white spaces
			while(isWhiteSpace())
				next();
			
			// ignore all comments
			if(ch_cur == '/' && ch_next == '/') { 
				while(ch_cur != '\n' && !iseof()) {
					ch_cur = ch_next;
					ch_next = reader.read();
				}
				if(ch_cur == '\n') {
					line++;
					pos = 0;
					next();
				}
					
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// eof stands for 'end of file'
	public boolean iseof() {
		return getChCur() == -1;
	}
	
	public boolean isDigit() {
		return Character.isDigit(getChCur());
	}
	
	public boolean isWhiteSpace() {
		if(getChCur() == ' ' || getChCur() == '\t')
			return true;
		return false;
	}
	
	

}
