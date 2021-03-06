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
import com.camsolute.code.camp.lib.models.process.Process.ProcessType;

public interface ProcessServicePointInterface {

	public static final String serverUrl = CampRest.PROCESS_API_SERVER_URL;
	public static final String domainUri = CampRest.PROCESS_API_DOMAIN;


	public String loadById(int id);


	public String loadByInstanceId(String instanceId);


	public String loadListByBusinessId(String businessId);

	public String loadList();

	public String loadListByKey(String businessKey);

	public String create(String businessId, String executionId, String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, String processType);

	public String create(String businessId, String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, String processType);

	public String save(String process);


	public String saveList(String processList);


	public String update(String process);


	public String updateList(String processList);


	public String loadUpdates(String businessKey, String target);


	public String loadUpdatesByKey(String businessKey);


	public String loadUpdatesByTarget(String target);


	public String loadUpdate(String instanceId, String businessId, String businessKey, String target);


	public String addToUpdates(String instanceId, String businessId, String businessKey, String target);


	public String addToUpdates(String processList, String businessKey, String target);


	public String deleteAllFromUpdates(String businessKey, String target);


	public String deleteFromUpdatesByKey(String businessKey);

	public String deleteFromUpdatesByTarget(String target);

	public String deleteFromUpdates(String instanceId, String businessId, String businessKey, String target);


	public String deleteFromUpdates(String processList, String businessKey, String target);


}
