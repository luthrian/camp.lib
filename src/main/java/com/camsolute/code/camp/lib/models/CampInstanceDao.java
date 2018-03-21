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

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.data.CampFormats;
import com.camsolute.code.camp.lib.data.CampSQL;
import com.camsolute.code.camp.lib.models.CampInstanceDaoInterface;
import com.camsolute.code.camp.lib.contract.HasResultSetToInstance;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.dao.DaoInterface;
import com.camsolute.code.camp.lib.dao.InstanceDaoInterface;
import com.camsolute.code.camp.lib.dao.database.DBDaoInterface;
import com.camsolute.code.camp.lib.dao.database.TableDaoInterface;
import com.camsolute.code.camp.lib.utilities.Util;

/**
 * The <code>CampInstance</code> object encapsulates a unique temporal aspect of an object instance
 * together with its process related status aspects at a point in its life cycle.
 * The temporal aspect of an object instance is denoted by a unique identifier - the objects
 * <code>InstanceId</code> - which changes each time an object is modified or changes state and is
 * then persisted.
 * the process status aspect is relevant to any process connected to a business object.
 * A <code>CampInstance</code> object therefore provides access to a representation of an object at
 * the time it was persisted.
 * In order to be able to navigate the various temporal aspects of an object the
 * <code>CampInstance</code> object contains an object's initial and current <code>InstanceId</code>.
 *
 * @author Christopher Campbell
 */
public class CampInstanceDao implements CampInstanceDaoInterface,HasResultSetToInstance<CampInstance>, TableDaoInterface {

		private static final Logger LOG = LogManager.getLogger(CampInstanceDao.class);
		
		private static String fmt = "[%15s] [%s]";
		
		public static String dbName = CampSQL.database[CampSQL._SYSTEM_TABLES_DB_INDEX];

		public static String table = CampSQL.sysTable(CampSQL._SYSTEM_TABLES_DB_INDEX, CampSQL.System._INSTANCE_INDEX);

		public static String[][] tabledef = CampSQL.System._instance_table_definition;
		
		public static String loadCurrentSQL = "SELECT * FROM %s AS i, " + table + " AS h "
				+ " WHERE h.`business_id` LIKE '%s%%' AND h.`_instance_id`=h.`_current_instance_id` AND i.`%s`=h.`_object_id`";

		private static CampInstanceDao instance = null;
		
		private CampInstanceDao(){
		}
		
		public static CampInstanceDao instance(){
			if(instance == null) {
				instance = new CampInstanceDao();
			}
			return instance;
		}
		
