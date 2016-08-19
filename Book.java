package library;
/**
 * class Book
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW10
 * 04/10/2015
 */
public class Book {

	private String title;
	private String author; 
	int dueDate = -1;
	
	/**
	 * The constructor. Saves the provided information. 
	 * When created, this book is not checked out.
	 * @param title
	 * @param author
	 */
	public Book(String title, String author) {
		this.title=title;
		this.author=author;
	}

	/**
	 * Returns this book’s title.
	 * @return
	 */
	public String getTitle() {
		return this.title;	
	}
	
	/**
	 * Returns this book’s author.
	 * @return
	 */
	public String getAuthor() {
		return this.author;	
	}
	
	/**
	 * Returns the date (as an integer) that this book is due.
	 * @return
	 */
	public int getDueDate() {
		return this.dueDate;	
	}
	
	/**
	 * Sets the due date of this Book to the specified date. 
	 * a book is due after 7 days upon checkout
	 * @param date
	 */
	public void checkOut(int date) {
		this.dueDate = date + 7;		
	}
	
	/**
	 * Sets the due date of this Book to -1.
	 */
	public void checkIn() {
		this.dueDate = -1;
	}
	
	/**
	 * Returns a string of the form title, by author.
	 */
	public String toString() {
		return this.title + ", by " + this.author;
	}
}
