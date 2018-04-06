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

import java.util.HashMap;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class AttributeMap extends HashMap<String,AttributeList> implements Serialization<AttributeMap>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7656221443212960098L;
	
	public AttributeMap clone() {
		return clone(this);
	}

	public static AttributeMap clone(AttributeMap map) {
		return _fromJson(map.toJson());
	}
	@Override
	public String toJson() {
		return _toJson(this);
	}

	@Override
	public AttributeMap fromJson(String json) {
		return _fromJson(json);
	}

	public static String _toJson(AttributeMap am) {
		String json = "{";
		boolean start = true;
		for(String group:am.keySet()) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += "\""+group+"\":"+am.get(group).toJson();
			
		}
		json += "}";
		return json;
	}
	
	public static AttributeMap _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static AttributeMap _fromJSONObject(JSONObject jo) {
		AttributeMap am = new AttributeMap();
		for(String group:jo.keySet()) {
			am.put(group, AttributeList._fromJSONArray(jo.getJSONArray(group)));
		}
		return am;
	}
}
