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

import com.camsolute.code.camp.lib.contract.HasStates;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.CampStatesInterface.PersistType;

/**
 * This object encapsulates the transient i/o status related aspects of a business object.
 * The i/o related status aspects are relevant to business object persistence
 * and display.
 * The term transient indicates that these object aspects are never persisted and are 
 * generated at runtime during active life-cycle periods of an object. 
 * Active life-cycle periods of an object denote those time periods in which a runtime
 * representation of the object (object instance) exists within the system.
 * Inactive life-cycle periods denote those time periods in which only a persisted 
 * representation of current (and past) object states exist.
 *
 * @author Christopher Campbell
 */
public class CampStates implements CampStatesInterface {

	private IOAction lastIO = IOAction.NONE;

	private PersistType todo = PersistType.SAVE;
	
	protected boolean dirty = false;

	private boolean modified = false;

	public void update(CampStates states) {
  	if(states.isDeleted())ioAction(CampStatesInterface.IOAction.DELETE);
  	if(states.isLoaded())ioAction(CampStatesInterface.IOAction.LOAD);
  	if(states.isSaved())ioAction(CampStatesInterface.IOAction.SAVE);
  	if(states.isUpdated())ioAction(CampStatesInterface.IOAction.UPDATE);
  	if(states.isNew())ioAction(CampStatesInterface.IOAction.NONE);
  	if(states.isDirty())dirty();
  	setModified(states.isModified());
	}
	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public void dirty() {
		dirty = true;
	}

	@Override
	public void persistType(PersistType todo) {
		this.todo = todo;
	}

	@Override
	public void ioAction(IOAction action) {
		lastIO = action;
	}

	@Override
	public boolean isLoaded() {
		return lastIO.equals(IOAction.LOAD);
	}

	@Override
	public boolean isSaved() {
		return lastIO.equals(IOAction.SAVE);
	}

	@Override
	public boolean isUpdated() {
		return lastIO.equals(IOAction.UPDATE);
	}

	@Override
	public boolean isDeleted() {
		return lastIO.equals(IOAction.DELETE);
	}

	@Override
	public boolean isNew() {
		return this.lastIO.equals(IOAction.NONE);
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public boolean saveRequired() {
		return todo.name().equals(PersistType.SAVE.name());
	}

	@Override
	public boolean updateRequired() {
		return todo.name().equals(PersistType.UPDATE.name());
	}
	
	@Override
	public void modify() {
		modified = true;
	}

	@Override
	public void modify(PersistType todo) {
		modified = true;
		this.todo = todo;
	}


	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		if(!modified){
			this.todo = PersistType.UPDATE;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String print() {
		return CampStatesInterface._toJson(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return CampStatesInterface._toJson(this);
	}

	@Override
	public CampStates fromJson(String json) {
		return CampStatesInterface._fromJson(json);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return CampStatesInterface._toJson(this);
	}

	@Override
	public boolean readyToSave() {
		return (isModified() || isNew());
	}
	
  public <T extends IsObjectInstance> boolean updateBeforeSave(T instance) {
    return (isLoaded() || (!isNew() && (isModified() || instance.history().isFirst())));
  }

  public <T extends IsObjectInstance> boolean notReadyToSave(T instance) {
    return (isLoaded() || (!instance.history().isCurrent() && (!isModified() || !isNew())));
  }



}
