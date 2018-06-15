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
package com.camsolute.code.camp.lib.models.order;

import java.sql.Timestamp;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.contract.persistance.PersistanceHandler;
import com.camsolute.code.camp.lib.contract.persistance.PersistanceHandler.OrderPersistanceHandler;

import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Version;
import com.camsolute.code.camp.lib.models.process.OrderProcess;
import com.camsolute.code.camp.lib.models.process.OrderProcessList;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.models.rest.Message;
import com.camsolute.code.camp.lib.models.rest.MessageList;
import com.camsolute.code.camp.lib.models.rest.OrderProcessMessage;
import com.camsolute.code.camp.lib.models.rest.OrderProcessMessage.CustomerOrderMessage;
import com.camsolute.code.camp.lib.models.rest.Request;
import com.camsolute.code.camp.lib.models.rest.Request.Principal;
import com.camsolute.code.camp.lib.models.rest.Request.RequestType;
import com.camsolute.code.camp.lib.models.process.Process.ProcessType;
import com.camsolute.code.camp.lib.utilities.Util;

public class Order implements OrderInterface {
    public static enum Status {
        CREATED,
        SUBMITTED,
        REJECTED,
        UPDATED,
        CANCELLED,
        PRODUCTION,
        SHIPPING,
        TRANSIT,
        FULFILLED,
        PAID,
        RECALLED,
        INTURNED,
        DELETED,
        CLEAN,
        MODIFIED,
        DIRTY;
    }
   
    public static enum UpdateAttribute {
    	STATUS,
    	BUSINESSKEY,
    	BY_DATE,
//    	GROUP,
//    	VERSION
    	;
    }
  	private int id= Util.NEW_ID;
  	
  	private String orderNumber;

  	private String businessKey;
  	
  	private Timestamp date;

  	private Timestamp byDate;

  	private Status status = Status.CREATED;
  	
  	private Status previousStatus = Status.CLEAN;

  	private ProcessList processInstances = new ProcessList();
  		
  	private OrderPositionList  orderPositions = new OrderPositionList();

  	private Group group =  new Group(Util.Config.instance().properties().getProperty("object.create.Order.group"));
  	
  	private Version version = new Version(Util.Config.instance().properties().getProperty("object.create.Order.version"));
  	
  	private CampStates states = new CampStates();
  	
  	private CampInstance history = new CampInstance();

  	private PersistanceHandler persistanceHandler = new OrderPersistanceHandler(this);
  	
   	public Order() {
  		this.orderNumber = "";
  		this.businessKey = Util.Config.instance().properties().getProperty("object.create.order.business.key");
  		this.date = Util.Time.timestamp();
  		this.byDate = Util.Time.timestamp(Util.Time.nowPlus(Util.Config.instance().defaultByDateDays("Order"), Util.Time.formatDateTime));
   		this.history.endOfLife(
   				Util.Time.timestamp(
     				Util.Time.nowPlus(
     						Util.Config.instance().defaultEndOfLifeDays("Order"),
     						Util.Time.formatDateTime)));
  	}

  	public Order(String orderNumber) {
  		this.orderNumber = orderNumber;
  		this.businessKey = Util.Config.instance().properties().getProperty("object.create.order.business.key");
  		this.date = Util.Time.timestamp();
  		this.byDate = Util.Time.timestamp(Util.Time.nowPlus(Util.Config.instance().defaultByDateDays("Order"), Util.Time.formatDateTime));
   		this.history.endOfLife(
   				Util.Time.timestamp(
     				Util.Time.nowPlus(
     						Util.Config.instance().defaultEndOfLifeDays("Order"),
     						Util.Time.formatDateTime)));
  	}
  	
  	public Order(String orderNumber, String businessKey) {
        this.orderNumber = orderNumber;
        this.businessKey = businessKey;
        this.date = Util.Time.timestamp();
        this.byDate = Util.Time.timestamp(Util.Time.nowPlus(Util.Config.instance().defaultByDateDays("Order"), Util.Time.formatDateTime));
     		this.history.endOfLife(
     				Util.Time.timestamp(
	     				Util.Time.nowPlus(
	     						Util.Config.instance().defaultEndOfLifeDays("Order"),
	     						Util.Time.formatDateTime)));
  	}
  	
  	public Order(String orderNumber, String businessKey, Timestamp byDate) {
        this.orderNumber = orderNumber;
        this.businessKey = businessKey;
        this.date = Util.Time.timestamp();
        this.byDate = byDate;
        if(this.byDate.after(
        		Util.Time.timestamp(
        				Util.Time.nowPlus(
        						Util.Config.instance().defaultByDateDays("Order"), 
        						Util.Time.formatDateTime)))) {
        	this.byDate = Util.Time.timestamp(
        			Util.Time.nowPlus(
        					Util.Config.instance().defaultByDateDays("Order"),
        					Util.Time.formatDateTime));
        }
     		this.history.endOfLife(
     				Util.Time.timestamp(
	     				Util.Time.nowPlus(
	     						Util.Config.instance().defaultEndOfLifeDays("Order"),
	     						Util.Time.formatDateTime)));
  	}

