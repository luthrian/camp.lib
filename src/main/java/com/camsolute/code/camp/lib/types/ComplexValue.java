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
 
import java.util.ArrayList;
import java.util.HashMap;

import com.camsolute.code.camp.lib.contract.core.Value;
import com.camsolute.code.camp.lib.contract.core.Coordinate;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;

public class ComplexValue extends Value<HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>>,ComplexValue> {//ArrayList<Attribute<? extends Value<?,?>>>
	
    public ComplexValue(HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>> value){
        super(AttributeType._complex,value);
    }

    public ComplexValue(HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>> value, Coordinate position){
        super(AttributeType._complex,value, position);
    }

    public ComplexValue(HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>> value, int x, int y, int z){
        super(AttributeType._complex,value, x, y, z);
    }

    public ComplexValue(HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>> value, String group, int x, int y, int z){
        super(AttributeType._complex,value, group, x, y, z);
    }

    public ComplexValue(HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>> value, String group, int x, int y, int z, boolean selected){
        super(AttributeType._complex,value, group, x, y, z, selected);
    }

    public ComplexValue(int id, HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>> value, String group, int x, int y, int z, boolean selected){
        super(id, AttributeType._complex,value, group, x, y, z, selected);
    }

    public ComplexValue(int id, HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>> value, String group, Coordinate position){
        super(id, AttributeType._complex,value, group, position);
    }

    public ComplexValue(int id, HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>> value, String group, Coordinate position, boolean selected){
        super(id, AttributeType._complex,value, group, position, selected);
    }

}
