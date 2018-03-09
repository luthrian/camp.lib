package com.camsolute.code.camp.lib.contract;

import com.camsolute.code.camp.lib.models.customer.Address;

public interface HasAddress {
	public Address address();
	public void setAddress(Address address);
	public void updateAddress(Address address);
}
