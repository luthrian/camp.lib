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
package com.camsolute.code.camp.lib.models.process;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.camsolute.code.camp.lib.dao.DaoInterface;
import com.camsolute.code.camp.lib.models.Model;
import com.camsolute.code.camp.lib.models.process.Process.ProcessType;

public interface ProcessDaoInterface {
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

	public String table(ProcessType type) ;

	public String[][] tabledef(ProcessType type) ;

	public String updatestable(ProcessType type) ;

	public String[][] updatestabledef(ProcessType type) ;

	public <T extends Process<?,?>> T rsToI(ResultSet rs, boolean log) throws SQLException;
	
  public <T extends Process<?,?>> T loadById(int id, boolean log);

  public  <T extends Process<?,?>> T loadByInstanceId(String instanceId, boolean log);

  public  <E extends ProcessList> E loadListByKey(String businessKey, boolean log);

  public  <E extends ProcessList> E loadListByBusinessId(String businessId, boolean log);

  public Process<?,?> save(Process<?,?> instance, boolean log);

  public <E extends ProcessList> E saveList(E instanceList, boolean log);

  public <T extends Process<?,?>> int update(T instance, boolean log);

  public <E extends ProcessList> int updateList(E instanceList, boolean log);

  public <E extends ProcessList> E loadUpdates(String businessKey, String target, boolean log);

  public <E extends ProcessList> E loadUpdatesByKey(String businessKey, boolean log);

  public <E extends ProcessList> E loadUpdatesByTarget(String target, boolean log);

  public  <T extends Process<?,?>> T loadUpdate(String instanceId, String businessId, String businessKey, String target, boolean log);

  public  <T extends Process<?,?>> T loadUpdate(T instance, String businessKey, String target, boolean log);

  public <T extends Process<?,?>> int addToUpdates(String instanceId, String businessId, String businessKey, String target, boolean log);
  
  public  <T extends Process<?,?>> int addToUpdates(T instance, String businessKey, String target, boolean log);

  public <E extends ProcessList> int addToUpdates(E instanceList, String businessKey, String target, boolean log);

  public int deleteAllFromUpdates(String businessKey, String target, boolean log);

  public int deleteFromUpdatesByKey(String businessKey, boolean log);

  public int deleteFromUpdatesByTarget(String target, boolean log);

  public int deleteFromUpdates(String instanceId, String businessId, String businessKey, String target, boolean log);

  public <E extends ProcessList> int deleteFromUpdates(E instanceList, String businessKey, String target, boolean log);

//TODO: just an idea    public int/T _dbExecute(Util.DB.dbActionType action,String SQL, String table, String[][] tabledef, boolean log);

	public String insertValues(Process<?,?> p, boolean log);

  public <E extends ProcessList> String insertListValues(E pl, boolean log);

  public <E extends ProcessList> String insertUpdateListValues(E pl,String target);

	public String insertUpdateValues(Process<?,?> p, String target);

	public String formatUpdateSQL(String SQL, Process<?,?> p, boolean log);
	
  public int createTable(boolean log);
  
  public int clearTables(boolean log);


}
