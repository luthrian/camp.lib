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

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiConsumer;

import com.camsolute.code.camp.lib.utilities.Util;
import com.camsolute.code.camp.lib.models.order.Order;
import com.camsolute.code.camp.lib.models.process.Process.ProcessType;
import com.camsolute.code.camp.lib.models.rest.OrderProcessMessage;

//public class OrderProcess extends Process<Order,OrderProcess> implements OrderProcessInterface  {
public class OrderProcess extends Process<Order> implements OrderProcessInterface  {
	
	 public static enum OrderAction {
	    CREATE_ORDER(Order.Status.CREATED,null),
	    SUBMIT_ORDER(Order.Status.SUBMITTED,Util.Config.instance().properties().getProperty("process.name.Order.Customer")),
	    REJECT_ORDER(Order.Status.REJECTED,Util.Config.instance().properties().getProperty("process.name.Order.Customer.ManagementProcess")),
	    RELEASE_ORDER(Order.Status.PRODUCTION,Util.Config.instance().properties().getProperty("process.name.Order.Customer.ManagementProcess")),
	    UPDATE_ORDER(Order.Status.UPDATED,Util.Config.instance().properties().getProperty("process.name.Order.Customer")),
	    CANCEL_ORDER(Order.Status.CANCELLED,Util.Config.instance().properties().getProperty("process.name.Order.Customer")),
	    ACKNOWLEDGE_UPDATE(Order.Status.PRODUCTION,Util.Config.instance().properties().getProperty("process.name.Production")),
	    SHIP_ORDER(Order.Status.SHIPPING,Util.Config.instance().properties().getProperty("process.name.Production")),
	    ACKNOWLEDGE_DELIVERY(Order.Status.FULFILLED,Util.Config.instance().properties().getProperty("process.name.Order.Customer"));
	  	
		 private final String process;
		 private Order.Status currentStatus;
		 private ArrayList<OrderAction> nextAllowed;
	  	
		 OrderAction(Order.Status status, String process){
	  		this.currentStatus = status;
	  		this.process = process;
	  	}
	  	public void initRules(Order order) {
	  		this.nextAllowed = new ArrayList<OrderAction>();
	  		switch((Order.Status)order.status()) {
	  		case CREATED:
	  			nextAllowed.add(SUBMIT_ORDER);
	  			break;
	  		case SUBMITTED:
	  			nextAllowed.add(REJECT_ORDER);
	  			nextAllowed.add(RELEASE_ORDER);
	  			break;
	  		case REJECTED:
	  			nextAllowed.add(SUBMIT_ORDER);
	  			break;
	  		case UPDATED:
	  			nextAllowed.add(ACKNOWLEDGE_UPDATE);
	  			nextAllowed.add(CANCEL_ORDER);
	  			break;
	  		case CANCELLED:
	  			break;
	  		case PRODUCTION:
	  			nextAllowed.add(UPDATE_ORDER);
	  			nextAllowed.add(SHIP_ORDER);
	  			nextAllowed.add(CANCEL_ORDER);
	  			break;
	  		case SHIPPING:
	  			nextAllowed.add(ACKNOWLEDGE_DELIVERY);
	  			break;
	  		case FULFILLED:
	  			break;
	  		default:
	  			break;
	  		}
	  	}
	  	
	  	public String process() {
	  		return process;
	  	}
	  	
	  	public Order.Status status() {
	  		return this.currentStatus;
	  	}
	  	public ArrayList<OrderAction> nextAllowed() {
	  		return nextAllowed;
	  	}

	  	public Process<?> process(Order order) {
	  		this.initRules(order);
	  		for(Process<?> p:order.processes()){
	  			if(p.processName().equals(process())){
	  				return p;
	  			}
	  		}
	  		return null;
	  	}
	  }

	public OrderProcess(String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type) {
		super(instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
	}

	public OrderProcess(String executionId, String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type) {
		super(executionId ,instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
	}

	public OrderProcess(int id, String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type) {
		super(id, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
	}

	public OrderProcess(int id, String executionId, String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType type) {
		super(id, executionId, instanceId, businessKey, processName, definitionId, tenantId, caseInstanceId, ended, suspended, type);
	}
	
}
