package com.camsolute.code.camp.lib.contract.process;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.Serialization;
import com.camsolute.code.camp.lib.contract.process.JSONProcessVariableValueHandler.JSONCamundaProcessVariableValueHandler;

public interface ProcessVariableValue extends HasJSONProcessVariableValueHandler, Serialization<ProcessVariableValue> {

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
	
		public JSONProcessVariableValueHandler jsonHandler() {
			return jsonHandler;
		}
		
		public void jsonHandler(JSONProcessVariableValueHandler jsonProcessVariableValueHandler) {
			this.jsonHandler = (JSONCamundaProcessVariableValueHandler) jsonProcessVariableValueHandler;
		}

		public String toJson() {
			return jsonHandler.toJson(this);
		}

		public ProcessVariableValue fromJson(String json) throws DataMismatchException {
			return jsonHandler.fromJson(json);
		}

		public ProcessVariableValue fromJSONObject(JSONObject jo) throws DataMismatchException {
			return jsonHandler.fromJSONObject(jo);
		}

		public static ProcessVariableValue _fromJSONObject(JSONObject jo) throws DataMismatchException {
			return JSONCamundaProcessVariableValueHandler._fromJSONObject(jo);
		}
	}
}
