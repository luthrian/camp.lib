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
package com.camsolute.code.camp.lib.contract;

import com.camsolute.code.camp.lib.models.process.Process.ProcessType;

public interface IsProcess<T> {
	public static final String DEFAULT_CUSTOMER_ORDER_PROCESS_KEY = "com.camsolute.code.camp.lib.models.OrderProcess";
	public static final String DEFAULT_CUSTOMER_ORDER_PROCESS_NAME = ProcessType.customer_order_process.name();
	
	public static final String DEFAULT_PRODUCTION_ORDER_PROCESS_KEY = "com.camsolute.code.camp.lib.models.OrderProcess";
	public static final String DEFAULT_PRODUCTION_ORDER_PROCESS_NAME = ProcessType.production_order_process.name();
	
	public static final String DEFAULT_PRODUCT_ORDER_PROCESS_KEY = "com.camsolute.code.camp.lib.models.OrderProcess";
	public static final String DEFAULT_PRODUCT_ORDER_PROCESS_NAME = ProcessType.production_order_process.name();
	
	public static final String DEFAULT_ORDER_MANAGEMENT_PROCESS_KEY = "com.camsolute.code.camp.lib.models.OrderProcess";
	public static final String DEFAULT_ORDER_MANAGEMENT_PROCESS_NAME = ProcessType.customer_order_management_process.name();

	public static final String DEFAULT_PRODUCTION_PROCESS_KEY = "com.camsolute.code.camp.lib.models.ProductionProcess";
	public static final String DEFAULT_PRODUCTION_PROCESS_NAME = ProcessType.production_process.name();
	
	public static final String DEFAULT_PRODUCT_PROCESS_KEY = "com.camsolute.code.camp.lib.models.ProductProcess";
	public static final String DEFAULT_PRODUCT_PROCESS_NAME = ProcessType.product_process.name();
	
	public static final String DEFAULT_CUSTOMER_MANAGEMENT_PROCESS_KEY = "com.camsolute.code.camp.lib.models.CustomerProcess";
	public static final String DEFAULT_CUSTOMER_MANAGEMENT_PROCESS_NAME = ProcessType.customer_management_process.name();

	public static final String DEFAULT_MANAGEMENT_PROCESS_KEY = "com.camsolute.code.camp.lib.models.ManagementProcess";
	public static final String DEFAULT_MANAGEMENT_PROCESS_NAME = ProcessType.order_support_process.name();

	public static String _defaultProcessName(ProcessType type) {
		switch(type){
		case customer_process:
		case customer_management_process:
			return DEFAULT_CUSTOMER_MANAGEMENT_PROCESS_NAME;
		case customer_order_process:
		case customer_order_management_process:
		case production_order_process:
		case production_order_management_process:
		case product_order_process:
		case product_order_management_process:
		case order_support_process:			
			return DEFAULT_ORDER_MANAGEMENT_PROCESS_NAME;
		case product_process:
		case product_management_process:
			return DEFAULT_PRODUCT_PROCESS_NAME;
		case production_process:
		case production_management_process:
			return DEFAULT_PRODUCTION_PROCESS_NAME;
		default:
			break;
		}
		return DEFAULT_MANAGEMENT_PROCESS_NAME;
	}

	public static String _defaultBusinessKey(ProcessType type) {
		switch(type){
		case customer_process:
		case customer_management_process:
			return DEFAULT_CUSTOMER_MANAGEMENT_PROCESS_KEY;
		case customer_order_process:
		case customer_order_management_process:
		case production_order_process:
		case production_order_management_process:
		case product_order_process:
		case product_order_management_process:
		case order_support_process:			
			return DEFAULT_ORDER_MANAGEMENT_PROCESS_KEY;
		case product_process:
		case product_management_process:
			return DEFAULT_PRODUCT_PROCESS_KEY;
		case production_process:
		case production_management_process:
			return DEFAULT_PRODUCTION_PROCESS_KEY;
		default:
			break;
		}
		return DEFAULT_MANAGEMENT_PROCESS_KEY;
	}
	
	public String instanceId();
	
	public String businessKey();
	
	public String processName();
	
	public String definitionId();
	
	public String tenantId();
	
	public String caseInstanceId();
	
	public boolean ended();
	public void ended(boolean end);
	
	public boolean suspended();
	public void suspended(boolean suspend);
}
