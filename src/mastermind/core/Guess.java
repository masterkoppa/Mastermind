package mastermind.core;

public class Guess {

	private Feedback feedback;
	private Code codeGuess;

	public Guess() {
		codeGuess = new Code();
		feedback = null;
	}

	/**
	 * Returns the code for this guess, by default it is an empty guess
	 * 
	 * @return The code object for this guess
	 */
	public Code getCode() {
		return codeGuess;
	}

	/**
	 * Returns the code for this guess, by default it is an empty guess
	 * 
	 * @return The code object for this guess
	 */
	public ColorPeg[] getCodePegs() {
		return codeGuess.getPegs();
	}

	/**
	 * Returns the feedback for this guess. Null if not analyzed yet.
	 * 
	 * @return
	 */
	public Feedback getFeedback() {
		return feedback;
	}

	/**
	 * Returns the feedback pegs for this guess. Null if not analyzed yet.
	 * 
	 * @return
	 */
	public FeedbackPeg[] getFeedbackPegs() {
		if (feedback == null) {
			return null;
		}
		return feedback.getRawFeedback();
	}

	/**
	 * Sets the code for this guess.
	 * 
	 * @param code
	 *            The code for this guess
	 */
	public void setCode(Code code) {
		this.codeGuess = code;
	}

	/**
	 * Sets the feedback for this guess
	 * 
	 * @param feedback
	 *            The feedback for this guess
	 */
	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	@Override
	public String toString() {
		String ret = "";

		if (feedback == null) {
			ret += "[ E E E E]";
		} else {
			ret += "[";
			for (FeedbackPeg i : feedback.getRawFeedback()) {
				if (i == null) {
					ret += "E ";
				} else {
					ret += i.getName().substring(0, 1) + " ";
				}
			}
			ret += "]";
		}

		ret += codeGuess.toString();

		return ret;
	}
	
	public String getShortCode(){
		return codeGuess.getShortCode();
	}
	
	public String getShortFeedback(){
		return feedback.getShortCode();
	}

}
