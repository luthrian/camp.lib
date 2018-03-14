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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.data.CampSQL;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;
import com.camsolute.code.camp.lib.models.CampStatesInterface.IOAction;
import com.camsolute.code.camp.lib.models.process.OrderProcess;
import com.camsolute.code.camp.lib.models.process.OrderProcessList;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessDao;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.models.process.ProductAttributeProcess;
import com.camsolute.code.camp.lib.models.process.ProductAttributeProcessList;
import com.camsolute.code.camp.lib.types.*;
import com.camsolute.code.camp.lib.utilities.Util;
import com.camsolute.code.camp.lib.utilities.Util.DB;
import com.camsolute.code.camp.lib.utilities.Util.DB.dbActionType;

public class  AttributeDao implements AttributeDaoInterface{

    private static final Logger LOG = LogManager.getLogger(AttributeDao.class);
    private static String fmt = "[%15s] [%s]";

    // SQL table details
    public static String dbName = CampSQL.database[CampSQL._SYSTEM_TABLES_DB_INDEX];
    public static String valueDbName = CampSQL.database[CampSQL._ORDER_TABLES_DB_INDEX];

    public static String table = CampSQL.sysTable(CampSQL._SYSTEM_TABLES_DB_INDEX, CampSQL.System._ATTRIBUTE_TYPE_INDEX);

    public static String[][] tabledef = CampSQL.System._attribute_type_table_definition;

    public static String updatestable = CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._ATTRIBUTE_UPDATES_INDEX);

    public static String[][] updatestabledef = CampSQL.System._attribute_updates_table_definition;

    public static String valuetable = CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._ATTRIBUTE_VALUE_INDEX);

    public static String[][] valuetabledef = CampSQL.System._attribute_value_table_definition;

    public static String ahptable = CampSQL.sysTable(CampSQL._ORDER_TABLES_DB_INDEX, CampSQL.System._ATTRIBUTE_HAS_PROCESS_INDEX);

    public static String[][] ahptabledef = CampSQL.System._attribute_has_process_table_definition;

    // SQL table select queries
    public static String loadVByIdSQL = "SELECT * FROM " + valuetable + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`attribute_type_id`=%s AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+valuetabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`attribute_businesskey`";//`"+valuetabledef[0][0]+"`=%s";
    public static String loadVByIOidSQL = "SELECT * FROM " + valuetable + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`object_id`=%s AND t.`attribute_type_id`=%s AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+valuetabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`attribute_businesskey`";
    public static String loadVByOidSQL = "SELECT * FROM " + valuetable + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`object_id`=%s AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+valuetabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`='%s' AND i.`_object_business_id`=t.`attribute_businesskey`";
    public static String loadVByKeySQL = "SELECT * FROM " + valuetable + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`attribute_businesskey`='%s' AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+valuetabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`attribute_businesskey`";
    public static String loadListVByGroupSQL = "SELECT * FROM " + valuetable + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`object_id`=%s AND i.`_group_name`='%s' AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+valuetabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`attribute_businesskey`";
    public static String loadVByGroupSQL = "SELECT * FROM " + valuetable + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`object_id`=%s AND i.`_group_name`='%s' AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+valuetabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`attribute_businesskey`";
    public static String loadVByGroupVersionSQL = "SELECT * FROM " + valuetable + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`object_id`=%s AND i.`_group_name`='%s' AND i.`_version_value`='%s' AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+valuetabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`attribute_businesskey`";
    public static String loadVByPosGTSQL = "SELECT * FROM " + valuetable + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`object_id`=%s AND t.`attribute_position`>%s AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+valuetabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`attribute_businesskey`";
    public static String loadVByPosLTSQL = "SELECT * FROM " + valuetable + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`object_id`=%s AND t.`attribute_position`<=%s AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+valuetabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`attribute_businesskey`";
    public static String loadVByPosRangeSQL = "SELECT * FROM " + valuetable + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`object_id`=%s AND t.`attribute_position`>%s AND t.`attribute_position`<=%s AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+valuetabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`attribute_businesskey`";
    public static String loadByIdSQL = "SELECT * FROM " + table + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`"+tabledef[0][0]+"`=%s AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+tabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`business_id";
    public static String loadByNameSQL = "SELECT * FROM " + table + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`name`='%s' AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+tabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`business_id";
    public static String loadByKeySQL = "SELECT * FROM " + table + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`businesskey`='%s' AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+tabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`business_id";
    public static String loadByTypeSQL = "SELECT * FROM " + table + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`type`='%s' AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+tabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`business_id";
    public static String loadListByGroupSQL = "SELECT * FROM " + table + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`parent_id`=0 AND i.`_group_name`='%s' AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+tabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`business_id";
    public static String loadByGroupSQL = "SELECT * FROM " + table + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`parent_id`=%s AND i.`_group_name`='%s' AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+tabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`business_id";
    public static String loadListByGroupVersionSQL = "SELECT * FROM " + table + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`parent_id`=0 AND i.`_group_name`='%s' AND i.`_version_value`='%s' AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+tabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`business_id";
    public static String loadByGroupVersionSQL = "SELECT * FROM " + table + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`parent_id`=%s AND i.`_group_name`='%s' AND i.`_version_value`='%s' AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+tabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`business_id";
    public static String loadByPosGTSQL = "SELECT * FROM " + table + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`parent_id`=%s AND t.`position`>%s AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+tabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`business_id";
    public static String loadByPosLTSQL = "SELECT * FROM " + table + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`parent_id`=%s AND t.`position`<=%s AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+tabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`business_id";
    public static String loadByPosRangeSQL = "SELECT * FROM " + table + " AS t, "+CampInstanceDao.table+" AS i WHERE t.`parent_id`=%s AND t.`position`>%s AND t.`position`<=%s AND i.`_instance_id`=i.`_current_instance_id` AND t.`"+tabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`business_id";
    public static String loadAllSQL = "SELECT * FROM " + table + " AS t, "+CampInstanceDao.table+" AS i WHERE i.`_instance_id`=i.`_current_instance_id` AND t.`"+tabledef[0][0]+"`=i.`_object_id` AND i.`_object_business_id`=t.`business_id";

    private static AttributeDao instance = null;
		
		private AttributeDao(){
		}
		
		public static AttributeDao instance(){
			if(instance == null) {
				instance = new AttributeDao();
			}
			return instance;
		}
		

		// DEFINITION ASPECTS
    /**
     * request to load the definition aspects of an attribute by its id
     *
     * @param id id of the attribute to be loaded
     * @param log <code>Boolean</code> flag to switch on logging
     * @return <code>Attribute</code>
     */
    @Override
    public Attribute<?> loadById(int id,boolean log) {
      return _loadById(id, log);
    }

    public static Attribute<?> _loadById(int id, boolean log) {
      Attribute<?> at = _loadAttribute(String.format(loadByIdSQL, id), log);
      at.states().ioAction(IOAction.LOAD);
      return at;
    }

    /**
     * request to load the definition aspects of an attribute by its businessId (name)
     *
     * @param businessId business id  of the attribute to be loaded
     * @param log <code>Boolean</code> flag to switch on logging
     * @return <code>Attribute</code> loaded
     */
    @Override
    public Attribute<?> loadByBusinessId(String businessId,boolean log) {
      return _loadByBusinessId(businessId, !Util._IN_PRODUCTION);
    }

    public static Attribute<?> _loadByBusinessId(String businessId, boolean log) {
      Attribute<?> at = _loadAttribute(String.format(loadByNameSQL, businessId), log);
      at.states().ioAction(IOAction.LOAD);
      return at;
    }

    /**
     * request to load the definition aspects of an attribute by its businessKey
     *
     * @param businessKey to load the definition aspects of an attribute by
     * @param log <code>Boolean</code> flag to switch on logging
     * @return list of attributes  
     */
      @SuppressWarnings("unchecked")
			@Override
      public AttributeList loadListByBusinessKey(String businessKey, boolean log) {
      	return  _loadListByBusinessKey(businessKey, log);
      }

      public static AttributeList _loadListByBusinessKey(String businessKey, boolean log) {
        AttributeList al = _loadAttributeList(String.format(loadByKeySQL, businessKey), log);
        for(Attribute<?> at:al) {
        	at.states().ioAction(IOAction.LOAD);
        }
        return al;
      }

    /**
     * request to load the definition aspects of an attribute by its group affiliation
     *
     * @param group group affiliation to load the definition aspects of an attribute by
     * @param log <code>Boolean</code> flag to switch on logging
     * @return list of attributes  
     */
      @SuppressWarnings("unchecked")
			@Override
      public AttributeList loadListByGroup(String group, boolean log) {
      	return  _loadListByGroup(group, log);
      }

      public static AttributeList _loadListByGroup(String group, boolean log) {
        AttributeList al = _loadAttributeList(String.format(loadListByGroupSQL, group), log);
        for(Attribute<?> at:al) {
        	at.states().ioAction(IOAction.LOAD);
        }
        return al;
      }

    /**
     * request to load the definition aspects of an attribute by its group affiliation and version
     *
     * @param group group affiliation by which to load the definition aspects of an attribute
     * @param version version value by which to load the definition aspects of an attribute
     * @param log <code>Boolean</code> flag to switch on logging
     * @return list of attributes  
     */
      @SuppressWarnings("unchecked")
			@Override
      public AttributeList loadListByGroupVersion(String group, String version, boolean log) {
      	return  _loadListByGroupVersion(group, version, log);
      }

      public static AttributeList _loadListByGroupVersion(String group, String version, boolean log) {
        AttributeList al = _loadAttributeList(String.format(loadListByGroupVersionSQL, group, version), log);
        for(Attribute<?> at:al) {
        	at.states().ioAction(IOAction.LOAD);
        }
        return al;
      }


    
    @Override
    public AttributeList loadList() {
      return _loadList(Util._IN_PRODUCTION);
    }

    public static AttributeList _loadList(boolean log) {
    	//TODO: should list result be assembled to the root attribute aspect here and a campMap returned instead of AttributeList
      AttributeList al = _loadAttributeList(loadAllSQL, log);
      for(Attribute<?> at:al) {
      	at.states().ioAction(IOAction.LOAD);
      }
      return al;
    }

    
    @Override
    public AttributeList loadList(AttributeType type) {
      return _loadList(type, !Util._IN_PRODUCTION);
    }

    public static AttributeList _loadList(AttributeType type, boolean log) {
    	//TODO: should list result be assembled to the root attribute aspect here and a campMap returned instead of AttributeList
      AttributeList al = _loadAttributeList(String.format(loadByTypeSQL, type.name()), log);
      for(Attribute<?> at:al) {
      	at.states().ioAction(IOAction.LOAD);
      }
      return al;
    }

   
    @Override
    public Attribute<?> create(int parentId, String name, AttributeType type, String businessId, String businessKey, String group, String version, String defaultValue) {
      return _create(parentId, name, type, businessId, businessKey, group, version, defaultValue, false);
    }

    public static <X extends Value<?>> Attribute<?> _create(int pid, String name, AttributeType type, String businessId, String businessKey, String group, String version, String defaultValue, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[createAttribute]";
        msg = "====[ create and save attribute type '" + name + "'/'" + type + "' ]====";
        LOG.info(String.format(fmt, _f, msg));
      }

      Attribute<X> a = null;

      a = Attribute.createAttribute(name, type, defaultValue);
      a.setGroup(group);
      a.setBusinessId(businessId);
      a.setBusinessKey(businessKey);
      a.setVersion(version);
      a.parentId(pid);
      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = DB.__conn(log);

        String SQL =
            "INSERT INTO "
                + table
                + "( "
                + DB._columns(tabledef, DB.dbActionType.INSERT, log)
                + " ) VALUES ( "
                + insertDefinitionValues(a)
                + " )";

        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ SQL: " + SQL + "]----";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();

        retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);

        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ saved '" + retVal + "' entry]----";
          LOG.info(String.format(fmt, _f, msg));
        }

        rs = dbs.getGeneratedKeys();
        // add the technical (ie. database table) id value to the attribute object x
        if (rs.next()) {

          a.updateId(rs.getInt("_attribute_type_id_"));

        } else {
          // TODO: throw an exception from here
        }
        a.history().stamptime();
        a.cleanStatus(a);
