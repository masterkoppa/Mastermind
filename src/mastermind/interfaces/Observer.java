package mastermind.interfaces;

public interface Observer {

	/**
	 * Register with the observable
	 */
	public void register();

	/**
	 * Method called by the observable objects that their data has changes
	 */
	public void notifyChange();
}
