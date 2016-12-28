package com.ruifios.commons;

import java.io.Serializable;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 查询范围接口
 */
public interface QueryRange extends Serializable {

	/**
	 * 时间类型
	 */
	public static enum Type {
		/*相对时间*/ relative, /*绝对时间*/ absolute
	}

	/**
	 * 时间单位
	 * MINUTE： 分钟毫秒数
	 * HOUR	： 小时毫秒数
	 * DAY	：天毫秒数
	 */
	public static enum QueryUnit {
		MINUTE(DateUtils.MILLIS_PER_MINUTE), 
		HOUR(DateUtils.MILLIS_PER_HOUR), 
		DAY(DateUtils.MILLIS_PER_DAY);
		
		public long value;
		
		private QueryUnit(long value){
			this.value = value;
		}
	}
	
	/**
	 * 查询范围
	 */
	public static class Range implements Serializable {

		private static final long serialVersionUID = 3161707370000864678L;

		protected long start;
		
		protected long stop;
		
		public Range() {
			
		}

		public Range(long start, long stop) {
			this.start = start;
			this.stop = stop;
		}

		public long getStart() {
			return start;
		}

		public void setStart(long start) {
			this.start = start;
		}

		public long getStop() {
			return stop;
		}

		public void setStop(long stop) {
			this.stop = stop;
		}

		@Override
		public String toString() {
			return DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(start) + 
				" to " + DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(start);
		}
	}

	public Range getRange();
	
}
