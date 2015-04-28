package timePhrase;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Time phrase for setting a date to the first or last time unit within the reference of another time unit.
 * Ex. First day of the month. If the given date was in April then this would return April 1st.
 * 
 * @author akauffman
 *
 */
public class FirstLastUnitTimePhrase implements TimePhrase {
	/** Phrase regex. Matches First or Last followed by a unit of time of another unit of time. Ex. First day of month. */
	private static final Pattern CHANGE_UNIT = Pattern.compile("^(FIRST|LAST)\\s*(MILLISECOND|SECOND|MINUTE|HOUR|DAY|MONTH)\\s*(?:OF|IN)\\s*(?:THE)?\\s*(SECOND|MINUTE|HOUR|DAY|WEEK|MONTH|YEAR)", Pattern.CASE_INSENSITIVE);
	/** Matcher that stores results of matching the given time to the pattern. */
	private Matcher matcher;
	/** Array of supported time units in order from largest to smallest. */
	private static final int[] TIME_UNITS = {Calendar.YEAR, Calendar.MONTH, Calendar.WEEK_OF_YEAR, Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND};
	
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
		final int changeUnit = getTimeField(matcher.group(2));
		final int containerUnit = getTimeField(matcher.group(3));
		
		if("FIRST".equals(changeDirection)){
			changeDate(cal, changeUnit, containerUnit, true);
		} else if("LAST".equals(changeDirection)){
			changeDate(cal, changeUnit, containerUnit, false);
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
			return Calendar.HOUR_OF_DAY;
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
	
	
	/**
	 * Changes the calendar to the maximum or minimum chosen time unit within the chosen container time unit. All time
	 * units between the chosen unit and the container unit will also be modified. The
	 * value may be dependent on the container unit. For instance the maximum day within a week is different than
	 * the maximum day within the month.
	 * 
	 * 
	 * @param cal The {@link Calendar} that will be altered as a result of this operation.
	 * @param changeUnit The chosen time unit that will be set to the maximum or minimum value.
	 * @param containerUnit The time unit that will be the reference point for the changeUnit.
	 * @param setMin True if we are setting fields to minimum values, false if setting to maximum.
	 */
	private void changeDate(Calendar cal, int changeUnit, int containerUnit, boolean setMin){
		cal.setMinimalDaysInFirstWeek(1);
		
		boolean foundContainerUnit = false;
		for(int i = 0; i < TIME_UNITS.length; i++){
			int timeUnit = TIME_UNITS[i];
			
			if(timeUnit != Calendar.WEEK_OF_YEAR){
				//Week is not really useful in determining the date unless the user
				//has specifically asked for a certain week. Therefore we will skip the week
				//unless requested.
				
				
				if(foundContainerUnit){
					final int calendarUnit = getCalendarUnitToChange(timeUnit, containerUnit);
					cal.get(Calendar.MONTH); //Force calendar to recompute internal state.
					if(setMin){
						cal.set(calendarUnit, cal.getActualMinimum(calendarUnit));
					} else { //Set to maximum value
						cal.set(calendarUnit, cal.getActualMaximum(calendarUnit));
					}
				}
				
				if(timeUnit == changeUnit){
					return;
				}
			}
			
			
			foundContainerUnit = foundContainerUnit || (timeUnit == containerUnit);
		}
		
		throw new IllegalArgumentException("Unable to change unit " + changeUnit + " within " + containerUnit);
	}
	
	/**
	 * Gets the corrected {@link Calendar} time unit to change based on the container unit.
	 * For instance Day could be Day within a week, month or year.
	 * 
	 * @param timeUnit The time unit phrase that will change.
	 * @param containerUnit The container time unit used as a reference point for changing the timeUnit.
	 * @return corrected Calendar time unit.
	 */
	private int getCalendarUnitToChange(int timeUnit, int containerUnit){
		//Special case with date. It may be day within a week, month or year.
		if(Calendar.DATE == timeUnit){
			switch(containerUnit){
			case Calendar.WEEK_OF_YEAR:
				return Calendar.DAY_OF_WEEK;
			case Calendar.MONTH:
				return Calendar.DAY_OF_MONTH;
			case Calendar.YEAR:
				return Calendar.DAY_OF_YEAR;
			}
		}
		
		return timeUnit;
	}
}
