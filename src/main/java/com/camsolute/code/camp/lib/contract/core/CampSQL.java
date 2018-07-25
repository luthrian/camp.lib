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
 *  Christopher Campbell - all code prior and post initial release
 ******************************************************************************/
package com.camsolute.code.camp.lib.contract.core;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import com.camsolute.code.camp.lib.contract.value.Value.ValueType;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;
import com.camsolute.code.camp.lib.utilities.Util;
import com.camsolute.code.camp.lib.utilities.Util.DB.dbActionType;


public class CampSQL {

  public static enum dbActionType {
    CREATE, INSERT, PINSERT, UPDATE, UPDATEB, SELECT, DELETE, MSELECT;
  }

  public static final String _DBLINK = Util.Config.instance().properties().getProperty("database.link");
  public static final String _USER = Util.Config.instance().properties().getProperty("database.user");
  public static final String _PASSWORD = Util.Config.instance().properties().getProperty("database.password");

  public static final int _UPDATES_TABLE_ID_INDEX = 1;// all updated must set the updated object identifier at this position

  public static final int _CUSTOMER_DB_INDEX = 0;
  public static final int _PROCESS_DB_INDEX = 1;
  public static final int _ORDER_DB_INDEX = 2;
  public static final int _PRODUCT_DB_INDEX = 3;
  public static final int _ORDER_TABLES_DB_INDEX = 4;
  public static final int _SUPPORT_TABLES_DB_INDEX = 5;
  public static final int _SYSTEM_TABLES_DB_INDEX = 6;
  public static final int _LOGGING_TABLES_DB_INDEX = 7;

  public static final String[] database = {
      "customer_management",	//0
      "process_management",	//1
      "order_management",		//2
      "product_management",	//3
      "order_tables",			//4
      "support_tables",		//5
      "system_tables",		//6
      "logging_tables",		//7
      Util.DB._NO_DB_TABLE
  };

  public static final int _FIELD_DBTS_INDEX = 0;
  public static final int _TYPE_DBTS_INDEX = 1;
  public static final int _NULL_DBTS_INDEX = 2;
  public static final int _KEY_DBTS_INDEX = 3;
  public static final int _DEFAULT_DBTS_INDEX = 4;
  public static final int _EXTRA_DBTS_INDEX = 5;
  public static final String[] database_table_structure = {
      "Field",
      "Type",
      "Null",
      "Key",
      "Default",
      "Extra"
  };



  public static class Logging {

    public static final int _INSTANCE_LOG_INDEX = 0;

    public static final String[] logging_tables = {
        "_instance_log", //0
    };

    public static final String[][] _instance_log_table_definition = {
        {"_log_id_", "VARCHAR(45) NOT NULL"},
        {"_object_id", "INT(11) NOT NULL"},
        {"_object_type", "VARCHAR(100) NOT NULL"},
        {"_object_business_id", "VARCHAR(100) NOT NULL"},
        {"_object_businesskey", "VARCHAR(100) NOT NULL"},
        {"_instance_id", "varchar(45) NOT NULL"},
        {"_current_instance_id", "varchar(45) NOT NULL"},
        {"_initial_instance_id", "varchar(45) NOT NULL"},
        {"_status", "varchar(45) NOT NULL"},
        {"_group_name", "varchar(45) NOT NULL DEFAULT 0"},
        {"_version_value", "varchar(45) NOT NULL DEFAULT 0"},
        {"_log_timestamp", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"_timestamp", "timestamp NOT NULL"},
        {"_date", "timestamp NOT NULL"},
        {"_end_of_life", "timestamp NOT NULL"},
        {"_object_json", "LONGTEXT NULL"},
        {"extra",  "PRIMARY KEY (`_log_id_`)"
          + ",UNIQUE KEY `_i_instance_id_UNIQUE` (`_instance_id`) "
          + ",INDEX `_i_object_business_id_idx` (`_object_business_id` ASC)"
          + ",INDEX `_i_instance_id_idx` (`_instance_id` ASC)"}
    };

    public static enum instanceLogTableDefinition {
        logId( new String[]{"_log_id_", "VARCHAR(45) NOT NULL","1"}),
        objectId( new String[]{"_object_id", "INT(11) NOT NULL","2"}),
        objectType( new String[]{"_object_type", "VARCHAR(100) NOT NULL","3"}),
        objectBusinessId( new String[]{"_object_business_id", "VARCHAR(100) NOT NULL","4"}),
        objectBusinessKey( new String[]{"_object_businesskey", "VARCHAR(100) NOT NULL","5"}),
        instanceId( new String[]{"_instance_id", "varchar(45) NOT NULL","6"}),
        currentInstanceId( new String[]{"_current_instance_id", "varchar(45) NOT NULL","7"}),
        initialInstanceId( new String[]{"_initial_instance_id", "varchar(45) NOT NULL","8"}),
        status( new String[]{"_status", "varchar(45) NOT NULL","9"}),
        groupName( new String[]{"_group_name", "varchar(45) NOT NULL DEFAULT 0","10"}),
        versionValue( new String[]{"_version_value", "varchar(45) NOT NULL DEFAULT 0","11"}),
        logTimestamp( new String[]{"_log_timestamp", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP","12"}),
        timestamp( new String[]{"_timestamp", "timestamp NOT NULL","13"}),
        date( new String[]{"_date", "timestamp NOT NULL","14"}),
        endOfLife( new String[]{"_end_of_life", "timestamp NOT NULL","15"}),
        objectJson( new String[]{"_object_json", "LONGTEXT NULL","16"}),
        extra( new String[]{"extra",  "PRIMARY KEY (`_log_id_`)"
            + ",UNIQUE KEY `_i_instance_id_UNIQUE` (`_instance_id`) "
            + ",INDEX `_i_object_business_id_idx` (`_object_business_id` ASC)"
            + ",INDEX `_i_instance_id_idx` (`_instance_id` ASC)","0"});
      public static String dbName = "logging_tables";
      public static String tableName = "_instance_log";
      private String columnName;
      private String definition;
      private int columnIndex;
      private instanceLogTableDefinition(String[] def) {
        columnName = def[0];
        definition = def[1];
        columnIndex = Integer.valueOf(def[2]);
      }
      public String columnName() { return columnName; }
      public String definition() { return definition; }
      public int columnIndex() {return columnIndex; }
      @Override
      public String toString() {
        return columnName;
      }
      public static String createSQL() {
        return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+logId+"` "+logId.definition()+",`"+objectId+"` "+objectId.definition()+",`"+objectType+"` "+objectType.definition()+
            ",`"+objectBusinessId+"` "+objectBusinessId.definition()+",`"+objectBusinessKey+"` "+objectBusinessKey.definition()+",`"+instanceId+"` "+instanceId.definition()+",`"+currentInstanceId+"` "+currentInstanceId.definition()+",`"+initialInstanceId+"` "+initialInstanceId.definition()+
            ",`"+status+"` "+status.definition()+",`"+groupName+"` "+groupName.definition()+",`"+versionValue+"` "+versionValue.definition()+",`"+logTimestamp+"` "+logTimestamp.definition()+",`"+timestamp+"` "+timestamp.definition()+
            ",`"+date+"` "+date.definition()+",`"+endOfLife+"` "+endOfLife.definition()+",`"+objectJson+"` "+objectJson.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
      }
      public static String updateSQL() {
        return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+logId+"`=?"+",`"+objectId+"`=?"+",`"+objectType+"`=?"+",`"
            +objectBusinessId+"`=?"+",`"+objectBusinessKey+"`=?"+",`"+instanceId+"`=?"+",`"+currentInstanceId+"`=?"+",`"+initialInstanceId+"`=?"+
    ",`"+status+"`=?"+",`"+groupName+"`=?"+",`"+versionValue+"`=?"+",`"+logTimestamp+"`=?"+",`"+timestamp+"`=?"+
    ",`"+date+"`=?"+",`"+endOfLife+"`=?"+",`"+objectJson+"`=? WHERE ";
      }

      public static String insertPSQL() {
        return "INSERT INTO `"+dbName+"`.`"+tableName+"`  ("+"`"+logId+"`"+",`"+objectId+"`"+",`"+objectType+"`"
            +",`"+objectBusinessId+"`"+",`"+objectBusinessKey+"`"+",`"+instanceId+"`"+",`"+currentInstanceId+"`"+",`"+initialInstanceId+"`"
            +",`"+status+"`"+",`"+groupName+"`"+",`"+versionValue+"`"+",`"+logTimestamp+"`"+",`"+timestamp+"`"
            +",`"+date+"`"+",`"+endOfLife+"`"+",`"+objectJson+"`) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) ";
      }

      public static String clearSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
      }
      public static String deleteSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
      }

    };
  }
  //refactor 1
  public static class System {

    public static final int _ATTRIBUTE_TYPE_INDEX = 0;
    public static final int _ATTRIBUTE_VALUE_INDEX = 1;
    public static final int _ATTRIBUTE_UPDATES_INDEX = 2;
    public static final int _ATTRIBUTE_HAS_PROCESS_INDEX = 3;
    public static final int _INTEGER_VALUE_INDEX = 4;
    public static final int _STRING_VALUE_INDEX = 5;
    public static final int _TIMESTAMP_VALUE_INDEX = 6;
    public static final int _BOOLEAN_VALUE_INDEX = 7;
    public static final int _TEXT_VALUE_INDEX = 8;
    public static final int _COMPLEX_VALUE_INDEX = 9;
    public static final int _BLOB_VALUE_INDEX = 10;
    public static final int _INSTANCE_INDEX = 11;

    public static final String[] system_tables = {
        "_attribute_type", //0
        "_attribute_value", //1
        "_attribute_updates",//2
        "_attribute_has_process",//3
        "_integer_value", //4
        "_string_value", //5
        "_timestamp_value", //6
        "_boolean_value", //7
        "_text_value", //8
        "_complex_value", //9
        "_blob_value", //10
        "_instance",//11
    };

