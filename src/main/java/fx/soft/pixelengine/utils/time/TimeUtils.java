package fx.soft.pixelengine.utils.time;

import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Time utilities
 */
public final class TimeUtils {
	
	/**
	 * Call {@code runnable} parameter after selected time
	 *
	 * @param timeTask time to wait
	 * @param runnable object to run
	 */
	public static void setTimeout(TimeTask timeTask, Runnable runnable) {
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				runnable.run();
				timer.cancel();
			}
		}, timeTask.getMilliseconds());
	}
	
	/**
	 * Call {@code runnable} parameter after selected time (this method is called in javafx thread)
	 *
	 * @param timeTask time to wait
	 * @param runnable object to run
	 */
	public static void setTimeoutFX(TimeTask timeTask, Runnable runnable) {
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(runnable);
				timer.cancel();
			}
		}, timeTask.getMilliseconds());
	}
	
	/**
	 * Call {@code runnable} every after selected time
	 *
	 * @param timeTask time to wait
	 * @param runnable object to run
	 */
	public static void setInterval(TimeTask timeTask, RunnableInterval runnable) {
		Timer timer = new Timer();
		final double[] counter = {0};
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				runnable.run(++counter[0], timer);
			}
		}, timeTask.getMilliseconds(), timeTask.getMilliseconds());
	}
	
	/**
	 * Call {@code runnable} every after selected time (this method is called in javafx thread)
	 *
	 * @param timeTask time to wait
	 * @param runnable object to run
	 */
	public static void setIntervalFX(TimeTask timeTask, RunnableInterval runnable) {
		Timer timer = new Timer();
		final double[] counter = {0};
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					runnable.run(++counter[0], timer);
				});
			}
		}, timeTask.getMilliseconds(), timeTask.getMilliseconds());
	}
	
}
