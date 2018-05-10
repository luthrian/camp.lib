package com.camsolute.code.camp.lib.dao.rest;

public interface LoggerServicePointInterface {
	public String log(String object);
	public String logList(String objects); //here below logentrylist
	public String loadByType(String objectType);
	public String loadByTypeGroup(String objectType, String group);
	public String loadByTypeVersion(String objectType, String version);
	public String loadByTypeDate(String objectType, String date);
	public String loadByTypeEndOfLife(String objectType, String endOfLife);
	public String loadByTypeDateRange(String businessId, String startDate, String endDate, String target);
	public String loadByTypeTimestamp(String businessId, String timestamp);
	public String loadByTypeLogTimestamp(String businessId, String timestamp);
	public String loadByBusinessId(String businessId);
	public String loadByGroup(String businessId, String group);
	public String loadByVersion(String businessId, String version);
	public String loadByDate(String businessId, String date);
	public String loadByEndOfLife(String objectType, String endOfLife);
	public String loadByDateRange(String businessId, String startDate, String endDate, String target);
	public String loadByTimestamp(String businessId, String timestamp);
	public String loadByLogTimestamp(String businessId, String timestamp);
}
