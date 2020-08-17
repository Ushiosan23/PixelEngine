package fx.soft.pixelengine.utils.time;

/**
 * Utility class to use in timer task
 */
public final class TimeTask {
	
	/**
	 * Final time result
	 */
	private final long milliseconds;
	
	/**
	 * Constructor with task milliseconds
	 *
	 * @param milliseconds time
	 */
	private TimeTask(long milliseconds) {
		this.milliseconds = milliseconds;
	}
	
	/**
	 * Get milliseconds result
	 *
	 * @return time in milliseconds
	 */
	public long getMilliseconds() {
		return milliseconds;
	}
	
	/**
	 * Type time durations
	 */
	public enum Type {
		MILLISECONDS, // 1
		SECONDS, // MILLISECONDS * 1000
		MINUTES, // SECONDS * 60
		HOURS, // MINUTES * 60
		DAYS // HOURS * 24
	}
	
	/**
	 * {@link TimeTask} builder class
	 */
	public static class Builder {
		
		/**
		 * Time without convert
		 */
		private final long baseTime;
		
		/**
		 * Time result
		 */
		private long timeResult;
		
		/**
		 * Type time
		 */
		private TimeTask.Type type;
		
		/**
		 * Constructor with time
		 *
		 * @param relativeTime base time without format
		 */
		public Builder(long relativeTime) {
			baseTime = relativeTime;
		}
		
		/**
		 * Set type time
		 *
		 * @param type target time type
		 *
		 * @return current object
		 */
		public Builder setType(TimeTask.Type type) {
			this.type = type;
			return this;
		}
		
		/**
		 * Get time task object
		 *
		 * @return time object
		 */
		public TimeTask build() {
			switch (type) {
				case MILLISECONDS:
					timeResult = convertMilliseconds(baseTime);
					break;
				case SECONDS:
					timeResult = convertSeconds(baseTime);
					break;
				case MINUTES:
					timeResult = convertMinutes(baseTime);
					break;
				case HOURS:
					timeResult = convertHours(baseTime);
					break;
				case DAYS:
					timeResult = convertDays(baseTime);
					break;
			}
			
			return new TimeTask(timeResult);
		}
		
		/**
		 * Convert time to milliseconds
		 *
		 * @param time time to convert
		 *
		 * @return converted time
		 */
		private long convertMilliseconds(long time) {
			return time;
		}
		
		/**
		 * Convert time to seconds
		 *
		 * @param time time to convert
		 *
		 * @return converted time
		 */
		private long convertSeconds(long time) {
			return time * 1000;
		}
		
		/**
		 * Convert time to minutes
		 *
		 * @param time time to convert
		 *
		 * @return converted time
		 */
		private long convertMinutes(long time) {
			return convertSeconds(time) * 60;
		}
		
		/**
		 * Convert time to hours
		 *
		 * @param time time to convert
		 *
		 * @return converted time
		 */
		private long convertHours(long time) {
			return convertMinutes(time) * 60;
		}
		
		/**
		 * Convert time to days
		 *
		 * @param time time to convert
		 *
		 * @return converted time
		 */
		private long convertDays(long time) {
			return convertHours(time) * 24;
		}
		
	}
	
}
