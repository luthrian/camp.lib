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
package com.camsolute.code.camp.lib.models.product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.data.CampSQL;
import com.camsolute.code.camp.lib.models.AttributeDao;
import com.camsolute.code.camp.lib.models.AttributeMap;
import com.camsolute.code.camp.lib.models.CampInstanceDao;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Model;
import com.camsolute.code.camp.lib.models.ModelDao;
import com.camsolute.code.camp.lib.models.ModelList;
import com.camsolute.code.camp.lib.models.Version;
import com.camsolute.code.camp.lib.models.CampStatesInterface.IOAction;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessDao;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.models.process.ProductProcess;
import com.camsolute.code.camp.lib.models.product.Product.Status;
import com.camsolute.code.camp.lib.utilities.Util;

public class ProductDao implements ProductDaoInterface {

	private static final Logger	LOG	= LogManager.getLogger(ProductDao.class);
	private static String				fmt	= "[%15s] [%s]";

	public static String dbName = CampSQL.database[CampSQL._PRODUCT_DB_INDEX];

	public static String table = CampSQL.pmTable(CampSQL._PRODUCT_DB_INDEX, CampSQL.Product._PRODUCT_TABLE_INDEX);

	public static String[][] tabledef = CampSQL.Product.product_table_definition;

	public static String updatestable = CampSQL.pmTable(CampSQL._PRODUCT_DB_INDEX,
			CampSQL.Product._PRODUCT_UPDATES_TABLE_INDEX);

	public static String[][] updatestabledef = CampSQL.Product.product_updates_table_definition;

	public static String paupdatestable = CampSQL.pmTable(CampSQL._PRODUCT_DB_INDEX,
			CampSQL.Product._PRODUCT_ATTRIBUTE_UPDATES_TABLE_INDEX);

	public static String[][] paupdatestabledef = CampSQL.Product.product_attribute_updates_table_definition;

	public static String phmtable = CampSQL.pmTable(CampSQL._PRODUCT_DB_INDEX,
			CampSQL.Product._PRODUCT_HAS_MODEL_TABLE_INDEX);

	public static String[][] phmtabledef = CampSQL.Product.product_has_model_table_definition;

	public static String phptable = CampSQL.pmTable(CampSQL._PRODUCT_DB_INDEX,
			CampSQL.Product._PRODUCT_HAS_PROCESS_TABLE_INDEX);

	public static String[][] phptabledef = CampSQL.Product.product_has_process_table_definition;

	private static ProductDao instance = null;
	
	private ProductDao(){
	}
	
