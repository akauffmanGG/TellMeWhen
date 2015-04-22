package timePhrase;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A phrase that represents changing one unit of time into the past or future. Ex: Next Week or Next Month
 * 
 * @author akauffman
 *
 */
public class ChangeUnitTimePhrase implements TimePhrase {
	
	/** Phrase regex. Matches Next or Last followed by millisecond, second, minute, hour, day, week, or month. */
	private static final Pattern CHANGE_WEEK = Pattern.compile("^(NEXT|LAST)\\s*(MILLISECOND|SECOND|MINUTE|HOUR|DAY|WEEK|MONTH|YEAR)", Pattern.CASE_INSENSITIVE);
	/** Matcher that stores results of matching the given time to the pattern. */
	private Matcher matcher;
	
	@Override
	public boolean matches(String time) {
		this.matcher = CHANGE_WEEK.matcher(time);
		return matcher.matches();
	}

	@Override
	public Date getTime(Calendar fromWhen) throws IllegalStateException {
		if(matcher==null){
			throw new IllegalStateException("No match has been attempted");
		}
		if(fromWhen == null){
			throw new NullPointerException("fromWhen is null");
		}
		
		final Calendar cal = (Calendar)fromWhen.clone();
		final String changeDirection = matcher.group(1);
		final String timeUnit = matcher.group(2);
		
		int timeField = getTimeField(timeUnit);
		
		if("NEXT".equals(changeDirection)){
			cal.add(timeField, 1);
		} else if("LAST".equals(changeDirection)){
			cal.add(timeField, -1);
		}
		
		return cal.getTime();
	}

	/**
	 * Returns the calendar time field for the given time unit.
	 * 
	 * @param timeUnit
	 * @return
	 */
	private int getTimeField(String timeUnit){
		switch(timeUnit){
		case "MILLISECOND":
			return Calendar.MILLISECOND;
		case "SECOND":
			return Calendar.SECOND;
		case "MINUTE":
			return Calendar.MINUTE;
		case "HOUR":
			return Calendar.HOUR;
		case "DAY":
			return Calendar.DATE;
		case "WEEK":
			return Calendar.WEEK_OF_YEAR;
		case "MONTH":
			return Calendar.MONTH;
		case "YEAR":
			return Calendar.YEAR;
		default:
			throw new IllegalArgumentException("Not a valid time unit.");
		}
	}
}
