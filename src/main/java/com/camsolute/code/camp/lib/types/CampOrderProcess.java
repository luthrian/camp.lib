/*******************************************************************************
 * Copyright (C) 2017 Christopher Campbell (campbellccc@gmail.com)
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
 * 	Christopher Campbell (campbellccc@gmail.com) - all code prior and post initial release
 ******************************************************************************/
package com.camsolute.code.camp.lib.types;

import com.camsolute.code.camp.core.dao.DBU;
import com.camsolute.code.camp.lib.CampType;
import com.camsolute.code.camp.models.business.Order;
import com.camsolute.code.camp.models.business.Process;
import com.camsolute.code.camp.models.business.ProductAttribute.Type;

public class CampOrderProcess extends CampType<Process<Order>>{
//TODO
	
	public static final String DEFAULT_CUSTOMER_PROCESS_KEY = "com.camsolute.code.camp.models.business.OrderProcess";
	public static final String DEFAULT_PRODUCTION_PROCESS_KEY = "com.camsolute.code.camp.models.business.ProductionProcess";
	public static final String DEFAULT_MANAGEMENT_PROCESS_KEY = "com.camsolute.code.camp.models.business.ManagementProcess";


	private Process<Order> process;
	
	private String attributeGroup = null;
	private int attributePosition = 0;
	
	private String attributeBusinessId = null;

	@Override
	public String attributeBusinessId() {
		return attributeBusinessId+DBU._NS+id();
	}

	@Override
	public String attrbuteBusinessId(String id) {
		String prev = this.attributeBusinessId;
		this.attributeBusinessId = id;
		return prev;
	}

	@Override
	public String onlyAttributeBusinessId() {
		return this.attributeBusinessId;
	}

	@Override
	public String initialAttributeBusinessId() {
		return attributeBusinessId+DBU._NS+0;
	}

	public CampOrderProcess(String instanceId, String businessKey, String processName,String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended) {
		super(processName, AttributeType._process, null);
		this.process = new Process<Order>(instanceId, businessKey, processName,definitionId,tenantId,caseInstanceId, ended, suspended);
	}

	public CampOrderProcess(int id,String instanceId, String businessKey, String processName,String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended) {
		super(processName, AttributeType._process, null);
		this.process = new Process<Order>(id,instanceId, businessKey, processName,definitionId,tenantId,caseInstanceId, ended, suspended);
	}
	public CampOrderProcess(String processName) {
		super(processName, AttributeType._process, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Process<Order> me() {
		// TODO Auto-generated method stub
		return this.process;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Process<Order> me(Process<Order> value) {
		Process<Order> prev = this.process;
		this.process = value;
		return prev;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String attributeGroup() {
		return this.attributeGroup;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String attributeGroup(String group) {
		String prev = this.attributeGroup;
		this.attributeGroup = group;
		return prev;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int attributePosition() {
		return this.attributePosition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int attributePosition(int position) {
		int prev = this.attributePosition;
		this.attributePosition = position;
		return prev;
	} 


	@Override
	public CampType<?> valueFromString(String value) {
		// TODO Auto-generated method stub
		return null;
	}

}
