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
package com.camsolute.code.camp.lib.contract;

import static com.camsolute.code.camp.lib.contract.core.CampStates.IOAction;

import com.camsolute.code.camp.lib.models.CampStatesInterface.PersistType;

/**
 * Objects that honar this contract encapsulate i/o status related to object persistence. Upon request these objects
 * must provide the following information:<br>
 * -- if an object has ever been persisted<br>
 * -- what the last i/o operation was<br>
 *
 * @author Christopher Campbell
 */
public interface IsStorable {

    public void ioAction(IOAction action);

    public void persistType(PersistType todo);
    
    public boolean saveRequired();
    
    public boolean updateRequired();
    
    public boolean isLoaded();

    public boolean isSaved();

    public boolean isUpdated();

    public boolean isDeleted();

    public boolean isNew();

    public boolean readyToSave();
    
    public <T extends IsObjectInstance<T>> boolean updateBeforeSave(T instance);
    
    public <T extends IsObjectInstance<T>> boolean notReadyToSave(T instance);
}










