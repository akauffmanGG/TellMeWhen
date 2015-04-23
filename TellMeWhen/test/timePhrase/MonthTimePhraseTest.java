package timePhrase;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

/**
 * Tests the {@link MonthTimePhrase} class
 * 
 * @author akauffman
 *
 */
public class MonthTimePhraseTest {

	/**
	 * Tests that all of the various qualifiers match the phrase.
	 */
	@Test
	public void testQualifierMatches() {
		final MonthTimePhrase timePhrase = new MonthTimePhrase();
		assertTrue(timePhrase.matches("APRIL"));
		assertTrue(timePhrase.matches("NEXT APRIL"));
		assertTrue(timePhrase.matches("LAST APRIL"));
		assertTrue(timePhrase.matches("THIS APRIL"));
		assertTrue(timePhrase.matches("PREVIOUS APRIL"));
	}
	
	/**
	 * Tests that all the months and abbreviations match the phrase.
	 */
	@Test
	public void testDaysMatch() {
		final MonthTimePhrase timePhrase = new MonthTimePhrase();
		assertTrue(timePhrase.matches("NEXT JANUARY"));
		assertTrue(timePhrase.matches("NEXT JAN"));
		assertTrue(timePhrase.matches("NEXT FEBRUARY"));
		assertTrue(timePhrase.matches("NEXT FEB"));
		assertTrue(timePhrase.matches("NEXT MARCH"));
		assertTrue(timePhrase.matches("NEXT MAR"));
		assertTrue(timePhrase.matches("NEXT APRIL"));
		assertTrue(timePhrase.matches("NEXT APR"));
		assertTrue(timePhrase.matches("NEXT MAY"));
		assertTrue(timePhrase.matches("NEXT JUNE"));
		assertTrue(timePhrase.matches("NEXT JULY"));
		assertTrue(timePhrase.matches("NEXT AUGUST"));
		assertTrue(timePhrase.matches("NEXT AUG"));
		assertTrue(timePhrase.matches("NEXT SEPTEMBER"));
		assertTrue(timePhrase.matches("NEXT SEPT"));
		assertTrue(timePhrase.matches("NEXT OCTOBER"));
		assertTrue(timePhrase.matches("NEXT OCT"));
		assertTrue(timePhrase.matches("NEXT NOVEMBER"));
		assertTrue(timePhrase.matches("NEXT NOV"));
		assertTrue(timePhrase.matches("NEXT DECEMBER"));
		assertTrue(timePhrase.matches("NEXT DEC"));
	}
	
	/**
	 * Tests that whitespace between qualifier and day is ignored.
	 */
	@Test
	public void testWhitespaceMatches() {
		final MonthTimePhrase timePhrase = new MonthTimePhrase();
		assertTrue(timePhrase.matches("NEXTAPRIL"));
		assertTrue(timePhrase.matches("NEXT APRIL"));
		assertTrue(timePhrase.matches("NEXT  APRIL"));
		assertTrue(timePhrase.matches("NEXT              APRIL"));
	}
	
	/**
	 * Tests that phrase is case insensitive.
	 */
	@Test
	public void testLowerCaseMatches(){
		final MonthTimePhrase timePhrase = new MonthTimePhrase();
		assertTrue(timePhrase.matches("NeXt ApRiL"));
	}
	
	/**
	 * Tests that an invalid phrase does not match.
	 */
	@Test
	public void testNoMatch(){
		final MonthTimePhrase timePhrase = new MonthTimePhrase();
		assertFalse(timePhrase.matches("DOES NOT MATCH"));
	}
	
