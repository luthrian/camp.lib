package com.camsolute.code.camp.lib.contract;

import com.camsolute.code.camp.lib.models.customer.ContactDetails;

public interface HasContactDetails {
	public ContactDetails contact();
	public void updateContact(ContactDetails contactDetails);
	public void setContact(ContactDetails contactDetails);
}
