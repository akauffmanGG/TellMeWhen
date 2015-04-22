package timePhrase;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

/**
 * Tests the {@link ChangeUnitTimePhrase} class.
 * 
 * @author akauffman
 *
 */
public class ChangeUnitTimePhraseTest {

	/**
	 * Tests that Next or Last match the phrase.
	 */
	@Test
	public void testQualifierMatches() {
		final ChangeUnitTimePhrase timePhrase = new ChangeUnitTimePhrase();
		assertTrue(timePhrase.matches("NEXT WEEK"));
		assertTrue(timePhrase.matches("LAST WEEK"));
	}
	
	/**
	 * Tests that time units match the phrase.
	 */
	@Test
	public void testTimeUnitMatches() {
		final ChangeUnitTimePhrase timePhrase = new ChangeUnitTimePhrase();
		assertTrue(timePhrase.matches("NEXT MILLISECOND"));
		assertTrue(timePhrase.matches("NEXT SECOND"));
		assertTrue(timePhrase.matches("NEXT MINUTE"));
		assertTrue(timePhrase.matches("NEXT HOUR"));
		assertTrue(timePhrase.matches("NEXT DAY"));
		assertTrue(timePhrase.matches("NEXT WEEK"));
		assertTrue(timePhrase.matches("NEXT MONTH"));
		assertTrue(timePhrase.matches("NEXT YEAR"));
	}
	
	/**
	 * Tests that whitespace between qualifier and week is ignored.
	 */
	@Test
	public void testWhitespaceMatches() {
		final ChangeUnitTimePhrase timePhrase = new ChangeUnitTimePhrase();
		assertTrue(timePhrase.matches("NEXTWEEK"));
		assertTrue(timePhrase.matches("NEXT WEEK"));
		assertTrue(timePhrase.matches("NEXT  WEEK"));
		assertTrue(timePhrase.matches("NEXT              WEEK"));
	}
	
	/**
	 * Tests that phrase is case insensitive.
	 */
	@Test
	public void testLowerCaseMatches(){
		final ChangeUnitTimePhrase timePhrase = new ChangeUnitTimePhrase();
		assertTrue(timePhrase.matches("NeXt WeEk"));
	}
	
	/**
	 * Tests that an invalid phrase does not match.
	 */
	@Test
	public void testNoMatch(){
		final ChangeUnitTimePhrase timePhrase = new ChangeUnitTimePhrase();
		assertFalse(timePhrase.matches("DOES NOT MATCH"));
	}
	
	/**
	 * Tests the Next Millisecond returns a date 1 millisecond in the future.
	 */
	@Test
	public void testNextMilli(){
		final ChangeUnitTimePhrase timePhrase = new ChangeUnitTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		fromWhen.set(Calendar.MILLISECOND, 1);
		
		final Calendar nextMilli = Calendar.getInstance();
		nextMilli.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		nextMilli.set(Calendar.MILLISECOND, 2);
		
		timePhrase.matches("NEXT MILLISECOND");
		assertEquals(nextMilli.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests the Next Second returns a date 1 second in the future.
	 */
	@Test
	public void testNextSecond(){
		final ChangeUnitTimePhrase timePhrase = new ChangeUnitTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar nextSecond = Calendar.getInstance();
		nextSecond.set(2015, 3, 6, 1, 1, 2); //Monday, April 6th, 2015
		
		timePhrase.matches("NEXT SECOND");
		assertEquals(nextSecond.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests the Next Minute returns a date 1 minute in the future.
	 */
	@Test
	public void testNextMinute(){
		final ChangeUnitTimePhrase timePhrase = new ChangeUnitTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar nextMinute = Calendar.getInstance();
		nextMinute.set(2015, 3, 6, 1, 2, 1); //Monday, April 6th, 2015
		
		timePhrase.matches("NEXT MINUTE");
		assertEquals(nextMinute.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests the Next Hour returns a date 1 hour in the future.
	 */
	@Test
	public void testNextHour(){
		final ChangeUnitTimePhrase timePhrase = new ChangeUnitTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar nextHour = Calendar.getInstance();
		nextHour.set(2015, 3, 6, 2, 1, 1); //Monday, April 6th, 2015
		
		timePhrase.matches("NEXT HOUR");
		assertEquals(nextHour.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests the Next Day returns a date 1 day in the future.
	 */
	@Test
	public void testNextDay(){
		final ChangeUnitTimePhrase timePhrase = new ChangeUnitTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar nextDay = Calendar.getInstance();
		nextDay.set(2015, 3, 7, 1, 1, 1); //Tuesday, April 7th, 2015
		
		timePhrase.matches("NEXT DAY");
		assertEquals(nextDay.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests the Next Week phrase returns a date 7 days in the future.
	 */
	@Test
	public void testNextWeek(){
		final ChangeUnitTimePhrase timePhrase = new ChangeUnitTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar nextWeek = Calendar.getInstance();
		nextWeek.set(2015, 3, 13, 1, 1, 1); //Monday, April 13th, 2015
		
		timePhrase.matches("NEXT WEEK");
		assertEquals(nextWeek.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests the Next qualifier returns a date 7 days in the past.
	 */
	@Test
	public void testLastWeek(){
		final ChangeUnitTimePhrase timePhrase = new ChangeUnitTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar lastWeek = Calendar.getInstance();
		lastWeek.set(2015, 2, 30, 1, 1, 1); //Monday, March 30th, 2015
		
		timePhrase.matches("LAST WEEK");
		assertEquals(lastWeek.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests the Next Month phrase returns a date 1 month in the future.
	 */
	@Test
	public void testNextMonth(){
		final ChangeUnitTimePhrase timePhrase = new ChangeUnitTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar nextMonth = Calendar.getInstance();
		nextMonth.set(2015, 4, 6, 1, 1, 1); //Wednesday, May 6th, 2015
		
		timePhrase.matches("NEXT MONTH");
		assertEquals(nextMonth.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests the Next Year phrase returns a date 1 year in the future.
	 */
	@Test
	public void testNextYear(){
		final ChangeUnitTimePhrase timePhrase = new ChangeUnitTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar nextMonth = Calendar.getInstance();
		nextMonth.set(2016, 3, 6, 1, 1, 1); //Wednesday, April 6th, 2016
		
		timePhrase.matches("NEXT YEAR");
		assertEquals(nextMonth.getTime(), timePhrase.getTime(fromWhen));
	}
}
