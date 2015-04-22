import elora.TellMeWhen;


public class Main {
	public static void main(String[] args){
		
		final String input;
		if(args.length == 0){
			input = "NOW";
		} else {
			input = args[0].toUpperCase();
		}
		
		System.out.println("Getting time for " + input);
		try {
			System.out.println("Time is " + TellMeWhen.listen(input));
		} catch (Exception e) {
			System.out.println("Problem interpreting input. " + e.getMessage());
		}
	}
}
