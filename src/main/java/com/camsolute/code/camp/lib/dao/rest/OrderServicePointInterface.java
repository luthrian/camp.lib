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

public interface OrderServicePointInterface {
	
	public static final String serverUrl = CampRest.ORDER_API_SERVER_URL;

	public static final String domainUri = CampRest.ORDER_API_DOMAIN;
	
	public String create(String businessId, String businessKey, String date, String byDate, String group, String version);
	
	public String save(String order);

	public String saveList(String orderList);

	public String update(String order);

	public String updateList(String orderList);

	public String updateAttribute(String attributeType, String businessId, String attributeValue);
	
	public String loadById(int id); 

	public String loadByBusinessId(String businessId);
	
	public String loadList();
	
	public String loadListByBusinessKey(String businessKey);

	public String loadListByGroup(String group);

	public String loadListByGroupVersion(String group, String version);

	public String loadUpdates(String businessKey, String target);

	public String loadFirst(String businessId, boolean primary);
	
	public String loadNext(String order, boolean primary);
	
	public String loadPrevious(String order, boolean primary);

	public String loadDate(String date, boolean primary);
	
	public String loadDateRange(String startDate, String endDate, boolean primary);
	
	public String loadDate(String businessId, String date, boolean primary);
	
	public String loadDateRange(String businessId, String startDate, String endDate, boolean primary);
	
	public String loadUpdatesByKey(String businessKey);

	public String loadUpdatesByTarget(String target);

	public String loadUpdate(String order, String businessKey, String target);

	public String addToUpdates(String order, String businessKey, String target);
	
	public String addListToUpdates(String orderList, String businessKey, String target);
	
	public String deleteAllFromUpdates(String businessKey, String target);
	
	public String deleteFromUpdatesByKey(String businessKey);

	public String deleteFromUpdatesByTarget(String target);

	public String deleteFromUpdates(String businessId, String businessKey, String target);
	
	public String deleteListFromUpdates(String orderList, String businessKey, String target);
	
	public String addProcessReference(String businessId, String instanceId, String processKey);
	
	public String addProcessReferences(String businessId, String processList);
	
	public String delProcessReference(String businessId, String instanceId, String processKey);
	
	public String delProcessReferences(String businessId, String processList);

	public String delAllProcessReferences(String businessId);

	public String loadProcessReferences(String businessId);

	public String addOrderPositionReference(String orderBusinessId, String businessId);

	public String addOrderPositionReferences(String orderBusinessId, String opl);

	public String delOrderPositionReference(String orderBusinessId, String buisinessId);

	public String delOrderPositionReferences(String orderBusinessId, String opl);

	public String loadOrderPositions(String orderBusinessId);

}
