package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * OverdueNoticeTest
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW10
 * 04/10/2015
 */
public class OverdueNoticeTest {

	Patron Anne;
	Patron Dirk;
	Calendar calendar;
	Book book;
	Book book2;
	Library library;
	
	@Before
	public void setUp() throws Exception {
		calendar = new Calendar();
		library = new Library();
		book = new Book("ABC", "Laura");
        book2 = new Book("River", "David");
        Anne = new Patron("Anne", library);
        Dirk = new Patron("Dirk", library);
       
	}

	@Test
	public void testToString() {
		// check out the books and advance the day
		book.checkOut(calendar.getDate());
		Anne.take(book);
		calendar.advance();
		calendar.advance();
		book2.checkOut(calendar.getDate());
		Anne.take(book2);
		
		OverdueNotice notice = new OverdueNotice(Anne, calendar.getDate());
		String s = "Dear Anne:\nABC, by Laura will be due in 5 days.\nRiver, by David will be due in 7 days.\n";
		assertEquals(s, notice.toString());
		
		// after a week, a book is overdue while the other one is due today
		for (int i =0; i<7; i++) {
			calendar.advance();
		}
		String s2 = "Dear Anne:\nABC, by Laura is overdue.\nRiver, by David is due today.\n";
		OverdueNotice notice2 = new OverdueNotice(Anne, calendar.getDate());
		assertEquals(s2, notice2.toString());
		
		// two days later, both books are overdue
		calendar.advance();
		calendar.advance();
		
		String s3 = "Dear Anne:\nABC, by Laura is overdue.\nRiver, by David is overdue.\n";
		OverdueNotice notice3 = new OverdueNotice(Anne, calendar.getDate());
		assertEquals(s3, notice3.toString());
		
		// patron w/ no book
		OverdueNotice notice4 = new OverdueNotice(Dirk, calendar.getDate());
		assertEquals("", notice4.toString());
	}

}
