package library;
import java.io.*;
import java.util.*;
/**
 * class Library
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW10
 * 04/10/2015
 */
public class Library {

	// variables for methods
	private boolean okToPrint;
	private HashMap<String, Patron> patronMap;
	private boolean isOpen; // if the library is open
	private boolean isEnd; // if the program is ended
	private ArrayList<Book> booksFound;
	private Patron patronServed;
	Calendar calendar;
	ArrayList<Book> collection;
	private String optionMsg;
	private int counter;

	/**
	 * This is the constructor that will be used by the main method, once, 
	 * to ”create the library”. It also sets a private instance variable okToPrint to true.
	 */
	public Library() {
		this.okToPrint = true;
		this.patronMap = new HashMap<String, Patron>();
		this.calendar = new Calendar();
		this.isOpen = false;
		this.collection = this.readBookCollection();
		this.isEnd = false;
	}

	/**
	 * To construct the library object, read in from file books.txt a list of title :: author lines,
	 * create a Book from each line, and save these in an ArrayList<Book>.
	 * @return
	 */
	private ArrayList<Book> readBookCollection() {
		File file = new File("books.txt");
		ArrayList<Book> collection = new ArrayList<Book>();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			while (true) {
				String line = reader.readLine();
				if (line == null) break;
				line = line.trim();
				if (line.equals("")) continue; // ignore possible blank lines
				String[] bookInfo = line.split(" :: ");
				collection.add(new Book(bookInfo[0], bookInfo[1]));
			} }
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return collection;
	}

	/**
	 * This is a second constructor, used by the test methods. 
	 * This constructor sets the private instance variable okToPrint to false
	 * @param collection
	 */
	public Library(ArrayList<Book> collection) {
		this.okToPrint = false;
		this.patronMap = new HashMap<String, Patron>();
		this.calendar = new Calendar();
		this.isOpen = false;
		this.collection= collection;
		this.isEnd = false;
	}

	/**
	 * This creates one Library object and calls its start method.
	 * @param args
	 */
	public static void main(String[] args) {
		Library library = new Library();
		library.start();
	}
	


	/**
	 * This method will read commands from the user, 
	 * such as open or search ”saga”, 
	 * call the corresponding method, and print the results. 
	 */
	public void start() {
		while (this.isEnd != true) {
			this.println("Hi! Good morning!\n\n"
					+ "Just a reminder: type in one number \nor number,number for multiple checkouts/checkins.\n\n"
					+ "Enter 'open' to start the system.");
			Scanner scanner = new Scanner(System.in);
			if (scanner.next().toLowerCase().equals("open")) {
				// Open the library in the morning. (This advances the date.)
				this.open();
				// this.println("System initializing\n..\n...\n....\n");
				
				// provide options for the librarian once per day
				this.optionMsg = "Enter 'issue' to issue a library card\n"
						+ "Enter 'serve' to serve a patron\n"
						+ "Enter 'checkIn' to check in books\n"
						+ "Enter 'search' to search for books\n"
						+ "Enter 'checkOut' to check out books\n"
						+ "Enter 'close' to close the library\n"
						+ "Enter 'quit' to Exit the program";
				this.println(this.optionMsg);
			}

			while (this.isOpen == true) {
				
				// print out the option again only if the librarian keeps entering the invalid input
				// prevent overprinting the command options
				if (this.counter > 3) {
					this.println(this.optionMsg);
					// reset counter
					this.counter = 0;
				}
			
				String option = scanner.next();
				//////////////////////////////////////
				//////// Issue a library card ////////
				//////////////////////////////////////
				if (option.toLowerCase().equals("issue")) {
					this.println("Please enter a name to issue a library card");
					String nameOfPatron = this.readLine();

					// check if the patron exist or not
					if (isPatronExist(nameOfPatron)) {
						this.println(nameOfPatron + " already had a library car.");
					}
					// else issue the card
					else {
						this.issueCard(nameOfPatron);
						this.println("Library card issued to " + nameOfPatron + ".\n");
					}
				}

				////////////////////////////////
				//////// serve a patron ////////
				////////////////////////////////
				else if (option.toLowerCase().equals("serve")) {
					this.println("Please enter the name of the patron");
					String name  = this.readLine();

					// check if Patron has a library card or not
					if (this.isPatronExist(name)) {
						this.patronServed = this.serve(name);
						this.println("Now " + this.patronServed.toString() + " is being served.");
						// Prints a numbered list of books currently checked out to this Patron.
						if (this.patronServed.getBooks().size()==0) {
							this.println(this.patronServed.toString() + " currently has no books.\n");
						}
						else {
							this.println(this.patronServed.toString() +"'s booklist:\n");
							this.nicePrinting(this.patronServed.getBooks());
						}
					}
					else {
						this.println( name + " does not have a library card yet.\nIssue him/her a card first to proceed.\n");
					}

				}

				////////////////////////////////
				//////// check in books ////////
				////////////////////////////////
				else if (option.toLowerCase().equals("checkin")) {
					ArrayList<Book> booksCheckedIn = null;

					// to check in, there must be a patron being served and he/she got book in the list
					if (this.patronServed == null) {
						this.println("please execute 'serve' to locate a patron in the system first.\n");
					}
					else if (this.patronServed.getBooks().size() == 0) {
						this.println(this.patronServed.toString() + " has no book to check in.\n");
					}
					else {
						this.println( this.patronServed.getName() + " has the following books:\n");
						this.nicePrinting(this.patronServed.getBooks());
						this.println("Which books do the patron want to return?");
						String bookNumber = scanner.next();

						// check input validity and turn string into integers
						int[] numbers = stringToInt(bookNumber);
						int idxLast = numbers.length - 1;

						if (numbers.length == 0) {
							this.println("input is invalid.\n");
							// break;
						}

						// check index not out of bound
						else if ( numbers[0] == 0  || numbers[idxLast] > patronServed.getBooks().size()) {
							this.println("input is invalid.\n");
						}

						else {
							// check in books
							booksCheckedIn = this.checkIn(numbers);
							if (booksCheckedIn.size()==0) {
								this.println("No books are being checked in.");
							}
							else {
								// print list of books just being checked in
								this.println("The books checked in:\n");
								this.nicePrinting(booksCheckedIn);
							}
						}
					}
				}

				//////////////////////////////
				//////// search books ////////
				//////////////////////////////
				else if (option.toLowerCase().equals("search")) {
					// search book
					this.println("Please type in the keyword (at least 4 characters)");
					String keyword = this.readLine();
					this.booksFound = this.search(keyword);
				}

				/////////////////////////////////
				//////// check out books ////////
				/////////////////////////////////
				else if (option.toLowerCase().equals("checkout")) {
					ArrayList<Book> booksCheckedOut = null;

					// to check out books, there must a patron to serve
					if (this.patronServed == null) {
						this.println("please execute 'serve' to locate a patron in the system first.\n");
					}

					// one can only checkout 3 books at most
					else if (this.patronServed.getBooks().size() ==3 ) {
						this.println("Sorry, one can only check out 3 books at most at a time.\n" 
								+ "And " + this.patronServed.toString() + " has 3 books already" );	
					}

					// have to conduct search first
					else if (this.booksFound == null || this.booksFound.size() ==0) {
						this.println("please execute 'search' to find the books of interest first.\n");
					}
					
					// proceed to checkout
					else {
						this.println("The followings are the books found:\n");
						this.nicePrinting(this.booksFound);
						this.println("Which books do "+ this.patronServed.toString() +" want to check out?");
						String bookNumber = scanner.next();

						// check input validity and turn string into integers
						int[] numbers = stringToInt(bookNumber);
						int idxLast = numbers.length - 1;
						int criteria = 3 - this.patronServed.getBooks().size();
						int numElement = noOfNonZeroElement(numbers);
						
						if (numbers.length == 0) {
							this.println("input is invalid.\n");
						}

						// check index not out of bound
						else if ( numbers[0] == 0  || numbers[idxLast] > this.booksFound.size()) {
							this.println("input is invalid.\n");
						}
						
						// check if the patron can take this many books
						else if ( numElement > criteria ) {
							this.println("Sorry, but " + this.patronServed.toString() + " can only checkout " + criteria + " books more.");	
						}

						else {
							// check out books
							booksCheckedOut = this.checkOut(numbers);
							if (booksCheckedOut.size() == 0) {
								this.println("no books are being checked out.");
							}
							else {
								this.println("Books checked out by "+ this.patronServed.toString()+":\n");
								this.nicePrinting(booksCheckedOut);
								// reset the search result since this.collection is being updated
								this.booksFound = new ArrayList<Book>();
							}
						}
					}
				}

				//////////////////////////////////////////////////
				//////// close the system, end of the day ////////
				//////////////////////////////////////////////////
				else if (option.toLowerCase().equals("close")) {
					this.close();
				}

				///////////////////////////////////////
				//////// shut down the library ////////
				///////////////////////////////////////
				else if (option.toLowerCase().equals("quit")) {
					this.isOpen = false;
					this.quit();
				}

				// invalid input //
				else {
					this.println("invalid cammand...\n");
					counter ++;
				}
				this.println("Please enter the next command");
			}
		}
	}	
	
	/**
	 * return the number of non-zero element
	 * @param numArray
	 * @return
	 */
	public int noOfNonZeroElement(int... numArray) {
		int count = 0;
		for (int num : numArray) {
			if (num != 0 ){
				count ++;
			}
			else {
				// since those pass in would be numbers follows by all zeros
				// break at the first 0 is fine
				break;
			}
		}
		return count;
	}
	

	/**
	 * If the instance variable okToPrint is true, prints the message
	 * @param message
	 */
	public void print(String message) {
		if (okToPrint == true) {
			System.out.print(message);
		}
	}

	/**
	 * If the instance variable okToPrint is true, prints the message 
	 * @param message
	 */
	public void println(String message) {
		if (okToPrint == true) {
			System.out.println(message);
		}

	}

	/**
	 * the function starts the day and sends overdue notices
	 * @return
	 */
	public ArrayList<OverdueNotice> open() {
		// Starts the day
		this.calendar.advance();
		// indicate that the library is now open
		this.isOpen = true;
		// reset the variables into null
		this.patronServed = null;
		this.booksFound = null;
		// sends overdue notices
		return createOverdueNotices();
	}


	/**
	 * Checks each Patron to see whether he/she has books which were due yesterday 
	 * @return
	 */
	public ArrayList<OverdueNotice> createOverdueNotices() {
		boolean isOverdue; 
		Collection<Patron> patrons= this.patronMap.values();
		ArrayList<OverdueNotice> notices = new ArrayList<OverdueNotice>();
		for (Patron patron : patrons) {
			isOverdue = false;
			ArrayList<Book> bookList = patron.getBooks();
			if (bookList.size() !=0) { 
				for (Book book : bookList) {
					if (book.getDueDate() - this.calendar.getDate()  == -1) {
						isOverdue = true;
					}
				}
			}
			if (isOverdue == true) {
				OverdueNotice overdues = new OverdueNotice(patron, this.calendar.getDate());
				notices.add(overdues);
			}
		}
		// print the overdue notices if it's not an empty list
		if (notices.size() ==0) {
			this.println("No overdue notice is being sent today.\n");
		}
		else {
			this.println("Sending the overdue notices state as the followings:\n");
			for (OverdueNotice notice : notices) {
				this.println(notice.toString());
			}
		}
		return notices;
	}

	/**
	 * Issues a library card to the person with this name.
	 * @param nameOfPatron
	 * @return
	 */
	public Patron issueCard(String nameOfPatron) {
		// creates a Patron object
		Patron newPatron = new Patron(nameOfPatron, this);
		// saves it as the value in a HashMap, with the patron’s name as the key. 
		this.patronMap.put(nameOfPatron, newPatron);
		// No patron can have more than one library card.
		// this criteria is inherited in HashMap
		return newPatron;
	}
	
	/**
	 * check if the patron exist
	 * case sensitive!
	 * @param nameOfPatron
	 * @return
	 */
	public boolean isPatronExist(String nameOfPatron) {
		boolean isExist = false;
		if (this.patronMap.containsKey(nameOfPatron)) {
			isExist = true;
		}
		return isExist;
	}
	

	/**
	 * Begin checking books out to (or in from) the named patron.
	 * @param nameOfPatron
	 * @return
	 */
	public Patron serve(String nameOfPatron) {
		// look up the patron’s name in the HashMap
		// save the returned Patron object in an instance variable of this Library
		this.patronServed = this.patronMap.get(nameOfPatron);
		return this.patronServed;
	}

	/**
	 * Prints out, and saves in an instance variable, 
	 * an ArrayList<Book> of books whose title or author (or both) contains this string.
	 * @param part
	 * @return
	 */
	public ArrayList<Book> search(String part) {
		this.booksFound = new ArrayList<Book>();

		// should search > 4 characters
		if (part.length() < 4) {
			this.println("The keyword should consist at least 4 characters.\n");
			return this.booksFound;
		}

		// search in collection
		for (Book book : this.collection) {
			if (book.toString().toLowerCase().contains(part.toLowerCase())) {
				this.booksFound.add(book);
			}
		}	

		// remove duplicates (assuming there's at most 6 duplicates of a book)
		for (int j = 0; j < 3; j++) {
			for ( int i = 0; i < this.booksFound.size(); i++) {
				// prevent index out of bound
				if ( i == this.booksFound.size() -1) {
					break;
				}
				// remove identical books
				else if (this.booksFound.get(i+1).toString().equals(this.booksFound.get(i).toString())) {
					this.booksFound.remove(this.booksFound.get(i));
				}
			}
		}

		// print the list of books found
		if (this.booksFound.size() == 0) {
			this.println("No books are found.\n");
		}
		else {
			this.println("List of the books found: ");
			this.nicePrinting(this.booksFound);
		}
		return this.booksFound;
	}

	/**
	 * print numbered list of books nicely
	 * @param bookList
	 */
	public void nicePrinting(ArrayList<Book> bookList) {
		for (int i=0; i < bookList.size(); i++) {
			this.println(String.valueOf(i+1) + " : " + bookList.get(i));
		}
		this.println("");
	}

	/**
	 * The listed books are being returned by the current Patron
	 * @param bookNumbers
	 * @return
	 */
	public ArrayList<Book> checkIn(int... bookNumbers) {
		ArrayList<Book> patronsBooks = this.patronServed.getBooks();
		ArrayList<Book> booksToCheckIn = new ArrayList<Book>();
		for (int num : bookNumbers) {
			if (num == 0) {
				// since 0 is the default integer in bookNumbers
				break;
			}
			else if (num > patronsBooks.size()) {
				// prevent index out bound
				this.println(num +" is not listed above.");
				break;
			}
			else {
				Book bookReturned = patronsBooks.get(num-1);
				booksToCheckIn.add(bookReturned);
			}
		}

		// wrote a extra outer loop to prevent varying patronsBooks.size() during check in
		for (Book book : booksToCheckIn) {
			// patron's giving back the book
			this.patronServed.giveBack(book);
			// check in books
			book.checkIn();
		}

		// put the check-in books back to library collection
		this.collection.addAll(booksToCheckIn);
		return booksToCheckIn;
	}


	/**
	 * Either checks out the book to the Patron currently being served (there must be one!), 
	 * or tells why the operation is not permitted.
	 * @param bookNumbers
	 * @return
	 */
	public ArrayList<Book> checkOut(int... bookNumbers) {
		ArrayList<Book> booksToCheckOut = new ArrayList<Book>();

		if (this.booksFound.size() ==0) {
			this.println("Sorry, those books are out of stock. Try another search");
			return booksToCheckOut;	
		}

		// check out books
		for (int num : bookNumbers) {
			if (num == 0 ) {
				// since 0 is the default integer in bookNumbers
				break;
			}
			else if (num > this.booksFound.size()) {
				// prevent index out bound
				this.println(num + " is not listed above.");
				break;
			}
			else {
				Book bookToCheckOut = this.booksFound.get(num-1);
				bookToCheckOut.checkOut(this.calendar.getDate());
				this.patronServed.take(bookToCheckOut);
				booksToCheckOut.add(bookToCheckOut);
			}
		}
		// remove list of checkout books form library collection
		this.collection.removeAll(booksToCheckOut);
		return booksToCheckOut;
	}

	/**
	 * Shut down operations and go home for the night.
	 */
	public void close() {
		this.isOpen = false;
		this.println("See you tomorrow!\n..\n....\n......\n");
	}

	/**
	 * End the program.
	 */
	public void quit() {
		this.isEnd = true;
		this.println("The mayor, citing a budget crisis, has stopped all funding for the library\n..\n...\n......LIBRARY SHUTS DOWN!");		
	}

	/**
	 * read the full line but not just the next word
	 * @return
	 */
	public String readLine() {
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}
	
	/**
	 * check if the string passed in contain integers only
	 * @param inputString
	 * @return
	 */
	public boolean checkStringValid (String inputString) {
		boolean isValid = true;
		String[] string = {};
		// check input validity
		if (inputString.length() > 2 ) {
			string = inputString.split(",");
			for (int i = 0; i < string.length; i++) {
				try {
					Integer.parseInt(string[i]);
				}
				catch (NumberFormatException e) {
					this.println("input can only contain numbers\n");
					isValid = false;
				}				
			}
		}
		else {
			try {
				Integer.parseInt(inputString);
			}
			catch (NumberFormatException e) {
				this.println("input can only contain numbers\n");
				isValid = false;
			}

		}
		return isValid;
	}

	/**
	 * turn string passed in into integer array
	 * @param inputString
	 * @return
	 */
	public int[] stringToInt(String inputString) {
		// proceed only if the the passed in string is a valid one (only numbers)
		boolean isValid = checkStringValid(inputString);
		int[] numArray =  new int[20]; // assuming a max display of 20 books
		String[] string = {};
		if (isValid == true) {
			// process input
			if (inputString.length() > 2 ) {
				string = inputString.split(",");
				for (int i = 0; i < string.length; i++) {
					int num = Integer.parseInt(string[i]);
					numArray[i] = num;
				}
			}
			else {
				// if there's single number being passed in
				int num = Integer.parseInt(inputString);
				numArray[0] = num;
			}
		}
		return numArray;
	}

}
