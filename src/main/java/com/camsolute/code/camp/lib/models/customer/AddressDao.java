/*******************************************************************************
 * Copyright (C) 2018 Christopher Campbell
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * 	Christopher Campbell - all code prior and post initial release
 ******************************************************************************/
package com.camsolute.code.camp.lib.models.customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.dao.InstanceDaoInterface;

public class AddressDao implements AddressDaoInterface {

	@Override
	public Address loadById(int id, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address loadByBusinessId(String businessId, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<Address>> E loadListByBusinessKey(String businessKey, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<Address>> E loadListByGroup(String group, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<Address>> E loadListByGroupVersion(String group, String version, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address save(Address instance, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<Address>> E saveList(E instanceList, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address update(Address instance, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<Address>> E updateList(E instanceList, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<Address>> E loadUpdates(String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<Address>> E loadUpdatesByKey(String businessKey, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<Address>> E loadUpdatesByTarget(String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address loadUpdate(Address instance, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addToUpdates(Address instance, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <E extends ArrayList<Address>> int addToUpdates(E instanceList, String businessKey, String target,
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
	public <E extends ArrayList<Address>> int deleteFromUpdates(E instanceList, String businessKey, String target,
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
	public String insertValues(Address p, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<Address>> String insertListValues(E pl, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ArrayList<Address>> String insertUpdateListValues(E pl, String target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertUpdateValues(Address p, String target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String formatUpdateSQL(String SQL, Address p, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address rsToI(ResultSet rs, boolean log) throws SQLException {
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
