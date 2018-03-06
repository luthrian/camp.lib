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

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class ProcessExecution implements Serialization<ProcessExecution> {
	private String id;
	private String processInstanceId;
	private String tenantId;
	private boolean ended;
	private boolean suspended;
	
	public ProcessExecution() {
	}
	
	public String id() {
		return id;
	}

	public String setId(String id) {
		return this.id = id;
	}

	public String processInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String tenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public boolean ended() {
		return ended;
	}
	
	public void setEnded(boolean b) {
		this.ended = b;
	}
	
	public boolean suspended() {
		return this.suspended;
	}
	
	public void setSuspended(boolean b) {
		this.suspended = b;
	}

	@Override
	public String toJson() {
		return null;
	}
	public static String _toJson(ProcessExecution p) {
		String json = "{";
		json += "\"id\":\""+p.id()+"\",";
		json += "\"processInstanceId\":\""+p.processInstanceId()+"\",";
		json += "\"tenantId\":\""+p.tenantId()+"\",";
		json += "\"ended\":"+p.ended()+",";
		json += "\"suspended\":"+p.suspended();
		json += "}";
		return json;
	}
	

	public ProcessExecution fromJson(String json) {
		return _fromJson(json);
	}
	
	public static ProcessExecution _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	public static ProcessExecution _fromJSONObject(JSONObject jo) {
		ProcessExecution p = new ProcessExecution();
		String id = jo.getString("id");
		String processInstanceId = jo.getString("processInstanceId");
		String tenantId = jo.getString("tenantId");
		boolean ended = jo.getBoolean("ended");
		boolean suspended = jo.getBoolean("suspended");		
		p.setId(id);
		p.setProcessInstanceId(processInstanceId);
		p.setTenantId(tenantId);
		p.setEnded(ended);
		p.setSuspended(suspended);
		return p;
	}

}
