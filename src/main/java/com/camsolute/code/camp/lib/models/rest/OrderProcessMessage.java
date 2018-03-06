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


import com.camsolute.code.camp.lib.models.order.Order;
import com.camsolute.code.camp.lib.models.process.OrderProcess;
import com.camsolute.code.camp.lib.models.rest.VariableValue.VariableValueType;

public class OrderProcessMessage extends Message{

	public static enum CustomerOrderMessage {
		cmp_orderrejected, opp_ordershipped, cop_ordercancelled, cop_orderupdated, cop_ordersubmitted,cmp_orderreleased
	}
	public static final CustomerOrderMessage ORDER_REJECTED_MSG = CustomerOrderMessage.cmp_orderrejected;
	public static final CustomerOrderMessage ORDER_SHIPPED_MSG = CustomerOrderMessage.opp_ordershipped;
	public static final CustomerOrderMessage ORDER_CANCELLED_MSG = CustomerOrderMessage.cop_ordercancelled;
	public static final CustomerOrderMessage ORDER_UPDATED_MSG = CustomerOrderMessage.cop_orderupdated;
	public static final CustomerOrderMessage ORDER_SUBMITTED_MSG = CustomerOrderMessage.cop_ordersubmitted;
	public static final CustomerOrderMessage ORDER_RELEASED_MSG = CustomerOrderMessage.cmp_orderreleased;
	
	public OrderProcessMessage(CustomerOrderMessage messageName, Order o) {
		super(messageName.name(),o.businessKey());
		OrderProcess p = (OrderProcess) o.processInstances().get(0);
		this.tenantId = p.tenantId();
		this.processInstanceId = p.instanceId();
		this.correlationKeys.variables().put("objectBusinessId", new VariableValue(o.businessId(),VariableValueType.valueOf("String")));
		this.correlationKeys.variables().put("objectId", new VariableValue(String.valueOf(o.id()),VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectBusinessId", new VariableValue(o.businessId(),VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectId", new VariableValue(String.valueOf(o.id()),VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectStatus",new VariableValue(o.status().name(), VariableValueType.valueOf("String")));
		
	}
	
	public OrderProcessMessage(CustomerOrderMessage messageName, String orderStatus, String orderNumber, int orderId, String businessKey) {
		super(messageName.name(),businessKey);
		this.correlationKeys.variables().put("objectBusinessId", new VariableValue(orderNumber,VariableValueType.valueOf("String")));
		this.correlationKeys.variables().put("objectId", new VariableValue(String.valueOf(orderId),VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectBusinessId", new VariableValue(orderNumber,VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectId", new VariableValue(String.valueOf(orderId),VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectStatus",new VariableValue(orderStatus, VariableValueType.valueOf("String")));
	}
	
	public CustomerOrderMessage message() {
		return CustomerOrderMessage.valueOf(this.messageName);
	}
	
}
