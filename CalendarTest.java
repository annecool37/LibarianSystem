package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * CalendarTest
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW10
 * 04/10/2015
 */
public class CalendarTest {
    Calendar cal;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        cal = new Calendar();
    }

    /**
     * Test method for {@link library.Calendar#getDate()}
     * and {@link library.Calendar#advance()}.
     */
    @Test
    public void testCalendar() {
        assertEquals(0, cal.getDate());
        cal.advance();
        assertEquals(1, cal.getDate());
        cal.advance();
        assertEquals(2, cal.getDate());
    }
}
