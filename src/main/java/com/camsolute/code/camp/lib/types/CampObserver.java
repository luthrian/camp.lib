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
package com.camsolute.code.camp.lib.types;

import com.camsolute.code.camp.core.interfaces.IsObserver;

public abstract class CampObserver<T> implements IsObserver<T>{
	
	private int id = 0;
	
	@Override
	public int id() {
		return this.id;
	}

	@Override
	public int updateId(int id) {
		int oldid = this.id;
		this.id = id;
		return oldid;
	}

	@Override
	public abstract void notify(T observer);

	@Override
	public abstract void notify(T observer, Enum<?> event);

}
