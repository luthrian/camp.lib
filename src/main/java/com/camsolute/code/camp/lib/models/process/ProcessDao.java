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
package com.camsolute.code.camp.lib.models.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.data.CampSQL;
import com.camsolute.code.camp.lib.models.CampInstanceDao;
import com.camsolute.code.camp.lib.models.CampStatesInterface.IOAction;
import com.camsolute.code.camp.lib.models.process.Process.ProcessType;
import com.camsolute.code.camp.lib.utilities.Util;

public class ProcessDao implements ProcessDaoInterface {

	private static final Logger LOG = LogManager.getLogger(ProcessDao.class);
	private static String fmt = "[%15s] [%s]";
	
	public static String dbName = CampSQL.database[CampSQL._PROCESS_DB_INDEX];

	public static String table = CampSQL.prmTable(CampSQL._PROCESS_DB_INDEX, CampSQL.Process._PROCESS_TABLE_INDEX);

	public static String[][] tabledef = CampSQL.Process.process_table_definition;

	public static String updatestable = CampSQL.prmTable(CampSQL._PROCESS_DB_INDEX, CampSQL.Process._PROCESS_UPDATES_TABLE_INDEX);
	
	public static String[][] updatestabledef = CampSQL.Process.process_updates_table_definition;

	public static String otable = CampSQL.prmTable(CampSQL._PROCESS_DB_INDEX, CampSQL.Process._ORDER_PROCESS_TABLE_INDEX);

	public static String[][] otabledef = CampSQL.Process.process_table_definition;

	public static String oupdatestable = CampSQL.prmTable(CampSQL._PROCESS_DB_INDEX, CampSQL.Process._ORDER_PROCESS_UPDATES_TABLE_INDEX);
	
	public static String[][] oupdatestabledef = CampSQL.Process.order_process_updates_table_definition;

	public static String pdtable = CampSQL.prmTable(CampSQL._PROCESS_DB_INDEX, CampSQL.Process._PRODUCT_PROCESS_TABLE_INDEX);

	public static String[][] pdtabledef = CampSQL.Process.product_process_table_definition;

	public static String pdupdatestable = CampSQL.prmTable(CampSQL._PROCESS_DB_INDEX, CampSQL.Process._PRODUCT_PROCESS_UPDATES_TABLE_INDEX);
	
	public static String[][] pdupdatestabledef = CampSQL.Process.product_process_updates_table_definition;

	public static String ptable = CampSQL.prmTable(CampSQL._PROCESS_DB_INDEX, CampSQL.Process._PRODUCTION_PROCESS_TABLE_INDEX);

	public static String[][] ptabledef = CampSQL.Process.production_process_table_definition;

	public static String pupdatestable = CampSQL.prmTable(CampSQL._PROCESS_DB_INDEX, CampSQL.Process._PRODUCTION_PROCESS_UPDATES_TABLE_INDEX);
	
	public static String[][] pupdatestabledef = CampSQL.Process.production_process_updates_table_definition;

	public static String ctable = CampSQL.prmTable(CampSQL._PROCESS_DB_INDEX, CampSQL.Process._CUSTOMER_PROCESS_TABLE_INDEX);

	public static String[][] ctabledef = CampSQL.Process.customer_process_table_definition;

	public static String cupdatestable = CampSQL.prmTable(CampSQL._PROCESS_DB_INDEX, CampSQL.Process._CUSTOMER_PROCESS_UPDATES_TABLE_INDEX);
	
	public static String[][] cupdatestabledef = CampSQL.Process.customer_process_updates_table_definition;

	private static ProcessDao instance = null;
	
	private ProcessDao(){
	}
	
	public static ProcessDao instance(){
		if(instance == null) {
			instance = new ProcessDao();
		}
		return instance;
	}
	
	@Override
	public String dbName() {
		return dbName;
	}

	@Override
	public String table() {
		return table;
	}

	@Override
	public String[][] tabledef() {
		return tabledef;
	}

	@Override
	public String updatestable() {
		return updatestable;
	}

	@Override
	public String[][] updatestabledef() {
		return updatestabledef;
	}

