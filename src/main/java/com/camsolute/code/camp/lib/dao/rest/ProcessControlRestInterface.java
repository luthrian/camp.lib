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

import com.camsolute.code.camp.lib.models.rest.Request.Principal;
import com.camsolute.code.camp.lib.models.rest.Task;
import com.camsolute.code.camp.lib.models.rest.TaskList;
import com.camsolute.code.camp.lib.models.rest.Variables;
import com.camsolute.code.camp.lib.contract.HasProcess;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessList;

public interface ProcessControlRestInterface {

	public <T extends HasProcess<T>> Process<?> startProcess(String processKey, T object, Principal principal, boolean log);
	
	public <T extends HasProcess<T>> void messageProcess(Enum<?> messageType, Enum<?> messageName, T object, boolean log);
	
	public <T extends HasProcess<T>> void messageProcess(String processInstanceId, Enum<?> messageType, Enum<?> messageName, T object, boolean log);
	
	public <T extends HasProcess<T>> void messageProcess(String processInstanceId, Enum<?> messageName, String businessKey, String objectStatus, String objectBusinessId, int objectId, boolean log);
	
	public void triggerMessageEvent(String processInstanceId, String businessKey, Enum<?> messageName, Variables variables, boolean log);
	
	public void signalProcess(Variables variables, ProcessList processList, boolean log);
	
	public void signalProcess(String processInstanceId, String businessKey, Variables variables, boolean log);
	
	public void claimTask(String taskId, String userId, boolean log);
	
	public void delegateTask(String taskId, String userId, boolean log);
	
	public <T extends HasProcess<?>> void completeTask(String processInstanceId, String principal, T object, boolean log);
	
	public void completeTask(String taskId, Variables variables, boolean log);
	
	public Task getTask(String taskId, boolean log);
	
	public TaskList getTasks(String processInstanceId, String businessKey, boolean log);
	
	public TaskList getTasks(String processInstanceId, boolean log);
}
