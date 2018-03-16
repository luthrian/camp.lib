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

import java.sql.Timestamp;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.utilities.Util;

public interface CampInstanceInterface extends Serialization<CampInstance>  {
	
		public int objectRefId();
		
		public void setObjectRefId(int id);
		
    public boolean isFirst();

    public boolean isCurrent();

    public Timestamp timestamp();

    public void timestamp(Timestamp ts);
        
    public Timestamp date();

    public void date(Timestamp d);
        
    public Timestamp endOfLife();
    
    public void endOfLife(Timestamp eol);

    public static String _toJson(CampInstance i){
      String json = "{";
      json += _toInnerJson(i);
      json += "}";
      return json;
  }

    public static String _toInnerJson(CampInstance i) {
    	String json = "";
      json += "\"objectRefId\":"+i.objectRefId()+",";
      json += "\"id\":\""+i.id().id()+"\",";
      json += "\"initialId\":\""+i.initialId().id()+"\",";
      json += "\"currentId\":\""+i.currentId().id()+"\",";
      json += "\"timestamp\":\""+i.timestamp().toString()+"\",";
      json += "\"date\":\""+i.date().toString()+"\",";
      json += "\"endOfLife\":\""+i.endOfLife().toString()+"\"";
    	return json;
    }
    public static CampInstance _fromJson(String json){
    	return _fromJSONObject(new JSONObject(json));
    }
    public static CampInstance _fromJSONObject(JSONObject jo){
        String id = jo.getString("id");
        int objectRefId = jo.getInt("objectRefId");
        String initialId = jo.getString("initialId");
        String currentId = jo.getString("currentId");
        String timestamp = jo.getString("timestamp");
        String date = jo.getString("date");
        String endOfLife = jo.getString("endOfLife");
        CampInstance i = new CampInstance(id,initialId,currentId);
        i.timestamp(Util.Time.timestamp(timestamp));
        i.date(Util.Time.timestamp(date));
        i.endOfLife(Util.Time.timestamp(endOfLife));
        i.setObjectRefId(objectRefId);
        return i;
    }


}