  	public Order(String orderNumber, String businessKey, String byDate) {
        this.orderNumber = orderNumber;
        this.businessKey = businessKey;
        this.date = Util.Time.timestamp();
        this.byDate = Util.Time.timestamp(byDate);
        if(this.byDate.after(Util.Time.timestamp(Util.Time.nowPlus(Util.Config.instance().defaultByDateDays("Order"), Util.Time.formatDateTime)))) {
        	this.byDate = Util.Time.timestamp(Util.Time.nowPlus(Util.Config.instance().defaultByDateDays("Order"), Util.Time.formatDateTime));
        }
     		this.history.endOfLife(
     				Util.Time.timestamp(
	     				Util.Time.nowPlus(
	     						Util.Config.instance().defaultEndOfLifeDays("Order"),
	     						Util.Time.formatDateTime)));
  	}

		@Override
		public String businessKey() {
			return this.businessKey;
		}

		@Override
		public ProcessList processInstances() {
			return processInstances;
		}

		@Override
		public void addProcess(Process<Order> process) {
			process.states().modify();
			processInstances.add(process);
			states.modify();
		}

		@Override
		public void addProcesses(ProcessList processes) {
			for(Process<?> p: processes) {
				p.states().modify();
			}
			processInstances.addAll(processes);
			this.states.modify();
		}

		@Override
		public Process<Order> deleteProcess(String instanceId) {
			int count = 0;
			for(Process<?> op: processInstances) {
				if(op.instanceId().equals(instanceId)){
					this.states.modify();
					return (OrderProcess) processInstances.remove(count);
				}
				count++;
			}
			return null;
		}

		@Override
		public void setProcesses(ProcessList pl) {
			this.processInstances = pl;
		}
		
		@Override
		public ProcessList processes() {
			return processInstances;
		}
		
		@Override
		public ProcessList processes(ProcessType type) {
			ProcessList opl = new OrderProcessList();
			for(Process<?> op: processInstances){
				if(op.type().name().equals(type.name())){
					opl.add((OrderProcess)op);
				}
			}
			return opl;
		}
		@SuppressWarnings("unchecked")
		@Override
		public void notifyProcesses() {
			for(Process<?> op:processInstances) {
				((Process<Order>)op).notify(this);
			}
		}
		@SuppressWarnings("unchecked")
		@Override
		public void notifyProcesses(ProcessType type) {
			for(Process<?> op:processInstances) {
				if(op.type().name().equals(type.name())){
					((Process<Order>)op).notify(this);
				}
			}
		}
		@SuppressWarnings("unchecked")
		@Override
		public void notifyProcesses(Enum<?> event) {
			for(Process<?> op: processInstances){
				((Process<Order>)op).notify(this, event);
			}
		}
		@SuppressWarnings("unchecked")
		@Override
		public void notifyProcesses(ProcessType type, Enum<?> event) {
			for(Process<?> op: processInstances){
				if(op.type().name().equals(type.name())){
					((Process<Order>)op).notify(this, event);
				}
			}
		}
		@Override
		public Enum<?> status() {
			return this.status;
		}
		@Override
		public Enum<?> updateStatus(Enum<?> status) {
			Status prev = this.status;
			this.status = (Status) status;
			this.states.modify();
			return prev;
		}
		@Override
		public Enum<?> updateStatus(String status) {
			Status prev = this.status;
			this.status = Enum.valueOf(Status.class, status);
			this.states.modify();
			return prev;
		}
		@Override
		public void setStatus(Enum<?> status) {
			this.status = (Status) status;
		}
		@Override
		public void setStatus(String status) {
			this.status	= Enum.valueOf(Status.class, status);
		}
		@Override
		public Enum<?> previousStatus() {
			return this.previousStatus;
		}
		@Override
		public void setPreviousStatus(Enum<?> status) {
			this.previousStatus = (Status) status;
		}
		@Override
		public void setPreviousStatus(String status) {
			this.previousStatus = Enum.valueOf(Status.class, status);
		}
		@Override
		public <T extends IsObjectInstance<T>> void cleanStatus(T object) {
	  	if(object.status().name().equals(Status.MODIFIED.name())) {
	  		object.setStatus(object.previousStatus());
	  		object.setPreviousStatus(Status.CLEAN);
	  	}
		}
		@Override
		public int id() {
			return this.id;
		}
		@Override
		public int updateId(int id) {
			int prev = id;
			this.id = id;
			return prev;
		}
		@Override
		public String updateName(String name) {
			String prev = this.orderNumber;
			this.orderNumber = name;
			this.states.modify();
			return prev;
		}
		@Override
		public void setName(String name) {
			this.orderNumber = name;
		}
		@Override
		public String name() {
			return orderNumber;
		}
		@Override
		public Version version() {
			return this.version;
		}
		@Override
		public void updateVersion(String version) {
			this.version = new Version(version);
			this.states.modify();
		}
		@Override
		public void updateVersion(Version version) {
			this.version = version;
			this.states.modify();
		}
		@Override
		public void setVersion(String version) {
			this.version = new Version(version);
		}
		@Override
		public Group group() {
			return this.group;
		}
		@Override
		public void updateGroup(Group group) {
			this.group = group;
			this.states.modify();
		}
		@Override
		public void updateGroup(String group) {
			this.group = new Group(group);
			this.states.modify();
		}
		@Override
		public void setGroup(String group){
			this.group = new Group(group);
		}
		@Override
		public Timestamp date() {
			return date;
		}
		public Timestamp updateDate(Timestamp date) {
			Timestamp prev = this.date;
			this.date = date;
			this.states.modify();
			return prev;
		}
		@Override
		public void setDate(Timestamp date) {
			this.date = date;
		}
		@Override
		public Timestamp byDate() {
			return this.byDate;
		}
		@Override
		public Timestamp updateByDate(Timestamp date) {
			Timestamp prev = this.byDate;
			this.byDate = date;
			this.states.modify();
			return prev;
		}
		@Override
		public void setByDate(Timestamp date) {
			this.byDate = date;
		}
		@Override
		public String initialBusinessId() {
			return this.orderNumber + Util.DB._NS + Util.NEW_ID;
		}
		@Override
		public String businessId() {
			return this.orderNumber + Util.DB._NS + this.id;
		}
		@Override
		public String updateBusinessId(String newId) {
			String prev = this.orderNumber;
			this.orderNumber = newId;
			this.states.modify();
			return prev;
		}
		@Override
		public void setBusinessId(String newId) {
			this.orderNumber = newId;
		}
		@Override
		public String onlyBusinessId() {
			return this.orderNumber;
		}
		@Override
		public void updateBusinessKey(String businessKey) {
			this.businessKey = businessKey;
			this.states.modify();
		}
		@Override
		public void setBusinessKey(String businessKey) {
			this.businessKey = businessKey;
		}
		@Override
		public OrderPositionList orderPositions() {
			return this.orderPositions;
		}
		@Override
		public void updateOrderPositions(OrderPositionList orderPositionList) {
			this.orderPositions = orderPositionList;
			this.states.modify();
		}
		@Override
		public void setOrderPositionList(OrderPositionList opl) {
			this.orderPositions = opl;
		}
		@Override
		public CampInstance history() {
			return this.history;
		}
		@Override
		public void setHistory(CampInstance instance) {
			this.history = instance;
		}
		@Override
		public CampStates states() {
			return this.states;
		}
		@Override
		public int getObjectId() {
			return this.id;
		}

