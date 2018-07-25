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
package com.camsolute.code.camp.lib.models.process;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasBusinessId;
import com.camsolute.code.camp.lib.contract.HasId;
import com.camsolute.code.camp.lib.contract.HasStates;
import com.camsolute.code.camp.lib.contract.IsObserver;
import com.camsolute.code.camp.lib.contract.IsProcess;
import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.contract.core.Value;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface;
import com.camsolute.code.camp.lib.models.process.Process.ProcessType;
import com.camsolute.code.camp.lib.utilities.Util;

//public interface ProcessInterface<T, U extends Process<T,U>>  extends IsProcess<T>, IsObserver<T>, HasId, HasStates, HasBusinessId, Serialization<U>{
public interface ProcessInterface<T>  extends IsProcess<T>, IsObserver<T>, HasId, HasStates, HasBusinessId, Serialization<Process<T>>{
	public static final Logger LOG = LogManager.getLogger(ProcessInterface.class);
	public static String fmt = "[%15s] [%s]";
	
//	public static <U extends Process<?,U>> String _toJson(Process<?,U> p) {
	public static String _toJson(Process<?> p) {
		String json = "{";
		json += _toInnerJson(p);
		json += "}";
		return json;
	}

	public static String _toInnerJson(Process<?> p) {
		String json = "";
		json += "\"id\":"+p.id()+",";
		json += "\"executionId\":\""+p.executionId()+"\",";
		json += "\"instanceId\":\""+p.instanceId()+"\",";
		json += "\"businessKey\":\""+p.businessKey()+"\","; 
		json += "\"businessId\":\""+p.onlyBusinessId()+"\","; 
		json += "\"processName\":\""+p.processName()+"\",";
		json += "\"definitionId\":\""+p.definitionId()+"\",";
		json += "\"tenantId\":\""+p.tenantId()+"\",";
		json += "\"caseInstanceId\":\""+p.caseInstanceId()+"\",";
		json += "\"ended\":"+p.ended()+",";
		json += "\"suspended\":"+p.suspended()+",";
		json += "\"type\":\""+p.type().name()+"\",";
		json += "\"states\":"+p.states().toJson();
		return json;
	}
	public static Process<?> _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static <U extends Value<?,?>> Process<?> _fromJSONObject(JSONObject jo) {
		int id = 0;
		if(jo.has("id")) {
			if(!Util._IN_PRODUCTION){String msg = "----[jo.get.id("+jo.get("id")+")]----";LOG.info(String.format(fmt, "_fromJSONObject",msg));}
			id = jo.getInt("id");
		}
		String executionId = null;
		if(jo.has("executionId") ) executionId = jo.getString("executionId");
		String instanceId = jo.getString("instanceId");
		String businessKey = jo.getString("businessKey"); 
		String businessId = jo.getString("businessId"); 
		String processName = jo.getString("processName");
		String definitionId = jo.getString("definitionId");
		String tenantId = jo.getString("tenantId");
		String caseInstanceId = jo.getString("caseInstanceId");
		boolean ended = jo.getBoolean("ended");
		boolean suspended = jo.getBoolean("suspended");
		CampStates states = CampStatesInterface._fromJSONObject(jo.getJSONObject("states"));
		ProcessType type = ProcessType.valueOf(ProcessType.class,jo.getString("type"));
		switch(type){//TODO add the other process types
		case customer_process:
		case customer_management_process:
			CustomerProcess cp = new CustomerProcess(id, executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			cp.setBusinessId(businessId);
			cp.states().update(states);
			return cp;
		case customer_order_process:
		case customer_order_management_process:
		case product_order_process:
		case product_order_management_process:
		case order_support_process:			
			OrderProcess op = new OrderProcess(id, executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			op.setBusinessId(businessId);
			op.states().update(states);
			return op;
		case product_process:
		case product_management_process:
			ProductProcess pdp = new ProductProcess(id, executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			pdp.setBusinessId(businessId);
			pdp.states().update(states);
			return pdp;
		case production_process:
		case production_management_process:
			ProductionProcess pp = new ProductionProcess(id, executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			pp.setBusinessId(businessId);
			pp.states().update(states);
			return pp;
		case product_attribute_order_process:
		case product_attribute_process:
		case product_attribute_order_management_process:
		case product_attribute_support_process:
			ProductAttributeProcess<U> pap = new ProductAttributeProcess<U>(id, executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			pap.setBusinessId(businessId);
			pap.states().update(states);
			return pap;
		default:
			break;
		}
		return null;
	
	}
	
	public static <U extends Value<?,?>> Process<?> _process(String businessId, String executionId, String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type){
		switch(type){//TODO add the other process types
		case customer_process:
		case customer_management_process:
			CustomerProcess cp = new CustomerProcess(executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			cp.setBusinessId(businessId);
			return cp;
		case customer_order_process:
		case customer_order_management_process:
		case product_order_process:
		case product_order_management_process:
		case order_support_process:			
			OrderProcess op = new OrderProcess(executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			op.setBusinessId(businessId);
			return op;
		case product_process:
		case product_management_process:
			ProductProcess pdp = new ProductProcess(executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			pdp.setBusinessId(businessId);
			return pdp;
		case production_process:
		case production_management_process:
			ProductionProcess pp = new ProductionProcess(executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			pp.setBusinessId(businessId);
			return pp;
		case product_attribute_order_process:
		case product_attribute_process:
		case product_attribute_order_management_process:
		case product_attribute_support_process:
			ProductAttributeProcess<U> pap = new ProductAttributeProcess<U>(executionId,instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			pap.setBusinessId(businessId);
			return pap;
		default:
			break;
		}
		return null;
	}
	public static <U extends Value<?,?>> Process<?> _process(String businessId, String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type){
		switch(type){//TODO add the other process types
		case customer_process:
		case customer_management_process:
			CustomerProcess cp = new CustomerProcess(instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			cp.setBusinessId(businessId);
			return cp;
		case customer_order_process:
		case customer_order_management_process:
		case product_order_process:
		case product_order_management_process:
		case order_support_process:			
			OrderProcess op = new OrderProcess(instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			op.setBusinessId(businessId);
			return op;
		case product_process:
		case product_management_process:
			ProductProcess pdp = new ProductProcess(instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			pdp.setBusinessId(businessId);
			return pdp;
		case production_process:
		case production_management_process:
			ProductionProcess pp = new ProductionProcess(instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			pp.setBusinessId(businessId);
			return pp;
		case product_attribute_order_process:
		case product_attribute_process:
		case product_attribute_order_management_process:
		case product_attribute_support_process:
			ProductAttributeProcess<U> pap = new ProductAttributeProcess<U>(instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			pap.setBusinessId(businessId);
			return pap;
		default:
			break;
		}
		return null;
	}
}
