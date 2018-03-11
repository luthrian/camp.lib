package com.camsolute.code.camp.lib.models.customer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class TouchPointList extends ArrayList<TouchPoint> implements Serialization<TouchPointList>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7036891343268204817L;

	@Override
	public String toJson() {
		return _toJson(this);
	}

	@Override
	public TouchPointList fromJson(String json) {
		return _fromJson(json);
	}

	public static String _toJson(TouchPointList tl) {
		String json = "[";
		boolean start = true;
		for(TouchPoint t:tl) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += TouchPointInterface._toJson(t);
			
		}
		json += "]";
		return json;
	}
	
	public static TouchPointList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static TouchPointList _fromJSONArray(JSONArray ja) {
		TouchPointList tl = new TouchPointList();
		for(Object jo:ja.toList()) {
			tl.add(TouchPointInterface._fromJSONObject((JSONObject) jo));
		}
		return tl;
	}


}
