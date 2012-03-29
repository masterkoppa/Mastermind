package mastermind.core;

import java.util.*;

public class PlayList {

	private Guess[] guessList;
	
	public static final int NUM_OF_ROWS = 10;
	
	public PlayList(){
		guessList = new Guess[NUM_OF_ROWS];
	}
	
	public Guess getMove(int moveID){
		return guessList[moveID];
	}
	
	/**
	 * Add a new guess on the next empty space
	 */
	public void addGuess(){
		for(int i = 0; i < NUM_OF_ROWS; i++){
			if(guessList[i] == null){
				guessList[i] = new Guess();
				return;
			}
		}
		//If it gets here then we are full
		throw new IllegalArgumentException("No more spaces to add new moves, all posible moves exhausted");
	}
	
	/**
	 * 
	 * @return -1 if no moves, otherwise an index of the moveID
	 */
	public int getLastPlayIndex(){
		for(int i = 0; i < NUM_OF_ROWS; i++){
			if(guessList[i] == null){
				return i-1;
			}
		}
		
		return NUM_OF_ROWS-1;
	}
	
}
