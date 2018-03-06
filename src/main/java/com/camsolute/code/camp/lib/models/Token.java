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

import java.util.Base64;
import com.camsolute.code.camp.lib.utilities.Util;
import static com.camsolute.code.camp.lib.utilities.Util.Config;

//TODO: implement
public class Token implements TokenInterface {
	//TODO:
	private String token;
    private final String salt = Util.Config.instance().properties().getProperty("token.salt");

    protected Token(String token) {
        this.token = token;

    }

	protected Token(String username, final String password) {
		//TODO: Salt will be random ...blabla
		this.token = Base64.getEncoder().encodeToString((username+"|"+password).getBytes());

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
	public String toJson() {
		return TokenInterface._toJson(this);
	}

	@Override
	public Token fromJson(String json) {
      return TokenInterface._fromJson(json);
	}

}
