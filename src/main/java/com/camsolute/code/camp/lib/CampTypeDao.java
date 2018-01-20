/*******************************************************************************
 * Copyright (C) 2017 Christopher Campbell (campbellccc@gmail.com)
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
 * 	Christopher Campbell (campbellccc@gmail.com) - all code prior and post initial release
 ******************************************************************************/
package com.camsolute.code.camp.lib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.U;
import com.camsolute.code.camp.lib.database.CampSQL;
import com.camsolute.code.camp.lib.utilities.Util;
import com.camsolute.code.camp.lib.utilities.Util.DB.dbActionType;
import com.camsolute.code.camp.lib.exceptions.CampTableRowOutOfBoundsException;
import com.camsolute.code.camp.lib.types.*;
import com.camsolute.code.camp.lib.exceptions.CampTableDoubleCellWriteException;
import com.camsolute.code.camp.lib.models.OrderProductAttribute;
import com.camsolute.code.camp.lib.models.ProductAttribute;
import com.camsolute.code.camp.lib.models.ProductAttributeDefinition;
import com.camsolute.code.camp.lib.models.ProductAttribute.Type;

public class CampTypeDatabase implements AttributeDatabaseInterface {
	public static final boolean _DEBUG = true;
	public static final boolean _IN_PRODUCTION = false;
	private static final Logger LOG = LogManager.getLogger(CampTypeDatabase.class);
	private static String fmt = "[%15s] [%s]";
	
	// SQL table details
	public static String table = CampSQL.pmTable(CampSQL._PRODUCT_DB_INDEX,CampSQL._PRODUCT_ATTRIBUTE_TYPE_TABLE_INDEX);
	public static String[][] tabledef = CampSQL.product_attribute_type_table_definition;		
	
	public static String pahttable =  CampSQL.pmTable(CampSQL._PRODUCT_DB_INDEX, CampSQL._PRODUCT_ATTRIBUTE_HAS_TYPE_TABLE_INDEX);
	public static String[][] pahttabledef = CampSQL.product_attribute_has_type_table_definition;
	
