package com.camsolute.code.camp.lib.contract.process;

import java.util.HashMap;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.contract.process.JSONProcessVariableValueHandler.JSONCamundaProcessVariableValueHandler;
import com.camsolute.code.camp.lib.contract.process.ProcessVariables.CamundaProcessVariables;

public interface JSONProcessVariablesHandler {

	public String toJson(ProcessVariables processVariable);
	public ProcessVariables fromJson(String json) throws DataMismatchException ;
	public ProcessVariables fromJSONObject(JSONObject jo) throws DataMismatchException;

	public class JSONCamundaProcessVariablesHandler implements JSONProcessVariablesHandler {

		public String toJson(ProcessVariables processVariables) {
			String json = "{";
			boolean start = true;
			for(String key: processVariables.variables().keySet()){
				if(!start) {
					json +=",";
				} else {
					start = false;
				}
				json += "\""+key+"\":"+processVariables.variables().get(key).toJson();
			}
			json += "}";
			return json;
		}
		public ProcessVariables fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public ProcessVariables fromJSONObject(JSONObject jo)  throws DataMismatchException {
			return _fromJSONObject(jo);
		}
		
		public static ProcessVariables _fromJSONObject(JSONObject jo)  throws DataMismatchException {
			HashMap<String,ProcessVariableValue> variables = new HashMap<String,ProcessVariableValue>();
			for(String key:jo.keySet()) {
				variables.put(key,(new JSONCamundaProcessVariableValueHandler()).fromJSONObject(jo.getJSONObject(key)));
			}
			ProcessVariables v = new CamundaProcessVariables();
			v.variables(variables);
			return v;
		}

	}
}
