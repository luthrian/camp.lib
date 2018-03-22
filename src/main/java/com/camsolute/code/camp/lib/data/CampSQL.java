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
package com.camsolute.code.camp.lib.data;

import java.util.EnumMap;

import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;
import com.camsolute.code.camp.lib.utilities.Util;


public class CampSQL {

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
				{"_log_id_", "INT(11) NOT NULL AUTO_INCREMENT"},
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
					+ "UNIQUE KEY `_i_instance_id_UNIQUE` (`_instance_id`), "
					+ "INDEX `_i_object_business_id_idx` (`_object_business_id` ASC),"
					+ "INDEX `_i_instance_id_idx` (`_instance_id` ASC)"}
		};


	}

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
				{"_attribute_type_id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"name", "varchar(100) NOT NULL"},
				{"type", "varchar(45) NOT NULL"},
				{"parent_id", "int(11) NOT NULL"},
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
				{"_attribute_value_id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"object_id", "int(11) NOT NULL"},
				{"attribute_type_id", "int(11) NOT NULL"},
				{"parent_type_id", "int(11) NOT NULL"},
        {"attribute_parent_id", "int(11) NOT NULL"},
				{"value_id", "int(11) NOT NULL"},
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
				{"_au_attribute_value_id", "int(11) NOT NULL"},
//				{"_au_object_id", "int(11) NOT NULL"},
				{"_au_businesskey", "int(11) NOT NULL"},
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
          {"_value_id_", "int(11) NOT NULL AUTO_INCREMENT"},
          {"object_id", "int(11) NOT NULL"},
          {"attribute_type", "varchar(45) NOT NULL"},
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
          {"_value_id_", "int(11) NOT NULL AUTO_INCREMENT"},
          {"object_id", "int(11) NOT NULL"},
          {"attribute_type", "varchar(45) NOT NULL"},
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
          {"_value_id_", "int(11) NOT NULL AUTO_INCREMENT"},
          {"object_id", "int(11) NOT NULL"},
          {"attribute_type", "varchar(45) NOT NULL"},
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
          {"_value_id_", "int(11) NOT NULL AUTO_INCREMENT"},
          {"object_id", "int(11) NOT NULL"},
          {"attribute_type", "varchar(45) NOT NULL"},
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
          {"_value_id_", "int(11) NOT NULL AUTO_INCREMENT"},
          {"object_id", "int(11) NOT NULL"},
          {"attribute_type", "varchar(45) NOT NULL"},
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
          {"_value_id_", "int(11) NOT NULL AUTO_INCREMENT"},
          {"object_id", "int(11) NOT NULL"},
          {"attribute_type", "varchar(45) NOT NULL"},
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
          {"_value_id_", "int(11) NOT NULL AUTO_INCREMENT"},
          {"object_id", "int(11) NOT NULL"},
          {"attribute_type", "varchar(45) NOT NULL"},
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
      
      public static EnumMap<AttributeType,String> attribute_value_tables = null;
      static {
    	  attribute_value_tables = new EnumMap<AttributeType,String>(AttributeType.class);

    	  attribute_value_tables.put(AttributeType._integer,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._INTEGER_VALUE_INDEX));
    	  attribute_value_tables.put(AttributeType._string,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._STRING_VALUE_INDEX));
    	  attribute_value_tables.put(AttributeType._datetime,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._TIMESTAMP_VALUE_INDEX));
          attribute_value_tables.put(AttributeType._timestamp,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._TIMESTAMP_VALUE_INDEX));
          attribute_value_tables.put(AttributeType._boolean,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._BOOLEAN_VALUE_INDEX));
          attribute_value_tables.put(AttributeType._time,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._TIMESTAMP_VALUE_INDEX));
          attribute_value_tables.put(AttributeType._date,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._TIMESTAMP_VALUE_INDEX));
          attribute_value_tables.put(AttributeType._text,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._TEXT_VALUE_INDEX));
          attribute_value_tables.put(AttributeType._enum,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._COMPLEX_VALUE_INDEX));
          attribute_value_tables.put(AttributeType._set,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._COMPLEX_VALUE_INDEX));
          attribute_value_tables.put(AttributeType._table,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._COMPLEX_VALUE_INDEX));
          attribute_value_tables.put(AttributeType._complex,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._COMPLEX_VALUE_INDEX));
          attribute_value_tables.put(AttributeType._list,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._COMPLEX_VALUE_INDEX));
          attribute_value_tables.put(AttributeType._map,CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._COMPLEX_VALUE_INDEX));    	  
      }
      
      public static EnumMap<AttributeType,String[][]> attribute_value_table_def = null;
      static {
        attribute_value_table_def = new EnumMap<AttributeType,String[][]>(AttributeType.class);

        attribute_value_table_def.put(AttributeType._integer,_integer_value_table_definition);
        attribute_value_table_def.put(AttributeType._string,_string_value_table_definition);
        attribute_value_table_def.put(AttributeType._datetime,_timestamp_value_table_definition);
        attribute_value_table_def.put(AttributeType._timestamp,_timestamp_value_table_definition);
        attribute_value_table_def.put(AttributeType._boolean,_boolean_value_table_definition);
        attribute_value_table_def.put(AttributeType._time,_timestamp_value_table_definition);
        attribute_value_table_def.put(AttributeType._date,_timestamp_value_table_definition);
        attribute_value_table_def.put(AttributeType._text,_text_value_table_definition);
        attribute_value_table_def.put(AttributeType._enum,_complex_value_table_definition);
        attribute_value_table_def.put(AttributeType._set,_complex_value_table_definition);
        attribute_value_table_def.put(AttributeType._table,_complex_value_table_definition);
        attribute_value_table_def.put(AttributeType._complex,_complex_value_table_definition);
        attribute_value_table_def.put(AttributeType._list,_complex_value_table_definition);
        attribute_value_table_def.put(AttributeType._map,_complex_value_table_definition);
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

	}

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
				{"_customer_id_", "int(11) NOT NULL AUTO_INCREMENT"},
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

		public static final String[][] customer_ref_table_definition = {
				{"_customer_ref_id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"customer_id", "int(11) NOT NULL"},
				{"customer_address_id", "int(11) NOT NULL"},
				{"customer_delivery_address_id", "int(11) NULL"},
				{"customer_contact_id", "int(11) not null"},
				{"customer_touchpoint_id", "int(11) NOT NULL"},
				{"customer_group", "varchar(45) NOT NULL"},
				{"extra", "PRIMARY KEY (`_customer_id_`)"
						+ ",UNIQUE KEY `_customer_ref_id_UNIQUE` (`_customer_ref_id_`)"
						+ ",INDEX `customer_business_id_idx` (`customer_business_id` ASC)"
						+ ",INDEX `customer_businesskey_idx` (`customer_businesskey` ASC)"
						+ ",INDEX `customer_group_idx` (`customer_group` ASC)"}
		};

		public static final String[][] contact_details_table_definition = {
				{"_contact_id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"c_customer_business_id", "VARCHAR(100) NOT NULL AUTO_INCREMENT"},
				{"c_customer_businesskey", "VARCHAR(100) NOT NULL AUTO_INCREMENT"},
				{"contact_email", "varchar(150) NOT NULL"},
				{"contact_mobile", "varchar(45) NOT NULL"},
				{"contact_telephone", "varchar(45) NOT NULL"},
				{"contact_skype", "varchar(45) not null"},
				{"contact_misc", "varchar(45) not null"},
				{"extra", "PRIMARY KEY (`_contact_id_`)"
						+ ",UNIQUE KEY `contact_id_UNIQUE` (`_contact_id_`)"
						+ ",UNIQUE KEY `contact_email_UNIQUE` (`contact_email_`)"}
		};

		public static final String[][] address_table_definition = {
				{"_address_id_", "int(11) NOT NULL AUTO_INCREMENT"},
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

		public static final String[][] touch_point_table_definition = {
				{"_touchpoint_id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"_topic", "varchar(45) NOT NULL"},
				{"extra", "PRIMARY KEY (`_touchpoint_id_`)"
						+ ",UNIQUE KEY `_touchpoint_id_UNIQUE` (`_touchpoint_id_`)"}
		};

		public static final String[][] touch_point_ref_table_definition = {
				{"_touchpoint_ref_id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"_touchpoint_id", "int(11) NOT NULL"},
				{"_customer_id", "int(11) NOT NULL"},
				{"_customer_business_id", "varchar(100) NOT NULL"},
				{"_responsible_business_id", "varchar(100) NOT NULL"},
				{"_responsible_businesskey", "varchar(100) NOT NULL"},
				{"_next_date", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"_minutes", "LONGTEXT NOT NULL"},
				{"extra", "PRIMARY KEY (`_touchpoint_ref_id_`)"
						+ ",UNIQUE KEY `_touchpoint_ref_id_UNIQUE` (`_touchpoint_ref_id_`)"
						+ ",INDEX `_responsible_businesskey_idx` (`_responsible_businesskey` ASC)"
						+ ",INDEX `_responsible_business_id_idx` (`_responsible_business_id` ASC)"}
		};

		public static final String[][] customer_updates_table_definition = {
				{"_customer_business_id", "VARCHAR(45) NOT NULL"},
				{"_businesskey", "VARCHAR(45) NOT NULL"},
				{"_target", "varchar(45) NOT NULL"},
				{"_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"extra", "INDEX `cu_customer_business_id_idx` (`_customer_business_id` ASC)"}
		};

		public static final String[][] contact_details_updates_table_definition = {
				{"_customer_business_id", "VARCHAR(45) NOT NULL"},
				{"_businesskey", "VARCHAR(45) NOT NULL"},
				{"_target", "varchar(45) NOT NULL"},
				{"_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"extra", "INDEX `cu_customer_id_idx` (`_customer_id` ASC)"}
		};

		public static final String[][] address_updates_table_definition = {
				{"_address_business_id", "VARCHAR(150) NOT NULL"},
				{"_businesskey", "VARCHAR(45) NOT NULL"},
				{"_target", "varchar(45) NOT NULL"},
				{"_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"extra", "INDEX `cu_customer_id_idx` (`_customer_id` ASC)"}
		};

		public static final String[][] touch_point_updates_table_definition = {
				{"_customer_business_id", "VARCHAR(100) NOT NULL"},
				{"_businesskey", "VARCHAR(100) NOT NULL"},
				{"_target", "varchar(45) NOT NULL"},
				{"_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"extra", "INDEX `cu_customer_id_idx` (`_customer_id` ASC)"}
		};

		public static final String[][] customer_has_process_table_definition = {
				{"_chp_business_id", "varchar(100) NOT NULL"},
				{"_chp_process_instance_id", "varchar(45) NOT NULL"},
				{"_chp_process_key", "varchar(45) NOT NULL"},
				{"extra", "INDEX `chp_business_id_idx` (`_chp_business_id` ASC)"
					  + "INDEX `chp_process_key_idx` (`_chp_process_key` ASC)"
						+ ",KEY `fk_chp_process2_idx` (`_chp_process_instance_id`)"
						+ ",KEY `fk_chp_order2_idx` (`_chp_business_id`)"}
		};
	}
	
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
				{"_order_id_", "INT(11) NOT NULL AUTO_INCREMENT"},
				{"order_number", "VARCHAR(45) NOT NULL"},
				{"order_businesskey", "VARCHAR(45) NOT NULL"},
				{"order_date", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"order_by_date", "TIMESTAMP NULL"},
				{"extra", "PRIMARY KEY (`_order_id_`)"
						+ ",UNIQUE KEY `_order_id_UNIQUE` (`_order_id_`)"
						+ ",UNIQUE KEY `order_number_UNIQUE` (`order_number`)"
						+ ",INDEX `order_date_idx` (`order_date` ASC)"
						+ ",INDEX `order_by_date_idx` (`order_by_date` ASC)"
						+ ",KEY `fk_order_customer1_idx` (`order_businesskey`)"}
		};
		public static final String[][] order_position_table_definition = {
				{"_order_position_id_",  "int(11) NOT NULL AUTO_INCREMENT"},
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
				{"_order_position_ref_id_",  "int(11) NOT NULL AUTO_INCREMENT"},
				{"op_position",  "int(11) NOT NULL DEFAULT 0"},
				{"op_quantity",  "int(11) NOT NULL DEFAULT 1"},
				{"op_product_id", "int(11) NOT NULL"},
				{"op_model_id", "int(11) NOT NULL"},
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
				{"_ohp_business_id", "varchar(10) NOT NULL"},
				{"_ohp_process_instance_id", "varchar(45) NOT NULL"},
				{"_ohp_businesskey", "varchar(45) NOT NULL"},
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
				{"oho_order_id",  "int(11) NOT NULL"},
				{"oho_child_order_id",  "int(11) NOT NULL"},
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
	}
	
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
				"production_process", 	//2 TODO: is unnecessary
				"product_process",		//3
				"customer_process",		//4
				"process_updates",		//5
				"order_process_updates",//6
				"production_process_updates",//7
				"product_process_updates",//8
				"customer_process_updates"//9
		};
		public static final String[][] process_table_definition = {
				{"_process_id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"execution_id", "varchar(45) DEFAULT NULL"},
				{"instance_id", "varchar(45) NOT NULL"},
				{"business_id", "VARCHAR(100) NOT NULL"},
				{"businesskey", "varchar(45) NOT NULL"},
				{"process_name","VARCHAR(45) NOT NULL"},
				{"definition_id", "varchar(45) NOT NULL"},
				{"tenant_id", "varchar(45) DEFAULT NULL"},
				{"case_instance_id", "varchar(45) DEFAULT NULL"},
				{"ended", "tinyint(1) NOT NULL DEFAULT '1'"},
				{"suspended", "tinyint(1) NOT NULL DEFAULT '1'"},
				{"process_type", "varchar(100) DEFAULT NULL"},
				{"extra", "PRIMARY KEY (`_process_id_`), "
						+ "UNIQUE KEY `instance_id_UNIQUE` (`instance_id`)"
						+ ",INDEX `p_business_id_idx` (`business_id` ASC)"},
		};
		public static final String[][] production_process_table_definition = {
				{"_process_id_", "int(11) NOT NULL AUTO_INCREMENT"},
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
				{"_process_id_", "int(11) NOT NULL AUTO_INCREMENT"},
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
				{"_process_id_", "int(11) NOT NULL AUTO_INCREMENT"},
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
				{"_process_id_", "int(11) NOT NULL AUTO_INCREMENT"},
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
	}	
	
	public static class Product {
		public static final int _PRODUCT_TABLE_INDEX = 0;
		public static final int _PRODUCT_MODEL_TABLE_INDEX = 1;
		public static final int _PRODUCT_UPDATES_TABLE_INDEX = 2;
		public static final int _PRODUCT_MODEL_UPDATES_TABLE_INDEX = 3;
		public static final int _PRODUCT_ATTRIBUTE_UPDATES_TABLE_INDEX = 4;
		public static final int _PRODUCT_HAS_MODEL_TABLE_INDEX = 5;
		public static final int _PRODUCT_HAS_PROCESS_TABLE_INDEX = 6;
	
		public static final String[] product_management_tables = {
				"product",					//0
				"product_model",		//1
				"product_updates",			//2
				"product_model_updates",//3
				"product_attribute_updates",//4
				"product_has_model",//5
				"product_has_process",//6
		};
		public static final String[][] product_table_definition = {
				{"_product_id_", "int(11) NOT NULL AUTO_INCREMENT"},
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
				{"_model_id_", "int(11) NOT NULL AUTO_INCREMENT"},
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
		//to hell!!! with both of you for this. seriously
		public static final String[][] product_updates_table_definition = {
				{"_product_name", "varchar(100) NOT NULL"},
				{"_model_id", "int(11) NOT NULL"},
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
				{"_attribute_id", "int(11) NOT NULL"},
				{"_product_id", "int(11) NOT NULL"},
				{"_model_id", "int(11) NOT NULL"},
				{"_product_group", "varchar(45) NOT NULL"},
				{"_product_businesskey", "varchar(45) NOT NULL"},
				{"_attribute_target", "varchar(100) NOT NULL"},
				{"extra", "INDEX `productattr_updates_target_idx` (`_attribute_target` ASC)"
						+",INDEX `productattr_product_id_idx` (`_product_id` ASC)"}
		};
		public static final String[][] order_product_updates_table_definition = {
				{"op_product_id", "int(11) NOT NULL"},
				{"op_product_name", "varchar(100) NOT NULL"},
				{"op_model_id", "int(11) NOT NULL"},
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
				{"_model_id", "int(11) NOT NULL"},
				{"extra","INDEX `phm_product_business_id_idx` (`_product_business_id` ASC)"}
		};
		public static final String[][] product_has_process_table_definition = {
				{"_php_product_business_id", "varchar(45) NOT NULL"},
				{"_php_process_instance_id", "varchar(45) NOT NULL"},
				{"_php_processkey", "varchar(45) NOT NULL"},
				{"extra", "INDEX `php_product_business_id_idx` (`_php_product_business_id` ASC)"}
		};
	}
	public static String oUpdateInstancePSQL = "UPDATE %s SET current_instance_id=? WHERE order_number LIKE ?";
	public static String oUpdateInstanceSQL = "UPDATE %s SET current_instance_id='%s' WHERE order_number LIKE '%s%%'";
	public static String opUpdateInstancePSQL = "UPDATE %s SET current_instance_id=? WHERE number LIKE ? AND order_number LIKE ?";
	public static String opUpdateInstanceSQL = "UPDATE %s SET current_instance_id='%s' WHERE number='%s' AND order_number LIKE '%s%%'";

  public static final String getValueFieldDefinition(String field,AttributeType attributeType){
      String[][] tableDefinition = System.attribute_value_table_def.get(attributeType);
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


}
