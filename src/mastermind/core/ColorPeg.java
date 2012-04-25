package mastermind.core;

import java.awt.Color;

public enum ColorPeg {

	RED("Red", "rd", Color.RED), BLUE("Blue", "bl", Color.BLUE), GREEN("Green",
			"gr", Color.GREEN), YELLOW("Yellow", "ye", Color.YELLOW), PURPLE(
			"Purple", "pu", Color.getHSBColor(0.83333f, 1.0f, 0.5686275f));

	private String Name;
	private Color ColorForGUI;
	private String ShortName;

	public static int NUMBER_OF_COLORS = 5;

	ColorPeg(String name, String shortHand, Color color) {
		Name = name;
		ColorForGUI = color;
		ShortName = shortHand;
	}

	/**
	 * Returns the name for this colored peg
	 * 
	 * @return String spelling out the name of the color, capitalized
	 */
	public String getName() {
		return Name;
	}

	public String getShortName() {
		return ShortName;
	}

	/**
	 * Returns a color java.awt.Color representing the color for this peg Useful
	 * when dealing with GUI Objects
	 * 
	 * @return Color that represents this peg
	 */
	public Color getColor() {
		return ColorForGUI;
	}
	
	/**
	 * Returns a color peg based on the short code sent in, useful for
	 * the command line interface.
	 * 
	 * @param color The color 
	 * @return 
	 */
	public static ColorPeg valueFromConsole(String color) {
		for(ColorPeg i :ColorPeg.values()){
			if(i.getShortName().equalsIgnoreCase(color)){
				return i;
			}
		}
		return null;
	}

}
