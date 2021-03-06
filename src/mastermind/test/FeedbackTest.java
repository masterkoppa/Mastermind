package mastermind.test;

import static org.junit.Assert.*;

import mastermind.core.*;

import org.junit.Before;
import org.junit.Test;

public class FeedbackTest {

	Code secret;
	Code guess;

	@Before
	public void setUp() throws Exception {
		// Setup the basic secret code
		secret = new Code();
		secret.setPegs(0, ColorPeg.GREEN);
		secret.setPegs(1, ColorPeg.BLUE);
		secret.setPegs(2, ColorPeg.PURPLE);
		secret.setPegs(3, ColorPeg.YELLOW);
	}

	@Test
	public void testPerfectMatch() {
		Feedback f = Feedback.analyze(secret, secret);

		assertTrue(f.isGameOver());
		assertEquals("Wrong Black Count", f.getBlackCount(), 4);
	}

	@Test
	public void testAllWhitePegs() {
		guess = new Code();
		guess.setPegs(2, ColorPeg.GREEN);
		guess.setPegs(3, ColorPeg.BLUE);
		guess.setPegs(1, ColorPeg.PURPLE);
		guess.setPegs(0, ColorPeg.YELLOW);

		Feedback f = Feedback.analyze(secret, guess);

		assertFalse(f.isGameOver());
		assertEquals("Wrong Black Count", f.getBlackCount(), 0);
		assertEquals("Wrong White Count", f.getWhiteCount(), 4);
	}

	@Test
	public void testHalfAndHalf() {
		// Half black pegs, half white pegs
		guess = new Code();
		guess.setPegs(0, ColorPeg.GREEN);
		guess.setPegs(2, ColorPeg.BLUE);
		guess.setPegs(1, ColorPeg.PURPLE);
		guess.setPegs(3, ColorPeg.YELLOW);

		Feedback f = Feedback.analyze(secret, guess);

		assertEquals("Wrong White Count", f.getWhiteCount(), 2);
		assertEquals("Wrong Black Count", f.getBlackCount(), 2);
		assertFalse(f.isGameOver());
	}

	@Test
	public void duplicateInGuess() {
		// Have duplicate colors in the guess
		guess = new Code();
		guess.setPegs(0, ColorPeg.GREEN);
		guess.setPegs(2, ColorPeg.BLUE);
		guess.setPegs(1, ColorPeg.PURPLE);
		guess.setPegs(3, ColorPeg.GREEN);

		Feedback f = Feedback.analyze(secret, guess);

		assertEquals("The first peg's feedback is wrong",
				f.getRawFeedback()[0], FeedbackPeg.BLACK);
		assertEquals("The last peg's feedback is wrong", f.getRawFeedback()[3],
				FeedbackPeg.WHITE); // The duplicate
		assertFalse(f.isGameOver());
	}

}
