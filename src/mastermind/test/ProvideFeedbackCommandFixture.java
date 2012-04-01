package mastermind.test;

import static org.junit.Assert.*;

import mastermind.core.Code;
import mastermind.core.ColorPeg;

import org.junit.Before;
import org.junit.Test;

public class ProvideFeedbackCommandFixture {

	private Code secret;
	
	@Before
	public void setUp() throws Exception {
		secret = new Code();
		secret.setPegs(0, ColorPeg.BLACK);
		secret.setPegs(1, ColorPeg.BLUE);
		secret.setPegs(2, ColorPeg.WHITE);
		secret.setPegs(3, ColorPeg.YELLOW);
	}

	@Test
	public void testExecute() {
		fail("Not yet implemented");
	}

	@Test
	public void testUndo() {
		fail("Not yet implemented");
	}

}
