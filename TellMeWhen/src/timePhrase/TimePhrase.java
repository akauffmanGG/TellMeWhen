package timePhrase;

import java.util.Calendar;
import java.util.Date;

/**
 * Represents a pattern of speech that indicates a time.
 * 
 * @author akauffman
 *
 */
public interface TimePhrase {
	
	/**
	 * Answers if the given time phrase matches the pattern.
	 * 
	 * @param time phrase in plain speech that represents a relative time.
	 * @return
	 */
	public boolean matches(String time);
	
	/**
	 * @param fromWhen The date that will be modified based on the phrase.
	 * @return a {@link Date} that follows the pattern.
	 * @throws IllegalStateException if the phrase has not found a match when this is called.
	 */
	public Date getTime(Calendar fromWhen) throws IllegalStateException;
}
