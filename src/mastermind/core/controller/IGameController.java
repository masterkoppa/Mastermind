package mastermind.core.controller;

import mastermind.core.ColorPeg;
import mastermind.core.commands.ICommand;

public interface IGameController {
	
	public void executeCommand(ICommand command);
	
	public void submitGuess(ColorPeg[] code);
	
	public void startAI(int delaySeconds);
	
	public void stopAI();
	
	public void configureLog(String fileName);// TBD
	
	public void undoCommand();
	
}
