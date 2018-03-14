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

import java.util.HashMap;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasProcess;
import com.camsolute.code.camp.lib.models.order.Order;
import com.camsolute.code.camp.lib.models.rest.VariableValue.VariableValueType;
import com.camsolute.code.camp.lib.utilities.Util;


public class ProcessStartRequest<T extends HasProcess<T>> extends Request<T> {

	private boolean skipCustomListeners = false;
	private boolean skipIoMappings = false;
	private HashMap<String, Object> startInstructions = new HashMap<String,Object>();
	
	public static enum StartInstructionType {
			startBeforeActivity, startAfterActivity, startTransition;
	}
	public ProcessStartRequest() {
		super();
	}
	public ProcessStartRequest(T o) {
		super(o);
	}
	
	@SuppressWarnings("null")
	public void initStartInstructions(StartInstructionType type,String idValue,HashMap<String,VariableValue> variables) {
		startInstructions = new HashMap<String,Object>();
		startInstructions.put("type", type.name());
		if((type.equals(StartInstructionType.startBeforeActivity)||type.equals(StartInstructionType.startAfterActivity))&&(idValue != null || !idValue.isEmpty())) {
			startInstructions.put("activityId", idValue);
		}
		if(type.equals(StartInstructionType.startTransition)&&(idValue != null || !idValue.isEmpty())) {
			startInstructions.put("transitionId", idValue);
		}
		if(variables != null && !variables.isEmpty()) {
			startInstructions.put("variables", variables);
		}
		
	}
	
	public void skipCustomListeners(boolean skip) {
		this.skipCustomListeners = skip;
	}
	
	public void skipIoMappings(boolean skip) {
		this.skipIoMappings = skip;
	}

	public boolean skipCustomListeners() {
		return this.skipCustomListeners;
	}
	
	public boolean skipIoMappings() {
		return this.skipIoMappings;
	}

	@Override
	public String toJson() {
		String json = "{";
		json += "\"skipCustomListeners\":"+skipCustomListeners+",";
		json += "\"skipIoMappings\":"+skipIoMappings+",";
		json += "\"startInstructions\":{";
			json += "\"type\":\""+(String)startInstructions.get("type")+"\",";
		if(startInstructions.containsKey("activityId") && !((String)startInstructions.get("activityId")).isEmpty()) {	
			json += "\"ativityId\":\""+(String)startInstructions.get("activityId")+"\",";
		}
		if(startInstructions.containsKey("transitionId") && !((String)startInstructions.get("transitionId")).isEmpty()) {	
			json += "\"transitionId\":\""+(String)startInstructions.get("transitionId")+"\",";
		}			
		if(startInstructions.containsKey("variables") && startInstructions.get("activityId") != null) {	
			json += "\"variables\":"+Variables._toJson((Variables)startInstructions.get("transitionId"))+",";
		}
		json += Request._toInnerJson(this);
		json += "}";
		return json;
	}
	
	@Override
	public ProcessStartRequest<T> fromJson(String json) {
		ProcessStartRequest<T> p = new ProcessStartRequest<T>();
		JSONObject jo = new JSONObject(json);
		p.skipCustomListeners(jo.getBoolean("skipCustomListeners"));
		p.skipIoMappings(jo.getBoolean("skipIoMappings"));
		HashMap<String,Object> startInstructions = new HashMap<String,Object>();
		JSONObject si = jo.getJSONObject("startInstructions");
		startInstructions.put("type",si.getString("type"));
		if(si.has("ativityId")) {
			startInstructions.put("ativityId",si.getString("ativityId"));
		}
		if(si.has("transitionId")) {
			startInstructions.put("transitionId",si.getString("transitionId"));
		}
		if(si.has("variables")) {
			startInstructions.put("variables",Variables._fromJSONObject(si.getJSONObject("variables")));
		}
		return p;
	}
}
