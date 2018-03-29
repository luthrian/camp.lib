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

import java.sql.ResultSet;
import java.sql.SQLException;

import com.camsolute.code.camp.lib.dao.database.DBDaoInterface;

public interface DescriptionDBDaoInterface {

   /**
     * Returns the database scheme name.
     *
     * @return data base name
     */
    public String dbName();

    /**
     * Returns the database table name in which the object instance our CampInstance belongs to is
     * persisted.
     *
     * @return table name
     */
    public String table();

    /**
     * Returns the database table definition (see: <code>com.camsolute.code.lib.data.CampSQL</code>)
     * of the object instance CampInstance belongs to.
     *
     * @return table definition
     */
    public String[][] tabledef();

  	public String updatestable();

  	public String[][] updatestabledef();

  	public String insertValues(Description d, boolean log);

    public String insertListValues(DescriptionList dl, boolean log);

    public String insertUpdateListValues(DescriptionList dl,String target);

  	public String insertUpdateValues(Description d, String target);

  	public String formatUpdateSQL(String SQL, Description d, boolean log);
  	
    public Description rsToI(ResultSet rs, boolean log) throws SQLException;
    
    public int createTable(boolean log);
    
    public int clearTables(boolean log);
}
