package mastermind.test;

import mastermind.core.GameModel;
import mastermind.core.PlayList;
import mastermind.core.codemaker.ComputerCodemaker;
import mastermind.core.controller.GameController;
import mastermind.core.controller.IGameController;

import org.junit.Before;

public class ComputerCodemakerTest {

	IGameController controller;

	@Before
	public void setUp() throws Exception {
		GameModel m = new GameModel();
		PlayList playList = new PlayList(10);
		this.controller = new GameController(m, playList, null);
		m.setCodeMaker(new ComputerCodemaker(this.controller));
	}

}