	public static ProductDao instance(){
		if(instance == null) {
			instance = new ProductDao();
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
	public Product loadById(int id, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadById]";
			msg = "====[ load a persisted product object instance from the database ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Product p = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ci WHERE "
				+ "t.`"+tabledef[0][0]+"`="+id
				+ " AND ci.`_instance_id`=ci.`_current_instance_id`"
				+ " AND t.`"+tabledef[0][0]+"`=ci.`_object_id`";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			if (rs.next()) {		
				p = rsToI(rs,log);
				p.setHistory(CampInstanceDao.instance().rsToI(rs,log));
				p.states().ioAction(IOAction.LOAD);
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
		return p;
	}

	@Override
	public Product loadByBusinessId(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByBusinessId]";
			msg = "====[ load a persisted product object instance from the database by business id ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Product p = null;
		int retVal = 0;
		try{
			p = CampInstanceDao.instance().loadCurrent(businessId, ProductDao.instance(),true);
			retVal = 1;
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		} 
		p.states().ioAction(IOAction.LOAD);		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByBusinessId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return p;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductList loadListByBusinessKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByBusinessKey]";
			msg = "====[ load a list of persisted product object instances that share a common business key ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProductList pl = new ProductList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ci WHERE "
					+ "t.`product_businesskey`='"+businessKey+"'"
					+ " AND ci.`_instance_id`=ci.`_current_instance_id`"
					+ " AND t.`"+tabledef[0][0]+"`=ci.`_object_id`"
					+ " ORDER BY t.`"+tabledef[0][0]+"` ASC ";
//					+ ", t.`product_name` ASC "
//					+ ",  t.`product_date` ASC ";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Product p = rsToI(rs, log);
				p.setHistory(CampInstanceDao.instance().rsToI(rs, log));
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
		//return retVal;
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByBusinessKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public Product save(Product p, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[save]";
			msg = "====[ persist a product object instance to the database ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + table + "( " + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( " + insertValues(p,log) + " )";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			rs = dbs.getGeneratedKeys();						
			if (rs.next()) {		
				p.updateId(rs.getInt(tabledef[0][0]));
			}
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}

			p.history().timestamp();
			
			CampInstanceDao.instance()._addInstance(p, false, log);
			
			p.states().ioAction(IOAction.SAVE);
			p.states().setModified(false);
			
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

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Product>> E saveList(E pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveList]";
			msg = "====[ persist a list of product object instances to the database ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + table + "( " + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertListValues(pl,log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			rs = dbs.getGeneratedKeys();						
			int count = 0;
			while (rs.next()) {		
				pl.get(count).updateId(rs.getInt(tabledef[0][0]));
				count++;
			}

			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
			for(Product p: pl){
				p.history().stamptime();
			}
			retVal = CampInstanceDao.instance()._addInstances(pl, false, log);
			
			if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' history entr"+((retVal>1)?"ies":"y")+" added ]----"; LOG.info(String.format(fmt, _f, msg)); }
			
			for(Product p:pl){
				p.states().ioAction(IOAction.SAVE);
				p.states().setModified(false);
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
		return pl;
	}

	@Override
	public Product update(Product p, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[update]";
			msg = "====[ update the database entries of the persisted value aspects of a product object instance  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String fSQL = "UPDATE " + table + " SET " + Util.DB._columns(tabledef, Util.DB.dbActionType.UPDATE, log)
			+ " WHERE `"+tabledef[0][0]+"`=%s";
			String SQL = formatUpdateSQL(fSQL, p, log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			retVal = dbs.executeUpdate(SQL);
			
			p.history().stamptime();
			p.history().updateInstance();
			CampInstanceDao.instance()._addInstance(p, false, log);
			retVal = 1;
			
//			addProcessReferences(p,log);
//			p = updateAttributes(p,log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" updated ]----";LOG.info(String.format(fmt,_f,msg));}
			
			p.states().ioAction(IOAction.UPDATE);
			p.states().setModified(false); 
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
		return p;
	}

	@Override
	public <E extends ArrayList<Product>> E updateList(E pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateList]";
			msg = "====[ update the persisted value apects of a list of product object instances ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			for(Product p: pl){
				String fSQL = "UPDATE " + table + " SET " + Util.DB._columns(tabledef, Util.DB.dbActionType.UPDATE, log)
				+ " WHERE `"+tabledef[0][0]+"`=" + p.id();
				String SQL = formatUpdateSQL(fSQL,p,log);
				
				if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
				dbs.addBatch(SQL);
			}
			
			retVal = Util.Math.addArray(dbs.executeBatch());
			
			for(Product p: pl){
				p.history().stamptime();
				p.history().updateInstance();
				retVal += 1;
			}
			CampInstanceDao.instance()._addInstances(pl, false, log);
			
//			addProcessReferences((ProductList)pl,log);
//			
//			updateAttributes((ProductList)pl,log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
			for(Product p: pl){
				p.states().ioAction(IOAction.UPDATE);
				p.states().setModified(false);
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
		return pl;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductList loadUpdates(String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdates]";
			msg = "====[ load a list of product object instances registered in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProductList pl = new ProductList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ci WHERE "
					+ "t.`"+tabledef[0][0]+"`=ci.`_object_id`"
					+ " AND ci.`_instance_id`=ci.`_current_instance_id`"
					+ " AND EXISTS "
					+ "(SELECT * FROM "+updatestable+" AS u WHERE "
					+ " u.`_product_businesskey`=t.`product_businesskey` "
					+ " AND u.`_product_businesskey`='"+businessKey+"' "
					+ " AND u.`_product_target`='"+target+"')";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Product p = rsToI(rs,log);
				p.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				pl.add(p);
			}
			
//			pl = loadProcesses((ProductList)pl,log);
//			pl = loadAttributes((ProductList)pl,log);
			
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
		return pl;
	}

	@Override
	public <E extends ArrayList<Product>> E loadUpdatesByKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByKey]";
			msg = "====[ load a list of product object instances that are registered in the updates table under a common business key ('"+businessKey+"') ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProductList pl = new ProductList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ci WHERE "
					+ "t.`"+tabledef[0][0]+"`=ci.`_object_id`"
					+ " AND ci.`_instance_id`=ci.`_current_instance_id`"
					+ " AND EXISTS "
					+ "(SELECT * FROM "+updatestable+" AS u WHERE "
					+ " u.`_product_businesskey`=t.`product_businesskey` "
					+ " AND u.`_product_businesskey`='"+businessKey+"')";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Product p = rsToI(rs,log);
				p.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				pl.add(p);
			}
			
//			pl = loadProcesses(pl,log);
//			pl = loadAttributes(pl,log);
			
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
			msg = "====[loadUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E) pl;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Product>> E loadUpdatesByTarget(String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByTarget]";
			msg = "====[ load a list of product object instances registered in the updates table under the common target('"+target+"') ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProductList pl = new ProductList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ci WHERE "
					+ "t.`"+tabledef[0][0]+"`=ci.`_object_id`"
					+ " AND ci.`_instance_id`=ci.`_current_instance_id`"
					+ " AND EXISTS "
					+ "(SELECT * FROM "+updatestable+" AS u WHERE "
					+ " u.`_product_businesskey`=t.`product_businesskey` "
					+ " AND u.`_product_target`='"+target+"')";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Product p = rsToI(rs,log);
				p.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				pl.add(p);
			}
			
