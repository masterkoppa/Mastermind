package mastermind.core.commands;

/**
 * ICommand is the interface for all commands sent from the view to complete
 * various tasks in our project. This interface is part of our implementation of
 * the command pattern.
 */
public interface ICommand {

	/**
	 * Method to execute the command. The implementation will take care of
	 * various tasks.
	 * 
	 * @param None
	 * @return None
	 */
	public void execute();

	/**
	 * If the command has been executed, this allows us to revert the actions it
	 * took.
	 * 
	 * @param None
	 * @return None
	 */
	public void undo();

}
