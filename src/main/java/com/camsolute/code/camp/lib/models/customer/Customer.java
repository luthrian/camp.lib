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
package com.camsolute.code.camp.lib.models.customer;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.contract.core.CampStates;
import com.camsolute.code.camp.lib.contract.core.CampStates.CampStatesImpl;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Version;
import com.camsolute.code.camp.lib.models.process.CustomerProcess;
import com.camsolute.code.camp.lib.models.process.CustomerProcessList;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.Process.ProcessType;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.models.rest.CustomerProcessMessage;
import com.camsolute.code.camp.lib.models.rest.CustomerProcessMessage.CustomerMessage;
import com.camsolute.code.camp.lib.models.rest.Message;
import com.camsolute.code.camp.lib.models.rest.MessageList;
import com.camsolute.code.camp.lib.models.rest.Request;
import com.camsolute.code.camp.lib.models.rest.Request.Principal;
import com.camsolute.code.camp.lib.models.rest.Request.RequestType;
import com.camsolute.code.camp.lib.utilities.Util;
//TODO
public class Customer implements CustomerInterface {
	
	public static enum Status {
		CREATED,
		NEW,
		ID_UNVERIFIED,
		ID_VERIFIED,
		CREDIT_UNVERIFIED,
		CREDIT_VERIFIED,
		ACTIVE,
		DEACTIVATED,
		MODIFIED,
		DIRTY,
		CLEAN
		;
	};
	
	public static enum Type {
		INT_PERSON,
		INT_BUSINESS,
		EXT_PERSON,
		EXT_BUSINESS,
		ORG_PERSON,
		ORG_BUSINESS,
		NGO_PERSON,
		NGO_BUSINESS,
		GOV_PERSON,
		GOV_BUSINESS,
		MIL_PERSON,
		MIL_BUSINESS
		;
	}
	public static enum Origin {
		LOCAL,
		FOREIGN
	}
	
	public static enum PersonTitle {
		NA,
		MR,
		MRS,
		MISS,
		PROF,
		DR;
	}
	
	public static enum BusinessTitle {
		LTD,
		CORP,
		PART;
	}
	private int id = Util.NEW_ID;
	private int refId = Util.NEW_ID;
	private Type type;
	private Origin origin;
	private String businessId;
	private String businessKey;
	private Group group;
	private Version version;
	private int addressId = Util.NEW_ID;
	private int deliveryAddressId = Util.NEW_ID;
	private int touchPointId = Util.NEW_ID;
	private int contactId = Util.NEW_ID;
	private Status status = Status.CREATED;
	private Status previousStatus = Status.CLEAN;
	private CampInstance history = new CampInstance();
	private CampStates states = new CampStatesImpl();
	private ContactDetails contact = null;
	private AddressList addressList = new AddressList();//
	private ProcessList processes = new ProcessList();
	private TouchPointList touchPoints = new TouchPointList();
	
	public Customer(int id, Origin origin, Type type, String businessId, String businessKey, Group group, Version version){
		this.id = id;
		this.type = type;
		this.origin = origin;
		this.businessId = businessId;
		this.businessKey = businessKey;
		this.group = group;
		this.version = version;
	}
	
	public Customer(Origin origin, Type type, String businessId, String businessKey, Group group, Version version){
		this.type = type;
		this.origin = origin;
		this.businessId = businessId;
		this.businessKey = businessKey;
		this.group = group;
		this.version = version;
	}
	
	public Customer(int id, Origin origin, Type type, String businessId, String businessKey, String group, String version){
		this.id = id;
		this.type = type;
		this.origin = origin;
		this.businessId = businessId;
		this.businessKey = businessKey;
		this.group = new Group(group);
		this.version = new Version(version);
	}
	
