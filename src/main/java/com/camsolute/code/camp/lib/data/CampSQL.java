/*******************************************************************************
 * Copyright (C) 2017 Christopher Campbell (campbellccc@gmail.com)
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
 * 	Christopher Campbell (campbellccc@gmail.com) - all code prior and post initial release
 ******************************************************************************/
package com.camsolute.code.camp.lib.data;

import java.util.EnumMap;

import com.camsolute.code.camp.lib.Attribute;
import com.camsolute.code.camp.lib.Attribute.AttributeType;
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

	public static final String[] database = {
			"customer_management",	//0
			"process_management",	//1
			"order_management",		//2
			"product_management",	//3
			"order_tables",			//4
			"support_tables",		//5
			"system_tables",		//6
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

	public static class System {

		public static final int _ATTRIBUTE_TYPE_INDEX = 0;
		public static final int _ATTRIBUTE_VALUE_INDEX = 1;
		
		public static final String[] system_tables = {
				"_attribute_type", //1
				"_attribute_value", //2
				"_integer_value", //3
				"_string_value", //4
				"_timestamp_value", //5
				"_boolean_value", //6
				"_text_value", //7
				"_complex_value", //8
				"_blob_value" //9
		};
		
		public static EnumMap<AttributeType,String> attribute_value_table = null;
		static {
			attribute_value_table = new EnumMap<AttributeType,String>(AttributeType.class);
			
			attribute_value_table.put(AttributeType._integer,"_integer_value");
			attribute_value_table.put(AttributeType._string,"_string_value");
			attribute_value_table.put(AttributeType._datetime,"_timestamp_value");
			attribute_value_table.put(AttributeType._timestamp,"_timestamp_value");
			attribute_value_table.put(AttributeType._boolean,"_boolean_value");
			attribute_value_table.put(AttributeType._time,"_timestamp_value");
			attribute_value_table.put(AttributeType._date,"_timestamp_value");
			attribute_value_table.put(AttributeType._text,"_text_value");
			attribute_value_table.put(AttributeType._enum,"_complex_value");
			attribute_value_table.put(AttributeType._set,"_complex_value");
			attribute_value_table.put(AttributeType._table,"_complex_value");
			attribute_value_table.put(AttributeType._complex,"_complex_value");
			attribute_value_table.put(AttributeType._list,"_complex_value");
			attribute_value_table.put(AttributeType._map,"_complex_value");
		};
		public static final String[][] _attribute_type_table_definition = {
				{"type_id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"name", "varchar(45) NOT NULL"},
				{"type", "varchar(45) NOT NULL"},
				{"default_value", "varchar(400) NULL"},
				{"type_group", "varchar(45) NOT NULL"},
				{"attribute_group", "varchar(45) NOT NULL"},
				{"position", "int(11) NOT NULL"},
				{"extra", "PRIMARY KEY (`id_`), UNIQUE KEY `id_UNIQUE` (`id_` ASC)"
						+ ", UNIQUE INDEX `_at_name_UNIQUE` (`name` ASC) "
						+ ", INDEX `_at_type_group_idx` (`type_group` ASC)"
            + ", INDEX `_at_position_idx` (`type_group` ASC)"}
		};
		public static final String[][] _attribute_value_table_definition = {
				{"value_id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"attribute_type_id", "int(11) NOT NULL"},
				{"value_id", "int(11) NOT NULL"},
				{"type_position", "int(11) NOT NULL"},
				{"attribute_position", "int(11) NULL"},
				{"extra", "PRIMARY KEY (`attribute_value_id`), UNIQUE INDEX `_av_attribute_value_id_UNIQUE` (`attribute_value_id` ASC)"
         + ",index `_av_attribute_type_id_idx` (`_attribute_type_id` asc)"
         + ",INDEX `_av_attribute_position_idx` (`_attribute_position` ASC)"}
		};
      public static final String[][] _blob_value_table_definition = {
          {"_blob_id_", "int(11) NOT NULL AUTO_INCREMENT"},
          {"attribute_value_id", "int(11) NOT NULL"},
          {"pos_x", "int(11) NULL DEFAULT 0"},
          {"pos_y", "int(11) NULL DEFAULT 0"},
          {"pos_z", "int(11) NULL DEFAULT 0"},
          {"value", "MEDIUMBLOB NOT NULL"},
          {"extra", "PRIMARY KEY (`_blob_id_`), UNIQUE INDEX `_v_blob_id_UNIQUE` (`_blob_id_` ASC)"
           + ",index `_av_attribute_value_id_idx` (`attribute_value_id` asc)"}
      };
      public static final String[][] _complex_value_table_definition = {
          {"_complex_id_", "int(11) NOT NULL AUTO_INCREMENT"},
          {"attribute_value_id", "int(11) NOT NULL"},
          {"pos_x", "int(11) NULL DEFAULT 0"},
          {"pos_y", "int(11) NULL DEFAULT 0"},
          {"pos_z", "int(11) NULL DEFAULT 0"},
          {"value", "VARCHAR(400) NOT NULL"},
          {"extra", "PRIMARY KEY (`_complex_id_`), UNIQUE INDEX `_v_complex_id_UNIQUE` (`_complex_id_` ASC)"
           + ",index `_av_attribute_value_id_idx` (`attribute_value_id` asc)"}
      };
      public static final String[][] _text_value_table_definition = {
          {"_text_id_", "int(11) NOT NULL AUTO_INCREMENT"},
          {"attribute_value_id", "int(11) NOT NULL"},
          {"pos_x", "int(11) NULL DEFAULT 0"},
          {"pos_y", "int(11) NULL DEFAULT 0"},
          {"pos_z", "int(11) NULL DEFAULT 0"},
          {"value", "TEXT NOT NULL"},
          {"extra", "PRIMARY KEY (`_text_id_`), UNIQUE INDEX `_v_text_id_UNIQUE` (`_text_id_` ASC)"
           + ",index `_av_attribute_value_id_idx` (`attribute_value_id` asc)"}
      };
      public static final String[][] _boolean_value_table_definition = {
          {"_boolean_id_", "int(11) NOT NULL AUTO_INCREMENT"},
          {"attribute_value_id", "int(11) NOT NULL"},
          {"pos_x", "int(11) NULL DEFAULT 0"},
          {"pos_y", "int(11) NULL DEFAULT 0"},
          {"pos_z", "int(11) NULL DEFAULT 0"},
          {"value", "BOOLEAN NOT NULL"},
          {"extra", "PRIMARY KEY (`_boolean_id_`), UNIQUE INDEX `_v_boolean_id_UNIQUE` (`_boolean_id_` ASC)"
           + ",index `_av_attribute_value_id_idx` (`attribute_value_id` asc)"}
      };
      public static final String[][] _timestamp_value_table_definition = {
          {"_timestamp_id_", "int(11) NOT NULL AUTO_INCREMENT"},
          {"attribute_value_id", "int(11) NOT NULL"},
          {"pos_x", "int(11) NULL DEFAULT 0"},
          {"pos_y", "int(11) NULL DEFAULT 0"},
          {"pos_z", "int(11) NULL DEFAULT 0"},
          {"value", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
          {"extra", "PRIMARY KEY (`_timestamp_id_`), UNIQUE INDEX `_v_timestamp_id_UNIQUE` (`_timestamp_id_` ASC)"
           + ",index `_av_attribute_value_id_idx` (`attribute_value_id` asc)"}
      };
      public static final String[][] _string_value_table_definition = {
          {"_string_id_", "int(11) NOT NULL AUTO_INCREMENT"},
          {"attribute_value_id", "int(11) NOT NULL"},
          {"pos_x", "int(11) NULL DEFAULT 0"},
          {"pos_y", "int(11) NULL DEFAULT 0"},
          {"pos_z", "int(11) NULL DEFAULT 0"},
          {"value", "VARCHAR(100) NOT NULL"},
          {"extra", "PRIMARY KEY (`_string_id_`), UNIQUE INDEX `_v_string_id_UNIQUE` (`_string_id_` ASC)"
           + ",index `_av_attribute_value_id_idx` (`attribute_value_id` asc)"}
      };
      public static final String[][] _integer_value_table_definition = {
          {"_integer_id_", "int(11) NOT NULL AUTO_INCREMENT"},
          {"attribute_value_id", "int(11) NOT NULL"},
          {"pos_x", "int(11) NULL DEFAULT 0"},
          {"pos_y", "int(11) NULL DEFAULT 0"},
          {"pos_z", "int(11) NULL DEFAULT 0"},
          {"value", "int(11) NOT NULL"},
          {"extra", "PRIMARY KEY (`_integer_id_`), UNIQUE INDEX `_v_integer_id_UNIQUE` (`_integer_id_` ASC)"
           + ",index `_av_attribute_value_id_idx` (`attribute_value_id` asc)"}
      };
	}
	public static class Support {
		
		public static final int _INSTANCE_INDEX = 0;
		public static final int _INSTANCE_VERSION_INDEX = 1;
		public static final int _INSTANCE_GROUP_INDEX = 2;
	
		public static final String[] support_tables = {
				"_instance"//1
				,"_instance_version"//2
				,"_instance_group"//3
		};
		
		public static final String[][] _instance_table_definition = {
				{"_object_id", "INT(11) NOT NULL"},
				{"_object_business_id", "VARCHAR(45) NOT NULL"},
				{"_instance_id", "varchar(45) NOT NULL"},
				{"_current_instance_id", "varchar(45) NOT NULL"},
				{"_initial_instance_id", "varchar(45) NOT NULL"},
				{"_status", "varchar(45) NOT NULL"},
				{"_timestamp", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"_version_id", "INT(11) NOT NULL DEFAULT 0"},
				{"_group_id", "INT(11) NOT NULL DEFAULT 0"},				
				{"_end_of_life", "timestamp NOT NULL DEFAULT DATE_ADD(CURRENT_TIMESTAMP,INTERVAL 10 YEAR)"},
				{"extra", "UNIQUE KEY `_i_instance_id_UNIQUE` (`_instance_id`), "
					+ "INDEX `_i_object_business_id_idx` (`_object_business_id` ASC),"
					+ "INDEX `_i_instance_id_idx` (`_instance_id` ASC)"}
		};
	
		public static final String[][] _instance_version_table_definition = {
				{"_instance_version_id", "INT(11) NOT NULL AUTO_INCREMENT"},
				{"_version_value", "VARCHAR(45) NOT NULL"},
				{"extra", "PRIMARY KEY (`_instance_version_id`)"}
		};
	
		public static final String[][] _instance_group_table_definition = {
				{"_instance_group_id", "INT(11) NOT NULL AUTO_INCREMENT"},
				{"_group_name", "VARCHAR(100) NOT NULL"},
				{"extra", "PRIMARY KEY (`_instance_group_id`)"}
		};
	
	
	}

	public static class Customer {
		public static final int _CUSTOMER_TABLE_INDEX = 0;
		public static final int _CUSTOMER_UPDATES_TABLE_INDEX = 1;
		
		public static final String[] customer_management_tables = {
				"customer",
				"customer_updates"
		};
		
		public static final String[][] customer_table_definition = {
				{"id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"businesskey", "varchar(45) NOT NULL"},
				{"customer_id", "varchar(45) NOT NULL"},
				{"customer_name", "varchar(45) NOT NULL"},
				{"extra", "PRIMARY KEY (`customer_id`),"
						+ "UNIQUE KEY `id_UNIQUE` (`id_`),"
						+ "UNIQUE KEY `customer_key_UNIQUE` (`customer_id`),"
						+ "UNIQUE KEY `customer_businesskey_UNIQUE` (`customer_businesskey`)"}
		};

		public static final String[][] customer_updates_table_definition = {
				{"customer_id", "VARCHAR(45) NOT NULL"},
				{"businesskey", "VARCHAR(45) NOT NULL"},
				{"target", "varchar(45) NOT NULL"},
				{"extra", "INDEX `c_updates_customer_id_idx` (`customer_id` ASC)"}
		};
	}
	
	public static class Order {
		public static final int _ORDER_TABLE_INDEX = 0;
		public static final int _ORDER_POSITION_TABLE_INDEX = 1;
		public static final int _ORDER_HAS_ORDER_PROCESS_TABLE_INDEX = 2;
		public static final int _ORDER_HAS_PROCESS_TABLE_INDEX = 3;
		public static final int _ORDER_UPDATES_TABLE_INDEX = 4;
		public static final int _ORDER_POSITION_UPDATES_TABLE_INDEX = 5;
		public static final int _ORDER_PRODUCT_ATTRIBUTE_UPDATES_TABLE_INDEX = 6;
		public static final int _ORDER_HAS_PRODUCTION_PROCESS_TABLE_INDEX = 7;
		public static final int _ORDER_HAS_PRODUCTION_ORDER_TABLE_INDEX = 8;
		public static final int _ORDER_POSITION_HAS_PRODUCT_TABLE_INDEX = 9;
		public static final int _PRODUCTION_ORDER_TABLE_INDEX = 10;
		public static final int _PRODUCTION_ORDER_POSITION_TABLE_INDEX = 11;
		public static final int _PRODUCT_ATTRIBUTE_VALUES_TABLE_INDEX = 12;
		public static final int _ORDER_PRODUCT_TABLE_INDEX = 13;
		public static final int _ORDER_HISTORY_TABLE_INDEX = 14;
		
		public static final String[] order_management_tables = {
				"order",   //0
				"order_position",//1
				"order_has_order_process",//2
				"order_has_process",//3
				"order_updates",//4
				"order_position_updates",//5
				"order_product_attribute_updates",//6
				"order_has_production_process", //7 TODO: may be unnecessary
				"order_has_production_order",   //8 TODO: may be unnecessary
				"order_position_has_product",   //9 TODO: may be unnecessary
				"production_order",//10
				"production_order_position",//11
				"product_attribute_values",//12
				"order_product",//13
				"order_history"//14
		};
		public static final String[][] order_table_definition = {
				{"id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"order_number", "varchar(45) NOT NULL"},
				{"businesskey", "varchar(45) NOT NULL"},
				{"instance_id", "varchar(45) NOT NULL"},
				{"current_instance_id", "varchar(45) NOT NULL"},
				{"initial_instance_id", "varchar(45) NOT NULL"},
				{"status", "varchar(45) NOT NULL"},
				{"timestamp", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"date", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"extra", "PRIMARY KEY (`id_`),"
						+ "UNIQUE KEY `id_UNIQUE` (`id_`),"
						+ "UNIQUE KEY `instance_id_UNIQUE` (`instance_id`),"
						+ "UNIQUE KEY `order_number_UNIQUE` (`order_number`),"
						+ "KEY `fk_o_order_customer1_idx` (`businesskey`)"}
		};
		public static final String[][] order_position_table_definition = {
				{"opos_id_",  "int(11) NOT NULL AUTO_INCREMENT"},
				{"opos_business_id", "varchar(15) NOT NULL DEFAULT '0000'"},
				{"opos_order_business_id", "varchar(45) NOT NULL"},
				{"opos_position",  "int(11) NOT NULL DEFAULT 0"},
				{"opos_quantity",  "int(11) NOT NULL DEFAULT 0"},
				//			{"product_id",  "int(11) NOT NULL DEFAULT 0"},
				{"opos_instance_id", "varchar(45) NOT NULL"},
				{"opos_current_instance_id", "varchar(45) NOT NULL"},
				{"opos_initial_instance_id", "varchar(45) NOT NULL"},
				{"opos_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"opos_status", "varchar(45) NOT NULL"},
				{"extra", "PRIMARY KEY (`opos_id_`), UNIQUE INDEX `id_UNIQUE` (`opos_id_` ASC),"
						+ "INDEX `fk_order_number_idx` (`opos_order_business_id` ASC),"
						//					+ "INDEX `fk_product_id_idx` (`opos_product_id` ASC),"
						+ "INDEX `fk_order_position_order_position1_idx` (`opos_instance_id` ASC),"
						+ "INDEX `fk_order_position_order_position2_idx` (`opos_current_instance_id` ASC),"
						+ "INDEX `fk_order_position_order_position3_idx` (`opos_initial_instance_id` ASC),"
						+ "UNIQUE INDEX `instance_id_UNIQUE` (`opos_instance_id` ASC)"
						//					+ ", "
						//					+ "CONSTRAINT `fk_order_position_order_number1` "
						//					+ "FOREIGN KEY (`order_number`) REFERENCES `order_management`.`order` (`order_number`) "
						//					+ "ON DELETE NO ACTION ON UPDATE NO ACTION"
				}
		};
		public static final String[][] order_has_order_process_table_definition = {
				{"order_number", "varchar(10) NOT NULL"},
				{"process_instance_id", "varchar(45) NOT NULL"},
				{"process_key", "varchar(45) NOT NULL"},
				{"extra", "INDEX `ohopr_order_number_idx` (`order_number` ASC),"
						+ "KEY `fk_order_has_order_process_order_process1_instance_idx` (`process_instance_id`),"
						+ "KEY `fk_order_has_order_process_order1_idx` (`order_number`)"}
		};
		public static final String[][] order_has_process_table_definition = {
				{"order_number", "varchar(10) NOT NULL"},
				{"process_instance_id", "varchar(45) NOT NULL"},
				{"process_key", "varchar(45) NOT NULL"},
				{"extra", "INDEX `ohpr_order_number_idx` (`order_number` ASC),"
						+ "KEY `fk_order_has_order_process_order_process2_instance_idx` (`process_instance_id`),"
						+ "KEY `fk_order_has_order_process_order2_idx` (`order_number`)"}
		};
		public static final String[][] order_updates_table_definition = {
				{"businesskey", "varchar(45) NOT NULL"},
				{"order_number", "varchar(10) NOT NULL"},
				{"target", "varchar(45) NOT NULL"},
				{"timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"extra", "INDEX `o_updates_order_number_idx` (`order_number` ASC)"
						+",INDEX `o_updates_target_idx` (`target` ASC)"
						+",INDEX `o_updates_businesskey_idx` (`businesskey` ASC)"}
		};
		public static final String[][] order_position_updates_table_definition = {
				{"oposu_order_position_number", "varchar("+CampFormats._ORDER_POSITION_NUMBER_SIZE+") NOT NULL"},
				{"oposu_order_number", "varchar("+CampFormats._ORDER_NUMBER_SIZE+") NOT NULL"},
				{"oposu_businesskey", "varchar(45) NOT NULL"},
				{"oposu_target", "varchar(45) NOT NULL"},
				{"extra", "INDEX `opos_oposu_updates_order_position_number_idx` (`oposu_order_number` ASC)"
						+",INDEX `opos_oposu_updates_order_number_idx` (`oposu_order_number` ASC)"
						+",INDEX `opos_oposu_updates_businesskey_idx` (`oposu_businesskey` ASC)"
						+",INDEX `opos_oposu_updates_target_idx` (`oposu_target` ASC)"}
		};
		public static final String[][] order_product_attribute_updates_table_definition = {
				{"attribute_name", "varchar(100) NOT NULL"},
				{"order_number", "varchar(10) NOT NULL"},
				{"order_position_id", "int(11) NOT NULL"},
				{"product_id", "int(11) NOT NULL"},
				{"product_group", "varchar(45) NOT NULL"},
				{"model_id", "int(11) NOT NULL"},
				{"target", "varchar(45) NOT NULL"},
				{"extra", "INDEX `opa_updates_order_number_idx` (`order_number` ASC)"}
		};
		public static final String[][] order_has_production_process_table_definition = {
				{"order_number", "varchar(10) NOT NULL"},
				{"process_instance_id", "varchar(45) NOT NULL"},
				{"process_key", "varchar(45) NOT NULL"},
				{"extra", "INDEX `ohprdpr_order_number_idx` (`order_number` ASC),"
						+ "KEY `fk_order_has_order_process_order_process1_idx` (`process_instance_id`),"
						+ "KEY `fk_order_has_order_process_order1_idx` (`order_number`)"}
		};
		public static final String[][] order_has_production_order_table_definition = {
				{"order_id",  "int(11) NOT NULL"},
				{"production_order_id",  "int(11) NOT NULL"},
				{"extra", "INDEX `ohprdo_updates_order_id_idx` (`order_id` ASC),"
						+ "KEY `fk_order_has_production_order_production_order1_idx` (`production_order_id`),"
						+ "KEY `fk_order_has_production_order_order1_idx` (`order_id`)"
						//					+ ","
						//					+ "CONSTRAINT `fk_order1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,"
						//					+ "CONSTRAINT `fk_production_order1` FOREIGN KEY (`production_order_id`) REFERENCES `production_order` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION"
				}
		};
		public static final String[][] order_position_has_product_table_definition = {
				{"order_position_id","int(11) NOT NULL"},
				{"order_product_id", "int(11) NOT NULL"},
				//			{"model_id", "int(11) NOT NULL"},
				{"order_number", "varchar("+CampFormats._ORDER_NUMBER_SIZE+") NOT NULL"},
				{"extra", "INDEX `fk_om_ophp_product1_namex` (`order_product_id` ASC),"
						+ "INDEX `fk_om_ophp_order_position1_idx` (`order_position_id` ASC),"
						+ "INDEX `fk_om_ophp_order_number1_idx` (`order_number` ASC),"
						+ "UNIQUE INDEX `order_position_id_UNIQUE` (`order_position_id`),"
						+ "UNIQUE INDEX `order_product_id_UNIQUE` (`order_product_id`)"
						//					+ ","
						//					+ "CONSTRAINT `fk_om_ophp_order_position_id_c` FOREIGN KEY (`order_position_id`) "
						//					+ "REFERENCES `order_management`.`order_position` (`order_position_id`) "
						//					+ "ON DELETE NO ACTION ON UPDATE NO ACTION, "
						//					+ "CONSTRAINT `fk_om_ophp_product_id_c` FOREIGN KEY (`product_id`) "
						//					+ "REFERENCES `product_management`.`product` (`product_id`) "
						//					+ "ON DELETE NO ACTION ON UPDATE NO ACTION"
				}
		};
		public static final String[][] production_order_table_definition = {
				{"pto_id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"pto_order_number", "varchar(45) NOT NULL"},
				{"pto_businesskey", "varchar(45) NOT NULL"},
				{"pto_instance_id", "varchar(45) NOT NULL"},
				{"pto_current_instance_id", "varchar(45) NOT NULL"},
				{"pto_initial_instance_id", "varchar(45) NOT NULL"},
				{"pto_status", "varchar(45) NOT NULL"},
				{"pto_timestamp", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"pto_date", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"extra", "PRIMARY KEY (`pto_id_`),"
						+ "UNIQUE KEY `pto_id_UNIQUE` (`pto_id_`),"
						+ "UNIQUE KEY `pto_instance_id_UNIQUE` (`pto_instance_id`),"
						+ "UNIQUE KEY `pto_order_number_UNIQUE` (`pto_order_number`),"
						+ "KEY `fk_pto_order_customer1_idx` (`pto_businesskey`)"}
		};
		public static final String[][] production_order_position_table_definition = {
				{"ptop_id_",  "int(11) NOT NULL AUTO_INCREMENT"},
				{"ptop_number", "varchar(15) NOT NULL DEFAULT '0000'"},
				{"ptop_order_number", "varchar(45) NOT NULL"},
				{"ptop_position",  "int(11) NOT NULL DEFAULT 0"},
				{"ptop_product_id",  "int(11) NOT NULL"},
				{"ptop_instance_id", "varchar(45) NOT NULL"},
				{"ptop_current_instance_id", "varchar(45) NOT NULL"},
				{"ptop_initial_instance_id", "varchar(45) NOT NULL"},
				{"ptop_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"ptop_status", "varchar(45) NOT NULL"},
				{"extra", "PRIMARY KEY (`ptop_id_`), UNIQUE INDEX `ptop_id_UNIQUE` (`ptop_id_` ASC),"
						+ "INDEX `fk_ptop_order_number_idx` (`ptop_order_number` ASC),"
						+ "INDEX `fk_ptop_product_id_idx` (`ptop_product_id` ASC),"
						+ "INDEX `fk_order_position_order_position1_idx` (`ptop_instance_id` ASC),"
						+ "INDEX `fk_order_position_order_position2_idx` (`ptop_current_instance_id` ASC),"
						+ "INDEX `fk_order_position_order_position3_idx` (`ptop_initial_instance_id` ASC),"
						+ "UNIQUE INDEX `ptop_instance_id_UNIQUE` (`ptop_instance_id` ASC)"
						//					+ ", "
						//					+ "CONSTRAINT `fk_order_position_order_number1` "
						//					+ "FOREIGN KEY (`order_number`) REFERENCES `order_management`.`order` (`order_number`) "
						//					+ "ON DELETE NO ACTION ON UPDATE NO ACTION"
				}
		};
		public static final String[][] order_product_table_definition = {
				{"op_id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"op_product_id", "int(11) NOT NULL"},
				{"op_product_name", "varchar(45) NOT NULL"},
				{"op_model_id", "int(11) NOT NULL"},
				{"op_order_position_number", "varchar(45) NULL"},
				{"op_order_business_id", "varchar(45) NOT NULL"},
				{"op_date", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"op_timestamp", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"extra", "PRIMARY KEY (`op_id_`), UNIQUE INDEX `op_id__UNIQUE` (`op_id_` ASC),"
						+ "INDEX `ordprd_op_product_name_idx` (`op_product_name` ASC),"
						+ "INDEX `ordprd_order_position_number_idx` (`op_order_position_number` ASC),"
						+ "INDEX `ordprd_op_order_numer_idx` (`op_order_business_id` ASC)"}
		};
		public static final String[][] order_history_table_definition = {
				{"object_id", "INT(11) NOT NULL"},
				{"_business_id", "VARCHAR(45) NOT NULL"},
				{"_instance_id", "varchar(45) NOT NULL"},
				{"_current_instance_id", "varchar(45) NOT NULL"},
				{"_initial_instance_id", "varchar(45) NOT NULL"},
				{"_status", "varchar(45) NOT NULL"},
				{"_timestamp", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"extra", "UNIQUE KEY `o_history_instance_id_UNIQUE` (`_instance_id`), "
					+ "INDEX `o_history_business_id_idx` (`_business_id` ASC),"
					+ "INDEX `o_history_instance_id_idx` (`_instance_id` ASC)"}
		};
	}
	
	public static class Process {
		public static final int _PROCESS_TABLE_INDEX = 0;
		public static final int _PRODUCTION_PROCESS_TABLE_INDEX = 1;
		public static final int _ORDER_PROCESS_TABLE_INDEX = 2;
		public static final int _PROCESS_UPDATES_TABLE_INDEX = 3;
		public static final int _ORDER_PROCESS_UPDATES_TABLE_INDEX = 4;
		public static final int _PRODUCT_PROCESS_TABLE_INDEX = 5;
		public static final int _PRODUCT_PROCESS_UPDATES_TABLE_INDEX = 6;
		public static final int _PRODUCTION_PROCESS_UPDATES_TABLE_INDEX = 7;
		
		public static final String[] process_management_tables = {
				"process",				//0
				"production_process", 	//1 TODO: is unnecessary
				"order_process",		//2
				"process_updates",		//3
				"order_process_updates",//4
				"product_process",		//5
				"product_process_updates",//6
				"production_process_updates"//7
		};
		public static final String[][] process_table_definition = {
				{"id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"instance_id", "varchar(45) NOT NULL"},
				{"business_key", "varchar(45) NOT NULL"},
				{"process_name","VARCHAR(45) NOT NULL"},
				{"definition_id", "varchar(45) NOT NULL"},
				{"tenant_id", "varchar(45) DEFAULT NULL"},
				{"case_instance_id", "varchar(45) DEFAULT NULL"},
				{"ended", "tinyint(1) NOT NULL DEFAULT '1'"},
				{"suspended", "tinyint(1) NOT NULL DEFAULT '1'"},
				{"extra", "PRIMARY KEY (`id_`), "
						+ "UNIQUE KEY `instance_id_UNIQUE` (`instance_id`)"},
		};
		public static final String[][] production_process_table_definition = {
				{"id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"instance_id", "varchar(45) NOT NULL"},
				{"business_key", "varchar(45) NOT NULL"},
				{"process_name","VARCHAR(45) NOT NULL"},
				{"definition_id", "varchar(45) NOT NULL"},
				{"tenant_id", "varchar(45) DEFAULT NULL"},
				{"case_instance_id", "varchar(45) DEFAULT NULL"},
				{"ended", "tinyint(1) NOT NULL DEFAULT 0"},
				{"suspended", "tinyint(1) NOT NULL DEFAULT 0"},
				{"extra", "PRIMARY KEY (`id_`), "
						+ "UNIQUE KEY `instance_id_UNIQUE` (`instance_id`)"},
		};
		public static final String[][] order_process_table_definition = {
				{"id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"instance_id", "varchar(45) NOT NULL"},
				{"business_key", "varchar(45) NOT NULL"},
				{"process_name","VARCHAR(45) NOT NULL"},
				{"definition_id", "varchar(45) NOT NULL"},
				{"tenant_id", "varchar(45) DEFAULT NULL"},
				{"case_instance_id", "varchar(45) DEFAULT NULL"},
				{"ended", "tinyint(1) NOT NULL DEFAULT 0"},
				{"suspended", "tinyint(1) NOT NULL DEFAULT 0"},
				{"extra", "PRIMARY KEY (`id_`), "
						+ "UNIQUE KEY `instance_id_UNIQUE` (`instance_id`)"},
		};
		public static final String[][] process_updates_table_definition = {
				{"instance_id", "VARCHAR(45) NOT NULL"},
				{"businesskey", "VARCHAR(45) NOT NULL"},
				{"target", "varchar(45) NOT NULL"},
				{"extra", "INDEX `pr_updates_instance_id_idx` (`instance_id` ASC)"}
		};
		public static final String[][] order_process_updates_table_definition = {
				{"instance_id", "VARCHAR(45) NOT NULL"},
				{"order_number", "VARCHAR(45) NOT NULL"},
				{"businesskey", "VARCHAR(45) NOT NULL"},
				{"target", "varchar(45) NOT NULL"},
				{"extra", "INDEX `opr_updates_order_number_idx` (`order_number` ASC)"}
		};
		public static final String[][] product_process_table_definition = {
				{"id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"instance_id", "varchar(45) NOT NULL"},
				{"business_key", "varchar(45) NOT NULL"},
				{"process_name","VARCHAR(45) NOT NULL"},
				{"definition_id", "varchar(45) NOT NULL"},
				{"tenant_id", "varchar(45) DEFAULT NULL"},
				{"case_instance_id", "varchar(45) DEFAULT NULL"},
				{"ended", "tinyint(1) NOT NULL DEFAULT 0"},
				{"suspended", "tinyint(1) NOT NULL DEFAULT 0"},
				{"extra", "PRIMARY KEY (`id_`), "
						+ "UNIQUE KEY `instance_id_UNIQUE` (`instance_id`)"},
		};
		public static final String[][] product_process_updates_table_definition = {
				{"instance_id", "VARCHAR(45) NOT NULL"},
				{"product_id", "VARCHAR(45) NOT NULL"},
				{"businesskey", "VARCHAR(45) NOT NULL"},
				{"target", "varchar(45) NOT NULL"},
				{"extra", "INDEX `ppr_updates_product_id_idx` (`product_id` ASC)"}
		};
		public static final String[][] production_process_updates_table_definition = {
				{"instance_id", "VARCHAR(45) NOT NULL"},
				{"order_number", "VARCHAR(45) NOT NULL"},
				{"businesskey", "VARCHAR(45) NOT NULL"},
				{"target", "varchar(45) NOT NULL"},
				{"extra", "INDEX `prdpr_updates_order_number_idx` (`order_number` ASC)"}
		};
	}	
	
	public static class Product {
		public static final int _PRODUCT_TABLE_INDEX = 0;
		public static final int _PRODUCT_HAS_MODEL_TABLE_INDEX = 1;
		public static final int _PRODUCT_UPDATES_TABLE_INDEX = 2;
		public static final int _PRODUCT_MODEL_TABLE_INDEX = 3;
		public static final int _PRODUCT_ATTRIBUTE_TABLE_INDEX = 4;
		public static final int _PRODUCT_ATTRIBUTE_HAS_PRODUCT_TABLE_INDEX = 5;
		public static final int _PRODUCT_ATTRIBUTE_UPDATES_TABLE_INDEX = 6;
		public static final int _GROUP_TABLE_INDEX = 7;
		public static final int _GROUP_HAS_PRODUCT_TABLE_INDEX = 8;
		public static final int _GROUP_HAS_PRODUCT_ATTRIBUTE_TABLE_INDEX = 9;
		public static final int _VERSION_TABLE_INDEX = 10;
		public static final int _VERSION_HAS_PRODUCT_TABLE_INDEX = 11;
		public static final int _VERSION_HAS_PRODUCT_ATTRIBUTE_TABLE_INDEX = 12;
		public static final int _PRODUCT_HAS_PROCESS_TABLE_INDEX = 13;
		public static final int _ORDER_PRODUCT_UPDATES_TABLE_INDEX = 14;
		public static final int _PRODUCT_ATTRIBUTE_TYPE_TABLE_INDEX = 15;
		public static final int _PRODUCT_ATTRIBUTE_HAS_TYPE_TABLE_INDEX = 16;
		public static final int _PRODUCT_HISTORY_TABLE_INDEX = 17;
	
		public static final String[] product_management_tables = {
				"product",					//0
				"product_has_model",		//1
				"product_updates",			//2
				"product_model",			//3
				"product_attribute",		//4
				"product_attribute_has_product",//5
				"product_attribute_updates",//6
				"group",					//7
				"group_has_product",		//8
				"group_has_product_attribute",//9
				"version",//10
				"version_has_product",//11
				"version_has_product_attribute",//12
				"product_has_process",//13
				"order_product_updates",//14
				"product_attribute_type",//15
				"product_attribute_has_type",//16
				"product_history"//17
		};
		public static final String[][] product_table_definition = {
				{"id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"name", "varchar(45) NOT NULL"},
				{"group", "varchar(45) NOT NULL"},
				{"date", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"version", "varchar(45) NOT NULL"},
				{"status", "varchar(45) NOT NULL"},
				//			{"order_number", "varchar(10) NULL"},
				//			{"order_position_id", "int(11) NULL"},
				{"extra", "PRIMARY KEY (`id_`),UNIQUE KEY `id_UNIQUE` (`id_` ASC),"
					//					+ "UNIQUE KEY `name_UNIQUE` (`name` ASC),"
					+ "INDEX `fk_product_product1_idx` (`instance_id` ASC),"
					+ "INDEX `fk_product_product2_idx` (`initial_instance_id` ASC),"
					+ "INDEX `fk_product_product3_idx` (`current_instance_id` ASC),"
					+ "UNIQUE INDEX `instance_id_UNIQUE` (`instance_id` ASC)"}
		};
		public static final String[][] product_has_model_table_definition = {
				{"product_name", "varchar(45) NOT NULL"},
				{"model_id", "int(11) NOT NULL"},
				{"extra","INDEX `phm_product_name_idx` (`product_name` ASC)"}
		};
		public static final String[][] product_updates_table_definition = {
				{"product_name", "varchar(45) NOT NULL"},
				{"businesskey", "varchar(45) NOT NULL"},
				{"target", "varchar(45) NOT NULL"},
				{"extra", "INDEX `p_updates_product_name_idx` (`product_name` ASC)"}
		};
		public static final String[][] product_model_table_definition = {
				{"id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"name", "varchar(45) NOT NULL"},
				{"release_date", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"end_of_life", "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"},
				{"version", "varchar(45) NOT NULL"},
				{"status", "varchar(45) NOT NULL"},
				{"extra", "PRIMARY KEY (`id_`)"}
		};
		//to hell with both of you for this. seriously
		public static final String[][] product_attribute_table_definition = {
				{"id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"name", "varchar(45) NOT NULL"},
				{"type", "varchar(45) NOT NULL"},
				{"value", "varchar(500) NOT NULL"},
				{"position", "int(11)  NULL DEFAULT NULL"},
				{"group", "varchar(45) NOT NULL"},
				{"product_group", "varchar(45) NOT NULL"},
				{"version", "varchar(45) NOT NULL"},
				{"parent_id", "int(11) NULL DEFAULT 0"},
				{"product_id", "int(11) NOT NULL"},
				{"product_name", "varchar(45) NOT NULL"},
				{"table_id", "int(11) NULL DEFAULT NULL"},
				{"extra", "PRIMARY KEY (`id_`), UNIQUE INDEX `id_UNIQUE` (`id_` ASC)"
						+ ", INDEX `fk_product_attribute_product_attribute1_idx` (`parent_id` ASC)"
						+ ", INDEX `fk_product_attribute_product1_idx` (`product_id` ASC)"
						//					+ ", CONSTRAINT `fk_product_attribute_product1` FOREIGN KEY (`product_id`) REFERENCES `product_management`.`product` (`id_`) "
						//				    + " ON DELETE NO ACTION ON UPDATE NO ACTION"
						//					+ ", CONSTRAINT `fk_product_attribute_product_attribute1` FOREIGN KEY (`parent_id`) REFERENCES `product_management`.`product_attribute` (`id_`) "
						//				    + " ON DELETE NO ACTION ON UPDATE NO ACTION"
				}
		};
		public static final String[][] product_attribute_has_product_table_definition = {
				{"product_attribute_id", "int(11) NOT NULL"},
				{"product_id", "int(11) NOT NULL"},
				{"product_name", "varchar(45) NOT NULL"},
				{"model_id", "int(11) NOT NULL"},
				{"product_group", "varchar(45) NOT NULL"},
				{"extra", "INDEX `pahp_product_id_idx` (`product_id` ASC)"
						+",INDEX `pahp_product_name_idx` (`product_name` ASC)"}
		};
		public static final String[][] product_attribute_updates_table_definition = {
				{"product_attribute_id", "int(11) NOT NULL"},
				{"product_id", "int(11) NOT NULL"},
				{"model_id", "int(11) NOT NULL"},
				{"product_group", "varchar(45) NOT NULL"},
				{"target", "varchar(100) NOT NULL"},
				{"extra", "INDEX `pa_updates_product_id_idx` (`product_id` ASC)"}
		};
		public static final String[][] group_table_definition = {
				{"id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"name", "varchar(45) NOT NULL"},
				{"extra", "PRIMARY KEY (`id_`)"}
		};
		public static final String[][] group_has_product_table_definition = {
				{"group_id", "int(11) NOT NULL"},
				{"product_id", "int(11) NOT NULL"},
				{"extra", "INDEX `ghp_group_id_idx` (`group_id` ASC)"}
		};
		public static final String[][] group_has_product_attribute_table_definition = {
				{"group_id", "int(11) NOT NULL"},
				{"product_attribute_id", "int(11) NOT NULL"},
				{"extra", "INDEX `ghpa_group_id_idx` (`group_id` ASC)"}
		};
		public static final String[][] version_table_definition = {
				{"id_", "int(11) NOT NULL AUTO_INCREMENT"},
				{"value", "varchar(45) NOT NULL"},
				{"extra", "PRIMARY KEY (`id_`)"}
		};
		public static final String[][] version_has_product_table_definition = {
				{"version_id", "int(11) NOT NULL"},
				{"product_id", "int(11) NOT NULL"},
				{"extra", "INDEX `vhprd_version_id_idx` (`version_id` ASC)"}
		};
		public static final String[][] version_has_product_attribute_table_definition = {
				{"version_id", "int(11) NOT NULL"},
				{"product_attribute_id", "int(11) NOT NULL"},
				{"extra", "INDEX `vhpa_version_id_idx` (`version_id` ASC)"}
		};
		public static final String[][] product_has_process_table_definition = {
				{"product_name", "varchar(10) NOT NULL"},
				{"process_instance_id", "varchar(45) NOT NULL"},
				{"process_name", "varchar(45) NOT NULL"},
				{"extra", "INDEX `phpr_product_name_idx` (`product_name` ASC),"
						+ "KEY `fk_order_has_order_process_order_process2_instance_idx` (`process_instance_id`),"
						+ "KEY `fk_order_has_order_process_order2_idx` (`product_name`)"}
		};
		public static final String[][] order_product_updates_table_definition = {
				{"op_product_id", "int(11) NOT NULL"},
				{"op_product_name", "varchar(45) NOT NULL"},
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
		public static final String[][] product_attribute_has_type_table_definition = {
				{"product_attribute_id", "int(11) NOT NULL"},
				{"product_attribute_value_id", "int(11) NOT NULL"},
				{"product_attribute_type_id", "int(11) NOT NULL"},
				{"extra", "INDEX `pahp_product_attribute_id_idx` (`product_attribute_id` ASC)"}
		};
	}	
	public static String oUpdateInstancePSQL = "UPDATE %s SET current_instance_id=? WHERE order_number LIKE ?";
	public static String oUpdateInstanceSQL = "UPDATE %s SET current_instance_id='%s' WHERE order_number LIKE '%s%%'";
	public static String opUpdateInstancePSQL = "UPDATE %s SET current_instance_id=? WHERE number LIKE ? AND order_number LIKE ?";
	public static String opUpdateInstanceSQL = "UPDATE %s SET current_instance_id='%s' WHERE number='%s' AND order_number LIKE '%s%%'";

	public static final String getValueDefinition(AttributeType attributeType, EnumMap<AttributeType,String> tableDefinition){
		return  tableDefinition.get(attributeType);
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
	public static final String supTable(int dbIndex, int tableIndex){
		return  " `"+database[dbIndex]+"`.`"+Support.support_tables[tableIndex]+"` ";
	}
	public static final String sysTable(int dbIndex, int tableIndex){
		return  " `"+database[dbIndex]+"`.`"+System.system_tables[tableIndex]+"` ";
	}


}
