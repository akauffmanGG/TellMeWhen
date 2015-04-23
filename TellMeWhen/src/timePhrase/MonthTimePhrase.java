package timePhrase;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A phrase that represents either the next or previous occurrence of a specific month of the year.
 * <br/>
 * Ex: Next April
 * 
 * @author akauffman
 *
 */
public class MonthTimePhrase implements TimePhrase {
	/** Phrase regex. Matches Next, Last, This, Previous, or no qualifier, followed by a month of the year or abbreviated month of the year. */
	private static final Pattern MONTH_OF_YEAR = Pattern.compile("(NEXT|LAST|THIS|PREVIOUS)?\\s*(JANUARY|JAN|FEBRUARY|FEB|MARCH|MAR|APRIL|APR|MAY|JUNE|JULY|AUGUST|AUG|SEPTEMBER|SEPT|OCTOBER|OCT|NOVEMBER|NOV|DECEMBER|DEC)", Pattern.CASE_INSENSITIVE);
	/** Matcher that stores results of matching the given time to the pattern. */
	private Matcher matcher;

	@Override
	public boolean matches(String time) {
		matcher = MONTH_OF_YEAR.matcher(time);
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
		final int month = getMonthOfYear(matcher.group(2));
		
		boolean lookNext = "NEXT".equals(modifier);
		boolean lookBack = "LAST".equals(modifier) || "PREVIOUS".equals(modifier);
		
		int difference = month - cal.get(Calendar.MONTH);
		
		if(difference >= 0 && lookBack){
			//If we're looking in the past, and it's before that month in the current year, 
			//then go back to previous year.
			difference -= 12;
		} else if(difference <=0 && !lookBack){
			//If we're looking in the future, and it's after that month in the current year, 
			//then go forward to next year.
			if(difference < 0 || lookNext){
				difference +=12;
			}
		}

		 cal.add(Calendar.MONTH, difference);
		
		return cal.getTime();
	}
	
	/**
	 * Gets the calendar month of the year for the given string representation
	 * 
	 * @param day Plain text string of the month. Ex: APRIL
	 * @return integer representing month of the year.
	 * @throws IllegalArgumentException if month cannot be found.
	 */
	private int getMonthOfYear(String month){
		switch(month){
		case "JANUARY":
		case "JAN":
			return Calendar.JANUARY;
		case "FEBRUARY":
		case "FEB":
			return Calendar.FEBRUARY;
		case "MARCH":
		case "MAR":
			return Calendar.MARCH;
		case "APRIL":
		case "APR":
			return Calendar.APRIL;
		case "MAY":
			return Calendar.MAY;
		case "JUNE":
			return Calendar.JUNE;
		case "JULY":
			return Calendar.JULY;
		case "AUGUST":
		case "AUG":
			return Calendar.AUGUST;
		case "SEPTEMBER":
		case "SEP":
			return Calendar.SEPTEMBER;
		case "OCTOBER":
		case "OCT":
			return Calendar.OCTOBER;
		case "NOVEMBER":
		case "NOV":
			return Calendar.NOVEMBER;
		case "DECEMBER":
		case "DEC":
			return Calendar.DECEMBER;
		default:
			throw new IllegalArgumentException("Unexpected month: " + month);
		}
	}

}
