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
package com.camsolute.code.camp.lib.models.customer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class AddressList extends ArrayList<Address> implements Serialization<AddressList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3564030965794875333L;

	@Override
	public String toJson() {
		return _toJson(this);
	}

	@Override
	public AddressList fromJson(String json) {
		return _fromJson(json);
	}

	public static String _toJson(AddressList al) {
		String json = "[";
		boolean start = true;
		for(Address a:al) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += AddressInterface._toJson(a);
			
		}
		json += "]";
		return json;
	}
	
	public static AddressList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static AddressList _fromJSONArray(JSONArray ja) {
		AddressList al = new AddressList();
		for(Object jo:ja.toList()) {
			al.add(AddressInterface._fromJSONObject((JSONObject) jo));
		}
		return al;
	}

}
