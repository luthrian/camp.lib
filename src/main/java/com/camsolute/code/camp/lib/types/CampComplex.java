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
package com.camsolute.code.camp.lib.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import java.util.Map.Entry;

import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.AttributeList;
import com.camsolute.code.camp.lib.models.Value;
import com.camsolute.code.camp.lib.models.ValueInterface;

public class CampComplex extends Attribute<ComplexValue> implements CampComplexInterface {


	public CampComplex(){
		super(null, AttributeType._complex, null);
	}

	public CampComplex(String name){
		super(name, AttributeType._complex, null);
	}

	public CampComplex(String name,String defaultValue){
		super(name, AttributeType._complex, defaultValue);
	}

	public CampComplex(String name,ComplexValue defaultValue){
		super(name, AttributeType._complex,ValueInterface._toJson(defaultValue));
	}

	public int size(){
		return this.value().value().size();
	}

	public boolean isEmpty(){
		return this.value().value().isEmpty();
	}

	public  ArrayList<Attribute<?>> get(String key){//ArrayList<Attribute<?>>
		return this.value().value().get(key);
	}

	public boolean containsKey(String key){
		return this.value().value().containsKey(key);
	}

	public ArrayList<Attribute<?>> put(String key, ArrayList<Attribute<?>> value){
		return this.value().value().put(key, value);
	}

	public ArrayList<Attribute<?>> remove(String key){
		return this.value().value().remove(key);
	}

	public void clear(){
		this.value().value().clear();
	}

	public boolean containsValue(ArrayList<Attribute<?>> value){
		return this.value().value().containsValue(value);
	}

	@Override
	public ComplexValue clone(){
		return  (ComplexValue) this.value().value().clone();
	}

	public Set<String> keySet(){
		return this.value().value().keySet();
	}

	public Collection<ArrayList<Attribute<?>>> values(){
		return this.value().value().values();
	}

	public Set<Entry<String, ArrayList<Attribute<?>>>> entrySet(){
		return this.value().value().entrySet();
	}

	public final static CampComplex fromList(ArrayList<Attribute<?>> list){
		CampComplex complex = new CampComplex();
		for(Attribute<?> a: list){
        if(!complex.containsKey(a.attributeGroup().name())) complex.put(a.attributeGroup().name(), new ArrayList<Attribute<?>>());
			complex.value().value().get(a.attributeGroup().name()).add(a);
		}
		return complex;
	}

	public ArrayList<Attribute<?>>	toList(){
		ArrayList<Attribute<?>> list = new ArrayList<Attribute<?>>();
		for(String key:keySet()){
			list.addAll(get(key));
		}
		return list;
	}

	public CampComplex fromJSON(String json){ 
		return CampComplexInterface._fromJson(json);
	}

	@Override
	public ComplexValue valueFromString(String jsonValue) {
      return new ComplexValue(ValueInterface._fromComplexJson(jsonValue));
	}

	@Override
	public String toJson() {
		return CampComplexInterface._toJSON(this);
	}

	@Override
	public CampComplex fromJson(String json) {
		return CampComplexInterface._fromJson(json);
	}

}
