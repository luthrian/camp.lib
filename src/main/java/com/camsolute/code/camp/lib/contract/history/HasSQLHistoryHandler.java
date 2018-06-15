package com.camsolute.code.camp.lib.contract.history;

public interface HasSQLHistoryHandler {
	public SQLHistoryHandler sqlHistoryHandler();
	public void sqlHistoryHandler(SQLHistoryHandler handler);
}
