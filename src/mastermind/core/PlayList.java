package mastermind.core;

import java.util.ArrayList;

import mastermind.interfaces.Observable;
import mastermind.interfaces.Observer;

public class PlayList extends Observable {

	private Guess[] guessList;	
	public static final int NUM_OF_ROWS = 10;
	
	private int lastMoveID;
	
	public PlayList(){
		guessList = new Guess[NUM_OF_ROWS];
		observers = new ArrayList<Observer>();
		lastMoveID = 0;
	}
	
	/**
	 * Returns the move specified by the moveID. Null if not 
	 * played yet.
	 * @param moveID The number of that move, in order starting at 0
	 * @return The guess object representing that game move or null if this
	 * move does not exist yet.
	 */
	public Guess getMove(int moveID){
		return guessList[moveID];
	}
	
	/**
	 * 
	 * @return The guess object that was most recently added to the array
	 */
	public Guess getLatestMove() {

		if(guessList.length > 0)
			return guessList[this.getLastPlayIndex()];
		else
			return null;
	}
	
	/**
	 * Add a new guess on the next empty space.
	 * Make sure you notify all the observers after calling
	 * this method.
	 */
	private void addGuess(){
		for(int i = 0; i < NUM_OF_ROWS; i++){
			if(guessList[i] == null){
				guessList[i] = new Guess();
				lastMoveID = i;
				return;
			}
		}
		//If it gets here then we are full
		throw new IllegalArgumentException("No more spaces to add new moves, all posible moves exhausted");
	}
	
	/**
	 * Returns the last move played, since we are suing a array we don't know
	 * the current size of this array(just the ones initialized)
	 * @return an index of the moveID
	 */
	private int getLastPlayIndex(){
		return lastMoveID;
	}
	
	public void addNewCode(Code code){
		this.addGuess();
		int index = this.getLastPlayIndex();
		
		System.out.println(index);
		
		this.guessList[index].setCode(code);
		
		//Notify all observers
		super.dataChanged();
	}
	
	/**
	 * Add a feedback to the last guess appended to this list.
	 * Make sure you call this after addNewCode so that you don't
	 * overwrite the previous calls.
	 * @param feedback
	 */
	public void addFeedbackToLastGuess(Feedback feedback){
		int index = this.getLastPlayIndex();
		
		this.guessList[index].setFeedback(feedback);
		
		//Notify all observers
		super.dataChanged();
	}
	
	/**
	 * Delete the last code added to this list.
	 */
	public void deleteNewCode(){
		int index = this.getLastPlayIndex();
		this.guessList[index].setCode(null);
		
		//Notify all observers
		super.dataChanged();
	}
	
	/**
	 * Delete the last feedback from the last move,
	 * if the code is also deleted then the whole move
	 * will be deleted.
	 */
	public void deleteLastFeedback(){
		int index = this.getLastPlayIndex();
		
		if(this.guessList[index].getCode() == null){
			this.guessList[index] = null;
		} else{
			this.guessList[index].setFeedback(null);
		}
		
		//Notify all observers
		super.dataChanged();
	}

	@Override
	public void register(Observer object) {
		observers.add(object);
		
		super.dataChanged();
	}
	
	
	
}
