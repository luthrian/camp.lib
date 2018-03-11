package com.camsolute.code.camp.lib.models.customer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class ContactDetailsList extends ArrayList<ContactDetails> implements Serialization<ContactDetailsList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3564030965794875333L;

	@Override
	public String toJson() {
		return _toJson(this);
	}

	@Override
	public ContactDetailsList fromJson(String json) {
		return _fromJson(json);
	}

	public static String _toJson(ContactDetailsList dl) {
		String json = "[";
		boolean start = true;
		for(ContactDetails d:dl) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += ContactDetailsInterface._toJson(d);
			
		}
		json += "]";
		return json;
	}
	
	public static ContactDetailsList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static ContactDetailsList _fromJSONArray(JSONArray ja) {
		ContactDetailsList dl = new ContactDetailsList();
		for(Object jo:ja.toList()) {
			dl.add(ContactDetailsInterface._fromJSONObject((JSONObject) jo));
		}
		return dl;
	}

}
