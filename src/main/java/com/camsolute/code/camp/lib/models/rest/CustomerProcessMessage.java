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


import com.camsolute.code.camp.lib.models.customer.Customer;
import com.camsolute.code.camp.lib.models.order.Order;
import com.camsolute.code.camp.lib.models.process.CustomerProcess;
import com.camsolute.code.camp.lib.models.process.OrderProcess;
import com.camsolute.code.camp.lib.models.rest.VariableValue.VariableValueType;

public class CustomerProcessMessage extends Message{
	//TODO: add correct messages
	public static enum CustomerMessage {
		cpm_customer_created, cpm_customer_new, cpm_customer_id_unverified, cpm_customer_id_verified, cpm_customer_credit_unverified,cpm_customer_credit_verified,
		cpm_customer_active,cpm_customer_deactiviated
		;
	}
	public static final CustomerMessage CUSTOMER_CREATED = CustomerMessage.cpm_customer_created;
	public static final CustomerMessage CUSTOMER_NEW = CustomerMessage.cpm_customer_new;
	public static final CustomerMessage CUSTOMER_ID_UNVERIFIED = CustomerMessage.cpm_customer_id_unverified;
	public static final CustomerMessage CUSTOMER_ID_VERIFIED = CustomerMessage.cpm_customer_id_verified;
	public static final CustomerMessage CUSTOMER_CREDIT_UNVERIFIED = CustomerMessage.cpm_customer_credit_unverified;
	public static final CustomerMessage CUSTOMER_CREDIT_VERIFIED = CustomerMessage.cpm_customer_credit_verified;
	public static final CustomerMessage CUSTOMER_ACTIVE = CustomerMessage.cpm_customer_active;
	public static final CustomerMessage CUSTOMER_DEACTIVIATED = CustomerMessage.cpm_customer_deactiviated;
	
	public CustomerProcessMessage(CustomerMessage messageName, Customer c) {
		super(messageName.name(),c.businessKey());
		CustomerProcess p = (CustomerProcess) c.processInstances().get(0);
		this.tenantId = p.tenantId();
		this.processInstanceId = p.instanceId();
		this.correlationKeys.variables().put("objectBusinessId", new VariableValue(c.businessId(),VariableValueType.valueOf("String")));
		this.correlationKeys.variables().put("objectId", new VariableValue(String.valueOf(c.id()),VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectBusinessId", new VariableValue(c.businessId(),VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectId", new VariableValue(String.valueOf(c.id()),VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectStatus",new VariableValue(c.status().name(), VariableValueType.valueOf("String")));
		
	}
	
	public CustomerProcessMessage(CustomerMessage messageName, String customerStatus, String customerBusinessId, int customerId, String businessKey) {
		super(messageName.name(),businessKey);
		this.correlationKeys.variables().put("objectBusinessId", new VariableValue(customerBusinessId,VariableValueType.valueOf("String")));
		this.correlationKeys.variables().put("objectId", new VariableValue(String.valueOf(customerId),VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectBusinessId", new VariableValue(customerBusinessId,VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectId", new VariableValue(String.valueOf(customerId),VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectStatus",new VariableValue(customerStatus, VariableValueType.valueOf("String")));
	}
	
	public CustomerMessage message() {
		return CustomerMessage.valueOf(this.messageName);
	}
	
}
