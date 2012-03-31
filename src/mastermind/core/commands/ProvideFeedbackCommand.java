package mastermind.core.commands;
import mastermind.core.ColorPeg;
import mastermind.core.PlayList;

public class ProvideFeedbackCommand extends PlayListCommand implements ICommand {
	
	public ProvideFeedbackCommand(PlayList listOfGuesses, ColorPeg[] colors)
	{
		super(listOfGuesses, colors);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}



}
