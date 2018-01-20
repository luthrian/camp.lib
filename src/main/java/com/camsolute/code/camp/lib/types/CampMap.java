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

import com.camsolute.code.camp.lib.Attribute;
import com.camsolute.code.camp.lib.Attribute;
import com.camsolute.code.camp.lib.utilities.Util;


/**
 * @author Christopher Campbell
 *
 */
public class CampMap extends Attribute<HashMap<String,CampList>>  {
	
	private HashMap<String,CampList> map;
	
	private String attributeGroup = null;
	private int attributePosition = 0;
	
	private String attributeBusinessId = null;

	@Override
	public String attributeBusinessId() {
		return attributeBusinessId+Util.DB._NS+id();
	}

	@Override
	public String attrbuteBusinessId(String id) {
		String prev = this.attributeBusinessId;
		this.attributeBusinessId = id;
		return prev;
	}

	@Override
	public String onlyAttributeBusinessId() {
		return this.attributeBusinessId;
	}

	@Override
	public String initialAttributeBusinessId() {
		return attributeBusinessId+Util.DB._NS+0;
	}

	public CampMap(){
		super(null, AttributeType._map, null	);
		this.map = new HashMap<String,CampList>();
	}
	
	public CampMap(String name){
		super(name, AttributeType._map, null	);
		this.map = new HashMap<String,CampList>();
	}
	
	public CampMap(String name,String str,CampList al){
		super(name, AttributeType._map, null	);
		this.map = new HashMap<String,CampList>();
		this.map.put(str, al);
	}

	public CampMap(String name,String str){
		super(name, AttributeType._map, null	);
		this.map = new HashMap<String,CampList>();
		CampList al = new CampList();
		this.map.put(str, al);
	}
	
	public CampMap(String name,HashMap<String,CampList> map){
		super(name, AttributeType._map, null	);
		this.map = map;
	}
	
	public CampMap defaultInstance(String name,String defaultValue){
		return new CampMap(name);
	}


	
	public int size(){
		return map.size();
	}
	
	public boolean isEmpty(){
		return map.isEmpty();
	}
	
	public CampList get(String key){
		return map.get(key);
	}
	
	public boolean containsKey(String key){
		return map.containsKey(key);
	}
	
	public CampList put(String key, CampList value){
		return this.map.put(key, value);
	}
	
	public void putAll(CampMap map){
		for(String k:map.keySet()){
			CampList v = map.get(k);
			this.map.put(k, v);
		}
	}
	
	public CampList remove(String key){
		return map.remove(key);
	}

	public void clear(){
		map.clear();
	}
	
	public boolean containsValue(CampList value){
		return this.map.containsValue(value);
	}
	
	public HashMap<String,CampList> clone(){
		return  (HashMap<String, CampList>) this.map.clone();
	}
	
	public Set<String> keySet(){
		return this.map.keySet();
	}
	
	public Collection<CampList> values(){
		return this.map.values();
	}
	
	public Set<Entry<String, CampList>> entrySet(){
		return this.map.entrySet();
	}
	

	@Override
	public HashMap<String, CampList> value() {
		return this.map;
	}
	
	public CampList toList(){
		CampList l = new CampList();
		for(String key:keySet()){
			l.addAll(get(key));
		}
		return l;
	}

	@Override
	public HashMap<String, CampList> value(HashMap<String, CampList> value) {
		HashMap<String, CampList> prev = this.map;
		this.map = value;
		return prev;
	}
	
	public final static CampMap fromDList(CampList list){
		CampMap map = new CampMap();
		for(Attribute<?> a: list.value()){
			if(!map.containsKey(a.attributeGroup())) {
				map.put(a.attributeGroup(), new CampList());
			}
			map.get(a.attributeGroup()).add(a);
		}
		return map;
	}
	
	public final static CampMap fromOList(CampList list){
		CampMap map = new CampMap();
		for(Attribute<?> pa: list.value()){
			if(!map.containsKey(pa.attributeGroup()))map.put(pa.attributeGroup(), new CampList());
			map.get(pa.attributeGroup()).add(pa);
		}
		return map;
	}

	@Override
	public Attribute<?> valueFromString(String value) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public String attributeGroup() {
		return this.attributeGroup;
	}

	@Override
	public String attributeGroup(String group) {
		String prev = this.attributeGroup;
		this.attributeGroup = group;
		return prev;
	}

	@Override
	public int attributePosition() {
		return this.attributePosition;
	}

	@Override
	public int attributePosition(int position) {
		int prev = this.attributePosition;
		this.attributePosition = position;
		return prev;
	} 


}
