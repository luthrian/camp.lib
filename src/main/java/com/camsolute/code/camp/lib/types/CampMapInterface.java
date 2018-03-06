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
/**
 * 
 */
package com.camsolute.code.camp.lib.types;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.AttributeInterface;
import com.camsolute.code.camp.lib.models.Value;
import com.camsolute.code.camp.lib.models.ValueInterface;
import com.camsolute.code.camp.lib.utilities.Util;
import static com.camsolute.code.camp.lib.utilities.Util.*;


/**
 * @author Christopher Campbell
 *
 */
public interface CampMapInterface extends AttributeInterface<MapValue>  {

	public int size();

	public boolean isEmpty();

	public Attribute<?> get(String key);

	public boolean containsKey(String key);

	public Attribute<?> put(String key, Attribute<?> value);

	public void putAll(CampMap map);

	public Attribute<?> remove(String key);

	public void clear();

	public boolean containsValue(Attribute<?> value);

	public HashMap<String,Attribute<?>> clone();

	public Set<String> keySet();

	public Collection<Attribute<?>> values();

	public Set<Entry<String, Attribute<?>>> entrySet();

	public CampList toList();

	public MapValue valueFromString(String jsonValue);
	
	public static CampMap fromList(CampList list){
		CampMap map = new CampMap();
		for(Attribute<?> a: list.value().value()){
			map.put(a.group().name(),a);
		}
		return map;
	}
	
	public static String _toJson(CampMap cm) {
		String json = "{";
		json += AttributeInterface._toInnerJson(cm);
		json = "}";
		return json;
	}
	
	public static CampMap _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static CampMap _fromJSONObject(JSONObject jo) {
		CampMap cm = new CampMap();
		cm = (CampMap) AttributeInterface._fromJSONObject(jo);
		return cm;
	}



}
