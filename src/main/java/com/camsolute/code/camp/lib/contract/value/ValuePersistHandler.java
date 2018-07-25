package com.camsolute.code.camp.lib.contract.value;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.camsolute.code.camp.lib.contract.core.CampBinary.Binary;
import com.camsolute.code.camp.lib.contract.core.CampComplex.ValueComplex;
import com.camsolute.code.camp.lib.contract.core.CampComplex.ValueListComplex;
import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.CampException.ElementNotInListException;
import com.camsolute.code.camp.lib.contract.core.CampException.PersistanceException;
import com.camsolute.code.camp.lib.contract.core.CampTable.ValueTable;
import com.camsolute.code.camp.lib.contract.core.CommandParameter;
import com.camsolute.code.camp.lib.contract.core.HasId;
import com.camsolute.code.camp.lib.contract.db.DBHandler;
import com.camsolute.code.camp.lib.contract.db.MethodParameter;
import com.camsolute.code.camp.lib.contract.db.SQLCreateHandler;
import com.camsolute.code.camp.lib.contract.db.SQLDeleteHandler;
import com.camsolute.code.camp.lib.contract.db.SQLReadHandler;
import com.camsolute.code.camp.lib.contract.db.SQLUpdateHandler;
import com.camsolute.code.camp.lib.contract.core.CampSQL;
import com.camsolute.code.camp.lib.contract.core.CampStates.IOAction;
import com.camsolute.code.camp.lib.contract.core.CampList.ValueList;
import com.camsolute.code.camp.lib.contract.value.Value;
import com.camsolute.code.camp.lib.contract.value.Value.*;
import com.camsolute.code.camp.lib.utilities.Util;

public interface ValuePersistHandler<T,Q extends Value<T,Q>> { //extends SQLReadHandler<Q>, SQLCreateHandler<Q>, SQLDeleteHandler<Q>, SQLUpdateHandler<Q>{

  public static int DB_INDEX = CampSQL._ORDER_TABLES_DB_INDEX;


  /**
   * table for the concrete value aspects of an attribute
   * Note: an associative tag was added for indexing
   */
    public static enum valueSQL {
        id("_value_id_", "VARCHAR(45) NOT NULL",1),
        objectId("object_id", "VARCHAR(45) NOT NULL",2),
        parentId("parent_id", "VARCHAR(45) NULL",3),
        valueType("value_type", "varchar(45) NOT NULL",4),
        valueGroup("value_group", "varchar(45) NOT NULL",5),
        selected("selected", "boolean NULL DEFAULT false",6),
        posX("pos_x", "int(11) NULL DEFAULT 0",7),
        posY("pos_y", "int(11) NULL DEFAULT 0",8),
        posZ("pos_z", "int(11) NULL DEFAULT 0",9),
        valueData("value_data", "VARCHAR(65535) NULL",10),
        extra("extra", "PRIMARY KEY (`_value_id_`), UNIQUE INDEX `_v_value_id_UNIQUE` (`_value_id_` ASC)"
         + ",index `_av_pos_y_idx` (`pos_y` asc)"
         + ",index `_av_object_id_idx` (`object_id` asc)",0);

      public static String dbName = "system_tables";

      public static String tableName = "_value";

      private String columnName;
      private String definition;
      private int columnIndex;

