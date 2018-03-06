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

import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.exceptions.CampTableColumnOutOfBoundsException;
import com.camsolute.code.camp.lib.exceptions.CampTableRowOutOfBoundsException;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.AttributeInterface;

public interface CampTableInterface extends AttributeInterface<TableValue> {

	public ArrayList<Attribute<?>> toList();
	
	public int rows();
	
	public int columns();
	
	public ArrayList<Attribute<?>> getRow(int row) throws CampTableRowOutOfBoundsException;
	
	public ArrayList<Attribute<?>> getColumn(int column) throws CampTableColumnOutOfBoundsException;
	
	public Attribute<?> getCell(int row, int col) throws CampTableRowOutOfBoundsException, CampTableColumnOutOfBoundsException;
	
	public ArrayList<Attribute<?>>  setRow(int atRow, ArrayList<Attribute<?>> row) throws CampTableRowOutOfBoundsException;
	
	public void addRow(int atRow,ArrayList<Attribute<?>> row) throws CampTableRowOutOfBoundsException;
	
	public ArrayList<Attribute<?>> removeRow(int row) throws CampTableRowOutOfBoundsException;
	
	public Attribute<?> add(int row, int col, Attribute<?> value) throws CampTableRowOutOfBoundsException, CampTableColumnOutOfBoundsException;
	
	public ArrayList<Attribute<?>> addColumn(int col, ArrayList<Attribute<?>> column) throws CampTableColumnOutOfBoundsException;
	
	public ArrayList<Attribute<?>> setColumn(int col, ArrayList<Attribute<?>> column) throws CampTableColumnOutOfBoundsException;
	
	public ArrayList<Attribute<?>> removeColumn(int col) throws CampTableColumnOutOfBoundsException;
	
	public int size();
	
	public boolean isEmpty();
	
	public boolean contains(ArrayList<Attribute<?>> o);
	
	public int indexOf(ArrayList<Attribute<?>> o);
	
	public int lastIndexOf(ArrayList<Attribute<?>> o);
	
	public static String _toJson(CampTable a) {
		return AttributeInterface._toJson(a);
	}
	
	public static CampTable _fromJson(String json) {
		return (CampTable) AttributeInterface._fromJson(json);
	}
	
}
