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
package com.camsolute.code.camp.lib.contract;

import java.util.ArrayList;
import com.camsolute.code.camp.core.rest.models.Task;
import com.camsolute.code.camp.core.rest.models.Variables;

public interface ProcessControlObjectInterface {
	
	
	// authenticate with process engine
	//TODO: not clear yet what we want ... perhaps basic auth over https 
	// signal a process observing bizObj
	public void signalProcess(String executionId,Variables variables);
	// delegate a process task to another user
	public void delegateTask(String taskId, String userId);
	// claim task (become assignee)
	public void claimTask(String taskId, String userId);
	// get task (become assignee)
	public Task getTask(String taskId);
	// complete a user task (generally)
	public void completeTask(String taskId, Variables variables);
	
	// get the TaskId of the current execution Task of a process.
	// TODO: check what happens with parallel task execution - now we only look at a sequential process flow
//	public <E extends Process<?>> ArrayList<Task> getTasks(E process);
	public ArrayList<Task> getTasks(String processInstanceId,String businessKey);
	
	public ArrayList<Task> getTasks(String processInstanceId);
}

