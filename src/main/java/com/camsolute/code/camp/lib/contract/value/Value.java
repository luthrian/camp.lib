package com.camsolute.code.camp.lib.contract.value;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasGroup;
import com.camsolute.code.camp.lib.contract.core.HasId;
import com.camsolute.code.camp.lib.contract.core.HasValueParent;
import com.camsolute.code.camp.lib.contract.core.HasStates;
import com.camsolute.code.camp.lib.contract.IsSelectable;
import com.camsolute.code.camp.lib.contract.core.Serialization;
import com.camsolute.code.camp.lib.contract.core.CampTable.ValueTable;
import com.camsolute.code.camp.lib.contract.core.HasCoordinate;
import com.camsolute.code.camp.lib.contract.core.Coordinate;
import com.camsolute.code.camp.lib.contract.core.CampException.AlreadyContainsElementException;
import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.CampException.TableDimensionsException;
import com.camsolute.code.camp.lib.contract.core.CampBinary.Binary;
import com.camsolute.code.camp.lib.contract.core.CampComplex.ValueComplex;
import com.camsolute.code.camp.lib.contract.core.CampComplex.ValueListComplex;
import com.camsolute.code.camp.lib.contract.core.CampList.ValueList;
import com.camsolute.code.camp.lib.contract.core.CampStates;
import com.camsolute.code.camp.lib.contract.core.CampStates.CampStatesImpl;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.utilities.Util;