//        a.history().updateInstance();
        CampInstanceDao.instance()._addInstance(a, false, log);
        a.states().ioAction(IOAction.SAVE);
      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ EXCEPTION! Save failed.]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ Releasing Connection ]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type created.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return a;
    }

    /**
     * persist the definition aspect of an attribute.
     *
     * @param a the Attribute to save
     * @param log <code>Boolean</code> flag to switch on logging
     * @return persisted <code>Attribute</code>  
     */
    @Override
    public Attribute<?> save(Attribute<?> a, boolean log) {
      return _save(a, log);
    }

    public static Attribute<?> _save(Attribute<?> a, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_save]";
        msg = "====[ save definition aspects of attribute  ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }
      if (a == null) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ERROR! Attribute parameter 'a' is null]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        return a;
      }
      // '"+ct.name()+"'/'"+ct.type()+"'
      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);

        dbs = conn.createStatement();

        String vSQL =
            "INSERT INTO "
                + table
                + "( "
                + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
                + " ) "
                + "VALUES ( "
                + insertDefinitionValues(a)
                + " )";

        retVal = dbs.executeUpdate(vSQL, Statement.RETURN_GENERATED_KEYS);

        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" saved]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        rs = dbs.getGeneratedKeys();

        if (rs.next()) {

          a.updateId(rs.getInt(tabledef[0][0]));

        } else {
          // TODO: throw an exception from here
        }
       
        boolean hasValue = (a.value() != null && a.value().value() != null);
        switch(a.attributeType()) {
        case _complex:
          	if(hasValue)
        	a = _saveComplexChildren((CampComplex)a, log);
        	break;
        case _table:
          	if(hasValue)
        	a = _saveTableChildren((CampTable)a, log);
        	break;
        case _map:
          	if(hasValue)
        	a = _saveMapChildren((CampMap)a, log);
        	break;
        case _list:
          	if(hasValue)
        	a = _saveListChildren((CampList)a, log);
        	break;
        	default:
        		break;
        }
        
 
        a.history().stamptime();
        a.cleanStatus(a);
        CampInstanceDao.instance()._addInstance(a, false, log);

      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ EXCEPTION! Save failed.]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = " -- -- [ Releasing Connection ]";
          LOG.info(String.format(fmt, _f, msg));
        }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ save completed ]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return a;
    }

    /**
     * persist a list of attribute definition aspects.
     *
     * @param attributeList save a list of attribute definition
     * @param log <code>Boolean</code> flag to switch on logging
     * @return list of attributes with updated id's
     */
    @SuppressWarnings("unchecked")
		@Override
    public <E extends ArrayList<Attribute<?>>> E saveList(E attributeList, boolean log) {
      return (E) _saveList((AttributeList)attributeList, log);
    }

    public static AttributeList _saveList(AttributeList attributeList, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_saveList]";
        msg = "====[ save list of attribute types ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;
      try {

        conn = Util.DB.__conn(log);
        
        String SQL =
            "INSERT INTO "
                + table
                + "( "
                + Util.DB._columns(tabledef, Util.DB.dbActionType.INSERT, log)
                + " ) VALUES "+insertDefinitionValues(attributeList);
        
        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ SQL: " + SQL + "]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();

        retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);

        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ saved '" + retVal + "' entries]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        rs = dbs.getGeneratedKeys();

        int counter = 0;

        while (rs.next()) {

          Attribute<?> ct = attributeList.get(counter);
          ct.updateId( rs.getInt(tabledef[0][0])); // TODO: FIXME: is this safe (ie is saveList.get(counter) and rs.next() always in sync)
          counter++;
        }

        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" saved ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
        for(Attribute<?> a: attributeList) {
        	boolean hasValue = (a.value() != null && a.value().value() != null);
          switch(a.attributeType()) {
          case _complex:
          	if(hasValue)
          	a = _saveComplexChildren((CampComplex)a, log);
          	break;
          case _table:
          	if(hasValue)
          	a = _saveTableChildren((CampTable)a, log);
          	break;
          case _map:
          	if(hasValue)
          	a = _saveMapChildren((CampMap)a, log);
          	break;
          case _list:
          	if(hasValue)
          	a = _saveListChildren((CampList)a, log);
          	break;
          	default:
          		break;
          }
        }

        for(Attribute<?> a: attributeList) {
        	a.history().stamptime();
        }
        CampInstanceDao.instance()._addInstances(attributeList, false, log);
        
        for(Attribute<?> a: attributeList) {
        	a.states().ioAction(IOAction.SAVE);
        }
      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ EXCEPTION! Save failed.]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = " -- -- [ Releasing Connection ]";
          LOG.info(String.format(fmt, _f, msg));
        }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }

      //		attributeList.addAll(saveList);

      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ saveList completed ]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }

      return attributeList;
    }

    /**
     * updates the definition aspect of an attribute which has been persisted.
     *
     * @param attribute: the <code>Attribute&lt;?&gt;</code> to be updated
     * @return the updated attribute
     */
    @Override
    public Attribute<?> update(Attribute<?> attribute, boolean log) {
      return _update(attribute, log);
    }

    public static Attribute<?> _update(Attribute<?> attribute, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_update]";
        msg = "====[ update attribute '" + attribute.name() + "'/'" + attribute.attributeType().name() + "' ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);

        String fSQL =
            "UPDATE "
                + table
                + " SET "
                + Util.DB._columns(tabledef, Util.DB.dbActionType.UPDATE, log)
                + " WHERE `"
                + tabledef[0][0]
                + "`=%s";

        if (log && !Util._IN_PRODUCTION) { msg = "----[ pre-format SQL:" + fSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

        String SQL = insertDefinitionUpdates(fSQL,attribute);
        
        if (log && !Util._IN_PRODUCTION) { msg = "----[ Formatted SQL: " + SQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

        dbs = conn.createStatement();
        retVal = dbs.executeUpdate(SQL);

        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" updated ]----"; LOG.info(String.format(fmt, _f, msg)); }

        boolean hasValue = (attribute.value() != null && attribute.value().value() != null);
        int cretVal = 0;
        switch(attribute.attributeType()) {
        case _complex:
        	if(hasValue) 
        		cretVal = _updateComplexChildren((CampComplex)attribute, log);
        	break;
        case _table:
          	if(hasValue)
        	cretVal = _updateTableChildren((CampTable)attribute, log);
        	break;
        case _map:
          	if(hasValue)
        	cretVal = _updateMapChildren((CampMap)attribute, log);
        	break;
        case _list:
          	if(hasValue)
        	cretVal = _updateListChildren((CampList)attribute, log);
        	break;
        	default:
        		break;
        }
        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + cretVal + "' child attribute entr"+((cretVal>1)?"ies":"y")+" updated  ]----"; LOG.info(String.format(fmt, _f, msg)); }

        attribute.history().stamptime();
        attribute.history().updateInstance();
        attribute.cleanStatus(attribute);
        CampInstanceDao.instance()._addInstance(attribute, false, log);
        
        attribute.states().ioAction(IOAction.UPDATE);
      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) { msg = "----[ EXCEPTION! Update failed.]-- --"; LOG.info(String.format(fmt, _f, msg)); }
        e.printStackTrace();
        return null;
      } finally {
        if (log && !Util._IN_PRODUCTION) { msg = "----[ Releasing Connection ]"; LOG.info(String.format(fmt, _f, msg)); }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ update completed. ]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return attribute;
    }

    /**
     * update the definition aspects of a list of attributes that have been persisted.
     *
     * @param attributeList list of attributes to be persisted
     * @param log <code>Boolean</code> flag to switch on logging
     * @return list of attributes with updated id's
     */
    @SuppressWarnings("unchecked")
		@Override
    public <E extends ArrayList<Attribute<?>>> E updateList(E attributeList, boolean log) {
      return (E) _updateList((AttributeList) attributeList, log);
    }

    public static AttributeList _updateList(AttributeList attributeList, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_updateList]";
        msg = "====[ update definition aspects of a list of attribute objects ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);

        String fSQL =
            "UPDATE "
                + table
                + " SET "
                + Util.DB._columns(tabledef, Util.DB.dbActionType.UPDATE, log)
                + " WHERE `"
                + tabledef[0][0]
                + "`=%s";

        if (log && !Util._IN_PRODUCTION) { msg = "----[ pre-formatted SQL:" + fSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

        dbs = conn.createStatement();

        for (Attribute<?> a : attributeList) {

          String SQL = insertDefinitionUpdates(fSQL,a);

          if (log && !Util._IN_PRODUCTION) { msg = "----[ Formatted SQL:" + SQL + " ]----";
            LOG.info(String.format(fmt, _f, msg));
          }

          dbs.addBatch(SQL);
        }
        retVal = Util.Math.addArray(dbs.executeBatch());

        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" updated ]----"; LOG.info(String.format(fmt, _f, msg)); }

        for(Attribute<?> a: attributeList) {
        	boolean hasValue = (a.value() != null && a.value().value() != null);
          int cretVal = 0;
          switch(a.attributeType()) {
          case _complex:
          	if(hasValue) 
          		cretVal = _updateComplexChildren((CampComplex)a, log);
          	break;
          case _table:
          	if(hasValue)
          	cretVal = _updateTableChildren((CampTable)a, log);
          	break;
          case _map:
          	if(hasValue)
          	cretVal = _updateMapChildren((CampMap)a, log);
          	break;
          case _list:
          	if(hasValue)
          	cretVal = _updateListChildren((CampList)a, log);
          	break;
          	default:
          		break;
          }
          if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + cretVal + "' child attribute entr"+((cretVal>1)?"ies":"y")+" updated  ]----"; LOG.info(String.format(fmt, _f, msg)); }
        }
        
        for(Attribute<?> a: attributeList) {
        	a.history().stamptime();
        	a.history().updateInstance();
        }
        CampInstanceDao.instance()._addInstances(attributeList, false, log);
        for(Attribute<?> a: attributeList) {
        	a.states().ioAction(IOAction.UPDATE);//TODO: see notes why we put this in
        }
      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) { msg = "----[ EXCEPTION! Update failed.]----"; LOG.info(String.format(fmt, _f, msg)); }
        e.printStackTrace();
        return null;
      } finally {
        if (log && !Util._IN_PRODUCTION) { msg = "----[ Releasing Connection ]----"; LOG.info(String.format(fmt, _f, msg)); }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ updateList completed. ]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return attributeList;
    }

    
    @Override
    public int delete(int id) {
      return _delete(id, !Util._IN_PRODUCTION);
    }

    public static int _delete(int id, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[delete]";
        msg = "====[ deleting definition aspects of attribute by id '" + id+ "' ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);

        String SQL = "DELETE FROM " + table + " WHERE `"+tabledef[0][0]+"`=" + id;

        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ SQL" + SQL + "]----";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();

        retVal = dbs.executeUpdate(SQL);

        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) { msg = "----[ EXCEPTION! Update failed.]----"; LOG.info(String.format(fmt, _f, msg)); }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) { msg = "----[ Releasing Connection ]----"; LOG.info(String.format(fmt, _f, msg)); }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ delete completed. ]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    
    @Override
    public int delete(String attributeName) {
      return _delete(attributeName, !Util._IN_PRODUCTION);
    }

    public static int _delete(String name, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[delete]";
        msg = "====[ deleting definition aspects of attribute by attribute Name '" + name+ "' ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);

        String SQL = "DELETE FROM " + table + " WHERE `name`='" + name+"'";

        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ SQL" + SQL + "]----";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();

        retVal = dbs.executeUpdate(SQL);

        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) { msg = "----[ EXCEPTION! Update failed.]----"; LOG.info(String.format(fmt, _f, msg)); }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) { msg = "----[ Releasing Connection ]----"; LOG.info(String.format(fmt, _f, msg)); }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ delete completed. ]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }
    
    
    @Override
    public int delete(Attribute<?> attribute) {
      return _delete(attribute, !Util._IN_PRODUCTION);
    }

    public static int _delete(Attribute<?> attribute, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[delete]";
        msg = "====[ deleting definition aspects of attribute by attribute Name '" + attribute.name()+ "' ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);

        String SQL = "DELETE FROM " + table + " WHERE `"+tabledef[0][0]+"`='" + attribute.id()+"'";

        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ SQL" + SQL + "]----";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();

        retVal = dbs.executeUpdate(SQL);

        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }

        boolean hasValue = (attribute.value() != null && attribute.value().value() != null);
        int cretVal = 0;
        switch(attribute.attributeType()) {
        case _complex:
          	if(hasValue)
        	cretVal = _deleteComplexChildrenValue(0, (CampComplex)attribute, true, false, log);
        	break;
        case _table:
          	if(hasValue)
        	cretVal = _deleteTableChildrenValue(0, (CampTable)attribute, true, false, log);
        	break;
        case _map:
          	if(hasValue)
        	cretVal = _deleteMapChildrenValue(0, (CampMap)attribute, true, false, log);
        	break;
        case _list:
          	if(hasValue)
        	cretVal = _deleteListChildrenValue(0, (CampList)attribute, true, false, log);
        	break;
        	default:
        		break;
        }
        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + cretVal + "' child attribute entr"+((cretVal>1)?"ies":"y")+" deleted  ]----"; LOG.info(String.format(fmt, _f, msg)); }
        attribute.states().ioAction(IOAction.DELETE); // TODO: see notes about this
      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) { msg = "----[ EXCEPTION! Update failed.]----"; LOG.info(String.format(fmt, _f, msg)); }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) { msg = "----[ Releasing Connection ]----"; LOG.info(String.format(fmt, _f, msg)); }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ delete completed. ]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    
    @Override
    public int deleteList(AttributeList attributeList) {
      return _deleteList(attributeList, !Util._IN_PRODUCTION);
    }

    public static int _deleteList(AttributeList atl, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[deleteList]";
        msg = "====[ deleting the definition aspects of a list of attribute objects ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);

        dbs = conn.createStatement();

        for (Attribute<?> a : atl) {
          String SQL = "DELETE FROM " + table + " WHERE `"+tabledef[0][0]+"`=" + a.id();

          if (log && !Util._IN_PRODUCTION) { msg = "----[ SQL:" + SQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

          dbs.addBatch(SQL);
        }

        retVal = Util.Math.addArray(dbs.executeBatch());
        
        for(Attribute<?> a: atl) {// TODO: see notes
        	a.states().ioAction(IOAction.DELETE);
        }
        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
			
        for(Attribute<?> attribute: atl) {
        	boolean hasValue = (attribute.value() != null && attribute.value().value() != null);
          int cretVal = 0;
          switch(attribute.attributeType()) {
          case _complex:
          	if(hasValue)
          	cretVal = _deleteComplexChildrenValue(0, (CampComplex)attribute, true, false, log);
          	break;
          case _table:
          	if(hasValue)
          	cretVal = _deleteTableChildrenValue(0, (CampTable)attribute, true, false, log);
          	break;
          case _map:
          	if(hasValue)
          	cretVal = _deleteMapChildrenValue(0, (CampMap)attribute, true, false, log);
          	break;
          case _list:
          	if(hasValue)
          	cretVal = _deleteListChildrenValue(0, (CampList)attribute, true, false, log);
          	break;
          	default:
          		break;
          }
          if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + cretVal + "' child attribute entr"+((cretVal>1)?"ies":"y")+" deleted  ]----"; LOG.info(String.format(fmt, _f, msg)); }

        }

      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ EXCEPTION! Update failed.]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ Releasing Connection ]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ deleteList completed.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    
    @Override
    public int deleteList(int rootId) {
      return _deleteList(rootId, !Util._IN_PRODUCTION);
    }

    public static int _deleteList(int rootId, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[deleteList]";
        msg = "====[ deleting the definition aspects of a list of attribute objects ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);

        dbs = conn.createStatement();

          String SQL = "DELETE FROM " + table + " WHERE `"+tabledef[0][0]+"`=" + rootId + " OR `parent_id`="+rootId;

          if (log && !Util._IN_PRODUCTION) { msg = "----[ SQL:" + SQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

        retVal = dbs.executeUpdate(SQL);
        
        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ EXCEPTION! Update failed.]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ Releasing Connection ]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ deleteList completed.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    
    @Override
    public AttributeMap loadByObjectId(int objectId) {
      return _loadByObjectId(objectId, !Util._IN_PRODUCTION);
    }

    public static AttributeMap _loadByObjectId(int oid, boolean log) {
    	//TODO: should list result be assembled to the root attribute aspect here and a campMap returned instead of AttributeList
    	AttributeMap r = new AttributeMap();
      AttributeList al = _loadAllAttributes(oid,log);
      HashMap<Integer,Attribute<?>> t = new HashMap<Integer,Attribute<?>>();
      // first pass add root attributes
      for(Attribute<?> a:al) {
      	a.states().ioAction(IOAction.LOAD);
      	if(a.parentId() == 0 && a.attributeParentId() == 0) {
      		t.put(a.id(), a);
      	}
      }
      // second pass sort in children
      for(int id: t.keySet()) {
      	for(Attribute<?> a: al) {
      		if(a.parentId()== id) {
      			switch(t.get(id).attributeType()) {
      			case _complex:
      				if(((CampComplex)t.get(id)).value().value() == null) {
      					((CampComplex)t.get(id)).value().setValue(new HashMap<String,ArrayList<Attribute<?>>>());
      				}
      				if(!((CampComplex)t.get(id)).value().value().containsKey(a.group().name())) {
      					((CampComplex)t.get(id)).value().value().put(a.group().name(), new ArrayList<Attribute<?>>());
      				}
      				((CampComplex)t.get(id)).value().value().get(a.group().name()).add(a);//TODO think about sorting if need be else let display etc handle this 
      				break;
      			case _table://TODO: fix this make value ArrayList<CampList> CampList is a column and not a row see notes
      				if(((CampTable)t.get(id)).value().value()==null) {
      					((CampTable)t.get(id)).value().setValue(new ArrayList<ArrayList<Attribute<?>>>());
      				}
      				if(((CampTable)t.get(id)).value().value().get(a.value().position().posY()) == null) {
      					((CampTable)t.get(id)).value().value().add(a.value().position().posY(),new ArrayList<Attribute<?>>());
      				}
      				((CampTable)t.get(id)).value().value().get(a.value().position().posY()).add(a);
      				break;      				
      			case _map:
      				if(((CampMap)t.get(id)).value().value() == null) {
      					((CampMap)t.get(id)).value().setValue(new HashMap<String,Attribute<?>>());
      				}
      				((CampMap)t.get(id)).value().value().put(a.group().name(),a); 
      				break;
      			case _list:
      				if(((CampList)t.get(id)).value().value()==null) {
      					((CampList)t.get(id)).value().setValue(new ArrayList<Attribute<?>>());
      				}
      				((CampList)t.get(id)).value().value().add(a);
      				break;
      				default:
      					break;
      			}
      		}
      	}
      }
      
      for(int id: t.keySet()) {
      	if(!r.containsKey(t.get(id).group().name())) {
      		r.put(t.get(id).group().name(),new AttributeList());
      	}
      	r.get(t.get(id).group().name()).add(t.get(id));
      }

      return r;
    }

    
    @Override
    public AttributeList loadGroup(int parentId, String groupName) {
      return _loadGroup(parentId, groupName, false);
    }

    public static AttributeList _loadGroup(int parentId, String groupName, boolean log) {
    	String SQL = String.format(loadByGroupSQL, parentId, groupName);
    	if(log && !Util._IN_PRODUCTION){String msg = "----[SQL : "+SQL+"]----";LOG.info(String.format(fmt,"_loadGroup",msg));}
      AttributeList al = _loadAttributeList(SQL, log);
      for(Attribute<?> at:al) {
      	at.states().ioAction(IOAction.LOAD);
      }
      return al;
    }

    
    @Override
    public AttributeList loadAfterPosition(int id, int position) {
      return _loadAfterPosition(id, position, !Util._IN_PRODUCTION);
    }

    public static AttributeList _loadAfterPosition(int id, int pos, boolean log) {
      AttributeList al = _loadAttributeList(String.format(loadByPosGTSQL, id, pos), log);
      for(Attribute<?> at:al) {
      	at.states().ioAction(IOAction.LOAD);
      }
      return al;
    }

    
    @Override
    public AttributeList loadRange(int id, int startPosition, int endPosition) {
      return _loadRange(id, startPosition, endPosition, !Util._IN_PRODUCTION);
    }

    public static AttributeList _loadRange(int id, int spos, int epos, boolean log) {
      AttributeList al = _loadAttributeList(String.format(loadByPosRangeSQL, id, spos, epos), log);
      for(Attribute<?> at:al) {
      	at.states().ioAction(IOAction.LOAD);
      }
      return al;
    }

    
    @Override
    public AttributeList loadBeforePosition(int id, int position) {
      return _loadBeforePosition(id, position, !Util._IN_PRODUCTION);
    }

    public static AttributeList _loadBeforePosition(int id, int pos, boolean log) {
      AttributeList al = _loadAttributeList(String.format(loadByPosLTSQL, id, pos), log);
      for(Attribute<?> at:al) {
      	at.states().ioAction(IOAction.LOAD);
      }
      return al;
    }

    public static Attribute<?> _loadAttribute(String SQL, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_loadAttribute]";
        msg = "====[ execute SQL query toload a single attribute object ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      Attribute<?> a = null;
      try {

        conn = Util.DB.__conn(log);

        dbs = conn.createStatement();
        
        rs = dbs.executeQuery(SQL);

        a = _rsToAttribute(rs);
        

      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ EXCEPTION! Update failed.]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = " -- -- [ Releasing Connection ]";
          LOG.info(String.format(fmt, _f, msg));
        }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }

      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ value loaded attribute type.]====";
        LOG.traceExit(String.format(fmt, _f, msg + time));
      }

      return a;
    }

    public static AttributeList _loadAttributeList(String SQL, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_loadAttributeList]";
        msg = "====[ execute SQL query to load the definition aspects of a list of attribute objects ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;
      
      AttributeList al = new AttributeList();
      try {

        conn = Util.DB.__conn(log);

        dbs = conn.createStatement();
        
        rs = dbs.executeQuery(SQL);

        al = _rsToAttributeList(rs);

      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) { msg = "----[ EXCEPTION! Update failed.]----"; LOG.info(String.format(fmt, _f, msg)); }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) { msg = "----[ Releasing Connection ]----"; LOG.info(String.format(fmt, _f, msg)); }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }

      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ value loaded attribute type.]====";
        LOG.traceExit(String.format(fmt, _f, msg + time));
      }

      return al;
    }


    //VALUE ASPECTS
    @Override
    public Attribute<?> create(int objectId, int parentId, String name, AttributeType type, String businessId, String businessKey, String attributeBusinessId, String group, String attributeGroup, String version, String defaultValue, Value<?> value) {
      return _create(objectId, parentId, name, type, businessId, businessKey, attributeBusinessId, group, attributeGroup, version, defaultValue, value, !Util._IN_PRODUCTION);
    }

    public static <X extends Value<?>> Attribute<X> _create(int oid, int pid, String name, AttributeType type, String businessId, String businessKey, String attributeBusinessKey, String group, String attributeGroup, String version, String defaultValue, X value, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[create]";
        msg = "====[ create and save attribute'" + name + "'/'" + type + "' ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      @SuppressWarnings("unchecked")
			Attribute<X> a = (Attribute<X>) _create(pid,name,type, businessId, businessKey, group, version, defaultValue, log);
      a.setAttributeBusinessKey(attributeBusinessKey);
      a.setAttributeGroup(attributeGroup);
      a.setValue(value);
      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);

        Value.ValueDao.save(oid, a.value(), log);
        if(a.value().id() == 0) {
        	if(log && !Util._IN_PRODUCTION) { msg = "ERROR! Attribute.Value.Id is 0!!!"; LOG.info(String.format(fmt, _f,msg));}
        }
        
        String vSQL = "INSERT INTO " + valuetable + "( " + Util.DB._columns(valuetabledef, Util.DB.dbActionType.INSERT, log) + " )"
                + " VALUES ( " + insertAttributeValues(oid,a) + " )";

        if (log && !Util._IN_PRODUCTION) { msg = "----[ SQL: " + vSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

        dbs = conn.createStatement();
        
        retVal = dbs.executeUpdate(vSQL, Statement.RETURN_GENERATED_KEYS);

        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ saved '" + retVal + "' entry]----";
          LOG.info(String.format(fmt, _f, msg));
        }

        rs = dbs.getGeneratedKeys();

        if (rs.next()) {

          a.attributeId(rs.getInt("_attribute_value_id_"));

        } else {
          // TODO: throw an exception from here
        }

        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" created ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
        a.valueHistory().stamptime();
        a.cleanStatus(a);
