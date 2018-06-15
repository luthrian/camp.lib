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
package com.camsolute.code.camp.lib.contract.core;

/**
 * Objects that honar this contract encapsulate the process status aspect of a business object.
 * They must supply information about the object's status current and previous process status,
 * and allow changes to be made to the status upon request.
 * The process status aspect of a business object expresses itself in form of any status
 * the object can adopt that is relevant to business processes that handle object in anyway.
 *
 * @author Christopher Campbell
 */
public interface HasStatus {
    public CampStatus status();
}
