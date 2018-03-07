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

public interface OrderPositionServicePointInterface {
	
	public static final String serverUrl = CampRest.ORDER_API_SERVER_URL;
	public static final String domainUri = CampRest.ORDER_API_DOMAIN;
	
	public String loadById(int id); 

	public String loadByBusinessId(String businessId); 

	public String loadListByBusinessKey(String businessKey); 

	public String save(String orderPosition); 
	
	public String saveList(String orderPositionList); 

	public String update(String orderPosition); 

	public String updateList(String orderPositionList);
	
	public String loadUpdates(String businessKey, String target); 

	public String loadUpdatesByKey(String businessKey); 

	public String loadUpdatesByTarget(String target); 

	public String loadUpdate(String orderPosition, String businessKey, String target); 

	public int addToUpdates(String orderPosition, String businessKey, String target);

	public int addListToUpdates(String orderPositionList, String businessKey, String target);

	public int deleteAllFromUpdates(String businessKey, String target);

	public int deleteFromUpdates(String businessId, String businessKey, String target);

	public int deleteListFromUpdates(String orderPositionList, String businessKey, String target); 

	public int addProcessReference(String businessId, String instanceId, String processKey);

	public int addProcessReferences(String businessId, String processList);

	public int delProcessReference(String businessId, String instanceId, String processKey);

	public int delProcessReferences(String businessId);

	public String loadProcessReferences(String businessId);	

	public int addInstance(String orderPosition, boolean useObjectId);
	
	public String loadFirst(String businessId, boolean primary);
	
	public String loadPrevious(String orderPosition, boolean primary);
	
	public String loadNext(String orderPosition, boolean primary);
	
	public String loadDate(String businessId, String date, boolean primary);
	
	public String loadDateRange(String businessId, String startDate, String endDate, boolean primary);

	public String loadDate(String date, boolean primary); 

	public String loadDateRange(String startDate, String endDate, boolean primary);

}
