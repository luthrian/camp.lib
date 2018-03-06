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

public interface HasDefaultValue {
	/**
	 * Request the default value. 
	 * The default value is a JSON String that represents the value aspect of an object.
	 * The default value is used to initialize the value aspect of an object with a value if this is required.
	 * 
	 * @return JSON String representation of the Value object to be used as default value;
	 */
	public String defaultValue();
	/**
	 * Request to set the default value of an object instance that encapsulates a value aspect. 
	 * @param value the default value of an object instance that encapsulates a value aspect
	 * @return JSON String representation of the default value prior the request. This value is NULL if no default
	 * value was previously set.
	 */
	public String updateDefaultValue(String value);

	public void setDefaultValue(String value);

}
