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
import com.camsolute.code.camp.lib.models.AttributeInterface;

public interface CampComplexInterface extends AttributeInterface<ComplexValue> {

	public int size();

	public boolean isEmpty();

	public ArrayList<Attribute<?>> get(String key);

	public boolean containsKey(String key);

	public ArrayList<Attribute<?>> put(String key, ArrayList<Attribute<?>> value);

	public ArrayList<Attribute<?>> remove(String key);

	public void clear();

	public boolean containsValue(ArrayList<Attribute<?>> value);

	public Set<String> keySet();

	public Collection<ArrayList<Attribute<?>>> values();

	public Set<Entry<String, ArrayList<Attribute<?>>>> entrySet();

	public ArrayList<Attribute<?>>	toList();

	public static CampComplex fromList(ArrayList<Attribute<?>> list){
		CampComplex complex = new CampComplex();
		for(Attribute<?> a: list){
        if(!complex.containsKey(a.attributeGroup().name())) complex.put(a.attributeGroup().name(), new ArrayList<Attribute<?>>());
			complex.value().value().get(a.attributeGroup().name()).add(a);
		}
		return complex;
	}

	public static CampComplex _fromJson(String json){
		return (CampComplex) AttributeInterface._fromJson(json);
	}

	public static String _toJSON(CampComplex complex){
		return AttributeInterface._toJson(complex);
	}


}
