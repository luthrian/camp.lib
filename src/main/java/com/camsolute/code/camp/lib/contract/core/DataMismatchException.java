package com.camsolute.code.camp.lib.contract.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataMismatchException extends Exception implements HasLogHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2976109144918557578L;
	
	private LogHandler logHandler; 
	
	private String msg = "";
	
	public DataMismatchException() {
		logHandler  = new LogHandler.DefaultLogHandler();
	}
	
	public DataMismatchException(String msg) {
		logHandler  = new LogHandler.DefaultLogHandler();
		this.msg = msg;
	}
	
	public void printStackTrace() {
		logHandler.logEnter(msg, "printStackTrace", DataMismatchException.class);
		super.printStackTrace();
		logHandler.logExit(msg, "printStackTrace", DataMismatchException.class);
	}		

	public LogHandler logHandler() {
		return logHandler;
	}

	public void logHandler(LogHandler logHandler) {
		this.logHandler = logHandler;
	}
	
}
