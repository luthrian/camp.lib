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
package com.camsolute.code.camp.lib.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.core.U;
import com.camsolute.code.camp.core.interfaces.IsTableNameGenerator;
import com.camsolute.code.camp.core.types.CampOldComplex;
import com.camsolute.code.camp.core.types.CampOldTable;
import com.camsolute.code.camp.core.types.CampTypes;
import com.camsolute.code.camp.models.business.OrderProduct;
import com.camsolute.code.camp.models.business.OrderProductAttribute;

public class TableNameGenerator implements IsTableNameGenerator{
	public static final boolean _DEBUG = true;
	private static String fmt = "[%15s] [%s]";
	
	private static final Logger LOG = LogManager.getLogger(TableNameGenerator.class);
	

	private static TableNameGenerator instance = null;
	
	private TableNameGenerator(){
	}
	
	public static TableNameGenerator instance(){
		if(instance == null) {
			instance = new TableNameGenerator();
		}
		return instance;
	}
	
	@Override
	public String tableName(String group, OrderProductAttribute<?,?> pa) throws SQLException {
		return _tableName(group,pa,true);
	}
	@Override
	public String _tableName(String group, OrderProductAttribute<?,?> pa,boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
		String _f = "[tableName]";
		String msg = "";
		if(group == null){
		if(log && _DEBUG) {msg = "-- --[ ERROR! null group!]-- --";LOG.info(String.format(fmt,_f,msg));}
			return null;
		}
		
		String tableName = TableNameGenerator.tableName(group,pa.product(),log);

		tableName += addTag(group,pa);
		
	    msg = "---- ---- generated table name '"+tableName+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
	    return tableName;
	}
	
	
	public String tableName(String orderNumber, int orderPositionId,int productId,String group) {
		return _tableName(orderNumber,orderPositionId,productId,group,true);
	}
	public String _tableName(String orderNumber, int orderPositionId,int productId,String group,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = "[tableName]";
		String msg = "";
		if(group == null){
		if(log && _DEBUG) {msg = "-- --[ ERROR! null group!]-- --";LOG.info(String.format(fmt,_f,msg));}
			return null;
		}
		String tableName = orderNumber+DBU._VS;
		tableName += orderPositionId+DBU._VS;
		tableName += productId+DBU._VS;
		tableName += group.trim().replace(" ", DBU._NS);
		
	    msg = "---- ---- generated table name '"+tableName+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
	    return tableName;
	}
	
public static String addTag(String group, OrderProductAttribute<?,?> pa){
		String ns = DBU._NS;
		String vs = DBU._VS;
		
		String tag = "";
		if((pa.type().equals(Attribute.PS_COMPLEX_L) && pa.value().getClass().isAssignableFrom(CampOldComplex.class))||
				   (pa.type().equals(Attribute.PS_TABLE_L) && pa.value().getClass().isAssignableFrom(CampOldTable.class))){
				    
			tag += vs+ U.tag(pa.type()) +vs+ pa.name().trim().replace(" ", ns)
//						+vs+group.trim().replace(" ", ns)
						;}
		
		return tag;
		
	}

//	@Override
//	public String[] tableNames(OrderProductAttribute<?,?> pa) {
//		String _f = "-- [tableName]";
//		String msg = ">>>> assembling tableName";U.log(U.msg(_f,msg,_DEBUG));
//		
//		String[] names = new String[pa.product().attributes().size()];
//		int count = 0;
//		for(String group: pa.product().attributes().keySet()){
//			String tableName = pa.product().tableName(group) 
//					+ DBU._NS + U.tag(pa.type()) 
//					+ DBU._NS + pa.name().trim().replace(" ", DBU._NS)
//					+ DBU._NS + group.trim().replace(" ", DBU._NS);
//			names[count] = tableName;
//			count++;
//		}
//	    return names;
//	}
	public static final com.camsolute.code.camp.lib.paContainerType getContainerType(String tableName){
		return _getContainerType(tableName, false);
	}
	public static final com.camsolute.code.camp.lib.paContainerType _getContainerType(String tableName,boolean log){
//		String _f = "-- [_getContainerType]";
//		String msg = ">>>> eliciting container type from '"+tableName+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
//		
		if(tableName == null) return null;
		
		Attribute.paContainerType ct = Attribute.paContainerType.PRODUCT;

		String vs = DBU._VS;
		
		String[] tmValues = tableName.split(vs);
		
		if(tmValues.length > 4){

			switch(tmValues[4]){
			
			case Attribute.PS_TABLE:
			
				ct = Attribute.paContainerType.TABLE;
				
				break;
			
			case Attribute.PS_COMPLEX:
			
				ct = Attribute.paContainerType.COMPLEX;
				
				break;
			default:
			}
		}
		return ct;
	}
	
