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
package com.camsolute.code.camp.lib.models.order;

import com.camsolute.code.camp.lib.dao.DaoInterface;
import com.camsolute.code.camp.lib.dao.HasProcessReference;
import com.camsolute.code.camp.lib.dao.InstanceDaoInterface;
import com.camsolute.code.camp.lib.dao.database.DBDaoInterface;
import com.camsolute.code.camp.lib.models.process.ProcessList;

public interface OrderPositionDaoInterface extends HasProcessReference<OrderPosition>,InstanceDaoInterface<OrderPosition>, DaoInterface<OrderPosition> , DBDaoInterface<OrderPosition>{
	
//	public int addProcessReferences(OrderPosition op, boolean log);
//
//	public int delProcessReference(OrderPosition op, String instanceId, boolean log);
//
//	public int delProcessReferences(OrderPosition op, boolean log);
//
//	public OrderPosition loadProcesses(OrderPosition op, boolean log);
//
	public String insertProcessReferenceValues(String businessId, ProcessList pl, boolean log);

	public OrderPosition loadFirst(String businessId);

	public OrderPosition loadPrevious(OrderPosition orderPosition);

	public OrderPosition loadNext(OrderPosition orderPosition);

	public OrderPositionList loadDate(String businessId, String date);

	public OrderPositionList loadDateRange(String businessId, String startDate, String endDate);

	public OrderPositionList loadDate(String date);

	public OrderPositionList loadDateRange(String startDate, String endDate);
}
