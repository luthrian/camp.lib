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
package com.camsolute.code.camp.lib.types;

import java.util.Base64;

import com.camsolute.code.camp.core.dao.DBU;
import com.camsolute.code.camp.lib.CampType;
import com.camsolute.code.camp.models.business.ProductAttribute.Type;

public class CampToken extends CampType<String> {
	//TODO:
	private String token;
	private final String salt;
	
	private String attributeGroup = null;
	private int attributePosition = 0;
	
	private String attributeBusinessId = null;

	@Override
	public String attributeBusinessId() {
		return attributeBusinessId+DBU._NS+id();
	}

	@Override
	public String attrbuteBusinessId(String id) {
		String prev = this.attributeBusinessId;
		this.attributeBusinessId = id;
		return prev;
	}

	@Override
	public String onlyAttributeBusinessId() {
		return this.attributeBusinessId;
	}

	@Override
	public String initialAttributeBusinessId() {
		return attributeBusinessId+DBU._NS+0;
	}

	private CampToken(String username) {
		super(username, AttributeType._timestamp, null);
		//TODO: Salt will be random ...blabla
		this.salt = "blablabla";
		this.token = Base64.getEncoder().encodeToString((username).getBytes());
		
	}
	
	private CampToken(String username, final String password) {
		super(username, AttributeType._timestamp, null);
		//TODO: Salt will be random ...blabla
		this.salt = "blablabla";
		this.token = Base64.getEncoder().encodeToString((username+"|"+password).getBytes());
		
	}
	
	public static CampToken passwordToken(String username, final String password) {
		return new CampToken(username,password);
			
	}
	
	public static CampToken usernameToken(String username) {
		return new CampToken(username);
			
	}
	
	public String token() {
		return this.token;
	}
	
	public String username() {
		String decUP = Base64.getDecoder().decode(this.token.getBytes()).toString();
		String[] up = decUP.split("|");
		return up[0];
	}
	
	public final String password() {
		String decUP = Base64.getDecoder().decode(this.token.getBytes()).toString();
		String[] up = decUP.split("|");
		final String pwd = up[1];
		return pwd;
	}
	
	@Override
	protected String me() {
		return this.token;
	}

	@Override
	protected String me(String value) {
		String prev = this.token;
		this.token = value;
		return prev;
	}

	@Override
	public CampToken valueFromString(String value) {
		String decUP = Base64.getDecoder().decode(value.getBytes()).toString();
		String[] v = decUP.split("|");
		value(passwordToken(v[0],v[1]).token());
		return this;
	}

	@Override
	public String attributeGroup() {
		return this.attributeGroup;
	}

	@Override
	public String attributeGroup(String group) {
		String prev = this.attributeGroup;
		this.attributeGroup = group;
		return prev;
	}

	@Override
	public int attributePosition() {
		return this.attributePosition;
	}

	@Override
	public int attributePosition(int position) {
		int prev = this.attributePosition;
		this.attributePosition = position;
		return prev;
	} 


}
