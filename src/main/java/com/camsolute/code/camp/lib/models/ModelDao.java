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
package com.camsolute.code.camp.lib.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.data.CampSQL;
import com.camsolute.code.camp.lib.models.CampStatesInterface.IOAction;
import com.camsolute.code.camp.lib.utilities.Util;

public class ModelDao implements ModelDaoInterface {
	
	private static final Logger LOG = LogManager.getLogger(ModelDao.class);
	private static String fmt = "[%15s] [%s]";

	public static String dbName = CampSQL.database[CampSQL._PRODUCT_DB_INDEX];

	public static String table = CampSQL.pmTable(CampSQL._PRODUCT_DB_INDEX, CampSQL.Product._PRODUCT_MODEL_TABLE_INDEX);

	public static String[][] tabledef = CampSQL.Product.product_model_table_definition;

	public static String updatestable = CampSQL.pmTable(CampSQL._PRODUCT_DB_INDEX, CampSQL.Product._PRODUCT_MODEL_UPDATES_TABLE_INDEX);
	
	public static String[][] updatestabledef = CampSQL.Product.product_model_updates_table_definition;

	private static ModelDao instance = null;
	
	private ModelDao(){
	}
	
	public static ModelDao instance(){
		if(instance == null) {
			instance = new ModelDao();
		}
		return instance;
	}
	
	@Override
	public String dbName() { return dbName; }

	@Override
	public String table() { return table; }

	@Override
	public String[][] tabledef() { return tabledef; }

	@Override
	public String updatestable() { return updatestable; }

	@Override
	public String[][] updatestabledef() { return updatestabledef; }

