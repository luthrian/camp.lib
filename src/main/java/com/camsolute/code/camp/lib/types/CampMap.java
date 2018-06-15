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

import com.camsolute.code.camp.lib.contract.core.Value;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.AttributeInterface;
import com.camsolute.code.camp.lib.models.ValueInterface;
import com.camsolute.code.camp.lib.utilities.Util;
import static com.camsolute.code.camp.lib.utilities.Util.*;


/**
 * @author Christopher Campbell
 *
 */
public class CampMap extends Attribute<MapValue> implements CampMapInterface {

    public CampMap(){
        super(null, AttributeType._map, null	);
    }

	public CampMap(String name){
		super(name, AttributeType._map, null	);
	}

	public CampMap(String name,String defaultValue,MapValue value){
		super(name, AttributeType._map, defaultValue	);
		this.setValue(value);
	}

	public CampMap(String name,String defaultValue){
		super(name, AttributeType._map, defaultValue	);
	}

	public CampMap(String name,String defaultValue, String value){
		super(name, AttributeType._map, defaultValue	);
		this.setValue(new MapValue(ValueInterface._fromMapJson(value)));
	}

	public int size(){
      return this.value().value().size();
	}

	public boolean isEmpty(){
      return this.value().value().isEmpty();
	}

	public Attribute<?> get(String key){
      return this.value().value().get(key);
	}

	public boolean containsKey(String key){
      return this.value().value().containsKey(key);
	}

	public Attribute<?> put(String key, Attribute<?> value){
		this.states().modify();
      return this.value().value().put(key, value);
	}

	public void putAll(CampMap map){
		for(String k:map.keySet()){
			Attribute<?> v = map.get(k);
			this.value().value().put(k, v);
		}
		this.states().modify();
	}

	public Attribute<?> remove(String key){
			this.states().modify();
      return this.value().value().remove(key);
	}

	public void clear(){
      this.value().value().clear();
      this.states().modify();
	}

	public boolean containsValue(Attribute<?> value){
      return this.value().value().containsValue(value);
	}

	public Set<String> keySet(){
      return this.value().value().keySet();
	}

	public Collection<Attribute<?>> values(){
      return this.value().value().values();
	}

	public Set<Entry<String, Attribute<?>>> entrySet(){
      return this.value().value().entrySet();
	}


	public CampList toList(){
		CampList l = new CampList();
		for(String key:keySet()){
			l.value().value().add(get(key));
		}
		return l;
	}

	@Override
	public MapValue valueFromString(String jsonValue) {
      return new MapValue(ValueInterface._fromMapJson(jsonValue));
	}

	@Override
	public String toJson() {
		return CampMapInterface._toJson(this);
	}

	@Override
	public CampMap fromJson(String json) {
		return CampMapInterface._fromJson(json);
	}

}
