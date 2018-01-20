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

import com.camsolute.code.camp.lib.contract.HasId;
import com.camsolute.code.camp.lib.types.CampOldList;

public interface HasOneToOneReferenceDaoInterface<S extends HasId,T extends HasId> {
	
	//add reference to model instance
	public int addReference(S sourceInstance,T targetInstance) throws SQLException ;
	
	//add reference to model instance
	public int addReference(int sourceId,T targetInstance) throws SQLException ;
	
	//add reference to model instance
	public int addReference(int sourceId,int targetId) throws SQLException ;
	
	//load reference source with targets
	public S loadReference(S sourceInstance,int targetId) throws SQLException ; 

	//load reference source with targets by source instance id
	public S loadReference(int sourceInstanceId) throws SQLException ; 
	
	//load reference target by source instance id
	public T loadReferenceTarget(int sourceInstanceId) throws SQLException ; 
	
	//load list of all sources with targets 
	public CampOldList<S> loadReferenceList() throws SQLException ; 
	
	//delete reference to target by instances
	public int deleteReference(S sourceInstance,T targetInstance) throws SQLException ;
	
	//delete reference to target by instance id's
	public int deleteReference(int sourceId,int targetId) throws SQLException ;
	
	//delete reference(s) to target
	public int deleteReference(S sourceInstance) throws SQLException ; 
}