      private valueSQL(String col,String def,int ind) {
        columnName = col;
        definition = def;
        columnIndex = ind;
       }
      public String columnName() { return columnName; }
      public String definition() { return definition; }
      public int columnIndex() {return columnIndex; }
      @Override
      public String toString() {
        return columnName;
      }
      public static String columns() {
        return "`"+id+"`"+",`"+objectId+"`"+",`"+parentId+"`"+",`"+valueType+"`"+",`"+valueGroup+"`"+",`"+selected+"`"
            +",`"+posX+"`"+",`"+posY+"`"+",`"+posZ+"`, `"+valueData+"`";
      }
      public static String columnsNoId() {
        return "`"+objectId+"`"+",`"+parentId+"`"+",`"+valueType+"`"+",`"+valueGroup+"`"+",`"+selected+"`"
            +",`"+posX+"`"+",`"+posY+"`"+",`"+posZ+"`, `"+valueData+"`";
      }
      public static String queryByIdSQL(String valueId) {
        return "SELECT * FROM `"+dbName+"`.`"+tableName+"` WHERE `"+id+"`='"+valueId+"'";
      }
      public static String queryByObjectIdSQL(String objId) {
        return "SELECT * FROM `"+dbName+"`.`"+tableName+"` WHERE `"+objectId+"`='"+objId+"'";
      }
      public static String querySQL(String objId, String valueId) {
        return "SELECT * FROM `"+dbName+"`.`"+tableName+"` WHERE `"+objectId+"`='"+objId+"' AND `"+id+"`='"+valueId+"'";
      }
      public static String querySQL(String where) {
        return "SELECT * FROM `"+dbName+"`.`"+tableName+"` WHERE "+where;
      }
      public static String insertSQL(String objId, Value<?,?> value) {
        return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+objectId+"`"+",`"+parentId+"`"+",`"+valueType+"`"+",`"+valueGroup+"`"+",`"+selected+"`"
            +",`"+posX+"`"+",`"+posY+"`"+",`"+posZ+"`, `"+valueData+"`) VALUES (`"+value.id()+"`,`"+objId+"`,`"+value.parentId()+"`,`"+value.type().name()+"`,`"+value.group().name()+"`,"+String.valueOf(value.selected())
                +","+String.valueOf(value.position().posX())+","+String.valueOf(value.position().posY())+","+String.valueOf(value.position().posZ())+","+value.sqlHandler().sqlValue(value)+")" ;
      }
      public static String insertPSQL() {
        return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+objectId+"`"+",`"+parentId+"`"+",`"+valueType+"`"+",`"+valueGroup+"`"+",`"+selected+"`"
            +",`"+posX+"`"+",`"+posY+"`"+",`"+posZ+"`, `"+valueData+"`) VALUES (?,?,?,?,?,?,?,?,?,?) ";
      }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+objectId+"` "+objectId.definition()+
              ",`"+parentId+"` "+parentId.definition()+",`"+valueType+"` "+valueType.definition()+",`"+valueGroup+"` "+valueGroup.definition()+",`"+selected+"` "+selected.definition()+
              ",`"+posX+"` "+posX.definition()+",`"+posY+"` "+posY.definition()+
              ",`"+posZ+"` "+posZ.definition()+", `"+valueData+"` "+valueData.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL(String objId, Value value) {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`='"+value.id()+"'"+",`"+objectId+"`='"+objId+"'"+",`"+parentId+"`='"+value.parentId()+"'"+",`"+valueType+"`='"+value.type().name()+"'"
              +",`"+valueGroup+"`='"+value.group().name()+"'"+",`"+selected+"`="+String.valueOf(value.selected())+",`"+posX+"`="+String.valueOf(value.position().posX())
              +",`"+posY+"`="+String.valueOf(value.position().posY())+",`"+posZ+"`="+String.valueOf(value.position().posZ())+", `"+valueData+"`="+value.sqlHandler().sqlValue(value)
              +" WHERE `"+id+"`='"+value.id()+"'";
        }
        public static String updatePSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+objectId+"`=?"+",`"+parentId+"`=?"+",`"+valueType+"`=?"+",`"+valueGroup+"`=?"+",`"+selected+"`=?"+
  ",`"+posX+"`=?"+",`"+posY+"`=?"+",`"+posZ+"`=?, `"+valueData+"`=?"+" WHERE `"+id+"`=?";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL(String where) {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE "+where;
        }
        public static String deleteByIdSQL(String id) {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE `"+id+"`='"+id+"'";
        }
        public static String deleteByObjectIdSQL(String objId) {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE `"+objectId+"`='"+objId+"'";
        }

    };


  /**
   * Returns the String representation of the value data formated for SQL.
   * @param value the String value SQL representation of the value aspects value data 
   * @return SQL String representation of value data.
   */
  public String sqlValue(Value<?,?> value);

  public Value<?,?> rsToV(Value<?,?> value, ResultSet rs) throws SQLException;

  public Q create(String objectId, ValueType type, String valueGroup, T value, int posX, int posY, int posZ, boolean selected) throws PersistanceException;

