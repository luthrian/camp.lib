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

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasListSelection;
import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.contract.core.Value;

public class ValueList extends ArrayList<Value<?,?>> implements Serialization<ValueList>, HasListSelection<Value<?,?>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2125252610616975496L;
	private int selected = 0;
	
	@Override
	public Value<?,?> selected() {
		return get(selected);
	}

	@Override
	public int selectionIndex() {
		return selected;
	}

	@Override
	public void setSelectionIndex(int index) {
		selected = index;
	}

	@Override
	public int select(int itemId) {
		if(itemId == 0){
			return 0;
		}
		int ctr = 0;
		for(Value<?,?> v: this) {
			if(v.id() == itemId) {
				selected = ctr;
				break;
			}
			ctr++;
		}
		return selected;
	}

	@Override
	public int select(Value<?,?> item) {
		return selected;
	}

	@Override
	public String toJson() {
		return _toJson(this);
	}
	
	public static String _toJson(ValueList vl) {
		String json = "{";
		json += "\"selected\":"+vl.selected();
		json += ",\"isEmpty\":"+vl.isEmpty();
		json += ",\"list\":[";
		boolean start = true;
		for(Value<?,?> v: vl) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += v.toJson();
		}
		json += "]}";
		return json;
	}
	
	@Override
	public ValueList fromJson(String json) {
		return _fromJson(json);
	}
	
	public static ValueList _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static ValueList _fromJSONObject(JSONObject jo) {
		ValueList vl = new ValueList();
		if(jo.getBoolean("isEmpty")) {
			return vl;
		}
		vl.setSelectionIndex(jo.getInt("selected"));
		Iterator<Object> i = jo.getJSONArray("list").iterator();
		while(i.hasNext()) {
			JSONObject j = (JSONObject)i.next();
			vl.add(ValueInterface._fromJSONObject(j));
		}
		return vl;
	}

}
