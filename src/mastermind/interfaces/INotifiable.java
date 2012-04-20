package mastermind.interfaces;

public interface INotifiable {
	/**
	 * Notify the class of a change.
	 * 
	 * This is a variation of the observer pattern where we don't have observables,
	 * the call of this method is not to provoke this class to look for the information
	 * that changed. Instead it's telling the class to move along.
	 */
	public void Notify();
}
