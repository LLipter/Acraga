package main;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;

public class InputReader {
	
	private LinkedList<Integer> buffer;
	private int line;
	private int pos;
	

	public int getLine() {
		return line;
	}

	public int getPos() {
		return pos;
	}
	
	public InputReader(String inputFile) {
		try {
			Reader reader = new InputStreamReader(new FileInputStream(inputFile));
			buffer = new LinkedList<Integer>();
			line = 1;
			pos = 0;
			
			int ch_cur = reader.read();
			int ch_next = reader.read();
			while(ch_cur != -1) {
				// ignore all comments
				if(ch_cur == '/' && ch_next == '/') {
					while(ch_cur != '\n' && ch_cur != -1) {
						ch_cur = ch_next;
						ch_next = reader.read();
					}
					if(ch_cur == -1)
						return;
				}
				buffer.addLast(ch_cur);
				ch_cur = ch_next;
				ch_next = reader.read();
			}
			reader.close();
			
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	public boolean iseof() {
		return buffer.isEmpty();
	}
	
	public boolean isWhiteSpace() {
		return getCh() == ' ' || getCh() == '\t' || getCh() == '\n';
	}
	
	
	public void next() {
		if(iseof())
			return;
		int ch = buffer.pollFirst();
		if(ch == '\n') {
			line++;
			pos = 0;
		}
		pos++;
	}
	
	public int getCh() {
		if(iseof())
			return -1;
		return buffer.getFirst();
	}
	
	public void nextNotWhiteSpace() {
		while(isWhiteSpace() && !iseof())
			next();
	}
	
	


}
