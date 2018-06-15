package com.camsolute.code.camp.lib.contract.value;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.contract.core.HasLogHandler;
import com.camsolute.code.camp.lib.contract.core.LogHandler;

public class ValueTypeMismatchException extends Exception implements HasLogHandler {

	private static final Logger LOG = LogManager.getLogger(ValueTypeMismatchException.class);
	private static String fmt = "[%15s] [%s]";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2415357709579129376L;

	private LogHandler logHandler;
	
	private String msg = "";
	
	public ValueTypeMismatchException() {
		this.logHandler = new LogHandler.ExceptionLogHandler();
	}
	
	public ValueTypeMismatchException(String msg) {
		this.msg = msg;
		this.logHandler = new LogHandler.ExceptionLogHandler();
	}

	public void printStackTrace() {
		logHandler.logEnter(msg, "printStackTrace", ValueTypeMismatchException.class);
		super.printStackTrace();
		logHandler.logExit(msg, "printStackTrace", ValueTypeMismatchException.class);
	}		

	public LogHandler logHandler() {
		return logHandler;
	}

	public void logHandler(LogHandler logHandler) {
		this.logHandler = logHandler;
	}

}
