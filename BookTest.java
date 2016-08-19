package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * BookTest
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW10
 * 04/10/2015
 */
public class BookTest {
	
	Book alice;
	Book pooh;

	@Before
	public void setUp() throws Exception {
		// create two books
		alice = new Book ("Alice in Wonder","Albert E. Donald");
		pooh = new Book ("Winnie the Pooh", "John Bear");
	}

//	@Test
//	public void testBook() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetTitle() {
		assertEquals("Alice in Wonder", alice.getTitle());
		assertEquals("Winnie the Pooh", pooh.getTitle());
	}

	@Test
	public void testGetAuthor() {
		assertEquals("Albert E. Donald", alice.getAuthor());
		assertEquals("John Bear", pooh.getAuthor());
	}

	@Test
	public void testGetDueDate() {
		// the due date is -1 if the book is not checked out.
		assertEquals(-1, alice.getDueDate());
		assertEquals(-1, pooh.getDueDate());
	}

	@Test
	public void testCheckOut() {
		alice.checkOut(5);
		pooh.checkOut(0);
		// Books are due one week (7 days) after being checked out–no renewals.
		assertEquals(12, alice.getDueDate());
		assertEquals(7, pooh.getDueDate());
	}

	@Test
	public void testCheckIn() {
		alice.checkIn();
		pooh.checkIn();
		assertEquals(-1, alice.getDueDate());
		assertEquals(-1, pooh.getDueDate());	
	}

	@Test
	public void testToString() {
		assertEquals("Alice in Wonder, by Albert E. Donald", alice.toString());
		assertEquals("Winnie the Pooh, by John Bear", pooh.toString());
	}

}
