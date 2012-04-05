package mastermind.core.codebreaker;

import mastermind.core.Code;
import mastermind.core.ColorPeg;
import mastermind.core.controller.*;

/**
 * A completely random guess algorithm for a ComputerCodebreaker.
 * 
 * @author Matt Addy <mxa5942>
 * 
 */
public class RandomGuess implements ComputerGuessBehavior {

	IGameController controller;

	public RandomGuess(IGameController controller) {
		this.controller = controller;
	}

	@Override
	public void guess() {
		ColorPeg[] pegs = Code.Random().getPegs();
		controller.submitGuess(pegs);
	}

}
