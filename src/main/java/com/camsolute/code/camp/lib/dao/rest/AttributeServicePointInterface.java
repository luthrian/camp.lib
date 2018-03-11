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

import com.camsolute.code.camp.lib.data.CampRest;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.utilities.Util;

public interface AttributeServicePointInterface {

	public String loadById(int id);

	public String loadByBusinessId(String businessId);

	public String loadListByBusinessKey(String businessKey);

	public String loadListByGroup(String group);

	public String loadListByGroupVersion(String group, String version);

	public String save(String attribute);

	public String saveList(String attributeList);

	public String update(String attribute);

	public String updateList(String attributeList);

	public String loadUpdates(String businessKey, String target);

	public String loadUpdatesByKey(String businessKey);

	public String loadUpdatesByTarget(String target);

	public String loadUpdate(String attribute, String businessKey, String target);

	public int addToUpdates(String attribute, String businessKey, String target);

	public int addListToUpdates(String attributeList, String businessKey, String target);

	public int deleteAllFromUpdates(String businessKey, String target);

	public int deleteFromUpdatesByKey(String businessKey);

	public int deleteFromUpdatesByTarget(String target);

	public int deleteFromUpdates(String instanceId, String businessKey, String target);

	public int deleteListFromUpdates(String attributeList, String businessKey, String target);

	public int deleteById(int id);

	public int deleteByBusinessId(String attributeName);

	public int deleteList(String attributeList);

	public int deleteList(int rootId);

	public String loadList(String attributeType);

	public String loadByObjectId(int objectId);

	public String saveByObjectId(int objectId, String attributeMap);

	public String loadAttributesByObjectId(int objectId);

	public String saveAttributesByObjectId(int objectId, String attributeList);

	public int updateAttributesByObjectId(int objectId, String attributeList);

	public String loadGroup(int parentId, String groupName);

	public String loadAfterPosition(int id, int position);

	public String loadBeforePosition(int id, int position);

	public String loadRange(int id, int startPosition, int endPosition);

	// VALUE ASPECTS

	public String save(int objectId, String attribute);

	public String saveList(int objectId, String attributeList);

	public int update(int objectId, String attribute);

	public int updateList(int objectId, String attributeList);

	public int delete(int objectId, int attributeId, int valueId, String attributeType);

	public int delete(int objectId, String attribute);

	public int deleteList(int objectId, String attributeList);

	public String load(int objectId, String attribute);

	public String loadByObjectId(String attributeList, int objectId);

	public String loadGroup(String attributeList, int objectId, String groupName);

	public String loadAfterPosition(String attributeList, int objectId, int position);

	public String loadBeforePosition(String attributeList, int objectId, int position);

	public String loadRange(String attributeList, int objectId, int startPosition, int endPosition);
	
  public String loadFirst(String businessId);

  public String loadPrevious(String attribute);

  public String loadNext(String attribute);
 
  public String  loadDate(String businessId, String date);
  
  public String  loadDateRange(String businessId, String startDate, String endDate);
  
  public String  loadDate(String date);
  
  public String  loadDateRange(String startDate, String endDate);
  
	public int addProcessReference(String objectBusinessId, String instanceId, String processKey);

	public int addProcessReferences(String businessId, String processList);

	public int delProcessReference(String businessId, String instanceId, String processKey);

	public int delAllProcessReferences(String businessId);

	public int delProcessReferences(String businessId,String processList);

	public String loadProcessReferences(String businessId);
}
