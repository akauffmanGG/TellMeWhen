package timePhrase;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A phrase that represents time near to the current time, such as Yesterday or Tomorrow.
 * 
 * @author akauffman
 *
 */
public class NearTimePhrase implements TimePhrase {
	/** Phrase regex. Matches Yesterday, Tomorrow, Now, or Today */
	private static final Pattern NEAR_TIME = Pattern.compile("(YESTERDAY|TOMORROW|NOW|TODAY)", Pattern.CASE_INSENSITIVE);
	/** Matcher that stores results of matching the given time to the pattern. */
	private Matcher matcher;
	
	@Override
	public boolean matches(String time) {
		this.matcher = NEAR_TIME.matcher(time);
		return matcher.matches();
	}

	@Override
	public Date getTime(Calendar fromWhen) throws IllegalStateException{
		if(matcher == null){
			throw new IllegalStateException("No match has been attempted");
		}
		if(fromWhen == null){
			throw new NullPointerException("fromWhen is null");
		}
		
		final Calendar cal = (Calendar)fromWhen.clone();
		
		if("TOMORROW".equals(matcher.group(1))){
			cal.add(Calendar.DATE, 1);
		} else if("YESTERDAY".equals(matcher.group(1))){
			cal.add(Calendar.DATE, -1);
		} //else treat it as the current time

		return cal.getTime();
	}

}
