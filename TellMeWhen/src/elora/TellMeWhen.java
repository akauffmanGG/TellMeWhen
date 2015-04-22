package elora;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import timePhrase.TimePhrase;
import timePhrase.TimePhraseFactory;

/**
 * Encapsulates the logic for converting a plain English representation of a time relative to now into a {@link Date} object.
 * 
 * @author akauffman
 *
 */
public final class TellMeWhen {

	/**
	 * Gets a date relative to the current date that matches a plain English description.
	 * <br/>
	 * Ex: "Next Thursday" would return a date for the next Thursday.
	 * 
	 * @param relativeTime A plain English representation of a time relative to now. Ex: Last Month
	 * @return the requested {@link Date}
	 * @throws InvalidPatternException if the given time string does not match any known time patterns.
	 */
	public static final Date listen(String relativeTime) throws InvalidPatternException{
		return listen(relativeTime, Calendar.getInstance().getTime());
	}
	
	/**
	 * Gets a date relative to the given date that matches a plain English description.
	 * <br/>
	 * Ex: "Next Thursday" from 4/19/2015 would return a date of 4/23/2015.
	 * 
	 * @param relativeTime A plain English representation of a relative time. Ex: Last Month
	 * @param fromDate The date that will be modified based on the relativeTime phrase.
	 * @return the requested {@link Date}
	 * @throws InvalidPatternException if the given time string does not match any known time patterns.
	 */
	public static final Date listen(String relativeTime, Date fromDate) throws InvalidPatternException{
		if(null == relativeTime){
			throw new NullPointerException("RelativeTime is null");
		}
		if(null == fromDate){
			throw new NullPointerException("fromDate is null");
		}
		
		relativeTime = relativeTime.toUpperCase().trim();
		final Calendar fromWhen = Calendar.getInstance();
		fromWhen.setTime(fromDate);

		final List<TimePhrase> timePhrases = TimePhraseFactory.getTimePhrases();
		for(TimePhrase timePhrase : timePhrases){
			if(timePhrase.matches(relativeTime)){
				return timePhrase.getTime(fromWhen);
			}
		}

		throw new InvalidPatternException();
	}

}
