import acm.io.*;
import acm.program.*;
import acm.util.*;
import java.io.*;
import java.util.*;

/* Reads a list of exam scores from a file which contains one score per line,
 * and then displays a histogram of those numbers, divided into the ranges
 * 0-9, 10-19, 20-29, and so forth, up to the range containing only 100. */
public class MidtermScores extends ConsoleProgram {
	
	private static final int NUMBER_OF_RANGES = 11;
	private static final int BIN_SIZE = 10;
	
	/* Creates a BufferedReader, opening a file */
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
	
	private void addToArray(int index) {
		midtermScoreRanges[index]++;
	}
	
	/* Prints the range of scores before a number of stars */
	private void printRange(int whichRange, boolean isLast) {
		int startingNumber = whichRange * BIN_SIZE;
		int endingNumber = startingNumber + (BIN_SIZE - 1);
		
		String start = padWithZeros(startingNumber, 2);
		String end = padWithZeros(endingNumber, 2);
		
		if (!isLast) {
			print(start + "-" + end + ": ");
		}
		else {
			print(start + ": ");
		}
		
	}
	
	/* Takes an integer and pads it with zeros until it is the desired length,
	 * then returns it as a string */
	private String padWithZeros(int number, int desiredLength) {
		String numberString = Integer.toString(number);
		while (numberString.length() < desiredLength) {
			numberString = "0" + numberString;
		}
		return numberString;
	}
	
	/* Finds the number of stars to print and prints them for each range */
	private void findNumberOfStars() {
		int numberOfStars = 0;
		for (int i = 0; i < midtermScoreRanges.length; i++) {
			numberOfStars = midtermScoreRanges[i];
			boolean isLast = i == NUMBER_OF_RANGES - 1;
			printRange(i, isLast);
			printStars(numberOfStars);
		}
	}
	
	/* Actually prints out the stars for each range */
	private void printStars(int numberOfStars) {
		for (int i = 0; i < numberOfStars; i++) {
			print("*");
		}
		println("");
	}
	
	/* Goes through the exam scores line by line and counts how many are in each range */
	private void countExamScores(BufferedReader rd) {
		String line = "";
		int score = 0;
		int index = 0;
		while (true) {
			try {
				line = rd.readLine();
				if (line == null) return;
				score = Integer.parseInt(line);
				index = score / BIN_SIZE;
				addToArray(index);
				
			} catch (IOException ex) {
				throw new ErrorException(ex);
			}
		}
	}
	
	public void run() {
		BufferedReader rd = createBufferedReader("MidtermScores.txt");
		countExamScores(rd);
		findNumberOfStars();
	}

	/* Instance variables */
	int[] midtermScoreRanges = new int[NUMBER_OF_RANGES];
}
