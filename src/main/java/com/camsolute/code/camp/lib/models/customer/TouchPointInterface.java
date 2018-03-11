package com.camsolute.code.camp.lib.models.customer;

import java.sql.Timestamp;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasDate;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampInstanceInterface;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface;
import com.camsolute.code.camp.lib.models.customer.TouchPoint.Status;
import com.camsolute.code.camp.lib.utilities.Util;

public interface TouchPointInterface extends HasDate, IsObjectInstance<TouchPoint> {

	public static final Logger LOG = LogManager.getLogger(TouchPointInterface.class);
	public static String fmt = "[%15s] [%s]";
		
	public void setRefId(int id);
		
	public String businessKeyResponsible();

	public void setBusinessKeyResponsible(String businessKeyResponsible);

	public String updateBusinessKeyResponsible(String businessKeyResponsible);

	public String businessIdResponsible();

	public void setBusinessIdResponsible(String businessIdResponsible);

	public String updateBusinessIdResponsible(String businessIdResponsible);

	public String businessKeyCustomer();

	public void setBusinessKeyCustomer(String businessKeyCustomer);

	public String updateBusinessKeyCustomer(String businessKeyCustomer);

	public String businessIdCustomer();
	
	public void setBusinessIdCustomer(String businessIdCustomer);
	
	public String updateBusinessIdCustomer(String businessIdCustomer);

	public Timestamp nextDate();
	
	public Timestamp updateNextDate(Timestamp nextDate);
	
	public void setNextDate(Timestamp nextDate);
	
	public String minutes();
	
	public String updateMinutes(String topic);
	
	public void setMinutes(String topic);

	public static String _toJson(TouchPoint t) {
		return "{"+_toInnerJson(t)+"}";
	}
	public static String _toInnerJson(TouchPoint a) {
		String json = "";
		json += "\"id\":"+a.id()+",";
		json += "\"refId\":"+a.getRefId()+",";
		json += "\"businessKeyResponsible\":\""+a.businessKeyResponsible()+"\",";
		json += "\"businessIdResponsible\":\""+a.businessIdResponsible()+"\",";
		json += "\"businessKeyCustomer\":\""+a.businessKeyCustomer()+"\",";
		json += "\"businessIdCustomer\":\""+a.businessIdCustomer()+"\",";
		json += "\"date\":\""+a.date().toString()+"\",";
		json += "\"nextDate\":\""+((a.nextDate()!=null)?a.nextDate().toString():"")+"\",";
		json += "\"topic\":\""+a.topic()+"\",";
		json += "\"minutes\":\""+a.minutes()+"\",";
		json += "\"history\":"+a.history().toJson()+",";
		json += "\"states\":"+a.states().toJson()+",";
		json += "\"status\":\""+a.status().name()+"\",";
		json += "\"previousStatus\":\""+a.previousStatus().name()+"\",";
		json += "\"group\":\""+a.group().name()+"\",";
		json += "\"version\":\""+a.version().value()+"\",";
		return json;
	}
		
	public static TouchPoint _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	public static TouchPoint _fromJSONObject(JSONObject jo) {
		int id = jo.getInt("id");
		int refId = jo.getInt("refId");
		String businessKeyResponsible = jo.getString("businessKeyResponsible");
		String businessIdResponsible = jo.getString("businessIdResponsible");
		String businessKeyCustomer = jo.getString("businessKeyCustomer");
		String businessIdCustomer = jo.getString("businessIdCustomer");
		Timestamp date = Util.Time.timestamp(jo.getString("date"));
		Timestamp nextDate = null;
		try {
			nextDate = Util.Time.timestamp(jo.getString("nextDate"));
		} catch (Exception e) {
			if(!Util._IN_PRODUCTION){String msg = "----[ JSON Error! No next date set .]----";LOG.info(String.format(fmt,"_fromJSONObject",msg));}
			e.printStackTrace();
		}
		String topic = jo.getString("topic");
		String minutes = jo.getString("minutes");
		CampInstance history = CampInstanceInterface._fromJSONObject(jo.getJSONObject("history"));
		CampStates states = CampStatesInterface._fromJSONObject(jo.getJSONObject("states"));
		Status status = Status.valueOf(jo.getString("status"));
		Status previousStatus = Status.valueOf(jo.getString("previousStatus"));
		String group = jo.getString("group");
		String version = jo.getString("version");
		TouchPoint t = new TouchPoint(businessKeyResponsible, businessIdResponsible, businessKeyCustomer, businessIdCustomer, date, topic, minutes);
		t.updateId(id);
		t.setRefId(refId);
		t.setNextDate(nextDate);
		t.setHistory(history);
		t.states().update(states);
		t.setStatus(status);
		t.setPreviousStatus(previousStatus);
		t.setGroup(group);
		t.setVersion(version);
		return t;
	}
}
