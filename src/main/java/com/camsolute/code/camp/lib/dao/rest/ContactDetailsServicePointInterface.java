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
package com.camsolute.code.camp.lib.dao.rest;

import com.camsolute.code.camp.lib.data.CampRest;

public interface ContactDetailsServicePointInterface {

	public static final String serverUrl = CampRest.CUSTOMER_API_SERVER_URL;
	public static final String domainUri = CampRest.CUSTOMER_API_DOMAIN;

	public String loadById(int id);
	
	public String loadByEmail(String emailAddress);
	
	public String loadBySkype(String mobileNumber);
	
	public String loadByPhone(String phoneNumber);
	
	public String loadByMobile(String mobileNumber);
	
	public String loadByMisc(String misc);
	
	public String create(String email, String mobile, String telephone, String skype, String misc);
	
	public String save(String c);
	
	public String saveList(String cl);
	
	public String update(String c);
	
	public String updateList(String cl);
	
	public String loadUpdates(String businessKey, String target);
	
	public String loadUpdatesByKey(String businessKey);
	
	public String loadUpdatesByTarget(String target);
	
	public String loadUpdate(String customerBusinessId, String businessKey, String target);
	
	public String addToUpdates(String customerBusinessId, String businessKey, String target);
	
	public String addListToUpdates(String customerList, String businessKey, String target);
	
	public String deleteAllFromUpdates(String businessKey, String target);
	
	public String deleteFromUpdatesByKey(String businessKey);
	
	public String deleteFromUpdatesByTarget(String target);
	
	public String deleteFromUpdates(String customerBusinessId, String businessKey, String target);
	
}
