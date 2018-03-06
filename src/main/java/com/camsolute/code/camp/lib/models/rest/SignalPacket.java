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
import com.camsolute.code.camp.lib.models.process.ProcessList;

public class SignalPacket implements Serialization<SignalPacket> {

	private ProcessList processList = new ProcessList();
	
	private Variables variables = new Variables();
	
	public SignalPacket(ProcessList processList, Variables variables) {
		this.processList = processList;
		this.variables = variables;
	}
	
	public ProcessList processList() {
		return this.processList;
	}
	
	public void setProcessList(ProcessList processList) {
		this.processList = processList;
	}
	
	public Variables variables() {
		return this.variables;
	}
	
	public void setVariables(Variables variables) {
		this.variables = variables;
	}
	
	@Override
	public String toJson() {
		return _toJson(this);
	}
	
	public static String _toJson(SignalPacket s) {
		String json = "{";
		json += "\"processList\":"+s.processList().toJson()+",";
		json += "\"variables\":"+s.variables().toJson();
		json += "}";
		return json;
	}

	@Override
	public SignalPacket fromJson(String json) {
		return _fromJson(json);
	}

	public static SignalPacket _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static SignalPacket _fromJSONObject(JSONObject jo) {
		return new SignalPacket(ProcessList._fromJSONArray(jo.getJSONArray("processList")),
				Variables._fromJSONObject(jo.getJSONObject("variables")));
	}
}