    /**
     * table for the definition aspects of an attribute
     */
    public static final String[][] _attribute_type_table_definition = {
        {"_attribute_type_id_", "VARCHAR(45) NOT NULL"},
        {"name", "varchar(100) NOT NULL"},
        {"type", "varchar(45) NOT NULL"},
        {"parent_id", "VARCHAR(45) NOT NULL"},
        {"default_value", "LONGTEXT NULL"},
        {"business_id", "varchar(100) NOT NULL"},
        {"businesskey", "varchar(45) NULL"},
        {"group", "varchar(45) NOT NULL"},
        {"version", "varchar(45) NOT NULL"},
        {"position", "int(11) NULL"},
        {"extra", "PRIMARY KEY (`_attribute_type_id_`)"
            + ", INDEX `_at_name_UNIQUE` (`name` ASC) "
            + ", INDEX `_at_parent_id_idx` (`parent_id` ASC)"
            + ", INDEX `_at_group_idx` (`group` ASC)"
            + ", INDEX `_at_business_id_idx` (`business_id` ASC)"}
    };
    /**
     * table for the associative value aspects of an attribute
     */
    public static final String[][] _attribute_value_table_definition = {
        {"_attribute_value_id_", "VARCHAR(45) NOT NULL"},
        {"object_id", "VARCHAR(45) NOT NULL"},
        {"attribute_type_id", "VARCHAR(45) NOT NULL"},
        {"parent_type_id", "VARCHAR(45) NOT NULL"},
        {"attribute_parent_id", "VARCHAR(45) NOT NULL"},
        {"value_id", "VARCHAR(45) NOT NULL"},
        {"attribute_businesskey", "varchar(45) NOT NULL"},
        {"attribute_group", "varchar(45) NOT NULL"},
        {"attribute_position", "int(11) NULL"},
        {"extra", "PRIMARY KEY (`_attribute_value_id_`) "
         + ",index `_av_object_id_idx` (`object_id` asc)"
         + ",index `_av_attribute_parent_id_idx` (`attribute_parent_id` asc)"
         + ",index `_av_attribute_businesskey_idx` (`attribute_businesskey` asc)"
         + ",index `_av_attribute_group_idx` (`attribute_group` asc)"
         + ",index `_av_attribute_type_id_idx` (`attribute_type_id` asc)"
         + ",index `_av_parent_type_id_idx` (`parent_type_id` asc)"}
    };
    public static final String[][] _attribute_updates_table_definition = {
        {"_au_attribute_value_id", "VARCHAR(45) NOT NULL"},
//				{"_au_object_id", "VARCHAR(45) NOT NULL"},
        {"_au_businesskey", "VARCHAR(45) NOT NULL"},
        {"_au_target", "varchar(45) NOT NULL"},
        {"extra", "INDEX `au_target_idx` (`_au_target` ASC)"
          +",INDEX `au_businesskey_idx` (`_au_businesskey` ASC)"}
    };
    public static final String[][] _attribute_has_process_table_definition = {
        {"_ahp_attribute_business_id", "varchar(100) NOT NULL"},
        {"_ahp_business_id", "varchar(100) NOT NULL"},
        {"_ahp_process_instance_id", "varchar(45) NOT NULL"},
        {"_ahp_process_key", "varchar(45) NOT NULL"},
        {"extra", "INDEX `ahp_attribute_business_id_idx` (`_ahp_attribute_business_id` ASC)"
          +",INDEX `ahp_business_id_idx` (`_ahp_business_id` ASC)"
            + ",INDEX `ahp_process_key_idx` (`_ahp_process_key` ASC)"
            + ",KEY `fk_ahp_process2_idx` (`_ahp_process_instance_id`)"
            + ",KEY `fk_ahp_order2_idx` (`_ahp_business_id`)"}
    };
    /**
     * table for the concrete value aspects of an attribute
     * Note: an associative tag was added for indexing
     */
      public static final String[][] _blob_value_table_definition = {
          {"_value_id_", "VARCHAR(45) NOT NULL"},
          {"object_id", "VARCHAR(45) NOT NULL"},
          {"parent_id", "VARCHAR(45) NULL"},
          {"value_type", "varchar(45) NOT NULL"},
          {"value_group", "varchar(45) NOT NULL"},
          {"selected", "boolean NULL DEFAULT false"},
          {"pos_x", "int(11) NULL DEFAULT 0"},
          {"pos_y", "int(11) NULL DEFAULT 0"},
          {"pos_z", "int(11) NULL DEFAULT 0"},
          {"value", "MEDIUMBLOB NOT NULL"},
          {"extra", "PRIMARY KEY (`_value_id_`), UNIQUE INDEX `_v_value_id_UNIQUE` (`_value_id_` ASC)"
           + ",index `_av_pos_y_idx` (`pos_y` asc)"
           + ",index `_av_object_id_idx` (`object_id` asc)"}
      };
      public static final String[][] _complex_value_table_definition = {
          {"_value_id_", "VARCHAR(45) NOT NULL"},
          {"object_id", "VARCHAR(45) NOT NULL"},
          {"parent_id", "VARCHAR(45) NULL"},
          {"value_type", "varchar(45) NOT NULL"},
          {"value_group", "varchar(45) NOT NULL"},
          {"selected", "boolean NULL DEFAULT false"},
          {"pos_x", "int(11) NULL DEFAULT 0"},
          {"pos_y", "int(11) NULL DEFAULT 0"},
          {"pos_z", "int(11) NULL DEFAULT 0"},
          {"value", "VARCHAR(400) NOT NULL"},
          {"extra", "PRIMARY KEY (`_value_id_`), UNIQUE INDEX `_v_value_id_UNIQUE` (`_value_id_` ASC)"
           + ",index `_av_pos_y_idx` (`pos_y` asc)"
           + ",index `_av_object_id_idx` (`object_id` asc)"}
      };
      public static final String[][] _text_value_table_definition = {
          {"_value_id_", "VARCHAR(45) NOT NULL"},
          {"object_id", "VARCHAR(45) NOT NULL"},
          {"parent_id", "VARCHAR(45) NULL"},
          {"value_type", "varchar(45) NOT NULL"},
          {"value_group", "varchar(45) NOT NULL"},
          {"selected", "boolean NULL DEFAULT false"},
          {"pos_x", "int(11) NULL DEFAULT 0"},
          {"pos_y", "int(11) NULL DEFAULT 0"},
          {"pos_z", "int(11) NULL DEFAULT 0"},
          {"value", "TEXT NOT NULL"},
          {"extra", "PRIMARY KEY (`_value_id_`), UNIQUE INDEX `_v_value_id_UNIQUE` (`_value_id_` ASC)"
           + ",index `_av_pos_y_idx` (`pos_y` asc)"
           + ",index `_av_object_id_idx` (`object_id` asc)"}
      };
      public static final String[][] _boolean_value_table_definition = {
          {"_value_id_", "VARCHAR(45) NOT NULL"},
          {"object_id", "VARCHAR(45) NOT NULL"},
          {"parent_id", "VARCHAR(45) NULL"},
          {"value_type", "varchar(45) NOT NULL"},
          {"value_group", "varchar(45) NOT NULL"},
          {"selected", "boolean NULL DEFAULT false"},
          {"pos_x", "int(11) NULL DEFAULT 0"},
          {"pos_y", "int(11) NULL DEFAULT 0"},
          {"pos_z", "int(11) NULL DEFAULT 0"},
          {"value", "BOOLEAN NOT NULL"},
          {"extra", "PRIMARY KEY (`_value_id_`), UNIQUE INDEX `_v_value_id_UNIQUE` (`_value_id_` ASC)"
           + ",index `_av_pos_y_idx` (`pos_y` asc)"
           + ",index `_av_object_id_idx` (`object_id` asc)"}
      };
      public static final String[][] _timestamp_value_table_definition = {
          {"_value_id_", "VARCHAR(45) NOT NULL"},
          {"object_id", "VARCHAR(45) NOT NULL"},
          {"parent_id", "VARCHAR(45) NULL"},
          {"value_type", "varchar(45) NOT NULL"},
          {"value_group", "varchar(45) NOT NULL"},
          {"selected", "boolean NULL DEFAULT false"},
          {"pos_x", "int(11) NULL DEFAULT 0"},
          {"pos_y", "int(11) NULL DEFAULT 0"},
          {"pos_z", "int(11) NULL DEFAULT 0"},
          {"value", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
          {"extra", "PRIMARY KEY (`_value_id_`), UNIQUE INDEX `_v_value_id_UNIQUE` (`_value_id_` ASC)"
           + ",index `_av_pos_y_idx` (`pos_y` asc)"
           + ",index `_av_object_id_idx` (`object_id` asc)"}
      };
      public static final String[][] _string_value_table_definition = {
          {"_value_id_", "VARCHAR(45) NOT NULL"},
          {"object_id", "VARCHAR(45) NOT NULL"},
          {"parent_id", "VARCHAR(45) NULL"},
          {"value_type", "varchar(45) NOT NULL"},
          {"value_group", "varchar(45) NOT NULL"},
          {"selected", "boolean NULL DEFAULT false"},
          {"pos_x", "int(11) NULL DEFAULT 0"},
          {"pos_y", "int(11) NULL DEFAULT 0"},
          {"pos_z", "int(11) NULL DEFAULT 0"},
          {"value", "VARCHAR(100) NOT NULL"},
          {"extra", "PRIMARY KEY (`_value_id_`), UNIQUE INDEX `_v_value_id_UNIQUE` (`_value_id_` ASC)"
           + ",index `_av_pos_y_idx` (`pos_y` asc)"
           + ",index `_av_object_id_idx` (`object_id` asc)"}
      };
      public static final String[][] _integer_value_table_definition = {
          {"_value_id_", "VARCHAR(45) NOT NULL"},
          {"object_id", "VARCHAR(45) NOT NULL"},
          {"parent_id", "VARCHAR(45) NULL"},
          {"value_type", "varchar(45) NOT NULL"},
          {"value_group", "varchar(45) NOT NULL"},
          {"selected", "boolean NULL DEFAULT false"},
          {"pos_x", "int(11) NULL DEFAULT 0"},
          {"pos_y", "int(11) NULL DEFAULT 0"},
          {"pos_z", "int(11) NULL DEFAULT 0"},
          {"value", "int(11) NOT NULL"},
          {"extra", "PRIMARY KEY (`_value_id_`), UNIQUE INDEX `_v_value_id_UNIQUE` (`_value_id_` ASC)"
           + ",index `_av_pos_y_idx` (`pos_y` asc)"
           + ",index `_av_object_id_idx` (`object_id` asc)"}
      };

      public static String[] value_tables = {
        CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._STRING_VALUE_INDEX),
        CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._INTEGER_VALUE_INDEX),
        CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._TIMESTAMP_VALUE_INDEX),
        CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._BOOLEAN_VALUE_INDEX),
        CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._BLOB_VALUE_INDEX),
        CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._TEXT_VALUE_INDEX),
        CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._COMPLEX_VALUE_INDEX)
      };

      public static EnumMap<ValueType,String> attribute_value_tables = null;
      static {
        attribute_value_tables = new EnumMap<ValueType,String>(ValueType.class);

        attribute_value_tables.put(ValueType._integer,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._INTEGER_VALUE_INDEX));
        attribute_value_tables.put(ValueType._string,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._STRING_VALUE_INDEX));
        attribute_value_tables.put(ValueType._datetime,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._TIMESTAMP_VALUE_INDEX));
          attribute_value_tables.put(ValueType._timestamp,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._TIMESTAMP_VALUE_INDEX));
          attribute_value_tables.put(ValueType._boolean,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._BOOLEAN_VALUE_INDEX));
          attribute_value_tables.put(ValueType._time,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._TIMESTAMP_VALUE_INDEX));
          attribute_value_tables.put(ValueType._date,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._TIMESTAMP_VALUE_INDEX));
          attribute_value_tables.put(ValueType._text,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._TEXT_VALUE_INDEX));
          attribute_value_tables.put(ValueType._enum,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._COMPLEX_VALUE_INDEX));
          attribute_value_tables.put(ValueType._set,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._COMPLEX_VALUE_INDEX));
          attribute_value_tables.put(ValueType._table,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._COMPLEX_VALUE_INDEX));
          attribute_value_tables.put(ValueType._complex,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._COMPLEX_VALUE_INDEX));
          attribute_value_tables.put(ValueType._list,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._COMPLEX_VALUE_INDEX));
          attribute_value_tables.put(ValueType._map,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._COMPLEX_VALUE_INDEX));
      }

      public static EnumMap<ValueType,String[][]> attribute_value_table_def = null;
      static {
        attribute_value_table_def = new EnumMap<ValueType,String[][]>(ValueType.class);

        attribute_value_table_def.put(ValueType._integer,_integer_value_table_definition);
        attribute_value_table_def.put(ValueType._string,_string_value_table_definition);
        attribute_value_table_def.put(ValueType._datetime,_timestamp_value_table_definition);
        attribute_value_table_def.put(ValueType._timestamp,_timestamp_value_table_definition);
        attribute_value_table_def.put(ValueType._boolean,_boolean_value_table_definition);
        attribute_value_table_def.put(ValueType._time,_timestamp_value_table_definition);
        attribute_value_table_def.put(ValueType._date,_timestamp_value_table_definition);
        attribute_value_table_def.put(ValueType._text,_text_value_table_definition);
        attribute_value_table_def.put(ValueType._enum,_complex_value_table_definition);
        attribute_value_table_def.put(ValueType._set,_complex_value_table_definition);
        attribute_value_table_def.put(ValueType._table,_complex_value_table_definition);
        attribute_value_table_def.put(ValueType._complex,_complex_value_table_definition);
        attribute_value_table_def.put(ValueType._list,_complex_value_table_definition);
        attribute_value_table_def.put(ValueType._map,_complex_value_table_definition);
      };

    public static final String[][] _instance_table_definition = {
        {"_object_id", "INT(11) NOT NULL"},
        {"_object_business_id", "VARCHAR(100) NOT NULL"},
        {"_object_ref_id", "INT(11) NOT NULL"},
        {"_instance_id", "varchar(45) NOT NULL"},
        {"_current_instance_id", "varchar(45) NOT NULL"},
        {"_initial_instance_id", "varchar(45) NOT NULL"},
        {"_status", "varchar(45) NOT NULL"},
        {"_timestamp", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"_date", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"_end_of_life", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},//DATE_ADD(CURRENT_TIMESTAMP,INTERVAL 10 YEAR)"},
        {"_version_value", "VARCHAR(45) NOT NULL"},
        {"_group_name", "VARCHAR(100) NOT NULL"},
        {"extra", "UNIQUE INDEX `_i_instance_id_UNIQUE` (`_instance_id` ASC)"
          + ",INDEX `_i_object_business_id_idx` (`_object_business_id` ASC)"
          + ",INDEX `_i_instance_id_idx` (`_instance_id` ASC)"
          + ",INDEX `_i_object_id_idx` (`_object_id` ASC)"}
    };

    public static enum attributeTypeTableDefinition {
        attributeTypeId(new String[]{"_attribute_type_id_", "VARCHAR(45) NOT NULL","1"}),
        name(new String[]{"name", "varchar(100) NOT NULL","2"}),
        type(new String[]{"type", "varchar(45) NOT NULL","3"}),
        parentId(new String[]{"parent_id", "VARCHAR(45) NOT NULL","4"}),
        defaultValue(new String[]{"default_value", "LONGTEXT NULL","5"}),
        businessId(new String[]{"business_id", "varchar(100) NOT NULL","6"}),
        businessKey(new String[]{"businesskey", "varchar(45) NULL","7"}),
        group(new String[]{"group", "varchar(45) NOT NULL","8"}),
        version(new String[]{"version", "varchar(45) NOT NULL","9"}),
        position(new String[]{"position", "int(11) NULL","10"}),
        extra(new String[]{"extra", "PRIMARY KEY (`_attribute_type_id_`)"
            + ", INDEX `_at_name_UNIQUE` (`name` ASC) "
            + ", INDEX `_at_parent_id_idx` (`parent_id` ASC)"
            + ", INDEX `_at_group_idx` (`group` ASC)"
            + ", INDEX `_at_business_id_idx` (`business_id` ASC)","0"});
          public static String dbName = "system_tables";
          public static String tableName = "_attribute_type";
          private String columnName;
          private String definition;
          private int columnIndex;
          private attributeTypeTableDefinition(String[] def) {
            columnName = def[0];
            definition = def[1];
            columnIndex = Integer.valueOf(def[2]);
          }
          public String columnName() { return columnName; }
          public String definition() { return definition; }
          public int columnIndex() {return columnIndex; }
          @Override
          public String toString() {
            return columnName;
          }

          public static String insertPSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+attributeTypeId+"`"+",`"+name+"`"+",`"+type+"`"+",`"+parentId+"`"+",`"+defaultValue+"`"+",`"+businessId+"`"
                +",`"+businessKey+"`"+",`"+group+"`"+",`"+version+"`"+",`"+position+"`) VALUES (?,?,?,?,?,?,?,?,?,?) ";
          }
          public static String createSQL() {
            return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+attributeTypeId+"` "+attributeTypeId.definition()+",`"+name+"` "+name.definition()+",`"+type+"` "+type.definition()+",`"+parentId+"` "+parentId.definition()+",`"+defaultValue+"` "+defaultValue.definition()+",`"+businessId+"` "+businessId.definition()+
  ",`"+businessKey+"` "+businessKey.definition()+",`"+group+"` "+group.definition()+",`"+version+"` "+version.definition()+",`"+position+"` "+position.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
          }
          public static String updateSQL() {
            return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+attributeTypeId+"`=?"+",`"+name+"`=?"+",`"+type+"`=?"+",`"+parentId+"`=?"+",`"+defaultValue+"`=?"+",`"+businessId+"`=?"+
    ",`"+businessKey+"`=?"+",`"+group+"`=?"+",`"+version+"`=?"+",`"+position+"`=? WHERE ";
          }
          public static String clearSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
          }
          public static String deleteSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
          }

    };
    /**
     * table for the associative value aspects of an attribute
     */
    public static enum attributeInstanceTableDefinition {
        attributeInstanceId(new String[]{"_attribute_instance_id_", "VARCHAR(45) NOT NULL","1"}),
        objectId(new String[]{"object_id", "VARCHAR(45) NOT NULL","2"}),
        attributeTypeId(new String[]{"attribute_type_id", "VARCHAR(45) NOT NULL","3"}),
        parentTypeId(new String[]{"parent_type_id", "VARCHAR(45) NOT NULL","4"}),
        attributeParentId(new String[]{"attribute_parent_id", "VARCHAR(45) NOT NULL","5"}),
        valueId(new String[]{"value_id", "VARCHAR(45) NOT NULL","6"}),
        attributeBusinessKey(new String[]{"attribute_businesskey", "varchar(45) NOT NULL","7"}),
        attributeGroup(new String[]{"attribute_group", "varchar(45) NOT NULL","8"}),
        attributePosition(new String[]{"attribute_position", "int(11) NULL","9"}),
        extra(new String[]{"extra", "PRIMARY KEY (`_attribute_value_id_`) "
         + ",index `_av_object_id_idx` (`object_id` asc)"
         + ",index `_av_attribute_parent_id_idx` (`attribute_parent_id` asc)"
         + ",index `_av_attribute_businesskey_idx` (`attribute_businesskey` asc)"
         + ",index `_av_attribute_group_idx` (`attribute_group` asc)"
         + ",index `_av_attribute_type_id_idx` (`attribute_type_id` asc)"
         + ",index `_av_parent_type_id_idx` (`parent_type_id` asc)","0"});
          public static String dbName = "system_tables";
          public static String tableName = "_attribute_instance";
          private String columnName;
          private String definition;
          private int columnIndex;
          private attributeInstanceTableDefinition(String[] def) {
            columnName = def[0];
            definition = def[1];
            columnIndex = Integer.valueOf(def[2]);
          }
          public String columnName() { return columnName; }
          public String definition() { return definition; }
          public int columnIndex() {return columnIndex; }
          @Override
          public String toString() {
            return columnName;
          }
          public static String insertPSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+attributeInstanceId+"`"+",`"+objectId+"`"
          +",`"+attributeTypeId+"`"+",`"+parentTypeId+"`"+",`"+attributeParentId+"`"+",`"+valueId+"`"
                +",`"+attributeBusinessKey+"`"+",`"+attributeGroup+"`"+",`"+attributePosition+"`) VALUES (?,?,?,?,?,?,?,?,?) ";
          }
          public static String createSQL() {
            return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+attributeInstanceId+"` "+attributeInstanceId.definition()+",`"+objectId+"` "+objectId.definition()+",`"+attributeTypeId+"` "+attributeTypeId.definition()+",`"+parentTypeId+"` "+parentTypeId.definition()+",`"+attributeParentId+"` "+attributeParentId.definition()+",`"+valueId+"` "+valueId.definition()+
    ",`"+attributeBusinessKey+"` "+attributeBusinessKey.definition()+",`"+attributeGroup+"` "+attributeGroup.definition()+",`"+attributePosition+"` "+attributePosition.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
          }
          public static String updateSQL() {
            return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+attributeInstanceId+"`=?"+",`"+objectId+"`=?"+",`"+attributeTypeId+"`=?"+",`"+parentTypeId+"`=?"+",`"+attributeParentId+"`=?"+",`"+valueId+"`=?"+
    ",`"+attributeBusinessKey+"`=?"+",`"+attributeGroup+"`=?"+",`"+attributePosition+"`=? WHERE ";
          }
          public static String clearSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
          }
          public static String deleteSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
          }

    };
    public static enum attributeUpdatesTableDefinition {
        attributeInstanceId(new String[]{"_au_attribute_instance_id", "VARCHAR(45) NOT NULL","1"}),
        businessKey(new String[]{"_au_businesskey", "VARCHAR(45) NOT NULL","2"}),
        target(new String[]{"_au_target", "varchar(45) NOT NULL","3"}),
        extra(new String[]{"extra", "INDEX `au_target_idx` (`_au_target` ASC)"+",INDEX `au_businesskey_idx` (`_au_businesskey` ASC)","0"});
      public static String dbName = "system_tables";
      public static String tableName = "_attribute_updates";
          private String columnName;
          private String definition;
          private int columnIndex;
          private attributeUpdatesTableDefinition(String[] def) {
            columnName = def[0];
            definition = def[1];
            columnIndex = Integer.valueOf(def[2]);
          }
          public String columnName() { return columnName; }
          public String definition() { return definition; }
          public int columnIndex() {return columnIndex; }
          @Override
          public String toString() {
            return columnName;
          }
          public static String insertPSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+attributeInstanceId+"`"+",`"+businessKey+"`"+",`"+target+"`) VALUES (?,?,?) ";
          }
          public static String createSQL() {
            return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+attributeInstanceId+"` "+attributeInstanceId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+target+"` "+target.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
          }
          public static String updateSQL() {
            return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+attributeInstanceId+"`=?"+",`"+businessKey+"`=?"+",`"+target+"`=? WHERE ";
          }
          public static String clearSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
          }
          public static String deleteSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
          }

    };
    public static enum attributeHasProcessTableDefinition {
        attributeBusinessId(new String[]{"_ahp_attribute_business_id", "varchar(100) NOT NULL","1"}),
        businessId(new String[]{"_ahp_business_id", "varchar(100) NOT NULL","2"}),
        processInstanceId(new String[]{"_ahp_process_instance_id", "varchar(45) NOT NULL","3"}),
        processKey(new String[]{"_ahp_process_key", "varchar(45) NOT NULL","4"}),
        extra(new String[]{"extra", "INDEX `ahp_attribute_business_id_idx` (`_ahp_attribute_business_id` ASC)"
          +",INDEX `ahp_business_id_idx` (`_ahp_business_id` ASC)"
            + ",INDEX `ahp_process_key_idx` (`_ahp_process_key` ASC)"
            + ",KEY `fk_ahp_process2_idx` (`_ahp_process_instance_id`)"
            + ",KEY `fk_ahp_order2_idx` (`_ahp_business_id`)","0"});
      public static String dbName = "system_tables";
      public static String tableName = "_attribute_has_process";
          private String columnName;
          private String definition;
          private int columnIndex;
          private attributeHasProcessTableDefinition(String[] def) {
            columnName = def[0];
            definition = def[1];
            columnIndex = Integer.valueOf(def[2]);
          }
          public String columnName() { return columnName; }
          public String definition() { return definition; }
          public int columnIndex() {return columnIndex; }
          @Override
          public String toString() {
            return columnName;
          }
          public static String insertPSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+attributeBusinessId+"`"+",`"+businessId+"`"+",`"+processInstanceId+"`"+",`"+processKey+"`) VALUES (?,?,?,?) ";
          }
          public static String createSQL() {
            return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+attributeBusinessId+"` "+attributeBusinessId.definition()+
                ",`"+businessId+"` "+businessId.definition()+",`"+processInstanceId+"` "+processInstanceId.definition()+
                ",`"+processKey+"` "+processKey.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
          }
          public static String updateSQL() {
            return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+attributeBusinessId+"`=?"+",`"+businessId+"`=?"+",`"+processInstanceId+"`=?"+",`"+processKey+"`=? WHERE ";
          }
          public static String clearSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
          }
          public static String deleteSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
          }

    };


    /**
     * table for the concrete value aspects of an attribute
     * Note: an associative tag was added for indexing
     */
      public static enum valueTableDefinition {
          id(new String[]{"_value_id_", "VARCHAR(45) NOT NULL","1"}),
          objectId(new String[]{"object_id", "VARCHAR(45) NOT NULL","2"}),
          parentId(new String[]{"parent_id", "VARCHAR(45) NULL","3"}),
          valueType(new String[]{"value_type", "varchar(45) NOT NULL","4"}),
          valueGroup(new String[]{"value_group", "varchar(45) NOT NULL","5"}),
          selected(new String[]{"selected", "boolean NULL DEFAULT false","6"}),
          posX(new String[]{"pos_x", "int(11) NULL DEFAULT 0","7"}),
          posY(new String[]{"pos_y", "int(11) NULL DEFAULT 0","8"}),
          posZ(new String[]{"pos_z", "int(11) NULL DEFAULT 0","9"}),
          valueData(new String[]{"value_data", "VARCHAR(65535) NULL","10"}),
          extra(new String[]{"extra", "PRIMARY KEY (`_value_id_`), UNIQUE INDEX `_v_value_id_UNIQUE` (`_value_id_` ASC)"
           + ",index `_av_pos_y_idx` (`pos_y` asc)"
           + ",index `_av_object_id_idx` (`object_id` asc)","0"});
        public static String dbName = "system_tables";
        public static String tableName = "_value";
          private String columnName;
          private String definition;
          private int columnIndex;
          private valueTableDefinition(String[] def) {
            columnName = def[0];
            definition = def[1];
            columnIndex = Integer.valueOf(def[2]);
          }
          public String columnName() { return columnName; }
          public String definition() { return definition; }
          public int columnIndex() {return columnIndex; }
          @Override
          public String toString() {
            return columnName;
          }
          public static String querySQL() {
            return "SELECT * FROM `"+dbName+"`.`"+tableName+"` WHERE ";
          }
          public static String insertSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+objectId+"`"+",`"+parentId+"`"+",`"+valueType+"`"+",`"+valueGroup+"`"+",`"+selected+"`"
                +",`"+posX+"`"+",`"+posY+"`"+",`"+posZ+"`, `"+valueData+"`) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s) ";
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
          public static String updateSQL() {
            return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=%s"+",`"+objectId+"`=%s"+",`"+parentId+"`=%s"+",`"+valueType+"`=%s"+",`"+valueGroup+"`=%s"+",`"+selected+"`=%s"+
    ",`"+posX+"`=%s"+",`"+posY+"`=%s"+",`"+posZ+"`=%s, `"+valueData+"`=%s"+" WHERE ";
          }
          public static String updatePSQL() {
            return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+objectId+"`=?"+",`"+parentId+"`=?"+",`"+valueType+"`=?"+",`"+valueGroup+"`=?"+",`"+selected+"`=?"+
    ",`"+posX+"`=?"+",`"+posY+"`=?"+",`"+posZ+"`=?, `"+valueData+"`=?"+" WHERE ";
          }
          public static String clearSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
          }
          public static String deleteSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
          }

      };
      public static enum blobTableDefinition {
          valueId(new String[]{"value_id", "VARCHAR(45) NOT NULL","1"}),
          data(new String[]{"data", "MEDIUMBLOB NOT NULL","2"}),
          extra(new String[]{"extra", "PRIMARY KEY (`value_id`), UNIQUE INDEX `_blv_value_id_UNIQUE` (`value_id` ASC)","0"});
        public static String dbName = "system_tables";
        public static String tableName = "_blob";
          private String columnName;
          private String definition;
          private int columnIndex;
          private blobTableDefinition(String[] def) {
            columnName = def[0];
            definition = def[1];
            columnIndex = Integer.valueOf(def[2]);
          }
          public String columnName() { return columnName; }
          public String definition() { return definition; }
          public int columnIndex() {return columnIndex; }
          @Override
          public String toString() {
            return columnName;
          }
          public static String insertPSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`"+",`"+data+"`) VALUES (?,?) ";
          }
          public static String createSQL() {
            return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+valueId+"` "+valueId.definition()+",`"+data+"` "+data.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
          }
          public static String updateSQL() { return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`=%s"+",`"+data+"`=%s WHERE "; }
          public static String updatePSQL() { return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`=?"+",`"+data+"`=? WHERE "; }
          public static String clearSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
          }
          public static String deleteSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
          }

      };
      public static enum booleanTableDefinition {
          valueId(new String[]{"value_id", "VARCHAR(45) NOT NULL","1"}),
          data(new String[]{"data", "BOOLEAN NOT NULL","2"}),
          extra(new String[]{"extra", "PRIMARY KEY (`value_id`), UNIQUE INDEX `_bov_value_id_UNIQUE` (`value_id` ASC)","0"});
        public static String dbName = "system_tables";
        public static String tableName = "_boolean";
          private String columnName;
          private String definition;
          private int columnIndex;
          private booleanTableDefinition(String[] def) {
            columnName = def[0];
            definition = def[1];
            columnIndex = Integer.valueOf(def[2]);
          }
          public String columnName() { return columnName; }
          public String definition() { return definition; }
          public int columnIndex() {return columnIndex; }
          @Override
          public String toString() {
            return columnName;
          }
          public static String insertSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`"+",`"+data+"`) VALUES (%s,%s) ";
          }
          public static String insertPSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`"+",`"+data+"`) VALUES (?,?) ";
          }
          public static String createSQL() {
            return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+valueId+"` "+valueId.definition()+",`"+data+"` "+data.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
          }
          public static String updateSQL() { return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`=%s"+",`"+data+"`=%s WHERE "; }
          public static String updatePSQL() { return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`=?"+",`"+data+"`=? WHERE "; }
          public static String clearSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
          }
          public static String deleteSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
          }

      };
      public static enum complexTableDefinition {
          valueId(new String[]{"value_id", "VARCHAR(45) NOT NULL","1"}),
          data(new String[]{"data", "VARCHAR(400) NOT NULL","2"}),
          extra(new String[]{"extra", "PRIMARY KEY (`value_id`), UNIQUE INDEX `_cov_value_id_UNIQUE` (`value_id` ASC)","0"});
        public static String dbName = "system_tables";
        public static String tableName = "_complex";
          private String columnName;
          private String definition;
          private int columnIndex;
          private complexTableDefinition(String[] def) {
            columnName = def[0];
            definition = def[1];
            columnIndex = Integer.valueOf(def[2]);
          }
          public String columnName() { return columnName; }
          public String definition() { return definition; }
          public int columnIndex() {return columnIndex; }
          @Override
          public String toString() {
            return columnName;
          }
          public static String insertSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`"+",`"+data+"`) VALUES (%s,%s) ";
          }
          public static String insertPSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`"+",`"+data+"`) VALUES (?,?) ";
          }
          public static String createSQL() {
            return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+valueId+"` "+valueId.definition()+",`"+data+"` "+data.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
          }
          public static String updateSQL() {return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`=%s"+",`"+data+"`=%s WHERE ";}
          public static String updatePSQL() {return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`=?"+",`"+data+"`=? WHERE ";}
          public static String clearSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
          }
          public static String deleteSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
          }

      };
      public static enum integerTableDefinition {
          valueId(new String[]{"value_id", "VARCHAR(45) NOT NULL","1"}),
          data(new String[]{"data", "INT(11) NOT NULL","2"}),
          extra(new String[]{"extra", "PRIMARY KEY (`value_id`), UNIQUE INDEX `_inv_value_id_UNIQUE` (`value_id` ASC)","0"});
        public static String dbName = "system_tables";
        public static String tableName = "_integer";
          private String columnName;
          private String definition;
          private int columnIndex;
          private integerTableDefinition(String[] def) {
            columnName = def[0];
            definition = def[1];
            columnIndex = Integer.valueOf(def[2]);
          }
          public String columnName() { return columnName; }
          public String definition() { return definition; }
          public int columnIndex() {return columnIndex; }
          @Override
          public String toString() {
            return columnName;
          }
          public static String insertSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`"+",`"+data+"`) VALUES (%s,%s) ";
          }
          public static String insertPSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`"+",`"+data+"`) VALUES (?,?) ";
          }
          public static String createSQL() {
            return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+valueId+"` "+valueId.definition()+",`"+data+"` "+data.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
          }
          public static String updateSQL() {return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`=%s"+",`"+data+"`=%s WHERE ";}
          public static String updatePSQL() {return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`=?"+",`"+data+"`=? WHERE ";}
          public static String clearSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
          }
          public static String deleteSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
          }

     };
      public static enum stringTableDefinition {
          valueId(new String[]{"value_id", "VARCHAR(45) NOT NULL","1"}),
          data(new String[]{"data", "VARCHAR(100) NOT NULL","2"}),
          extra(new String[]{"extra", "PRIMARY KEY (`value_id`), UNIQUE INDEX `_stv_value_id_UNIQUE` (`value_id` ASC)","0"});
        public static String dbName = "system_tables";
        public static String tableName = "_string";
          private String columnName;
          private String definition;
          private int columnIndex;
          private stringTableDefinition(String[] def) {
            columnName = def[0];
            definition = def[1];
            columnIndex = Integer.valueOf(def[2]);
          }
          public String columnName() { return columnName; }
          public String definition() { return definition; }
          public int columnIndex() {return columnIndex; }
          @Override
          public String toString() {
            return columnName;
          }
          public static String insertSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`"+",`"+data+"`) VALUES (%s,%s) ";
          }
          public static String insertPSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`"+",`"+data+"`) VALUES (?,?) ";
          }
          public static String createSQL() {
            return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+valueId+"` "+valueId.definition()+",`"+data+"` "+data.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
          }
          public static String updateSQL() {return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`=%s"+",`"+data+"`=%s WHERE ";}
          public static String updatePSQL() {return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`=?"+",`"+data+"`=? WHERE ";}
          public static String clearSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
          }
          public static String deleteSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
          }

      };
      public static enum textTableDefinition {
          valueId(new String[]{"value_id", "VARCHAR(45) NOT NULL","1"}),
          data(new String[]{"data", "TEXT NOT NULL","2"}),
          extra(new String[]{"extra", "PRIMARY KEY (`value_id`), UNIQUE INDEX `_tev_value_id_UNIQUE` (`value_id` ASC)","0"});
        public static String dbName = "system_tables";
        public static String tableName = "_text";
          private String columnName;
          private String definition;
          private int columnIndex;
          private textTableDefinition(String[] def) {
            columnName = def[0];
            definition = def[1];
            columnIndex = Integer.valueOf(def[2]);
          }
          public String columnName() { return columnName; }
          public String definition() { return definition; }
          public int columnIndex() {return columnIndex; }
          @Override
          public String toString() {
            return columnName;
          }
          public static String insertSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`"+",`"+data+"`) VALUES (%s,%s) ";
          }
          public static String insertPSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`"+",`"+data+"`) VALUES (?,?) ";
          }
          public static String createSQL() {
            return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+valueId+"` "+valueId.definition()+",`"+data+"` "+data.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
          }
          public static String updateSQL() {return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`=%s"+",`"+data+"`=%s WHERE ";}
          public static String updatePSQL() {return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`=?"+",`"+data+"`=? WHERE ";}
          public static String clearSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
          }
          public static String deleteSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
          }

      };
      public static enum timestampTableDefinition {
          valueId(new String[]{"value_id", "VARCHAR(45) NOT NULL","1"}),
          data(new String[]{"data", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP","2"}),
          extra(new String[]{"extra", "PRIMARY KEY (`value_id`), UNIQUE INDEX `_tiv_value_id_UNIQUE` (`value_id` ASC)","0"});
        public static String dbName = "system_tables";
        public static String tableName = "_timestamp";
          private String columnName;
          private String definition;
          private int columnIndex;
          private timestampTableDefinition(String[] def) {
            columnName = def[0];
            definition = def[1];
            columnIndex = Integer.valueOf(def[2]);
          }
          public String columnName() { return columnName; }
          public String definition() { return definition; }
          public int columnIndex() {return columnIndex; }
          @Override
          public String toString() {
            return columnName;
          }
          public static String insertSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`"+",`"+data+"`) VALUES (%s,%s) ";
          }
          public static String insertPSQL() {
            return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`"+",`"+data+"`) VALUES (?,?) ";
          }
          public static String createSQL() {
            return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+valueId+"` "+valueId.definition()+",`"+data+"` "+data.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
          }
          public static String updateSQL() {return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`=%s"+",`"+data+"`=%s WHERE ";}
          public static String updatePSQL() {return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+valueId+"`=?"+",`"+data+"`=? WHERE ";}
          public static String clearSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
          }
          public static String deleteSQL() {
            return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
          }

     };
    public static enum instanceTableDefinition {
        objectId(new String[]{"_object_id", "INT(11) NOT NULL","1"}),
        objectBusinessId(new String[]{"_object_business_id", "VARCHAR(100) NOT NULL","2"}),
        objectReferenceId(new String[]{"_object_ref_id", "INT(11) NOT NULL","3"}),
        instanceId(new String[]{"_instance_id", "varchar(45) NOT NULL","4"}),
        currentInstanceId(new String[]{"_current_instance_id", "varchar(45) NOT NULL","5"}),
        initialInstanceId(new String[]{"_initial_instance_id", "varchar(45) NOT NULL","6"}),
        status(new String[]{"_status", "varchar(45) NOT NULL","7"}),
        timestamp(new String[]{"_timestamp", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP","8"}),
        date(new String[]{"_date", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP","9"}),
        endOfLife(new String[]{"_end_of_life", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP","10"}),//DATE_ADD(CURRENT_TIMESTAMP,INTERVAL 10 YEAR)"},
        versionValue(new String[]{"_version_value", "VARCHAR(45) NOT NULL","11"}),
        groupName(new String[]{"_group_name", "VARCHAR(100) NOT NULL","12"}),
        extra(new String[]{"extra", "UNIQUE INDEX `_i_instance_id_UNIQUE` (`_instance_id` ASC)"
          + ",INDEX `_i_object_business_id_idx` (`_object_business_id` ASC)"
          + ",INDEX `_i_instance_id_idx` (`_instance_id` ASC)"
          + ",INDEX `_i_object_id_idx` (`_object_id` ASC)","0"});
      public static String dbName = "system_tables";
      public static String tableName = "_instance";
      private String columnName;
      private String definition;
      private int columnIndex;
      private instanceTableDefinition(String[] def) {
        columnName = def[0];
        definition = def[1];
        columnIndex = Integer.valueOf(def[2]);
      }
      public String columnName() { return columnName; }
      public String definition() { return definition; }
      public int columnIndex() {return columnIndex; }
      @Override
      public String toString() {
        return columnName;
      }
//			public static String update() {
//				String updateSQL = "";
//				boolean start = true;
//				for(customerTableDefinition column: values()) {
//					if(!start) {
//						updateSQL += ",";
//					} else {
//						start = false;
//					}
//					updateSQL = "`" + column + "`=?";
//				}
//				return updateSQL;
//			}

      public static String insertPSQL() {
        return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+objectId+"`"+",`"+objectBusinessId+"`"+",`"+objectReferenceId+"`"+",`"+instanceId+"`"+",`"+currentInstanceId+"`"+",`"+initialInstanceId+"`"
            +",`"+status+"`"+",`"+timestamp+"`"+",`"+date+"`"+",`"+endOfLife+"`"+",`"+versionValue+"`"+",`"+groupName+"`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
      }
      public static String createSQL() {
        return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+objectId+"` "+objectId.definition()+",`"+objectBusinessId+"` "+objectBusinessId.definition()+",`"+objectReferenceId+"` "+objectReferenceId.definition()+",`"+instanceId+"` "+instanceId.definition()+",`"+currentInstanceId+"` "+currentInstanceId.definition()+",`"+initialInstanceId+"` "+initialInstanceId.definition()+
    ",`"+status+"` "+status.definition()+",`"+timestamp+"` "+timestamp.definition()+",`"+date+"` "+date.definition()+",`"+endOfLife+"` "+endOfLife.definition()+",`"+versionValue+"` "+versionValue.definition()+",`"+groupName+"` "+groupName.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
      }
      public static String updateSQL() {
        return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+objectId+"`=?"+",`"+objectBusinessId+"`=?"+",`"+objectReferenceId+"`=?"+",`"+instanceId+"`=?"+",`"+currentInstanceId+"`=?"+",`"+initialInstanceId+"`=?"+
    ",`"+status+"`=?"+",`"+timestamp+"`=?"+",`"+date+"`=?"+",`"+endOfLife+"`=?"+",`"+versionValue+"`=?"+",`"+groupName+"`=? WHERE ";
      }
      public static String clearSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
      }
      public static String deleteSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
      }

    };

  }
  //refactor 1
  public static class Customer {
    public static final int _CUSTOMER_TABLE_INDEX = 0;
    public static final int _CUSTOMER_REF_TABLE_INDEX = 1;
    public static final int _CONTACT_DETAILS_TABLE_INDEX = 2;
    public static final int _ADDRESS_TABLE_INDEX = 3;
    public static final int _TOUCH_POINT_TABLE_INDEX = 4;
    public static final int _TOUCH_POINT_REF_TABLE_INDEX = 5;
    public static final int _CUSTOMER_UPDATES_TABLE_INDEX = 6;
    public static final int _CONTACT_DETAILS_UPDATES_TABLE_INDEX = 7;
    public static final int _ADDRESS_UPDATES_TABLE_INDEX = 8;
    public static final int _TOUCH_POINT_UPDATES_TABLE_INDEX = 9;
    public static final int _CUSTOMER_HAS_PROCESS_TABLE_INDEX = 10;

    public static final String[] customer_management_tables = {
        "customer", //0
        "customer_ref", //1
        "contact_details", //2
        "address", //3
        "touch_point",//4
        "touch_point_ref",//5
        "customer_updates",//6
        "contact_details_updates",//7
        "address_updates",//8
        "touch_point_updates",//9
        "customer_has_process",//10
    };

    public static final String[][] customer_table_definition = {
        {"_customer_id_", "VARCHAR(45) NOT NULL"},
        {"customer_business_id", "varchar(150) NOT NULL"},
        {"customer_businesskey", "varchar(45) not null"},
        {"customer_type", "varchar(45) NOT NULL"},
        {"customer_origin", "varchar(45) NOT NULL"},
        {"customer_version", "varchar(45) NOT NULL"},
        {"extra", "PRIMARY KEY (`_customer_id_`)"
            + ",UNIQUE KEY `_customer_id_UNIQUE` (`_customer_id_`)"
            + ",INDEX `customer_businesskey_idx` (`customer_businesskey` ASC)"
            + ",INDEX `customer_type_idx` (`customer_type` ASC)"
            + ",INDEX `customer_origin_idx` (`customer_origin` ASC)"}
    };

    public static enum customerTableDefinition {
      id(new String[]{"_customer_id_", "VARCHAR(45) NOT NULL","1"}),
      businessId(new String[]{"customer_business_id", "varchar(150) NOT NULL","2"}),
      businessKey(new String[]{"customer_businesskey", "varchar(45) not null","3"}),
      type(new String[]{"customer_type", "varchar(45) NOT NULL","4"}),
      origin(new String[]{"customer_origin", "varchar(45) NOT NULL","5"}),
      version(new String[]{"customer_version", "varchar(45) NOT NULL","6"}),
      extra(new String[]{"extra", "PRIMARY KEY (`_customer_id_`)"
          + ",UNIQUE KEY `_customer_id_UNIQUE` (`_customer_id_`)"
          + ",INDEX `customer_businesskey_idx` (`customer_businesskey` ASC)"
          + ",INDEX `customer_type_idx` (`customer_type` ASC)"
          + ",INDEX `customer_origin_idx` (`customer_origin` ASC)","0"});

      public static String dbName = "customer_management";
      public static String tableName = "_customer";
      private String columnName;
      private String definition;
      private int columnIndex;
      private customerTableDefinition(String[] def) {
        columnName = def[0];
        definition = def[1];
        columnIndex = Integer.valueOf(def[2]);
      }
      public String columnName() { return columnName; }
      public String definition() { return definition; }
      public int columnIndex() {return columnIndex; }
      @Override
      public String toString() {
        return columnName;
      }
//			public static String update() {
//				String updateSQL = "";
//				boolean start = true;
//				for(customerTableDefinition column: values()) {
//					if(!start) {
//						updateSQL += ",";
//					} else {
//						start = false;
//					}
//					updateSQL = "`" + column + "`=?";
//				}
//				return updateSQL;
//			}

      public static String insertPSQL() {
        return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+businessId+"`"+",`"+businessKey+"`"+",`"+type+"`"+",`"+origin+"`"+",`"+version+"`) VALUES (?,?,?,?,?,?) ";
      }
      public static String createSQL() {
        return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+businessId+"` "+businessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+type+"` "+type.definition()+",`"+origin+"` "+origin.definition()+",`"+version+"` "+version.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
      }
      public static String updateSQL() {
        return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+businessId+"`=?"+",`"+businessKey+"`=?"+",`"+type+"`=?"+",`"+origin+"`=?"+",`"+version+"`=? WHERE ";
      }
      public static String clearSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
      }
      public static String deleteSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
      }

    }
    public static final String[][] customer_ref_table_definition = {
        {"_customer_ref_id_", "VARCHAR(45) NOT NULL"},
        {"customer_id", "VARCHAR(45) NOT NULL"},
        {"customer_address_id", "VARCHAR(45) NOT NULL"},
        {"customer_delivery_address_id", "VARCHAR(45) NOT NULL"},
        {"customer_contact_id", "VARCHAR(45) NOT NULL"},
        {"customer_touchpoint_id", "VARCHAR(45) NOT NULL"},
        {"customer_group", "varchar(45) NOT NULL"},
        {"extra", "PRIMARY KEY (`_customer_id_`)"
            + ",UNIQUE KEY `_customer_ref_id_UNIQUE` (`_customer_ref_id_`)"
            + ",INDEX `customer_business_id_idx` (`customer_business_id` ASC)"
            + ",INDEX `customer_businesskey_idx` (`customer_businesskey` ASC)"
            + ",INDEX `customer_group_idx` (`customer_group` ASC)"}
    };
    public static enum customerReferenceTableDefinition {
        id(new String[]{"_customer_ref_id_", "VARCHAR(45) NOT NULL","1"}),
        customerId(new String[]{"customer_id", "VARCHAR(45) NOT NULL","2"}),
        addressId(new String[]{"customer_address_id", "VARCHAR(45) NOT NULL","3"}),
        deliveryAddressId(new String[]{"customer_delivery_address_id", "VARCHAR(45) NOT NULL","4"}),
        contactId(new String[]{"customer_contact_id", "VARCHAR(45) NOT NULL","5"}),
        touchPointId(new String[]{"customer_touchpoint_id", "VARCHAR(45) NOT NULL","6"}),
        group(new String[]{"customer_group", "varchar(45) NOT NULL","7"}),
        extra(new String[]{"extra", "PRIMARY KEY (`_customer_id_`)"
            + ",UNIQUE KEY `_customer_ref_id_UNIQUE` (`_customer_ref_id_`)"
            + ",INDEX `customer_business_id_idx` (`customer_business_id` ASC)"
            + ",INDEX `customer_businesskey_idx` (`customer_businesskey` ASC)"
            + ",INDEX `customer_group_idx` (`customer_group` ASC)","0"});

      public static String dbName = "customer_management";
      public static String tableName = "_customer_reference";
        private String columnName;
        private String definition;
        private int columnIndex;
        private customerReferenceTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() {return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+customerId+"`"+",`"+addressId+"`"+",`"+deliveryAddressId+"`"+",`"+contactId+"`"+",`"+touchPointId+"`"+",`"+group+"`) VALUES (?,?,?,?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+customerId+"` "+customerId.definition()+",`"+addressId+"` "+addressId.definition()+",`"+deliveryAddressId+"` "+deliveryAddressId.definition()+",`"+contactId+"` "+contactId.definition()+",`"+touchPointId+"` "+touchPointId.definition()+",`"+group+"` "+group.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+customerId+"`=?"+",`"+addressId+"`=?"+",`"+deliveryAddressId+"`=?"+",`"+contactId+"`=?"+",`"+touchPointId+"`=?"+",`"+group+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

};

    public static final String[][] contact_details_table_definition = {
        {"_contact_id_", "VARCHAR(45) NOT NULL"},
        {"c_customer_business_id", "VARCHAR(100) NOT NULL"},
        {"c_customer_businesskey", "VARCHAR(100) NOT NULL"},
        {"contact_email", "varchar(150) NOT NULL"},
        {"contact_mobile", "varchar(45) NOT NULL"},
        {"contact_telephone", "varchar(45) NOT NULL"},
        {"contact_skype", "varchar(45) not null"},
        {"contact_misc", "varchar(45) not null"},
        {"extra", "PRIMARY KEY (`_contact_id_`)"
            + ",UNIQUE KEY `contact_id_UNIQUE` (`_contact_id_`)"
            + ",UNIQUE KEY `contact_email_UNIQUE` (`contact_email`)"}
    };
    public static enum contactDetailsTableDefinition {
        id(new String[]{"_contact_id_", "VARCHAR(45) NOT NULL","1"}),
        customerBusinessId(new String[]{"c_customer_business_id", "VARCHAR(100) NOT NULL","2"}),
        customerBusinessKey(new String[]{"c_customer_businesskey", "VARCHAR(100) NOT NULL","3"}),
        email(new String[]{"contact_email", "varchar(150) NOT NULL","4"}),
        mobile(new String[]{"contact_mobile", "varchar(45) NOT NULL","5"}),
        telephone(new String[]{"contact_telephone", "varchar(45) NOT NULL","6"}),
        skype(new String[]{"contact_skype", "varchar(45) not null","7"}),
        misc(new String[]{"contact_misc", "varchar(45) not null","8"}),
        extra(new String[]{"extra", "PRIMARY KEY (`_contact_id_`)"
            + ",UNIQUE KEY `contact_id_UNIQUE` (`_contact_id_`)"
            + ",UNIQUE KEY `contact_email_UNIQUE` (`contact_email`)","0"});
      public static String dbName = "customer_management";
      public static String tableName = "_contact_details";
        private String columnName;
        private String definition;
        private int columnIndex;
        private contactDetailsTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex;}
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+customerBusinessId+"`"+",`"+customerBusinessKey+"`"
              +",`"+email+"`"+",`"+mobile+"`"+",`"+telephone+"`"+",`"+skype+"`"+",`"+misc+"`) VALUES (?,?,?,?,?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+customerBusinessId+"` "+customerBusinessId.definition()+",`"+customerBusinessKey+"` "+customerBusinessKey.definition()+",`"+email+"` "+email.definition()+",`"+mobile+"` "+mobile.definition()+",`"+telephone+"` "+telephone.definition()+",`"+skype+"` "+skype.definition()+",`"+misc+"` "+misc.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+customerBusinessId+"`=?"+",`"+customerBusinessKey+"`=?"+",`"+email+"`=?"+",`"+mobile+"`=?"+",`"+telephone+"`=?"+",`"+skype+"`=?"+",`"+misc+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };


    public static final String[][] address_table_definition = {
        {"_address_id_", "VARCHAR(45) NOT NULL"},
        {"address_country", "varchar(45) NOT NULL"},
        {"address_state", "varchar(45) NULL"},
        {"address_postcode", "varchar(45) NOT NULL"},
        {"address_city", "varchar(45) not null"},
        {"address_street", "varchar(45) not null"},
        {"address_street_number", "varchar(45) not null"},
        {"address_floor", "varchar(45) null"},
        {"address_room_number", "varchar(45) null"},
        {"address_business_id", "varchar(150) not null"},
        {"address_businesskey", "varchar(45) not null"},
        {"extra", "PRIMARY KEY (`_address_id_`)"
            + ",UNIQUE KEY `_address_id_UNIQUE` (`_address_id_`)"
            + ",INDEX `address_businesskey_idx` (`address_businesskey` ASC)"}
    };
    public static enum addressTableDefinition {
        id(new String[]{"_address_id_", "VARCHAR(45) NOT NULL","1"}),
        country(new String[]{"address_country", "varchar(45) NOT NULL","2"}),
        state(new String[]{"address_state", "varchar(45) NULL","3"}),
        postCode(new String[]{"address_postcode", "varchar(45) NOT NULL","4"}),
        city(new String[]{"address_city", "varchar(45) not null","5"}),
        street(new String[]{"address_street", "varchar(45) not null","6"}),
        streetNumber(new String[]{"address_street_number", "varchar(45) not null","7"}),
        floor(new String[]{"address_floor", "varchar(45) null","8"}),
        roomNumber(new String[]{"address_room_number", "varchar(45) null","9"}),
        businessId(new String[]{"address_business_id", "varchar(150) not null","10"}),
        businessKey(new String[]{"address_businesskey", "varchar(45) not null","11"}),
        extra(new String[]{"extra", "PRIMARY KEY (`_address_id_`))"
            + ",UNIQUE KEY `_address_id_UNIQUE` (`_address_id_`))"
            + ",INDEX `address_businesskey_idx` (`address_businesskey` ASC)","0"});
      public static String dbName = "customer_management";
      public static String tableName = "_address";
        private String columnName;
        private String definition;
        private int columnIndex;
        private addressTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex;}
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+country+"`"+",`"+state+"`"
              +",`"+postCode+"`"+",`"+city+"`"+",`"+street+"`"+",`"+streetNumber+"`"+",`"+floor+"`"
              +",`"+roomNumber+"`"+",`"+businessId+"`"+",`"+businessKey+"`) VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+country+"` "+country.definition()+",`"+state+"` "+state.definition()+
    ",`"+postCode+"` "+postCode.definition()+",`"+city+"` "+city.definition()+",`"+street+"` "+street.definition()+",`"+streetNumber+"` "+streetNumber.definition()+",`"+floor+"` "+floor.definition()+
    ",`"+roomNumber+"` "+roomNumber.definition()+",`"+businessId+"` "+businessId.definition()+",`"+businessKey+"` "+businessKey.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+country+"`=?"+",`"+state+"`=?"+
    ",`"+postCode+"`=?"+",`"+city+"`=?"+",`"+street+"`=?"+",`"+streetNumber+"`=?"+",`"+floor+"`=?"+
    ",`"+roomNumber+"`=?"+",`"+businessId+"`=?"+",`"+businessKey+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };


    public static final String[][] touch_point_table_definition = {
        {"_touchpoint_id_", "VARCHAR(45) NOT NULL"},
        {"_topic", "varchar(200) NOT NULL"},
        {"_date", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"extra", "PRIMARY KEY (`_touchpoint_id_`)"
            + ",UNIQUE KEY `_touchpoint_id_UNIQUE` (`_touchpoint_id_`)"}
    };
    public static enum touchPointTableDefinition {
        id(new String[]{"_touchpoint_id_", "VARCHAR(45) NOT NULL","1"}),
        topic(new String[]{"_topic", "varchar(200) NOT NULL","2"}),
        date(new String[]{"_date", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP","3"}),
        extra(new String[]{"extra", "PRIMARY KEY (`_touchpoint_id_`)"
            + ",UNIQUE KEY `_touchpoint_id_UNIQUE` (`_touchpoint_id_`)","0"});
      public static String dbName = "customer_management";
      public static String tableName = "_touch_point";
        private String columnName;
        private String definition;
        private int columnIndex;
        private touchPointTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+topic+"`"+",`"+date+"`) VALUES (?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+topic+"` "+topic.definition()+",`"+date+"` "+date.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+topic+"`=?"+",`"+date+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };


    public static final String[][] touch_point_ref_table_definition = {
        {"_touchpoint_ref_id_", "VARCHAR(45) NOT NULL"},
        {"_touchpoint_id", "VARCHAR(45) NOT NULL"},
        {"_customer_business_id", "varchar(100) NOT NULL"},
        {"_customer_businesskey", "varchar(45) NOT NULL"},
        {"_responsible_business_id", "varchar(100) NOT NULL"},
        {"_responsible_businesskey", "varchar(100) NOT NULL"},
        {"_next_date", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"_minutes", "LONGTEXT NOT NULL"},
        {"extra", "PRIMARY KEY (`_touchpoint_ref_id_`)"
            + ",UNIQUE KEY `_touchpoint_ref_id_UNIQUE` (`_touchpoint_ref_id_`)"
            + ",INDEX `_responsible_businesskey_idx` (`_responsible_businesskey` ASC)"
            + ",INDEX `_responsible_business_id_idx` (`_responsible_business_id` ASC)"}
    };
    public static enum touchPointReferenceTableDefinition {
        id(new String[]{"_touchpoint_ref_id_", "VARCHAR(45) NOT NULL","1"}),
        touchpointId(new String[]{"_touchpoint_id", "VARCHAR(45) NOT NULL","2"}),
        customerBusinessId(new String[]{"_customer_business_id", "varchar(100) NOT NULL","3"}),
        customerBusinessKey(new String[]{"_customer_businesskey", "varchar(45) NOT NULL","4"}),
        responsibleBusinessId(new String[]{"_responsible_business_id", "varchar(100) NOT NULL","5"}),
        responsibleBusinessKey(new String[]{"_responsible_businesskey", "varchar(100) NOT NULL","6"}),
        nextDate(new String[]{"_next_date", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP","7"}),
        minutes(new String[]{"_minutes", "LONGTEXT NOT NULL","8"}),
        extra(new String[]{"extra", "PRIMARY KEY (`_touchpoint_ref_id_`)"
            + ",UNIQUE KEY `_touchpoint_ref_id_UNIQUE` (`_touchpoint_ref_id_`)"
            + ",INDEX `_responsible_businesskey_idx` (`_responsible_businesskey` ASC)"
            + ",INDEX `_responsible_business_id_idx` (`_responsible_business_id` ASC)","0"});
      public static String dbName = "customer_management";
      public static String tableName = "_touch_point_reference";
        private String columnName;
        private String definition;
        private int columnIndex;
        private touchPointReferenceTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+touchpointId+"`"+",`"+customerBusinessId+"`"+",`"+customerBusinessKey+"`"
              +",`"+responsibleBusinessId+"`"+",`"+responsibleBusinessKey+"`"+",`"+nextDate+"`"+",`"+minutes+"`) VALUES (?,?,?,?,?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+touchpointId+"` "+touchpointId.definition()+",`"+customerBusinessId+"` "+customerBusinessId.definition()+",`"+customerBusinessKey+"` "+customerBusinessKey.definition()+
    ",`"+responsibleBusinessId+"` "+responsibleBusinessId.definition()+",`"+responsibleBusinessKey+"` "+responsibleBusinessKey.definition()+",`"+nextDate+"` "+nextDate.definition()+",`"+minutes+"` "+minutes.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+touchpointId+"`=?"+",`"+customerBusinessId+"`=?"+",`"+customerBusinessKey+"`=?"+
    ",`"+responsibleBusinessId+"`=?"+",`"+responsibleBusinessKey+"`=?"+",`"+nextDate+"`=?"+",`"+minutes+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };


    public static final String[][] customer_updates_table_definition = {
        {"_customer_business_id", "VARCHAR(45) NOT NULL"},
        {"_businesskey", "VARCHAR(45) NOT NULL"},
        {"_target", "varchar(45) NOT NULL"},
        {"_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"extra", "INDEX `cu_customer_business_id_idx` (`_customer_business_id` ASC)"}
    };
    public static enum customerUpdatesTableDefinition {
        customerBusinessId(new String[]{"_customer_business_id", "VARCHAR(45) NOT NULL","1"}),
        businessKey(new String[]{"_businesskey", "VARCHAR(45) NOT NULL","2"}),
        target(new String[]{"_target", "varchar(45) NOT NULL","3"}),
        timestamp(new String[]{"_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP","4"}),
        extra(new String[]{"extra", "INDEX `cu_customer_business_id_idx` (`_customer_business_id` ASC)","0"});
      public static String dbName = "customer_management";
      public static String tableName = "_customer_updates";
        private String columnName;
        private String definition;
        private int columnIndex;
        private customerUpdatesTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+customerBusinessId+"`"+",`"+businessKey+"`"+",`"+target+"`"+",`"+timestamp+"`) VALUES (?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+customerBusinessId+"` "+customerBusinessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+target+"` "+target.definition()+",`"+timestamp+"` "+timestamp.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+customerBusinessId+"`=?"+",`"+businessKey+"`=?"+",`"+target+"`=?"+",`"+timestamp+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };


    public static final String[][] contact_details_updates_table_definition = {
        {"_customer_business_id", "VARCHAR(45) NOT NULL"},
        {"_businesskey", "VARCHAR(45) NOT NULL"},
        {"_target", "varchar(45) NOT NULL"},
        {"_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"extra", "INDEX `cdu_customer_business_id_idx` (`_customer_business_id` ASC)"}
    };
    public static enum contactDetailsUpdatesTableDefinition {
        customerBusinessId(new String[]{"_customer_business_id", "VARCHAR(45) NOT NULL","1"}),
        businessKey(new String[]{"_businesskey", "VARCHAR(45) NOT NULL","2"}),
        target(new String[]{"_target", "varchar(45) NOT NULL","3"}),
        timestamp(new String[]{"_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP","4"}),
        extra(new String[]{"extra", "INDEX `cdu_customer_business_id_idx` (`_customer_business_id` ASC)","0"});
      public static String dbName = "customer_management";
      public static String tableName = "_contact_details_updates";
        private String columnName;
        private String definition;
        private int columnIndex;
        private contactDetailsUpdatesTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+customerBusinessId+"`"+",`"+businessKey+"`"+",`"+target+"`"+",`"+timestamp+"`) VALUES (?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+customerBusinessId+"` "+customerBusinessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+target+"` "+target.definition()+",`"+timestamp+"` "+timestamp.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+customerBusinessId+"`=?"+",`"+businessKey+"`=?"+",`"+target+"`=?"+",`"+timestamp+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };

    public static final String[][] address_updates_table_definition = {
        {"_address_business_id", "VARCHAR(150) NOT NULL"},
        {"_businesskey", "VARCHAR(45) NOT NULL"},
        {"_target", "varchar(45) NOT NULL"},
        {"_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"extra", "INDEX `au__address_business_id_idx` (`_address_business_id` ASC)"}
    };
    public static enum addressUpdatesTableDefinition {
        addressBusinessId(new String[]{"_address_business_id", "VARCHAR(150) NOT NULL","1"}),
        businessKey(new String[]{"_businesskey", "VARCHAR(45) NOT NULL","2"}),
        target(new String[]{"_target", "varchar(45) NOT NULL","3"}),
        timestamp(new String[]{"_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP","4"}),
        extra(new String[]{"extra", "INDEX `au__address_business_id_idx` (`_address_business_id` ASC)","0"});
      public static String dbName = "customer_management";
      public static String tableName = "_address_updates";
        private String columnName;
        private String definition;
        private int columnIndex;
        private addressUpdatesTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+addressBusinessId+"`"+",`"+businessKey+"`"+",`"+target+"`"+",`"+timestamp+"`) VALUES (?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+addressBusinessId+"` "+addressBusinessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+target+"` "+target.definition()+",`"+timestamp+"` "+timestamp.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+addressBusinessId+"`=?"+",`"+businessKey+"`=?"+",`"+target+"`=?"+",`"+timestamp+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };

    public static final String[][] touch_point_updates_table_definition = {
        {"_customer_business_id", "VARCHAR(100) NOT NULL"},
        {"_responsible_business_id", "VARCHAR(100) NOT NULL"},
        {"_customer_businesskey", "VARCHAR(45) NOT NULL"},
        {"_responsible_businesskey", "VARCHAR(45) NOT NULL"},
        {"_target", "varchar(45) NOT NULL"},
        {"_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"extra", "INDEX `tpu_target_idx` (`_target` ASC)"}
    };
    public static enum touchPointUpdatesTableDefinition {
        customerBusinessId(new String[]{"_customer_business_id", "VARCHAR(100) NOT NULL","1"}),
        responsibleBusinessId(new String[]{"_responsible_business_id", "VARCHAR(100) NOT NULL","2"}),
        customerBusinessKey(new String[]{"_customer_businesskey", "VARCHAR(45) NOT NULL","3"}),
        responsibleBusinessKey(new String[]{"_responsible_businesskey", "VARCHAR(45) NOT NULL","4"}),
        target(new String[]{"_target", "varchar(45) NOT NULL","5"}),
        timestamp(new String[]{"_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP","6"}),
        extra(new String[]{"extra", "INDEX `tpu_target_idx` (`_target` ASC)","0"});
      public static String dbName = "customer_management";
      public static String tableName = "_touch_point_updates";
        private String columnName;
        private String definition;
        public int columnIndex;
        private touchPointUpdatesTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+customerBusinessId+"`"+",`"+responsibleBusinessId+"`"
        +",`"+customerBusinessKey+"`"+",`"+responsibleBusinessKey+"`"+",`"+target+"`"+",`"+timestamp+"`) VALUES (?,?,?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+customerBusinessId+"` "+customerBusinessId.definition()+",`"+responsibleBusinessId+"` "+responsibleBusinessId.definition()+
    ",`"+customerBusinessKey+"` "+customerBusinessKey.definition()+",`"+responsibleBusinessKey+"` "+responsibleBusinessKey.definition()+",`"+target+"` "+target.definition()+",`"+timestamp+"` "+timestamp.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+customerBusinessId+"`=?"+",`"+responsibleBusinessId+"`=?"+
    ",`"+customerBusinessKey+"`=?"+",`"+responsibleBusinessKey+"`=?"+",`"+target+"`=?"+",`"+timestamp+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };

    public static final String[][] customer_has_process_table_definition = {
        {"_chp_business_id", "ffvarchar(100) NOT NULL"},
        {"_chp_process_instance_id", "varchar(45) NOT NULL"},
        {"_chp_process_key", "varchar(45) NOT NULL"},
        {"extra", "INDEX `chp_business_id_idx` (`_chp_business_id` ASC)"
            + "INDEX `chp_process_key_idx` (`_chp_process_key` ASC)"
            + ",KEY `fk_chp_process2_idx` (`_chp_process_instance_id`)"
            + ",KEY `fk_chp_order2_idx` (`_chp_business_id`)"}
    };
    public static enum customerHasProcessTableDefinition {
        businessId(new String[]{"_chp_business_id", "varchar(100) NOT NULL","1"}),
        processInstanceId(new String[]{"_chp_process_instance_id", "varchar(45) NOT NULL","2"}),
        processKey(new String[]{"_chp_process_key", "varchar(45) NOT NULL","3"}),
        extra(new String[]{"extra", "INDEX `chp_business_id_idx` (`_chp_business_id` ASC)"
            + "INDEX `chp_process_key_idx` (`_chp_process_key` ASC)"
            + ",KEY `fk_chp_process2_idx` (`_chp_process_instance_id`)"
            + ",KEY `fk_chp_order2_idx` (`_chp_business_id`)","0"});
      public static String dbName = "customer_management";
      public static String tableName = "_customer_has_process";
        private String columnName;
        private String definition;
        private int columnIndex;
        private customerHasProcessTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+businessId+"`"+",`"+processInstanceId+"`"+",`"+processKey+"`) VALUES (?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+businessId+"` "+businessId.definition()+",`"+processInstanceId+"` "+processInstanceId.definition()+",`"+processKey+"` "+processKey.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+businessId+"`=?"+",`"+processInstanceId+"`=?"+",`"+processKey+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };
  }

  //refactor 1
  public static class Order {
    public static final int _ORDER_TABLE_INDEX = 0;
    public static final int _ORDER_POSITION_TABLE_INDEX = 1;
    public static final int _ORDER_POSITION_REF_TABLE_INDEX = 2;
    public static final int _ORDER_HAS_ORDER_POSITION_TABLE_INDEX = 3;
    public static final int _ORDER_HAS_ORDER_TABLE_INDEX = 4;
    public static final int _ORDER_HAS_PROCESS_TABLE_INDEX = 5;
    public static final int _ORDER_POSITION_HAS_PROCESS_TABLE_INDEX = 6;
    public static final int _ORDER_UPDATES_TABLE_INDEX = 7;
    public static final int _ORDER_POSITION_UPDATES_TABLE_INDEX = 8;
    //===========================================================================
    public static final String[] order_management_tables = {
        "order",   //0
        "order_position",//1
        "order_position_ref",//2
        "order_has_order_position", //3
        "order_has_order",   //4
        "order_has_process",//5
        "order_position_has_process",//6
        "order_updates",//7
        "order_position_updates"//8
    };
    public static final String[][] order_table_definition = {
        {"_order_id_", "VARCHAR(45) NOT NULL"},
        {"order_number", "VARCHAR(45) NOT NULL"},
        {"order_businesskey", "VARCHAR(150) NOT NULL"},
        {"order_date", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"order_by_date", "TIMESTAMP NULL"},
        {"extra", "PRIMARY KEY (`_order_id_`)"
          + ",UNIQUE KEY `_order_id_UNIQUE` (`_order_id_`)"
//					+ ",UNIQUE KEY `order_number_UNIQUE` (`order_number`)"
          + ",INDEX `order_date_idx` (`order_date` ASC)"
          + ",INDEX `order_by_date_idx` (`order_by_date` ASC)"
          + ",KEY `fk_order_customer1_idx` (`order_businesskey`)"}
    };
    public static final String[][] order_position_table_definition = {
        {"_order_position_id_",  "VARCHAR(45) NOT NULL"},
        {"op_business_id", "varchar(45) NOT NULL"},
        {"op_order_business_id", "varchar(45) NOT NULL"},
        {"op_businesskey", "varchar(45) NOT NULL"},
        {"op_group", "varchar(45) NOT NULL"},// TODO: remove since in instance
        {"op_version", "varchar(45) NOT NULL"},// TODO: remove since in instance
        {"extra", "PRIMARY KEY (`_order_position_id_`)"
            + ",UNIQUE INDEX `op_order_position_id_UNIQUE` (`_order_position_id_` ASC)"
            + ",INDEX `op_business_id_idx` (`op_business_id` ASC)"
            + ",INDEX `op_businesskey_idx` (`op_businesskey` ASC)"
//						+ ",INDEX `op_business_id_idx` (`op_business_id` ASC)"
            + ",INDEX `op_order_business_id_idx` (`op_order_business_id` ASC)"
        }
    };
    public static final String[][] order_position_ref_table_definition = {
        {"_order_position_ref_id_",  "VARCHAR(45) NOT NULL"},
        {"op_position",  "int(11) NOT NULL DEFAULT 0"},
        {"op_quantity",  "int(11) NOT NULL DEFAULT 1"},
        {"op_product_id", "VARCHAR(45) NOT NULL"},
        {"op_model_id", "VARCHAR(45) NOT NULL"},
        {"op_ref_businesskey", "varchar(45) NULL"},
        {"op_date", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"extra", "PRIMARY KEY (`_order_position_ref_id_`)"
            + ",UNIQUE INDEX `op_order_position_ref_id_UNIQUE` (`_order_position_ref_id_` ASC)"
            + ",INDEX `op_ref_businesskey_idx` (`op_ref_businesskey` ASC)"
//						+ ",INDEX `op_business_id_idx` (`op_business_id` ASC)"
//						+ ",INDEX `op_order_business_id_idx` (`op_order_business_id` ASC)"
        }
    };
    public static final String[][] order_has_order_position_table_definition = {
        {"_ohop_order_business_id_",  "VARCHAR(45) NOT NULL"},
        {"_ohop_order_position_business_id",  "VARCHAR(45) NOT NULL"},
        {"extra","INDEX `ohop_order_business_id_idx` (`_ohop_order_business_id_` ASC)"}
    };
    public static final String[][] order_has_process_table_definition = {
        {"_ohp_business_id", "varchar(45) NOT NULL"},
        {"_ohp_process_instance_id", "varchar(45) NOT NULL"},
        {"_ohp_businesskey", "varchar(100) NOT NULL"},
        {"extra", "INDEX `ohp_business_id_idx` (`_ohp_business_id` ASC),"
            + "INDEX `ohp_businesskey_idx` (`_ohp_businesskey` ASC),"
            + "KEY `fk_ohp_process2_idx` (`_ohp_process_instance_id`),"
            + "KEY `fk_ohp_order2_idx` (`_ohp_business_id`)"}
    };
    public static final String[][] order_position_has_process_table_definition = {
        {"_ophp_business_id", "varchar(45) NOT NULL"},
        {"_ophp_process_instance_id", "varchar(45) NOT NULL"},
        {"_ophp_process_key", "varchar(45) NOT NULL"},
        {"extra", "INDEX `ophp_business_id_idx` (`_ophp_business_id` ASC),"
            + "KEY `fk_ophp_process2_idx` (`_ophp_process_instance_id`),"
            + "KEY `fk_ophp_order2_idx` (`_ophp_business_id`)"}
    };
    public static final String[][] order_has_order_table_definition = {
        {"oho_order_id",  "VARCHAR(45) NOT NULL"},
        {"oho_child_order_id",  "VARCHAR(45) NOT NULL"},
        {"extra", "INDEX `oho_order_id_idx` (`oho_order_id` ASC),"
            + "KEY `fk_oho_child_order1` (`oho_child_order_id`),"
            + "KEY `fk_oho_order1` (`oho_order_id`)"
            //					+ ","
            //					+ "CONSTRAINT `fk_ohpo_order1` FOREIGN KEY (`ohpo_order_id`) REFERENCES `order` (`_order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,"
            //					+ "CONSTRAINT `fk_production_order1` FOREIGN KEY (`ohpo_production_order_id`) REFERENCES `production_order` (`_production_order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION"
        }
    };
    public static final String[][] order_updates_table_definition = {
        {"_businesskey", "varchar(45) NOT NULL"},
        {"_business_id", "varchar(45) NOT NULL"},
        {"_target", "varchar(45) NOT NULL"},
        {"timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"extra", "INDEX `o_updates_business_id_idx` (`_business_id` ASC)"
            +",INDEX `o_updates_target_idx` (`_target` ASC)"
            +",INDEX `o_updates_businesskey_idx` (`_businesskey` ASC)"}
    };
    public static final String[][] order_position_updates_table_definition = {
        {"_oposu_business_id", "varchar(45) NOT NULL"},
        {"_oposu_order_business_id", "varchar(45) NOT NULL"},
        {"_oposu_businesskey", "varchar(45) NOT NULL"},
        {"_oposu_target", "varchar(45) NOT NULL"},
        {"extra", "INDEX `oposu_business_id_idx` (`_oposu_business_id` ASC)"
            +",INDEX `oposu_order_business_id_idx` (`_oposu_order_business_id` ASC)"
            +",INDEX `oposu_businesskey_idx` (`_oposu_businesskey` ASC)"
            +",INDEX `oposu_target_idx` (`_oposu_target` ASC)"}
    };

    public static enum orderTableDefinition {
        id(new String[]{"_order_id_", "VARCHAR(45) NOT NULL","1"}),
        businessId(new String[]{"order_businessKey", "VARCHAR(45) NOT NULL","2"}),
        businessKey(new String[]{"order_businesskey", "VARCHAR(150) NOT NULL","3"}),
        date(new String[]{"order_date", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP","4"}),
        byDate(new String[]{"order_by_date", "TIMESTAMP NULL","5"}),
        extra(new String[]{"extra", "PRIMARY KEY (`_order_id_`)"
          + ",UNIQUE KEY `_order_id_UNIQUE` (`_order_id_`)"
//					+ ",UNIQUE KEY `order_number_UNIQUE` (`order_number`)"
          + ",INDEX `order_date_idx` (`order_date` ASC)"
          + ",INDEX `order_by_date_idx` (`order_by_date` ASC)"
          + ",KEY `fk_order_customer1_idx` (`order_businesskey`)","0"});

      public static String dbName = "order_management";
      public static String tableName = "_order";
      private String columnName;
        private String definition;
        private int columnIndex;
        private orderTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+businessId+"`"+",`"+businessKey+"`"+",`"+date+"`"+",`"+byDate+"`) VALUES (?,?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+businessId+"` "+businessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+date+"` "+date.definition()+",`"+byDate+"` "+byDate.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+businessId+"`=?"+",`"+businessKey+"`=?"+",`"+date+"`=?"+",`"+byDate+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };
    public static enum orderPositionTableDefinition {
        id(new String[]{"_order_position_id_",  "VARCHAR(45) NOT NULL","1"}),
        businessId(new String[]{"op_business_id", "varchar(45) NOT NULL","2"}),
        orderBusinessId(new String[]{"op_order_business_id", "varchar(45) NOT NULL","3"}),
        businessKey(new String[]{"op_businesskey", "varchar(45) NOT NULL","4"}),
        group(new String[]{"op_group", "varchar(45) NOT NULL","5"}),// TODO: remove since in instance
        version(new String[]{"op_version", "varchar(45) NOT NULL","6"}),// TODO: remove since in instance
        extra(new String[]{"extra", "PRIMARY KEY (`_order_position_id_`)"
            + ",UNIQUE INDEX `op_order_position_id_UNIQUE` (`_order_position_id_` ASC)"
            + ",INDEX `op_business_id_idx` (`op_business_id` ASC)"
            + ",INDEX `op_businesskey_idx` (`op_businesskey` ASC)"
//						+ ",INDEX `op_business_id_idx` (`op_business_id` ASC)"
            + ",INDEX `op_order_business_id_idx` (`op_order_business_id` ASC)","0"});
      public static String dbName = "order_management";
      public static String tableName = "_order_position";
        private String columnName;
        private String definition;
        private int columnIndex;
        private orderPositionTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+businessId+"`"+",`"+orderBusinessId+"`"+",`"+businessKey+"`"+",`"+group+"`"+",`"+version+"`) VALUES (?,?,?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+"`"+",`"+businessId+"` "+businessId.definition()+",`"+orderBusinessId+"` "+orderBusinessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+group+"` "+group.definition()+",`"+version+"` "+version.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+"`"+",`"+businessId+"`=?"+",`"+orderBusinessId+"`=?"+",`"+businessKey+"`=?"+",`"+group+"`=?"+",`"+version+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };
    public static enum orderPositionReferenceTableDefinition {
        id(new String[]{"_order_position_ref_id_",  "VARCHAR(45) NOT NULL","1"}),
        position(new String[]{"op_position",  "int(11) NOT NULL DEFAULT 0","2"}),
        quantity(new String[]{"op_quantity",  "int(11) NOT NULL DEFAULT 1","3"}),
        productId(new String[]{"op_product_id", "VARCHAR(45) NOT NULL","4"}),
        modelId(new String[]{"op_model_id", "VARCHAR(45) NOT NULL","5"}),
        referenceBusinessKey(new String[]{"op_ref_businesskey", "varchar(45) NULL","6"}),
        date(new String[]{"op_date", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP","7"}),
        extra(new String[]{"extra", "PRIMARY KEY (`_order_position_ref_id_`)"
            + ",UNIQUE INDEX `op_order_position_ref_id_UNIQUE` (`_order_position_ref_id_` ASC)"
            + ",INDEX `op_ref_businesskey_idx` (`op_ref_businesskey` ASC)","0"});
      public static String dbName = "order_management";
      public static String tableName = "_order_position_reference";
        private String columnName;
        private String definition;
        private int columnIndex;
        private orderPositionReferenceTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+position+"`"+",`"+quantity+"`"+",`"+productId+"`"+",`"+modelId+"`"+",`"
              +referenceBusinessKey+"`"+",`"+date+"`) VALUES (?,?,?,?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+position+"` "+position.definition()+",`"+quantity+"` "+quantity.definition()+",`"+productId+"` "+productId.definition()+",`"+modelId+"` "+modelId.definition()+",`"
              +referenceBusinessKey+"` "+referenceBusinessKey.definition()+",`"+date+"` "+date.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+position+"`=?"+",`"+quantity+"`=?"+",`"+productId+"`=?"+",`"+modelId+"`=?"+",`"
              +referenceBusinessKey+"`=?"+",`"+date+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };
    public static enum orderHasOrderPositionTableDefinition {
        orderBusinessId(new String[]{"_ohop_order_business_id_",  "VARCHAR(45) NOT NULL","1"}),
        orderPositionBusinessId(new String[]{"_ohop_order_position_business_id",  "VARCHAR(45) NOT NULL","2"}),
        extra(new String[]{"extra","INDEX `ohop_order_business_id_idx` (`_ohop_order_business_id_` ASC)","0"});
      public static String dbName = "order_management";
      public static String tableName = "_order_has_order_position";
        private String columnName;
        private String definition;
        private int columnIndex;
        private orderHasOrderPositionTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+orderBusinessId+"`"+",`"+orderPositionBusinessId+"`) VALUES (?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+orderBusinessId+"` "+orderBusinessId.definition()+",`"+orderPositionBusinessId+"` "+orderPositionBusinessId.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+orderBusinessId+"`=?"+",`"+orderPositionBusinessId+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };
    public static enum orderHasProcessTableDefinition {
        businessId(new String[]{"_ohp_business_id", "varchar(45) NOT NULL","1"}),
        processInstanceId(new String[]{"_ohp_process_instance_id", "varchar(45) NOT NULL","2"}),
        businessKey(new String[]{"_ohp_businesskey", "varchar(100) NOT NULL","3"}),
        extra(new String[]{"extra", "INDEX `ohp_business_id_idx` (`_ohp_business_id` ASC),"
            + "INDEX `ohp_businesskey_idx` (`_ohp_businesskey` ASC),"
            + "KEY `fk_ohp_process2_idx` (`_ohp_process_instance_id`),"
            + "KEY `fk_ohp_order2_idx` (`_ohp_business_id`)","0"});
      public static String dbName = "order_management";
      public static String tableName = "_order_has_process";
        private String columnName;
        private String definition;
        private int columnIndex;
        private orderHasProcessTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+businessId+"`"+",`"+processInstanceId+"`"+",`"+businessKey+"`) VALUES (?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+businessId+"` "+businessId.definition()+",`"+processInstanceId+"` "+processInstanceId.definition()+",`"+businessKey+"` "+businessKey.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+businessId+"`=?"+",`"+processInstanceId+"`=?"+",`"+businessKey+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };
    public static enum orderPositionHasProcessTableDefinition {
        businessId(new String[]{"_ophp_business_id", "varchar(45) NOT NULL","1"}),
        processInstanceId(new String[]{"_ophp_process_instance_id", "varchar(45) NOT NULL","2"}),
        processKey(new String[]{"_ophp_process_key", "varchar(45) NOT NULL","3"}),
        extra(new String[]{"extra", "INDEX `ophp_business_id_idx` (`_ophp_business_id` ASC),"
            + "KEY `fk_ophp_process2_idx` (`_ophp_process_instance_id`),"
            + "KEY `fk_ophp_order2_idx` (`_ophp_business_id`)","0"});
      public static String dbName = "order_management";
      public static String tableName = "_order_position_has_process";
        private String columnName;
        private String definition;
        private int columnIndex;
        private orderPositionHasProcessTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+businessId+"`"+",`"+processInstanceId+"`"+",`"+processKey+"`) VALUES (?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+businessId+"` "+businessId.definition()+",`"+processInstanceId+"` "+processInstanceId.definition()+",`"+processKey+"` "+processKey.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+businessId+"`=?"+",`"+processInstanceId+"`=?"+",`"+processKey+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };
    public static enum orderHasOrderTableDefinition {
        orderId(new String[]{"oho_order_id",  "VARCHAR(45) NOT NULL","1"}),
        childOrderId(new String[]{"oho_child_order_id",  "VARCHAR(45) NOT NULL","2"}),
        extra(new String[]{"extra", "INDEX `oho_order_id_idx` (`oho_order_id` ASC),"
            + "KEY `fk_oho_child_order1` (`oho_child_order_id`),"
            + "KEY `fk_oho_order1` (`oho_order_id`)","0"});
      public static String dbName = "order_management";
      public static String tableName = "_order_has_order";
        private String columnName;
        private String definition;
        private int columnIndex;
        private orderHasOrderTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+orderId+"`"+",`"+childOrderId+"`) VALUES (?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+orderId+"` "+orderId.definition()+",`"+childOrderId+"` "+childOrderId.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+orderId+"`=?"+",`"+childOrderId+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };
    public static enum orderUpdatesTableDefinition {
        businessKey(new String[]{"_businesskey", "varchar(45) NOT NULL","1"}),
        businessId(new String[]{"_business_id", "varchar(45) NOT NULL","2"}),
        target(new String[]{"_target", "varchar(45) NOT NULL","3"}),
        timestamp(new String[]{"timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP","4"}),
        extra(new String[]{"extra", "INDEX `o_updates_business_id_idx` (`_business_id` ASC)"
            +",INDEX `o_updates_target_idx` (`_target` ASC)"
            +",INDEX `o_updates_businesskey_idx` (`_businesskey` ASC)","0"});
      public static String dbName = "order_management";
      public static String tableName = "_order_updates";
        private String columnName;
        private String definition;
        private int columnIndex;
        private orderUpdatesTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+businessKey+"`"+",`"+businessId+"`"+",`"+target+"`"+",`"+timestamp+"`) VALUES (?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+businessKey+"` "+businessKey.definition()+",`"+businessId+"` "+businessId.definition()+",`"+target+"` "+target.definition()+",`"+timestamp+"` "+timestamp.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+businessKey+"`=?"+",`"+businessId+"`=?"+",`"+target+"`=?"+",`"+timestamp+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };
    public static enum orderPositionUpdatesTableDefinition {
        businessId(new String[]{"_oposu_business_id", "varchar(45) NOT NULL","1"}),
        orderBusinessId(new String[]{"_oposu_order_business_id", "varchar(45) NOT NULL","2"}),
        businessKey(new String[]{"_oposu_businesskey", "varchar(45) NOT NULL","3"}),
        target(new String[]{"_oposu_target", "varchar(45) NOT NULL","4"}),
        extra(new String[]{"extra", "INDEX `oposu_business_id_idx` (`_oposu_business_id` ASC)"
            +",INDEX `oposu_order_business_id_idx` (`_oposu_order_business_id` ASC)"
            +",INDEX `oposu_businesskey_idx` (`_oposu_businesskey` ASC)"
            +",INDEX `oposu_target_idx` (`_oposu_target` ASC)","0"});
      public static String dbName = "order_management";
      public static String tableName = "_order_position_updates";
        private String columnName;
        private String definition;
        private int columnIndex;
        private orderPositionUpdatesTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+businessId+"`"+",`"+orderBusinessId+"`"+",`"+businessKey+"`"+",`"+target+"`) VALUES (?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+businessId+"` "+businessId.definition()+",`"+orderBusinessId+"` "+orderBusinessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+target+"` "+target.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+businessId+"`=?"+",`"+orderBusinessId+"`=?"+",`"+businessKey+"`=?"+",`"+target+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };
  }

  //refactor 1
  public static class Process {
    public static final int _PROCESS_TABLE_INDEX = 0;
    public static final int _ORDER_PROCESS_TABLE_INDEX = 1;
    public static final int _PRODUCTION_PROCESS_TABLE_INDEX = 2;
    public static final int _PRODUCT_PROCESS_TABLE_INDEX = 3;
    public static final int _CUSTOMER_PROCESS_TABLE_INDEX = 4;
    public static final int _PROCESS_UPDATES_TABLE_INDEX = 5;
    public static final int _ORDER_PROCESS_UPDATES_TABLE_INDEX = 6;
    public static final int _PRODUCTION_PROCESS_UPDATES_TABLE_INDEX = 7;
    public static final int _PRODUCT_PROCESS_UPDATES_TABLE_INDEX = 8;
    public static final int _CUSTOMER_PROCESS_UPDATES_TABLE_INDEX = 9;

    public static final String[] process_management_tables = {
        "process",				//0
        "order_process",		//1
        "production_process",   //2 TODO: is unnecessary
        "product_process",		//3
        "customer_process",		//4
        "process_updates",		//5
        "order_process_updates",//6
        "production_process_updates",//7
        "product_process_updates",//8
        "customer_process_updates"//9
    };
    public static final String[][] process_table_definition = {
        {"_process_id_", "VARCHAR(45) NOT NULL"},
        {"execution_id", "varchar(100) DEFAULT NULL"},
        {"instance_id", "varchar(100) NOT NULL"},
        {"business_id", "VARCHAR(100) NOT NULL"},
        {"businesskey", "varchar(100) NOT NULL"},
        {"process_name","VARCHAR(100) NOT NULL"},
        {"definition_id", "varchar(100) NOT NULL"},
        {"tenant_id", "varchar(100) DEFAULT NULL"},
        {"case_instance_id", "varchar(100) DEFAULT NULL"},
        {"ended", "tinyint(1) NOT NULL DEFAULT '1'"},
        {"suspended", "tinyint(1) NOT NULL DEFAULT '1'"},
        {"process_type", "varchar(100) DEFAULT NULL"},
        {"extra", "PRIMARY KEY (`_process_id_`), "
            + "UNIQUE KEY `instance_id_UNIQUE` (`instance_id`)"
            + ",INDEX `p_business_id_idx` (`business_id` ASC)"},
    };
    public static final String[][] production_process_table_definition = {
        {"_process_id_", "VARCHAR(45) NOT NULL"},
        {"instance_id", "varchar(45) NOT NULL"},
        {"business_id", "VARCHAR(100) NOT NULL"},
        {"businesskey", "varchar(45) NOT NULL"},
        {"process_name","VARCHAR(45) NOT NULL"},
        {"definition_id", "varchar(45) NOT NULL"},
        {"tenant_id", "varchar(45) DEFAULT NULL"},
        {"case_instance_id", "varchar(45) DEFAULT NULL"},
        {"ended", "tinyint(1) NOT NULL DEFAULT 0"},
        {"suspended", "tinyint(1) NOT NULL DEFAULT 0"},
        {"process_type", "varchar(100) DEFAULT NULL"},
        {"extra", "PRIMARY KEY (`_process_id_`), "
            + "UNIQUE KEY `instance_id_UNIQUE` (`instance_id`)"
            + ",INDEX `pp_business_id_idx` (`business_id` ASC)"},
    };
    public static final String[][] product_process_table_definition = {
        {"_process_id_", "VARCHAR(45) NOT NULL"},
        {"instance_id", "varchar(45) NOT NULL"},
        {"business_id", "VARCHAR(100) NOT NULL"},
        {"businesskey", "varchar(45) NOT NULL"},
        {"process_name","VARCHAR(45) NOT NULL"},
        {"definition_id", "varchar(45) NOT NULL"},
        {"tenant_id", "varchar(45) DEFAULT NULL"},
        {"case_instance_id", "varchar(45) DEFAULT NULL"},
        {"ended", "tinyint(1) NOT NULL DEFAULT 0"},
        {"suspended", "tinyint(1) NOT NULL DEFAULT 0"},
        {"process_type", "varchar(100) DEFAULT NULL"},
        {"extra", "PRIMARY KEY (`_process_id_`), "
            + "UNIQUE KEY `instance_id_UNIQUE` (`instance_id`)"
            + ",INDEX `pdp_business_id_idx` (`business_id` ASC)"},
    };
    public static final String[][] customer_process_table_definition = {
        {"_process_id_", "VARCHAR(45) NOT NULL"},
        {"instance_id", "varchar(45) NOT NULL"},
        {"business_id", "VARCHAR(100) NOT NULL"},
        {"businesskey", "varchar(45) NOT NULL"},
        {"process_name","VARCHAR(45) NOT NULL"},
        {"definition_id", "varchar(45) NOT NULL"},
        {"tenant_id", "varchar(45) DEFAULT NULL"},
        {"case_instance_id", "varchar(45) DEFAULT NULL"},
        {"ended", "tinyint(1) NOT NULL DEFAULT 0"},
        {"suspended", "tinyint(1) NOT NULL DEFAULT 0"},
        {"process_type", "varchar(100) DEFAULT NULL"},
        {"extra", "PRIMARY KEY (`_process_id_`), "
            + "UNIQUE KEY `instance_id_UNIQUE` (`instance_id`)"
            + ",INDEX `cp_business_id_idx` (`business_id` ASC)"},
    };
    public static final String[][] order_process_table_definition = {
        {"_process_id_", "VARCHAR(45) NOT NULL"},
        {"instance_id", "varchar(45) NOT NULL"},
        {"business_id", "VARCHAR(100) NOT NULL"},
        {"businesskey", "varchar(45) NOT NULL"},
        {"process_name","VARCHAR(45) NOT NULL"},
        {"definition_id", "varchar(45) NOT NULL"},
        {"tenant_id", "varchar(45) DEFAULT NULL"},
        {"case_instance_id", "varchar(45) DEFAULT NULL"},
        {"ended", "tinyint(1) NOT NULL DEFAULT 0"},
        {"suspended", "tinyint(1) NOT NULL DEFAULT 0"},
        {"process_type", "varchar(100) DEFAULT NULL"},
        {"extra", "PRIMARY KEY (`_process_id_`), "
            + "UNIQUE KEY `instance_id_UNIQUE` (`instance_id`)"
            + ",INDEX `op_business_id_idx` (`business_id` ASC)"},
    };
    public static final String[][] process_updates_table_definition = {
        {"_instance_id", "VARCHAR(45) NOT NULL"},
        {"_business_id", "VARCHAR(100) NOT NULL"},
        {"_businesskey", "VARCHAR(100) NOT NULL"},
        {"_target", "varchar(45) NOT NULL"},
        {"extra", "INDEX `prdpr_updates_business_id_idx` (`_business_id` ASC)"}
    };
    public static final String[][] order_process_updates_table_definition = {
        {"_instance_id", "VARCHAR(45) NOT NULL"},
        {"_business_id", "VARCHAR(100) NOT NULL"},
        {"_businesskey", "VARCHAR(100) NOT NULL"},
        {"_target", "varchar(45) NOT NULL"},
        {"extra", "INDEX `ordpr_updates_business_id_idx` (`_business_id` ASC)"}
    };
    public static final String[][] product_process_updates_table_definition = {
        {"_instance_id", "VARCHAR(45) NOT NULL"},
        {"_business_id", "VARCHAR(100) NOT NULL"},
        {"_businesskey", "VARCHAR(100) NOT NULL"},
        {"_target", "varchar(45) NOT NULL"},
        {"extra", "INDEX `prpr_updates_business_id_idx` (`_business_id` ASC)"}
    };
    public static final String[][] production_process_updates_table_definition = {
        {"_instance_id", "VARCHAR(45) NOT NULL"},
        {"_business_id", "VARCHAR(100) NOT NULL"},
        {"_businesskey", "VARCHAR(100) NOT NULL"},
        {"_target", "varchar(45) NOT NULL"},
        {"extra", "INDEX `prdpr_updates_business_id_idx` (`_business_id` ASC)"}
    };
    public static final String[][] customer_process_updates_table_definition = {
        {"_instance_id", "VARCHAR(45) NOT NULL"},
        {"_business_id", "VARCHAR(100) NOT NULL"},
        {"_businesskey", "VARCHAR(100) NOT NULL"},
        {"_target", "varchar(45) NOT NULL"},
        {"extra", "INDEX `cpr_updates_business_id_idx` (`_business_id` ASC)"}
    };
    public static enum processTableDefinition {
        id(new String[]{"_process_id_", "VARCHAR(45) NOT NULL","1"}),
        executionId(new String[]{"execution_id", "varchar(100) DEFAULT NULL","2"}),
        instanceId(new String[]{"instance_id", "varchar(100) NOT NULL","3"}),
        businessId(new String[]{"business_id", "VARCHAR(100) NOT NULL","4"}),
        businessKey(new String[]{"businesskey", "varchar(100) NOT NULL","5"}),
        processName(new String[]{"process_name","VARCHAR(100) NOT NULL","6"}),
        definitionId(new String[]{"definition_id", "varchar(100) NOT NULL","7"}),
        tenantId(new String[]{"tenant_id", "varchar(100) DEFAULT NULL","8"}),
        caseInstanceId(new String[]{"case_instance_id", "varchar(100) DEFAULT NULL","9"}),
        ended(new String[]{"ended", "tinyint(1) NOT NULL DEFAULT '1'","10"}),
        suspended(new String[]{"suspended", "tinyint(1) NOT NULL DEFAULT '1'","11"}),
        processType(new String[]{"process_type", "varchar(100) DEFAULT NULL","12"}),
        extra(new String[]{"extra", "PRIMARY KEY (`_process_id_`) "
            + ",UNIQUE KEY `instance_id_UNIQUE` (`instance_id`)"
            + ",INDEX `p_business_id_idx` (`business_id` ASC)","0"});
      public static String dbName = "process_management";
      public static String tableName = "_process";
        private String columnName;
        private String definition;
        private int columnIndex;
        private processTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+executionId+"`"+",`"+instanceId+"`"+businessId+"`"+",`"+businessKey+"`"+",`"+processName+"`"
              +",`"+definitionId+"`"+",`"+tenantId+"`"+",`"+caseInstanceId+"`"+",`"+ended+"`"+",`"+suspended+"`"+",`"+processType+"`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+executionId+"` "+executionId.definition()+",`"+instanceId+"` "+instanceId.definition()+businessId+"` "+businessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+processName+"` "+processName.definition()+
    ",`"+definitionId+"` "+definitionId.definition()+",`"+tenantId+"` "+tenantId.definition()+",`"+caseInstanceId+"` "+caseInstanceId.definition()+",`"+ended+"` "+ended.definition()+",`"+suspended+"` "+suspended.definition()+",`"+processType+"` "+processType.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+executionId+"`=?"+",`"+instanceId+"`=?"+businessId+"`=?"+",`"+businessKey+"`=?"+",`"+processName+"`=?"+
    ",`"+definitionId+"`=?"+",`"+tenantId+"`=?"+",`"+caseInstanceId+"`=?"+",`"+ended+"`=?"+",`"+suspended+"`=?"+",`"+processType+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };
    public static enum productionProcessTableDefinition {
      id(new String[]{"_process_id_", "VARCHAR(45) NOT NULL","1"}),
      executionId(new String[]{"execution_id", "varchar(100) DEFAULT NULL","2"}),
      instanceId(new String[]{"instance_id", "varchar(100) NOT NULL","3"}),
      businessId(new String[]{"business_id", "VARCHAR(100) NOT NULL","4"}),
      businessKey(new String[]{"businesskey", "varchar(100) NOT NULL","5"}),
      processName(new String[]{"process_name","VARCHAR(100) NOT NULL","6"}),
      definitionId(new String[]{"definition_id", "varchar(100) NOT NULL","7"}),
      tenantId(new String[]{"tenant_id", "varchar(100) DEFAULT NULL","8"}),
      caseInstanceId(new String[]{"case_instance_id", "varchar(100) DEFAULT NULL","9"}),
      ended(new String[]{"ended", "tinyint(1) NOT NULL DEFAULT '1'","10"}),
      suspended(new String[]{"suspended", "tinyint(1) NOT NULL DEFAULT '1'","11"}),
      processType(new String[]{"process_type", "varchar(100) DEFAULT NULL","12"}),
      extra(new String[]{"extra", "PRIMARY KEY (`_process_id_`) "
          + ",UNIQUE KEY `instance_id_UNIQUE` (`instance_id`)"
          + ",INDEX `prp_business_id_idx` (`business_id` ASC)","0"});
      public static String dbName = "process_management";
      public static String tableName = "_production_process";
      private String columnName;
      private String definition;
      private int columnIndex;
      private productionProcessTableDefinition(String[] def) {
        columnName = def[0];
        definition = def[1];
        columnIndex = Integer.valueOf(def[2]);
      }
      public String columnName() { return columnName; }
      public String definition() { return definition; }
      public int columnIndex() { return columnIndex; }
      @Override
      public String toString() {
        return columnName;
      }

      public static String insertPSQL() {
        return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+executionId+"`"+",`"+instanceId+"`"+businessId+"`"+",`"+businessKey+"`"+",`"+processName+"`"
            +",`"+definitionId+"`"+",`"+tenantId+"`"+",`"+caseInstanceId+"`"+",`"+ended+"`"+",`"+suspended+"`"+",`"+processType+"`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
      }
      public static String createSQL() {
        return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+executionId+"` "+executionId.definition()+",`"+instanceId+"` "+instanceId.definition()+businessId+"` "+businessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+processName+"` "+processName.definition()+
    ",`"+definitionId+"` "+definitionId.definition()+",`"+tenantId+"` "+tenantId.definition()+",`"+caseInstanceId+"` "+caseInstanceId.definition()+",`"+ended+"` "+ended.definition()+",`"+suspended+"` "+suspended.definition()+",`"+processType+"` "+processType.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
      }
      public static String updateSQL() {
        return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+executionId+"`=?"+",`"+instanceId+"`=?"+businessId+"`=?"+",`"+businessKey+"`=?"+",`"+processName+"`=?"+
    ",`"+definitionId+"`=?"+",`"+tenantId+"`=?"+",`"+caseInstanceId+"`=?"+",`"+ended+"`=?"+",`"+suspended+"`=?"+",`"+processType+"`=? WHERE ";
      }
      public static String clearSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
      }
      public static String deleteSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
      }

    };
    public static enum productProcessTableDefinition {
      id(new String[]{"_process_id_", "VARCHAR(45) NOT NULL","1"}),
      executionId(new String[]{"execution_id", "varchar(100) DEFAULT NULL","2"}),
      instanceId(new String[]{"instance_id", "varchar(100) NOT NULL","3"}),
      businessId(new String[]{"business_id", "VARCHAR(100) NOT NULL","4"}),
      businessKey(new String[]{"businesskey", "varchar(100) NOT NULL","5"}),
      processName(new String[]{"process_name","VARCHAR(100) NOT NULL","6"}),
      definitionId(new String[]{"definition_id", "varchar(100) NOT NULL","7"}),
      tenantId(new String[]{"tenant_id", "varchar(100) DEFAULT NULL","8"}),
      caseInstanceId(new String[]{"case_instance_id", "varchar(100) DEFAULT NULL","9"}),
      ended(new String[]{"ended", "tinyint(1) NOT NULL DEFAULT '1'","10"}),
      suspended(new String[]{"suspended", "tinyint(1) NOT NULL DEFAULT '1'","11"}),
      processType(new String[]{"process_type", "varchar(100) DEFAULT NULL","12"}),
      extra(new String[]{"extra", "PRIMARY KEY (`_process_id_`) "
          + ",UNIQUE KEY `instance_id_UNIQUE` (`instance_id`)"
          + ",INDEX `prdp_business_id_idx` (`business_id` ASC)","0"});
      public static String dbName = "process_management";
      public static String tableName = "_product_process";
        private String columnName;
        private String definition;
        private int columnIndex;
        private productProcessTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+executionId+"`"+",`"+instanceId+"`"+businessId+"`"+",`"+businessKey+"`"+",`"+processName+"`"
              +",`"+definitionId+"`"+",`"+tenantId+"`"+",`"+caseInstanceId+"`"+",`"+ended+"`"+",`"+suspended+"`"+",`"+processType+"`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+executionId+"` "+executionId.definition()+",`"+instanceId+"` "+instanceId.definition()+businessId+"` "+businessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+processName+"` "+processName.definition()+
    ",`"+definitionId+"` "+definitionId.definition()+",`"+tenantId+"` "+tenantId.definition()+",`"+caseInstanceId+"` "+caseInstanceId.definition()+",`"+ended+"` "+ended.definition()+",`"+suspended+"` "+suspended.definition()+",`"+processType+"` "+processType.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+executionId+"`=?"+",`"+instanceId+"`=?"+businessId+"`=?"+",`"+businessKey+"`=?"+",`"+processName+"`=?"+
    ",`"+definitionId+"`=?"+",`"+tenantId+"`=?"+",`"+caseInstanceId+"`=?"+",`"+ended+"`=?"+",`"+suspended+"`=?"+",`"+processType+"`=? WHERE ";
        }
        public static String clearSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
        }
        public static String deleteSQL() {
          return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
        }

    };
    public static enum customerProcessTableDefinition {
      id(new String[]{"_process_id_", "VARCHAR(45) NOT NULL","1"}),
      executionId(new String[]{"execution_id", "varchar(100) DEFAULT NULL","2"}),
      instanceId(new String[]{"instance_id", "varchar(100) NOT NULL","3"}),
      businessId(new String[]{"business_id", "VARCHAR(100) NOT NULL","4"}),
      businessKey(new String[]{"businesskey", "varchar(100) NOT NULL","5"}),
      processName(new String[]{"process_name","VARCHAR(100) NOT NULL","6"}),
      definitionId(new String[]{"definition_id", "varchar(100) NOT NULL","7"}),
      tenantId(new String[]{"tenant_id", "varchar(100) DEFAULT NULL","8"}),
      caseInstanceId(new String[]{"case_instance_id", "varchar(100) DEFAULT NULL","9"}),
      ended(new String[]{"ended", "tinyint(1) NOT NULL DEFAULT '1'","10"}),
      suspended(new String[]{"suspended", "tinyint(1) NOT NULL DEFAULT '1'","11"}),
      processType(new String[]{"process_type", "varchar(100) DEFAULT NULL","12"}),
      extra(new String[]{"extra", "PRIMARY KEY (`_process_id_`) "
          + ",UNIQUE KEY `instance_id_UNIQUE` (`instance_id`)"
          + ",INDEX `cp_business_id_idx` (`business_id` ASC)","0"});
      public static String dbName = "process_management";
      public static String tableName = "_customer_process";
        private String columnName;
        private String definition;
        private int columnIndex;
        private customerProcessTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+executionId+"`"+",`"+instanceId+"`"+businessId+"`"+",`"+businessKey+"`"+",`"+processName+"`"
              +",`"+definitionId+"`"+",`"+tenantId+"`"+",`"+caseInstanceId+"`"+",`"+ended+"`"+",`"+suspended+"`"+",`"+processType+"`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+executionId+"` "+executionId.definition()+",`"+instanceId+"` "+instanceId.definition()+businessId+"` "+businessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+processName+"` "+processName.definition()+
    ",`"+definitionId+"` "+definitionId.definition()+",`"+tenantId+"` "+tenantId.definition()+",`"+caseInstanceId+"` "+caseInstanceId.definition()+",`"+ended+"` "+ended.definition()+",`"+suspended+"` "+suspended.definition()+",`"+processType+"` "+processType.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+executionId+"`=?"+",`"+instanceId+"`=?"+businessId+"`=?"+",`"+businessKey+"`=?"+",`"+processName+"`=?"+
    ",`"+definitionId+"`=?"+",`"+tenantId+"`=?"+",`"+caseInstanceId+"`=?"+",`"+ended+"`=?"+",`"+suspended+"`=?"+",`"+processType+"`=? WHERE ";
        }
    };
    public static enum orderProcessTableDefinition {
      id(new String[]{"_process_id_", "VARCHAR(45) NOT NULL","1"}),
      executionId(new String[]{"execution_id", "varchar(100) DEFAULT NULL","2"}),
      instanceId(new String[]{"instance_id", "varchar(100) NOT NULL","3"}),
      businessId(new String[]{"business_id", "VARCHAR(100) NOT NULL","4"}),
      businessKey(new String[]{"businesskey", "varchar(100) NOT NULL","5"}),
      processName(new String[]{"process_name","VARCHAR(100) NOT NULL","6"}),
      definitionId(new String[]{"definition_id", "varchar(100) NOT NULL","7"}),
      tenantId(new String[]{"tenant_id", "varchar(100) DEFAULT NULL","8"}),
      caseInstanceId(new String[]{"case_instance_id", "varchar(100) DEFAULT NULL","9"}),
      ended(new String[]{"ended", "tinyint(1) NOT NULL DEFAULT '1'","10"}),
      suspended(new String[]{"suspended", "tinyint(1) NOT NULL DEFAULT '1'","11"}),
      processType(new String[]{"process_type", "varchar(100) DEFAULT NULL","12"}),
      extra(new String[]{"extra", "PRIMARY KEY (`_process_id_`) "
          + ",UNIQUE KEY `instance_id_UNIQUE` (`instance_id`)"
          + ",INDEX `op_business_id_idx` (`business_id` ASC)","0"});
      public static String dbName = "process_management";
      public static String tableName = "_order_process";
        private String columnName;
        private String definition;
        private int columnIndex;
        private orderProcessTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+executionId+"`"+",`"+instanceId+"`"+businessId+"`"+",`"+businessKey+"`"+",`"+processName+"`"
              +",`"+definitionId+"`"+",`"+tenantId+"`"+",`"+caseInstanceId+"`"+",`"+ended+"`"+",`"+suspended+"`"+",`"+processType+"`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+executionId+"` "+executionId.definition()+",`"+instanceId+"` "+instanceId.definition()+businessId+"` "+businessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+processName+"` "+processName.definition()+
    ",`"+definitionId+"` "+definitionId.definition()+",`"+tenantId+"` "+tenantId.definition()+",`"+caseInstanceId+"` "+caseInstanceId.definition()+",`"+ended+"` "+ended.definition()+",`"+suspended+"` "+suspended.definition()+",`"+processType+"` "+processType.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+executionId+"`=?"+",`"+instanceId+"`=?"+businessId+"`=?"+",`"+businessKey+"`=?"+",`"+processName+"`=?"+
    ",`"+definitionId+"`=?"+",`"+tenantId+"`=?"+",`"+caseInstanceId+"`=?"+",`"+ended+"`=?"+",`"+suspended+"`=?"+",`"+processType+"`=? WHERE ";
        }
    };
    public static enum processUpdatesTableDefinition {
        instanceId(new String[]{"_instance_id", "VARCHAR(45) NOT NULL","1"}),
        businessId(new String[]{"_business_id", "VARCHAR(100) NOT NULL","2"}),
        businessKey(new String[]{"_businesskey", "VARCHAR(100) NOT NULL","3"}),
        target(new String[]{"_target", "varchar(45) NOT NULL","4"}),
        extra(new String[]{"extra", "INDEX `pr_updates_business_id_idx` (`_business_id` ASC)","0"});
      public static String dbName = "process_management";
      public static String tableName = "_process_updates";
        private String columnName;
        private String definition;
        private int columnIndex;
        private processUpdatesTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+instanceId+"`"+",`"+businessId+"`"+",`"+businessKey+"`"+",`"+target+"`) VALUES (?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+instanceId+"` "+instanceId.definition()+",`"+businessId+"` "+businessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+target+"` "+target.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+instanceId+"`=?"+",`"+businessId+"`=?"+",`"+businessKey+"`=?"+",`"+target+"`=? WHERE ";
        }
    };
    public static enum orderProcessUpdatesTableDefinition {
        instanceId(new String[]{"_instance_id", "VARCHAR(45) NOT NULL","1"}),
        businessId(new String[]{"_business_id", "VARCHAR(100) NOT NULL","2"}),
        businessKey(new String[]{"_businesskey", "VARCHAR(100) NOT NULL","3"}),
        target(new String[]{"_target", "varchar(45) NOT NULL","4"}),
        extra(new String[]{"extra", "INDEX `opr_updates_business_id_idx` (`_business_id` ASC)","0"});
      public static String dbName = "process_management";
      public static String tableName = "_order_process_updates";
        private String columnName;
        private String definition;
        private int columnIndex;
        private orderProcessUpdatesTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+instanceId+"`"+",`"+businessId+"`"+",`"+businessKey+"`"+",`"+target+"`) VALUES (?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+instanceId+"` "+instanceId.definition()+",`"+businessId+"` "+businessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+target+"` "+target.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+instanceId+"`=?"+",`"+businessId+"`=?"+",`"+businessKey+"`=?"+",`"+target+"`=? WHERE ";
        }
    };
    public static enum productProcessUpdatesTableDefinition {
        instanceId(new String[]{"_instance_id", "VARCHAR(45) NOT NULL","1"}),
        businessId(new String[]{"_business_id", "VARCHAR(100) NOT NULL","2"}),
        businessKey(new String[]{"_businesskey", "VARCHAR(100) NOT NULL","3"}),
        target(new String[]{"_target", "varchar(45) NOT NULL","4"}),
        extra(new String[]{"extra", "INDEX `prdpr_updates_business_id_idx` (`_business_id` ASC)","0"});
      public static String dbName = "process_management";
      public static String tableName = "_product_process_updates";
        private String columnName;
        private String definition;
        private int columnIndex;
        private productProcessUpdatesTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+instanceId+"`"+",`"+businessId+"`"+",`"+businessKey+"`"+",`"+target+"`) VALUES (?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+instanceId+"` "+instanceId.definition()+",`"+businessId+"` "+businessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+target+"` "+target.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+instanceId+"`=?"+",`"+businessId+"`=?"+",`"+businessKey+"`=?"+",`"+target+"`=? WHERE ";
        }
    };
    public static enum productionProcessUpdatesTableDefinition {
        instanceId(new String[]{"_instance_id", "VARCHAR(45) NOT NULL"}),
        businessId(new String[]{"_business_id", "VARCHAR(100) NOT NULL"}),
        businessKey(new String[]{"_businesskey", "VARCHAR(100) NOT NULL"}),
        target(new String[]{"_target", "varchar(45) NOT NULL"}),
        extra(new String[]{"extra", "INDEX `ordpr_updates_business_id_idx` (`_business_id` ASC)"});
      public static String dbName = "process_management";
      public static String tableName = "_production_process_updates";
        private String columnName;
        private String definition;
        private int columnIndex;
        private productionProcessUpdatesTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+instanceId+"`"+",`"+businessId+"`"+",`"+businessKey+"`"+",`"+target+"`) VALUES (?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+instanceId+"` "+instanceId.definition()+",`"+businessId+"` "+businessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+target+"` "+target.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+instanceId+"`=?"+",`"+businessId+"`=?"+",`"+businessKey+"`=?"+",`"+target+"`=? WHERE ";
        }
    };
    public static enum customerProcessUpdatesTableDefinition {
      instanceId(new String[]{"_instance_id", "VARCHAR(45) NOT NULL","1"}),
      businessId(new String[]{"_business_id", "VARCHAR(100) NOT NULL","2"}),
      businessKey(new String[]{"_businesskey", "VARCHAR(100) NOT NULL","3"}),
      target(new String[]{"_target", "varchar(45) NOT NULL","4"}),
      extra(new String[]{"extra", "INDEX `cpr_updates_business_id_idx` (`_business_id` ASC)","0"});
      public static String dbName = "process_management";
      public static String tableName = "_customer_process_updates";
        private String columnName;
        private String definition;
        private int columnIndex;
        private customerProcessUpdatesTableDefinition(String[] def) {
          columnName = def[0];
          definition = def[1];
          columnIndex = Integer.valueOf(def[2]);
        }
        public String columnName() { return columnName; }
        public String definition() { return definition; }
        public int columnIndex() { return columnIndex; }
        @Override
        public String toString() {
          return columnName;
        }

        public static String insertPSQL() {
          return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+instanceId+"`"+",`"+businessId+"`"+",`"+businessKey+"`"+",`"+target+"`) VALUES (?,?,?,?) ";
        }
        public static String createSQL() {
          return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+instanceId+"` "+instanceId.definition()+",`"+businessId+"` "+businessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+target+"` "+target.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
        }
        public static String updateSQL() {
          return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+instanceId+"`=?"+",`"+businessId+"`=?"+",`"+businessKey+"`=?"+",`"+target+"`=? WHERE ";
        }
    };
  }

  public static class Product {
    public static final int _PRODUCT_TABLE_INDEX = 0;
    public static final int _PRODUCT_MODEL_TABLE_INDEX = 1;
    public static final int _PRODUCT_DESCRIPTION_TABLE_INDEX = 2;
    public static final int _PRODUCT_UPDATES_TABLE_INDEX = 3;
    public static final int _PRODUCT_MODEL_UPDATES_TABLE_INDEX = 4;
    public static final int _PRODUCT_ATTRIBUTE_UPDATES_TABLE_INDEX = 5;
    public static final int _PRODUCT_HAS_MODEL_TABLE_INDEX = 6;
    public static final int _PRODUCT_HAS_PROCESS_TABLE_INDEX = 7;

    public static final String[] product_management_tables = {
        "product",					//0
        "product_model",		//1
        "product_description",	//2
        "product_updates",			//3
        "product_model_updates",//4
        "product_attribute_updates",//5
        "product_has_model",//6
        "product_has_process",//7
    };
    public static final String[][] product_table_definition = {
        {"_product_id_", "VARCHAR(45) NOT NULL"},
        {"product_name", "varchar(100) NOT NULL"},
        {"product_businesskey", "varchar(100) NOT NULL"},
        {"product_date", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"product_group", "varchar(45) NOT NULL"},//TODO: remove since in instance
        {"product_version", "varchar(45) NOT NULL"},//TODO: remove since in instance
//				{"product_status", "varchar(45) NOT NULL"},
        {"extra", "PRIMARY KEY (`_product_id_`),UNIQUE KEY `product_id_UNIQUE` (`_product_id_` ASC),"
          + "INDEX `product_group_idx` (`product_group` ASC)"}
    };
    public static final String[][] product_model_table_definition = {
        {"_model_id_", "VARCHAR(45) NOT NULL"},
        {"model_name", "varchar(100) NOT NULL"},
        {"model_release_date", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"model_end_of_life", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"model_businesskey", "varchar(45) NOT NULL"},
        {"model_version", "varchar(45) NOT NULL"},
        {"model_group", "varchar(45) NOT NULL"},
        {"extra", "PRIMARY KEY (`_model_id_`)"
          + ",INDEX `p_model_end_of_life_idx` (`model_end_of_life` ASC)"
          + ",INDEX `p_model_release_date_idx` (`model_release_date` ASC)"}
    };
    public static final String[][] product_description_table_definition = {
        {"_description_id_", "VARCHAR(45) NOT NULL"},
        {"description_product_id", "VARCHAR(45) NOT NULL"},
        {"description_business_id", "varchar(200) NOT NULL"},
        {"description_businesskey", "varchar(45) NOT NULL"},
        {"description_title", "varchar(200) NOT NULL"},
        {"description_body", "LONGTEXT NOT NULL"},
        {"extra", "PRIMARY KEY (`_description_id_`)"
          + ",INDEX `p_description_business_id_idx` (`description_business_id` ASC)"
          + ",INDEX `p_description_businesskey_idx` (`description_businesskey` ASC)"}
    };
    //to hell!!! with both of you for this. seriously
    public static final String[][] product_updates_table_definition = {
        {"_product_name", "varchar(100) NOT NULL"},
        {"_model_id", "VARCHAR(45) NOT NULL"},
        {"_product_businesskey", "varchar(45) NOT NULL"},
        {"_product_target", "varchar(45) NOT NULL"},
        {"_model_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"extra", "INDEX `product_updates_target_idx` (`_product_target` ASC)"
          + ",INDEX `product_updates_product_businesskey_idx` (`_product_businesskey` ASC)"}
    };
    public static final String[][] product_model_updates_table_definition = {
        {"_model_name", "varchar(100) NOT NULL"},
        {"_model_businesskey", "varchar(45) NOT NULL"},
        {"_model_target", "varchar(45) NOT NULL"},
        {"_model_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
        {"extra", "INDEX `model_updates_target_idx` (`_model_target` ASC)"
          +",INDEX `model_updates_businesskey_idx` (`_model_businesskey` ASC)"}
    };
    public static final String[][] product_attribute_updates_table_definition = {
        {"_attribute_id", "VARCHAR(45) NOT NULL"},
        {"_product_id", "VARCHAR(45) NOT NULL"},
        {"_model_id", "VARCHAR(45) NOT NULL"},
        {"_product_group", "varchar(45) NOT NULL"},
        {"_product_businesskey", "varchar(45) NOT NULL"},
        {"_attribute_target", "varchar(100) NOT NULL"},
        {"extra", "INDEX `productattr_updates_target_idx` (`_attribute_target` ASC)"
            +",INDEX `productattr_product_id_idx` (`_product_id` ASC)"}
    };
    public static final String[][] order_product_updates_table_definition = {
        {"op_product_id", "VARCHAR(45) NOT NULL"},
        {"op_product_name", "varchar(100) NOT NULL"},
        {"op_model_id", "VARCHAR(45) NOT NULL"},
        {"op_order_position_number", "varchar("+CampFormats._ORDER_POSITION_NUMBER_SIZE+") NULL"},
        {"op_order_business_id", "varchar("+CampFormats._ORDER_NUMBER_SIZE+") NOT NULL"},
        {"op_businesskey", "varchar(45) NOT NULL"},
        {"op_target", "varchar(45) NOT NULL"},
        {"extra", "INDEX `p_updates_order_number_idx` (`op_order_business_id` ASC)"
            +",INDEX `p_updates_order_position_number` (`op_order_position_number` ASC)"
            +",INDEX `p_updates_product_name_idx` (`op_product_name` ASC)"
            +",INDEX `p_updates_businesskey_idx` (`op_businesskey` ASC)"
            +",INDEX `p_updates_target_idx` (`op_target` ASC)"}
    };
    public static final String[][] product_has_model_table_definition = {
        {"_product_business_id", "varchar(45) NOT NULL"},
        {"_model_id", "VARCHAR(45) NOT NULL"},
        {"extra","INDEX `phm_product_business_id_idx` (`_product_business_id` ASC)"}
    };
    public static final String[][] product_has_process_table_definition = {
        {"_php_product_business_id", "varchar(45) NOT NULL"},
        {"_php_process_instance_id", "varchar(45) NOT NULL"},
        {"_php_processkey", "varchar(45) NOT NULL"},
        {"extra", "INDEX `php_product_business_id_idx` (`_php_product_business_id` ASC)"}
    };

    public static enum productTableDefinition {
        id(new String[]{"_product_id_", "VARCHAR(45) NOT NULL","1"}),
        productName(new String[]{"product_name", "varchar(100) NOT NULL","2"}),
        productBusinessKey(new String[]{"product_businesskey", "varchar(100) NOT NULL","3"}),
        productDate(new String[]{"product_date", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP","4"}),
        productGroup(new String[]{"product_group", "varchar(45) NOT NULL","5"}),//TODO: remove since in instance
        productVersion(new String[]{"product_version", "varchar(45) NOT NULL","6"}),//TODO: remove since in instance
        extra(new String[]{"extra", "PRIMARY KEY (`_product_id_`),UNIQUE KEY `product_id_UNIQUE` (`_product_id_` ASC)," + "INDEX `product_group_idx` (`product_group` ASC)","0"});
      public static String dbName = "product_management";
      public static String tableName = "_product";
      private String columnName;
      private String definition;
      private int columnIndex;
      private productTableDefinition(String[] def) {
        columnName = def[0];
        definition = def[1];
        columnIndex = Integer.valueOf(def[2]);
      }
      public String columnName() { return columnName; }
      public String definition() { return definition; }
      public int columnIndex() {return columnIndex; }
      @Override
      public String toString() {
        return columnName;
      }
      public static String insertPSQL() {
        return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+productName+"`"+",`"+productBusinessKey+"`"+",`"+productDate+"`"
            +",`"+productGroup+"`"+",`"+productVersion+"`) VALUES (?,?,?,?,?,?) ";
      }
      public static String createSQL() {
        return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+productName+"` "+productName.definition()+",`"+productBusinessKey+"` "+productBusinessKey.definition()+",`"+productDate+"` "+productDate.definition()+
    ",`"+productGroup+"` "+productGroup.definition()+",`"+productVersion+"` "+productVersion.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
      }
      public static String updateSQL() {
        return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+productName+"`=?"+",`"+productBusinessKey+"`=?"+",`"+productDate+"`=?"+
    ",`"+productGroup+"`=?"+",`"+productVersion+"`=? WHERE ";
      }
    };
    public static enum productModelTableDefinition {
        id(new String[]{"_model_id_", "VARCHAR(45) NOT NULL","1"}),
        modelName(new String[]{"model_name", "varchar(100) NOT NULL","2"}),
        modelReleaseDate(new String[]{"model_release_date", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP","3"}),
        modelEndOfLife(new String[]{"model_end_of_life", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP","4"}),
        modelBusinessKey(new String[]{"model_businesskey", "varchar(45) NOT NULL","5"}),
        modelVersion(new String[]{"model_version", "varchar(45) NOT NULL","6"}),
        modelGroup(new String[]{"model_group", "varchar(45) NOT NULL","7"}),
        extra(new String[]{"extra", "PRIMARY KEY (`_model_id_`)"+ ",INDEX `p_model_end_of_life_idx` (`model_end_of_life` ASC)"+ ",INDEX `p_model_release_date_idx` (`model_release_date` ASC)","0"});
      public static String dbName = "product_management";
      public static String tableName = "_product_model";
      private String columnName;
      private String definition;
      private int columnIndex;
      private productModelTableDefinition(String[] def) {
        columnName = def[0];
        definition = def[1];
        columnIndex = Integer.valueOf(def[2]);
      }
      public String columnName() { return columnName; }
      public String definition() { return definition; }
      public int columnIndex() {return columnIndex; }
      @Override
      public String toString() {
        return columnName;
      }
      public static String insertPSQL() {
        return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+modelName+"`"+",`"+modelReleaseDate+"`"+",`"+modelEndOfLife+"`"
      +",`"+modelBusinessKey+"`"+",`"+modelVersion+"`"+",`"+modelGroup+"`) VALUES (?,?,?,?,?,?,?) ";
      }
      public static String createSQL() {
        return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+modelName+"` "+modelName.definition()+",`"+modelReleaseDate+"` "+modelReleaseDate.definition()+",`"+modelEndOfLife+"` "+modelEndOfLife.definition()+
    ",`"+modelBusinessKey+"` "+modelBusinessKey.definition()+",`"+modelVersion+"` "+modelVersion.definition()+",`"+modelGroup+"` "+modelGroup.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
      }
      public static String updateSQL() {
        return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+modelName+"`=?"+",`"+modelReleaseDate+"`=?"+",`"+modelEndOfLife+"`=?"+
    ",`"+modelBusinessKey+"`=?"+",`"+modelVersion+"`=?"+",`"+modelGroup+"`=? WHERE ";
      }
      public static String clearSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
      }
      public static String deleteSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
      }

    };
    public static enum productDescriptionTableDefinition {
        id(new String[]{"_description_id_", "VARCHAR(45) NOT NULL","1"}),
        descriptionProductId(new String[]{"description_product_id", "VARCHAR(45) NOT NULL","2"}),
        descriptionBusinessId(new String[]{"description_business_id", "varchar(200) NOT NULL","3"}),
        descriptionBusinessKey(new String[]{"description_businesskey", "varchar(45) NOT NULL","4"}),
        descriptionTitle(new String[]{"descriptionTitle", "varchar(200) NOT NULL","5"}),
        descriptionBody(new String[]{"description_body", "LONGTEXT NOT NULL","6"}),
        extra(new String[]{"extra", "PRIMARY KEY (`_description_id_`)"+ ",INDEX `p_description_business_id_idx` (`description_business_id` ASC)"+ ",INDEX `p_description_businesskey_idx` (`description_businesskey` ASC)","0"});
      public static String dbName = "product_management";
      public static String tableName = "_product_description";
      private String columnName;
      private String definition;
      private int columnIndex;
      private productDescriptionTableDefinition(String[] def) {
        columnName = def[0];
        definition = def[1];
        columnIndex = Integer.valueOf(def[2]);
      }
      public String columnName() { return columnName; }
      public String definition() { return definition; }
      public int columnIndex() {return columnIndex; }
      @Override
      public String toString() {
        return columnName;
      }
      public static String insertPSQL() {
        return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`"+",`"+descriptionProductId+"`"+",`"+descriptionBusinessId+"`"
      +",`"+descriptionBusinessKey+"`"+",`"+descriptionTitle+"`"+",`"+descriptionBody+"`) VALUES (?,?,?,?,?,?) ";
      }
      public static String createSQL() {
        return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+id+"` "+id.definition()+",`"+descriptionProductId+"` "+descriptionProductId.definition()+",`"+descriptionBusinessId+"` "+descriptionBusinessId.definition()+
    ",`"+descriptionBusinessKey+"` "+descriptionBusinessKey.definition()+",`"+descriptionTitle+"` "+descriptionTitle.definition()+",`"+descriptionBody+"` "+descriptionBody.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
      }
      public static String updateSQL() {
        return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+id+"`=?"+",`"+descriptionProductId+"`=?"+",`"+descriptionBusinessId+"`=?"+
    ",`"+descriptionBusinessKey+"`=?"+",`"+descriptionTitle+"`=?"+",`"+descriptionBody+"`=? WHERE ";
      }
      public static String clearSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
      }
      public static String deleteSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
      }

    };
    //to hell!!! with both of you for this. seriously
    public static enum productUpdatesTableDefinition {
        productName(new String[]{"_product_name", "varchar(100) NOT NULL","1"}),
        modelId(new String[]{"_model_id", "VARCHAR(45) NOT NULL","2"}),
        productBusinessKey(new String[]{"_product_businesskey", "varchar(45) NOT NULL","3"}),
        productTarget(new String[]{"_product_target", "varchar(45) NOT NULL","4"}),
        modelTimestamp(new String[]{"_model_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP","5"}),
        extra(new String[]{"extra", "INDEX `product_updates_target_idx` (`_product_target` ASC)"+ ",INDEX `product_updates_product_businesskey_idx` (`_product_businesskey` ASC)","0"});
      public static String dbName = "product_management";
      public static String tableName = "_product_updates";
      private String columnName;
      private String definition;
      private int columnIndex;
      private productUpdatesTableDefinition(String[] def) {
        columnName = def[0];
        definition = def[1];
        columnIndex = Integer.valueOf(def[2]);
      }
      public String columnName() { return columnName; }
      public String definition() { return definition; }
      public int columnIndex() {return columnIndex; }
      @Override
      public String toString() {
        return columnName;
      }
      public static String insertPSQL() {
        return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+productName+"`"+",`"+modelId+"`"+",`"+productBusinessKey+"`"+",`"+productTarget+"`"+",`"+modelTimestamp+"`) VALUES (?,?,?,?,?) ";
      }
      public static String createSQL() {
        return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+productName+"` "+productName.definition()+",`"+modelId+"` "+modelId.definition()+",`"+productBusinessKey+"` "+productBusinessKey.definition()+",`"+productTarget+"` "+productTarget.definition()+",`"+modelTimestamp+"` "+modelTimestamp.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
      }
      public static String updateSQL() {
        return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+productName+"`=?"+",`"+modelId+"`=?"+",`"+productBusinessKey+"`=?"+",`"+productTarget+"`=?"+",`"+modelTimestamp+"`=? WHERE ";
      }
      public static String clearSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
      }
      public static String deleteSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
      }

    };
    public static enum productModelUpdatesTableDefinition {
        modelName(new String[]{"_model_name", "varchar(100) NOT NULL","1"}),
        modelBusinessKey(new String[]{"_model_businesskey", "varchar(45) NOT NULL","2"}),
        modelTarget(new String[]{"_model_target", "varchar(45) NOT NULL","3"}),
        modelTimestamp(new String[]{"_model_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP","4"}),
        extra(new String[]{"extra", "INDEX `model_updates_target_idx` (`_model_target` ASC)"+",INDEX `model_updates_businesskey_idx` (`_model_businesskey` ASC)","0"});
      public static String dbName = "product_management";
      public static String tableName = "_product_model_updates";
      private String columnName;
      private String definition;
      private int columnIndex;
      private productModelUpdatesTableDefinition(String[] def) {
        columnName = def[0];
        definition = def[1];
        columnIndex = Integer.valueOf(def[2]);
      }
      public String columnName() { return columnName; }
      public String definition() { return definition; }
      public int columnIndex() {return columnIndex; }
      @Override
      public String toString() {
        return columnName;
      }
      public static String insertPSQL() {
        return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+modelName+"`"+",`"+modelBusinessKey+"`"+",`"+modelTarget+"`"+",`"+modelTimestamp+"`) VALUES (?,?,?,?) ";
      }
      public static String createSQL() {
        return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+modelName+"` "+modelName.definition()+",`"+modelBusinessKey+"` "+modelBusinessKey.definition()+",`"+modelTarget+"` "+modelTarget.definition()+",`"+modelTimestamp+"` "+modelTimestamp.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
      }
      public static String updateSQL() {
        return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+modelName+"`=?"+",`"+modelBusinessKey+"`=?"+",`"+modelTarget+"`=?"+",`"+modelTimestamp+"`=? WHERE ";
      }
      public static String clearSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
      }
      public static String deleteSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
      }

    };
    public static enum productAttributeUpdatesTableDefinition {
      attributeId(new String[]{"_attribute_id", "VARCHAR(45) NOT NULL","1"}),
      productId(new String[]{"_product_id", "VARCHAR(45) NOT NULL","2"}),
      modelId(new String[]{"_model_id", "VARCHAR(45) NOT NULL","3"}),
      productGroup(new String[]{"_product_group", "varchar(45) NOT NULL","4"}),
      productBusinessKey(new String[]{"_product_businesskey", "varchar(45) NOT NULL","5"}),
      attributeTarget(new String[]{"_attribute_target", "varchar(100) NOT NULL","6"}),
      extra(new String[]{"extra", "INDEX `productattr_updates_target_idx` (`_attribute_target` ASC)"+",INDEX `productattr_product_id_idx` (`_product_id` ASC)","0"});
      public static String dbName = "product_management";
      public static String tableName = "_product_attributes_updates";
      private String columnName;
      private String definition;
      private int columnIndex;
      private productAttributeUpdatesTableDefinition(String[] def) {
        columnName = def[0];
        definition = def[1];
        columnIndex = Integer.valueOf(def[2]);
      }
      public String columnName() { return columnName; }
      public String definition() { return definition; }
      public int columnIndex() {return columnIndex; }
      @Override
      public String toString() {
        return columnName;
      }
      public static String insertPSQL() {
        return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+attributeId+"`"+",`"+productId+"`"+",`"+modelId+"`"
            +",`"+productGroup+"`"+",`"+productBusinessKey+"`"+",`"+attributeTarget+"`) VALUES (?,?,?,?,?,?) ";
      }
      public static String createSQL() {
        return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+attributeId+"` "+attributeId.definition()+",`"+productId+"` "+productId.definition()+",`"+modelId+"` "+modelId.definition()+
    ",`"+productGroup+"` "+productGroup.definition()+",`"+productBusinessKey+"` "+productBusinessKey.definition()+",`"+attributeTarget+"` "+attributeTarget.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
      }
      public static String updateSQL() {
        return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+attributeId+"`=?"+",`"+productId+"`=?"+",`"+modelId+"`=?"+
    ",`"+productGroup+"`=?"+",`"+productBusinessKey+"`=?"+",`"+attributeTarget+"`=? WHERE ";
      }
      public static String clearSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
      }
      public static String deleteSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
      }

    };
    public static enum orderProductUpdatesTableDefinition {
        productId(new String[]{"op_product_id", "VARCHAR(45) NOT NULL","1"}),
        productName(new String[]{"op_product_name", "varchar(100) NOT NULL","2"}),
        modelId(new String[]{"op_model_id", "VARCHAR(45) NOT NULL","3"}),
        orderPositionBusinessId(new String[]{"op_order_position_number", "varchar("+CampFormats._ORDER_POSITION_NUMBER_SIZE+") NULL","4"}),
        orderBusinessId(new String[]{"op_order_business_id", "varchar("+CampFormats._ORDER_NUMBER_SIZE+") NOT NULL","5"}),
        businessKey(new String[]{"op_businesskey", "varchar(45) NOT NULL","6"}),
        target(new String[]{"op_target", "varchar(45) NOT NULL","7"}),
        extra(new String[]{"extra", "INDEX `p_updates_order_number_idx` (`op_order_business_id` ASC)"
            +",INDEX `p_updates_order_position_number` (`op_order_position_number` ASC)"
            +",INDEX `p_updates_product_name_idx` (`op_product_name` ASC)"
            +",INDEX `p_updates_businesskey_idx` (`op_businesskey` ASC)"
            +",INDEX `p_updates_target_idx` (`op_target` ASC)","0"});
      public static String dbName = "product_management";
      public static String tableName = "_order_product_updates";
      private String columnName;
      private String definition;
      private int columnIndex;
      private orderProductUpdatesTableDefinition(String[] def) {
        columnName = def[0];
        definition = def[1];
        columnIndex = Integer.valueOf(def[2]);
      }
      public String columnName() { return columnName; }
      public String definition() { return definition; }
      public int columnIndex() {return columnIndex; }
      @Override
      public String toString() {
        return columnName;
      }
      public static String insertPSQL() {
        return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+productId+"`"+",`"+productName+"`"+",`"+modelId+"`"
            +",`"+orderPositionBusinessId+"`"+",`"+orderBusinessId+"`"+",`"+businessKey+"`"+",`"+target+"`) VALUES (?,?,?,?,?,?,?) ";
      }
      public static String createSQL() {
        return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+productId+"` "+productId.definition()+",`"+productName+"` "+productName.definition()+",`"+modelId+"` "+modelId.definition()+
    ",`"+orderPositionBusinessId+"` "+orderPositionBusinessId.definition()+",`"+orderBusinessId+"` "+orderBusinessId.definition()+",`"+businessKey+"` "+businessKey.definition()+",`"+target+"` "+target.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
      }
      public static String updateSQL() {
        return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+productId+"`=?"+",`"+productName+"`=?"+",`"+modelId+"`=?"+
    ",`"+orderPositionBusinessId+"`=?"+",`"+orderBusinessId+"`=?"+",`"+businessKey+"`=?"+",`"+target+"`=? WHERE ";
      }
      public static String clearSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
      }
      public static String deleteSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
      }

    };
    public static enum productHasModelTableDefinition {
        productBusinessId(new String[]{"_product_business_id", "varchar(45) NOT NULL","1"}),
        modelId(new String[]{"_model_id", "VARCHAR(45) NOT NULL","2"}),
        extra(new String[]{"extra","INDEX `phm_product_business_id_idx` (`_product_business_id` ASC)","0"});
      public static String dbName = "product_management";
      public static String tableName = "_product_has_model";
      private String columnName;
      private String definition;
      private int columnIndex;
      private productHasModelTableDefinition(String[] def) {
        columnName = def[0];
        definition = def[1];
        columnIndex = Integer.valueOf(def[2]);
      }
      public String columnName() { return columnName; }
      public String definition() { return definition; }
      public int columnIndex() {return columnIndex; }
      @Override
      public String toString() {
        return columnName;
      }
      public static String insertPSQL() {
        return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+productBusinessId+"`"+",`"+modelId+"`) VALUES (?,?) ";
      }
      public static String createSQL() {
        return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+productBusinessId+"` "+productBusinessId.definition()+",`"+modelId+"` "+modelId.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
      }
      public static String updateSQL() {
        return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+productBusinessId+"`=?"+",`"+modelId+"`=? WHERE ";
      }
      public static String clearSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
      }
      public static String deleteSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
      }

    };
    public static enum productHasProcessTableDefinition {
        productBusinessId(new String[]{"_php_product_business_id", "varchar(45) NOT NULL","1"}),
        processInstanceId(new String[]{"_php_process_instance_id", "varchar(45) NOT NULL","2"}),
        processKey(new String[]{"_php_processkey", "varchar(45) NOT NULL","3"}),
        extra(new String[]{"extra", "INDEX `php_product_business_id_idx` (`_php_product_business_id` ASC)","0"});
      public static String dbName = "product_management";
      public static String tableName = "_product_has_process";
      private String columnName;
      private String definition;
      private int columnIndex;
      private productHasProcessTableDefinition(String[] def) {
        columnName = def[0];
        definition = def[1];
        columnIndex = Integer.valueOf(def[2]);
      }
      public String columnName() { return columnName; }
      public String definition() { return definition; }
      public int columnIndex() {return columnIndex; }
      @Override
      public String toString() {
        return columnName;
      }
      public static String insertPSQL() {
        return "INSERT INTO (`"+dbName+"`.`"+tableName+"` SET "+"`"+productBusinessId+"`"+",`"+processInstanceId+"`"+",`"+processKey+"`) VALUES (?,?,?) ";
      }
      public static String createSQL() {
        return "CREATE TABLE IF NOT EXISTS `"+dbName+"`.`"+tableName+"` ( "+"`"+productBusinessId+"` "+productBusinessId.definition()+",`"+processInstanceId+"` "+processInstanceId.definition()+",`"+processKey+"` "+processKey.definition()+" ) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
      }
      public static String updateSQL() {
        return "UPDATE `"+dbName+"`.`"+tableName+"` SET "+"`"+productBusinessId+"`=?"+",`"+processInstanceId+"`=?"+",`"+processKey+"`=? WHERE ";
      }
      public static String clearSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"`";
      }
      public static String deleteSQL() {
        return "DELETE FROM `"+dbName+"`.`"+tableName+"` WHERE ";
      }

    };
  }

  public static String oUpdateInstancePSQL = "UPDATE %s SET current_instance_id=? WHERE order_number LIKE ?";
  public static String oUpdateInstanceSQL = "UPDATE %s SET current_instance_id='%s' WHERE order_number LIKE '%s%%'";
  public static String opUpdateInstancePSQL = "UPDATE %s SET current_instance_id=? WHERE number LIKE ? AND order_number LIKE ?";
  public static String opUpdateInstanceSQL = "UPDATE %s SET current_instance_id='%s' WHERE number='%s' AND order_number LIKE '%s%%'";

  public static final String getValueFieldDefinition(String field,ValueType valueType){
      String[][] tableDefinition = System.attribute_value_table_def.get(valueType);
      return getFieldDefinition(field,tableDefinition);
  }
  public static final String getFieldDefinition(String field, String[][] tableDefinition){
    String retVal = null;
    for(String[] def:tableDefinition){
      if(def[0].equals(field)){
        retVal =  def[1]+" ";
      }
    }
    return retVal;
  }
  public static final String cmTable(int dbIndex, int tableIndex){
    return  " `"+database[dbIndex]+"`.`"+Customer.customer_management_tables[tableIndex]+"` ";
  }
  public static final String omTable(int dbIndex, int tableIndex){
    return  " `"+database[dbIndex]+"`.`"+Order.order_management_tables[tableIndex]+"` ";
  }
  public static final String pmTable(int dbIndex, int tableIndex){
    return  " `"+database[dbIndex]+"`.`"+Product.product_management_tables[tableIndex]+"` ";
  }
  public static final String prmTable(int dbIndex, int tableIndex){
    return  " `"+database[dbIndex]+"`.`"+Process.process_management_tables[tableIndex]+"` ";
  }
//	public static final String supTable(int dbIndex, int tableIndex){
//		return  " `"+database[dbIndex]+"`.`"+Support.support_tables[tableIndex]+"` ";
//	}
  public static final String sysTable(int dbIndex, int tableIndex){
    return  " `"+database[dbIndex]+"`.`"+System.system_tables[tableIndex]+"` ";
  }
  public static final String logTable(int dbIndex, int tableIndex){
    return  " `"+database[dbIndex]+"`.`"+Logging.logging_tables[tableIndex]+"` ";
  }
//  public static final String oTable(int dbIndex, int tableIndex){
//        return  " `"+database[dbIndex]+"`.`"+Order.order_tables[tableIndex]+"` ";
//    }

  public static String[] columnArray(String[][] tableDefinition,dbActionType action){
    return _columnArray("",tableDefinition,action);
  }
  /**
   *
   * @param table table name
   * @param tdef table definition
   * @param action type of db action
   * @param log log switch
   * @return a String representation of the SQL 'column_name [column_definition] [=?]' dependent on the type of DB action.
   */
  //TODO: add logging;
  public static String[] _columnArray(String table, String[][] tdef, dbActionType action){
    return getColumnArray(table,tdef,action);
  }

  public static String columns(String[][] tableDefinition,dbActionType action){
    return _columns("",tableDefinition,action);
  }
  public static String _columns(String table,String[][] tdef, dbActionType action){
    return getColumnString(table,tdef,action);
  }

  /**
   *
   * @param tdef table definition
   * @param action type of db action
   * @param log log switch
   * @return a String representation of the SQL 'column_name [column_definition] [=?]' dependent on the type of DB action.
   */
  //TODO: add logging;
  public static String _columns(String[][] tdef, dbActionType action){
    return getColumnString("",tdef,action);
  }

  //TODO: add dbAction and handle counter value that way to get rid of redundency
  public static String getColumnString(String table,String[][] tableDefinition,dbActionType action){
    String colDef = "";
    table = ((table.isEmpty())?"":"`"+table+"`.");

    boolean create = action.equals(dbActionType.CREATE);
    boolean insert = action.equals(dbActionType.INSERT);
    boolean update = action.equals(dbActionType.UPDATE); // dirty batch for use with with String.format
    boolean updateb = action.equals(dbActionType.UPDATEB); // batch for use with prepared statement
    boolean select = action.equals(dbActionType.SELECT);
//		boolean hasExtra = tableDefinition[tableDefinition.length-1][0].equals("extra");
//		boolean hasId = tableDefinition[0][0].contains("id_".subSequence(0, 3));

    boolean start = true;
    // walk the definition array
    for(String[] cr:tableDefinition){
      if(!start) {
        colDef += ",";
      } else {
        start = false;
      }
      boolean extra = cr[0].equals("extra");
      if(extra && !create) {
        continue;
      }
      colDef += ((!extra)? table + "`"+cr[0]+"`":"")
             + ((create)?" "+cr[1]:"")
             + ((update)?"=%s":"")
             + ((updateb)?"=?":"");
    }
    return colDef;
  }

  public static String[] getColumnArray(String table, String[][] tableDefinition,dbActionType action){

    table = ((table.isEmpty())?"":"`"+table+"`.");

    boolean create = action.equals(dbActionType.CREATE);
    boolean insert = action.equals(dbActionType.INSERT);
    boolean update = action.equals(dbActionType.UPDATE);
    boolean select = action.equals(dbActionType.SELECT);

    int cols = tableDefinition.length;

    if(insert||update||select) {
      cols = cols -1;
    }

    int count = 0;

    String[] aCol = new String[cols];

    for(String[] cr:tableDefinition){

      boolean extra = cr[0].equals("extra");
      if(extra && !create) {
        continue;
      }
      aCol[count] = ((!extra)? table + "`"+cr[0]+"`":"")
             + ((create)?" "+cr[1]:"")
             + ((update)?"`=%s":"");
      count++;
    }
    return aCol;
  }


}