	public static String valuetable =  CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL._PRODUCT_ATTRIBUTE_VALUES_TABLE_INDEX);
	public static String[][] valuetabledef = CampSQL.product_attribute_values_table_definition;

	public static String dbName = CampSQL.database[CampSQL._PRODUCT_DB_INDEX];
	public static String odbName = CampSQL.database[CampSQL._ORDER_DB_INDEX];
	
	// SQL table select queries
	public static String loadValueByIdPSQL = "SELECT * FROM "+valuetable+" WHERE `id_`=?";
	public static String loadByIdSQL = "SELECT * FROM "+table+" WHERE `id_`=%s";
	public static String loadByNameSQL = "SELECT * FROM "+table+" WHERE `name`='%s'";
	public static String loadByTypeSQL = "SELECT * FROM "+table+" WHERE `type`='%s'";
	public static String loadAllSQL = "SELECT * FROM "+table;

	private static CampTypeDatabase instance = null;
	
	private CampTypeDatabase(){
	}
	
	public static CampTypeDatabase instance(){
		if(instance == null) {
			instance = new CampTypeDatabase();
		}
		return instance;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampType<?> createDefinition(int productAttributeId, String name, Type type, String defaultValue) {
		return _createDefinition(productAttributeId, name,type,defaultValue,false);
	}
	public CampType<?> _createDefinition(int paid,String name, Type type, String defaultValue,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[create]";
			msg = "====[ create and save attribute type '"+name+"'/'"+type+"' ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		
		CampType<?> ct = null;
		
		if(defaultValue != null) {
			ct = getType(name,type,defaultValue);
		}else {
			ct = getType(name,type);
		}
		int retVal = 0;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		try{
			
			conn = DBU.__conn(log);

			String SQL = "INSERT INTO "+table+"( "+ DBU._columns(tabledef, dbActionType.INSERT,log)+" ) VALUES ( "+insertTypeValues(ct)+" )";
						  
			if(log && _DEBUG) {msg = "-- --[ SQL: "+SQL+"]-- --";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);
			
			if(log && _DEBUG) {msg = "-- --[ saved '"+retVal+"' entry]-- --";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.getGeneratedKeys();
			
			if (rs.next()) {
		    	
				ct.id(rs.getInt("id_"));

			} else {
		        // TODO: throw an exception from here
		    }
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Save failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
		String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ attribute type created.]====";LOG.info(String.format(fmt,_f,msg+time));
		}
		return ct;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampType<?> create(int productAttributeId, String name, Type type, String defaultValue,Object value) {
		return _create(productAttributeId, name,type,defaultValue,value,false);
	}
	public CampType<?> _create(int paid,String name, Type type, String defaultValue,Object value,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[create]";
			msg = "====[ create and save attribute type '"+name+"'/'"+type+"' ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		
		CampType<?> ct = null;
		
		if(defaultValue != null) {
			ct = getType(name,type,defaultValue);
		}else {
			ct = getType(name,type);
		}
		int retVal = 0;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		try{
			
			conn = DBU.__conn(log);

			String SQL = "INSERT INTO "+table+"( "+ DBU._columns(tabledef, dbActionType.INSERT,log)+" )"
					+ " VALUES ( "+insertTypeValues(ct)+" )";
						  
			if(log && _DEBUG) {msg = "-- --[ SQL: "+SQL+"]-- --";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);
			
			if(log && _DEBUG) {msg = "-- --[ saved '"+retVal+"' entry]-- --";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.getGeneratedKeys();
			
			if (rs.next()) {
		    	
				ct.id(rs.getInt("id_"));

			} else {
		        // TODO: throw an exception from here
		    }
			
			String vSQL = "INSERT INTO "+valuetable+"( "+ DBU._columns(valuetabledef, dbActionType.INSERT,log)+" ) "
					+ "VALUES ( "+insertValueValues(paid,ct)+" )";
			
			retVal = dbs.executeUpdate(vSQL, Statement.RETURN_GENERATED_KEYS);
			
			rs.close();
			
			rs = dbs.getGeneratedKeys();
			
			if (rs.next()) {
		    	
				ct.valueId(rs.getInt("pav_id"));
				
			} else {
		        // TODO: throw an exception from here
			}
			
			String refSQL = "INSERT INTO "+pahttable+"( "+ DBU._columns(pahttabledef, dbActionType.INSERT,log)+" ) "
					+ "VALUES ( "+paid+", "+ct.valueId()+", "+ct.id()+" )";
			dbs.executeUpdate(refSQL);

			if(log && _DEBUG) {msg = "----[ created '"+retVal+"' entries]-- --";LOG.info(String.format(fmt,_f,msg));}

	
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Save failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
		String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ attribute type created.]====";LOG.info(String.format(fmt,_f,msg+time));
		}
		return ct;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampType<?> save(int productAttributeId, CampType<?> ct) {
		return _save(productAttributeId,ct,false);
	}
	public CampType<?> _save(int paid,CampType<?> ct,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[create]";
			msg = "====[ save attribute type  ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		if(ct == null) {
			if(log && _DEBUG){msg = "----[ERROR! ct is null]----";LOG.info(String.format(fmt, _f,msg));}
			return ct;
		}
		//'"+ct.name()+"'/'"+ct.type()+"'
		int retVal = 0;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		try{
			
			conn = DBU.__conn(log);

			String SQL = "INSERT INTO "+table+"( "+ DBU._columns(tabledef, dbActionType.INSERT,log)+" ) VALUES ( "+insertTypeValues(ct)+" )";
						  
			if(log && _DEBUG) {msg = "-- --[ SQL: "+SQL+"]-- --";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);
			
			if(log && _DEBUG) {msg = "-- --[ saved '"+retVal+"' entry]-- --";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.getGeneratedKeys();
			
			if (rs.next()) {
		    	
				ct.id(rs.getInt("id_"));

			} else {
		        // TODO: throw an exception from here
		    }
			

			String vSQL = "INSERT INTO "+valuetable+"( "+ DBU._columns(valuetabledef, dbActionType.INSERT,log)+" ) "
					+ "VALUES ( "+insertValueValues(paid,ct)+" )";
			
			retVal = dbs.executeUpdate(vSQL, Statement.RETURN_GENERATED_KEYS);
			rs.close();
			
			rs = dbs.getGeneratedKeys();

			if (rs.next()) {
		    	
				ct.valueId(rs.getInt("pav_id"));
				
			} else {
		        // TODO: throw an exception from here
			}
			
			
			String refSQL = "INSERT INTO "+pahttable+"( "+ DBU._columns(pahttabledef, dbActionType.INSERT,log)+" ) "
					+ "VALUES ( "+paid+", "+ct.valueId()+", "+ct.id()+" )";
			dbs.executeUpdate(refSQL);
			
			if(log && _DEBUG) {msg = "----[ created '"+retVal+"' entries]-- --";LOG.info(String.format(fmt,_f,msg));}

		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Save failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
		String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ attribute type created.]====";LOG.info(String.format(fmt,_f,msg+time));
		}
		return ct;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampList<CampType<?>> saveList(int productAttributeId, CampList<CampType<?>> attributeTypeList) {
		return _saveList(productAttributeId, attributeTypeList,false);
	}
	public CampList<CampType<?>> _saveList(int paid, CampList<CampType<?>> attributeTypeList,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[create]";
			msg = "====[ create and save list of attribute types ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		
		int retVal = 0;
		Connection conn = null;
		ResultSet rs = null;
		ResultSet vrs = null;
		Statement dbs = null;
		Statement rdbs = null;
		// TODO:we only save list entries which have no database representation (ie. entry id == 0) 
//		CampList<CampType<?>> saveList = new CampList<CampType<?>>();
//		for(CampType<?> ct:attributeTypeList.value()) {
//			if(ct.id()== 0) {
//				attributeTypeList.remove(ct); 
//				saveList.add(ct);
//			}
//		}
//		
		try{
			
			conn = DBU.__conn(log);

			String SQL = "INSERT INTO "+table+"( "+ DBU._columns(tabledef, dbActionType.INSERT,log)+" ) VALUES "+insertTypeValues(attributeTypeList);
						  
			if(log && _DEBUG) {msg = "-- --[ SQL: "+SQL+"]-- --";LOG.info(String.format(fmt,_f,msg));}
			
			dbs = conn.createStatement();
			rdbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL, Statement.RETURN_GENERATED_KEYS);
			
			if(log && _DEBUG) {msg = "-- --[ saved '"+retVal+"' entries]-- --";LOG.info(String.format(fmt,_f,msg));}
			
			rs = dbs.getGeneratedKeys();
			
			int counter = 0;
			
			while (rs.next()) {
		    	
//				CampType<?> ct = saveList.get(counter);//TODO: see savelist note: would destroy order
				CampType<?> ct = attributeTypeList.get(counter);
				ct.id(rs.getInt("id_"));//TODO: FIXME: is this safe (ie is saveList.get(counter) and rs.next() always in sync) 
				
				String vSQL = "INSERT INTO "+valuetable+"( "+ DBU._columns(valuetabledef, dbActionType.INSERT,log)+" ) "
						+ "VALUES ( "+insertValueValues(paid,ct)+"' )";
				
				retVal = dbs.executeUpdate(vSQL, Statement.RETURN_GENERATED_KEYS);
				
				vrs = dbs.getGeneratedKeys();

				if (vrs.next()) {
			    	
					ct.valueId(vrs.getInt("pav_id"));
					vrs.close();
				} else {
			        // TODO: throw an exception from here
				}
				
				
				String refSQL = "INSERT INTO "+pahttable+"( "+ DBU._columns(pahttabledef, dbActionType.INSERT,log)+" ) "
						+ "VALUES ( "+paid+", "+ct.valueId()+", "+ct.id()+" )";
				rdbs.addBatch(refSQL);
								
				counter++;
			} 
			retVal = U.addUp(rdbs.executeBatch());

			if(log && _DEBUG) {msg = "----[ created '"+retVal+"' entries]-- --";LOG.info(String.format(fmt,_f,msg));}

		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Save failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseStatement(rdbs, log);
			DBU._releaseRS(rs, log);
			DBU._releaseRS(vrs, log);
			
		}

//		attributeTypeList.addAll(saveList);
		
		if(log && _DEBUG) {
		String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ attribute type created.]====";LOG.info(String.format(fmt,_f,msg+time));
		}

		return attributeTypeList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int updateDefinition(CampType<?> attributeType) {
		return _updateDefinition(attributeType,false);
	}
	public int _updateDefinition(CampType<?> attributeType,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[create]";
			msg = "====[ update attribute type definition '"+attributeType.name()+"'/'"+attributeType.type()+"' ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		
		int retVal = 0;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		try{
			
			conn = DBU.__conn(log);

			String SQL = "UPDATE "+table+" SET "+ DBU._columns(tabledef, dbActionType.UPDATE,log)+" WHERE `"+tabledef[0][0]+"`=%s";
			  
			if(log && _DEBUG) {msg = "-- --[ FORMAT SQL ]]"+SQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}

			String eSQL = String.format(SQL, "'"+attributeType.name()+"'","'"+attributeType.attributeType().name()
				+"'","'"+attributeType.defaultValue()+"'",attributeType.typePosition(),"'"+attributeType.typeGroup()+"'",attributeType.id());
						  
			if(log && _DEBUG) {msg = "-- --[ SQL: "+eSQL+"]-- --";LOG.info(String.format(fmt,_f,msg));}
						
			dbs = conn.createStatement();
			retVal = dbs.executeUpdate(eSQL);
					
			if(log && _DEBUG) {msg = "-- --[ updated '"+retVal+"' entries]-- --";LOG.info(String.format(fmt,_f,msg));}
			
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
		String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ attribute type definition updated.]====";LOG.info(String.format(fmt,_f,msg+time));
		}
		return retVal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int updateDefinitionList(CampList<CampType<?>> attributeTypeList) {
		return _updateDefinitionList(attributeTypeList,false);
	}
	public int _updateDefinitionList(CampList<CampType<?>> atl,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[create]";
			msg = "====[ update attribute type definition list ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		
		int retVal = 0;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		try{
			
			conn = DBU.__conn(log);

			String SQL = "UPDATE "+table+" SET "+ DBU._columns(tabledef, dbActionType.UPDATE,log)+" WHERE `"+tabledef[0][0]+"`=%s";
			  
			if(log && _DEBUG) {msg = "-- --[ FORMAT SQL ]]"+SQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}

			dbs = conn.createStatement();

			for(CampType<?> at: atl.value()){

				String eSQL = String.format(SQL, "'"+at.name()+"'","'"+at.attributeType().name()+"'","'"+at.defaultValue()+"'",at.id());
				
				if(log && _DEBUG) {msg = "-- --[ SQL ]]"+eSQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}
				
				dbs.addBatch(eSQL);
				
			}
			retVal = U.addUp(dbs.executeBatch());
						  
			if(log && _DEBUG) {msg = "-- --[ updated '"+retVal+"' entries]-- --";LOG.info(String.format(fmt,_f,msg));}
			
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
		String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ attribute type definition list updated.]====";LOG.info(String.format(fmt,_f,msg+time));
		}
		return retVal;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public int update(int productAttributeId, CampType<?> attributeType) {
		return _update(productAttributeId, attributeType,false);
	}
	public int _update(int paid, CampType<?> attributeType,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[create]";
			msg = "====[ create and save attribute type '"+attributeType.name()+"'/'"+attributeType.type()+"' ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		
		int retVal = 0;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		try{
			
			conn = DBU.__conn(log);

			String SQL = "UPDATE "+table+" SET "+ DBU._columns(tabledef, dbActionType.UPDATE,log)+" WHERE `"+tabledef[0][0]+"`=%s";
			  
			if(log && _DEBUG) {msg = "-- --[ FORMAT SQL ]]"+SQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}

			String eSQL = String.format(SQL, "'"+attributeType.name()+"'","'"+attributeType.attributeType().name()
				+"'","'"+attributeType.defaultValue()+"'",attributeType.typePosition(),"'"+attributeType.typeGroup()+"'",attributeType.id());
						  
			if(log && _DEBUG) {msg = "-- --[ SQL: "+SQL+"]-- --";LOG.info(String.format(fmt,_f,msg));}
			
			String vSQL = "UPDATE"+valuetable+" SET "+ DBU._columns(valuetabledef, dbActionType.UPDATE, log)+" WHERE `"+valuetabledef[0][0]+"`=%s";
			
			if(log && _DEBUG) {msg = "-- --[ FORMAT VALUE SQL ]]"+vSQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}

			String evSQL = String.format(vSQL, paid,attributeType.attributePosition()
				,"'"+attributeType.attributeGroup()+"'","'"+updatevalue(paid,attributeType)+"'",attributeType.valueId());
			
			dbs = conn.createStatement();
			dbs.addBatch(eSQL);
			dbs.addBatch(evSQL);
					
			retVal = U.addUp(dbs.executeBatch());
			
			if(log && _DEBUG) {msg = "-- --[ updated '"+retVal+"' entries]-- --";LOG.info(String.format(fmt,_f,msg));}
			
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
		String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ attribute type created.]====";LOG.info(String.format(fmt,_f,msg+time));
		}
		return retVal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int updateList(int productAttributeId, CampList<CampType<?>> attributeTypeList) {
		return _updateList(productAttributeId, attributeTypeList,false);
	}
	public int _updateList(int paid, CampList<CampType<?>> atl,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[create]";
			msg = "====[ create and save list of attribute types ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		
		int retVal = 0;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		try{
			
			conn = DBU.__conn(log);

			String SQL = "UPDATE "+table+" SET "+ DBU._columns(tabledef, dbActionType.UPDATE,log)+" WHERE `"+tabledef[0][0]+"`=%s";
			  
			if(log && _DEBUG) {msg = "-- --[ FORMAT SQL ]]"+SQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}

			String vSQL = "UPDATE"+valuetable+" SET "+ DBU._columns(valuetabledef, dbActionType.UPDATE, log)+" WHERE `"+valuetabledef[0][0]+"`=%s";
			
			if(log && _DEBUG) {msg = "-- --[ FORMAT VALUE SQL ]]"+vSQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}

			dbs = conn.createStatement();

			for(CampType<?> at: atl.value()){

				String eSQL = String.format(SQL, "'"+at.name()+"'","'"+at.attributeType().name()+"'","'"+at.defaultValue()+"'",at.id());
				
				if(log && _DEBUG) {msg = "-- --[ SQL ]]"+eSQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}
				
				dbs.addBatch(eSQL);
				
				String evSQL = String.format(vSQL, paid,at.attributePosition()
						,"'"+at.attributeGroup()+"'","'"+updatevalue(paid,at)+"'",at.valueId());
				
				if(log && _DEBUG){ msg = "----[VALUE SQL : "+evSQL+"]----";LOG.info(String.format(fmt,_f,msg));}
				
				dbs.addBatch(evSQL);
					

			}
			retVal = U.addUp(dbs.executeBatch());
						  
			if(log && _DEBUG) {msg = "-- --[ updated '"+retVal+"' entries]-- --";LOG.info(String.format(fmt,_f,msg));}
			
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
		String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ attribute type list updated.]====";LOG.info(String.format(fmt,_f,msg+time));
		}
		return retVal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int delete(int id) {
		return _delete(id,false);
	}
	public int _delete(int id,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[create]";
			msg = "====[ deleting attribute type by id '"+id+"' ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		
		int retVal = 0;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		try{
			
			conn = DBU.__conn(log);

			String SQL = "DELETE FROM "+table+" WHERE `id_`="+id;
			  
			if(log && _DEBUG) {msg = "-- --[ SQL ]]"+SQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}

			dbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL);
			
			if(log && _DEBUG) {msg = "-- --[ deleted '"+retVal+"' entry]-- --";LOG.info(String.format(fmt,_f,msg));}
			
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
		String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ attribute type created.]====";LOG.info(String.format(fmt,_f,msg+time));
		}
		return retVal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int delete(String name) {
		return _delete(name,false);
	}
	public int _delete(String name,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[create]";
			msg = "====[ deleting attribute type by name '"+name+"' ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		
		int retVal = 0;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		try{
			
			conn = DBU.__conn(log);

			String SQL = "DELETE FROM "+table+" WHERE `name`="+name;
			  
			if(log && _DEBUG) {msg = "-- --[ SQL ]]"+SQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}

			dbs = conn.createStatement();
			
			retVal = dbs.executeUpdate(SQL);
			
			if(log && _DEBUG) {msg = "-- --[ deleted '"+retVal+"' entry]-- --";LOG.info(String.format(fmt,_f,msg));}
			
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
		String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ attribute type created.]====";LOG.info(String.format(fmt,_f,msg+time));
		}
		return retVal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteList(CampList<CampType<?>> attributeTypeList) {
		return _deleteList(attributeTypeList,false);
	}
	public int _deleteList(CampList<CampType<?>> atl,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[create]";
			msg = "====[ deleting list attribute types ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		
		int retVal = 0;
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		try{
			
			conn = DBU.__conn(log);
			
			dbs = conn.createStatement();

			for(CampType<?> at: atl.value()) {
				String SQL = "DELETE FROM "+table+" WHERE `id_`="+at.id();
			
				if(log && _DEBUG) {msg = "-- --[ SQL ]]"+SQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}

				dbs.addBatch(SQL);
			}
			
			retVal = U.addUp(dbs.executeBatch());
			
			if(log && _DEBUG) {msg = "-- --[ deleted '"+retVal+"' entries]-- --";LOG.info(String.format(fmt,_f,msg));}
			
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
		String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ attribute type created.]====";LOG.info(String.format(fmt,_f,msg+time));
		}
		return retVal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampType<?> loadDefinition(int id) {
		return _loadDefinition(id,false);
	}
	public CampType<?> _loadDefinition(int id,boolean log) {
		CampType<?> at = _loadType(String.format(loadByIdSQL, id),log);
		return at;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampType<?> loadDefinition(String name) {
		return _loadDefinition(name,false);
	}
	public CampType<?> _loadDefinition(String name,boolean log) {
		CampType<?> at = _loadType(String.format(loadByNameSQL, name),log);
		return at;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampList<CampType<?>> loadDefinitionByType(Type type) {
		return _loadDefinition(type,false);
	}
	public CampList<CampType<?>> _loadDefinition(Type type,boolean log) {
		CampList<CampType<?>>  atl = _loadTypeList(String.format(loadByTypeSQL, type),log);
		return atl;
	}

	@Override
	public CampList<CampType<?>> loadListByProductAttributeId(int[] ids){
		return _loadList(ids,false);
	}
	public CampList<CampType<?>> _loadList(int[] ids, boolean log){
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_loadList]";
			msg = "====[ loading list of '"+ids.length+"' attribute types]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement dbp = null;
		
		int retVal = 0;
		CampList<CampType<?>> atl = new CampList<CampType<?>>();
		
		try{
			
			conn = DBU.__conn(log);
			
			dbp = conn.prepareStatement(loadValueByIdPSQL);
			
			for(int id: ids) {
				dbp.setInt(1,id);
				dbp.addBatch();
			}
			rs = dbp.executeQuery();
			
			while(rs.next()) {
				atl.add(_rsToType(rs, log));
			}
			
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbp, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ attribute type list loaded.]====";LOG.traceExit(String.format(fmt,_f,msg+time));
		}
		
		return atl;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampList<CampType<?>> loadList() {
		return _loadList(false);
	}
	public CampList<CampType<?>> _loadList(boolean log) {
		CampList<CampType<?>>  atl = _loadTypeList(loadAllSQL,log);
		return atl;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampType<?> loadByProductAttributeId(int id) {
		return _loadByPAId(id,false);
	}
	public CampType<?> _loadByPAId(int id,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_loadValue]";
			msg = "====[ load attribute type by id ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		CampType<?> ct = null;
		
		try{
			
			conn = DBU.__conn(log);
			
			dbs = conn.createStatement();
			String SQL = "SELECT `id_`,`pav_id`,`name`,`type`,`default_value`,`type_position`,`attribute_position`,`value` "
					+ " FROM "+table+" AS at, "+valuetable+" AS av, "+pahttable+" as thv WHERE "
					+ " thv.product_attribute_type_id=at.id "
					+ " AND thv.product_attribute_value_id=av.pav_id "
					+ " AND thv.product_attribute_id=av.product_attribute_id"
					+ " AND av.product_attribute_id="+id;

			rs = dbs.executeQuery(SQL);
			
			if(rs.next()) {
				ct = _rsToType(rs, log);

				int valueId = 0; 
				if(rs.getInt("pav_id") != 0) {
					valueId = rs.getInt("pav_id");
				}
				ct.valueId(valueId);
				
				String attributeGroup = null;
				if(rs.getString("attribute_group") != null) {
					attributeGroup = rs.getString("attribute_group");
				}
				ct.attributeGroup(attributeGroup);
			
				int attributePosition = 0; //in a CampTable this represents col
				if(rs.getInt("attribute_position") != 0) {
					attributePosition = rs.getInt("attribute_position");
				}
				ct.attributePosition(attributePosition);
								
				String value = rs.getString("value");
				ct = getValue(value,ct, log);
//				if(ct.attributeType().equals(ProductAttributeType._complex)) {
//					CampList<CampType<?>> ctl = _loadValueList(rs.getString("value").split(","),log);					
//				}
			}

			
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ value loaded attribute type.]====";LOG.traceExit(String.format(fmt,_f,msg+time));
		}
		
		return ct;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampType<?> load(int id) {
		return _load(id,false);
	}
	public CampType<?> _load(int id,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_loadValue]";
			msg = "====[ load attribute type by id ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		CampType<?> ct = null;
		
		try{
			
			conn = DBU.__conn(log);
			
			dbs = conn.createStatement();
			String SQL = "SELECT `id_`,`pav_id`,`name`,`type`,`default_value`,`type_position`,`attribute_position`,`value` "
					+ " FROM "+table+" AS at, "+valuetable+" AS av, "+pahttable+" as thv WHERE "
					+ " at.id="+id
					+ " AND thv.product_attribute_type_id=at.id "
					+ " AND thv.product_attribute_value_id=av.pav_id "
					+ " AND thv.product_attribute_id=av.product_attribute_id";

			rs = dbs.executeQuery(SQL);
			
			if(rs.next()) {
				ct = _rsToType(rs, log);

				int valueId = 0; 
				if(rs.getInt("pav_id") != 0) {
					valueId = rs.getInt("pav_id");
				}
				ct.valueId(valueId);
				
				String attributeGroup = null;
				if(rs.getString("attribute_group") != null) {
					attributeGroup = rs.getString("attribute_group");
				}
				ct.attributeGroup(attributeGroup);
			
				int attributePosition = 0; //in a CampTable this represents col
				if(rs.getInt("attribute_position") != 0) {
					attributePosition = rs.getInt("attribute_position");
				}
				ct.attributePosition(attributePosition);
								
				String value = rs.getString("value");
				ct = getValue(value,ct, log);
//				if(ct.attributeType().equals(ProductAttributeType._complex)) {
//					CampList<CampType<?>> ctl = _loadValueList(rs.getString("value").split(","),log);					
//				}
			}

			
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ value loaded attribute type.]====";LOG.traceExit(String.format(fmt,_f,msg+time));
		}
		
		return ct;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampType<?> loadByValueId(int id) {
		return _loadByValueId(id,false);
	}
	public CampType<?> _loadByValueId(int id,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_loadValue]";
			msg = "====[ load attribute type by value id ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		CampType<?> ct = null;
		
		try{
			
			conn = DBU.__conn(log);
			
			dbs = conn.createStatement();
			String SQL = "SELECT `id_`,`pav_id`,`name`,`type`,`default_value`,`type_position`,`attribute_position`,`value` "
					+ " FROM "+table+" AS at, "+valuetable+" AS av, "+pahttable+" as thv WHERE "
					+ " thv.product_attribute_type_id=at.id "
					+ " AND thv.product_attribute_value_id=av.pav_id "
					+ " AND thv.product_attribute_id=av.product_attribute_id"
					+ " AND thv.product_attribute_value_id="+id;

			rs = dbs.executeQuery(SQL);
			
			if(rs.next()) {
				ct = _rsToType(rs, log);

				int valueId = 0; 
				if(rs.getInt("pav_id") != 0) {
					valueId = rs.getInt("pav_id");
				}
				ct.valueId(valueId);
				
				String attributeGroup = null;
				if(rs.getString("attribute_group") != null) {
					attributeGroup = rs.getString("attribute_group");
				}
				ct.attributeGroup(attributeGroup);
			
				int attributePosition = 0; //in a CampTable this represents col
				if(rs.getInt("attribute_position") != 0) {
					attributePosition = rs.getInt("attribute_position");
				}
				ct.attributePosition(attributePosition);
				
				String value = rs.getString("value");
				ct = getValue(value,ct, log);
//				if(ct.attributeType().equals(ProductAttributeType._complex)) {
//					CampList<CampType<?>> ctl = _loadValueList(rs.getString("value").split(","),log);					
//				}
			}

			
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ value loaded attribute type.]====";LOG.traceExit(String.format(fmt,_f,msg+time));
		}
		
		return ct;
	}


	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampList<CampType<?>> loadValueList(String[] idList) {
		return _loadValueList(idList,false);
	}
	public CampList<CampType<?>> _loadValueList(String[] idList,boolean log) {
		// TODO Auto-generated method stub
		return null;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProductAttributeDefinition<?,?> add(ProductAttributeDefinition<?,?> productAttribute) {
		return _add(productAttribute,false);
	}
	public ProductAttributeDefinition<?,?> _add(ProductAttributeDefinition<?,?> pad,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_addOrder]";
			msg = "====[ adding attribute type and values to product attribute definition ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		int retVal = 0;
		
		try{
			
			conn = DBU.__conn(log);
			
			dbs = conn.createStatement();
			String SQL = "SELECT `id_`,`name`,`type`,`default_value`,`type_position`,`attribute_position`,`value` "
					+ " FROM "+table+" AS at, "+valuetable+" AS av WHERE "
					+ " av.product_attribute_id="+pad.id()
					+ " AND EXISTS "
					+ "( SELECT `product_attribute_id` FROM "+pahttable
					+ " WHERE `product_attribute_type_id`=at.id "
					+ " AND `product_attribute_id`=av.product_attribute_id "
					+ " AND `product_attribute_value_id`=av.pav_id"
					+ " AND `product_attribute_id`="+pad.id()
					+ " )";

			
			rs = dbs.executeQuery(SQL);
			
			boolean first = true;
			
			while(rs.next()) {
				CampType<?> ct = _rsToType(rs, log);
				int row = 0;
				if(rs.getString("row") != null) {
					row = Integer.valueOf(rs.getString("row"));
				}
				String value = rs.getString("value");
				
			}

			
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ types and values saved.]====";LOG.traceExit(String.format(fmt,_f,msg+time));
		}
		
		return pad;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrderProductAttribute<?,?> addOrder(OrderProductAttribute<?,?> productAttribute) {
		return _addOrder(productAttribute,false);
	}
	public OrderProductAttribute<?,?> _addOrder(OrderProductAttribute<?,?> opa,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_addOrder]";
			msg = "====[ adding attribute type and values to order product attribute ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		int retVal = 0;
		
		try{
			
			conn = DBU.__conn(log);
			
			dbs = conn.createStatement();
			String SQL = "SELECT `id_`,`name`,`type`,`default_value`,`row`,`value` "
					+ " FROM "+table+" AS at, "+valuetable+" AS av WHERE "
					+ " av.product_attribute_id="+opa.id()
					+ " AND EXISTS "
					+ "( SELECT `product_attribute_id` FROM "+pahttable
					+ " WHERE `product_attribute_type_id`=at.id "
					+ " AND `product_attribute_id`=av.product_attribute_id "
					+ " AND `product_attribute_id`="+opa.id()
					+ " )";

			
			rs = dbs.executeQuery(SQL);
			
			boolean first = true;
			
			while(rs.next()) {
				CampType<?> ct = _rsToType(rs, log);
				int row = 0;
				if(rs.getString("row") != null) {
					row = Integer.valueOf(rs.getString("row"));
				}
				String value = rs.getString("value");
				
			}

			
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ types and values saved.]====";LOG.traceExit(String.format(fmt,_f,msg+time));
		}
		
		return opa;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampOldList<ProductAttributeDefinition<?,?>> addList(
			CampOldList<ProductAttributeDefinition<?,?>> productAttributeList) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampOldList<OrderProductAttribute<?,?>> addOrderList(CampOldList<OrderProductAttribute<?,?>> productAttributeList) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CampType<?> saveReference(int productAttributeId, CampType<?> attributeType) {
		return _saveReference(productAttributeId,attributeType,false);
	}
	public CampType<?> _saveReference(int paid,CampType<?> ct,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_save]";
			msg = "====[ saving product attribute reference]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		int retVal = 0;
		
		try{
			
			conn = DBU.__conn(log);
			
			dbs = conn.createStatement();
			
			// create attribute type database entry if it is new (id == 0)
			
			if(ct.id() == 0) {
				ct.id(_save(paid,ct, log).id());
			} else {
				
				String SQL = "INSERT INTO "+pahttable+"( "+ DBU._columns(pahttabledef, dbActionType.INSERT,log)+" ) "
						+ "VALUES ( "+paid+", "+ct.valueId()+", "+ct.id()+" )";
	
				retVal = dbs.executeUpdate(SQL);
				
				if(log && _DEBUG) {msg = "----[ created '"+retVal+"' entries]-- --";LOG.info(String.format(fmt,_f,msg));}
			}
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ reference saved.]====";LOG.traceExit(String.format(fmt,_f,msg+time));
		}
		
		return ct;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HashMap<Integer,CampType<?>> saveReferenceList(HashMap<Integer,CampType<?>> productAttributeReferenceList) {
		return _saveReferenceList(productAttributeReferenceList,false);
	}
	public HashMap<Integer,CampType<?>> _saveReferenceList(HashMap<Integer,CampType<?>> parl,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_save]";
			msg = "====[ saving references to product attribute ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		int retVal = 0;
		
		try{
			
			conn = DBU.__conn(log);
			
			dbs = conn.createStatement();
			boolean batch = false;
			for(int id:parl.keySet()) {
				CampType<?> ct = parl.get(id);
				if(ct.id() == 0) {
					ct.id(_save(id,ct, log).id());
				} else {
					batch = true;
					
					String SQL = "INSERT INTO "+pahttable+"( "+ DBU._columns(pahttabledef, dbActionType.INSERT,log)+" ) "
							+ "VALUES ( "+id+", "+", "+ct.valueId()+ct.id()+" )";
		
					dbs.addBatch(SQL);
				}
			}
			if(batch) {
				retVal = U.addUp(dbs.executeBatch());
			}

			if(log && _DEBUG) {msg = "-- --[ saved '"+retVal+"' references]-- --";LOG.info(String.format(fmt,_f,msg));}
			
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! save failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ references added.]====";LOG.traceExit(String.format(fmt,_f,msg+time));
		}
		
		return parl;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int clear(OrderProductAttribute<?,?> productAttribute) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int clearList(OrderProductAttribute<?,?> productAttributeList) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	// support functions
	public CampType<?> _loadType(String SQL, boolean log){
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_load]";
			msg = "====[ executing db query: '"+SQL+"' ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		int retVal = 0;
		CampType<?> at = null;
		
		try{
			
			conn = DBU.__conn(log);
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			
			if(rs.next()) {
				at = _rsToType(rs, log);
			} else {
				if(log && _DEBUG){msg = "----[ERROR! empty db result set.]----";LOG.info(String.format(fmt, _f,msg));}
			}
			
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ executed db query.]====";LOG.traceExit(String.format(fmt,_f,msg+time));
		}
		
		return at;
	}
	
	public CampList<CampType<?>> _loadTypeList(String SQL, boolean log){
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_loadList]";
			msg = "====[ executing db query: '"+SQL+"' ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;
		
		int retVal = 0;
		CampList<CampType<?>> atl = new CampList<CampType<?>>();
		
		try{
			
			conn = DBU.__conn(log);
			
			dbs = conn.createStatement();
			
			rs = dbs.executeQuery(SQL);
			
			while(rs.next()) {
				atl.add(_rsToType(rs, log));
			}
		} catch (Exception e) {
			if(log && _DEBUG) {msg = "-- --[ EXCEPTION! Update failed.]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);
			
		}
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ db query executed.]====";LOG.traceExit(String.format(fmt,_f,msg+time));
		}
		
		return atl;
	}

	

	public CampType<?> _rsToType(ResultSet rs,boolean log) throws SQLException{
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_rsToType]";
			msg = "====[ generating new camp type from db query result set. ]====";LOG.info(String.format(fmt,_f,msg));
		}

		CampType<?> ct = null;
		
    	int id = rs.getInt("id_");   
    	msg = "---- ---- id is '"+id+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
    	
    	String name = rs.getString("name");   
    	msg = "---- ---- name is '"+name+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
    	
    	Type type = ProductAttribute.toType(rs.getString("type"));   
    	msg = "---- ---- type is '"+type.name()+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
    	
    	String defaultValue = rs.getString("default_value");   
    	msg = "---- ---- default value is '"+defaultValue+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
    	
    	int typePosition = rs.getInt("type_position");    //in a CampTable this represents row
    	msg = "---- ---- type position is '"+typePosition+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
    	
    	String typeGroup = rs.getString("type_group");   
    	msg = "---- ---- type group is '"+typeGroup+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
    	
    	ct = getType(name, type);
    	ct.id(id);
    	ct.typePosition(typePosition);
    	ct.typeGroup(typeGroup);
    	ct.defaultValue(defaultValue);
    	
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ generated CampType<'"+ProductAttribute.typeToL(type)+"'>.]====";LOG.traceExit(String.format(fmt,_f,msg+time));
		}
		
	    return ct;

	}
	public static CampType<?> getType(String name, Type type){
		return getType(name,type,null);
	}
	public static CampType<?> getType(String name, Type type,String defaultValue){
		CampType<?> ct = null;
		boolean def = (defaultValue != null);
    	switch(type) {
    	case _integer:
    		ct = (def)?new CampInteger(name, Integer.valueOf(defaultValue)):new CampInteger(name);
    		ct.defaultValue(defaultValue);
    		break;
    	case _string:
    		ct = (def)?new CampString(name,defaultValue):new CampString(name);
    		ct.defaultValue(defaultValue);
    		break;
    	case _boolean:
    		ct = (def)?new CampBoolean(name,Boolean.valueOf(defaultValue)):new CampBoolean(name);
    		ct.defaultValue(defaultValue);
    		break;
    	case _datetime:
    		ct = (def)?new CampDateTime(name,defaultValue):new CampDateTime(name);
    		ct.defaultValue(defaultValue);
    		break;
    	case _date:
    		ct = (def)?new CampDate(name,defaultValue):new CampDate(name);
    		ct.defaultValue(defaultValue);
    		break;
    	case _time:
    		ct = (def)?new CampTime(name,defaultValue):new CampTime(name);
    		ct.defaultValue(defaultValue);
    		break;
    	case _timestamp:
    		ct = (def)?new CampTimestamp(name,defaultValue):new CampTimestamp(name);
    		ct.defaultValue(defaultValue);
    		break;
    	case _enum:
    		String dv = null;
    		ct = (def)?new CampEnum(name,defaultValue):new CampEnum(name,dv);
    		break;
    	case _set:
    		dv = null;
    		ct = (def)?new CampSet(name,defaultValue):new CampSet(name,dv);
    		ct.defaultValue(defaultValue);
    		break;
    	case _text:
    		ct = (def)?new CampText(name,defaultValue):new CampText(name);
    		ct.defaultValue(defaultValue);
    		break;
    	case _table:
    		ct = new CampTable<CampType<?>>(name);
    		break;
    	case _complex:
    		ct = new CampComplex<CampType<?>>(name);
    		ct.defaultValue(defaultValue);
    		break;
    	case _list:
    		ct = new CampList<CampType<?>>(name);
    		ct.defaultValue(defaultValue);
    		break;
    	case _map:
    		ct = new CampMap<CampType<?>>(name);
    		ct.defaultValue(defaultValue);
    		break;
    	case _palist:
    		ct = new CampList<CampType<?>>(name);
    		ct.defaultValue(defaultValue);
    		break;
    	case _token:
    		ct = CampToken.usernameToken(name);
    		ct.defaultValue(defaultValue);
    		break;
    	default:
    		break;
    	}

    	return ct;
	}

	public CampType<?> getValue(String sValue, CampType<?> ct,boolean log) throws CampTableDoubleCellWriteException{
    	switch(ct.attributeType()) {
    	case _integer:
    		((CampInteger)ct).value(Integer.valueOf(sValue));
    		break;
    	case _string:
    		((CampString)ct).value(sValue);
    		break;
    	case _boolean:
    		((CampBoolean)ct).value(Boolean.valueOf(sValue));
    		break;
    	case _datetime:
    		((CampDateTime)ct).value(TU.dateTimeFromString(sValue));
    		break;
    	case _date:
    		((CampDate)ct).value(TU.dateTimeFromString(sValue));
    		break;
    	case _time:
    		((CampTime)ct).value(TU.dateTimeFromString(sValue));
    		break;
    	case _timestamp:
    		((CampTimestamp)ct).value(TU.timestamp(sValue));
    		break;
    	case _enum:
    		((CampEnum)ct).value(sValue);
    		break;
    	case _set:
    		((CampSet)ct).value(sValue);
    		break;
    	case _text:
    		((CampText)ct).value(sValue);
    		break;
    	case _table:
    		((CampTable<CampType<?>>)ct).value(loadTable(sValue,(CampTable<CampType<?>>)ct,log));
    		break;
    	case _complex:
    		((CampComplex<CampType<?>>)ct).value(loadComplex(sValue,(CampComplex<CampType<?>>)ct,log));
    		break;
    	case _list:
    		((CampList<CampType<?>>)ct).value(loadList(sValue,(CampList<CampType<?>>)ct,log));
    		break;
    	case _map:
    		((CampMap<CampType<?>>)ct).value(loadMap(sValue,(CampMap<CampType<?>>)ct,log));
    		break;
    	case _token:
    		((CampToken)ct).value(sValue);
    		break;
    	default:
    		break;
    	}

    	return ct;
	}

	
	public ArrayList<CampList<CampType<?>>> loadTable(String sValue, CampTable<CampType<?>> ct, boolean log) throws CampTableDoubleCellWriteException {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[loadTable]";
			msg = "====[ load camp table from CampType string value '"+sValue+"' ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ArrayList<CampList<CampType<?>>> list = new ArrayList<CampList<CampType<?>>>();
		String[] sids = sValue.split(",");
		int[] ids = new int[sids.length];
		int count = 0;
		for(String sid:sids) {
			ids[count] = Integer.valueOf(sid);
			count++;
		}
		
		CampList<CampType<?>> ctl = _loadList(ids, log);
		
		// To ensure correct table structure, create a "table" HashMap to intially store CampTable row/column CampType<?> entries. 
		
		HashMap<Integer,HashMap<Integer,CampType<?>>> tableMap = new HashMap<Integer,HashMap<Integer,CampType<?>>>();
		
		// Add CampType entries to correct tableMap matrix location.
		
		for(CampType<?> ctre: ctl.value()) {
			if(!tableMap.containsKey(ctre.typePosition()-1)) {
				tableMap.put(ctre.typePosition()-1, new HashMap<Integer,CampType<?>>());
			}
			if(tableMap.get(ctre.typePosition()-1).containsKey(ctre.attributePosition()-1)) {
				throw new CampTableDoubleCellWriteException("CAMPTABLE EXCEPTION! DOUBLE-WRITE to cell {'"+ctre.typePosition()+"','"+ctre.attributePosition()+"'} ocurred.");
			}
			tableMap.get(ctre.typePosition()-1).put(ctre.attributePosition()-1, ctre);
		}
		
		// get number of rows and columns from the table hashmap matrix
		int numRows = tableMap.size();
		
		int numColumns = tableMap.get(0).size();
		
		// loop over rows and columns to add table hash map matrix entries to result table list
		for(int r=0;r<numRows;r++) {
			list.add(new CampList<CampType<?>>());
			for(int c = 0;c<numColumns;c++) {
				list.get(r).add(tableMap.get(r).get(c));
			}
		}
		
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ camp table loaded.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return list;
	}
	
	public HashMap<String,CampList<CampType<?>>> loadComplex(String sValue, CampComplex<CampType<?>> ct, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[loadComplex]";
			msg = "====[ load camp complex from CampType string value '"+sValue+"' ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		HashMap<String,CampList<CampType<?>>> map = new HashMap<String,CampList<CampType<?>>>();
		
		String[] sids = sValue.split(",");
		
		int[] ids = new int[sids.length];
		
		int count = 0;
		
		for(String sid:sids) {
			ids[count] = Integer.valueOf(sid);
			count++;
		}
		
		CampList<CampType<?>> ctl = _loadList(ids, log);
				
		for(CampType<?> ctre: ctl.value()) {
			if(!map.containsKey(ctre.typeGroup())) {
				map.put(ctre.typeGroup(), new CampList<CampType<?>>());
			}
			map.get(ctre.typeGroup()).add(ctre);
		}
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ complex type loaded.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
				
		return map;
	}
	
	public ArrayList<CampType<?>> loadList(String sValue, CampList<CampType<?>> ct, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[loadList]";
			msg = "====[ load camp list from CampType string value '"+sValue+"'  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String[] sids = sValue.split(",");
		
		int[] ids = new int[sids.length];
		
		int count = 0;
		
		for(String sid:sids) {
			ids[count] = Integer.valueOf(sid);
			count++;
		}
		
		ArrayList<CampType<?>> list = _loadList(ids, log).value();
		
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ list type loaded.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return list;
	}
	
	public HashMap<String,CampList<CampType<?>>> loadMap(String sValue, CampMap<CampType<?>> ct, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[loadMap]";
			msg = "====[ load camp map from CampType string value '"+sValue+"'  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		HashMap<String,CampList<CampType<?>>> map = new HashMap<String,CampList<CampType<?>>>();

		String[] sids = sValue.split(",");
		
		int[] ids = new int[sids.length];
		
		int count = 0;
		
		for(String sid:sids) {
			ids[count] = Integer.valueOf(sid);
			count++;
		}

		CampList<CampType<?>> ctl = _loadList(ids, log);
		
		for(CampType<?> ctre: ctl.value()) {
			if(!map.containsKey(ctre.typeGroup())) {
				map.put(ctre.typeGroup(), new CampList<CampType<?>>());
			}
			map.get(ctre.typeGroup()).add(ctre);
		}
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ map type loaded.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return map;
	}
	
	public String value(int productAttributeId, CampType<?> ct) {
		return _value(productAttributeId, ct,false);
	}
	@SuppressWarnings("unchecked")
	public String _value(int paid, CampType<?> ct,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_value]";
			msg = "====[ save complex/table attribute types and return a comma separated list of product attribute id's ]====";LOG.info(String.format(fmt,_f,msg));
		}
		
		String value = "";
		Type type = ct.attributeType();

		/**
		 * Set complex/table value to a comma separated list of product attribute id's of the
		 * table product attributes after saving them.
		 * In case of set/enum set value to return value of the <code>toString()</code> method.
		 */
		if(type.equals(Type._complex)){
			
			CampComplex<CampType<?>> cc = (CampComplex<CampType<?>>) ct.value();
			
			CampList<CampType<?>> crl = cc.toList();
			
			if(crl.size() == 0){
				if(log && _DEBUG) {msg = "----[ Error! attempt to get value of empty complex product attribute type occured.]----";LOG.info(String.format(fmt,_f,msg));}
				return null;
			}
			
			crl = _saveList(paid,crl, log);
			
			boolean first = true;
			
			for(CampType<?> cce: crl.value()){
				value += ((first)?"":",")+cce.id();
				first = false;
			}
			
		} 
		else if(type.equals(Type._table)){
			
			CampList<CampType<?>> crl = new CampList<CampType<?>>();
			
			CampTable<CampType<?>> t = (CampTable<CampType<?>>) ct.value();
			
			try {
				// check that we have a first row.
				if(t._getRow(1, log).size() == 0){
					if(log && _DEBUG) {msg = "-- --[ Error! attempted to save empty table product attribute.]-- --";LOG.info(String.format(fmt,_f,msg));}
					return null;
				}
			} catch (CampTableRowOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(CampList<CampType<?>> rl: t.value()) {
				crl.addAll(rl);
			}
			
			crl = _saveList(paid,crl, log);
						
			boolean first = true;
			
			for(CampType<?> crle: crl.value() ){
				value += ((first)?"":",")+crle.id(); //TODO: FIXME: this design approach has limitations due to database value entry size chosen. this makes sense for the current scope but a different use of tableType can be envisaged which would make a redesign (database tables) sensible in the future  
				first = false;
			}
			
		} else if(type.equals(Type._enum)){
			
			value = (String) ct.value();// ((CampEnum)ct.value()).toString(); 
			
		} else if(type.equals(Type._set)){
			
			value = ((CampSet)ct.value()).toString();
							
		}  else {
			
			value = ""+ct.value();
		
		}
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ value generated.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return value;
	}

	public String updatevalue(int productAttributeId, CampType<?> ct) {
		return _updatevalue(productAttributeId, ct,false);
	}
	@SuppressWarnings("unchecked")
	public String _updatevalue(int paid, CampType<?> ct,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_value]";
			msg = "====[ update complex/table attribute types and return a comma separated list of product attribute id's ]====";LOG.info(String.format(fmt,_f,msg));
		}
		
		String value = "";
		Type type = ct.attributeType();

		/**
		 * Set complex/table value to a comma separated list of product attribute id's of the
		 * table product attributes after saving them.
		 * In case of set/enum set value to return value of the <code>toString()</code> method.
		 */
		if(type.equals(Type._complex)){
			
			CampComplex<CampType<?>> cc = (CampComplex<CampType<?>>) ct.value();
			
			CampList<CampType<?>> crl = cc.toList();
			
			if(crl.size() == 0){
				if(log && _DEBUG) {msg = "----[ Error! attempt to get value of empty complex product attribute type occured.]----";LOG.info(String.format(fmt,_f,msg));}
				return null;
			}
			
			int retVal = _updateList(paid,crl, log);
			
			boolean first = true;
			
			for(CampType<?> cce: crl.value()){
				value += ((first)?"":",")+cce.id();
				first = false;
			}
			
		} 
		else if(type.equals(Type._table)){
			
			CampList<CampType<?>> crl = new CampList<CampType<?>>();
			
			CampTable<CampType<?>> t = (CampTable<CampType<?>>) ct.value();
			
			try {
				// check that we have a first row.
				if(t._getRow(1, log).size() == 0){
					if(log && _DEBUG) {msg = "-- --[ Error! attempted to get value (save) empty table product attribute.]-- --";LOG.info(String.format(fmt,_f,msg));}
					return null;
				}
			} catch (CampTableRowOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(CampList<CampType<?>> rl: t.value()) {
				crl.addAll(rl);
			}
			
			int retVal = _updateList(paid,crl, log);
						
			boolean first = true;
			
			for(CampType<?> crle: crl.value() ){
				value += ((first)?"":",")+crle.id(); //TODO: FIXME: this design approach has limitations due to database value entry size chosen. this makes sense for the current scope but a different use of tableType can be envisaged which would make a redesign (database tables) sensible in the future  
				first = false;
			}
			
		} else if(type.equals(Type._enum)){
			
			value = ((CampEnum)ct.value()).toString(); 
			
		} else if(type.equals(Type._set)){
			
			value = ((CampSet)ct.value()).toString();
							
		}  else {
			
			value = ""+ct.value();
		
		}
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[ update value generated.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return value;
	}

	public int clearTable(boolean log){
		return _clearTable(false);
	}
	public int _clearTable(boolean log){
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_createTable]";
			msg = "====[ creating '"+table+"' table ... ]====";LOG.info(String.format(fmt,_f,msg));
		}
		int retVal = 0;
	
		String SQL = "DELETE FROM "+table;
		
		if(log && _DEBUG){ msg = "-- --[ SQL ]]"+SQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}
		
		String pahtSQL = "DELETE FROM "+pahttable;
		
		if(log && _DEBUG){ msg = "-- --[ SQL ]]"+pahtSQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}
		
		String valueSQL = "DELETE FROM "+valuetable;
		
		if(log && _DEBUG){ msg = "-- --[ SQL ]]"+valueSQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}
		
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;

		try {
			
			conn = DBU.__conn(log);
			
			dbs = conn.createStatement();
			
			dbs.addBatch(SQL);
			dbs.addBatch(pahtSQL);
			dbs.addBatch(valueSQL);
			
			retVal = U.addUp(dbs.executeBatch());
			
			if(log && _DEBUG){msg = "----[created '"+retVal+"' tables]----";LOG.info(String.format(fmt, _f,msg));}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);			
		}
		return retVal;
	}

	public int createTable(boolean checkDBExists){
		return _createTable(checkDBExists,true);
	}
	public int _createTable(boolean cde, boolean log){
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_createTable]";
			msg = "====[ creating '"+table+"' table ... ]====";LOG.info(String.format(fmt,_f,msg));
		}
		int retVal = 0;
		if(cde && !DBU._dbExists(dbName,log)){
			DBU._createDatabases(log);
		}
