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

import org.joda.time.DateTime;

import com.camsolute.code.camp.lib.contract.core.Value;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.AttributeInterface;
import com.camsolute.code.camp.lib.utilities.Util;

public class CampTime extends Attribute<TimeValue>{

	public CampTime(){
		super(null, AttributeType._time, (new DateTime()).toString());
	}
	public CampTime(String name){
		super(name, AttributeType._time, (new DateTime()).toString());
	}
	
	public CampTime(String name,String defaultValue){
		super(name, AttributeType._time, defaultValue);
	}

	public CampTime(String name,DateTime defaultValue){
		super(name, AttributeType._time, defaultValue.toString());
	}

	public CampTime(String name,String defaultValue, String value){
		super(name, AttributeType._time, defaultValue);
		this.value().setValue(Util.Time.dateTimeFromString(value));
	}

	public CampTime(String name,DateTime defaultValue, DateTime value){
		super(name, AttributeType._time, defaultValue.toString());
		this.value().setValue(value);
	}


	public static DateTime fromString(String time){
		return Util.Time.dateTimeFromString(time);
	}
	
	@Override
	public String toString(){
		return this.value().value().toString("HH:mm:ss");
	}
	
	public final String milliSeconds(){
		return this.value().value().toString("HH:mm:ss.SSS");
	}
	
	@Override
	public TimeValue valueFromString(String value) {
		return new TimeValue(fromString(value));
	}
	@Override
	public String toJson() {
		return AttributeInterface._toJson(this);
	}
	@Override
	public CampTime fromJson(String json) {
		return (CampTime) AttributeInterface._fromJson(json);
	}

}
