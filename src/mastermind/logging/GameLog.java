package mastermind.logging;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileExistsException;

import org.apache.log4j.*;

public class GameLog 
{
	private static GameLog currentLogger;
	private Logger logInfrastructure;
	private ArrayList<String> logMessages;
	
	private GameLog() throws IOException
	{
		this.logMessages = new ArrayList<String>();
		this.logInfrastructure = this.ConfigureNewGameLogger(null);
	}
	
	private GameLog(String fileName) throws IOException
	{
		if(this.logMessages == null)
			this.logMessages = new ArrayList<String>();
		
		this.logInfrastructure = this.ConfigureNewGameLogger(fileName);
	}
	
	/**
	 * 
	 * @return Instance of GameLog
	 * @throws IOException
	 */
	public static synchronized GameLog getInstance() throws IOException
	{
		if(null == currentLogger)
			currentLogger = new GameLog();
		
		return currentLogger;
	}
	
	/**
	 * 
	 * @param fileName the file name to configure
	 * @throws FileExistsException Throws if the file specified already exists
	 * @throws IOException
	 */
	public static synchronized void configureForFileName(String fileName, Boolean overwriteFile) throws FileExistsException, IOException
	{
		if(null == fileName || fileName.isEmpty())
			throw new IllegalArgumentException("Must supply a valid file name");
		
		File f = new File(fileName);
		
		if(f.exists() && !overwriteFile) 
		{
			throw new FileExistsException("Game Log for that file name already exists");
		}
		else
		{
			currentLogger = new GameLog(fileName);
		}
	}
	
	/**
	 * 
	 * @param message The message to write to the log
	 */
	public synchronized void write(String message)
	{
		if(null != message && !message.isEmpty())
		{
			this.logInfrastructure.info(message);
			this.logMessages.add(message);
		}
	}
	
	/**
	 * 
	 * @param fileName The file name to configure
	 * @return
	 * @throws IOException
	 */
	private Logger ConfigureNewGameLogger(String fileName) throws IOException
	{
		String file;
		
		if(fileName == null || fileName.isEmpty())
			file = "game_log.txt";
		else
			file = fileName;
	
		Logger l = Logger.getLogger(GameLog.class);
		l.setLevel((Level) Level.DEBUG);
		
		SimpleLayout sl = new SimpleLayout();
		FileAppender fa = null;
		
		fa = new FileAppender(sl, file, false);
		l.addAppender(fa);
		
		l.debug("Logger created");
		
		if(this.logMessages.size() > 0)
		{
			for(String s : this.logMessages)
			{
				l.info(s);
			}
		}
		
		return l;
	}
}
