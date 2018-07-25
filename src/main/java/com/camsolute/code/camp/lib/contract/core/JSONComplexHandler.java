package com.camsolute.code.camp.lib.contract.core;

import java.util.Iterator;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.attribute.Attribute;
import com.camsolute.code.camp.lib.contract.attribute.JSONAttributeHandler;
import com.camsolute.code.camp.lib.contract.core.CampComplex.AttributeComplex;
import com.camsolute.code.camp.lib.contract.core.CampComplex.AttributeListComplex;
import com.camsolute.code.camp.lib.contract.core.CampComplex.ValueComplex;
import com.camsolute.code.camp.lib.contract.core.CampComplex.ValueListComplex;
import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.CampList.AttributeList;
import com.camsolute.code.camp.lib.contract.core.CampList.ValueList;
import com.camsolute.code.camp.lib.contract.value.JSONValueHandler;
import com.camsolute.code.camp.lib.contract.value.Value;

public interface JSONComplexHandler<T extends Serialization<T>,Q extends CampComplex<T,Q>> extends SerializationHandler<Q> {	
	
	public abstract class AbstractJSONComplexHandler <T extends Serialization<T>,Q extends CampComplex<T,Q>> implements JSONComplexHandler<T,Q> {
		public String toJson(Q complex) {
			String json = "{";
			json += "\"isEmpty\":"+complex.isEmpty();
			json += "\"size\":"+complex.size();
			json += "\"map\":{";
			boolean start = true;
			for(String key:complex.keySet()) {
				if(!start) {
					json += ",";
				} else {
					start = false;
				}
				json += "\""+key+"\":"+complex.get(key).toJson();
			}
			json += "}}";
			return json;
		}

		public Q fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}

	}
	public class JSONValueComplexHandler extends AbstractJSONComplexHandler<Value<?,?>,ValueComplex> {

		public ValueComplex fromJSONObject(JSONObject jo) throws DataMismatchException {
			return _fromJSONObject(jo);
		}
		public static ValueComplex _fromJSONObject(JSONObject jo) throws DataMismatchException {
			ValueComplex c = new ValueComplex();
			if(jo.getBoolean("isEmpty")) {
				return c;
			}
			Iterator<String> mapGroups = jo.getJSONObject("map").keys();
			while(mapGroups.hasNext()) {
				String key = mapGroups.next();
				c.put(key,JSONValueHandler._fromJSONObject(jo.getJSONObject("map").getJSONObject(key)));
			}
			return c;
		}
		
	}
	
	public class JSONValueListComplexHandler extends AbstractJSONComplexHandler<ValueList,ValueListComplex> {

		public ValueListComplex fromJSONObject(JSONObject jo) throws DataMismatchException {
			return _fromJSONObject(jo);
		}
		public static ValueListComplex _fromJSONObject(JSONObject jo) throws DataMismatchException {
			ValueListComplex c = new ValueListComplex();
			if(jo.getBoolean("isEmpty")) {
				return c;
			}
			Iterator<String> mapGroups = jo.getJSONObject("map").keys();
			while(mapGroups.hasNext()) {
				String key = mapGroups.next();
				c.put(key,ValueList._fromJSONObject(jo.getJSONObject("map").getJSONObject(key)));
			}
			return c;
		}

	}

	public class JSONAttributeComplexHandler extends AbstractJSONComplexHandler<Attribute,AttributeComplex> {

		public AttributeComplex fromJSONObject(JSONObject jo) throws DataMismatchException {
			return _fromJSONObject(jo);
		}
		public AttributeComplex _fromJSONObject(JSONObject jo) throws DataMismatchException {
			AttributeComplex c = new AttributeComplex();
			if(jo.getBoolean("isEmpty")) {
				return c;
			}
			Iterator<String> mapGroups = jo.getJSONObject("map").keys();
			while(mapGroups.hasNext()) {
				String key = mapGroups.next();
				c.put(key,JSONAttributeHandler._fromJSONObject(jo.getJSONObject("map").getJSONObject(key)));
			}
			return c;
		}

	}

	public class JSONAttributeListComplexHandler extends AbstractJSONComplexHandler<AttributeList,AttributeListComplex> {

		public AttributeListComplex fromJSONObject(JSONObject jo) throws DataMismatchException {
			return _fromJSONObject(jo);
		}
		public AttributeListComplex _fromJSONObject(JSONObject jo) throws DataMismatchException {
			AttributeListComplex c = new AttributeListComplex();
			if(jo.getBoolean("isEmpty")) {
				return c;
			}
			Iterator<String> mapGroups = jo.getJSONObject("map").keys();
			while(mapGroups.hasNext()) {
				String key = mapGroups.next();
				c.put(key,AttributeList._fromJSONObject(jo.getJSONObject("map").getJSONObject(key)));
			}
			return c;
		}

	}

}