		@Override
		public CampInstance rsToI(ResultSet rs, boolean log) throws SQLException {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[rsToI]";
				msg = "====[ Assemble Instance history aspects of instance object ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
			int objectRefId = rs.getInt("_object_ref_id");
			if(log && !Util._IN_PRODUCTION){msg = "----[ object reference id '"+objectRefId+"']----";LOG.info(String.format(fmt, _f,msg));}

			String id = rs.getString("_instance_id");
			if (log && !Util._IN_PRODUCTION) {
				msg = "----[ instance id is '" + id + "']----";
				LOG.info(String.format(fmt, _f, msg));
			}

			String currentInstanceId = rs.getString("_current_instance_id");
			if (log && !Util._IN_PRODUCTION) {
				msg = "----[ current instance id is '" + currentInstanceId + "']----";
				LOG.info(String.format(fmt, _f, msg));
			}

			String initialId = rs.getString("_initial_instance_id");
			if (log && !Util._IN_PRODUCTION) {
				msg = "----[ initial instance id is '" + initialId + "']----";
				LOG.info(String.format(fmt, _f, msg));
			}

			Timestamp timestamp = rs.getTimestamp("_timestamp");
			if (log && !Util._IN_PRODUCTION) {
				msg = "----[ timestamp is '" + timestamp + "']----";
				LOG.info(String.format(fmt, _f, msg));
			}

			Timestamp endOfLife = rs.getTimestamp("_end_of_life");
			if (log && !Util._IN_PRODUCTION) {
				msg = "----[ End of Life is '" + endOfLife + "']----";
				LOG.info(String.format(fmt, _f, msg));
			}

			CampInstance instance =  new CampInstance(id, currentInstanceId, initialId);
			instance.endOfLife(endOfLife);
			instance.timestamp(timestamp);
			instance.setObjectRefId(objectRefId);
			
			if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[rsToI completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			
			return instance;
		}
		
		/**
		 * NOTE: <code>object.history().stamptime()</code>, 
		 * <code>object.history().cleanStatus()</code> and if required (check with <code>object.states().updateBeforeSave(object)</code>) 
		 * <code>object.history().updateInstance()</code> must be called 
		 * prior to executing <code>CampInstanceDao.instance().addInstance(object)</code>
		 */
		@Override
		public <T extends IsObjectInstance<T>> int addInstance(T object, boolean useObjectId) throws SQLException {
			return _addInstance(object, useObjectId, !Util._IN_PRODUCTION);
		}

		public <T extends IsObjectInstance<T>> int _addInstance(T object, boolean useObjectId, boolean log)
				throws SQLException {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[_addInstance]";
				msg = "====[ persisting business object instance ]====";
				LOG.info(String.format(fmt, (_f + ">>>>>>>>>").toUpperCase(), msg));
			}

			int retVal = 0;

			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;
			Statement dbu = null;

			try {

				conn = Util.DB.__conn(log);

				dbs = conn.createStatement();
				dbu = conn.createStatement();
				
				String currentId = object.history().currentId().id();
				
				object.cleanStatus(object);
				if (!object.states().isNew()) {
					if(useObjectId) {
						object.getObjectHistory().updateInstance();
					}else{
						object.history().updateInstance();
					}
					if(log && !Util._IN_PRODUCTION){msg = "----[UPDATED object instance id ]----";LOG.info(String.format(fmt, _f,msg));}
				}
				object.history().stamptime();

				String SQL = "INSERT INTO " + table + "( " + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
				+ " ) VALUES ( " + insertValues(object, useObjectId) + " )";
				
				if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}

				dbs.addBatch(SQL);

				if (!object.history().isFirst()) {

					SQL = "UPDATE " + table + " SET `_current_instance_id`='" + object.history().id().id()
							+ "' WHERE `_current_instance_id`='" + currentId + "'";

					if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
					
					dbu.addBatch(SQL);

				}

				retVal = dbs.executeBatch()[0];
				dbu.executeBatch();

			} catch (SQLException e) {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[SQL EXCEPTION ROOT! _save FAILED]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				e.printStackTrace();
				throw e;
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[Releasing Connection]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
				Util.DB._releaseRS(rs, log);
			}

			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[_addInstances completed.]====";
				LOG.info(String.format(fmt, ("<<<<<<<<<" + _f).toUpperCase(), msg + time));
			}

			return retVal;
		}

		@Override
		public <T extends IsObjectInstance<T>, E extends ArrayList<T>> int addInstances(E objectList, boolean useObjectId) throws SQLException {
			return _addInstances(objectList, useObjectId, !Util._IN_PRODUCTION);
		}

		public <T extends IsObjectInstance<T>, E extends ArrayList<T>> int _addInstances(E ol, boolean useObjectId, boolean log) throws SQLException {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[_addInstances]";
				msg = "====[ add business instances history ]====";
				LOG.traceEntry(String.format(fmt, (_f + ">>>>>>>>>").toUpperCase(), msg));
			}

			int count = 0;

			int retVal = 0;

			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;

			try {

				conn = Util.DB.__conn(log);

				dbs = conn.createStatement();

				for (T o:ol) {

					o.cleanStatus(o);

					o.history().stamptime();
					
					String currentId = o.history().currentId().id();
					
					if (!o.states().isNew()) {
						if(useObjectId) {
							o.getObjectHistory().updateInstance();
						}else{
							o.history().updateInstance();
						}
						if(log && !Util._IN_PRODUCTION){msg = "----[UPDATED object instance id]----";LOG.info(String.format(fmt, _f,msg));}
					}

					String SQL = "INSERT INTO " + table + "( " + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
							+ " ) VALUES ( " + insertValues(o, useObjectId) + " )";

					if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
					
					dbs.addBatch(SQL);

					if (!o.history().isFirst()) {

						SQL = "UPDATE " + table + " SET `_current_instance_id`='" + o.history().id().id()
								+ "' WHERE `_current_instance_id`='" + currentId + "'";

						if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
						
						dbs.addBatch(SQL);

					}
					count++;
				}
				retVal = Util.Math.addArray(dbs.executeBatch());
				
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" updated in database ]----"; LOG.info(String.format(fmt, _f, msg)); }
				

			} catch (SQLException e) {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[SQL EXCEPTION ROOT! _save FAILED]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				e.printStackTrace();
				throw e;
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[Releasing Connection]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
				Util.DB._releaseRS(rs, log);
			}

			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[_addInstance completed.]====";
				LOG.info(String.format(fmt, ("<<<<<<<<<" + _f).toUpperCase(), msg + time));
			}

			return retVal;
		}

		@Override
		public <T extends InstanceDaoInterface<?>, E extends IsObjectInstance<E>> E loadFirst(String businessId, T dao, boolean primary) throws SQLException {
			return _loadFirst(businessId, dao, primary, !Util._IN_PRODUCTION);
		}

		@SuppressWarnings("unchecked")
		public <T extends InstanceDaoInterface<?>, E extends IsObjectInstance<E>> E _loadFirst(String businessId, T dao, boolean primary, boolean log) throws SQLException {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[_loadFirst]";
				msg = "====[ load initially persisted object instance '" + businessId + "' from database ]====";
				LOG.traceEntry(String.format(fmt, (_f + ">>>>>>>>>").toUpperCase(), msg));
			}
			String select = " AND ci.`_object_business_id` LIKE '"+businessId+"%'" 
					+ " AND ci.`_instance_id`=ci.`_initial_instance_id`"
					+ " AND t.`"+dao.tabledef(primary)[0][0]+"`=ci.`_object_id`";
			E first = (E) dao.instanceLoad(select, primary, log);

