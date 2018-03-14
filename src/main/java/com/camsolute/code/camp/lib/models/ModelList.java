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
package com.camsolute.code.camp.lib.models;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class ModelList extends ArrayList<Model> implements Serialization<ModelList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5697032410270499148L;

	@Override
	public ModelList fromJson(String json) {
		return _fromJson(json);
	}

	public static String _toJson(ModelList ml) {
		String json = "[";
		boolean start = true;
		for(Model m:ml) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += m.toJson();
			
		}
		json += "]";
		return json;
	}
	
	public static ModelList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static ModelList _fromJSONArray(JSONArray ja) {
		ModelList ml = new ModelList();
		Iterator<Object> i = ja.iterator();
		while(i.hasNext()) {
			JSONObject jo = (JSONObject) i.next();
			ml.add(ModelInterface._fromJSONObject(jo));
		}
		return ml;
	}

	@Override
	public String toJson() {
		return _toJson(this);
	}

}
