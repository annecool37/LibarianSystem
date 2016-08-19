package library;
import java.util.*;
/**
 * class OverdueNotice
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW10
 * 04/10/2015
 */
public class OverdueNotice {

	private Patron patron;
	private int todaysDate;
	
	public OverdueNotice(Patron patron, int todaysDate) {
		this.patron = patron;
		this.todaysDate = todaysDate;
	}

	public String toString() {
		String s ="";
		ArrayList<Book> bookList = this.patron.getBooks();
		// enter the loop if the patron had checkOut books
		if (bookList.size() != 0) { 
			for (Book book : bookList) {
				s += book.toString() ; 
				int dueDate = book.getDueDate();
				int daysDifference = dueDate - todaysDate;
				if (daysDifference < 0) {
					s += " is overdue.\n";
				}
				else if (daysDifference == 0) {
					s += " is due today.\n";
				}
				else {
					s += " will be due in " + daysDifference +" days.\n";	
				}	
			}
			return "Dear " + this.patron.getName() + ":\n" + s;
		}
		else { // patron w/o a book --> return an empty string
			return s;
		}
	}

}
