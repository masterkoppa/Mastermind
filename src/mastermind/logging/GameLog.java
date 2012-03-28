package mastermind.logging;

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
	
	private static ConfigureNewGameLogger()
	{
		//TODO: Configure Log4J
	}
}
