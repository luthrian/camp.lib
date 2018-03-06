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
/**
 * ***************************************************************************** Copyright (C) 2017
 * Christopher Campbell (campbellccc@gmail.com)
 *
 * <p>This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * <p>You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 *
 * <p>Contributors: Christopher Campbell (campbellccc@gmail.com) - all code prior and post initial
 * release ****************************************************************************
 */
package com.camsolute.code.camp.lib.contract;

import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampStates;

/**
 * The has number interface indicates that the implementing element has an association with an order
 * or is and order. It therefore extends has history, has customer and has order number interfaces,
 * etc. interfaces.
 *
 * @author Christopher Campbell
 */
public interface IsValue<T> {
	
	/**
	 * Request the value aspect of a business object instance.
	 * A value aspect of a business object instance typically encapsulates the aspect which holds the highest 
	 * informational value (that aspect of central interest) to the business "End-User". 
	 * To better understand the role of a value aspect; If a business object instance is visually 
	 * displayed to the business end-user then the value aspect must clearly be displayed as the central
	 * value of interest.
	 * The business "End-User" is typically the business entity that consumes the service or product resulting from
	 * the main business process associated with the business object instance.
	 * @return value object
	 */
  public T value();

	/**
	 * Request to update the value of a business object instance. The value aspect of a business object instance must generally 
	 * available during the all active periods of its life-cycle. An update therefore requires that the value aspect be 
	 * persisted to storage to ensure this availability.
	 * An update does not necessarily require the notification of associated processes.
	 * @param newValue new value
	 * @return previous value
	 */
  public T updateValue(T newValue);

	/**
	 * ask to set the value of the attribute
	 * @param newValue new value
	 */
  public void setValue(T newValue);

}
