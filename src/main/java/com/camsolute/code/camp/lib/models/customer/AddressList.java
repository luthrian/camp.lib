package com.camsolute.code.camp.lib.models.customer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class AddressList extends ArrayList<Address> implements Serialization<AddressList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3564030965794875333L;

	@Override
	public String toJson() {
		return _toJson(this);
	}

	@Override
	public AddressList fromJson(String json) {
		return _fromJson(json);
	}

	public static String _toJson(AddressList al) {
		String json = "[";
		boolean start = true;
		for(Address a:al) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += AddressInterface._toJson(a);
			
		}
		json += "]";
		return json;
	}
	
	public static AddressList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static AddressList _fromJSONArray(JSONArray ja) {
		AddressList al = new AddressList();
		for(Object jo:ja.toList()) {
			al.add(AddressInterface._fromJSONObject((JSONObject) jo));
		}
		return al;
	}

}
