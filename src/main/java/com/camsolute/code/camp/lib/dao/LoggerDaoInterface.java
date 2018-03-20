package com.camsolute.code.camp.lib.dao;

import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.utilities.LogEntry;
import com.camsolute.code.camp.lib.utilities.LogEntryList;

public interface LoggerDaoInterface {
	public static enum RangeTarget {
		LOG_TIMESTAMP,
		OBJECT_TIMESTAMP,
		OBJECT_DATE,
		OBJECT_END_OF_LIFE;
	};
	public <T extends IsObjectInstance<T>> LogEntry<T> log(T object, boolean log);
	public <T extends IsObjectInstance<T>, E extends ArrayList<T>> LogEntryList log(E objects, boolean log);
	public LogEntryList loadByType(String objectType, boolean log);
	public LogEntryList loadByTypeGroup(String objectType, String group, boolean log);
	public LogEntryList loadByTypeVersion(String objectType, String version, boolean log);
	public LogEntryList loadByTypeDate(String objectType, String date, boolean log);
	public LogEntryList loadByTypeEndOfLife(String objectType, String endOfLife, boolean log);
	public LogEntryList loadByTypeDateRange(String businessId, String startDate, String endDate, RangeTarget target, boolean log);
	public LogEntryList loadByTypeTimestamp(String businessId, String timestamp, boolean log);
	public LogEntryList loadByTypeLogTimestamp(String businessId, String timestamp, boolean log);
	public LogEntryList loadByBusinessId(String businessId, boolean log);
	public LogEntryList loadByGroup(String businessId, String group, boolean log);
	public LogEntryList loadByVersion(String businessId, String version, boolean log);
	public LogEntryList loadByDate(String businessId, String date, boolean log);
	public LogEntryList loadByEndOfLife(String objectType, String endOfLife, boolean log);
	public LogEntryList loadByDateRange(String businessId, String startDate, String endDate, RangeTarget target, boolean log);
	public LogEntryList loadByTimestamp(String businessId, String timestamp, boolean log);
	public LogEntryList loadByLogTimestamp(String businessId, String timestamp, boolean log);
	
}
