package com.camsolute.code.camp.lib.contract.attribute;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.attribute.Attribute;
import com.camsolute.code.camp.lib.contract.core.DataMismatchException;
import com.camsolute.code.camp.lib.contract.history.HistoryMarker;
import com.camsolute.code.camp.lib.contract.history.JSONHistoryHandler;
import com.camsolute.code.camp.lib.contract.value.JSONValueHandler;
import com.camsolute.code.camp.lib.contract.value.Value;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface;
import com.camsolute.code.camp.lib.contract.process.ProcessList;
import com.camsolute.code.camp.lib.contract.process.ProcessList.ProcessListImpl;

public interface JSONAttributeHandler {
	
	public String toJson(Attribute attribute);
	
	public Attribute fromJson(String json) throws DataMismatchException;
	
	public Attribute fromJSONObject(JSONObject jo) throws DataMismatchException;

	public static  Attribute _fromJSONObject(JSONObject jo) throws DataMismatchException {
		int id = 0;
		if(jo.has("id")) id = jo.getInt("id");
    String businessId = jo.getString("businessId");
    String defaultValue = jo.getString("defaultValue");
    int valueId = jo.getInt("valueId");
    Value value = JSONValueHandler._fromJSONObject(jo.getJSONObject("value"));
    String businessKey = jo.getString("businessKey");
    String group = jo.getString("group");
    String version = jo.getString("version");
    int position = jo.getInt("position");
    CampStates states = CampStatesInterface._fromJSONObject(jo.getJSONObject("states"));
    boolean hasValueParent = jo.getBoolean("hasValueParent");
    int valueParentId = jo.getInt("valueParentId");
    HistoryMarker history = JSONHistoryHandler._fromJSONObject(jo.getJSONObject("history")); 
    ProcessList processes = new ProcessListImpl();
    if(jo.has("processes")) {
    	processes = ProcessList._fromJSONObject(jo.getJSONObject("processes"));
    } 
    Attribute a = new Attribute.DefaultAttribute();
    a.id(id);
    a.valueId(valueId);
    a.updateBusinessKey(businessKey,false);
    a.updateGroup(group,false);
    a.updateVersion(version,false);
    a.updatePosition(position,false);
    a.hasValueParent(hasValueParent);
    a.valueParentId(valueParentId);
    a.states().update(states);
    a.setHistory(history);
    a.observerProcesses(processes);
    a.updateDefaultValue(defaultValue, false);
    a.updateBusinessId(businessId, false);
    a.updateValue(value, false);
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
    json += ",\"defaultValue\":"+JSONObject.quote(a.defaultValue())+"";
    json += ",\"valueId\":"+a.valueId();
    json += ",\"businessKey\":\""+a.businessKey()+"\"";
    json += ",\"group\":\""+a.group().name()+"\"";
    json += ",\"version\":\""+a.version().value()+"\"";
    json += ",\"position\":"+a.position();
    json += ",\"hasParentValue\":"+a.hasValueParent();
    json += ",\"parentValueId\":"+a.valueParentId();
    json += ",\"states\":"+a.states().toJson();
    json += ",\"history\":"+a.history().jsonHandler().toJson(a.history());
    json += ((!a.observerProcesses().isEmpty())?","+"\"processes\":"+a.observerProcesses().toJson():"");
    json += ",\"value\":"+a.value().toJson();
    return json;
}

}
