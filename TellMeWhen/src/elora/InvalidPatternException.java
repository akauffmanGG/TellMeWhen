package elora;

/**
 * Exception class used when an invalid time pattern is used.
 * 
 * @author akauffman
 *
 */
public class InvalidPatternException extends Exception {
	private static final long serialVersionUID = 3810223634374783786L;

	public InvalidPatternException(){
		super("Input string not a recognized pattern");
	}
	
	public InvalidPatternException(String message){
		super(message);
	}
}
