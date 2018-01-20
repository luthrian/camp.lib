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

import java.sql.SQLException;

import com.camsolute.code.camp.core.interfaces.HasId;
import com.camsolute.code.camp.core.types.CampOldList;

public interface ModelDaoInterface<T extends HasId> {
	
	/**
	 * The <code>save</code> function persists an object instance to the database.
	 * The object is only persisted if it is a new object and a current instance.
	 * 
	 * @param instance - the object instance being saved
	 * @return the instance with updated technical id (== database entry id)
	 * @throws SQLException 
	 */
	public T save(T instance) throws SQLException;
	
	/**
	 * The <code>saveList</code> function persists a list of object instances to the database.
	 * The instances in the list are only persisted if it is a new object and the current instance.
	 * 
	 * @param instanceList
	 * @return a list of instances with updated technical id value (== database entry id)
	 * @throws SQLException
	 */
	public CampOldList<T> saveList(CampOldList<T>  instanceList) throws SQLException; 

	//update an instance
	public T update(T instance) throws SQLException;
	
	//update a list of instances
	public CampOldList<T>updateList(CampOldList<T>  instanceList); 

	//load instance by id
	public T loadById(int instanceId) throws SQLException; 
	
	//load instance list by key
	public CampOldList<T> loadList(String... key); //TODO:?
	
	//load instance list by key
	public CampOldList<T> loadAll() throws SQLException; 
	
	//delete instance by id
	public int delete(int instanceId) ;
	
	//delete multiple instances by id list
	public int delete(int... instanceIds);
	
	//delete instance (list) by key
	public int delete(String key); 
	
	//delete multiple instances by key list
	public int delete(String... key); 
}
