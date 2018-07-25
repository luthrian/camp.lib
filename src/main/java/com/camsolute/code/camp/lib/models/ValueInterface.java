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
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.eclipse.sisu.space.asm.Type;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.CampStates;
import com.camsolute.code.camp.lib.contract.core.CampStates.CampStatesImpl;
import com.camsolute.code.camp.lib.contract.core.CampStates.IOAction;
import com.camsolute.code.camp.lib.contract.HasGroup;
import com.camsolute.code.camp.lib.contract.HasId;
import com.camsolute.code.camp.lib.contract.core.HasStates;
import com.camsolute.code.camp.lib.contract.HasValue;
import com.camsolute.code.camp.lib.contract.IsSelectable;
import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.contract.core.Coordinate;
import com.camsolute.code.camp.lib.contract.core.HasCoordinate;
import com.camsolute.code.camp.lib.contract.core.Value;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;
import com.camsolute.code.camp.lib.types.BooleanValue;
import com.camsolute.code.camp.lib.types.CampList;
import com.camsolute.code.camp.lib.types.CampListInterface;
import com.camsolute.code.camp.lib.types.ComplexValue;
import com.camsolute.code.camp.lib.types.DateTimeValue;
import com.camsolute.code.camp.lib.types.DateValue;
import com.camsolute.code.camp.lib.types.IntegerValue;
import com.camsolute.code.camp.lib.types.ListValue;
import com.camsolute.code.camp.lib.types.MapValue;
import com.camsolute.code.camp.lib.types.StringValue;
import com.camsolute.code.camp.lib.types.TableValue;
import com.camsolute.code.camp.lib.types.TextValue;
import com.camsolute.code.camp.lib.types.TimeValue;
import com.camsolute.code.camp.lib.types.TimestampValue;
import com.camsolute.code.camp.lib.types.SetValue;
import com.camsolute.code.camp.lib.types.EnumValue;
import com.camsolute.code.camp.lib.utilities.Util;

public interface ValueInterface<T, Q extends Value<T,Q>> extends HasId, HasStates, HasGroup, HasCoordinate, IsSelectable, HasValue<T>, Serialization<Value<T,Q>> {

    public Coordinate position();
    
    public static String _toJson(Value<?,?> v) {
        String json ="{";
        json += _toInnerJson(v);
        json += "}";
        return json;
    }
    
    public static String _toInnerJson(Value<?,?> v) {
    	String json = "";
        json += "\"id\":"+v.id()+",";
        json += "\"type\":\""+v.type().name()+"\",";
        json += "\"group\":\""+v.group().name()+"\",";
        json += "\"selected\":"+v.selected()+",";
        json += "\"position\":"+v.position().toJson()+",";
        json += "\"states\":"+v.states().toJson()+",";
        json += "\"value\":"+_valueToJson(v);
        return json;
    }


  public static String _valueToJson(Value<?,?> v) {
        String str = "";
        AttributeType type = v.type();
        switch(type) {
        case _integer:
            str += String.valueOf(((IntegerValue)v).value());
            break;
        case _string:
            str += "\""+((StringValue)v).value()+"\"";
            break;
        case _text:
            str += "\""+((TextValue)v).value()+"\"";
            break;
        case _boolean:
            str += String.valueOf(((BooleanValue)v).value());
            break;
        case _timestamp:
            str += "\""+((TimestampValue)v).value().toString()+"\"";
            break;
        case _datetime:
            str += "\""+((DateTimeValue)v).value().toString()+"\"";
            break;
        case _date:
            str += "\""+((DateValue)v).value().toString()+"\"";
            break;
        case _time:
            str += "\""+((TimeValue)v).value().toString()+"\"";
            break;
        case _set:
            str += "\""+((SetValue)v).value()+"\"";
            break;
        case _enum:
            str += "\""+((EnumValue)v).value()+"\"";
            break;
        case _complex:
            str += ValueInterface._complexValueToJson((ComplexValue)v);
            break;
        case _table:
            str += ValueInterface._tableValueToJson((TableValue)v);
            break;
        case _map:
            str += ValueInterface._mapValueToJson((MapValue)v);
            break;
        case _list:
            str += ValueInterface._listValueToJson((ListValue)v);
            break;
        default:
            break;
        }
        return str;
    }

