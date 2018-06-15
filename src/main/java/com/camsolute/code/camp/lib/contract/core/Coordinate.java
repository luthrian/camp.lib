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
package com.camsolute.code.camp.lib.contract.core;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public interface Coordinate extends Serialization<Coordinate> {
	
    public int posX();
    
    public int posY();
    
    public int posZ();
    
    public void posX(int x);
	
    public void posY(int y);
	
    public void posZ(int z);
	
    public int[] toArray();
    
    public String toString();
    
    public void update(Coordinate coordinate);
    
    public void update(int x, int y, int z);
    
    public Coordinate clone();
    
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
    	
    	return new CoordinateImpl(x,y,z);
    }
    
    public class CoordinateImpl implements Coordinate {
    	
    	private int posX = 0;
    	private int posY = 0;
    	private int posZ = 0;

    	public CoordinateImpl() {
    	}

    	public CoordinateImpl(int x, int y, int z) {
    		this.posX = x;
    		this.posY = y;
    		this.posZ = z;
    	}

    	public int[] toArray() {
    		return new int[] { posX, posY, posZ };
    	}

    	public String toString() {
    		return Coordinate._toJson(this);
    	}

    	public String toJson() {
    		return Coordinate._toJson(this);
    	}

    	public Coordinate fromJson(String json) {
    		return Coordinate._fromJson(json);
    	}

    	public int posX() {
    		return this.posX;
    	}

    	public int posY() {
    		return this.posY;
    	}

    	public int posZ() {
    		return this.posZ;
    	}

    	public void posX(int x) {
    		this.posX = x;
    	}

    	public void posY(int y) {
    		this.posY = y;
    	}

    	public void posZ(int z) {
    		this.posZ = z;
    	}
    	
    	public void update( Coordinate coordinate) {
    		posX = coordinate.posX();
    		posY = coordinate.posY();
    		posZ = coordinate.posZ();
    	}
    	
    	public void update( int newPosX, int newPosY, int newPosZ) {
    		posX = newPosX;
    		posY = newPosY;
    		posZ = newPosZ;
    	}
    	
    	public Coordinate clone() {
    		return new CoordinateImpl(posX,posY,posZ);
    	}
    }

}