//        a.valueHistory().updateInstance();
        CampInstanceDao.instance()._addInstance(a, true, log);
        
        a.valueStates().ioAction(IOAction.SAVE);

      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ EXCEPTION! Save failed.]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = " -- -- [ Releasing Connection ]";
          LOG.info(String.format(fmt, _f, msg));
        }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type created.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return a;
    }

    @Override
    public Attribute<?> save(int objectId, Attribute<?> a) {
      return _save(objectId, a, false);
    }

    public static Attribute<?> _save(int oid, Attribute<?> a, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_save]";
        msg = "====[ save attribute type  ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }
      if (a == null) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ERROR! Attribute parameter 'a' is null]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        return a;
      }
      // '"+ct.name()+"'/'"+ct.type()+"'
      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);

        dbs = conn.createStatement();
        //TODO REVERSE THE SAVE ORDER
        Value<?> v = Value.ValueDao.save(oid, a.value(), log);
        a.valueId(v.id());
        if(a.value().id() == 0) {
	        if (log && !Util._IN_PRODUCTION) {
	          msg = "----[ERROR! Value.id is 0 - Value not saved]----";
	          LOG.info(String.format(fmt, _f, msg));
	        }
        }
        
        String vSQL =
            "INSERT INTO "
                + valuetable
                + "( "
                + Util.DB._columns(valuetabledef, Util.DB.dbActionType.INSERT, log)
                + " ) "
                + "VALUES ( "
                + insertAttributeValues(oid, a)
                + " )";

        retVal = dbs.executeUpdate(vSQL, Statement.RETURN_GENERATED_KEYS);

        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" saved]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        rs = dbs.getGeneratedKeys();

        if (rs.next()) {

          a.attributeId(rs.getInt("_attribute_value_id_"));

        } else {
          // TODO: throw an exception from here
        }
       
        boolean hasValue = (a.value() != null && a.value().value() != null);
        switch(a.attributeType()) {
        case _complex:
          	if(hasValue)
        	a = _saveComplexChildrenValue(oid, (CampComplex)a, log);
        	break;
        case _table:
          	if(hasValue)
        	a = _saveTableChildrenValue(oid, (CampTable)a, log);
        	break;
        case _map:
          	if(hasValue)
        	a = _saveMapChildrenValue(oid, (CampMap)a, log);
        	break;
        case _list:
          	if(hasValue)
        	a = _saveListChildrenValue(oid, (CampList)a, log);
        	break;
        	default:
        		break;
        }
        
        a.valueHistory().stamptime();
        a.cleanStatus(a);
