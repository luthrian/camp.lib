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

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class ProductList extends ArrayList<Product> implements Serialization<ProductList>{

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
		String json = "[";
		boolean start = true;
		for(Product p:pl) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += ProductInterface._toJson(p);
			
		}
		json += "]";
		return json;
	}
	
	public static ProductList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static ProductList _fromJSONArray(JSONArray ja) {
		ProductList pl = new ProductList();
		for(Object jo:ja.toList()) {
			pl.add(ProductInterface._fromJSONObject((JSONObject) jo));
		}
		return pl;
	}


}
