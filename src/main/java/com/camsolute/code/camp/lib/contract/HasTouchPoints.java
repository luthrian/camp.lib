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

import com.camsolute.code.camp.lib.models.customer.TouchPoint;
import com.camsolute.code.camp.lib.models.customer.TouchPointList;

public interface HasTouchPoints {
	public void setTouchPointId(int id);
	public int touchPointId();
	public TouchPointList touchPoints();
	public void setTouchPoints(TouchPointList touchPoints);
	public boolean addTouchPoint(TouchPoint touchPoint);
	public TouchPoint removeTouchPoint(TouchPoint touchPoint);
	public TouchPoint removeTouchPoint(int touchPointId);
}
