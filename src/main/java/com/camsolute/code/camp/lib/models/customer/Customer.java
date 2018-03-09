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
import com.camsolute.code.camp.lib.models.CampInstanceInterface;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Version;
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
		INT_CONSUMER,
		INT_BUSINESS,
		EXT_CONSUMER,
		EXT_BUSINESS,
		ORG_CONSUMER,
		ORG_BUSINESS,
		NGO_CONSUMER,
		NGO_BUSINESS,
		GOV_CONSUMER,
		GOV_BUSINESS,
		MIL_CONSUMER,
		MIL_BUSINESS
		;
	}
	public static enum Origin {
		LOCAL,
		FOREIGN
	}
	
	private int id = Util.NEW_ID;
	private String title;
	private String firstName;
	private String surName;
	private String businessKey;
	private Group group;
	private Version version;
	private CampInstance history = new CampInstance();
	private CampStates states = new CampStates();
	private Status status = Status.CREATED;
	private Status previousStatus = Status.CLEAN;
	private Address address = null;
	private ContactDetails contact = null;
	
	public Customer(int id, String title, String firstName, String surName, String businessKey){
		this.id = id;
		this.title = title;
		this.firstName = firstName;
		this.surName = surName;
		this.businessKey = businessKey;
	}
	
	public Customer(String title, String firstName, String surName, String businessKey){
		this.title = title;
		this.firstName = firstName;
		this.surName = surName;
		this.businessKey = businessKey;
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
	public String firstName() {
		return firstName;
	}
	public String updateFirstName(String name) {
		String prev = this.firstName;
		this.firstName = name;
		return prev;
	}
	public void setFirstName(String name) {
		this.firstName = name;
	}
	public String surName() {
		return surName;
	}
	public String updateSurName(String name) {
		String prev = this.surName;
		this.surName = name;
		return prev;
	}
	public void setSurName(String name) {
		this.surName = name;
	}
	@Override
	public String name() {
		return firstName+Util.DB._VS+surName;
	}
	@Override
	public String updateName(String name) {
		String prev = name();
		String names[] = name.split(Util.DB._VS);
		if(names.length == 2) {
			this.firstName = names[0];
			this.surName = names[1];
			return prev;
		}
		return null;
	}
	@Override
	public void setName(String name) {
		String names[] = name.split(Util.DB._VS);
		if(names.length == 2) {
			this.firstName = names[0];
			this.surName = names[1];
		}
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
		return name();
	}
	@Override
	public String businessId() {
		return name();
	}
	@Override
	public String updateBusinessId(String newId) {
		return updateName(newId);
	}
	@Override
	public void setBusinessId(String newId) {
		setName(newId);
	}
	@Override
	public String onlyBusinessId() {
		return name();
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
		return id();
	}
	@Override
	public String getObjectBusinessId() {
		return businessId();
	}
	@Override
	public CampInstance getObjectHistory() {
		return history();
	}
	@Override
	public int getRefId() {
		return 0;
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
	
	public String title() {
		return this.title();
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String updateTitle(String title) {
		String prev = this.title;
		this.title = title;
		this.states.modify();
		return prev;
	}
	
	public Address address(){ 
		return this.address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public void updateAddress(Address address) {
		this.address = address;
		this.states.modify();
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
	public ContactDetails contact() {
		return this.contact;
	}

	@Override
	public void updateContact(ContactDetails contactDetails) {
		this.contact = contactDetails;
		this.states.modify();
	}

	@Override
	public void setContact(ContactDetails contactDetails) {
		this.contact = contactDetails;
	}
}