//			pl = loadProcesses(pl,log);
//			pl = loadAttributes(pl,log);
			
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
		return (E) pl;
	}

	@Override
	public Product loadUpdate(Product p, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdate]";
			msg = "====[ load a product object instance registered in the udpates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Product lp = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ci WHERE "
					+ "t.`product_businesskey`='"+p.businessKey()+"' "
					+ " AND t.`"+tabledef[0][0]+"`=ci.`_object_id`"
					+ " AND t.`"+tabledef[0][0]+"`="+p.id()
					+ " AND EXISTS "
					+ "(SELECT * FROM "+updatestable+" AS u WHERE "
					+ "u.`_product_name`='"+p.onlyBusinessId()+"'"
					+ " AND u.`_product_businesskey`='"+p.businessKey()+"'"
					+ " AND u.`_product_target`='"+target+"'"
					+ " AND u.`_product_name`=t.`product_name`"
					+ " AND u.`_product_businesskey`=t.`product_businesskey`)";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);
			if (rs.next()) {		
				lp = rsToI(rs,log);
				lp.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				retVal = 1;
			} else {
				if(log && !Util._IN_PRODUCTION){msg = "----[SQL ERROR! loadUpdate FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			}
			
//			loadProcesses(lp,log);
//			loadAttributes(lp,log);
			
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
	public int addToUpdates(Product p, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[ register a product object instance in the udpates tables ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + updatestable + "( " + Util.DB._columns(updatestabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( " + insertUpdateValues(p,target) + " )";
			
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
	public <E extends ArrayList<Product>> int addToUpdates(E pl, String businessKey, String target,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[ register a list of product object instances in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + updatestable + "( " + Util.DB._columns(updatestabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertUpdateListValues(pl,target);
			
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
			msg = "====[ deregister a list of product object instances from the updates table that share common business key and target values ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM "+updatestable+" WHERE "
					+"`_product_businesskey`='"+businessKey+"'"
					+" AND `_product_target`='"+target+"'";
			
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
			msg = "====[ deregister a product object instance from the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM "+updatestable+" WHERE "
					+ "`_product_name`='"+businessId+"'"
					+ " AND `_product_businesskey`='"+businessKey+"'"
					+ " AND `_product_target`='"+target+"'";
			
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
	public <E extends ArrayList<Product>> int deleteFromUpdates(E pl, String businessKey, String target,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[ deregister a list of product object instances from the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			for(Product p:pl){
				String SQL = "DELETE FROM "+updatestable+" WHERE "
						+"`_product_name`='"+p.onlyBusinessId()+"' "
						+" AND `_product_businesskey`='"+p.businessKey()+"' "
						+" AND `_product_target`='"+target+"' ";
				
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
	public String insertValues(Product p, boolean log) {
		String values = 
		  "'"+p.name()+"',"
		+ "'"+p.businessKey()+"',"
		+ "'"+p.date().toString()+"',"
		+ "'"+p.group().name()+"',"
		+ "'"+p.version().value()+"'";
//		+ "'"+p.status().name()+"'";
		return values;
	}

	@Override
	public <E extends ArrayList<Product>> String insertListValues(E pl, boolean log) {
		String values = "";
		boolean start = true;
		for(Product p:pl){
			if(!start){
				values += ",";
			} else {
				start = false;
			}
			values += "("+insertValues(p,log)+")";
		}
		return values;
	}

	@Override
	public <E extends ArrayList<Product>> String insertUpdateListValues(E pl, String target) {
		String values = "";
		boolean start = true;
		for(Product p:pl){
			if(!start){
				values += ",";
			} else {
				start = false;
			}
			values += "("+insertUpdateValues(p,target)+")";
		}
		return values;
	}

	@Override
	public String insertUpdateValues(Product p, String target) {
		String values = 
				  "'"+p.onlyBusinessId()+"',"
				+ p.modelId()+","
				+ "'"+p.businessKey()+"',"
				+ "'"+target+"',"
				+ "'"+Util.Time.timeStampString()+"'";
		return values;
	}

	@Override
	public String formatUpdateSQL(String SQL, Product p, boolean log) {
		String fSQL = String.format(SQL, 
			  "'"+p.name()+"'",
			"'"+p.businessKey()+"'",
			"'"+p.date()+"'",
			"'"+p.group().name()+"'",
			"'"+p.version().value()+"'",
//			"'"+p.status().name()+"'",
			p.id());
		return fSQL;
	}

	@Override
	public int createTable(boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if (log && !Util._IN_PRODUCTION) {
			_f = "[createTable]";
			msg = "====[ creating required tables for model objects ]====";
			LOG.traceEntry(String.format(fmt, (_f + ">>>>>>>>>").toUpperCase(), msg));
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
		if (log && !Util._IN_PRODUCTION) {
			msg = "----[SQL : " + SQL + "]----";
			LOG.info(String.format(fmt, _f, msg));
		}

		String ucolDef = Util.DB._columns(updatestabledef, action, log);
		String uSQL = "CREATE TABLE IF NOT EXISTS " + updatestable + " " + " ( " + ucolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) {
			msg = "----[UPDATES SQL : " + uSQL + "]----";
			LOG.info(String.format(fmt, _f, msg));
		}

		String pcolDef = Util.DB._columns(paupdatestabledef, action, log);
		String pSQL = "CREATE TABLE IF NOT EXISTS " + paupdatestable + " " + " ( " + pcolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) {
			msg = "----[SQL : " + pSQL + "]----";
			LOG.info(String.format(fmt, _f, msg));
		}

		String phpcolDef = Util.DB._columns(phptabledef, action, log);
		String phpSQL = "CREATE TABLE IF NOT EXISTS " + phptable + " " + " ( " + phpcolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) {
			msg = "----[SQL : " + phpSQL + "]----";
			LOG.info(String.format(fmt, _f, msg));
		}

		String phmcolDef = Util.DB._columns(phmtabledef, action, log);
		String phmSQL = "CREATE TABLE IF NOT EXISTS " + phmtable + " " + " ( " + phmcolDef
				+ ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if (log && !Util._IN_PRODUCTION) {
			msg = "----[SQL : " + phmSQL + "]----";
			LOG.info(String.format(fmt, _f, msg));
		}

		Connection conn = null;
		Statement dbs = null;
		int retVal = 0;
		try {
			conn = Util.DB.__conn(log);

			dbs = conn.createStatement();
			 retVal += dbs.executeUpdate(SQL);
			 retVal += dbs.executeUpdate(uSQL);
			 retVal += dbs.executeUpdate(pSQL);
			 retVal += dbs.executeUpdate(phmSQL);
			 retVal += dbs.executeUpdate(phpSQL);
//			dbs.addBatch(SQL);
//			dbs.addBatch(uSQL);
//			dbs.addBatch(pSQL);
//			dbs.addBatch(phmSQL);
//			dbs.addBatch(phpSQL);
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
		if (log && !Util._IN_PRODUCTION) {
			_f = "[clearTables]";
			msg = "====[ delete all table entries ]====";
			LOG.traceEntry(String.format(fmt, (_f + ">>>>>>>>>").toUpperCase(), msg));
		}

		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try {
			conn = Util.DB.__conn(log);

			dbs = conn.createStatement();

			String SQL = "DELETE FROM " + table;

			if (log && !Util._IN_PRODUCTION) {
				msg = "----[ SQL: " + SQL + "]----";
				LOG.info(String.format(fmt, _f, msg));
			}

			dbs.addBatch(SQL);

			SQL = "DELETE FROM " + updatestable;

			if (log && !Util._IN_PRODUCTION) {
				msg = "----[ SQL: " + SQL + "]----";
				LOG.info(String.format(fmt, _f, msg));
			}

			dbs.addBatch(SQL);

			SQL = "DELETE FROM " + paupdatestable;

			if (log && !Util._IN_PRODUCTION) {
				msg = "----[ SQL: " + SQL + "]----";
				LOG.info(String.format(fmt, _f, msg));
			}

			dbs.addBatch(SQL);

			SQL = "DELETE FROM " + phmtable;

			if (log && !Util._IN_PRODUCTION) {
				msg = "----[ SQL: " + SQL + "]----";
				LOG.info(String.format(fmt, _f, msg));
			}

			dbs.addBatch(SQL);

			SQL = "DELETE FROM " + phptable;

			if (log && !Util._IN_PRODUCTION) {
				msg = "----[ SQL: " + SQL + "]----";
				LOG.info(String.format(fmt, _f, msg));
			}

			dbs.addBatch(SQL);
			retVal = Util.Math.addArray(dbs.executeBatch());

			if (log && !Util._IN_PRODUCTION) {
				msg = "----[ '" + retVal + "' entr" + ((retVal > 1) ? "ies" : "y") + " deleted ]----";
				LOG.info(String.format(fmt, _f, msg));
			}

		} catch (SQLException e) {
			if (log && !Util._IN_PRODUCTION) {
				msg = "----[ SQLException! database transaction failed.]----";
				LOG.info(String.format(fmt, _f, msg));
			}
			e.printStackTrace();
			// throw e;
		} finally {
			if (log && !Util._IN_PRODUCTION) {
				msg = "----[ releasing connection]----";
				LOG.info(String.format(fmt, _f, msg));
			}
			Util.DB.__release(conn, log);
			Util.DB._releaseRS(rs, log);
			Util.DB._releaseStatement(dbs, log);
		}
		if (log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
			msg = "====[clearTables completed.]====";
			LOG.info(String.format(fmt, ("<<<<<<<<<" + _f).toUpperCase(), msg + time));
		}

		return retVal;
	}

	@Override
	public Product rsToI(ResultSet rs, boolean log) throws SQLException {
		int id = rs.getInt(tabledef[0][0]);
		String name = rs.getString("product_name");
		String businessKey = rs.getString("product_businesskey");
		Timestamp date = Util.Time.timestamp(rs.getString("product_date"));
		Group group = new Group(rs.getString("product_group"));
		Version version = new Version(rs.getString("product_version"));
//		Product.Status status = Status.valueOf(rs.getString("product_status"));
		Product.Status status = Status.valueOf(rs.getString("_status"));
		Product p = new Product(id, name, businessKey, group, version, date);
		p.setStatus(status);
		return p;
	}

	public int addProcessReference(String businessId, String instanceId, String processKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addProcessReference]";
			msg = "====[ register an associated process in the reference database table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + table + "( " + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( '"+businessId+"','"+instanceId+"','"+processKey+"')";;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			retVal = dbs.executeUpdate(SQL);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" associated ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
	public int addProcessReferences(String businessId, ProcessList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addProcessReferences]";
			msg = "====[ register all associated processes in the reference database table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		if(pl.size() <1) return 0;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + phptable + "( " + Util.DB._columns(phptabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertProcessReferenceValues(businessId,pl,log);
			
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

	@SuppressWarnings("unchecked")
	@Override
	public ProcessList loadProcessReferences(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadProcesses]";
			msg = "====[ load all persisted process object instances associated with the product object instance ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProcessList pl = new ProcessList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+ProcessDao.table+" AS p, "+phptable+" AS r WHERE "
					+ "p.`instance_id`=r.`_php_process_instance_id` "
					+ " AND r.`_php_product_business_id`='"+businessId+"'";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Process<Product,ProductProcess> pr = ProcessDao.instance().rsToI(rs, log);
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
			
			String SQL = "DELETE FROM " + phptable + " WHERE " 
			+ " `_php_product_business_id`='"+businessId+"' AND `_php_process_instance_id`='"+instanceId+"' AND `_php_processkey`='"+processKey+"'";
			
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
	public int delProcessReferences(String businessId, boolean log) {
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
			
			String SQL = "DELETE FROM " + phptable + " WHERE " 
			+ " `_php_product_business_id`='"+businessId+"'";
			
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
	public int addModelReference(String businessId, int modelId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addModelReferenc]";
			msg = "====[ register an associated mode in the reference database table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + table + "( " + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( '"+businessId+"',"+modelId+" )";;
			
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
			msg = "====[addModelReferenc completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}
	
	@Override
	public int addModelReferences(String businessId, ModelList ml, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addModelReferences]";
			msg = "====[ register all associated models in the reference database table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + phmtable + "( " + Util.DB._columns(phmtabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertModelReferenceValues(businessId, ml,log);
			
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
			msg = "====[addModelReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}


	@Override
	public int delModelReference(String businessId, int modelId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delModelReference]";
			msg = "====[ deregister an associated model from the model reference database table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM " + phmtable + " WHERE " 
			+ " `_product_business_id`='"+businessId+"' AND `_model_id`='"+modelId+"' ";
			
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
			msg = "====[delModelReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int delModelReferences(String businessId, ModelList ml, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delModelReferences]";
			msg = "====[ deregister all associated models in the model reference database table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM " + phmtable + " WHERE " 
			+ " `_product_business_id`='"+businessId+"'";
			
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
			msg = "====[delModelReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public ModelList loadModels(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadModels]";
			msg = "====[ load all persisted models object instances associated with the product object instance ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ModelList ml = new ModelList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+ModelDao.table+" AS m, "+CampInstanceDao.table+" AS mi, "+phmtable+" AS r WHERE "
					+ "m.`"+ModelDao.tabledef[0][0]+"`=r.`_model_id` "
					+ " AND mi.`_object_id`=m.`"+ModelDao.tabledef[0][0]+"` "
					+ " AND mi.`_instance_id`=mi.`_current_instance_id` "
					+ " AND r.`_product_business_id`='"+businessId+"'";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Model m = ModelDao.instance().rsToI(rs, log);
				m.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				m.states().ioAction(IOAction.LOAD);
				ml.add(m);
			}
			retVal = ml.size();
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
			msg = "====[loadModels completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ml;
	}

	@Override
	public Product saveModels(Product p, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveModels]";
			msg = "====[ persist all models associated with a product ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ModelList ml = p.models();
		ml = ModelDao.instance().saveList(ml, log);
		p.setModels(ml);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveModels completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return p;
	}

	@Override
	public ProductList saveModels(ProductList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveModels]";
			msg = "====[ persist all models associated with a list of product object instances ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		for(Product p: pl) {
			p = saveModels(p,log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveModels completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public Product saveAttributes(Product p, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveAttributes]";
			msg = "====[ persist all product attributes associated with a product ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		AttributeMap a = p.attributes();
		a = (AttributeMap) AttributeDao.instance().saveByObjectId(p.id(),a);
//		a = (AttributeMap) AttributeDao.instance().save(p.id(), a);
		p.setAttributes(a);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveAttributes completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return p;
	}

	@Override
	public ProductList saveAttributes(ProductList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveAttributes]";
			msg = "====[ persist all product attributes associated with a list of product object instances ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		for(Product p: pl) {
			p = saveAttributes(p,log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveAttributes completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public Product updateAttributes(Product p, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveAttributes]";
			msg = "====[ update the definition and value aspects of all product attributes associated with a product ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		AttributeMap a = p.attributes();
		int retVal = AttributeDao._updateByObjectId(p.id(), a,log);

		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" updated ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		p.setAttributes(a);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveAttributes completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return p;
	}


	@Override
	public ProductList updateAttributes(ProductList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateAttributes]";
			msg = "====[ update the value aspects of all product attributes associated with a of product attributes ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		for(Product p: pl) {
			p = updateAttributes(p,log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[updateAttributes completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public Product loadAttributes(Product p, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadAttributes]";
			msg = "====[ load all product attributes assoiated with a product ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		AttributeMap a = AttributeDao._loadByObjectId(p.id(), log);
		p.setAttributes(a);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadAttributes completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return p;
	}

	@Override
	public ProductList loadAttributes(ProductList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadAttributes]";
			msg = "====[ load all product attributes assoiated with a list of products ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		for(Product p: pl){
			p = loadAttributes(p,log);
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadAttributes completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}



	@Override
	public String insertProcessReferenceValues(String businessId, ProcessList pl, boolean log) {
		String values = "";
		boolean start = true;
		for(Process<?,?> pr:pl){
			if(!pr.states().isModified()) continue;
			if(!start) {
				values += ",";
			} else {
				start = false;
			}
			values += "('"+businessId+"','"+pr.instanceId()+"','"+pr.businessKey()+"')";
			pr.states().setModified(false);
		}
		return values;
	}

	@Override
	public String insertModelReferenceValues(String businessId, ModelList ml, boolean log) {
		String values = "";
		boolean start = true;
		for(Model m:ml){
			if(!start) {
				values += ",";
			} else {
				start = false;
			}
			values += "('"+businessId+"',"+m.id()+")";
		}
		return values;
	}

	@Override
	public Product instanceLoad(String select, boolean primary, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[instanceLoad]";
			msg = "====[ load a product object instance from SQL select fragment ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Product p = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ci WHERE "
					+ "t.`product_name`=ci.`_object_business_id`"
					+ select;
		
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			if (rs.next()) {		
				p = rsToI(rs, log);
				p.setHistory(CampInstanceDao.instance().rsToI(rs, log));
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
			msg = "====[instanceLoad completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return p;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Product>> E instanceListLoad(String select, boolean primary, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[instanceListLoad]";
			msg = "====[ load a list of product object instances from SQL select fragment ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProductList pl = new ProductList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ci WHERE "
					+ "t.`product_name`=ci.`_object_business_id`"
					+ select;
		
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Product p = rsToI(rs, log);
				p.setHistory(CampInstanceDao.instance().rsToI(rs, log));
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
			msg = "====[instanceListLoad completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E) pl;
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
		return "product_name";
	}

}
