import acm.io.*;
import acm.program.*;
import acm.util.*;
import java.io.*;
import java.util.*;

/* Asks the user for a list of names (one per line) until the user
 * enters a blank line. At that point the program prints out the names entered,
 * where each name is listed only once, no matter how many times
 * the user entered that name into the program. */
public class UniqueNames extends ConsoleProgram {
	
	/* Checks if the list contains the name already,
	 * and if not, adds the name to the list */
	private void addNameToList(String name) {
		if (!nameList.contains(name)) {
			nameList.add(name);
		}
	}
	
	private void printNames() {
		println("Unique name list contains:");
		for (int i = 0; i < nameList.size(); i++) {
			println(nameList.get(i));
		}
	}
	
	public void run() {
		while (true){
			String name = readLine("Enter name: ");
			if (name.equals("")) break;
			addNameToList(name);
		}
		printNames();
	}

	/* Instance variables */
	ArrayList<String> nameList = new ArrayList<String>();
}
