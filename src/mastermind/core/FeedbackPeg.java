package mastermind.core;

import java.awt.Color;

public enum FeedbackPeg {
	
	BLACK("Black", Color.BLACK),
	WHITE("White", Color.WHITE);
	
	//TODO: Add color codes and possibly a description of each peg, for logging purposes?
	
	private String name;
	private Color color;
	
	FeedbackPeg(String name, Color color){
		this.name = name;
		this.color = color;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public String getName(){
		return this.name;
	}
}
