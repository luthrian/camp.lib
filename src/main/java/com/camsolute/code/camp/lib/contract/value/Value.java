package com.camsolute.code.camp.lib.contract.value;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasGroup;
import com.camsolute.code.camp.lib.contract.HasId;
import com.camsolute.code.camp.lib.contract.HasStates;
import com.camsolute.code.camp.lib.contract.IsSelectable;
import com.camsolute.code.camp.lib.contract.core.Serialization;
import com.camsolute.code.camp.lib.contract.core.CampData;
import com.camsolute.code.camp.lib.contract.core.CampTable.ValueTable;
import com.camsolute.code.camp.lib.contract.core.HasCoordinate;
import com.camsolute.code.camp.lib.contract.core.Coordinate;
import com.camsolute.code.camp.lib.contract.core.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.CampComplex.ValueComplex;
import com.camsolute.code.camp.lib.contract.core.CampComplex.ValueListComplex;
import com.camsolute.code.camp.lib.contract.core.CampList.ValueList;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.utilities.Util;

public interface Value extends HasId, HasStates, HasGroup, HasCoordinate, IsSelectable, Serialization<Value>, HasJSONValueHandler, HasSQLValueHandler {

	public static enum ValueType {
		_boolean
		,_complex
		,_date
		,_datetime
		,_enum
		,_integer
		,_list
		,_map
		,_set
		,_string
		,_table
		,_text
		,_time
		,_timestamp		
	}

	public void updateValue(Value newValue,boolean registerStateChange) throws ValueTypeMismatchException;
	
	public void updateData(Object data, boolean registerStateChange) throws DataMismatchException;
	
	public ValueType type();
	
	public String toString();
		
	public String toValueString();
	
	public static class ValueFactory {
		public static Value generateValue(ValueType type) { 
			switch(type) {
			case _boolean:
				return new BooleanValue();
			case _complex:
				return new ComplexValue();
			case _date:
				return new DateValue();
			case _datetime:
				return new DateTimeValue();
			case _enum:
				return new EnumValue();
			case _integer:
				return new IntegerValue();
			case _list:
				return new ListValue();
			case _map:
				return new MapValue();
			case _set:
				return new SetValue();
			case _string:
				return new StringValue();
			case _table:
				return new TableValue();
			case _text:
				return new TextValue();
			case _time:
				return new TimeValue();
			case _timestamp:
				return new TimestampValue();
				
			}
			return null;
		}
		public static Value generateValue(ValueType type,Object data) {
			switch(type) {
			case _boolean:
				return new BooleanValue((Boolean)data);
			case _complex:
				return new ComplexValue((ValueListComplex)data);
			case _date:
				return new DateValue((DateTime)data);
			case _datetime:
				return new DateTimeValue((DateTime)data);
			case _enum:
				return new EnumValue((String)data);
			case _integer:
				return new IntegerValue((Integer)data);
			case _list:
				return new ListValue((ValueList)data);
			case _map:
				return new MapValue((ValueComplex)data);
			case _set:
				return new SetValue((String)data);
			case _string:
				return new StringValue((String)data);
			case _table:
				return new TableValue((ValueTable)data);
			case _text:
				return new TextValue((String)data);
			case _time:
				return new TimeValue((DateTime)data);
			case _timestamp:
				return new TimestampValue((Timestamp)data);
				
			}
			return null;
		}
		
	}
	
	public abstract class AbstractValue implements Value {
		
		private int id = CampData.NEW_ID;
	
		private Group group = new Group(Util.Config.instance().defaultGroup("Value"));
		
		private Coordinate position;

		private boolean selected = false;

		private CampStates states = new CampStates();

		private SQLValueHandler sqlHandler;

		private JSONValueHandler jsonHandler;

		public int id() {
			return this.id;
		}
		
		public int updateId(int id) {
			int r = this.id;
			this.id = id;
			return r;
		}
		

		public CampStates states() {
			return this.states;
		}

		public Group group() {
			return this.group;
		}

		public void updateGroup(Group group) {
			this.group = group;
			this.states.modify();
		}

		public void updateGroup(String group) {
			this.group = new Group(group);
			this.states.modify();
		}

		public void setGroup(String group) {
			this.group = new Group(group);
		}

