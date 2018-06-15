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

import com.camsolute.code.camp.lib.contract.core.Value;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.AttributeInterface;
import com.camsolute.code.camp.lib.utilities.Util;

public class CampBoolean extends Attribute<BooleanValue>{
    public CampBoolean(){
        super(null,AttributeType._boolean,"false");
    }

    public CampBoolean(String name){
        super(name,AttributeType._boolean,"false");
    }

    public CampBoolean(String name, String defaultValue){
        super(name,AttributeType._boolean,defaultValue);
    }

    public CampBoolean(String name, Boolean defaultValue){
        super(name,AttributeType._boolean,defaultValue.toString());
    }

    public CampBoolean(String name, String defaultValue, String value){
        super(name,AttributeType._boolean,defaultValue.toString());
        this.setValue(new BooleanValue(Boolean.valueOf(value)));
    }

    public CampBoolean(String name, Boolean defaultValue, Boolean value){
        super(name,AttributeType._boolean,defaultValue.toString());
        this.setValue(new BooleanValue(value));
    }


	/**
	 * {@inheritDoc}
	 */
	@Override
	public BooleanValue valueFromString(String value) {
		return new BooleanValue(Boolean.valueOf(value));
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
    public CampBoolean fromJson(String json){
        return (CampBoolean)AttributeInterface._fromJson(json);
    }
}
