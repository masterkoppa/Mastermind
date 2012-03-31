package mastermind.core.commands;

import java.util.*;

public class PlayCommand implements IMacroCommand {
	
	private ArrayList<ICommand> commands;
	
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
