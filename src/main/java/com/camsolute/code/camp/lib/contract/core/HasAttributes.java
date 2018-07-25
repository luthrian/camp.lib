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

import com.camsolute.code.camp.lib.contract.core.CampList.AttributeList;

/**
 * Objects that honer this contract can have descriptive attribute aspects to which they grant
 * access in response to the <code>attributes()</code> request. An attribute aspect is encapsulated
 * in an <code>Attribute</code> object and can be simple or complex in nature. Simple attributes
 * hold a single value whereas complex attributes aggregate several attributes together.
 *
 * @author Christopher Campbell
 *
 */
public interface HasAttributes {
	public AttributeList attributes();
	public void attributes(AttributeList attributes);
}
