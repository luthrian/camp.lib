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

import com.camsolute.code.camp.core.rest.models.Variables;
import com.camsolute.code.camp.core.rest.models.OrderProcessMessage.customerOrderMessage;
import com.camsolute.code.camp.models.business.OrderProcess;

public interface OrderProcessControlObjectInterface<T extends HasProcess<?>>  extends ProcessControlObjectInterface{
	
	// start a process registered with bizObj
	public OrderProcess startProcess(String processKey,T bizObj);
	// start all processes registered with bizObj 
//	public void startProcesses(T bizObj);
	// message observing bizObj processes
	public String messageProcess(customerOrderMessage message,T bizObj);
	// message observing bizObj processes
	public String messageProcess(String processInstanceId,customerOrderMessage message,T bizObj);
	// message observing bizObj processes
	public String messageProcess(String processInstanceId,customerOrderMessage messageName,String orderStatus, String orderNumber, int orderId, String businessKey);
	// message a process observing bizObj
	public void triggerMessageEvent(String executionId, customerOrderMessage messageName,Variables variables);
	// signal all processes observing bizObj
	public void signalProcesses(Variables variables,T bizObj);
	
}

