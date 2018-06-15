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
package com.camsolute.code.camp.lib.types;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.utilities.Util;
import com.camsolute.code.camp.lib.exceptions.CampTableRowOutOfBoundsException;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.ValueInterface;
import com.camsolute.code.camp.lib.contract.core.Value;
import com.camsolute.code.camp.lib.exceptions.CampTableColumnOutOfBoundsException;


public class CampTable extends Attribute<TableValue> implements CampTableInterface{
	public static final boolean _DEBUG = true;
	private static String fmt = "[%15s] [%s]";
	
	private static final Logger LOG = LogManager.getLogger(CampTable.class);
	
	public CampTable() {
		super(null, AttributeType._table, null);
	}

	public CampTable(String name) {
		super(name, AttributeType._table, null);
	}

	public CampTable(String name, String defaultValue) {
		super(name, AttributeType._table, defaultValue);
	}

	public ArrayList<Attribute<?>> toList(){
		ArrayList<Attribute<?>> list = new ArrayList<Attribute<?>>();
		for(ArrayList<Attribute<?>> al: this.value().value()) {
			for(Attribute<?> a: al) {
				list.add(a);
			}
		}
		return list;
	}
	/**
	 * The <code>row()</code> method returns the number of rows in the table or 0 if
	 * the table is empty.
	 * 
	 * @return int: the number of rows in the table
	 */
	public int rows() {
		return this.value().value().size();
	}
	
	/**
	 * The <code>columns()</code> method returns the number of columns in the table or 0
	 * if the table is empty.
	 * 
	 * @return int: the number of columns in the table.
	 */
	public int columns() {
		return ((this.value().value().size()>0)?this.value().value().get(0).size():0);
	}
	
	/**
	 * The <code>row(int row)</code> method returns the table row at position 'row'
	 * as CampList&lt;T&gt;. The row-count begins with <code>1</code>. 
	 * 
	 * @param row: the table row we want to get
	 * @return CampList&lt;T&gt;: returns a list of elements (<code>CampList&lt;T&gt;</code>) that make up the table row.
	 * @throws CampTableRowOutOfBoundsException if the parameter <code>row</code> larger than number of rows in table.
	 */
	public ArrayList<Attribute<?>> getRow(int row) throws CampTableRowOutOfBoundsException{ return _getRow(row, !Util._IN_PRODUCTION); }
	public ArrayList<Attribute<?>> _getRow(int row, boolean log) throws CampTableRowOutOfBoundsException{
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
		return value().value().get(row);
	}
	
	/**
	 * The <code>getColumn(int col)</code> method returns the column at position <code>col</code>.
	 * The column-count begins at <code>1</code>. 
	 * @param col - an <code>integer</code> representing the table column to return.
	 * @return CampList&lt;T&gt; returns a list containing values of type &lt;T&gt;.
	 * @throws CampTableColumnOutOfBoundsException if col larger than number of columns in table.
	 */
	public ArrayList<Attribute<?>> getColumn(int col) throws CampTableColumnOutOfBoundsException{
		col--;
		if(col < 0 || col > columns()){
			throw new CampTableColumnOutOfBoundsException();
		}
		
		ArrayList<Attribute<?>> rcol = new ArrayList<Attribute<?>>();
		for(int count=0;count<value().value().size();count++){
			rcol.add(value().value().get(count).get(col));
		}
		return rcol;
	}
	
	public Attribute<?> getCell(int row, int col) throws CampTableRowOutOfBoundsException, CampTableColumnOutOfBoundsException {
		if(row < 0 || row > rows()){
			throw new CampTableRowOutOfBoundsException();
		}
		col--;
		if(col < 0 || col > columns()){
			throw new CampTableColumnOutOfBoundsException();
		}
		return getRow(row).get(col);
	}
	
	public ArrayList<Attribute<?>> setRow(int atRow, ArrayList<Attribute<?>> row) throws CampTableRowOutOfBoundsException{
		atRow--;
		if(atRow < 0 || atRow > rows()){
			throw new CampTableRowOutOfBoundsException();
		}		
		return this.value().value().set(atRow,row);
	}
	
	public boolean addRow(ArrayList<Attribute<?>> row){
		return this.value().value().add(row);
	}
	
	public void addRow(int atRow,ArrayList<Attribute<?>> row) throws CampTableRowOutOfBoundsException{
		atRow--;
		if(atRow < 0 || atRow > rows()){
			throw new CampTableRowOutOfBoundsException();
		}		
		this.value().value().add(atRow, row);
	}
	
	public ArrayList<Attribute<?>> removeRow(int row) throws CampTableRowOutOfBoundsException{
		row--;
		if(row < 0 || row > rows()){
			throw new CampTableRowOutOfBoundsException();
		}		
		return this.value().value().remove(row);
	}
	
	public Attribute<?> add(int row, int col, Attribute<?> value) throws CampTableRowOutOfBoundsException, CampTableColumnOutOfBoundsException{
		row--;
		if(row < 0 || row > rows()){
			throw new CampTableRowOutOfBoundsException();
		}			
		col--;
		if(col < 0 || col > columns()){
			throw new CampTableColumnOutOfBoundsException();
		}	
		return this.value().value().get(row).set(col, value);
	}
	
	public ArrayList<Attribute<?>> addColumn(int col, ArrayList<Attribute<?>> column) throws CampTableColumnOutOfBoundsException{
		ArrayList<Attribute<?>> ret = getColumn(col);
		col--;
		if(col < 0 || col > columns()){
			throw new CampTableColumnOutOfBoundsException();
		}	
		int rows = rows();
		for(int i=0;i<rows;i++){
			this.value().value().get(i).add(col, column.get(i));
		}
		return ret;
	}

	public ArrayList<Attribute<?>> setColumn(int col, ArrayList<Attribute<?>> column) throws CampTableColumnOutOfBoundsException{
		ArrayList<Attribute<?>> ret = getColumn(col);
		col--;
		if(col < 0 || col > columns()){
			throw new CampTableColumnOutOfBoundsException();
		}	
		int rows = rows();
		for(int i=0;i<rows;i++){
			this.value().value().get(i).set(col, column.get(i));
		}
		return ret;
	}

	public ArrayList<Attribute<?>> removeColumn(int col) throws CampTableColumnOutOfBoundsException{
		ArrayList<Attribute<?>> ret = new ArrayList<Attribute<?>>();
		col--;
		if(col < 0 || col > columns()){
			throw new CampTableColumnOutOfBoundsException();
		}	
		int rows = rows();
		for(int i = 0; i<rows;i++){
			ret.add(this.value().value().get(i).remove(col));
		}
		return ret;
	}
	
	public int size(){
		return value().value().size();
	}
	
	public boolean isEmpty(){
		return value().value().isEmpty();
	}
	
	public boolean contains(ArrayList<Attribute<?>> o){
		return this.value().value().contains(o);
	}
	
	public int indexOf(ArrayList<Attribute<?>> o){
		return this.value().value().indexOf(o);
	}
	
	public int lastIndexOf(ArrayList<Attribute<?>> o){
		return this.value().value().lastIndexOf(o);
	}
	
	@Override
	public String toJson() {
		return CampTableInterface._toJson(this);
	}

	@Override
	public Attribute<TableValue> fromJson(String json) {
		return CampTableInterface._fromJson(json);
	}

	@Override
	public TableValue valueFromString(String json) {
		return (TableValue) ValueInterface._fromJson(json);
	}


}
