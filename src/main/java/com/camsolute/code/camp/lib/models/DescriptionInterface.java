package com.camsolute.code.camp.lib.models;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasDescription;
import com.camsolute.code.camp.lib.contract.HasProduct;
import com.camsolute.code.camp.lib.contract.HasTitle;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.Description.Status;

public interface DescriptionInterface extends IsObjectInstance<Description>, HasTitle, HasDescription, HasProduct {
	
	public static String _toJson(Description d) {
		String json = "{";
		json += _toInnerJson(d);
		json += "}";
		return json;
	}
	public static String _toInnerJson(Description d) {
		String json = "";
		json += "\"id\":"+d.id()+",";
		json += "\"productId\":"+d.productId()+",";
		json += "\"title\":\""+d.title()+"\",";
		json += "\"description\":\""+d.description()+"\",";
		json += "\"businessId\":\""+d.onlyBusinessId()+"\",";
		json += "\"businessKey\":\""+d.businessKey()+"\",";
		json += "\"group\":\""+d.group().name()+"\",";
		json += "\"version\":\""+d.version().value()+"\",";
		json += "\"status\":\""+d.status().name()+"\",";
		json += "\"previousStatus\":\""+d.previousStatus().name()+"\",";
		json += "\"history\":"+d.history().toJson()+",";
		json += "\"states\":"+d.states().toJson();
		return json;
	}
	public static Description _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	public static Description _fromJSONObject(JSONObject jo) {
		int id = jo.getInt("id");
		int productId = jo.getInt("productId");
		String title = jo.getString("title");
		String description = jo.getString("description");
		String businessId = jo.getString("businessId");
		String businessKey = jo.getString("businessKey");
		String group = jo.getString("group");
		String version = jo.getString("version");
		CampStates states = CampStatesInterface._fromJSONObject(jo.getJSONObject("states"));
		CampInstance history = CampInstanceInterface._fromJSONObject(jo.getJSONObject("history"));
		Status status = Description.Status.valueOf(jo.getString("status"));
		Status previousStatus = Description.Status.valueOf(jo.getString("previousStatus"));
		Description d = new Description(id, title, description, businessId, businessKey, group, version);
		d.states().update(states);
		d.setStatus(status);
		d.setHistory(history);
		d.setPreviousStatus(previousStatus);
		d.setProductId(productId);
		return d;
	}
}
