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

import com.camsolute.code.camp.lib.models.product.Product;

public class ProductProcess extends Process<Product,ProductProcess> implements ProductProcessInterface{
	public ProductProcess(String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type) {
		super(instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
	}

	public ProductProcess(int id, String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type) {
		super(id, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
	}

	public ProductProcess(String executionId, String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type) {
		super(executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
	}

	public ProductProcess(int id, String executionId, String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type) {
		super(id, executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
	}

	@Override
	public void notify(Product observed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notify(Product observed, Enum<?> event) {
		// TODO Auto-generated method stub
		
	}
}