	public Customer(Origin origin, Type type, String businessId, String businessKey, String group, String version){
		this.type = type;
		this.origin = origin;
		this.businessId = businessId;
		this.businessKey = businessKey;
		this.group = new Group(group);
		this.version = new Version(version);
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
	public void setRefId(int id) {
		this.refId = id;
	}
	@Override
	public String name() {
		return onlyBusinessId();
	}
	@Override
	public String updateName(String name) {
		return updateBusinessId(name);
	}
	@Override
	public void setName(String name) {
		setBusinessId(name);
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
	public void setGroup(String group) {
		this.group = new Group(group);
	}
	@Override
	public String initialBusinessId() {
		return this.businessId;
	}
	@Override
	public String businessId() {
		return this.origin.name()+Util.DB._VS+this.type.name()+Util.DB._VS+this.businessId;
	}
	@Override
	public String updateBusinessId(String newId) {
		String prev = this.businessId;
		this.businessId = newId;
		this.states.modify();
		return prev;
	}
	@Override
	public void setBusinessId(String newId) {
		this.businessId = newId;
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
	public int getObjectId() {
		return this.refId;
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
		return this.history.objectRefId();
	}
	@Override
	public int updateRefId(int id) {
		int prev = this.history.objectRefId();
		this.refId = id;
		this.history.setObjectRefId(id);
		return prev;
	}
	@Override
	public CampStates states() {
		return this.states();
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
		this.status = Status.valueOf(status);
		this.states.modify();
		return prev;
	}
	@Override
	public void setStatus(Enum<?> status) {
		this.status = (Status) status;
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
		this.previousStatus = (Status) status;
	}
	@Override
	public void setPreviousStatus(String status) {
		this.previousStatus = Status.valueOf(status);
	}
	@Override
	public <T extends IsObjectInstance<T>> void cleanStatus(T object) {
		if(this.status.name().equals(Status.MODIFIED.name())){
			this.status = this.previousStatus;
			this.previousStatus = Status.CLEAN;
		}
	}
	
	public Type type() {
		return this.type;
	}
	public void setType(String type) {
		this.type = Type.valueOf(type);
	}
	public Type updateType(String type) {
		Type prev = this.type;
		this.type = Type.valueOf(type);
		this.states.modify();
		return prev;
	}
	public Type updateType(Type type) {
		Type prev = this.type;
		this.type = (Type) type;
		this.states.modify();
		return prev;
	}
	
	public Origin origin() {
		return this.origin;
	}
	public void setOrigin(String origin) {
		this.origin = Origin.valueOf(origin);
	}
	public Origin updateOrigin(String origin) {
		Origin prev = this.origin;
		this.origin = Origin.valueOf(origin);
		this.states.modify();
		return prev;
	}
	public Origin updateOrigin(Origin origin) {
		Origin prev = this.origin;
		this.origin = (Origin) origin;
		this.states.modify();
		return prev;
	}
	
	public int addressId() {
		return this.addressId;
	}
	public void setAddressId(int id) {
		this.addressId = id;
	}
	public int deliveryAddressId() {
		return this.deliveryAddressId;
	}
	public void setDeliveryAddressId(int id) {
		this.deliveryAddressId = id;
	}
	public Address address(){ 
		for(Address a:this.addressList) {
			if(a.id() == this.addressId) {
				return a;
			}
		}
		return null;
	}
	public Address deliveryAddress(){ 
		for(Address a:this.addressList) {
			if(a.id() == this.deliveryAddressId) {
				return a;
			}
		}
		return null;
	}
	public AddressList addressList() {
		return this.addressList;
	}
	public void setAddressList(AddressList addressList) {
		this.addressList = addressList;
	}
	public boolean addAddress(Address address) {
		for(Address a:this.addressList) {
			if(a.id() == address.id()) {
				return false;
			}
		}
		this.addressList.add(address);
		address.states().modify();
		this.states.modify();
		return true;
	}
	public Address removeAddress(Address address) {
		Address a = null;
		for(Address ad:this.addressList){
			int ctr = 0;
			if(ad.id() == address.id()) {
				a = this.addressList.get(ctr);
				this.addressList.remove(ctr);
				this.states.modify();
				break;
			}
			ctr++;
		}
		return a;
	}
	public Address removeAddress(int addressId) {
		Address a = null;
		for(Address ad:this.addressList){
			int ctr = 0;
			if(ad.id() == addressId) {
				a = this.addressList.get(ctr);
				this.addressList.remove(ctr);
				this.states.modify();
				break;
			}
			ctr++;
		}
		return a;
	}
	
	@Override
	public String toJson() {
		return CustomerInterface._toJson(this);
	}
	@Override
	public Customer fromJson(String json) {
		return CustomerInterface._fromJson(json);
	}

	@Override
	public int contactId() {
		return this.contactId;
	}
	@Override
	public void setContactId(int id) {
		this.contactId = id;
	}
	@Override
	public ContactDetails contact() {
		return this.contact;
	}

	@Override
	public void updateContact(ContactDetails contactDetails) {
		this.contact = contactDetails;
		this.contactId = contactDetails.id();
		this.states.modify();
	}

	@Override
	public void setContact(ContactDetails contactDetails) {
		this.contact = contactDetails;
		this.contactId = contactDetails.id();
	}



	@Override
	public ProcessList processInstances() {
		return this.processes;
	}
	@Override
	public void addProcess(Process<Customer> process) {
		process.states().modify();
		this.processes.add(process);
		this.states.modify();
	}

	@Override
	public void addProcesses(ProcessList processes) {
		for(Process<?> p: processes) {
			p.states().modify();
		}
		processes.addAll(processes);
		this.states.modify();
	}
	@Override
	public Process<Customer> deleteProcess(String instanceId) {
		int count = 0;
		for(Process<?> p: processes) {
			if(p.instanceId().equals(instanceId)){
				this.states.modify();
				return (Process<Customer>) processes.remove(count);
			}
			count++;
		}
		return null;
	}

	@Override
	public void setProcesses(ProcessList pl) {
		this.processes = (ProcessList) pl;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ProcessList processes() {
		return processes;
	}
	@SuppressWarnings("unchecked")
	@Override
	public ProcessList processes(ProcessType type) {
		ProcessList opl = new ProcessList();
		for(Process<?> op: processes){
			if(op.type().name().equals(type.name())){
				opl.add((Process<Customer>)op);
			}
		}
		return opl;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void notifyProcesses() {
		for(Process<?> op:processes) {
			((Process<Customer>)op).notify(this);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void notifyProcesses(ProcessType type) {
		for(Process<?> op:processes) {
			if(op.type().name().equals(type.name())){
				((Process<Customer>)op).notify(this);
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void notifyProcesses(Enum<?> event) {
		for(Process<?> op: processes){
			((Process<Customer>)op).notify(this, event);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void notifyProcesses(ProcessType type, Enum<?> event) {
		for(Process<?> op: processes){
			if(op.type().name().equals(type.name())){
				((Process<Customer>)op).notify(this, event);
			}
		}
	}

	@Override
	public Message prepareMessage(String insanceId, Enum<?> message) {
		CustomerMessage msg = CustomerMessage.valueOf(message.name());
		CustomerProcessMessage m = new CustomerProcessMessage(msg, this);
		for(Process<?> p: processes){
			m.setProcessInstanceId(p.instanceId());
			m.setTenantId(p.tenantId());
		}
		return m;
	}
	@Override
	public MessageList prepareMessages(Enum<?> message) {
		CustomerMessage msg = CustomerMessage.valueOf(message.name());
		
		MessageList ml = new MessageList();
		
		for(Process<?> p: processes){
			CustomerProcessMessage m = new CustomerProcessMessage(msg, this);
			m.setProcessInstanceId(p.instanceId());
			m.setTenantId(p.tenantId());
			ml.add(m);
		}
		
		return ml;
	}
	@Override
	public Request<?> prepareRequest(Principal principal, RequestType type) {
		return new Request<Customer>(this,principal, type);
	}
	@Override
	public int touchPointId() {
		return touchPointId;
	}
	
	@Override
	public void setTouchPointId(int id) {
		this.touchPointId = id;
	}

	@Override
	public TouchPointList touchPoints() {
		return touchPoints;
	}

	@Override
	public void setTouchPoints(TouchPointList touchPoints) {
		this.touchPoints = touchPoints;
	}

	@Override
	public boolean addTouchPoint(TouchPoint touchPoint) {
		for(TouchPoint tp:this.touchPoints){
			if(tp.id() == touchPoint.id()){
				return false;
			}
		}
		touchPoint.states().modify();
		this.touchPoints.add(touchPoint);
		this.touchPointId = touchPoint.id();
		this.states.modify();
		return true;
	}

	@Override
	public TouchPoint removeTouchPoint(TouchPoint touchPoint) {
		TouchPoint tp = null;
		int ctr = 0;
		int size = this.touchPoints.size();
		for(TouchPoint t:this.touchPoints){
			if(t.id() == touchPoint.id()) {
				tp = this.touchPoints.get(ctr);
				this.touchPoints.remove(ctr);
				this.states.modify();
				// if we removed the last touch point list entry we set the Customer.touchPointId to the id of the new last entry 
				if(ctr+1 == size) {
					this.touchPointId = touchPoints.get(touchPoints.size()-1).id();
				}
				break;
			}
			ctr++;
		}
		return tp;
	}

	@Override
	public TouchPoint removeTouchPoint(int touchPointId) {
		TouchPoint tp = null;
		int ctr = 0;
		int size = this.touchPoints.size();
		for(TouchPoint t:this.touchPoints){
			if(t.id() == touchPointId) {
				tp = this.touchPoints.get(ctr);
				this.touchPoints.remove(ctr);
				this.states.modify();
				// if we removed the last touch point list entry we set the Customer.touchPointId to the id of the new last entry 
				if(ctr+1 == size) {
					this.touchPointId = touchPoints.get(touchPoints.size()-1).id();
				}
				break;
			}
			ctr++;
		}
		return tp;
	}

	
}
