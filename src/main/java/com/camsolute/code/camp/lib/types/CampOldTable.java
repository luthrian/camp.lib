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
package com.camsolute.code.camp.lib.types;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.core.exceptions.CampTableColumnOutOfBoundsException;
import com.camsolute.code.camp.core.exceptions.CampTableRowOutOfBoundsException;
import com.camsolute.code.camp.core.exceptions.TransformFromException;
import com.camsolute.code.camp.core.exceptions.TransformToException;
import com.camsolute.code.camp.models.business.ProductAttributeDefinition;
import com.camsolute.code.camp.models.business.transformers.HashMapCampTablePATransformer;
import com.camsolute.code.camp.models.business.transformers.JSONCampTablePATransformer;

public class CampOldTable<T> extends CampOldType<ArrayList<CampOldList<T>>>{
	public static final boolean _DEBUG = true;
	private static String fmt = "[%15s] [%s]";
	
	private static final Logger LOG = LogManager.getLogger(CampOldTable.class);
	
	private ArrayList<CampOldList<T>> table;
	private static String defaultValue = null;
	
	public CampOldTable() {
		this.table = new ArrayList<CampOldList<T>>();
	}

	public ArrayList<CampOldList<T>> toList(){
		return table;
	}
	/**
	 * The <code>row()</code> method returns the number of rows in the table or 0 if
	 * the table is empty.
	 * 
	 * @return int: the number of rows in the table.
	 */
	public int rows() {
		return this.table.size();
	}
	
	/**
	 * The <code>columns()</code> method returns the number of columns in the table or 0
	 * if the table is empty.
	 * 
	 * @return int: the number of columns in the table.
	 */
	public int columns() {
		return ((this.table.size()>0)?this.table.get(0).size():0);
	}
	
	/**
	 * The <code>row(int row)</code> method returns the table row at position 'row'
	 * as CampList&lt;T&gt;. The row-count begins with <code>1</code>. 
	 * 
	 * @param row: the table row we want to get
	 * @return CampList&lt;T>: returns a list of elements (<code>CampList&lt;T></code>) that make up the table row.
	 * @throws CampTableRowOutOfBoundsException: if the parameter <code>row</code> larger than number of rows in table.
	 */
	public CampOldList<T> getRow(int row) throws CampTableRowOutOfBoundsException{ return _getRow(row, false); }
	public CampOldList<T> _getRow(int row, boolean log) throws CampTableRowOutOfBoundsException{
		String _f = "[_getRow]";
		String msg = " -- [ get row list ]";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
		if(row == 0){
		if(log && _DEBUG) {msg = "[Error! Row '"+row+"' is out of bounds. First table row is 1.]-- --";LOG.info(String.format(fmt,_f,msg));}

		}else if (row-1 > rows()){
		if(log && _DEBUG) {msg = "[Exception! Row '"+row+"' is out of bounds.]-- --";LOG.info(String.format(fmt,_f,msg));}

			throw new CampTableRowOutOfBoundsException(msg);
		}else if (Integer.valueOf(rows()) == null || rows()==0){
			return null;//throw new CampTableRowOutOfBoundsException(msg);
		}
		row--;
		return table.get(row);
	}
	
	/**
	 * The <code>getColumn(int col)</code> method returns the column at position <code>col</code>.
	 * The column-count begins at <code>1</code>. 
	 * @param col - an <code>integer</code> representing the table column to return.
	 * @return CampList&lt;T&gt; returns a list containing values of type &lt;T&gt;.
	 * @throws CampTableColumnOutOfBoundsException: if col larger than number of columns in table.
	 */
	public CampOldList<T> getColumn(int col) throws CampTableColumnOutOfBoundsException{
		col--;
		if(col < 0 || col > columns()){
			throw new CampTableColumnOutOfBoundsException();
		}
		CampOldList<T> rcol = new CampOldList<T>();
		for(int count=0;count<table.size();count++){
			rcol.add(table.get(count).get(col));
		}
		return rcol;
	}
	
	public T getCell(int row, int col) throws CampTableRowOutOfBoundsException, CampTableColumnOutOfBoundsException {
		if(row < 0 || row > rows()){
			throw new CampTableRowOutOfBoundsException();
		}
		col--;
		if(col < 0 || col > columns()){
			throw new CampTableColumnOutOfBoundsException();
		}
		return getRow(row).get(col);
	}
	
	public CampOldList<T> setRow(int atRow, CampOldList<T> row) throws CampTableRowOutOfBoundsException{
		atRow--;
		if(atRow < 0 || atRow > rows()){
			throw new CampTableRowOutOfBoundsException();
		}		
		return this.table.set(atRow,row);
	}
	
	public boolean addRow(CampOldList<T> row){
		return this.table.add(row);
	}
	
	public void addRow(int atRow,CampOldList<T> row) throws CampTableRowOutOfBoundsException{
		atRow--;
		if(atRow < 0 || atRow > rows()){
			throw new CampTableRowOutOfBoundsException();
		}		
		this.table.add(atRow, row);
	}
	
	public CampOldList<T> removeRow(int row) throws CampTableRowOutOfBoundsException{
		row--;
		if(row < 0 || row > rows()){
			throw new CampTableRowOutOfBoundsException();
		}		
		return this.table.remove(row);
	}
	
