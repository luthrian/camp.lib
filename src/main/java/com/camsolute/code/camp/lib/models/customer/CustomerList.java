package com.camsolute.code.camp.lib.models.customer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class CustomerList extends ArrayList<Customer> implements Serialization<CustomerList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1529775917179729791L;

	@Override
	public String toJson() {
		return _toJson(this);
	}

	@Override
	public CustomerList fromJson(String json) {
		return _fromJson(json);
	}

	public static String _toJson(CustomerList cl) {
		String json = "[";
		boolean start = true;
		for(Customer c:cl) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += CustomerInterface._toJson(c);
			
		}
		json += "]";
		return json;
	}
	
	public static CustomerList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static CustomerList _fromJSONArray(JSONArray ja) {
		CustomerList cl = new CustomerList();
		for(Object jo:ja.toList()) {
			cl.add(CustomerInterface._fromJSONObject((JSONObject) jo));
		}
		return cl;
	}


}
