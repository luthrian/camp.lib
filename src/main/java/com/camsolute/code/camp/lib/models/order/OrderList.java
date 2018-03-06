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
import com.camsolute.code.camp.lib.models.order.Order;


public class OrderList extends ArrayList<Order> implements Serialization<OrderList>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5913642995981072889L;

	@Override
	public String toJson() {
		return _toJson(this);
	}

	@Override
	public OrderList fromJson(String json) {
		return _fromJson(json);
	}

	public static String _toJson(OrderList ol) {
		String json = "[";
		boolean start = true;
		for(Order o:ol) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += OrderInterface._toJson(o);
			
		}
		json += "]";
		return json;
	}
	
	public static OrderList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static OrderList _fromJSONArray(JSONArray ja) {
		OrderList ol = new OrderList();
		for(Object jo:ja.toList()) {
			ol.add(OrderInterface._fromJSONObject((JSONObject) jo));
		}
		return ol;
	}


}
