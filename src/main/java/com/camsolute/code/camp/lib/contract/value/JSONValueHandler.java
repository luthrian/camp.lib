package com.camsolute.code.camp.lib.contract.value;

import java.util.HashMap;
import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.CampComplex.ValueComplex;
import com.camsolute.code.camp.lib.contract.core.CampComplex.ValueListComplex;
import com.camsolute.code.camp.lib.contract.core.CampTable.AttributeTable;
import com.camsolute.code.camp.lib.contract.core.Coordinate;
import com.camsolute.code.camp.lib.contract.core.CampList.ValueList;
import com.camsolute.code.camp.lib.contract.core.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.JSONComplexHandler.JSONValueComplexHandler;
import com.camsolute.code.camp.lib.contract.core.JSONComplexHandler.JSONValueListComplexHandler;
import com.camsolute.code.camp.lib.contract.core.JSONListHandler.JSONValueListHandler;
import com.camsolute.code.camp.lib.contract.value.Value;
import com.camsolute.code.camp.lib.contract.value.Value.ValueType;
import static com.camsolute.code.camp.lib.contract.value.Value.ValueType.*;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface;
import com.camsolute.code.camp.lib.utilities.Util;

public interface JSONValueHandler {
	
	public String toJson(Value value);
	
	public Value fromJson(String json) throws DataMismatchException;
	
	public Value fromJSONObject(JSONObject jo) throws DataMismatchException;
	
	public Value setDataFromJSONObject(Value value, JSONObject jo) throws DataMismatchException;

	public static String _toJson(Value value, String valueJson) {
		String json = "{";
    json += "\"id\":"+value.id()+",";
    json += "\"type\":\""+value.type().name()+"\",";
    json += "\"group\":\""+value.group().name()+"\",";
    json += "\"selected\":"+value.selected()+",";
    json += "\"position\":"+value.position().toJson()+",";
    json += "\"states\":"+value.states().toJson()+",";
    json += "\"value\":" + valueJson;
		json += "}";		
		return json;
	}
	
	public static Value _fromJSONObject(JSONObject jo) throws DataMismatchException {
  	int id = 0;
  	if(jo.has("id")) {id = jo.getInt("id");}
  	String group = jo.getString("group");
  	boolean selected = jo.getBoolean("selected");
  	ValueType type = ValueType.valueOf(ValueType.class,jo.getString("type"));
  	Coordinate position = Coordinate._fromJSONObject(jo.getJSONObject("position")); //TODO:refactor 
  	CampStates states = CampStatesInterface._fromJSONObject(jo.getJSONObject("states")); // TODO:refactor 
  	
  	Value v = Value.ValueFactory.generateValue(type);
  	
  	v.updateId(id);
  	v.setGroup(group);
  	v.setSelected(selected);
  	v.position().update(position);
  	v.states().update(states);
  	return v.jsonHandler().setDataFromJSONObject(v, jo);
	}

  public static void _checkType(ValueType valueType, JSONObject jo) throws DataMismatchException {
        ValueType type = ValueType.valueOf(ValueType.class,jo.getString("type"));
        if( ! type.equals(valueType)) {
            throw new DataMismatchException(ValueMessages.DataMismatchException.msg(valueType, type));
        }
  }
	public class BooleanJSONValueHandler implements JSONValueHandler {
		
		public String toJson(Value value) {
			return _toJson(value,value.toString());
		}

		public Value fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public Value fromJSONObject(JSONObject jo) throws DataMismatchException {
        _checkType(_boolean,jo);
        return _fromJSONObject(jo);
		}
		
		public Value setDataFromJSONObject(Value v, JSONObject jo) throws DataMismatchException {
			Boolean data = jo.getBoolean("value");
			v.updateData(data, false);
			return v;
		}
			
	}

	public class ComplexJSONValueHandler implements JSONValueHandler {


		public String toJson(Value value) {
			return _toJson(value,value.toValueString());
		}

		public Value fromJson(String json) throws  DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public Value fromJSONObject(JSONObject jo) throws DataMismatchException {
        _checkType(_complex,jo);
			return _fromJSONObject(jo);
		}
		
		public Value setDataFromJSONObject(Value v, JSONObject jo) throws DataMismatchException {
			ValueListComplex value = JSONValueListComplexHandler._fromJSONObject(jo.getJSONObject("value"));
//			 HashMap<String,AttributeList> value = new HashMap<String,AttributeList>();
//			 JSONObject j = jo.getJSONObject("value");
//	      for(String group:j.keySet()) {
//	      	value.put(group, AttributeList._fromJSONObject(jo.getJSONObject(group)));
//	      }
	     v.updateData(value,false);
	     return v;
		}
		
}

	public class DateJSONValueHandler implements JSONValueHandler {


		public String toJson(Value value) {
			return _toJson(value, "\""+value.toValueString()+"\"");
		}


		public Value fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public Value fromJSONObject(JSONObject jo) throws DataMismatchException {
        _checkType(_date,jo);
			return _fromJSONObject(jo);
		}
		
      public Value setDataFromJSONObject(Value v, JSONObject jo) throws DataMismatchException {
          DateTime data = Util.Time.dateTimeFromString(jo.getString("value"));
          v.updateData(data, false);
          return v;
      }
	}

	public class DateTimeJSONValueHandler implements JSONValueHandler {


		public String toJson(Value value) {
			return _toJson(value, "\""+value.toValueString()+"\"");
		}
		
		public Value fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public Value fromJSONObject(JSONObject jo) throws DataMismatchException {
        _checkType(_datetime,jo);
			return _fromJSONObject(jo);
		}
      public Value setDataFromJSONObject(Value v, JSONObject jo) throws DataMismatchException {
          DateTime data = Util.Time.dateTimeFromString(jo.getString("value"));
          v.updateData(data, false);
          return v;
      }
	}

