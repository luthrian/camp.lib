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

public interface TouchPointServicePointInterface {
	
	public static final String serverUrl = CampRest.CUSTOMER_API_SERVER_URL;
	public static final String domainUri = CampRest.CUSTOMER_API_DOMAIN;

  public String loadById(int id);
  
  public String loadByBusinessId(String businessId);

  public String loadListByBusinessKey(String businessKey);

  public String loadListByGroup(String group);
  
  public String loadListByGroupVersion(String group, String version);
  
  public String save(String touchPoint);

  public String saveList(String touchPointList);

  public String update(String touchPoint);

  public String updateList(String touchPointList);

  public String loadUpdates(String businessKey, String target);

  public String loadUpdatesByKey(String businessKey);

  public String loadUpdatesByTarget(String target);

  public String loadUpdate(String touchPoint, String businessKey, String target);

  public String addToUpdates(String touchPoint, String businessKey, String target);

  public String addListToUpdates(String touchPointList, String businessKey, String target);

  public String deleteAllFromUpdates(String customerBusinessKey, String target);

	public String deleteFromUpdatesByKey(String customerBusinessKey);

  public String deleteFromUpdates(String customerBusinessId, String customerBusinessKey, String target);

	public String deleteAllFromUpdatesResponsible(String responsibleBusinessKey, String target);
	
	public String deleteFromUpdatesByKeyResponsible(String responsibleBusinessKey);
	
	public String deleteAllFromUpdates(String customerBusinessKey, String responsibleBusinessKey, String target);
	
	public String deleteFromUpdatesByKey(String customerBusinessKey, String responsibleBusinessKey);
	
	public String deleteFromUpdates(String touchPoint, String target); //cust/resp
	
  public String deleteListFromUpdates(String touchPointList, String target); //cust/resp

	public String deleteFromUpdatesByTarget(String target);

	public String loadFirst(String businessId);

	public String loadPrevious(String touchPoint);

	public String loadNext(String touchPoint);

	public String loadDate(String businessId, String date);

	public String loadDateRange(String businessId, String startDate, String endDate);

	public String loadDate(String date);

	public String loadDateRange(String startDate, String endDate);

	public String deleteFromUpdatesResponsible(String touchPoint, String target);

}