  public Q save(String objectId, Q v) throws PersistanceException;

  public ValueList saveList(String objectId, ValueList vl) throws PersistanceException;

  public int update(String objectId, Q v) throws PersistanceException;

  public int updateList(String objectId, ValueList vl) throws PersistanceException;

  public int deleteById(String valueId) throws PersistanceException;

  public int deleteList(ValueList vl) throws PersistanceException;

  public int deleteByObjectId(String objectId) throws PersistanceException;

  public int createTables() throws SQLException;
  
  public int clearTables() throws SQLException;
//  public static String _updateValuePSQL() {
//    return valueSQL.updatePSQL(" `"+valueSQL.id+"`=?");
//  }
//
//  public static String _updateValueSQL() {
//    return valueSQL.updateSQL(" `"+valueSQL.id+"`=%s");
//  }
  public static int _createTables() throws SQLException {
    Connection conn = null;
    Statement dbs = null;
    int retVal = 0;
    try {
      // get a connection TODO: need to setup connection pool
      conn = Util.DB.__conn();
      dbs = conn.createStatement();
      retVal = dbs.executeUpdate(valueSQL.createSQL());
   } catch (SQLException e) {
      throw e;
    } finally {
      Util.DB.__release(conn);
      Util.DB.releaseStatement(dbs);
    }
    return retVal;
  }

  public static int _clearTables() throws SQLException {
    Connection conn = null;
    Statement dbs = null;
    int retVal = 0;
    try {
      // get a connection TODO: need to setup connection pool
      conn = Util.DB.__conn();
      dbs = conn.createStatement();
      retVal = dbs.executeUpdate(valueSQL.clearSQL());
   } catch (SQLException e) {
      throw e;
    } finally {
      Util.DB.__release(conn);
      Util.DB.releaseStatement(dbs);
    }
    return retVal;
  }


  public static Statement _addValueUpdateToStatement(String objectId, Value<?,?> value, Statement dbs) throws SQLException {
    dbs.addBatch(valueSQL.updateSQL(objectId,value));
    return dbs;
  }

 public static PreparedStatement _addValueUpdateToPreparedStatement(String objectId, Value<?,?> value, PreparedStatement dbp) throws SQLException {
    dbp.setString(valueSQL.id.columnIndex(), value.id());
    dbp.setString(valueSQL.objectId.columnIndex(), objectId);
    dbp.setString(valueSQL.parentId.columnIndex(), value.parentId());
    dbp.setString(valueSQL.valueType.columnIndex(), value.type().name());
    dbp.setString(valueSQL.valueGroup.columnIndex(), value.group().name());
    dbp.setBoolean(valueSQL.selected.columnIndex(), value.selected());
    dbp.setInt(valueSQL.posX.columnIndex(), value.position().posX());
    dbp.setInt(valueSQL.posY.columnIndex(), value.position().posY());
    dbp.setInt(valueSQL.posZ.columnIndex(), value.position().posZ());
    dbp.setString(valueSQL.valueData.columnIndex(), value.toValueString());
    dbp.setString(11, value.id());
    dbp.addBatch();
    return dbp;
  }

  public static PreparedStatement _addValueListUpdateToPreparedStatement(String objectId, ValueList vl, Connection conn) throws SQLException {

    PreparedStatement dbp = conn.prepareStatement(valueSQL.updatePSQL());
    boolean values = true;
    for(Value<?,?> value:vl) {
      if(!value.states().isModified()){
        continue;
      }
      dbp = _addValueUpdateToPreparedStatement(objectId, value, dbp);
      values = false;
    }
    if(values) {
      throw new SQLException("No modified values in list. Cannot perform update.");
    }
    return dbp;
  }

  public static Statement _addValuedDeleteToStatement(Value<?,?> value, Statement dbs) throws SQLException {
    dbs.addBatch(valueSQL.deleteSQL(" `"+valueSQL.id+"`='"+value.id()+"'"));
    return dbs;
  }

  public static Statement _addValuedDeleteToStatement(ValueList vl, Statement dbs) throws SQLException {
    for(Value<?,?> value:vl) {
      dbs.addBatch(valueSQL.deleteByIdSQL(value.id()));
    }
    return dbs;
  }

