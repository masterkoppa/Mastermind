package mastermind.interfaces;

/**
 * Interface for the Observable Pattern
 * 
 * @author Andres J Ruiz(ajr2546@rit.edu)
 *
 */
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