//			
//			Connection conn = null;
//			ResultSet rs = null;
//			Statement dbs = null;
//
//			int retVal = 0;
//			try{
//				conn = Util.DB.__conn(log);
//				
//				String SQL = "SELECT * FROM " + dao.table() + " AS i, " + table + " AS h "
//						+ " WHERE "
//						+ "h.`_object_business_id` LIKE '"+businessId+"%'" 
//						+ " AND h.`_instance_id`=h.`_initial_instance_id`"
//						+ " AND i.`"+dao.tabledef()[0][0]+"`=h.`_object_id`";
//				
//				if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
//				
//				dbs = conn.createStatement();
//				
//				rs = dbs.executeQuery(SQL);		
//				
//				if(rs.next()) {
//					first = (E) dao.rsToI(rs, log);
//					first.setHistory(rsToI(rs,log));
//					
//					retVal = 1;
//					if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
//				}
//			} catch(SQLException e) {
//				if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
//				e.printStackTrace();
//				//throw e;
//			} finally {
//				if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
//				Util.DB.__release(conn,log);
//				Util.DB._releaseRS(rs, log);
//				Util.DB._releaseStatement(dbs, log);
//			}
			
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[_loadFirst completed.]====";
				LOG.info(String.format(fmt, ("<<<<<<<<<" + _f).toUpperCase(), msg + time));
			}

			return first;
		}

		@Override
		public <T extends InstanceDaoInterface<?>, E extends IsObjectInstance<E>> E loadCurrent(String businessId, T dao, boolean primary) throws SQLException {
			return _loadCurrent(businessId, dao, primary, !Util._IN_PRODUCTION);
		}

		@SuppressWarnings("unchecked")
		public static <T extends InstanceDaoInterface<?>, E extends IsObjectInstance<E>> E _loadCurrent(String businessId, T dao, boolean primary, boolean log) throws SQLException {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[_loadCurrent]";
				msg = "====[ loading current object instance '" + businessId + "' from database ]====";
				LOG.info(String.format(fmt, _f, msg));
			}
			String select = " AND ci.`_object_business_id` LIKE '"+businessId+"%'" 
					+ " AND ci.`_instance_id`=ci.`_current_instance_id`"
					+ " AND t.`"+dao.tabledef(primary)[0][0]+"`=ci.`_object_id`";
			E currentInstance = (E) dao.instanceLoad(select,primary, log);
			
