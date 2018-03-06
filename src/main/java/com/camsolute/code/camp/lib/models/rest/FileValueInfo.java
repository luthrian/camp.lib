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

public class FileValueInfo implements VariableValueInfo, Serialization<FileValueInfo> {
	
	private String filename;
	private String mimetype;
	private String encoding;

	public FileValueInfo(String filename,String mimetype, String encoding) {
		this.filename = filename;
		this.mimetype = mimetype;
		this.encoding = encoding;
	}
	
	public String mimetype() {
		return mimetype;
	}
	
	public String encoding() {
		return encoding;
	}
	
	@Override
	public String nameInfo() {
		return this.filename;
	}

	@Override
	public String toJson() {
		return _toJson(this);
	}

	public static String _toJson(FileValueInfo f) {
		String json = "{";
		json += "\"filename\":\""+f.nameInfo()+"\",";
		json += "\"mimetype\":\""+f.mimetype()+"\",";
		json += "\"encoding\":\""+f.encoding()+"\",";
		json += "}";
		return json;
	}
	@Override
	public FileValueInfo fromJson(String json) {
		return _fromJson(json);
	}

	public static FileValueInfo _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static FileValueInfo _fromJSONObject(JSONObject jo) {
		String filename = jo.getString("filename");
		String mimetype = jo.getString("mimetype");
		String encoding = jo.getString("encoding");
		return new FileValueInfo(filename,mimetype,encoding);
	}
}
