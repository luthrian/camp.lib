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
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Version;
import com.camsolute.code.camp.lib.models.process.OrderPositionProcess;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.models.process.Process.ProcessType;
import com.camsolute.code.camp.lib.models.product.OrderProduct;
import com.camsolute.code.camp.lib.models.product.Product;
import com.camsolute.code.camp.lib.models.rest.Message;
import com.camsolute.code.camp.lib.models.rest.MessageList;
import com.camsolute.code.camp.lib.models.rest.OrderPositionProcessMessage;
import com.camsolute.code.camp.lib.models.rest.OrderPositionProcessMessage.OrderPositionMessage;
import com.camsolute.code.camp.lib.models.rest.Request;
import com.camsolute.code.camp.lib.models.rest.Request.Principal;
import com.camsolute.code.camp.lib.models.rest.Request.RequestType;
import com.camsolute.code.camp.lib.utilities.Util;

public class OrderPosition implements OrderPositionInterface {
		
	private int id = Util.NEW_ID;
	private int refId = Util.NEW_ID;
	private int productId = 0;
	private int modelId = 0;
	private String businessId;
	private String orderBusinessId;
	private String businessKey;
	private String refBusinessKey;
	private int position = 0;
	private int quantity = 1;
	private Timestamp date = Util.Time.timestamp();
	private Group group;
	private Version version;
	private CampStates states = new CampStates();
	private CampInstance history = new CampInstance();
	private Status status = Status.CREATED;
	private Status previousStatus = Status.CLEAN;

	private Product product;
	private Order order;
	private ProcessList processes = new ProcessList();
	
	public OrderPosition(String businessId, String orderBusinessId, int position) {
		this.businessId = businessId;
		this.orderBusinessId = orderBusinessId;
		this.position = position;
	}
	
	public OrderPosition(String businessId, String orderBusinessId, int position, OrderProduct product) {
		this.businessId = businessId;
		this.orderBusinessId = orderBusinessId;
		this.position = position;
		this.product = product;
		this.productId = product.id();
	}
	
	public OrderPosition(int id, String businessId, String orderBusinessId, int position) {
		this.id = id;
		this.businessId = businessId;
		this.orderBusinessId = orderBusinessId;
		this.position = position;
	}
	
	public OrderPosition(int id, String businessId, String orderBusinessId, int position, OrderProduct product) {
		this.id = id;
		this.businessId = businessId;
		this.orderBusinessId = orderBusinessId;
		this.position = position;
		this.product = product;
		this.productId = product.id();
	}

		@Override
		public int quantity() {
			return this.quantity;
		}

		@Override
		public void setQuantity(int amount) {
			this.quantity = amount;
		}

		@Override
		public void updateQuantity(int newAmount) {
			this.quantity = newAmount;
			this.states.modify();
		}

		@Override
		public int position() {
			return this.position;
		}

		@Override
		public int updatePosition(int position) {
			int prev = this.position;
			this.position = id;
			this.states.modify();
			return prev;
		}

		@Override
		public void setPosition(int position) {
			this.position = position;
		}

		@Override
		public int productId() {
			return this.productId;
		}

		@Override
		public void setProductId(int id) {
			this.productId = id;
		}

		@Override
		public int modelId() {
			return this.modelId;
		}

		@Override
		public void setModelId(int id) {
			this.modelId = id;
		}

		@Override
		public Product product() {
			return this.product;
		}

		@Override
		public Product updateProduct(Product product) {
			Product prev = this.product;
			this.product = product;
			this.productId = product.id();
			this.modelId = product.modelId();
			this.states.modify();
			return prev;
		}

		@Override
		public void setProduct(Product product) {
			this.product = product;
			this.productId = product.id();
			this.modelId = product.modelId();
		}

		@Override
		public Order order() {
			return order;
		}

		@Override
		public Order order(Order order) {
			Order prev = this.order;
			this.order = order;
			this.orderBusinessId = order.onlyBusinessId();
			this.businessKey = order.businessKey();
			this.group = order.group();
			this.version = order.version();
			this.states.modify();
			return prev;
		}

