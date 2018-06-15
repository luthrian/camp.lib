package com.camsolute.code.camp.lib.contract.process;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.process.JSONProcessVariableValueHandler.JSONCamundaProcessVariableValueHandler;

public interface ProcessVariableValue extends HasJSONProcessVariableValueHandler {

	public static enum ProcessVariableValueType {
		String, Integer, Short, Long, Double, Date, Boolean, Object, File;
	}

	public String value();
	public void value(String value);
	public ProcessVariableValueType type();
	public boolean localScope();
	public void localScope(boolean localScope);
	
	public class CamundaProcessVariableValue implements ProcessVariableValue {

		private String value;
		private ProcessVariableValueType type;
		private boolean local = false;
		private JSONCamundaProcessVariableValueHandler jsonHandler;
		
		public CamundaProcessVariableValue(String value, ProcessVariableValueType type, boolean local) {
			this(value,type);
			this.local = local;
			this.jsonHandler = new JSONCamundaProcessVariableValueHandler();
		}

		public CamundaProcessVariableValue(String value, ProcessVariableValueType type) {
			this.value = value;			
			this.type = type;
			this.jsonHandler = new JSONCamundaProcessVariableValueHandler();
		}
		
		public String value() {
			return value;
		}
		
		public void value(String value) {
			this.value = value;
		}
		
		public ProcessVariableValueType type() {
			return type;
		}
		
		public boolean localScope() {
			return local;
		}
		
		public void localScope(boolean b) {
			this.local = b;
		}
	
		public JSONProcessVariableValueHandler jsonProcessVariableValueHandler() {
			return jsonHandler;
		}
		
		public void jsonProcessVariableValueHandler(JSONProcessVariableValueHandler jsonProcessVariableValueHandler) {
			this.jsonHandler = (JSONCamundaProcessVariableValueHandler) jsonProcessVariableValueHandler;
		}
	}
}