  public static PreparedStatement _addValueInsertToPreparedStatement(String objectId, Value<?,?> value, PreparedStatement dbp) throws SQLException {
    if(!value.states().isNew() && !value.states().hasNewId()){
      value.updateId(HasId.newId());
      value.states().ioAction(IOAction.NEWID);
    }
    dbp.setString(valueSQL.id.columnIndex(),value.id());
    dbp.setString(valueSQL.objectId.columnIndex(), objectId);
    dbp.setString(valueSQL.parentId.columnIndex(), value.parentId());
    dbp.setString(valueSQL.valueType.columnIndex(), value.type().name());
    dbp.setString(valueSQL.valueGroup.columnIndex(), value.group().name());
    dbp.setBoolean(valueSQL.selected.columnIndex(), value.selected());
    dbp.setInt(valueSQL.posX.columnIndex(), value.position().posX());
    dbp.setInt(valueSQL.posY.columnIndex(), value.position().posY());
    dbp.setInt(valueSQL.posZ.columnIndex(), value.position().posZ());
    dbp.setString(valueSQL.valueData.columnIndex(), value.toValueString());
    dbp.addBatch();
    return dbp;
  }

  public static Statement _addValueInsertToStatement(String objectId, Value<?,?> value, Statement dbs) throws SQLException {
    if(!value.states().isNew() && !value.states().hasNewId()){
      value.updateId(HasId.newId());
      value.states().ioAction(IOAction.NEWID);
    }
    String SQL = valueSQL.insertSQL(objectId,value);
    dbs.addBatch(SQL);
    return dbs;
  }

  public abstract class AbstractValuePersistHandler<T,Q extends Value<T,Q>> implements ValuePersistHandler<T,Q> {

    public Q create(String objectId, ValueType type, String valueGroup, T value, int posX, int posY, int posZ, boolean selected) throws PersistanceException {
      @SuppressWarnings("unchecked")
      Q v = (Q) Value.ValueFactory.generateValue(type, value);
      v.position().posX(posX);
      v.position().posY(posY);
      v.position().posZ(posZ);
      v.setSelected(selected);
      return save(objectId,v);
    }

    public Q save(String objectId, Q value) throws PersistanceException {
      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      PreparedStatement dbp = null;
      // update Value.id if Value is not new
      if(!value.states().isNew() && !value.states().hasNewId()){
        value.updateId(HasId.newId());
        value.states().ioAction(IOAction.NEWID);
      }

      try {
        // get a connection TODO: need to setup connection pool
        conn = Util.DB.__conn();
        dbp = conn.prepareStatement(valueSQL.insertPSQL());
        dbp = _addValueInsertToPreparedStatement(objectId, value, dbp);
        retVal = Util.Math.addArray(dbp.executeBatch());
        if (retVal == 0) {
          throw new PersistanceException("Failed to persist Value instance to database.");
        }
        value.states().ioAction(IOAction.SAVE);
      } catch (Exception e) {
         throw new PersistanceException("SQL Exception!", e);
      } finally {
        Util.DB.release(conn);
        if(dbp != null) Util.DB.releaseStatement(dbp);
        Util.DB.releaseRS(rs);
      }
      return value;
    }

    public ValueList saveList(String objectId, ValueList vl) throws PersistanceException {
      Connection conn = null;
      ResultSet rs = null;
      PreparedStatement dbp = null;
      Statement dbs = null;
      int retVal = 0;
      boolean dbsOK = false;
      try {
        // get a connection TODO: need to setup connection pool
        conn = Util.DB.__conn();
        dbs = conn.prepareStatement(valueSQL.insertPSQL());
        for(Value<?,?> value:vl) {
          dbp = _addValueInsertToPreparedStatement(objectId, value, dbp);
        }

        retVal = Util.Math.addArray(dbs.executeBatch());
        if (retVal == 0) {
          throw new PersistanceException("Failed to persist Value instance to database.");
        }
      } catch (Exception e) {
        throw new PersistanceException("SQL Exception!",e);
      } finally {
        Util.DB.__release(conn);
        if(dbp != null) Util.DB.releaseStatement(dbp);
        if(dbs != null) Util.DB.releaseStatement(dbs);
        Util.DB.releaseRS(rs);
      }
      return vl;
    }

