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
package com.camsolute.code.camp.lib.types;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.core.exceptions.TransformFromException;
import com.camsolute.code.camp.core.exceptions.TransformToException;
import com.camsolute.code.camp.models.business.OrderProductAttribute;
import com.camsolute.code.camp.models.business.ProductAttributeDefinition;
import com.camsolute.code.camp.models.business.transformers.JSONCampOldComplexPATransformer;

import java.util.Map.Entry;

public class CampOldComplex<T> extends CampOldType<HashMap<String,CampOldList<T>>> {
	public static final boolean _DEBUG = true;
	private static String fmt = "[%15s] [%s]";
	
	private static final Logger LOG = LogManager.getLogger(CampOldComplex.class);
	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -60669250734466595L;
	
	private HashMap<String,CampOldList<T>> complex;
	private String defaultValue = null; // complex values don't have a default
	
	public CampOldComplex(){
		this.complex = new HashMap<String,CampOldList<T>>();
	}
	
	public CampOldComplex(String str,CampOldList<T> al){
		this.complex = new HashMap<String,CampOldList<T>>();
		this.complex.put(str, al);
	}

	public CampOldComplex(String str){
		this.complex = new HashMap<String,CampOldList<T>>();
		CampOldList<T> al = new CampOldList<T>();
		this.complex.put(str, al);
	}
	
	public CampOldComplex<T> defaultInstance(){
		return new CampOldComplex<T>();
	}


	
	public int size(){
		return complex.size();
	}
	
	public boolean isEmpty(){
		return complex.isEmpty();
	}
	
	public CampOldList<T> get(String key){
		return complex.get(key);
	}
	
	public boolean containsKey(String key){
		return complex.containsKey(key);
	}
	
	public CampOldList<T> put(String key, CampOldList<T> value){
		return this.complex.put(key, value);
	}
	
	public void putAll(CampOldMap<T> map){
		for(String k:map.keySet()){
			CampOldList<T> v = map.get(k);
			this.complex.put(k, v);
		}
	}
	
	public CampOldList<T> remove(String key){
		return complex.remove(key);
	}

	public void clear(){
		complex.clear();
	}
	
	public boolean containsValue(CampOldList<T> value){
		return this.complex.containsValue(value);
	}
	
	@Override
	public HashMap<String,CampOldList<T>> clone(){
		return  (HashMap<String, CampOldList<T>>) this.complex.clone();
	}
	
	public Set<String> keySet(){
		return this.complex.keySet();
	}
	
	public Collection<CampOldList<T>> values(){
		return this.complex.values();
	}
	
	public Set<Entry<String, CampOldList<T>>> entrySet(){
		return this.complex.entrySet();
	}

	public final static CampOldComplex<ProductAttributeDefinition<?,?>> fromList(CampOldList<ProductAttributeDefinition<?,?>> list){
		CampOldComplex<ProductAttributeDefinition<?,?>> complex = new CampOldComplex<ProductAttributeDefinition<?,?>>();
		for(ProductAttributeDefinition<?,?> pa: list.value()){
			if(!complex.containsKey(pa.group().name()))complex.put(pa.group().name(), new CampOldList<ProductAttributeDefinition<?,?>>());
			complex.get(pa.group().name()).add(pa);
		}
		return complex;
	}
	
	public final static CampOldComplex<OrderProductAttribute<?,?>> fromOList(CampOldList<OrderProductAttribute<?,?>> list){
		CampOldComplex<OrderProductAttribute<?,?>> complex = new CampOldComplex<OrderProductAttribute<?,?>>();
		for(OrderProductAttribute<?,?> pa: list.value()){
			if(!complex.containsKey(pa.group().name()))complex.put(pa.group().name(), new CampOldList<OrderProductAttribute<?,?>>());
			complex.get(pa.group().name()).add(pa);
		}
		return complex;
	}
	
	public CampOldList<T>	toList(){
		CampOldList<T> list = new CampOldList<T>();
		for(String key:keySet()){
			list.addAll(get(key));
		}
		return list;
	}
	
	public final static CampOldComplex<ProductAttributeDefinition<?,?>> fromJSON(String json){ return _fromJSON(json, true); }
	public final static CampOldComplex<ProductAttributeDefinition<?,?>> _fromJSON(String json, boolean log){
		String _f = "[_fromJSON]";
		String msg = " -- [ transforming JSON string to product attribute definition ]";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
		try {
			return JSONCampOldComplexPATransformer.instance().transformTo(json);
		} catch (TransformToException e) {
			msg = "Failed to create complex from JSON "+json;if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
			e.printStackTrace();
		}
		return null;
	}
	
	public final static String toJSON(CampOldComplex<ProductAttributeDefinition<?,?>> complex){ return _toJSON(complex,true);}
	public final static String _toJSON(CampOldComplex<ProductAttributeDefinition<?,?>> complex, boolean log){
		String _f = "[_toJSON]";
		String msg = " -- [ transforming product attribute definition to JSON ]";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
		try {
			return (String) JSONCampOldComplexPATransformer.instance().transformFrom(complex);
		} catch (TransformFromException e) {
		if(log && _DEBUG) {msg = "[Failed to create JSON from CampComplex ]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * ask for the current map value of the complex attribute
	 * @return value (HashMap<String, CampList<T>>)
	 * {@inheritDoc}
	 */
	@Override
	protected HashMap<String, CampOldList<T>> me() {
		return this.complex;
	}
	/** 
	 * replace the current map value of the complex attribute
	 * @return previous value (HashMap<String, CampList<T>>)
	 * {@inheritDoc}
	 */
	@Override
	protected HashMap<String, CampOldList<T>> me(HashMap<String, CampOldList<T>> value) {
		HashMap<String, CampOldList<T>> prev = this.complex;
		this.complex = value;
		return prev;
	}
	
}
