package com.schein.utils;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
/**
 * @author Santosh.Thakur
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LoggerUtil {

	public final static String INFO = "info";
	public final static String WARNING = "warn";
	public final static String ERROR = "error";
	public final static String FATAL = "fatal";
	public final static String DEBUG = "debug";

	public static Logger logger = Logger.getRootLogger();
	
	public static void callTheProcedure() {
		LoggerUtil theLogger = new LoggerUtil();
		theLogger.init();
	}
	
	public void init() {
		//SimpleLayout layout = new SimpleLayout();
		String pattern = "Milliseconds since program start: %r %n";
		pattern += "Classname of caller: %C %n";
		pattern += "Date in ISO8601 format: %d{ISO8601} %n";
		pattern += "Location of log event: %l %n";
		pattern += "Message: %m %n %n";

		FileAppender dynamicFileAppender = null;
		try {
			dynamicFileAppender =
				new FileAppender(
					new PatternLayout("%d - [%c{1}] %m %n"),
					"c:\\log\\output.txt",
					true);

		} catch (Exception e) {
			LoggerUtil.log(this.getClass().getName(), "except", "init ", e);
		}

		//logger.addAppender(appender);
		logger.addAppender(dynamicFileAppender);
		logger.setLevel((Level) Level.DEBUG);

	}
	/**
	 * @param level
	 * @param msg
	 */
	public static void log(String level, String msg) {
		if (level.equalsIgnoreCase("info")) {
			logger.info(msg);
		} else if (level.equalsIgnoreCase("warn")) {
			logger.warn(msg);
		} else if (level.equalsIgnoreCase("error")) {
			logger.error(msg);
		} else if (level.equalsIgnoreCase("fatal")) {
			logger.fatal(msg);
		} else if (level.equalsIgnoreCase("debug")) {
			logger.debug(msg);
		}
	}
	/**
	 * @param level
	 * @param msg
	 * @param s
	 * @param o
	 */
	public static void log(String level, String msg, String s, Object o) {
		if (level.equalsIgnoreCase("info")) {
			logger.info(msg);
		} else if (level.equalsIgnoreCase("warn")) {
			logger.warn(msg);
		} else if (level.equalsIgnoreCase("error")) {
			logger.error(msg);
		} else if (level.equalsIgnoreCase("fatal")) {
			logger.fatal(msg);
		} else if (level.equalsIgnoreCase("debug")) {
			logger.debug(msg);
		} else {
			logger.debug(o);
		}

	}
	/**
	 * @param level
	 * @param msg
	 * @param o
	 */
	public static void log(String level, String msg, Object o) {
		if (level.equalsIgnoreCase("info")) {
			logger.info(msg);
		} else if (level.equalsIgnoreCase("warn")) {
			logger.warn(msg);
		} else if (level.equalsIgnoreCase("error")) {
			logger.error(msg);
		} else if (level.equalsIgnoreCase("fatal")) {
			logger.fatal(msg);
		} else if (level.equalsIgnoreCase("debug")) {
			logger.debug(msg);
		} else {
			logger.debug(o);
		}
	}
}
