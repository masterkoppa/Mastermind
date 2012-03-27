package mastermind.core;

import java.awt.Color;

public enum ColorPeg {
	
	RED("Red", Color.RED),
	BLUE("Blue", Color.BLUE),
	GREEN("Green", Color.GREEN),
	YELLOW("Yello", Color.YELLOW),
	WHITE("White", Color.WHITE),
	BLACK("Black", Color.BLACK);
	
	private String Name;
	private Color ColorForGUI;
	
	ColorPeg(String name, Color color){
		Name = name;
		ColorForGUI = color;
	}
	/**
	 * Returns the name for this colored peg
	 * @return String spelling out the name of the color, capitalized
	 */
	public String getName(){
		return Name;
	}
	
	/**
	 * Returns a color java.awt.Color representing the color for this peg
	 * Useful when dealing with GUI Objects
	 * @return Color that represents this peg
	 */
	public Color getColor(){
		return ColorForGUI;
	}

}
