package com.camsolute.code.camp.lib.models;

public class DescriptionRest implements DescriptionDaoInterface {

	@Override
	public Description loadById(int id, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Description loadByBusinessId(String businessId, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DescriptionList loadByKey(String businessKey, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DescriptionList loadByTitle(String title, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Description create(String title, String description, String businessId, String businessKey, Group group,
			Version version, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Description save(Description d, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DescriptionList saveList(DescriptionList dl, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Description update(Description d, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DescriptionList updateList(DescriptionList dl, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DescriptionList loadUpdates(String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DescriptionList loadUpdatesByKey(String businessKey, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DescriptionList loadUpdatesByTarget(String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Description loadUpdate(String businessId, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addToUpdates(String businessId, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addListToUpdates(DescriptionList descriptionList, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAllFromUpdates(String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFromUpdatesByKey(String businessKey, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFromUpdatesByTarget(String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFromUpdates(String businessId, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Description loadFirst(String businessId, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Description loadPrevious(Description description, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Description loadNext(Description description, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DescriptionList loadDate(String businessId, String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DescriptionList loadDateRange(String businessId, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DescriptionList loadDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DescriptionList loadDateRange(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
