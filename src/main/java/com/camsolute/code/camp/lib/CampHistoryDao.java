package com.camsolute.code.camp.lib;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.core.U;
import com.camsolute.code.camp.core.dao.CampSQL;
import com.camsolute.code.camp.core.dao.DBU;
import com.camsolute.code.camp.core.dao.DBU.dbActionType;
import com.camsolute.code.camp.core.interfaces.HasHistory;
import com.camsolute.code.camp.core.interfaces.HasStatus;
import com.camsolute.code.camp.core.interfaces.UpdateInstanceInterface;
import com.camsolute.code.camp.core.types.CampHistory;
import com.camsolute.code.camp.models.business.Order;

public abstract class CampHistoryDao<T extends HasStatus> implements CampHistoryDaoInterface<T> {
	public static final boolean _DEBUG = true;
	public static final boolean _IN_PRODUCTION = false;
	private static final Logger LOG = LogManager.getLogger(CampHistoryDao.class);
	private static String fmt = "[%15s] [%s]";
	
	public abstract String dbName();
	public abstract String table();
	public abstract String itable();
	public abstract String[][] tabledef();
	public abstract T rsToI(ResultSet rs,boolean log) throws SQLException;
	
	@Override
	public int addInstance(int instanceId, String businessId, T instance, CampHistory<?> history) throws SQLException {
		return _addInstance(instanceId,businessId,instance, history, true);
	}
	public int _addInstance(int instanceId, String businessId, T instance, CampHistory<?> history, boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_addInstance]";
			msg = "====[ add business instance history ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		instance.states().cleanStatus();
		
		history.stamptime();
		
		String values = instanceId+", "
				+ "'"+businessId+"', "
		        + "'"+history.currentInstanceId().id()+"', "
		        + "'"+history.initialInstanceId().id()+"', "
                + "'"+instance.states().status().name()+"', "
		        + "'"+history.timestamp()+"' ";

