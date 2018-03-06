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

import com.camsolute.code.camp.lib.models.Value;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;

public class StringValue extends Value<String> {
    public StringValue(String value){
        super(AttributeType._string,value);
    }

    public StringValue(String value, int x, int y, int z){
        super(AttributeType._string,value, x, y, z);
    }

    public StringValue(String value, String group, int x, int y, int z){
        super(AttributeType._string,value, group, x, y, z);
    }

    public StringValue(String value, String group, int x, int y, int z, boolean selected){
        super(AttributeType._string,value, group, x, y, z, selected);
    }

    public StringValue(int id, String value,String group, int x, int y, int z, boolean selected){
        super(id, AttributeType._string,value, group, x, y, z, selected);
    }

    public StringValue(int id, String value, String group, Coordinate position){
        super(id, AttributeType._string,value, group, position);
    }

    public StringValue(int id, String value, String group, Coordinate position, boolean selected){
        super(id, AttributeType._string,value, group, position, selected);
    }

}
