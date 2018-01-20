/*******************************************************************************
 * Copyright (C) 2017 Christopher Campbell (campbellccc@gmail.com)
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
 * 	Christopher Campbell (campbellccc@gmail.com) - all code prior and post initial release
 ******************************************************************************/
package com.camsolute.code.camp.lib.contract;

import com.camsolute.code.camp.core.types.CampHistory;
import com.camsolute.code.camp.models.InstanceId;

public interface  HasHistory {
	public CampHistory<?> history();
//	public InstanceId instanceId();
//	public InstanceId currentInstanceId();
//	public InstanceId initialInstanceId();
//	public InstanceId updateInstance();
//	public boolean firstInstance();
//	public boolean currentInstance();
	//TODO
//	public <T extends HasNumber> T loadFirst(AbstractDao<T> dao);
//	public <T extends HasNumber> T loadCurrent(AbstractDao<T> dao);
//	public <T extends HasNumber> CampList<T> loadAll(AbstractDao<T> dao);
//	public <T extends HasNumber> T loadPrevious(AbstractDao<T> dao);
//	public <T extends HasNumber> T loadNext(AbstractDao<T> dao);
//	public <T extends HasNumber> CampList<T> loadDate(AbstractDao<T> dao);
	//TODO
//	public <T extends HasNumber> ArrayList<T> loadDateRange(Timestamp fromDate, Timestamp toDate,AbstractDao<T> dao);
}
