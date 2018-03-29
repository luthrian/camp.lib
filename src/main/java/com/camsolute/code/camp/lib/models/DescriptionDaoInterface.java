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
package com.camsolute.code.camp.lib.models;

import com.camsolute.code.camp.lib.models.Description;
import com.camsolute.code.camp.lib.models.DescriptionList;

public interface DescriptionDaoInterface {
	
	public Description loadById(int id, boolean log);
	
	public Description loadByBusinessId(String businessId, boolean log);
	
	public DescriptionList loadByKey(String businessKey, boolean log);
	
	public DescriptionList loadByTitle(String title, boolean log);
	
	public Description create(String title, String description, String businessId, String businessKey, Group group, Version version, boolean log);
	
	public Description save(Description d, boolean log);
	
	public DescriptionList saveList(DescriptionList dl, boolean log);
	
	public Description update(Description d, boolean log);
	
	public DescriptionList updateList(DescriptionList dl, boolean log);
	
	public DescriptionList loadUpdates(String businessKey, String target, boolean log);
	
	public DescriptionList loadUpdatesByKey(String businessKey, boolean log);
	
	public DescriptionList loadUpdatesByTarget(String target, boolean log);
	
	public Description loadUpdate(String businessId, String businessKey, String target, boolean log);
	
	public int addToUpdates(String businessId, String businessKey, String target, boolean log);
	
	public int addListToUpdates(DescriptionList descriptionList, String businessKey, String target, boolean log);
	
	public int deleteAllFromUpdates(String businessKey, String target, boolean log);
	
	public int deleteFromUpdatesByKey(String businessKey, boolean log);
	
	public int deleteFromUpdatesByTarget(String target, boolean log);
	
	public int deleteFromUpdates(String businessId, String businessKey, String target, boolean log);

	public Description loadFirst(String businessId, boolean log);

	public Description loadPrevious(Description description, boolean log);
	
	public Description loadNext(Description description, boolean log);
	
	public DescriptionList loadDate(String businessId, String date);

	public DescriptionList loadDateRange(String businessId, String startDate, String endDate);

	public DescriptionList loadDate(String date);

	public DescriptionList loadDateRange(String startDate, String endDate);

}
