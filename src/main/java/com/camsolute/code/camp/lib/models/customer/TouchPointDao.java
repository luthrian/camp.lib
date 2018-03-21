package com.camsolute.code.camp.lib.models.customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.dao.InstanceDaoInterface;

public class TouchPointDao implements TouchPointDaoInterface {

	@Override
	public TouchPoint loadById(int id, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TouchPoint loadByBusinessId(String businessId, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<TouchPoint>> E loadListByBusinessKey(String businessKey, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<TouchPoint>> E loadListByGroup(String group, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<TouchPoint>> E loadListByGroupVersion(String group, String version, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TouchPoint save(TouchPoint instance, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<TouchPoint>> E saveList(E instanceList, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TouchPoint update(TouchPoint instance, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<TouchPoint>> E updateList(E instanceList, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<TouchPoint>> E loadUpdates(String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<TouchPoint>> E loadUpdatesByKey(String businessKey, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<TouchPoint>> E loadUpdatesByTarget(String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TouchPoint loadUpdate(TouchPoint instance, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addToUpdates(TouchPoint instance, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <E extends ArrayList<TouchPoint>> int addToUpdates(E instanceList, String businessKey, String target,
			boolean log) {
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
	public int deleteFromUpdates(String instanceId, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <E extends ArrayList<TouchPoint>> int deleteFromUpdates(E instanceList, String businessKey, String target,
			boolean log) {
		// TODO Auto-generated method stub
		return 0;
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
	public String insertValues(TouchPoint p, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<TouchPoint>> String insertListValues(E pl, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<TouchPoint>> String insertUpdateListValues(E pl, String target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertUpdateValues(TouchPoint p, String target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String formatUpdateSQL(String SQL, TouchPoint p, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TouchPoint rsToI(ResultSet rs, boolean log) throws SQLException {
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
	public <T extends IsObjectInstance<T>> int addInstance(T object, boolean useObjectId) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T extends IsObjectInstance<T>, E extends ArrayList<T>> int addInstances(E objectList, boolean useObjectId)
			throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T extends InstanceDaoInterface<?>, E extends IsObjectInstance<E>> E loadCurrent(String businessId, T dao,
			boolean primary) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends InstanceDaoInterface<?>, E extends IsObjectInstance<E>> E loadFirst(String businessId, T dao,
			boolean primary) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends InstanceDaoInterface<?>, E extends IsObjectInstance<E>> E loadPrevious(E object, T dao,
			boolean primary) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends InstanceDaoInterface<?>, E extends IsObjectInstance<E>> E loadNext(E object, T dao, boolean primary)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends InstanceDaoInterface<?>, U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDate(
			String businessId, String date, T dao, boolean primary) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends InstanceDaoInterface<?>, U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDateRange(
			String businessId, String startDate, String endDate, T dao, boolean primary) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends InstanceDaoInterface<?>, U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDate(
			String date, T dao, boolean primary) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends InstanceDaoInterface<?>, U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDateRange(
			String startDate, String endDate, T dao, boolean primary) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
