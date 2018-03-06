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

import java.sql.Timestamp;

import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Version;

public class OrderProduct extends Product {

	public OrderProduct(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public OrderProduct(String name, String businessKey, Group group, Version version, Timestamp date) {
		super(name, businessKey, group, version, date);
		// TODO Auto-generated constructor stub
	}

	public OrderProduct(int id, String name, String businessKey, Group group, Version version, Timestamp date) {
		super(id, name, businessKey, group, version, date);
		// TODO Auto-generated constructor stub
	}

}
