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
package com.camsolute.code.camp.lib.contract.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import com.camsolute.code.camp.lib.contract.core.CampStates;
import com.camsolute.code.camp.lib.contract.core.CampStates.CampStatesImpl;
import com.camsolute.code.camp.lib.contract.core.CampStates.IOAction;
import com.camsolute.code.camp.lib.contract.core.Coordinate;
import com.camsolute.code.camp.lib.contract.core.Coordinate.CoordinateImpl;
import com.camsolute.code.camp.lib.data.CampSQL;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.AttributeDao;
//import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.ValueInterface;
import com.camsolute.code.camp.lib.models.ValueList;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;
//import com.camsolute.code.camp.lib.models.CampStatesInterface.IOAction;
import com.camsolute.code.camp.lib.types.*;
import com.camsolute.code.camp.lib.utilities.Util;

public class Value<T,Q extends Value<T,Q>> implements ValueInterface<T,Q> {

	private int id = 0;

	private T value;

	private AttributeType type;

	private Group group;

	private Coordinate position;

	private boolean selected = false;
	
	private CampStates states = new CampStatesImpl();
	
	public int id() {
		return this.id;
	}

	public void id(int id) {
		this.id = id;
	}

    public Value(AttributeType type) {
        this.type = type;
        this.position = new CoordinateImpl();
    }
	public Value(AttributeType type, T value) {
		this.value = value;
		this.type = type;
		this.position = new CoordinateImpl();
	}

	public Value(AttributeType type, T value, Coordinate position) {
		this.value = value;
		this.type = type;
		this.position = position;
	}

	public Value(AttributeType type, T value, int x, int y, int z) {
		this.value = value;
		this.type = type;
		this.position = new CoordinateImpl(x, y, z);
	}

	public Value(AttributeType type, T value, String group, int x, int y, int z) {
		this.value = value;
		this.type = type;
		this.group = new Group(group);
		this.position = new CoordinateImpl(x, y, z);
	}

	public Value(AttributeType type, T value, String group, int x, int y, int z, boolean selected) {
		this.value = value;
		this.type = type;
		this.group = new Group(group);
		this.position = new CoordinateImpl(x, y, z);
	}

	public Value(int id, AttributeType type, T value, String group, int x, int y, int z) {
		this.id = id;
		this.value = value;
		this.type = type;
		this.group = new Group(group);
		this.position = new CoordinateImpl(x, y, z);
	}

	public Value(int id, AttributeType type, T value, String group, int x, int y, int z, boolean selected) {
		this.id = id;
		this.value = value;
		this.type = type;
		this.group = new Group(group);
		this.position = new CoordinateImpl(x, y, z);
	}

	public Value(int id, AttributeType type, T value, String group, Coordinate position) {
		this.id = id;
		this.value = value;
		this.type = type;
		this.group = new Group(group);
		this.position = position;
	}

	public Value(int id, AttributeType type, T value, String group, Coordinate position, boolean selected) {
		this.id = id;
		this.value = value;
		this.type = type;
		this.group = new Group(group);
		this.position = position;
		this.selected = selected;
	}

	public T value() {
		return this.value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public T updateValue(T newValue) {
		T prev = this.value;
		this.value = newValue;
		this.states.modify();
		return prev;
	}

	public AttributeType type() {
		return this.type;
	}

	public void type(AttributeType type) {
		this.type = type;
	}

	@Override
	public Group group() {
		return this.group;
	}

	@Override
	public void updateGroup(Group group) {
		this.group = group;
		this.states.modify();
	}

	@Override
	public void updateGroup(String group) {
		this.group = new Group(group);
		this.states.modify();
	}

	@Override
	public void setGroup(String group) {
		this.group = new Group(group);
	}

	@Override
	public int updateId(int id) {
		return this.id = id;
	}

	@Override
	public CampStates states() {
		return this.states;
	}


	public Coordinate position() {
		return this.position;
	}

	@Override
	public boolean selected() {
		return this.selected;
	}
	
	@Override
	public void select() {
		this.selected = true;
	}
	
	@Override
	public void deselect() {
		this.selected = false;
	}
	
	@Override
	public void setSelected(boolean b) {
		this.selected = b;
	}
	
	public String toString() {
		return ValueInterface._toJson(this);
	}

	public Value<?,?> fromString(String jsonString) {
		return ValueInterface._fromJson(jsonString);
	}

	public String toJson() {
		return ValueInterface._toJson(this);
	}

	public Value<T,Q> fromJson(String json) {
		return (Value<T, Q>) ValueInterface._fromJson(json);
	}
	
 	public static class ValueDao {
		private static final Logger LOG = LogManager.getLogger(Value.ValueDao.class);
		private static String fmt = Util.Text.LOG_FORMAT;

		public static String integertable = CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX,
				CampSQL.System._INTEGER_VALUE_INDEX);
		public static String[][] integertabledef = CampSQL.System._integer_value_table_definition;

		public static String stringtable = CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX,
				CampSQL.System._STRING_VALUE_INDEX);
		public static String[][] stringtabledef = CampSQL.System._string_value_table_definition;

