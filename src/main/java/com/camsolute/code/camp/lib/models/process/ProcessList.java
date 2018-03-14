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
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

//public class ProcessList<U,T extends Process<U,T>> extends ArrayList<Process<U,T>>{
public class  ProcessList extends ArrayList<Process<?>> implements Serialization<ProcessList>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8624653726555787594L;
	
	public ProcessList subList(int startIndex, int endIndex) {
		ProcessList pl = new ProcessList();
		for(Process<?> p: super.subList(startIndex,endIndex)){
			pl.add(p);
		}
		return pl;
	}
	
	@Override
	public String toJson() {
		return _toJson(this);
	}

	@Override
	public ProcessList fromJson(String json) {
		return _fromJson(json);
	}

	public static String _toJson(ProcessList pl) {
		String json = "[";
		boolean start = true;
		for(Process<?> p:pl) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += ProcessInterface._toJson(p);
			
		}
		json += "]";
		return json;
	}
	
	public static ProcessList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static ProcessList _fromJSONArray(JSONArray ja) {
		ProcessList pl = new ProcessList();
//		Iterator<Object> i = ja.iterator();
		for(int i = 0; i < ja.length();i++) {
			pl.add(ProcessInterface._fromJSONObject(ja.getJSONObject(i)));
		}
		return pl;
	}

}