//			Connection conn = null;
//			ResultSet rs = null;
//			Statement dbs = null;
//			int retVal = 0;
//			try{
//				conn = Util.DB.__conn(log);
//				
//				String SQL = "SELECT * FROM " + dao.table() + " AS i, " + table + " AS h "
//						+ " WHERE "
//						+ "h.`_object_business_id` LIKE '"+businessId+"%'" 
//						+ " AND h.`_instance_id`=h.`_current_instance_id`"
//						+ " AND i.`"+dao.tabledef()[0][0]+"`=h.`_object_id`";
//				
//				if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
//				
//				dbs = conn.createStatement();
//				
//				rs = dbs.executeQuery(SQL);		
//				if (rs.next()) {
//					currentInstance = (E) dao.rsToI(rs, log);
//					currentInstance.setHistory(rsToI(rs,log));
//					retVal = 1;
//				} else {
//					if(log && !Util._IN_PRODUCTION){msg = "----[SQL ERROR! _loadCurrent FAILED]----";LOG.info(String.format(fmt, _f,msg));}
//				}
//				
//				if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
//				
//			} catch(SQLException e) {
//				if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
//				e.printStackTrace();
//				//throw e;
//			} finally {
//				if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
//				Util.DB.__release(conn,log);
//				Util.DB._releaseRS(rs, log);
//				Util.DB._releaseStatement(dbs, log);
//			}
//			
			
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[ current order loaded.]====";
				LOG.info(_f + msg + time);
			}

			return currentInstance;
		}

		@Override
		public <T extends InstanceDaoInterface<?>, E extends IsObjectInstance<E>> E loadPrevious(E object, T dao, boolean primary) throws SQLException {
			return _loadPrevious(object, dao,primary, !Util._IN_PRODUCTION);
		}

		@SuppressWarnings("unchecked")
		public <T extends InstanceDaoInterface<?>, E extends IsObjectInstance<E>> E _loadPrevious(E o, T dao, boolean primary, boolean log) throws SQLException {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[_loadPrevious]";
				msg = "====[ loading previous object instance to '" + o.onlyBusinessId() + "' ]====";
				LOG.traceEntry(String.format(fmt, (_f + ">>>>>>>>>").toUpperCase(), msg));
			}
			String select = " AND ci.`_object_business_id` LIKE '"+o.onlyBusinessId()+"%'" 
					+ " AND ci.`_instance_id`='"+o.history().initialId().id()+"'"
					+ " AND t.`"+dao.tabledef(primary)[0][0]+"`=ci.`_object_id`";
			E previousInstance = (E) dao.instanceLoad(select,primary, log);
			
//			Connection conn = null;
//			ResultSet rs = null;
//			Statement dbs = null;
//			int retVal = 0;
//			try{
//				conn = Util.DB.__conn(log);
//				
//				String SQL = "SELECT * FROM " + dao.table() + " AS i, " + table + " AS h "
//						+ " WHERE "
//						+ "h.`_object_business_id` LIKE '"+o.onlyBusinessId()+"%'" 
//						+ " AND h.`_instance_id`='"+o.history().initialId().id()+"'"
//						+ " AND i.`"+dao.tabledef()[0][0]+"`=h.`_object_id`";
//				
//				if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
//				
//				dbs = conn.createStatement();
//				
//				rs = dbs.executeQuery(SQL);		
//				if (rs.next()) {		
//					previousInstance = (E) dao.rsToI(rs, log);
//					previousInstance.setHistory(rsToI(rs,log));
//					retVal = 0;
//				} else {
//					if(log && !Util._IN_PRODUCTION){msg = "----[SQL ERROR! _loadPrevious FAILED]----";LOG.info(String.format(fmt, _f,msg));}
//				}
//				
//				if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
//				
//			} catch(SQLException e) {
//				if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
//				e.printStackTrace();
//				//throw e;
//			} finally {
//				if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
//				Util.DB.__release(conn,log);
//				Util.DB._releaseRS(rs, log);
//				//Util.DB._releaseStatement(dbp, log);
//				Util.DB._releaseStatement(dbs, log);
//			}
//			//return retVal;
//			
//

			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[_loadPrevious completed.]====";
				LOG.info(String.format(fmt, ("<<<<<<<<<" + _f).toUpperCase(), msg + time));
			}

			return previousInstance;
		}

		@Override
		public <T extends InstanceDaoInterface<?>, E extends IsObjectInstance<E>> E loadNext(E object, T dao, boolean primary) throws SQLException {
			return _loadNext(object, dao,primary, !Util._IN_PRODUCTION);
		}

		@SuppressWarnings("unchecked")
		public <T extends InstanceDaoInterface<?>, E extends IsObjectInstance<E>> E _loadNext(E o, T dao, boolean primary, boolean log) throws SQLException {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[_loadNext]";
				msg = "====[ loading next instance of '" + o.onlyBusinessId() + "' ]====";
				LOG.traceEntry(String.format(fmt, _f.toUpperCase() + ">>>>>>>>>", msg));
			}
			String select = " AND ci.`_object_id`=t.`"+dao.tabledef(primary)[0][0]+"`"
						+ " AND ci.`_object_business_id` LIKE '"+o.onlyBusinessId()+"%'" 
						+ " AND ci.`_initial_instance_id`='"+o.history().id().id()+"'"
						+ " AND ci.`_initial_instance_id`<>ci.`_instance_id`";
			E nextInstance = (E) dao.instanceLoad(select,primary, log);
			
