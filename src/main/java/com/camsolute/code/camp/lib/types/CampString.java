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

import java.lang.String;

import com.camsolute.code.camp.lib.contract.core.Value;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.AttributeInterface;
import com.camsolute.code.camp.lib.utilities.Util;

public class CampString extends Attribute<StringValue>{

	public CampString(){
		super(null, AttributeType._string, null);
	}

	public CampString(String name){
		super(name, AttributeType._string, null);
	}

	public CampString(String name,String defaultValue){
		super(name, AttributeType._string, defaultValue);
	}

	public CampString(String name,String defaultValue, String value){
		super(name, AttributeType._string, defaultValue);
		this.value().setValue(value);
	}

	@Override
	public StringValue valueFromString(String value) {
		return new StringValue(value);
	}

    @Override
    public String toString(){
        return AttributeInterface._toString(this);
    }

    @Override
    public String toJson(){
        return AttributeInterface._toJson(this);
    }

    @Override
    public CampString fromJson(String json){
        return (CampString)AttributeInterface._fromJson(json);
    }
}
