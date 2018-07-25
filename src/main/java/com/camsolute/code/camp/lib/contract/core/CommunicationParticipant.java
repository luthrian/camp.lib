package com.camsolute.code.camp.lib.contract.core;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;

public interface CommunicationParticipant extends HasDataHandler, Serialization<CommunicationParticipant> {
	public String identifier();
	public DataHandler messagePayloadHandler();

	public static CommunicationParticipant _fromJSONObject(JSONObject jo) throws DataMismatchException {
		CommunicationParticipant participant = new CommunicationParticipantImpl(jo.getString("identifier"));
		try {
			participant.dataHandler((DataHandler)Class.forName(jo.getString("dataHandler")).newInstance());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataMismatchException("Failed to instantiate message payload handler for CommunicationParticipant", e);
		}
		return participant;
	}


	public class CommunicationParticipantImpl implements CommunicationParticipant {

		private final String identifier;
		private DataHandler dataHandler;
		
		public CommunicationParticipantImpl(String identifier) {
			this.identifier = identifier;
		}
		public DataHandler dataHandler() {
			return dataHandler;
		}

		public void dataHandler(DataHandler handler) {
			this.dataHandler = handler;
		}

		public String toJson() {
			String json = "{";
			json += "\"identifier\":\""+identifier+"\"";
			json += ",\"dataHandler\":\""+this.dataHandler.getClass().getName()+"\"";
			json += "}";
			return json;
		}

		public CommunicationParticipant fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}

		public CommunicationParticipant fromJSONObject(JSONObject jo) throws DataMismatchException {
			return _fromJSONObject(jo);
		}

		public String identifier() {
			return identifier;
		}

		public DataHandler messagePayloadHandler() {
			return dataHandler;
		}
	}
}
