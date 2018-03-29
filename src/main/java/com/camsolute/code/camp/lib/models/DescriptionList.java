package com.camsolute.code.camp.lib.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class DescriptionList extends ArrayList<Description> implements Serialization<DescriptionList>{


	@Override
	public String toJson() {
		return _toJson(this);
	}

	@Override
	public DescriptionList fromJson(String json) {
		return _fromJson(json);
	}

	public static String _toJson(DescriptionList dl) {
		String json = "[";
		boolean start = true;
		for(Description d:dl) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += DescriptionInterface._toJson(d);
			
		}
		json += "]";
		return json;
	}
	
	public static DescriptionList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static DescriptionList _fromJSONArray(JSONArray ja) {
		DescriptionList dl = new DescriptionList();
		for(Object jo:ja.toList()) {
			dl.add(DescriptionInterface._fromJSONObject((JSONObject) jo));
		}
		return dl;
	}
}
