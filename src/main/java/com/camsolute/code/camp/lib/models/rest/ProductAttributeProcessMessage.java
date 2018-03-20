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


import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.Value;
import com.camsolute.code.camp.lib.models.process.ProductAttributeProcess;
import com.camsolute.code.camp.lib.models.rest.VariableValue.VariableValueType;


public class ProductAttributeProcessMessage extends Message{

	public static enum ProductAttributeMessage {
		papm_order_created,
		papm_order_submitted,
		papm_order_updated,
		papm_order_rejected,
		papm_order_released,
		papm_order_production,
		papm_order_shipped,
		papm_order_fulfilled;
	}
	/**
	 * Instantiates a ProductAttributeMessage to be sent to the process engine via rest service call.
	 * @param message The {@link ProductAttributeMessage} enumeration name value to be sent to the process.
	 * @param a The {@link com.camsolute.code.camp.lib.models.Attribute} object type instance being managed by the process.
	 * @param attributeBusinessKey The business entity identifier aspect currently responsible for managing the Attribute object type instance.
	 */
	public ProductAttributeProcessMessage(ProductAttributeMessage message, Attribute<? extends Value<?>> a, String attributeBusinessKey) {
		super(message.name(),a.processInstances().get(0).businessKey());
		ProductAttributeProcess<?> p = (ProductAttributeProcess<?>) a.processInstances().get(0);
		this.tenantId = p.tenantId();
		this.processInstanceId = p.instanceId();
		this.correlationKeys.variables().put("objectId", new VariableValue(String.valueOf(a.attributeId()), VariableValueType.Integer,false));
		this.correlationKeys.variables().put("objectBusinessId", new VariableValue(a.businessId(),VariableValueType.valueOf("String")));
		this.correlationKeys.variables().put("objectBusinessKey", new VariableValue(attributeBusinessKey,VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectStatus",new VariableValue(a.status().name(), VariableValueType.String,false));
		this.processVariables.variables().put("objectBusinessKey", new VariableValue(a.attributeBusinessKey(),VariableValueType.valueOf("String")));
	}
	
	public ProductAttributeProcessMessage(ProductAttributeMessage messageName, String status, String attributeBusinessId, int attributeId, String attributeBusinessKey, String businessKey) {
		super(messageName.name(),businessKey);
		this.correlationKeys.variables().put("objectBusinessId", new VariableValue(attributeBusinessId,VariableValueType.String));
		this.correlationKeys.variables().put("objectId", new VariableValue(String.valueOf(attributeId),VariableValueType.Integer));
		this.processVariables.variables().put("objectBusinessId", new VariableValue(attributeBusinessId,VariableValueType.String));
		this.processVariables.variables().put("objectId", new VariableValue(String.valueOf(attributeId),VariableValueType.Integer));
		this.processVariables.variables().put("objectStatus",new VariableValue(status, VariableValueType.String));
	}

	public ProductAttributeMessage message() {
		return ProductAttributeMessage.valueOf(this.messageName);
	}
	
}
