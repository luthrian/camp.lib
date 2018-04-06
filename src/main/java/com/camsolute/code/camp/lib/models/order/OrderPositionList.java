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
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasListSelection;
import com.camsolute.code.camp.lib.contract.Serialization;


public class OrderPositionList extends ArrayList<OrderPosition> implements Serialization<OrderPositionList>, HasListSelection<OrderPosition> {

	private int selected = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2510549814966325989L;

	@Override
	public String toJson() {
		return _toJson(this);
	}

	@Override
	public OrderPositionList fromJson(String json) {
		return _fromJson(json);
	}

	public static String _toJson(OrderPositionList opl) {
		String json = "{";
		json += "\"selected\":"+opl.selectionIndex();
		json += ",\"isEmpty\":"+opl.isEmpty();
		json += ",\"list\":[";
		boolean start = true;
		for(OrderPosition op:opl) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += OrderPositionInterface._toJson(op);
			
		}
		json += "]}";
		return json;
	}
	
	public static OrderPositionList _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static OrderPositionList _fromJSONObject(JSONObject jo) {
		OrderPositionList opl = new OrderPositionList();
		if(jo.getBoolean("isEmpty")) {
			return opl;
		}
		opl.setSelectionIndex(jo.getInt("selected"));
		Iterator<Object> i = jo.getJSONArray("list").iterator();
		while(i.hasNext()){
			JSONObject j = (JSONObject)i.next();
			opl.add(OrderPositionInterface._fromJSONObject(j));
		}
		return opl;
	}

	@Override
	public OrderPosition selected() {
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
		int ctr = 0; 
		for(OrderPosition op : this) {
			if(op.id() == itemId) {
				selected = ctr;
				break;
			}
			ctr++;
		}
		return selected;
	}

	@Override
	public int select(OrderPosition item) {
		int ctr = 0;
		for(OrderPosition op: this) {
			if(item.businessId().equals(op.businessId())) {
				selected = ctr;
				break;
			}
			ctr++;
		}
		return selected;
	}


}

