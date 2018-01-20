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
package com.camsolute.code.camp.lib.contract;

import com.camsolute.code.camp.models.business.Order;
import com.camsolute.code.camp.models.business.OrderProcess;
import com.camsolute.code.camp.models.business.Product;

/**
 * The has number interface indicates that the implementing element has
 * an association with an order or is and order. It therefore extends has history,
 * has customer and has order number interfaces, etc. interfaces.
 * @author Christopher Campbell
 * @param <T>
 *
 */
public interface  IsOrder extends HasBusinessId, HasCustomer, HasHistory, HasObserverProcess<OrderProcess>, HasProcess<OrderProcess>, HasId, HasStatus{
//public interface  IsOrder extends HasBusinessId, HasCustomer, HasHistory, HasObserverProcess<OrderProcess>, HasProcess<OrderProcess>, HasId, HasStatus, IsDisplayable, IsStorable, IsTimestamped,  UpdateInstanceInterface<Order> {
}
