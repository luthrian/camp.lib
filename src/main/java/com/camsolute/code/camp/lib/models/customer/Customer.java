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

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Version;
import com.camsolute.code.camp.lib.utilities.Util;
//TODO
public class Customer implements IsObjectInstance<Customer> {
	private int id;
	private String title;
	private String firstName;
	private String surName;
	@Override
	public int id() {
		return 0;
	}
	@Override
	public int updateId(int id) {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updateVersion(String version) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateVersion(Version version) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Group group() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updateGroup(Group group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateGroup(String group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setGroup(String group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String initialBusinessId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String businessId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String updateBusinessId(String newId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setBusinessId(String newId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String onlyBusinessId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String businessKey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updateBusinessKey(String businessKey) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setBusinessKey(String businessKey) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public CampInstance history() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setHistory(CampInstance instance) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getObjectId() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getObjectBusinessId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CampInstance getObjectHistory() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getRefId() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public CampStates states() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Enum<?> status() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Enum<?> updateStatus(Enum<?> status) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Enum<?> updateStatus(String status) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setStatus(Enum<?> status) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setStatus(String status) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Enum<?> previousStatus() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setPreviousStatus(Enum<?> status) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setPreviousStatus(String status) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public <T extends IsObjectInstance<T>> void cleanStatus(T object) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Customer fromJson(String json) {
		// TODO Auto-generated method stub
		return null;
	}
}
