package com.camsolute.code.camp.lib.contract;

import com.camsolute.code.camp.lib.models.customer.Customer;

public interface HasCustomer {
	public Customer customer();
	public void setCustomer(Customer customer);
	public void updateCustomer(Customer customer);
}
