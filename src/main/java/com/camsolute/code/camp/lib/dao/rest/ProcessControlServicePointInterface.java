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

	public String startProcess(String processKey, String request);
	
	public void messageProcesses(String messageType, String messageName, String object);
	
	public void messageProcess(String processInstanceId, String messageType, String messageName, String object);
	
	public void messageProcess(String processInstanceId, String messageName, String businessKey, String objectStatus, String objectBusinessId, int objectId);
	
	public void triggerMessageEvent(String processInstanceId, String businessKey, String messageName, String variables);
	
	public void signalProcessList(String signalPacket);
	
	public void signalProcess(String processInstanceId, String businessKey, String variables);
	
	public void claimTask(String taskId, String userId);
	
	public void delegateTask(String taskId, String userId);
	
	public void completeTask(String taskId, String variables);
	
	public String getTask(String taskId);
	
	public String getTasks(String processInstanceId, String businessKey);
	
	public String getTasks(String processInstanceId);
}