    public int update(String objectId, Q value) throws PersistanceException {
      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      PreparedStatement dbp = null;

      try {
        // get a connection TODO: need to setup connection pool
        conn = Util.DB.__conn();
        dbp = conn.prepareStatement(valueSQL.updatePSQL());
        dbp = _addValueUpdateToPreparedStatement(objectId, value, dbp);
        retVal = Util.Math.addArray(dbp.executeBatch());

        if (retVal == 0) {
          throw new PersistanceException("Failed to persist Value instance to database.");
        }
        value.states().ioAction(IOAction.UPDATE);
        value.states().setModified(false);
      } catch (Exception e) {
         throw new PersistanceException("SQL Exception!", e);
      } finally {
        Util.DB.release(conn);
        Util.DB.releaseStatement(dbp);
        Util.DB.releaseRS(rs);
      }
      return retVal;
    }

    public int updateList(String objectId, ValueList vl) throws PersistanceException {
      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      PreparedStatement dbp = null;

      boolean autoCommit = true;
      try {
        // get a connection TODO: need to setup connection pool
        conn = Util.DB.__conn();
        autoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        dbp = conn.prepareStatement(valueSQL.updatePSQL());
        //create single list from value tree
        ValueList rvl = toList(vl);
        dbp = _addValueListUpdateToPreparedStatement(objectId, rvl, conn);
        retVal = Util.Math.addArray(dbp.executeBatch());
        conn.setAutoCommit(autoCommit);

        for(Value<?,?> v: rvl) {
          if(v.states().isModified()) {
            v.states().ioAction(IOAction.UPDATE);
            v.states().setModified(false);
          }
        }
      } catch (Exception e) {
        ValueList rvl = toList(vl);
        for(Value<?,?> v: rvl) {
          if(v.states().isModified()) {
            v.states().revertIOAction();
          }
        }
        throw new PersistanceException("Failed to persist updated values in list",e);
      } finally {
        Util.DB.__release(conn);
        Util.DB.releaseStatement(dbp);
        Util.DB.releaseRS(rs);
      }
      return retVal;
   }

    public int deleteById(String valueId) throws PersistanceException {
      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;
      try {
        // get a connection TODO: need to setup connection pool
        conn = Util.DB.__conn();
        dbs = conn.createStatement();
        retVal = dbs.executeUpdate(valueSQL.deleteByIdSQL(valueId));
        if(retVal == 0) {
          throw new PersistanceException("Failed to delete value with value id("+valueId+"). Perhaps no value with that value id has been persisted to the database yet.");
        }
      } catch (Exception e) {
        throw new PersistanceException("Failed to delete value!",e);
      } finally {
        Util.DB.__release(conn);
        Util.DB.releaseStatement(dbs);
        Util.DB.releaseRS(rs);
      }
      return retVal;
    }

    public int deleteList(ValueList vl) throws PersistanceException {
      ValueList dvl = toList(vl);
      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;
      boolean autoCommit = true;
      try {
        // get a connection TODO: need to setup connection pool
        conn = Util.DB.__conn();
        autoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        dbs = conn.createStatement();
        dbs = _addValuedDeleteToStatement(dvl, dbs);
        retVal = Util.Math.addArray(dbs.executeBatch());
        conn.setAutoCommit(autoCommit);
        if(retVal == 0) {
          throw new PersistanceException("No values from list were deleted. Perhaps no value with that value id has been persisted to the database yet.");
        }
      } catch (Exception e) {
        throw new PersistanceException("Failed to delete value!",e);
      } finally {
        Util.DB.__release(conn);
        Util.DB.releaseStatement(dbs);
        Util.DB.releaseRS(rs);
      }
      return retVal;
    }

