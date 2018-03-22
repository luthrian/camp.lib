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

import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.utilities.Util;

public class ContactDetails implements ContactDetailsInterface{
	
	private int id = Util.NEW_ID;
	private String customerBusinessId;
	private String customerBusinessKey;
	private String email;
	private String mobile;
	private String telephone;
	private String skype;
	private String misc;
	
	private CampStates states = new CampStates();

	public ContactDetails(String email, String mobile, String telephone, String skype, String misc) {
		this.email = email;
		this.mobile = mobile;
		this.telephone = telephone;
		this.skype = skype;
		this.misc = misc;
	}
	
	public ContactDetails(int id, String email, String mobile, String telephone, String skype, String misc) {
		this.id = id;
		this.email = email;
		this.mobile = mobile;
		this.telephone = telephone;
		this.skype = skype;
		this.misc = misc;
	}
	
	public int id(){
		return this.id;
	}
	
	public int updateId(int id) {
		int prev = this.id;
		this.id = id;
		return prev;
	}
	
	public String customerBusinessId(){
		return this.customerBusinessId;
	}	
	public void updateCustomerBusinessId(String id) {
		this.customerBusinessId = id;
		this.states.modify();
	}
	public void setCustomerBusinessId(String id) {
		this.customerBusinessId = id;
	}
	
	public String customerBusinessKey(){
		return this.customerBusinessKey;
	}	
	public void updateCustomerBusinessKey(String key) {
		this.customerBusinessKey = key;
		this.states.modify();
	}
	public void setCustomerBusinessKey(String key) {
		this.customerBusinessKey = key;
	}
	
	public String email(){
		return this.email;
	}
	public void updateEmail(String email) {
		this.email = email;
		this.states.modify();
	}
	public void setEmail(String email) {
		this.email=email;
	}
	
	public String mobile(){
		return this.mobile;
	}
	public void updateMobile(String mobile) {
		this.mobile = mobile;
		this.states.modify();
	}
	public void setMobile(String mobile) {
		this.mobile=mobile;
	}
	
	public String telephone(){
		return this.telephone;
	}
	public void updateTelephone(String telephone) {
		this.telephone = telephone;
		this.states.modify();
	}
	public void setTelephone(String telephone) {
		this.telephone=telephone;
	}
	
	public String skype(){
		return this.skype;
	}
	public void updateSkype(String skype) {
		this.skype = skype;
		this.states.modify();
	}
	public void setSkype(String skype) {
		this.skype=skype;
	}
	
	public String misc(){
		return this.misc;
	}
	public void updateMisc(String misc) {
		this.misc = misc;
		this.states.modify();
	}
	public void setMisc(String misc) {
		this.misc=misc;
	}
	
	public CampStates states() {
		return this.states();
	}

	public String toJson() {
		return ContactDetailsInterface._toJson(this);
	}
	public ContactDetails fromJson(String json) {
		return ContactDetailsInterface._fromJson(json);
	}
}
