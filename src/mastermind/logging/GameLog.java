package mastermind.logging;

import org.apache.log4j.*;

public class GameLog 
{
	private static Logger theLog;
	
	GameLog()
	{
		
	}
	
	public static synchronized Logger getInstance()
	{
		if(null == theLog)
			theLog = ConfigureNewGameLogger();
		
		return theLog;
	}
	
	private static Logger ConfigureNewGameLogger()
	{
		return null;
		
		//TODO: Configure Log4J
	}
}
