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
package com.camsolute.code.camp.lib.models;

import com.camsolute.code.camp.lib.contract.Serialization;
import org.json.JSONObject;
//TODO: implement
public interface TokenInterface extends Serialization<Token> {

	public static Token passwordToken(String username, final String password) {
		return new Token(username,password);
	}

    public static Token usernameToken(String username) {
        return new Token(username,"");
    }

	public static Token tokenToken(String token) {
		return new Token(token);
	}

	public String token();

	public String username();

	public String password();

    public static String _toJson(Token t){
        String json = "{";
        json += "\"token\":\""+t.token()+"\"";
        json += "}";
        return json;
    }

    public static Token _fromJson(String json){
        return _fromJSONObject(new JSONObject(json));
    }

    public static Token _fromJSONObject(JSONObject jo){
        String token = jo.getString("token");
        return tokenToken(token);
    }
}
