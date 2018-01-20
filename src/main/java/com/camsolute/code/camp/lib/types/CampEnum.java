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

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.Attribute;
import com.camsolute.code.camp.lib.utilities.Util;

public class CampEnum extends Attribute<String>{
	public static final boolean _DEBUG = true;
	
	private static final Logger LOG = LogManager.getLogger(CampEnum.class);
	private static String fmt = "[%15s] [%s]";
	
	private String[] values;
	private String selected;
	
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

	public CampEnum(String string){
		super(null, AttributeType._enum, null);
		CampEnum e = CampEnum.fromString(string);
		this.values = e.values();
		this.selected = e.selected();
		name(e.name());
	}
	
	public CampEnum(String name,String[] values){
		super(name, AttributeType._enum, null);
		try{
			if(values == null || values.length < 1){
				throw new Exception("Instatiation attempt with empty enumeration Array! CampEnum cannot be instantiated with a String[] equals to null or with zero length!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		this.values = values;
		selected = values[0];
	}
	public CampEnum(String name,String defaultValue){
		super(name, AttributeType._enum, defaultValue);
	}
	public CampEnum(String name,String selected,String[] values) {
		super(name, AttributeType._enum, null);
		try{
			if(values == null || values.length < 1){
				throw new Exception("CampEnum cannot be instantiated with a String[] equals to null or with zero length!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		this.values = values;
		select(selected);
	}
	
	public CampEnum defaultInstance(){
		return new CampEnum(name(),selected(),values());
	}



	public String[] values() {
		return this.values;
	}
	public String selected() {
		return this.selected;
	}

	public void select(String select) {
		try{
			if(!contains(select)){
				throw new Exception("Attempt to select unknown value. The value '"+select+"' is not an element of the enumeration array.");
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
		this.selected = select;
	}
	public void unselect() {
		this.selected = values[0];
	}

	public boolean isSelected(String srchVal){
		boolean isSelected = false;
		if(srchVal.equals(this.selected)){
			isSelected = true;
		}
		return isSelected;
	}

	public boolean contains(String srchVal){
		boolean isElement = false;
		for(String chkVal:this.values){
			if(srchVal.equals(chkVal)){
				isElement = true;
				break;
			}
		}
		return isElement;
	}
	
	public int size() {
		if(values != null){
			return values.length;
		}else{
			return 0;
		}
	}

	@Override
	public String toString(){
		String value = name();
		value += "|"+StringUtils.join(this.values(), ",").trim();
		value += "|"+selected.trim(); 
		return value;
	}
	
	public static CampEnum fromString(String e) {
		String[] vs = StringUtils.split(e, "|");
		if(vs.length<3) {
			try {
				throw new Exception("String CampEnum must declare a selected value: '<<attribute name>|value 1, value 2,...,value n>|<selected value>' !");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		String name = vs[0];
		String[] values = StringUtils.split(vs[1],",");
		if(vs[2] == null || vs[2].isEmpty()){
			return new CampEnum(name,values);
		}
		return new CampEnum(name,vs[2],values);
	}
	@Override
	public String value() {
		return toString();
	}
	@Override
	public String value(String value) {
		String prev = toString();
		CampEnum e = fromString(value);
		name(e.name());
		this.values = e.values();
		this.selected = e.selected();
		return prev;
	}

	@Override
	public CampEnum valueFromString(String value) {
		this.value(value);
		return this;
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
