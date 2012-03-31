package mastermind.core;

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
	private void addGuess(){
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
	private int getLastPlayIndex(){
		for(int i = 0; i < NUM_OF_ROWS; i++){
			if(guessList[i] == null){
				return i-1;
			}
		}
		
		return NUM_OF_ROWS-1;
	}
	
	public void addNewCode(Code code){
		this.addGuess();
		int index = this.getLastPlayIndex();
		
		this.guessList[index].setCode(code);
	}
	
	public void addFeedbackToLastGuess(Feedback feedback){
		int index = this.getLastPlayIndex();
		
		this.guessList[index].setFeedback(feedback);
	}
	
	public void deleteNewCode(){
		int index = this.getLastPlayIndex();
		this.guessList[index].setCode(null);
	}
	
	public void deleteLastFeedback(){
		int index = this.getLastPlayIndex();
		
		if(this.guessList[index].getCode() == null){
			this.guessList[index] = null;
		} else{
			this.guessList[index].setFeedback(null);
		}
	}
	
	
	
}
