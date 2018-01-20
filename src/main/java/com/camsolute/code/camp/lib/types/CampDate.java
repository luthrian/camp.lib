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

public class CampDate extends Attribute<DateTime>{
	public static final String _F = "[CampDate]";
	public static final boolean _DEBUG = false;
	
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


	private DateTime value;
	
	private String attributeGroup = null;
	private int attributePosition = 0;
	
	public CampDate(){
		super(null, AttributeType._date, (new DateTime()).toString());
		this.value = fromString(defaultValue());
	}
	
	public CampDate(String name){
		super(name, AttributeType._date, (new DateTime()).toString());
		this.value = fromString(defaultValue());
	}
	
	public CampDate(String name, String date){
		super(name, AttributeType._date, (new DateTime()).toString());
		this.value = fromString(date);
	}
	
	public CampDate(String name, DateTime date){
		super(name, AttributeType._date, date.toString());
		this.value = date;
	}
	
	public CampDate defaultInstance(){
		return new CampDate(name(),(new DateTime()).toString());
	}



	public static DateTime fromString(String date){
		return TU.dateTimeFromString(date);
	}
	
	@Override
	public final String toString(){
		return this.value.toString("yyyy-MM-dd");
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
	public CampDate valueFromString(String value) {
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
