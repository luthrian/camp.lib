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
package com.camsolute.code.camp.lib.dao.rest;

import java.sql.SQLException;
import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.HasResultSetToInstance;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.dao.DaoInterface;
import com.camsolute.code.camp.lib.dao.InstanceDaoInterface;
import com.camsolute.code.camp.lib.dao.database.DBDaoInterface;
import com.camsolute.code.camp.lib.models.CampInstance;

public interface CampInstanceRestDaoInterface  {

  public <T extends IsObjectInstance<T>>int addInstance(T object, boolean useObjectId) throws SQLException;

  public <T extends IsObjectInstance<T>, E extends ArrayList<T>>int addInstances(E objectList, boolean useObjectId) throws SQLException;
  
  /**
   * Load the most recent persisted business object instance of Type <code>T</code> from the Database based on its
   * businessId. Only the most current instance entry (:= instanceId==currentInstanceId) is loaded.
   *
   * @param businessId business id
   * @param primary boolean switch which object id etc. to use
   * @param <E> current object instance
   * @return current instance of the business persisted business object
   * @throws SQLException sql exception
   */
  public <E extends IsObjectInstance<E>> E loadCurrent(String businessId,  boolean primary) throws SQLException;

  public <E extends IsObjectInstance<E>> E loadFirst(String businessId, boolean primary) throws SQLException;

  public <E extends IsObjectInstance<E>> E loadPrevious(E object, boolean primary) throws SQLException;

  public <E extends IsObjectInstance<E>> E loadNext(E object, boolean primary) throws SQLException;
 
  public <U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDate(String businessId, String date, boolean primary) throws SQLException;
  
  public <U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDateRange(String businessId, String startDate, String endDate, boolean primary) throws SQLException;
  
  public <U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDate(String date, boolean primary) throws SQLException;
  
  public <U extends IsObjectInstance<U>, E extends ArrayList<U>> E loadDateRange(String startDate, String endDate, boolean primary) throws SQLException;
  

}
