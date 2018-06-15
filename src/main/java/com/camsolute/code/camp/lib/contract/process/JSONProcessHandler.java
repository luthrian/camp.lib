package com.camsolute.code.camp.lib.contract.process;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.SerializationHandler;
import com.camsolute.code.camp.lib.contract.process.Process;

public interface JSONProcessHandler extends SerializationHandler<Process> {

	public static Process _fromJSONObject(JSONObject jo) {
		//TODO:
		return null;
	}
	
	public class DefaultJSONProcessHandler implements JSONProcessHandler {

		public String toJson(Process p) {
			// TODO Auto-generated method stub
			return null;
		}

		public Process fromJson(String json) throws DataMismatchException {
			// TODO Auto-generated method stub
			return null;
		}

		public Process fromJSONObject(JSONObject jo) throws DataMismatchException {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public class CamundaJSONProcessHandler implements JSONProcessHandler {


		public String toJson(Process object) {
			// TODO Auto-generated method stub
			return null;
		}


		public Process fromJson(String json) throws DataMismatchException {
			// TODO Auto-generated method stub
			return null;
		}


		public Process fromJSONObject(JSONObject jo) throws DataMismatchException {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
