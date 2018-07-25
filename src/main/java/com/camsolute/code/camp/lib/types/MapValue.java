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

import java.util.HashMap;

import com.camsolute.code.camp.lib.contract.core.Value;
import com.camsolute.code.camp.lib.contract.core.Coordinate;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;

public class MapValue extends Value<HashMap<String,Attribute<? extends Value<?,?>>>,MapValue> {
    public MapValue(HashMap<String,Attribute<? extends Value<?,?>>> value){
        super(AttributeType._map,value);
    }

    public MapValue(HashMap<String,Attribute<? extends Value<?,?>>> value, int x, int y, int z){
        super(AttributeType._map,value, x, y, z);
    }

    public MapValue(HashMap<String,Attribute<? extends Value<?,?>>> value, String group, int x, int y, int z){
        super(AttributeType._map,value, group, x, y, z);
    }

    public MapValue(HashMap<String,Attribute<? extends Value<?,?>>> value, String group, int x, int y, int z, boolean selected){
        super(AttributeType._map,value, group, x, y, z, selected);
    }

    public MapValue(int id, HashMap<String,Attribute<? extends Value<?,?>>> value, String group, int x, int y, int z, boolean selected){
        super(id, AttributeType._map,value, group, x, y, z, selected);
    }

    public MapValue(int id, HashMap<String,Attribute<? extends Value<?,?>>> value, String group, Coordinate position){
        super(id, AttributeType._map,value, group, position);
    }

    public MapValue(int id, HashMap<String,Attribute<? extends Value<?,?>>> value, String group, Coordinate position, boolean selected){
        super(id, AttributeType._map,value, group, position, selected);
    }

}
