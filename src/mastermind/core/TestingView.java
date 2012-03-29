package mastermind.core;

import mastermind.interfaces.Observer;

public class TestingView implements Observer{
	
	public TestingView(){
		this.register(); //Register with the models
	}

	@Override
	public void register() {
		
	}

	@Override
	public void notifyChange() {
		
	}

}
