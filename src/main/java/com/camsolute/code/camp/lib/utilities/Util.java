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
package com.camsolute.code.camp.lib.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.naming.InitialContext;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.ParseException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

//import org.apache.commons.dbcp2.BasicDataSource;
//import org.apache.commons.dbcp2.ConnectionFactory;
//import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
//import org.apache.commons.dbcp2.PoolableConnection;
//import org.apache.commons.dbcp2.PoolableConnectionFactory;
//import org.apache.commons.dbcp2.PoolingDriver;
//import org.apache.commons.pool2.ObjectPool;
//import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import com.camsolute.code.camp.lib.data.CampSQL;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;
import com.camsolute.code.camp.lib.types.CampEnum;
import com.camsolute.code.camp.lib.types.CampSet;

public class Util {

	private static final Logger LOG = LogManager.getLogger(Util.class);
	private static String fmt = "[%15s] [%s]";
	
    public static final int NEW_ID = 0;
    
    public static final String DEFAULT_GLOBAL_SYSTEM_TARGET = "com.camsolute.code.camp.global.system.target";

	public static boolean _IN_PRODUCTION =  Boolean.getBoolean(Util.Config.instance().properties().getProperty("system.in.production"));

	public static class Math {
		public static int addArray(int[] values) {
			int retval = 0;
			for(int i:values) {
				retval += i;
			}
			return retval;
		}
	}
    public static class Text {
    	public static final String LOG_FORMAT = "[%15s] [%s]";
    	
  		public static String joinInt(Set<Integer> ar,String sep){
  			String joined = "";
  			boolean start = true;
  			for(int i:ar){
  				if(!start) {
  					joined += sep;
  				} else {
  					start = false;
  				}
  				joined += i;
  			}
  			return joined;
  		}

  		public static String joinInt(int[] ar,String sep){
  			String joined = "";
  			boolean start = true;
  			for(int i:ar){
  				if(!start) {
  					joined += sep;
  				} else {
  					start = false;
  				}
  				joined += i;
  			}
  			return joined;
  		}

		public static String join(Set<String> ar,String sep){
			String joined = "";
			boolean start = true;
			for(String s:ar){
				if(!start) {
					joined += sep;
				} else {
					start = false;
				}
				joined += s;
			}
			return joined;
		}

		public static String join(String[] ar,String sep){
			String joined = "";
			boolean start = true;
			for(String s:ar){
				if(!start) {
					joined += sep;
				} else {
					start = false;
				}
				joined += s;
			}
			return joined;
		}

		public static String tag(AttributeType type){
			return Attribute.attributeMatrix.get(type)[1];
		}

	}

    public static class Time {

    	public static String formatMilli = "yyyy-MM-dd HH:mm:ss.SSS";
    	public static String formatDateTime = "yyyy-MM-dd HH:mm:ss";
    	public static String formatDate = "yyyy-MM-dd";
    	public static String formatTime = "HH:mm:ss";
    	
