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

public interface ModelServicePointInterface {

	public String loadById(int id);

  public String loadByBusinessId(String businessId);

  public String loadList();

  public String loadListByBusinessKey(String businessKey);

	public String loadListByGroup(String group);

	public String loadListByGroupVersion(String group, String version);
	
	public String create(String businessId, String releaseDate, String endOfLife, String businessKey, String group, String version);

  public String save(String model);

  public String saveList(String modelList);

  public String update(String model);

  public String updateList(String modelList);

  public String loadUpdates(String businessKey, String target);

  public String loadUpdatesByKey(String businessKey);

  public String loadUpdatesByTarget(String target);

  public String loadUpdate(String model, String businessKey, String target);

  public String addToUpdates(String model, String businessKey, String target);

  public String addListToUpdates(String modelList, String businessKey, String target);

  public String deleteAllFromUpdates(String businessKey, String target);

	public String deleteFromUpdatesByKey(String businessKey);

	public String deleteFromUpdatesByTarget(String target);

  public String deleteFromUpdates(String instanceId, String businessKey, String target);

  public String deleteListFromUpdates(String modelList, String businessKey, String target);

	public String loadFirst(String businessId);

	public String loadPrevious(String model);

	public String loadNext(String model);

	public String loadDate(String businessId, String date);

	public String loadDateRange(String businessId, String startDate, String endDate);

	public String loadDate(String date);

	public String loadDateRange(String startDate, String endDate);

}
