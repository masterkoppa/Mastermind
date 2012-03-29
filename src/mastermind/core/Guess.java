package mastermind.core;

public class Guess {
	
	private Feedback feedback;
	private Code codeGuess;
	
	public Guess(){
		codeGuess = new Code();
		feedback = null;
	}
	
	public Code getCode(){
		return codeGuess;
	}
	
	public Feedback getFeedback(){
		return feedback;
	}
	
	public void setFeedback(Feedback feedback){
		this.feedback = feedback;
	}
	
	

}
