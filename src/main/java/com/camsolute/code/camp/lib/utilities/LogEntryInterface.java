package com.camsolute.code.camp.lib.utilities;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampInstanceInterface;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Version;

public interface LogEntryInterface<T extends IsObjectInstance<T>> extends Serialization<LogEntry<T>> {

	public static <I extends IsObjectInstance<I>> String _toJson(LogEntry<I> entry) {
		String json = "{";
		json += _toInnerJson(entry);
		json += "}";
		return json;
	}
	
	public static <I extends IsObjectInstance<I>> String _toInnerJson(LogEntry<I> entry) {
		String json = "";
		json += "\"id\":"+entry.id()+",";	
		json += "\"objectId\":"+entry.objectId()+",";	
		json += "\"objectType\":\""+entry.objectType()+"\",";	
		json += "\"objectBusinessId\":\""+entry.objectBusinessId()+"\",";	
		json += "\"objectBusinessKey\":\""+entry.objectBusinessKey()+"\",";	
		json += "\"group\":\""+entry.group().name()+"\",";	
		json += "\"version\":\""+entry.version().value()+"\",";	
		json += "\"history\":"+entry.history().toJson()+",";	
		json += "\"timestamp\":\""+entry.timestamp().toString()+"\",";	
		json += "\"objectJson\":\""+JSONObject.quote(entry.objectJson())+"\"";
		return json;
	}
	
	public static <I extends IsObjectInstance<I>> LogEntry<I> _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static <I extends IsObjectInstance<I>> LogEntry<I> _fromJSONObject(JSONObject jo) {
		LogEntry<I> le = null;
		try {
			le = new LogEntry<I>(
					jo.getInt("id"),
					jo.getInt("objectId"),
					jo.getString("objectType"),
					jo.getString("objectBusinessId"),
					jo.getString("objectBusinessKey"),
					new Group(jo.getString("group")),
					new Version(jo.getString("version")),
					CampInstanceInterface._fromJSONObject(jo.getJSONObject("history")),
					Util.Time.timestamp(jo.getString("timestamp")),
					jo.getString("objectJson")
					);
		} catch (ClassNotFoundException | JSONException e) {
			e.printStackTrace();
		}
		
		return le;
	}

	public int id();

	public int objectId();

	public Class<T> objectType();

	public String objectBusinessId();

	public String objectBusinessKey();

	public Group group();
	
	public Version version();
	
	public Timestamp timestamp();

	public CampInstance history();

	public String objectJson();
	
}