    public static String _mapValueToJson(MapValue v){
        String json = "{";
        HashMap<String,Attribute<? extends Value<?,?>>> h = v.value();
        boolean start = true;
        for(String group:h.keySet()){
          if(!start){
	            json += ",";
	        } else {
	            start = false;
	        }
            json += "\""+group+"\":"+AttributeInterface._toJson(h.get(group));
        }
        json += "}";
        return json;
    }

    public static String _arrayToJson(ArrayList<Attribute<? extends Value<?,?>>> v){
        String json = "[";
        boolean start = true;
        for(Attribute<? extends Value<?,?>> a: v){
            if(!start){
                json += ",";
            } else {
                start = false;
            }
            json += AttributeInterface._toJson(a);
        }
        json += "]";
        return json;
    }

    public static String _listValueToJson(ListValue v){
        String json = "[";
        ArrayList<Attribute<? extends Value<?,?>>> h = v.value();
        boolean start = true;
        for(Attribute<? extends Value<?,?>> a: h){
            if(!start){
                json += ",";
            } else {
                start = false;
            }
            json += AttributeInterface._toJson(a);
        }
        json += "]";
        return json;
    }

    public static String _complexValueToJson(ComplexValue v){
        String json = "{";
        HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>> h = v.value();
        boolean start = true;
        for(String group: h.keySet()){
        	if(!start) {
        		json += ",";
        	} else {
        		start = false;
        	}
            json += "\""+group+"\":"+_arrayToJson(h.get(group));
        }
        json += "}";
        return json;
    }

    public static String _tableValueToJson(TableValue v){
        String json = "[";
        ArrayList<ArrayList<Attribute<? extends Value<?,?>>>> h = v.value();
        boolean start = true;
        for(ArrayList<Attribute<? extends Value<?,?>>> l: h){
            if(!start){
                json += ",";
            } else {
                start = false;
            }
           json += _arrayToJson(l);
        }
        json += "]";
        return json;
    }
    
    public static Value<?,?> _fromJson(String json) {
    	return _fromJSONObject(new JSONObject(json));
    }