    public int deleteByObjectId(String objectId) throws PersistanceException {
      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;
      try {
        // get a connection TODO: need to setup connection pool
        conn = Util.DB.__conn();
        conn.setAutoCommit(false);
        dbs = conn.createStatement();
        retVal = dbs.executeUpdate(valueSQL.deleteByObjectIdSQL(objectId));
        conn.setAutoCommit(true);
        if(retVal == 0) {
          throw new PersistanceException("No values from list were deleted. Perhaps no value with that value id has been persisted to the database yet.");
        }
      } catch (Exception e) {
        throw new PersistanceException("Failed to delete value!",e);
      } finally {
        Util.DB.__release(conn);
        Util.DB.releaseStatement(dbs);
        Util.DB.releaseRS(rs);
      }
      return retVal;
    }

    @SuppressWarnings("unchecked")
		public Q load(String objectId, String valueId) throws PersistanceException {
      Q value = null;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;
      try {
        // get a connection TODO: need to setup connection pool
        conn = Util.DB.__conn();
        dbs = conn.createStatement();
        rs = dbs.executeQuery(valueSQL.querySQL(objectId,valueId));
        if(rs.next()) {
          value = (Q) toValue(rs);
        } else {
          throw new PersistanceException("No value loaded. Perhaps no value with that value id has been persisted to the database yet.");
        }
      } catch (Exception e) {
        throw new PersistanceException("Failed to load value!",e);
      } finally {
        Util.DB.__release(conn);
        Util.DB.releaseStatement(dbs);
        Util.DB.releaseRS(rs);
      }
      return value;
    }

    public ValueList load(String objectId) throws PersistanceException {
      ValueList vl = new ValueList();
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;
      try {
        // get a connection TODO: need to setup connection pool
        conn = Util.DB.__conn();
        dbs = conn.createStatement();
        for(String table: CampSQL.System.value_tables) {
          rs = dbs.executeQuery("SELECT * FROM " +  table+ " WHERE `object_id`='"+objectId+"'");
          vl.addAll(toValueList(rs));
        }
        if(vl.size() == 0) {
          throw new PersistanceException("No values loaded. Perhaps no values with that objectId has been persisted to the database yet.");
        }
      } catch (Exception e) {
        throw new PersistanceException("Failed to load values!",e);
      } finally {
        Util.DB.__release(conn);
        Util.DB.releaseStatement(dbs);
        Util.DB.releaseRS(rs);
      }
      return vl;
    }

    public int createTables() throws SQLException {
      return _createTables();
    }

    public int clearTables() throws SQLException {
      return _clearTables();
    }

    public Value<?,?> toValue(ResultSet rs) throws SQLException {
      Value<?,?> value = Value.ValueFactory.generateValue(ValueType.valueOf(rs.getString(valueSQL.valueType.columnName())));
      value.parentId(rs.getString(valueSQL.parentId.columnName()), false);
      value.updateId(rs.getString(valueSQL.id.columnName()));
      value.setSelected(rs.getBoolean(valueSQL.selected.columnName()));
      value.position().update(rs.getInt(valueSQL.posX.columnName()), rs.getInt(valueSQL.posY.columnName()), rs.getInt(valueSQL.posZ.columnName()));
      value.setGroup(rs.getString(valueSQL.valueGroup.columnName()));
      return value.sqlHandler().rsToV(value,rs);
    }
    
    public ValueList toValueList(ResultSet rs) throws SQLException {
      ValueList vl = new ValueList();
      while(rs.next()){
        vl.add(toValue(rs));
      }
      if(vl.size() == 0) {
        throw new SQLException("ResultSet contained no value instances.");
      }
      return vl;
    }
    protected ValueList toList(ValueList valueList) {
      ValueList vl = new ValueList();
      for(Value<?,?> v: valueList) {
        vl.add(v);
        switch(v.type()) {
          case _complex:
            vl.addAll(toList(((ComplexValue)v).dataToValueList()));
            break;
          case _table:
            vl.addAll(toList(((TableValue)v).dataToValueList()));
            break;
          case _map:
            vl.addAll(toList(((MapValue)v).dataToValueList()));
            break;
          case _list:
            vl.addAll(toList(((ListValue)v).dataToValueList()));
            break;
          default:
            break;
        }
      }
      return vl;
    }

  }

  public class BooleanValuePersistHandler extends AbstractValuePersistHandler<Boolean,BooleanValue> {

