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
package com.camsolute.code.camp.lib.models.product;

import java.sql.Timestamp;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasAttributes;
import com.camsolute.code.camp.lib.contract.HasDate;
import com.camsolute.code.camp.lib.contract.HasModel;
import com.camsolute.code.camp.lib.contract.HasProcess;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.models.AttributeMap;
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampInstanceInterface;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.ModelList;
import com.camsolute.code.camp.lib.models.Version;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.models.process.ProductProcess;
import com.camsolute.code.camp.lib.types.CampMap;
import com.camsolute.code.camp.lib.types.CampMapInterface;
import com.camsolute.code.camp.lib.utilities.Util;

public interface ProductInterface extends HasProcess<Product,ProductProcess>, HasAttributes, HasModel ,HasDate, IsObjectInstance<Product>{

	public static String _toJson(Product p){
		String json = "{";
		json += "\"id\":"+p.id()+",";
		json += "\"name\":\""+p.name()+"\",";
		json += "\"businesskey\":\""+p.businessKey()+"\",";
		json += "\"modelId\":"+p.modelId()+",";
		json += "\"models\":"+p.models().toJson()+",";
		json += "\"group\":\""+p.group().name()+"\",";
		json += "\"version\":\""+p.version().value()+"\",";
		json += "\"date\":\""+p.date().toString()+"\",";
		json += "\"status\":\""+p.status().name()+"\",";
		json += "\"previousStatus\":\""+p.previousStatus().name()+"\",";
		json += "\"states\":"+p.states().toJson()+",";
		json += "\"history\":"+p.history().toJson()+",";
		json += "\"attributes\":"+p.attributes().toJson();
		json += "\"processes\":"+p.processes().toJson();
		json += "}";
		return json;
	}
	
	public static Product _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static Product _fromJSONObject(JSONObject jo) {
		int id = jo.getInt("id");
		String name = jo.getString("name");
		int modelId = jo.getInt("modelId");
		String businesskey = jo.getString("businesskey");
		ModelList models = ModelList._fromJSONArray(jo.getJSONArray("models"));
		Group group = new Group(jo.getString("group"));
		Version version = new Version(jo.getString("version"));
		Timestamp date = Util.Time.timestamp(jo.getString("date"));
		String status = jo.getString("status");
		String previousStatus = jo.getString("previousStatus");
		CampStates states = CampStatesInterface._fromJSONObject(jo.getJSONObject("states"));
		CampInstance history = CampInstanceInterface._fromJSONObject(jo.getJSONObject("history"));
		AttributeMap attributes = AttributeMap._fromJSONObject(jo.getJSONObject("attributes"));
		ProcessList processes = ProcessList._fromJSONArray(jo.getJSONArray("processes"));
		Product p = new Product(id, name, businesskey, group, version, date);
		p.setStatus(status);
		p.setPreviousStatus(previousStatus);
		p.setHistory(history);
		p.states().update(states);
		p.setModels(models);
		p.setAttributes(attributes);
		p.setProcesses(processes);
		p.setModelId(modelId);
		return p;
	}
}
