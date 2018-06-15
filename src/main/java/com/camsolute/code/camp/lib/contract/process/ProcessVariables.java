package com.camsolute.code.camp.lib.contract.process;

import java.util.HashMap;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.process.JSONProcessVariablesHandler.JSONCamundaProcessVariablesHandler;
import com.camsolute.code.camp.lib.contract.process.ProcessVariableValue;
import com.camsolute.code.camp.lib.contract.process.ProcessVariableValue.CamundaProcessVariableValue;
import com.camsolute.code.camp.lib.contract.process.ProcessVariableValue.ProcessVariableValueType;

public interface ProcessVariables extends HasJSONProcessVariableHandler {

	public HashMap<String,ProcessVariableValue> variables();
	public void variables(HashMap<String,ProcessVariableValue> variables);
	public void add(String name, ProcessVariableValue value);
	public void add(String name,String value, ProcessVariableValueType type);
	
	public class CamundaProcessVariables implements ProcessVariables {
		
		private HashMap<String,ProcessVariableValue> variables = new HashMap<String,ProcessVariableValue>();
		
		private JSONProcessVariablesHandler jsonHandler;
		
		public CamundaProcessVariables() {
			jsonHandler = new JSONCamundaProcessVariablesHandler();
		}
		
		public CamundaProcessVariables(String variableName, ProcessVariableValue variableValue) {
			variables.put(variableName, variableValue);
			jsonHandler = new JSONCamundaProcessVariablesHandler();
		}
		
		public CamundaProcessVariables(String variableName, String variableValue, ProcessVariableValueType variableType) {
			variables.put(variableName, new CamundaProcessVariableValue(variableValue, variableType));
			jsonHandler = new JSONCamundaProcessVariablesHandler();
		}	
		
		public CamundaProcessVariables(String variableName, String variableValue, ProcessVariableValueType variableType, boolean local) {
			variables.put(variableName, new CamundaProcessVariableValue(variableValue, variableType,local));
			jsonHandler = new JSONCamundaProcessVariablesHandler();
		}	

		public HashMap<String,ProcessVariableValue> variables() {
			return this.variables;
		}
		
		public void variables(HashMap<String,ProcessVariableValue> variables) {
			this.variables = variables;
		}
		
		public void add(String name, ProcessVariableValue value) {
			variables.put(name, value);
		}
		
		public void add(String name,String value, ProcessVariableValueType type) {
			variables.put(name, new CamundaProcessVariableValue(value,type));
		}
		
		public void add(String name, String value, ProcessVariableValueType type, boolean local) {
			variables.put(name, new CamundaProcessVariableValue(value,type,local));
		}
		
		public ProcessVariableValue get(String name) {
			return variables.get(name);
		}

		public JSONProcessVariablesHandler jsonProcessVariableHandler() {
			return jsonHandler;
		}

		public void jsonProcessVariableHandler(JSONProcessVariablesHandler jsonProcessVariableHandler) {
			jsonHandler = jsonProcessVariableHandler;
		}
}
}
