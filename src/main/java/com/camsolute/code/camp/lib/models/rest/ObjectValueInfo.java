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

public class ObjectValueInfo implements VariableValueInfo, Serialization<ObjectValueInfo> {
	
	private String objectTypeName;
	private String  serializationDataFormat;

	public ObjectValueInfo(String objectTypeName,String serializationDataFormat) {
		this.objectTypeName = objectTypeName;
		this.serializationDataFormat = serializationDataFormat;
	}
	
	public String serializationDataFormat() {
		return serializationDataFormat;
	}
	
	public void setSerializationDataFormat(String serializationDataFormat) {
		this.serializationDataFormat = serializationDataFormat;
	}
	
	@Override
	public String nameInfo() {
		return this.objectTypeName;
	}
	
	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}
	
	@Override
	public String toJson() {
		return _toJson(this);
	}

	public static String _toJson(ObjectValueInfo f) {
		String json = "{";
		json += "\"objectTypeName\":\""+f.nameInfo()+"\",";
		json += "\"serializationDataFormat\":\""+f.serializationDataFormat()+"\",";
		json += "}";
		return json;
	}
	@Override
	public ObjectValueInfo fromJson(String json) {
		return _fromJson(json);
	}

	public static ObjectValueInfo _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static ObjectValueInfo _fromJSONObject(JSONObject jo) {
		String objectTypeName = jo.getString("objectTypeName");
		String serializationDataFormat = jo.getString("serializationDataFormat");
		return new ObjectValueInfo(objectTypeName,serializationDataFormat);
	}

}
