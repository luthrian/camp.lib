package com.camsolute.code.camp.lib.contract.core;

import java.util.Iterator;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.utilities.Util;

public interface CampMessage extends HasDataHandler, Serialization<CampMessage> {
    public CommunicationParticipant sender();
    public CommunicationParticipant[] recipients();
    public void addRecipient(CommunicationParticipant recipient);
    public Object data();
    public DataHandler dataHandler();
    public String address();
    public void address(String address);
    

    public class CampMessageImpl implements CampMessage {

        protected final CommunicationParticipant sender;

        protected CommunicationParticipant[] recipients;

        protected String address;

        protected Object data;

        public CommunicationParticipant sender() {
            return sender;
        }

        public CommunicationParticipant[] recipients() {
            return recipients;
        }

        public void addRecipient(CommunicationParticipant recipient) {
        	CommunicationParticipant[] newArray = new CommunicationParticipant[recipients.length + 1];
            int counter = 0;
            for(CommunicationParticipant r:recipients) {
                newArray[counter] = r;
                counter++;
            }
            newArray[counter] = recipient;
            recipients = newArray;
        }

        public String address() {
            return this.address;
        }
        public void address(String address) {
            this.address = address;
        }

        public DataHandler dataHandler() {
            return sender.dataHandler();
        }
        
        public void dataHandler(DataHandler handler) {
        	sender.dataHandler(handler);
        }

        public Object data() {
            return data;
        }

        public CampMessageImpl(CommunicationParticipant sender, CommunicationParticipant[] recipients, Object data) {
          this.sender = sender;
          this.recipients = recipients;
          this.data = data;
        }

				public String toJson() {
					// TODO Auto-generated method stub
					return _toJson(this);
				}

				public CampMessage fromJson(String json) throws DataMismatchException {
					// TODO Auto-generated method stub
					return fromJSONObject(new JSONObject(json));
				}

				public CampMessage fromJSONObject(JSONObject jo) throws DataMismatchException {
					// TODO Auto-generated method stub
					return _fromJSONObject(jo);
				}
        
    }

   public static String _toJson(CampMessage msg) {
      String json = "{";
      json += "\"sender\":\""+msg.sender()+"\"";
      json += ",\"recipients\":[";
      boolean start = true;
      for(CommunicationParticipant cp: msg.recipients()) {
      	if(!start) {
      		json += ",";
      	} else {
      		start = false;
      	}
      	json += cp.toJson();
      }
      json += "]";
      json += ",\"dataHandler\":\""+msg.dataHandler().getClass().getName()+"\"";
      json += ",\"data\":"+msg.dataHandler().toJson(msg.data());
        json += "}";
        return json;
    }

    public static CampMessage _fromJSONObject(JSONObject jo) throws DataMismatchException {
    	CommunicationParticipant sender = CommunicationParticipant._fromJSONObject(jo.getJSONObject("sender"));
    	DataHandler handler= sender.dataHandler();
    	CommunicationParticipant[] recipients = new CommunicationParticipant[jo.getJSONArray("recipients").length()];
      Iterator<Object> i = jo.getJSONArray("recipients").iterator();
      int counter = 0;
      while(i.hasNext()) {
        recipients[counter] = CommunicationParticipant._fromJSONObject((JSONObject)i.next());
      }
      Object data = handler.fromJSONObject(jo.getJSONObject("data"));
      return new CampMessageImpl(sender, recipients, data);
    }

}
