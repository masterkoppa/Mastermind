package mastermind.logging;

import java.io.IOException;

import org.apache.log4j.*;

public class GameLog 
{
	private static Logger theLog;
	
	public static synchronized Logger getInstance()
	{
		if(null == theLog)
			theLog = ConfigureNewGameLogger();
		
		return theLog;
	}
	
	private static Logger ConfigureNewGameLogger()
	{
		Logger l = Logger.getLogger(GameLog.class);
		SimpleLayout sl = new SimpleLayout();
		FileAppender fa = null;
		
		try 
		{
			fa = new FileAppender(sl, "game_log.txt", false);
			l.addAppender(fa);
		} 
		catch (IOException e) 
		{
			ConsoleAppender ca = new ConsoleAppender();
			l.addAppender(ca);
		}
		
		l.debug("Logger created");
		
		return l;
	}
}
