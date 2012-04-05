package mastermind.test;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import mastermind.core.Code;
import mastermind.core.ColorPeg;

public class CodeTest {
	private Code test1;
	private Code test2;

	@Before
	public void setUp() throws Exception {
		ColorPeg[] colors = new ColorPeg[4];
		colors[0] = ColorPeg.BLUE;
		colors[1] = ColorPeg.YELLOW;
		colors[2] = ColorPeg.GREEN;
		colors[3] = ColorPeg.RED;
		test1 = new Code(colors);
		test2 = new Code();
		System.out.println("spacedicks");
	}

	@Test
	public void testGetPegs() {
		ColorPeg[] testcolors = test1.getPegs();
		assertTrue(testcolors[0].getColor() == Color.BLUE);
		assertTrue(testcolors[1].getColor() == Color.YELLOW);
		assertTrue(testcolors[2].getColor() == Color.GREEN);
		assertTrue(testcolors[3].getColor() == Color.RED);
	}

	@Test
	public void testSetPegs() {
		ColorPeg cpeg = ColorPeg.GREEN;
		test2.setPegs(0, cpeg);
		ColorPeg[] testcode = test2.getPegs();
		assert (testcode[0].getColor() == Color.GREEN);
	}

	@Test
	public void testToString() {
		assert (test1.toString().equals("Blue Yellow Green Red "));
	}

	@Test
	public void testRandom() {
		Code r = Code.Random();
		Code c = Code.Random();

		ColorPeg[] rPegs = r.getPegs();
		ColorPeg[] cPegs = c.getPegs();

		int countEquals = 0;

		for (int count = 0; count < Code.NUM_OF_PEGS; count++) {
			ColorPeg rPeg = rPegs[count];
			ColorPeg cPeg = cPegs[count];

			if (rPeg.name() == cPeg.name())
				countEquals++;
		}

		assertTrue(countEquals < Code.NUM_OF_PEGS);
	}
}