	public static final String tableName(String group,OrderProduct p,boolean log) throws SQLException{
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[tableName]";
			msg = "====[ generating table name ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		if(group == null||p == null) {
			throw new SQLException("TABLENAME EXCEPTION! group('"+group+"')/p('"+p+"')");
		}
		String tableName = p.orderBusinessId()+DBU._VS;
		tableName += p.orderPositionId()+DBU._VS;
		tableName += p.id()+DBU._VS;
		tableName += group.trim().replace(" ", DBU._NS);
		
		if(log && _DEBUG) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[tableName ('"+tableName+"') completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		
		return tableName;
	}

	public static final String _getOrderNumber(String tableName,boolean log){
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_getOrderNumber]";
			msg = "====[ get order number from '"+tableName+"' ]====";LOG.info(String.format(fmt,_f,msg));
		}

		String vs = DBU._VS;
		
		String on=null;

		String[] tmValues = tableName.split(vs);
		
		//TODO: checks
		if(tmValues.length > 2){
			on = tmValues[0];
		}
		
		return on;
	}
	public static final int _getOrderPositionId(String tableName,boolean log){
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_getOrderPositionId]";
			msg = "====[ get order position id from '"+tableName+"' ]====";LOG.info(String.format(fmt,_f,msg));
		}

		String vs = DBU._VS;
		
		String opid=null;

		String[] tmValues = tableName.split(vs);
		
		//TODO: some checks
		if(tmValues.length > 2){
			opid = tmValues[1];
		}
		
		return Integer.valueOf(opid);
	}
	public static final int _getProductId(String tableName,boolean log){
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_getProductId]";
			msg = "====[ get product id from '"+tableName+"' ]====";LOG.info(String.format(fmt,_f,msg));
		}

		String vs = DBU._VS;
		
		String opid=null;

		String[] tmValues = tableName.split(vs);
		
		//TODO: some checks
		if(tmValues.length > 2){
			opid = tmValues[2];
		}
		
		return Integer.valueOf(opid);
	}
	public static final String _getTableGroupName(String tableName,boolean log){
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_getTableGroupName]";
			msg = "====[ get group name from '"+tableName+"'  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}

		if(tableName == null)return null;
		
		String vs = DBU._VS;
		
		String group=null;

		String[] tmValues = tableName.split(vs);
		
		//FIXME: this will break if table name generator changes naming convention (as it did when i dropped the prefix pa)
		if(tmValues.length > 3){
			group = tmValues[3];
		}
		
		return group;
	}

	public static final String _typeFromTableName(String tableName,boolean log){
//		String _f = "-- [_typeFromTableName]";
//		String msg = ">>>> get order product attribute type from '"+tableName+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));

		String vs = DBU._VS;
		
		String type=null;

		String[] tmValues = tableName.split(vs);
		
		//TODO: checks
		if(tmValues.length > 4){
			type = tmValues[4];
		}
		String[][] types = Attribute.tags;
		return type;
	}

	public static final String _nameFromTableName(String tableName,boolean log){
//		String _f = "-- [_nameFromTableName]";
//		String msg = ">>>> get order product attribute  name from '"+tableName+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));

		String vs = DBU._VS;
		String ns = DBU._NS;
		
		String name=null;

		String[] tmValues = tableName.split(vs);
		
		//TODO: checks
		if(tmValues.length > 5){
			name = tmValues[5].trim().replace(ns, " ");
		}
		String[][] types = Attribute.tags;
		return name;
	}
	public static final String _groupFromTableName(String tableName,boolean log){
//		String _f = "-- [_groupFromTableName]";
//		String msg = ">>>> get order product attribute  group from '"+tableName+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));

		String vs = DBU._VS;
		String ns = DBU._NS;
		
		String paGroup=null;

		String[] tmValues = tableName.split(vs);
		
		//TODO: checks
		if(tmValues.length > 6){
			paGroup = tmValues[6].trim().replace(ns, " ");
		}
		return paGroup;
	}

	public static String columnName(OrderProductAttribute<?,?> op) {
		// TODO Auto-generated method stub
		String vs = DBU._VS;
		String ns = DBU._NS;
		return U.tag(op.type()) +vs+ op.name().trim().replace(" ", ns) 
				+vs+ op.group().name().trim().replace(" ", ns);//+vs+op.position();
	}
	public static final String _typeFromColumnsName(String columnName,boolean log){
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_typeFromColumnName]";
			msg = "====[ get order product attribute type from '"+columnName+"' ]====";LOG.info(String.format(fmt,_f,msg));
		}

		String vs = DBU._VS;
		
		String type=null;

		String[] tmValues = columnName.split(vs);
		
		//TODO: checks
		if(tmValues.length > 2){
			type = tmValues[0];
		}
		String[][] types = Attribute.tags;
		return type;
	}

	public static final String _nameFromColumnName(String columnName,boolean log){
//		String _f = "-- [_nameFromColumnName]";
//		String msg = ">>>> get order product attribute  name from '"+columnName+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));

		String vs = DBU._VS;
		String ns = DBU._NS;
		
		String name=null;

		String[] tmValues = columnName.split(vs);
		
		//TODO: checks
		if(tmValues.length > 2){
			name = tmValues[1].trim().replace(ns, " ");
		}
		String[][] types = Attribute.tags;
		return name;
	}
	public static final String _groupFromColumnName(String columnName,boolean log){
//		String _f = "-- [_groupFromColumnName]";
//		String msg = ">>>> get order product attribute  group from '"+columnName+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));

		String vs = DBU._VS;
		String ns = DBU._NS;
		
		String paGroup=null;

		String[] tmValues = columnName.split(vs);
		
		//TODO: checks
		if(tmValues.length > 2){
			paGroup = tmValues[2].trim().replace(ns, " ");
		}
		return paGroup;
	}