		public Coordinate position() {
			return this.position;
		}

		public boolean selected() {
			return this.selected;
		}

		public void setSelected(boolean select) {
			this.selected = select;
		}

		public void select() {
			this.selected = true;
		}

		public void deselect() {
			this.selected = false;
		}
		
		public void updateValue(Value newValue, boolean registerStateChange) throws ValueTypeMismatchException {
			if(!newValue.type().name().equals(this.type().name())) {
				throw new ValueTypeMismatchException("Expected ValueType("+this.type().name()+") but new Value had ValueType("+newValue.type().name()+")");
			}
			this.id = newValue.id();
			this.group = newValue.group();
			this.states.update(newValue.states());
			this.selected = newValue.selected();
			this.position = newValue.position();
			this.jsonHandler = newValue.jsonHandler();
			this.sqlHandler = newValue.sqlValueHandler();
			if(registerStateChange) {
				this.states.modify();
			}
		}
		
		public void setSQLValueHandler(SQLValueHandler sqlValueHandler) {
			this.sqlHandler = sqlValueHandler;
		}

		public void sqlValueHandler(SQLValueHandler sqlValueHandler) {
			this.sqlHandler = sqlValueHandler;
		}
		
		public SQLValueHandler sqlValueHandler() {
			return this.sqlHandler;
		}
		public void jsonHandler(JSONValueHandler jsonHandler) {
			this.jsonHandler = jsonHandler;
		}
		
		public JSONValueHandler jsonHandler() {
			return this.jsonHandler;
		}
		
		public Value fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public String toJson() {
			return jsonHandler.toJson(this);
		}
		
		public Value fromJSONObject(JSONObject jo) throws DataMismatchException {
			return jsonHandler().fromJSONObject(jo);
		}
	}
	
	public class BooleanValue extends AbstractValue {
		
		private boolean myValue = false;
		
		protected BooleanValue() {
			sqlValueHandler(new SQLValueHandler.BooleanSQLValueHandler());
			jsonHandler(new JSONValueHandler.BooleanJSONValueHandler());
		}
		
		protected BooleanValue(Boolean value) {
			this.myValue = value;
			sqlValueHandler(new SQLValueHandler.BooleanSQLValueHandler());
			jsonHandler(new JSONValueHandler.BooleanJSONValueHandler());
		}
		public ValueType type() {
			return  ValueType._boolean;
		}

		public String toString() {
			return String.valueOf(myValue);
		}