	@Override
	public String table(ProcessType type) {
		switch(type){
		case customer_process:
		case customer_management_process:
			return ctable;
		case customer_order_process:
		case customer_order_management_process:
		case product_order_process:
		case product_order_management_process:
			return otable;
		case product_process:
		case product_management_process:
			return pdtable;
		case production_process:
		case production_management_process:
			return ptable;
		case order_support_process:			
			return table;
		default:
			break;
		}
		return table;
	}

	@Override
	public String[][] tabledef(ProcessType type) {
		switch(type){
		case customer_process:
		case customer_management_process:
			return ctabledef;
		case customer_order_process:
		case customer_order_management_process:
		case product_order_process:
		case product_order_management_process:
			return otabledef;
		case product_process:
		case product_management_process:
			return pdtabledef;
		case production_process:
		case production_management_process:
			return ptabledef;
		case order_support_process:			
		default:
			break;
		}
		return tabledef;
	}

	@Override
	public String updatestable(ProcessType type) {
		switch(type){
		case customer_process:
		case customer_management_process:
			return cupdatestable;
		case customer_order_process:
		case customer_order_management_process:
		case product_order_process:
		case product_order_management_process:
			return oupdatestable;
		case product_process:
		case product_management_process:
			return pdupdatestable;
		case production_process:
		case production_management_process:
			return pupdatestable;
		case order_support_process:			
		default:
			break;
		}
		return updatestable;
	}

	@Override
	public String[][] updatestabledef(ProcessType type) {
		switch(type){
		case customer_process:
		case customer_management_process:
			return cupdatestabledef;
		case customer_order_process:
		case customer_order_management_process:
		case product_order_process:
		case product_order_management_process:
			return oupdatestabledef;
		case product_process:
		case product_management_process:
			return pdupdatestabledef;
		case production_process:
		case production_management_process:
			return pupdatestabledef;
		case order_support_process:			
		default:
			break;
		}
		return updatestabledef;
	}


