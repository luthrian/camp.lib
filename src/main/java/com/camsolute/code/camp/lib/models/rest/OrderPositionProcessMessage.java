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


import com.camsolute.code.camp.lib.models.order.OrderPosition;
import com.camsolute.code.camp.lib.models.process.OrderProcess;
import com.camsolute.code.camp.lib.models.rest.VariableValue.VariableValueType;

public class OrderPositionProcessMessage extends Message{

	public static enum OrderPositionMessage {
		cmp_orderrejected, 
		opp_ordershipped, 
		cop_ordercancelled, 
		cop_orderupdated, 
		cop_ordersubmitted,
		cmp_orderreleased,
		cop_orderfulfilled
	}
	public static final OrderPositionMessage ORDER_REJECTED_MSG = OrderPositionMessage.cmp_orderrejected;
	public static final OrderPositionMessage ORDER_SHIPPED_MSG = OrderPositionMessage.opp_ordershipped;
	public static final OrderPositionMessage ORDER_CANCELLED_MSG = OrderPositionMessage.cop_ordercancelled;
	public static final OrderPositionMessage ORDER_UPDATED_MSG = OrderPositionMessage.cop_orderupdated;
	public static final OrderPositionMessage ORDER_SUBMITTED_MSG = OrderPositionMessage.cop_ordersubmitted;
	public static final OrderPositionMessage ORDER_RELEASED_MSG = OrderPositionMessage.cmp_orderreleased;
	public static final OrderPositionMessage ORDER_FULFILLEd_MSG = OrderPositionMessage.cop_orderfulfilled;
	
	public OrderPositionProcessMessage(OrderPositionMessage messageName, OrderPosition o) {
		super(messageName.name(),o.businessKey());
		OrderProcess p = (OrderProcess) o.processInstances().get(0);
		this.tenantId = p.tenantId();
		this.processInstanceId = p.instanceId();
		this.correlationKeys.variables().put("objectBusinessId", new VariableValue(o.businessId(),VariableValueType.valueOf("String")));
		this.correlationKeys.variables().put("objectId", new VariableValue(String.valueOf(o.id()),VariableValueType.valueOf("Integer")));
		this.processVariables.variables().put("objectBusinessId", new VariableValue(o.businessId(),VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectId", new VariableValue(String.valueOf(o.id()),VariableValueType.valueOf("Integer")));
		this.processVariables.variables().put("objectStatus",new VariableValue(o.status().name(), VariableValueType.valueOf("String")));
		
	}
	
	public OrderPositionProcessMessage(OrderPositionMessage messageName, String objectStatus, String objectBusinessId, int objectId, String businessKey) {
		super(messageName.name(),businessKey);
		this.correlationKeys.variables().put("objectBusinessId", new VariableValue(objectBusinessId,VariableValueType.valueOf("String")));
		this.correlationKeys.variables().put("objectId", new VariableValue(String.valueOf(objectId),VariableValueType.valueOf("Integer")));
		this.processVariables.variables().put("objectBusinessId", new VariableValue(objectBusinessId,VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectId", new VariableValue(String.valueOf(objectId),VariableValueType.valueOf("Integer")));
		this.processVariables.variables().put("objectStatus",new VariableValue(objectStatus, VariableValueType.valueOf("String")));
	}
	
	public OrderPositionMessage message() {
		return OrderPositionMessage.valueOf(this.messageName);
	}
	
}
