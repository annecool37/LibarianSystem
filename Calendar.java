package library;
/**
 * class Calendar
 * @author Chia-An Chen
 * CIT 590, Spring 2015, HW10
 * 04/10/2015
 */
public class Calendar {

	private int date;
	
	/**
	 * Constructor, set date to 0
	 */
	public Calendar() {
		this.date = 0;
	}
	
	/**
	 * get the current date
	 * @return
	 */
	public int getDate() {
		return this.date;
	}
	
	/**
	 * move ahead to the next day
	 */
	public void advance() {
		this.date ++;
	}


}
