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


import com.camsolute.code.camp.lib.models.process.ProductProcess;
import com.camsolute.code.camp.lib.models.product.Product;
import com.camsolute.code.camp.lib.models.rest.VariableValue.VariableValueType;


public class ProductProcessMessage extends Message{

	public static enum ProductMessage {
		productideation, 
		productproposed, 
		productgovernance, 
		productrejected, 
		productaccepted, 
		productbudgeted, 
		productanalysis, 
		productdesign, 
		productproduction,
		productmarketing,
		productreleased, 
		productshipping, 
		productcancelled, 
		productrecalled, 
		productendoflife,
		productsoldout,
		productnoinventory,
		productinventoryok;
	}
	
	public ProductProcessMessage(ProductMessage message, Product pd) {
		super(message.name(),pd.processInstances().get(0).businessKey());
		ProductProcess p = (ProductProcess) pd.processInstances().get(0);
		this.tenantId = p.tenantId();
		this.processInstanceId = p.instanceId();
		this.correlationKeys.variables().put("objectId", new VariableValue(String.valueOf(pd.id()), VariableValueType.valueOf("Integer"),false));
		this.processVariables.variables().put("objectStatus",new VariableValue(pd.status().name(), VariableValueType.valueOf("String"),false));
	}
	
	public ProductProcessMessage(ProductMessage messageName, String status, String productName, int productId, String businessKey) {
		super(messageName.name(),businessKey);
		this.correlationKeys.variables().put("objectBusinessId", new VariableValue(productName,VariableValueType.valueOf("String")));
		this.correlationKeys.variables().put("objectId", new VariableValue(String.valueOf(productId),VariableValueType.valueOf("Integer")));
		this.processVariables.variables().put("objectBusinessId", new VariableValue(productName,VariableValueType.valueOf("String")));
		this.processVariables.variables().put("objectId", new VariableValue(String.valueOf(productId),VariableValueType.valueOf("Integer")));
		this.processVariables.variables().put("objectStatus",new VariableValue(status, VariableValueType.valueOf("String")));
	}

	public ProductMessage message() {
		return ProductMessage.valueOf(this.messageName);
	}
	
}
