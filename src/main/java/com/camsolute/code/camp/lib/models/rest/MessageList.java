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

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.Serialization;

public class MessageList extends ArrayList<Message> implements Serialization<MessageList>{

	@Override
	public String toJson() {
		return _toJson(this);
	}

	public static String _toJson(MessageList ml) {
		String json = "[";
		boolean start = true;
		for(Message m:ml) {
			if(!start) {
				json += ",";
			} else {
				start = false;
			}
			json += Message._toJson(m);
			
		}
		json += "]";
		return json;
	}
	
	@Override
	public MessageList fromJson(String json) {
		return _fromJson(json);
	}

	public static MessageList _fromJson(String json) {
		return _fromJSONArray(new JSONArray(json));
	}
	
	public static MessageList _fromJSONArray(JSONArray ja) {
		MessageList ml = new MessageList();
		for(Object jo:ja.toList()) {
			ml.add(Message._fromJSONObject((JSONObject) jo));
		}
		return ml;
	}


}
