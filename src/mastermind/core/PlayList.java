package mastermind.core;

import mastermind.interfaces.Observable;
import mastermind.interfaces.Observer;

public class PlayList extends Observable {

	private Guess[] guessList;
	private static int NUM_OF_ROWS;

	private int lastMoveID;

	/**
	 * Constructor used to build a standard guess play list
	 */
	@Deprecated
	public PlayList() {
		NUM_OF_ROWS = 10;
		guessList = new Guess[NUM_OF_ROWS];
		lastMoveID = 0;
	}

	/**
	 * Constructor used to build a guess play list based on the parameter.
	 * 
	 * @param numberOfPlays
	 *            The number of guesses for this game board
	 */
	public PlayList(int numberOfPlays) {
		if (numberOfPlays < 0 || numberOfPlays > 50) {
			throw new IllegalArgumentException("Number of guesses is invalid");
		}

		NUM_OF_ROWS = numberOfPlays;

		guessList = new Guess[NUM_OF_ROWS];
		lastMoveID = 0;
	}

	public static int getNUM_OF_ROWS() {
		return NUM_OF_ROWS;
	}

	/**
	 * Returns the move specified by the moveID. Null if not played yet.
	 * 
	 * @param moveID
	 *            The number of that move, in order starting at 0
	 * @return The guess object representing that game move or null if this move
	 *         does not exist yet.
	 */
	public Guess getMove(int moveID) {
		return guessList[moveID];
	}

	/**
	 * 
	 * @return The guess object that was most recently added to the array
	 */
	public Guess getLatestMove() {

		if (guessList.length > 0)
			return guessList[this.getLastPlayIndex()];
		else
			return null;
	}

	/**
	 * Add a new guess on the next empty space. Make sure you notify all the
	 * observers after calling this method.
	 */
	private void addGuess() {
		for (int i = 0; i < NUM_OF_ROWS; i++) {
			if (guessList[i] == null) {
				guessList[i] = new Guess();
				lastMoveID = i;
				return;
			}
		}
		// If it gets here then we are full
		throw new IllegalArgumentException(
				"No more spaces to add new moves, all posible moves exhausted");
	}

	/**
	 * Removes the last Guess from the stack
	 */
	private void removeGuess() {
		guessList[this.getLastPlayIndex()] = null;
		if (this.getLastPlayIndex() > 0)
			lastMoveID--;
	}

	/**
	 * Returns the last move played, since we are using an array we don't know
	 * the current size of this array(just the ones initialized)
	 * 
	 * @return an index of the moveID
	 */
	public int getLastPlayIndex() {
		return lastMoveID;
	}

	/**
	 * Adds a new code to the stack of guesses
	 * 
	 * @param code
	 */
	public void addNewCode(Code code) {
		this.addGuess();
		int index = this.getLastPlayIndex();

		this.guessList[index].setCode(code);

		// Notify all observers
		super.dataChanged();
	}

	/**
	 * Add a feedback to the last guess appended to this list. Make sure you
	 * call this after addNewCode so that you don't overwrite the previous
	 * calls.
	 * 
	 * @param feedback
	 */
	public void addFeedbackToLastGuess(Feedback feedback) {
		int index = this.getLastPlayIndex();

		this.guessList[index].setFeedback(feedback);

		// Notify all observers
		super.dataChanged();
	}

	/**
	 * Remove feedback from the most recently played guess
	 */
	public void removeFeedbackFromLastGuess() {
		int index = this.getLastPlayIndex();

		this.guessList[index].setFeedback(null);

		// Notify all observers
		super.dataChanged();
	}

	/**
	 * Delete the last code added to this list.
	 */
	public void deleteNewCode() {
		this.removeGuess();

		// Notify all observers
		super.dataChanged();
	}

	/**
	 * Delete the last feedback from the last move, if the code is also deleted
	 * then the whole move will be deleted.
	 */
	public void deleteLastFeedback() {
		int index = this.getLastPlayIndex();

		if (this.guessList[index].getCode() == null) {
			this.guessList[index] = null;
		} else {
			this.guessList[index].setFeedback(null);
		}

		// Notify all observers
		super.dataChanged();
	}

	/**
	 * Method to get how many guesses have been made
	 * 
	 * @return
	 */
	public int getNumGuesses() {
		return this.guessList.length;
	}

	@Override
	public void register(Observer object) {
		observers.add(object);

		super.dataChanged();
	}

}
