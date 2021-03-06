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

import java.util.ArrayList;

import javax.ws.rs.QueryParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.dao.rest.RestInterface;
import com.camsolute.code.camp.lib.data.CampRest;
import com.camsolute.code.camp.lib.models.AttributeMap;
import com.camsolute.code.camp.lib.models.ModelList;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.utilities.Util;

public interface ProductServicePointInterface {
	public static final String serverUrl = CampRest.PRODUCT_API_SERVER_URL;
	public static final String domainUri = CampRest.PRODUCT_API_DOMAIN;
	
	
	public String loadById(int id);

	public String loadList();
	
	public String loadByBusinessId(String businessId);
	
	public String loadListByBusinessKey(String businessKey);
	
	public String loadListByGroup(String group);

	public String loadListByGroupVersion(String group, String version);

	public String create(String businessId, String businessKey, String date, String endOfLife, String group, String version);
	
	public String save(String product);
	
	public String saveList(String productList);
	
	public String update(String product);
	
	public String updateList(String productList);

	public String addInstance(String product, boolean useObjectId);

	public String loadFirst(String businessId, boolean primary);
	
	public String loadNext(String product, boolean primary);
	
	public String loadPrevious(String product, boolean primary);

	public String loadDate(String date, boolean primary);
	
	public String loadDateRange(String startDate, String endDate, boolean primary);
	
	public String loadDate(String businessId, String date, boolean primary);
	
	public String loadDateRange(String businessId, String startDate, String endDate, boolean primary);
	
	public String loadUpdates(String businessKey, String target);
	
	public String loadUpdatesByKey(String businessKey);
	
	public String loadUpdatesByTarget(String target);
	
	public String loadUpdate(String product, String businessKey, String target);
	
	public String loadUpdateByBusinessId(String businessId, String businessKey, String target);
	
	
	public String addToUpdates(String businessId, String businessKey, String target);
	
	public String addToUpdatesPost(String product, String businessKey, String target);
	
	public String addListToUpdates(String productList, String businessKey, String target);
	
	public String deleteAllFromUpdates(String businessKey, String target);
	
	public String deleteFromUpdatesByKey(String businessKey);
	
	public String deleteFromUpdatesByTarget(String target);
	
	public String deleteFromUpdates(String businessId, String businessKey, String target);
	
	public String addModelReference(String businessId, int modelId);
	
	public String addModelReferences(String businessId, String modelList);
	
	public String delModelReference(String businessId, int modelId);
	
	public String delModelReferences(String businessId, String modelList);
	
	public String delAllModelReferences(String businessId);
	
	public String loadModels(String businessId);
	
	public String addProcessReference(String businessId, String instanceId, String processKey);
	
	public String addProcessReferences(String businessId, String processList);
	
	public String delProcessReference(String businessId, String instanceId, String processKey);
	
	public String delProcessReferences(String businessId, String processList);
	
	public String delAllProcessReferences(String businessId);
	
	public String loadProcessReferences(String businessId);
	
	public String saveAttributes(int objectId, String attributeMap);
	
	public String updateAttributes(int objectId, String attributeMap);
	
	public String loadAttributes(int objectId);
}