	/**
	 * Tests that the Next qualifier gets the appropriate next instance of a month.
	 */
	@Test
	public void testNextTime(){
		final MonthTimePhrase timePhrase = new MonthTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //April 6th, 2015
		
		final Calendar nextApril = Calendar.getInstance();
		nextApril.set(2016, 3, 6, 1, 1, 1);//April 6th, 2016
		
		final Calendar nextMay = Calendar.getInstance();
		nextMay.set(2015, 4, 6, 1, 1, 1);//May 6th, 2015
		
		final Calendar nextMarch = Calendar.getInstance();
		nextMarch.set(2016, 2, 6, 1, 1, 1);//March 6th, 2016
		
		timePhrase.matches("NEXT APRIL");
		assertEquals(nextApril.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("NEXT MAY");
		assertEquals(nextMay.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("NEXT MARCH");
		assertEquals(nextMarch.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests that passing "This" as a qualifier gets the next instance of the month. However if the requested month
	 * is the current month, then the current date will be returned.
	 */
	@Test
	public void testThisTime(){
		final MonthTimePhrase timePhrase = new MonthTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //April 6th, 2015
		
		final Calendar thisApril = Calendar.getInstance();
		thisApril.set(2015, 3, 6, 1, 1, 1);//April 6th, 2015
		
		final Calendar thisMay = Calendar.getInstance();
		thisMay.set(2015, 4, 6, 1, 1, 1);//May 6th, 2015
		
		final Calendar thisMarch = Calendar.getInstance();
		thisMarch.set(2016, 2, 6, 1, 1, 1);//March 6th, 2016
		
		timePhrase.matches("THIS APRIL");
		assertEquals(thisApril.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("THIS MAY");
		assertEquals(thisMay.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("THIS MARCH");
		assertEquals(thisMarch.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests that passing no qualifier gets the next instance of the month. However if the requested month
	 * is the current month, then the current month will be returned.
	 */
	@Test
	public void testNoQualifierTime(){
		final MonthTimePhrase timePhrase = new MonthTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //April 6th, 2015
		
		final Calendar april = Calendar.getInstance();
		april.set(2015, 3, 6, 1, 1, 1);//April 6th, 2015
		
		final Calendar may = Calendar.getInstance();
		may.set(2015, 4, 6, 1, 1, 1);//May 6th, 2015
		
		final Calendar march = Calendar.getInstance();
		march.set(2016, 2, 6, 1, 1, 1);//March 6th, 2016
		
		timePhrase.matches("APRIL");
		assertEquals(april.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("MAY");
		assertEquals(may.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("MARCH");
		assertEquals(march.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests that the "LAST" qualifier gets the last instance of a month.
	 */
	@Test
	public void testLastTime(){
		final MonthTimePhrase timePhrase = new MonthTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar lastApril = Calendar.getInstance();
		lastApril.set(2014, 3, 6, 1, 1, 1);//April 6th, 2014
		
		final Calendar lastMay = Calendar.getInstance();
		lastMay.set(2014, 4, 6, 1, 1, 1);//May 6th, 2014
		
		final Calendar lastMarch = Calendar.getInstance();
		lastMarch.set(2015, 2, 6, 1, 1, 1);//March 6th, 2015
		
		timePhrase.matches("LAST APRIL");
		assertEquals(lastApril.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("LAST MAY");
		assertEquals(lastMay.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("LAST MARCH");
		assertEquals(lastMarch.getTime(), timePhrase.getTime(fromWhen));
	}
	
	/**
	 * Tests that the "PREVIOUS" qualifier gets the last instance of a month.
	 */
	@Test
	public void testPreviousTime(){
		final MonthTimePhrase timePhrase = new MonthTimePhrase();
		
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		final Calendar previousApril = Calendar.getInstance();
		previousApril.set(2014, 3, 6, 1, 1, 1);//April 6th, 2014
		
		final Calendar previousMay = Calendar.getInstance();
		previousMay.set(2014, 4, 6, 1, 1, 1);//May 6th, 2014
		
		final Calendar previousMarch = Calendar.getInstance();
		previousMarch.set(2015, 2, 6, 1, 1, 1);//March 6th, 2015
		
		timePhrase.matches("PREVIOUS APRIL");
		assertEquals(previousApril.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("PREVIOUS MAY");
		assertEquals(previousMay.getTime(), timePhrase.getTime(fromWhen));
		
		timePhrase.matches("PREVIOUS MARCH");
		assertEquals(previousMarch.getTime(), timePhrase.getTime(fromWhen));
	}

}
