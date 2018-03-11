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
package com.camsolute.code.camp.lib.models.product;

import com.camsolute.code.camp.lib.dao.DaoInterface;
import com.camsolute.code.camp.lib.dao.HasModelReference;
import com.camsolute.code.camp.lib.dao.HasProcessReference;
import com.camsolute.code.camp.lib.models.AttributeMap;

public interface ProductRestInterface extends DaoInterface<Product>, HasModelReference, HasProcessReference<Product> {

	public Product create(String businessId, String businessKey, String date, String endOfLife, String group, String version, boolean log);
	
	public AttributeMap saveAttributes(int objectId, AttributeMap am, boolean log);

	public AttributeMap updateAttributes(int objectId, AttributeMap am, boolean log);

	public AttributeMap loadAttributes(int objectId, boolean log);

}
