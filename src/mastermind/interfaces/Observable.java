package mastermind.interfaces;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
	
	protected List<Observer> observers;
	
	protected Observable(){
		observers = new ArrayList<Observer>();
	}
	
	public abstract void register(Observer object);
	
	protected void dataChanged(){
		for(Observer i : observers){
			i.notifyChange();
		}
	}
}
