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

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class VariableValue implements Serialization<VariableValue>{
	@JsonIgnore
	public static final String _F = "["+VariableValue.class.getSimpleName()+"]";
	@JsonIgnore
	public static final boolean _DEBUG = false;
	
	public static enum VariableValueType {
		String, Integer, Short, Long, Double, Date, Boolean, Object, File;
	}

	private String value;
	private VariableValueType type;
	private boolean local = false;
	
	public VariableValue(String value, VariableValueType type, boolean local) {
		this(value,type);
		this.local = local;
	}
	public VariableValue(String value, VariableValueType type) {
		this.value = value;			
		this.type = type;
	}
	
	public String value() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String type() {
		return type.name();
	}
	
	public boolean local() {
		return local;
	}
	
	public void setLocal(boolean b) {
		this.local = b;
	}
	@Override
	public String toJson() {
		return _toJson(this);
	}
	public static String _toJson(VariableValue v) {
		String json = "{";
		json += "\"value\":\""+v.value()+"\",";
		json += "\"type\":\""+v.type()+"\",";
		json += "\"local\":\""+String.valueOf(v.local())+"\"";
		json += "}";
		return json;
	}
	@Override
	public VariableValue fromJson(String json) {
		return _fromJson(json);
	}
	
	public static VariableValue _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static VariableValue _fromJSONObject(JSONObject jo) {
		String value = jo.getString("value");
		VariableValueType type = VariableValueType.valueOf(jo.getString("type"));
		boolean local = jo.getBoolean("local");
		return new VariableValue(value,type,local);
	}
}
