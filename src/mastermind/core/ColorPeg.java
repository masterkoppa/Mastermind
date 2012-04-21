package mastermind.core;

import java.awt.Color;

public enum ColorPeg {

	RED("Red", Color.RED), BLUE("Blue", Color.BLUE), GREEN("Green", Color.GREEN), 
			YELLOW("Yellow", Color.YELLOW), 
			BLACK("Black", Color.BLACK), PURPLE("Purple", Color.getHSBColor(0.83333f, 1.0f, 0.5686275f));

	private String Name;
	private Color ColorForGUI;
	public static int NUMBER_OF_COLORS=5;

	ColorPeg(String name, Color color) {
		Name = name;
		ColorForGUI = color;
	}

	/**
	 * Returns the name for this colored peg
	 * 
	 * @return String spelling out the name of the color, capitalized
	 */
	public String getName() {
		return Name;
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
	
	public static ColorPeg valueFromConsole(String color){
		if(color.compareTo("rd")==0)
			return RED;
		if(color.compareTo("bl")==0)
			return BLUE;
		if(color.compareTo("gr")==0)
			return GREEN;
		if(color.compareTo("ye")==0)
			return YELLOW;
		if(color.compareTo("pu")==0)
			return PURPLE;
		if(color.compareTo("bk")==0)
			return BLACK;
		return null;
	}

}