    public static Value<?,?> _fromJSONObject(JSONObject jo) {
    	int id = 0;
    	if(jo.has("id")) {id = jo.getInt("id");}
    	String group = jo.getString("group");
    	boolean selected = jo.getBoolean("selected");
    	AttributeType type = AttributeType.valueOf(AttributeType.class,jo.getString("type"));
    	Coordinate position = Coordinate._fromJSONObject(jo.getJSONObject("position"));
    	CampStates states = CampStates._fromJSONObject(jo.getJSONObject("states"));
    	
    	switch(type) {
    	case _integer:
    		int ivalue = jo.getInt("value");
    		IntegerValue iv = new  IntegerValue(id,ivalue,group,position,selected);//Value<Integer>(id,type, (Integer) value,group,position);
    		iv.states().update(states);
    		return iv;
    	case _string:
    		String svalue = jo.getString("value");
    		StringValue sv = new StringValue(id,svalue,group,position,selected); //Value<String>(id,type, (String)value,group,position);
    		sv.states().update(states);
    		return sv;
    	case _text:
    		String txtvalue = jo.getString("value");
    		TextValue txv = new TextValue(id,txtvalue,group,position,selected); //Value<String>(id,type, (String)value,group,position);
    		txv.states().update(states);
    		return txv;
    	case _boolean:
    		boolean bvalue = Boolean.valueOf(jo.getString("value"));
    		BooleanValue bv = new BooleanValue(id,bvalue,group,position,selected); //Value<Boolean>(id,type, (Boolean)value,group,position);
    		bv.states().update(states);
    		return bv;
    	case _timestamp:
    		Timestamp tsvalue = Util.Time.timestamp(jo.getString("value"));
    		TimestampValue tsv = new TimestampValue(id,tsvalue,group,position,selected); //Value<Timestamp>(id,type, (Timestamp)value,group,position);
    		tsv.states().update(states);
    		return tsv;
    	case _datetime:
    		DateTime dtvalue = Util.Time.dateTimeFromString(jo.getString("value"));
    		DateTimeValue dtv = new DateTimeValue(id,dtvalue,group,position,selected); //Value<DateTime>(id,type, (DateTime)value,group,position);
    		dtv.states().update(states);
    		return dtv;
    	case _date:
    		DateTime dvalue = Util.Time.dateTimeFromString(jo.getString("value"));
    		DateValue dv = new DateValue(id,dvalue,group,position,selected); //Value<DateTime>(id,type, (DateTime)value,group,position);
    		dv.states().update(states);
    		return dv;
    	case _time:
    		DateTime tvalue = Util.Time.dateTimeFromString(jo.getString("value"));
    		TimeValue tv = new TimeValue(id,tvalue,group,position,selected); //Value<DateTime>(id,type, (DateTime)value,group,position);
    		tv.states().update(states);
    		return tv;
    	case _set:
    		String stvalue = jo.getString("value");
    		 SetValue stv = new SetValue(id,stvalue,group,position,selected); //Value<String>(id,type, (String)value,group,position);
    		 stv.states().update(states);
    		 return stv;
    	case _enum:
    		String evalue = jo.getString("value");
    		 EnumValue ev = new EnumValue(id,evalue,group,position,selected); //Value<String>(id,type, (String)value,group,position);
    		 ev.states().update(states);
    		 return ev;
    	case _complex:
    		HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>> cvalue = _fromComplexJSONObject(jo.getJSONObject("value"));
    		 ComplexValue cv = new ComplexValue(id,cvalue,group,position,selected); //Value<String>(id,type, (String)value,group,position);
    		 cv.states().update(states);
    		 return cv;
    	case _table:
    		ArrayList<ArrayList<Attribute<? extends Value<?,?>>>> tblvalue = _fromTableJSONArray(jo.getJSONArray("value"));
    		 TableValue tbv = new TableValue(id,tblvalue,group,position,selected); //Value<String>(id,type, (String)value,group,position);
    		 tbv.states().update(states);
    		 return tbv;
    	case _map:
    		HashMap<String,Attribute<? extends Value<?,?>>> mvalue = _fromMapJSONObject(jo.getJSONObject("value"));
    		 MapValue mv = new MapValue(id,mvalue,group,position,selected); //Value<String>(id,type, (String)value,group,position);
    		 mv.states().update(states);
    		 return mv;
    	case _list:
    		ArrayList<Attribute<? extends Value<?,?>>> lvalue = _fromListJSONArray(jo.getJSONArray("value"));
    		 ListValue lv = new ListValue(id,lvalue,group,position,selected); //Value<String>(id,type, (String)value,group,position);
    		 lv.states().update(states);
    		 return lv;
    	default:
    		return null;
    	}
    }

    public static HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>> _fromComplexJson(String jsonValue){
    	JSONObject jo = new JSONObject(jsonValue);
    	return _fromComplexJSONObject(jo);
    }
    public static HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>> _fromComplexJSONObject(JSONObject jo){
      HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>> value = new HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>>();
      for(String group:jo.keySet()) {
      	
      	value.put(group, _fromListJSONArray(jo.getJSONArray(group)));
      }
      return value;
    }

    public static ArrayList<ArrayList<Attribute<? extends Value<?,?>>>> _fromTableJson(String jsonValue){
    	JSONArray ja = new JSONArray(jsonValue);
    	return _fromTableJSONArray(ja);
    }
    public static ArrayList<ArrayList<Attribute<? extends Value<?,?>>>> _fromTableJSONArray(JSONArray ja){
      ArrayList<ArrayList<Attribute<? extends Value<?,?>>>> value = new ArrayList<ArrayList<Attribute<? extends Value<?,?>>>>();
      for(int i = 0;i<ja.length();i++) {
      	value.add(_fromListJSONArray(ja.getJSONArray(i)));
      }
      return value;
    }

