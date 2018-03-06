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
/**
 * ***************************************************************************** Copyright (C) 2017
 * Christopher Campbell (campbellccc@gmail.com)
 *
 * <p>This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * <p>You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 *
 * <p>Contributors: Christopher Campbell (campbellccc@gmail.com) - all code prior and post initial
 * release ****************************************************************************
 */
package com.camsolute.code.camp.lib.dao.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.HasResultSetToInstance;
import com.camsolute.code.camp.lib.utilities.Util;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.Model;

public interface DBDaoInterface<T extends IsObjectInstance> extends TableDaoInterface{

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

  	public String insertValues(T p, boolean log);

    public <E extends ArrayList<T>> String insertListValues(E pl, boolean log);

    public <E extends ArrayList<T>> String insertUpdateListValues(E pl,String target);

  	public String insertUpdateValues(T p, String target);

  	public String formatUpdateSQL(String SQL, T p, boolean log);
  	
    public T rsToI(ResultSet rs, boolean log) throws SQLException;
  	
}
