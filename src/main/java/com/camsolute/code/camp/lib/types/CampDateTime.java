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
import static com.camsolute.code.camp.lib.utilities.Util.*;

public class CampDateTime extends Attribute<DateTimeValue>{
	public CampDateTime(){
		super(null, AttributeType._datetime, (new DateTime()).toString());
	}

	public CampDateTime(String name){
		super(name, AttributeType._datetime, (new DateTime()).toString());
	}

    public CampDateTime(String name, String defaultValue){
        super(name, AttributeType._datetime, defaultValue);
    }

    public CampDateTime(String name, DateTime defaultValue){
        super(name, AttributeType._datetime, defaultValue.toString());
    }

    public CampDateTime(String name, String defaultValue, String value){
		super(name, AttributeType._datetime, defaultValue);
		this.setValue(new DateTimeValue(fromString(value)));
	}

  public CampDateTime(String name, DateTime defaultValue, DateTimeValue value){
		super(name, AttributeType._datetime, defaultValue.toString());
		this.setValue(value);
	}

	public static DateTime fromString(String datetime){
		return Time.dateTimeFromString(datetime);
	}

	public final String toString(){
      return this.value().value().toString("yyyy-MM-dd HH:mm:ss.SSS");
	}

	@Override
	public DateTimeValue valueFromString(String value) {
		return new DateTimeValue(fromString(value));
	}

	@Override
	public String toJson() {
		return AttributeInterface._toJson(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Attribute<DateTimeValue> fromJson(String json) {
		return (Attribute<DateTimeValue>) AttributeInterface._fromJson(json);
	}

}