//			Connection conn = null;
//			ResultSet rs = null;
//			Statement dbs = null;
//			int retVal = 0;
//			try{
//				conn = Util.DB.__conn(log);
//				
//				String SQL = "SELECT * FROM " + dao.table() + " AS i, " + table + " AS h "
//						+ " WHERE "
//						+ " i.`"+dao.tabledef()[0][0]+"`="+o.id()
//						+ " AND h.`_object_id`=i.`"+dao.tabledef()[0][0]+"`"
//						+ " AND h.`_object_business_id` LIKE '"+o.onlyBusinessId()+"%'" 
//						+ " AND h.`_initial_instance_id`='"+o.history().id().id()+"'"
//						+ " AND h.`_initial_instance_id`<>h.`_instance_id`";
//				
//				if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
//				
//				dbs = conn.createStatement();
//				
//				rs = dbs.executeQuery(SQL);		
//				if (rs.next()) {		
//					nextInstance = (E) dao.rsToI(rs, log);
//					nextInstance.setHistory(rsToI(rs,log));
//				} else {
//					if(log && !Util._IN_PRODUCTION){msg = "----[SQL ERROR! _loadNext FAILED]----";LOG.info(String.format(fmt, _f,msg));}
//				}
//				
//				if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
//				
//			} catch(SQLException e) {
//				if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
//				e.printStackTrace();
//				//throw e;
//			} finally {
//				if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
//				Util.DB.__release(conn,log);
//				Util.DB._releaseRS(rs, log);
//				Util.DB._releaseStatement(dbs, log);
//			}

			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[_loadNext completed.]====";
				LOG.info(String.format(fmt, ("<<<<<<<<<" + _f).toUpperCase(), msg + time));
			}

			return nextInstance;
		}

		public <T extends InstanceDaoInterface<?>,U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDate(String date, T dao, boolean primary) throws SQLException {
			return _loadDate(date, dao,primary, true);
		}
		@SuppressWarnings("unchecked")
		public <T extends InstanceDaoInterface<?>,U extends IsObjectInstance<U>, E extends ArrayList<U>> E _loadDate(String date, T dao, boolean primary, boolean log) throws SQLException {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[_loadFirst]";
				msg = "====[ load a list of persisted current object instance by the time is was persisted to the database ]====";
				LOG.traceEntry(String.format(fmt, (_f + ">>>>>>>>>").toUpperCase(), msg));
			}
//			String dt = Util.Time.datetime(Util.Time.timestamp(date)); //TODO: this may be better but slower check
			String dt = date.substring(0,19);
			String select = " AND ci.`_date` LIKE '"+dt+"%'"
						+ " AND ci.`_instance_id`=ci.`_initial_instance_id`"
						+ " AND t.`"+dao.tabledef(primary)[0][0]+"`=ci.`_object_id`";
			E o = (E)dao.instanceListLoad(select,primary,log);

			if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[_loadDate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			return o;
		}
		
		@Override
		public <T extends InstanceDaoInterface<?>,U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDate(String businessId, String date, T dao, boolean primary) throws SQLException {
			return _loadDate(businessId, date, dao, primary, !Util._IN_PRODUCTION);
		}
		@SuppressWarnings("unchecked")
		public <T extends InstanceDaoInterface<?>,U extends IsObjectInstance<U>, E extends ArrayList<U>> E _loadDate(String businessId, String date, T dao, boolean primary, boolean log) throws SQLException {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[_loadFirst]";
				msg = "====[ load persisted object instance by timestamp '" + businessId + "' from database ]====";
				LOG.traceEntry(String.format(fmt, (_f + ">>>>>>>>>").toUpperCase(), msg));
			}
