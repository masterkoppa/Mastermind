package mastermind.core.codebreaker;

import java.util.Timer;
import java.util.TimerTask;
import mastermind.core.controller.*;
import mastermind.core.*;

/**
 * A class that represents a Computer Codebreaker.
 * 
 * ComputerCodebreaker uses the Strategy pattern to delegate the guessing
 * algorithm.
 * 
 * @author Matt Addy <mxa5942@rit.edu>
 * 
 */
public class ComputerCodebreaker {

	/** The guess strategy */
	private ComputerGuessBehavior guessBehavior;

	/** The actual Timer the guesses run on */
	private Timer guessTimer;

	/** The delay the Timer runs on (immediate - 30 seconds) */
	private int delay;

	/**
	 * Create a ComputerCodeBreaker.
	 * 
	 * @param delay
	 *            the delay in seconds
	 * @param guessBehavior
	 *            the guessing algorithm used by this codebreaker
	 */
	public ComputerCodebreaker(int delay, ComputerGuessBehavior guessBehavior) {
		this.delay = delay;
		this.guessBehavior = guessBehavior;
		this.guessTimer = new Timer();
	}

	/**
	 * Start the guessing by this codebreaker.
	 */
	public void start() {
		guessTimer.schedule(new TimerTask() {
			public void run() {
				guessBehavior.guess();
			}
		}, 0, delay);
	}

	/**
	 * Stop the guessing by this codebreaker.
	 */
	public void stop() {
		guessTimer.cancel();
	}

	/**
	 * Change the guessing algorithm for this codebreaker.
	 * 
	 * @param guessBehavior
	 *            the guessing algorithm to switch to
	 */
	public void setGuessBehavior(ComputerGuessBehavior guessBehavior) {
		this.guessBehavior = guessBehavior;
	}

}
