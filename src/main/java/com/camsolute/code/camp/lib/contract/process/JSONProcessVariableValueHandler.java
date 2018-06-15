package com.camsolute.code.camp.lib.contract.process;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.DataMismatchException;
import com.camsolute.code.camp.lib.contract.process.ProcessVariableValue.ProcessVariableValueType;
import com.camsolute.code.camp.lib.contract.process.ProcessVariableValue.CamundaProcessVariableValue;

public interface JSONProcessVariableValueHandler {

	public String toJson(ProcessVariableValue processVariableValue);
	public ProcessVariableValue fromJson(String json) throws DataMismatchException;
	public ProcessVariableValue fromJSONObject(JSONObject jo) throws DataMismatchException;
	
	public class JSONCamundaProcessVariableValueHandler implements JSONProcessVariableValueHandler {

		public String toJson(ProcessVariableValue processVariableValue) {
			String json = "{";
			json += "\"value\":\""+processVariableValue.value()+"\",";
			json += "\"type\":\""+processVariableValue.type().name()+"\",";
			json += "\"local\":\""+processVariableValue.localScope();
			json += "}";
			return json;
		}

		public ProcessVariableValue fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public ProcessVariableValue fromJSONObject(JSONObject jo) throws DataMismatchException {
			String value = jo.getString("value");
			ProcessVariableValueType type = ProcessVariableValueType.valueOf(jo.getString("type"));
			boolean local = jo.getBoolean("local");
			return  new CamundaProcessVariableValue(value,type,local);
		}
	}
}
