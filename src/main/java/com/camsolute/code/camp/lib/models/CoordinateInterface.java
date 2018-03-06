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
package com.camsolute.code.camp.lib.models;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.models.Value.Coordinate;

public interface CoordinateInterface<T> extends Serialization<Coordinate> {
	
    public int posX();
    
    public int posY();
    
    public int posZ();
    
    public void posX(int x);
	
    public void posY(int y);
	
    public void posZ(int z);
	
    public static String _toJson(Coordinate c) {
    	return "{\"posX\":"+c.posX()+",\"posY\":"+c.posY()+",\"posZ\":"+c.posZ()+"}";
    }
    
    public static Coordinate _fromJson(String json) {
    	return _fromJSONObject(new JSONObject(json));
    }
    public static Coordinate _fromJSONObject(JSONObject jo) {
    	
    	int x= jo.getInt("posX");
    	int y= jo.getInt("posY");
    	int z= jo.getInt("posZ");
    	
    	return new Coordinate(x,y,z);
    }

}
