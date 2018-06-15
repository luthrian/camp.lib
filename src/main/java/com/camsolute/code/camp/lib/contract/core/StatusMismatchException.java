package com.camsolute.code.camp.lib.contract.core;

public class StatusMismatchException extends Exception implements HasLogHandler {

	private static final long serialVersionUID = -4666342985577080020L;

	private LogHandler logHandler; 
	
	private String msg = "";
	
	public StatusMismatchException() {
		logHandler  = new LogHandler.DefaultLogHandler();
	}
	
	public StatusMismatchException(String msg) {
		logHandler  = new LogHandler.DefaultLogHandler();
		this.msg = msg;
	}
	
	public void printStackTrace() {
		logHandler.logEnter(msg, "printStackTrace", StatusMismatchException.class);
		super.printStackTrace();
		logHandler.logExit(msg, "printStackTrace", StatusMismatchException.class);
	}		

	public LogHandler logHandler() {
		return logHandler;
	}

	public void logHandler(LogHandler logHandler) {
		this.logHandler = logHandler;
	}
	
}
