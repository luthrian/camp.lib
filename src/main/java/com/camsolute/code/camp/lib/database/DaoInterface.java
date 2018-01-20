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


import com.camsolute.code.camp.core.interfaces.HasId;
import com.camsolute.code.camp.core.types.CampOldList;

public interface DaoInterface<T extends HasId> {

	public T _save(T p, boolean log);

	public CampOldList<T> _saveAll(CampOldList<T> pl, boolean log);	
	
	public T _update(T p, boolean log);
	
	public CampOldList<T> _update(CampOldList<T> pl, boolean log);

	
	public T _save(T p,String table,String[][] tabledef, boolean log);

	public CampOldList<T> _saveAll(CampOldList<T> pl,String table,String[][] tabledef, boolean log);	
	
	public T _update(T p,String table,String[][] tabledef, boolean log);
	
	public CampOldList<T> _update(CampOldList<T> pl,String table,String[][] tabledef,boolean log);

	
	public CampOldList<T> _loadUpdates(String businessKey,String target, boolean log);
	
	public CampOldList<T> _loadUpdatesByKey(String businessKey, boolean log);

	public CampOldList<T> _loadUpdatesByTarget(String target, boolean log);
	
	public T _loadUpdate(T p ,String businessKey,String target, boolean log);
	
	
	public int _addToUpdates(T p, boolean log);

	public int _addToUpdates(T p, String businessKey, String target, boolean log);

	public int _addToUpdates(T p,String target, boolean log);
	
	
	public int _deleteAllFromUpdates(String businessKey, String target, boolean log);
	
	public int _deleteFromUpdates(String instanceId, String businessKey, String target, boolean log);	
	
}
