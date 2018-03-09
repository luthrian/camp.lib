package com.camsolute.code.camp.lib.dao.rest;

public interface ModelServicePointInterface {

	public String loadById(int id);

  public String loadByBusinessId(String businessId);

  public String loadListByBusinessKey(String businessKey);

  public String save(String model);

  public String saveList(String modelList);

  public String update(String model);

  public String updateList(String modelList);

  public String loadUpdates(String businessKey, String target);

  public String loadUpdatesByKey(String businessKey);

  public String loadUpdatesByTarget(String target);

  public String loadUpdate(String model, String businessKey, String target);

  public  int addToUpdates(String model, String businessKey, String target);

  public int addListToUpdates(String modelList, String businessKey, String target);

  public int deleteAllFromUpdates(String businessKey, String target);

	public int deleteFromUpdatesByKey(String businessKey);

	public int deleteFromUpdatesByTarget(String target);

  public int deleteFromUpdates(String instanceId, String businessKey, String target);

  public int deleteListFromUpdates(String modelList, String businessKey, String target);

	public String loadFirst(String businessId);

	public String loadPrevious(String model);

	public String loadNext(String model);

	public String loadDate(String businessId, String date);

	public String loadDateRange(String businessId, String startDate, String endDate);

	public String loadDate(String date);

	public String loadDateRange(String startDate, String endDate);

}
