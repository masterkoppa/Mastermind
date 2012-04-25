package mastermind.core;

import java.awt.Color;

public enum FeedbackPeg {

	BLACK("Black", "bk", Color.BLACK), WHITE("White", "wh", Color.WHITE);

	// TODO: Add color codes and possibly a description of each peg, for logging
	// purposes?

	private String name;
	private Color color;
	private String shortName;

	FeedbackPeg(String name, String shortCode, Color color) {
		this.name = name;
		this.color = color;
		this.shortName = shortCode;
	}

	public Color getColor() {
		return this.color;
	}

	public String getName() {
		return this.name;
	}
	
	public String getShortCode(){
		return this.shortName;
	}
}
