package library;
import static org.junit.Assert.*;
import org.junit.*;
/**
 * PatronTest
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW10
 * 04/10/2015
 */
public class PatronTest {
    private Patron dave;
    private Patron paula;
    private Book book;
    private Book book2;
    private Book book3;
    private Book book4;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        dave = new Patron("Dave", null);
        paula = new Patron("Paula", null);
        book = new Book("Disappearing Nightly", "Laura Resnick");
        book2 = new Book("Divergent", "David");
    	book3 = new Book("Sciences", "Dr.Auman");
    	book4 = new Book("Woolala", "Mary");
    	
    }

    /**
     * Test method for {@link library.Patron#Patron(java.lang.String, library.Library)}.
     */
    @Test
    public void testPatron() {
        Patron paula = new Patron("Paula", null);
        assertTrue(paula instanceof Patron);
    }

    /**
     * Test method for {@link library.Patron#getName()}.
     */
    @Test
    public void testGetName() {
        assertEquals("Dave", dave.getName());
        assertEquals("Paula", paula.getName());
    }

    /**
     * Test method for {@link library.Patron#take(library.Book)}.
     */
    @Test
    public void testTake() {
        paula.take(book);
        assertTrue(paula.getBooks().contains(book));
        assertFalse(dave.getBooks().contains(book));
    }

    /**
     * Test method for {@link library.Patron#giveBack(library.Book)}.
     */
    @Test
    public void testGiveBack() {
        paula.take(book);
        assertTrue(paula.getBooks().contains(book));
        paula.giveBack(book);
        assertFalse(paula.getBooks().contains(book));
    }

    /**
     * Test method for {@link library.Patron#getBooks()}.
     */
    @Test
    public void testGetBooks() {
        dave.take(book);
        assertTrue(dave.getBooks().contains(book));
        assertEquals(1, dave.getBooks().size());
        dave.take(book2);
        dave.take(book3);
        dave.take(book4);
        assertTrue(dave.getBooks().contains(book2));
        assertTrue(dave.getBooks().contains(book3));
        assertFalse(dave.getBooks().contains(book4)); // can only check out 3 books at a time
        assertEquals(3, dave.getBooks().size());
    }

    /**
     * Test method for {@link library.Patron#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals("Dave", dave.toString());
        assertEquals("Paula", paula.toString());
    }

}
