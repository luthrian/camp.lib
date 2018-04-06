package com.camsolute.code.camp.lib.models.rest;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class FormVariables implements Serialization<FormVariables>{
	private Variables variables = new Variables();
	public Variables variables() {
		return variables;
	}
	public void setVariables(Variables variables) {
		this.variables = variables;
	}
	
	@Override
	public String toJson() {
		return _toJson(this);
	}
	public static String _toJson(FormVariables v) {
		String json = "{";
		json += "\"variables\":"+Variables._toJson(v.variables());
		json += "}";
		return json;
	}
	@Override
	public FormVariables fromJson(String json) {
		return _fromJson(json);
	}
	
	public static FormVariables _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static FormVariables _fromJSONObject(JSONObject jo) {
		FormVariables v = new FormVariables();
		v.setVariables(Variables._fromJSONObject(jo.getJSONObject("variables")));
		return v;
	}
}
