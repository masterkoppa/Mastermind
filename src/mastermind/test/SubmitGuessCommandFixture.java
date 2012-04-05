package mastermind.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.*;
import mastermind.core.*;
import mastermind.core.commands.*;

public class SubmitGuessCommandFixture {

	@Test
	public void createCommandTest() {
		ColorPeg[] colors = new ColorPeg[4];

		colors[0] = ColorPeg.BLACK;
		colors[1] = ColorPeg.GREEN;
		colors[2] = ColorPeg.RED;
		colors[3] = ColorPeg.WHITE;

		PlayList plays = new PlayList();
		ICommand command = null;

		try {
			command = new SubmitGuessCommand(plays, colors);
		} catch (IOException io) {
			fail();
		}

		command.execute();

		Guess g = new Guess();
		g.setCode(new Code(colors));

		try {
			assertTrue(plays.getMove(0) != null);
			assertTrue(plays.getMove(0).getCode() != null);
		} catch (IndexOutOfBoundsException e) {
			fail("Didn't properly add the guess");
		}
	}
}