    public String sqlValue(Value<?, ?> value) {
      return "'"+value.toValueString()+"'";
    }

    public Value<Boolean, BooleanValue> rsToV(Value<?, ?> value, ResultSet rs) throws SQLException {
      try {
        ((BooleanValue)value).updateData(Boolean.valueOf(rs.getString(valueSQL.valueData.columnName())), false);
      } catch (DataMismatchException e) {
        e.printStackTrace();
        throw new SQLException("DataMismatchException caught while genertating value from result set");
      }
      return (BooleanValue) value;
    }


  }

  public class ComplexValuePersistHandler extends AbstractValuePersistHandler<ValueListComplex,ComplexValue> {

    public String sqlValue(Value<?, ?> value) {
      return "NULL";
    }


    public Value<ValueListComplex, ComplexValue> rsToV(Value<?, ?> value, ResultSet rs) throws SQLException {
      // complex types get their data set later
      return (ComplexValue) value;
    }
  }

  public class MapValuePersistHandler extends AbstractValuePersistHandler<ValueComplex,MapValue> {

    public String sqlValue(Value<?, ?> value) {
      return "NULL";
    }


    public Value<ValueComplex, MapValue> rsToV(Value<?, ?> value, ResultSet rs) throws SQLException {
      // complex types get their data set later
      return (MapValue) value;
    }
  }

  public class ListValuePersistHandler extends AbstractValuePersistHandler<ValueList,ListValue> {

    public String sqlValue(Value<?, ?> value) {
      return "NULL";
    }


    public Value<ValueList, ListValue> rsToV(Value<?, ?> value, ResultSet rs) throws SQLException {
      // complex types get their data set later
        return (ListValue) value;
    }
  }

  public class DateTimeValuePersistHandler extends AbstractValuePersistHandler<DateTime,DateTimeValue> {
    public String sqlValue(Value<?, ?> value) {
      return "'"+value.toValueString()+"'";
    }


    public Value<DateTime, DateTimeValue> rsToV(Value<?, ?> value, ResultSet rs) throws SQLException {
      try {
        ((DateTimeValue)value).updateData(Util.Time.dateTimeFromString(valueSQL.valueData.columnName()), false);
      } catch (DataMismatchException e) {
        e.printStackTrace();
        throw new SQLException("DataMismatchException caught while genertating value from result set");
      }
      return (DateTimeValue) value;
    }

  }

  public class DateValuePersistHandler extends AbstractValuePersistHandler<DateTime,DateValue> {
    public String sqlValue(Value<?, ?> value) {
      return "'"+value.toValueString()+"'";
    }


    public Value<DateTime, DateValue> rsToV(Value<?, ?> value, ResultSet rs) throws SQLException {
      try {
        ((DateValue)value).updateData(Util.Time.dateTimeFromString(valueSQL.valueData.columnName()), false);
      } catch (DataMismatchException e) {
        e.printStackTrace();
        throw new SQLException("DataMismatchException caught while genertating value from result set");
      }
      return (DateValue) value;
    }

  }

  public class TimeValuePersistHandler extends AbstractValuePersistHandler<DateTime,TimeValue> {
    public String sqlValue(Value<?, ?> value) {
      return "'"+value.toValueString()+"'";
    }

    public Value<DateTime, TimeValue> rsToV(Value<?, ?> value, ResultSet rs) throws SQLException {
      try {
        ((TimeValue)value).updateData(Util.Time.dateTimeFromString(valueSQL.valueData.columnName()), false);
      } catch (DataMismatchException e) {
        e.printStackTrace();
        throw new SQLException("DataMismatchException caught while genertating value from result set");
      }
      return (TimeValue) value;
    }

  }

  public class IntegerValuePersistHandler extends AbstractValuePersistHandler<Integer,IntegerValue> {
    public String sqlValue(Value<?, ?> value) {
      return "'"+value.toValueString()+"'";
    }

    public Value<Integer, IntegerValue> rsToV(Value<?, ?> value, ResultSet rs) throws SQLException {
      try {
        ((IntegerValue)value).updateData(Integer.valueOf(valueSQL.valueData.columnName()), false);
      } catch (DataMismatchException e) {
        e.printStackTrace();
        throw new SQLException("DataMismatchException caught while genertating value from result set");
      }
      return (IntegerValue) value;
    }


  }

