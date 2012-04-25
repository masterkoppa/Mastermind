package mastermind.test;

import java.awt.Color;

import mastermind.core.ColorPeg;

import org.junit.Test;

public class ColorPegTest {

	@Test
	public void testGetName() {
		assert (ColorPeg.BLUE.getName().equalsIgnoreCase("Blue"));
	}

	@Test
	public void testGetColor() {
		assert (ColorPeg.BLUE.getColor().equals(Color.BLUE));
	}

}
