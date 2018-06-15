package com.camsolute.code.camp.lib.contract.core;

public class StatusDirtyException extends Exception implements HasLogHandler {

	private static final long serialVersionUID = 4183370633967857338L;

	private LogHandler logHandler; 
	
	private String msg = "";
	
	public StatusDirtyException() {
		logHandler  = new LogHandler.DefaultLogHandler();
	}
	
	public StatusDirtyException(String msg) {
		logHandler  = new LogHandler.DefaultLogHandler();
		this.msg = msg;
	}
	
	public void printStackTrace() {
		logHandler.logEnter(msg, "printStackTrace", StatusDirtyException.class);
		super.printStackTrace();
		logHandler.logExit(msg, "printStackTrace", StatusDirtyException.class);
	}		

	public LogHandler logHandler() {
		return logHandler;
	}

	public void logHandler(LogHandler logHandler) {
		this.logHandler = logHandler;
	}
	
}
