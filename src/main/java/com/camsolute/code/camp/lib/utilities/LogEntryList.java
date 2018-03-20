package com.camsolute.code.camp.lib.utilities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.contract.Serialization;

public class LogEntryList extends ArrayList<LogEntry<? extends IsObjectInstance<?>>> implements Serialization<LogEntryList>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5345526852213584869L;

	@Override
	public String toJson() {
		return _toJson(this);
	}

	@Override
	public LogEntryList fromJson(String json) {
		return _fromJson(json);
	}

	public static String _toJson(LogEntryList opl) {
		String json = "[";
		boolean start = true;
		for(LogEntry<? extends IsObjectInstance<?>> op:opl) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += LogEntryInterface._toJson(op);
			
		}
		json += "]";
		return json;
	}
	
	public static LogEntryList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static LogEntryList _fromJSONArray(JSONArray ja) {
		LogEntryList lel = new LogEntryList();
		for(Object jo:ja.toList()) {
			lel.add(LogEntryInterface._fromJSONObject((JSONObject) jo));
		}
		return lel;
	}

	
}
