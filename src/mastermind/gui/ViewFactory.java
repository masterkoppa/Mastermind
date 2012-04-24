package mastermind.gui;

import javax.swing.JPanel;

import mastermind.core.GameNotStarted;
import mastermind.core.SettingsSelected;
import mastermind.core.SecretCodeSet;
import mastermind.core.GameInProgress;
import mastermind.core.IGameState;
import mastermind.core.controller.IGameController;
import mastermind.interfaces.INotifiable;

public class ViewFactory {
	
	private IGameController theController;
	private INotifiable theFrame;
	
	public ViewFactory(IGameController controller, INotifiable frame)
	{
		if(null == controller)
			throw new IllegalArgumentException();
		
		if(null == frame)
			throw new IllegalArgumentException();
		
		this.theController = controller;
		this.theFrame = frame;
	}
	
	public JPanel getViewForState()
	{
		IGameState theState = theController.getGameState();
			
		if(theState instanceof SettingsSelected)
			return new CodeMakerPanel(this.theController);
		else if(theState instanceof SecretCodeSet)
			return new MastermindBoard(this.theController.getPlaylist());
		
		return new SettingsView(this.theFrame);
	}
}
