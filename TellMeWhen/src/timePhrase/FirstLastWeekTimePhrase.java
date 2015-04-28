package timePhrase;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Phrase the represents the first or last week of the given month or year. Ex: If the given date was Monday, then
 * the phrase "First week of the year." would return a date that is equal to Monday of the first week in
 * the given year.
 * 
 * @author akauffman
 *
 */
public class FirstLastWeekTimePhrase implements TimePhrase {
	/** Phrase regex. Matches First or Last followed week followed by month or year*/
	private static final Pattern CHANGE_UNIT = Pattern.compile("^(FIRST|LAST)\\s*WEEK\\s*(?:OF|IN)\\s*(?:THE)?\\s*(MONTH|YEAR)", Pattern.CASE_INSENSITIVE);
	/** Matcher that stores results of matching the given time to the pattern. */
	private Matcher matcher;

	@Override
	public boolean matches(String time) {
		this.matcher = CHANGE_UNIT.matcher(time);
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
		final String containerUnit = matcher.group(2);
		
		if("MONTH".equals(containerUnit)){
			changeWeekInMonth(cal, changeDirection);
		} else {
			changeWeekInYear(cal, changeDirection);
		}
		
		return cal.getTime();
	}
	
	/**
	 * Changes the date to the first or last week of the month.
	 * 
	 * @param cal {@link Calendar} that will be altered.
	 * @param changeDirection Indicates if this will be the first or last week.
	 */
	private void changeWeekInMonth(Calendar cal, String changeDirection){
		cal.get(Calendar.MONTH);
		if("FIRST".equals(changeDirection)){
			cal.set(Calendar.WEEK_OF_MONTH, 1);
		} else {
			cal.set(Calendar.WEEK_OF_MONTH, cal.getMaximum(Calendar.WEEK_OF_MONTH)-1);
		}
	}
	
	/**
	 * Changes the date to the first or last week of the year.
	 * 
	 * @param cal {@link Calendar} that will be altered.
	 * @param changeDirection Indicates if this will be the first or last week.
	 */
	private void changeWeekInYear(Calendar cal, String changeDirection){
		cal.get(Calendar.MONTH);
		if("FIRST".equals(changeDirection)){
			cal.set(Calendar.WEEK_OF_YEAR, cal.getMinimum(Calendar.WEEK_OF_YEAR));
		} else {
			cal.set(Calendar.WEEK_OF_YEAR, cal.getMaximum(Calendar.WEEK_OF_YEAR));
		}
	}

}
