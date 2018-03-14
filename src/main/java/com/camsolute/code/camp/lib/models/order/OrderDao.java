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
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.data.CampSQL;
import com.camsolute.code.camp.lib.models.CampInstanceDao;
import com.camsolute.code.camp.lib.models.CampStatesInterface.IOAction;
import com.camsolute.code.camp.lib.models.process.OrderProcess;
import com.camsolute.code.camp.lib.models.process.OrderProcessList;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessDao;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.models.product.Product;
import com.camsolute.code.camp.lib.models.product.ProductDao;
import com.camsolute.code.camp.lib.models.product.ProductList;
import com.camsolute.code.camp.lib.utilities.Util;

public class OrderDao implements OrderDaoInterface {

	private static final Logger LOG = LogManager.getLogger(OrderDao.class);
	private static String fmt = "[%15s] [%s]";
	
	public static String dbName = CampSQL.database[CampSQL._ORDER_DB_INDEX];

	public static String table = CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL.Order._ORDER_TABLE_INDEX);

	public static String[][] tabledef = CampSQL.Order.order_table_definition;

	public static String updatestable = CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL.Order._ORDER_UPDATES_TABLE_INDEX);
	
	public static String[][] updatestabledef = CampSQL.Order.order_updates_table_definition;

	public static String ohptable = CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL.Order._ORDER_HAS_PROCESS_TABLE_INDEX);

	public static String[][] ohptabledef = CampSQL.Order.order_has_process_table_definition;

	public static String ohoptable = CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL.Order._ORDER_HAS_ORDER_POSITION_TABLE_INDEX);

	public static String[][] ohoptabledef = CampSQL.Order.order_has_order_position_table_definition;

	public static String ohotable = CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL.Order._ORDER_HAS_ORDER_TABLE_INDEX);

	public static String[][] ohotabledef = CampSQL.Order.order_has_order_table_definition;

	private static OrderDao instance = null;
	
	private OrderDao(){
	}
	
	public static OrderDao instance(){
		if(instance == null) {
			instance = new OrderDao();
		}
		return instance;
	}

	@Override
	public String dbName() {
		return dbName;
	}

	@Override
	public String table() {
		// TODO Auto-generated method stub
		return table;
	}

	@Override
	public String[][] tabledef() {
		// TODO Auto-generated method stub
		return tabledef;
	}

	@Override
	public String updatestable() {
		// TODO Auto-generated method stub
		return updatestable;
	}

	@Override
	public String[][] updatestabledef() {
		// TODO Auto-generated method stub
		return updatestabledef;
	}

	@Override
	public Order loadById(int id, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadById]";
			msg = "====[ load a persisted order object instance from the database by id ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Order o= null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ti WHERE "
				+ "t.`"+tabledef[0][0]+"`="+id
				+ " AND t.`"+tabledef[0][0]+"`=ti.`_object_id`"
						+ " AND ti.`_instance_id`=ti.`_current_instance_id`";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			if (rs.next()) {		
				o = rsToI(rs,log);
				o.setHistory(CampInstanceDao.instance().rsToI(rs,log));
				o.states().ioAction(IOAction.LOAD);
				retVal =1;
			}
			
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
			msg = "====[loadById completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public Order loadByBusinessId(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByBusinessId]";
			msg = "====[ load a persisted order object instance from the database with businessId='"+businessId+"' ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Order o = null;
		int retVal = 0;
		try{
			o = CampInstanceDao.instance().loadCurrent(businessId, OrderDao.instance(),true);
			retVal = 1;
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} 
		o.states().ioAction(IOAction.LOAD);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByBusinessId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrderList loadListByBusinessKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByKey]";
			msg = "====[ load a list of persisted order object instances that share the same businessKey ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = new OrderList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS i WHERE "
					+ " t.`order_businesskey`='"+businessKey+"' "
					+ " AND i.`_object_id`=t.`"+tabledef[0][0]+"` "
					+ " AND i.`_object_business_id`=t.`order_number` "
					+ " AND i.`_instance_id`=i.`_current_instance_id` "
					+ " ORDER BY t.`order_number`, t.`order_date`" 
					;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Order o = rsToI(rs,log);
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
			msg = "====[loadListByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrderList loadListByGroup(String group, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByGroup]";
			msg = "====[ load a list of persisted order object instances that share the same group. ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = new OrderList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS i WHERE "
					+ " i.`_group_name`='"+group+"' "
					+ " AND i.`_object_id`=t.`"+tabledef[0][0]+"` "
					+ " AND i.`_object_business_id`=t.`order_number` "
					+ " AND i.`_instance_id`=i.`_current_instance_id` "
					+ " ORDER BY t.`order_number`, t.`order_date`" 
					;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Order o = rsToI(rs,log);
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
	public OrderList loadListByGroupVersion(String group, String version, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByGroupVersion]";
			msg = "====[ load a list of persisted order object instances that share the same businessKey ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = new OrderList();
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
					+ " AND i.`_instance_id`=i.`_current_instance_id` "
					+ " ORDER BY t.`order_number`, t.`order_date`" 
					;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Order o = rsToI(rs,log);
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
	public Order save(Order o, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[save]";
			msg = "====[ persist an order object instance to the database ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + table + "( " + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( " + insertValues(o,log) + " )";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			rs = dbs.getGeneratedKeys();						
			if (rs.next()) {		
				o.updateId(rs.getInt(tabledef[0][0]));
			}
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}

			o.history().timestamp();
//			o.cleanStatus(o);
			
			CampInstanceDao.instance()._addInstance(o, false, log);
			
			o.states().ioAction(IOAction.SAVE);
			
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
		return o;
	}

	@Override
	public <E extends ArrayList<Order>> E saveList(E ol, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveList]";
			msg = "====[ persist a list of order object instances to the database ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + table + "( " + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertListValues(ol,log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			rs = dbs.getGeneratedKeys();						
			int count = 0;
			while (rs.next()) {		
				ol.get(count).updateId(rs.getInt(tabledef[0][0]));
				count++;
			}

			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
			for(Order o: ol){
				o.history().stamptime();
//				o.cleanStatus(o);
			}
			retVal = CampInstanceDao.instance()._addInstances(ol, false, log);
			
			if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' history entr"+((retVal>1)?"ies":"y")+" added ]----"; LOG.info(String.format(fmt, _f, msg)); }
			
			for(Order o:ol){
				o.states().ioAction(IOAction.SAVE);
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
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@Override
	public Order update(Order o, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[update]";
			msg = "====[ update an order instance oject ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String fSQL = "UPDATE " + table + " SET " + Util.DB._columns(tabledef, Util.DB.dbActionType.UPDATE, log)
			+ " WHERE `"+tabledef[0][0]+"`=" + o.id();
			String SQL = formatUpdateSQL(fSQL, o, log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			retVal = dbs.executeUpdate(SQL);
			
			o.history().stamptime();
//			o.cleanStatus(o);
			o.history().updateInstance();
			CampInstanceDao.instance()._addInstance(o, false, log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" updated ]----";LOG.info(String.format(fmt,_f,msg));}
			
			o.states().ioAction(IOAction.UPDATE);
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
		return o;
	}

	@Override
	public Order updateAttribute(Order.UpdateAttribute type, String businessId, String newValue, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[update]";
			msg = "====[ update a specific order instance oject attribute]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		int retVal = 0;
		Order o = loadByBusinessId(businessId, log);
		if(o == null) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ERROR! updateAttribute FAILED. Could not load Order]----";LOG.info(String.format(fmt, _f,msg));}
			return null;
		}
		switch(type){
		case BUSINESSKEY:
			o.updateBusinessKey(newValue);
			break;
		case STATUS:
			o.updateStatus(Order.Status.valueOf(newValue));
			break;
		case BY_DATE:
			o.updateByDate(Util.Time.timestamp(newValue));
			break;
			default:
				break;
		}
		// we save this purposely
		o = save(o,log);
		
		o.history().stamptime();
		o.history().updateInstance();
			try{
			
			CampInstanceDao.instance()._addInstance(o, false, log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" updated ]----";LOG.info(String.format(fmt,_f,msg));}
			
			o.states().ioAction(IOAction.UPDATE);
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[update completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public <E extends ArrayList<Order>> E updateList(E ol, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateList]";
			msg = "====[ update the value aspects of a list of order instance objects ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			for(Order o: ol){
				String fSQL = "UPDATE " + table + " SET " + Util.DB._columns(tabledef, Util.DB.dbActionType.UPDATE, log)
				+ " WHERE `"+tabledef[0][0]+"`=" + o.id();
				String SQL = formatUpdateSQL(fSQL,o,log);
				
				if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
				dbs.addBatch(SQL);
			}
			
			retVal = Util.Math.addArray(dbs.executeBatch());
			
			for(Order o: ol){
				o.history().stamptime();
//				o.cleanStatus(o);
				o.history().updateInstance();
			}
			CampInstanceDao.instance()._addInstances(ol, false, log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
			for(Order o: ol){
				o.states().ioAction(IOAction.UPDATE);
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
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[updateList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Order>> E loadUpdates(String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdates]";
			msg = "====[ load a list of order instance objects that are registered in the updates table by target ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = new OrderList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ti WHERE "
					+ "t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND ti.`_instance_id`=ti.`_current_instance_id`"
					+ " AND EXISTS "
					+ "(SELECT * FROM "+updatestable+" AS u WHERE "
					+ " u.`_businesskey`=t.`order_businesskey` "
					+ " AND u.`_businesskey`='"+businessKey+"' "
					+ " AND u.`_target`='"+target+"')";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Order o = rsToI(rs,log);
				o.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				ol.add(o);
			}
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' updated order instance entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
		return (E) ol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Order>> E loadUpdatesByKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByKey]";
			msg = "====[ load a list of order instance objects that are registered in the updates table by key ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = new OrderList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ti WHERE "
					+ "t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND ti.`_instance_id`=ti.`_current_instance_id`"
					+ " AND EXISTS "
					+ "(SELECT * FROM "+updatestable+" AS u WHERE "
					+ " u.`_businesskey`=t.`order_businesskey` "
					+ " AND u.`_businesskey`='"+businessKey+"')";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Order o = rsToI(rs,log);
				o.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				ol.add(o);
			}
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' updated order instance entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
		return (E) ol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Order>> E loadUpdatesByTarget(String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByTarget]";
			msg = "====[ load a list of order instance objects that are registered in the updates table by target ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = new OrderList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ti WHERE "
					+ "t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND ti.`_instance_id`=ti.`_current_instance_id`"
					+ " AND EXISTS "
					+ "(SELECT * FROM "+updatestable+" AS u WHERE "
					+ " u.`_businesskey`=t.`order_businesskey` "
					+ " AND u.`_target`='"+target+"')";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Order o = rsToI(rs,log);
				o.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				ol.add(o);
			}
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' updated order instance entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
		return (E) ol;
	}

	@Override
	public Order loadUpdate(Order o, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdate]";
			msg = "====[ load an order instance object registered in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Order lo = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ti WHERE "
					+ "t.`order_businesskey`='"+o.businessKey()+"' "
					+ " AND t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND t.`"+tabledef[0][0]+"`="+o.id()
					+ " AND EXISTS "
					+ "(SELECT * FROM "+updatestable+" AS u WHERE "
					+ "u.`_business_id`='"+o.onlyBusinessId()+"'"
					+ " AND u.`_businesskey`='"+o.businessKey()+"'"
					+ " AND u.`_target`='"+target+"'"
					+ " AND u.`_business_id`=t.`order_number`"
					+ " AND u.`_businesskey`=t.`order_businesskey`)";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);
			if (rs.next()) {		
				lo = rsToI(rs,log);
				lo.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				retVal = 1;
			} else {
				if(log && !Util._IN_PRODUCTION){msg = "----[SQL ERROR! loadUpdate FAILED]----";LOG.info(String.format(fmt, _f,msg));}
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
		return lo;
	}

	@Override
	public int addToUpdates(Order o, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[ register an order instance object in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + updatestable + "( " + Util.DB._columns(updatestabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( " + insertUpdateValues(o,target) + " )";
			
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
			msg = "====[addToUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public <E extends ArrayList<Order>> int addToUpdates(E ol, String businessKey, String target,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[ register a list of order instance objects in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + updatestable + "( " + Util.DB._columns(updatestabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertUpdateListValues(ol,target);
			
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
			msg = "====[ deregister a list of order instance objects from the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM "+updatestable+" WHERE "
					+"`_businesskey`='"+businessKey+"'"
					+" AND `_target`='"+target+"'";
			
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
			msg = "====[ deregister a list of order instance objects from the updates table that share the common business key aspect]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM "+updatestable+" WHERE "
					+"`_businesskey`='"+businessKey+"'";
			
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
			msg = "====[ deregister a list of order instance objects from the updates table that share the common target identifier aspect ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM "+updatestable+" WHERE "
					+" AND `_target`='"+target+"'";
			
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
			msg = "====[ deregister an order instance object from the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM "+updatestable+" WHERE "
					+ "`_business_id`='"+businessId+"'"
					+ " AND `_businesskey`='"+businessKey+"'"
					+ " AND `_target`='"+target+"'";
			
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
			msg = "====[deleteFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public <E extends ArrayList<Order>> int deleteFromUpdates(E ol, String businessKey, String target,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[ deregister a list of order instance objects from the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			for(Order o:ol){
				String SQL = "DELETE FROM "+updatestable+" WHERE "
						+"`_business_id`='"+o.onlyBusinessId()+"' "
						+" AND `_businesskey`='"+o.businessKey()+"' "
						+" AND `_target`='"+target+"' ";
				
				dbs.addBatch(SQL);
				
				if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
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
	public String insertValues(Order o, boolean log) {
		String values = 
				 "'"+o.onlyBusinessId()+"'"
				+",'"+o.businessKey()+"'"
				+",'"+o.date().toString()+"'"
				+","+((o.byDate()!=null)?"'"+o.byDate().toString()+"'":"NULL");
		return values;
	}

	@Override
	public <E extends ArrayList<Order>> String insertListValues(E ol, boolean log) {
		String values = "";
		boolean start = true;
		for(Order o:ol) {
			if(!start) {
				values += ",";
			} else {
				start = false;
			}
			values += "(" + insertValues(o,log)+")";
		}
		return values;
	}

	@Override
	public <E extends ArrayList<Order>> String insertUpdateListValues(E ol, String target) {
		String values = "";
		boolean start = true;
		for(Order o:ol) {
			if(!start) {
				values += ",";
			} else {
				start = false;
			}
			values += "(" + insertUpdateValues(o,target)+")";
		}
		return values;
	}

	@Override
	public String insertUpdateValues(Order o, String target) {
		String values = "'"+o.businessKey()+"'"
				+",'"+o.onlyBusinessId()+"'"
				+",'"+target+"'"
				+",'"+Util.Time.timeStampString()+"'";
		return values;
	}

	@Override
	public String formatUpdateSQL(String SQL, Order o, boolean log) {
		String fSQL = String.format(SQL,
				"'"+o.onlyBusinessId()+"'",
				"'"+o.businessKey()+"'",
				"'"+o.date().toString()+"'",
				o.id());
		return fSQL;
	}

	public String insertOrderPositionReferenceValues(OrderPositionList opl, String orderBusinessId,boolean log) {
		String values = "";
		boolean start = true;
		for(OrderPosition op:opl) {
			if(!start) {
				values += ",";
			} else {
				start = false;
			}
			values += "('"+orderBusinessId+"','"+op.onlyBusinessId()+"')";
		}
		return values;
	}

	public String insertProcessReferenceValues(ProcessList pl, String orderBusinessId, boolean log) {
		String values = "";
		boolean start = true;
		for(Process<?> pr:pl){
			if(!pr.states().isModified()) continue;
			if(!start) {
				values += ",";
			} else {
				start = false;
			}
			values += "('"+orderBusinessId+"','"+pr.instanceId()+"','"+pr.businessKey()+"')";
			pr.states().setModified(false);
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

		String ohpcolDef = Util.DB._columns(ohptabledef, action, log);
		String ohpSQL = "CREATE TABLE IF NOT EXISTS " + ohptable + " " + " ( " + ohpcolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) { msg = "----[SQL : " + ohpSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

		String ohocolDef = Util.DB._columns(ohotabledef, action, log);
		String ohoSQL = "CREATE TABLE IF NOT EXISTS " + ohotable + " " + " ( " + ohocolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) { msg = "----[SQL : " + ohoSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

		String ohopcolDef = Util.DB._columns(ohoptabledef, action, log);
		String ohopSQL = "CREATE TABLE IF NOT EXISTS " + ohoptable + " " + " ( " + ohopcolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) { msg = "----[SQL : " + ohopSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

		Connection conn = null;
		Statement dbs = null;
		int retVal = 0;
		try {
			conn = Util.DB.__conn(log);

			dbs = conn.createStatement();
			retVal += dbs.executeUpdate(SQL);
			retVal += dbs.executeUpdate(uSQL);
			retVal += dbs.executeUpdate(ohpSQL);
			retVal += dbs.executeUpdate(ohoSQL);
			retVal += dbs.executeUpdate(ohopSQL);
//			dbs.addBatch(SQL);
//			dbs.addBatch(uSQL);
//			dbs.addBatch(ohpSQL);
//			dbs.addBatch(ohoSQL);
//			retVal = Util.Math.addArray(dbs.executeBatch());

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
			
			SQL = "DELETE FROM "+ohotable;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}

			dbs.addBatch(SQL);
			
			SQL = "DELETE FROM "+ohptable;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}

			dbs.addBatch(SQL);
			
			SQL = "DELETE FROM "+ohoptable;
			
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
	public Order rsToI(ResultSet rs, boolean log) throws SQLException {
		int id = rs.getInt(tabledef[0][0]);
		String businessId = rs.getString("order_number");
		String businessKey = rs.getString("order_businesskey");
		Timestamp date = rs.getTimestamp("order_date");
		String status = rs.getString("_status");
		Order o = new Order(businessId,businessKey,date);
		o.updateId(id);
		o.setStatus(status);
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProcessList loadProcessReferences(String orderBusinessId ,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadProcesses]";
			msg = "====[ load all processes associated with the current order object instance ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderProcessList opl = new OrderProcessList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+ohptable+" AS o, "+ ProcessDao.table+" AS p WHERE "
					+ " o.`_ohp_business_id`='"+orderBusinessId+"' "
					+ " AND o.`_ohp_process_key`=p.`process_instance_id` "
					+ " AND o.`_ohp_process_key`=p.`businesskey` "
					+ " ORDER BY p.`process_type`";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			
			if (rs.next()) {		
				OrderProcess op = ProcessDao.instance().rsToI(rs, log);
				op.states().ioAction(IOAction.LOAD);
				op.states().setModified(false); // ensure that we don't get re-registered
				opl.add(op);
			}
			retVal = opl.size();
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
		return opl;
	}

	@Override
	public int addProcessReference(String orderBusinessId, String instanceId, String processKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addProcessReference]";
			msg = "====[ register an associated process instance in the reference table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + ohptable + "( " + Util.DB._columns(ohptabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( '"+orderBusinessId+"','"+instanceId+"','"+processKey+"' )";
			
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
			msg = "====[addProcessReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}
	@Override
	public int addProcessReferences(String orderBusinessId, ProcessList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addProcessReferences]";
			msg = "====[ register all associated process instances in the reference table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + ohptable + "( " + Util.DB._columns(ohptabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES "+insertProcessReferenceValues(pl,orderBusinessId,log);
				
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}

			retVal = dbs.executeUpdate(SQL);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" registered ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
	public int delProcessReference(String orderBusinessId, String instanceId, String processKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delProcessReference]";
			msg = "====[ deregister an associated process instance from the reference table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM "+ohptable+ " WHERE "
					+ "`_ohp_business_id`='"+orderBusinessId+"' "
					+ " AND `_ohp_process_instance_id`='"+instanceId+"' "
							+ " AND `_ohp_businesskey`='"+processKey+"'";
			
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
	public int delAllProcessReferences(String orderBusinessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delProcessReferences]";
			msg = "====[ deregister all associated process instances from the reference table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			String SQL = "DELETE FROM "+ohptable+ " WHERE "
						+ "`_ohp_business_id`='"+orderBusinessId+"'";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}

			retVal = dbs.executeUpdate(SQL);
			
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
			msg = "====[delProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int delProcessReferences(String orderBusinessId, ProcessList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delProcessReferences]";
			msg = "====[ deregister a list of associated process instances from the reference table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			String SQL = "DELETE FROM "+ohptable+ " WHERE "
						+ "`_ohp_business_id`='"+orderBusinessId+"'";

			boolean start = true;
			for(Process<?>p:pl) {
				if(!start) {
					SQL += " OR";
				} else {
					SQL += " AND";
					start = false;
				}
				SQL += " (`_ohp_instance_id`='"+p.instanceId()+"' AND `_ohp_businesskey`='"+p.businessKey()+"')";
			}

			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}

			retVal = dbs.executeUpdate(SQL);
			
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
			msg = "====[delProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	public int addOrderPositionReference(String orderBusinessId, String orderPositionBusinessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addOrderPositionReference]";
			msg = "====[ register an associated process instance in the reference table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + ohptable + "( " + Util.DB._columns(ohptabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( '"+orderBusinessId+"','"+orderPositionBusinessId+"' )";;
			
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
			msg = "====[addOrderPositionReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}
	
	@Override
	public int addOrderPositionReferences(String orderBusinessId, OrderPositionList opl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addOrderPositionReferences]";
			msg = "====[ register all associated order position object instances in the reference database table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + ohptable + "( " + Util.DB._columns(ohptabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertOrderPositionReferenceValues(opl,orderBusinessId,log) ;
			
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
			msg = "====[addOrderPositionReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int delOrderPositionReference(String orderBusinessId, String orderPositionBuisinessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delOrderPositionReference]";
			msg = "====[ deregister an associated order position from the reference database table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM " + ohptable + " WHERE " 
			+ " `_ohop_order_business_id_`='"+orderBusinessId+"'"
			+ " AND `_ohop_order_position_business_id`='"+orderPositionBuisinessId+"' ";
			
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
			msg = "====[delOrderPositionReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int delOrderPositionReferences(String orderBusinessId, OrderPositionList opl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delOrderPositionReferences]";
			msg = "====[ deregister all associated orderpositions in the reference database table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			for(OrderPosition op: opl) {
				String SQL = "DELETE FROM " + ohoptable + " WHERE " 
				+ " `_ohop_order_business_id_`='"+orderBusinessId+"' "
				+ " AND `_ohop_order_position_business_id`='"+op.onlyBusinessId()+"'";
				
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
			msg = "====[delOrderPositionReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public OrderPositionList loadOrderPositions(String orderBusinessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadOrderPositions]";
			msg = "====[ load all persisted order position object instances registered in the reference table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPositionList ol = new OrderPositionList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+ OrderPositionDao.table + " AS t"
					+ ", "+OrderPositionDao.reftable+" AS rt"
					+ ", "+CampInstanceDao.table+" AS ti"
					+ ", "+ohotable+" AS r WHERE "
					+ " t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND t.`op_business_id`=r.`_ohop_order_position_business_id`"
					+ " AND r.`_ohop_order_business_id_`='"+orderBusinessId+"'"
					+ " AND rt.`"+OrderPositionDao.reftabledef[0][0]+"`=ti.`_object_ref_id`"
					+ " AND ti.`_instance_id`=ti.`_current_instance_id`";
//			String SQL = "SELECT * FROM "+OrderPositionDao.table+" AS op"
//					+ ", "+ohotable+" AS r WHERE "
//					+ "op.`op_business_id`=r.`_ohop_order_position_business_id` "
//					+ " AND r.`_ohop_order_business_id_`='"+o.onlyBusinessId()+"'";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				OrderPosition op = OrderPositionDao.instance().rsToI(rs, log);
				op.states().ioAction(IOAction.LOAD);
				ol.add(op);
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
			msg = "====[loadOrderPositions completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@Override
	public Order instanceLoad(String select, boolean primary, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[instanceLoad]";
			msg = "====[ load an Order object instance from SQL select fragment ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Order o = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ci WHERE "
					+ "t.`order_number`=ci.`_object_business_id`"
					+ select;
		
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			if (rs.next()) {		
				o = rsToI(rs, log);
				o.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				o.states().ioAction(IOAction.LOAD);
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
			msg = "====[instanceLoad completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Order>> E instanceListLoad(String select, boolean primary, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[instanceListLoad]";
			msg = "====[ load a list of product object instances from SQL select fragment ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = new OrderList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ci WHERE "
					+ "t.`order_number`=ci.`_object_business_id`"
					+ select;
		
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Order o = rsToI(rs, log);
				o.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				o.states().ioAction(IOAction.LOAD);
				ol.add(o);
			}
			retVal = ol.size();
			
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
			msg = "====[instanceListLoad completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
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
		return "order_number";
	}


	@Override
	public Order loadFirst(String businessId) {
		return _loadFirst(businessId, !Util._IN_PRODUCTION);
	}
	public static Order _loadFirst(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadFirst]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Order o = null;
		try {
			o = CampInstanceDao.instance()._loadFirst(businessId, OrderDao.instance(), false,log);
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
	public Order loadPrevious(Order order) {
		return _loadPrevious(order, !Util._IN_PRODUCTION);
	}
	public static Order _loadPrevious(Order order,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadPrevious]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Order o = null;
		try {
			o = CampInstanceDao.instance()._loadPrevious(order, OrderDao.instance(), false, log);
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
	public Order loadNext(Order order) {
		return _loadNext(order, !Util._IN_PRODUCTION);
	}
	public static Order _loadNext(Order order, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadNext]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Order o = null;
		try {
			o = CampInstanceDao.instance()._loadNext(order, OrderDao.instance(), false, log);
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
	public OrderList loadDate(String businessId, String date) {
		return _loadDate(businessId, date, !Util._IN_PRODUCTION);
	}
	public static OrderList _loadDate(String businessId, String date, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = null;
		try {
			ol = CampInstanceDao.instance()._loadDate(businessId, date, OrderDao.instance(), false,log);
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
	public OrderList loadDateRange(String businessId, String startDate, String endDate) {
		return _loadDateRange(businessId, startDate, endDate, !Util._IN_PRODUCTION);
	}
	public static OrderList _loadDateRange(String businessId, String startDate, String endDate, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDateRange]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = null;
		try {
			ol = CampInstanceDao.instance()._loadDateRange(businessId, startDate, endDate, OrderDao.instance(), false,log);
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
	public OrderList loadDate(String date) {
		return _loadDate(date,!Util._IN_PRODUCTION);
	}
	public static OrderList _loadDate(String date,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = null;
		try {
			ol = CampInstanceDao.instance()._loadDate(date, OrderDao.instance(), false,log);
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
	public OrderList loadDateRange(String startDate, String endDate) {
		return _loadDateRange(startDate,endDate,!Util._IN_PRODUCTION);
	}
	public static OrderList _loadDateRange(String startDate, String endDate, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDateRange]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = null;
		try {
			ol = CampInstanceDao.instance()._loadDateRange(startDate, endDate, OrderDao.instance(), false,log);
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

