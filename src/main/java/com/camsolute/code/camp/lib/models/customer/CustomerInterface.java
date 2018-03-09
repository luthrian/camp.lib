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

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasAddress;
import com.camsolute.code.camp.lib.contract.HasContactDetails;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampInstanceInterface;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface;
import com.camsolute.code.camp.lib.models.customer.Customer.Status;
//TODO
public interface CustomerInterface extends IsObjectInstance<CustomerInterface> , HasAddress, HasContactDetails{
	
	public String firstName();
	public String updateFirstName(String name);
	public void setFirstName(String name);
	public String surName();
	public String updateSurName(String name);
	public void setSurName(String name);
	public String title();
	public void setTitle(String title);
	public String updateTitle(String title);
	public static String _toJson(Customer c){
		return "{"+_toInnerJson(c)+"}";
	}
	public static String _toInnerJson(Customer c){
		String json = "";
		json += "\"id\":"+c.id()+",";
		json += "\"title\":\""+c.title()+"\",";
		json += "\"firstName\":\""+c.firstName()+"\",";
		json += "\"surName\":\""+c.surName()+"\",";
		json += "\"businessKey\":\""+c.businessKey()+"\",";
		json += "\"group\":\""+c.group()+"\",";
		json += "\"version\":\""+c.version()+"\",";
		json += "\"status\":\""+c.status()+"\",";
		json += "\"previousStatus\":\""+c.previousStatus()+"\",";
		json += "\"history\":"+c.history().toJson()+",";
		json += "\"states\":"+c.states().toJson()+",";
		json += "\"address\":"+c.address().toJson()+",";
		json += "\"contact\":"+c.contact().toJson();
		return json;
	}
	public static Customer _fromJson(String json){
		return _fromJSONObject(new JSONObject(json));
	}
	public static Customer _fromJSONObject(JSONObject jo) {
		int id = jo.getInt("id");
		String title = jo.getString("title");
		String firstName = jo.getString("firstName");
		String surName = jo.getString("surName");
		String businessKey = jo.getString("businessKey");
		String group = jo.getString("group");
		String version = jo.getString("version");
		CampInstance history = CampInstanceInterface._fromJSONObject(jo.getJSONObject("history"));
		CampStates states = CampStatesInterface._fromJSONObject(jo.getJSONObject("states"));
		Status status = Status.valueOf(jo.getString("status"));
		Status previousStatus = Status.valueOf(jo.getString("previousStatus"));
		Address address = Address._fromJSONObject(jo.getJSONObject("address"));
		ContactDetails contact = ContactDetails._fromJSONObject(jo.getJSONObject("contact"));
		Customer c = new Customer(id,title,firstName,surName,businessKey);
		c.setGroup(group);
		c.setVersion(version);
		c.setHistory(history);
		c.states().update(states);
		c.setStatus(status);
		c.setPreviousStatus(previousStatus);
		c.setContact(contact);
		c.setAddress(address);
		return c;
	}
		
}
