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

import java.sql.SQLException;

import com.camsolute.code.camp.lib.dao.DaoInterface;
import com.camsolute.code.camp.lib.dao.InstanceDaoInterface;
import com.camsolute.code.camp.lib.dao.database.DBDaoInterface;
import com.camsolute.code.camp.lib.models.CampInstanceDaoInterface;

public interface AddressDaoInterface extends DaoInterface<Address>, DBDaoInterface<Address>, InstanceDaoInterface<Address>{
	public Address loadCurrent(String businessId, AddressDao dao,boolean primary) throws SQLException;
	public Address loadFirst(String businessId, AddressDao dao,boolean primary) throws SQLException;
	public Address loadPrevious(Address address, AddressDao dao,boolean primary) throws SQLException;
	public Address loadNext(Address address, AddressDao dao, boolean primary)throws SQLException;
	public AddressList loadDate(String businessId, String date, AddressDao dao, boolean primary) throws SQLException;
	public AddressList loadDateRange(String businessId, String startDate, String endDate, AddressDao dao, boolean primary) throws SQLException;
	public AddressList loadDate(String date, AddressDao dao, boolean primary) throws SQLException;
	public AddressList loadDateRange(String startDate, String endDate, AddressDao dao, boolean primary) throws SQLException;

}