        public static SimpleDateFormat[] FORMATS = {
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy-MM-dd"),
    			new SimpleDateFormat("dd/MM/yyyy"),
    			new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"),
    			new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS"),
    			new SimpleDateFormat("dd.MM.yyyy"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSz"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy.MM.dd"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
       			new SimpleDateFormat("HH:mm:ss.SSSZ"),
       			new SimpleDateFormat("HH:mm:ss.SSSz"),
       			new SimpleDateFormat("HH:mm:ss.SSS"),
       			new SimpleDateFormat("HH:mm:ss"),
       			new SimpleDateFormat("HH:mm")
			};

	public final static DateTime dateTimeFromString(String datetime){
		DateTime value = null;
        SimpleDateFormat[] FORMATS = {
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy-MM-dd"),
    			new SimpleDateFormat("dd/MM/yyyy"),
    			new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"),
    			new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS"),
    			new SimpleDateFormat("dd.MM.yyyy"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSz"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy.MM.dd"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
       			new SimpleDateFormat("HH:mm:ss.SSSZ"),
       			new SimpleDateFormat("HH:mm:ss.SSSz"),
       			new SimpleDateFormat("HH:mm:ss.SSS"),
       			new SimpleDateFormat("HH:mm:ss"),
       			new SimpleDateFormat("HH:mm")
			};
		for(SimpleDateFormat sdf:FORMATS){
			try{
				sdf.setLenient(false);
				value = new DateTime(sdf.parse(datetime));
    		return value;
			} catch (ParseException e){
				value = null;
				continue;
			}
		}
		return value;
	}

	public final static Timestamp timestampFromString(String datetime){
		if(!Util._IN_PRODUCTION){String msg = "----[TIMESTAMPFROMSTRING("+datetime+")]----";LOG.info(String.format(fmt, "timestampFromString",msg));}
		Timestamp value = null;
        SimpleDateFormat[] FORMATS = {
    			new SimpleDateFormat("yyyy-MM-dd kk:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy-MM-dd"),
    			new SimpleDateFormat("yyyy/MM/dd"),
    			new SimpleDateFormat("yyyy/MM/dd kk:mm:ss"),
    			new SimpleDateFormat("yyyy/MM/dd kk:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS"),
    			new SimpleDateFormat("dd/MM/yyyy"),
    			new SimpleDateFormat("dd/MM/yyyy kk:mm:ss"),
    			new SimpleDateFormat("dd/MM/yyyy kk:mm:ss.SSS"),
    			new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"),
    			new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS"),
    			new SimpleDateFormat("dd.MM.yyyy"),
    			new SimpleDateFormat("dd.MM.yyyy kk:mm:ss"),
    			new SimpleDateFormat("dd.MM.yyyy kk:mm:ss.SSSZ"),
    			new SimpleDateFormat("dd.MM.yyyy kk:mm:ss.SSSz"),
    			new SimpleDateFormat("dd.MM.yyyy kk:mm:ss.SSS"),
    			new SimpleDateFormat("dd.MM.yyyy kk:mm:ss.SSSZ"),
    			new SimpleDateFormat("dd.MM.yyyy kk:mm:ss.SSSz"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSz"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy.MM.dd"),
    			new SimpleDateFormat("yyyy.MM.dd kk:mm"),
    			new SimpleDateFormat("yyyy.MM.dd kk:mm:ss"),
    			new SimpleDateFormat("yyyy.MM.dd kk:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy.MM.dd kk:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy.MM.dd kk:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy/MM/dd kk:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd kk:mm:ss"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
       			new SimpleDateFormat("kk:mm:ss.SSSZ"),
       			new SimpleDateFormat("kk:mm:ss.SSSz"),
       			new SimpleDateFormat("kk:mm:ss.SSS"),
       			new SimpleDateFormat("kk:mm:ss"),
       			new SimpleDateFormat("kk:mm"),
       			new SimpleDateFormat("HH:mm:ss.SSSZ"),
       			new SimpleDateFormat("HH:mm:ss.SSSz"),
       			new SimpleDateFormat("HH:mm:ss.SSS"),
       			new SimpleDateFormat("HH:mm:ss"),
       			new SimpleDateFormat("HH:mm")
			};
		for(SimpleDateFormat sdf:FORMATS){
			try{
				sdf.setLenient(true);
				value = Timestamp.valueOf(LocalDateTime.fromDateFields(sdf.parse(datetime)).toString());
    			return value;
			} catch (ParseException e){
				value = null;
				continue;
			}
		}
//		value = Timestamp.valueOf(LocalDateTime.parse(datetime).toString());
    if(!Util._IN_PRODUCTION){String msg = "----[TIMESTAMPFROMSTRING.RETURN("+value.toString()+")]----";LOG.info(String.format(fmt, "timestampFromString",msg));}
		return value;
	}

        /**
         *  Creates a new Timesamp with the current date and time.
         * 
         *  @return <code>java.sql.TimeStamp</code> instance set to current date and time.
         * 
         *  @author Christopher Campbell
         * 
         *  @see org.joda.time.DateTime
         *
         */
        public static Timestamp timestamp() {

            DateTime dt = new DateTime();

            return new Timestamp(dt.getMillis());
        }

        
        @SuppressWarnings("deprecation")
				public static LocalDate getLocalDate(Timestamp datetime) {
        	return LocalDate.parse(Util.Time.date(datetime));
//        	return LocalDate.of(datetime.getYear(),datetime.getMonth(),datetime.getDate());
        }
        
        /**
         *  Returns the string value of  a Timesamp with format "yyyy-MM-dd HH:mm:ss".
         *
         *  @param timestamp <code>Timestamp timestamp</code>: the input <code>Timestamp</code>
         *
         *  @return <code>String</code> the output string value of Timestamp with the aforementioned formatting.
         * 
         *  @author Christopher Campbell
         * 
         *  @see org.joda.time.DateTime
         *
         */
        public static String datetime(Timestamp timestamp) {
            DateTime dt = new DateTime(timestamp);
            String stimestamp = dt.toString("yyyy-MM-dd HH:mm:ss");
            return stimestamp;
        }
		
        /**
         *  Returns the string value of  a Timesamp with format "yyyy-MM-dd".
         *
         *  @param timestamp <code>Timestamp timestamp</code>: the input <code>Timestamp</code>
         *
         *  @return <code>String</code> the output string value of Timestamp with the aforementioned formatting.
         * 
         *  @author Christopher Campbell
         * 
         *  @see org.joda.time.DateTime
         *
         */
        public static String date(Timestamp timestamp) {
            DateTime dt = new DateTime(timestamp);
            String stimestamp = dt.toString("yyyy-MM-dd");
            return stimestamp;
        }
		
        /**
         *  Returns the string value of  a Timesamp with format "HH:mm:ss".
         *
         *  @param timestamp <code>Timestamp timestamp</code>: the input <code>Timestamp</code>
         *
         *  @return <code>String</code> the output string value of Timestamp with the aforementioned formatting.
         * 
         *  @author Christopher Campbell
         * 
         *  @see org.joda.time.DateTime
         *
         */
        public static String time(Timestamp timestamp) {
            DateTime dt = new DateTime(timestamp);
            String stimestamp = dt.toString("HH:mm:ss");
            return stimestamp;
        }
		
        /**
         *  Creates a new Timesamp with format "yyyy-MM-dd HH:mm:ss:SSS" of the current date and time.
         * 
         *  @return A new <code>java.sql.TimeStamp</code> instance set to current date and time.
         * 
         *  @author Christopher Campbell
         *  
         *  @param timestamp new timestamp
         * 
         */
        public static Timestamp timestamp(String timestamp) {
            
            return Timestamp.valueOf(timestamp);
        }

        public static Timestamp timstamp(String timestamp, final String format) {
        	DateTime dt = new DateTime(timestamp);
        	return Timestamp.valueOf(dt.toString(format));
        }
        /**
         *  Returns the string value of a <code>DateTime</code> with format "yyyy-MM-dd".
         *
         *  @param date <code>DateTime date</code>: the input <code>DateTime</code>
         *
         *  @return <code>String</code> the output string value of DateTime with the aforementioned formatting.
         * 
         *  @author Christopher Campbell
         * 
         *  @see org.joda.time.DateTime
         *
         */
        public static String getDate(DateTime date) { 
            // TODO Auto-generated method stub
            return date.toString("yyyy-MM-dd");
        }

        /**
         *  Returns the string value of the current date/time in the requested format.
         *
         *  @param format <code>String format</code>: the input <code>String</code> containing the format the date should have. 
         *
         *  @return <code>String</code> the output string value of DateTime with the aforementioned formatting.
         * 
         *  @author Christopher Campbell
         * 
         *  @see org.joda.time.DateTime
         *
         */
        public static String now(String format) {
            DateTime dt = new DateTime();
            if(format == null || format.isEmpty()) return dt.toString().substring(0,19);
            return dt.toString(format);
        }
	
        public static String dateMinus(int days, Timestamp date, String format) {
            DateTime dt = new DateTime(date);
            dt = dt.minusDays(days);
		
            if(format == null || format.isEmpty()) return dt.toString().substring(0,19);
            return dt.toString(format);
        }
	
        public static String nowMinus(int days,String format) {
            DateTime dt = new DateTime();
            dt = dt.minusDays(days);
		
            if(format == null || format.isEmpty()) return dt.toString().substring(0,19);
            return dt.toString(format);
        }
	
        public static String datePlus(int days,Timestamp date, String format) {
            DateTime dt = new DateTime(date);
            dt = dt.plusDays(days);
            if(format == null || format.isEmpty()) return dt.toString().substring(0,19);
            return dt.toString(format);
        }
	
        public static String nowPlus(int days,String format) {
          DateTime dt = new DateTime();
          dt = dt.plusDays(days);
          if(format == null || format.isEmpty()) return dt.toString().substring(0,19);
          return dt.toString(format);
        }
	
        public static String timeStampString() {
            Date date = new Date();	
            return new Timestamp(date.getTime()).toString().substring(0, 19);
        }
		

    }

    public static class DB {
    	public static final boolean _DEBUG = true;
    	private static String fmt = "[%15s] [%s]";
    	
    	private static final Logger LOG = LogManager.getLogger(DB.class);
    	
    	public static DataSource dataSource = null;

    	public static final String _NO_DB_TABLE = "_no_db_table_";
    	
    	public static final String _ORDER_TABLE_PA_PREFIX = "pa";
    	
    	public static final String _NS = "_";
    	
    	public static final String _VS = ":";
    	
    	public static String pTable = 
    			CampSQL.database[CampSQL._PRODUCT_DB_INDEX]
    			+ "."+CampSQL.Product.product_management_tables[CampSQL.Product._PRODUCT_TABLE_INDEX];

    	public static String order_table_db = 
    			CampSQL.database[CampSQL._ORDER_TABLES_DB_INDEX];

    	public static String order_db = 
    			CampSQL.database[CampSQL._ORDER_DB_INDEX];


    	/**
    	 * number of connections to initially create
    	 */
    	public static final int _INITIAL_CONNECTIONS = 100;

    	
    	private static DB instance = null;
    	
    	private DB() {
    		
    	}

    	private static void init(){
//    			try {
//						Class.forName("com.mariadb.jdbc.Driver");
//					} catch (ClassNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
    		if(dataSource ==null) {
          PoolProperties p = new PoolProperties();
          p.setName("CAMPDBPOOL");
          p.setUrl(CampSQL._DBLINK);
          p.setDriverClassName("org.mariadb.jdbc.Driver");
          p.setUsername(CampSQL._USER);
          p.setPassword(CampSQL._PASSWORD);
          p.setJmxEnabled(true);
          p.setTestWhileIdle(false);
          p.setTestOnBorrow(true);
          p.setValidationQuery("SELECT 1");
          p.setTestOnReturn(false);
          p.setValidationInterval(30000);
          p.setTimeBetweenEvictionRunsMillis(30000);
          p.setMaxActive(100);
          p.setInitialSize(10);
          p.setMaxWait(10000);
          p.setRemoveAbandonedTimeout(60);
          p.setMinEvictableIdleTimeMillis(30000);
          p.setMinIdle(10);
          p.setLogAbandoned(true);
          p.setRemoveAbandoned(true);
          p.setJdbcInterceptors(
            "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
            "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
          dataSource = new DataSource();
          dataSource.setPoolProperties(p);
    		}
    	}
    	
    	public static void closeDatasource(){
    		dataSource.purge();
    		dataSource.close();
    	}
    	
    	public static DB instance(){
    		if(DB.instance == null){
    			DB.instance = new DB();
    			init();
    		}
    		return DB.instance;
    	}
    	
    	public static final int rsOffset = 10;
    	public static final int rstOffset = rsOffset+1;
    	
    /*
    	public static String getDBColumns(String tableName,CampList<OrderProductAttribute> attributes, dbActionType action,boolean log){

    		String _f = "-- [getDBColumns]";

    		paContainerType type = TableNameGenerator._getContainerType(tableName, log);
    		boolean isTable = type.equals(paContainerType.TABLE)?true:false;
    		
    		String msg = ">>>> assembling '"+tableName+"' columns for order product '"+type.name()+"' attributes '"+action.name()+"' table";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));

    		boolean isCreate = (action==dbActionType.CREATE)?true:false;
    		boolean isUpdate = (action==dbActionType.UPDATE)?true:false;
    		boolean isInsert = (action==dbActionType.INSERT)?true:false;
    		boolean isSelect = (action==dbActionType.SELECT)?true:false;
    		//**
    		 //* Creating the column 'header'
    		 //:/
    		if(log && _DEBUG){ msg = "-- --[ creating header ...]-- --";LOG.info(String.format(fmt,_f,msg));}
    		String createColumns = 
    				   "`"+"id_` "					+ paTableFieldDef("id_")+", " + "PRIMARY KEY (`"+"id_`), " 
    				 + "`"+"position` " 			+ paTableFieldDef("position")+ ", "
    				 + "`"+"product_group` " 		+ paTableFieldDef("product_group")+ ", "
    				 + "`"+"version` "	 			+ paTableFieldDef("version")+ ", "
    				 + "`"+"product_id` "			+ "int(11) NOT NULL, "
    				 + "`"+"model_id` "				+ "int(11) NOT NULL, "
    				 + "`"+"order_number` " 		+ oTableFieldDef("order_number")+ ", "
    				 + "`"+"order_position_id` "	+ "int(11) NOT NULL, "
    				 + "`"+"table_name` "			+ "varchar(100) NOT NULL, "
    				 + ((isTable)? "`"+"row` "		+ "INT(11) DEFAULT NULL, " : "");
    		
    		String updateColumns = "`"+"id_`= ?, " 
    				 + "`"+"position`= ?, "
    				 + "`"+"product_group`= ?, "
    				 + "`"+"version`= ?, "
    				 + "`"+"product_id`= ?, "
    				 + "`"+"model_id`= ?, "
    				 + "`"+"order_number`= ?, "
    				 + "`"+"order_position_id`= ?, "
    				 + "`"+"table_name`= ?, "
    				 + ((isTable)? "`"+"row` = ?, " : "");
    				 
    		String selectColumns = "`"+"id_`, " 
    				 + "`"+"position`, "
    				 + "`"+"product_group`, "
    				 + "`"+"version`, "
    				 + "`"+"product_id`, "
    				 + "`"+"model_id`, "
    				 + "`"+"order_number`, "
    				 + "`"+"order_position_id`, "
    				 + "`"+"table_name`, "
    				 + ((isTable)? "`"+"row`, " : "");

    		String insertColumns = "`"+"position`, "
    				 + "`"+"product_group`, "
    				 + "`"+"version`, "
    				 + "`"+"product_id`, "
    				 + "`"+"model_id`, "
    				 + "`"+"order_number`, "
    				 + "`"+"order_position_id`, "
    				 + "`"+"table_name`, "
    				 + ((isTable)? "`"+"row`, " : "");

    		/// So you tell them all that shit Dennis?

    		String columns = ((isCreate)? createColumns : "" ) 
    						+((isUpdate)? updateColumns : "" )
    						+((isInsert)? insertColumns : "" ) 
    						+((isSelect)? selectColumns : "" );

    		if(log && _DEBUG){ msg = "-- --[ ... header created: ["+columns+"]]-- --";LOG.info(String.format(fmt,_f,msg));}
    		
    		if(log && _DEBUG){ msg = "-- --[ creating body ...]-- --";LOG.info(String.format(fmt,_f,msg));}
    		int count = attributes.size();	
    		String body = "";
    		for(OrderProductAttribute<?,?> a : attributes.value()){			
    			count--;
    			body += "`"+a.columnName()+"` ";
    			body += ((isCreate)?paValueTableFieldDef(a.type()):"");
    			body += ((isUpdate)?"= ?":"");			
    			body += (count > 0)?", ":" ";			
    		}
    		columns += body;
    		if(log && _DEBUG){ msg = "-- --[ body created :["+body+"]]-- --";LOG.info(String.format(fmt,_f,msg));}

    		if(log && _DEBUG){ msg = "-- --[<<<< ... columns assembled ['"+columns+"']]-- --";LOG.info(String.format(fmt,_f,msg));}

    		return columns;
    	}
*/
        private static String attibuteValueTableFieldDef(String field,AttributeType attributeType){
            return CampSQL.getValueFieldDefinition(field, attributeType);
    	}
    	private static String oTableFieldDef(String field){
    		return CampSQL.getFieldDefinition(field, CampSQL.Order.order_table_definition);
    	}
    	private static String opTableFieldDef(String field){
    		return CampSQL.getFieldDefinition(field, CampSQL.Order.order_has_order_position_table_definition);
    	}
    	private static String enumTableFieldDef(CampEnum e){
    		String def = "ENUM(";
    		String[] values = e.values();
    		int count = values.length;
    		for(String value:values){
    			count--;
    			def += "'"+value+"'";
    			def += (count>0)?",":"";
    		}
    		def += ") NOT NULL";
    		return def;
    	}
    	
    	private static String setTableFieldDef(CampSet set) {
    		String def = "SET(";
    		String[] values = set.values();
    		int count = values.length;
    		for(String value:values){
    			count--;
    			def += "'"+value+"'";
    			def += (count>0)?",":"";
    		}
    		def += ") NOT NULL";		
    		return def;
    	}
    	public static enum dbActionType {
    		CREATE, INSERT, PINSERT, UPDATE, SELECT, DELETE, MSELECT;
    	}
    	public static final String[] cColumns(){
    		return columns(CampSQL.Customer.customer_table_definition);
    	}
    	public static final String[] prColumns(){
    		return columns(CampSQL.Process.process_table_definition);
    	}
    	public static final String[] pprColumns(){
    		return columns(CampSQL.Process.production_process_table_definition);
    	}
    	public static final String[] pColumns(){
    		return columns(CampSQL.Product.product_table_definition);
    	}
    	public static final String[] oColumns(){
    		return columns(CampSQL.Order.order_table_definition);
    	}
    	public static final String[] opColumns(){
    		return columns(CampSQL.Order.order_position_table_definition);
    	}
    	public static final String[] ohpColumns(){
    		return columns(CampSQL.Order.order_has_process_table_definition);
    	}
    	public static final String[] columns(String[][] cd){
    		String[] cols = new String[cd.length];
    		int count = 0;
    		for(String[]a:cd){
    			cols[count] = a[0];
    			count++;
    		}
    		return cols;
    	}

    	public static final HashMap<String,Boolean> createDatabases() {
    		return Util.DB._createDatabases(true);
    	}
    	public static final HashMap<String,Boolean> _createDatabases(Boolean log) {
    		long startTime = System.currentTimeMillis();
    		String _f = null;
    		String msg = null;
    		if(log && _DEBUG) {
    			_f = "[_createDatabases]";
    			msg = "====[ creating databases ]====";LOG.info(String.format(fmt,_f,msg));
    		}
    		
    		Connection conn = null;
    		HashMap<String,Boolean> ok = new HashMap<String,Boolean>();
    		Statement dbs = null;
    		try {
    			conn = DriverManager.getConnection(CampSQL._DBLINK,
    					CampSQL._USER, CampSQL._PASSWORD);
    			for(String dbName:CampSQL.database){
    				
    				if(dbName.equals(Util.DB._NO_DB_TABLE))continue;
    				ok.put(dbName, false);
    				String SQL = "CREATE DATABASE IF NOT EXISTS `"+dbName+"`"+" DEFAULT CHARSET=utf8 ";
    			if(log && _DEBUG) {msg = "-- --[ SQL: "+SQL+"]-- --";LOG.info(String.format(fmt,_f,msg));}
    				
    				dbs = conn.createStatement();
    				if(dbs.executeQuery(SQL) != null){
    					msg = "Created"; if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
    					ok.put(dbName, true);
    				}else{
    					msg = "ERROR! Failed to create database '"+dbName+"'"; if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
    				}
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		} finally {
    				
    				if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
    				Util.DB.__release(conn,log);
    				Util.DB._releaseStatement(dbs, log);
    				
    		}
    		return ok;
    	}
    	
    	public static final HashMap<String,Boolean> deleteDatabases() {
//    		Util.DB._instance(false,true);
    		return Util.DB._deleteDatabases(true);
    	}
    	public static final HashMap<String,Boolean> _deleteDatabases(Boolean log) {
    		long startTime = System.currentTimeMillis();
    		String _f = null;
    		String msg = null;
    		if(log && _DEBUG) {
    			_f = "[_deleteDatabases]";
    			msg = "====[ deleting all application databases ]====";LOG.info(String.format(fmt,_f,msg));
    		}
    		
    		String db = "";
    		Connection conn = null;
    		HashMap<String,Boolean> ok = new HashMap<String,Boolean>();
    		try {
    			conn = DriverManager.getConnection(CampSQL._DBLINK,
    					CampSQL._USER, CampSQL._PASSWORD);
    			for(String dbName:CampSQL.database){			
    				db = dbName;
    				if(dbName.equals(Util.DB._NO_DB_TABLE))continue;
    				ok.put(dbName, false);
    				String SQL = "DROP DATABASE IF EXISTS "+dbName;
    				if(log && _DEBUG) {msg = "-- --[ deleting '"+dbName+"']-- --";LOG.info(String.format(fmt,_f,msg));}
    					Statement dbStmt = conn.createStatement(); 
    					if(dbStmt.executeQuery(SQL) != null){
    					if(log && _DEBUG) {msg = "-- --[ ... deleted!]-- --";LOG.info(String.format(fmt,_f,msg));}
    						ok.put(dbName, true);
    					}else{
    					if(log && _DEBUG) {msg = "-- --[ ERROR! delete failed]-- --";LOG.info(String.format(fmt,_f,msg));}
    					}
    			}
    		} catch (SQLException e) {
    		if(log && _DEBUG) {msg = "-- --[ EXCEPTION! delete failed]-- --";LOG.info(String.format(fmt,_f,msg));}
    			e.printStackTrace();
    		} finally {
    			__release(conn,log);
//    			if(conn != null)
//    				Util.DB._instance(false,log).releaseConnection(conn);
    		}
    		return ok;
    	}
    	/**
    	 * Check if all databases in the dbNames String Array exist.
    	 * NOTE: this only works if the postfix '_management' naming 
    	 * convention is upheld.
    	 * @param dbNames database names
    	 * @return boolean array 
    	 */
    	public static boolean[] dbExists(String[] dbNames){
    		return _dbExists(dbNames,true);
    	}
    	public static boolean[] _dbExists(String[] dbNames, boolean log){
    		long startTime = System.currentTimeMillis();
    		String _f = null;
    		String msg = null;
    		if(log && _DEBUG) {
    			_f = "[_dbExists]";
    			msg = "====[ checking if list of dbs exists on database  ]====";LOG.info(String.format(fmt,_f,msg));
    		}
    		boolean[] dbExists = new boolean[dbNames.length];
    		ResultSet rs = null;
    		Connection con = null;
    		Statement dbs = null;
    		//Tables_in_ordertables
    		try {
    			con = Util.DB.__conn(log);
    			dbs = con.createStatement();		
    			String sqlStmt = "show databases like '_management'";
    			rs = dbs.executeQuery(sqlStmt);
    			
    			while(rs.next()){
    				String rsDBName = rs.getString("Database (%management)");
    				for(int i= 0;i< dbNames.length;i++){
    					if(rsDBName.equals(dbNames[i])){
    						dbExists[i] = true;
    					}
    				}
    			} 
    		}catch (SQLException e) {

    			e.printStackTrace();
    		
    		} finally {
    			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
    			Util.DB.__release(con,log);
    			Util.DB._releaseRS(rs, log);
    		}
    		if(log && _DEBUG) {
    		String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
    			msg = "====[ check completed.]====";LOG.info(String.format(fmt,_f,msg+time));
    		}
    		return dbExists;
    	}
    	public static boolean dbExists(String dbName){
    		return _dbExists(dbName,true);
    	}
    	public static boolean _dbExists(String dbName, boolean log){
    		long startTime = System.currentTimeMillis();
    		String _f = null;
    		String msg = null;
    		if(log && _DEBUG) {
    			_f = "[_dbExists]";
    			msg = "====[ checking if '"+dbName+"' exists on database  ]====";LOG.info(String.format(fmt,_f,msg));
    		}

    		ResultSet rs = null;
    		Connection con = null;
    		String sqlStmt = "show databases like '"+dbName+"'";
    		//Tables_in_ordertables
    		try {
    			con = Util.DB.__conn(log);
    			rs = con.createStatement().executeQuery(sqlStmt);
    			
    			while (rs.next()){
    				String rsDBName = rs.getString("Database ("+dbName+")");
    				if(rsDBName.equals(dbName)){		
    					return true;
    				}
    			} 
    		}catch (SQLException e) {

    			e.printStackTrace();
    		
    		} finally {
    			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
    			Util.DB.__release(con,log);
    			Util.DB._releaseRS(rs, log);
    		}
    		if(log && _DEBUG) {
    		String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
    			msg = "====[ check completed.]====";LOG.info(String.format(fmt,_f,msg+time));
    		}
    		return false;
    	}
    	public static  HashMap<String,HashMap<String,Boolean>> tableExists(String dbName,String table, String[][] tableDefinition) {
    		return _tableExists(dbName,table,tableDefinition,true);
    	}
    	public static  HashMap<String,HashMap<String,Boolean>> _tableExists(String db,String t, String[][] tdef,boolean log) {
    		long startTime = System.currentTimeMillis();
    		String _f = null;
    		String msg = null;
    		if(log && _DEBUG) {
    			_f = "[_tableExists]";
    			msg = "====[ checking if '"+t+"' exists ... ]====";LOG.info(String.format(fmt,_f,msg));
    		}
    				
    		HashMap<String,HashMap<String,Boolean>> ok = new HashMap<String,HashMap<String,Boolean>>();
    		
    		
    		dbActionType action = dbActionType.SELECT;			
    		String[] columns = _columnArray("",tdef, action, log);
    		int count = 0;

    		for(String column:columns){
    			columns[count] = column.replace("`", "").trim();
    			count++;
    		}
    		ok = _getOK(action, db, t, ok, columns,log);
    		return ok;
    	}

    	
//    	/**
//    	 * 
//    	 * @param conn
//    	 * @param o
//    	 * @return
//    	 * @throws SQLException 
//    	 */
//    	/*
///**    	public static HashMap<String,HashMap<String,Boolean>> orderTablesExist(Connection conn,Order o) throws SQLException {
//    		return _orderTablesExist(o,true);
//    	}
//
//    	public static  HashMap<String,HashMap<String,Boolean>> _orderTablesExist(Order o,boolean log) throws SQLException {
//    		HashMap<String,CampList> tableMap = new HashMap<String,CampList>();
//    		for(OrderPosition op: o.orderPositions().value()){
//    			op.product().addToOrderTableCreateMap(tableMap,log);
//    		}
//    		return _orderTablesExist(tableMap,log);
//    	}
//    	public static  HashMap<String,HashMap<String,Boolean>> _orderTablesExist(HashMap<String,CampList> tableMap,Boolean log) {
//    		long startTime = System.currentTimeMillis();
//    		String _f = null;
//    		String msg = null;
//    		if(log && _DEBUG) {
//    			_f = "[_orderTablesExist]";
//    			msg = "====[ check if order tables exist ]====";LOG.info(String.format(fmt,_f,msg));
//    		}
//    				
//    		dbActionType action = dbActionType.SELECT;
//    		
//    		String columns = "";
//    		
//    		HashMap<String,HashMap<String,Boolean>> ok = new HashMap<String,HashMap<String,Boolean>>();
//    		
//    		for(String tableName:tableMap.keySet()){
//    			
//    			columns = Util.DB.getDBColumns(tableName,tableMap.get(tableName), action, log);
//
//    			String[] otColumns = columns.replace("`", "").split(",");
//    			
//    			int count = otColumns.length;
//    			while(count>1){
//    				count--;
//    				otColumns[count] = otColumns[count].trim();
//    			}
//    			ok = _getOK(action, Util.DB.order_table_db,tableName, ok, otColumns,log);
//    		}
//    		return ok;
//    	}
//    	*/
    	public static HashMap<String,HashMap<String,Boolean>>  _getOK(dbActionType action,
    			String dbName,String tableName,HashMap<String,HashMap<String,Boolean>>   ok,String[] otColumns,Boolean log){
    		long startTime = System.currentTimeMillis();
    		String _f = null;
    		String msg = null;
    		if(log && _DEBUG) {
    			_f = "[_getOK]";
    			msg = "====[ Checking field names of '"+tableName+"' on action '"+action+"' ]====";LOG.info(String.format(fmt,_f,msg));
    		}

    		if(log && _DEBUG){ msg = "-- --[ check against column array ["+Util.Text.join(otColumns, ",")+"]]-- --";LOG.info(String.format(fmt,_f,msg));}
    		HashMap<String,Boolean> truth = new HashMap<String,Boolean>();
    		if(!tableName.contains("`")){
    			if(tableName.contains(".")){
    				String[] parts = tableName.split(".");
    				int count = 0;
    				for(String part: parts){
    					parts[count] = "`"+part.trim()+"`";
    					count++;
    				}
    				tableName = Util.Text.join(parts, ".");
    			}else{
    				tableName = "`"+tableName+"`";
    			}
    		}
    		String SQL = " SHOW COLUMNS FROM "+tableName+" FROM `"+dbName+"`";
    		if(log && _DEBUG){ msg = "-- --[ SQL ]]"+SQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}
    		
    		//Tables_in_ordertables
    		
    		ResultSet rs = null;
    		Connection conn = null;
    		try {
    			conn = __conn(log);
    			rs = conn.createStatement().executeQuery(SQL);
    			int count = 0;
    			//if(action.equals(dbActionType.INSERT)) count = 1;
    			while (rs.next()){
    				
    				String  fn = CampSQL.database_table_structure[CampSQL._FIELD_DBTS_INDEX];
    				String columnName = rs.getString(fn);
    				String checkCol = otColumns[count].replaceAll("`", "");
    				if(action.equals(dbActionType.INSERT) && columnName.equals("id")) continue;
    			if(log && _DEBUG) {msg = "-- --[ check '"+columnName+"' == '"+checkCol+"'? ('"+(columnName.equals(checkCol))+"')]-- --";LOG.info(String.format(fmt,_f,msg));}
    				truth.put(otColumns[count], (columnName.equals(otColumns[count])));
    				count++;
    			} 
    			ok.put(tableName, truth);
    		}catch (SQLException e) {

    			e.printStackTrace();
    		
    		} finally {
    			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
    			Util.DB.__release(conn,log);
    			Util.DB._releaseRS(rs, log);
    			
    			
    		}
    		return ok;

    	}
    	

    	public static boolean orderTableExists(String dbName, String tableName) throws SQLException{
    		return _orderTableExists(dbName, tableName, true);
    	}
    	public static boolean _orderTableExists(String dbName, String tableName, boolean log) throws SQLException{
    		long startTime = System.currentTimeMillis();
    		String _f = null;
    		String msg = null;
    		if(log && _DEBUG) {
    			_f = "[_orderTableExists]";
    			msg = "====[ check if table '"+tableName+"' exists in '"+dbName+"' ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
    		}
    		
    		Connection conn = null;
    		Statement dbs = null;
    		ResultSet rs = null;
    		
    		//Tables_in_ordertables
    		try {
    			conn = __conn(false);
    			dbs = conn.createStatement();
    			dbName = dbName.trim();
    			
    			String SQL = "show tables from "+dbName;
    			
    			rs = dbs.executeQuery(SQL);
    			
    			while (rs.next()){
    				String col = "Tables_in_"+dbName;
    				col.trim();
    				String table = rs.getString(col);
    				
    				if(table.equals(tableName)){
    				if(log && _DEBUG) {msg = "[>>>> ---- >>>> ---- found '"+table+"']-- --";LOG.info(String.format(fmt,_f,msg));}
    					msg= "<<<< ---- Checking if table '"+tableName+"' exists in '"+dbName+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
    					return true;
    				}
    			} 
    		}catch (SQLException e) {
    			if(log && _DEBUG){msg = "----[SQL EXCEPTION ROOT! Check '"+tableName+"' FAILED]----";LOG.info(String.format(fmt, _f,msg));}
    			e.printStackTrace();
    			throw e;
    		
    		} finally {
    			Util.DB.__release(conn,log);
    			Util.DB._releaseStatement(dbs, log);
    			Util.DB._releaseRS(rs, log);
    		}
    		if(log && _DEBUG) {
    			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
    			msg = "====[_orderTableExists completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
    		}
    		return false;
    	}

    	public static Connection __conn(boolean log){
    		log = false;
    		instance();
    		Connection connection = null;
    		try {
    		 			connection = dataSource.getConnection();
//    			connection = DriverManager.getConnection(CampSQL._DBLINK, CampSQL._USER, CampSQL._PASSWORD);
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
//    		catch (Exception e1) {
//    			e1.printStackTrace();
//    		}
    		return connection;
    	}
    	public static Connection _conn(boolean log){
    		log = false;
    		instance();
    		Connection connection = null;
    		try {
    			connection = DriverManager.getConnection("jdbc:apache:commons:dbcp:example");
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		return connection;
    	}
    	public static void _release(Connection connection,boolean log) {
    		log = false;
    		long startTime = System.currentTimeMillis();
    		String _f = null;
    		String msg = null;
    		if(log && _DEBUG) {
    			_f = "[releaseConnection]";
    			msg = "====[ releasing connection ... ]====";LOG.info(String.format(fmt,_f,msg));
    		}
    		
    		try {
    			if(connection != null)connection.commit();
    			if(connection != null)connection.close();
    		} catch (SQLException e1) {
    			e1.printStackTrace();
    		}
    	}

    	public static void __release(Connection connection,boolean log) {
    		log = false;
    		long startTime = System.currentTimeMillis();
    		String _f = null;
    		String msg = null;
    		if(log && _DEBUG) {
    			_f = "[releaseConnection]";
    			msg = "====[ releasing connection ... ]====";LOG.info(String.format(fmt,_f,msg));
    		}
    		
    		try {
    			if(connection != null && !connection.isClosed())connection.commit();
    			if(connection != null&& !connection.isClosed())connection.close();
    		} catch (SQLException e1) {
    			e1.printStackTrace();
    		}
    	}
    	public static void releaseRS(ResultSet rs) {
    		_releaseRS(rs,true);
    	}
    	public static void _releaseRS(ResultSet rs, boolean log) {
    		log = false;
    		long startTime = System.currentTimeMillis();
    		String _f = null;
    		String msg = null;
    		if(log && _DEBUG) {
    			_f = "[_releaseRS]";
    			msg = "====[ releasing result set ... ]====";LOG.info(String.format(fmt,_f,msg));
    		}

    		try {
    			if(rs != null && !rs.isClosed()) rs.close();
    		} catch (SQLException e1) {
    		if(log && _DEBUG) {msg = "-- --[ ERROR! could not close database result set]-- --";LOG.info(String.format(fmt,_f,msg));}
    			e1.printStackTrace();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		if(log && _DEBUG) {msg = "-- --[ ERROR! could not close database result set]-- --";LOG.info(String.format(fmt,_f,msg));}
    		}
    	}
    	public static void releaseStatement(Statement dbs) {
    		_releaseStatement(dbs,true);
    	}
    	public static void _releaseStatement(Statement dbs, boolean log) {
    		log = false;
    		long startTime = System.currentTimeMillis();
    		String _f = null;
    		String msg = null;
    		if(log && _DEBUG) {
    			_f = "[_releaseStatement]";
    			msg = "====[ releasing statement ... ]====";LOG.info(String.format(fmt,_f,msg));
    		}

    		try {
    			if(dbs != null)dbs.close();
    		} catch (SQLException e1) {
    		if(log && _DEBUG) {msg = "-- --[ ERROR! could not close database Statement]-- --";LOG.info(String.format(fmt,_f,msg));}
    			e1.printStackTrace();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		if(log && _DEBUG) {msg = "-- --[ ERROR! could not close database Statement]-- --";LOG.info(String.format(fmt,_f,msg));}
    		}
    	}
    	public static void releaseStatement(PreparedStatement dbs) {
    		_releaseStatement(dbs,true);
    	}
    	public static void _releaseStatement(PreparedStatement dbs, boolean log) {
    		log = false;
    		long startTime = System.currentTimeMillis();
    		String _f = null;
    		String msg = null;
    		if(log && _DEBUG) {
    			_f = "[_releaseStatement]";
    			msg = "====[ releasing prepared statement ... ]====";LOG.info(String.format(fmt,_f,msg));
    		}

    		try {
    			if(dbs!=null) dbs.close();
    		} catch (SQLException e1) {
    		if(log && _DEBUG) {msg = "-- --[ ERROR! could not close PreparedStatement]-- --";LOG.info(String.format(fmt,_f,msg));}
    			e1.printStackTrace();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		if(log && _DEBUG) {msg = "-- --[ ERROR! could not close PreparedStatement]-- --";LOG.info(String.format(fmt,_f,msg));}
    		}
    	}


    	public static String[] columnArray(String[][] tableDefinition,dbActionType action){
    		return _columnArray("",tableDefinition,action, true);
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
    	public static String[] _columnArray(String table, String[][] tdef, dbActionType action,boolean log){
    		return getColumnArray(table,tdef,action,log);
    	}

    	public static String columns(String[][] tableDefinition,dbActionType action){
    		return _columns("",tableDefinition,action, true);
    	}
    	public static String _columns(String table,String[][] tdef, dbActionType action,boolean log){
    		log = false;
    		return getColumns(table,tdef,action, log);
    	}

    	/**
    	 * 
    	 * @param tdef table definition
    	 * @param action type of db action
    	 * @param log log switch
    	 * @return a String representation of the SQL 'column_name [column_definition] [=?]' dependent on the type of DB action. 
    	 */
    	//TODO: add logging;
    	public static String _columns(String[][] tdef, dbActionType action,boolean log){
    		log = false;
    		return getColumns("",tdef,action,log);
    	}
    	
    	//TODO: add dbAction and handle counter value that way to get rid of redundency
    	public static String getColumns(String table,String[][] tableDefinition,dbActionType action,boolean log){
    		long startTime = System.currentTimeMillis();
    		String _f = null;
    		String msg = null;
    		if(log && _DEBUG) {
    			_f = "[insertColumns]";
    			msg = "====[ assembling columns String for insert sql ... ]====";LOG.info(String.format(fmt,_f,msg));
    		}

    		String colDef = "";
    		
    		table = ((table.isEmpty())?"":"`"+table+"`.");
    		
    		boolean create = action.equals(dbActionType.CREATE);
    		boolean insert = action.equals(dbActionType.INSERT);
    		boolean update = action.equals(dbActionType.UPDATE);
    		boolean select = action.equals(dbActionType.SELECT);
    		boolean hasExtra = tableDefinition[tableDefinition.length-1][0].equals("extra");
    		boolean hasId = tableDefinition[0][0].contains("id_".subSequence(0, 3));
    		
    		int counter = tableDefinition.length;
    		
    		// subtract 2 since 'id' and 'extra' matrix entries are skipped for insert and update actions
    		
    		if((insert||update) && ((!hasExtra && hasId)||(hasExtra && !hasId))) {
    			counter = counter - 1;
    		} else if((insert||update)&& hasExtra && hasId) {
    			counter = counter - 2;
    		}
    		
    		// subtract 1 since the 'extra' matrix entry is skipped for select actions
    		
    		if(select && hasExtra) {
    			counter = counter -1;
    		}
    		
    		// walk the definition array
    		for(String[] cr:tableDefinition){

    			boolean extra = cr[0].equals("extra");
    		//.equals("id")
    			if((cr[0].contains("id_".subSequence(0, 3))&&!(create || select)) || (extra && !create)) continue;

    			counter--;
    			
    			String entry = ((!extra)? table + "`"+cr[0]+"`":"") 
    						 + ((create)?" "+cr[1]:"") 
    						 + ((update)?"=%s":"") 
    						 + ((counter>0)?", ":"");
    			
    			colDef += entry;
    			
    		if(log && _DEBUG) {msg = "-- --[ added >> "+entry+" <<]-- --";LOG.info(String.format(fmt,_f,msg));}

    		}
    		if(log && _DEBUG) {
    		String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
    			msg = "====[ created.]====";LOG.info(String.format(fmt,_f,msg+time));
    		}
    		return colDef;
    	}
    	public static String[] getColumnArray(String table, String[][] tableDefinition,dbActionType action,boolean log){
    		long startTime = System.currentTimeMillis();
    		String _f = null;
    		String msg = null;
    		if(log && _DEBUG) {
    			_f = "[createColumns]";
    			msg = "====[ creating table def ... ]====";LOG.info(String.format(fmt,_f,msg));
    		}

    		table = ((table.isEmpty())?"":"`"+table+"`.");
    		
    		boolean create = action.equals(dbActionType.CREATE);
    		boolean insert = action.equals(dbActionType.INSERT);
    		boolean update = action.equals(dbActionType.UPDATE);
    		boolean select = action.equals(dbActionType.SELECT);

    		int cols = tableDefinition.length;
    		
    		// subtract 2 since 'id' and 'extra' matrix entries are skipped for insert and update actions
    		
    		if(insert||update) {
    			cols = cols -2;
    		}
    		
    		// subtract 1 since the 'extra' matrix entry is skipped for select actions
    		
    		if(select) {
    			cols = cols-1;
    		}
    		

    		int count = 0;
    		
    		boolean start = true;
    		
    		String[] aCol = new String[cols];
    		
    		for(String[] cr:tableDefinition){

    			boolean extra = cr[0].equals("extra");
    			//.equals("id")
    			if((cr[0].contains("id_".subSequence(0, 3))&&!(create || select)) || (extra && !create)) continue;
    			
    			aCol[count] = ((!extra)? table + "`"+cr[0]+"`":"") 
    						 + ((create)?" "+cr[1]:"") 
    						 + ((update)?"`=%s":"");
    			
    		if(log && _DEBUG) {msg = "-- --[ added >> "+aCol[count]+" <<]-- --";LOG.info(String.format(fmt,_f,msg));}

    			count++;
    		}
    		if(log && _DEBUG){ msg = "-- --[<<<< ... created.]-- --";LOG.info(String.format(fmt,_f,msg));}
    		
    		return aCol;		
    	}
    	
    	public static boolean hasId(String[][] tabledef){
    		String id = (tabledef[0][0]);
    		//.equals("id")
    		if(id.contains("id_".subSequence(0, 3))) return true;
    		return false;
    	}


    	public static String loadTableNamesSQL = "select TABLE_NAME from information_schema.tables  where table_name like '%s"+Util.DB._NS+"%%'";
    	public static String loadTableNamesSQL(String... on){return String.format(loadTableNamesSQL, on[0]);}

    	/**
    	 * Assmbles a list of strings from the first column of a database query result set.
    	 * TODO: no checks are made if column contains a sting value or not. 
    	 * @param rs result set
    	 * @return array list 
    	 */
    	public static ArrayList<String> rsToStringList(ResultSet rs){
    		return _rsToStringList(rs,true);
    	}
    	public static ArrayList<String> _rsToStringList(ResultSet rs,boolean log){
    		long startTime = System.currentTimeMillis();
    		String _f = null;
    		String msg = null;
    		if(log && _DEBUG) {
    			_f = "[_rsToStringList]";
    			msg = "====[ assembling list of string values from DB result set ]====";LOG.info(String.format(fmt,_f,msg));
    		}
    		
    		ArrayList<String> result = new ArrayList<String>();
    		int count = 0;
    		try{
    			while(rs.next()){
    				result.add(rs.getString(1));
    				count++;
    			}
    		} catch(SQLException e){
    		if(log && _DEBUG) {msg = "-- --[ EXCEPTION! String list assembly failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
    			e.printStackTrace();
    		}
    		if(log && _DEBUG){ msg = "-- --[ assembled list of '"+count+"' elements]-- --";LOG.info(String.format(fmt,_f,msg));}
    		return result;
    	}

    }

    public static class Config {

        private static Properties properties = null;

    	private static Config instance = null;
    	
    	private Config() {
            properties = new Properties();

            InputStream input = null;

            try {

                input = getClass().getResourceAsStream("/" +"config.properties");

                properties.load(input);
                
            } catch (IOException ex) {
            	
                ex.printStackTrace();
            
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    		
    	}
    	public static Config instance() {
    		if(instance == null) {
    			instance = new Config();
    		}
    		return instance;
    	}
    	
    	public Properties properties() {
    		return properties;
    	}
    	
    	public String defaultBusinessId(String object) {
    		return Util.Time.now("yyyyMMddhhmmss");
//    		return properties.getProperty("object.create."+object+".businessId")+"."+UUID.randomUUID().toString();
    	}

    	public String defaultBusinessKey(String object) {
    		return properties.getProperty("object.create."+object+".businessKey")+"."+UUID.randomUUID().toString();
    	}

    	public int defaultByDateDays(String object) {
    		return Integer.valueOf(properties.getProperty("object.create."+object+".byDate.days"));
    	}

    	public int defaultEndOfLifeDays(String object) {
    		return Integer.valueOf(properties.getProperty("object.create."+object+".endOfLife.days"));
    	}

    	public String defaultGroup(String object) {
    		return properties.getProperty("object.create."+object+".group")+"."+UUID.randomUUID().toString();
    	}

    	public String defaultVersion(String object) {
    		return properties.getProperty("object.create."+object+".version")+"."+UUID.randomUUID().toString();
    	}

   }

    public static class Test {
    	
       public static int coverage(int count,Class<?> clazz) {
        	int FUNCTOTAL = clazz.getDeclaredMethods().length-2; //minus the setup before and tear down after methods
        	return java.lang.Math.floorDiv((count*100), FUNCTOTAL);
        }

        public static int coverage(int count,int numberOfMethods) {
        	return java.lang.Math.floorDiv((count*100), numberOfMethods);
        }
    }

}
