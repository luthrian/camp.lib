package com.camsolute.code.camp.lib.contract.value;

import java.sql.Timestamp;

import org.joda.time.DateTime;

import com.camsolute.code.camp.lib.contract.value.Value;
import com.camsolute.code.camp.lib.contract.value.Value.AbstractValue;
import com.camsolute.code.camp.lib.contract.value.Value.BooleanValue;
import com.camsolute.code.camp.lib.contract.value.Value.ValueType;
import com.camsolute.code.camp.lib.data.CampSQL;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;
import com.camsolute.code.camp.lib.utilities.Util;

public interface SQLValueHandler {


  public static int DB_INDEX = CampSQL._ORDER_TABLES_DB_INDEX;

  public  String insertValue(int objectId, Value value);

  public String table();

  public String[][] tableDefinition();

  public Value create(int objectId, ValueType type, String valueGroup, Object value, int posX, int posY, int posZ, boolean selected);

  public Value save(int objectId, Value v);

  public ValueList saveList(int objectId, ValueList vl);
  
  public int update(int objectId, Value v);
  
  public int updateList(int objectId, ValueList vl);
  
  public int delete(int valueId, ValueType type);
  
  public int delete(Value v);
  
  public int deleteList(ValueList vl);
  
  public int deleteList(int objectId);
  
  public ValueList load(int objectId);

  public static String _insertValue(int objectId, Value value, String stringValue) {
    return objectId   + ",'" + value.type().name() + "','" + value.group().name() + "'" + "," + value.selected() + "," + value.position().posX()
        + "," + value.position().posY() + "," + value.position().posZ() + ","+stringValue;
  }

  public class BooleanSQLValueHandler implements SQLValueHandler {

    public String insertValue(int objectId, Value value) {
      return _insertValue(objectId,value,value.toString());
    }
     public String table() {
       return CampSQL.sysTable(DB_INDEX, CampSQL.System._BOOLEAN_VALUE_INDEX);
     }

     public String[][] tableDefinition() {
       return CampSQL.System._boolean_value_table_definition;
     }
  }

  public class ComplexSQLValueHandler implements SQLValueHandler {

    public String insertValue(int objectId, Value value) {
      return _insertValue(objectId,value, "'NULL'");
    }

    public String table() {
      return CampSQL.sysTable(DB_INDEX,CampSQL.System._COMPLEX_VALUE_INDEX);
    }

    public String[][] tableDefinition() {
      return CampSQL.System._complex_value_table_definition;
    }
  }

  public class DateTimeSQLValueHandler implements SQLValueHandler {
    public String insertValue(int objectId, Value value) {
      return _insertValue(objectId,value,"'"+Util.Time.timestampFromString(value.toString()).toString()+"'");
    }

    public String table() {
      return CampSQL.sysTable(DB_INDEX,CampSQL.System._TIMESTAMP_VALUE_INDEX);
    }

    public String[][] tableDefinition() {
      return CampSQL.System._timestamp_value_table_definition;
    }

  }

  public class IntegerSQLValueHandler implements SQLValueHandler {
    public String insertValue(int objectId, Value value) {
      return _insertValue(objectId,value, value.toString());
    }
    public String table() {
      return CampSQL.sysTable(DB_INDEX,CampSQL.System._INTEGER_VALUE_INDEX);
    }

    public String[][] tableDefinition() {
      return CampSQL.System._integer_value_table_definition;
    }


  }

  public class StringSQLValueHandler implements SQLValueHandler {
    public String insertValue(int objectId, Value value) {
      return _insertValue(objectId,value,"'"+value.toString()+"'");
    }
    public String table() {
      return CampSQL.sysTable(DB_INDEX,CampSQL.System._STRING_VALUE_INDEX);
    }

    public String[][] tableDefinition() {
      return CampSQL.System._string_value_table_definition;
    }

  }

  //TODO: implement
  public class TableSQLValueHandler implements SQLValueHandler {
    public String insertValue(int objectId, Value value) {
      return _insertValue(objectId,value, "'NULL'");
    }
    public String table() {
      return CampSQL.sysTable(DB_INDEX,CampSQL.System._COMPLEX_VALUE_INDEX);
    }

    public String[][] tableDefinition() {
      return CampSQL.System._complex_value_table_definition;
    }
  }

  //TODO: use or remove
  public class BinarySQLValueHandler implements SQLValueHandler {
    public String insertValue(int objectId, Value value) {
      return _insertValue(objectId,value, "'"+value.toString()+"'");
    }
    public String table() {
      return CampSQL.sysTable(DB_INDEX,CampSQL.System._BLOB_VALUE_INDEX);
    }

    public String[][] tableDefinition() {
      return CampSQL.System._blob_value_table_definition;
    }
  }

  public class TextSQLValueHandler implements SQLValueHandler {
    public String insertValue(int objectId, Value value) {
      return _insertValue(objectId,value,"'"+value.toString()+"'");
    }
    public String table() {
      return CampSQL.sysTable(DB_INDEX,CampSQL.System._TEXT_VALUE_INDEX);
    }

    public String[][] tableDefinition() {
      return CampSQL.System._text_value_table_definition;
    }
  }

  public class TimestampSQLValueHandler implements SQLValueHandler {
    public String insertValue(int objectId, Value value) {
      return _insertValue(objectId,value, "'"+value.toString()+"'");
    }
    public String table() {
      return CampSQL.sysTable(DB_INDEX,CampSQL.System._TIMESTAMP_VALUE_INDEX);
    }

    public String[][] tableDefinition() {
      return CampSQL.System._timestamp_value_table_definition;
    }

}

}
