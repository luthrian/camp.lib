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
package com.camsolute.code.camp.lib.models.order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.data.CampSQL;
import com.camsolute.code.camp.lib.models.CampInstanceDao;
import com.camsolute.code.camp.lib.models.Model;
import com.camsolute.code.camp.lib.models.CampStatesInterface.IOAction;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessDao;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.models.process.ProductProcess;
import com.camsolute.code.camp.lib.models.product.Product;
import com.camsolute.code.camp.lib.utilities.Util;

public class OrderPositionDao implements OrderPositionDaoInterface{

	private static final Logger LOG = LogManager.getLogger(OrderPositionDao.class);
	private static String fmt = "[%15s] [%s]";

	public static String dbName = CampSQL.database[CampSQL._ORDER_DB_INDEX];

	public static String table = CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL.Order._ORDER_POSITION_TABLE_INDEX);

	public static String[][] tabledef = CampSQL.Order.order_position_table_definition;

	public static String updatestable = CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL.Order._ORDER_POSITION_UPDATES_TABLE_INDEX);
	
	public static String[][] updatestabledef = CampSQL.Order.order_position_updates_table_definition;

	public static String reftable = CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL.Order._ORDER_POSITION_REF_TABLE_INDEX);

	public static String[][] reftabledef = CampSQL.Order.order_position_ref_table_definition;

	public static String ohptable = CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL.Order._ORDER_POSITION_HAS_PROCESS_TABLE_INDEX);

	public static String[][] ohptabledef = CampSQL.Order.order_position_has_process_table_definition;


	private static OrderPositionDao instance = null;
	
	private OrderPositionDao(){
	}
	
	public static OrderPositionDao instance(){
		if(instance == null) {
			instance = new OrderPositionDao();
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

	public String reftable() {
		return reftable;
	}

	public String[][] reftabledef() {
		return reftabledef;
	}

	public String ohptable() {
		return ohptable;
	}

	public String[][] ohptabledef() {
		return ohptabledef;
	}


	@Override
	public OrderPosition loadById(int id, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadById]";
			msg = "====[ load an order position object instance by id  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPosition op = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+ table + " AS t, "+reftable+" AS rt, "
			    +CampInstanceDao.table+" AS ti WHERE "
					+ " t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND t.`"+tabledef[0][0]+"`="+id
					+ " AND rt.`"+reftabledef[0][0]+"`=ti.`_object_ref_id`"
					+ " AND ti.`_instance_id`=ti.`_current_instance_id`";

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			if (rs.next()) {
				op = rsToI(rs, log);
				op.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				op.states().ioAction(IOAction.LOAD);
			}
			retVal = 1;
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' updated model instance entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[loadById completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return op;
	}

	@Override
	public OrderPosition loadByBusinessId(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByBusinessId]";
			msg = "====[ load an order position object instances by businessId ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String[] bid = businessId.split(Util.DB._VS);
		if(bid.length <2 ) {
			if(log && !Util._IN_PRODUCTION){msg = "----[EXCEPTION: businessId has wrong format! MUST be <orderBusinessId>"+Util.DB._VS+"<businessId>]----";LOG.info(String.format(fmt, _f,msg));}
		}	
		OrderPosition op = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+ table + " AS t, "+reftable+" AS rt, "
			    +CampInstanceDao.table+" AS ti WHERE "
					+ " t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND t.`op_business_id`='"+bid[1]+"'"
					+ " AND t.`op_order_business_id`='"+bid[0]+"'"
					+ " AND rt.`"+reftabledef[0][0]+"`=ti.`_object_ref_id`"
					+ " AND ti.`_instance_id`=ti.`_current_instance_id`";

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			if (rs.next()) {
				op = rsToI(rs, log);
				op.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				op.states().ioAction(IOAction.LOAD);
			}
			retVal = 1;
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' updated model instance entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
		return op;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<OrderPosition>> E loadListByBusinessKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByBusinessKey]";
			msg = "====[ load a list of order position object instances that share a common businesskey]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPositionList pl = new OrderPositionList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+ table + " AS t, "+reftable+" AS rt, "
			    +CampInstanceDao.table+" AS ti WHERE "
					+ " t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND t.`op_businesskey`='"+businessKey+"'"
					+ " AND rt.`"+reftabledef[0][0]+"`=ti.`_object_ref_id`"
					+ " AND ti.`_instance_id`=ti.`_current_instance_id`"
					+ " ORDER BY t.`"+tabledef[0][0]+"` ASC";

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			while (rs.next()) {
				OrderPosition op = rsToI(rs, log);
				op.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				op.states().ioAction(IOAction.LOAD);
				pl.add(op);
			}
			
			if(log && !Util._IN_PRODUCTION) {retVal = pl.size();msg = "----[ '"+retVal+"' updated model instance entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[loadListByBusinessKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E) pl;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrderPositionList loadListByGroup(String group, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByGroup]";
			msg = "====[ load a list of persisted order position object instances that share the same group. ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPositionList ol = new OrderPositionList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, , "+reftable+" AS rt, "+CampInstanceDao.table+" AS i WHERE "
					+ " i.`_group_name`='"+group+"' "
					+ " AND i.`_object_id`=t.`"+tabledef[0][0]+"` "
					+ " AND i.`_object_business_id`=t.`order_number` "
					+ " AND rt.`"+reftabledef[0][0]+"`=ti.`_object_ref_id`"
					+ " AND i.`_instance_id`=i.`_current_instance_id` "
					+ " ORDER BY rt.`op_position`" 
					;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				OrderPosition o = rsToI(rs,log);
				o.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				o.states().ioAction(IOAction.LOAD);
				ol.add(o);
			}
			retVal = ol.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[loadListByGroup completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrderPositionList loadListByGroupVersion(String group, String version, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByKey]";
			msg = "====[ load a list of persisted order position object instances that share the same group and version aspects ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPositionList ol = new OrderPositionList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS i WHERE "
					+ " i.`_group_name`='"+group+"' "
					+ " AND i.`_version_value`='"+version+"' "
					+ " AND i.`_object_id`=t.`"+tabledef[0][0]+"` "
					+ " AND i.`_object_business_id`=t.`order_number` "
					+ " AND rt.`"+reftabledef[0][0]+"`=ti.`_object_ref_id`"
					+ " AND i.`_instance_id`=i.`_current_instance_id` "
					+ " ORDER BY rt.`op_position`" 
					;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				OrderPosition o = rsToI(rs,log);
				o.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				o.states().ioAction(IOAction.LOAD);
				ol.add(o);
			}
			retVal = ol.size();
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[loadListByGroupVersion completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}


	@Override
	public OrderPosition save(OrderPosition op, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[save]";
			msg = "====[ persist an order position object instance to the database ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		// we only save order position object that are ready to save
		if(op.states().notReadyToSave(op)) {
			return op; // skip this entry if not ready to save
		}
		
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + table + "( " + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( " + insertValues(op,log) + " )";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			
			rs = dbs.getGeneratedKeys();						
			if (rs.next()) {
				op.updateId(rs.getInt(tabledef[0][0]));
			} 
			rs.close();
			
			SQL = "INSERT INTO " + reftable + "( " + Util.DB._columns(reftabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( " + insertRefValues(op,log) + " )";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			
			rs = dbs.getGeneratedKeys();						
			if (rs.next()) {
				op.updateRefId(rs.getInt(reftabledef[0][0]));
			}
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
			op.history().stamptime();
			op.cleanStatus(op);
			CampInstanceDao.instance()._addInstance(op, false, log);
			
			op.states().ioAction(IOAction.SAVE);
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
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[save completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return op;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<OrderPosition>> E saveList(E pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveList]";
			msg = "====[ persist a list of order position object instances to the database ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		// we only save order position object that are ready to save
		OrderPositionList rpl = new OrderPositionList();
		for(OrderPosition op: pl) {
			if(op.states().notReadyToSave(op)) continue; // skip this entry if not ready to save
			rpl.add(op);
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + table + "( " + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertListValues(rpl,log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			
			rs = dbs.getGeneratedKeys();						
			int count = 0;
			while (rs.next()) {
				rpl.get(count).updateId(rs.getInt(tabledef[0][0]));
				count++;
			} 
			rs.close();
			
			SQL = "INSERT INTO " + reftable + "( " + Util.DB._columns(reftabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertListRefValues(rpl,log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			
			rs = dbs.getGeneratedKeys();						
			count = 0;
			while (rs.next()) {
				rpl.get(count).updateRefId(rs.getInt(reftabledef[0][0]));
				count++;
			}
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
			for(OrderPosition op: rpl) {
				op.history().stamptime();
				op.cleanStatus(op);
			}
			
			CampInstanceDao.instance()._addInstances(rpl, false, log);
			
			for(OrderPosition op : rpl) {
				op.states().ioAction(IOAction.SAVE);
			}
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
			msg = "====[saveList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E) rpl;
	}

	@Override
	public OrderPosition update(OrderPosition op, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[update]";
			msg = "====[ update a persisted order position object instance database entry  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		if(log && !Util._IN_PRODUCTION){msg = "----[CURRENT STATES: "+op.states().print()+"]----";LOG.info(String.format(fmt, _f,msg));}
		if(!op.states().isModified() || op.states().isNew()) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ERROR! Attempt to update unmodified object instance! ]----";LOG.info(String.format(fmt, _f,msg));}
			return null;
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "INSERT INTO " + reftable +" ( "+ Util.DB._columns(reftabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( "+insertRefValues(op, log)+" )";

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			
			rs = dbs.getGeneratedKeys();
			if (rs.next()) {
				op.updateRefId(rs.getInt(reftabledef[0][0]));
			}
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
//			op.history().stamptime();
//			op.cleanStatus(op);
//			op.history().updateInstance();// no check required since is update
			CampInstanceDao.instance()._addInstance(op, false, log);
			op.states().ioAction(IOAction.UPDATE);
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
			msg = "====[update completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return op;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<OrderPosition>> E updateList(E pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateList]";
			msg = "====[ update a list of persisted order position object instances ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPositionList rpl = new OrderPositionList();
		for(OrderPosition op: pl) {
			if(!op.states().isModified() || op.states().isNew()) continue;
			rpl.add(op);
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();

			
			String SQL = "INSERT INTO " + reftable +" ( "+ Util.DB._columns(reftabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES "+insertListRefValues(rpl, log);
				
			if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}

			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			
			rs = dbs.getGeneratedKeys();
			int ctr = 0;
			while (rs.next()) {
				rpl.get(ctr).updateRefId(rs.getInt(reftabledef[0][0]));
				ctr++;
			}
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}

//			for(OrderPosition op: rpl) {
//				op.history().stamptime();
//				op.cleanStatus(op);
//				op.history().updateInstance();// no check required 
//			}
			CampInstanceDao.instance()._addInstances(rpl, false, log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			for(OrderPosition op : pl) {
				op.states().ioAction(IOAction.UPDATE);
			}

		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		//return retVal;
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[updateList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E) rpl;
	}

	@Override
	public <E extends ArrayList<OrderPosition>> E loadUpdates(String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByKey]";
			msg = "====[ load a list of order position object current instances that were registered in the order position updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPositionList pl = new OrderPositionList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+ table + " AS t, "+reftable+" AS rt, "
			    +CampInstanceDao.table+" AS ti WHERE "
					+ " t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND rt.`"+reftabledef[0][0]+"`=ti.`_object_ref_id`"
					+ " AND ti.`_instance_id`=ti.`_current_instance_id`"
					+ " AND EXISTS "
					+ " (SELECT * FROM "+updatestable+" AS u WHERE "
					+ " u.`_oposu_businesskey`=t.`op_businesskey` "
					+ " AND u.`_oposu_target`='"+target+"' "
					+ " AND u.`_oposu_businesskey`='"+businessKey+"' "
					+ " AND u.`_oposu_business_id`=t.`op_business_id`"
					+ " AND u.`_oposu_order_business_id`=t.`op_order_business_id`)"
					+ " ORDER BY  t.`"+tabledef[0][0]+"`";

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			while (rs.next()) {
				OrderPosition op = rsToI(rs, log);
				op.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				op.states().ioAction(IOAction.LOAD);
				pl.add(op);
			}
			
			if(log && !Util._IN_PRODUCTION) {retVal = pl.size();msg = "----[ '"+retVal+"' updated model instance entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
		return (E) pl;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<OrderPosition>> E loadUpdatesByKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByKey]";
			msg = "====[ load a list of order position object current instances that were registered in the order position updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPositionList pl = new OrderPositionList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+ table + " AS t, "+reftable+" AS rt, "
			    +CampInstanceDao.table+" AS ti WHERE "
					+ " t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND rt.`"+reftabledef[0][0]+"`=ti.`_object_ref_id`"
					+ " AND ti.`_instance_id`=ti.`_current_instance_id`"
					+ " AND EXISTS "
					+ " (SELECT * FROM "+updatestable+" AS u WHERE "
					+ " u.`_oposu_businesskey`=t.`op_businesskey` "
					+ " AND u.`_oposu_businesskey`='"+businessKey+"' "
					+ " AND u.`_oposu_business_id`=t.`op_business_id`"
					+ " AND u.`_oposu_order_business_id`=t.`op_order_business_id`)"
					+ " ORDER BY  t.`"+tabledef[0][0]+"`";

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			while (rs.next()) {
				OrderPosition op = rsToI(rs, log);
				op.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				op.states().ioAction(IOAction.LOAD);
				pl.add(op);
			}
			
			if(log && !Util._IN_PRODUCTION) {retVal = pl.size();msg = "----[ '"+retVal+"' updated model instance entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
		return (E) pl;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<OrderPosition>> E loadUpdatesByTarget(String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByTarget]";
			msg = "====[ load a list of order position object current instances that were registered in the order position updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPositionList pl = new OrderPositionList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+ table + " AS t, "+reftable+" AS rt, "
			    +CampInstanceDao.table+" AS ti WHERE "
					+ " t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND rt.`"+reftabledef[0][0]+"`=ti.`_object_ref_id`"
					+ " AND ti.`_instance_id`=ti.`_current_instance_id`"
					+ " AND EXISTS "
					+ " (SELECT * FROM "+updatestable+" AS u WHERE "
					+ " u.`_oposu_businesskey`=t.`op_businesskey` "
					+ " AND u.`_oposu_target`='"+target+"' "
					+ " AND u.`_oposu_business_id`=t.`op_business_id`"
					+ " AND u.`_oposu_order_business_id`=t.`op_order_business_id`)"
					+ " ORDER BY  t.`"+tabledef[0][0]+"`";

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			while (rs.next()) {
				OrderPosition op = rsToI(rs, log);
				op.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				op.states().ioAction(IOAction.LOAD);
				pl.add(op);
			}
			
			if(log && !Util._IN_PRODUCTION) {retVal = pl.size();msg = "----[ '"+retVal+"' updated model instance entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
		return (E) pl;
	}

	@Override
	public OrderPosition loadUpdate(OrderPosition op, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdate]";
			msg = "====[ load a specific order position object instance if it is registered in the order position updates table this is not neccessarily the current instance ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+ table + " AS t, "+reftable+" AS rt, "
			    +CampInstanceDao.table+" AS ti WHERE "
					+ "t.`op_businesskey`='"+businessKey+"' "
					+ " AND t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND t.`"+tabledef[0][0]+"`="+op.id()
					+ " AND rt.`"+reftabledef[0][0]+"`=ti.`_object_ref_id`"
					+ " AND ti.`_instance_id`=ti.`_current_instance_id`"
					+ " AND EXISTS "
					+ " (SELECT * FROM "+updatestable+" AS u WHERE "
					+ " u.`_oposu_businesskey`=t.`op_businesskey` "
					+ " AND u.`_oposu_businesskey`='"+businessKey+"' "
					+ " AND u.`_oposu_target`='"+target+"' "
					+ " AND u.`_oposu_business_id`='"+op.onlyBusinessId()+"' "
					+ " AND u.`_oposu_order_business_id`='"+op.onlyOrderBusinessId()+"')";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			if (rs.next()) {
				op = rsToI(rs, log);
				op.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				op.states().ioAction(IOAction.LOAD);
				retVal = 1;
			}
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' updated model instance entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
		return op;			}

	@Override
	public int addToUpdates(OrderPosition op, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[ add order position object instance reference to the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "INSERT INTO " + updatestable + "( " + Util.DB._columns(updatestabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( " + insertUpdateValues(op,target) + " )";
			
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
	public <E extends ArrayList<OrderPosition>> int addToUpdates(E pl, String businessKey, String target,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[ add a list of order position object instances to the updates list ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
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
			msg = "====[ delete all updates table entries of registered order position object instances by their businesskey and target aspect values ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "DELETE FROM "+updatestable+" WHERE "
			+ "`_oposu_businesskey`='"+businessKey+"' AND `_oposu_target`='"+target+"'";
			
			
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
	public int deleteFromUpdatesByKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteAllFromUpdates]";
			msg = "====[ delete all updates table entries of registered order position object instances by their businesskey identifier aspect values ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "DELETE FROM "+updatestable+" WHERE "
			+ "`_oposu_businesskey`='"+businessKey+"'";
			
			
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
	public int deleteFromUpdatesByTarget(String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteAllFromUpdates]";
			msg = "====[ delete updates table entries of registered order position object instances by their target identifier aspect values ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "DELETE FROM "+updatestable+" WHERE "
			+ "`_oposu_target`='"+target+"'";
			
			
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
	public int deleteFromUpdates(String businessId, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[ delete all updates table entries of registered order position object instances by their order, businesskey and target aspect values ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String ids[] = businessId.split(Util.DB._VS);
		if(ids.length < 2) return 0;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "DELETE FROM "+updatestable+" WHERE "
			+ "`_oposu_business_id`='"+ids[1]+"'"
			+ " AND `_oposu_order_business_id`='"+ids[0]+"'"
			+ " AND `_oposu_businesskey`='"+businessKey+"' AND `_oposu_target`='"+target+"'";
			
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
	public <E extends ArrayList<OrderPosition>> int deleteFromUpdates(E pl, String businessKey,
			String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[ delete all updates table entries of a list of registered order position object instances by their name, businesskey and target aspect values ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			for(OrderPosition op: pl) {
				String SQL = "DELETE FROM "+updatestable+" WHERE "
						+ "`_oposu_business_id`='"+op.onlyBusinessId()+"'"
						+ " AND `_oposu_order_business_id`='"+op.onlyOrderBusinessId()+"'"
						+ " AND `_oposu_businesskey`='"+businessKey+"' AND `_oposu_target`='"+target+"'";
				
				if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
				
				dbs.addBatch(SQL);
			}
			retVal = Util.Math.addArray(dbs.executeBatch());
			
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
	public int addProcessReference(String businessId, String instanceId, String processKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addProcessReferences]";
			msg = "====[ register all associated processes in the reference database table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + ohptable + "( " + Util.DB._columns(ohptabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES '"+businessId+"','"+instanceId+"','"+processKey+"')" ;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[addProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}


	@Override
	public int addProcessReferences(String businessId, ProcessList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addProcessReferences]";
			msg = "====[ register all associated processes in the reference database table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
//		String ids[] = businessId.split(Util.DB._VS);
//		if(ids.length <2) return 0;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + ohptable + "( " + Util.DB._columns(ohptabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertProcessReferenceValues(businessId, pl,log) ;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[addProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int delProcessReference(String businessId, String instanceId, String processKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delProcessReference]";
			msg = "====[ deregister an associated process from the reference database table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM " + ohptable + " WHERE " 
			+ " `_ophp_business_id`='"+businessId+"' AND `_ophp_process_instance_id`='"+instanceId+"' AND `_ophp_process_key`='"+processKey+"'";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[delProcessReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int delAllProcessReferences(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delProcessReferences]";
			msg = "====[ deregister all associated processes in the reference database table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM " + ohptable + " WHERE " 
			+ " `_ophp_business_id`='"+businessId+"'";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[delProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int delProcessReferences(String businessId, ProcessList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delProcessReferences]";
			msg = "====[ deregister a list of associated processes in the reference database table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM " + ohptable + " WHERE " 
			+ " `_ophp_business_id`='"+businessId+"'";
			
			boolean start = true;
			for(Process<?>p:pl) {
				if(!start) {
					SQL += " OR";
				} else {
					SQL += " AND";
					start = false;
				}
				SQL += " (`_ophp_instance_id`='"+p.instanceId()+"' AND `_ophp_businesskey`='"+p.businessKey()+"')";
			}

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[delProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	public ProcessList loadProcessReferences(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadProcesses]";
			msg = "====[ load all persisted process object instances associated with the order position object instance ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProcessList pl = new ProcessList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+ProcessDao.table+" AS p, "+ohptable+" AS r WHERE "
					+ "p.`instance_id`=r.`_ophp_process_instance_id` "
					+ " AND r.`_ophp_business_id`='"+businessId+"'";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Process<Product> pr = ProcessDao.instance().rsToI(rs, log);
				pr.states().ioAction(IOAction.LOAD);
				pl.add(pr);
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
			msg = "====[loadProcesses completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public String insertValues(OrderPosition p, boolean log) {
		String values = "'"+p.onlyBusinessId()+"',"
				+ "'"+p.onlyOrderBusinessId()+"',"
				+ "'"+p.businessKey()+"',"
				+ "'"+p.group().name()+"',"
				+ "'"+p.version().value()+"'";
		return values;
	}

	public String insertRefValues(OrderPosition p, boolean log) {
		String values = p.position()+","
				+ p.quantity()+","
				+ p.productId()+","
				+ p.modelId()+","
				+ "'"+p.refBusinessKey()+"',"
				+ "'"+p.date().toString()+"'";
		return values;
	}

	@Override
	public <E extends ArrayList<OrderPosition>> String insertListValues(E pl, boolean log) {
		String values = "";
		boolean start = true;
		for(OrderPosition op: pl) {
			if(!start) {
				values += ",";
			} else {
				start = false;
			}
			values += "(" + insertValues(op,log) + ")";
		}
		return values;
	}

	public <E extends ArrayList<OrderPosition>> String insertListRefValues(E pl, boolean log) {
		String values = "";
		boolean start = true;
		for(OrderPosition op: pl) {
			if(!start) {
				values += ",";
			} else {
				start = false;
			}
			values += "(" + insertRefValues(op,log) + ")";
		}
		return values;
	}

	@Override
	public <E extends ArrayList<OrderPosition>> String insertUpdateListValues(E pl, String target) {
		String values = "";
		boolean start = true;
		for(OrderPosition op:pl) {
			if(!start) {
				values += ",";
			} else {
				start = false;
			}
			values += "(" + insertUpdateValues(op,target) + ")";
		}
		return values;
	}

	@Override
	public String insertUpdateValues(OrderPosition p, String target) {
		String values = "'"+p.onlyBusinessId()+"',"
				+ "'"+p.onlyOrderBusinessId()+"',"
				+ "'"+p.businessKey() +"',"
				+ "'"+target+"'";
		return values;
	}

	@Override
	public String formatUpdateSQL(String SQL, OrderPosition p, boolean log) {
		String values = String.format(SQL, 
				p.position(),
				p.quantity(),
				p.productId(),
				p.modelId(),
				p.refBusinessKey(),
				p.date(),
				p.getRefId());
		return values;
	}

	@Override
	public String insertProcessReferenceValues(String businessId, ProcessList pl, boolean log) {
		String values = "";
		boolean start = true;
		for(Process<?> pr:pl) {
			if(!start) {
				values += ",";
			} else {
				start = false;
			}
			values += "('"+businessId+"','"+pr.instanceId()+"','"+pr.businessKey()+"')";
		}
		return values;
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

		String rcolDef = Util.DB._columns(reftabledef, action, log);
		String rSQL = "CREATE TABLE IF NOT EXISTS " + reftable + " " + " ( " + rcolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) { msg = "----[SQL : " + rSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

		String pcolDef = Util.DB._columns(ohptabledef, action, log);
		String pSQL = "CREATE TABLE IF NOT EXISTS " + ohptable + " " + " ( " + pcolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) { msg = "----[SQL : " + rSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }


		Connection conn = null;
		Statement dbs = null;
		int retVal = 0;
		try {
			conn = Util.DB.__conn(log);

			dbs = conn.createStatement();
			dbs.addBatch(SQL);
			dbs.addBatch(uSQL);
			dbs.addBatch(rSQL);
			dbs.addBatch(pSQL);
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
			
			SQL = "DELETE FROM "+reftable;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}

			dbs.addBatch(SQL);
								
			SQL = "DELETE FROM "+ohptable;
			
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

	@Override
	public OrderPosition rsToI(ResultSet rs, boolean log) throws SQLException {
		int id = rs.getInt(tabledef[0][0]);
		String businessId = rs.getString("op_business_id");
		String orderBusinessId = rs.getString("op_order_business_id");
		int quantity = rs.getInt("op_quantity");
		int position = rs.getInt("op_position");
		String refBusinessKey = rs.getString("op_ref_businesskey");
		String businessKey = rs.getString("op_businesskey");
		String status = rs.getString("_status");
		int refId = rs.getInt(reftabledef[0][0]);
		OrderPosition op = new OrderPosition(id, businessId, orderBusinessId, position);
		op.updateRefId(refId);
		op.setQuantity(quantity);
		op.setRefBusinessKey(refBusinessKey);
		op.setBusinessKey(businessKey);
		op.setStatus(status);
		return op;
	}

	@Override
	public OrderPosition instanceLoad(String select, boolean primary, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[instanceLoad]";
			msg = "====[ load order position instance from SQL select fragment ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPosition o = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+ table + " AS t, "+reftable+" AS rt, "
			    +CampInstanceDao.table+" AS ci WHERE "
					+ " rt.`"+reftabledef[0][0]+"`=ci.`_object_ref_id`"
					+ select; 

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			if (rs.next()) {
				o = rsToI(rs, log);
				o.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				o.states().ioAction(IOAction.LOAD);
				retVal = 1;
			}
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' updated model instance entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[instanceLoad completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<OrderPosition>> E instanceListLoad(String select, boolean primary, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_instanceListLoad]";
			msg = "====[ load an order position object instance from SQL select fragment ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPositionList ol = new OrderPositionList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+ table + " AS t, "+reftable+" AS rt, "
			    +CampInstanceDao.table+" AS ci WHERE "
					+ " rt.`"+reftabledef[0][0]+"`=ci.`_object_ref_id`"
					+ select; 

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			while (rs.next()) {
				OrderPosition op = rsToI(rs, log);
				op.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				op.states().ioAction(IOAction.LOAD);
				ol.add(op);
			}
			
			if(log && !Util._IN_PRODUCTION) {retVal = ol.size();msg = "----[ '"+retVal+"' updated model instance entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[_instanceListLoad completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E) ol;
	}

	@Override
	public String dbName(boolean primary) {
		return dbName;
	}

	@Override
	public String table(boolean primary) {
		return table;
	}

	@Override
	public String[][] tabledef(boolean primary) {
		return tabledef;
	}

	@Override
	public String updatestable(boolean primary) {
		return updatestable;
	}

	@Override
	public String[][] updatestabledef(boolean primary) {
		return updatestabledef;
	}

	@Override
	public String businessIdColumn(boolean primary) {
		return "op_business_id";
	}

	@Override
	public OrderPosition loadFirst(String businessId) {
		return _loadFirst(businessId, !Util._IN_PRODUCTION);
	}
	public static OrderPosition _loadFirst(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadFirst]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPosition o = null;
		try {
			o = CampInstanceDao.instance()._loadFirst(businessId, OrderPositionDao.instance(), false,log);
		} catch (SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[SQL EXCEPTION! loadFirst FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadFirst completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public OrderPosition loadPrevious(OrderPosition orderPosition) {
		return _loadPrevious(orderPosition, !Util._IN_PRODUCTION);
	}
	public static OrderPosition _loadPrevious(OrderPosition orderPosition,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadPrevious]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPosition o = null;
		try {
			o = CampInstanceDao.instance()._loadPrevious(orderPosition, OrderPositionDao.instance(), false, log);
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[SQL EXCEPTION! _loadPrevious FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadPrevious completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public OrderPosition loadNext(OrderPosition orderPosition) {
		return _loadNext(orderPosition, !Util._IN_PRODUCTION);
	}
	public static OrderPosition _loadNext(OrderPosition orderPosition, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadNext]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPosition o = null;
		try {
			o = CampInstanceDao.instance()._loadNext(orderPosition, OrderPositionDao.instance(), false, log);
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[SQL EXCEPTION! _loadNext FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadNext completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public OrderPositionList loadDate(String businessId, String date) {
		return _loadDate(businessId, date, !Util._IN_PRODUCTION);
	}
	public static OrderPositionList _loadDate(String businessId, String date, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPositionList ol = null;
		try {
			ol = CampInstanceDao.instance()._loadDate(businessId, date, OrderPositionDao.instance(), false,log);
		} catch (SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[SQL EXCEPTION! _loadDate FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadDate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@Override
	public OrderPositionList loadDateRange(String businessId, String startDate, String endDate) {
		return _loadDateRange(businessId, startDate, endDate, !Util._IN_PRODUCTION);
	}
	public static OrderPositionList _loadDateRange(String businessId, String startDate, String endDate, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDateRange]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPositionList ol = null;
		try {
			ol = CampInstanceDao.instance()._loadDateRange(businessId, startDate, endDate, OrderPositionDao.instance(), false,log);
		} catch (SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[SQL EXCEPTION! _loadDateRange FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadDateRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@Override
	public OrderPositionList loadDate(String date) {
		return _loadDate(date,!Util._IN_PRODUCTION);
	}
	public static OrderPositionList _loadDate(String date,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPositionList ol = null;
		try {
			ol = CampInstanceDao.instance()._loadDate(date, OrderPositionDao.instance(), false,log);
		} catch (SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[SQL EXCEPTION! _loadDate FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadDate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@Override
	public OrderPositionList loadDateRange(String startDate, String endDate) {
		return _loadDateRange(startDate,endDate,!Util._IN_PRODUCTION);
	}
	public static OrderPositionList _loadDateRange(String startDate, String endDate, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDateRange]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPositionList ol = null;
		try {
			ol = CampInstanceDao.instance()._loadDateRange(startDate, endDate, OrderPositionDao.instance(), false,log);
		} catch (SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[SQL EXCEPTION! _loadDateRange FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadDateRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}


}