//			String dt = Util.Time.datetime(Util.Time.timestamp(date)); //TODO: this may be better but slower check
			String dt = date.substring(0,19);
			String select = " AND ci.`_object_business_id`='"+businessId+"'" 
						+ " AND ci.`_date` LIKE '"+dt+"%'"
//						+ " AND ci.`_instance_id`=ci.`_initial_instance_id`"
						+ " AND t.`"+dao.tabledef(primary)[0][0]+"`=ci.`_object_id` "
						+ " ORDER BY t.`"+dao.tabledef(primary)[0][0]+"` ASC";
			E o = (E)dao.instanceListLoad(select,primary,log);

//			Connection conn = null;
//			ResultSet rs = null;
//			Statement dbs = null;
//
//			int retVal = 0;
//			try{
//				conn = Util.DB.__conn(log);
//				
//				String SQL = "SELECT * FROM " + dao.table() + " AS i, " + table + " AS h "
//						+ " WHERE "
//						+ "h.`_object_business_id` LIKE '"+businessId+"%'" 
//						+ " AND h.`_timestamp` LIKE '"+date+"%'"
//						+ " AND h.`_instance_id`=h.`_initial_instance_id`"
//						+ " AND i.`"+dao.tabledef()[0][0]+"`=h.`_object_id`";
//
//				if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
//				
//				dbs = conn.createStatement();
//				
//				rs = dbs.executeQuery(SQL);		
//				
//				if(rs.next()) {
//					o = (E) dao.rsToI(rs, log);
//					o.setHistory(rsToI(rs,log));
//					
//					retVal = 1;
//					if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
//				}
//			} catch(SQLException e) {
//				if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
//				e.printStackTrace();
//				//throw e;
//			} finally {
//				if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
//				Util.DB.__release(conn,log);
//				Util.DB._releaseRS(rs, log);
//				Util.DB._releaseStatement(dbs, log);
//			}
			
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[_loadFirst completed.]====";
				LOG.info(String.format(fmt, ("<<<<<<<<<" + _f).toUpperCase(), msg + time));
			}

			return o;
		}

		@Override
		public <T extends InstanceDaoInterface<?>,U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDateRange(String startDate, String endDate, T dao, boolean primary) throws SQLException {
			return _loadDateRange(startDate, endDate, RangeTarget.DATE, dao,primary, !Util._IN_PRODUCTION);
		}
		@SuppressWarnings("unchecked")
		public <T extends InstanceDaoInterface<?>,U extends IsObjectInstance<U>, E extends ArrayList<U>> E _loadDateRange(String startDate, String endDate, RangeTarget target, T dao, boolean primary, boolean log) throws SQLException {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[_loadFirst]";
				msg = "====[ load a list of persisted object instances that were persistied to the database within a range of 2 dates ]====";
				LOG.traceEntry(String.format(fmt, (_f + ">>>>>>>>>").toUpperCase(), msg));
			}
			//TODO: refactor RangeTarget into all daterange queries
			String targ = "";
			switch(target) {
			case TIMESTAMP:
				targ = "_timestamp";
				break;
			case DATE:
				targ = "_date";
				break;
			case END_OF_LIFE:
				targ = "_end_of_life";
				break;
			default:
				targ = "_date";
				break;
			}
