package mastermind.test;

import static org.junit.Assert.*;

import mastermind.core.Code;
import mastermind.core.ColorPeg;
import mastermind.core.Guess;
import mastermind.core.PlayList;
import mastermind.core.commands.ICommand;
import mastermind.core.commands.ProvideFeedbackCommand;

import org.junit.Before;
import org.junit.Test;

public class ProvideFeedbackCommandFixture {

	private Code secret;
	private PlayList gamePlays;
	private Code guessCode;
	private Guess theGuess;
	
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
		this.gamePlays = new PlayList();
	}

	@Test
	public void testExecute() {
		this.gamePlays.addNewCode(this.guessCode);
		
		ICommand feedback = new ProvideFeedbackCommand(this.gamePlays, this.secret.getPegs());
		
		feedback.execute();
		
		assertTrue(this.gamePlays.getLatestMove().getFeedback() != null);
		
	}

	@Test
	public void testUndo() {
		this.gamePlays.addNewCode(this.guessCode);
		
		ICommand feedback = new ProvideFeedbackCommand(this.gamePlays, this.secret.getPegs());
		
		feedback.execute();
		
		assertTrue(this.gamePlays.getLatestMove().getFeedback() != null);
		
		feedback.undo();
		
		assertTrue(this.gamePlays.getLatestMove().getFeedback() == null);
	}

}
