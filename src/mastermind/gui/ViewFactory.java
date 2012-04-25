package mastermind.gui;

import javax.swing.JPanel;

import mastermind.core.GameNotStarted;
import mastermind.core.SettingsSelected;
import mastermind.core.SecretCodeSet;
import mastermind.core.IGameState;
import mastermind.core.controller.IGameController;

/**
 * This class provides access to factory methods for generating Gui JPanels for the system.
 * 
 * @author Andrew Church
 *
 */
public class ViewFactory {

	private IGameController theController;

	public ViewFactory(IGameController controller) {
		if (null == controller)
			throw new IllegalArgumentException();

		this.theController = controller;
	}

	/**
	 * This method is a factory method to return the correct JPanel for the
	 * given state of the game
	 * 
	 * @return
	 */
	public JPanel getViewForState() {
		IGameState theState = theController.getGameState();

		if (theState instanceof GameNotStarted) {
			return new SettingsView(this.theController);
		} else if (theState instanceof SettingsSelected) {
			if (theController.isCodemakerComputer()) {
				return new MastermindMain(this.theController).getView();
			} else {
				return new CodeMakerPanel(this.theController);
			}
		} else if (theState instanceof SecretCodeSet) {
			return new MastermindMain(this.theController).getView();
		}

		throw new IllegalStateException();
	}
}
