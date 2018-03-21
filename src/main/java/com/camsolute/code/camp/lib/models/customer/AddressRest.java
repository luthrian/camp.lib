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

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.data.CampRest;

public class AddressRest implements AddressRestInterface {
	private static final Logger LOG = LogManager.getLogger(AddressRest.class);
	private static String fmt = "[%15s] [%s]";
	
	public static final String serverUrl = CampRest.CUSTOMER_API_SERVER_URL;
	public static final String domainUri = CampRest.CUSTOMER_API_DOMAIN;

	private static AddressRest instance = null;
	
	private AddressRest(){
	}
	
	public static AddressRest instance(){
		if(instance == null) {
			instance = new AddressRest();
		}
		return instance;
	}
	
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
	public <E extends IsObjectInstance<E>> E loadCurrent(String businessId, boolean primary) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends IsObjectInstance<E>> E loadFirst(String businessId, boolean primary) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends IsObjectInstance<E>> E loadPrevious(E object, boolean primary) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends IsObjectInstance<E>> E loadNext(E object, boolean primary) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDate(String businessId, String date,
			boolean primary) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDateRange(String businessId, String startDate,
			String endDate, boolean primary) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDate(String date, boolean primary)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDateRange(String startDate, String endDate,
			boolean primary) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
