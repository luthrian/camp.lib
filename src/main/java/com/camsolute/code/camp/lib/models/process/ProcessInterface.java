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

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasBusinessId;
import com.camsolute.code.camp.lib.contract.HasId;
import com.camsolute.code.camp.lib.contract.HasStates;
import com.camsolute.code.camp.lib.contract.IsObserver;
import com.camsolute.code.camp.lib.contract.IsProcess;
import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.models.process.Process.ProcessType;

public interface ProcessInterface<T, U extends Process<T,U>>  extends IsProcess<T>, IsObserver<T>, HasId, HasStates, HasBusinessId, Serialization<U>{

	public static <U extends Process<?,U>> String _toJson(Process<?,U> p) {
		String json = "{";
		json += "\"id\":"+p.id()+",";
		json += "\"executionId\":\""+p.executionId+",";
		json += "\"instanceId\":\""+p.instanceId+",";
		json += "\"businessKey\":\""+p.businessKey()+"\","; 
		json += "\"businessId\":\""+p.onlyBusinessId()+"\","; 
		json += "\"processName\":\""+p.processName()+"\",";
		json += "\"definitionId\":\""+p.definitionId()+"\",";
		json += "\"tenantId\":\""+p.tenantId()+"\",";
		json += "\"caseInstanceId\":\""+p.caseInstanceId()+"\",";
		json += "\"ended\":"+p.ended()+",";
		json += "\"suspended\":"+p.suspended()+",";
		json += "\"type\":"+p.type().name()+",";
		json += "}";
		return json;
	}

	public static <T extends Process<?,T>> T _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	@SuppressWarnings("unchecked")
	public static <U extends Process<?,U>> U _fromJSONObject(JSONObject jo) {
		int id = jo.getInt("id");
		String executionId = jo.getString("executionId");
		String instanceId = jo.getString("instanceId");
		String businessKey = jo.getString("businessKey"); 
		String businessId = jo.getString("businessId"); 
		String processName = jo.getString("processName");
		String definitionId = jo.getString("definitionId");
		String tenantId = jo.getString("tenantId");
		String caseInstanceId = jo.getString("caseInstanceId");
		boolean ended = jo.getBoolean("ended");
		boolean suspended = jo.getBoolean("suspended");
		ProcessType type = ProcessType.valueOf(ProcessType.class,jo.getString("type"));
		switch(type){//TODO add the other process types
		case customer_process:
		case customer_management_process:
			CustomerProcess cp = new CustomerProcess(id, executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			cp.setBusinessId(businessId);
			return (U) cp;
		case customer_order_process:
		case customer_order_management_process:
		case product_order_process:
		case product_order_management_process:
		case order_support_process:			
			OrderProcess op = new OrderProcess(id, executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			op.setBusinessId(businessId);
			return (U) op;
		case product_process:
		case product_management_process:
			ProductProcess pdp = new ProductProcess(id, executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			pdp.setBusinessId(businessId);
			return (U) pdp;
		case production_process:
		case production_management_process:
			ProductionProcess pp = new ProductionProcess(id, executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
			pp.setBusinessId(businessId);
			return (U) pp;
		default:
			break;
		}
		return null;
	
	}
	
	public static Process<?,?> _process(String businessId, String executionId, String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type){
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
		default:
			break;
		}
		return null;
	}
	public static Process<?,?> _process(String businessId, String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type){
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
		default:
			break;
		}
		return null;
	}
}
