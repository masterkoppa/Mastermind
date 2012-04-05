package mastermind.core;

import java.util.Random;

public class Code {

	/**
	 * Global variable that states the number of pegs available per code. Stated
	 * here in case this needs to be changed in the future.
	 */
	public static final int NUM_OF_PEGS = 4;

	/**
	 * Array that holds all the pegs that make this code
	 */
	private ColorPeg[] Pegs;

	/**
	 * Initialize an empty code, all the color pegs are null at this point
	 */
	public Code() {
		Pegs = new ColorPeg[NUM_OF_PEGS];
	}

	public Code(ColorPeg[] colors) {
		if (colors.length > NUM_OF_PEGS)
			throw new IllegalArgumentException("Too many colors!");
		else if (colors.length < NUM_OF_PEGS)
			throw new IllegalArgumentException("Too few colors!");

		this.Pegs = colors;
	}

	/**
	 * Returns an array of pegs that make up this code. For empty spaces the
	 * array will have null in that spot.
	 * 
	 * @return ColorPeg Array with ColorPeg Objects representing the pegs, null
	 *         for empty spaces
	 */
	public ColorPeg[] getPegs() {
		return this.Pegs;
	}

	/**
	 * Allows you to set a specific color to a position in this code
	 * 
	 * @param ID
	 *            The index in the array starting at 0.
	 * @param color
	 *            The color to be placed here
	 */
	public void setPegs(int ID, ColorPeg color) {

		if (ID >= NUM_OF_PEGS)
			throw new IllegalArgumentException(
					"The index for this peg exeeds the max number of pegs");

		Pegs[ID] = color;

	}

	/**
	 * Factory method to randomly generate a code for a guess
	 * 
	 * @return
	 */
	public static Code Random() {
		Code c = new Code();

		for (int count = 0; count < 4; count++) {
			c.setPegs(count, Code.pickRandomPeg());
		}

		return c;
	}

	@Override
	public String toString() {
		String ret = "";

		for (ColorPeg i : Pegs) {
			if (i == null) {
				ret += "E ";
			} else {
				ret += i.getName() + " ";
			}
		}

		return ret;
	}

	public boolean isEmpty() {
		for (int i = 0; i < NUM_OF_PEGS; i++) {
			if (Pegs[i] == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Randomly selects a peg color
	 * 
	 * @return
	 */
	private static ColorPeg pickRandomPeg() {
		Random r = new Random();
		int randomNum = r.nextInt(5) + 1;

		switch (randomNum) {
		case 1:
			return ColorPeg.RED;
		case 2:
			return ColorPeg.BLUE;
		case 3:
			return ColorPeg.GREEN;
		case 4:
			return ColorPeg.YELLOW;
		case 5:
			return ColorPeg.WHITE;
		case 6:
			return ColorPeg.BLACK;
		default:
			return ColorPeg.BLACK;
		}
	}
}
