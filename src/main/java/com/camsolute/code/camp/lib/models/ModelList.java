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

import com.camsolute.code.camp.lib.contract.HasListSelection;
import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.models.order.OrderPosition;

public class ModelList extends ArrayList<Model> implements Serialization<ModelList>, HasListSelection<Model> {

	private int selected = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = -5697032410270499148L;

	public int selectionIndex() {
		return selected;
	}
	public void setSelectionIndex(int index) {
		selected = index;
	}
	public Model selected() {
		return get(selected);
	}
	public int select(int modelId) {
		int ctr = 0;
		for(Model m: this){
			if(m.id() == modelId) {
				selected = ctr;
				break;
			}
			ctr++;
		}
		return selected;
	}
	public int select(Model model) {
		int ctr = 0;
		for(Model m: this) {
			if(model.businessId().equals(m.businessId())) {
				selected = ctr;
				break;
			}
			ctr++;
		}
		return selected;
	}
		
	@Override
	public ModelList fromJson(String json) {
		return _fromJson(json);
	}

	public static String _toJson(ModelList ml) {
		String json = "{";
		json += "\"selected\":"+ml.selectionIndex();
		json += ",\"isEmpty\":"+ml.isEmpty();
		json += ",\"list\":[";
		boolean start = true;
		for(Model m:ml) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += m.toJson();
			
		}
		json += "]}";
		return json;
	}
	
	public static ModelList _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static ModelList _fromJSONObject(JSONObject jo) {
		ModelList ml = new ModelList();
		if(jo.getBoolean("isEmpty")) {
			return ml;
		}
		ml.setSelectionIndex(jo.getInt("selected"));
		Iterator<Object> i = jo.getJSONArray("list").iterator();
		while(i.hasNext()) {
			JSONObject j = (JSONObject) i.next();
			ml.add(ModelInterface._fromJSONObject(j));
		}
		return ml;
	}

	@Override
	public String toJson() {
		return _toJson(this);
	}

	public ModelList clone() {
		return _fromJson(toJson());
	}
}
