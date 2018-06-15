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
import com.camsolute.code.camp.lib.contract.core.Coordinate;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;

public class TimeValue extends Value<DateTime> {
    public TimeValue(DateTime value){
        super(AttributeType._time,value);
    }

    public TimeValue(DateTime value, int x, int y, int z){
        super(AttributeType._time,value, x, y, z);
    }

    public TimeValue(DateTime value, String group, int x, int y, int z){
        super(AttributeType._time,value, group, x, y, z);
    }

    public TimeValue(DateTime value, String group, int x, int y, int z, boolean selected){
        super(AttributeType._time,value, group, x, y, z, selected);
    }

    public TimeValue(int id, DateTime value, String group, int x, int y, int z, boolean selected){
        super(id, AttributeType._time,value, group, x, y, z, selected);
    }

    public TimeValue(int id, DateTime value, String group, Coordinate position){
        super(id, AttributeType._time,value, group, position);
    }

    public TimeValue(int id, DateTime value, String group, Coordinate position, boolean selected){
        super(id, AttributeType._time,value, group, position, selected);
    }

}
