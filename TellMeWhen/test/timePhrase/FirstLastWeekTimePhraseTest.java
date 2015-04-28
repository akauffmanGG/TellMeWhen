package timePhrase;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

/**
 * Tests the {@link FirstLastWeekTimePhrase} class.
 * 
 * @author akauffman
 *
 */
public class FirstLastWeekTimePhraseTest {

	/**
	 * Tests that various qualifiers match the phrase.
	 */
	@Test
	public void testQualifierMatches() {
		final FirstLastWeekTimePhrase timePhrase = new FirstLastWeekTimePhrase();
		assertTrue(timePhrase.matches("FIRST WEEK OF YEAR"));
		assertTrue(timePhrase.matches("LAST WEEK OF YEAR"));
		assertTrue(timePhrase.matches("LAST WEEK OF THE YEAR"));
		assertTrue(timePhrase.matches("LAST WEEK IN YEAR"));
		assertTrue(timePhrase.matches("LAST WEEK IN THE YEAR"));
	}
	
	/**
	 * Tests that time units match the phrase.
	 */
	@Test
	public void testTimeUnitMatches() {
		final FirstLastWeekTimePhrase timePhrase = new FirstLastWeekTimePhrase();
		assertTrue(timePhrase.matches("FIRST WEEK OF YEAR"));
		assertTrue(timePhrase.matches("FIRST WEEK OF MONTH"));
	}
	
	/**
	 * Tests that whitespace between qualifier and time unit is ignored.
	 */
	@Test
	public void testWhitespaceMatches() {
		final FirstLastWeekTimePhrase timePhrase = new FirstLastWeekTimePhrase();
		assertTrue(timePhrase.matches("FIRSTWEEKOFYEAR"));
		assertTrue(timePhrase.matches("FIRST WEEK OF YEAR"));
		assertTrue(timePhrase.matches("FIRST  WEEK OF YEAR"));
		assertTrue(timePhrase.matches("FIRST              WEEK   OF   YEAR"));
		assertTrue(timePhrase.matches("FIRST              WEEK   IN   THE   YEAR"));
	}
	
	/**
	 * Tests that phrase is case insensitive.
	 */
	@Test
	public void testLowerCaseMatches(){
		final FirstLastWeekTimePhrase timePhrase = new FirstLastWeekTimePhrase();
		assertTrue(timePhrase.matches("FiRsT wEeK iN tHe YeAr"));
	}
	
	/**
	 * Tests that an invalid phrase does not match.
	 */
	@Test
	public void testNoMatch(){
		final FirstLastWeekTimePhrase timePhrase = new FirstLastWeekTimePhrase();
		assertFalse(timePhrase.matches("DOES NOT MATCH"));
	}

	/**
	 * Tests the first or last week is returned for the current month.
	 */
	@Test
	public void testWeekInMonth(){
		final FirstLastWeekTimePhrase timePhrase = new FirstLastWeekTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar firstWeekMonth = Calendar.getInstance();
		firstWeekMonth.set(2015, 2, 30, 1, 1, 1); //March 30th, 2015
		
		final Calendar lastWeekMonth = Calendar.getInstance();
		lastWeekMonth.set(2015, 3, 27, 1, 1, 1); //April 27th, 2015
		
		timePhrase.matches("FIRST WEEK OF MONTH");
		assertEquals(firstWeekMonth.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("LAST WEEK IN MONTH");
		assertEquals(lastWeekMonth.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests the first or last week is returned for the current year.
	 */
	@Test
	public void testWeekInYear(){
		final FirstLastWeekTimePhrase timePhrase = new FirstLastWeekTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar firstWeekYear = Calendar.getInstance();
		firstWeekYear.set(2014, 11, 29, 1, 1, 1); //December 29th, 2014
		
		final Calendar lastWeekYear = Calendar.getInstance();
		lastWeekYear.set(2015, 11, 28, 1, 1, 1); //December 28th, 2015
		
		timePhrase.matches("FIRST WEEK OF THE YEAR");
		assertEquals(firstWeekYear.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("LAST WEEK OF THE YEAR");
		assertEquals(lastWeekYear.getTime(), timePhrase.getTime(fromWhen));
	}
	
}
