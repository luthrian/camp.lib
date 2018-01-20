/**
 * ***************************************************************************** Copyright (C) 2017
 * Christopher Campbell (campbellccc@gmail.com)
 *
 * <p>This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * <p>You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 *
 * <p>Contributors: Christopher Campbell (campbellccc@gmail.com) - all code prior and post initial
 * release ****************************************************************************
 */
package com.camsolute.code.camp.lib;

import java.sql.Timestamp;
import java.util.EnumMap;
import org.joda.time.DateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.types.CampBoolean;
import com.camsolute.code.camp.lib.types.CampDate;
import com.camsolute.code.camp.lib.types.CampDateTime;
import com.camsolute.code.camp.lib.types.CampEnum;
import com.camsolute.code.camp.lib.types.CampInteger;
import com.camsolute.code.camp.lib.types.CampSet;
import com.camsolute.code.camp.lib.types.CampString;
import com.camsolute.code.camp.lib.types.CampTable;
import com.camsolute.code.camp.lib.types.CampText;
import com.camsolute.code.camp.lib.types.CampTime;
import com.camsolute.code.camp.lib.types.CampTimestamp;
import com.camsolute.code.camp.lib.AttributeDaoInterface;
import com.camsolute.code.camp.lib.utilities.Util;
import com.camsolute.code.camp.lib.data.CampSQL;

public abstract class Attribute<T> implements AttributeInterface<T> {

  public static enum AttributeType {
    _integer,
    _string,
    _boolean,
    _datetime,
    _date,
    _time,
    _timestamp,
    _enum,
    _set,
    _text,
    _table,
    _complex,
    _list,
    _map,
    _palist,
    _token,
    _process
  }

  public static EnumMap<AttributeType, String[]> attributeMatrix = null;

  static {
    attributeMatrix = new EnumMap<AttributeType, String[]>(AttributeType.class);
    attributeMatrix.put(AttributeType._integer, new String[] {"integer", "int"});
    attributeMatrix.put(AttributeType._string, new String[] {"string", "str"});
    attributeMatrix.put(AttributeType._boolean, new String[] {"boolean", "bol"});
    attributeMatrix.put(AttributeType._datetime, new String[] {"datetime", "dtm"});
    attributeMatrix.put(AttributeType._date, new String[] {"date", "dat"});
    attributeMatrix.put(AttributeType._time, new String[] {"time", "tim"});
    attributeMatrix.put(AttributeType._timestamp, new String[] {"timestamp", "tst"});
    attributeMatrix.put(AttributeType._enum, new String[] {"enum", "enm"});
    attributeMatrix.put(AttributeType._set, new String[] {"set", "set"});
    attributeMatrix.put(AttributeType._text, new String[] {"text", "txt"});
    attributeMatrix.put(AttributeType._token, new String[] {"token", "tkn"});
    attributeMatrix.put(AttributeType._process, new String[] {"process", "prc"});
    attributeMatrix.put(AttributeType._table, new String[] {"table", "t"});
    attributeMatrix.put(AttributeType._complex, new String[] {"complex", "c"});
    attributeMatrix.put(AttributeType._list, new String[] {"list", "l"});
    attributeMatrix.put(AttributeType._map, new String[] {"map", "m"});
    attributeMatrix.put(AttributeType._palist, new String[] {"palist", "p"});
  }

  public static enum attributeContainerType {
    _product,
    _complex,
    _table;
  }

  public static enum daoEndpoint {
    _database,
    _rest_service;
  }

  private int id;
  private String name;
  private AttributeType type;
  private String defaultValue = null;

  private int valueId;

  private String version = null;

  private String typeGroup = null;
  private int typePosition = 0;
  private String typeBusinessId = null;

  private String attributeGroup = null;
  private int attributePosition = 0;
  private String attributeBusinessId = null;

  private boolean hasParent = false;
  private Attribute<?> parent = null;

  private CampStates states = new CampStates();

  public Attribute(String name, AttributeType type, String defaultValue) {
    this.name = name;
    this.type = type;
    this.defaultValue = defaultValue;
  }

  public Attribute(
      String name, AttributeType type, String defaultValue, Version version, Group group) {
    this(name, type, defaultValue);
    this.version = version.value();
    this.typeGroup = group.name();
  }

  public Attribute(
      int id, String name, AttributeType type, String defaultValue, Version version, Group group) {
    this(name, type, defaultValue);
    this.version = version.value();
    this.typeGroup = group.name();
    this.id = id;
  }

  /** {@inheritDoc} */
  @Override
  public int id() {
    return this.id;
  }
  /** {@inheritDoc} */
  @Override
  public int id(int id) {
    int prev = this.id;
    this.id = id;
    return prev;
  }

  /** {@inheritDoc} */
  @Override
  public String name() {
    return name;
  }
  /** {@inheritDoc} */
  @Override
  public String name(String name) {
    String prev = this.name;
    this.name = name;
    return prev;
  }

  /** {@inheritDoc} */
  @Override
  public AttributeType attributeType() {
    return type;
  }

  /** {@inheritDoc} */
  @Override
  public AttributeType attributeType(AttributeType type) {
    AttributeType prev = this.type;
    this.type = type;
    return prev;
  }

  /** {@inheritDoc} */
  @Override
  public int valueId(int valueId) {
    int retValueId = this.valueId;
    return retValueId;
  }
  /** {@inheritDoc} */
  @Override
  public int valueId() {
    return valueId;
  };

  /** {@inheritDoc} */
  @Override
  public abstract T value(T value);
  //	{
  //		T retValue = me(value);
  //		return retValue;
  //	}
  /** {@inheritDoc} */
  @Override
  public abstract T value();
  //	{
  //		T value = me();
  //		return value;
  //	};

  //	protected abstract T me();

  //	protected abstract T me(T value);

  /** {@inheritDoc} */
  @Override
  public String defaultValue() {
    // TODO Auto-generated method stub
    return this.defaultValue;
  }
  /** {@inheritDoc} */
  @Override
  public String defaultValue(String value) {
    String prev = this.defaultValue;
    this.defaultValue = value;
    return prev;
  }

  /** {@inheritDoc} */
  @Override
  public String version() {
    return this.version;
  }
  /** {@inheritDoc} */
  @Override
  public String version(String version) {
    String prev = this.version;
    this.version = version;
    return prev;
  }

  /** {@inheritDoc} */
  @Override
  public String typeGroup() {
    return this.typeGroup;
  }
  /** {@inheritDoc} */
  @Override
  public String typeGroup(String group) {
    String prev = this.typeGroup;
    this.typeGroup = group;
    return prev;
  }

  @Override
  public abstract String attributeGroup();

  @Override
  public abstract String attributeGroup(String group);

  /** {@inheritDoc} */
  @Override
  public int typePosition() {
    return this.typePosition;
  }
  /** {@inheritDoc} */
  @Override
  public int typePosition(int position) {
    int prev = this.typePosition;
    this.typePosition = position;
    return prev;
  }

  @Override
  public abstract int attributePosition();

  @Override
  public abstract int attributePosition(int position);

  @Override
  public String typeBusinessId() {
    return this.typeBusinessId + Util.DB._NS + this.id;
  }

  @Override
  public String typeBusinessId(String id) {
    String prev = this.typeBusinessId;
    this.typeBusinessId = id;
    return prev;
  }

  @Override
  public String onlyTypeBusinessId() {
    return this.typeBusinessId;
  }

  @Override
  public String initialTypeBusinessId() {
    return this.typeBusinessId + Util.DB._NS + 0; // initial value id = 0
  }

  /** {@inheritDoc} */
  @Override
  public String attributeBusinessId() {
    return attributeBusinessId + Util.DB._NS + id();
  }

  /** {@inheritDoc} */
  @Override
  public String attrbuteBusinessId(String id) {
    String prev = this.attributeBusinessId;
    this.attributeBusinessId = id;
    return prev;
  }

  /** {@inheritDoc} */
  @Override
  public String onlyAttributeBusinessId() {
    return this.attributeBusinessId;
  }

  /** {@inheritDoc} */
  @Override
  public String initialAttributeBusinessId() {
    return attributeBusinessId + Util.DB._NS + 0;
  }

  @Override
  public boolean hasParent() {
    return hasParent;
  }

  @Override
  public boolean hasParent(boolean hasParent) {
    boolean prev = this.hasParent;
    this.hasParent = hasParent;
    return prev;
  }

  @Override
  public Attribute<?> parent() {
    return this.parent;
  }

  @Override
  public Attribute<?> parent(Attribute<?> parent) {
    Attribute<?> prev = this.parent;
    this.parent = parent;
    return prev;
  }

