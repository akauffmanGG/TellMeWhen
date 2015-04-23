package timePhrase;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link TimePhrase TimePhrases} are not meant to be reused asynchronously so this factory creates them fresh.
 * 
 * @author akauffman
 *
 */
public class TimePhraseFactory {
	/**
	 * Creates all {@link TimePhrase TimePhrases} in order of importance. Typically the most common phrases
	 * should be created first.
	 * 
	 * @return all available {@link TimePhrase}
	 */
	public static List<TimePhrase> getTimePhrases(){
		final List<TimePhrase> timePhrases = new ArrayList<>();
		timePhrases.add(new NearTimePhrase());
		timePhrases.add(new DayOfWeekTimePhrase());
		timePhrases.add(new ChangeUnitTimePhrase());
		timePhrases.add(new MonthTimePhrase());
		
		return timePhrases;
	}
}
