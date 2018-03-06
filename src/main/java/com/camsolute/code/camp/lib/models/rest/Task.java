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
package com.camsolute.code.camp.lib.models.rest;

import java.io.Serializable;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class Task implements Serializable, Serialization<Task> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7713524941660849666L;
	
	public static enum DelegationState {
		RESOLVED,PENDING;
	}
    private String id;
    //The tasks name.
    private String name;
    //The user assigned to this task.
    private String assignee;
    //The owner of the task.
    private String owner;
    //The time the task was created. Format yyyy-MM-dd'T'HH:mm:ss.
    private String created;
    //The due date for the task. Format yyyy-MM-dd'T'HH:mm:ss.
    private String due;
    //The follow-up date for the task. Format yyyy-MM-dd'T'HH:mm:ss.
    private String followUp;
    //Corresponds to the DelegationState enum in the engine. Possible values are RESOLVED and PENDING.
    private String delegationState;
    //The task description.
    private String description;
    //The id of the execution the task belongs to.
    private String executionId;
    //The id of the parent task, if this task is a subtask.
    private String parentTaskId;
    //The priority of the task.
    private int priority;
    //The id of the process definition this task belongs to.
    private String processDefinitionId;
    //The id of the process instance this task belongs to.
    private String processInstanceId;
    //The task case execution id
    private String caseExecutionId;
    //The task case definition id
    private String caseDefinitionId;
    //The task case instance id
    private String caseInstanceId;
    //The task definition key.
    private String taskDefinitionKey;
    //The id of the tenant the task belongs to.
    private String tenantId;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public String getAssignee()
    {
        return assignee;
    }

    public void setAssignee(String assignee)
    {
        this.assignee = assignee;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public String getFollowUp() {
        return followUp;
    }

    public void setFollowUp(String followUp) {
        this.followUp = followUp;
    }

    public String getDelegationState() {
        return delegationState;
    }

    public void setDelegationState(String delegationState) {
        this.delegationState = delegationState;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }
       
    public String getExecutionId()
    {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }
	
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
	
    @Override
	public String toString() {
        return "Activiti Task: ID=" + getId() + " ; Name=" + getName() + " ; Description=" + getDescription() + 
               " ; Priority=" + getPriority() + " ; Assignee=" + getAssignee() + " ; Execution ID=" + getExecutionId() + 
               " ; Task Definition Key=" + getTaskDefinitionKey();
    }

    public String getCaseExecutionId() {
        return caseExecutionId;
    }

    public void setCaseExecutionId(String caseExecutionId) {
        this.caseExecutionId = caseExecutionId;
    }

    public String getCaseInstanceId() {
        return caseInstanceId;
    }

    public void setCaseInstanceId(String caseInstanceId) {
        this.caseInstanceId = caseInstanceId;
    }

    public String getCaseDefinitionId() {
        return caseDefinitionId;
    }

    public void setCaseDefinitionId(String caseDefinitionId) {
        this.caseDefinitionId = caseDefinitionId;
    }

		@Override
		public String toJson() {
			return _toJson(this);
		}
		public static String _toJson(Task t) {
			String json = "{";
	    json += "\"id\":\""+t.getId()+"\",";
	    json += "\"name\":\""+t.getName()+"\",";
	    json += "\"assignee\":\""+t.getAssignee()+"\",";
	    json += "\"owner\":\""+t.getOwner()+"\",";
	    json += "\"created\":\""+t.getCreated()+"\",";
	    json += "\"due\":\""+t.getDue()+"\",";
	    json += "\"followUp\":\""+t.getFollowUp()+"\",";
	    json += "\"delegationState\":\""+t.getDelegationState()+"\",";
	    json += "\"description\":\""+t.getDescription()+"\",";
	    json += "\"executionId\":\""+t.getExecutionId()+"\",";
	    json += "\"parentTaskId\":\""+t.getParentTaskId()+"\",";
	    json += "\"priority\":"+t.getPriority()+",";
	    json += "\"processDefinitionId\":\""+t.getProcessDefinitionId()+"\",";
	    json += "\"processInstanceId\":\""+t.getProcessInstanceId()+"\",";
	    json += "\"caseExecutionId\":\""+t.getCaseExecutionId()+"\",";
	    json += "\"caseDefinitionId\":\""+t.getCaseDefinitionId()+"\",";
	    json += "\"caseInstanceId\":\""+t.getCaseInstanceId()+"\",";
	    json += "\"taskDefinitionKey\":\""+t.getTaskDefinitionKey()+"\",";
	    json += "\"tenantId\":\""+t.getTenantId()+"\",";
	    
	    json += "}";
			return json;
		}

		@Override
		public Task fromJson(String json) {
			return _fromJson(json);
		}
		public static Task _fromJson(String json) {
			return _fromJSONObject(new JSONObject(json));
		}
		public static Task _fromJSONObject(JSONObject jo) {
			Task t = new Task();
	    String id = jo.getString("id");
	    t.setId(id);
	    String name = jo.getString("name");
	    t.setName(name);
	    String assignee = jo.getString("assignee");
	    t.setAssignee(assignee);
	    String owner = jo.getString("owner");
	    t.setOwner(owner);
	    String created = jo.getString("created");
	    t.setCreated(created);
	    String due = jo.getString("due");
	    t.setDue(due);
	    String followUp = jo.getString("followUp");
	    t.setFollowUp(followUp);
	    String delegationState = jo.getString("delegationState");
	    t.setDelegationState(delegationState);
	    String description = jo.getString("description");
	    t.setDescription(description);
	    String executionId = jo.getString("executionId");
	    t.setExecutionId(executionId);
	    String parentTaskId = jo.getString("parentTaskId");
	    t.setParentTaskId(parentTaskId);
	    int priority = jo.getInt("priority");
	    t.setPriority(priority);
	    String processDefinitionId = jo.getString("processDefinitionId");
	    t.setProcessDefinitionId(processDefinitionId);
	    String processInstanceId = jo.getString("processInstanceId");
	    t.setProcessInstanceId(processInstanceId);
	    String caseExecutionId = jo.getString("caseExecutionId");
	    t.setCaseExecutionId(caseExecutionId);
	    String caseDefinitionId = jo.getString("caseDefinitionId");
	    t.setCaseDefinitionId(caseDefinitionId);
	    String caseInstanceId = jo.getString("caseInstanceId");
	    t.setCaseInstanceId(caseInstanceId);
	    String taskDefinitionKey = jo.getString("taskDefinitionKey");
	    t.setTaskDefinitionKey(taskDefinitionKey);
	    String tenantId = jo.getString("tenantId");
	    t.setTenantId(tenantId);
			return t;
		}
}