public interface Value<T,Q extends Value<T,Q>> extends HasId, HasValueParent, HasStates, HasGroup, HasCoordinate, IsSelectable, Serialization<Value<?,?>>, HasJSONValueHandler<T,Q>, HasSQLValueHandler<T,Q>, HasRestValueHandler<T,Q>{

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
		,_binary
	}

	public void updateValue(Q newValue,boolean registerStateChange) throws ValueTypeMismatchException;
	
	public void updateData(Object data, boolean registerStateChange) throws DataMismatchException;
	
	public ValueType type();
	
	public String toString();
		
	public String toValueString();
	
	public static class ValueFactory {
		public static Value<?,?> generateValue(ValueType type) { 
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
			case _binary:
				return new BinaryValue();
			default://never reached
				break;
			}
			return null;
		}
		public static Value<?,?> generateValue(ValueType type,Object data) {
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
			case _binary:
				return new BinaryValue((Binary)data);
			default://never reached
				break;
			}
			return null;
		}
		
	}
	
	public abstract class AbstractValue<T,Q extends Value<T,Q>> implements Value<T,Q> {
		
		private String id = HasId.newId();
	
		private Group group = new Group(Util.Config.instance().defaultGroup("Value"));
		
		private Coordinate position;

		private boolean selected = false;

		private CampStates states = new CampStatesImpl();

		private String parentId = "";
		
		private Value<?,?> parent = null;
		
//		protected SQLValueHandler<T,Q> sqlHandler;
		protected ValuePersistHandler<T,Q> sqlHandler;

		protected JSONValueHandler<T,Q>  jsonHandler;

		protected RestValueHandler<T,Q> restHandler;
		
 		public String id() {
			return this.id;
		}
		
		public String updateId(String id) {
			String r = this.id;
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
		
		public void updateValue(Q newValue, boolean registerStateChange) throws ValueTypeMismatchException {
			if(!newValue.type().name().equals(this.type().name())) {
				throw new ValueTypeMismatchException("Expected ValueType("+this.type().name()+") but new Value had ValueType("+newValue.type().name()+")");
			}
			this.id = newValue.id();
			this.group = newValue.group();
			this.states.update(newValue.states());
			this.selected = newValue.selected();
			this.position = newValue.position();
			this.jsonHandler = newValue.jsonHandler();
			this.sqlHandler = newValue.sqlHandler();
			if(registerStateChange) {
				this.states.modify();
			}
		}
		
		public String parentId() {
			return parentId;
		}
		
		public void parentId(String id, boolean registerUpdate) {
			parentId = id;
			if(registerUpdate) {
				states().modify();
			}
		}
		
		@SuppressWarnings("unchecked")
		public <X,Y extends Value<X,Y>> Y parent() {
			return (Y) parent;
		}
		
		public <X,Y extends Value<X,Y>> void parent(Y parent) {
			this.parent = parent;
			parentId(parent.id(), true);
		}
		
 		public <S extends ValuePersistHandler<T,Q>> void sqlHandler(S sqlValueHandler) {
			this.sqlHandler = sqlValueHandler;
		}
		@SuppressWarnings("unchecked")
		public <S extends ValuePersistHandler<T,Q>> S sqlHandler() {
			return (S) this.sqlHandler;
		}
		
		public <R extends JSONValueHandler<T,Q>> void jsonHandler(R jsonHandler) {
			this.jsonHandler = jsonHandler;
		}
		@SuppressWarnings("unchecked")
		public <R extends JSONValueHandler<T,Q>> R jsonHandler() {
			return (R) this.jsonHandler;
		}

		public <U extends RestValueHandler<T,Q>> void restHandler(U restValueHandler) {
			restHandler = restValueHandler;
		}
		
		@SuppressWarnings("unchecked")
		public <U extends RestValueHandler<T,Q>> U restHandler() {
			return (U) restHandler;
		}
		
 		public Q fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public Q fromJSONObject(JSONObject jo) throws DataMismatchException {
			return jsonHandler().fromJSONObject(jo);
		}
	}
	
	public class BooleanValue extends AbstractValue<Boolean,BooleanValue> {
		
		private boolean myValue = false;
		
		public Boolean data() { return myValue; }
		
		protected BooleanValue() {
			sqlHandler(new ValuePersistHandler.BooleanSQLValueHandler());
			jsonHandler(new JSONValueHandler.BooleanJSONValueHandler());
		}
		
		protected BooleanValue(Boolean value) {
			this.myValue = value;
			sqlHandler(new ValuePersistHandler.BooleanSQLValueHandler());
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
			myValue = (Boolean)data;
			if(registerStateChange) {
				states().modify();
			}
		}

		public String toJson() {
			return jsonHandler().toJson(this);
		}
		
	}

	public class ComplexValue extends AbstractValue<ValueListComplex,ComplexValue> {
		
		ValueListComplex myValue = new ValueListComplex();
		
		public ValueListComplex data() { return myValue; }
	
		protected ComplexValue() {
			sqlHandler(new ValuePersistHandler.ComplexSQLValueHandler());
			jsonHandler(new JSONValueHandler.ComplexJSONValueHandler());
		}
		
		protected ComplexValue(ValueListComplex value) {
			this.myValue = value;
			sqlHandler(new ValuePersistHandler.ComplexSQLValueHandler());
			jsonHandler(new JSONValueHandler.ComplexJSONValueHandler());
		}

		public ValueType type() {
			return ValueType._complex;
		}
		
		public String toString() {
			return toJson();
		}

		public String toValueString() {
			return toString();
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (ValueListComplex) data;
			if(registerStateChange) {
				states().modify();
			}
		}
		
		public String toJson() {
			return jsonHandler().toJson(this);
		}
		
		public ValueList dataToValueList() {
			ValueList vl = new ValueList();
			for(String key: myValue.keySet()) {
				for(Value<?,?> v: myValue.get(key)) {
					vl.add(v);
				}
			}
			return vl;
		}

		public void add(String group, Value<?,?> value) {
			if(!myValue.containsKey(group)) {
				myValue.put(group,new ValueList());
			}
			myValue.get(group).add(value);
		}
		
		public void add(Group group, Value<?,?> value) {
			add(group.name(),value);
		}

		public void add(String group, ValueList list) {
			if(!myValue.containsKey(group)) {
				myValue.put(group,new ValueList());
			}
			myValue.get(group).addAll(list);
		}
		
		public void add(Group group, ValueList list) {
			add(group.name(),list);
		}

		public ValueList get(String group) {
			return myValue.get(group);
		}
		
		public ValueList get(Group group) {
			return get(group.name());
		}
		
		public boolean contains(String group) {
			return myValue.containsKey(group);
		}
		
		public boolean contains(Group group) {
			return contains(group.name());
		}
	}
	
	public class DateValue extends AbstractValue<DateTime,DateValue> {
		
		private DateTime myValue;
		
		public DateTime data() { return myValue; }
		
		protected DateValue() {
			sqlHandler(new ValuePersistHandler.DateSQLValueHandler());
			jsonHandler(new JSONValueHandler.DateJSONValueHandler());
		}
		
		protected DateValue(DateTime value) {
			this.myValue = value;
			sqlHandler(new ValuePersistHandler.DateSQLValueHandler());
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

		public String toJson() {
			return jsonHandler().toJson(this);
		}
		
	}
	
	public class DateTimeValue extends AbstractValue<DateTime,DateTimeValue> {
		
		private DateTime myValue;
		
		public DateTime data() { return myValue; }
		
		protected DateTimeValue() {
			sqlHandler(new ValuePersistHandler.DateTimeSQLValueHandler());
			jsonHandler(new JSONValueHandler.DateTimeJSONValueHandler());
		}
		
		protected DateTimeValue(DateTime value) {
			this.myValue = value;
			sqlHandler(new ValuePersistHandler.DateTimeSQLValueHandler());
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

		public String toJson() {
			return jsonHandler().toJson(this);
		}
		

	}
	
 public class EnumValue extends AbstractValue<String,EnumValue> {
	 
	 private String myValue;
	 
	 public String data() { return myValue; }

	 protected EnumValue() {
			sqlHandler(new ValuePersistHandler.EnumSQLValueHandler());
			jsonHandler(new JSONValueHandler.EnumJSONValueHandler());
		}
		
		protected EnumValue(String value) {
			this.myValue = value;
			sqlHandler(new ValuePersistHandler.EnumSQLValueHandler());
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

		public String toJson() {
			return jsonHandler().toJson(this);
		}
		

 }

 public class IntegerValue extends AbstractValue<Integer,IntegerValue> {
	 
	 private int myValue;
	 
	 public Integer data() { return myValue; }

	 protected IntegerValue() {
			sqlHandler(new ValuePersistHandler.IntegerSQLValueHandler());
			jsonHandler(new JSONValueHandler.IntegerJSONValueHandler());
		}
		
		protected IntegerValue(int value) {
			this.myValue = value;
			sqlHandler(new ValuePersistHandler.IntegerSQLValueHandler());
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

		public String toJson() {
			return jsonHandler().toJson(this);
		}
		

 }

 public class ListValue extends AbstractValue<ValueList,ListValue> {
	 
	 private ValueList myValue = new ValueList();
	 
	 public ValueList data() { return myValue; }

	 protected ListValue() {
			sqlHandler(new ValuePersistHandler.ListSQLValueHandler());
			jsonHandler(new JSONValueHandler.ListJSONValueHandler());
		}
		
		protected ListValue(ValueList value) {
			this.myValue = value;
			sqlHandler(new ValuePersistHandler.ListSQLValueHandler());
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
			myValue = (ValueList)data;
			if(registerStateChange) {
				states().modify();
			}
		}

		public String toJson() {
			return jsonHandler().toJson(this);
		}
		
		public ValueList dataToValueList() {
			return myValue;
		}
 
		public int size() {
			return myValue.size();
		}
		
		public int selectionValueIndex() {
			return myValue.selectionIndex();
		}
		
		public Value<?,?> selectedValue() {
			return myValue.selected();
		}
		
		public int selectValue(String valueId) {
			return myValue.select(valueId);
		}
		
		public int selectValue(Value<?,?> value) {
			return myValue.select(value);
		}
		public Value<?,?> remove(int index) {
			return myValue.remove(index);
		}
		public void add(Value<?,?> value) {
			myValue.add(value);
		}
					
 }

 public class MapValue extends AbstractValue<ValueComplex,MapValue> {
	 
	 private ValueComplex myValue = new ValueComplex();
	 
	 public ValueComplex data() { return myValue; }

	 protected MapValue() {
			sqlHandler(new ValuePersistHandler.MapSQLValueHandler());
			jsonHandler(new JSONValueHandler.MapJSONValueHandler());
		}
		
		protected MapValue(ValueComplex value) {
			this.myValue = value;
			sqlHandler(new ValuePersistHandler.MapSQLValueHandler());
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
			myValue =  (ValueComplex) data;
		}

		public String toJson() {
			return jsonHandler().toJson(this);
		}
		
		public ValueList dataToValueList() {
			ValueList vl = new ValueList();
			for(String key: myValue.keySet()) {
				vl.add(myValue.get(key));
			}
			return vl;
		}

		public void add(String group, Value<?,?> value) throws AlreadyContainsElementException {
			if(!myValue.containsKey(group)) {
				myValue.put(group,value);
			}else {
				throw new AlreadyContainsElementException("Value already present in MapValue!!!");
			}
		}
		
		public void add(Group group, Value<?,?> value) throws AlreadyContainsElementException{
			add(group.name(),value);
		}

		public void set(String group, Value<?,?> value) {
			myValue.put(group,value);
		}
		
		public void set(Group group, Value<?,?> value) {
			set(group.name(),value);
		}

		public Value<?,?> get(String group) {
			return myValue.get(group);
		}
		
		public Value<?,?> get(Group group) {
			return get(group.name());
		}
		
		public boolean contains(String group) {
			return myValue.containsKey(group);
		}
		
		public boolean contains(Group group) {
			return contains(group.name());
		}

 }

 public class SetValue extends AbstractValue<String,SetValue> {
	 
	 private String myValue;
	 
	 public String data() { return myValue; }

	 protected SetValue() {
			sqlHandler(new ValuePersistHandler.SetSQLValueHandler());
			jsonHandler(new JSONValueHandler.SetJSONValueHandler());
		}
		
		protected SetValue(String value) {
			this.myValue = value;
			sqlHandler(new ValuePersistHandler.SetSQLValueHandler());
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
		
		public String toJson() {
			return jsonHandler().toJson(this);
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (String) data;
			if(registerStateChange) {
				states().modify();
			}
		}

 }

 public class StringValue extends AbstractValue<String,StringValue> {
	 
	 private String myValue;
	 
	 public String data() { return myValue; }

	 protected StringValue() {
			sqlHandler(new ValuePersistHandler.StringSQLValueHandler());
			jsonHandler(new JSONValueHandler.StringJSONValueHandler());
		}
		
		protected StringValue(String value) {
			this.myValue = value;
			sqlHandler(new ValuePersistHandler.StringSQLValueHandler());
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
		
		public String toJson() {
			return jsonHandler().toJson(this);
		}
		
		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (String) data;
			if(registerStateChange) {
				states().modify();
			}
		}


 }

 public class TableValue extends AbstractValue<ValueTable,TableValue> {
	 
	 private ValueTable myValue = new ValueTable();
	 
	 public ValueTable data() { return myValue; }

	 protected TableValue() {
			sqlHandler(new ValuePersistHandler.TableSQLValueHandler());
			jsonHandler(new JSONValueHandler.TableJSONValueHandler());
		}
		
		protected TableValue(ValueTable value) {
			this.myValue = value;
			sqlHandler(new ValuePersistHandler.TableSQLValueHandler());
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
			this.myValue = (ValueTable) data;
			if(registerStateChange) {
				states().modify();
			}
		}
		
		public String toJson() {
			return jsonHandler().toJson(this);
		}
		
		public ValueList dataToValueList() {
			ValueList vl = new ValueList();
			for(ArrayList<Value<?,?>> l: myValue.tableColumns()) {
				vl.addAll(l);
			}
			return vl;
		}

		public void addColumn(ArrayList<Value<?,?>> column) throws TableDimensionsException {
			myValue.addColumn(column);
		}
		
		public void addRow(ArrayList<Value<?,?>> row) throws TableDimensionsException {
			myValue.addRow(row);
		}
		
		public void set(Value<?,?> element) throws TableDimensionsException {
			myValue.setCell(element.position(), element);
		}
		
		public void set(Value<?,?> element,Coordinate coord) throws TableDimensionsException {
			myValue.setCell(coord, element);
		}
		
		public void set(Value<?,?> element, int row, int column) throws TableDimensionsException {
			myValue.setCell(row,column,element);
		}
		
		public Value<?,?> get(Coordinate coord) throws TableDimensionsException {
			return myValue.cell(coord);
		}
		
		public Value<?,?> get(int row, int column) throws TableDimensionsException {
			return myValue.cell(row, column);
		}
		
		public ArrayList<Value<?,?>> getRow(int index) {
			return myValue.row(index);
		}
		
		public ArrayList<Value<?,?>> getColumn(int index) {
			return myValue.column(index);
		}
		
		public void insertRow(ArrayList<Value<?,?>> row, int index, boolean after) throws TableDimensionsException {
			myValue.insertRow(index, after, row);
		}
		
		public void insertColumn(ArrayList<Value<?,?>> column, int index, boolean after) throws TableDimensionsException {
			myValue.insertColumn(index, after, column);
		}
		
		public ArrayList<ArrayList<Value<?,?>>> columns() {
			return myValue.tableColumns();
		}
		
		public ArrayList<ArrayList<Value<?,?>>> rows() {
			return myValue.tableRows();
		}
		
		public void organize(ValueList valueList) {
			myValue.organize(valueList.toArrayList());
		}
 }

 public class TextValue extends AbstractValue<String,TextValue> {
	 
	 private String myValue;
	 
	 public String data() { return myValue; }

	 protected TextValue() {
			sqlHandler(new ValuePersistHandler.TextSQLValueHandler());
			jsonHandler(new JSONValueHandler.TextJSONValueHandler());
		}
		
		protected TextValue(String value) {
			this.myValue = value;
			sqlHandler(new ValuePersistHandler.TextSQLValueHandler());
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
			myValue = (String)data;
			if(registerStateChange) {
				states().modify();
			}
			
		}
		
		public String toJson() {
			return jsonHandler().toJson(this);
		}
		
 }

	public class TimeValue extends AbstractValue<DateTime,TimeValue> {
		
		private DateTime myValue;
		
		public DateTime data() { return myValue; }
		
		protected TimeValue() {
			sqlHandler(new ValuePersistHandler.TimeSQLValueHandler());
			jsonHandler(new JSONValueHandler.TimeJSONValueHandler());
		}
		
		protected TimeValue(DateTime value) {
			this.myValue = value;
			sqlHandler(new ValuePersistHandler.TimeSQLValueHandler());
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
			myValue = (DateTime)data;
			if(registerStateChange) {
				states().modify();
			}
		}
		
		public String toJson() {
			return jsonHandler().toJson(this);
		}
		
	}

	public class TimestampValue extends AbstractValue<Timestamp,TimestampValue> {
		
		private Timestamp myValue;
		
		public Timestamp data() { return myValue; }
		
		protected TimestampValue() {
			sqlHandler(new ValuePersistHandler.TimestampSQLValueHandler());
			jsonHandler(new JSONValueHandler.TimestampJSONValueHandler());
		}
		
		protected TimestampValue(Timestamp value) {
			this.myValue = value;
			sqlHandler(new ValuePersistHandler.TimestampSQLValueHandler());
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
			myValue = (Timestamp)data;
			if(registerStateChange) {
				states().modify();
			}
		}
		public String toJson() {
			return jsonHandler().toJson(this);
		}
	
	}
	
	public class BinaryValue extends AbstractValue<Binary,BinaryValue> {
		
		private Binary myValue;
		
		public Binary data() { return myValue; }
		
		protected BinaryValue() {
			sqlHandler(new ValuePersistHandler.BinarySQLValueHandler());
			jsonHandler(new JSONValueHandler.BinaryJSONValueHandler());
		}
		
		protected BinaryValue(Binary value) {
			this.myValue = value;
			sqlHandler(new ValuePersistHandler.BinarySQLValueHandler());
			jsonHandler(new JSONValueHandler.BinaryJSONValueHandler());
		}
		public ValueType type() {
			return  ValueType._binary;
		}

		public String toString() {
			return myValue.toString();
		}

		public String toValueString() {
			return toString();
		}
		
		public byte[] valueAsBytes() {
			return myValue.data();
		}
 		public void updateData(Object data, boolean registerStateChange) throws DataMismatchException {
			myValue = (Binary)data;
			if(registerStateChange) {
				states().modify();
			}
		}

		public String toJson() {
			return jsonHandler().toJson(this);
		}
		
	}


}
