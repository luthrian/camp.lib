package com.camsolute.code.camp.lib.contract.value;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.CampBinary.Binary;
import com.camsolute.code.camp.lib.contract.core.CampComplex.ValueComplex;
import com.camsolute.code.camp.lib.contract.core.CampComplex.ValueListComplex;
import com.camsolute.code.camp.lib.contract.core.CampTable.ValueTable;
import com.camsolute.code.camp.lib.contract.core.Coordinate;
import com.camsolute.code.camp.lib.contract.core.CampList.ValueList;
import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.CampStates;
import com.camsolute.code.camp.lib.contract.core.CampStates.CampStatesImpl;
import com.camsolute.code.camp.lib.contract.core.JSONComplexHandler.JSONValueComplexHandler;
import com.camsolute.code.camp.lib.contract.core.JSONComplexHandler.JSONValueListComplexHandler;
import com.camsolute.code.camp.lib.contract.core.JSONListHandler.JSONValueListHandler;
import com.camsolute.code.camp.lib.contract.value.Value;
import com.camsolute.code.camp.lib.contract.value.Value.BinaryValue;
import com.camsolute.code.camp.lib.contract.value.Value.BooleanValue;
import com.camsolute.code.camp.lib.contract.value.Value.ComplexValue;
import com.camsolute.code.camp.lib.contract.value.Value.DateTimeValue;
import com.camsolute.code.camp.lib.contract.value.Value.DateValue;
import com.camsolute.code.camp.lib.contract.value.Value.EnumValue;
import com.camsolute.code.camp.lib.contract.value.Value.IntegerValue;
import com.camsolute.code.camp.lib.contract.value.Value.ListValue;
import com.camsolute.code.camp.lib.contract.value.Value.MapValue;
import com.camsolute.code.camp.lib.contract.value.Value.SetValue;
import com.camsolute.code.camp.lib.contract.value.Value.StringValue;
import com.camsolute.code.camp.lib.contract.value.Value.TableValue;
import com.camsolute.code.camp.lib.contract.value.Value.TextValue;
import com.camsolute.code.camp.lib.contract.value.Value.TimeValue;
import com.camsolute.code.camp.lib.contract.value.Value.TimestampValue;
import com.camsolute.code.camp.lib.contract.value.Value.ValueType;
import static com.camsolute.code.camp.lib.contract.value.Value.ValueType.*;
import com.camsolute.code.camp.lib.utilities.Util;

public interface JSONValueHandler<T,Q extends Value<T,Q>> {
	
	public String toJson(Q value);
	
	public Q fromJson(String json) throws DataMismatchException;
	
	public Q fromJSONObject(JSONObject jo) throws DataMismatchException;
	
	public Q setDataFromJSONObject(Value<?,?> value, JSONObject jo) throws DataMismatchException;

