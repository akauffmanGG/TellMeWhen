package timePhrase;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A phrase that represents either the next or previous occurrence of a specific day of the week.
 * <br/>
 * Ex: Next Thursday
 * 
 * @author akauffman
 *
 */
public class DayOfWeekTimePhrase implements TimePhrase {
	/** Phrase regex. Matches Next, Last, This, Previous, or no qualifier, followed by a day of the week. */
	private static final Pattern DAY_OF_WEEK = Pattern.compile("(NEXT|LAST|THIS|PREVIOUS)?\\s*(SUNDAY|MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY)", Pattern.CASE_INSENSITIVE);
	/** Matcher that stores results of matching the given time to the pattern. */
	private Matcher matcher;
	
	@Override
	public boolean matches(String time) {
		matcher = DAY_OF_WEEK.matcher(time);
		return matcher.matches();
	}

	@Override
	public Date getTime(Calendar fromWhen) throws IllegalStateException {
		if(matcher == null){
			throw new IllegalStateException("No match has been attempted");
		}
		if(fromWhen == null){
			throw new NullPointerException("fromWhen is null");
		}
		
		final Calendar cal = (Calendar)fromWhen.clone();

		final String modifier = matcher.group(1);
		final int day = getDayOfWeek(matcher.group(2));
		
		boolean lookNext = "NEXT".equals(modifier);
		boolean lookBack = "LAST".equals(modifier) || "PREVIOUS".equals(modifier);
		
		int difference = day - cal.get(Calendar.DAY_OF_WEEK);
		
		if(difference >= 0 && lookBack){
			//If we're looking in the past, and it's before that day in the current week, 
			//then go back to previous week.
			difference -= 7;
		} else if(difference <=0 && !lookBack){
			//If we're looking in the future, and it's after that day in the current week, 
			//then go forward to next week.
			if(difference < 0 || lookNext){
				difference +=7;
			}
		}

		 cal.add(Calendar.DATE, difference);
		
		return cal.getTime();

	}
	
	/**
	 * Gets the calendar day of the week for the given string representation
	 * 
	 * @param day Plain text string of the day of the week. Ex: MONDAY
	 * @return integer representing day of the week.
	 * @throws Exception
	 */
	private int getDayOfWeek(String day) throws IllegalArgumentException{
		switch(day){
		case "SUNDAY":
			return Calendar.SUNDAY;
		case "MONDAY":
			return Calendar.MONDAY;
		case "TUESDAY":
			return Calendar.TUESDAY;
		case "WEDNESDAY":
			return Calendar.WEDNESDAY;
		case "THURSDAY":
			return Calendar.THURSDAY;
		case "FRIDAY":
			return Calendar.FRIDAY;
		case "SATURDAY":
			return Calendar.SATURDAY;
		default:
			throw new IllegalArgumentException("Expected day of week, but was given " + day);
		}
	}

}
