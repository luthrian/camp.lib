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

import java.sql.Timestamp;

/**
 * Objects implementing the HasLifeCycle contract   interface indicates that the implementing element has
 * an association with an order or is and order. It therefore extends has history,
 * has customer and has order number interfaces, etc. interfaces.
 * @author Christopher Campbell
 *
 */
public interface  HasModelLifeCycle {
	

  public Timestamp releaseDate(); 

  public void updateReleaseDate(Timestamp date); 

  public Timestamp endOfLife(); 

  public void updateEndOfLife(Timestamp eol);
  
}
