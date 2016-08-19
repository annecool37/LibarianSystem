package library;
import java.util.*;
/**
 * class Patron
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW10
 * 04/10/2015
 */
public class Patron {
	
	private String name;
	private Library library;
	private ArrayList<Book> patronsBooks;
	
	public Patron(String name, Library library) {
		this.name = name;
		this.library = library;
		patronsBooks = new ArrayList<Book>();
	}
	
	/**
	 * Returns this patron’s name.
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Adds this book to the list of books checked out by this Patron.
	 * limited to 3 books at a time
	 * @param book
	 */
	public void take(Book book) {
		if (this.patronsBooks.size() < 3 ) {
		this.patronsBooks.add(book);	
		}
	}
	
	/**
	 * Removes this Book object from the list of books checked out by this Patron.
	 * @param book
	 */
	public void giveBack(Book book) {
		this.patronsBooks.remove(book);
	}
	
	/**
	 * Returns the list of Book objects checked out to this Patron.
	 * @return
	 */
	public ArrayList<Book> getBooks() {
		return this.patronsBooks;
	}
	
	/**
	 * Returns this patron’s name.
	 */
	public String toString() {
		return this.name;
	}
}
