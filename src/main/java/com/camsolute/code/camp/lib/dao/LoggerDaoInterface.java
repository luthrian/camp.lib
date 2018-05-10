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
package com.camsolute.code.camp.lib.dao;

import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.dao.database.DBDaoInterface;
import com.camsolute.code.camp.lib.utilities.LogEntry;
import com.camsolute.code.camp.lib.utilities.LogEntryInterface.LogObjects;
import com.camsolute.code.camp.lib.utilities.LogEntryList;

public interface LoggerDaoInterface {
	public static enum RangeTarget {
		LOG_TIMESTAMP,
		OBJECT_TIMESTAMP,
		OBJECT_DATE,
		OBJECT_END_OF_LIFE;
	};
	public <T extends IsObjectInstance<T>> LogEntry<T> log(IsObjectInstance<?> object, LogObjects type, boolean log);
	public <T extends IsObjectInstance<?>, E extends ArrayList<T>> LogEntryList log(E objects, LogObjects type, boolean log);
	public LogEntryList loadByType(String objectType, boolean log);
	public LogEntryList loadByTypeGroup(String objectType, String group, boolean log);
	public LogEntryList loadByTypeVersion(String objectType, String version, boolean log);
	public LogEntryList loadByTypeDate(String objectType, String date, boolean log);
	public LogEntryList loadByTypeEndOfLife(String objectType, String endOfLife, boolean log);
	public LogEntryList loadByTypeDateRange(String businessId, String startDate, String endDate, RangeTarget target, boolean log);
	public LogEntryList loadByTypeTimestamp(String businessId, String timestamp, boolean log);
	public LogEntryList loadByTypeLogTimestamp(String businessId, String timestamp, boolean log);
	public LogEntryList loadByBusinessId(String businessId, boolean log);
	public LogEntryList loadByGroup(String businessId, String group, boolean log);
	public LogEntryList loadByVersion(String businessId, String version, boolean log);
	public LogEntryList loadByDate(String businessId, String date, boolean log);
	public LogEntryList loadByEndOfLife(String objectType, String endOfLife, boolean log);
	public LogEntryList loadByDateRange(String businessId, String startDate, String endDate, RangeTarget target, boolean log);
	public LogEntryList loadByTimestamp(String businessId, String timestamp, boolean log);
	public LogEntryList loadByLogTimestamp(String businessId, String timestamp, boolean log);
	
}
