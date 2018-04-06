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

import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.utilities.Util;

//public class Process<T,U extends Process<T,U>> implements ProcessInterface<T,U>{
public class Process<T> implements ProcessInterface<T>{

	public static final String DEFAULT_CUSTOMER_PROCESS_KEY = "process.name.Order.Customer";
	public static final String DEFAULT_PRODUCTION_PROCESS_KEY = "process.name.Production";
	public static final String DEFAULT_MANAGEMENT_PROCESS_KEY = "process.name.Order.Customer.ManagementProcess";

	public static enum ProcessType {
		order_process,
		customer_order_process,
		order_order_process,
		production_order_process,
		product_order_process,
		product_attribute_order_process,
		product_model_order_process,
		production_process,
		product_process,
		product_attribute_process,
		product_model_process,
		customer_process,
		customer_management_process,
		product_management_process,
		production_management_process,
		order_management_process,
		customer_order_management_process,
		product_order_management_process,
		product_attribute_order_management_process,
		product_model_order_management_process,
		production_order_management_process,
		order_support_process,
		customer_support_process,
		product_support_process,
		product_attribute_support_process,
		production_support_process
		;
	};

	private int id = Util.NEW_ID;
	private String executionId;
	private String instanceId;
	private String businessId;
	private String businessKey; 
	private String processName;
	private String definitionId;
	private String tenantId;
	private String caseInstanceId;
	private boolean ended = false;
	private boolean suspended = false;
	private ProcessType type;
	
	private CampStates states = new CampStates();
	
	public Process(String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type) {
	this.instanceId = instanceId;
	this.businessKey = businessKey; 
	this.processName = processName;
	this.definitionId = definitionId;
	this.tenantId = tenantId;
	this.caseInstanceId = caseInstanceId;
	this.ended = ended;
	this.suspended = suspended;
	this.type = type;
	}

	public Process(String executionId,String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type) {
		this.executionId = executionId;
		this.instanceId = instanceId;
		this.businessKey = businessKey; 
		this.processName = processName;
		this.definitionId = definitionId;
		this.tenantId = tenantId;
		this.caseInstanceId = caseInstanceId;
		this.ended = ended;
		this.suspended = suspended;
		this.type = type;
	}

	public Process(int id, String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type) {
	this.id = id;
	this.instanceId = instanceId;
	this.businessKey = businessKey; 
	this.processName = processName;
	this.definitionId = definitionId;
	this.tenantId = tenantId;
	this.caseInstanceId = caseInstanceId;
	this.ended = ended;
	this.suspended = suspended;
	this.type = type;
	}


	public Process(int id, String executionId,String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type) {
		this.id = id;
		this.executionId = executionId;
		this.instanceId = instanceId;
		this.businessKey = businessKey; 
		this.processName = processName;
		this.definitionId = definitionId;
		this.tenantId = tenantId;
		this.caseInstanceId = caseInstanceId;
		this.ended = ended;
		this.suspended = suspended;
		this.type = type;
	}

	@Override
	public int id() {
		return this.id;
	}
	
	@Override
	public int updateId(int id) {
		int prev = this.id;
		this.id = id;
		return prev;
	}
	
	public ProcessType type() {
		return type;
	}
	
	public void type(ProcessType type) {
		this.type = type;
	}
	@Override
	public CampStates states() {
		return this.states;
	}

	public String executionId() {
		return this.executionId;
	}
	
	public void executionId(String executionId) {
		this.executionId = executionId;
	}
	
	public String instanceId() {
		return this.instanceId;
	}
	
	public void instanceId(String id) {
		this.instanceId = id;
	}
	
	public String businessKey() {
		return this.businessKey;
	}
	
	public void businessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	
	public String processName() {
		return this.processName;
	}
	
	public void processName(String processName) {
		this.processName = processName;
	}
	
	public String definitionId() {
		return this.definitionId;
	}
	
	public void definitionId(String definitionId) {
		this.definitionId = definitionId;
	}
	
	public String tenantId() {
		return this.tenantId;
	}
	
	public void tenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public String caseInstanceId() {
		return this.caseInstanceId;
	}
	
	public void caseInstanceId(String caseInstanceId) {
		this.caseInstanceId = caseInstanceId;
	}
	
	public boolean ended() {
		return this.ended;
	}
	
	public void ended(boolean end) {
		this.ended = end;
	}
	
	public boolean suspended() {
		return this.suspended;
	}
	
	public void suspended(boolean susp) {
		this.suspended = susp;
	}
	
	@Override
	public void notify(T observed) {
		//TODO: REST call to process 
	}

	@Override
	public void notify(T observed, Enum<?> event) {
		//TODO:  REST call to process
	}


	@Override
	public String toJson() {
//		return ProcessInterface._toJson( (Process<?, U>) this);
		return ProcessInterface._toJson( (Process<T>) this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Process<T> fromJson(String json) {
		return (Process<T>) ProcessInterface._fromJson(json);
	}
	
  @Override
  public String businessId() {
    return this.businessId + Util.DB._NS + this.id;
  }

  @Override
  public String updateBusinessId(String businessId) {
    String prev = this.businessId;
    this.businessId = businessId;
    this.states().modify();
    return prev;
  }
  
  public void setBusinessId(String businessId) {
  	this.businessId = businessId;
  }

  @Override
  public String onlyBusinessId() {
    return this.businessId;
  }

  @Override
  public String initialBusinessId() {
    return this.businessId + Util.DB._NS + Util.NEW_ID; // initial value id = Util.NEW_ID
  }

}
