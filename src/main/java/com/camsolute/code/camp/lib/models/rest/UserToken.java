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
package com.camsolute.code.camp.lib.models.rest;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class UserToken implements Serialization<UserToken>{
	private String userId;
	private boolean authenticated = false;
	
	public UserToken(String userId, boolean authenticated) {
		this.userId = userId;
		this.authenticated = authenticated;
	}
	
	public String userId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public boolean authenticated() {
		return this.authenticated;
	}
	public void setAuthenticated(boolean authenticated) {
		this.authenticated  = authenticated;
	}

	@Override
	public String toJson() {
		return _toJson(this);
	}

	public static String _toJson(UserToken u) {
		String json = "{";
		json += "\"userId\":\""+u.userId()+"\",";
		json += "\"authenticated\":"+u.authenticated();
		json += "}";
		return json;
	}
	@Override
	public UserToken fromJson(String json) {
		return _fromJson(json);
	}
	public static UserToken _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	public static UserToken _fromJSONObject(JSONObject jo) {
		String userId = jo.getString("userId");
		boolean authenticated = jo.getBoolean("authenticated");
		return new UserToken(userId,authenticated);
	}
}