	@Override
	public Model loadById(int id, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadById]";
			msg = "====[ load current model instance by id ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Model m = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ti WHERE "
					+ "t.`"+tabledef[0][0]+"`="+id
					+ " AND t.`"+tabledef[0][0]+"`=ti.`_object_id`"
				  + " AND ti.`_instance_id`=ti.`_current_instance_id`";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);		
			
			if (rs.next()) {
				m = rsToI(rs,log);
				m.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				m.states().ioAction(IOAction.LOAD);
				retVal = 1;
			} else {
				if(log && !Util._IN_PRODUCTION){msg = "----[SQL ERROR! loadById FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			}
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' model instance entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[loadById completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return m;
	}

	@Override
	public Model loadByBusinessId(String name, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByName]";
			msg = "====[ load current model object instance by name ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		Model m = null;
		int retVal = 0;
		try {
			m = CampInstanceDao.instance().loadCurrent(name, ModelDao.instance(),true);
			retVal = 1;
		} catch (SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[SQL EXCEPTION ROOT! loadByName FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		m.states().ioAction(IOAction.LOAD);
		if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByName completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelList loadListByBusinessKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByKey]";
			msg = "====[ load all persisted model object instances with businessKey = '"+businessKey+"'  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		ModelList ml = new ModelList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ti WHERE "
					+ " t.`model_businesskey`='"+businessKey+"'"
					+ " AND t.`model_group`=ti.`_group_name`"
					+ " AND ti.`_instance_id`=ti.`_current_instance_id`"
					+ " AND t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " ORDER BY t.`model_group`, ti.`_object_business_id`";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);		
			
			while (rs.next()) {
				Model m = rsToI(rs,log);
				m.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				m.states().ioAction(IOAction.LOAD);
				ml.add(m);
			} 
			if(log && !Util._IN_PRODUCTION) {retVal = ml.size();msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
		return ml;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelList loadListByGroup(String group, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByGroup]";
			msg = "====[ load all persisted model object instances with group = '"+group+"'  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		ModelList ml = new ModelList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ci WHERE "
					+ " ci.`_group_name`='"+group+"'"
					+ " AND t.`model_group`=ci.`_group_name`"
					+ " AND ci.`_instance_id`=ci.`_current_instance_id`"
					+ " AND t.`"+tabledef[0][0]+"`=ci.`_object_id`"
					+ " ORDER BY ci.`_group_name`, ci.`_object_business_id`";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);		
			
			while (rs.next()) {
				Model m = rsToI(rs,log);
				m.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				m.states().ioAction(IOAction.LOAD);
				ml.add(m);
			} 
			if(log && !Util._IN_PRODUCTION) {retVal = ml.size();msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
		return ml;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelList loadListByGroupVersion(String group, String version, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByGroupVersion]";
			msg = "====[ load all persisted model object instances with group = '"+group+"' and version = '"+version+"' ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		ModelList ml = new ModelList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ci WHERE "
					+ " ci.`_group_name`='"+group+"'"
					+ " AND ci.`_version_value`='"+version+"'"
					+ " AND t.`model_group`=ci.`_group_name`"
					+ " AND ci.`_instance_id`=ci.`_current_instance_id`"
					+ " AND t.`"+tabledef[0][0]+"`=ci.`_object_id`"
					+ " ORDER BY ci.`_group_name`, ci.`_object_business_id`";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);		
			
			while (rs.next()) {
				Model m = rsToI(rs,log);
				m.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				m.states().ioAction(IOAction.LOAD);
				ml.add(m);
			} 
			if(log && !Util._IN_PRODUCTION) {retVal = ml.size();msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
		return ml;
	}

	@Override
	public Model create(String businessId, Timestamp releaseDate, Timestamp endOfLife, String businessKey, Version version, Group group, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[create]";
			msg = "====[ persist a model object instance '"+businessId+"' to the database ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Model m = new Model(Util.NEW_ID, businessId, releaseDate, endOfLife, version, group);
		m.setBusinessKey(businessKey);
		m = ModelDao.instance().save(m,log);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[create completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return m;
	}

	public Model save(Model m, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[save]";
			msg = "====[ persist a model object instance '"+m.name()+"' to the database ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		if(m.states().notReadyToSave(m)) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ERROR! Attempt to save non-new and unmodified model object instance. ]----";LOG.info(String.format(fmt, _f,msg));}
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			String SQL = "INSERT INTO " + table + "( " + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( " + insertValues(m,log) + " )";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();

			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			rs = dbs.getGeneratedKeys();						
			if (rs.next()) {		
				m.updateId(rs.getInt(tabledef[0][0]));				
			} else {
				if(log && !Util._IN_PRODUCTION){msg = "----[SQL ERROR! save FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			}
			
			m.history().stamptime();
			m.cleanStatus(m);
			//the following is no longer required due to redesign. object instances are only saved once ... needs checking 
//			if(m.states().updateBeforeSave(m)) {
//				m.history().updateInstance();
//					m.states().ioAction(IOAction.SAVE);
//				if(log && !Util._IN_PRODUCTION){msg = "----[UPDATED Instance Id before save]----";LOG.info(String.format(fmt, _f,msg));}
//			}				
			CampInstanceDao.instance()._addInstance(m, false, log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			m.states().ioAction(IOAction.SAVE);
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
			msg = "====[save completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return m;
	}

	@Override
	public <E extends ArrayList<Model>> E saveList(E ml, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveList]";
			msg = "====[ persist a list of model object instances to the database ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		// we only save model object that are ready to save
		ModelList rml = new ModelList();
		for(Model m: ml) {
			if(m.states().notReadyToSave(m)) continue; // skip this entry if not ready to save
			rml.add(m);
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "INSERT INTO " + table + "( " + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertListValues(rml,log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);		
			rs = dbs.getGeneratedKeys();						
			int count = 0;
			while (rs.next()) {
				rml.get(count).updateId(rs.getInt(tabledef[0][0]));
				count++;
			} 
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
			for(Model m: rml) {
				m.history().stamptime();
				m.cleanStatus(m);
			}
			
			CampInstanceDao.instance()._addInstances(rml, false, log);
			
			for(Model m : rml) {
				m.states().ioAction(IOAction.SAVE);
			}
			for(Model m: ml) {
				for(Model sm: rml) {
					if(m.businessId().equals(sm.businessId())){
						m.updateId(sm.id());
						m.setHistory(sm.history());
						m.states().update(sm.states());
					}
				}
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
		return (E) ml;
	}

	@Override
	public Model update(Model m, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[update]";
			msg = "====[ update a persisted model object instance database entry  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		if(log && !Util._IN_PRODUCTION){msg = "----[CURRENT STATES: "+m.states().print()+"]----";LOG.info(String.format(fmt, _f,msg));}
		if(!m.states().isModified() || m.states().isNew()) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ERROR! Attempt to update unmodified object instance! ]----";LOG.info(String.format(fmt, _f,msg));}
			return null;
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "UPDATE " + table + " SET " + Util.DB._columns(tabledef, Util.DB.dbActionType.UPDATE, log)
			+ " WHERE `"+tabledef[0][0]+"`=%s";
			
			SQL = formatUpdateSQL(SQL,m,log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL);		
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			
			m.history().stamptime();
			m.cleanStatus(m);
			m.history().updateInstance();// no check required since is update
			CampInstanceDao.instance()._addInstance(m, false, log);
			m.states().ioAction(IOAction.UPDATE);
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
		return m;
	}

	@Override
	public <E extends ArrayList<Model>> E updateList(E ml, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateList]";
			msg = "====[ update a list of persisted model object instances ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ModelList rml = new ModelList();
		for(Model m: ml) {
			if(!m.states().isModified() || m.states().isNew()) continue;
			rml.add(m);
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();

			for(Model m: rml) {
				String SQL = "UPDATE " + table + " SET " + Util.DB._columns(tabledef, Util.DB.dbActionType.UPDATE, log)
				+ " WHERE `"+tabledef[0][0]+"`=%s";
				
				SQL = formatUpdateSQL(SQL,m,log);
				
				if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
				
				dbs.addBatch(SQL);
			}
			retVal = Util.Math.addArray(dbs.executeBatch());

			for(Model m: rml) {
				m.history().stamptime();
				m.cleanStatus(m);
				m.history().updateInstance();// no check required 
			}
			CampInstanceDao.instance()._addInstances(rml, false, log);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
			for(Model m : rml) {
				m.states().ioAction(IOAction.UPDATE);
			}
			for(Model m: ml) {
				for(Model sm: rml) {
					if(m.businessId().equals(sm.businessId())){
						m.updateId(sm.id());
						m.setHistory(sm.history());
						m.states().update(sm.states());
					}
				}
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
		return (E) ml;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Model>> E loadUpdates(String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdates]";
			msg = "====[ load a list of model object current instances that were registered in the model updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ModelList ml = new ModelList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+ table + " AS t, "+CampInstanceDao.table+" AS ti WHERE "
					+ "t.`model_businesskey`='"+businessKey+"' "
					+ " AND t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND ti.`_instance_id`=ti.`_current_instance_id`"
					+ "AND EXISTS "
					+ "(SELECT * FROM "+updatestable+" AS u WHERE "
					+ " u.`_model_businesskey`=t.`model_businesskey` "
					+ " AND u.`_model_businesskey`='"+businessKey+"' "
					+ " AND u.`_model_target`='"+target+"' "
					+ " AND u.`_model_name`=t.`model_name`)";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			while (rs.next()) {
				Model m = rsToI(rs, log);
				m.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				ml.add(m);
			}
			
			if(log && !Util._IN_PRODUCTION) {retVal = ml.size();msg = "----[ '"+retVal+"' updated model instance entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
		return (E) ml;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Model>> E loadUpdatesByKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByKey]";
			msg = "====[ load a list of model object current instances that were registered in the model updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ModelList ml = new ModelList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+ table + " AS t, "+CampInstanceDao.table+" AS ti WHERE "
					+ "t.`model_businesskey`='"+businessKey+"' "
					+ " AND t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND ti.`_instance_id`=ti.`_current_instance_id`"
					+ "AND EXISTS "
					+ "(SELECT * FROM "+updatestable+" AS u WHERE "
					+ " u.`_model_businesskey`=t.`model_businesskey` "
					+ " AND u.`_model_businesskey`='"+businessKey+"' "
					+ " AND u.`_model_name`=t.`model_name`)";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			while (rs.next()) {
				Model m = rsToI(rs, log);
				m.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				ml.add(m);
			}
			
			if(log && !Util._IN_PRODUCTION) {retVal = ml.size();msg = "----[ '"+retVal+"' updated model instance entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
		return (E) ml;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Model>> E loadUpdatesByTarget(String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByTarget]";
			msg = "====[ load a list of model object current instances that were registered in the model updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ModelList ml = new ModelList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+ table + " AS t, "+CampInstanceDao.table+" AS ti WHERE "
					+ " t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND ti.`_instance_id`=ti.`_current_instance_id`"
					+ "AND EXISTS "
					+ "(SELECT * FROM "+updatestable+" AS u WHERE "
					+ " u.`_model_businesskey`=t.`model_businesskey` "
					+ " AND u.`_model_target`='"+target+"' "
					+ " AND u.`_model_name`=t.`model_name`)";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			while (rs.next()) {
				Model m = rsToI(rs, log);
				m.setHistory(CampInstanceDao.instance().rsToI(rs, log));
				ml.add(m);
			}
			
			if(log && !Util._IN_PRODUCTION) {retVal = ml.size();msg = "----[ '"+retVal+"' updated model instance entr"+((retVal!=1)?"ies":"y")+" loaded ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
		return (E) ml;
	}

	@Override
	public Model loadUpdate(Model m, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdate]";
			msg = "====[ load a specific model object instance if it is registered in the model updates table this is not neccessarily the current instance ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "SELECT * FROM "+ table + " AS t, "+CampInstanceDao.table+" AS ti WHERE "
					+ "t.`model_businesskey`='"+businessKey+"' "
					+ " AND t.`"+tabledef[0][0]+"`=ti.`_object_id`"
					+ " AND t.`"+tabledef[0][0]+"`="+m.id()
					+ " AND EXISTS "
					+ " (SELECT * FROM "+updatestable+" AS u WHERE "
					+ " u.`_model_businesskey`=t.`model_businesskey` "
					+ " AND u.`_model_businesskey`='"+businessKey+"' "
					+ " AND u.`_model_target`='"+target+"' "
					+ " AND u.`_model_name`='"+m.name()+"' "
					+ " AND u.`_model_name`=t.`model_name`)";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			if (rs.next()) {
				m = rsToI(rs, log);
				m.setHistory(CampInstanceDao.instance().rsToI(rs, log));
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
		return m;
	}

	@Override
	public int addToUpdates(Model m, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[ add model object instance reference to the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "INSERT INTO " + updatestable + "( " + Util.DB._columns(updatestabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( " + insertUpdateValues(m,target) + " )";
			
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
	public <E extends ArrayList<Model>> int addToUpdates(E ml, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[ add a list of model object instances to the updates list ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "INSERT INTO " + updatestable + "( " + Util.DB._columns(updatestabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertUpdateListValues(ml,target);
			
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
			msg = "====[ delete all updates table entries of registered model object instances by their businesskey and target aspect values ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "DELETE FROM "+updatestable+" WHERE `_model_businesskey`='"+businessKey+"' AND `_model_target`='"+target+"'";
			
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
			_f = "[deleteFromUpdatesByKey]";
			msg = "====[ delete all updates table entries of registered model object instances by their businesskey and target aspect values ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "DELETE FROM "+updatestable+" WHERE `_model_businesskey`='"+businessKey+"'";
			
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
			msg = "====[deleteFromUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteFromUpdatesByTarget(String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdatesByTarget]";
			msg = "====[ delete all updates table entries of registered model object instances by their businesskey and target aspect values ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "DELETE FROM "+updatestable+" WHERE `_model_target`='"+target+"'";
			
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
			msg = "====[deleteFromUpdatesByTarget completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteFromUpdates(String modelName, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[ delete all updates table entries of registered model object instances by their name, businesskey and target aspect values ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			String SQL = "DELETE FROM "+updatestable+" WHERE `_model_name`='"+modelName+"' AND `_model_businesskey`='"+businessKey+"' AND `_model_target`='"+target+"'";
			
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
	public <E extends ArrayList<Model>> int deleteFromUpdates(E ml, String businessKey, String target,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[ delete all updates table entries of a list of registered model object instances by their name, businesskey and target aspect values ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			for(Model m: ml) {
				String SQL = "DELETE FROM "+updatestable+" WHERE `_model_name`='"+m.name()+"' AND `_model_businesskey`='"+businessKey+"' AND `_model_target`='"+target+"'";
				
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
	public String insertValues(Model m, boolean log) {
		String values = 
				  "'"+m.name()+"'"
				+ ",'"+m.releaseDate().toString()+"'"
				+ ",'"+m.endOfLife().toString()+"'"
				+ ",'"+m.businessKey()+"'"
				+ ",'"+m.version().value()+"'"
				+ ",'"+m.group().name()+"'";
		return values;
	}

	public String formatUpdateSQL(String SQL, Model m, boolean log) {
		String sql = String.format(SQL,
			 "'"+m.name()+"'"
			,"'"+m.releaseDate().toString()+"'"
			,"'"+m.endOfLife().toString()+"'"
			,"'"+m.businessKey()+"'"
			,"'"+m.version().value()+"'"
			,"'"+m.group().name()+"'"
			,m.id());
		return sql;
	}

	@Override
	public <E extends ArrayList<Model>> String insertListValues(E ml, boolean log) {
		String values = "";
		boolean start = true;
		for(Model m:ml) {
			if(!start) {
				values += ",";
			} else {
				start = false;
			}
			values += " ("+insertValues(m,log)+") ";
		}
		return values;
	}

	@Override
	public <E extends ArrayList<Model>> String insertUpdateListValues(E ml, String target) {
		String values = "";
		boolean start = true;
		for(Model m:ml) {
			if(!start) {
				values += ",";
			} else {
				start = false;
			}
			values += " ("+insertUpdateValues(m,target)+") ";
		}
		return values;
	}

	@Override
	public String insertUpdateValues(Model m, String target) {
		String values = 
			  "'"+m.name()+"'"
			+ ",'"+m.businessKey()+"'"
			+ ",'"+target+"'"
			+ ",'"+Util.Time.timeStampString()+"'";
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

		Connection conn = null;
		Statement dbs = null;
		int retVal = 0;
		try {
			conn = Util.DB.__conn(log);

			dbs = conn.createStatement();
			dbs.addBatch(SQL);
			dbs.addBatch(uSQL);
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
	public Model rsToI(ResultSet rs, boolean log) throws SQLException {
		int id = rs.getInt(tabledef[0][0]);
		String name = rs.getString("model_name");
		Timestamp releaseDate = rs.getTimestamp("model_release_date");
		Timestamp endOfLife = rs.getTimestamp("model_end_of_life");
		String businessKey = rs.getString("model_businesskey");
		Version version = new Version(rs.getString("model_version"));
		Group group = new Group(rs.getString("model_group"));
		Model m = new Model(id, name, releaseDate, endOfLife, version, group);
		m.setBusinessKey(businessKey);
		return m;
	}

	@Override
	public Model instanceLoad(String select, boolean primary, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[instanceLoad]";
			msg = "====[ load a persisted model object instance from SQL select fragment ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Model m = null;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ci WHERE "
					+ "t.`"+tabledef[0][0]+"`=ci.`_object_id`"
					+ select;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			if (rs.next()) {		
				m = rsToI(rs, log);
				m.setHistory(CampInstanceDao.instance().rsToI(rs,log));
				m.states().ioAction(IOAction.LOAD);
				retVal = 1;
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
			msg = "====[instanceLoad completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Model>> E instanceListLoad(String select, boolean primary, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[instanceListLoad]";
			msg = "====[ load a persisted model object instance from SQL select fragment ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ModelList ml = new ModelList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+table+" AS t, "+CampInstanceDao.table+" AS ci WHERE "
					+ "t.`"+tabledef[0][0]+"`=ci.`_object_id`"
					+ select;
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			while (rs.next()) {		
				Model m = rsToI(rs, log);
				m.setHistory(CampInstanceDao.instance().rsToI(rs,log));
				m.states().ioAction(IOAction.LOAD);
				ml.add(m);
			}
			retVal = ml.size();
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
		//return retVal;
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[instanceListLoad completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E) ml;
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
		return "model_name";
	}
	
	@Override
	public Model loadFirst(String businessId) {
		return _loadFirst(businessId, !Util._IN_PRODUCTION);
	}
	public static Model _loadFirst(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadFirst]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Model a = null;
		try {
			a = CampInstanceDao.instance()._loadFirst(businessId, ModelDao.instance(), false,log);
		} catch (SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[SQL EXCEPTION! loadFirst FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadFirst completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return a;
	}

	@Override
	public Model loadPrevious(Model model) {
		return _loadPrevious(model, !Util._IN_PRODUCTION);
	}
	public static Model _loadPrevious(Model model,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadPrevious]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Model a = null;
		try {
			a = CampInstanceDao.instance()._loadPrevious(model, ModelDao.instance(), false, log);
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[SQL EXCEPTION! _loadPrevious FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadPrevious completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return a;
	}

	@Override
	public Model loadNext(Model model) {
		return _loadNext(model, !Util._IN_PRODUCTION);
	}
	public Model _loadNext(Model model, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadNext]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Model a = null;
		try {
			a = CampInstanceDao.instance()._loadNext(model, ModelDao.instance(), false, log);
		} catch(SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[SQL EXCEPTION! _loadNext FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadNext completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return a;
	}

	@Override
	public ModelList loadDate(String businessId, String date) {
		return _loadDate(businessId, date, !Util._IN_PRODUCTION);
	}
	public ModelList _loadDate(String businessId, String date, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ModelList al = null;
		try {
			al = CampInstanceDao.instance()._loadDate(businessId, date, ModelDao.instance(), false,log);
		} catch (SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[SQL EXCEPTION! _loadDate FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadDate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public ModelList loadDateRange(String businessId, String startDate, String endDate) {
		return _loadDateRange(businessId, startDate, endDate, !Util._IN_PRODUCTION);
	}
	public ModelList _loadDateRange(String businessId, String startDate, String endDate, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDateRange]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ModelList al = null;
		try {
			al = CampInstanceDao.instance()._loadDateRange(businessId, startDate, endDate, ModelDao.instance(), false,log);
		} catch (SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[SQL EXCEPTION! _loadDateRange FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadDateRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public ModelList loadDate(String date) {
		return _loadDate(date,!Util._IN_PRODUCTION);
	}
	public ModelList _loadDate(String date,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ModelList al = null;
		try {
			al = CampInstanceDao.instance()._loadDate(date, ModelDao.instance(), false,log);
		} catch (SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[SQL EXCEPTION! _loadDate FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadDate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public ModelList loadDateRange(String startDate, String endDate) {
		return _loadDateRange(startDate,endDate,!Util._IN_PRODUCTION);
	}
	public ModelList _loadDateRange(String startDate, String endDate, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDateRange]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ModelList al = null;
		try {
			al = CampInstanceDao.instance()._loadDateRange(startDate, endDate, ModelDao.instance(), false,log);
		} catch (SQLException e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[SQL EXCEPTION! _loadDateRange FAILED]----";LOG.info(String.format(fmt, _f,msg));}
			e.printStackTrace();
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_loadDateRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	
}

