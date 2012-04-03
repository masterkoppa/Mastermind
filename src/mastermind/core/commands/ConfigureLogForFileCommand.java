package mastermind.core.commands;

import java.io.IOException;

import org.apache.commons.io.FileExistsException;

import mastermind.logging.GameLog;

public class ConfigureLogForFileCommand implements IFileCommand {

	private String file;
	
	public ConfigureLogForFileCommand(String fileName)
	{
		if(null == file || fileName.isEmpty())
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
