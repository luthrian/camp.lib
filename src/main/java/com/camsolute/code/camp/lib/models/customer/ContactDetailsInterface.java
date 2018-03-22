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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasStates;
import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface;
import com.camsolute.code.camp.lib.utilities.Util;

public interface ContactDetailsInterface extends Serialization<ContactDetails>, HasStates {
	public static final Logger LOG = LogManager.getLogger(ContactDetailsInterface.class);
	public static String fmt = "[%15s] [%s]";
	
	public int id();
	
	public int updateId(int id);
	
	public String customerBusinessId();	
	public void setCustomerBusinessId(String id);
	public void updateCustomerBusinessId(String id);
	
	public String customerBusinessKey();	
	public void setCustomerBusinessKey(String key);
	public void updateCustomerBusinessKey(String key);
	
	public String email();
	public void updateEmail(String email);
	public void setEmail(String email);
	
	public String mobile();
	public void updateMobile(String mobile);
	public void setMobile(String mobile);
	
	public String telephone();
	public void updateTelephone(String telephone);
	public void setTelephone(String telephone);
	public String skype();
	public void updateSkype(String skype);
	public void setSkype(String skype);
	
	public String misc();
	public void updateMisc(String misc);
	public void setMisc(String misc);
	
	public static String _toJson(ContactDetailsInterface d) {
		return "{"+_toInnerJson(d)+"}";
	}
	public static String _toInnerJson(ContactDetailsInterface d) {
		String json = "";
		json += "\"id\":"+d.id()+",";
		json += "\"customerBusinessId\":\""+d.customerBusinessId()+"\",";
		json += "\"customerBusinessKey\":\""+d.customerBusinessKey()+"\",";
		json += "\"email\":\""+d.email()+"\",";
		json += "\"mobile\":\""+d.mobile()+"\",";
		json += "\"telephone\":\""+d.telephone()+"\",";
		json += "\"skype\":\""+((d.skype()!=null)?d.skype():"")+"\",";
		json += "\"misc\":\""+((d.misc()!=null)?d.misc():"")+"\",";
		json += "\"states\":"+d.states().toJson();
		return json;
	}
	
	public static ContactDetails _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	public static ContactDetails _fromJSONObject(JSONObject jo) {
		int id = 0;
		if(jo.has("id")) id = jo.getInt("id");
		String customerBusinessId = jo.getString("customerBusinessId");
		String customerBusinessKey = jo.getString("customerBusinessKey");
		String email = jo.getString("email");
		String mobile = jo.getString("mobile");
		String telephone = jo.getString("telephone");
		String skype = jo.getString("skype");
		String misc = jo.getString("misc");
		CampStates states = CampStatesInterface._fromJSONObject(jo.getJSONObject("states"));
		ContactDetails d = new ContactDetails(id,email,mobile,telephone,skype,misc);
		d.states().update(states);
		d.setCustomerBusinessId(customerBusinessId);
		d.setCustomerBusinessKey(customerBusinessKey);
		
		return d;
	}
}
