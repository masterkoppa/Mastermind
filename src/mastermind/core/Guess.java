package mastermind.core;

public class Guess {
	
	private Feedback feedback;
	private Code codeGuess;
	
	public Guess(){
		codeGuess = new Code();
		feedback = null;
	}
	
	/**
	 * Returns the code for this guess, by default it is an empty guess
	 * @return The code object for this guess
	 */
	public Code getCode(){
		return codeGuess;
	}
	
	/**
	 * Returns the feedback for this object. Null if not analyzed yet.
	 * @return
	 */
	public Feedback getFeedback(){
		return feedback;
	}
	
	/**
	 * Sets the code for this guess.
	 * @param code The code for this guess
	 */
	public void setCode(Code code){
		this.codeGuess = code;
	}
	
	/**
	 * Sets the feedback for this guess
	 * @param feedback The feedback for this guess
	 */
	public void setFeedback(Feedback feedback){
		this.feedback = feedback;
	}
	
	

}
