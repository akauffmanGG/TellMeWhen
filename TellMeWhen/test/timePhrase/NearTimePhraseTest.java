package timePhrase;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

/**
 * Tests the {@link NearTimePhrase} class
 * 
 * @author akauffman
 *
 */
public class NearTimePhraseTest {

	/**
	 * Tests that the phrase "NOW" matches correctly.
	 */
	@Test
	public void testNowMatches() {
		final NearTimePhrase timePhrase = new NearTimePhrase();
		assertTrue(timePhrase.matches("NOW"));
	}
	
	/**
	 * Tests the phrase "NOW" will result in the current time relative to the given time.
	 */
	@Test
	public void testNowTime() {
		final NearTimePhrase timePhrase = new NearTimePhrase();
		timePhrase.matches("NOW");
		
		Calendar calNow = Calendar.getInstance();
		assertTrue(calNow.getTime().equals(timePhrase.getTime(calNow)));
		
		//Tests that "NOW" will equal the given time even though it is set to tomorrow.
		calNow.add(Calendar.DATE, 1);
		assertTrue(calNow.getTime().equals(timePhrase.getTime(calNow)));
	}
	
	/**
	 * Tests that the phrase "TODAY" matches correctly.
	 */
	@Test
	public void testTodayMatches() {
		final NearTimePhrase timePhrase = new NearTimePhrase();
		assertTrue(timePhrase.matches("TODAY"));
	}
	
	/**
	 * Tests the phrase "TODAY" will result in the current time relative to the given time.
	 */
	@Test
	public void testTodayTime() {
		final NearTimePhrase timePhrase = new NearTimePhrase();
		timePhrase.matches("TODAY");
		
		final Calendar calToday = Calendar.getInstance();
		assertTrue(calToday.getTime().equals(timePhrase.getTime(calToday)));
		
		//Tests that "TODAY" will equal the given time even though it is set to tomorrow.
		calToday.add(Calendar.DATE, 1);
		assertTrue(calToday.getTime().equals(timePhrase.getTime(calToday)));
	}
	
	/**
	 * Tests that the phrase "TOMORROW" matches correctly.
	 */
	@Test
	public void testTomorrowMatches() {
		final NearTimePhrase timePhrase = new NearTimePhrase();
		assertTrue(timePhrase.matches("TOMORROW"));
	}
	
	/**
	 * Tests the phrase "TOMORROW" will result in one day into the future relative to the given time.
	 */
	@Test
	public void testTomorrowTime() {
		final NearTimePhrase timePhrase = new NearTimePhrase();
		timePhrase.matches("TOMORROW");
		
		final Calendar calToday = Calendar.getInstance();
		final Calendar calTomorrow = (Calendar)calToday.clone();
		calTomorrow.add(Calendar.DATE, 1);
		
		//Tests "TOMORROW" will be one day from now.
		assertTrue(calTomorrow.getTime().equals(timePhrase.getTime(calToday)));
		
		calToday.add(Calendar.DATE, 1);
		calTomorrow.add(Calendar.DATE, 1);
		
		//Tests "TOMORROW" will be one day from tomorrow.
		assertTrue(calTomorrow.getTime().equals(timePhrase.getTime(calToday)));
	}
	
	/**
	 * Tests that the phrase "YESTERDAY" matches correctly.
	 */
	@Test
	public void testYesterdayMatches() {
		final NearTimePhrase timePhrase = new NearTimePhrase();
		assertTrue(timePhrase.matches("YESTERDAY"));
		
	}
	
	/**
	 * Tests the phrase "YESTERDAY" will result in one day into the past relative to the given time.
	 */
	@Test
	public void testYesterdayTime() {
		final NearTimePhrase timePhrase = new NearTimePhrase();
		timePhrase.matches("YESTERDAY");
		
		final Calendar calToday = Calendar.getInstance();
		final Calendar calYesterday = (Calendar)calToday.clone();
		calYesterday.add(Calendar.DATE, -1);
		
		//Tests "YESTERDAY" will be one day into the past.
		assertTrue(calYesterday.getTime().equals(timePhrase.getTime(calToday)));
		
		calToday.add(Calendar.DATE, 1);
		calYesterday.add(Calendar.DATE, 1);
		//Tests "YESTERDAY will be one day into the past from tomorrow, ie today.
		assertTrue(calYesterday.getTime().equals(timePhrase.getTime(calToday)));
	}
	
	/**
	 * Tests that an invalid phrase does not match.
	 */
	@Test
	public void testNoMatch(){
		final NearTimePhrase timePhrase = new NearTimePhrase();
		assertFalse(timePhrase.matches("NOT A MATCH"));
	}
	
	/**
	 * Tests that phrases are not case sensitive.
	 */
	@Test
	public void testLowerCaseMatches(){
		final NearTimePhrase timePhrase = new NearTimePhrase();
		assertTrue(timePhrase.matches("YeStErDaY"));
	}
	
	

}
