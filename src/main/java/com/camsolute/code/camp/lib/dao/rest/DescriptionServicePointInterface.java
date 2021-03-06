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


public interface DescriptionServicePointInterface {
	
	public String loadById(int id);
	
	public String loadByBusinessId(String businessId);
	
	public String loadByKey(String businessKey);
	
	public String loadByTitle(String title);
	
	public String create(String title, String description, String businessId, String businessKey, String group, String version);
	
	public String save(String d);
	
	public String saveList(String dl);
	
	public String update(String d);
	
	public String updateList(String dl);
	
	public String loadUpdates(String businessKey, String target);
	
	public String loadUpdatesByKey(String businessKey);
	
	public String loadUpdatesByTarget(String target);
	
	public String loadUpdate(String businessId, String businessKey, String target);
	
	public int addToUpdates(String businessId, String businessKey, String target);
	
	public int addListToUpdates(String descriptionList, String businessKey, String target);
	
	public int deleteAllFromUpdates(String businessKey, String target);
	
	public int deleteFromUpdatesByKey(String businessKey);
	
	public int deleteFromUpdatesByTarget(String target);
	
	public int deleteFromUpdates(String businessId, String businessKey, String target);

	public String loadFirst(String businessId);

	public String loadPrevious(String description);
	
	public String loadNext(String description);
	
	public String loadDate(String businessId, String date);

	public String loadDateRange(String businessId, String startDate, String endDate);

	public String loadDate(String date);

	public String loadDateRange(String startDate, String endDate);

}
