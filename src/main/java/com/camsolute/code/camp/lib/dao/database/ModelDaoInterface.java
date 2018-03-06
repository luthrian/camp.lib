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
package com.camsolute.code.camp.lib.dao.database;

import java.sql.SQLException;
import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.Model;
import com.camsolute.code.camp.lib.models.ModelList;

public interface ModelDaoInterface<Model> {
	
	/**
	 * The <code>save</code> functionality persists an object instance to the database.
	 * Only a current business object instance with a relevant change in its process status aspect or a
   * newly created business object instance will be persisted.
	 * 
	 * @param instance - the object instance being saved
	 * @return the instance with updated technical id (== database entry id)
	 * @throws SQLException  sql exception
	 */
	public Model save(Model instance) throws SQLException;
	
	/**
	 * The <code>saveList</code> functionality persists a list of business object instances to the database.
	 * Only a current business object instance with a relevant change in its process status aspect or a
   * newly created business object instance will be persisted.
	 * 
	 * @param instanceList list of objects to save
	 * @return a list of instances with updated technical id value (== database entry id)
	 * @throws SQLException sql exception
	 */
	public ModelList saveList(ModelList instanceList) throws SQLException; 

	//update an instance
	public Model update(Model instance) throws SQLException;
	
	//update a list of instances
	public ModelList updateList(ModelList instanceList); 

	//load instance by id
	public Model loadById(int instanceId) throws SQLException; 
	
	//load instance list by key
	public ModelList  loadListByKey(String[] key); //TODO:?
	
	//load instance list by key
	public ModelList  loadAll() throws SQLException; 
	
	//delete instance by id
	public int deleteById(int instanceId) ;
	
	//delete multiple instances by id list
	public int deleteListById(int[] instanceIds);
	
	//delete instance (list) by key
	public int deleteByKey(String key); 
	
	//delete multiple instances by key list
	public int deleteListByKey(String[] key); 
	
    //delete multiple instances by id list
    public int delete(Model instanceIds);
	
    //delete multiple instances by id list
    public int deleteList(ModelList instanceIds);
}
