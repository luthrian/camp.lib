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

import com.camsolute.code.camp.lib.models.order.OrderPosition;
import com.camsolute.code.camp.lib.models.product.Product;

/**
 * Objects that honar this contract represent an order position of an order. An order position
 * can be understood as an order item that references a product being ordered and can provide
 * information relevant to that order item; such as the quantity of the product being ordered
 * (in the case that the product is a tangible).
 *
 * @author Christopher Campbell
 *
 */
public interface  IsOrderPosition extends HasBusinessId, HasOrderBusinessId,HasOrder, HasBusinessKey, HasHistory, HasId, HasStatus, HasOrderProduct {

	public int quantity();
 
	public void updateQuantity(int quantity);
}
