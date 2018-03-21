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
package com.camsolute.code.camp.lib.models.customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ContactDetailsDBDaoInterface {
	
	public String dbName();
	
	public String table();
	
	public String[][] tabledef();
	
	public String updatestable();
	
	public String[][] updatestabledef();
	
	public String insertValues(ContactDetails c, boolean log);
	
	public String insertValues(ContactDetailsList cl, boolean log);
	
	public String insertUpdateValues(ContactDetails c, String target);
	
	public String insertUpdateValues(ContactDetailsList cl, String target);
	
	public ContactDetails rsToI(ResultSet rs, boolean log) throws SQLException; 
	
	public int createTable(boolean log);
	
	public int clearTables(boolean log);

}
