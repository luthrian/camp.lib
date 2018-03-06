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
package com.camsolute.code.camp.lib.models.process;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.models.process.Process.ProcessType;

public class ProcessMap<U,T extends Process<U,T>> extends HashMap<ProcessType,ProcessList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1062446517829391790L;
/*
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessMap<U,T> fromJson(String json) {
		HashMap<ProcessType,ArrayList<Process<?,?>>> hm = _fromJson(json);
		ProcessMap<U,T> pm = new ProcessMap<U,T>();
		for(ProcessType type: hm.keySet()){
			ProcessList<U,T> pl = new ProcessList<U,T>();
			for(Process<?,?> p: hm.get(type)){
				@SuppressWarnings("unchecked")
				Process<U,T> op = (Process<U,T>)p;
				pl.add(op);
			}
			pm.put(type, pl);
		}
		return pm;
	}

	public static String _toJson(ProcessMap<?,?> p) {
		String json = "{";
		boolean start = true;
		for(ProcessType type: p.keySet()){
			if(!start){
				json += ",";
			} else {
				start = false;
			}
			json += "\""+type.name()+"\":"+ p.get(type).toJson();
			
		}
		json += "}";
		return json;
	}
	
	public static HashMap<ProcessType,ArrayList<Process<?,?>>> _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static HashMap<ProcessType,ArrayList<Process<?,?>>> _fromJSONObject(JSONObject jo) {
		HashMap<ProcessType,ArrayList<Process<?,?>>> hm = new HashMap<ProcessType,ArrayList<Process<?,?>>>();
		for(String types: jo.keySet()){
			ProcessType type = ProcessType.valueOf(types);
			ArrayList<Process<?,?>> al = ProcessList._fromJSONArray(jo.getJSONArray(types));
			hm.put(type, al );
		}
		return hm;
	}
	*/
}
