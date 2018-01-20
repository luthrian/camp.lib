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
package com.camsolute.code.camp.lib.contract;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IsInstanceDao<T> {
	/**
	 * Returns the database scheme name.
	 * 
	 * @return
	 */
	public String dbName();
	
	/**
	 * Returns the database table name in which the object instance our CampInstance belongs to is persisted.
	 * 
	 * @return
	 */
	public String table();
	
	/**
	 * Returns the database table definition (see: <code>com.camsolute.code.lib.data.CampSQL</code>) of the object instance Campinstance belongs to. 
	 * @return
	 * 
	 */
	public String[][] tabledef();

	/**
	 * Returns the database table name in which CampInstance is persisted.
	 * 
	 * @return
	 */
	public String itable();
	
	public T rsToI(ResultSet rs, boolean log) throws SQLException;
	
	
}