  @Override
  public CampStates states() {
    return this.states;
  }

  @Override
  public abstract Attribute<?> valueFromString(String value);

  public static <X extends Attribute<?>> X createAttribute(
      String name, AttributeType type, Object value) {
    boolean isString = value.getClass().isAssignableFrom(String.class);
    switch (type) {
      case _integer:
        Integer iVal = (isString) ? Integer.valueOf((String) value) : (Integer) value;
        return (X) new CampInteger(name, iVal);
      case _string:
        return (X) new CampString(name, (String) value);
      case _boolean:
        Boolean bVal = (isString) ? Boolean.valueOf((String) value) : (Boolean) value;
        return (X) new CampBoolean(name, bVal);
      case _datetime:
        DateTime dtVal = (isString) ? DateTime.parse((String) value) : (DateTime) value;
        return (X) new CampDateTime(name, dtVal);
      case _date:
        DateTime dVal = (isString) ? DateTime.parse((String) value) : (DateTime) value;
        return (X) new CampDate(name, dVal);
      case _time:
        DateTime tVal = (isString) ? DateTime.parse((String) value) : (DateTime) value;
        return (X) new CampTime(name, tVal);
      case _timestamp:
        Timestamp tsVal = (isString) ? Util.Time.timestamp((String) value) : (Timestamp) value;
        return (X) new CampTimestamp(name, tsVal);
      case _enum:
        CampEnum eVal = (isString) ? CampEnum.fromString((String) value) : (CampEnum) value;
        return (X) eVal;
      case _set:
        CampSet stVal = (isString) ? CampSet.fromString((String) value) : (CampSet) value;
        return (X) stVal;
      case _text:
        return (X) new CampText(name, (String) value);
      case _table:
        CampTable tblVal =
            (isString) ? CampTable._fromJSON((String) value, !Util._IN_PRODUCTION) : (CampTable) value;
        return (X) tblVal;
        //			return (X) new CampTable(name,CampTable.valueFromString((String)value));
      case _complex:
        //			return (X) new CampComplex(name,CampComplex.valueFromString((String)value));
      case _list:
        //			return (X) new CampList(name,CampComplex.valueFromString((String)value));
      case _map:
        //			return (X) new CampMap(name,CampMap.valueFromString((String)value));
      case _palist:
        //			return (X) new CampList(name,CampList.valueFromString((String)value));
      case _token:
      default:
        break;
    }
    return null;
  }

  public static AttributeType toType(String typeAsString) {
    for (AttributeType type : attributeMatrix.keySet()) {
      String[] t = attributeMatrix.get(type);
      if (typeAsString.equals(t[0]) || typeAsString.equals(t[1])) {
        return type;
      }
    }
    return null;
  }

  public static String typeToL(AttributeType type) {
    return attributeMatrix.get(type)[0];
  }

  public static String typeToS(AttributeType type) {
    return attributeMatrix.get(type)[1];
  }

  public static class Dao implements AttributeDaoInterface {

    private static final Logger LOG = LogManager.getLogger(Attribute.Dao.class);
    private static String fmt = "[%15s] [%s]";

    // SQL table details
    public static String table =
        CampSQL.pmTable(CampSQL._SYSTEM_TABLES_DB_INDEX, CampSQL.System._ATTRIBUTE_TYPE_INDEX);
    public static String[][] tabledef = CampSQL.System._attribute_type_table_definition;

    public static String valuetable =
        CampSQL.omTable(CampSQL._SYSTEM_TABLES_DB_INDEX, CampSQL.System._ATTRIBUTE_VALUE_INDEX);
    public static String[][] valuetabledef = CampSQL.System._attribute_value_table_definition;

    // SQL table select queries
    public static String loadValueByIdPSQL = "SELECT * FROM " + valuetable + " WHERE `id_`=?";
    public static String loadByIdSQL = "SELECT * FROM " + table + " WHERE `id_`=%s";
    public static String loadByNameSQL = "SELECT * FROM " + table + " WHERE `name`='%s'";
    public static String loadByTypeSQL = "SELECT * FROM " + table + " WHERE `type`='%s'";
    public static String loadAllSQL = "SELECT * FROM " + table;

    private static Dao instance = null;

    private Dao() {}

    public static Attribute.Dao instance() {
      if (instance == null) {
        instance = new Dao();
      }
      return instance;
    }

    /** {@inheritDoc} */
    @Override
    public Attribute<?> createAttribute (int objectId, String name, AttributeType type, String defaultValue) {
      return _createCreate(objectId, name, type, defaultValue, false);
    }

    public Attribute<?> _createDefinition(int oid, String name, Type type, String defaultValue, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION ) {
        _f = "[createAttribute]";
        msg = "====[ create and save attribute type '" + name + "'/'" + type + "' ]====";
        LOG.info(String.format(fmt, _f, msg));
      }

      Attribute<?> ct = null;

      if (defaultValue != null) {
        ct = getType(name, type, defaultValue);
      } else {
        ct = getType(name, type);
      }
      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = DBU.__conn(log);

        String SQL =
            "INSERT INTO "
                + table
                + "( "
                + DBU._columns(tabledef, dbActionType.INSERT, log)
                + " ) VALUES ( "
                + insertTypeValues(ct)
                + " )";

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ SQL: " + SQL + "]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();

        retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ saved '" + retVal + "' entry]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        rs = dbs.getGeneratedKeys();

        if (rs.next()) {

          ct.id(rs.getInt("id_"));

        } else {
          // TODO: throw an exception from here
        }
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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type created.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return ct;
    }

    /** {@inheritDoc} */
    @Override
    public Attribute<?> create(
        int productAttributeId, String name, Type type, String defaultValue, Object value) {
      return _create(productAttributeId, name, type, defaultValue, value, false);
    }

    public Attribute<?> _create(
        int paid, String name, Type type, String defaultValue, Object value, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[create]";
        msg = "====[ create and save attribute type '" + name + "'/'" + type + "' ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      Attribute<?> ct = null;

      if (defaultValue != null) {
        ct = getType(name, type, defaultValue);
      } else {
        ct = getType(name, type);
      }
      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = DBU.__conn(log);

        String SQL =
            "INSERT INTO "
                + table
                + "( "
                + DBU._columns(tabledef, dbActionType.INSERT, log)
                + " )"
                + " VALUES ( "
                + insertTypeValues(ct)
                + " )";

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ SQL: " + SQL + "]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();

        retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ saved '" + retVal + "' entry]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        rs = dbs.getGeneratedKeys();

        if (rs.next()) {

          ct.id(rs.getInt("id_"));

        } else {
          // TODO: throw an exception from here
        }

        String vSQL =
            "INSERT INTO "
                + valuetable
                + "( "
                + DBU._columns(valuetabledef, dbActionType.INSERT, log)
                + " ) "
                + "VALUES ( "
                + insertValueValues(paid, ct)
                + " )";

        retVal = dbs.executeUpdate(vSQL, Statement.RETURN_GENERATED_KEYS);

        rs.close();

        rs = dbs.getGeneratedKeys();

        if (rs.next()) {

          ct.valueId(rs.getInt("pav_id"));

        } else {
          // TODO: throw an exception from here
        }

        String refSQL =
            "INSERT INTO "
                + pahttable
                + "( "
                + DBU._columns(pahttabledef, dbActionType.INSERT, log)
                + " ) "
                + "VALUES ( "
                + paid
                + ", "
                + ct.valueId()
                + ", "
                + ct.id()
                + " )";
        dbs.executeUpdate(refSQL);

        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ created '" + retVal + "' entries]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type created.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return ct;
    }

    /** {@inheritDoc} */
    @Override
    public Attribute<?> save(int productAttributeId, Attribute<?> ct) {
      return _save(productAttributeId, ct, false);
    }

    public Attribute<?> _save(int paid, Attribute<?> ct, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[create]";
        msg = "====[ save attribute type  ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }
      if (ct == null) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ERROR! ct is null]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        return ct;
      }
      // '"+ct.name()+"'/'"+ct.type()+"'
      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = DBU.__conn(log);

        String SQL =
            "INSERT INTO "
                + table
                + "( "
                + DBU._columns(tabledef, dbActionType.INSERT, log)
                + " ) VALUES ( "
                + insertTypeValues(ct)
                + " )";

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ SQL: " + SQL + "]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();

        retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ saved '" + retVal + "' entry]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        rs = dbs.getGeneratedKeys();

        if (rs.next()) {

          ct.id(rs.getInt("id_"));

        } else {
          // TODO: throw an exception from here
        }

        String vSQL =
            "INSERT INTO "
                + valuetable
                + "( "
                + DBU._columns(valuetabledef, dbActionType.INSERT, log)
                + " ) "
                + "VALUES ( "
                + insertValueValues(paid, ct)
                + " )";

        retVal = dbs.executeUpdate(vSQL, Statement.RETURN_GENERATED_KEYS);
        rs.close();

        rs = dbs.getGeneratedKeys();

        if (rs.next()) {

          ct.valueId(rs.getInt("pav_id"));

        } else {
          // TODO: throw an exception from here
        }

        String refSQL =
            "INSERT INTO "
                + pahttable
                + "( "
                + DBU._columns(pahttabledef, dbActionType.INSERT, log)
                + " ) "
                + "VALUES ( "
                + paid
                + ", "
                + ct.valueId()
                + ", "
                + ct.id()
                + " )";
        dbs.executeUpdate(refSQL);

        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ created '" + retVal + "' entries]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type created.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return ct;
    }

    /** {@inheritDoc} */
    @Override
    public CampList<Attribute<?>> saveList(
        int productAttributeId, CampList<Attribute<?>> attributeTypeList) {
      return _saveList(productAttributeId, attributeTypeList, false);
    }

    public CampList<Attribute<?>> _saveList(
        int paid, CampList<Attribute<?>> attributeTypeList, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[create]";
        msg = "====[ create and save list of attribute types ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      ResultSet vrs = null;
      Statement dbs = null;
      Statement rdbs = null;
      // TODO:we only save list entries which have no database representation (ie. entry id == 0)
      //		CampList<Attribute<?>> saveList = new CampList<Attribute<?>>();
      //		for(Attribute<?> ct:attributeTypeList.value()) {
      //			if(ct.id()== 0) {
      //				attributeTypeList.remove(ct);
      //				saveList.add(ct);
      //			}
      //		}
      //
      try {

        conn = DBU.__conn(log);

        String SQL =
            "INSERT INTO "
                + table
                + "( "
                + DBU._columns(tabledef, dbActionType.INSERT, log)
                + " ) VALUES "
                + insertTypeValues(attributeTypeList);

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ SQL: " + SQL + "]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();
        rdbs = conn.createStatement();

        retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ saved '" + retVal + "' entries]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        rs = dbs.getGeneratedKeys();

        int counter = 0;

        while (rs.next()) {

          //				Attribute<?> ct = saveList.get(counter);//TODO: see savelist note: would destroy
          // order
          Attribute<?> ct = attributeTypeList.get(counter);
          ct.id(
              rs.getInt(
                  "id_")); // TODO: FIXME: is this safe (ie is saveList.get(counter) and rs.next()
          // always in sync)

          String vSQL =
              "INSERT INTO "
                  + valuetable
                  + "( "
                  + DBU._columns(valuetabledef, dbActionType.INSERT, log)
                  + " ) "
                  + "VALUES ( "
                  + insertValueValues(paid, ct)
                  + "' )";

          retVal = dbs.executeUpdate(vSQL, Statement.RETURN_GENERATED_KEYS);

          vrs = dbs.getGeneratedKeys();

          if (vrs.next()) {

            ct.valueId(vrs.getInt("pav_id"));
            vrs.close();
          } else {
            // TODO: throw an exception from here
          }

          String refSQL =
              "INSERT INTO "
                  + pahttable
                  + "( "
                  + DBU._columns(pahttabledef, dbActionType.INSERT, log)
                  + " ) "
                  + "VALUES ( "
                  + paid
                  + ", "
                  + ct.valueId()
                  + ", "
                  + ct.id()
                  + " )";
          rdbs.addBatch(refSQL);

          counter++;
        }
        retVal = U.addUp(rdbs.executeBatch());

        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ created '" + retVal + "' entries]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseStatement(rdbs, log);
        DBU._releaseRS(rs, log);
        DBU._releaseRS(vrs, log);
      }

      //		attributeTypeList.addAll(saveList);

      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type created.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }

      return attributeTypeList;
    }

    /** {@inheritDoc} */
    @Override
    public int updateDefinition(Attribute<?> attributeType) {
      return _updateDefinition(attributeType, false);
    }

    public int _updateDefinition(Attribute<?> attributeType, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[create]";
        msg =
            "====[ update attribute type definition '"
                + attributeType.name()
                + "'/'"
                + attributeType.type()
                + "' ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = DBU.__conn(log);

        String SQL =
            "UPDATE "
                + table
                + " SET "
                + DBU._columns(tabledef, dbActionType.UPDATE, log)
                + " WHERE `"
                + tabledef[0][0]
                + "`=%s";

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ FORMAT SQL ]]" + SQL + "[[]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        String eSQL =
            String.format(
                SQL,
                "'" + attributeType.name() + "'",
                "'" + attributeType.attributeType().name() + "'",
                "'" + attributeType.defaultValue() + "'",
                attributeType.typePosition(),
                "'" + attributeType.typeGroup() + "'",
                attributeType.id());

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ SQL: " + eSQL + "]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();
        retVal = dbs.executeUpdate(eSQL);

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ updated '" + retVal + "' entries]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type definition updated.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    /** {@inheritDoc} */
    @Override
    public int updateDefinitionList(CampList<Attribute<?>> attributeTypeList) {
      return _updateDefinitionList(attributeTypeList, false);
    }

    public int _updateDefinitionList(CampList<Attribute<?>> atl, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[create]";
        msg = "====[ update attribute type definition list ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = DBU.__conn(log);

        String SQL =
            "UPDATE "
                + table
                + " SET "
                + DBU._columns(tabledef, dbActionType.UPDATE, log)
                + " WHERE `"
                + tabledef[0][0]
                + "`=%s";

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ FORMAT SQL ]]" + SQL + "[[]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();

        for (Attribute<?> at : atl.value()) {

          String eSQL =
              String.format(
                  SQL,
                  "'" + at.name() + "'",
                  "'" + at.attributeType().name() + "'",
                  "'" + at.defaultValue() + "'",
                  at.id());

          if (log && !Util._IN_PRODUCTION) {
            msg = "-- --[ SQL ]]" + eSQL + "[[]-- --";
            LOG.info(String.format(fmt, _f, msg));
          }

          dbs.addBatch(eSQL);
        }
        retVal = U.addUp(dbs.executeBatch());

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ updated '" + retVal + "' entries]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type definition list updated.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    /** {@inheritDoc} */
    @Override
    public int update(int productAttributeId, Attribute<?> attributeType) {
      return _update(productAttributeId, attributeType, false);
    }

    public int _update(int paid, Attribute<?> attributeType, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[create]";
        msg =
            "====[ create and save attribute type '"
                + attributeType.name()
                + "'/'"
                + attributeType.type()
                + "' ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = DBU.__conn(log);

        String SQL =
            "UPDATE "
                + table
                + " SET "
                + DBU._columns(tabledef, dbActionType.UPDATE, log)
                + " WHERE `"
                + tabledef[0][0]
                + "`=%s";

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ FORMAT SQL ]]" + SQL + "[[]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        String eSQL =
            String.format(
                SQL,
                "'" + attributeType.name() + "'",
                "'" + attributeType.attributeType().name() + "'",
                "'" + attributeType.defaultValue() + "'",
                attributeType.typePosition(),
                "'" + attributeType.typeGroup() + "'",
                attributeType.id());

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ SQL: " + SQL + "]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        String vSQL =
            "UPDATE"
                + valuetable
                + " SET "
                + DBU._columns(valuetabledef, dbActionType.UPDATE, log)
                + " WHERE `"
                + valuetabledef[0][0]
                + "`=%s";

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ FORMAT VALUE SQL ]]" + vSQL + "[[]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        String evSQL =
            String.format(
                vSQL,
                paid,
                attributeType.attributePosition(),
                "'" + attributeType.attributeGroup() + "'",
                "'" + updatevalue(paid, attributeType) + "'",
                attributeType.valueId());

        dbs = conn.createStatement();
        dbs.addBatch(eSQL);
        dbs.addBatch(evSQL);

        retVal = U.addUp(dbs.executeBatch());

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ updated '" + retVal + "' entries]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type created.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    /** {@inheritDoc} */
    @Override
    public int updateList(int productAttributeId, CampList<Attribute<?>> attributeTypeList) {
      return _updateList(productAttributeId, attributeTypeList, false);
    }

    public int _updateList(int paid, CampList<Attribute<?>> atl, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[create]";
        msg = "====[ create and save list of attribute types ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = DBU.__conn(log);

        String SQL =
            "UPDATE "
                + table
                + " SET "
                + DBU._columns(tabledef, dbActionType.UPDATE, log)
                + " WHERE `"
                + tabledef[0][0]
                + "`=%s";

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ FORMAT SQL ]]" + SQL + "[[]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        String vSQL =
            "UPDATE"
                + valuetable
                + " SET "
                + DBU._columns(valuetabledef, dbActionType.UPDATE, log)
                + " WHERE `"
                + valuetabledef[0][0]
                + "`=%s";

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ FORMAT VALUE SQL ]]" + vSQL + "[[]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();

        for (Attribute<?> at : atl.value()) {

          String eSQL =
              String.format(
                  SQL,
                  "'" + at.name() + "'",
                  "'" + at.attributeType().name() + "'",
                  "'" + at.defaultValue() + "'",
                  at.id());

          if (log && !Util._IN_PRODUCTION) {
            msg = "-- --[ SQL ]]" + eSQL + "[[]-- --";
            LOG.info(String.format(fmt, _f, msg));
          }

          dbs.addBatch(eSQL);

          String evSQL =
              String.format(
                  vSQL,
                  paid,
                  at.attributePosition(),
                  "'" + at.attributeGroup() + "'",
                  "'" + updatevalue(paid, at) + "'",
                  at.valueId());

          if (log && !Util._IN_PRODUCTION) {
            msg = "----[VALUE SQL : " + evSQL + "]----";
            LOG.info(String.format(fmt, _f, msg));
          }

          dbs.addBatch(evSQL);
        }
        retVal = U.addUp(dbs.executeBatch());

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ updated '" + retVal + "' entries]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type list updated.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    /** {@inheritDoc} */
    @Override
    public int delete(int id) {
      return _delete(id, false);
    }

    public int _delete(int id, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[create]";
        msg = "====[ deleting attribute type by id '" + id + "' ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = DBU.__conn(log);

        String SQL = "DELETE FROM " + table + " WHERE `id_`=" + id;

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ SQL ]]" + SQL + "[[]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();

        retVal = dbs.executeUpdate(SQL);

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ deleted '" + retVal + "' entry]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type created.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    /** {@inheritDoc} */
    @Override
    public int delete(String name) {
      return _delete(name, false);
    }

    public int _delete(String name, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[create]";
        msg = "====[ deleting attribute type by name '" + name + "' ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = DBU.__conn(log);

        String SQL = "DELETE FROM " + table + " WHERE `name`=" + name;

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ SQL ]]" + SQL + "[[]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();

        retVal = dbs.executeUpdate(SQL);

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ deleted '" + retVal + "' entry]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type created.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    /** {@inheritDoc} */
    @Override
    public int deleteList(CampList<Attribute<?>> attributeTypeList) {
      return _deleteList(attributeTypeList, false);
    }

    public int _deleteList(CampList<Attribute<?>> atl, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[create]";
        msg = "====[ deleting list attribute types ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = DBU.__conn(log);

        dbs = conn.createStatement();

        for (Attribute<?> at : atl.value()) {
          String SQL = "DELETE FROM " + table + " WHERE `id_`=" + at.id();

          if (log && !Util._IN_PRODUCTION) {
            msg = "-- --[ SQL ]]" + SQL + "[[]-- --";
            LOG.info(String.format(fmt, _f, msg));
          }

          dbs.addBatch(SQL);
        }

        retVal = U.addUp(dbs.executeBatch());

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ deleted '" + retVal + "' entries]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type created.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    /** {@inheritDoc} */
    @Override
    public Attribute<?> loadDefinition(int id) {
      return _loadDefinition(id, false);
    }

    public Attribute<?> _loadDefinition(int id, boolean log) {
      Attribute<?> at = _loadType(String.format(loadByIdSQL, id), log);
      return at;
    }

    /** {@inheritDoc} */
    @Override
    public Attribute<?> loadDefinition(String name) {
      return _loadDefinition(name, false);
    }

    public Attribute<?> _loadDefinition(String name, boolean log) {
      Attribute<?> at = _loadType(String.format(loadByNameSQL, name), log);
      return at;
    }

    /** {@inheritDoc} */
    @Override
    public CampList<Attribute<?>> loadDefinitionByType(Type type) {
      return _loadDefinition(type, false);
    }

    public CampList<Attribute<?>> _loadDefinition(Type type, boolean log) {
      CampList<Attribute<?>> atl = _loadTypeList(String.format(loadByTypeSQL, type), log);
      return atl;
    }

    @Override
    public CampList<Attribute<?>> loadListByProductAttributeId(int[] ids) {
      return _loadList(ids, false);
    }

    public CampList<Attribute<?>> _loadList(int[] ids, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_loadList]";
        msg = "====[ loading list of '" + ids.length + "' attribute types]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      Connection conn = null;
      ResultSet rs = null;
      PreparedStatement dbp = null;

      int retVal = 0;
      CampList<Attribute<?>> atl = new CampList<Attribute<?>>();

      try {

        conn = DBU.__conn(log);

        dbp = conn.prepareStatement(loadValueByIdPSQL);

        for (int id : ids) {
          dbp.setInt(1, id);
          dbp.addBatch();
        }
        rs = dbp.executeQuery();

        while (rs.next()) {
          atl.add(_rsToType(rs, log));
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbp, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type list loaded.]====";
        LOG.traceExit(String.format(fmt, _f, msg + time));
      }

      return atl;
    }

    /** {@inheritDoc} */
    @Override
    public CampList<Attribute<?>> loadList() {
      return _loadList(false);
    }

    public CampList<Attribute<?>> _loadList(boolean log) {
      CampList<Attribute<?>> atl = _loadTypeList(loadAllSQL, log);
      return atl;
    }

    /** {@inheritDoc} */
    @Override
    public Attribute<?> loadByProductAttributeId(int id) {
      return _loadByPAId(id, false);
    }

    public Attribute<?> _loadByPAId(int id, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_loadValue]";
        msg = "====[ load attribute type by id ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      Attribute<?> ct = null;

      try {

        conn = DBU.__conn(log);

        dbs = conn.createStatement();
        String SQL =
            "SELECT `id_`,`pav_id`,`name`,`type`,`default_value`,`type_position`,`attribute_position`,`value` "
                + " FROM "
                + table
                + " AS at, "
                + valuetable
                + " AS av, "
                + pahttable
                + " as thv WHERE "
                + " thv.product_attribute_type_id=at.id "
                + " AND thv.product_attribute_value_id=av.pav_id "
                + " AND thv.product_attribute_id=av.product_attribute_id"
                + " AND av.product_attribute_id="
                + id;

        rs = dbs.executeQuery(SQL);

        if (rs.next()) {
          ct = _rsToType(rs, log);

          int valueId = 0;
          if (rs.getInt("pav_id") != 0) {
            valueId = rs.getInt("pav_id");
          }
          ct.valueId(valueId);

          String attributeGroup = null;
          if (rs.getString("attribute_group") != null) {
            attributeGroup = rs.getString("attribute_group");
          }
          ct.attributeGroup(attributeGroup);

          int attributePosition = 0; // in a CampTable this represents col
          if (rs.getInt("attribute_position") != 0) {
            attributePosition = rs.getInt("attribute_position");
          }
          ct.attributePosition(attributePosition);

          String value = rs.getString("value");
          ct = getValue(value, ct, log);
          //				if(ct.attributeType().equals(ProductAttributeType._complex)) {
          //					CampList<Attribute<?>> ctl = _loadValueList(rs.getString("value").split(","),log);
          //				}
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }

      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ value loaded attribute type.]====";
        LOG.traceExit(String.format(fmt, _f, msg + time));
      }

      return ct;
    }

    /** {@inheritDoc} */
    @Override
    public Attribute<?> load(int id) {
      return _load(id, false);
    }

    public Attribute<?> _load(int id, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_loadValue]";
        msg = "====[ load attribute type by id ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      Attribute<?> ct = null;

      try {

        conn = DBU.__conn(log);

        dbs = conn.createStatement();
        String SQL =
            "SELECT `id_`,`pav_id`,`name`,`type`,`default_value`,`type_position`,`attribute_position`,`value` "
                + " FROM "
                + table
                + " AS at, "
                + valuetable
                + " AS av, "
                + pahttable
                + " as thv WHERE "
                + " at.id="
                + id
                + " AND thv.product_attribute_type_id=at.id "
                + " AND thv.product_attribute_value_id=av.pav_id "
                + " AND thv.product_attribute_id=av.product_attribute_id";

        rs = dbs.executeQuery(SQL);

        if (rs.next()) {
          ct = _rsToType(rs, log);

          int valueId = 0;
          if (rs.getInt("pav_id") != 0) {
            valueId = rs.getInt("pav_id");
          }
          ct.valueId(valueId);

          String attributeGroup = null;
          if (rs.getString("attribute_group") != null) {
            attributeGroup = rs.getString("attribute_group");
          }
          ct.attributeGroup(attributeGroup);

          int attributePosition = 0; // in a CampTable this represents col
          if (rs.getInt("attribute_position") != 0) {
            attributePosition = rs.getInt("attribute_position");
          }
          ct.attributePosition(attributePosition);

          String value = rs.getString("value");
          ct = getValue(value, ct, log);
          //				if(ct.attributeType().equals(ProductAttributeType._complex)) {
          //					CampList<Attribute<?>> ctl = _loadValueList(rs.getString("value").split(","),log);
          //				}
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }

      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ value loaded attribute type.]====";
        LOG.traceExit(String.format(fmt, _f, msg + time));
      }

      return ct;
    }
    /** {@inheritDoc} */
    @Override
    public Attribute<?> loadByValueId(int id) {
      return _loadByValueId(id, false);
    }

    public Attribute<?> _loadByValueId(int id, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_loadValue]";
        msg = "====[ load attribute type by value id ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      Attribute<?> ct = null;

      try {

        conn = DBU.__conn(log);

        dbs = conn.createStatement();
        String SQL =
            "SELECT `id_`,`pav_id`,`name`,`type`,`default_value`,`type_position`,`attribute_position`,`value` "
                + " FROM "
                + table
                + " AS at, "
                + valuetable
                + " AS av, "
                + pahttable
                + " as thv WHERE "
                + " thv.product_attribute_type_id=at.id "
                + " AND thv.product_attribute_value_id=av.pav_id "
                + " AND thv.product_attribute_id=av.product_attribute_id"
                + " AND thv.product_attribute_value_id="
                + id;

        rs = dbs.executeQuery(SQL);

        if (rs.next()) {
          ct = _rsToType(rs, log);

          int valueId = 0;
          if (rs.getInt("pav_id") != 0) {
            valueId = rs.getInt("pav_id");
          }
          ct.valueId(valueId);

          String attributeGroup = null;
          if (rs.getString("attribute_group") != null) {
            attributeGroup = rs.getString("attribute_group");
          }
          ct.attributeGroup(attributeGroup);

          int attributePosition = 0; // in a CampTable this represents col
          if (rs.getInt("attribute_position") != 0) {
            attributePosition = rs.getInt("attribute_position");
          }
          ct.attributePosition(attributePosition);

          String value = rs.getString("value");
          ct = getValue(value, ct, log);
          //				if(ct.attributeType().equals(ProductAttributeType._complex)) {
          //					CampList<Attribute<?>> ctl = _loadValueList(rs.getString("value").split(","),log);
          //				}
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }

      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ value loaded attribute type.]====";
        LOG.traceExit(String.format(fmt, _f, msg + time));
      }

      return ct;
    }

    /** {@inheritDoc} */
    @Override
    public CampList<Attribute<?>> loadValueList(String[] idList) {
      return _loadValueList(idList, false);
    }

    public CampList<Attribute<?>> _loadValueList(String[] idList, boolean log) {
      // TODO Auto-generated method stub
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public ProductAttributeDefinition<?, ?> add(ProductAttributeDefinition<?, ?> productAttribute) {
      return _add(productAttribute, false);
    }

    public ProductAttributeDefinition<?, ?> _add(
        ProductAttributeDefinition<?, ?> pad, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_addOrder]";
        msg = "====[ adding attribute type and values to product attribute definition ]====";
        LOG.traceEntry(String.format(fmt, (_f + ">>>>>>>>>").toUpperCase(), msg));
      }

      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      int retVal = 0;

      try {

        conn = DBU.__conn(log);

        dbs = conn.createStatement();
        String SQL =
            "SELECT `id_`,`name`,`type`,`default_value`,`type_position`,`attribute_position`,`value` "
                + " FROM "
                + table
                + " AS at, "
                + valuetable
                + " AS av WHERE "
                + " av.product_attribute_id="
                + pad.id()
                + " AND EXISTS "
                + "( SELECT `product_attribute_id` FROM "
                + pahttable
                + " WHERE `product_attribute_type_id`=at.id "
                + " AND `product_attribute_id`=av.product_attribute_id "
                + " AND `product_attribute_value_id`=av.pav_id"
                + " AND `product_attribute_id`="
                + pad.id()
                + " )";

        rs = dbs.executeQuery(SQL);

        boolean first = true;

        while (rs.next()) {
          Attribute<?> ct = _rsToType(rs, log);
          int row = 0;
          if (rs.getString("row") != null) {
            row = Integer.valueOf(rs.getString("row"));
          }
          String value = rs.getString("value");
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ types and values saved.]====";
        LOG.traceExit(String.format(fmt, _f, msg + time));
      }

      return pad;
    }

    /** {@inheritDoc} */
    @Override
    public OrderProductAttribute<?, ?> addOrder(OrderProductAttribute<?, ?> productAttribute) {
      return _addOrder(productAttribute, false);
    }

    public OrderProductAttribute<?, ?> _addOrder(OrderProductAttribute<?, ?> opa, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_addOrder]";
        msg = "====[ adding attribute type and values to order product attribute ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      int retVal = 0;

      try {

        conn = DBU.__conn(log);

        dbs = conn.createStatement();
        String SQL =
            "SELECT `id_`,`name`,`type`,`default_value`,`row`,`value` "
                + " FROM "
                + table
                + " AS at, "
                + valuetable
                + " AS av WHERE "
                + " av.product_attribute_id="
                + opa.id()
                + " AND EXISTS "
                + "( SELECT `product_attribute_id` FROM "
                + pahttable
                + " WHERE `product_attribute_type_id`=at.id "
                + " AND `product_attribute_id`=av.product_attribute_id "
                + " AND `product_attribute_id`="
                + opa.id()
                + " )";

        rs = dbs.executeQuery(SQL);

        boolean first = true;

        while (rs.next()) {
          Attribute<?> ct = _rsToType(rs, log);
          int row = 0;
          if (rs.getString("row") != null) {
            row = Integer.valueOf(rs.getString("row"));
          }
          String value = rs.getString("value");
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ types and values saved.]====";
        LOG.traceExit(String.format(fmt, _f, msg + time));
      }

      return opa;
    }

    /** {@inheritDoc} */
    @Override
    public CampOldList<ProductAttributeDefinition<?, ?>> addList(
        CampOldList<ProductAttributeDefinition<?, ?>> productAttributeList) {
      // TODO Auto-generated method stub
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public CampOldList<OrderProductAttribute<?, ?>> addOrderList(
        CampOldList<OrderProductAttribute<?, ?>> productAttributeList) {
      // TODO Auto-generated method stub
      return null;
    }

    /** {@inheritDoc} */
    @Override
    public Attribute<?> saveReference(int productAttributeId, Attribute<?> attributeType) {
      return _saveReference(productAttributeId, attributeType, false);
    }

    public Attribute<?> _saveReference(int paid, Attribute<?> ct, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_save]";
        msg = "====[ saving product attribute reference]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      int retVal = 0;

      try {

        conn = DBU.__conn(log);

        dbs = conn.createStatement();

        // create attribute type database entry if it is new (id == 0)

        if (ct.id() == 0) {
          ct.id(_save(paid, ct, log).id());
        } else {

          String SQL =
              "INSERT INTO "
                  + pahttable
                  + "( "
                  + DBU._columns(pahttabledef, dbActionType.INSERT, log)
                  + " ) "
                  + "VALUES ( "
                  + paid
                  + ", "
                  + ct.valueId()
                  + ", "
                  + ct.id()
                  + " )";

          retVal = dbs.executeUpdate(SQL);

          if (log && !Util._IN_PRODUCTION) {
            msg = "----[ created '" + retVal + "' entries]-- --";
            LOG.info(String.format(fmt, _f, msg));
          }
        }
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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ reference saved.]====";
        LOG.traceExit(String.format(fmt, _f, msg + time));
      }

      return ct;
    }

    /** {@inheritDoc} */
    @Override
    public HashMap<Integer, Attribute<?>> saveReferenceList(
        HashMap<Integer, Attribute<?>> productAttributeReferenceList) {
      return _saveReferenceList(productAttributeReferenceList, false);
    }

    public HashMap<Integer, Attribute<?>> _saveReferenceList(
        HashMap<Integer, Attribute<?>> parl, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_save]";
        msg = "====[ saving references to product attribute ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      int retVal = 0;

      try {

        conn = DBU.__conn(log);

        dbs = conn.createStatement();
        boolean batch = false;
        for (int id : parl.keySet()) {
          Attribute<?> ct = parl.get(id);
          if (ct.id() == 0) {
            ct.id(_save(id, ct, log).id());
          } else {
            batch = true;

            String SQL =
                "INSERT INTO "
                    + pahttable
                    + "( "
                    + DBU._columns(pahttabledef, dbActionType.INSERT, log)
                    + " ) "
                    + "VALUES ( "
                    + id
                    + ", "
                    + ", "
                    + ct.valueId()
                    + ct.id()
                    + " )";

            dbs.addBatch(SQL);
          }
        }
        if (batch) {
          retVal = U.addUp(dbs.executeBatch());
        }

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ saved '" + retVal + "' references]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ EXCEPTION! save failed.]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = " -- -- [ Releasing Connection ]";
          LOG.info(String.format(fmt, _f, msg));
        }
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ references added.]====";
        LOG.traceExit(String.format(fmt, _f, msg + time));
      }

      return parl;
    }

    /** {@inheritDoc} */
    @Override
    public int clear(OrderProductAttribute<?, ?> productAttribute) {
      // TODO Auto-generated method stub
      return 0;
    }

    /** {@inheritDoc} */
    @Override
    public int clearList(OrderProductAttribute<?, ?> productAttributeList) {
      // TODO Auto-generated method stub
      return 0;
    }

    // support functions
    public Attribute<?> _loadType(String SQL, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_load]";
        msg = "====[ executing db query: '" + SQL + "' ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      int retVal = 0;
      Attribute<?> at = null;

      try {

        conn = DBU.__conn(log);

        dbs = conn.createStatement();

        rs = dbs.executeQuery(SQL);

        if (rs.next()) {
          at = _rsToType(rs, log);
        } else {
          if (log && !Util._IN_PRODUCTION) {
            msg = "----[ERROR! empty db result set.]----";
            LOG.info(String.format(fmt, _f, msg));
          }
        }

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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }

      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ executed db query.]====";
        LOG.traceExit(String.format(fmt, _f, msg + time));
      }

      return at;
    }

    public CampList<Attribute<?>> _loadTypeList(String SQL, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_loadList]";
        msg = "====[ executing db query: '" + SQL + "' ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      int retVal = 0;
      CampList<Attribute<?>> atl = new CampList<Attribute<?>>();

      try {

        conn = DBU.__conn(log);

        dbs = conn.createStatement();

        rs = dbs.executeQuery(SQL);

        while (rs.next()) {
          atl.add(_rsToType(rs, log));
        }
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
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ db query executed.]====";
        LOG.traceExit(String.format(fmt, _f, msg + time));
      }

      return atl;
    }

    public Attribute<?> _rsToType(ResultSet rs, boolean log) throws SQLException {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_rsToType]";
        msg = "====[ generating new camp type from db query result set. ]====";
        LOG.info(String.format(fmt, _f, msg));
      }

      Attribute<?> ct = null;

      int id = rs.getInt("id_");
      msg = "---- ---- id is '" + id + "'";
      if (log && !Util._IN_PRODUCTION) LOG.info(String.format(fmt, _f, msg));

      String name = rs.getString("name");
      msg = "---- ---- name is '" + name + "'";
      if (log && !Util._IN_PRODUCTION) LOG.info(String.format(fmt, _f, msg));

      Type type = ProductAttribute.toType(rs.getString("type"));
      msg = "---- ---- type is '" + type.name() + "'";
      if (log && !Util._IN_PRODUCTION) LOG.info(String.format(fmt, _f, msg));

      String defaultValue = rs.getString("default_value");
      msg = "---- ---- default value is '" + defaultValue + "'";
      if (log && !Util._IN_PRODUCTION) LOG.info(String.format(fmt, _f, msg));

      int typePosition = rs.getInt("type_position"); // in a CampTable this represents row
      msg = "---- ---- type position is '" + typePosition + "'";
      if (log && !Util._IN_PRODUCTION) LOG.info(String.format(fmt, _f, msg));

      String typeGroup = rs.getString("type_group");
      msg = "---- ---- type group is '" + typeGroup + "'";
      if (log && !Util._IN_PRODUCTION) LOG.info(String.format(fmt, _f, msg));

      ct = getType(name, type);
      ct.id(id);
      ct.typePosition(typePosition);
      ct.typeGroup(typeGroup);
      ct.defaultValue(defaultValue);

      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ generated Attribute<'" + ProductAttribute.typeToL(type) + "'>.]====";
        LOG.traceExit(String.format(fmt, _f, msg + time));
      }

      return ct;
    }

    public static Attribute<?> getType(String name, Type type) {
      return getType(name, type, null);
    }

    public static Attribute<?> getType(String name, AttributeType type, String defaultValue) {
      Attribute<?> ct = null;
      boolean def = (defaultValue != null);
      switch (type) {
        case _integer:
          ct = (def) ? new CampInteger(name, Integer.valueOf(defaultValue)) : new CampInteger(name);
          ct.defaultValue(defaultValue);
          break;
        case _string:
          ct = (def) ? new CampString(name, defaultValue) : new CampString(name);
          ct.defaultValue(defaultValue);
          break;
        case _boolean:
          ct = (def) ? new CampBoolean(name, Boolean.valueOf(defaultValue)) : new CampBoolean(name);
          ct.defaultValue(defaultValue);
          break;
        case _datetime:
          ct = (def) ? new CampDateTime(name, defaultValue) : new CampDateTime(name);
          ct.defaultValue(defaultValue);
          break;
        case _date:
          ct = (def) ? new CampDate(name, defaultValue) : new CampDate(name);
          ct.defaultValue(defaultValue);
          break;
        case _time:
          ct = (def) ? new CampTime(name, defaultValue) : new CampTime(name);
          ct.defaultValue(defaultValue);
          break;
        case _timestamp:
          ct = (def) ? new CampTimestamp(name, defaultValue) : new CampTimestamp(name);
          ct.defaultValue(defaultValue);
          break;
        case _enum:
          String dv = null;
          ct = (def) ? new CampEnum(name, defaultValue) : new CampEnum(name, dv);
          break;
        case _set:
          dv = null;
          ct = (def) ? new CampSet(name, defaultValue) : new CampSet(name, dv);
          ct.defaultValue(defaultValue);
          break;
        case _text:
          ct = (def) ? new CampText(name, defaultValue) : new CampText(name);
          ct.defaultValue(defaultValue);
          break;
        case _table:
          ct = new CampTable<Attribute<?>>(name);
          break;
        case _complex:
          ct = new CampComplex<Attribute<?>>(name);
          ct.defaultValue(defaultValue);
          break;
        case _list:
          ct = new CampList<Attribute<?>>(name);
          ct.defaultValue(defaultValue);
          break;
        case _map:
          ct = new CampMap<Attribute<?>>(name);
          ct.defaultValue(defaultValue);
          break;
        case _palist:
          ct = new CampList<Attribute<?>>(name);
          ct.defaultValue(defaultValue);
          break;
        case _token:
          ct = CampToken.usernameToken(name);
          ct.defaultValue(defaultValue);
          break;
        default:
          break;
      }

      return ct;
    }

    public Attribute<?> getValue(String sValue, Attribute<?> ct, boolean log)
        throws CampTableDoubleCellWriteException {
      switch (ct.attributeType()) {
        case _integer:
          ((CampInteger) ct).value(Integer.valueOf(sValue));
          break;
        case _string:
          ((CampString) ct).value(sValue);
          break;
        case _boolean:
          ((CampBoolean) ct).value(Boolean.valueOf(sValue));
          break;
        case _datetime:
          ((CampDateTime) ct).value(TU.dateTimeFromString(sValue));
          break;
        case _date:
          ((CampDate) ct).value(TU.dateTimeFromString(sValue));
          break;
        case _time:
          ((CampTime) ct).value(TU.dateTimeFromString(sValue));
          break;
        case _timestamp:
          ((CampTimestamp) ct).value(TU.timestamp(sValue));
          break;
        case _enum:
          ((CampEnum) ct).value(sValue);
          break;
        case _set:
          ((CampSet) ct).value(sValue);
          break;
        case _text:
          ((CampText) ct).value(sValue);
          break;
        case _table:
          ((CampTable<Attribute<?>>) ct).value(loadTable(sValue, (CampTable<Attribute<?>>) ct, log));
          break;
        case _complex:
          ((CampComplex<Attribute<?>>) ct)
              .value(loadComplex(sValue, (CampComplex<Attribute<?>>) ct, log));
          break;
        case _list:
          ((CampList<Attribute<?>>) ct).value(loadList(sValue, (CampList<Attribute<?>>) ct, log));
          break;
        case _map:
          ((CampMap<Attribute<?>>) ct).value(loadMap(sValue, (CampMap<Attribute<?>>) ct, log));
          break;
        case _token:
          ((CampToken) ct).value(sValue);
          break;
        default:
          break;
      }

      return ct;
    }

    public ArrayList<CampList<Attribute<?>>> loadTable(
        String sValue, CampTable<Attribute<?>> ct, boolean log)
        throws CampTableDoubleCellWriteException {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[loadTable]";
        msg = "====[ load camp table from Attribute string value '" + sValue + "' ]====";
        LOG.traceEntry(String.format(fmt, (_f + ">>>>>>>>>").toUpperCase(), msg));
      }
      ArrayList<CampList<Attribute<?>>> list = new ArrayList<CampList<Attribute<?>>>();
      String[] sids = sValue.split(",");
      int[] ids = new int[sids.length];
      int count = 0;
      for (String sid : sids) {
        ids[count] = Integer.valueOf(sid);
        count++;
      }

      CampList<Attribute<?>> ctl = _loadList(ids, log);

      // To ensure correct table structure, create a "table" HashMap to intially store CampTable
      // row/column Attribute<?> entries.

      HashMap<Integer, HashMap<Integer, Attribute<?>>> tableMap =
          new HashMap<Integer, HashMap<Integer, Attribute<?>>>();

      // Add Attribute entries to correct tableMap matrix location.

      for (Attribute<?> ctre : ctl.value()) {
        if (!tableMap.containsKey(ctre.typePosition() - 1)) {
          tableMap.put(ctre.typePosition() - 1, new HashMap<Integer, Attribute<?>>());
        }
        if (tableMap.get(ctre.typePosition() - 1).containsKey(ctre.attributePosition() - 1)) {
          throw new CampTableDoubleCellWriteException(
              "CAMPTABLE EXCEPTION! DOUBLE-WRITE to cell {'"
                  + ctre.typePosition()
                  + "','"
                  + ctre.attributePosition()
                  + "'} ocurred.");
        }
        tableMap.get(ctre.typePosition() - 1).put(ctre.attributePosition() - 1, ctre);
      }

      // get number of rows and columns from the table hashmap matrix
      int numRows = tableMap.size();

      int numColumns = tableMap.get(0).size();

      // loop over rows and columns to add table hash map matrix entries to result table list
      for (int r = 0; r < numRows; r++) {
        list.add(new CampList<Attribute<?>>());
        for (int c = 0; c < numColumns; c++) {
          list.get(r).add(tableMap.get(r).get(c));
        }
      }

      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ camp table loaded.]====";
        LOG.info(String.format(fmt, ("<<<<<<<<<" + _f).toUpperCase(), msg + time));
      }

      return list;
    }

    public HashMap<String, CampList<Attribute<?>>> loadComplex(
        String sValue, CampComplex<Attribute<?>> ct, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[loadComplex]";
        msg = "====[ load camp complex from Attribute string value '" + sValue + "' ]====";
        LOG.traceEntry(String.format(fmt, (_f + ">>>>>>>>>").toUpperCase(), msg));
      }
      HashMap<String, CampList<Attribute<?>>> map = new HashMap<String, CampList<Attribute<?>>>();

      String[] sids = sValue.split(",");

      int[] ids = new int[sids.length];

      int count = 0;

      for (String sid : sids) {
        ids[count] = Integer.valueOf(sid);
        count++;
      }

      CampList<Attribute<?>> ctl = _loadList(ids, log);

      for (Attribute<?> ctre : ctl.value()) {
        if (!map.containsKey(ctre.typeGroup())) {
          map.put(ctre.typeGroup(), new CampList<Attribute<?>>());
        }
        map.get(ctre.typeGroup()).add(ctre);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ complex type loaded.]====";
        LOG.info(String.format(fmt, ("<<<<<<<<<" + _f).toUpperCase(), msg + time));
      }

      return map;
    }

    public ArrayList<Attribute<?>> loadList(String sValue, CampList<Attribute<?>> ct, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[loadList]";
        msg = "====[ load camp list from Attribute string value '" + sValue + "'  ]====";
        LOG.traceEntry(String.format(fmt, (_f + ">>>>>>>>>").toUpperCase(), msg));
      }
      String[] sids = sValue.split(",");

      int[] ids = new int[sids.length];

      int count = 0;

      for (String sid : sids) {
        ids[count] = Integer.valueOf(sid);
        count++;
      }

      ArrayList<Attribute<?>> list = _loadList(ids, log).value();

      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ list type loaded.]====";
        LOG.info(String.format(fmt, ("<<<<<<<<<" + _f).toUpperCase(), msg + time));
      }

      return list;
    }

    public HashMap<String, CampList<Attribute<?>>> loadMap(
        String sValue, CampMap<Attribute<?>> ct, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[loadMap]";
        msg = "====[ load camp map from Attribute string value '" + sValue + "'  ]====";
        LOG.traceEntry(String.format(fmt, (_f + ">>>>>>>>>").toUpperCase(), msg));
      }
      HashMap<String, CampList<Attribute<?>>> map = new HashMap<String, CampList<Attribute<?>>>();

      String[] sids = sValue.split(",");

      int[] ids = new int[sids.length];

      int count = 0;

      for (String sid : sids) {
        ids[count] = Integer.valueOf(sid);
        count++;
      }

      CampList<Attribute<?>> ctl = _loadList(ids, log);

      for (Attribute<?> ctre : ctl.value()) {
        if (!map.containsKey(ctre.typeGroup())) {
          map.put(ctre.typeGroup(), new CampList<Attribute<?>>());
        }
        map.get(ctre.typeGroup()).add(ctre);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ map type loaded.]====";
        LOG.info(String.format(fmt, ("<<<<<<<<<" + _f).toUpperCase(), msg + time));
      }

      return map;
    }

    public String value(int productAttributeId, Attribute<?> ct) {
      return _value(productAttributeId, ct, false);
    }

    @SuppressWarnings("unchecked")
    public String _value(int paid, Attribute<?> ct, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_value]";
        msg =
            "====[ save complex/table attribute types and return a comma separated list of product attribute id's ]====";
        LOG.info(String.format(fmt, _f, msg));
      }

      String value = "";
      Type type = ct.attributeType();

      /**
       * Set complex/table value to a comma separated list of product attribute id's of the table
       * product attributes after saving them. In case of set/enum set value to return value of the
       * <code>toString()</code> method.
       */
      if (type.equals(Type._complex)) {

        CampComplex<Attribute<?>> cc = (CampComplex<Attribute<?>>) ct.value();

        CampList<Attribute<?>> crl = cc.toList();

        if (crl.size() == 0) {
          if (log && !Util._IN_PRODUCTION) {
            msg =
                "----[ Error! attempt to get value of empty complex product attribute type occured.]----";
            LOG.info(String.format(fmt, _f, msg));
          }
          return null;
        }

        crl = _saveList(paid, crl, log);

        boolean first = true;

        for (Attribute<?> cce : crl.value()) {
          value += ((first) ? "" : ",") + cce.id();
          first = false;
        }

      } else if (type.equals(Type._table)) {

        CampList<Attribute<?>> crl = new CampList<Attribute<?>>();

        CampTable<Attribute<?>> t = (CampTable<Attribute<?>>) ct.value();

        try {
          // check that we have a first row.
          if (t._getRow(1, log).size() == 0) {
            if (log && !Util._IN_PRODUCTION) {
              msg = "-- --[ Error! attempted to save empty table product attribute.]-- --";
              LOG.info(String.format(fmt, _f, msg));
            }
            return null;
          }
        } catch (CampTableRowOutOfBoundsException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        for (CampList<Attribute<?>> rl : t.value()) {
          crl.addAll(rl);
        }

        crl = _saveList(paid, crl, log);

        boolean first = true;

        for (Attribute<?> crle : crl.value()) {
          value +=
              ((first) ? "" : ",")
                  + crle.id(); // TODO: FIXME: this design approach has limitations due to database
          // value entry size chosen. this makes sense for the current scope but
          // a different use of tableType can be envisaged which would make a
          // redesign (database tables) sensible in the future
          first = false;
        }

      } else if (type.equals(Type._enum)) {

        value = (String) ct.value(); // ((CampEnum)ct.value()).toString();

      } else if (type.equals(Type._set)) {

        value = ((CampSet) ct.value()).toString();

      } else {

        value = "" + ct.value();
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ value generated.]====";
        LOG.info(String.format(fmt, ("<<<<<<<<<" + _f).toUpperCase(), msg + time));
      }

      return value;
    }

    public String updatevalue(int productAttributeId, Attribute<?> ct) {
      return _updatevalue(productAttributeId, ct, false);
    }

    @SuppressWarnings("unchecked")
    public String _updatevalue(int paid, Attribute<?> ct, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_value]";
        msg =
            "====[ update complex/table attribute types and return a comma separated list of product attribute id's ]====";
        LOG.info(String.format(fmt, _f, msg));
      }

      String value = "";
      Type type = ct.attributeType();

      /**
       * Set complex/table value to a comma separated list of product attribute id's of the table
       * product attributes after saving them. In case of set/enum set value to return value of the
       * <code>toString()</code> method.
       */
      if (type.equals(Type._complex)) {

        CampComplex<Attribute<?>> cc = (CampComplex<Attribute<?>>) ct.value();

        CampList<Attribute<?>> crl = cc.toList();

        if (crl.size() == 0) {
          if (log && !Util._IN_PRODUCTION) {
            msg =
                "----[ Error! attempt to get value of empty complex product attribute type occured.]----";
            LOG.info(String.format(fmt, _f, msg));
          }
          return null;
        }

        int retVal = _updateList(paid, crl, log);

        boolean first = true;

        for (Attribute<?> cce : crl.value()) {
          value += ((first) ? "" : ",") + cce.id();
          first = false;
        }

      } else if (type.equals(Type._table)) {

        CampList<Attribute<?>> crl = new CampList<Attribute<?>>();

        CampTable<Attribute<?>> t = (CampTable<Attribute<?>>) ct.value();

        try {
          // check that we have a first row.
          if (t._getRow(1, log).size() == 0) {
            if (log && !Util._IN_PRODUCTION) {
              msg =
                  "-- --[ Error! attempted to get value (save) empty table product attribute.]-- --";
              LOG.info(String.format(fmt, _f, msg));
            }
            return null;
          }
        } catch (CampTableRowOutOfBoundsException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        for (CampList<Attribute<?>> rl : t.value()) {
          crl.addAll(rl);
        }

        int retVal = _updateList(paid, crl, log);

        boolean first = true;

        for (Attribute<?> crle : crl.value()) {
          value +=
              ((first) ? "" : ",")
                  + crle.id(); // TODO: FIXME: this design approach has limitations due to database
          // value entry size chosen. this makes sense for the current scope but
          // a different use of tableType can be envisaged which would make a
          // redesign (database tables) sensible in the future
          first = false;
        }

      } else if (type.equals(Type._enum)) {

        value = ((CampEnum) ct.value()).toString();

      } else if (type.equals(Type._set)) {

        value = ((CampSet) ct.value()).toString();

      } else {

        value = "" + ct.value();
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ update value generated.]====";
        LOG.info(String.format(fmt, ("<<<<<<<<<" + _f).toUpperCase(), msg + time));
      }

      return value;
    }

    public int clearTable(boolean log) {
      return _clearTable(false);
    }

    public int _clearTable(boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_createTable]";
        msg = "====[ creating '" + table + "' table ... ]====";
        LOG.info(String.format(fmt, _f, msg));
      }
      int retVal = 0;

      String SQL = "DELETE FROM " + table;

      if (log && !Util._IN_PRODUCTION) {
        msg = "-- --[ SQL ]]" + SQL + "[[]-- --";
        LOG.info(String.format(fmt, _f, msg));
      }

      String pahtSQL = "DELETE FROM " + pahttable;

      if (log && !Util._IN_PRODUCTION) {
        msg = "-- --[ SQL ]]" + pahtSQL + "[[]-- --";
        LOG.info(String.format(fmt, _f, msg));
      }

      String valueSQL = "DELETE FROM " + valuetable;

      if (log && !Util._IN_PRODUCTION) {
        msg = "-- --[ SQL ]]" + valueSQL + "[[]-- --";
        LOG.info(String.format(fmt, _f, msg));
      }

      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = DBU.__conn(log);

        dbs = conn.createStatement();

        dbs.addBatch(SQL);
        dbs.addBatch(pahtSQL);
        dbs.addBatch(valueSQL);

        retVal = U.addUp(dbs.executeBatch());

        if (log && !Util._IN_PRODUCTION) {
          msg = "----[created '" + retVal + "' tables]----";
          LOG.info(String.format(fmt, _f, msg));
        }

      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = " -- -- [ Releasing Connection ]";
          LOG.info(String.format(fmt, _f, msg));
        }
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      return retVal;
    }

    public int createTable(boolean checkDBExists) {
      return _createTable(checkDBExists, true);
    }

    public int _createTable(boolean cde, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_createTable]";
        msg = "====[ creating '" + table + "' table ... ]====";
        LOG.info(String.format(fmt, _f, msg));
      }
      int retVal = 0;
      if (cde && !DBU._dbExists(dbName, log)) {
        DBU._createDatabases(log);
      }
      //		super._createTable(cde, log);
      DBU.dbActionType action = DBU.dbActionType.CREATE;
      if (log && !Util._IN_PRODUCTION) {
        msg = "-- --[ assembling '" + action.name().toLowerCase() + "' columns]-- --";
        LOG.info(String.format(fmt, _f, msg));
      }

      String columndef = DBU._columns(tabledef, action, log);
      String SQL =
          "CREATE TABLE IF NOT EXISTS "
              + table
              + " "
              + " ( "
              + columndef
              + ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";

      if (log && !Util._IN_PRODUCTION) {
        msg = "-- --[ SQL ]]" + SQL + "[[]-- --";
        LOG.info(String.format(fmt, _f, msg));
      }

      String pahtcolumndef = DBU._columns(pahttabledef, action, log);
      String pahtSQL =
          "CREATE TABLE IF NOT EXISTS "
              + pahttable
              + " "
              + " ( "
              + pahtcolumndef
              + ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";

      if (log && !Util._IN_PRODUCTION) {
        msg = "-- --[ SQL ]]" + pahtSQL + "[[]-- --";
        LOG.info(String.format(fmt, _f, msg));
      }

      String valuecolumndef = DBU._columns(valuetabledef, action, log);
      String valueSQL =
          "CREATE TABLE IF NOT EXISTS "
              + valuetable
              + " "
              + " ( "
              + valuecolumndef
              + ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";

      if (log && !Util._IN_PRODUCTION) {
        msg = "-- --[ SQL ]]" + valueSQL + "[[]-- --";
        LOG.info(String.format(fmt, _f, msg));
      }

      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = DBU.__conn(log);

        dbs = conn.createStatement();

        dbs.addBatch(SQL);
        dbs.addBatch(pahtSQL);
        dbs.addBatch(valueSQL);

        retVal = U.addUp(dbs.executeBatch());

        if (log && !Util._IN_PRODUCTION) {
          msg = "----[created '" + retVal + "' tables]----";
          LOG.info(String.format(fmt, _f, msg));
        }

      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = " -- -- [ Releasing Connection ]";
          LOG.info(String.format(fmt, _f, msg));
        }
        DBU.__release(conn, log);
        DBU._releaseStatement(dbs, log);
        DBU._releaseRS(rs, log);
      }
      return retVal;
    }

    public String insertTypeValues(CampList<Attribute<?>> ctl) {
      String retVal = "";
      boolean first = true;
      for (Attribute<?> ct : ctl.value()) {
        if (ct.id() == 0) {
          retVal += ((first) ? "" : " ,") + "( " + insertTypeValues(ct) + " )";
          first = false;
        }
      }
      return retVal;
    }

    public String insertTypeValues(Attribute<?> ct) {

      return "'"
          + ct.name()
          + "', "
          + "'"
          + ct.attributeType().name()
          + "', "
          + "'"
          + ct.defaultValue()
          + "', "
          + ct.typePosition()
          + ", "
          + "'"
          + ct.typeGroup()
          + "'";
    }

    public String insertValueValues(int paid, CampList<Attribute<?>> ctl) {
      String retVal = "";
      boolean first = true;
      for (Attribute<?> ct : ctl.value()) {
        if (ct.id() == 0) {
          retVal += ((first) ? "" : " ,") + "( " + insertValueValues(paid, ct) + " )";
          first = false;
        }
      }
      return retVal;
    }

    public String insertValueValues(int paid, Attribute<?> ct) {

      return ct.valueId()
          + ", "
          + paid
          + ", "
          + ct.attributePosition()
          + ", "
          + "'"
          + ct.attributeGroup()
          + "', "
          + "'"
          + value(paid, ct)
          + "'";
    }

    @Override
    public int deleteList(int productAttributeId) {
      // TODO Auto-generated method stub
      return 0;
    }
  }
}
