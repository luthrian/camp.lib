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
	
	public String loadByBusinessId(String businessId);
	
	public String loadListByBusinessKey(String businessKey);
	
	public String save(String product);
	
	public String saveList(String productList);
	
	public String update(String product);
	
	public String updateList(String productList);
	
	public String loadUpdates(String businessKey, String target);
	
	public String loadUpdatesByKey(String businessKey);
	
	public String loadUpdatesByTarget(String target);
	
	public String loadUpdate(String product, String businessKey, String target);
	
	public int addToUpdates(String product, String businessKey, String target);
	
	public int addListToUpdates(String productList, String businessKey, String target);
	
	public int deleteAllFromUpdates(String businessKey, String target);
	
	public int deleteFromUpdatesByKey(String businessKey);
	
	public int deleteFromUpdatesByTarget(String target);
	
	public int deleteFromUpdates(String businessId, String businessKey, String target);
	
	public int addModelReference(String businessId, int modelId);
	
	public int addModelReferences(String businessId, String modelList);
	
	public int delModelReference(String businessId, int modelId);
	
	public int delModelReferences(String businessId, String modelList);
	
	public String loadModels(String businessId);
	
	public int addProcessReference(String businessId, String instanceId, String processKey);
	
	public int addProcessReferences(String businessId, String processList);
	
	public int delProcessReference(String businessId, String instanceId, String processKey);
	
	public int delProcessReferences(String businessId);
	
	public String loadProcessReferences(String businessId);
	
	public String saveAttributes(int objectId, String attributeMap);
	
	public String updateAttributes(int objectId, String attributeMap);
	
	public String loadAttributes(int objectId);
}
