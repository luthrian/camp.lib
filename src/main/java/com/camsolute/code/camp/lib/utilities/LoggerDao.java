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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.dao.LoggerDaoInterface;
import com.camsolute.code.camp.lib.data.CampFormats;
import com.camsolute.code.camp.lib.data.CampSQL;
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampInstanceDao;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.InstanceId;
import com.camsolute.code.camp.lib.models.Version;

public class LoggerDao implements LoggerDaoInterface {

	private static final Logger LOG = LogManager.getLogger(LoggerDao.class);
	private static String fmt = "[%15s] [%s]";
	
	public static String dbName = CampSQL.database[CampSQL._LOGGING_TABLES_DB_INDEX];

	public static String table = CampSQL.logTable(CampSQL._LOGGING_TABLES_DB_INDEX, CampSQL.Logging._INSTANCE_LOG_INDEX);

	public static String[][] tabledef = CampSQL.Logging._instance_log_table_definition;

	public static String loadByTypeSQL = "SELECT * FROM " + table + " WHERE `_object_type`='%s'";
	public static String loadByTypeGroupSQL = "SELECT * FROM " + table + " WHERE `_object_type`='%s' AND `_group_name`='%s'";
	public static String loadByTypeVersionSQL = "SELECT * FROM " + table + " WHERE `_object_type`='%s' AND `_version_value`='%s'";
	public static String loadByTypeDateSQL = "SELECT * FROM " + table + " WHERE `_object_type`='%s' AND `_date`='%s'";
	public static String loadByTypeEndOfLifeSQL = "SELECT * FROM " + table + " WHERE `_object_type`='%s' AND `_end_of_life`='%s'";
	public static String loadByTypeTimestampSQL = "SELECT * FROM " + table + " WHERE `_object_type`='%s' AND `_timestamp`='%s'";
	public static String loadByTypeLogTimestampSQL = "SELECT * FROM " + table + " WHERE `_object_type`='%s' AND `_log_timestamp`='%s'";
	public static String loadByBusinessIdSQL = "SELECT * FROM " + table + " WHERE `_object_business_id`='%s'";
	public static String loadByGroupSQL = "SELECT * FROM " + table + " WHERE `_object_business_id`='%s' AND `_group_name`='%s'";
	public static String loadByVersionSQL = "SELECT * FROM " + table + " WHERE `_object_type`='%s' AND `_version_value`='%s'";
	public static String loadByDateSQL = "SELECT * FROM " + table + " WHERE `_object_type`='%s' AND `_date`='%s'";
	public static String loadByEndOfLifeSQL = "SELECT * FROM " + table + " WHERE `_object_type`='%s' AND `_end_of_life`='%s'";
	public static String loadByTimestampSQL = "SELECT * FROM " + table + " WHERE `_object_type`='%s' AND `_timestamp`='%s'";
	public static String loadByLogTimestampSQL = "SELECT * FROM " + table + " WHERE `_object_type`='%s' AND `_log_timestamp` LIKE '%s%%'";
	
	private static LoggerDao instance = null;
	
	private LoggerDao(){
	}
	
	public static LoggerDao instance(){
		if(instance == null) {
			instance = new LoggerDao();
		}
		return instance;
	}
	
	/**
  * Returns the database scheme name.
  *
  * @return data base name
  */
 public String dbName() {
	 return dbName;
 }

 /**
  * Returns the database table name in which the object instance log entry is
  * persisted.
  *
  * @return table {@link java.lang.String} value of the table name.
  */
 public String table() {
	 return table;
 }