		public static String timestamptable = CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX,
				CampSQL.System._TIMESTAMP_VALUE_INDEX);
		public static String[][] timestamptabledef = CampSQL.System._timestamp_value_table_definition;

		public static String booleantable = CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX,
				CampSQL.System._BOOLEAN_VALUE_INDEX);
		public static String[][] booleantabledef = CampSQL.System._boolean_value_table_definition;

		public static String texttable = CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX,
				CampSQL.System._TEXT_VALUE_INDEX);
		public static String[][] texttabledef = CampSQL.System._text_value_table_definition;

		public static String complextable = CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX,
				CampSQL.System._COMPLEX_VALUE_INDEX);
		public static String[][] complextabledef = CampSQL.System._complex_value_table_definition;

		public static String blobtable = CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX,
				CampSQL.System._BLOB_VALUE_INDEX);
		public static String[][] blobtabledef = CampSQL.System._blob_value_table_definition;

		@SuppressWarnings("unchecked")
		public static <T extends Value<?,?>> T create(int objectId, AttributeType type, String valueGroup, Object value,
				int posX, int posY, int posZ, boolean selected, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[create]";
				msg = "====[ persist specific attribute value to database ]====";
				LOG.info(String.format(fmt, _f, msg));
			}

		try {	
			switch (type) {
			case _integer:
				return (T) save(objectId,new IntegerValue((Integer) value, valueGroup, posX, posY, posZ, selected),log);
			case _string:
				return (T) save(objectId,new StringValue((String) value, valueGroup, posX, posY, posZ, selected),log);
			case _text:
				return (T) save(objectId,new TextValue((String) value, valueGroup, posX, posY, posZ, selected),log);
			case _boolean:
				return (T) save(objectId,new BooleanValue((Boolean) value, valueGroup, posX, posY, posZ, selected),log);
			case _timestamp:
				return (T) save(objectId,new TimestampValue((Timestamp) value, valueGroup, posX, posY, posZ, selected),log);
			case _datetime:
				return (T) save(objectId,new DateTimeValue((DateTime) value, valueGroup, posX, posY, posZ, selected),log);
			case _date:
				return (T) save(objectId,new DateValue((DateTime) value, valueGroup, posX, posY, posZ, selected),log);
			case _time:
				return (T) save(objectId,new TimeValue((DateTime) value, valueGroup, posX, posY, posZ, selected),log);
			case _enum:
				return (T) save(objectId,new EnumValue((String) value, valueGroup, posX, posY, posZ, selected),log);
			case _set:
				return (T) save(objectId,new SetValue((String) value, valueGroup, posX, posY, posZ, selected),log);
			case _complex:
				return (T) save(objectId,new ComplexValue((HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>>) value, valueGroup, posX, posY, posZ, selected),log);
			case _table:
				return (T) save(objectId,new TableValue((ArrayList<ArrayList<Attribute<? extends Value<?,?>>>>) value, valueGroup, posX, posY, posZ, selected),log);
			case _map:
				return (T) save(objectId,new MapValue((HashMap<String,Attribute<? extends Value<?,?>>>) value, valueGroup, posX, posY, posZ, selected),log);
			case _list:
				return (T) save(objectId,new ListValue((ArrayList<Attribute<? extends Value<?,?>>>) value, valueGroup, posX, posY, posZ, selected),log);
			default:
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[ attribute value created.]====";
				LOG.info(String.format(fmt, _f, msg + time));
			}
		}
			return null;
		}
		
		public static Value<?,?> save(int objectId, Value<?,?> v, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[save]";
				msg = "====[ persist specific attribute value to database ]====";
				LOG.info(String.format(fmt, _f, msg));
			}

			int retVal = 0;
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;

			try {
				// get a connection TODO: need to setup connection pool
				conn = Util.DB.__conn(log);

				String SQL = "INSERT INTO " + CampSQL.System.attribute_value_tables.get(v.type())
					         + "(" + Util.DB._columns(CampSQL.System.attribute_value_table_def.get(v.type()), Util.DB.dbActionType.INSERT, log) + ")" 
					         + " VALUES " + "( " + insertValues(objectId, v.type(), v,log) + " )";
//					         + " VALUES " + "( " + insertValues(objectId, parentId, v.type(), v,log) + " )";

				dbs = conn.createStatement();

				retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);

				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ '" + retVal + "' entr" + ((retVal != 1) ? "ies" : "y") + " saved to database ]----";
					LOG.info(String.format(fmt, _f, msg));
				}

				rs = dbs.getGeneratedKeys();

				if (rs.next()) {
					v.id(rs.getInt("_value_id_"));
				} else {
					if (log && !Util._IN_PRODUCTION) {
						msg = "EXCEPTION! Did not receive an technical id from database!";
						LOG.info(String.format(fmt, _f, msg));
					}
				}
				v.states().ioAction(IOAction.SAVE);
			} catch (Exception e) {
				if (log && !Util._IN_PRODUCTION) {
					msg = "-- --[ EXCEPTION! Save failed.]-- --";
					LOG.info(String.format(fmt, _f, msg));
				}
				e.printStackTrace();
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = " -- -- [ Releasing Connection ]";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
				Util.DB._releaseRS(rs, log);
			}
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[ save completed.]====";
				LOG.info(String.format(fmt, _f, msg + time));
			}
			return v;
		}

		public static ValueList saveList(int objectId, ValueList vl, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[saveList]";
				msg = "====[ persist a list of specific attribute values to the database ]====";
				LOG.info(String.format(fmt, _f, msg));
			}

			int retVal = 0;
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;
			try {
				// get a connection TODO: need to setup connection pool
				conn = Util.DB.__conn(log);
				String iSQL = "INSERT INTO " + integertable
			       + " (" + Util.DB._columns(CampSQL.System.attribute_value_table_def.get(AttributeType._integer), Util.DB.dbActionType.INSERT, log) + ")" + " VALUES "
				   ;
				String bSQL = "INSERT INTO " + booleantable
					       + " (" + Util.DB._columns(CampSQL.System.attribute_value_table_def.get(AttributeType._integer), Util.DB.dbActionType.INSERT, log) + ")" + " VALUES "
						   ;
				String sSQL = "INSERT INTO " + stringtable
					       + " (" + Util.DB._columns(CampSQL.System.attribute_value_table_def.get(AttributeType._integer), Util.DB.dbActionType.INSERT, log) + ")" + " VALUES "
						   ;
				String txSQL = "INSERT INTO " + texttable
					       + " (" + Util.DB._columns(CampSQL.System.attribute_value_table_def.get(AttributeType._integer), Util.DB.dbActionType.INSERT, log) + ")" + " VALUES "
						   ;
				String tSQL = "INSERT INTO " + timestamptable
					       + " (" + Util.DB._columns(CampSQL.System.attribute_value_table_def.get(AttributeType._integer), Util.DB.dbActionType.INSERT, log) + ")" + " VALUES "
						   ;
				String cSQL = "INSERT INTO " + complextable
					       + " (" + Util.DB._columns(CampSQL.System.attribute_value_table_def.get(AttributeType._integer), Util.DB.dbActionType.INSERT, log) + ")" + " VALUES "
						   ;
				String blSQL = "INSERT INTO " + blobtable
					       + " (" + Util.DB._columns(CampSQL.System.attribute_value_table_def.get(AttributeType._integer), Util.DB.dbActionType.INSERT, log) + ")" + " VALUES "
						   ;
			HashMap<String,String> dbpmap = new HashMap<String,String>();
				
				dbpmap.put(integertable,iSQL);
				dbpmap.put(booleantable,bSQL);
				dbpmap.put(stringtable,sSQL);
				dbpmap.put(texttable,txSQL);
				dbpmap.put(timestamptable,tSQL);
				dbpmap.put(complextable,cSQL);
				dbpmap.put(blobtable,blSQL);

				for(String table:dbpmap.keySet()) {
					boolean start = true;
					String sql = dbpmap.get(table);
					for (Value<?,?> v : vl) {
						if(CampSQL.System.attribute_value_tables.get(v.type()).equals(table)) {
							sql += ((start)?"":",")+ "("+insertValues(objectId, v.type(), v,log)+")";
							start =false;
						}
					}
					dbpmap.put(table, sql);
				}
				for(String table:dbpmap.keySet()) {
					String sql = dbpmap.get(table);
					if(!(sql.charAt(sql.length()-1) == ")".charAt(0))) { continue; }
					if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+sql+"]----";LOG.info(String.format(fmt,_f,msg));}
					dbs = conn.createStatement();
					retVal += dbs.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
					rs = dbs.getGeneratedKeys();
					for (Value<?,?> v : vl) {
						if(CampSQL.System.attribute_value_tables.get(v.type()).equals(table)) {
							if(rs.next()) {
								v.id(rs.getInt("_value_id_"));
								v.states().ioAction(IOAction.SAVE);
							}
						}
					}
				}

				if (log && !Util._IN_PRODUCTION) { msg = "'" + retVal + "' entr" + ((retVal != 1) ? "ies" : "y") + " saved to database"; LOG.info(String.format(fmt, _f, msg)); }

			} catch (Exception e) {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ EXCEPTION! Save failed.]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				e.printStackTrace();
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = " Releasing Connection ";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
				Util.DB._releaseRS(rs, log);
			}
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[ saveList completed.]====";
				LOG.info(String.format(fmt, _f, msg + time));
			}
			return vl;
		}

		public static int update(int objectId, Value<?,?> v, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[update]";
				msg = "====[ update the database entry of a specific attribute value ]====";
				LOG.info(String.format(fmt, _f, msg));
			}

			int retVal = 0;
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;

			try {
				// get a connection TODO: need to setup connection pool
				conn = Util.DB.__conn(log);

				String SQL = "UPDATE " + CampSQL.System.attribute_value_tables.get(v.type()) + " SET "
						+ Util.DB._columns(CampSQL.System.attribute_value_table_def.get(v.type()), Util.DB.dbActionType.UPDATE, log)
						+ " WHERE `" + CampSQL.System.attribute_value_table_def.get(v.type())[0][0] // table id field
						+ "`=%s";

				String eSQL = String.format(SQL, 
						objectId, 
						"'" + v.type().name() + "'", 
						"'" + v.group().name() + "'",
						v.selected(),
						v.position().posX(), 
						v.position().posY(), 
						v.position().posZ(),
						Value.ValueDao.valueForSQL(objectId,v,log), 
						v.id());

				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ SQL: " + eSQL + "]----";
					LOG.info(String.format(fmt, _f, msg));
				}

				dbs = conn.createStatement();

				retVal = dbs.executeUpdate(eSQL);

				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ '" + retVal + "' entr" + ((retVal != 1) ? "ies" : "y") + " updated in database ]----";
					LOG.info(String.format(fmt, _f, msg));
				}

				v.states().ioAction(IOAction.UPDATE);
			} catch (Exception e) {
				if (log && !Util._IN_PRODUCTION) {
					msg = "-- --[ EXCEPTION! Update failed.]-- --";
					LOG.info(String.format(fmt, _f, msg));
				}
				e.printStackTrace();
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = " -- -- [ Releasing Connection ]";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
				Util.DB._releaseRS(rs, log);
			}
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[ update completed.]====";
				LOG.info(String.format(fmt, _f, msg + time));
			}
			return retVal;
		}

		@SuppressWarnings("unchecked")
		public static int updateList(int objectId, ValueList vl, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[updateList]";
				msg = "====[ update a list of specific attribute values in the database ]====";
				LOG.info(String.format(fmt, _f, msg));
			}

			int retVal = 0;
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;
			
			try {
				// get a connection TODO: need to setup connection pool
				conn = Util.DB.__conn(log);
				//TODO: rework
				//all value_tables have the same field names so its ok to use just the integer table def here
				String SQL = "UPDATE %s SET "
						+ Util.DB._columns(CampSQL.System.attribute_value_table_def.get(AttributeType._integer), Util.DB.dbActionType.UPDATE,log)
						+" WHERE `"+CampSQL.System.attribute_value_table_def.get(AttributeType._integer)[0][0]+"`=%s";
				
				dbs = conn.createStatement();
				for (Value<?,?> v : vl) {
					String table = CampSQL.System.attribute_value_tables.get(v.type());
					String eSQL = String.format(SQL, 
							table,
							objectId, 
							"'" + v.type().name() + "'", 
							"'" + v.group().name() + "'",
							v.selected(),
							v.position().posX(), 
							v.position().posY(), 
							v.position().posZ(),
							valueForSQL(objectId,v,log), 
							v.id());

					if (log && !Util._IN_PRODUCTION) {
						msg = "SQL: " + eSQL + "";
						LOG.info(String.format(fmt, _f, msg));
					}
					dbs.addBatch(eSQL);
				}
				retVal += Util.Math.addArray(dbs.executeBatch());

				for(Value<?,?> v: vl){
					v.states().ioAction(IOAction.UPDATE);
				}
					
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ '" + retVal + "' entr" + ((retVal != 1) ? "ies" : "y") + " updated in database ]----";
					LOG.info(String.format(fmt, _f, msg));
				}

			} catch (Exception e) {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ EXCEPTION! Update failed.]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				e.printStackTrace();
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = " Releasing Connection ";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
				Util.DB._releaseRS(rs, log);
			}
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[ saveList completed.]====";
				LOG.info(String.format(fmt, _f, msg + time));
			}
			return retVal;
		}
		
		public static int delete(int valueId, AttributeType type, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[delete]";
				msg = "====[ delete a specific attribute value from the database ]====";
				LOG.info(String.format(fmt, _f, msg));
			}
			int retVal = 0;
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;

			try {
				// get a connection TODO: need to setup connection pool
				conn = Util.DB.__conn(log);
				dbs = conn.createStatement();

				String SQL = " DELETE FROM "+CampSQL.System.attribute_value_tables.get(type)+" WHERE `_value_id_`=" + valueId;

				retVal = dbs.executeUpdate(SQL);
				
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }

			} catch (Exception e) {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ EXCEPTION! Update failed.]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				e.printStackTrace();
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = " Releasing Connection ";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
				Util.DB._releaseRS(rs, log);
			}
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[ delete completed.]====";
				LOG.info(String.format(fmt, _f, msg + time));
			}
			return retVal;
			
		}

		public static int delete(Value<?,?> v, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[delete]";
				msg = "====[ delete a specific attribute value from the database ]====";
				LOG.info(String.format(fmt, _f, msg));
			}
			int retVal = 0;
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;

			try {
				// get a connection TODO: need to setup connection pool
				conn = Util.DB.__conn(log);
				dbs = conn.createStatement();

				String SQL = " DELETE FROM "+CampSQL.System.attribute_value_tables.get(v.type())+" WHERE `_value_id_`=" + v.id();

				retVal = dbs.executeUpdate(SQL);
				
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }

				v.states().ioAction(IOAction.DELETE);
			} catch (Exception e) {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ EXCEPTION! Update failed.]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				e.printStackTrace();
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = " Releasing Connection ";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
				Util.DB._releaseRS(rs, log);
			}
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[ delete completed.]====";
				LOG.info(String.format(fmt, _f, msg + time));
			}
			return retVal;
			
		}
		
		public static int deleteList(ValueList vl, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[deleteList]";
				msg = "====[ delete a list of specific attribute values from the database ]====";
				LOG.info(String.format(fmt, _f, msg));
			}
			int retVal = 0;
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;

			try {
				// get a connection TODO: need to setup connection pool
				conn = Util.DB.__conn(log);
				dbs = conn.createStatement();

				String fSQL = " DELETE FROM %s WHERE `_value_id_`=%s";

				for(Value<?,?> v: vl) {
					String SQL = String.format(fSQL, CampSQL.System.attribute_value_tables.get(v.type()), v.id());
					dbs.addBatch(SQL);
				}
				
				retVal = Util.Math.addArray(dbs.executeBatch());
				
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }

				for(Value<?,?> v:vl){
					v.states().ioAction(IOAction.DELETE);
				}
			} catch (Exception e) {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ EXCEPTION! delete failed.]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				e.printStackTrace();
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = " Releasing Connection ";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
				Util.DB._releaseRS(rs, log);
			}
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[ deleteList completed.]====";
				LOG.info(String.format(fmt, _f, msg + time));
			}
			return retVal;
			
		}

		public static int deleteList(int objectId, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[deleteList]";
				msg = "====[ delete a list of specific attribute values from the database that share a common object reference (objectId) ]====";
				LOG.info(String.format(fmt, _f, msg));
			}
			int retVal = 0;
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;

			try {
				// get a connection TODO: need to setup connection pool
				conn = Util.DB.__conn(log);
				dbs = conn.createStatement();

				String fSQL = " DELETE FROM %s WHERE `object_id_`="+objectId;
				
				String[] tl = { integertable, stringtable, booleantable, timestamptable, texttable, complextable, blobtable };

				for (String table : tl) {
					dbs.addBatch(String.format(fSQL, table));
				}

				retVal = Util.Math.addArray(dbs.executeBatch());
				
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }

			} catch (Exception e) {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ EXCEPTION! delete failed.]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				e.printStackTrace();
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = " Releasing Connection ";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
				Util.DB._releaseRS(rs, log);
			}
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[ deleteList completed.]====";
				LOG.info(String.format(fmt, _f, msg + time));
			}
			return retVal;
			
		}


		public static ValueList load(int objectId, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[load]";
				msg = "====[ persist a list of specific attribute values to the database ]====";
				LOG.info(String.format(fmt, _f, msg));
			}

			ValueList vl = new ValueList();
			int retVal = 0;
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;

			try {
				// get a connection TODO: need to setup connection pool
				conn = Util.DB.__conn(log);
				dbs = conn.createStatement();

				String SQL = " SELECT * FROM %s WHERE `object_id`=" + objectId;

				String[] tl = { integertable, stringtable, booleantable, timestamptable, texttable, complextable, blobtable };

				for (String table : tl) {
					rs = dbs.executeQuery(String.format(SQL, table));
					while (rs.next()) {
						vl.add(rsToV(rs, log));
					}
				}

				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ '" + vl.size() + "' entr" + ((vl.size() != 1) ? "ies" : "y")
							+ " loaded from the database ]----";
					LOG.info(String.format(fmt, _f, msg));
				}

				for(Value<?,?> v:vl){
					v.states().ioAction(IOAction.LOAD);
				}
			} catch (Exception e) {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ EXCEPTION! Update failed.]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				e.printStackTrace();
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = " Releasing Connection ";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
				Util.DB._releaseRS(rs, log);
			}
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[ load completed.]====";
				LOG.info(String.format(fmt, _f, msg + time));
			}
			return vl;
		}

		public static ValueList loadList(HashMap<Integer,String> ids, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[load]";
				msg = "====[ load a list of specific attribute values from the database by id ("+Util.Text.joinInt(ids.keySet(), ",")+") and db table name ]====";
				LOG.info(String.format(fmt, _f, msg));
			}

			ValueList vl = new ValueList();
			int retVal = 0;
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;
			try {
				// get a connection TODO: need to setup connection pool
				conn = Util.DB.__conn(log);

				String hSQL = " SELECT * FROM %s WHERE";
				String bSQL	= " `"+integertabledef[0][0]+"`=%s";

				dbs = conn.createStatement();
				
				String[] tl = { integertable, stringtable, booleantable, timestamptable, texttable, complextable, blobtable };
				String fhSQL = "";
				String fbSQL = "";
				for (String table : tl) {
					boolean load = false;
					fhSQL = String.format(hSQL, table);
					fbSQL = "";
					boolean start = true;
					for(int id: ids.keySet()) {
						if(table.equals(ids.get(id))) {
							if(!start) {
								fbSQL += " OR ";
							} else {
								start = false;
							}
							fbSQL += String.format(bSQL, id);
							load = true;
						}
					}
					String SQL = fhSQL + fbSQL;
					if(!fbSQL.isEmpty()) {
						if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
					}
					if(load) {
						rs = dbs.executeQuery(SQL);
						while (rs.next()) {
							vl.add(rsToV(rs, log));
						}
					}
				}

				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ '" + vl.size() + "' entr" + ((vl.size() != 1) ? "ies" : "y")
							+ " loaded from the database ]----";
					LOG.info(String.format(fmt, _f, msg));
				}

				for(Value<?,?> v:vl){
					v.states().ioAction(IOAction.LOAD);
				}
			} catch (Exception e) {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ EXCEPTION! Load failed.]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				e.printStackTrace();
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = " Releasing Connection ";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
				Util.DB._releaseRS(rs, log);
			}
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[ load completed.]====";
				LOG.info(String.format(fmt, _f, msg + time));
			}
			return vl;
		}

		public static Value<?,?> loadById(int attributeId, int objectId, int valueId, AttributeType type, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[load]";
				msg = "====[ load a specific attribute value from the database by ]====";
				LOG.info(String.format(fmt, _f, msg));
			}

			Value<?,?> v = null;
			int retVal = 0;
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;

			try {
				// get a connection TODO: need to setup connection pool
				conn = Util.DB.__conn(log);
				dbs = conn.createStatement();

				String SQL = " SELECT * FROM "+CampSQL.System.attribute_value_tables.get(type)+" WHERE `"+integertabledef[0][0]+"`=" + valueId;

				rs = dbs.executeQuery(SQL);
				if (rs.next()) {
					v = rsToV(attributeId, objectId, rs, log);
				}

				if(v != null) {
					retVal = 1;
				}
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				v.states().ioAction(IOAction.LOAD);
			} catch (Exception e) {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ EXCEPTION! Update failed.]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				e.printStackTrace();
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = " Releasing Connection ";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
				Util.DB._releaseRS(rs, log);
			}
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[ load completed.]====";
				LOG.info(String.format(fmt, _f, msg + time));
			}
			return v;
		}

		public static ComplexValue loadComplexValue(int attributeId, int objectId, int valueId, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[loadComplexValue]";
				msg = "====[ load a complex value and its child attribute elements ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
			ComplexValue c = new ComplexValue(new HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>>());
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;
			int retVal = 0;
			try{
				conn = Util.DB.__conn(log);
				
			  String SQL = "SELECT * FROM "+AttributeDao.valuetable+" AS tv" + ", "+AttributeDao.table+" AS td %s"
		    		+ " AND td._attribute_type_id_=tv.attribute_type_id"
		    		+ " AND tv.attribute_parent_id=%s"
		    		+ " AND tv.object_id=%s";
		    String iSQL = ", "+Value.ValueDao.integertable+" AS i WHERE i.object_id=tv.object_id AND i."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
		    String sSQL =  ", "+Value.ValueDao.stringtable+" AS s WHERE s.object_id=tv.object_id AND s."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String tSQL = ", "+Value.ValueDao.texttable+" AS t  WHERE t.object_id=tv.object_id AND t."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String tiSQL = ", "+Value.ValueDao.timestamptable+" AS ti  WHERE ti.object_id=tv.object_id AND ti."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String bSQL = ", "+Value.ValueDao.booleantable+" AS b  WHERE b.object_id=tv.object_id AND b."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String cSQL = ", "+Value.ValueDao.complextable+" AS c  WHERE c.object_id=tv.object_id AND c."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
		    String blSQL = ", "+Value.ValueDao.blobtable+" AS bl  WHERE bl.object_id=tv.object_id AND bl."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 

				String[] vSQL = {iSQL,sSQL,tSQL,tiSQL,bSQL,cSQL,blSQL};
				
				
				dbs = conn.createStatement();
				for(String f:vSQL) {
					String fSQL = String.format(SQL, f,attributeId,objectId);
					if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+fSQL+"]----";LOG.info(String.format(fmt,_f,msg));}
					rs = dbs.executeQuery(fSQL);		
					while(rs.next()) {
						Attribute<? extends Value<?,?>> a = AttributeDao._rsToA(rs);
						a = AttributeDao._rsToAV(a, rs);
						Value<?,?> v = Value.ValueDao.rsToV(rs, log);
						v.states().ioAction(IOAction.LOAD);
						a = AttributeDao.setValue(a, v);
						if(!c.value().containsKey(a.group().name())) {
							c.value().put(a.group().name(), new ArrayList<Attribute<? extends Value<?,?>>>());
						}
						c.value().get(a.group().name()).add(a);
						retVal++;
					}
				}
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' complex value entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				
		} catch(SQLException e) {
				if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
				e.printStackTrace();
				//throw e;
			} finally {
				if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
				Util.DB.__release(conn,log);
				Util.DB._releaseRS(rs, log);
				//Util.DB._releaseStatement(dbp, log);
				Util.DB._releaseStatement(dbs, log);
			}
			//return retVal;
			
			
			if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[loadComplexValue completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			
			return c;
		}
		
		public static TableValue loadTableValue(int attributeId, int objectId, int valueId, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[loadTableValue]";
				msg = "====[ load a table value child attribute elements ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
			TableValue t = new TableValue(new ArrayList<ArrayList<Attribute<? extends Value<?,?>>>>());
//			TODO: TableValue t = new TableValue(new ArrayList<CampList>());
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;
			int retVal = 0;
			try{
				conn = Util.DB.__conn(log);
				
			  String SQL = "SELECT * FROM "+AttributeDao.valuetable+" AS tv" + ", "+AttributeDao.table+" AS td %s"
		    		+ " AND td._attribute_type_id_=tv.attribute_type_id"
		    		+ " AND tv.attribute_parent_id=%s"
		    		+ " AND tv.object_id=%s";
		    String iSQL = ", "+Value.ValueDao.integertable+" AS i WHERE i.object_id=tv.object_id AND i."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
		    String sSQL =  ", "+Value.ValueDao.stringtable+" AS s WHERE s.object_id=tv.object_id AND s."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String tSQL = ", "+Value.ValueDao.texttable+" AS t  WHERE t.object_id=tv.object_id AND t."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String tiSQL = ", "+Value.ValueDao.timestamptable+" AS ti  WHERE ti.object_id=tv.object_id AND ti."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String bSQL = ", "+Value.ValueDao.booleantable+" AS b  WHERE b.object_id=tv.object_id AND b."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String cSQL = ", "+Value.ValueDao.complextable+" AS c  WHERE c.object_id=tv.object_id AND c."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
		    String blSQL = ", "+Value.ValueDao.blobtable+" AS bl  WHERE bl.object_id=tv.object_id AND bl."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 

				String[] vSQL = {iSQL,sSQL,tSQL,tiSQL,bSQL,cSQL,blSQL};
				
				
				dbs = conn.createStatement();
//				AttributeList al = new AttributeList();
				
				HashMap<Integer,HashMap<Integer,Attribute<? extends Value<?,?>>>> rowMap = new HashMap<Integer,HashMap<Integer,Attribute<? extends Value<?,?>>>>();
				for(String f:vSQL) {
					String fSQL = String.format(SQL, f,attributeId,objectId);
					if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+fSQL+"]----";LOG.info(String.format(fmt,_f,msg));}
					rs = dbs.executeQuery(fSQL);		
					while(rs.next()) {
						Attribute<? extends Value<?,?>> a = AttributeDao._rsToA(rs);
						a = AttributeDao._rsToAV(a, rs);
						Value<?,?> v = Value.ValueDao.rsToV(rs, log);
						v.states().ioAction(IOAction.LOAD);
						a = AttributeDao.setValue(a, v);
						if(!rowMap.containsKey(a.value().position().posY())) {
							rowMap.put(a.value().position().posY(), new HashMap<Integer,Attribute<? extends Value<?,?>>>());
						}
						rowMap.get(a.value().position().posY()).put(a.value().position().posX(), a);
//						al.add(a);
						retVal++;
					}
				}
				// assemble table matrix
				for(int y = 0;y<rowMap.size();y++ ) {
					for(int x = 0;x<rowMap.get(y).size();x++) {
						if(t.value().get(y)==null) {
							t.value().add(y, new ArrayList<Attribute<? extends Value<?,?>>>());
						}
						t.value().get(y).add(x,rowMap.get(y+1).get(x+1));
					}
				}
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' complex value entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				
		} catch(SQLException e) {
				if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
				e.printStackTrace();
				//throw e;
			} finally {
				if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
				Util.DB.__release(conn,log);
				Util.DB._releaseRS(rs, log);
				//Util.DB._releaseStatement(dbp, log);
				Util.DB._releaseStatement(dbs, log);
			}
			//return retVal;
			
			
			if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[loadTableValue completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			
			return t;
		}
				
		public static MapValue loadMapValue(int attributeId, int objectId, int valueId, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[loadComplexValue]";
				msg = "====[ load a map value with child attribute elements ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
			MapValue m = new MapValue(new HashMap<String,Attribute<? extends Value<?,?>>>());
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;
			int retVal = 0;
			try{
				conn = Util.DB.__conn(log);
				
			  String SQL = "SELECT * FROM "+AttributeDao.valuetable+" AS tv" + ", "+AttributeDao.table+" AS td %s"
		    		+ " AND td._attribute_type_id_=tv.attribute_type_id"
		    		+ " AND tv.attribute_parent_id=%s"
		    		+ " AND tv.object_id=%s";
		    String iSQL = ", "+Value.ValueDao.integertable+" AS i WHERE i.object_id=tv.object_id AND i."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
		    String sSQL =  ", "+Value.ValueDao.stringtable+" AS s WHERE s.object_id=tv.object_id AND s."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String tSQL = ", "+Value.ValueDao.texttable+" AS t  WHERE t.object_id=tv.object_id AND t."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String tiSQL = ", "+Value.ValueDao.timestamptable+" AS ti  WHERE ti.object_id=tv.object_id AND ti."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String bSQL = ", "+Value.ValueDao.booleantable+" AS b  WHERE b.object_id=tv.object_id AND b."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String cSQL = ", "+Value.ValueDao.complextable+" AS c  WHERE c.object_id=tv.object_id AND c."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
		    String blSQL = ", "+Value.ValueDao.blobtable+" AS bl  WHERE bl.object_id=tv.object_id AND bl."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 

				String[] vSQL = {iSQL,sSQL,tSQL,tiSQL,bSQL,cSQL,blSQL};
				
				
				dbs = conn.createStatement();
				for(String f:vSQL) {
					String fSQL = String.format(SQL, f,attributeId,objectId);
					if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+fSQL+"]----";LOG.info(String.format(fmt,_f,msg));}
					rs = dbs.executeQuery(fSQL);		
					while(rs.next()) {
						Attribute<? extends Value<?,?>> a = AttributeDao._rsToA(rs);
						a = AttributeDao._rsToAV(a, rs);
						Value<?,?> v = Value.ValueDao.rsToV(rs, log);
						v.states().ioAction(IOAction.LOAD);
						a = AttributeDao.setValue(a, v);
						m.value().put(a.group().name(),a);
						retVal++;
					}
				}
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' complex value entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				
		} catch(SQLException e) {
				if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
				e.printStackTrace();
				//throw e;
			} finally {
				if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
				Util.DB.__release(conn,log);
				Util.DB._releaseRS(rs, log);
				//Util.DB._releaseStatement(dbp, log);
				Util.DB._releaseStatement(dbs, log);
			}
			//return retVal;
			
			
			if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[loadComplexValue completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			
			return m;
		}
		
		public static ListValue loadListValue(int attributeId, int objectId, int valueId, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[loadListValue]";
				msg = "====[ load a list value child attribute elements ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
			ListValue l = new ListValue(new ArrayList<Attribute<? extends Value<?,?>>>());
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;
			int retVal = 0;
			try{
				conn = Util.DB.__conn(log);
				
			  String SQL = "SELECT * FROM "+AttributeDao.valuetable+" AS tv" + ", "+AttributeDao.table+" AS td %s"
		    		+ " AND td._attribute_type_id_=tv.attribute_type_id"
		    		+ " AND tv.attribute_parent_id=%s"
		    		+ " AND tv.object_id=%s";
		    String iSQL = ", "+Value.ValueDao.integertable+" AS i WHERE i.object_id=tv.object_id AND i."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
		    String sSQL =  ", "+Value.ValueDao.stringtable+" AS s WHERE s.object_id=tv.object_id AND s."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String tSQL = ", "+Value.ValueDao.texttable+" AS t  WHERE t.object_id=tv.object_id AND t."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String tiSQL = ", "+Value.ValueDao.timestamptable+" AS ti  WHERE ti.object_id=tv.object_id AND ti."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String bSQL = ", "+Value.ValueDao.booleantable+" AS b  WHERE b.object_id=tv.object_id AND b."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String cSQL = ", "+Value.ValueDao.complextable+" AS c  WHERE c.object_id=tv.object_id AND c."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
		    String blSQL = ", "+Value.ValueDao.blobtable+" AS bl  WHERE bl.object_id=tv.object_id AND bl."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 

				String[] vSQL = {iSQL,sSQL,tSQL,tiSQL,bSQL,cSQL,blSQL};
				
				
				dbs = conn.createStatement();
//				AttributeList al = new AttributeList();
				
				for(String f:vSQL) {
					String fSQL = String.format(SQL, f,attributeId,objectId);
					if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+fSQL+"]----";LOG.info(String.format(fmt,_f,msg));}
					rs = dbs.executeQuery(fSQL);		
					while(rs.next()) {
						Attribute<? extends Value<?,?>> a = AttributeDao._rsToA(rs);
						a = AttributeDao._rsToAV(a, rs);
						Value<?,?> v = Value.ValueDao.rsToV(rs, log);
						v.states().ioAction(IOAction.LOAD);
						a = AttributeDao.setValue(a, v);
						l.value().add(a);
						retVal++;
					}
				}
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' complex value entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				
		} catch(SQLException e) {
				if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
				//throw e;
			} finally {
				if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
				Util.DB.__release(conn,log);
				Util.DB._releaseRS(rs, log);
				//Util.DB._releaseStatement(dbp, log);
				Util.DB._releaseStatement(dbs, log);
			}
			//return retVal;
			
			
			if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[loadListValue completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			
			return l;
		}
				
		public static String saveMapValue(int objectId, int parentId, HashMap<String,Attribute<? extends Value<?,?>>> value,boolean log) {
			String idList = "NULL";
			boolean start = true;
			for(String group: value.keySet() ) {
				Attribute<? extends Value<?,?>> a = value.get(group);
				if(a.id()==0) {
					a.parentId(parentId);
//					AttributeDao.instance();
					a.updateId(AttributeDao._save(a,log).id());
				}
//AttributeDao.instance();
				//				Value<?,?> v = Value.Dao.save(objectId, a.id(), a.value(), log);
//				a.valueId(v.id());
//				a.value().id(v.id());
//				a = AttributeDao.instance()._save(objectId, a,log); 
				a = AttributeDao._save(objectId,a,log);
				if(!start) {
					idList += ",";
				} else {
					start = false;
					idList = "";
				}
				idList += a.value().id()+"|"+a.value().type().name();
			}
			return idList;
		}

		public static String saveListValue(int objectId, int parentId, ArrayList<Attribute<? extends Value<?,?>>> value,boolean log) {
			String idList = "NULL";
			boolean start = true;
			for(Attribute<? extends Value<?,?>> a: value ) {
				if(a.id()==0) {
					a.parentId(parentId);
					a = AttributeDao._save(a,log);
				}
//				Value<?,?> v = Value.Dao.save(objectId, a.id(), a.value(), log);
//				a.valueId(v.id());
//				a.value().id(v.id());
				a = AttributeDao._save(objectId, a,log); 
				if(!start) {
					idList += ",";
				} else {
					start = false;
					idList = "";
				}
				idList += a.value().id()+"|"+a.value().type().name();
			}
			return idList;
		}

		public static String saveTableValue(int objectId, int parentId, ArrayList<ArrayList<Attribute<? extends Value<?,?>>>> value,boolean log) {
			String idList = "NULL";
			boolean start = true;
			for(ArrayList<Attribute<? extends Value<?,?>>> al: value ) {
				for(Attribute<? extends Value<?,?>>a:al) {
					if(a.id()==0) {
						a.parentId(parentId);
						a.updateId(AttributeDao._save(a, log).id());
					}
//				Value<?,?> v = Value.Dao.save(objectId, a.id(), a.value(), log);
//				a.valueId(v.id());
//				a.value().id(v.id());
					a = AttributeDao._save(objectId,a, log);
					if(!start) {
						idList += ",";
					} else {
						start = false;
						idList = "";
					}
					idList += a.value().id()+"|"+a.value().type().name();
				}
			}
			return idList;
		}

		public static String saveComplexValue(int objectId, int parentId, HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>> value,boolean log) {
			String idList = "NULL";
			boolean start = true;
			for(String group: value.keySet() ) {
				ArrayList<Attribute<? extends Value<?,?>>> al = value.get(group);
				for(Attribute<? extends Value<?,?>>a:al) {
					if(a.id()==0) {
						a.parentId(parentId);
						a.updateId(AttributeDao._save(a, log).id());
					}
					a = AttributeDao._save(objectId,a, log);
					if(!start) {
						idList += ",";
					} else {
						start = false;
						idList = "";
					}
					idList += a.value().id()+"|"+a.value().type().name();
				}
			}
			return idList;
		}

		@SuppressWarnings("unchecked")
		public static <T extends Value<?,?>> String insertValues(int objectId, AttributeType type, T v,boolean log) {
			String values = "";
//			String idList = "";
			values += objectId + ",'" + type.name() + "','" + v.group().name() + "'" + "," + v.selected() + "," + v.position().posX()
					+ "," + v.position().posY() + "," + v.position().posZ();
			switch (type) {
			case _integer:
				values += "," + ((Value<Integer,IntegerValue>) v).value();
				break;
			case _string:
				values += ",'" + ((Value<String,StringValue>) v).value() + "'";
				break;
			case _text:
				values += ",'" + ((Value<String,TextValue>) v).value() + "'";
				break;
			case _boolean:
				values += "," + ((Value<Boolean,BooleanValue>) v).value();
				break;
			case _timestamp:
				values += ",'" + ((Value<Timestamp,TimestampValue>) v).value().toString() + "'";
				break;
			case _datetime:
				values += ",'" + ((Value<DateTime,DateTimeValue>) v).value().toString() + "'";
				break;
			case _date:
				values += ",'" + ((Value<DateTime,DateValue>) v).value().toString() + "'";
				break;
			case _time:
				values += ",'" + ((Value<DateTime,TimeValue>) v).value().toString() + "'";
				break;
			case _set:
				values += ",'" + ((Value<String,SetValue>) v).value() + "'";
				break;
			case _enum:
				values += ",'" + ((Value<String,EnumValue>) v).value() + "'";
				break;
			case _map:
				values += ",'NULL'";
//				idList = saveMapValue(objectId, parentId, ((MapValue) v).value(), log);
//				values += "," + ((idList.equals("NULL"))?idList:"'"+idList+"'");
				break;
			case _list:
				values += ",'NULL'";
//				idList = saveListValue(objectId, parentId,((ListValue) v).value(),log);
//				values += "," + ((idList.equals("NULL"))?idList:"'"+idList+"'");
				break;
			case _complex:
				values += ",'NULL'";
//				idList = saveComplexValue(objectId, parentId, ((ComplexValue) v).value(),log);
//				values += "," + ((idList.equals("NULL"))?idList:"'"+idList+"'");
				break;
			case _table:
				values += ",'NULL'";
//				idList = saveTableValue(objectId, parentId, ((TableValue) v).value(), log);
//				values += "," + ((idList.equals("NULL"))?idList:"'"+idList+"'");
				break;
			default:
				break;
			}

			return values;
		}

		public static PreparedStatement updateValues(PreparedStatement dbp, int objectId, int parentId, Value<?,?> v, boolean log) throws SQLException {
			dbp.setInt(1,objectId);
			dbp.setString(2,v.type().name());
			dbp.setString(3,v.group().name());
			dbp.setBoolean(4, v.selected());
			dbp.setInt(5,v.position().posX());
			dbp.setInt(6,v.position().posY());
			dbp.setInt(7,v.position().posZ());
			switch (v.type()) {
			case _integer:
				dbp.setInt(8,((IntegerValue) v).value());
				break;
			case _string:
				dbp.setString(8,((StringValue) v).value());
				break;
			case _text:
				dbp.setString(8,((TextValue) v).value());
				break;
			case _boolean:
				dbp.setBoolean(8,((BooleanValue) v).value());
				break;
			case _timestamp:
				dbp.setTimestamp(8,((TimestampValue) v).value());
				break;
			case _datetime:
				dbp.setTimestamp(8, new Timestamp(((DateTimeValue) v).value().getMillis()));
				break;
			case _date:
				dbp.setTimestamp(8, new Timestamp(((DateValue) v).value().getMillis()));
				break;
			case _time:
				dbp.setTimestamp(8, new Timestamp(((TimeValue) v).value().getMillis()));
				break;
			case _enum:
				dbp.setString(8,((EnumValue) v).value());
				break;
			case _set:
				dbp.setString(8,((SetValue) v).value());
				break;
			case _map:
				dbp.setString(8,ValueDao.saveMapValue(objectId, parentId, ((MapValue) v).value(), log));
				break;
			case _list:
				dbp.setString(8,ValueDao.saveListValue(objectId, parentId, ((ListValue) v).value(), log));
				break;
			case _complex:
				dbp.setString(8,ValueDao.saveComplexValue(objectId, parentId, ((ComplexValue) v).value(), log));
				break;
			case _table:
				dbp.setString(8,ValueDao.saveTableValue(objectId, parentId, ((TableValue) v).value(), log));
				break;
			default:
				break;
			}

			return dbp;
		}

		@SuppressWarnings("unchecked")
		public static String valueForSQL(int objectId, Value<?,?> v, boolean log) {
			String val = "";
//			String idList = "";
			
			switch(v.type()) {
			case _integer:
				val = ""+ ((Value<Integer,IntegerValue>)v).value();
				break;
			case _boolean:
				val = ((Value<Boolean,BooleanValue>)v).value().toString();
				break;
			case _timestamp:
				val = "'"+((Value<Timestamp,TimestampValue>)v).value().toString()+"'";
				break;
			case _datetime:
				val = "'"+((Value<DateTime,DateTimeValue>)v).value().toString()+"'";
				break;
			case _date:
				val = "'"+((Value<DateTime,DateValue>)v).value().toString()+"'";
				break;
			case _time:
				val = "'"+((Value<DateTime,TimeValue>)v).value().toString()+"'";
				break;
			case _string:
				val = "'"+((Value<String,StringValue>)v).value()+"'";
				break;
			case _text:
				val = "'"+((Value<String,TextValue>)v).value()+"'";
				break;
			case _enum:
				val = "'"+((Value<String,EnumValue>)v).value()+"'";
				break;
			case _set:
				val = "'"+((Value<String,SetValue>)v).value()+"'";
				break;
			case _map:
				val += "'NULL'";
//				idList = saveMapValue(objectId, parentId, ((MapValue) v).value(), log);
//				val += ((idList.equals("NULL"))?idList:"'"+idList+"'");
				break;
			case _list:
				val += "'NULL'";
//				idList = saveListValue(objectId, parentId,((ListValue) v).value(),log);
//				val += ((idList.equals("NULL"))?idList:"'"+idList+"'");
				break;
			case _complex:
				val += "'NULL'";
//				idList = saveComplexValue(objectId, parentId, ((ComplexValue) v).value(),log);
//				val += ((idList.equals("NULL"))?idList:"'"+idList+"'");
				break;
			case _table:
				val += "'NULL'";
//				idList = saveTableValue(objectId, parentId, ((TableValue) v).value(), log);
//				val += ((idList.equals("NULL"))?idList:"'"+idList+"'");
				break;
			default:
				break;
			}
			return val;
		}
		
		public static int clearTables(boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && Util._IN_PRODUCTION) {
				_f = "[createTable]";
				msg = "====[ Creating attribute value tables .... ]====";
				LOG.info(String.format(fmt, _f, msg));
			}
			int retVal = 0;
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;

			try {
				// get a connection TODO: need to setup connection pool
				conn = Util.DB.__conn(log);
				dbs = conn.createStatement();

				String SQL = " DELETE FROM %s";

				String[] tl = { integertable, stringtable, booleantable, timestamptable, texttable, complextable,
						blobtable };

				for (String table : tl) {
					dbs.addBatch(String.format(SQL, table));
				}

				retVal = Util.Math.addArray(dbs.executeBatch());

				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ '" + retVal + "' table entr" + ((retVal != 1) ? "ies" : "y") + " deleted ]----";
					LOG.info(String.format(fmt, _f, msg));
				}

			} catch (Exception e) {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ EXCEPTION! Update failed.]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				e.printStackTrace();
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = " Releasing Connection ";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
				Util.DB._releaseRS(rs, log);
			}
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[ load completed.]====";
				LOG.info(String.format(fmt, _f, msg + time));
			}
			return retVal;
		}

		public static int clearTable(String table, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && Util._IN_PRODUCTION) {
				_f = "[createTable]";
				msg = "====[ Creating attribute value tables .... ]====";
				LOG.info(String.format(fmt, _f, msg));
			}
			int retVal = 0;
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;

			try {
				// get a connection TODO: need to setup connection pool
				conn = Util.DB.__conn(log);
				dbs = conn.createStatement();
				String SQL = " DELETE FROM " + table;

				retVal = dbs.executeUpdate(SQL);

				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ '" + retVal + "' table entr" + ((retVal != 1) ? "ies" : "y") + " deleted ]----";
					LOG.info(String.format(fmt, _f, msg));
				}

			} catch (Exception e) {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[ EXCEPTION! Update failed.]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				e.printStackTrace();
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = " Releasing Connection ";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
				Util.DB._releaseRS(rs, log);
			}
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[ load completed.]====";
				LOG.info(String.format(fmt, _f, msg + time));
			}
			return retVal;
		}

		public static int createTable(boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && Util._IN_PRODUCTION) {
				_f = "[createTable]";
				msg = "====[ Creating attribute value tables .... ]====";
				LOG.info(String.format(fmt, _f, msg));
			}

			// create the database schema if they do not exist.
			Util.DB._createDatabases(log);

			Util.DB.dbActionType action = Util.DB.dbActionType.CREATE;

			if (log && Util._IN_PRODUCTION) {
				msg = "----[assembling '" + action.name().toLowerCase() + "' columns]----";
				LOG.info(String.format(fmt, _f, msg));
			}

			String colDef3 = Util.DB._columns(integertabledef, action, log);
			String SQL3 = "CREATE TABLE IF NOT EXISTS " + integertable + " " + " ( " + colDef3
					+ ") ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
			if (log && Util._IN_PRODUCTION) {
				msg = "----[SQL : " + SQL3 + "]----";
				LOG.info(String.format(fmt, _f, msg));
			}

			String colDef4 = Util.DB._columns(stringtabledef, action, log);
			String SQL4 = "CREATE TABLE IF NOT EXISTS " + stringtable + " " + " ( " + colDef4
					+ ") ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
			if (log && Util._IN_PRODUCTION) {
				msg = "----[SQL : " + SQL4 + "]----";
				LOG.info(String.format(fmt, _f, msg));
			}

			String colDef5 = Util.DB._columns(texttabledef, action, log);
			String SQL5 = "CREATE TABLE IF NOT EXISTS " + texttable + " " + " ( " + colDef5
					+ ") ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
			if (log && Util._IN_PRODUCTION) {
				msg = "----[SQL : " + SQL5 + "]----";
				LOG.info(String.format(fmt, _f, msg));
			}

			String colDef6 = Util.DB._columns(booleantabledef, action, log);
			String SQL6 = "CREATE TABLE IF NOT EXISTS " + booleantable + " " + " ( " + colDef6
					+ ") ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
			if (log && Util._IN_PRODUCTION) {
				msg = "----[SQL : " + SQL6 + "]----";
				LOG.info(String.format(fmt, _f, msg));
			}

			String colDef7 = Util.DB._columns(complextabledef, action, log);
			String SQL7 = "CREATE TABLE IF NOT EXISTS " + complextable + " " + " ( " + colDef7
					+ ") ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
			if (log && Util._IN_PRODUCTION) {
				msg = "----[SQL : " + SQL7 + "]----";
				LOG.info(String.format(fmt, _f, msg));
			}

			String colDef8 = Util.DB._columns(blobtabledef, action, log);
			String SQL8 = "CREATE TABLE IF NOT EXISTS " + blobtable + " " + " ( " + colDef8
					+ ") ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
			if (log && Util._IN_PRODUCTION) {
				msg = "----[SQL : " + SQL8 + "]----";
				LOG.info(String.format(fmt, _f, msg));
			}

			String colDef9 = Util.DB._columns(timestamptabledef, action, log);
			String SQL9 = "CREATE TABLE IF NOT EXISTS " + timestamptable + " " + " ( " + colDef9
					+ ") ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
			if (log && Util._IN_PRODUCTION) {
				msg = "----[SQL : " + SQL9 + "]----";
				LOG.info(String.format(fmt, _f, msg));
			}

			Connection conn = null;
			Statement dbs = null;
			int retVal = 0;
			try {
				conn = Util.DB.__conn(log);

				dbs = conn.createStatement();
				dbs.addBatch(SQL3);
				dbs.addBatch(SQL4);
				dbs.addBatch(SQL5);
				dbs.addBatch(SQL6);
				dbs.addBatch(SQL7);
				dbs.addBatch(SQL8);
				dbs.addBatch(SQL9);

				retVal = Util.Math.addArray(dbs.executeBatch());
			} catch (Exception e) {
				msg = "SQL Exception Happend!!!";
				if (log && Util._IN_PRODUCTION)
					LOG.info(String.format(fmt, _f, msg));
				e.printStackTrace();
			} finally {
				if (log && Util._IN_PRODUCTION) {
					msg = "----[Releasing Connection]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
			}

			if (log && Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[_createTable completed.]====";
				LOG.info(String.format(fmt, ("<<<<<<<<<" + _f).toUpperCase(), msg + time));
			}

			return retVal;
		}

		public static Value<?,?> rsToV(ResultSet rs, boolean log) throws SQLException {
			return rsToV(0,0,rs,log);
		}
		public static Value<?,?> rsToV(int aid, int oid, ResultSet rs, boolean log) throws SQLException {

			AttributeType type = AttributeType.valueOf(AttributeType.class,rs.getString("attribute_type"));

			int id = rs.getInt(integertabledef[0][0]);

			// int objectId = rs.getInt("object_id");

			String group = rs.getString("value_group");

			boolean selected = rs.getBoolean("selected");
			
			int x = rs.getInt("pos_x");

			int y = rs.getInt("pos_Y");

			int z = rs.getInt("pos_Z");

			Coordinate position = new CoordinateImpl(x, y, z);

			switch (type) {
			case _integer:
				return new IntegerValue(id, rs.getInt("value"), group, position, selected);
			case _string:
				return new StringValue(id, rs.getString("value"), group, position, selected);
			case _text:
				return new TextValue(id, rs.getString("value"), group, position, selected);
			case _boolean:
				return new BooleanValue(id, rs.getBoolean("value"), group, position, selected);
			case _timestamp:
				return new TimestampValue(id, rs.getTimestamp("value"), group, position, selected);
			case _datetime:
				return new DateTimeValue(id, Util.Time.dateTimeFromString(rs.getTimestamp("value").toString()), group, position, selected);
			case _date:
				return new DateValue(id, Util.Time.dateTimeFromString(rs.getTimestamp("value").toString()), group, position, selected);
			case _time:
				return new TimeValue(id, Util.Time.dateTimeFromString(rs.getTimestamp("value").toString()), group, position, selected);
			case _set:
				return new SetValue(id, rs.getString("value"), group, position, selected);
			case _enum:
				return new EnumValue(id, rs.getString("value"), group, position, selected);
			case _complex:
				ComplexValue c =loadComplexValue(aid,oid,id,log);
				c.id(id);
				c.setGroup(group);
				c.position().posX(x);
				c.position().posY(y);
				c.position().posZ(z);
				c.setSelected(selected);
				return c;
//				return new ComplexValue(id, new HashMap<String,ArrayList<Attribute<? extends Value<?,?>>>>(), group, position, selected);
//				return new ComplexValue(id, rs.getString("value"), group, position, selected);
			case _table:
				TableValue t = loadTableValue(aid,oid,id,log);
				t.id(id);
				t.setGroup(group);
				t.setSelected(selected);
				t.position().posX(x);
				t.position().posY(y);
				t.position().posZ(z);
				return t;
//				return new TableValue(id, new ArrayList<ArrayList<Attribute<? extends Value<?,?>>>>(), group, position, selected);
//				return new TableValue(id, rs.getString("value"), group, position, selected);
			case _map:
				MapValue m = loadMapValue(aid,oid,id,log);
				m.id(id);
				m.setGroup(group);
				m.setSelected(selected);
				m.position().posX(x);
				m.position().posY(y);
				m.position().posZ(z);
				return m;
//				return new MapValue(id, new HashMap<String,Attribute<? extends Value<?,?>>>(), group, position, selected);
//				return new MapValue(id, rs.getString("value"), group, position, selected);
			case _list:
				ListValue l = loadListValue(aid,oid,id,log);
				l.id(id);
				l.setGroup(group);
				l.setSelected(selected);
				l.position().posX(x);
				l.position().posY(y);
				l.position().posZ(z);
				return l;
//				return new ListValue(id, new ArrayList<Attribute<? extends Value<?,?>>>(), group, position, selected);
//				return new ListValue(id, rs.getString("value"), group, position, selected);
			default:
				return null;
			}

		}

	}

 	@SuppressWarnings("unchecked")
 	@Override
	public Value<T,Q> clone() {
 		return (Value<T,Q>) ValueInterface.clone(this);
 	}
}