	public class EnumJSONValueHandler implements JSONValueHandler {


		public String toJson(Value value) {
        return _toJson(value,"\""+value.toValueString()+"\"");
		}


		public Value fromJson(String json) throws DataMismatchException {
        return fromJSONObject(new JSONObject(json));
		}

      public Value fromJSONObject(JSONObject jo) throws DataMismatchException{
          _checkType(_enum,jo);
          return _fromJSONObject(jo);
      }
      public Value setDataFromJSONObject(Value v, JSONObject jo) throws DataMismatchException {
          String data = jo.getString("value");
          v.updateData(data, false);
          return v;
      }
	}

	public class IntegerJSONValueHandler implements JSONValueHandler {


		public String toJson(Value value) {
        return _toJson(value,value.toString());
		}


		public Value fromJson(String json) throws DataMismatchException {
        return fromJSONObject(new JSONObject(json));
		}

      public Value fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_integer,jo);
          return _fromJSONObject(jo);
      }
      public Value setDataFromJSONObject(Value v, JSONObject jo) throws DataMismatchException {
          Integer data = jo.getInt("value");
          v.updateData(data, false);
          return v;
      }
	}

	public class ListJSONValueHandler implements JSONValueHandler {


		public String toJson(Value value) {
        return _toJson(value,value.toValueString());
		}


		public Value fromJson(String json) throws DataMismatchException {
        return fromJSONObject(new JSONObject(json));
		}

      public Value fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_list,jo);
          return _fromJSONObject(jo);
      }
 
      public Value setDataFromJSONObject(Value v, JSONObject jo) throws DataMismatchException {
      	ValueList value = JSONValueListHandler._fromJSONObject(jo);
        v.updateData(value, false);
        return v;
      }
	}

	public class MapJSONValueHandler implements JSONValueHandler {


		public String toJson(Value value) {
        return _toJson(value,value.toValueString());
		}


		public Value fromJson(String json) throws DataMismatchException {
        return fromJSONObject(new JSONObject(json));
		}

      public Value fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_map, jo);
          return _fromJSONObject(jo);
      }
      public Value setDataFromJSONObject(Value v, JSONObject jo) throws DataMismatchException {
      	ValueComplex value = JSONValueComplexHandler._fromJSONObject(jo);
          v.updateData(value, false);
          return v;
      }
	}

	public class SetJSONValueHandler implements JSONValueHandler {


		public String toJson(Value value) {
        return _toJson(value,"\""+value.toValueString()+"\"");
		}


		public Value fromJson(String json) throws DataMismatchException {
            return fromJSONObject(new JSONObject(json));
		}

        public Value fromJSONObject(JSONObject jo) throws DataMismatchException {
            _checkType(_set,jo);
            return _fromJSONObject(jo);
        }
        public Value setDataFromJSONObject(Value v, JSONObject jo) throws DataMismatchException {
            String data = jo.getString("value");
            v.updateData(data, false);
            return v;
        }
	}

	public class StringJSONValueHandler implements JSONValueHandler {


		public String toJson(Value value) {
        return _toJson(value,"\""+value.toValueString()+"\"");
		}


		public Value fromJson(String json) throws DataMismatchException {
        return fromJSONObject(new JSONObject(json));
		}
      public Value fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_string, jo);
          return _fromJSONObject(jo);
      }
      public Value setDataFromJSONObject(Value v, JSONObject jo) throws DataMismatchException {
          String data = jo.getString("value");
          v.updateData(data, false);
          return v;
      }
	}

	public class TableJSONValueHandler implements JSONValueHandler {


		public String toJson(Value value) {
        return _toJson(value,value.toValueString());
		}


		public Value fromJson(String json) throws DataMismatchException {
        return fromJson(json);
		}

      public Value fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_table,jo);
          return _fromJSONObject(jo);
      }
      public Value setDataFromJSONObject(Value v, JSONObject jo) throws DataMismatchException {
          v.updateData(AttributeTable._fromJSONObject(jo), false);
          return v;
      }
	}

	public class TextJSONValueHandler implements JSONValueHandler {


      public String toJson(Value value) {
          return _toJson(value,value.toValueString());
      }


      public Value fromJson(String json) throws DataMismatchException {
          return fromJson(json);
      }

      public Value fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_text,jo);
          return _fromJSONObject(jo);
      }
      public Value setDataFromJSONObject(Value v, JSONObject jo) throws DataMismatchException {
          String data = jo.getString("value");
          v.updateData(data, false);
          return v;
      }
	}

	public class TimeJSONValueHandler implements JSONValueHandler {


      public String toJson(Value value) {
          return _toJson(value,value.toValueString());
      }


      public Value fromJson(String json) throws DataMismatchException {
          return fromJson(json);
      }

      public Value fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_time,jo);
          return _fromJSONObject(jo);
      }
      public Value setDataFromJSONObject(Value v, JSONObject jo) throws DataMismatchException {
          DateTime data = Util.Time.dateTimeFromString(jo.getString("value"));
          v.updateData(data, false);
          return v;
      }
	}

	public class TimestampJSONValueHandler implements JSONValueHandler {


      public String toJson(Value value) {
          return _toJson(value,"\""+value.toValueString()+"\"");
      }


      public Value fromJson(String json) throws DataMismatchException {
          return fromJson(json);
      }

      public Value fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_timestamp,jo);
          return _fromJSONObject(jo);
      }
      public Value setDataFromJSONObject(Value v, JSONObject jo) throws DataMismatchException {
          Timestamp data = Util.Time.timestamp(jo.getString("value"));
          v.updateData(data, false);
          return v;
      }
	}


}
