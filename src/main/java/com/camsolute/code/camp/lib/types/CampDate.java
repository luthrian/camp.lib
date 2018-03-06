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

import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.AttributeInterface;
import com.camsolute.code.camp.lib.models.Value;
import com.camsolute.code.camp.lib.utilities.Util;
import static com.camsolute.code.camp.lib.utilities.Util.*;

public class CampDate extends Attribute<DateValue>{

	public CampDate(){
		super(null, AttributeType._date, (new DateTime()).toString());
	}

	public CampDate(String name){
		super(name, AttributeType._date, (new DateTime()).toString());
	}

    public CampDate(String name, String defaultValue){
        super(name, AttributeType._date, defaultValue);
    }

    public CampDate(String name, DateTime defaultValue){
        super(name, AttributeType._date, defaultValue.toString());
    }

    public CampDate(String name, String defaultValue, String value){
		super(name, AttributeType._date, defaultValue.toString());
		this.value().setValue(fromString(value));
	}

  public CampDate(String name, DateTime date, DateValue value){
		super(name, AttributeType._date, date.toString());
		this.setValue(value);
	}

	public CampDate defaultInstance(){
		return new CampDate(name(),(new DateTime()).toString());
	}



	public static DateTime fromString(String date){
		return Time.dateTimeFromString(date);
	}

	public final String toString(){
		return this.value().value().toString("yyyy-MM-dd");
	}


	@Override
	public DateValue valueFromString(String value) {
		return new DateValue(fromString(value));
	}

	@Override
	public String toJson() {
		return AttributeInterface._toJson(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Attribute<DateValue> fromJson(String json) {
		return (Attribute<DateValue>) AttributeInterface._fromJson(json);
	}

}