	public static String _toJson(Value<?,?> value, String valueJson) {
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

	public static Value<?,?> _fromJson(String json) throws DataMismatchException {
		return _fromJSONObject(new JSONObject(json));
	}
	
 	public static Value<?,?> _fromJSONObject(JSONObject jo) throws DataMismatchException {
  	String id = "";
  	if(jo.has("id")) {id = jo.getString("id");}
  	String group = jo.getString("group");
  	boolean selected = jo.getBoolean("selected");
  	ValueType type = ValueType.valueOf(ValueType.class,jo.getString("type"));
  	Coordinate position = Coordinate._fromJSONObject(jo.getJSONObject("position")); //TODO:refactor 
  	CampStates states = CampStates._fromJSONObject(jo.getJSONObject("states")); // TODO:refactor 
  	
  	Value<?,?> v = Value.ValueFactory.generateValue(type);
  	
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

  public class BooleanJSONValueHandler implements JSONValueHandler<Boolean,BooleanValue> {
		
		public String toJson(BooleanValue value) {
			return _toJson(value,value.toString());
		}

		public BooleanValue fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public BooleanValue fromJSONObject(JSONObject jo) throws DataMismatchException {
        _checkType(_boolean,jo);
        return (BooleanValue) _fromJSONObject(jo);
		}
		
		public BooleanValue setDataFromJSONObject(Value<?,?> v, JSONObject jo) throws DataMismatchException {
			Boolean data = jo.getBoolean("value");
			((BooleanValue)v).updateData(data, false);
			return (BooleanValue)v;
		}
			
	}

	public class ComplexJSONValueHandler implements JSONValueHandler<ValueListComplex,ComplexValue> {


		public String toJson(ComplexValue value) {
			return _toJson(value,value.toValueString());
		}

		public ComplexValue fromJson(String json) throws  DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public ComplexValue fromJSONObject(JSONObject jo) throws DataMismatchException {
        _checkType(_complex,jo);
			return (ComplexValue) _fromJSONObject(jo);
		}
		
		public ComplexValue setDataFromJSONObject(Value<?,?> v, JSONObject jo) throws DataMismatchException {
			ValueListComplex value = JSONValueListComplexHandler._fromJSONObject(jo.getJSONObject("value"));
	     ((ComplexValue)v).updateData(value,false);
	     return (ComplexValue)v;
		}
		
}

	public class DateJSONValueHandler implements JSONValueHandler<DateTime,DateValue> {


		public String toJson(DateValue value) {
			return _toJson(value, "\""+value.toValueString()+"\"");
		}


		public DateValue fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public DateValue fromJSONObject(JSONObject jo) throws DataMismatchException {
        _checkType(_date,jo);
			return (DateValue) _fromJSONObject(jo);
		}
		
      public DateValue setDataFromJSONObject(Value<?,?> v, JSONObject jo) throws DataMismatchException {
          DateTime data = Util.Time.dateTimeFromString(jo.getString("value"));
          ((DateValue)v).updateData(data, false);
          return (DateValue)v;
      }
	}

  public class BinaryJSONValueHandler implements JSONValueHandler<Binary,BinaryValue> {
		
		public String toJson(BinaryValue value) {
			return _toJson(value,value.toJson());
		}

		public BinaryValue fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public BinaryValue fromJSONObject(JSONObject jo) throws DataMismatchException {
        _checkType(_binary,jo);
        return (BinaryValue) _fromJSONObject(jo);
		}
		
		public BinaryValue setDataFromJSONObject(Value<?,?> v, JSONObject jo) throws DataMismatchException {
			Binary data = Binary._fromJSONObject(jo.getJSONObject("value"));
			((BinaryValue)v).updateData(data, false);
			return (BinaryValue)v;
		}
	}


	public class DateTimeJSONValueHandler implements JSONValueHandler<DateTime,DateTimeValue> {


		public String toJson(DateTimeValue value) {
			return _toJson(value, "\""+value.toValueString()+"\"");
		}
		
		public DateTimeValue fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public DateTimeValue fromJSONObject(JSONObject jo) throws DataMismatchException {
        _checkType(_datetime,jo);
			return (DateTimeValue) _fromJSONObject(jo);
		}
      public DateTimeValue setDataFromJSONObject(Value<?,?> v, JSONObject jo) throws DataMismatchException {
          DateTime data = Util.Time.dateTimeFromString(jo.getString("value"));
          ((DateTimeValue)v).updateData(data, false);
          return (DateTimeValue)v;
      }
	}

	public class EnumJSONValueHandler implements JSONValueHandler<String,EnumValue> {


		public String toJson(EnumValue value) {
        return _toJson(value,"\""+value.toValueString()+"\"");
		}


		public EnumValue fromJson(String json) throws DataMismatchException {
        return fromJSONObject(new JSONObject(json));
		}

      public EnumValue fromJSONObject(JSONObject jo) throws DataMismatchException{
          _checkType(_enum,jo);
          return (EnumValue) _fromJSONObject(jo);
      }
      public EnumValue setDataFromJSONObject(Value<?,?> v, JSONObject jo) throws DataMismatchException {
          String data = jo.getString("value");
          ((EnumValue)v).updateData(data, false);
          return (EnumValue)v;
      }
	}

	public class IntegerJSONValueHandler implements JSONValueHandler<Integer,IntegerValue> {


		public String toJson(IntegerValue value) {
        return _toJson(value,value.toString());
		}


		public IntegerValue fromJson(String json) throws DataMismatchException {
        return fromJSONObject(new JSONObject(json));
		}

      public IntegerValue fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_integer,jo);
          return (IntegerValue) _fromJSONObject(jo);
      }
      public IntegerValue setDataFromJSONObject(Value<?,?> v, JSONObject jo) throws DataMismatchException {
          Integer data = jo.getInt("value");
          ((IntegerValue)v).updateData(data, false);
          return (IntegerValue)v;
      }
	}

	public class ListJSONValueHandler implements JSONValueHandler<ValueList,ListValue> {


		public String toJson(ListValue value) {
        return _toJson(value,value.toValueString());
		}


		public ListValue fromJson(String json) throws DataMismatchException {
        return fromJSONObject(new JSONObject(json));
		}

