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
package com.camsolute.code.camp.lib.models.rest;


import java.util.HashMap;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.models.rest.VariableValue.VariableValueType;

public class Variables implements Serialization<Variables>{
	
	private HashMap<String,VariableValue> variables = new HashMap<String,VariableValue>();
	
	public Variables() {
	}
	public Variables(String variableName, VariableValue variableValue) {
		variables.put(variableName, variableValue);
	}
	public Variables(String variableName, String variableValue, VariableValueType variableType) {
		VariableValue value = new VariableValue(variableValue, variableType);
		variables.put(variableName, value);
	}	
	public Variables(String variableName, String variableValue, VariableValueType variableType, boolean local) {
		VariableValue value = new VariableValue(variableValue, variableType,local);
		variables.put(variableName, value);
	}	

	public HashMap<String,VariableValue> variables() {
		return this.variables;
	}
	
	public void setVariables(HashMap<String,VariableValue> variables) {
		this.variables = variables;
	}
	
	public void add(String name, VariableValue value) {
		variables.put(name, value);
	}
	
	public void add(String name,String value, VariableValueType type) {
		VariableValue vValue = new VariableValue(value,type);
		variables.put(name, vValue);
	}
	
	public void add(String name, String value, VariableValueType type, boolean local) {
		VariableValue vValue = new VariableValue(value,type,local);
		variables.put(name, vValue);
	}
	
	public VariableValue get(String name) {
		return variables.get(name);
	}
	@Override
	public String toJson() {
		return _toJson(this);
	}
	public static String _toJson(Variables v) {
		String json = "{";
		boolean start = true;
		for(String key: v.variables().keySet()){
			if(!start) {
				json +=",";
			} else {
				start = false;
			}
			json += "\""+key+"\":"+v.variables().get(key).toJson();
		}
		json += "}";
		return json;
	}
	@Override
	public Variables fromJson(String json) {
		return _fromJson(json);
	}
	
	public static Variables _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static Variables _fromJSONObject(JSONObject jo) {
		HashMap<String,VariableValue> variables = new HashMap<String,VariableValue>();
		for(String key:jo.keySet()) {
			variables.put(key,VariableValue._fromJSONObject(jo.getJSONObject(key)));
		}
		Variables v = new Variables();
		v.setVariables(variables);
		return v;
	}
}
