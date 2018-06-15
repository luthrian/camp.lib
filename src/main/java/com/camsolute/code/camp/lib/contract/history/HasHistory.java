package com.camsolute.code.camp.lib.contract.history;

import java.sql.Timestamp;

public interface HasHistory {
	
	public HistoryMarker history();
	public void setHistory(HistoryMarker instance);
	
}
