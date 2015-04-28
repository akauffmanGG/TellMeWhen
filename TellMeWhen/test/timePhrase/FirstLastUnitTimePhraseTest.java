package timePhrase;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

/**
 * Tests the {@link FirstLastUnitTimePhrase} class.
 * 
 * @author akauffman
 *
 */
public class FirstLastUnitTimePhraseTest {

	/**
	 * Tests that various qualifiers match the phrase.
	 */
	@Test
	public void testQualifierMatches() {
		final FirstLastUnitTimePhrase timePhrase = new FirstLastUnitTimePhrase();
		assertTrue(timePhrase.matches("FIRST DAY OF YEAR"));
		assertTrue(timePhrase.matches("LAST DAY OF YEAR"));
		assertTrue(timePhrase.matches("FIRST DAY IN YEAR"));
		assertTrue(timePhrase.matches("FIRST DAY IN THE YEAR"));
		assertTrue(timePhrase.matches("FIRST DAY OF THE YEAR"));
	}
	
	/**
	 * Tests that time units match the phrase.
	 */
	@Test
	public void testTimeUnitMatches() {
		final FirstLastUnitTimePhrase timePhrase = new FirstLastUnitTimePhrase();
		assertTrue(timePhrase.matches("FIRST MILLISECOND OF YEAR"));
		assertTrue(timePhrase.matches("FIRST SECOND OF YEAR"));
		assertTrue(timePhrase.matches("FIRST MINUTE OF YEAR"));
		assertTrue(timePhrase.matches("FIRST HOUR OF YEAR"));
		assertTrue(timePhrase.matches("FIRST DAY OF YEAR"));
		assertTrue(timePhrase.matches("FIRST MONTH OF YEAR"));
		assertFalse(timePhrase.matches("FIRST YEAR OF YEAR"));
		
		assertFalse(timePhrase.matches("FIRST MILLISECOND OF MILLISECOND"));
		assertTrue(timePhrase.matches("FIRST MILLISECOND OF SECOND"));
		assertTrue(timePhrase.matches("FIRST MILLISECOND OF MINUTE"));
		assertTrue(timePhrase.matches("FIRST MILLISECOND OF HOUR"));
		assertTrue(timePhrase.matches("FIRST MILLISECOND OF DAY"));
		assertTrue(timePhrase.matches("FIRST MILLISECOND OF MONTH"));
	}
	
	/**
	 * Tests that whitespace between qualifier and time unit is ignored.
	 */
	@Test
	public void testWhitespaceMatches() {
		final FirstLastUnitTimePhrase timePhrase = new FirstLastUnitTimePhrase();
		assertTrue(timePhrase.matches("FIRSTDAYOFYEAR"));
		assertTrue(timePhrase.matches("FIRST DAY OF YEAR"));
		assertTrue(timePhrase.matches("FIRST  DAY OF YEAR"));
		assertTrue(timePhrase.matches("FIRST              DAY   OF   YEAR"));
		assertTrue(timePhrase.matches("FIRST              DAY   IN   THE   YEAR"));
	}
	
	/**
	 * Tests that phrase is case insensitive.
	 */
	@Test
	public void testLowerCaseMatches(){
		final FirstLastUnitTimePhrase timePhrase = new FirstLastUnitTimePhrase();
		assertTrue(timePhrase.matches("FiRsT dAy iN tHe YeAr"));
	}
	
	/**
	 * Tests that an invalid phrase does not match.
	 */
	@Test
	public void testNoMatch(){
		final FirstLastUnitTimePhrase timePhrase = new FirstLastUnitTimePhrase();
		assertFalse(timePhrase.matches("DOES NOT MATCH"));
	}
	
	/**
	 * Tests the first or last millisecond is returned for the chosen context.
	 */
	@Test
	public void testFirstLastMilli(){
		final FirstLastUnitTimePhrase timePhrase = new FirstLastUnitTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //April 6th, 2015
		fromWhen.set(Calendar.MILLISECOND, 1);
		
		final Calendar firstMilliDay = Calendar.getInstance();
		firstMilliDay.set(2015, 3, 6, 0, 0, 0); //April 6th, 2015
		firstMilliDay.set(Calendar.MILLISECOND, 0);
		
		final Calendar firstMilliYear = Calendar.getInstance();
		firstMilliYear.set(2015, 0, 1, 0, 0, 0); //January 1st, 2015
		firstMilliYear.set(Calendar.MILLISECOND, 0);
		
		final Calendar lastMilliYear = Calendar.getInstance();
		lastMilliYear.set(2015, 11, 31, 23, 59, 59); //December 31st, 2015
		lastMilliYear.set(Calendar.MILLISECOND, 999);
		
		timePhrase.matches("FIRST MILLISECOND OF THE DAY");
		assertEquals(firstMilliDay.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("FIRST MILLISECOND IN THE YEAR");
		assertEquals(firstMilliYear.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("LAST MILLISECOND IN THE YEAR");
		assertEquals(lastMilliYear.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests the first or last day is returned for the chosen context.
	 */
	@Test
	public void testFirstLastDay(){
		final FirstLastUnitTimePhrase timePhrase = new FirstLastUnitTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar firstDayMonth = Calendar.getInstance();
		firstDayMonth.set(2015, 3, 1, 1, 1, 1); //April 1st, 2015
		
		final Calendar lastDayMonth = Calendar.getInstance();
		lastDayMonth.set(2015, 3, 30, 1, 1, 1); //April 30th, 2015
		
		final Calendar lastDayWeek = Calendar.getInstance();
		lastDayWeek.set(2015, 3, 11, 1, 1, 1); //Saturday, April 11th, 2015
		
		final Calendar firstDayYear = Calendar.getInstance();
		firstDayYear.set(2015, 0, 1, 1, 1, 1); //January 1st, 2015
		
		final Calendar lastDayYear = Calendar.getInstance();
		lastDayYear.set(2015, 11, 31, 1, 1, 1); //December 31st, 2015
		
		timePhrase.matches("FIRST DAY OF MONTH");
		assertEquals(firstDayMonth.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("LAST DAY IN MONTH");
		assertEquals(lastDayMonth.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("LAST DAY OF THE WEEK");
		assertEquals(lastDayWeek.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("FIRST DAY OF THE YEAR");
		assertEquals(firstDayYear.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("LAST DAY OF THE YEAR");
		assertEquals(lastDayYear.getTime(), timePhrase.getTime(fromWhen));
	}
	
}