      public ListValue fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_list,jo);
          return (ListValue) _fromJSONObject(jo);
      }
 
      public ListValue setDataFromJSONObject(Value<?,?> v, JSONObject jo) throws DataMismatchException {
      	ValueList value = JSONValueListHandler._fromJSONObject(jo);
        ((ListValue)v).updateData(value, false);
        return (ListValue)v;
      }
	}

	public class MapJSONValueHandler implements JSONValueHandler<ValueComplex,MapValue> {


		public String toJson(MapValue value) {
        return _toJson(value,value.toValueString());
		}


		public MapValue fromJson(String json) throws DataMismatchException {
        return fromJSONObject(new JSONObject(json));
		}

      public MapValue fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_map, jo);
          return (MapValue) _fromJSONObject(jo);
      }
      public MapValue setDataFromJSONObject(Value<?,?> v, JSONObject jo) throws DataMismatchException {
      	ValueComplex value = JSONValueComplexHandler._fromJSONObject(jo);
          ((MapValue)v).updateData(value, false);
          return (MapValue)v;
      }
	}

	public class SetJSONValueHandler implements JSONValueHandler<String,SetValue> {


		public String toJson(SetValue value) {
        return _toJson(value,"\""+value.toValueString()+"\"");
		}


		public SetValue fromJson(String json) throws DataMismatchException {
            return fromJSONObject(new JSONObject(json));
		}

        public SetValue fromJSONObject(JSONObject jo) throws DataMismatchException {
            _checkType(_set,jo);
            return (SetValue) _fromJSONObject(jo);
        }
        public SetValue setDataFromJSONObject(Value<?,?> v, JSONObject jo) throws DataMismatchException {
            String data = jo.getString("value");
            ((SetValue)v).updateData(data, false);
            return (SetValue)v;
        }
	}

	public class StringJSONValueHandler implements JSONValueHandler<String,StringValue> {


		public String toJson(StringValue value) {
        return _toJson(value,"\""+value.toValueString()+"\"");
		}


		public StringValue fromJson(String json) throws DataMismatchException {
        return fromJSONObject(new JSONObject(json));
		}
      public StringValue fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_string, jo);
          return (StringValue) _fromJSONObject(jo);
      }
      public StringValue setDataFromJSONObject(Value<?,?> v, JSONObject jo) throws DataMismatchException {
          String data = jo.getString("value");
          ((StringValue)v).updateData(data, false);
          return (StringValue)v;
      }
	}

	public class TableJSONValueHandler implements JSONValueHandler<ValueTable,TableValue> {


		public String toJson(TableValue value) {
        return _toJson(value,value.toValueString());
		}


		public TableValue fromJson(String json) throws DataMismatchException {
        return fromJson(json);
		}

      public TableValue fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_table,jo);
          return (TableValue) _fromJSONObject(jo);
      }
      public TableValue setDataFromJSONObject(Value<?,?> v, JSONObject jo) throws DataMismatchException {
          ((TableValue)v).updateData(ValueTable._fromJSONObject(jo), false);
          return (TableValue)v;
      }
	}

	public class TextJSONValueHandler implements JSONValueHandler<String,TextValue> {


      public String toJson(TextValue value) {
          return _toJson(value,value.toValueString());
      }


      public TextValue fromJson(String json) throws DataMismatchException {
          return fromJson(json);
      }

      public TextValue fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_text,jo);
          return (TextValue) _fromJSONObject(jo);
      }
      public TextValue setDataFromJSONObject(Value<?,?> v, JSONObject jo) throws DataMismatchException {
          String data = jo.getString("value");
          ((TextValue)v).updateData(data, false);
          return (TextValue)v;
      }
	}

	public class TimeJSONValueHandler implements JSONValueHandler<DateTime,TimeValue> {


      public String toJson(TimeValue value) {
          return _toJson(value,value.toValueString());
      }


      public TimeValue fromJson(String json) throws DataMismatchException {
          return fromJson(json);
      }

      public TimeValue fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_time,jo);
          return (TimeValue) _fromJSONObject(jo);
      }
      public TimeValue setDataFromJSONObject(Value<?,?> v, JSONObject jo) throws DataMismatchException {
          DateTime data = Util.Time.dateTimeFromString(jo.getString("value"));
          ((TimeValue)v).updateData(data, false);
          return (TimeValue)v;
      }
	}

	public class TimestampJSONValueHandler implements JSONValueHandler<Timestamp,TimestampValue> {


      public String toJson(TimestampValue value) {
          return _toJson(value,"\""+value.toValueString()+"\"");
      }


      public TimestampValue fromJson(String json) throws DataMismatchException {
          return fromJson(json);
      }

      public TimestampValue fromJSONObject(JSONObject jo) throws DataMismatchException {
          _checkType(_timestamp,jo);
          return (TimestampValue) _fromJSONObject(jo);
      }
      public TimestampValue setDataFromJSONObject(Value<?,?> v, JSONObject jo) throws DataMismatchException {
          Timestamp data = Util.Time.timestamp(jo.getString("value"));
          ((TimestampValue)v).updateData(data, false);
          return (TimestampValue)v;
      }
	}


}
