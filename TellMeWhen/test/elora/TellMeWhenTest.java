package elora;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import elora.TellMeWhen;

/**
 * Tests the {@link TellMeWhen} class.
 * 
 * @author akauffman
 *
 */
public class TellMeWhenTest {
	

	/**
	 * Tests that empty parameters are being handled.
	 */
	@Test
	public void testEmpty() {
		
		try{
			TellMeWhen.listen(null);
			fail("Allowed null phrase.");
		} catch (NullPointerException e){
			//Passes test
		} catch (InvalidPatternException e) {
			e.printStackTrace();
		}
		
		try{
			TellMeWhen.listen("NOW", null);
			fail("Allowed null fromWhen.");
		} catch (NullPointerException e){
			//Passes test
		} catch (InvalidPatternException e) {
			e.printStackTrace();
		}
		
		try{
			TellMeWhen.listen("");
			fail("Allowed empty phrase.");
		} catch (InvalidPatternException e) {
			//passes test
		}
		
		try{
			TellMeWhen.listen("NOT A VALID PATTERN");
			fail("Allowed invalid pattern.");
		} catch (InvalidPatternException e){
			//passes
		}
	}
	
	/**
	 * Tests passing just the phrase returns the time relative to now.
	 * @throws InvalidPatternException 
	 * @throws Exception 
	 */
	@Test
	public void testPhrase() throws InvalidPatternException{
		final Calendar cal = Calendar.getInstance();
		
		assertTrue(isEqual(cal, TellMeWhen.listen("now")));
		assertTrue(isEqual(cal, TellMeWhen.listen("NOW")));
	}
	
	/**
	 * Tests passing phrase and fromDate returns time relative to the fromDate
	 * @throws InvalidPatternException 
	 * @throws Exception 
	 */
	@Test
	public void testPhraseFromWhen() throws InvalidPatternException{
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.set(2015, 3, 6, 1, 1, 1); //Monday, April 6th, 2015
		
		assertEquals(fromWhen.getTime(), TellMeWhen.listen("NOW", fromWhen.getTime()));
	}

	/**
	 * Compares a calendar and a date to see if they're equal within a tolerance of one hour.
	 * 
	 * @param cal
	 * @param date
	 * @return
	 */
	private boolean isEqual(Calendar cal, Date date){
		Calendar cal1 = (Calendar)cal.clone();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date);
		
		cal1.set(Calendar.MILLISECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);
		cal1.set(Calendar.SECOND, 0);
		cal2.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.MINUTE, 0);
		
		return cal1.equals(cal2);
	}
	
}
