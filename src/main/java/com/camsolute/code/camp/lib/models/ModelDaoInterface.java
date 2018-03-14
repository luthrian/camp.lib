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

import java.sql.Timestamp;

import com.camsolute.code.camp.lib.dao.DaoInterface;
import com.camsolute.code.camp.lib.dao.InstanceDaoInterface;
import com.camsolute.code.camp.lib.dao.database.DBDaoInterface;

public interface ModelDaoInterface extends InstanceDaoInterface<Model>, DaoInterface<Model>, DBDaoInterface<Model> {

	public Model create(String businessId, Timestamp releaseDate, Timestamp endOfLife, String businessKey, Version version, Group group, boolean log);
	
	public Model loadFirst(String businessId);

	public Model loadPrevious(Model model);

	public Model loadNext(Model model);

	public ModelList loadDate(String businessId, String date);

	public ModelList loadDateRange(String businessId, String startDate, String endDate);

	public ModelList loadDate(String date);

	public ModelList loadDateRange(String startDate, String endDate);

}
