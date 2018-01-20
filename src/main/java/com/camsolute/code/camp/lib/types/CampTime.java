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

import org.joda.time.DateTime;

import com.camsolute.code.camp.lib.Attribute;
import com.camsolute.code.camp.lib.utilities.Util;

public class CampTime extends Attribute<DateTime>{
	public static final String _F = "[CampTime]";
	public static final boolean _DEBUG = false;
	
	private DateTime value;
	
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

	public CampTime(){
		super(null, AttributeType._time, (new DateTime()).toString());
		this.value = fromString(defaultValue());
	}
	public CampTime(String name){
		super(name, AttributeType._time, (new DateTime()).toString());
		this.value = fromString(defaultValue());
	}
	
	public CampTime(String name,String time){
		super(name, AttributeType._time, (new DateTime()).toString());
		this.value = fromString(time);
	}

	public CampTime(String name,DateTime time){
		super(name, AttributeType._time, time.toString());
		this.value = time;
	}

	public CampTime defaultInstance(){
		return new CampTime(name(),defaultValue());
	}


	public static DateTime fromString(String time){
		return TU.dateTimeFromString(time);
	}
	
	@Override
	public final String toString(){
		return this.value.toString("HH:mm:ss");
	}
	
	public final String milliSeconds(){
		return this.value.toString("HH:mm:ss.SSS");
	}
	
	@Override
	public DateTime value() {
		return this.value;
	}

	@Override
	public DateTime value(DateTime value) {
		DateTime prev = this.value;
		this.value = value;
		return prev;
	}
	@Override
	public CampTime valueFromString(String value) {
		value(fromString(value));
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
