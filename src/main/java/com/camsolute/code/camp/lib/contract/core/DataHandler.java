package com.camsolute.code.camp.lib.contract.core;

import org.json.JSONObject;

public interface DataHandler {
	public String toJson(Object data);
	public Object fromJson(String json);
	public Object fromJSONObject(JSONObject jo);
}
