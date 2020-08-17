package fx.soft.pixelengine.utils.time;

import java.util.Timer;

/**
 * Interface to use in timer tasks
 */
public interface RunnableInterval {
	
	/**
	 * <code>run</code> method to be called in that separately executing
	 *
	 * @param total number of total calls
	 * @param timer current timer object
	 */
	void run(double total, Timer timer);
	
}
