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

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class ProcessExecutionList extends ArrayList<ProcessExecution> implements Serialization<ProcessExecutionList>{

	@Override
	public String toJson() {
		return _toJson(this);
	}

	public static String _toJson(ProcessExecutionList el) {
		String json = "[";
		boolean start = true;
		for(ProcessExecution e:el) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += ProcessExecution._toJson(e);
			
		}
		json += "]";
		return json;
	}
	
	@Override
	public ProcessExecutionList fromJson(String json) {
		return _fromJson(json);
	}

	public static ProcessExecutionList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static ProcessExecutionList _fromJSONArray(JSONArray ja) {
		ProcessExecutionList el = new ProcessExecutionList();
		for(Object jo:ja.toList()) {
			el.add(ProcessExecution._fromJSONObject((JSONObject) jo));
		}
		return el;
	}


}
