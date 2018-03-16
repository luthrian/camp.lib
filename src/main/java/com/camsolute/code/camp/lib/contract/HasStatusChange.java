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

import com.camsolute.code.camp.lib.models.CampStatesInterface.PersistType;

/**
 * Objects that honor this contract encapsulate status change aspects of a business object.<br>
 * The status change aspect indicates if a relevant process status aspect of the business object
 * has changed and if the system has taken the appropriate action required by such change.<br>
 * The status change aspect is expressed as true/false value.
 * If the value of a business object's status change aspect is true then a change has
 * occurred and the system has not yet acted on the change.<br>
 * A false value indicates that system has taken action after a change or that no relevant
 * change has occurred.<br>
 * All changes to a business object's data that must be persisted and communicated to
 * connected business processes are considered relevant. These changes must be reflected in
 * the process status aspect of the object and must therefore be reflected by the status change aspect.<br>
 * The action the System takes must include persisting all relevant business object aspects
 * which have changed and informing all connected business processes that a change to that
 * specific business object has occurred.<br>
 * After the system performs these action the status change aspects value must be expressed as false.
 *
 * @author Christopher Campbell
 */
public interface HasStatusChange {
	public boolean isModified();
	public void modify();
	public void modify(PersistType todo);
	void setModified(boolean t);
}