//        a.valueHistory().updateInstance();
        CampInstanceDao.instance()._addInstance(a, true, log);
        
        a.valueStates().ioAction(IOAction.SAVE);
      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ EXCEPTION! Save failed.]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = " -- -- [ Releasing Connection ]";
          LOG.info(String.format(fmt, _f, msg));
        }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type created.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return a;
    }

    @Override
    public AttributeList saveList(int objectId, AttributeList attributeList) {
      return _saveList(objectId, attributeList, false);
    }

    public static AttributeList _saveList(int oid, AttributeList attributeList, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_saveList]";
        msg = "====[ save list of attribute types ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);
        
        ValueList vl = getValueList(attributeList);
        
        vl = Value.ValueDao.saveList(oid, vl, log);
        for(Value<?>v:vl) {
	        if(v.id() == 0) {
	  	        if (log && !Util._IN_PRODUCTION) { msg = "----[ERROR! Value not saved]----"; LOG.info(String.format(fmt, _f, msg)); }
	        }
        }
        attributeList = setValuesPreSave(attributeList,vl);
        String SQL =
            "INSERT INTO "
                + valuetable
                + "( "
                + Util.DB._columns(valuetabledef, Util.DB.dbActionType.INSERT, log)
                + " ) VALUES "+insertAttributeValues(oid,attributeList);
        
        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ SQL: " + SQL + "]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();

        retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);

        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
        rs = dbs.getGeneratedKeys();

        int counter = 0;

        while (rs.next()) {

          //				Attribute<?> ct = saveList.get(counter);//TODO: see savelist note: would destroy
          // order
          attributeList.get(counter).attributeId( rs.getInt(valuetabledef[0][0])); // TODO: FIXME: is this safe (ie is saveList.get(counter) and rs.next()
          // always in sync)
          counter++;
        }
        
        for(Attribute<?> a: attributeList) {
        	boolean hasValue = (a.value() != null && a.value().value() != null);
          switch(a.attributeType()) {
          case _complex:
          	if(hasValue)
          	a = _saveComplexChildrenValue(oid, (CampComplex)a, log);
          	break;
          case _table:
          	if(hasValue)
          	a = _saveTableChildrenValue(oid, (CampTable)a, log);
          	break;
          case _map:
          	if(hasValue)
          	a = _saveMapChildrenValue(oid, (CampMap)a, log);
          	break;
          case _list:
          	if(hasValue)
          	a = _saveListChildrenValue(oid, (CampList)a, log);
          	break;
          	default:
          		break;
          }
        }
        
        for(Attribute<?> a: attributeList) {
          a.valueHistory().stamptime();
          a.valueHistory().updateInstance();
        }
          CampInstanceDao.instance()._addInstances(attributeList, true, log);
        for(Attribute<?> a: attributeList) {
          a.valueStates().ioAction(IOAction.SAVE);
        }
      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ EXCEPTION! Save failed.]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = " -- -- [ Releasing Connection ]";
          LOG.info(String.format(fmt, _f, msg));
        }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }

      //		attributeList.addAll(saveList);

      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute type created.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }

      return attributeList;
    }

    
    @Override
    public int update(int objectId, Attribute<?> attribute) {
      return _update(objectId, attribute, false);
    }

    public static int _update(int oid, Attribute<?> attribute, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_update]";
        msg = "====[ update attribute '" + attribute.name() + "'/'" + attribute.attributeType().name() + "' ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);

        //TODO: does it make sense to add complexity here to reduce DB traffic/processing load or just keep it simple.. (tendency kiss)
       int vupdates = Value.ValueDao.update(oid, attribute.value(), log);
       if(vupdates == 0) {
      	 if(log && !Util._IN_PRODUCTION){msg = "----[ EXCEPTION! Attribute.Value was not updated. ]----";LOG.info(String.format(fmt, _f,msg));}
      	 return 0;
       }
        
        String fSQL =
            "UPDATE "
                + valuetable
                + " SET "
                + Util.DB._columns(valuetabledef, Util.DB.dbActionType.UPDATE, log)
                + " WHERE `"
                + valuetabledef[0][0]
                + "`=%s";

        if (log && !Util._IN_PRODUCTION) { msg = "----[ pre-format SQL:" + fSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

        String SQL = insertAttributeVUpdates(fSQL,oid, attribute);
        
        if (log && !Util._IN_PRODUCTION) { msg = "----[ Formatted SQL: " + SQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

        dbs = conn.createStatement();
        retVal = dbs.executeUpdate(SQL);

        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" updated ]----"; LOG.info(String.format(fmt, _f, msg)); }
       
        boolean hasValue = (attribute.value() != null && attribute.value().value() != null);
        int cretVal = 0;
        switch(attribute.attributeType()) {
        case _complex:
        	if(hasValue) 
        		cretVal = _updateComplexChildrenValue(oid,(CampComplex)attribute, log);
        	break;
        case _table:
          	if(hasValue)
        	cretVal = _updateTableChildrenValue(oid, (CampTable)attribute, log);
        	break;
        case _map:
          	if(hasValue)
        	cretVal = _updateMapChildrenValue(oid, (CampMap)attribute, log);
        	break;
        case _list:
          	if(hasValue)
        	cretVal = _updateListChildrenValue(oid, (CampList)attribute, log);
        	break;
        	default:
        		break;
        }
        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + cretVal + "' child attribute entr"+((cretVal>1)?"ies":"y")+" updated  ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
        attribute.valueHistory().stamptime();
        attribute.valueHistory().updateInstance();
        attribute.cleanStatus(attribute);
        CampInstanceDao.instance()._addInstance(attribute, true, log);
          
        attribute.valueStates().ioAction(IOAction.UPDATE);

      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) { msg = "-- --[ EXCEPTION! Update failed.]-- --"; LOG.info(String.format(fmt, _f, msg)); }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) { msg = " -- -- [ Releasing Connection ]"; LOG.info(String.format(fmt, _f, msg)); }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attributed updated.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    
    @Override
    public int updateList(int objectId, AttributeList attributeList) {
      return _updateList(objectId, attributeList, false);
    }

    public static int _updateList(int oid, AttributeList attributeList, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_updateList]";
        msg = "====[ update list of attribute objects ]====";
        LOG.traceEntry(String.format(fmt, _f.toUpperCase()+">>>>>>>>", msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);
        ValueList vl = new ValueList();
        ValueList svl = new ValueList();
        for(Value<?> v: getValueList(attributeList)){
        	if(v.states().isModified()) {
        		svl.add(v);
        	} else {
        		vl.add(v);
        	}
        }
        svl = Value.ValueDao.saveList(oid, svl, log);
        
        for(Value<?> v:svl) {
        	v.states().ioAction(IOAction.UPDATE);
        }
        vl.addAll(svl);
        setValues(attributeList, vl);
        
        String fSQL =
            "UPDATE "
                + valuetable
                + " SET "
                + Util.DB._columns(valuetabledef, Util.DB.dbActionType.UPDATE, log)
                + " WHERE `"
                + valuetabledef[0][0]
                + "`=%s";

        if (log && !Util._IN_PRODUCTION) { msg = "----[ pre-formatted SQL:" + fSQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

        dbs = conn.createStatement();

        for (Attribute<?> a : attributeList) {

          String SQL = insertAttributeVUpdates(fSQL,oid,a);

          if (log && !Util._IN_PRODUCTION) { msg = "----[ Formatted SQL:" + SQL + " ]----";
            LOG.info(String.format(fmt, _f, msg));
          }

          dbs.addBatch(SQL);
        }
        retVal = Util.Math.addArray(dbs.executeBatch());

        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" updated ]----"; LOG.info(String.format(fmt, _f, msg)); }
       
        for(Attribute<?> a: attributeList) {
        	boolean hasValue = (a.value() != null && a.value().value() != null);
          int cretVal = 0;
          switch(a.attributeType()) {
          case _complex:
          	if(hasValue) 
          		cretVal = _updateComplexChildrenValue(oid,(CampComplex)a, log);
          	break;
          case _table:
          	if(hasValue)
          	cretVal = _updateTableChildrenValue(oid, (CampTable)a, log);
          	break;
          case _map:
          	if(hasValue)
          	cretVal = _updateMapChildrenValue(oid, (CampMap)a, log);
          	break;
          case _list:
          	if(hasValue)
          	cretVal = _updateListChildrenValue(oid, (CampList)a, log);
          	break;
          	default:
          		break;
          }
          if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + cretVal + "' child attribute entr"+((cretVal>1)?"ies":"y")+" updated  ]----"; LOG.info(String.format(fmt, _f, msg)); }
        	
        }
        for(Attribute<?> a: attributeList) {
          a.valueHistory().stamptime();
          a.valueHistory().updateInstance();
        }
        CampInstanceDao.instance()._addInstances(attributeList, true, log);        
        for(Attribute<?> a: attributeList) {
          a.valueStates().ioAction(IOAction.UPDATE);
        }

      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ EXCEPTION! Update failed.]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ Releasing Connection ]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute list updated.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    
    @Override
    public int delete(int objectId, int attributeId, int valueId, AttributeType type) {
      return _delete(objectId, attributeId, valueId, type, !Util._IN_PRODUCTION);
    }

    public static int _delete(int objectId, int attributeId, int vid, AttributeType type, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[create]";
        msg = "====[ deleting attribute by id '" + attributeId+ "' ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);

        int vdel = Value.ValueDao.delete(vid, type, log);
        if(vdel == 0) {
        	 if(log && !Util._IN_PRODUCTION){msg = "----[ EXCEPTION! Attribute.Value object was not deleted. ]----";LOG.info(String.format(fmt, _f,msg));}
        	 return 0;
         }
        String SQL = "DELETE FROM " + valuetable + " WHERE `"+valuetabledef[0][0]+"`=" + attributeId;

        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ SQL" + SQL + "]----";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();

        retVal = dbs.executeUpdate(SQL);

        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ EXCEPTION! Update failed.]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ Releasing Connection ]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute deleted.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    
    @Override
    public int delete(int objectId, Attribute<?> attribute) {
      return _delete(objectId, attribute, !Util._IN_PRODUCTION);
    }

    public static int _delete(int objectId, Attribute<?> a, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[create]";
        msg = "====[ deleting attribute by id '" + a.attributeId() + "' ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);

        int vdel = Value.ValueDao.delete(a.value().id(), a.value().type(), log);
        if(vdel == 0) {
        	 if(log && !Util._IN_PRODUCTION){msg = "----[ EXCEPTION! Attribute.Value object was not deleted. ]----";LOG.info(String.format(fmt, _f,msg));}
        	 return 0;
         }
        String SQL = "DELETE FROM " + valuetable + " WHERE `"+valuetabledef[0][0]+"`=" + a.attributeId();

        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ SQL" + SQL + "]----";
          LOG.info(String.format(fmt, _f, msg));
        }

        dbs = conn.createStatement();

        retVal = dbs.executeUpdate(SQL);

        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }

        boolean hasValue = (a.value() != null && a.value().value() != null);
        int cretVal = 0;
        switch(a.attributeType()) {
        case _complex:
          	if(hasValue)
        	cretVal = _deleteComplexChildrenValue(objectId, (CampComplex)a, false, true, log);
        	break;
        case _table:
          	if(hasValue)
        	cretVal = _deleteTableChildrenValue(objectId, (CampTable)a, false, true, log);
        	break;
        case _map:
          	if(hasValue)
        	cretVal = _deleteMapChildrenValue(objectId, (CampMap)a, false, true, log);
        	break;
        case _list:
          	if(hasValue)
        	cretVal = _deleteListChildrenValue(objectId, (CampList)a, false, true, log);
        	break;
        	default:
        		break;
        }
        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + cretVal + "' child attribute entr"+((cretVal>1)?"ies":"y")+" deleted  ]----"; LOG.info(String.format(fmt, _f, msg)); }

      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ EXCEPTION! Update failed.]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ Releasing Connection ]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ attribute deleted.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    
    @Override
    public int deleteList(int objectId, AttributeList attributeList) {
      return _deleteList(objectId, attributeList, !Util._IN_PRODUCTION);
    }

    public static int _deleteList(int oid, AttributeList atl, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[deleteList]";
        msg = "====[ deleting the value aspects of a list of attribute objects ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      int retVal = 0;
      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);
        ValueList vl = getValueList(atl);
        int vdels = Value.ValueDao.deleteList(vl, log);
        if(vdels == 0 || vdels < vl.size()) {
       	 if(log && !Util._IN_PRODUCTION){msg = "----[ EXCEPTION! one or more Attribute.Value objects were not deleted. ]----";LOG.info(String.format(fmt, _f,msg));}
       	 return 0;
        }
        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + vdels + "' Value entr"+((vdels>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
				

        dbs = conn.createStatement();

        for (Attribute<?> a : atl) {
          String SQL = "DELETE FROM " + valuetable + " WHERE `"+valuetabledef[0][0]+"`=" + a.attributeId();

          if (log && !Util._IN_PRODUCTION) { msg = "----[ SQL:" + SQL + "]----"; LOG.info(String.format(fmt, _f, msg)); }

          dbs.addBatch(SQL);
        }

        retVal = Util.Math.addArray(dbs.executeBatch());
        
        if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' value apsect attributes entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
        for(Attribute<?> attribute: atl) {
        	boolean hasValue = (attribute.value() != null && attribute.value().value() != null);
          int cretVal = 0;
          switch(attribute.attributeType()) {
          case _complex:
          	if(hasValue)
          	cretVal = _deleteComplexChildrenValue(oid, (CampComplex)attribute, false, true, log);
          	break;
          case _table:
          	if(hasValue)
          	cretVal = _deleteTableChildrenValue(oid, (CampTable)attribute, false, true, log);
          	break;
          case _map:
          	if(hasValue)
          	cretVal = _deleteMapChildrenValue(oid, (CampMap)attribute, false, true, log);
          	break;
          case _list:
          	if(hasValue)
          	cretVal = _deleteListChildrenValue(oid, (CampList)attribute, false, true, log);
          	break;
          	default:
          		break;
          }
          if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + cretVal + "' child attribute entr"+((cretVal>1)?"ies":"y")+" deleted  ]----"; LOG.info(String.format(fmt, _f, msg)); }
        }
      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ EXCEPTION! Update failed.]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = "----[ Releasing Connection ]----";
          LOG.info(String.format(fmt, _f, msg));
        }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }
      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ deleteList completed.]====";
        LOG.info(String.format(fmt, _f, msg + time));
      }
      return retVal;
    }

    @Override
    public Attribute<?> load(int objectId, Attribute<?> attribute) {
      return _load(objectId, attribute, !Util._IN_PRODUCTION);
    }

    public static Attribute<?> _load(int oid, Attribute<?> a, boolean log) {
    	long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[_load]";
				msg = "====[ load value aspects of attribute object ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
			//TODO: use this SQL for loading instead of loading in two steps
//    	String SQL = "SELECT * FROM "+valuetable+" AS tv" + ", "+Value.Dao.integertable+" AS i " + ", "+Value.Dao.stringtable+" AS s " + ", "+Value.Dao.texttable+" AS t " + ", "+Value.Dao.timestamptable+" AS ti " + ", "+Value.Dao.booleantable+" AS b " + ", "+Value.Dao.complextable+" AS c " + ", "+Value.Dao.blobtable+" AS bl " + " WHERE " + " tv.object_id="+objectId + " AND (i.object_id=tv.object_id " + " OR s.object_id=tv.object_id " + " OR t.object_id=tv.object_id " + " OR ti.object_id=tv.object_id " + " OR b.object_id=tv.object_id " + " OR c.object_id=tv.object_id " + " OR bl.object_id=tv.object_id )" + " AND td._attribute_type_id_=tv.attribute_type_id";
    	Attribute<?> at = _loadVAttribute(a,String.format(loadVByIOidSQL, oid, a.id()), log);
    	Value<?> v = Value.ValueDao.loadById(a.attributeId(),oid,a.valueId(), a.attributeType(), log);
    	at = setValue(at, v);
    	if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[_load completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			
    	return at;
    }

    
    @Override
    public AttributeList loadByObjectId(AttributeList attributeList, int objectId) {
      return _loadByObjectId(attributeList, objectId, !Util._IN_PRODUCTION);
    }

    public static AttributeList _loadByObjectId(AttributeList al, int oid, boolean log) {
      al = _loadVAttributeList(al, String.format(loadVByOidSQL, oid), log);
      ValueList vl = Value.ValueDao.load(oid, log);
      setValues(al, vl);
      return al;
    }

    
    @Override
    public AttributeList loadGroup(AttributeList attributeList, int objectId, String groupName) {
      return _loadGroup(attributeList, groupName, objectId, !Util._IN_PRODUCTION);
    }

    public static AttributeList _loadGroup(AttributeList al, String groupName, int oid, boolean log) {
    	long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[_loadGroup]";
				msg = "====[ load value aspects of list of attribute objects by group("+groupName+") and object Id("+oid+") ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
      al = _loadVAttributeList(al, String.format(loadVByGroupSQL, oid, groupName), log);
      if(log && !Util._IN_PRODUCTION){msg = "----[ '"+al.size()+"' value aspects entr"+((al.size()!=1)?"ies":"y")+" for attribute list loaded]----";LOG.info(String.format(fmt, _f,msg));}
      HashMap<Integer,String> ids = getValueIds(al);
      if(log && !Util._IN_PRODUCTION){msg = "----[loading list specific values ("+Util.Text.joinInt(ids.keySet(), ",")+")]----";LOG.info(String.format(fmt, _f,msg));}
      ValueList vl = Value.ValueDao.loadList(ids,log);
      if(log && !Util._IN_PRODUCTION){msg = "----[ '"+vl.size()+"' specific value entr"+((vl.size()!=1)?"ies":"y")+" for attribute list loaded]----";LOG.info(String.format(fmt, _f,msg));}
      al = setValues(al, vl);
      if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[_loadGroup completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			
      return al;
    }

    
    @Override
    public AttributeList loadAfterPosition(AttributeList attributeList, int objectId, int position) {
      return _loadAfterPosition(attributeList, objectId, position, !Util._IN_PRODUCTION);
    }

    public static AttributeList _loadAfterPosition(AttributeList al, int oid, int pos, boolean log) {
    	long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[_loadAfterPosition]";
				msg = "====[ load value aspects of attribute list based on position and objectId RULE: position > "+pos+"  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
      al = _loadVAttributeList(al, String.format(loadVByPosGTSQL, oid, pos), log);
      if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + al.size() + "' entr"+((al.size()>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
      HashMap<Integer,String> ids = getValueIds(al);
      ValueList vl = Value.ValueDao.loadList(ids,log);
      al = setValues(al, vl);
      if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[_loadAfterPosition completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			
      return al;
    }

    
    @Override
    public AttributeList loadRange(AttributeList attributeList, int objectId, int startPosition, int endPosition) {
      return _loadRange(attributeList, objectId, startPosition, endPosition, !Util._IN_PRODUCTION);
    }

    public static AttributeList _loadRange(AttributeList al, int oid, int spos, int epos, boolean log) {
      al = _loadVAttributeList(al,String.format(loadVByPosRangeSQL, oid, spos, epos), log);
      HashMap<Integer,String> ids = getValueIds(al);
      ValueList vl = Value.ValueDao.loadList(ids,log);
      al = setValues(al, vl);
      return al;
    }

    
    @Override
    public AttributeList loadBeforePosition(AttributeList al, int objectId, int position) {
      return _loadBeforePosition(al, objectId, position, !Util._IN_PRODUCTION);
    }

    public static AttributeList _loadBeforePosition(AttributeList al, int oid, int pos, boolean log) {
      al = _loadVAttributeList(al,String.format(loadVByPosLTSQL, oid, pos), log);
      HashMap<Integer,String> ids = getValueIds(al);
      ValueList vl = Value.ValueDao.loadList(ids,log);
      al = setValues(al, vl);
      return al;
    }

    public static Attribute<?> _loadVAttribute(Attribute<?> a,String SQL, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_Attribute]";
        msg = "====[ execute SQL query ("+SQL+") toload a single attribute object ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;

      try {

        conn = Util.DB.__conn(log);

        dbs = conn.createStatement();
        
        rs = dbs.executeQuery(SQL);

        a = _rsToVAttribute(a,rs);

      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ EXCEPTION! Update failed.]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = " -- -- [ Releasing Connection ]";
          LOG.info(String.format(fmt, _f, msg));
        }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }

      if (log && !Util._IN_PRODUCTION) {
        String time = "[ExecutionTime:" + (System.currentTimeMillis() - startTime) + ")]====";
        msg = "====[ value loaded attribute type.]====";
        LOG.traceExit(String.format(fmt, _f, msg + time));
      }

      return a;
    }

    public static AttributeList _loadVAttributeList(AttributeList al,String SQL, boolean log) {
      long startTime = System.currentTimeMillis();
      String _f = null;
      String msg = null;
      if (log && !Util._IN_PRODUCTION) {
        _f = "[_loadVAttributeList]";
        msg = "====[ execute SQL query ( "+SQL+" ) toload a single attribute object ]====";
        LOG.traceEntry(String.format(fmt, _f, msg));
      }

      Connection conn = null;
      ResultSet rs = null;
      Statement dbs = null;
      AttributeList ral = null;
      try {

        conn = Util.DB.__conn(log);

        dbs = conn.createStatement();
        
        rs = dbs.executeQuery(SQL);

        ral = _rsToVAttributeList(al,rs);

      } catch (Exception e) {
        if (log && !Util._IN_PRODUCTION) {
          msg = "-- --[ EXCEPTION! Update failed.]-- --";
          LOG.info(String.format(fmt, _f, msg));
        }
        e.printStackTrace();
      } finally {
        if (log && !Util._IN_PRODUCTION) {
          msg = " -- -- [ Releasing Connection ]";
          LOG.info(String.format(fmt, _f, msg));
        }
        Util.DB.__release(conn, log);
        Util.DB._releaseStatement(dbs, log);
        Util.DB._releaseRS(rs, log);
      }

      if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[_loadVAttributeList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			
      return ral;
    }
    
    
    @Override
    public AttributeMap saveByObjectId(int objectId, AttributeMap attributeMap) {
      return _saveByObjectId(objectId, attributeMap, !Util._IN_PRODUCTION);
    }

    public static AttributeMap _saveByObjectId(int oid, AttributeMap am, boolean log) {
    	long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[_saveByObjectId]";
				msg = "====[ Save all attribute object instances associated with an object ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
    	//TODO: should list result be assembled to the root attribute aspect here and a campMap returned instead of AttributeList
    	AttributeMap r = new AttributeMap();
    	AttributeList al = new AttributeList();
    	for(String group:am.keySet()) {
    		al.addAll(am.get(group));
    	}
    	if(al.isEmpty()) return am;
      al = _saveAllAttributes(oid,al,log);
      HashMap<Integer,Attribute<?>> t = new HashMap<Integer,Attribute<?>>();
      
      // first pass add root attributes
      for(Attribute<?> a:al) {
      	if(a.parentId() == 0 && a.attributeParentId() == 0) {
      		t.put(a.id(), a);
      		if(log && !Util._IN_PRODUCTION){msg = "----[ASSEMBLING PASS 1 Parent Attribute("+a.name()+") after save all call]----";LOG.info(String.format(fmt, _f,msg));}
      	}
      }
      // second pass sort in children
      for(int id: t.keySet()) {
     		if(log && !Util._IN_PRODUCTION){msg = "----[ASSEMBLING PASS 2 Parent Attribute("+t.get(id).name()+") after save all call]----";LOG.info(String.format(fmt, _f,msg));}
      	for(Attribute<?> a: al) {
      		if(log && !Util._IN_PRODUCTION){msg = "----[ASSEMBLING PASS 2 Child Attribute("+a.name()+") to Parent assignment ]----";LOG.info(String.format(fmt, _f,msg));}
      		if(a.parentId()== id) {
      			switch(t.get(id).attributeType()) {
      			case _complex:
      				if(log && !Util._IN_PRODUCTION){msg = "----[ADDING Child Attribute("+a.name()+") to Complex Parent Attribute("+t.get(id).name()+")]----";LOG.info(String.format(fmt, _f,msg));}
      				if(((CampComplex)t.get(id)).value().value() == null) {
      					((CampComplex)t.get(id)).value().setValue(new HashMap<String,ArrayList<Attribute<?>>>());
      				}
      				if(!((CampComplex)t.get(id)).value().value().containsKey(a.group().name())) {
      					((CampComplex)t.get(id)).value().value().put(a.group().name(), new ArrayList<Attribute<?>>());
      				}
      				((CampComplex)t.get(id)).value().value().get(a.group().name()).add(a);//TODO think about sorting if need be else let display etc handle this 
      				break;
      			case _table://TODO: fix this make value ArrayList<CampList> CampList is a column and not a row see notes
      				if(log && !Util._IN_PRODUCTION){msg = "----[ADDING Child Attribute("+a.name()+") to Table Parent Attribute("+t.get(id).name()+")]----";LOG.info(String.format(fmt, _f,msg));}
      				if(((CampTable)t.get(id)).value().value()==null) {
      					((CampTable)t.get(id)).value().setValue(new ArrayList<ArrayList<Attribute<?>>>());
      				}
      				if(((CampTable)t.get(id)).value().value().get(a.value().position().posY()) == null) {
      					((CampTable)t.get(id)).value().value().add(a.value().position().posY(),new ArrayList<Attribute<?>>());
      				}
      				((CampTable)t.get(id)).value().value().get(a.value().position().posY()).add(a);
      				break;      				
      			case _map:
      				if(log && !Util._IN_PRODUCTION){msg = "----[ADDING Child Attribute("+a.name()+") to Map Parent Attribute("+t.get(id).name()+")]----";LOG.info(String.format(fmt, _f,msg));}
      				if(((CampMap)t.get(id)).value().value() == null) {
      					((CampMap)t.get(id)).value().setValue(new HashMap<String,Attribute<?>>());
      				}
      				((CampMap)t.get(id)).value().value().put(a.group().name(),a); 
      				break;
      			case _list:
      				if(log && !Util._IN_PRODUCTION){msg = "----[ADDING Child Attribute("+a.name()+") to List Parent Attribute("+t.get(id).name()+")]----";LOG.info(String.format(fmt, _f,msg));}
      				if(((CampList)t.get(id)).value().value()==null) {
      					((CampList)t.get(id)).value().setValue(new ArrayList<Attribute<?>>());
      				}
      				((CampList)t.get(id)).value().value().add(a);
      				break;
      				default:
      					break;
      			}
      		}
      	}
      
      }
      for(int id: t.keySet()) {
      	if(!r.containsKey(t.get(id).group().name())) {
      		r.put(t.get(id).group().name(),new AttributeList());
      	}
      	r.get(t.get(id).group().name()).add(t.get(id));
      }
      if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[_saveByObjectId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			return r;
    }

    public static int updateByObjectId(int oid, AttributeMap am, boolean log) {
    	return _updateByObjectId(oid, am, log); 
    }
    public static int _updateByObjectId(int oid, AttributeMap am, boolean log) {
    	AttributeList al = new AttributeList();
    	int retVal = 0;
    	for(String group:am.keySet()) {
    		al.addAll(am.get(group));
    	}
    	if(al.isEmpty()) return retVal;
      retVal = _updateAllAttributes(oid,al,log);
      return retVal;
    }
    public int updateAttributesByObjectId(int objectId,AttributeList attributeList) {
    	return _updateAllAttributes(objectId, attributeList, !Util._IN_PRODUCTION);
    }
    public static int _updateAllAttributes(int objectId, AttributeList attributeList, boolean log) {
    	long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[_updateAllAttributes]";
				msg = "====[ update all attributes (definition and value aspects) of the object with object id "+objectId+" ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
			int retVal = 0;
			AttributeList rList = _updateList(attributeList,log);
			
			if(rList != null) {
				retVal = rList.size();
			} else {
				if(log && !Util._IN_PRODUCTION){msg = "----[SQL ERROR! _updateAllAttributes FAILED]----";LOG.info(String.format(fmt, _f,msg));}
				return 0;
			}
			if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" updated ]----"; LOG.info(String.format(fmt, _f, msg)); }
			retVal = _updateList(objectId, attributeList, log);
			if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" updated ]----"; LOG.info(String.format(fmt, _f, msg)); }
			if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[_updateAllAttributes completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			return retVal;
    }
    
    
    public AttributeList saveAttributesByObjectId(int objectId,AttributeList attributeList) {
    	return _saveAllAttributes(objectId, attributeList, !Util._IN_PRODUCTION);
    }
    public static AttributeList _saveAllAttributes(int objectId, AttributeList attributeList, boolean log) {
    	long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[_saveAllAttributes]";
				msg = "====[ save all attributes (definition and value aspects) of the object with object id "+objectId+" ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
			attributeList = _saveList(attributeList,log);
			attributeList = _saveList(objectId, attributeList, log);
			if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[_saveAllAttributes completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			return attributeList;
    }
    
    public AttributeList loadAttributesByObjectId(int objectId) {
    	return _loadAllAttributes(objectId, !Util._IN_PRODUCTION);
    }
    public static AttributeList _loadAllAttributes(int objectId, boolean log) {
    	long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[_loadAttributes]";
				msg = "====[ load all attributes (definition and value aspects) of object "+objectId+"]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
			AttributeList al = new AttributeList();
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;
			try{
				conn = Util.DB.__conn(log);
				
		  String SQL = "SELECT * FROM "+valuetable+" AS tv" + ", "+table+" AS td, "+CampInstanceDao.table+" AS ci %s"
		    		+ " AND td._attribute_type_id_=tv.attribute_type_id"
		    		+ " AND tv.object_id=%s" 
		    		+ " AND ci.`_instance_id`=ci.`_current_instance_id` "
		    		+ " AND tv.`"+valuetabledef[0][0]+"`=ci.`_object_id` "
		    		+ " AND ci.`_object_business_id`=tv.`attribute_businesskey` ";
		  
		    String iSQL = ", "+Value.ValueDao.integertable+" AS i WHERE i.object_id=tv.object_id AND i."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
		    String sSQL =  ", "+Value.ValueDao.stringtable+" AS s WHERE s.object_id=tv.object_id AND s."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String tSQL = ", "+Value.ValueDao.texttable+" AS t  WHERE t.object_id=tv.object_id AND t."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String tiSQL = ", "+Value.ValueDao.timestamptable+" AS ti  WHERE ti.object_id=tv.object_id AND ti."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String bSQL = ", "+Value.ValueDao.booleantable+" AS b  WHERE b.object_id=tv.object_id AND b."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
		    String cSQL = ", "+Value.ValueDao.complextable+" AS c  WHERE c.object_id=tv.object_id AND c."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
		    String blSQL = ", "+Value.ValueDao.blobtable+" AS bl  WHERE bl.object_id=tv.object_id AND bl."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 

				String[] vSQL = {iSQL,sSQL,tSQL,tiSQL,bSQL,cSQL,blSQL};
				
				
				dbs = conn.createStatement();
			
				for(String f:vSQL) {
					String fSQL = String.format(SQL, f,objectId);
					if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+fSQL+"]----";LOG.info(String.format(fmt,_f,msg));}
					rs = dbs.executeQuery(fSQL);		
					while(rs.next()) {
						Attribute<?> a = _rsToA(rs);
						a = _rsToAV(a, rs);
						a.setHistory(CampInstanceDao.instance().rsToI(rs, log));
						Value<?> v = Value.ValueDao.rsToV(rs, log);
						a = setValue(a, v);
						al.add(a);
					}
				}
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + al.size() + "' attribute entr"+((al.size()>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				
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
				msg = "====[_loadAttributes completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			return al;
    }

    public static CampComplex _saveComplexChildren(CampComplex attribute, boolean log){
    	HashMap<String,ArrayList<Attribute<?>>> v = new HashMap<String,ArrayList<Attribute<?>>>();//HashMap<String,ArrayList<Attribute<?>>>();
    	AttributeList al = new AttributeList();
    	AttributeList alv = new AttributeList();
    	for(String group: attribute.value().value().keySet()) {
    		for(Attribute<?> a:attribute.value().value().get(group)) {
//    			if(a.group()==null || a.group().name().isEmpty())a.setGroup(group);
    			a.setGroup(group);
    			a.parent(attribute);
    			a.parentId(attribute.id());
//     			if(a.attributeGroup()==null || a.attributeGroup().name().isEmpty())a.setAttributeGroup(attribute.attributeGroup().name());
//    			if(a.attributeBusinessKey()==null || a.attributeBusinessKey().isEmpty())a.setAttributeBusinessKey(attribute.attributeBusinessKey());
    			// place new attributes (id==0) in al list to persist the definition aspects
    			if(a.id() == 0) {
    				al.add((Attribute<?>) a);
    			}
    			alv.add(a);
    		}
    	}
    	al =_saveList(al,log);
    	// update the definition aspect attribute id of attributes in alv list 
    	for(Attribute<?> a:al) {
    		for(Attribute<?> av: alv) {
    			if(a.name().equals(av.name())) {
    				av.updateId(a.id());
    			}
    		}
    	}
    	for(Attribute<?>a : alv) {
    		if(!v.containsKey(a.attributeGroup().name())) {
    			v.put(a.attributeGroup().name(), new AttributeList());
    		}
    		v.get(a.attributeGroup().name()).add(a);
    	}
    	attribute.value().setValue(v);
    	return attribute;
    }
    public static CampTable _saveTableChildren(CampTable attribute, boolean log){
    	AttributeList al = new AttributeList();
     	AttributeList alv = new AttributeList();
    	int x = 0;
    	int y = 0;
    	int count = 0;
    	for(ArrayList<Attribute<?>> arl: attribute.value().value()) {
    		y++;
    		for(Attribute<?> a: arl) {
    			x++;
//    			if(a.group()==null || a.group().name().isEmpty())a.setGroup(attribute.group().name());
    			if(a.position()==0)a.setPosition(count);
    			a.parent(attribute);
    			a.parentId(attribute.id());
     			a.value().position().posX(x);
    			a.value().position().posY(y);
//    			if(a.attributeGroup()==null || a.attributeGroup().name().isEmpty())a.setAttributeGroup(attribute.attributeGroup().name());
//    			if(a.attributeBusinessKey()==null || a.attributeBusinessKey().isEmpty())a.setAttributeBusinessKey(attribute.attributeBusinessKey());
    			a.attributeParentId(attribute.attributeId());
    			// place new attributes (id==0) in al list to persist the definition aspects
    			if(a.id() == 0) {
    				al.add(a);
    			}
    			alv.add(a);
    			count++;
    		}
    	}
    	//persist definition aspects of attributes which have not yet been persisted. 
    	al =_saveList(al,log);
    	// update the definition aspect attribute id of attributes in alv list 
    	for(Attribute<?> a:al) {
    		for(Attribute<?> av: alv) {
    			if(a.name().equals(av.name())) {
    				av.updateId(a.id());
    			}
    		}
    	}
    	int row = 0;
    	int col = 0;
//    	recreate attribute value item to ensure that everything is up to date TODO: check if this is necessary
    	for(ArrayList<Attribute<?>> arl: attribute.value().value()) {
    		for(@SuppressWarnings("unused") Attribute<?> at: arl) {
    			for(Attribute<?>a:alv) {
    				if(a.value().position().posX()==(col+1) && a.value().position().posY()==(row+1)){
    					attribute.value().value().get(row).set(col, a);
    				}
    			}
    			col++;
    		}
    		row++;
    	}
    	return attribute;
    }

    public static CampMap _saveMapChildren(CampMap attribute, boolean log){
    	HashMap<String,Attribute<?>> v = new HashMap<String,Attribute<?>>();
    	AttributeList alv = new AttributeList();
    	AttributeList al = new AttributeList();
    	for(String group: attribute.value().value().keySet()) {
    		Attribute<?> a = attribute.value().value().get(group);
//    		if(a.group()==null || a.group().name().isEmpty())a.setGroup(group);
  			a.parent(attribute);
  			a.parentId(attribute.id());
//  			if(a.attributeGroup()==null || a.attributeGroup().name().isEmpty())a.setAttributeGroup(attribute.attributeGroup().name());
//  			if(a.attributeBusinessKey()==null || a.attributeBusinessKey().isEmpty())a.setAttributeBusinessKey(attribute.attributeBusinessKey());
  			a.setAttributePosition(attribute.position());
  			a.attributeParentId(attribute.attributeId());
  			// place new attributes (id==0) in al list to persist the definition aspects
  			if(a.id() == 0) {
  				al.add(a);
  			}
  			alv.add(a);
    	}
    	//persist definition aspects of attributes which have not yet been persisted. 
    	al =_saveList(al,log);
    	// update the definition aspect attribute id of attributes in alv list 
    	for(Attribute<?> a:al) {
    		for(Attribute<?> av: alv) {
    			if(a.name().equals(av.name())) {
    				av.updateId(a.id());
    			}
    		}
    	}
    	// recreate attribute value element and add it to attribute
    	for(Attribute<?>a : alv) {
    		v.put(a.attributeGroup().name(),a);
    	}
    	attribute.value().setValue(v);
    	return attribute;
    }

    public static CampList _saveListChildren(CampList attribute, boolean log){
    	AttributeList alv = (AttributeList) attribute.value().value();
     	AttributeList al = new AttributeList();
    	int x = 0;
  		for(Attribute<?> a: alv) {
  			x++;
//  			if(a.group()==null || a.group().name().isEmpty())a.setGroup(attribute.group().name());
  			a.parent(attribute);
  			a.parentId(attribute.id());
  			a.value().position().posX(x);
//  			if(a.attributeGroup()==null || a.attributeGroup().name().isEmpty())a.setAttributeGroup(attribute.attributeGroup().name());
//  			if(a.attributeBusinessKey()==null || a.attributeBusinessKey().isEmpty())a.setAttributeBusinessKey(attribute.attributeBusinessKey());
  			a.attributeParentId(attribute.attributeId());
  			// place new attributes (id==0) in al list to persist the definition aspects
  			if(a.id() == 0) {
  				al.add(a);
  			}
  		}
    	//persist definition aspects of attributes which have not yet been persisted. 
    	al =_saveList(al,log);
    	// update the definition aspect attribute id of attributes in alv list 
    	for(Attribute<?> a:al) {
    		for(Attribute<?> av: alv) {
    			if(a.name().equals(av.name())) {
    				av.updateId(a.id());
    			}
    		}
    	}
    	attribute.value().setValue(alv);
    	return attribute;
    }

    public static int _updateComplexChildren(CampComplex attribute, boolean log){
    	long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[_updateComplexChildren]";
				msg = "====[ update complex child elements of complex attribute object ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
			int retVal = 0;
    	AttributeList alv = new AttributeList();
    	for(String group: attribute.value().value().keySet()) {
    		for(Attribute<?> a:attribute.value().value().get(group)) {
    			if(a.states().isModified()){
    				alv.add(a);
    			} 
    			if(log && !Util._IN_PRODUCTION){msg = "----[Added attribute("+a.name()+")]----";LOG.info(String.format(fmt, _f,msg));}
    		}
    	}
    	//update definition aspects of the complex child attributes
    	AttributeList rList = _updateList(alv,log);
    	
			if(rList != null) {
				retVal = rList.size();
			} else {
				if(log && !Util._IN_PRODUCTION){msg = "----[SQL ERROR! _updateAllAttributes FAILED]----";LOG.info(String.format(fmt, _f,msg));}
				return 0;
			}

    	if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" updated ]----"; LOG.info(String.format(fmt, _f, msg)); }
    	
    	if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[_updateComplexChildren completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			
    	return retVal;
    }
    public static int _updateTableChildren(CampTable attribute, boolean log){
    	AttributeList alv = new AttributeList();
    	for(ArrayList<Attribute<?>> arl: attribute.value().value()) {
    		for(Attribute<?> a: arl) {
    			if(a.states().isModified()){
    				alv.add(a);
    			} 
    		}
    	}
    	//update definition aspects of the complex child attributes
    	int retVal = 0;
    	AttributeList rList = _updateList(alv,log);
			if(rList != null) {
				retVal = rList.size();
			} else {
				if(log && !Util._IN_PRODUCTION){String msg = "----[SQL ERROR! _updateAllAttributes FAILED]----";LOG.info(String.format(fmt, "[_updateTableChildren]",msg));}
				return 0;
			}

    	return retVal;
    }
    public static int _updateMapChildren(CampMap attribute, boolean log){
    	AttributeList alv = new AttributeList();
    	for(String group: attribute.value().value().keySet()) {
    		if(attribute.value().value().get(group).states().isModified()) {
    			alv.add(attribute.value().value().get(group));
    		}
    	}
    	//update definition aspects of the complex child attributes
    	int retVal = 0;
    	AttributeList rList = _updateList(alv,log);
			if(rList != null) {
				retVal = rList.size();
			} else {
				if(log && !Util._IN_PRODUCTION){String msg = "----[SQL ERROR! _updateAllAttributes FAILED]----";LOG.info(String.format(fmt, "[_updateMapChildren]",msg));}
				return 0;
			}

    	return retVal;
    }
    public static int _updateListChildren(CampList attribute, boolean log){
    	//update definition aspects of the complex child attributes
    	AttributeList alv = new AttributeList();
    	for(Attribute<?> a: attribute.value().value()) {
    		if(a.states().isModified()) {
    			alv.add(a);
    		}
    	}
    	int retVal = 0;
    	AttributeList rList = _updateList(alv,log);
			if(rList != null) {
				retVal = rList.size();
			} else {
				if(log && !Util._IN_PRODUCTION){String msg = "----[SQL ERROR! _updateAllAttributes FAILED]----";LOG.info(String.format(fmt, "_updateListChildren",msg));}
				return 0;
			}
    	return retVal;
    }
    
    public static CampComplex _saveComplexChildrenValue(int objectId, CampComplex attribute, boolean log){
    	HashMap<String,ArrayList<Attribute<?>>> v = new HashMap<String,ArrayList<Attribute<?>>>();
    	AttributeList al = new AttributeList();
    	AttributeList alv = new AttributeList();
    	for(String group: attribute.value().value().keySet()) {
    		for(Attribute<?> a:attribute.value().value().get(group)) {
//    			if(a.group()==null || a.group().name().isEmpty())a.setGroup(group);
    			a.parent(attribute);
    			a.parentId(attribute.id());
//     			if(a.attributeGroup()==null || a.attributeGroup().name().isEmpty())a.setAttributeGroup(attribute.attributeGroup().name());
//    			if(a.attributeBusinessKey()==null || a.attributeBusinessKey().isEmpty())a.setAttributeBusinessKey(attribute.attributeBusinessKey());
    			a.attributeParentId(attribute.attributeId());
    			alv.add(a);
    		}
    	}
     	//persist the value aspects of the complex child attributes
    	alv = _saveList(objectId,alv,log);
    	for(Attribute<?>a : alv) {
    		if(!v.containsKey(a.attributeGroup().name())) {
    			v.put(a.attributeGroup().name(), new ArrayList<Attribute<?>>());
    		}
    		v.get(a.attributeGroup().name()).add(a);
    	}
    	attribute.value().setValue(v);
    	return attribute;
    }

    public static CampTable _saveTableChildrenValue(int objectId, CampTable attribute, boolean log){
     	AttributeList alv = new AttributeList();
    	int x = 0;
    	int y = 0;
    	int count = 0;
    	for(ArrayList<Attribute<?>> arl: attribute.value().value()) {
    		y++;
    		for(Attribute<?> a: arl) {
    			x++;
//    			if(a.group()==null || a.group().name().isEmpty())a.setGroup(attribute.group().name());
    			if(a.position()==0)a.setPosition(count);
    			a.parent(attribute);
    			a.parentId(attribute.id());
     			a.value().position().posX(x);
    			a.value().position().posY(y);
//    			if(a.attributeGroup()==null || a.attributeGroup().name().isEmpty())a.setAttributeGroup(attribute.attributeGroup().name());
//    			if(a.attributeBusinessKey()==null || a.attributeBusinessKey().isEmpty())a.setAttributeBusinessKey(attribute.attributeBusinessKey());
    			a.attributeParentId(attribute.attributeId());
    			alv.add(a);
    			count++;
    		}
    	}
    	//persist the value aspects of the complex child attributes
    	alv = _saveList(objectId,alv,log);
    	int row = 0;
    	int col = 0;
//    	recreate attribute value item to ensure that everything is up to date TODO: check if this is necessary
    	for(ArrayList<Attribute<?>> arl: attribute.value().value()) {
    		for(@SuppressWarnings("unused") Attribute<?> at: arl) {
    			for(Attribute<?>a:alv) {
    				if(a.value().position().posX()==(col+1) && a.value().position().posY()==(row+1)){
    					attribute.value().value().get(row).set(col, a);
    				}
    			}
    			col++;
    		}
    		row++;
    	}
    	return attribute;
    }

    public static CampMap _saveMapChildrenValue(int objectId, CampMap attribute, boolean log){
    	HashMap<String,Attribute<?>> v = new HashMap<String,Attribute<?>>();
    	AttributeList alv = new AttributeList();
    	for(String group: attribute.value().value().keySet()) {
    		Attribute<?> a = attribute.value().value().get(group);
//    		if(a.group()==null || a.group().name().isEmpty())a.setGroup(group);
  			a.parent(attribute);
  			a.parentId(attribute.id());
//  			if(a.attributeGroup()==null || a.attributeGroup().name().isEmpty())a.setAttributeGroup(attribute.attributeGroup().name());
//  			if(a.attributeBusinessKey()==null || a.attributeBusinessKey().isEmpty())a.setAttributeBusinessKey(attribute.attributeBusinessKey());
  			a.setAttributePosition(attribute.position());
  			a.attributeParentId(attribute.attributeId());
  			alv.add(a);
    	}
    	//persist the value aspects of the complex child attributes
    	alv = _saveList(objectId,alv,log);
    	// recreate attribute value element and add it to attribute
    	for(Attribute<?>a : alv) {
    		v.put(a.attributeGroup().name(),a);
    	}
    	attribute.value().setValue(v);
    	return attribute;
    }

    public static CampList _saveListChildrenValue(int objectId, CampList attribute, boolean log){
    	AttributeList alv = (AttributeList) attribute.value().value();
     	AttributeList al = new AttributeList();
    	int x = 0;
  		for(Attribute<?> a: alv) {
  			x++;
//  			if(a.group()==null || a.group().name().isEmpty())a.setGroup(attribute.group().name());
//  			if(a.onlyBusinessId()==null || a.onlyBusinessId().isEmpty())a.setBusinessId(attribute.onlyBusinessId());
  			a.parent(attribute);
  			a.parentId(attribute.id());
  			// place new attributes (id==0) in al list to persist the definition aspects
  			if(a.id() == 0) {
  				al.add(a);
  			}
  			a.value().position().posX(x);
//  			if(a.attributeGroup()==null || a.attributeGroup().name().isEmpty())a.setAttributeGroup(attribute.attributeGroup().name());
//  			if(a.attributeBusinessKey()==null || a.attributeBusinessKey().isEmpty())a.setAttributeBusinessKey(attribute.attributeBusinessKey());
  			a.attributeParentId(attribute.attributeId());
  		}
    	//persist definition aspects of attributes which have not yet been persisted. 
    	al =_saveList(al,log);
    	// update the definition aspect attribute id of attributes in alv list 
    	for(Attribute<?> a:al) {
    		for(Attribute<?> av: alv) {
    			if(a.name().equals(av.name())) {
    				av.updateId(a.id());
    			}
    		}
    	}
    	//persist the value aspects of the complex child attributes
    	alv = _saveList(objectId,alv,log);
    	attribute.value().setValue(alv);
    	return attribute;
    }

    /**
     * Recursively update the definition and value aspects of only the modified elements attributes 
     * of a specific complex attribute value.
     * @param objectId The technical identifier - as an <code>int</code> value - of the object to which the attribute instances are associated. 
     * @param attribute The complex attribute instance whose specific value aspects are being updated
     * @param log A <code>boolean</code> switch to control log output 
     * @return number updated
     */
    public static int _updateComplexChildrenValue(int objectId, CampComplex attribute, boolean log){
    	long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[_updateComplexChildrenValue]";
				msg = "====[ update complex child elements of complex attribute object ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
    	AttributeList alv = new AttributeList();
    	for(String group: attribute.value().value().keySet()) {
    		for(Attribute<?> a:attribute.value().value().get(group)) {
//    			if(a.id()==attribute.id() && a.name().equals(attribute.name())) continue;//TODO: WHY OH WHY IS attribute in the list!
    			if(a.states().isModified()){
    				alv.add(a);
    			} 
    			if(log && !Util._IN_PRODUCTION){msg = "----[Added attribute("+a.name()+")]----";LOG.info(String.format(fmt, _f,msg));}
    		}
    	}
    	//update the value aspects of the complex child attributes
    	int retVal = _updateList(objectId,alv,log);
    	if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" updated ]----"; LOG.info(String.format(fmt, _f, msg)); }
			
    	
    	if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[_updateComplexChildrenValue completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			
    	return retVal;
    }

    public static int _updateTableChildrenValue(int objectId, CampTable attribute, boolean log){
    	AttributeList alv = new AttributeList();
    	for(ArrayList<Attribute<?>> arl: attribute.value().value()) {
    		for(Attribute<?> a: arl) {
    			if(a.states().isModified()){
    				alv.add(a);
    			} 
    		}
    	}
    	//update the value aspects of the complex child attributes
    	int retVal = _updateList(objectId,alv,log);
    	return retVal;
    }

    public static int _updateMapChildrenValue(int objectId, CampMap attribute, boolean log){
    	AttributeList alv = new AttributeList();
    	for(String group: attribute.value().value().keySet()) {
    		if(attribute.value().value().get(group).states().isModified()) {
    			alv.add(attribute.value().value().get(group));
    		}
    	}
    	//update the value aspects of the complex child attributes
    	int retVal = _updateList(objectId,alv,log);
    	return retVal;
    }

    public static int _updateListChildrenValue(int objectId, CampList attribute, boolean log){
    	//update definition aspects of the complex child attributes
    	AttributeList alv = new AttributeList();
    	for(Attribute<?> a: attribute.value().value()) {
    		if(a.states().isModified()) {
    			alv.add(a);
    		}
    	}
    	int retVal = _updateList(objectId,(AttributeList) attribute.value().value(),log);
    	return retVal;
    }


    public static int _deleteComplexChildrenValue(int objectId, CampComplex attribute,boolean def, boolean val, boolean log){
    	long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[_deleteComplexChildrenValue]";
				msg = "====[ deleting "+((def)?"def aspects":"")+((val && def)?" and ":" ")+((val)?"value aspects":"")+" child elements of complex attribute ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
    	AttributeList alv = new AttributeList();
    	for(String group: attribute.value().value().keySet()) {
    		for(Attribute<?> a:attribute.value().value().get(group)) {
    			alv.add(a);
    		}
    	}
    	if(def) {
    	int vretVal =_deleteList(alv,log);
    	if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + vretVal + "' definiton aspect attribute entr"+((vretVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
    	}
    	//persist the value aspects of the complex child attributes
    	int retVal = 0;
    	if(val) {
    		retVal = _deleteList(objectId,alv,log);
    		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' value aspect attribute entr"+((retVal>1)?"ies":"y")+"  ]----"; LOG.info(String.format(fmt, _f, msg)); }
    	}
    	if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[_deleteComplexChildrenValue completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			
    	return retVal;
    }

    public static int _deleteTableChildrenValue(int objectId, CampTable attribute,boolean def, boolean val, boolean log){
    	long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[_deleteComplexChildrenValue]";
				msg = "====[ deleting "+((def)?"def aspects":"")+((val && def)?" and ":" ")+((val)?"value aspects":"")+" child elements of complex attribute ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
    	AttributeList alv = new AttributeList();
    	for(ArrayList<Attribute<?>> arl: attribute.value().value()) {
    		for(Attribute<?> a: arl) {
    			alv.add(a);
    		}
    	}
    	if(def) {
    	int vretVal =_deleteList(alv,log);
    	if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + vretVal + "' definiton aspect attribute entr"+((vretVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
    	}
    	//persist the value aspects of the complex child attributes
    	int retVal = 0;
    	if(val) {
    		retVal = _deleteList(objectId,alv,log);
    		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' value aspect attribute entr"+((retVal>1)?"ies":"y")+"  ]----"; LOG.info(String.format(fmt, _f, msg)); }
    	}
    	if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[_deleteComplexChildrenValue completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			
    	return retVal;
    }

    public static int _deleteMapChildrenValue(int objectId, CampMap attribute,boolean def, boolean val, boolean log){
    	long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[_deleteComplexChildrenValue]";
				msg = "====[ deleting "+((def)?"def aspects":"")+((val && def)?" and ":" ")+((val)?"value aspects":"")+" child elements of complex attribute ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
    	AttributeList alv = new AttributeList();
    	for(String group: attribute.value().value().keySet()) {
  			alv.add(attribute.value().value().get(group));
    	}
    	if(def) {
    	int vretVal =_deleteList(alv,log);
    	if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + vretVal + "' definiton aspect attribute entr"+((vretVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
    	}
    	//persist the value aspects of the complex child attributes
    	int retVal = 0;
    	if(val) {
    		retVal = _deleteList(objectId,alv,log);
    		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' value aspect attribute entr"+((retVal>1)?"ies":"y")+"  ]----"; LOG.info(String.format(fmt, _f, msg)); }
    	}
    	if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[_deleteComplexChildrenValue completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			
    	return retVal;
   }

    public static int _deleteListChildrenValue(int objectId, CampList attribute,boolean def, boolean val, boolean log){
    	long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[_deleteComplexChildrenValue]";
				msg = "====[ deleting "+((def)?"def aspects":"")+((val && def)?" and ":" ")+((val)?"value aspects":"")+" child elements of complex attribute ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
    	if(def) {
    		int vretVal =_deleteList((AttributeList) attribute.value().value(),log);
    		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + vretVal + "' definiton aspect attribute entr"+((vretVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
    	}
    	//persist the value aspects of the complex child attributes
    	int retVal = 0;
    	if(val) {
    		retVal = _deleteList(objectId,(AttributeList) attribute.value().value(),log);
    		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' value aspect attribute entr"+((retVal>1)?"ies":"y")+"  ]----"; LOG.info(String.format(fmt, _f, msg)); }
    	}
    	if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[_deleteComplexChildrenValue completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
    	return retVal;
    }

    @Override
    public Attribute<?> instanceLoad(String select, boolean primary, boolean log) {
    	return _instanceLoad(select,primary,log);
    }
    public static Attribute<?> _instanceLoad(String select, boolean primary, boolean log) {
    	long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[_instanceLoad]";
				msg = "====[ load attribute (definition and value aspects) object instance ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
			Attribute<?> a = null;
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;
			int retVal = 0;
			try{
				conn = Util.DB.__conn(log);
				
		  String SQL = "SELECT * FROM "+valuetable+" AS t" + ", "+table+" AS td, "+CampInstanceDao.table+" AS ci %s"
		    		+ " AND td._attribute_type_id_=t.attribute_type_id"
		    		+ select;
		  
		    String iSQL = ", "+Value.ValueDao.integertable+" AS i WHERE i.object_id=t.object_id AND i."+Value.ValueDao.integertabledef[0][0]+"=t.value_id"; 
		    String sSQL =  ", "+Value.ValueDao.stringtable+" AS s WHERE s.object_id=t.object_id AND s."+Value.ValueDao.integertabledef[0][0]+"=t.value_id";
		    String tSQL = ", "+Value.ValueDao.texttable+" AS tx  WHERE tx.object_id=t.object_id AND tx."+Value.ValueDao.integertabledef[0][0]+"=t.value_id";
		    String tiSQL = ", "+Value.ValueDao.timestamptable+" AS ti  WHERE ti.object_id=tv.object_id AND ti."+Value.ValueDao.integertabledef[0][0]+"=t.value_id";
		    String bSQL = ", "+Value.ValueDao.booleantable+" AS b  WHERE b.object_id=t.object_id AND b."+Value.ValueDao.integertabledef[0][0]+"=t.value_id";
		    String cSQL = ", "+Value.ValueDao.complextable+" AS c  WHERE c.object_id=t.object_id AND c."+Value.ValueDao.integertabledef[0][0]+"=t.value_id"; 
		    String blSQL = ", "+Value.ValueDao.blobtable+" AS bl  WHERE bl.object_id=t.object_id AND bl."+Value.ValueDao.integertabledef[0][0]+"=t.value_id"; 

				String[] vSQL = {iSQL,sSQL,tSQL,tiSQL,bSQL,cSQL,blSQL};
				
				
				dbs = conn.createStatement();
			
				for(String f:vSQL) {
					String fSQL = String.format(SQL, f);
					if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+fSQL+"]----";LOG.info(String.format(fmt,_f,msg));}
					rs = dbs.executeQuery(fSQL);		
					if(rs.next()) {
						a = _rsToA(rs);
						a = _rsToAV(a, rs);
						a.setHistory(CampInstanceDao.instance().rsToI(rs, log));
						Value<?> v = Value.ValueDao.rsToV(rs, log);
						a = setValue(a, v);
						retVal = 1;
					}
				}
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' attribute entr"+((retVal!=1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				
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
				msg = "====[_instanceLoad completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			return a;
    }

    @SuppressWarnings("unchecked")
		@Override
    public AttributeList instanceListLoad(String select, boolean primary, boolean log) {
    	return _instanceListLoad(select, primary, log);
    }
    public static AttributeList _instanceListLoad(String select, boolean primary, boolean log) {
    	long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[_instanceLoad]";
				msg = "====[ load attribute (definition and value aspects) object instance ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
			AttributeList al = new AttributeList();
			
			Connection conn = null;
			ResultSet rs = null;
			Statement dbs = null;
			try{
				conn = Util.DB.__conn(log);
				
		  String SQL = "SELECT * FROM "+valuetable+" AS t" + ", "+table+" AS td, "+CampInstanceDao.table+" AS ci %s"
		    		+ " AND td._attribute_type_id_=t.attribute_type_id"
		    		+ select;
		  
		    String iSQL = ", "+Value.ValueDao.integertable+" AS i WHERE i.object_id=t.object_id AND i."+Value.ValueDao.integertabledef[0][0]+"=t.value_id"; 
		    String sSQL =  ", "+Value.ValueDao.stringtable+" AS s WHERE s.object_id=t.object_id AND s."+Value.ValueDao.integertabledef[0][0]+"=t.value_id";
		    String tSQL = ", "+Value.ValueDao.texttable+" AS tx  WHERE tx.object_id=t.object_id AND tx."+Value.ValueDao.integertabledef[0][0]+"=t.value_id";
		    String tiSQL = ", "+Value.ValueDao.timestamptable+" AS ti  WHERE ti.object_id=tv.object_id AND ti."+Value.ValueDao.integertabledef[0][0]+"=t.value_id";
		    String bSQL = ", "+Value.ValueDao.booleantable+" AS b  WHERE b.object_id=t.object_id AND b."+Value.ValueDao.integertabledef[0][0]+"=t.value_id";
		    String cSQL = ", "+Value.ValueDao.complextable+" AS c  WHERE c.object_id=t.object_id AND c."+Value.ValueDao.integertabledef[0][0]+"=t.value_id"; 
		    String blSQL = ", "+Value.ValueDao.blobtable+" AS bl  WHERE bl.object_id=t.object_id AND bl."+Value.ValueDao.integertabledef[0][0]+"=t.value_id"; 

				String[] vSQL = {iSQL,sSQL,tSQL,tiSQL,bSQL,cSQL,blSQL};
				
				
				dbs = conn.createStatement();
			
				for(String f:vSQL) {
					String fSQL = String.format(SQL, f);
					if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+fSQL+"]----";LOG.info(String.format(fmt,_f,msg));}
					rs = dbs.executeQuery(fSQL);		
					while(rs.next()) {
						Attribute<?> a = _rsToA(rs);
						a = _rsToAV(a, rs);
						a.setHistory(CampInstanceDao.instance().rsToI(rs, log));
						Value<?> v = Value.ValueDao.rsToV(rs, log);
						a = setValue(a, v);
						al.add(a);
					}
				}
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + al.size() + "' attribute entr"+((al.size()!=1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				
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
				msg = "====[_instanceLoad completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			return al;
    }

 
    public static AttributeList  _rsToAttributeList(ResultSet rs) throws SQLException {
    	AttributeList al = new AttributeList();
    	while(rs.next()) {
    		Attribute<?> a = _rsToA(rs);
    		a.setHistory(CampInstanceDao.instance().rsToI(rs, !Util._IN_PRODUCTION));
    		al.add(a);
    	}
    	return al;
    }

    public static Attribute<?> _rsToAttribute(ResultSet rs) throws SQLException {
    	Attribute<?> a = null;
      if(rs.next()) {
      	a =  _rsToA(rs);
      	if(!Util._IN_PRODUCTION){String msg = "----[Loaded "+a.attributeType().name()+" attribute("+a.id()+") :"+a.name()+" ]----";LOG.info(String.format(fmt,"[_rsToAttribute]",msg));}
      	a.setHistory(CampInstanceDao.instance().rsToI(rs, !Util._IN_PRODUCTION));
      }
      return a;
    }
   
    public static Attribute<?> _rsToA(ResultSet rs) throws SQLException {
    	int id = rs.getInt(tabledef[0][0]);
    	String name = rs.getString("name");
    	AttributeType type = AttributeType.valueOf(rs.getString("type"));
    	int parentId = rs.getInt("parent_id");
    	String defaultValue = rs.getString("default_value");
    	String businessId = rs.getString("business_id");
    	String businessKey = rs.getString("businesskey");
    	String group = rs.getString("group");
    	String version = rs.getString("version");
    	int position = rs.getInt("position");
    	Attribute<?> a = Attribute.createAttribute(name, type, defaultValue);
    	a.updateId(id);
    	a.parentId(parentId);
    	a.setBusinessId(businessId);
    	a.setBusinessKey(businessKey);
    	a.setGroup(group);
    	a.setVersion(version);
    	a.setPosition(position);
    	return a;
    }
    
    public static AttributeList  _rsToVAttributeList(AttributeList al, ResultSet rs) throws SQLException {
    	AttributeList ral = new AttributeList();
    	while(rs.next()) {
    		int id = rs.getInt("attribute_type_id");
    		for(Attribute<?>a:al) {
    			if(id==a.id()) {
    				a = _rsToAV(a,rs);
        		a.setHistory(CampInstanceDao.instance().rsToI(rs, !Util._IN_PRODUCTION));
    				ral.add(a);
    			}
    		}
    	}
    	return ral;
    }

    public static Attribute<?> _rsToVAttribute(Attribute<?> a, ResultSet rs) throws SQLException {
      if(rs.next()) {
      	return _rsToAV(a,rs);
      }
      throw new SQLException("SQLException! ResultSet has no next() entry!");
    }
   
    public static Attribute<?> _rsToAV(Attribute<?> a, ResultSet rs) throws SQLException{
    	int id = rs.getInt("attribute_type_id");
    	if(a.id() != id) {
    		throw new SQLException("Exception! Attribute definition to value aspect mismatch!" );
    	}
    	
    	a.attributeId(rs.getInt(valuetabledef[0][0]));
//    	int objectId = rs.getInt("object_id");
    	a.parentId(rs.getInt("parent_type_id"));
    	a.valueId(rs.getInt("value_id"));
    	a.setAttributeGroup(rs.getString("attribute_group"));
      a.setAttributePosition(rs.getInt("attribute_position"));
      return a;
    }

    public static ValueList getValueList(AttributeList attributeList) {
    	ValueList vl = new ValueList();
      for(Attribute<?>a : attributeList) {
      	vl.add(a.value()); 
      	if(!Util._IN_PRODUCTION){String msg = "----[added Value("+a.value().type().name()+") to ValueList]----";LOG.info(String.format(fmt, "[_getValueList]",msg));}
      }
      return vl;
    }
    public static HashMap<Integer,String> getValueIds(AttributeList attributeList) {
    	HashMap<Integer,String> vl = new HashMap<Integer,String>();
      for(Attribute<?>a : attributeList) {
      	vl.put(a.valueId(),CampSQL.System.attribute_value_tables.get(a.attributeType())); 
      }
      return vl;
    }
    public static AttributeList setValues(AttributeList al,ValueList vl) {
    	for(Value<?>v : vl) {
    		for(Attribute<?> a: al) {
    			if(a.valueId() == v.id()) {
    				setValue(a,v);
    			}
    		}
    	}
    	return al;
    }
    public static AttributeList setValuesPreSave(AttributeList al,ValueList vl) {
    	int count = 0;
    	for(Value<?> v : vl) {
    		setValue(al.get(count),v);
    		count++;
    	}
    	return al;
    }
    public static Attribute<?> setValue(Attribute<?> a,Value<?> v){
  		switch(v.type()) {
			case _integer:
				((CampInteger)a).setValue((IntegerValue)v);
				break;
			case _string:
				((CampString)a).setValue((StringValue)v);
				break;
			case _text:
				((CampText)a).setValue((TextValue)v);
				break;
			case _boolean:
				((CampBoolean)a).setValue((BooleanValue)v);
				break;
			case _timestamp:
				((CampTimestamp)a).setValue((TimestampValue)v);
				break;
			case _datetime:
				((CampDateTime)a).setValue((DateTimeValue)v);
				break;
			case _date:
				((CampDate)a).setValue((DateValue)v);
				break;
			case _time:
				((CampTime)a).setValue((TimeValue)v);
				break;
			case _map:
				((CampMap)a).setValue((MapValue)v);
				break;
			case _list:
				((CampList)a).setValue((ListValue)v);
				break;
			case _complex:
				((CampComplex)a).setValue((ComplexValue)v);
				break;
			case _table:
				((CampTable)a).setValue((TableValue)v);
				break;
			default:
				break;

  		}
			a.valueId(v.id());
  		return a;

    }
    public static String insertDefinitionValues(AttributeList attributeList) {
    	String SQL = "";
      boolean start = true;
      for(Attribute<?> a:attributeList) {
      	if(!start) {
      		SQL += ",";
      	} else {
      		start = false;
      	}
        SQL += "("+ insertDefinitionValues(a) +")";
      }
    	return SQL;
    }
    
    public static String insertDefinitionValues(Attribute<?> a) {
    	String values = "";
    	values += "'"+a.name()+"'"
    			+ ",'"+a.attributeType().name()+"'"
    			+ ","+a.parentId()
    			+ ",'"+a.defaultValue()+"'"
    			+ ","+((a.onlyBusinessId()==null)?"NULL":"'"+a.onlyBusinessId()+"'")
    			+ ","+((a.businessKey()==null)?"NULL":"'"+a.businessKey()+"'")
    			+ ","+((a.group()==null)?"NULL":"'"+a.group().name()+"'")
    			+ ","+((a.version() == null)?"NULL":"'"+a.version().value()+"'")
    			+ ","+a.position();
    	return values;
    }
    
    public static String insertDefinitionUpdates(String fSQL, Attribute<?> attribute) {
      String SQL =
          String.format(
             fSQL,
              "'"+attribute.name()+"'",
              "'"+attribute.attributeType().name()+"'",
              attribute.parentId(),
              "'"+attribute.defaultValue()+"'",
              "'"+attribute.onlyBusinessId()+"'",
              "'"+attribute.businessKey()+"'",
              "'"+attribute.group().name()+"'",
              "'"+attribute.version().value()+"'",
              attribute.position(),
              attribute.id());
      return SQL;
    }
    
    public static String insertAttributeValues(int objectId,AttributeList attributeList) {
    	String SQL = "";
      boolean start = true;
      for(Attribute<?> a: attributeList) {
      	if(!start) {
      		SQL += ",";
      	} else {
      		start = false;
      	}
      	SQL += "("+insertAttributeValues(objectId,a)+")";
      }
      return SQL;
    }

    public static String insertAttributeValues(int objectId,Attribute<?> a) {
    	String values = "";
    	values += objectId 
    			+","+a.id()
    			+","+a.parentId()
    			+","+a.attributeParentId()
    			+","+a.valueId()
    			+","+((a.attributeBusinessKey()== null || a.attributeBusinessKey().isEmpty())?"NULL":"'"+a.attributeBusinessKey()+"'")
    			+","+"'"+a.attributeGroup().name()+"'"
    			+","+a.attributePosition();
    	return values;
    }
    
    public static String insertAttributeVUpdates(String fSQL, int oid, Attribute<?> attribute) {
      String SQL =
          String.format(
             fSQL,
              oid,
              attribute.id(),
              attribute.parentId(),
              attribute.attributeParentId(),
              attribute.value().id(),
              "'"+attribute.attributeBusinessKey()+"'",
              "'"+attribute.attributeGroup().name()+"'",
              attribute.attributePosition(),
              attribute.attributeId());
      return SQL;
    }

	@Override
	public int createTable(boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[createTable]";
			msg = "====[ Creating attribute definition and value tables .... ]====";LOG.info(String.format(fmt,_f,msg));
		}
				
		// create the database schema if they do not exist.
		Util.DB._createDatabases(log);
		
		Util.DB.dbActionType action = Util.DB.dbActionType.CREATE;
		
		if(log && !Util._IN_PRODUCTION) {msg = "----[assembling '"+action.name().toLowerCase()+"' columns]----";LOG.info(String.format(fmt,_f,msg));}
		
		String colDef1 = Util.DB._columns(tabledef, action, log);
		String SQL1 = "CREATE TABLE IF NOT EXISTS "+table+" "
				    + " ( "+  colDef1 + ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL1+"]----";LOG.info(String.format(fmt,_f,msg));}
		
		String colDef2 = Util.DB._columns(valuetabledef, action, log);
		String SQL2 = "CREATE TABLE IF NOT EXISTS "+valuetable+" "
				    + " ( "+  colDef2 + ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL2+"]----";LOG.info(String.format(fmt,_f,msg));}
		
		String colDef3 = Util.DB._columns(updatestabledef, action, log);
		String SQL3 = "CREATE TABLE IF NOT EXISTS "+updatestable+" "
				    + " ( "+  colDef3 + ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL3+"]----";LOG.info(String.format(fmt,_f,msg));}
		
		String colDef4 = Util.DB._columns(ahptabledef, action, log);
		String SQL4 = "CREATE TABLE IF NOT EXISTS "+ahptable+" "
				    + " ( "+  colDef4 + ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL4+"]----";LOG.info(String.format(fmt,_f,msg));}
		
		Connection conn = null;
		Statement dbs = null;
		int retVal = 0;
		try {
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			dbs.addBatch(SQL1);
			dbs.addBatch(SQL2);
			dbs.addBatch(SQL3);
			dbs.addBatch(SQL4);
			
			retVal = Util.Math.addArray(dbs.executeBatch());
		} catch (Exception e) {
			msg= "SQL Exception Happend!!!";if(log && !Util._IN_PRODUCTION)LOG.info(String.format(fmt,_f,msg));
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[Releasing Connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseStatement(dbs, log);
		}			
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_createTable completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
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
			msg = "====[ Clearing attribute definition and value tables .... ]====";LOG.info(String.format(fmt,_f,msg));
		}
				
		// create the database schema if they do not exist.
		Util.DB._createDatabases(log);
		
		Util.DB.dbActionType action = Util.DB.dbActionType.CREATE;
		
		if(log && !Util._IN_PRODUCTION) {msg = "----[assembling '"+action.name().toLowerCase()+"' columns]----";LOG.info(String.format(fmt,_f,msg));}
		
		String SQL1 = "DELETE FROM "+table;
		if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL1+"]----";LOG.info(String.format(fmt,_f,msg));}
		
		String SQL2 = "DELETE FROM "+valuetable;
		if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL2+"]----";LOG.info(String.format(fmt,_f,msg));}
		
		String SQL3 = "DELETE FROM "+updatestable;
		if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL3+"]----";LOG.info(String.format(fmt,_f,msg));}
		
		String SQL4 = "DELETE FROM "+ahptable;
		if(log && !Util._IN_PRODUCTION){ msg = "----[SQL : "+SQL4+"]----";LOG.info(String.format(fmt,_f,msg));}
		
		Connection conn = null;
		Statement dbs = null;
		int retVal = 0;
		try {
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			dbs.addBatch(SQL1);
			dbs.addBatch(SQL2);
			dbs.addBatch(SQL3);
			dbs.addBatch(SQL4);
			
			retVal = Util.Math.addArray(dbs.executeBatch());
		} catch (Exception e) {
			msg= "SQL Exception Happend!!!";if(log && !Util._IN_PRODUCTION)LOG.info(String.format(fmt,_f,msg));
			e.printStackTrace();
		} finally {
			if(log && !Util._IN_PRODUCTION){msg = "----[Releasing Connection]----";LOG.info(String.format(fmt, _f,msg));}
			Util.DB.__release(conn,log);
			Util.DB._releaseStatement(dbs, log);
		}			
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[_clearTables completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}

		return retVal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Attribute<?>>> E loadUpdates(String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdates]";
			msg = "====[ load attributes registered in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
//		AttributeList al = new AttributeList();
		ArrayList<Attribute<?>> al = new ArrayList<Attribute<?>>();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
		  String SQL = "SELECT * FROM "+valuetable+" AS tv" + ", "+table+" AS td, , "+CampInstanceDao.table+" AS ci %s"
	    		+ " AND td._attribute_type_id_=tv.attribute_type_id"
	    		+ " AND ci.`_instance_id`=ci.`_current_instance_id` "
	    		+ " AND tv.`"+valuetabledef[0][0]+"`=ci.`_object_id` "
	    		+ " AND ci.`_object_business_id`=t.`attribute_businesskey` "
	    		+ " AND WHERE EXISTS "
	    		+ "( SELECT `_au_attribute_value_id` FROM "+updatestable+" AS u WHERE "
	    		+ " u.`_au_attribute_value_id`=tv.`"+valuetabledef[0][0]+"` "
	    		+ " AND u.`_au_object_id`=tv.`object_id` "
	    		+ " AND u.`_au_businesskey`='"+businessKey+"' "
	    		+ " AND u.`_au_target`='"+target+"')";
	  
	    String iSQL = ", "+Value.ValueDao.integertable+" AS i WHERE i.object_id=tv.object_id AND i."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
	    String sSQL =  ", "+Value.ValueDao.stringtable+" AS s WHERE s.object_id=tv.object_id AND s."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String tSQL = ", "+Value.ValueDao.texttable+" AS t  WHERE t.object_id=tv.object_id AND t."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String tiSQL = ", "+Value.ValueDao.timestamptable+" AS ti  WHERE ti.object_id=tv.object_id AND ti."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String bSQL = ", "+Value.ValueDao.booleantable+" AS b  WHERE b.object_id=tv.object_id AND b."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String cSQL = ", "+Value.ValueDao.complextable+" AS c  WHERE c.object_id=tv.object_id AND c."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
	    String blSQL = ", "+Value.ValueDao.blobtable+" AS bl  WHERE bl.object_id=tv.object_id AND bl."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 

			String[] vSQL = {iSQL,sSQL,tSQL,tiSQL,bSQL,cSQL,blSQL};
			
			
			dbs = conn.createStatement();
		
			for(String f:vSQL) {
				String fSQL = String.format(SQL, f);
				if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+fSQL+"]----";LOG.info(String.format(fmt,_f,msg));}
				rs = dbs.executeQuery(fSQL);		
				while(rs.next()) {
					Attribute<?> a = _rsToA(rs);
					a = _rsToAV(a, rs);
					a.setHistory(CampInstanceDao.instance().rsToI(rs, log));
					Value<?> v = Value.ValueDao.rsToV(rs, log);
					a = setValue(a, v);
					al.add(a);
				}
			}
			if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + al.size() + "' attribute entr"+((al.size()>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
			
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
			msg = "====[loadUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E) al;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Attribute<?>>> E loadUpdatesByKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByKey]";
			msg = "====[ load all attribute object instances (definition and value aspect) registered in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		AttributeList al = new AttributeList();
//		ArrayList<Attribute<?>> al - new ArrayList<Attribute<?>>();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
		  String SQL = "SELECT * FROM "+valuetable+" AS tv" + ", "+table+" AS td, , "+CampInstanceDao.table+" AS ci %s"
	    		+ " AND td._attribute_type_id_=tv.attribute_type_id"
	    		+ " AND ci.`_instance_id`=ci.`_current_instance_id` "
	    		+ " AND tv.`"+valuetabledef[0][0]+"`=ci.`_object_id` "
	    		+ " AND ci.`_object_business_id`=t.`attribute_businesskey` "
	    		+ " AND WHERE EXISTS "
	    		+ "( SELECT `_au_attribute_value_id` FROM "+updatestable+" AS u WHERE "
	    		+ " u.`_au_attribute_value_id`=tv.`"+valuetabledef[0][0]+"` "
	    		+ " AND u.`_au_object_id`=tv.`object_id` "
	    		+ " AND u.`_au_businesskey`='"+businessKey+"')";
	  
	    String iSQL = ", "+Value.ValueDao.integertable+" AS i WHERE i.object_id=tv.object_id AND i."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
	    String sSQL =  ", "+Value.ValueDao.stringtable+" AS s WHERE s.object_id=tv.object_id AND s."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String tSQL = ", "+Value.ValueDao.texttable+" AS t  WHERE t.object_id=tv.object_id AND t."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String tiSQL = ", "+Value.ValueDao.timestamptable+" AS ti  WHERE ti.object_id=tv.object_id AND ti."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String bSQL = ", "+Value.ValueDao.booleantable+" AS b  WHERE b.object_id=tv.object_id AND b."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String cSQL = ", "+Value.ValueDao.complextable+" AS c  WHERE c.object_id=tv.object_id AND c."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
	    String blSQL = ", "+Value.ValueDao.blobtable+" AS bl  WHERE bl.object_id=tv.object_id AND bl."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 

			String[] vSQL = {iSQL,sSQL,tSQL,tiSQL,bSQL,cSQL,blSQL};
			
			
			dbs = conn.createStatement();
		
			for(String f:vSQL) {
				String fSQL = String.format(SQL, f);
				if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+fSQL+"]----";LOG.info(String.format(fmt,_f,msg));}
				rs = dbs.executeQuery(fSQL);		
				while(rs.next()) {
					Attribute<?> a = _rsToA(rs);
					a = _rsToAV(a, rs);
					a.setHistory(CampInstanceDao.instance().rsToI(rs, log));
					Value<?> v = Value.ValueDao.rsToV(rs, log);
					a = setValue(a, v);
					al.add(a);
				}
			}
			if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + al.size() + "' attribute entr"+((al.size()>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
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
			msg = "====[loadUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E) al;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Attribute<?>>> E loadUpdatesByTarget(String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByTarget]";
			msg = "====[ load all attribute object instances registered in the updates table that share the same target ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		AttributeList al = new AttributeList();
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
		  String SQL = "SELECT * FROM "+valuetable+" AS tv" + ", "+table+" AS td, , "+CampInstanceDao.table+" AS ci %s"
	    		+ " AND td._attribute_type_id_=tv.attribute_type_id"
	    		+ " AND ci.`_instance_id`=ci.`_current_instance_id` "
	    		+ " AND tv.`"+valuetabledef[0][0]+"`=ci.`_object_id` "
	    		+ " AND ci.`_object_business_id`=t.`attribute_businesskey` "
	    		+ " AND WHERE EXISTS "
	    		+ "( SELECT `_au_attribute_value_id` FROM "+updatestable+" AS u WHERE "
	    		+ " u.`_au_attribute_value_id`=tv.`"+valuetabledef[0][0]+"` "
	    		+ " AND u.`_au_object_id`=tv.`object_id` "
	    		+ " AND u.`_au_businesskey`=tv.`attribute_businesskey` "
	    		+ " AND u.`_au_target`='"+target+"')";
	  
	    String iSQL = ", "+Value.ValueDao.integertable+" AS i WHERE i.object_id=tv.object_id AND i."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
	    String sSQL =  ", "+Value.ValueDao.stringtable+" AS s WHERE s.object_id=tv.object_id AND s."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String tSQL = ", "+Value.ValueDao.texttable+" AS t  WHERE t.object_id=tv.object_id AND t."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String tiSQL = ", "+Value.ValueDao.timestamptable+" AS ti  WHERE ti.object_id=tv.object_id AND ti."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String bSQL = ", "+Value.ValueDao.booleantable+" AS b  WHERE b.object_id=tv.object_id AND b."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String cSQL = ", "+Value.ValueDao.complextable+" AS c  WHERE c.object_id=tv.object_id AND c."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
	    String blSQL = ", "+Value.ValueDao.blobtable+" AS bl  WHERE bl.object_id=tv.object_id AND bl."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 

			String[] vSQL = {iSQL,sSQL,tSQL,tiSQL,bSQL,cSQL,blSQL};
			
			
			dbs = conn.createStatement();
		
			for(String f:vSQL) {
				String fSQL = String.format(SQL, f);
				if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+fSQL+"]----";LOG.info(String.format(fmt,_f,msg));}
				rs = dbs.executeQuery(fSQL);		
				while(rs.next()) {
					Attribute<?> a = _rsToA(rs);
					a = _rsToAV(a, rs);
					a.setHistory(CampInstanceDao.instance().rsToI(rs, log));
					Value<?> v = Value.ValueDao.rsToV(rs, log);
					a = setValue(a, v);
					al.add(a);
				}
			}
			if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + al.size() + "' attribute entr"+((al.size()>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
			
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
			msg = "====[loadUpdatesByTarget completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E) al;
	}

	@Override
	public Attribute<?> loadUpdate(Attribute<?> a, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdate]";
			msg = "====[ load an attribute object instance that is registered in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
		  String SQL = "SELECT * FROM "+valuetable+" AS tv" + ", "+table+" AS td, , "+CampInstanceDao.table+" AS ci %s"
	    		+ " AND tv.`"+valuetabledef[0][0]+"`="+a.attributeId()
	    		+ " AND td._attribute_type_id_=tv.attribute_type_id"
	    		+ " AND ci.`_instance_id`=ci.`_current_instance_id` "
	    		+ " AND tv.`"+valuetabledef[0][0]+"`=ci.`_object_id` "
	    		+ " AND ci.`_object_business_id`=t.`attribute_businesskey` "
	    		+ " AND WHERE EXISTS "
	    		+ "( SELECT `_au_attribute_value_id` FROM "+updatestable+" AS u WHERE "
	    		+ " u.`_au_attribute_value_id`="+a.attributeId()
	    		+ " AND u.`_au_attribute_value_id`=tv.`"+valuetabledef[0][0]+"` "
	    		+ " AND u.`_au_object_id`="+a.getObjectId()+" "
	    		+ " AND u.`_au_businesskey`="+businessKey+" "
	    		+ " AND u.`_au_target`='"+target+"')";
	  
	    String iSQL = ", "+Value.ValueDao.integertable+" AS i WHERE i.object_id=tv.object_id AND i."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
	    String sSQL =  ", "+Value.ValueDao.stringtable+" AS s WHERE s.object_id=tv.object_id AND s."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String tSQL = ", "+Value.ValueDao.texttable+" AS t  WHERE t.object_id=tv.object_id AND t."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String tiSQL = ", "+Value.ValueDao.timestamptable+" AS ti  WHERE ti.object_id=tv.object_id AND ti."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String bSQL = ", "+Value.ValueDao.booleantable+" AS b  WHERE b.object_id=tv.object_id AND b."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id";
	    String cSQL = ", "+Value.ValueDao.complextable+" AS c  WHERE c.object_id=tv.object_id AND c."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 
	    String blSQL = ", "+Value.ValueDao.blobtable+" AS bl  WHERE bl.object_id=tv.object_id AND bl."+Value.ValueDao.integertabledef[0][0]+"=tv.value_id"; 

			String[] vSQL = {iSQL,sSQL,tSQL,tiSQL,bSQL,cSQL,blSQL};
			
			
			dbs = conn.createStatement();
		
			for(String f:vSQL) {
				String fSQL = String.format(SQL, f);
				if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+fSQL+"]----";LOG.info(String.format(fmt,_f,msg));}
				rs = dbs.executeQuery(fSQL);		
				if(rs.next()) {
					a = _rsToA(rs);
					a = _rsToAV(a, rs);
					a.setHistory(CampInstanceDao.instance().rsToI(rs, log));
					Value<?> v = Value.ValueDao.rsToV(rs, log);
					a = setValue(a, v);
					retVal = 1;
				}
			}
			if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' attribute entr"+((retVal!=1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
			
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
			msg = "====[loadUpdate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (retVal == 0)?null:a;
	}

	@Override
	public int addToUpdates(Attribute<?> a, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[ register an attribute object instance (value aspect) in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + updatestable + "( " + Util.DB._columns(updatestabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( " +insertUpdateValues(a,businessKey,target)+" )";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			retVal = dbs.executeUpdate(SQL);

			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" registered in updates ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
	public <E extends ArrayList<Attribute<?>>> int addToUpdates(E al, String businessKey, String target,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[ register a list of attribute object instances (value aspects) in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + updatestable + "( " + Util.DB._columns(updatestabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES " + insertUpdateListValues((AttributeList)al,businessKey,target);
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			retVal = dbs.executeUpdate(SQL);

			if(log && !Util._IN_PRODUCTION) {msg = "----[ '"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" registered in updates ]----";LOG.info(String.format(fmt,_f,msg));}
			
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
			msg = "====[ deregister a list of attribute object instances from the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM "+updatestable+" WHERE "
					+ "`_au_businesskey`='"+businessKey+"' AND `_au_target`='"+target+"'";
			
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
			_f = "[deleteFromUpdatesByKey]";
			msg = "====[ deregister a list of attribute object instances from the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM "+updatestable+" WHERE "
					+ "`_au_businesskey`='"+businessKey+"'";
			
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
			msg = "====[ deregister a list of attribute object instances from the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM "+updatestable+" WHERE "
					+ "`_au_target`='"+target+"'";
			
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
			msg = "====[deleteFromUpdatesByTarget completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteFromUpdates(String instanceId, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[ deregister an attribute object instance from the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String[] ids = instanceId.split(Util.DB._VS);
		if(ids.length < 2) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ERROR! instanceId has wrong format! Format must be: <attributeValueId>"+Util.DB._VS+"<objectId> ]----";LOG.info(String.format(fmt, _f,msg));}
			return 0;
		}

		int id = Integer.valueOf(ids[0]);
		int objectId = Integer.valueOf(ids[1]);
		
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM "+updatestable+" WHERE "
					+ "`_au_attribute_value_id`="+id+" AND `_au_object_id`="+objectId+" "
					+ " AND `_au_businesskey`='"+businessKey+"' AND `_au_target`='"+target+"'";
			
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
	public <E extends ArrayList<Attribute<?>>> int deleteFromUpdates(E al, String businessKey, String target,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[ deregister a list of attribute object instances from the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			for(Attribute<?> a:al) {
				String SQL = "DELETE FROM "+updatestable+" WHERE "
						+ "`_au_attribute_value_id`="+a.attributeId()+" AND `_au_object_id`="+a.getObjectId()+" "
						+ " AND `_au_businesskey`='"+businessKey+"' AND `_au_target`='"+target+"'";
				
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

	public String insertUpdateValues(Attribute<?> a, String businessKey, String target) {
		return a.attributeId()+","+a.getObjectId()+",'"+businessKey+"','"+target+"'";
	}
	
	public String insertUpdateListValues(AttributeList al, String businessKey, String target) {
		String values = "";
		boolean start = true;
		for(Attribute<?> a: al) {
			if(!start) {
				values += ",";
			} else {
				start = false;
			}
			values += "("+insertUpdateValues(a,businessKey,target)+")"; 
		}
		return values;
	}

	@Override
	public String dbName(boolean primary) {
		return (primary)?valueDbName:dbName;
	}

	@Override
	public String table(boolean primary) {
		return (primary)?valuetable:table;
	}

	@Override
	public String[][] tabledef(boolean primary) {
		return (primary)?valuetabledef:tabledef;
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
		return (primary)?"attribute_businesskey":"business_id";
	}

	@Override
	public Attribute<?> loadFirst(String businessId) {
		return _loadFirst(businessId, !Util._IN_PRODUCTION);
	}
	public static Attribute<?> _loadFirst(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadFirst]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Attribute<?> a = null;
		try {
			a = CampInstanceDao.instance()._loadFirst(businessId, AttributeDao.instance(), false,log);
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
	public Attribute<?> loadPrevious(Attribute<?> attribute) {
		return _loadPrevious(attribute, !Util._IN_PRODUCTION);
	}
	public static Attribute<?> _loadPrevious(Attribute<?> attribute,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadPrevious]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Attribute<?> a = null;
		try {
			a = CampInstanceDao.instance()._loadPrevious(attribute, AttributeDao.instance(), false, log);
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
	public Attribute<?> loadNext(Attribute<?> attribute) {
		return _loadNext(attribute, !Util._IN_PRODUCTION);
	}
	public Attribute<?> _loadNext(Attribute<?> attribute, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadNext]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Attribute<?> a = null;
		try {
			a = CampInstanceDao.instance()._loadNext(attribute, AttributeDao.instance(), false, log);
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
	public AttributeList loadDate(String businessId, String date) {
		return _loadDate(businessId, date, !Util._IN_PRODUCTION);
	}
	public AttributeList _loadDate(String businessId, String date, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		AttributeList al = null;
		try {
			al = CampInstanceDao.instance()._loadDate(businessId, date, AttributeDao.instance(), false,log);
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
	public AttributeList loadDateRange(String businessId, String startDate, String endDate) {
		return _loadDateRange(businessId, startDate, endDate, !Util._IN_PRODUCTION);
	}
	public AttributeList _loadDateRange(String businessId, String startDate, String endDate, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDateRange]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		AttributeList al = null;
		try {
			al = CampInstanceDao.instance()._loadDateRange(businessId, startDate, endDate, AttributeDao.instance(), false,log);
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
	public AttributeList loadDate(String date) {
		return _loadDate(date,!Util._IN_PRODUCTION);
	}
	public AttributeList _loadDate(String date,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		AttributeList al = null;
		try {
			al = CampInstanceDao.instance()._loadDate(date, AttributeDao.instance(), false,log);
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
	public AttributeList loadDateRange(String startDate, String endDate) {
		return _loadDateRange(startDate,endDate,!Util._IN_PRODUCTION);
	}
	public AttributeList _loadDateRange(String startDate, String endDate, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[_loadDateRange]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		AttributeList al = null;
		try {
			al = CampInstanceDao.instance()._loadDateRange(startDate, endDate, AttributeDao.instance(), false,log);
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

	@SuppressWarnings("unchecked")
	@Override
	public ProcessList loadProcessReferences(String objectBusinessId ,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadProcesses]";
			msg = "====[ load all processes associated with the current order object instance ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProcessList pl = new ProcessList();
		String[] ids = objectBusinessId.split(Util.DB._VS);
		if(ids.length <2) {
			return pl;
		}
		String businessId = ids[0];
		String attributeBusinessId = ids[1];
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "SELECT * FROM "+ahptable+" AS a, "+ ProcessDao.table+" AS p WHERE "
					+ " a.`_ahp_attribute_business_id`='"+attributeBusinessId+"' "
					+ " AND a.`_ahp_business_id`='"+businessId+"' "
					+ " AND a.`_ohp_process_key`=p.`process_instance_id` "
					+ " AND a.`_ohp_process_key`=p.`businesskey` "
					+ " ORDER BY p.`process_type`";
			
			if(log && !Util._IN_PRODUCTION) {msg = "----[ SQL: "+SQL+"]----";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.executeQuery(SQL);		
			
			if (rs.next()) {		
				ProductAttributeProcess<?> ap = ProcessDao.instance().rsToI(rs, log);
				ap.states().ioAction(IOAction.LOAD);
				ap.states().setModified(false); // ensure that we don't get re-registered
				pl.add(ap);
			}
			retVal = pl.size();
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
			msg = "====[loadProcesses completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public int addProcessReference(String objectBusinessId, String instanceId, String processKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addProcessReference]";
			msg = "====[ register an associated process instance in the reference table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String[] ids = objectBusinessId.split(Util.DB._VS);
		if(ids.length <2) {
			return 0;
		}
		String businessId = ids[0];
		String attributeBusinessId = ids[1];
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + ahptable + "( " + Util.DB._columns(ahptabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES ( '"+attributeBusinessId+"','"+businessId+"','"+instanceId+"','"+processKey+"' )";
			
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
			msg = "====[addProcessReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}
	@Override
	public int addProcessReferences(String objectBusinessId, ProcessList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addProcessReferences]";
			msg = "====[ register all associated process instances in the reference table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String[] ids = objectBusinessId.split(Util.DB._VS);
		if(ids.length <2) {
			return 0;
		}
		String businessId = ids[0];
		String attributeBusinessId = ids[1];
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "INSERT INTO " + ahptable + "( " + Util.DB._columns(ahptabledef, Util.DB.dbActionType.INSERT, log)
			+ " ) VALUES "+insertProcessReferenceValues(pl,attributeBusinessId,businessId,log);
				
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
	public int delProcessReference(String objectBusinessId, String instanceId, String processKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delProcessReference]";
			msg = "====[ deregister an associated process instance from the reference table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String[] ids = objectBusinessId.split(Util.DB._VS);
		if(ids.length <2) {
			return 0;
		}
		String businessId = ids[0];
		String attributeBusinessId = ids[1];
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			
			String SQL = "DELETE FROM "+ahptable+ " WHERE "
					+ "`_ahp_business_id`='"+attributeBusinessId+"' "
					+ " AND `_ahp_business_id`='"+businessId+"' "
					+ " AND `_ahp_process_instance_id`='"+instanceId+"' "
							+ " AND `_ahp_process_key`='"+processKey+"'";
			
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
			msg = "====[delProcessReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int delAllProcessReferences(String objectBusinessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delProcessReferences]";
			msg = "====[ deregister all associated process instances from the reference table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String[] ids = objectBusinessId.split(Util.DB._VS);
		if(ids.length <2) {
			return 0;
		}
		String businessId = ids[0];
		String attributeBusinessId = ids[1];
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			String SQL = "DELETE FROM "+ahptable+ " WHERE "
						+ "`_ahp_attribute_business_id`='"+attributeBusinessId+"'"
						+ " AND `_ahp_business_id`='"+businessId+"'";
			
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
	public int delProcessReferences(String objectBusinessId, ProcessList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delProcessReferences]";
			msg = "====[ deregister all associated process instances from the reference table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String[] ids = objectBusinessId.split(Util.DB._VS);
		if(ids.length <2) {
			return 0;
		}
		String businessId = ids[0];
		String attributeBusinessId = ids[1];
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		int retVal = 0;
		try{
			conn = Util.DB.__conn(log);
			
			dbs = conn.createStatement();
			String SQL = "DELETE FROM "+ahptable+ " WHERE "
						+ "`_ahp_attribute_business_id`='"+attributeBusinessId+"'"
						+ " AND `_ahp_business_id`='"+businessId+"'";

			boolean start = true;
			for(Process<?>p:pl) {
				if(!start) {
					SQL += " OR";
				} else {
					SQL += " AND";
					start = false;
				}
				SQL += " (`_ahp_process_instance_id`='"+p.instanceId()+"' AND `_ahp_process_key`='"+p.businessKey()+"')";
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

	public String insertProcessReferenceValues(ProcessList pl, String attributeBusinessId, String businessId, boolean log) {
		String values = "";
		boolean start = true;
		for(Process<?> pr:pl){
			if(!pr.states().isModified()) continue;
			if(!start) {
				values += ",";
			} else {
				start = false;
			}
			values += "('"+attributeBusinessId+"','"+businessId+"','"+pr.instanceId()+"','"+pr.businessKey()+"')";
			pr.states().setModified(false);
		}
		return values;
	}


}
