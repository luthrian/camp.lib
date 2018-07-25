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

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.CampStates;
import com.camsolute.code.camp.lib.contract.core.CampStates.CampStatesImpl;
import com.camsolute.code.camp.lib.contract.HasAddress;
import com.camsolute.code.camp.lib.contract.HasContactDetails;
import com.camsolute.code.camp.lib.contract.HasProcess;
import com.camsolute.code.camp.lib.contract.HasRefBusinessId;
import com.camsolute.code.camp.lib.contract.HasRefId;
import com.camsolute.code.camp.lib.contract.HasTouchPoints;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.process.CustomerProcess;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.utilities.Util;
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampInstanceInterface;
//import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface;
import com.camsolute.code.camp.lib.models.customer.Customer.Status;
import com.camsolute.code.camp.lib.models.customer.Customer.Type;
import com.camsolute.code.camp.lib.models.customer.Customer.Origin;
//TODO
public interface CustomerInterface extends IsObjectInstance<Customer> , HasRefId, HasAddress, HasTouchPoints, HasContactDetails, HasProcess<Customer>{
	public static final Logger LOG = LogManager.getLogger(CustomerInterface.class);
	public static String fmt = "[%15s] [%s]";

	public void setRefId(int id);
	public Type type();
	public void setType(String type);
	public Type updateType(Type type);
	public Type updateType(String type);
	public Origin origin();
	public void setOrigin(String origin);
	public Origin updateOrigin(Origin origin);
	public Origin updateOrigin(String origin);
	public static String _toJson(Customer c){
		return "{"+_toInnerJson(c)+"}";
	}
	public static String _toInnerJson(Customer c){
		String json = "";
		json += "\"id\":"+c.id()+",";
		json += "\"refId\":"+c.getRefId()+",";
		json += "\"origin\":\""+c.origin().name()+"\",";
		json += "\"type\":\""+c.type().name()+"\",";
		json += "\"businessId\":\""+c.businessId()+"\",";
		json += "\"businessKey\":\""+c.businessKey()+"\",";
		json += "\"group\":\""+c.group()+"\",";
		json += "\"version\":\""+c.version()+"\",";
		json += "\"addressId\":"+c.addressId()+",";
		json += "\"deliveryAddressId\":"+c.deliveryAddressId()+",";
		json += "\"touchPointId\":"+c.touchPointId()+",";
		json += "\"contactId\":"+c.contactId()+",";
		json += "\"status\":\""+c.status().name()+"\",";
		json += "\"previousStatus\":\""+c.previousStatus().name()+"\",";
		json += "\"history\":"+c.history().toJson()+",";
		json += "\"states\":"+c.states().toJson()+",";
		json += ((c.contact() != null)?"\"contact\":"+c.contact().toJson():"");
		json += ((c.addressList().size() > 0)?"\"addressList\":"+c.addressList().toJson()+",":"");
		json += ((c.processes().size() > 0)?"\"processes\":"+c.processes().toJson()+",":"");
		json += ((c.touchPoints().size() > 0)?"\"touchPoints\":"+c.touchPoints().toJson():"");
		return json;
	}
	public static Customer _fromJson(String json){
		return _fromJSONObject(new JSONObject(json));
	}
	public static Customer _fromJSONObject(JSONObject jo) {
		int id = 0;
		if(jo.has("id")) id = jo.getInt("id");
		int refId = jo.getInt("refId");
		Origin origin = Origin.valueOf(jo.getString("origin"));
		Type type = Type.valueOf(jo.getString("type"));
		String businessId = jo.getString("businessId");
		String businessKey = jo.getString("businessKey");
		String group = jo.getString("group");
		String version = jo.getString("version");
		int addressId = jo.getInt("addressId");
		int deliveryAddressId = jo.getInt("deliveryAddressId");
		int touchPointId = jo.getInt("touchPointId");
		int contactId = jo.getInt("contactId");
		Status status = Status.valueOf(jo.getString("status"));
		Status previousStatus = Status.valueOf(jo.getString("previousStatus"));
		CampInstance history = CampInstanceInterface._fromJSONObject(jo.getJSONObject("history"));
		CampStates states = CampStates._fromJSONObject(jo.getJSONObject("states"));
		ContactDetails contact = null;
		AddressList addressList = null;
		ProcessList processes = null;
		TouchPointList touchPoints = null;
		
		if(jo.has("contact")) { 
			contact = ContactDetailsInterface._fromJSONObject(jo.getJSONObject("contact"));
		}
		if(jo.has("addressList")) {
			addressList = AddressList._fromJSONArray(jo.getJSONArray("addressList"));
		} 
		if(jo.has("processes")) {
			processes = ProcessList._fromJSONArray(jo.getJSONArray("processes"));
		}
		if(jo.has("touchPoints")) {
			touchPoints = TouchPointList._fromJSONArray(jo.getJSONArray("touchPoints"));
		}
		Customer c = new Customer(id,origin,type,businessId,businessKey,group,version);
		c.setHistory(history);
		c.setRefId(refId);
		c.states().update(states);
		c.setStatus(status);
		c.setPreviousStatus(previousStatus);
		c.setAddressId(addressId);
		c.setDeliveryAddressId(deliveryAddressId);
		c.setTouchPointId(touchPointId);
		c.setContactId(contactId);
		if(contact != null) { 
			c.setContact(contact);
		}
		if(addressList != null) {
			c.setAddressList(addressList);
		}
		if(processes != null) {
			c.setProcesses(processes);
		}
		if(touchPoints != null) {
			c.setTouchPoints(touchPoints);
		}
		return c;
	}
		
}
