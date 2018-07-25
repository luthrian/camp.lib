package com.camsolute.code.camp.lib.contract.attribute;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.attribute.Attribute;
import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.contract.history.HistoryMarker;
import com.camsolute.code.camp.lib.contract.history.JSONHistoryHandler;
import com.camsolute.code.camp.lib.contract.value.JSONValueHandler;
import com.camsolute.code.camp.lib.contract.value.Value;
import com.camsolute.code.camp.lib.contract.core.CampList.ProcessList;
import com.camsolute.code.camp.lib.contract.core.CampList.AttributeList;
import com.camsolute.code.camp.lib.contract.core.CampStates;

public interface JSONAttributeHandler {
	
	public String toJson(Attribute attribute);
	
	public Attribute fromJson(String json) throws DataMismatchException;
	
	public Attribute fromJSONObject(JSONObject jo) throws DataMismatchException;

	public static  Attribute _fromJSONObject(JSONObject jo) throws DataMismatchException {
		String id = "";
		if(jo.has("id")) id = jo.getString("id");
    String businessId = jo.getString("businessId");
    String businessKey = jo.getString("businessKey");
    String group = jo.getString("group");
    String version = jo.getString("version");
    int position = jo.getInt("position");
    String defaultValue = null;
    defaultValue = (jo.has("defaultValue"))?jo.getString("defaultValue"):null;
    String valueId = jo.getString("valueId");
    Value<?,?> value = null;
    value = (jo.has("value"))?JSONValueHandler._fromJSONObject(jo.getJSONObject("value")):null;
    CampStates states = CampStates._fromJSONObject(jo.getJSONObject("states"));
    boolean hasParent = jo.getBoolean("hasParent");
    String parentId = (hasParent)?jo.getString("parentId"):null;
    HistoryMarker history = JSONHistoryHandler._fromJSONObject(jo.getJSONObject("history"));
    AttributeList attributes = new AttributeList();
    if(jo.has("attributes")) {
    	attributes = AttributeList._fromJSONObject(jo.getJSONObject("attributes"));
    }
    ProcessList processes = new ProcessList();
    if(jo.has("processes")) {
    	processes = ProcessList._fromJSONObject(jo.getJSONObject("processes"));
    } 
    Attribute a = new Attribute.DefaultAttribute();
    a.updateId(id);
    a.updateBusinessId(businessId, false);
    a.updateBusinessKey(businessKey,false);
    a.updatePosition(position,false);
    a.updateGroup(group,false);
    a.updateVersion(version,false);
    a.setHistory(history);
    a.states().update(states);
    a.hasParent(hasParent);
    a.parentId(parentId,false);
    a.attributes(attributes);
    a.observerProcesses(processes);
    a.updateDefaultValue(defaultValue, false);
    a.valueId(valueId);
    if(value != null) {
    	a.updateValue(value, false);
    }
    for(Attribute ca:a.attributes()) {
    	ca.parent(a);
    }
    return a;
}

public static String _toJson(Attribute a) {
    String json = "{";
    json += _toInnerJson(a);
    json += "}";
    return json;
}
public static String _toInnerJson(Attribute a) {
    String json = "";
    json += "\"id\":"+a.id();
    json += ",\"businessId\":\""+a.onlyBusinessId()+"\"";
    json += ",\"businessKey\":\""+a.businessKey()+"\"";
    json += ",\"position\":"+a.position();
    json += ",\"group\":\""+a.group().name()+"\"";
    json += ",\"version\":\""+a.version().value()+"\"";
    json += ",\"history\":"+a.history().jsonHandler().toJson(a.history());
    json += ",\"states\":"+a.states().toJson();
    json += ",\"hasParent\":"+a.hasParent();
    json += ",\"parentId\":"+a.parentId();
    json += ((!a.attributes().isEmpty())?",\"attributes\":"+a.attributes().toJson():"");
    json += ((!a.observerProcesses().isEmpty())?","+"\"processes\":"+a.observerProcesses().toJson():"");
    json += (a.defaultValue() != null || !a.defaultValue().isEmpty())?",\"defaultValue\":"+JSONObject.quote(a.defaultValue())+"":"";
    json += ",\"valueId\":"+a.valueId();
    json += (a.value() != null)?",\"value\":"+a.value().toJson():"";
    return json;
}

}
