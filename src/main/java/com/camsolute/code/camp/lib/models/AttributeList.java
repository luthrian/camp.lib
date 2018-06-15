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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.contract.core.Value;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;
import com.camsolute.code.camp.lib.types.BooleanValue;
import com.camsolute.code.camp.lib.types.ComplexValue;
import com.camsolute.code.camp.lib.types.DateTimeValue;
import com.camsolute.code.camp.lib.types.DateValue;
import com.camsolute.code.camp.lib.types.IntegerValue;
import com.camsolute.code.camp.lib.types.ListValue;
import com.camsolute.code.camp.lib.types.MapValue;
import com.camsolute.code.camp.lib.types.StringValue;
import com.camsolute.code.camp.lib.types.TableValue;
import com.camsolute.code.camp.lib.types.TextValue;
import com.camsolute.code.camp.lib.types.TimeValue;
import com.camsolute.code.camp.lib.types.TimestampValue;
import com.camsolute.code.camp.lib.utilities.Util;

public class AttributeList extends ArrayList<Attribute<? extends Value<?>>> implements Serialization<AttributeList>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7656221443212960098L;

	public AttributeList clone() {
		return clone(this);
	}
	public static AttributeList clone(AttributeList list) {
		return _fromJson(list.toJson());
	}
	@Override
	public String toJson() {
		return _toJson(this);
	}

	@Override
	public AttributeList fromJson(String json) {
		return _fromJson(json);
	}

	public static String _toJson(AttributeList al) {
		String json = "[";
		boolean start = true;
		for(Attribute<? extends Value<?>> a:al) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += AttributeInterface._toJson(a);
			
		}
		json += "]";
		return json;
	}
	
	public static AttributeList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static AttributeList _fromJSONArray(JSONArray ja) {
		AttributeList al = new AttributeList();
		Iterator<Object> i = ja.iterator();
		while(i.hasNext()) {
//		for(Object jo:ja.toList()) {
			JSONObject jo = (JSONObject) i.next();
			al.add(AttributeInterface._fromJSONObject(jo));
		}
		return al;
	}
}
