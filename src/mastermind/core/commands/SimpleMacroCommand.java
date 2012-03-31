package mastermind.core.commands;

import java.util.ArrayList;

public class SimpleMacroCommand implements IMacroCommand {
	
	private ArrayList<ICommand> commands;

	public SimpleMacroCommand(){
		commands = new ArrayList<ICommand>();
	}
	
	@Override
	public void Add(ICommand command) {
		commands.add(command);
	}

	@Override
	public void Remove() {
		commands.remove(commands.size()-1);
	}

	@Override
	public void execute() {
		for(ICommand i : commands){
			i.execute();
		}
	}

}
