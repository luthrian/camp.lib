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
package com.camsolute.code.camp.lib.dao.rest;

import com.camsolute.code.camp.lib.data.CampRest;

public interface CustomerServicePointInterface {
	
	public static final String serverUrl = CampRest.CUSTOMER_API_SERVER_URL;
	public static final String domainUri = CampRest.CUSTOMER_API_DOMAIN;

	public String loadById(int id);
	public String loadByBusinessId(String businessId);
	public String loadListByBusinessKey(String businessKey);
	public String loadListByGroup(String group);
	public String loadListByGroupVersion(String group, String version);
	public String save(String customer);
	public String saveList(String customerList);
	public String update(String customer);
	public String updateList(String customerList);
	public String loadUpdates(String businessKey, String target);
	public String loadUpdatesByKey(String businessKey);
	public String loadUpdatesByTarget(String target);
	public String loadUpdate(String customer, String businessKey, String target);
	public String addToUpdates(String customer, String businessKey, String target);
	public String addListToUpdates(String customerList, String businessKey, String target);
	public String deleteAllFromUpdates(String businessKey, String target);
	public String deleteFromUpdatesByKey(String businessKey);
	public String deleteFromUpdatesByTarget(String target);
	public String deleteFromUpdates(String businessId, String businessKey, String target);
	public String deleteListFromUpdates(String customerList, String businessKey, String target);
	public String loadFirst(String businessId);
	public String loadPrevious(String customer);
	public String loadNext(String customer);
	public String loadDate( String businessId, String date);
	public String loadDateRange( String businessId, String startDate, String endDate);
	public String loadDate( String date);
	public String loadDateRange( String startDate, String endDate);
	public String addProcessReference(String businessId, String instanceId, String processKey);
	public String addProcessReferences(String businessId, String processList);
	public String delProcessReference(String businessId, String instanceId, String processKey);
	public String delAllProcessReferences(String businessId);
	public String delProcessReferences(String businessId, String processList);
	public String loadProcessReferences(String businessId);

}
