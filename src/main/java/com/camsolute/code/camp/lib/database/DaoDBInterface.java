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

import java.sql.ResultSet;

import com.camsolute.code.camp.core.interfaces.HasId;
import com.camsolute.code.camp.core.types.CampOldList;

public interface DaoDBInterface<T extends HasId> {

	
	public CampOldList<T> _rsToList(ResultSet rs, boolean log);

	public T _rsTo(ResultSet rs,boolean log);
	
	public abstract T _rsToT(ResultSet rs, boolean log);
	
	
	public abstract String insertValues(T p, boolean log);
	
	public abstract String insertListValues(CampOldList<T> pl, boolean log);
	
	public abstract String insertUpdateListValues(CampOldList<T> pl,String target);

	public abstract String insertUpdateValues(T p, String target);
	
	
	public String table();
	
	public void table(String tableName);
	
	public String[][] tableDefinition();
	
	public void tableDefinition(String[][] tableDefinition);
	
	public String updatesTable();
	
	public void updatesTable(String tableName);
	
	public String[][] updatesTableDefinition();
	
	public void updatesTableDefinition(String[][] tableDefinition);
	
	public String dbName();
	
	public void dbName(String databaseName);
	
	
	public int _initDB(boolean log);
	
	public int _createDB(boolean log);
	
	public int _createTable(boolean checkDbExists, boolean log);

	public int _clearTable(boolean log);	

	public String _columns(DBU.dbActionType action,Boolean log);
	
}
