package mastermind.interfaces;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

/**
 * An observable object is usually a model or class that will hold information
 * that other classes might be interested in. Following the Observer pattern
 * this class will notify every observer that wants information from it and is
 * registered to it.
 * 
 * 
 * @author Andres J Ruiz(ajr2546@rit.edu)
 * 
 */
public abstract class Observable {

	/**
	 * All the registered observers, any classes that want to re-implement this
	 * can use any kind of list the wish, if they wanted to change it for
	 * whatever reason
	 */
	protected List<Observer> observers;

	/**
	 * Constructor that will initialize the list of observers
	 */
	protected Observable() {
		observers = new ArrayList<Observer>();
	}

	/**
	 * Register to be notified when this class changes
	 * 
	 * @param object
	 *            Pass in a observer object that is interested in keeping track
	 *            of information stored here
	 */
	public abstract void register(Observer object);

	/**
	 * Data has changed, this method will call the notify methods on all the
	 * registered observers of this object
	 */
	protected void dataChanged() {
		for (final Observer i : observers) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					i.notifyChange();
				}

			});

		}
	}
}
