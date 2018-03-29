package com.camsolute.code.camp.lib.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.camsolute.code.camp.lib.dao.InstanceDaoInterface;

public class DescriptionDao implements DescriptionDaoInterface, DescriptionDBDaoInterface, InstanceDaoInterface<Description> {

	@Override
	public <E extends ArrayList<Description>> E loadListByBusinessKey(String businessKey, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<Description>> E loadListByGroup(String group, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<Description>> E loadListByGroupVersion(String group, String version, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<Description>> E saveList(E instanceList, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<Description>> E updateList(E instanceList, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Description loadUpdate(Description instance, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addToUpdates(Description instance, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <E extends ArrayList<Description>> int addToUpdates(E instanceList, String businessKey, String target,
			boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <E extends ArrayList<Description>> int deleteFromUpdates(E instanceList, String businessKey, String target,
			boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Description instanceLoad(String select, boolean primary, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<Description>> E instanceListLoad(String select, boolean primary, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dbName(boolean primary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String table(boolean primary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[][] tabledef(boolean primary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updatestable(boolean primary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[][] updatestabledef(boolean primary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String businessIdColumn(boolean primary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dbName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String table() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[][] tabledef() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updatestable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[][] updatestabledef() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertValues(Description d, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertListValues(DescriptionList dl, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertUpdateListValues(DescriptionList dl, String target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertUpdateValues(Description d, String target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String formatUpdateSQL(String SQL, Description d, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Description rsToI(ResultSet rs, boolean log) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createTable(boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int clearTables(boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

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
