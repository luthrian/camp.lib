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

import java.sql.Timestamp;

import com.camsolute.code.camp.lib.contract.core.Value;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.AttributeInterface;
import com.camsolute.code.camp.lib.utilities.Util;
import com.camsolute.code.camp.lib.utilities.Util.Time;

public class CampTimestamp extends Attribute<TimestampValue>{

	public CampTimestamp(){
		super(null, AttributeType._timestamp, Util.Time.timestamp().toString());
	}
	
	public CampTimestamp(String name){
		super(name, AttributeType._timestamp, Time.timestamp().toString());
	}
	
	public CampTimestamp(String name,String defaultValue){
		super(name, AttributeType._timestamp, defaultValue);
	}

	public CampTimestamp(String name,Timestamp defaultValue){
		super(name, AttributeType._timestamp, defaultValue.toString());
	}

	public CampTimestamp(String name,String defaultValue, String value){
		super(name, AttributeType._timestamp, defaultValue);
		this.value().setValue(Time.timestamp(value));
	}

	public CampTimestamp(String name,Timestamp defaultValue, Timestamp value){
		super(name, AttributeType._timestamp, defaultValue.toString());
		this.value().setValue(value);
	}

	public static Timestamp fromString(String timestamp){
		return Util.Time.timestamp(timestamp);
	}
	
	@Override
	public final String toString(){
		return this.value().value().toString();
	}
	
	@Override
	public TimestampValue valueFromString(String value) {
		return new TimestampValue(Util.Time.timestamp(value));
	}

	@Override
	public String toJson() {
		return AttributeInterface._toJson(this);
	}

	@Override
	public CampTimestamp fromJson(String json) {
		return (CampTimestamp) AttributeInterface._fromJson(json);
	}
	
}
