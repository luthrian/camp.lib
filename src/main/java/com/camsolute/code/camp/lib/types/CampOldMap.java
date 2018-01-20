/*******************************************************************************
 * Copyright (C) 2017 Christopher Campbell (campbellccc@gmail.com)
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
 * 	Christopher Campbell (campbellccc@gmail.com) - all code prior and post initial release
 ******************************************************************************/
/**
 * 
 */
package com.camsolute.code.camp.lib.types;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.camsolute.code.camp.models.business.OrderProductAttribute;
import com.camsolute.code.camp.models.business.ProductAttributeDefinition;

/**
 * @author Christopher Campbell
 *
 */
public class CampOldMap<T> extends CampOldType<HashMap<String,CampOldList<T>>>  {
	
	private HashMap<String,CampOldList<T>> map;
	
	private static String defaultValue = null; //CampMap have no default
	
	public CampOldMap(){
		this.map = new HashMap<String,CampOldList<T>>();
	}
	
	public CampOldMap(String str,CampOldList<T> al){
		this.map = new HashMap<String,CampOldList<T>>();
		this.map.put(str, al);
	}

	public CampOldMap(String str){
		this.map = new HashMap<String,CampOldList<T>>();
		CampOldList<T> al = new CampOldList<T>();
		this.map.put(str, al);
	}
	
	public CampOldMap(String name,HashMap<String,CampOldList<T>> map){
		this.map = map;
	}
	
	public CampOldMap<T> defaultInstance(){
		return new CampOldMap<T>();
	}


	
	public int size(){
		return map.size();
	}
	
	public boolean isEmpty(){
		return map.isEmpty();
	}
	
	public CampOldList<T> get(String key){
		return map.get(key);
	}
	
	public boolean containsKey(String key){
		return map.containsKey(key);
	}
	
	public CampOldList<T> put(String key, CampOldList<T> value){
		return this.map.put(key, value);
	}
	
	public void putAll(CampOldMap<T> map){
		for(String k:map.keySet()){
			CampOldList<T> v = map.get(k);
			this.map.put(k, v);
		}
	}
	
	public CampOldList<T> remove(String key){
		return map.remove(key);
	}

	public void clear(){
		map.clear();
	}
	
	public boolean containsValue(CampOldList<T> value){
		return this.map.containsValue(value);
	}
	
	@Override
	public HashMap<String,CampOldList<T>> clone(){
		return  (HashMap<String, CampOldList<T>>) this.map.clone();
	}
	
	public Set<String> keySet(){
		return this.map.keySet();
	}
	
	public Collection<CampOldList<T>> values(){
		return this.map.values();
	}
	
	public Set<Entry<String, CampOldList<T>>> entrySet(){
		return this.map.entrySet();
	}
	

	@Override
	protected HashMap<String, CampOldList<T>> me() {
		return this.map;
	}
	
	public CampOldList<T> toList(){
		CampOldList<T> l = new CampOldList<T>();
		for(String key:keySet()){
			l.addAll(get(key));
		}
		return l;
	}

	@Override
	protected HashMap<String, CampOldList<T>> me(HashMap<String, CampOldList<T>> value) {
		HashMap<String, CampOldList<T>> prev = this.map;
		this.map = value;
		return prev;
	}
	
	public final static CampOldMap<ProductAttributeDefinition<?,?>> fromDList(CampOldList<ProductAttributeDefinition<?,?>> list){
		CampOldMap<ProductAttributeDefinition<?,?>> map = new CampOldMap<ProductAttributeDefinition<?,?>>();
		for(ProductAttributeDefinition<?,?> pa: list.value()){
			if(!map.containsKey(pa.group().name()))map.put(pa.group().name(), new CampOldList<ProductAttributeDefinition<?,?>>());
			map.get(pa.group().name()).add(pa);
		}
		return map;
	}
	
	public final static CampOldMap<OrderProductAttribute<?,?>> fromOList(CampOldList<OrderProductAttribute<?,?>> list){
		CampOldMap<OrderProductAttribute<?,?>> map = new CampOldMap<OrderProductAttribute<?,?>>();
		for(OrderProductAttribute<?,?> pa: list.value()){
			if(!map.containsKey(pa.group().name()))map.put(pa.group().name(), new CampOldList<OrderProductAttribute<?,?>>());
			map.get(pa.group().name()).add(pa);
		}
		return map;
	}

}