		public String toValueString() {
			return toString();
		}
		
 		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (Boolean) data;
			if(registerStateChange) {
				states().modify();
			}
		}


	}

	public class ComplexValue extends AbstractValue {
		
		ValueListComplex myValue = new ValueListComplex();
	
		protected ComplexValue() {
			sqlValueHandler(new SQLValueHandler.ComplexSQLValueHandler());
			jsonHandler(new JSONValueHandler.ComplexJSONValueHandler());
		}
		
		protected ComplexValue(ValueListComplex value) {
			this.myValue = value;
			sqlValueHandler(new SQLValueHandler.ComplexSQLValueHandler());
			jsonHandler(new JSONValueHandler.ComplexJSONValueHandler());
		}

		public ValueType type() {
			return ValueType._complex;
		}
		
		public String toString() {
			return toJson();
		}

		public String toValueString() {
			return "NULL";
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (ValueListComplex) data;
			if(registerStateChange) {
				states().modify();
			}
		}
		
	}
	
	public class DateValue extends AbstractValue {
		
		private DateTime myValue;
		
		protected DateValue() {
			sqlValueHandler(new SQLValueHandler.DateTimeSQLValueHandler());
			jsonHandler(new JSONValueHandler.DateJSONValueHandler());
		}
		
		protected DateValue(DateTime value) {
			this.myValue = value;
			sqlValueHandler(new SQLValueHandler.DateTimeSQLValueHandler());
			jsonHandler(new JSONValueHandler.DateJSONValueHandler());
		}

		public ValueType type() {
			return ValueType._date;
		}
		
		public String toString() {
			return Util.Time.getDate(myValue);
		}

		public String toValueString() {
			return myValue.toString();
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (DateTime) data;
			if(registerStateChange) {
				states().modify();
			}
		}


	}
	
	public class DateTimeValue extends AbstractValue {
		
		private DateTime myValue;
		
		protected DateTimeValue() {
			sqlValueHandler(new SQLValueHandler.DateTimeSQLValueHandler());
			jsonHandler(new JSONValueHandler.DateJSONValueHandler());
		}
		
		protected DateTimeValue(DateTime value) {
			this.myValue = value;
			sqlValueHandler(new SQLValueHandler.DateTimeSQLValueHandler());
			jsonHandler(new JSONValueHandler.DateTimeJSONValueHandler());
		}

		public ValueType type() {
			return ValueType._datetime;
		}
		
		public String toString() {
			return myValue.toString(Util.Time.formatDateTime);
		}

		public String toValueString() {
			return myValue.toString();
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (DateTime) data;
			if(registerStateChange) {
				states().modify();
			}
		}


	}
	
 public class EnumValue extends AbstractValue {
	 
	 private String myValue;

	 protected EnumValue() {
			sqlValueHandler(new SQLValueHandler.TextSQLValueHandler());
			jsonHandler(new JSONValueHandler.EnumJSONValueHandler());
		}
		
		protected EnumValue(String value) {
			this.myValue = value;
			sqlValueHandler(new SQLValueHandler.TextSQLValueHandler());
			jsonHandler(new JSONValueHandler.EnumJSONValueHandler());
		}

		public ValueType type() {
			return ValueType._enum;
		}
		
		public String toString() {
			return myValue;
		}
		
		public String toValueString() {
			return toString();
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (String) data;
			if(registerStateChange) {
				states().modify();
			}
		}


 }

 public class IntegerValue extends AbstractValue {
	 
	 private int myValue;

	 protected IntegerValue() {
			sqlValueHandler(new SQLValueHandler.IntegerSQLValueHandler());
			jsonHandler(new JSONValueHandler.IntegerJSONValueHandler());
		}
		
		protected IntegerValue(int value) {
			this.myValue = value;
			sqlValueHandler(new SQLValueHandler.IntegerSQLValueHandler());
			jsonHandler(new JSONValueHandler.IntegerJSONValueHandler());
		}

		public ValueType type() {
			return ValueType._integer;
		}
		
		public String toString() {
			return String.valueOf(myValue);
		}
		
		public String toValueString() {
			return toString();
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (Integer) data;
			if(registerStateChange) {
				states().modify();
			}
		}


 }

 public class ListValue extends AbstractValue {
	 
	 private ValueList myValue = new ValueList();

	 protected ListValue() {
			sqlValueHandler(new SQLValueHandler.TextSQLValueHandler());
			jsonHandler(new JSONValueHandler.ListJSONValueHandler());
		}
		
		protected ListValue(ValueList value) {
			this.myValue = value;
			sqlValueHandler(new SQLValueHandler.TextSQLValueHandler());
			jsonHandler(new JSONValueHandler.ListJSONValueHandler());
		}

		public ValueType type() {
			return ValueType._list;
		}
		
		public String toString() {
			return toJson();
		}
		
		public String toValueString() {
			return toString();
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (ValueList) data;
			if(registerStateChange) {
				states().modify();
			}
		}

 }

 public class MapValue extends AbstractValue {
	 
	 private ValueComplex myValue = new ValueComplex();

	 protected MapValue() {
			sqlValueHandler(new SQLValueHandler.ComplexSQLValueHandler());
			jsonHandler(new JSONValueHandler.MapJSONValueHandler());
		}
		
		protected MapValue(ValueComplex value) {
			this.myValue = value;
			sqlValueHandler(new SQLValueHandler.ComplexSQLValueHandler());
			jsonHandler(new JSONValueHandler.MapJSONValueHandler());
		}

		public ValueType type() {
			return ValueType._map;
		}
		
		public String toString() {
			return toJson();
		}
		
		public String toValueString() {
			return toString();
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (ValueComplex) data;
		}


 }

 public class SetValue extends AbstractValue {
	 
	 private String myValue;

	 protected SetValue() {
			sqlValueHandler(new SQLValueHandler.TextSQLValueHandler());
			jsonHandler(new JSONValueHandler.SetJSONValueHandler());
		}
		
		protected SetValue(String value) {
			this.myValue = value;
			sqlValueHandler(new SQLValueHandler.TextSQLValueHandler());
			jsonHandler(new JSONValueHandler.SetJSONValueHandler());
		}

		public ValueType type() {
			return ValueType._set;
		}
		
		public String toString() {
			return myValue;
		}
		
		public String toValueString() {
			return toString();
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (String) data;
			if(registerStateChange) {
				states().modify();
			}
		}

 }

 public class StringValue extends AbstractValue {
	 
	 private String myValue;

	 protected StringValue() {
			sqlValueHandler(new SQLValueHandler.StringSQLValueHandler());
			jsonHandler(new JSONValueHandler.StringJSONValueHandler());
		}
		
		protected StringValue(String value) {
			this.myValue = value;
			sqlValueHandler(new SQLValueHandler.StringSQLValueHandler());
			jsonHandler(new JSONValueHandler.StringJSONValueHandler());
		}

		public ValueType type() {
			return ValueType._string;
		}
		
		public String toString() {
			return myValue;
		}
		
		public String toValueString() {
			return toString();
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (String) data;
			if(registerStateChange) {
				states().modify();
			}
		}


 }

 public class TableValue extends AbstractValue {
	 
	 private ValueTable myValue = new ValueTable();

	 protected TableValue() {
			sqlValueHandler(new SQLValueHandler.TableSQLValueHandler());
			jsonHandler(new JSONValueHandler.TableJSONValueHandler());
		}
		
		protected TableValue(ValueTable value) {
			this.myValue = value;
			sqlValueHandler(new SQLValueHandler.TableSQLValueHandler());
			jsonHandler(new JSONValueHandler.TableJSONValueHandler());
		}

		public ValueType type() {
			return ValueType._table;
		}
		
		public String toString() {
			return toJson();
		}

		public String toValueString() {
			return toString();
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (ValueTable) data;
			if(registerStateChange) {
				states().modify();
			}
		}
 }

 public class TextValue extends AbstractValue {
	 
	 private String myValue;

	 protected TextValue() {
			sqlValueHandler(new SQLValueHandler.TextSQLValueHandler());
			jsonHandler(new JSONValueHandler.TextJSONValueHandler());
		}
		
		protected TextValue(String value) {
			this.myValue = value;
			sqlValueHandler(new SQLValueHandler.TextSQLValueHandler());
			jsonHandler(new JSONValueHandler.TextJSONValueHandler());
		}

		public ValueType type() {
			return ValueType._text;
		}
		
		public String toString() {
			return myValue;
		}
		
		public String toValueString() {
			return toString();
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (String) data;
			if(registerStateChange) {
				states().modify();
			}
		}
 }

	public class TimeValue extends AbstractValue {
		
		private DateTime myValue;
		
		protected TimeValue() {
			sqlValueHandler(new SQLValueHandler.DateTimeSQLValueHandler());
			jsonHandler(new JSONValueHandler.TimeJSONValueHandler());
		}
		
		protected TimeValue(DateTime value) {
			this.myValue = value;
			sqlValueHandler(new SQLValueHandler.DateTimeSQLValueHandler());
			jsonHandler(new JSONValueHandler.TimeJSONValueHandler());
		}

		public ValueType type() {
			return ValueType._time;
		}
		
		public String toString() {
			return Util.Time.getTime(myValue);
		}
		
		public String toValueString() {
			return myValue.toString();
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (DateTime) data;
			if(registerStateChange) {
				states().modify();
			}
		}
	}
	public class TimestampValue extends AbstractValue {
		
		private Timestamp myValue;
		
		protected TimestampValue() {
			sqlValueHandler(new SQLValueHandler.TimestampSQLValueHandler());
			jsonHandler(new JSONValueHandler.TimestampJSONValueHandler());
		}
		
		protected TimestampValue(Timestamp value) {
			this.myValue = value;
			sqlValueHandler(new SQLValueHandler.TimestampSQLValueHandler());
			jsonHandler(new JSONValueHandler.TimestampJSONValueHandler());
		}

		public ValueType type() {
			return ValueType._timestamp;
		}
		
		public String toString() {
			return myValue.toString();
		}
		
		public String toValueString() {
			return toString();
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (Timestamp) data;
			if(registerStateChange) {
				states().modify();
			}
		}
	}
	
}
