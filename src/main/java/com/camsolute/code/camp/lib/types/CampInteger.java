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

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.contract.core.Value;
import com.camsolute.code.camp.lib.dao.DaoInterface;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.AttributeInterface;
import com.camsolute.code.camp.lib.utilities.Util;

public class CampInteger extends Attribute<IntegerValue>{

    public CampInteger(){
        super(null, AttributeType._integer, "0");
    }

	public CampInteger(String name){
		super(name, AttributeType._integer, "0");
	}

    public CampInteger(String name, String defaultValue){
        super(name, AttributeType._integer, defaultValue);
    }

    public CampInteger(String name, int defaultValue){
        super(name, AttributeType._integer, ""+defaultValue);
    }

    public CampInteger(String name, String defaultValue, String value){
        super(name, AttributeType._integer, defaultValue);
        this.setValue(new IntegerValue(Integer.valueOf(value)));
    }

    public CampInteger(String name, int defaultValue, int value){
        super(name, AttributeType._integer, ""+defaultValue);
        this.setValue(new IntegerValue(value));
    }

	@Override
	public IntegerValue valueFromString(String value) {
		return new IntegerValue(Integer.valueOf(value));
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
    public CampInteger fromJson(String json){
        return (CampInteger) AttributeInterface._fromJson(json);
    }

}

