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

import com.camsolute.code.camp.lib.Attribute;
import com.camsolute.code.camp.lib.CampType;
import com.camsolute.code.camp.lib.utilities.Util;

public class CampBoolean extends Attribute<Boolean>{
	
	private boolean value;
	
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

	public CampBoolean(){
		super(null, AttributeType._boolean, "false");
		this.value = Boolean.valueOf("false");
	}
	
	public CampBoolean(String name){
		super(name, AttributeType._boolean, "false");
		this.value = Boolean.valueOf("false");
	}
	
	public CampBoolean(String name,String stringValue){
		super(name, AttributeType._boolean, "false");
		this.value = Boolean.valueOf(stringValue);
	}
	
	public CampBoolean(String name,boolean value){
		super(name, AttributeType._boolean, "false");
		this.value = value;
	}

	public CampBoolean(String name,String defaultValue,boolean value){
		super(name, AttributeType._boolean, defaultValue);
		this.value = value;
	}
	
	public CampBoolean defaultInstance(){
		return new CampBoolean(name(),Boolean.valueOf("false"));
	}



	@Override
	public Boolean value() {
		return value;
	}

	@Override
	public Boolean value(Boolean value) {
		boolean prev = value;
		this.value = value;
		return prev;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampBoolean valueFromString(String value) {
		value(Boolean.valueOf(value));
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String attributeGroup() {
		return this.attributeGroup;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String attributeGroup(String group) {
		String prev = this.attributeGroup;
		this.attributeGroup = group;
		return prev;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int attributePosition() {
		return this.attributePosition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int attributePosition(int position) {
		int prev = this.attributePosition;
		this.attributePosition = position;
		return prev;
	} 


}
