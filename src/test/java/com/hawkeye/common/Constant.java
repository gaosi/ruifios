package com.hawkeye.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 常量
 * @author 陈华
 *
 */
public class Constant {

	public static final long K = 1000L;
	public static final long M = 1000000L;
	public static final long G = 1000000000L;
	public static final long T = 1000000000000L;
	
	public static final long Ki = 1024L;
	public static final long Mi = 1048576L;
	public static final long Gi = 1073741824L;
	public static final long Ti = 1099511627776L;
	
	public static final int HOUR = 60;
	public static final int DAY = 1440;
	
	public static final DecimalFormat formatN = new DecimalFormat(",###.##");
	public static final DecimalFormat formatN2 = new DecimalFormat(",###.00");
	
	public static final SimpleDateFormat formatDd = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat formatDm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat formatTm = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
	
	public static LocaleResource resEN = new LocaleResource("en", "");
	public static LocaleResource resZH = new LocaleResource("zh", "CN");
	public static LocaleResource resJA = new LocaleResource("ja", "JP");
	
}