	public T add(int row, int col, T value) throws CampTableRowOutOfBoundsException, CampTableColumnOutOfBoundsException{
		row--;
		if(row < 0 || row > rows()){
			throw new CampTableRowOutOfBoundsException();
		}			
		col--;
		if(col < 0 || col > columns()){
			throw new CampTableColumnOutOfBoundsException();
		}	
		return this.table.get(row).set(col, value);
	}
	
	public CampOldList<T> addColumn(int col, CampOldList<T> column) throws CampTableColumnOutOfBoundsException{
		CampOldList<T> ret = getColumn(col);
		col--;
		if(col < 0 || col > columns()){
			throw new CampTableColumnOutOfBoundsException();
		}	
		int rows = rows();
		for(int i=0;i<rows;i++){
			this.table.get(i).add(col, column.get(i));
		}
		return ret;
	}

	public CampOldList<T> setColumn(int col, CampOldList<T> column) throws CampTableColumnOutOfBoundsException{
		CampOldList<T> ret = getColumn(col);
		col--;
		if(col < 0 || col > columns()){
			throw new CampTableColumnOutOfBoundsException();
		}	
		int rows = rows();
		for(int i=0;i<rows;i++){
			this.table.get(i).set(col, column.get(i));
		}
		return ret;
	}

	public CampOldList<T> removeColumn(int col) throws CampTableColumnOutOfBoundsException{
		CampOldList<T> ret = new CampOldList<T>();
		col--;
		if(col < 0 || col > columns()){
			throw new CampTableColumnOutOfBoundsException();
		}	
		int rows = rows();
		for(int i = 0; i<rows;i++){
			ret.add(this.table.get(i).remove(col));
		}
		return ret;
	}
	
	public int size(){
		return table.size();
	}
	
	public boolean isEmpty(){
		return table.size() == 0;
	}
	
	public boolean contains(CampOldList<ProductAttributeDefinition<?,?>> o){
		return this.table.contains(o);
	}
	
	public int indexOf(CampOldList<ProductAttributeDefinition<?,?>> o){
		return this.table.indexOf(o);
	}
	
	public int lastIndexOf(CampOldList<ProductAttributeDefinition<?,?>> o){
		return this.table.lastIndexOf(o);
	}
		
	
	public final static CampOldTable<ProductAttributeDefinition<?,?>> fromJSON(String json){ return _fromJSON(json,true); }
	public final static CampOldTable<ProductAttributeDefinition<?,?>> _fromJSON(String json, boolean log){
		String _f = "[_fromJSON]";
		String msg = " -- [  ]";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
		try {
			return JSONCampTablePATransformer.instance().transformTo(json);
		} catch (TransformToException e) {
			msg = "Failed to create CampTable from JSON "+json;if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
			e.printStackTrace();
		}
		return null;
	}
	
	public final static String toJSON(CampOldTable<ProductAttributeDefinition<?,?>> from){ return _toJSON(from, true); }
	public final static String _toJSON(CampOldTable<ProductAttributeDefinition<?,?>> from, boolean log){ 
		String _f = "[_toJSON]";
		String msg = " -- [  ]";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
		try {
			return (String) JSONCampTablePATransformer.instance().transformFrom(from);
		} catch (TransformFromException e) {
		if(log && _DEBUG) {msg = "[Failed to create JSON from CampTable ]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		}
		return null;
	}
	
	public final static CampOldTable<ProductAttributeDefinition<?,?>> fromHashMap(LinkedHashMap<String, Object> from){ return _fromHashMap(from,true);}
	public final static CampOldTable<ProductAttributeDefinition<?,?>> _fromHashMap(LinkedHashMap<String, Object> from, boolean log){
		String _f = "[_fromHashMap]";
		String msg = " -- [ transforming hashmap to product attribute definition ]";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
		try {
			return HashMapCampTablePATransformer.instance().transformTo(from);
		} catch (TransformToException e) {
		if(log && _DEBUG) {msg = "[Failed to create CampTable from LinkedHashMap]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		}
		return null;
	}

	public final static LinkedHashMap<String,Object> toHashMap(CampOldTable<ProductAttributeDefinition<?,?>> from){ return _toHashMap(from, true); }
	@SuppressWarnings("unchecked")
	public final static LinkedHashMap<String,Object> _toHashMap(CampOldTable<ProductAttributeDefinition<?,?>> from, boolean log){
		String _f = "[_toHashMap]";
		String msg = " -- [ fransforming product attribute definition to hashmap ]";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
		try {
			return (LinkedHashMap<String, Object>) HashMapCampTablePATransformer.instance().transformFrom(from);
		} catch (TransformFromException e) {
		if(log && _DEBUG) {msg = "[Failed to create LinkedHashMap from CampTable ]-- --";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected ArrayList<CampOldList<T>> me() {
		return table;
	}

	@Override
	protected ArrayList<CampOldList<T>> me(ArrayList<CampOldList<T>> value) {
		ArrayList<CampOldList<T>> prev = this.table;
		this.table = value;
		return prev;
	}
}