		@Override
		public String getObjectBusinessId() {
			return this.orderNumber;
		}
	
		@Override
		public CampInstance getObjectHistory() {
			return this.history;
		}
		@Override
		public int getRefId() {
			return 0;
		}
		@Override
		public String toJson() {
			return OrderInterface._toJson(this);
		}
		@Override
		public Order fromJson(String json) {
			return OrderInterface._fromJson(json);
		}
		@Override
		public Message prepareMessage(String insanceId, Enum<?> message) {
			CustomerOrderMessage msg = CustomerOrderMessage.valueOf(message.name());
			OrderProcessMessage m = new OrderProcessMessage(msg, this);
			for(Process<?> p: processInstances){
				m.setProcessInstanceId(p.instanceId());
				m.setTenantId(p.tenantId());
			}
			return m;
		}
		@Override
		public MessageList prepareMessages(Enum<?> message) {
			CustomerOrderMessage msg = CustomerOrderMessage.valueOf(message.name());
			
			MessageList ml = new MessageList();
			
			for(Process<?> p: processInstances){
				OrderProcessMessage m = new OrderProcessMessage(msg, this);
				m.setProcessInstanceId(p.instanceId());
				m.setTenantId(p.tenantId());
				ml.add(m);
			}
			
			return ml;
		}
		@Override
		public Request<?> prepareRequest(Principal principal, RequestType type) {
			return new Request<Order>(this,principal,type);
		}
		
		public Order clone() {
			String json = this.toJson();
			if(!Util._IN_PRODUCTION){String msg = "----[JSON: ("+json+")]----";LOG.info(String.format(fmt, "clone",msg));}
			return OrderInterface._fromJson(json);
		}
		
		public void mirror(Order object) {
	  	this.id= object.id();
	  	this.orderNumber = object.onlyBusinessId();
	  	this.businessKey = object.businessKey();
	  	this.date = object.date();
	  	this.byDate = object.byDate();
	  	this.status = (Status)object.status();
	  	this.previousStatus = (Status)object.previousStatus();
	  	this.processInstances = object.processes();
	  	this.orderPositions = object.orderPositions();
	  	this.group = object.group();
	  	this.version = object.version();
	  	this.states = object.states();
	  	this.history = object.history();
		}

		public PersistanceHandler persistance() {
			return persistanceHandler;
		}
}
