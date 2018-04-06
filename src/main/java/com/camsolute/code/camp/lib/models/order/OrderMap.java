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

import java.util.HashMap;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class OrderMap extends HashMap<String,Order> implements Serialization<OrderMap> {
    public String toJson() {
    	return _toJson(this);
    }
    public static String _toJson(OrderMap om) {
    	String json = "{";
    	json += _toInnerJson(om);
    	json += "}";
    	return json;
    }
    public static String _toInnerJson(OrderMap om) {
    	String json = "";
    	boolean start = true;
    	for(String key:om.keySet()) {
    		if(!start) {
    			json += ",";
    		} else {
    			start = false;
    		}
    		json += "\""+key+"\":"+om.get(key).toJson();
    	}
    	return json;
    }
    public OrderMap fromJson(String json) {
    	return _fromJson(json);
    }
    public static OrderMap _fromJson(String json) {
    	return _fromJSONObject(new JSONObject(json));
    }
    public static OrderMap _fromJSONObject(JSONObject jo) {
    	OrderMap om = new OrderMap();
    	for(String key:jo.keySet()) {
    		om.put(key,OrderInterface._fromJSONObject(jo.getJSONObject(key)));
    	}
    	return om;
    }
}
