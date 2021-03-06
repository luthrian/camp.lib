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
package com.camsolute.code.camp.lib.models.customer;

import com.camsolute.code.camp.lib.dao.DaoInterface;
import com.camsolute.code.camp.lib.dao.HasProcessReference;
import com.camsolute.code.camp.lib.dao.InstanceDaoInterface;
import com.camsolute.code.camp.lib.dao.database.DBDaoInterface;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Version;
import com.camsolute.code.camp.lib.models.customer.Customer.Origin;
import com.camsolute.code.camp.lib.models.customer.Customer.Type;
import com.camsolute.code.camp.lib.models.order.Order;
import com.camsolute.code.camp.lib.models.process.ProcessList;

public interface CustomerDaoInterface extends DaoInterface<Customer>, InstanceDaoInterface<Customer>, DBDaoInterface<Customer>, HasProcessReference{
	
	public Customer create(Origin origin, Type type, String businessId, String businessKey, Group group, Version version, boolean log);

	public String insertProcessReferenceValues(String businessId, ProcessList pl, boolean log);
	
	public Customer loadFirst(String businessId, boolean log);

	public Customer loadPrevious(Customer customer, boolean log);
	
	public Customer loadNext(Customer customer, boolean log);
	
	public CustomerList loadDate(String businessId, String date);

	public CustomerList loadDateRange(String businessId, String startDate, String endDate);

	public CustomerList loadDate(String date);

	public CustomerList loadDateRange(String startDate, String endDate);
}
