package timePhrase;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

/**
 * Tests the {@link DayOfWeekTimePhrase} class.
 * 
 * @author akauffman
 *
 */
public class DayOfWeekTimePhraseTest {

	/**
	 * Tests that all of the various qualifiers match the phrase.
	 */
	@Test
	public void testQualifierMatches() {
		final DayOfWeekTimePhrase timePhrase = new DayOfWeekTimePhrase();
		assertTrue(timePhrase.matches("MONDAY"));
		assertTrue(timePhrase.matches("NEXT MONDAY"));
		assertTrue(timePhrase.matches("LAST MONDAY"));
		assertTrue(timePhrase.matches("THIS MONDAY"));
		assertTrue(timePhrase.matches("PREVIOUS MONDAY"));
	}
	
	/**
	 * Tests that all the days of the week match the phrase.
	 */
	@Test
	public void testDaysMatch() {
		final DayOfWeekTimePhrase timePhrase = new DayOfWeekTimePhrase();
		assertTrue(timePhrase.matches("NEXT MONDAY"));
		assertTrue(timePhrase.matches("NEXT TUESDAY"));
		assertTrue(timePhrase.matches("NEXT WEDNESDAY"));
		assertTrue(timePhrase.matches("NEXT THURSDAY"));
		assertTrue(timePhrase.matches("NEXT FRIDAY"));
		assertTrue(timePhrase.matches("NEXT SATURDAY"));
		assertTrue(timePhrase.matches("NEXT SUNDAY"));
	}
	
	/**
	 * Tests that whitespace between qualifier and day is ignored.
	 */
	@Test
	public void testWhitespaceMatches() {
		final DayOfWeekTimePhrase timePhrase = new DayOfWeekTimePhrase();
		assertTrue(timePhrase.matches("NEXTMONDAY"));
		assertTrue(timePhrase.matches("NEXT MONDAY"));
		assertTrue(timePhrase.matches("NEXT  MONDAY"));
		assertTrue(timePhrase.matches("NEXT              MONDAY"));
	}
	
	/**
	 * Tests that phrase is case insensitive.
	 */
	@Test
	public void testLowerCaseMatches(){
		final DayOfWeekTimePhrase timePhrase = new DayOfWeekTimePhrase();
		assertTrue(timePhrase.matches("NeXt MoNdAy"));
	}
	
	/**
	 * Tests that an invalid phrase does not match.
	 */
	@Test
	public void testNoMatch(){
		final DayOfWeekTimePhrase timePhrase = new DayOfWeekTimePhrase();
		assertFalse(timePhrase.matches("DOES NOT MATCH"));
	}

	/**
	 * Tests that the Next qualifier gets the appropriate next instance of a day.
	 */
	@Test
	public void testNextTime(){
		final DayOfWeekTimePhrase timePhrase = new DayOfWeekTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar nextMonday = Calendar.getInstance();
		nextMonday.set(2015, 3, 13, 1, 1, 1);//Monday, April 13th, 2015
		
		final Calendar nextTuesday = Calendar.getInstance();
		nextTuesday.set(2015, 3, 7, 1, 1, 1);//Tuesday, April 7th, 2015
		
		final Calendar nextSunday = Calendar.getInstance();
		nextSunday.set(2015, 3, 12, 1, 1, 1);//Sunday, April 12th, 2015
		
		timePhrase.matches("NEXT MONDAY");
		assertEquals(nextMonday.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("NEXT TUESDAY");
		assertEquals(nextTuesday.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("NEXT SUNDAY");
		assertEquals(nextSunday.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests that passing "This" as a qualifier gets the next instance of the day. However if the requested day
	 * is the current day, then the current day will be returned.
	 */
	@Test
	public void testThisTime(){
		final DayOfWeekTimePhrase timePhrase = new DayOfWeekTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar nextTuesday = Calendar.getInstance();
		nextTuesday.set(2015, 3, 7, 1, 1, 1);//Tuesday, April 7th, 2015
		
		final Calendar nextSunday = Calendar.getInstance();
		nextSunday.set(2015, 3, 12, 1, 1, 1);//Sunday, April 12th, 2015
		
		timePhrase.matches("THIS MONDAY");
		assertEquals(fromWhen.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("THIS TUESDAY");
		assertEquals(nextTuesday.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("THIS SUNDAY");
		assertEquals(nextSunday.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests that passing no qualifier gets the next instance of the day. However if the requested day
	 * is the current day, then the current day will be returned.
	 */
	@Test
	public void testNoQualifierTime(){
		final DayOfWeekTimePhrase timePhrase = new DayOfWeekTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar nextTuesday = Calendar.getInstance();
		nextTuesday.set(2015, 3, 7, 1, 1, 1);//Tuesday, April 7th, 2015
		
		final Calendar nextSunday = Calendar.getInstance();
		nextSunday.set(2015, 3, 12, 1, 1, 1);//Sunday, April 12th, 2015
		
		timePhrase.matches("MONDAY");
		assertEquals(fromWhen.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("TUESDAY");
		assertEquals(nextTuesday.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("SUNDAY");
		assertEquals(nextSunday.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests that the "LAST" qualifier gets the last instance of a day.
	 */
	@Test
	public void testLastTime(){
		final DayOfWeekTimePhrase timePhrase = new DayOfWeekTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar lastMonday = Calendar.getInstance();
		lastMonday.set(2015, 2, 30, 1, 1, 1);//Monday, March 30th, 2015
		
		final Calendar lastTuesday = Calendar.getInstance();
		lastTuesday.set(2015, 2, 31, 1, 1, 1);//Tuesday, March 31st, 2015
		
		final Calendar lastSunday = Calendar.getInstance();
		lastSunday.set(2015, 3, 5, 1, 1, 1);//Sunday, April 5th, 2015
		
		timePhrase.matches("LAST MONDAY");
		assertEquals(lastMonday.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("LAST TUESDAY");
		assertEquals(lastTuesday.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("LAST SUNDAY");
		assertEquals(lastSunday.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests that the "PREVIOUS" qualifier gets the last instance of a day.
	 */
	@Test
	public void testPreviousTime(){
		final DayOfWeekTimePhrase timePhrase = new DayOfWeekTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar lastMonday = Calendar.getInstance();
		lastMonday.set(2015, 2, 30, 1, 1, 1);//Monday, March 30th, 2015
		
		final Calendar lastTuesday = Calendar.getInstance();
		lastTuesday.set(2015, 2, 31, 1, 1, 1);//Tuesday, March 31st, 2015
		
		final Calendar lastSunday = Calendar.getInstance();
		lastSunday.set(2015, 3, 5, 1, 1, 1);//Sunday, April 5th, 2015
		
		timePhrase.matches("PREVIOUS MONDAY");
		assertEquals(lastMonday.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("PREVIOUS TUESDAY");
		assertEquals(lastTuesday.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("PREVIOUS SUNDAY");
		assertEquals(lastSunday.getTime(), timePhrase.getTime(fromWhen));
	}
}
