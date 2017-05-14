import acm.io.*;
import acm.program.*;
import acm.util.*;
import java.io.*;
import java.util.*;

/* Reads a file and reports how many lines, words and characters appear in it. */
public class WordCount extends ConsoleProgram {
	
	private BufferedReader createBufferedReader(String file) {
		BufferedReader rd = null;
		while (rd == null) {
			try {
				rd = new BufferedReader(new FileReader(file));
			} catch (IOException ex) {
				System.out.println("Bad file name");
			}
		}
		return rd;
	}
	
	/* Reads through the file and tells you how many lines, chars and words are in it */
	private void countWordsLinesChars(BufferedReader rd) {
		String line = "";
		
		try {
			while (true) {
				line = rd.readLine();
				if (line == null) break;
				numberOfLines++;
				countWords(line);
				numberOfChars += line.length();
			}
			rd.close();
		} catch (IOException ex) {
				throw new ErrorException(ex);
		}
	}
	
	/* Counts the number of words in the file */
	private void countWords(String line) {
		boolean inWord = false;
		
		for (int i = 0; i < line.length(); i++) {
			if (Character.isLetterOrDigit(line.charAt(i))) {
				inWord = true;
			} else {
				if (inWord) numberOfWords++;
				inWord = false;
			} 
		}
		if (inWord) numberOfWords++;
	}
	
	public void run() {
		String file = readLine("File: ");
		BufferedReader rd = createBufferedReader(file);
		countWordsLinesChars(rd);
		println("Lines = " + numberOfLines);
		println("Words = " + numberOfWords);
		println("Chars = " + numberOfChars);
	}

	/* Instance variables */
	int numberOfWords = 0;
	int numberOfLines = 0;
	int numberOfChars = 0;
}
