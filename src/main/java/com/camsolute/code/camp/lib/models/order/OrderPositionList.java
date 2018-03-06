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
package com.camsolute.code.camp.lib.models.order;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;


public class OrderPositionList extends ArrayList<OrderPosition> implements Serialization<OrderPositionList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2510549814966325989L;

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderPositionList fromJson(String json) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String _toJson(OrderPositionList opl) {
		String json = "[";
		boolean start = true;
		for(OrderPosition op:opl) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += OrderPositionInterface._toJson(op);
			
		}
		json += "]";
		return json;
	}
	
	public static OrderPositionList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static OrderPositionList _fromJSONArray(JSONArray ja) {
		OrderPositionList opl = new OrderPositionList();
		for(Object jo:ja.toList()) {
			opl.add(OrderPositionInterface._fromJSONObject((JSONObject) jo));
		}
		return opl;
	}


}

