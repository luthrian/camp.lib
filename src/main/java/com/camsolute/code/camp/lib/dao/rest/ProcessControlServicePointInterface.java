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
package com.camsolute.code.camp.lib.dao.rest;

public interface ProcessControlServicePointInterface {

	public String startProcess(String processKey, String object, boolean log);
	
	public void messageProcess(String messageType, String object, boolean log);
	
	public void messageProcess(String processInstanceId, String messageType, String object, boolean log);
	
	public void messageProcess(String processInstanceId, String messageType, String objectStatus, String objectBusinessId, int objectId, boolean log);
	
	public void triggerMessageEvent(String executionId, String messageType, String String, boolean log);
	
	public void signalProcessList(String variables, String signalPacket, boolean log);
	
	public void signalProcess(String execustionId, String variables, boolean log);
	
	public void claimTask(String taskId, String userId, boolean log);
	
	public void delegateTask(String taskId, String userId, boolean log);
	
	public void completeTask(String taskId, String variables, boolean log);
	
	public String getTask(String taskId, boolean log);
	
	public String getTasks(String processInstanceId, String businessKey, boolean log);
	
	public String getTasks(String processInstanceId, boolean log);
}
