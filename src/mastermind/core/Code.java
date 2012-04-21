package mastermind.core;

import java.util.Random;

/**
 * Code
 * 
 * This class is used to contain the representation of the code using ColorPeg.
 * 
 * @author Andres J Ruiz(ajr2546@rit.edu)
 * 
 */
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

	/**
	 * Initialize a code with the specified colors in it
	 * 
	 * @param colors
	 *            The desired colors inside the code, an incorrect number of
	 *            pegs inside the array with throw a IllegalArgumentException.
	 */
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
	 * @param id
	 *            The index in the array starting at 0.
	 * @param color
	 *            The color to be placed here
	 */
	public void setPegs(int id, ColorPeg color) {

		//Check whether the id is between 0 and the max number of pegs
		if (id >= NUM_OF_PEGS || id < 0)
			throw new IllegalArgumentException(
					"The index for this peg exeeds the max number of pegs");

		Pegs[id] = color;

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
	 * @return A randomly generated code
	 */
	private static ColorPeg pickRandomPeg() {
		Random r = new Random();
		
		int randomNum = r.nextInt(ColorPeg.NUMBER_OF_COLORS);

		switch (randomNum) {
		case 0:
			return ColorPeg.RED;
		case 1:
			return ColorPeg.BLUE;
		case 2:
			return ColorPeg.GREEN;
		case 3:
			return ColorPeg.YELLOW;
		case 4:
			return ColorPeg.BLACK;
		case 5:
			return ColorPeg.PURPLE;
		default://If on the off chance the number is 6, then we default
			return ColorPeg.BLACK;
		}
	}
}