//	public static final int _positionFromColumnName(String columnName,boolean log){
////		String _f = "-- [_groupFromColumnName]";
////		String msg = ">>>> get order product attribute  group from '"+columnName+"'";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
//
//		String vs = DBU._VS;
//		String ns = DBU._NS;
//		
//		int position=0;
//
//		String[] tmValues = columnName.split(vs);
//		
//		//TODO: checks
//		if(tmValues.length > 3){
//			position = Integer.valueOf(tmValues[3]);
//		}
//		return position;
//	}
	/**
	 * get a list of table names and from it return a list of top level product attribute table names;
	 * @param tables
	 * @return
	 */
	public static final ArrayList<String> _getProductTables(ArrayList<String> tables){
		ArrayList<String> pTables = new ArrayList<String>();
		String vs = DBU._VS;
		for(String tableName:tables){
			if(tableName.split(vs).length==4){
				pTables.add(tableName);
			}
		}
		return pTables;
	}

	public static String _updatesName(String opaName,String opaGroup) {		
		return opaName.trim().replace(" ", DBU._NS)+DBU._VS+opaGroup.trim().replace(" ", DBU._NS);
	}
	public static String _updatesName(OrderProductAttribute<?,?> p) {		
		return p.name().trim().replace(" ", DBU._NS)+DBU._VS+p.group().name().trim().replace(" ", DBU._NS);
	}
	public static HashMap<String,String> _updatesNameParts(String updatesName){
		HashMap<String,String> parts = new HashMap<String,String>();
		String[] p = updatesName.split(DBU._VS);
		parts.put("attribute_name", p[0].replace(DBU._NS," "));
		parts.put("attribute_group", p[1].replace(DBU._NS," "));
		return parts;
	}

	public static String toDBName(String name) {
		name.trim().replace(" ", DBU._NS);
		return name;
	}
	public static String fromDBName(String name) {
		name.replace(DBU._NS, " ");
		return name;
	}

}
