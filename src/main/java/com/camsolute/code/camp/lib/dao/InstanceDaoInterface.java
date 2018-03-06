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
package com.camsolute.code.camp.lib.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.dao.database.DBDaoInterface;

public interface InstanceDaoInterface<T extends IsObjectInstance<T>> extends DaoInterface<T> {

	public T instanceLoad(String select, boolean primary,boolean log);

	public <E extends ArrayList<T>> E instanceListLoad(String select, boolean primary, boolean log);
	
  public String dbName(boolean primary);

  public String table(boolean primary);

  public String[][] tabledef(boolean primary);

	public String updatestable(boolean primary);

	public String[][] updatestabledef(boolean primary);
	
	public String businessIdColumn(boolean primary);

}
