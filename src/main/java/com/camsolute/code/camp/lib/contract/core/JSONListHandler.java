package com.camsolute.code.camp.lib.contract.core;

import java.util.Iterator;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.attribute.Attribute;
import com.camsolute.code.camp.lib.contract.attribute.JSONAttributeHandler;
import com.camsolute.code.camp.lib.contract.core.CampList.AttributeList;
import com.camsolute.code.camp.lib.contract.core.CampList.ValueList;
import com.camsolute.code.camp.lib.contract.value.JSONValueHandler;
import com.camsolute.code.camp.lib.contract.value.Value;

public interface JSONListHandler<T extends Serialization<T>,Q extends CampList<T,Q>> extends SerializationHandler<Q> {	
		
	//TODO: abstract JSONListHandler to get rid of duplicate code;
	
	public abstract class JSONAbstractListHandler<T extends Serialization<T>,Q extends CampList<T,Q>> implements JSONListHandler<T,Q> {
		
		public String toJson(Q list) {
			String json = "{";
			json += "\"selected\":"+list.selected();
			json += ",\"isEmpty\":"+list.isEmpty();
			json += ",\"list\":[";
			boolean start = true;
			for(T e: list) {
				if(!start) {
					json += ",";
				} else {
					start = false;
				}
				json += e.toJson();
			}
			json += "]}";
			return json;
		}

		public Q fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public abstract Q fromJSONObject(JSONObject jo) throws DataMismatchException;

	}
	
	public class JSONValueListHandler extends JSONAbstractListHandler<Value,ValueList> {//implements JSONListHandler<Value,ValueList> {
		
		public ValueList fromJSONObject(JSONObject jo) throws DataMismatchException {
			return _fromJSONObject(jo);
		}
		
		public static ValueList _fromJSONObject(JSONObject jo) throws DataMismatchException {
			ValueList vl = new ValueList();
			if(jo.getBoolean("isEmpty")) {
				return vl;
			}
			vl.setSelectionIndex(jo.getInt("selected"));
			Iterator<Object> i = jo.getJSONArray("list").iterator();
			while(i.hasNext()) {
				JSONObject j = (JSONObject)i.next();
				vl.add(JSONValueHandler._fromJSONObject(j));
			}
			return vl;
		}
	}

	public class JSONAttributeListHandler extends JSONAbstractListHandler<Attribute,AttributeList> {

		public AttributeList fromJSONObject(JSONObject jo) throws DataMismatchException {
			return _fromJSONObject(jo);
		}

		public static AttributeList _fromJSONObject(JSONObject jo) throws DataMismatchException {
			AttributeList al = new AttributeList();
			if(jo.getBoolean("isEmpty")) {
				return al;
			}
			al.setSelectionIndex(jo.getInt("selected"));
			Iterator<Object> i = jo.getJSONArray("list").iterator();
			while(i.hasNext()) {
				JSONObject j = (JSONObject)i.next();
				al.add(JSONAttributeHandler._fromJSONObject(j));
			}
			return al;
		}


	}

}