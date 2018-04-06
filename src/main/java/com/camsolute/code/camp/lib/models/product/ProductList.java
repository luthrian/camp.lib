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
package com.camsolute.code.camp.lib.models.product;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasListSelection;
import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.models.order.OrderPosition;

public class ProductList extends ArrayList<Product> implements Serialization<ProductList>, HasListSelection<Product>{

	int selected = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4618015377295630827L;

	@Override
	public String toJson() {
		return _toJson(this);
	}

	@Override
	public ProductList fromJson(String json) {
		return _fromJson(json);
	}

	public static String _toJson(ProductList pl) {
		String json = "{";
		json += "\"selected\":"+pl.selectionIndex();
		json += ",\"isEmpty\":"+pl.isEmpty();
		json += ",\"list\":[";
		boolean start = true;
		for(Product p:pl) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += ProductInterface._toJson(p);
			
		}
		json += "]}";
		return json;
	}
	
	public static ProductList _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static ProductList _fromJSONObject(JSONObject jo) {
		ProductList pl = new ProductList();
		if(jo.getBoolean("isEmpty")) {
			return pl;
		}
		pl.setSelectionIndex(jo.getInt("selected"));
		Iterator<Object> i = jo.getJSONArray("list").iterator();
		while(i.hasNext()) {
			JSONObject j = (JSONObject) i.next();
			pl.add(ProductInterface._fromJSONObject(j));
		}
		return pl;
	}

	@Override
	public Product selected() {
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
		for(Product p: this) {
			if(p.id()==itemId) {
				selected = ctr;
				break;
			}
			ctr ++;
		}
		return selected;
	}

	@Override
	public int select(Product item) {
		int ctr = 0;
		for(Product p: this) {
			if(item.businessId().equals(p.businessId())) {
				selected = ctr;
				break;
			}
			ctr++;
		}
		return selected;
	}


}
