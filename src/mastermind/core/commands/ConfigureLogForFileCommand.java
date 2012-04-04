package mastermind.core.commands;

import java.io.IOException;

import org.apache.commons.io.FileExistsException;

import mastermind.logging.GameLog;

/**
 * 
 * @author Andrew Church
 * 
 * Command to handle configuring the log with a file name
 *
 */
public class ConfigureLogForFileCommand implements IFileCommand {

	private String file;
	
	public ConfigureLogForFileCommand(String fileName)
	{
		if(fileName.isEmpty())
			throw new IllegalArgumentException("Must supply a valid file name");
		
		this.file = fileName;
	}
	
	public void execute() throws IOException, FileExistsException
	{
		GameLog.configureForFileName(file);
	}

	public void undo() throws IOException, FileExistsException
	{
		// TODO Auto-generated method stub
	}

}
