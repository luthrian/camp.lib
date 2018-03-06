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

public interface ProcessServicePointInterface {

	public static final String serverUrl = CampRest.PROCESS_API_SERVER_URL;
	public static final String domainUri = CampRest.PROCESS_API_DOMAIN;


	public String loadById(int id);


	public String loadByInstanceId(String instanceId);


	public String loadListByBusinessId(String businessId);


	public String loadListByKey(String businessKey);


	public String save(String process);


	public String saveList(String processList);


	public int update(String process);


	public int updateList(String processList);


	public String loadUpdates(String businessKey, String target);


	public String loadUpdatesByKey(String businessKey);


	public String loadUpdatesByTarget(String target);


	public String loadUpdate(String instanceId, String businessId, String businessKey, String target);


	public String loadUpdate(String process, String businessKey, String target);


	public int addToUpdates(String instanceId, String businessId, String businessKey, String target);


	public int addToUpdates(String processList, String businessKey, String target);


	public int deleteAllFromUpdates(String businessKey, String target);


	public int deleteFromUpdates(String instanceId, String businessId, String businessKey, String target);


	public int deleteFromUpdates(String processList, String businessKey, String target);


}