	@Override
	public <T extends Process<?, ?>> T loadById(int id, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadById]";
			msg = "====[ load process by id ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		T p = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * from "+table()+" WHERE `"+tabledef()[0][0]+"`="+id;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);		
			if (rs.next()) {		
				p = rsToI(rs,log);
				p.states().ioAction(IOAction.LOAD);
				retVal = 1;
			} else {
				if(log && !Util._IN_PRODUCTION){msg = "----[SQL ERROR! loadById FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			}
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[loadById completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return p;
	}

	@Override
	public <T extends Process<?, ?>> T loadByInstanceId(String instanceId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByInstanceId]";
			msg = "====[ load process by instance id ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		T p = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * from "+table()+" WHERE `instance_id`='"+instanceId+"'";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);		
			if (rs.next()) {		
				p = rsToI(rs,log);
				p.states().ioAction(IOAction.LOAD);
				retVal = 1;
			} else {
				if(log && !Util._IN_PRODUCTION){msg = "----[SQL ERROR! loadByInstanceId FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			}
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[loadByInstanceId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return p;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ProcessList> E loadListByKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByKey]";
			msg = "====[ load a list of process objects by their businesskey ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProcessList pl = new ProcessList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+table()+" WHERE `businesskey`='"+businessKey+"'";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Process<?,?> p = rsToI(rs,log);
				p.states().ioAction(IOAction.LOAD);
				pl.add(p);
			}
			retVal = pl.size();
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
			msg = "====[loadListByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E) pl;
	}

	@Override
	public <E extends ProcessList> E loadListByBusinessId(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByBusinessId]";
			msg = "====[ load a list of persisted process objects from the database by their businessId  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		@SuppressWarnings("unchecked")
		E pl = (E) new ProcessList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+table()+" WHERE `business_id`='"+businessId+"'";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Process<?,?> p = rsToI(rs,log);
				p.states().ioAction(IOAction.LOAD);
				pl.add(p);
			}
			retVal = pl.size();
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
			msg = "====[loadListByBusinessId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}
	
	@Override
	public Process<?, ?> save(Process<?,?> p, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[save]";
			msg = "====[ persist  a process object instance to the database ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "INSERT INTO " + table() + "( " + Util.DB._columns(tabledef(), Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( " + insertValues(p,log) + " )";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			rs = dbs.getGeneratedKeys();						
			if (rs.next()) {		
				p.updateId(rs.getInt(tabledef()[0][0]));
				p.states().ioAction(IOAction.SAVE);
			} else {
				if(log && !Util._IN_PRODUCTION){msg = "----[SQL ERROR! save FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			}
			
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
			msg = "====[save completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return p;
	}

	@Override
	public <E extends ProcessList> E saveList(E pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveList]";
			msg = "====[ persist a list of process instance objects to the database ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "INSERT INTO " + table + "( " + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertListValues(pl,log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			rs = dbs.getGeneratedKeys();
			int count = 0;
			while (rs.next()) {		
				pl.get(count).updateId(rs.getInt(tabledef()[0][0]));				
				pl.get(count).states().ioAction(IOAction.SAVE);
				count++;
			} 
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" saved ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[saveList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public <T extends Process<?, ?>> int update(T p, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[update]";
			msg = "====[ update the database entry of a persisited process instance object ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String fSQL = "UPDATE " + table() + " SET " + Util.DB._columns(tabledef(), Util.DB.dbActionType.UPDATE, log)
			+ " WHERE `"+tabledef()[0][0]+"`=%s";

			String SQL = formatUpdateSQL(fSQL,p,log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL);
			
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
			msg = "====[update completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public <E extends ProcessList> int updateList(E pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateList]";
			msg = "====[ update the database entries of a list of persisted process instance objects ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			for(Process<?, ?> p:pl) {
			
				String fSQL = "UPDATE " + table + " SET " + Util.DB._columns(tabledef, Util.DB.dbActionType.UPDATE, log)
				+ " WHERE `"+tabledef[0][0]+"`=%s";
				@SuppressWarnings("unchecked")
				String SQL = formatUpdateSQL(fSQL, p, log);
				
				dbs.addBatch(SQL);
				
				if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			}
			retVal = Util.Math.addArray(dbs.executeBatch());
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" updated ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[updateList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public <E extends ProcessList> E loadUpdates(String businessKey, String target,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdates]";
			msg = "====[ load a list of process instance objects that are registered in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		@SuppressWarnings("unchecked")
		E pl = (E) new ProcessList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * from "+table()+" AS t, "+updatestable()+" AS u WHERE "
					+ "u.`_target`='"+target+"'"
					+ " AND u.`_businesskey`='"+businessKey+"'"
					+ " AND u.`_instance_id`=t.`instance_id`"
					+ " AND u.`_business_id`=t.`business_id`"
					+ " AND u.`_businesskey`=t.`businesskey`";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {
				Process<?,?> p = rsToI(rs,log);
				p.states().ioAction(IOAction.LOAD);
				pl.add(p);
			}
			retVal = pl.size();
			
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
			msg = "====[loadUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public <E extends ProcessList> E loadUpdatesByKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByKey]";
			msg = "====[ load a list of process instance objects that are registered in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		@SuppressWarnings("unchecked")
		E pl = (E) new ProcessList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * from "+table()+" AS t, "+updatestable()+" AS u WHERE "
					+ " u.`_businesskey`='"+businessKey+"'"
					+ " AND u.`_instance_id`=t.`instance_id`"
					+ " AND u.`_business_id`=t.`business_id`"
					+ " AND u.`_businesskey`=t.`businesskey`";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);		
			while(rs.next()) {
				Process<?,?> p = rsToI(rs,log);
				p.states().ioAction(IOAction.LOAD);
				pl.add(p);
			}
			retVal = pl.size();
			
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
			msg = "====[loadUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public <E extends ProcessList> E loadUpdatesByTarget(String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByTarget]";
			msg = "====[ load a list of process instance objects that are registered in the updates table by target ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		@SuppressWarnings("unchecked")
		E pl = (E) new ProcessList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * from "+table()+" AS t, "+updatestable()+" AS u WHERE "
					+ "u.`_target`='"+target+"'"
					+ " AND u.`_instance_id`=t.`instance_id`"
					+ " AND u.`_business_id`=t.`business_id`"
					+ " AND u.`_businesskey`=t.`businesskey`";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			while(rs.next()) {
				Process<?,?> p = rsToI(rs,log);
				p.states().ioAction(IOAction.LOAD);
				pl.add(p);
			}
			retVal = pl.size();
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
			msg = "====[loadUpdatesByTarget completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public <T extends Process<?, ?>> T loadUpdate(String instanceId, String businessId, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdate]";
			msg = "====[ load a process instance object that is registered in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		T p = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * from "+table()+" AS t, "+updatestable()+" AS u WHERE "
					+ "u.`_target`='"+target+"'"
					+ " AND u.`_businesskey`='"+businessKey+"'"
					+ " AND u.`_instance_id`='"+instanceId+"'"
					+ " AND u.`_business_id`='"+businessId+"'"
					+ " AND u.`_instance_id`=t.`instance_id`"
					+ " AND u.`_business_id`=t.`business_id`"
					+ " AND u.`_businesskey`=t.`businesskey`";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			if(rs.next()) {
				p = (T) rsToI(rs, log);
				p.states().ioAction(IOAction.LOAD);
				retVal = 1;
			}
			
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
			msg = "====[loadUpdate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return p;
	}

	@Override
	public <T extends Process<?, ?>> T loadUpdate(T p, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdate]";
			msg = "====[ load a process instance object that is registered in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		T lp = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * from "+table()+" AS t, "+updatestable()+" AS u WHERE "
					+ "u.`_target`='"+target+"'"
					+ " AND u.`_businesskey`='"+businessKey+"'"
					+ " AND u.`_instance_id`='"+p.instanceId()+"'"
					+ " AND u.`_business_id`='"+p.onlyBusinessId()+"'"
					+ " AND u.`_instance_id`=t.`instance_id`"
					+ " AND u.`_business_id`=t.`business_id`"
					+ " AND u.`_businesskey`=t.`businesskey`";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			if(rs.next()) {
				lp = (T) rsToI(rs, log);
				lp.states().ioAction(IOAction.LOAD);
				retVal = 1;
			}
			
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
			msg = "====[loadUpdate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return lp;
	}

	@Override
	public <T extends Process<?, ?>> int addToUpdates(T p, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[ register a persisted process instance object in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);

			String SQL = "INSERT INTO " + updatestable + "( " + Util.DB._columns(updatestabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( " + insertUpdateValues(p,target) + " )";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL);		
			
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
			msg = "====[addToUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public <E extends ProcessList> int addToUpdates(E pl, String businessKey,
			String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[ register a list of persisted process instance objects in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "INSERT INTO " + updatestable + "( " + Util.DB._columns(updatestabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertUpdateListValues(pl,target);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL);
			
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
			msg = "====[addToUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteAllFromUpdates(String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteAllFromUpdates]";
			msg = "====[ deregister a list of process instance objects from the updates table by businessKey and target ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "DELETE FROM "+updatestable()+" WHERE `_businesskey`='"+businessKey+"' AND `_target`='"+target+"'";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL);
			
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
			msg = "====[deleteAllFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteFromUpdates(String instanceId, String businessId, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[ deregister a list of process instance objects from the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "DELETE FROM "+updatestable()+" WHERE "
					+ "`_businesskey`='"+businessKey+"'"
					+ " AND `_target`='"+target+"'"
					+ " AND `_business_id`='"+businessId+"'"
					+ " AND `_instance_id`='"+instanceId+"'";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL);
			
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
			msg = "====[deleteFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public <E extends ProcessList> int deleteFromUpdates(E pl, String target, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[deleteFromUpdates]";
				msg = "====[ deregister a list of processes from the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;
			int retVal = 0;
			try{
				conn = Util.DB.__conn(log);
				
				dbs = conn.createStatement();
				
				for(Process<?, ?> p: pl){
					String SQL = "DELETE FROM "+updatestable()+" WHERE "
							+ "`_businesskey`='"+p.businessKey()+"' "
							+ " AND `_target`='"+target+"'"
							+ " AND `_instance_id`='"+p.instanceId()+"'"
							+ " AND `_business_id`='"+p.onlyBusinessId()+"'";
					
					if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
					
					dbs.addBatch(SQL);
				}
				
				retVal = Util.Math.addArray(dbs.executeBatch());
				
				if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" deleted ]----";LOG.info(String.format(fmt,_f,msg));}
				
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
				msg = "====[deleteFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			return retVal;
	}

	@Override
	public String insertValues(Process<?,?> p, boolean log) {
		String values = "";
		values += "'"+p.instanceId()+"',";
		values += "'"+p.onlyBusinessId()+"',";
		values += "'"+p.businessKey()+"',"; 
		values += "'"+p.processName()+"',";
		values += "'"+p.definitionId()+"',";
		values += "'"+p.tenantId()+"',";
		values += "'"+p.caseInstanceId()+"',";
		values += p.ended()+",";
		values += p.suspended()+",";
		values += "'"+p.type().name()+"'";

		return values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ProcessList> String insertListValues(E pl, boolean log) {
		String values = "";
		boolean start = true;
		for(Process<?,?> p:pl){
			if(!start) {
				values += ",";
			}else{
				start = false;
			}
			values += "(";
			values += insertValues(p,log);
			values += ")";
		}
		
		return values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ProcessList> String insertUpdateListValues(E pl, String target) {
		String values = "";
		boolean start = true;
		for(Process<?,?> p:pl){
			if(!start) {
				values += ",";
			}else{
				start = false;
			}
			values += "(";
			values += insertUpdateValues(p,target);
			values += ")";
		}
		
		return values;
	}

	@Override
	public String insertUpdateValues(Process<?,?> p, String target) {
		String values = "";
		values += "'"+p.instanceId()+"',";
		values += "'"+p.onlyBusinessId()+"',";
		values += "'"+p.businessKey()+"',"; 
		values += "'"+target+"'";
		return values;
	}

	@Override
	public String formatUpdateSQL(String SQL, Process<?,?> p, boolean log) {
		String fSQL = String.format(SQL, 
				"'"+p.instanceId()+"'",
				"'"+p.onlyBusinessId()+"'",
				"'"+p.businessKey()+"'", 
				"'"+p.processName()+"'",
				"'"+p.definitionId()+"'",
				"'"+p.tenantId()+"'",
				"'"+p.caseInstanceId()+"'",
				p.ended(),
				p.suspended(),
				"'"+p.type().name()+"'",
				p.id());

		return fSQL;
	}

	@Override
	public int createTable(boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[createTable]";
			msg = "====[ creating required tables for model objects ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
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

		String ucolDef = Util.DB._columns(updatestabledef, action, log);
		String uSQL = "CREATE TABLE IF NOT EXISTS " + updatestable + " " + " ( " + ucolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) { msg = "----[UPDATES SQL : " + uSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

		String pcolDef = Util.DB._columns(ptabledef, action, log);
		String pSQL = "CREATE TABLE IF NOT EXISTS " + ptable + " " + " ( " + pcolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) { msg = "----[SQL : " + pSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

		String pucolDef = Util.DB._columns(pupdatestabledef, action, log);
		String puSQL = "CREATE TABLE IF NOT EXISTS " + pupdatestable + " " + " ( " + pucolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) { msg = "----[UPDATES SQL : " + puSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

		String ccolDef = Util.DB._columns(ctabledef, action, log);
		String cSQL = "CREATE TABLE IF NOT EXISTS " + ctable + " " + " ( " + ccolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) { msg = "----[SQL : " + cSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

		String cucolDef = Util.DB._columns(cupdatestabledef, action, log);
		String cuSQL = "CREATE TABLE IF NOT EXISTS " + cupdatestable + " " + " ( " + cucolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) { msg = "----[UPDATES SQL : " + cuSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

		String pdcolDef = Util.DB._columns(pdtabledef, action, log);
		String pdSQL = "CREATE TABLE IF NOT EXISTS " + pdtable + " " + " ( " + pdcolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) { msg = "----[SQL : " + pdSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

		String pducolDef = Util.DB._columns(pdupdatestabledef, action, log);
		String pduSQL = "CREATE TABLE IF NOT EXISTS " + pdupdatestable + " " + " ( " + pducolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) { msg = "----[UPDATES SQL : " + pduSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

		String ocolDef = Util.DB._columns(otabledef, action, log);
		String oSQL = "CREATE TABLE IF NOT EXISTS " + otable + " " + " ( " + ocolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) { msg = "----[SQL : " + oSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

		String oucolDef = Util.DB._columns(oupdatestabledef, action, log);
		String ouSQL = "CREATE TABLE IF NOT EXISTS " + oupdatestable + " " + " ( " + oucolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) { msg = "----[UPDATES SQL : " + ouSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

		Connection conn = null;
		Statement dbs = null;
		int retVal = 0;
		try {
			conn = Util.DB.__conn(log);

			dbs = conn.createStatement();
			dbs.addBatch(SQL);
			dbs.addBatch(uSQL);
			dbs.addBatch(pSQL);
			dbs.addBatch(puSQL);
			dbs.addBatch(pdSQL);
			dbs.addBatch(pduSQL);
			dbs.addBatch(oSQL);
			dbs.addBatch(ouSQL);
			dbs.addBatch(cSQL);
			dbs.addBatch(cuSQL);
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

	@Override
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

			dbs.addBatch(SQL);
			
			SQL  = "DELETE FROM "+updatestable;

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs.addBatch(SQL);
			
			SQL = "DELETE FROM "+ctable;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}

			dbs.addBatch(SQL);
			
			SQL  = "DELETE FROM "+cupdatestable;

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs.addBatch(SQL);
			
			SQL = "DELETE FROM "+ptable;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}

			dbs.addBatch(SQL);
			
			SQL  = "DELETE FROM "+pupdatestable;

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs.addBatch(SQL);
			
			SQL = "DELETE FROM "+pdtable;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}

			dbs.addBatch(SQL);
			
			SQL  = "DELETE FROM "+pdupdatestable;

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs.addBatch(SQL);
			
			SQL = "DELETE FROM "+otable;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}

			dbs.addBatch(SQL);
			
			SQL  = "DELETE FROM "+oupdatestable;

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs.addBatch(SQL);
			
				
			retVal = Util.Math.addArray(dbs.executeBatch());
			
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

	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Process<?, ?>> T rsToI(ResultSet rs, boolean log) throws SQLException {
		int id = rs.getInt(tabledef[0][0]);
		String instanceId = rs.getString("instance_id");
		String businessKey = rs.getString("businesskey");
		String processName = rs.getString("process_name");
		String definitionId = rs.getString("definition_id");
		String tenantId = rs.getString("tenant_id");
		String caseInstanceId = rs.getString("case_instance_id");
		boolean ended = rs.getBoolean("ended");
		boolean suspended = rs.getBoolean("suspended");
		
		String businessId = rs.getString("business_id");
		ProcessType type = ProcessType.valueOf(rs.getString("process_type"));
		switch(type){
		case customer_process:
		case customer_management_process:
			CustomerProcess cp = new CustomerProcess(id, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			cp.setBusinessId(businessId);
			return (T) cp;
		case customer_order_process:
		case customer_order_management_process:
		case product_order_process:
		case product_order_management_process:
		case order_support_process:			
			OrderProcess op = new OrderProcess(id, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			op.setBusinessId(businessId);
			return (T) op;
		case product_process:
		case product_management_process:
			ProductProcess pdp = new ProductProcess(id, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			pdp.setBusinessId(businessId);
			return (T) pdp;
		case production_process:
		case production_management_process:
			ProductionProcess pp = new ProductionProcess(id, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			pp.setBusinessId(businessId);
			return (T) pp;
		default:
			break;
		}
		return null;
	}
  
}
