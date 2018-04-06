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

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasProcess;
import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.models.rest.VariableValue.VariableValueType;
import com.camsolute.code.camp.lib.utilities.Util;

public class Request<T extends HasProcess<T>> implements Serialization<Request<?>> {
	private static final Logger LOG = LogManager.getLogger(Request.class);
	private static String fmt = "[%15s] [%s]";
	
	//the principal is the business entity that initiated/is initiating the process which manages the request initiator object 
	public static enum Principal {
		Order, 
		Production,
		Product,
		Customer,
		Attribute,
		Model
	};
	
	private String id;
	private String key;
	
	private Variables  variables = new Variables();
	private String businessKey;
	
	// TODO: this may need to be relocated 
	public static enum RequestType {
		START_BASE_PROCESS,
		START_PRINCIPAL_PROCESS,
		START_MANAGEMENT_PROCESS,
		START_SUPPORT_PROCESS,
		MANAGE_BASE_PROCESS,
		MANAGE_PRINCIPAL_PROCESS,
		MANAGE_MANGEMENT_PROCESS,
		MANAGE_SUPPORT_PROCESS
	};
	
	public Request() {
	}
	public Request(T o, Principal principal,RequestType type) {
		this.businessKey = o.businessKey();
		this.initVariables(o, principal);
		initKey(o,principal,type);
	}
	
	protected String initKey(T o, Principal principal,RequestType type) {
		switch(type) {
		case START_BASE_PROCESS:
			setKey(Util.Config.instance().properties().getProperty("process.name."+o.getClass().getSimpleName()));
			break;
		case START_PRINCIPAL_PROCESS:
			setKey(Util.Config.instance().properties().getProperty("process.name."+o.getClass().getSimpleName()+"."+principal.name()));
			break;
		case START_SUPPORT_PROCESS:
			setKey(Util.Config.instance().properties().getProperty("process.name."+o.getClass().getSimpleName()+"."+((principal==null)?"":principal.name())+"."+"SupportProcess"));
			break;
		case START_MANAGEMENT_PROCESS:
			setKey(Util.Config.instance().properties().getProperty("process.name."+o.getClass().getSimpleName()+"."+((principal==null)?"":principal.name())+"."+"ManagementProcess"));
			break;
		default:
			setKey(Util.Config.instance().properties().getProperty("process.name."+o.getClass().getSimpleName()+"."+((principal==null)?"":principal.name())));
			break;
		}
		return key();
	}
	
	protected void initVariables(T o,  Principal principal) {
		updateId(String.valueOf(o.id()));
		variables().put("objectId", new VariableValue(String.valueOf(o.id()),VariableValueType.valueOf("String"),false));
		if(!Util._IN_PRODUCTION){String msg = "----[adding variable objectId("+o.id()+")]----";LOG.info(String.format(fmt, "initVariables",msg));}
		variables().put("objectBusinessId", new VariableValue(o.onlyBusinessId(),VariableValueType.valueOf("String"),false));
		if(!Util._IN_PRODUCTION){String msg = "----[adding variable objectBusinessId("+o.onlyBusinessId()+")]----";LOG.info(String.format(fmt, "initVariables",msg));}
		variables().put("objectStatus", new VariableValue(o.status().name(),VariableValueType.valueOf("String"),false));
		if(!Util._IN_PRODUCTION){String msg = "----[adding variable objectStatus("+o.status().name()+")]----";LOG.info(String.format(fmt, "initVariables",msg));}
		variables().put("objectType", new VariableValue(o.getClass().getName(),VariableValueType.valueOf("String"),false));
		if(!Util._IN_PRODUCTION){String msg = "----[adding variable objectType("+o.getClass().getName()+")]----";LOG.info(String.format(fmt, "initVariables",msg));}
		variables().put("objectPrincipal", new VariableValue(principal.name(),VariableValueType.valueOf("String"),false));
		if(!Util._IN_PRODUCTION){String msg = "----[adding variable objectPrincipal("+principal.name()+")]----";LOG.info(String.format(fmt, "initVariables",msg));}
	}
	
	public Variables vars() {
		return variables;
	}

	public HashMap<String,VariableValue> variables() {
		return variables.variables();
	}

	public void setVariables(HashMap<String,VariableValue> variables) {
		this.variables.setVariables(variables);
	}
	
	public void setVar(Variables variables) {
		this.variables = variables;
	}

	public String businessKey() {
		return businessKey;
	}

	public void setBusinesskey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String id() {
		return id;
	}

	public String updateId(String id) {
		return this.id = id;
	}

	public String key() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public String toJson() {
		return _toJson(this);
	}
	public static String _toJson(Request<?> r) {
		String json = "{";
		json += _toInnerJson(r);
		json += "}";
		return json;
	}
	public static String _toInnerJson(Request<?> r) {
		String json = "\"id\":\""+r.id()+"\",";
		json += "\"key\":\""+r.key()+"\",";
		json += "\"businessKey\":\""+r.businessKey()+"\",";
		json += "\"variables\":"+r.vars().toJson();
		return json;
	}
 	public Request<T> fromJson(String json) {
		return _fromJson(json);
	}
	
	public static <T extends HasProcess<T>> Request<T> _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	public static <T extends HasProcess<T>> Request<T> _fromJSONObject(JSONObject jo) {
		Request<T> o = new Request<T>();
		String id = jo.getString("id");
		String key = jo.getString("key");
		String businessKey = jo.getString("businessKey");
		Variables variables = Variables._fromJSONObject(jo.getJSONObject("variables"));
		o.setVar(variables);
		o.setKey(key);
		o.setBusinesskey(businessKey);
		o.updateId(id);
		return o;
	}
}
