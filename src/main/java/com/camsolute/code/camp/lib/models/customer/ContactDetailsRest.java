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

public class ContactDetailsRest implements ContactDetailsDaoInterface {

	@Override
	public ContactDetails loadById(int id, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetails loadByEmail(String emailAddress, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetails loadBySkype(String mobileNumber, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList loadByPhone(String phoneNumber, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList loadByMobile(String mobileNumber, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList loadByMisc(String misc, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetails create(String email, String mobile, String telephone, String skype, String misc, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetails save(ContactDetails c, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList saveList(ContactDetailsList cl, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetails update(ContactDetails c, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList updateList(ContactDetailsList cl, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList loadUpdates(String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList loadUpdatesByKey(String businessKey, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList loadUpdatesByTarget(String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetails loadUpdate(String customerBusinessid, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addToUpdates(String customerBusinessId, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addListToUpdates(CustomerList customerList, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAllFromUpdates(String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFromUpdatesByKey(String businessKey, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFromUpdatesByTarget(String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFromUpdates(String customerBusinessId, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

}