 /**
  * Returns a {@link java.lang.String} matrix containing the the database table field name and definition as defined in {@link com.camsolute.code.camp.lib.data.CampSQL}.
  *
  * @return tabledef {@link java.lang.String} matrix containing the table definition.
  */
 public String[][] tabledef() {
	 return tabledef;
 }
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IsObjectInstance<T>> LogEntry<T> log(IsObjectInstance<?> object, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[log]";
			msg = "====[ logging dao call: log the current state of an object instance ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntry<T> le = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + table + "( " + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( " + insertValues(object,log) + " )";;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			rs = dbs.getGeneratedKeys();						
			int id = 0;
			if (rs.next()) {		
				id = rs.getInt(tabledef[0][0]);
			}
			le = new LogEntry<T>(id,(T)object);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[log completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return le;
	}

	@Override
	public <T extends IsObjectInstance<?>, E extends ArrayList<T>> LogEntryList log(E objects, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[log]";
			msg = "====[ logging dao call: log the current state of a list of object instances ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + table + "( " + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertValues(objects,log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			rs = dbs.getGeneratedKeys();						
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[log completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	@Override
	public LogEntryList loadByType(String objectType, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByType]";
			msg = "====[ logging dao call: load a list ob log entry objects by object type ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = String.format(loadByTypeSQL,objectType);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByType completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	@Override
	public LogEntryList loadByTypeGroup(String objectType, String group,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByTypeGroup]";
			msg = "====[ logging dao call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = String.format(loadByTypeGroupSQL,objectType, group);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByTypeGroup completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	@Override
	public LogEntryList loadByTypeVersion(String objectType, String version,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByTypeVersion]";
			msg = "====[ logging dao call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = String.format(loadByTypeVersionSQL,objectType, version);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByTypeVersion completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	@Override
	public LogEntryList loadByTypeDate(String objectType, String date,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByTypeDate]";
			msg = "====[ logging dao call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = String.format(loadByTypeDateSQL,objectType, date);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByTypeDate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	@Override
	public LogEntryList loadByTypeDateRange(String objectType,
			String startDate, String endDate, RangeTarget target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByTypeDateRange]";
			msg = "====[ logging dao call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String targ = "";
			switch(target) {
			case LOG_TIMESTAMP:
				targ = "_log_timestamp";
				break;
			case OBJECT_TIMESTAMP:
				targ = "_timestamp";
				break;
			case OBJECT_DATE:
				targ = "_date";
				break;
			case OBJECT_END_OF_LIFE:
				targ = "_end_of_life";
				break;
			default:
				targ = "_date";
				break;
			}
			String sdt = startDate.substring(0,19);
			String edt = endDate.substring(0,19);
			String fSQL = "";
			if( (sdt == null || sdt.isEmpty())){
				fSQL = " AND DATE_SUB('"+edt+"',INTERVAL "+CampFormats._DAYS_IN_PAST_SEARCH_RANGE+" DAY) <= `"+targ+"` AND '"+edt+"' >= `"+targ+"` ";
			} else if( (edt == null || edt.isEmpty())) {
				fSQL = " AND '"+sdt+"' <= `"+targ+"` AND DATE_ADD('"+sdt+"',INTERVAL "+CampFormats._DAYS_IN_FUTURE_SEARCH_RANGE+" DAY) >= `"+targ+"` ";
			} else {
				fSQL = " AND '"+sdt+"' <= `"+targ+"` AND '"+edt+"' >= `"+targ+"` ";
			}
	
			String SQL = "SELECT * FROM " +table+ " WHERE `_object_type`='"+objectType+"' "+fSQL;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByTypeDateRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	@Override
	public LogEntryList loadByTypeTimestamp(String businessId,
			String timestamp, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByTypeTimestamp]";
			msg = "====[ logging dao call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = String.format(loadByTypeTimestampSQL,businessId, timestamp);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByTypeTimestamp completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	@Override
	public LogEntryList loadByTypeLogTimestamp(String businessId,
			String timestamp, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByTypeLogTimestamp]";
			msg = "====[ logging dao call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = String.format(loadByTypeLogTimestampSQL,businessId, timestamp);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByTypeLogTimestamp completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	@Override
	public LogEntryList loadByBusinessId(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByBusinessId]";
			msg = "====[ logging dao call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = String.format(loadByBusinessIdSQL,businessId);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByBusinessId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	@Override
	public LogEntryList loadByGroup(String businessId, String group,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByGroup]";
			msg = "====[ logging dao call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = String.format(loadByGroupSQL,businessId, group);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByGroup completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	@Override
	public LogEntryList loadByVersion(String businessId, String version,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByVersion]";
			msg = "====[ logging dao call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = String.format(loadByVersionSQL,businessId, version);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByVersion completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	@Override
	public LogEntryList loadByDate(String businessId, String date,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByDate]";
			msg = "====[ logging dao call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = String.format(loadByDateSQL,businessId, date);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByDate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	@Override
	public LogEntryList loadByDateRange(String businessId, String startDate,
			String endDate, RangeTarget target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByDateRange]";
			msg = "====[ logging dao call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			String targ = "";
			switch(target) {
			case LOG_TIMESTAMP:
				targ = "_log_timestamp";
				break;
			case OBJECT_TIMESTAMP:
				targ = "_timestamp";
				break;
			case OBJECT_DATE:
				targ = "_date";
				break;
			case OBJECT_END_OF_LIFE:
				targ = "_end_of_life";
				break;
			default:
				targ = "_date";
				break;
			}
			String sdt = startDate.substring(0,19);
			String edt = endDate.substring(0,19);
			String fSQL = "";
			if( (sdt == null || sdt.isEmpty())){
				fSQL = " AND DATE_SUB('"+edt+"',INTERVAL "+CampFormats._DAYS_IN_PAST_SEARCH_RANGE+" DAY) <= `"+targ+"` AND '"+edt+"' >= `"+targ+"` ";
			} else if( (edt == null || edt.isEmpty())) {
				fSQL = " AND '"+sdt+"' <= `"+targ+"` AND DATE_ADD('"+sdt+"',INTERVAL "+CampFormats._DAYS_IN_FUTURE_SEARCH_RANGE+" DAY) >= `"+targ+"` ";
			} else {
				fSQL = " AND '"+sdt+"' <= `"+targ+"` AND '"+edt+"' >= `"+targ+"` ";
			}
	
			String SQL = "SELECT * FROM " +table+ " WHERE `_object_business_id`='"+businessId+"' "+fSQL;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByDateRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	@Override
	public LogEntryList loadByTimestamp(String businessId, String timestamp,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByTimestamp]";
			msg = "====[ logging dao call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = String.format(loadByTimestampSQL,businessId, timestamp);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByTimestamp completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	@Override
	public LogEntryList loadByLogTimestamp(String businessId,
			String timestamp, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByLogTimestamp]";
			msg = "====[ logging dao call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = String.format(loadByLogTimestampSQL,businessId, timestamp);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByLogTimestamp completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	public <T extends IsObjectInstance<?>,E extends ArrayList<T>> String insertValues(E ol, boolean log) {
		boolean start = true;
		String values = "";
		for(IsObjectInstance<?> o:ol){
			if(!start) {
				values += ",";
			}else{
				start = false;
			}
			values += "("+insertValues(o,log)+")";
		}
		return values;
	}
	public <T extends IsObjectInstance<T>> String insertValues(IsObjectInstance<?> o, boolean log) {
		String values = 
				 "'"+o.id()+"'"
				+",'"+o.getClass().getSimpleName()+"'"
				+",'"+o.businessId()+"'"
				+",'"+o.businessKey()+"'"
				+",'"+o.history().id().id()+"'"
				+",'"+o.history().currentId().id()+"'"
				+",'"+o.history().initialId().id()+"'"
				+",'"+o.status().name()+"'"
				+",'"+o.group().name()+"'"
				+",'"+o.version().value()+"'"
				+",'"+Util.Time.timeStampString()+"'"
				+",'"+o.history().timestamp().toString()+"'"
				+",'"+o.history().date().toString()+"'"
				+",'"+o.history().endOfLife().toString()+"'"
				+",'"+o.toJson()+"'";
		return values;
	}

	public <T extends IsObjectInstance<T>,E extends ArrayList<T>> String insertListValues(E ol, boolean log) {
		String values = "";
		boolean start = true;
		for(T o:ol) {
			if(!start) {
				values += ",";
			} else {
				start = false;
			}
			values += "(" + insertValues(o,log)+")";
		}
		return values;
	}


	public int createTable(boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[createTable]";
			msg = "====[ creating required tables for the logging dao utility ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}

		Util.DB._createDatabases(log);

		CampInstanceDao.instance().createTable(log);
		
		Util.DB.dbActionType action = Util.DB.dbActionType.CREATE;
		if (log && Util._IN_PRODUCTION) {
			msg = "----[assembling '" + action.name().toLowerCase() + "' columns]----";
			LOG.info(String.format(fmt, _f, msg));
		}
		String colDef = Util.DB._columns(tabledef, action, log);
		String SQL = "CREATE TABLE IF NOT EXISTS " + table + " " + " ( " + colDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) { msg = "----[SQL : " + SQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

		Connection conn = null;
		Statement dbs = null;
		int retVal = 0;
		try {
			conn = Util.DB.__conn(log);

			dbs = conn.createStatement();
			retVal = dbs.executeUpdate(SQL);

		} catch (Exception e) {
			msg = "SQL Exception Happend!!! Failed to create logging utility tables.";
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

	public int clearTables(boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[clearTables]";
			msg = "====[ delete all table entries ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM "+table;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}

			retVal = dbs.executeUpdate(SQL);
			
			if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
			//throw e;
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[clearTables completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return retVal;
	}

	public <T extends IsObjectInstance<T>> LogEntry<T> rsToI(ResultSet rs, boolean log) throws SQLException {
		CampInstance history = new CampInstance(
				new InstanceId(rs.getString("_instance_id")),
				new InstanceId(rs.getString("_current_instance_id")),
				new InstanceId(rs.getString("_initial_instance_id"))
				);
		history.timestamp(rs.getTimestamp("_timestamp"));
		history.date(rs.getTimestamp("_date"));
		history.endOfLife(rs.getTimestamp("_end_of_life"));
		LogEntry<T> le = null;
		try {
			le = new LogEntry<T>(
			rs.getInt(tabledef[0][0]),
			rs.getInt("_object_id"),
			rs.getString("_object_type"),
			rs.getString("_object_business_id"),
			rs.getString("_object_businesskey"),
			new Group(rs.getString("_group_name")),
			new Version(rs.getString("_version_value")),
			history,
			rs.getTimestamp("_log_timestamp"),
			rs.getString("_object_json")
			);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return le;
	}

	@Override
	public LogEntryList loadByTypeEndOfLife(String objectType, String endOfLife, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByTypeEndOfLife]";
			msg = "====[ logging dao call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = String.format(loadByTypeEndOfLifeSQL,objectType, endOfLife);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByTypeEndOfLife completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

	@Override
	public LogEntryList loadByEndOfLife(String objectType, String endOfLife, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByEndOfLife]";
			msg = "====[ logging dao call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		LogEntryList lel = new LogEntryList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = String.format(loadByEndOfLifeSQL,objectType, endOfLife);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				lel.add(rsToI(rs,log));
			}
			retVal = lel.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByEndOfLife completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lel;
	}

}