		int retVal = 0;
		
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		try{
			
			conn = DBU.__conn(log);
			
			String SQL = "INSERT INTO "+table()+"( "+ DBU._columns(tabledef(), dbActionType.INSERT,log)+" ) VALUES ( "+values+" )";
						  
			if(log && _DEBUG){ msg = "---- ---- SQL : "+SQL;LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			dbs.addBatch(SQL);
			
//			rs = dbs.getGeneratedKeys();
			
			if (!history.firstInstance()) {
		    	
				SQL = "UPDATE "+table()+" SET `_current_instance_id`='"+history.instanceId().id()+"' WHERE `_business_id`='"+businessId+"'";	
	
				if(log && _DEBUG) {msg = "-- --[ SQL: "+SQL+"]-- --";LOG.info(String.format(fmt,_f,msg));}
				
				dbs.addBatch(SQL);
				
		    } 
			
			retVal = dbs.executeBatch()[1];

		} catch (SQLException e) {
			if(log && _DEBUG){msg = "----[SQL EXCEPTION ROOT! _save FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
			throw e;
		} finally {
			if(log && _DEBUG){msg = "----[Releasing Connection]----";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
		}
		
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_addInstances completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return retVal;
	}
	
	@Override
	public int addInstances(int[] instanceId, String[] businessId, T[] instance, CampHistory<?>[] history) throws SQLException {
		return _addInstances(instanceId,businessId,instance,history, true);
	}
	public int _addInstances(int[] instanceId, String[] businessId, T[] instance, CampHistory<?>[] history, boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_addInstances]";
			msg = "====[ add business instances history ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		int count = 0;

		int retVal = 0;
		
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		try{
			
			conn = DBU.__conn(log);
			
			dbs = conn.createStatement();
			
			for(int id:instanceId) {
				CampHistory<?> h = history[count]; 
				
				String bId = businessId[count];
			
				instance[count].states().cleanStatus();
				
				h.stamptime();
				
				String values = id+", "
						+ "'"+bId+"', "
				        + "'"+h.currentInstanceId().id()+"', "
				        + "'"+h.initialInstanceId().id()+"', "
		                + "'"+instance[count].states().status().name()+"', "
				        + "'"+h.timestamp()+"' ";

				String SQL = "INSERT INTO "+table()+"( "+ DBU._columns(tabledef(), dbActionType.INSERT,log)+" ) VALUES ( "+values+" )";
						  
				if(log && _DEBUG){ msg = "---- ---- SQL : "+SQL;LOG.info(String.format(fmt,_f,msg));}
			
				dbs.addBatch(SQL);
			
//				rs = dbs.getGeneratedKeys();
				
				if (!h.firstInstance()) {
			    	
					SQL = "UPDATE "+table()+" SET `_current_instance_id`='"+h.instanceId().id()+"' WHERE `_business_id`='"+bId+"'";	
		
					if(log && _DEBUG) {msg = "-- --[ SQL: "+SQL+"]-- --";LOG.info(String.format(fmt,_f,msg));}
					
					dbs.addBatch(SQL);
					
			    } 
				count++;
			}
			retVal = U.addUp(dbs.executeBatch())/2;

		} catch (SQLException e) {
			if(log && _DEBUG){msg = "----[SQL EXCEPTION ROOT! _save FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
			throw e;
		} finally {
			if(log && _DEBUG){msg = "----[Releasing Connection]----";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
		}
		
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_addInstance completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return retVal;
	}
	
	@Override
	public T loadFirst(String businessId) throws SQLException {
		return _loadFirst(businessId,true);
	}
	public T _loadFirst(String businessId,boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_loadFirst]";
			msg = "====[ load first persisted instance '"+businessId+"' from database ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String SQL = String.format("SELECT * FROM "+itable()+" AS i, "+table()+" AS h WHERE `business_id` LIKE '%s%%' "+ "AND (`instance_id`=`current_instance_id`)", businessId);
		
		if(log && _DEBUG){ msg = "-- --[ SQL ]]"+SQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}
		
		T firstInstance = __load(SQL,log);
		
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadFirst completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return firstInstance;
	}
	
	@Override
	public T loadCurrent(String businessId) throws SQLException {
		return _loadCurrent(businessId,true);
	}
	public T _loadCurrent(String businessId,boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_loadCurrent]";
			msg = "====[ loading current order '"+businessId+"' from database ]====";LOG.info(String.format(fmt,_f,msg));
		}
		
		String SQL = "SELECT * FROM "+itable()+" AS i, "+table()+" AS h WHERE "
				+ "h.business_id='"+businessId+"' " 
				+ "AND i.id_=h.object_id"
				+ "AND h.`_instance_id`=h.`_current_instance_id`";
		
		T currentInstance = __load(SQL,log);

		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ current order loaded.]====";LOG.info(_f+msg+time);
		}
		
		return currentInstance;
	}

	@Override
	public T loadPrevious(String businessId, CampHistory<?> history) throws SQLException{
		return _loadPrevious(businessId,history,true);
	}
	public T _loadPrevious(String businessId, CampHistory<?> history,boolean log) throws SQLException{
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_loadPrevious]";
			msg = "====[ loading previous instance of '"+businessId+"' ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}

		String SQL = "SELECT * FROM "+itable()+" AS i, "+table()+" AS h WHERE "
				+ "h.business_id='"+businessId+"' " 
				+ "AND i.id_=h.object_id"
				+ "AND h.`_instance_id`='"+businessId+"'";
		
		if(log && _DEBUG){ msg = "-- --[ SQL ]]"+SQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}
		
		T previousInstance = __load(SQL,log);
		
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadPrevious completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return previousInstance;
	}
	
	@Override
	public T loadNext(String businessId, CampHistory<?> history) throws SQLException{
		return _loadNext(businessId,history,true);
	}
	public T _loadNext(String businessId, CampHistory<?> history,boolean log) throws SQLException{
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_loadNext]";
			msg = "====[ loading next instance of '"+businessId+"' ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String SQL = "SELECT * FROM "+itable()+" AS i, "+table()+" AS h WHERE "
				+ "h.`business_id`='"+businessId+"' " 
				+ "AND i.id_=h.object_id "
				+ "AND (`h._initial_instance_id`='"+history.initialInstanceId()+"') "
				+ "AND `h._instance_id`<>'"+history.initialInstanceId()+"'";
		
		if(log && _DEBUG){ msg = "-- --[ SQL ]]"+SQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}
		
		T nextInstance = __load(SQL,log);
		
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadNext completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return nextInstance;
	}
	
	protected T __load(String loadSQL,boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[__load]";
			msg = "====[ unpersisting object instance ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		T instance = null;

		Connection conn = null;
					
		ResultSet rs = null;
		
		try {
			conn = DBU.__conn(log);
			
			//TODO: build some kind of SQL generator ...
			String SQL = loadSQL;
			
			rs = conn.createStatement().executeQuery(SQL);
		
			if(log && _DEBUG){msg = "----[>> unpersisting]----";LOG.info(String.format(fmt, _f,msg));}

			boolean olog=log;log=false;
			instance = rsToI(rs,log);
			log=olog;
			
			if(log && _DEBUG){msg = "----[<< unpersisting]----";LOG.info(String.format(fmt, _f,msg));}	
		} catch (SQLException e) {
			if(log && _DEBUG){msg = "----[SQL EXCEPTION ROOT! __load FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
			throw e;
		} finally {
			if(log && _DEBUG){msg = "----[Releasing Connection]----";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseRS(rs, log);				
		}
		
		instance.states().loaded();
		
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[__load completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return instance;
	}

	@Override
	public int createTable(boolean checkDBExists){
		return _createTable(checkDBExists,true);
	}
	public int _createTable(boolean cde, boolean log) {
		log=false;
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_createTable]";
			msg = "====[ Creating history tables .... ]====";LOG.info(String.format(fmt,_f,msg));
		}
				
		// check that databases exist
		if(cde && !DBU._dbExists(dbName(),log)){
			DBU._createDatabases(log);
		}
		
		
		DBU.dbActionType action = DBU.dbActionType.CREATE;
		if(log && _DEBUG) {msg = "----[assembling '"+action.name().toLowerCase()+"' columns]----";LOG.info(String.format(fmt,_f,msg));}
		String colDef = DBU._columns(tabledef(), action, log);
		String SQL = "CREATE TABLE IF NOT EXISTS "+table()+" "
				    + " ( "+  colDef + ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if(log && _DEBUG){ msg = "----[SQL : "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
		
		Connection conn = null;
		Statement dbs = null;
		int retVal = 0;
		try {
			conn = DBU.__conn(log);
			
			dbs = conn.createStatement();
			retVal= dbs.executeUpdate(SQL);
			
		} catch (Exception e) {
			msg= "SQL Exception Happend!!!";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = "----[Releasing Connection]----";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
		}			
		
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_createTable completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return retVal;
	}

	
}
