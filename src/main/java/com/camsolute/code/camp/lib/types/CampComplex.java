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

import com.camsolute.code.camp.core.dao.DBU;
import com.camsolute.code.camp.core.exceptions.TransformFromException;
import com.camsolute.code.camp.core.exceptions.TransformToException;
import com.camsolute.code.camp.core.types.transformers.JSONCampComplexTransformer;
import com.camsolute.code.camp.lib.CampType;
import com.camsolute.code.camp.models.business.ProductAttribute.Type;
import java.util.Map.Entry;

public class CampComplex<T extends CampType<?>> extends CampType<HashMap<String,CampList<T>>> {
	public static final boolean _DEBUG = true;
	private static String fmt = "[%15s] [%s]";
	
	private static final Logger LOG = LogManager.getLogger(CampComplex.class);
	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -60669250734466595L;
	
	private HashMap<String,CampList<T>> complex;

	private String attributeGroup = null;
	private int attributePosition = 0;
	
	private String attributeBusinessId = null;

	@Override
	public String attributeBusinessId() {
		return attributeBusinessId+DBU._NS+id();
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
		return attributeBusinessId+DBU._NS+0;
	}

	public CampComplex(){
		super(null, AttributeType._complex, null);
		this.complex = new HashMap<String,CampList<T>>();
	}
	
	public CampComplex(String name){
		super(name, AttributeType._complex, null);
		this.complex = new HashMap<String,CampList<T>>();
	}
	
	public CampComplex(String name,String str,CampList<T> al){
		super(name, AttributeType._complex, null);
		this.complex = new HashMap<String,CampList<T>>();
		this.complex.put(str, al);
	}

	public CampComplex(String name,String str){
		super(name, AttributeType._complex, null);
		this.complex = new HashMap<String,CampList<T>>();
		CampList<T> al = new CampList<T>(name);
		this.complex.put(str, al);
	}
	
	public CampComplex<T> defaultInstance(){
		return new CampComplex<T>(name());
	}


	
	public int size(){
		return complex.size();
	}
	
	public boolean isEmpty(){
		return complex.isEmpty();
	}
	
	public CampList<T> get(String key){
		return complex.get(key);
	}
	
	public boolean containsKey(String key){
		return complex.containsKey(key);
	}
	
	public CampList<T> put(String key, CampList<T> value){
		return this.complex.put(key, value);
	}
	
	public void putAll(CampMap<T> map){
		for(String k:map.keySet()){
			CampList<T> v = map.get(k);
			this.complex.put(k, v);
		}
	}
	
	public CampList<T> remove(String key){
		return complex.remove(key);
	}

	public void clear(){
		complex.clear();
	}
	
	public boolean containsValue(CampList<T> value){
		return this.complex.containsValue(value);
	}
	
	@Override
	public HashMap<String,CampList<T>> clone(){
		return  (HashMap<String, CampList<T>>) this.complex.clone();
	}
	
	public Set<String> keySet(){
		return this.complex.keySet();
	}
	
	public Collection<CampList<T>> values(){
		return this.complex.values();
	}
	
	public Set<Entry<String, CampList<T>>> entrySet(){
		return this.complex.entrySet();
	}

	public final static CampComplex<CampType<?>> fromList(CampList<CampType<?>> list){
		CampComplex<CampType<?>> complex = new CampComplex<CampType<?>>();
		for(CampType<?> pa: list.value()){
			if(!complex.containsKey(pa.attributeGroup()))complex.put(pa.attributeGroup(), new CampList<CampType<?>>());
			complex.get(pa.attributeGroup()).add(pa);
		}
		return complex;
	}
	
	public final static CampComplex<CampType<?>> fromOList(CampList<CampType<?>> list){
		CampComplex<CampType<?>> complex = new CampComplex<CampType<?>>();
		for(CampType<?> pa: list.value()){
			if(!complex.containsKey(pa.attributeGroup()))complex.put(pa.attributeGroup(), new CampList<CampType<?>>());
			complex.get(pa.attributeGroup()).add(pa);
		}
		return complex;
	}
	
	public CampList<T>	toList(){
		CampList<T> list = new CampList<T>();
		for(String key:keySet()){
			list.addAll(get(key));
		}
		return list;
	}
	
	public final static CampComplex<CampType<?>> fromJSON(String json){ return _fromJSON(json, true); }
	public final static CampComplex<CampType<?>> _fromJSON(String json, boolean log){
		String _f = "[_fromJSON]";
		String msg = " -- [ transforming JSON string to product attribute definition ]";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
		try {
			return JSONCampComplexTransformer.instance().transformTo(json);
		} catch (TransformToException e) {
			msg = "Failed to create complex from JSON "+json;if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
			e.printStackTrace();
		}
		return null;
	}
	
	public final static String toJSON(CampComplex<CampType<?>> complex){ return _toJSON(complex,true);}
	public final static String _toJSON(CampComplex<CampType<?>> complex, boolean log){
		String _f = "[_toJSON]";
		String msg = " -- [ transforming product attribute definition to JSON ]";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
		try {
			return (String) JSONCampComplexTransformer.instance().transformFrom(complex);
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
	protected HashMap<String, CampList<T>> me() {
		return this.complex;
	}
	/** 
	 * replace the current map value of the complex attribute
	 * @return previous value (HashMap<String, CampList<T>>)
	 * {@inheritDoc}
	 */
	@Override
	protected HashMap<String, CampList<T>> me(HashMap<String, CampList<T>> value) {
		HashMap<String, CampList<T>> prev = this.complex;
		this.complex = value;
		return prev;
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


	@Override
	public CampComplex<T> valueFromString(String value) {
		
		return null;
	}

	
}
