package mastermind.core;

public class Feedback {

	// TODO Shuffle the response to provide some sort of randomness
	// in the response.

	private FeedbackPeg[] feedback;

	private Feedback() {
		feedback = new FeedbackPeg[Code.NUM_OF_PEGS];
	}

	/**
	 * Analyzes the secret code versus the guess and returns a immutable
	 * feedback object containing the feedback from the system to be shown to
	 * the user.
	 * 
	 * @param secretCode
	 *            - Validated code containing the secret code made by the code
	 *            maker
	 * @param guess
	 *            - Validated code containing the guess made by the code breaker
	 * @return The result from analysis of the secret code and the guess
	 */
	public static Feedback analyze(Code secretCode, Code guess) {
		Feedback howClose = new Feedback(); // How close the guess is :D

		ColorPeg[] secretCodePegs = secretCode.getPegs();
		ColorPeg[] guessPegs = guess.getPegs();

		// Ideally this line could be substituted by the loop in the else
		// but optimally this would be a quick and dirty check
		// instead of looping many times.
		if (secretCodePegs == guessPegs) {
			// Set all black pegs
			for (int i = 0; i < Code.NUM_OF_PEGS; i++) {
				howClose.feedback[i] = FeedbackPeg.BLACK;
			}
		} else {
			// Check the first one in the guess
			for (int i = 0; i < Code.NUM_OF_PEGS; i++) {
				for (int u = 0; u < Code.NUM_OF_PEGS; u++) {
					if (i == u && guessPegs[i] == secretCodePegs[u]) {
						// Set the peg at i to black
						// Same position same color
						howClose.feedback[i] = FeedbackPeg.BLACK;
						break;
					} else if (guessPegs[i] == secretCodePegs[u]) {
						// Set the peg to white
						// Same color but wrong position
						howClose.feedback[i] = FeedbackPeg.WHITE;
					}
				}
			}
		}

		return howClose;
	}

	/**
	 * Is the game over, does the code match the secret code?
	 * 
	 * Run the code breaker has made it!!
	 * 
	 * @return Are all the pegs for this feedback black
	 */
	public boolean isGameOver() {

		for (FeedbackPeg peg : feedback) {
			// If any peg isn't black error out
			if (peg != FeedbackPeg.BLACK)
				return false;
		}
		// All the pegs were black
		return true;
	}

	/**
	 * Count the number of white pegs, useful for testing and to randomize the
	 * showing of the positions of the pegs.
	 * 
	 * @return The count of white pegs in this feedback
	 */
	public int getWhiteCount() {
		int count = 0;
		for (int i = 0; i < Code.NUM_OF_PEGS; i++) {
			if (feedback[i] == FeedbackPeg.WHITE)
				count++;
		}
		return count;
	}

	/**
	 * Count the number of black pegs, useful for testing and to randomize the
	 * showing of the positions of the pegs.
	 * 
	 * @return The count of black pegs in this feedback
	 */
	public int getBlackCount() {
		int count = 0;
		for (int i = 0; i < Code.NUM_OF_PEGS; i++) {
			if (feedback[i] == FeedbackPeg.BLACK)
				count++;
		}
		return count;
	}

	public FeedbackPeg[] getRawFeedback() {
		return this.feedback;
	}

	/**
	 * Prints out the peg representation
	 */
	public String toString() {
		String ret = "";

		for (int i = 0; i < feedback.length; i++) {
			if (feedback[i] == null) {
				ret += " EMPTY";
			} else if (feedback[i] == FeedbackPeg.BLACK) {
				ret += " BLACK";
			} else if (feedback[i] == FeedbackPeg.WHITE) {
				ret += " WHITE";
			}
		}

		return ret;
	}

}
