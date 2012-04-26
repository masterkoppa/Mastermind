package mastermind.core.codemaker;

import mastermind.core.Code;
import mastermind.core.controller.IGameController;

public class ComputerCodemaker implements ICodemaker {

	IGameController theController;

	public ComputerCodemaker(IGameController controller) {
		if (null == controller)
			throw new IllegalArgumentException();

		theController = controller;
	}

	@Override
	public void setSecretCode() {
		
		
		Boolean validCode = false;
		
		do
		{
			try
			{	
				Code c = Code.Random();

				theController.setSecretCode(c);
				validCode = true;
			} catch (IllegalArgumentException iae) {
				validCode = false;
			}
		} while (!validCode);
	}

}
