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


public interface ProcessEngineServicePointInterface {

	public String authenticate(String username,String password, boolean log);
	
	public String startProcess(String processKey, String variables, boolean log);
	
	public void messageProcess(String message, boolean log);
	
	public void triggerMessageEvent(String executionId, String messageType, String variables, boolean log);
	
	public String getProcessExecutions(String processInstanceId, String businessKey, boolean log);
	
	public void signalProcess(String execustionId, String variables, boolean log);
	
	public void updateVariables(String instanceId, String businessKey, String variables, boolean log);
	
	public void claimTask(String taskId, String userId, boolean log);
	
	public void delegateTask(String taskId, String userId, boolean log);
	
	public void completeTask(String taskId, String variables, boolean log);
	
	public String getTask(String taskId, boolean log);
	
	public String getTasks(String processInstanceId, String businessKey, boolean log);
	
}
