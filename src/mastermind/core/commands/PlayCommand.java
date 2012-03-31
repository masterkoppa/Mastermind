package mastermind.core.commands;
import java.util.*;

/**
 * PlayCommand holds a list of various commands that need to be executed.
 * These commands take care of functions such as Submit guess and request
 * feedback.
 */
public class PlayCommand implements IMacroCommand {
	
	/** List of commands to be executed */
	private ArrayList<ICommand> commands;
	
	/** Initializes the container for commands */
	public PlayCommand()
	{
		commands = new ArrayList<ICommand>();
	}
	
	//IMacroCommand
	public void add(ICommand command)
	{
		if(null == command)
			throw new IllegalArgumentException("Must supply a valid command");
		
		this.commands.add(command);
	}
	
	//IMacroCommand
	public void remove()
	{
		if(this.commands.size() > 0)
			this.commands.remove(this.commands.size()-1);
	}
	
	//IMacroCommand
	public void execute() 
	{	
		for(ICommand c : this.commands)
		{
			c.execute();
		}
	}

	//IMacroCommand
	public void undo() 
	{
		for(int count = this.commands.size() - 1; count >= 0; count--)
		{
			this.commands.get(count).undo();
		}
	}

}
