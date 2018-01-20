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
package com.camsolute.code.camp.lib.contract;

public interface IsProcess<T> {
	public static final String DEFAULT_CUSTOMER_ORDER_PROCESS_KEY = "com.camsolute.code.camp.models.business.OrderProcess";
	public static final String DEFAULT_CUSTOMER_ORDER_PROCESS_NAME = "customer_order_process";
	
	public static final String DEFAULT_PRODUCTION_PROCESS_KEY = "com.camsolute.code.camp.models.business.ProductionProcess";
	public static final String DEFAULT_PRODUCTION_PROCESS_NAME = "production_process";
	
	public static final String DEFAULT_ORDER_MANAGEMENT_PROCESS_KEY = "com.camsolute.code.camp.models.business.ManagementProcess";
	public static final String DEFAULT_ORDER_MANAGEMENT_PROCESS_NAME = "customer_order_management_process";

	public static final String DEFAULT_PRODUCT_PROCESS_KEY = "com.camsolute.code.camp.models.business.ProductProcess";
	public static final String DEFAULT_PRODUCT_PROCESS_NAME = "product_process";
	
	public static final String DEFAULT_CUSTOMER_MANAGEMENT_PROCESS_KEY = "com.camsolute.code.camp.models.business.CustomerManagementProcess";
	public static final String DEFAULT_CUSTOMER_MANAGEMENT_PROCESS_NAME = "customer_management_process";

	public String instanceId();
	
	public String businessKey();
	
	public String processName();
	
	public String definitionId();
	
	public String tenantId();
	
	public String caseInstanceId();
	
	public boolean ended();
	public void end(boolean end);
	
	public boolean suspended();
	public void suspend(boolean suspend);
}
