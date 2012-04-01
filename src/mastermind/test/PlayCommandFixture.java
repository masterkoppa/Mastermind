package mastermind.test;

import static org.junit.Assert.*;

import mastermind.core.Code;
import mastermind.core.ColorPeg;
import mastermind.core.Guess;
import mastermind.core.PlayList;
import mastermind.core.commands.ICommand;
import mastermind.core.commands.IMacroCommand;
import mastermind.core.commands.PlayCommand;
import mastermind.core.commands.ProvideFeedbackCommand;
import mastermind.core.commands.SubmitGuessCommand;

import org.junit.Before;
import org.junit.Test;

public class PlayCommandFixture {

	Code secret;
	Guess theGuess;
	Code guessCode;
	PlayList gamePlays;
	
	@Before
	public void setUp() throws Exception {
		secret = new Code();
		secret.setPegs(0, ColorPeg.BLACK);
		secret.setPegs(1, ColorPeg.BLUE);
		secret.setPegs(2, ColorPeg.WHITE);
		secret.setPegs(3, ColorPeg.YELLOW);
		
		guessCode = new Code();
		guessCode.setPegs(0,  ColorPeg.BLUE);
		guessCode.setPegs(1, ColorPeg.BLACK);
		guessCode.setPegs(2, ColorPeg.WHITE);
		guessCode.setPegs(3, ColorPeg.YELLOW);
		
		this.theGuess = new Guess();
		this.theGuess.setCode(guessCode);
		gamePlays = new PlayList();
	}

	@Test
	public void testAdd() {
		IMacroCommand play = new PlayCommand();
		
		ICommand submitGuess = new SubmitGuessCommand(this.gamePlays, this.guessCode.getPegs());
		
		play.add(submitGuess);
		
		assertTrue(play.stackSize() > 0);
	}

	@Test
	public void testRemove() {
		IMacroCommand play = new PlayCommand();
		
		ICommand submitGuess = new SubmitGuessCommand(this.gamePlays, this.guessCode.getPegs());
		
		play.add(submitGuess);
		
		assertTrue(play.stackSize() > 0);
		
		play.remove();
		
		assertTrue(play.stackSize() == 0);
	}

	@Test
	public void testExecute() {
		IMacroCommand play = new PlayCommand();
		
		ICommand submitGuess = new SubmitGuessCommand(this.gamePlays, this.guessCode.getPegs());
		ICommand getFeedback = new ProvideFeedbackCommand(this.gamePlays, this.secret.getPegs());
		
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
		
		ICommand submitGuess = new SubmitGuessCommand(this.gamePlays, this.guessCode.getPegs());
		ICommand getFeedback = new ProvideFeedbackCommand(this.gamePlays, this.secret.getPegs());
		
		play.add(submitGuess);
		play.add(getFeedback);
		
		assertTrue(play.stackSize() > 1);
		
		play.execute();
		
		assertTrue(this.gamePlays.getLatestMove() != null);
		assertTrue(this.gamePlays.getLatestMove().getFeedback() != null);
		
		play.undo();
		
		assertTrue(this.gamePlays.getLatestMove().getCode() == null);
	}

}