		@Override
		public String orderBusinessId() {
			return this.orderBusinessId;
		}

		@Override
		public void setBusinessId(String businessId) {
			this.businessId = businessId;
		}

		@Override
		public String updateOrderBusinessId(String newOrderBusinessId) {
			String prev = this.orderBusinessId;
			this.orderBusinessId = newOrderBusinessId;
			this.states.modify();
			return prev;
		}

		@Override
		public String initialOrderBusinessId() {
			return this.orderBusinessId;
		}

		@Override
		public String onlyOrderBusinessId() {
			return this.orderBusinessId;
		}

		@Override
		public int id() {
			return this.id;
		}

		@Override
		public int updateId(int id) {
			int prev = this.id;
			this.id = id;
			return prev;
		}

		@Override
		public String name() {
			return businessId;
		}

		@Override
		public String updateName(String businessId) {
			String prev = this.businessId;
			this.businessId = businessId;
			this.states.modify();
			return prev;
		}
		
		@Override
		public void setName(String businessId) {
			this.businessId = businessId;
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
			this.version  = version;
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
		public void setGroup(String group) {
			this.group = new Group(group);
		}

		@Override
		public String initialBusinessId() {
			return this.orderBusinessId + Util.DB._VS + this.businessId;
		}

		@Override
		public String businessId() {
			return this.orderBusinessId + Util.DB._VS + this.businessId;
		}

		@Override
		public String updateBusinessId(String newId) {
			String prev = this.businessId;
			this.businessId = newId;
			this.states.modify();
			return prev;
		}

		@Override
		public String onlyBusinessId() {
			return this.businessId;
		}

		@Override
		public String businessKey() {
			return this.businessKey;
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
		public Enum<?> status() {
			return this.status;
		}

		@Override
		public Enum<?> updateStatus(Enum<?> status) {
			Status prev = this.status;
			this.previousStatus = this.status;
			this.status = (Status)status;
			this.states.modify();
			return prev;
		}

		@Override
		public Enum<?> updateStatus(String status) {
			Status prev = this.status;
			this.previousStatus = this.status;
			this.status = Status.valueOf(status);
			this.states.modify();
			return prev;
		}

		@Override
		public void setStatus(Enum<?> status) {
			this.status = (Status)status;
		}

		@Override
		public void setStatus(String status) {
			this.status = Status.valueOf(status);
		}

		@Override
		public Enum<?> previousStatus() {
			return this.previousStatus;
		}

		@Override
		public void setPreviousStatus(Enum<?> status) {
			this.previousStatus = (Status)status;
		}

		@Override
		public void setPreviousStatus(String status) {
			this.previousStatus = Status.valueOf(status);
		}

		@Override
		public <T extends IsObjectInstance<T>> void cleanStatus(T object) {
			if(object.status().name().equals(Status.MODIFIED.name())) {
				object.setStatus(object.previousStatus());
				object.setPreviousStatus(Status.CLEAN);
			}
		}

		@Override
		public int getObjectId() {
			return this.id;
		}
		
		@Override
		public String getObjectBusinessId() {
			return this.businessId;
		}
	
		@Override
		public CampInstance getObjectHistory() {
			return this.history;
		}

		@Override
		public int getRefId() {
			return this.history().objectRefId();
		}
		
		@Override
		public int updateRefId(int id) {
			int prev = this.history().objectRefId();
			this.refId = id;
			this.history().setObjectRefId(id);
			return prev;
		}
		
		@Override
		public String refBusinessKey() {
			return this.refBusinessKey;
		}
		
		@Override
		public String updateRefBusinessKey(String id) {
			String prev = this.refBusinessKey;
			this.refBusinessKey = id;
			this.states.modify();
			return prev;
		}
		
		@Override
		public void setRefBusinessKey(String id) {
			this.refBusinessKey = id;
		}
		
		@Override
		public Timestamp date() {
			return this.date;
		}
		@Override
		public void setDate(Timestamp date) {
			this.date = date;
		}
		@Override
		public Timestamp updateDate(Timestamp newDate) {
			Timestamp prev = this.date;
			this.date = newDate;
			this.states.modify();
			return prev;
		}
	
		@Override
		public ProcessList processInstances() {
			return this.processes;
		}

		@Override
		public void addProcess(Process<OrderPosition> process) {
			this.processes.add(process);
		}

		@Override
		public void addProcesses(ProcessList processes) {
			this.processes.addAll(processes);
		}

		@Override
		public Process<OrderPosition> deleteProcess(String instanceId) {
			Process<?> process = null;
			for(Process<?> p: this.processes) {
				if(p.instanceId().equals(instanceId)) {
					process = p;
				}
			}
			return (OrderPositionProcess) process;
		}

		@Override
		public ProcessList processes() {
			return this.processes;
		}

		@Override
		public ProcessList processes(ProcessType group) {
			ProcessList pl = new ProcessList();
			for(Process<?> p: this.processes) {
				if(p.type().name().equals(group.name())){
					pl.add(p);
				}
			}
			return pl;
		}

		@Override
		public void setProcesses(ProcessList pl) {
			this.processes = pl;
		}

		@Override
		public void notifyProcesses() {
			for(Process<?> p: this.processes) {
				((OrderPositionProcess)p).notify(this);
			}
		}

		@Override
		public void notifyProcesses(ProcessType type) {
			for(Process<?> p: this.processes) {
				if(p.type().name().equals(type.name())) {
					((OrderPositionProcess)p).notify(this);
				}
			}
		}

		@Override
		public void notifyProcesses(Enum<?> event) {
			for(Process<?> p: this.processes) {
				((OrderPositionProcess)p).notify(this,(OrderPositionEvent)event);
			}
		}

		@Override
		public void notifyProcesses(ProcessType type, Enum<?> event) {
			for(Process<?> p: this.processes) {
				if(p.type().name().equals(type.name())) {
					((OrderPositionProcess)p).notify(this,event);
				}
			}			
		}


		@Override
 		public String toJson() {
			return OrderPositionInterface._toJson(this);
		}

		@Override
		public OrderPosition fromJson(String json) {
			return OrderPositionInterface._fromJson(json);
		}

    public static enum Status {
    	CREATED,
    	ADDED,
    	MODIFIED,
    	SUBMITTED,
    	APPROVED,
    	REGISTERED,
    	ASSIGNED,
    	REJECTED,
    	ACCEPTED,
    	ON_HOLD,
    	IN_PRODUCTION,
    	COMPLETED,
    	FULFILLED,
    	UPDATED,
    	CANCELLED,
    	CLEAN,
    	DIRTY
    }

    public static enum OrderPositionEvent {
    	order_position_added,
    	order_position_submitted,
    	order_position_approved,
    	order_position_registered,
    	order_position_assigned,
    	order_position_rejected,
    	order_position_accepted,
    	order_position_on_hold,
    	order_position_in_production,
    	order_position_completed,
    	order_position_fulfilled,
    	order_position_modified,
    	order_position_updated,
    	order_position_cancelled
    }
    	
		@Override
		public Message prepareMessage(String insanceId, Enum<?> message) {
			OrderPositionMessage msg = OrderPositionMessage.valueOf(message.name());
			OrderPositionProcessMessage m = new OrderPositionProcessMessage(msg, this);
			for(Process<?> p: processes){
				m.setProcessInstanceId(p.instanceId());
				m.setTenantId(p.tenantId());
			}
			return m;
		}
		@Override
		public MessageList prepareMessages(Enum<?> message) {
			OrderPositionMessage msg = OrderPositionMessage.valueOf(message.name());
			
			MessageList ml = new MessageList();
			
			for(Process<?> p: processes){
				OrderPositionProcessMessage m = new OrderPositionProcessMessage(msg, this);
				m.setProcessInstanceId(p.instanceId());
				m.setTenantId(p.tenantId());
				ml.add(m);
			}
			
			return ml;
		}
		@Override
		public Request<?> prepareRequest(Principal principal, RequestType type) {
			return new Request<OrderPosition>(this,principal, type);
		}

}
