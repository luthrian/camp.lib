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
package com.camsolute.code.camp.lib.contract;

import com.camsolute.code.camp.lib.models.customer.Address;
import com.camsolute.code.camp.lib.models.customer.AddressList;

public interface HasAddress {
	public int addressId();
	public void setAddressId(int id);
	public int deliveryAddressId();
	public void setDeliveryAddressId(int id);
	public Address address();
	public Address deliveryAddress();
	public AddressList addressList();
	public void setAddressList(AddressList addressList);
	public boolean addAddress(Address address);
	public Address removeAddress(Address address);
	public Address removeAddress(int addressId);
}