//			String sdt = Util.Time.datetime(Util.Time.timestamp(endDate)); //TODO: this may be better but slower check
//			String edt = Util.Time.datetime(Util.Time.timestamp(endDate)); //TODO: this may be better but slower check
			String sdt = startDate.substring(0,19);
			String edt = endDate.substring(0,19);
			String fSQL = "";
			if( (sdt == null || sdt.isEmpty())){
				fSQL = " AND DATE_SUB('"+edt+"',INTERVAL "+CampFormats._DAYS_IN_PAST_SEARCH_RANGE+" DAY) <= ci.`"+targ+"` AND '"+edt+"' >= ci.`"+targ+"` ";
			} else if( (edt == null || edt.isEmpty())) {
				fSQL = " AND '"+sdt+"' <= ci.`"+targ+"` AND DATE_ADD('"+sdt+"',INTERVAL "+CampFormats._DAYS_IN_FUTURE_SEARCH_RANGE+" DAY) >= ci.`"+targ+"` ";
			} else {
				fSQL = " AND '"+sdt+"' <= ci.`"+targ+"` AND '"+edt+"' >= ci.`"+targ+"` ";
			}
			String select = " AND ci.`_object_business_id`=t.`"+dao.businessIdColumn(primary)+"`" 
						+ fSQL
						+ " AND ci.`_instance_id`=ci.`_initial_instance_id`"
						+ " AND t.`"+dao.tabledef(primary)[0][0]+"`=ci.`_object_id`";
			E o = (E) dao.instanceListLoad(select,primary,log);
			if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[_loadDateRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			return o;
		}
		@Override
		public <T extends InstanceDaoInterface<?>,U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDateRange(String businessId, String startDate, String endDate, T dao, boolean primary) throws SQLException {
			return _loadDateRange(businessId, startDate, endDate, RangeTarget.DATE, dao,primary, !Util._IN_PRODUCTION);
		}
		@SuppressWarnings("unchecked")
		public <T extends InstanceDaoInterface<?>,U extends IsObjectInstance<U>, E extends ArrayList<U>> E _loadDateRange(String businessId, String startDate, String endDate, RangeTarget target, T dao, boolean primary, boolean log) throws SQLException {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[_loadFirst]";
				msg = "====[ load persisted object instance by timestamp '" + businessId + "' from database ]====";
				LOG.traceEntry(String.format(fmt, (_f + ">>>>>>>>>").toUpperCase(), msg));
			}
			//TODO: refactor RangeTarget into all daterange queries
			String targ = "";
			switch(target) {
			case TIMESTAMP:
				targ = "_timestamp";
				break;
			case DATE:
				targ = "_date";
				break;
			case END_OF_LIFE:
				targ = "_end_of_life";
				break;
			default:
				targ = "_date";
				break;
			}
//			String sdt = Util.Time.datetime(Util.Time.timestamp(endDate)); //TODO: this may be better but slower check
//			String edt = Util.Time.datetime(Util.Time.timestamp(endDate)); //TODO: this may be better but slower check
			String sdt = startDate.substring(0,19);
			String edt = endDate.substring(0,19);
			String fSQL = "";
			if( (sdt == null || sdt.isEmpty())){
				fSQL = " AND DATE_SUB('"+edt+"',INTERVAL "+CampFormats._DAYS_IN_PAST_SEARCH_RANGE+" DAY) <= ci.`"+targ+"` AND '"+edt+"' >= ci.`"+targ+"` ";
			} else if( (edt == null || edt.isEmpty())) {
				fSQL = " AND '"+sdt+"' <= ci.`"+targ+"` AND DATE_ADD('"+sdt+"',INTERVAL "+CampFormats._DAYS_IN_FUTURE_SEARCH_RANGE+" DAY) >= ci.`"+targ+"` ";
			} else {
				fSQL = " AND '"+sdt+"' <= ci.`"+targ+"` AND '"+edt+"' >= ci.`"+targ+"` ";
			}
			String select = " AND ci.`_object_business_id`='"+businessId+"'" 
						+ fSQL
//						+ " AND ci.`_instance_id`=ci.`_initial_instance_id`"
						+ " AND t.`"+dao.tabledef(primary)[0][0]+"`=ci.`_object_id`";
			E o = (E) dao.instanceListLoad(select,primary,log);

