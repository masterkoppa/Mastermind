package mastermind.test;

import static org.junit.Assert.*;
import mastermind.core.commands.*;
import mastermind.core.controller.*;

import org.junit.Test;

public class GameControllerTest {

	@Test
	public void testHistoryAdd() {
		ICommand cmd1 = new PlayCommand();
		GameController c = new GameController();
		c.executeCommand(cmd1);
		assertEquals(c.getHistory().size(), 1);
	}

	@Test
	public void testUndo() {
		ICommand cmd1 = new PlayCommand();
		GameController c = new GameController();
		c.executeCommand(cmd1);
		c.undoCommand();
		assertEquals(c.getHistory().size(), 1);
	}

	@Test
	public void testUndoAndHistoryAdd() {
		ICommand cmd1 = new PlayCommand();
		ICommand cmd2 = new PlayCommand();
		GameController c = new GameController();
		c.executeCommand(cmd1);
		c.undoCommand();
		c.executeCommand(cmd2);
		assertEquals(c.getHistory().size(), 1);
	}

}
