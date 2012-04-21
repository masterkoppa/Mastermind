package mastermind.cui;

/**
 * ICommand is the interface for all commands sent from the view to complete
 * various tasks in our project. This interface is part of our implementation of
 * the command pattern.
 */
public interface IConsoleController {

		/**
		 * .
		 * 
		 * @param None
		 * @return 
		 * @return None
		 */
		public void setModeOfPlay(int mode);

		public void setCodeMaker(int type);
		
		public void setCodeBreaker(int type);
		
		public String[] getCodeBreakerAlgorithms();
		
		public void setSecretCode(String[] code);
		
		public void makeGuess(String[] code);
		
		public String[] getLastGuess();
		
		public String[] getLastFeedback();

		public void newGame();
		
		public void restartGame();
		
		public void exitGame();

		public void setAlgorithm(int algorithm);

		public void startGame();		
		
}
