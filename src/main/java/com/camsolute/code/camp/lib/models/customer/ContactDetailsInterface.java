package com.camsolute.code.camp.lib.models.customer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasStates;
import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface;
import com.camsolute.code.camp.lib.utilities.Util;

public interface ContactDetailsInterface extends Serialization<ContactDetails>, HasStates {
	public static final Logger LOG = LogManager.getLogger(ContactDetailsInterface.class);
	public static String fmt = "[%15s] [%s]";
	
	public int id();
	
	public int updateId(int id);
	
	public String email();
	public void updateEmail(String email);
	public void setEmail(String email);
	
	public String mobile();
	public void updateMobile(String mobile);
	public void setMobile(String mobile);
	
	public String telephone();
	public void updateTelephone(String telephone);
	public void setTelephone(String telephone);
	public String skype();
	public void updateSkype(String skype);
	public void setSkype(String skype);
	
	public String misc();
	public void updateMisc(String misc);
	public void setMisc(String misc);
	
	public TouchPoint contactHistory();
	public void updatecontactHistory(TouchPoint contactHistory);
	public void setContactHistory(TouchPoint contactHistory);
	
	public static String _toJson(ContactDetailsInterface d) {
		return "{"+_toInnerJson(d)+"}";
	}
	public static String _toInnerJson(ContactDetailsInterface d) {
		String json = "";
		json += "\"id\":\""+d.id()+"\",";
		json += "\"email\":\""+d.email()+"\",";
		json += "\"mobile\":\""+d.mobile()+"\",";
		json += "\"telephone\":\""+d.telephone()+"\",";
		json += "\"skype\":\""+((d.skype()!=null)?d.skype():"")+"\",";
		json += "\"misc\":\""+((d.misc()!=null)?d.misc():"")+"\",";
		json += "\"contactHistory\":"+((d.contactHistory()!=null)?d.contactHistory().toJson():"")+",";
		json += "\"states\":"+d.states().toJson();
		return json;
	}
	
	public static ContactDetails _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	public static ContactDetails _fromJSONObject(JSONObject jo) {
		int id = jo.getInt("id");
		String email = jo.getString("email");
		String mobile = jo.getString("mobile");
		String telephone = jo.getString("telephone");
		String skype = jo.getString("skype");
		String misc = jo.getString("misc");
		CampStates states = CampStatesInterface._fromJSONObject(jo.getJSONObject("states"));
		TouchPoint contactHistory = null;
		try {
			contactHistory = TouchPointInterface._fromJSONObject(jo.getJSONObject("contactHistory"));
		} catch (Exception e){
			if(!Util._IN_PRODUCTION){String msg = "----[ JSON Error! Contact history missing.]----";LOG.info(String.format(fmt,"_fromJSONObject",msg));}
			e.printStackTrace();
		}
		ContactDetails d = new ContactDetails(id,email,mobile,telephone,skype,misc);
		d.setContactHistory(contactHistory);
		d.states().update(states);
		return d;
	}
}
