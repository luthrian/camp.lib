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

public interface AddressRestInterface extends DaoInterface<Address> {
	public Address loadFirst(String businessId, boolean log);

	public Address loadPrevious(Address p, boolean log);

	public Address loadNext(Address p, boolean log);

	public AddressList loadDate( String businessId, String date, boolean log);

	public AddressList loadDateRange( String businessId, String startDate, String endDate, boolean log);

	public AddressList loadDate( String date, boolean log);

	public AddressList loadDateRange( String startDate, String endDate, boolean log);


}
