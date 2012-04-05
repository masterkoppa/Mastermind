package mastermind.test;

import static org.junit.Assert.*;

import java.io.IOException;

import mastermind.core.Code;
import mastermind.core.ColorPeg;
import mastermind.core.GameModel;
import mastermind.core.Guess;
import mastermind.core.PlayList;
import mastermind.core.commands.ICommand;
import mastermind.core.commands.IMacroCommand;
import mastermind.core.commands.PlayCommand;
import mastermind.core.commands.ProvideFeedbackCommand;
import mastermind.core.commands.SubmitGuessCommand;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Andrew Church
 * 
 */
public class PlayCommandFixture {

	private Code secret;
	private Guess theGuess;
	private Code guessCode;
	private PlayList gamePlays;
	private GameModel theGame;

	@Before
	public void setUp() throws Exception {
		secret = new Code();
		secret.setPegs(0, ColorPeg.BLACK);
		secret.setPegs(1, ColorPeg.BLUE);
		secret.setPegs(2, ColorPeg.WHITE);
		secret.setPegs(3, ColorPeg.YELLOW);

		guessCode = new Code();
		guessCode.setPegs(0, ColorPeg.BLUE);
		guessCode.setPegs(1, ColorPeg.BLACK);
		guessCode.setPegs(2, ColorPeg.WHITE);
		guessCode.setPegs(3, ColorPeg.YELLOW);

		this.theGuess = new Guess();
		this.theGuess.setCode(guessCode);
		gamePlays = new PlayList();
		this.theGame = new GameModel();
	}

	@Test
	public void testAdd() {
		IMacroCommand play = new PlayCommand();
		ICommand submitGuess = null;

		try {
			submitGuess = new SubmitGuessCommand(this.gamePlays,
					this.guessCode.getPegs());
		} catch (IOException io) {
			fail();
		}

		play.add(submitGuess);

		assertTrue(play.stackSize() > 0);
	}

	@Test
	public void testRemove() {
		IMacroCommand play = new PlayCommand();
		ICommand submitGuess = null;

		try {
			submitGuess = new SubmitGuessCommand(this.gamePlays,
					this.guessCode.getPegs());
		} catch (IOException io) {
			fail();
		}

		play.add(submitGuess);

		assertTrue(play.stackSize() > 0);

		play.remove();

		assertTrue(play.stackSize() == 0);
	}

	@Test
	public void testExecute() {
		IMacroCommand play = new PlayCommand();
		ICommand submitGuess = null;
		ICommand getFeedback = null;

		try {
			submitGuess = new SubmitGuessCommand(this.gamePlays,
					this.guessCode.getPegs());
			getFeedback = new ProvideFeedbackCommand(this.theGame,
					this.gamePlays, this.secret.getPegs());
		} catch (IOException io) {
			fail();
		}

		play.add(submitGuess);
		play.add(getFeedback);

		assertTrue(play.stackSize() > 1);

		play.execute();

		assertTrue(this.gamePlays.getLatestMove() != null);
		assertTrue(this.gamePlays.getLatestMove().getFeedback() != null);
	}

	@Test
	public void testUndo() {
		IMacroCommand play = new PlayCommand();
		ICommand submitGuess = null;
		ICommand getFeedback = null;

		try {
			submitGuess = new SubmitGuessCommand(this.gamePlays,
					this.guessCode.getPegs());
			getFeedback = new ProvideFeedbackCommand(this.theGame,
					this.gamePlays, this.secret.getPegs());
		} catch (IOException io) {
			fail();
		}

		play.add(submitGuess);
		play.add(getFeedback);

		assertTrue(play.stackSize() > 1);

		play.execute();

		assertTrue(this.gamePlays.getLatestMove() != null);
		assertTrue(this.gamePlays.getLatestMove().getFeedback() != null);

		play.undo();

		assertTrue(this.gamePlays.getLatestMove() == null);
	}

}
