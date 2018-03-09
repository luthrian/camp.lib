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

public class Message implements Serialization<Message>{
	
	public static enum MessageType {
		OrderProcessMessage,
		OrderOrderProcessMessage, //TODO
		ProductionOrderProcessMessage, //TODO
		ProductOrderProcessMessage, //TODO
		AttributeOrderProcessMessage, //TODO
		ModelOrderProcessMessage, //TODO
		OrderPositionProcessMessage, 
		ProductProcessMessage,
		AttributeProcessMessage, //TODO
		ModelProcessMessage, //TODO
		ProductionProcessMessage, //TODO
		CustomerProcessMessage; //TODO
	};
	protected String messageName;
	protected String businessKey;
	protected String tenantId;
	protected boolean withoutTenant = true;
	protected String processInstanceId;
	protected Variables correlationKeys = new Variables();
	protected Variables localCorrelationKeys = new Variables();
	protected Variables processVariables = new Variables();
	protected boolean all = false;
	protected boolean resultEnabled = false;

	public Message(String messageName, String businessKey, String tenantId, String processInstanceId) {
		this.messageName = messageName;
		this.businessKey = businessKey;
		this.tenantId = tenantId;
		this.processInstanceId = processInstanceId;
	}
	public Message(String messageName, String businessKey, String processInstanceId) {
		this.messageName = messageName;
		this.businessKey = businessKey;
		this.processInstanceId = processInstanceId;
	}
	public Message(String messageName, String businessKey) {
		this.messageName = messageName;
		this.businessKey = businessKey;
	}
	
	public String messageName() {
		return this.messageName;
	}
	
	public String businessKey() {
		return this.businessKey;
	}
	
	public String processInstanceId() {
		return this.processInstanceId;
	}
	
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	public String tenantId() {
		return this.tenantId;
	}
	
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public boolean withoutTenant() {
		return withoutTenant;
	}
	
	public void withoutTenant(boolean truth) {
		this.withoutTenant = truth;
	}

	public boolean all() {
		return all;
	}
	
	public void all(boolean truth) {
		this.all = truth;
	}

	public boolean resultEnabled() {
		return resultEnabled;
	}
	
	public void resultEnabled(boolean truth) {
		this.resultEnabled = truth;
	}
	
	public Variables correlationKeys(){
		return this.correlationKeys;
	}
	public void setCorrelationKeys(Variables correlationKeys) {
		this.correlationKeys = correlationKeys;
	}

	public Variables localCorrelationKeys(){
		return this.localCorrelationKeys;
	}
	public void setLocalCorrelationKeys(Variables localCorrelationKeys) {
		this.localCorrelationKeys = localCorrelationKeys;
	}
	
	public Variables processVariables(){
		return this.processVariables;
	}
	public void setProcessVariables(Variables processVariables) {
		this.processVariables = processVariables;
	}
	
	@Override
	public String toJson() {
		return _toJson(this);
	}
	public static String _toJson(Message m) {
		String json = "{";
		json += "\"messageName\":\""+m.messageName()+"\",";
		json += "\"businessKey\":\""+m.businessKey()+"\",";
		json += "\"tenantId\":\""+m.tenantId()+"\",";
		json += "\"withoutTenant\":"+m.withoutTenant()+",";
		json += "\"processInstanceId\":\""+m.processInstanceId()+"\",";
		json += "\"correlationKeys\":\""+m.correlationKeys()+"\",";
		json += "\"localCorrelationKeys\":\""+m.localCorrelationKeys()+"\",";
		json += "\"processVariables\":\""+m.processVariables()+"\",";
		json += "\"all\":"+m.all()+",";
		json += "\"resultEnabled\":"+m.resultEnabled()+",";
		json += "}";
		return json;
	}
	@Override
	public Message fromJson(String json) {
		return _fromJson(json);
	}
	
	public static Message _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static Message _fromJSONObject(JSONObject jo) {
		String messageName = jo.getString("messageName");
		String businessKey = jo.getString("businessKey");
		String tenantId = jo.getString("tenantId");
		boolean withoutTenant = jo.getBoolean("withoutTenant");
		String processInstanceId = jo.getString("processInstanceId");
		Variables correlationKeys = Variables._fromJSONObject(jo.getJSONObject("correlationKeys"));
		Variables localCorrelationKeys = Variables._fromJSONObject(jo.getJSONObject("localCorrelationKeys"));
		Variables processVariables = Variables._fromJSONObject(jo.getJSONObject("processVariables"));
		boolean all = jo.getBoolean("all");
		boolean resultEnabled = jo.getBoolean("resultEnabled");
		Message m = new Message(messageName, businessKey, tenantId, processInstanceId);
		m.withoutTenant(withoutTenant);
		m.all(all);
		m.resultEnabled(resultEnabled);
		m.setCorrelationKeys(correlationKeys);
		m.setLocalCorrelationKeys(localCorrelationKeys);
		m.setProcessVariables(processVariables);
		return m;
	}

}
