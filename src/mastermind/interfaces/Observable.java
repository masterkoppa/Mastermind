package mastermind.interfaces;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

public abstract class Observable {

	protected List<Observer> observers;

	protected Observable() {
		observers = new ArrayList<Observer>();
	}

	public abstract void register(Observer object);

	protected void dataChanged() {
		for (final Observer i : observers) {
			SwingUtilities.invokeLater(new Runnable(){

				@Override
				public void run() {
					i.notifyChange();
				}
				
			});
			
		}
	}
}