  public class StringValuePersistHandler extends AbstractValuePersistHandler<String,StringValue> {
    public String sqlValue(Value<?, ?> value) {
      return "'"+value.toValueString()+"'";
    }

    public Value<String, StringValue> rsToV(Value<?, ?> value, ResultSet rs) throws SQLException {
      try {
        ((StringValue)value).updateData(valueSQL.valueData.columnName(), false);
      } catch (DataMismatchException e) {
        e.printStackTrace();
        throw new SQLException("DataMismatchException caught while genertating value from result set");
      }
      return (StringValue) value;
    }

  }

  public class TableValuePersistHandler extends AbstractValuePersistHandler<ValueTable,TableValue> {
    public String sqlValue(Value<?, ?> value) {
      return "NULL";
    }

    public Value<ValueTable, TableValue> rsToV(Value<?, ?> value, ResultSet rs) throws SQLException {
      return (TableValue) value;
    }
  }

  // TODO: do we need?
  public class BinaryValuePersistHandler extends AbstractValuePersistHandler<Binary,BinaryValue> {
    public String sqlValue(Value<?, ?> value) {
      return "'"+value.toValueString()+"'";
    }

    public Value<Binary, BinaryValue> rsToV(Value<?, ?> value, ResultSet rs) throws SQLException {
      try {
        ((BinaryValue)value).updateData(new Binary(valueSQL.valueData.columnName()), false);
      } catch (DataMismatchException e) {
        e.printStackTrace();
        throw new SQLException("DataMismatchException caught while genertating value from result set");
      }
      return (BinaryValue) value;
    }


  }

  public class TextValuePersistHandler extends AbstractValuePersistHandler<String, TextValue> {
    public String sqlValue(Value<?, ?> value) {
      return "'"+value.toValueString()+"'";
    }

    public Value<String, TextValue> rsToV(Value<?, ?> value, ResultSet rs) throws SQLException {
      try {
        ((TextValue)value).updateData(valueSQL.valueData.columnName(), false);
      } catch (DataMismatchException e) {
        e.printStackTrace();
        throw new SQLException("DataMismatchException caught while genertating value from result set");
      }
      return (TextValue) value;
    }
  }

  public class EnumValuePersistHandler extends AbstractValuePersistHandler<String, EnumValue> {
    public String sqlValue(Value<?, ?> value) {
      return "'"+value.toValueString()+"'";
    }

    public Value<String, EnumValue> rsToV(Value<?, ?> value, ResultSet rs) throws SQLException {
      try {
        ((EnumValue)value).updateData(valueSQL.valueData.columnName(), false);
      } catch (DataMismatchException e) {
        e.printStackTrace();
        throw new SQLException("DataMismatchException caught while genertating value from result set");
      }
      return (EnumValue) value;
    }
  }

  public class SetValuePersistHandler extends AbstractValuePersistHandler<String, SetValue> {
    public String sqlValue(Value<?, ?> value) {
      return "'"+value.toValueString()+"'";
    }

    public Value<String, SetValue> rsToV(Value<?, ?> value, ResultSet rs) throws SQLException {
      try {
        ((SetValue)value).updateData(valueSQL.valueData.columnName(), false);
      } catch (DataMismatchException e) {
        e.printStackTrace();
        throw new SQLException("DataMismatchException caught while genertating value from result set");
      }
      return (SetValue) value;
    }
  }


  public class TimestampValuePersistHandler extends AbstractValuePersistHandler<Timestamp, TimestampValue> {
    public String sqlValue(Value<?, ?> value) {
      return "'"+value.toValueString()+"'";
    }

    public Value<Timestamp, TimestampValue> rsToV(Value<?, ?> value, ResultSet rs) throws SQLException {
      try {
        ((TimestampValue)value).updateData(Util.Time.timestamp(valueSQL.valueData.columnName()), false);
      } catch (DataMismatchException e) {
        e.printStackTrace();
        throw new SQLException("DataMismatchException caught while genertating value from result set");
      }
      return (TimestampValue) value;
    }

}

}
	