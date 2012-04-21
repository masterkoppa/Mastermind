package mastermind.core.codemaker;

import mastermind.core.Code;
import mastermind.core.controller.IGameController;

public class ComputerCodemaker implements ICodemaker {

	IGameController theController;
	
	public ComputerCodemaker(IGameController controller)
	{
		if(null == controller)
			throw new IllegalArgumentException();
		
		theController = controller;
	}
	
	@Override
	public void setSecretCode() {
		Code c = Code.Random();
		
		Boolean validCode = false;
		
		do
		{
			try
			{
				theController.setSecretCode(c);
				validCode = true;
			}
			catch(IllegalArgumentException iae)
			{
				validCode = false;
			}
		}
		while(!validCode);
	}
	
}
