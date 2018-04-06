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
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class TaskList extends ArrayList<Task> implements Serialization<TaskList>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1753736195026936080L;

	@Override
	public String toJson() {
		return _toJson(this);
	}

	public static String _toJson(TaskList tl) {
		String json = "[";
		boolean start = true;
		for(Task t:tl) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += Task._toJson(t);
			
		}
		json += "]";
		return json;
	}
	
	@Override
	public TaskList fromJson(String json) {
		return _fromJson(json);
	}

	public static TaskList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static TaskList _fromJSONArray(JSONArray ja) {
		TaskList tl = new TaskList();
		Iterator<Object> i = ja.iterator();
		while(i.hasNext()) {
			JSONObject jo = (JSONObject) i.next();
			tl.add(Task._fromJSONObject((JSONObject) jo));
		}
		return tl;
	}


}
