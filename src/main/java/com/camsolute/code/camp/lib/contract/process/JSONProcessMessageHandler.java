package com.camsolute.code.camp.lib.contract.process;

import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;

public interface JSONProcessMessageHandler {
	
	public String toJson(ProcessMessage message);
	public ProcessMessage fromJson(String json) throws DataMismatchException;
	
	public class CamundaJSONProcessMessageHandler implements JSONProcessMessageHandler {

		public String toJson(ProcessMessage message) {
			// TODO Auto-generated method stub
			return null;
		}

		public ProcessMessage fromJson(String json) throws DataMismatchException {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
