package com.camsolute.code.camp.lib.contract.history;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.history.HistoryMarker.DefaultHistoryMarker;
import com.camsolute.code.camp.lib.utilities.Util;

public interface JSONHistoryHandler {

	public String toJson(HistoryMarker history);
	
	public HistoryMarker fromJson(String json);
	
	public HistoryMarker fromJSONObject(JSONObject jo);
	
	public static String _toJson(HistoryMarker i){
  	String json = "{";
  		json += _toInnerJson(i);
  		json += "}";
  	return json;
  }

    public static String _toInnerJson(HistoryMarker i) {
    	String json = "";
      json += "\"objectReferenceId\":"+i.objectReferenceId()+",";
      json += "\"id\":\""+i.id().id()+"\",";
      json += "\"initialId\":\""+i.initialId().id()+"\",";
      json += "\"currentId\":\""+i.currentId().id()+"\",";
      json += "\"timestamp\":\""+i.timestamp().toString()+"\",";
      json += "\"creationDate\":\""+i.creationDate().toString()+"\",";
      json += "\"endOfLife\":\""+i.endOfLife().toString()+"\"";
    	return json;
    }
    
    public static HistoryMarker _fromJSONObject(JSONObject jo){
        String id = jo.getString("id");
        int objectRefId = jo.getInt("objectRefId");
        String initialId = jo.getString("initialId");
        String currentId = jo.getString("currentId");
        String timestamp = jo.getString("timestamp");
        String date = jo.getString("date");
        String endOfLife = jo.getString("endOfLife");
        HistoryMarker i = new DefaultHistoryMarker(id,initialId,currentId);
        i.timestamp(Util.Time.timestamp(timestamp));
        i.creationDate(Util.Time.timestamp(date));
        i.endOfLife(Util.Time.timestamp(endOfLife));
        i.objectReferenceId(objectRefId);
        return i;
    }

    public class JSONHistoryHandlerImpl implements JSONHistoryHandler {
    	
    	public String toJson(HistoryMarker history) {
    		return JSONHistoryHandler._toJson(history);
    	}
    	
    	public HistoryMarker fromJson(String json) {
    		return fromJSONObject(new JSONObject(json));
    	}
    	
    	public HistoryMarker fromJSONObject(JSONObject jo) {
    		return _fromJSONObject(jo);
    	}
    }


}
