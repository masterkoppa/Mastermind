package mastermind.gui;

import javax.swing.JPanel;

import mastermind.core.GameNotStarted;
import mastermind.core.SettingsSelected;
import mastermind.core.SecretCodeSet;
import mastermind.core.IGameState;
import mastermind.core.controller.IGameController;
import mastermind.interfaces.INotifiable;
import mastermind.interfaces.Observer;

public class ViewFactory {
	
	private IGameController theController;
	private Observer theFrame;
	
	public ViewFactory(IGameController controller, Observer frame)
	{
		if(null == controller)
			throw new IllegalArgumentException();
		
		if(null == frame)
			throw new IllegalArgumentException();
		
		this.theController = controller;
		this.theFrame = frame;
	}
	
	/**
	 * This method is a factory method to return the correct JPanel for the given state of the game
	 * @return
	 */
	public JPanel getViewForState()
	{
		IGameState theState = theController.getGameState();
			
		if(theState instanceof GameNotStarted) {
			return new SettingsView(this.theController);
		} else if(theState instanceof SettingsSelected) {
			if (theController.isCodemakerComputer()) {
				return new MastermindMain(this.theController).getView();
			} else {
				return new CodeMakerPanel(this.theController);
			}
		} else if(theState instanceof SecretCodeSet) {
			return new MastermindMain(this.theController).getView();
		}
		
		throw new IllegalStateException();
	}
}
