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