    public static HashMap<String,Attribute<? extends Value<?,?>>> _fromMapJson(String jsonValue){
    	JSONObject jo = new JSONObject(jsonValue);
    	return _fromMapJSONObject(jo);
    }
    public static HashMap<String,Attribute<? extends Value<?,?>>> _fromMapJSONObject(JSONObject jo){
      HashMap<String,Attribute<? extends Value<?,?>>> value = new HashMap<String,Attribute<? extends Value<?,?>>>();
      for(String group:jo.keySet()) {
      	value.put(group, AttributeInterface._fromJSONObject(jo.getJSONObject(group)));
      }
      return value;
    }

    public static ArrayList<Attribute<? extends Value<?,?>>> _fromListJson(String jsonValue){
    	JSONArray ja = new JSONArray(jsonValue);
    	return _fromListJSONArray(ja);
    }
    public static ArrayList<Attribute<? extends Value<?,?>>> _fromListJSONArray(JSONArray ja){
      ArrayList<Attribute<? extends Value<?,?>>> value = new ArrayList<Attribute<? extends Value<?,?>>>();
      for(int i = 0;i<ja.length();i++) {
      	value.add(AttributeInterface._fromJSONObject(ja.getJSONObject(i)));
      }
      return value;
    }
    
    public static String _toComplexJson(HashMap<String,CampList> v){
    	String json = "{";
    	boolean start = true;
    	for(String group : v.keySet()) {
    		if(!start) {
    			json += ",";
    		} else {
    			start = false;
    		}
    		json += "\""+group+"\":"+ CampListInterface._toJson(v.get(group));
    	}
    	json += "}";
    	return json;
    }

    public static String _toTableJson(ArrayList<CampList> v){
    	String json = "[";
    	boolean start = true;
    	for(CampList c : v) {
    		if(!start) {
    			json += ",";
    		} else {
    			start = false;
    		}
    		json += CampListInterface._toJson(c);
    	}
    	json += "]";
    	return json;
    }

    public static String _toMapJson(HashMap<String,Attribute<? extends Value<?,?>>> v){
    	String json = "{";
    	boolean start = true;
    	for(String group : v.keySet()) {
    		if(!start) {
    			json += ",";
    		} else {
    			start = false;
    		}
    		json += "\""+group+"\":"+ AttributeInterface._toJson(v.get(group));
    	}
    	json += "}";
    	return json;
    }

    public static String _toListJson(ArrayList<Attribute<? extends Value<?,?>>> v){
    	String json = "[";
    	boolean start = true;
    	for(Attribute<? extends Value<?,?>> a : v) {
    		if(!start) {
    			json += ",";
    		} else {
    			start = false;
    		}
    		json += AttributeInterface._toJson(a);
    	}
    	json += "]";
    	return json;
    }
   
		public static String toString(Value<?,?> v) {
    	String value = null;
			switch (v.type()) {
			case _integer:
				value = "" + ((IntegerValue) v).value();
				break;
			case _string:
				value = ((StringValue) v).value();
				break;
			case _text:
				value = ((TextValue) v).value();
				break;
			case _boolean:
				value = String.valueOf(((BooleanValue) v).value());
				break;
			case _timestamp:
				value = ((TimestampValue) v).value().toString();
				break;
			case _datetime:
				value = ((DateTimeValue) v).value().toString();
				break;
			case _date:
				value = ((DateValue) v).value().toString();
				break;
			case _time:
				value = ((TimeValue) v).value().toString();
				break;
			case _map:
				value = ValueInterface._mapValueToJson((MapValue) v);
				break;
			case _list:
				value = ValueInterface._listValueToJson((ListValue) v);
				break;
			case _complex:
				value = ValueInterface._complexValueToJson((ComplexValue) v);
				break;
			case _table:
				value = ValueInterface._tableValueToJson((TableValue) v);
				break;
			default:
				break;
			}
			return value;
    }

		public Value<T,Q> clone();
		
		public static Value<?,?> clone(Value<?,?> value) {
			String json = value.toJson();
			return ValueInterface._fromJson(json);
		}
}
