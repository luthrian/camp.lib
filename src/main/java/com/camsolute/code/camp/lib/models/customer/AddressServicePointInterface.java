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

import com.camsolute.code.camp.lib.data.CampRest;

public interface AddressServicePointInterface {

	public static final String serverUrl = CampRest.CUSTOMER_API_SERVER_URL;
	public static final String domainUri = CampRest.CUSTOMER_API_DOMAIN;

	public String loadById(int id, boolean log) ;
	
	public String loadByBusinessId(String businessId, boolean log) ;

	public String loadListByBusinessKey(String businessKey, boolean log);
	
	public String loadListByGroup(String group, boolean log) ;

	public String loadListByGroupVersion(String group, String version, boolean log);
	
	public String save(String address, boolean log);
	
	public String saveList(String addressList, boolean log);
	
	public String update(String address, boolean log);
	
	public String updateList(String addressList, boolean log);
	
	public String loadUpdates(String businessKey, String target, boolean log);
	
	public String loadUpdatesByKey(String businessKey, boolean log);
	
	public String loadUpdatesByTarget(String target, boolean log);
	
	public String loadUpdate(String address, String businessKey, String target, boolean log);
	
	public String addToUpdates(String address, String businessKey, String target, boolean log);
	
	public String addListToUpdates(String addressList, String businessKey, String target,boolean log);
	
	public String deleteAllFromUpdates(String businessKey, String target, boolean log);
	
	public String deleteFromUpdatesByKey(String businessKey, boolean log);
	
	public String deleteFromUpdatesByTarget(String target, boolean log);
	
	public String deleteFromUpdates(String addressId, String businessKey, String target, boolean log);
	
	public String deleteListFromUpdates(String addressList, String businessKey, String target, boolean log);
	
	public String addInstance(String customer, boolean useObjectId);
	
	public String addInstances(String customerList, boolean useObjectId);
	
	public String loadCurrent(String businessId, boolean primary);
	
	public String loadFirst(String businessId, boolean primary);
	
	public String loadPrevious(String customer, boolean primary);
	
	public String loadNext(String customer, boolean primary);
	
	public String loadDate(String businessId, String date, boolean primary);
	
	public String loadDateRange(String businessId, String startDate, String endDate, boolean primary);
	
	public String loadDateRange(String startDate, String endDate,boolean primary);

}
