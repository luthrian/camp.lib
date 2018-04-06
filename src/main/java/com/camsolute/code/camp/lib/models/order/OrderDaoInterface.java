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
import com.camsolute.code.camp.lib.dao.HasOrderPositionReference;
import com.camsolute.code.camp.lib.dao.HasProcessReference;
import com.camsolute.code.camp.lib.dao.InstanceDaoInterface;
import com.camsolute.code.camp.lib.dao.database.DBDaoInterface;
import com.camsolute.code.camp.lib.models.Model;
import com.camsolute.code.camp.lib.models.ModelList;

public interface OrderDaoInterface extends InstanceDaoInterface<Order>, DaoInterface<Order>, DBDaoInterface<Order>, HasProcessReference, HasOrderPositionReference {

	OrderList loadList(boolean log);
	
	Order loadFirst(String businessId);

	Order loadPrevious(Order order);

	Order loadNext(Order order);

	OrderList loadDate(String businessId, String date);

	OrderList loadDateRange(String businessId, String startDate, String endDate);

	OrderList loadDate(String date);

	OrderList loadDateRange(String startDate, String endDate);

	public Order updateAttribute(Order.UpdateAttribute attribute, String businessId, String newValue, boolean log);
	
}