//			Connection conn = null;
//			ResultSet rs = null;
//			Statement dbs = null;
//
//			int retVal = 0;
//			try{
//				conn = Util.DB.__conn(log);
//				
//				String fSQL = "";
//				if( (startDate == null || startDate.isEmpty())){
//					fSQL = " AND DATE_SUB('"+endDate+"',INTERVAL "+CampFormats._DAYS_IN_PAST_SEARCH_RANGE+" DAY) <= h.`_timestamp` AND '"+endDate+"' >= h`_timestamp` ";
//				} else if( (endDate == null || endDate.isEmpty())) {
//					fSQL = " AND '"+startDate+"' <= h.`_timestamp` AND DATE_ADD('"+startDate+"',INTERVAL "+CampFormats._DAYS_IN_FUTURE_SEARCH_RANGE+" DAY) >= h.`_timestamp` ";
//				} else {
//					fSQL = " AND '"+startDate+"' <= h.`_timestamp` AND '"+endDate+"' >= h.`_timestamp` ";
//				}
//				
//				String SQL = "SELECT * FROM " + dao.table() + " AS i, " + table + " AS h "
//						+ " WHERE "
//						+ "h.`_object_business_id` LIKE '"+businessId+"%'" 
//						+ fSQL
//						+ " AND h.`_instance_id`=h.`_initial_instance_id`"
//						+ " AND i.`"+dao.tabledef()[0][0]+"`=h.`_object_id`";
//				
//				if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
//				
//				dbs = conn.createStatement();
//				
//				rs = dbs.executeQuery(SQL);		
//				
//				if(rs.next()) {
//					o = (E) dao.rsToI(rs, log);
//					o.setHistory(rsToI(rs,log));
//					
//					retVal = 1;
//					if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" modified ]----";LOG.info(String.format(fmt,_f,msg));}
//				}
//			} catch(SQLException e) {
//				if(log && !Util._IN_PRODUCTION){msg = "----[ SQLException! database transaction failed.]----";LOG.info(String.format(fmt, _f,msg));}
//				e.printStackTrace();
//				//throw e;
//			} finally {
//				if(log && !Util._IN_PRODUCTION){msg = "----[ releasing connection]----";LOG.info(String.format(fmt, _f,msg));}
//				Util.DB.__release(conn,log);
//				Util.DB._releaseRS(rs, log);
//				Util.DB._releaseStatement(dbs, log);
//			}
			
			if (log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
				msg = "====[_loadFirst completed.]====";
				LOG.info(String.format(fmt, ("<<<<<<<<<" + _f).toUpperCase(), msg + time));
			}

			return o;
		}

		public static <T extends IsObjectInstance<T>> String insertValues(T o, boolean useObjectId) {
			String values = ((useObjectId)?o.getObjectId():o.id()) + ", " 
			+ "'" + ((useObjectId)?o.getObjectBusinessId():o.onlyBusinessId()) + "', " 
			+ ((useObjectId)?o.getObjectHistory().objectRefId():o.history().objectRefId()) + ", "
			+ "'" +((useObjectId)?o.getObjectHistory().id().id():o.history().id().id()) + "', " 
			+ "'" + ((useObjectId)?o.getObjectHistory().currentId().id():o.history().currentId().id()) + "', " 
			+ "'" + ((useObjectId)?o.getObjectHistory().initialId().id():o.history().initialId().id()) + "', " 
			+ "'" + o.status().name() + "', " 
			+ "'" + ((useObjectId)?o.getObjectHistory().timestamp():o.history().timestamp()) + "', "
			+ "'" + ((useObjectId)?o.getObjectHistory().date():o.history().date()) + "', "
			+ "'" + ((useObjectId)?o.getObjectHistory().endOfLife():o.history().endOfLife()).toString() + "', "
			+ "'" + o.version().value() + "', "
			+ "'" + o.group().name() + "'";
			return values;
		}


		@Override
		public int createTable(boolean log) {
			log = false;
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if (log && !Util._IN_PRODUCTION) {
				_f = "[_createTable]";
				msg = "====[ Creating history tables .... ]====";
				LOG.info(String.format(fmt, _f, msg));
			}

			Util.DB._createDatabases(log);

			Util.DB.dbActionType action = Util.DB.dbActionType.CREATE;
			if (log && !Util._IN_PRODUCTION) {
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

			Connection conn = null;
			Statement dbs = null;
			int retVal = 0;
			try {
				conn = Util.DB.__conn(log);

				dbs = conn.createStatement();
				retVal = dbs.executeUpdate(SQL);

			} catch (Exception e) {
				msg = "SQL Exception Happend!!!";
				if (log && !Util._IN_PRODUCTION)
					LOG.info(String.format(fmt, _f, msg));
				e.printStackTrace();
			} finally {
				if (log && !Util._IN_PRODUCTION) {
					msg = "----[Releasing Connection]----";
					LOG.info(String.format(fmt, _f, msg));
				}
				Util.DB.__release(conn, log);
				Util.DB._releaseStatement(dbs, log);
			}

			if (log && !Util._IN_PRODUCTION) {
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
				
				String SQL = "DELETE FROM "+table;
				
				if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
				
				dbs = conn.createStatement();
				
				retVal = dbs.executeUpdate(SQL);
				
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+"  ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
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
}