//		super._createTable(cde, log);
		DBU.dbActionType action = DBU.dbActionType.CREATE;
		if(log && _DEBUG){ msg = "-- --[ assembling '"+action.name().toLowerCase()+"' columns]-- --";LOG.info(String.format(fmt,_f,msg));}
		
		String columndef = DBU._columns(tabledef, action,log);
		String SQL = "CREATE TABLE IF NOT EXISTS "+table+" "
			    + " ( "+  columndef + ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		
		if(log && _DEBUG){ msg = "-- --[ SQL ]]"+SQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}
		
		String pahtcolumndef = DBU._columns(pahttabledef, action,log);
		String pahtSQL = "CREATE TABLE IF NOT EXISTS "+pahttable+" "
			    + " ( "+  pahtcolumndef + ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		
		if(log && _DEBUG){ msg = "-- --[ SQL ]]"+pahtSQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}
		
		String valuecolumndef = DBU._columns(valuetabledef, action,log);
		String valueSQL = "CREATE TABLE IF NOT EXISTS "+valuetable+" "
			    + " ( "+  valuecolumndef + ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ";
		
		if(log && _DEBUG){ msg = "-- --[ SQL ]]"+valueSQL+"[[]-- --";LOG.info(String.format(fmt,_f,msg));}
		
		Connection conn = null;
		ResultSet rs = null;
		Statement dbs = null;

		try {
			
			conn = DBU.__conn(log);
			
			dbs = conn.createStatement();
			
			dbs.addBatch(SQL);
			dbs.addBatch(pahtSQL);
			dbs.addBatch(valueSQL);
			
			retVal = U.addUp(dbs.executeBatch());
			
			if(log && _DEBUG){msg = "----[created '"+retVal+"' tables]----";LOG.info(String.format(fmt, _f,msg));}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(log && _DEBUG){msg = " -- -- [ Releasing Connection ]";LOG.info(String.format(fmt, _f,msg));}
			DBU.__release(conn,log);
			DBU._releaseStatement(dbs, log);
			DBU._releaseRS(rs, log);			
		}
		return retVal;
	}

	public String insertTypeValues(CampList<CampType<?>> ctl){
		String retVal = "";
		boolean first = true;
		for(CampType<?> ct: ctl.value()) {
			if(ct.id()==0) {
				retVal += ((first)?"":" ,") +"( "+ insertTypeValues(ct)+" )";
				first = false;
			}
		}
		return 	retVal;
	}
	

	public String insertTypeValues(CampType<?> ct){
		
		return 	 "'"+ct.name()+"', "
				+"'"+ct.attributeType().name()+"', "
				+"'"+ct.defaultValue()+"', "
				    +ct.typePosition()+", "
				+"'"+ct.typeGroup()+"'";
	}

	public String insertValueValues(int paid,CampList<CampType<?>> ctl){
		String retVal = "";
		boolean first = true;
		for(CampType<?> ct: ctl.value()) {
			if(ct.id()==0) {
				retVal += ((first)?"":" ,") +"( "+ insertValueValues(paid,ct)+" )";
				first = false;
			}
		}
		return 	retVal;
	}
	

	public String insertValueValues(int paid,CampType<?> ct){
		
		return 	     ct.valueId()+", "
					+paid+", "
					+ct.attributePosition()+", "
				+"'"+ct.attributeGroup()+"', "
				+"'"+value(paid,ct)+"'";
	}

	@Override
	public int deleteList(int productAttributeId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
