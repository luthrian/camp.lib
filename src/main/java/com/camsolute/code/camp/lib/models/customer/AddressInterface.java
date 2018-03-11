package com.camsolute.code.camp.lib.models.customer;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampInstanceInterface;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Version;
import com.camsolute.code.camp.lib.models.customer.Address.Status;
import com.camsolute.code.camp.lib.utilities.Util;

public interface AddressInterface extends IsObjectInstance<AddressInterface> {
	public String country();
	public void setCountry(String country);
	public void updateCountry(String country);
	public String state();
	public void setState(String state);
	public void updateState(String state);
	public String postCode();
	public void setPostCode(String postCode);
	public void updatePostCode(String postCode);
	public String city();
	public void setCity(String city);
	public void updateCity(String city);
	public String street();
	public void setStreet(String street);
	public void updateStreet(String street);
	public String streetNumber();
	public void setStreetNumber(String streetNumber);
	public void updateStreetNumber(String streetNumber);
	public String floor();
	public void setFloor(String floor);
	public void updatefloor(String floor);
	public String roomNumber();
	public void setRoomNumber(String roomNumber);
	public void updateRoomNumber(String roomNumber);

	public static String _toJson(Address a) {
		return "{"+_toInnerJson(a)+"}";
	}
	public static String _toInnerJson(Address a) {
		String json = "";
		json += "\"id\":"+a.id()+",";
		json += "\"country\":\""+a.country()+"\",";
		json += "\"state\":\""+a.state()+"\",";
		json += "\"postCode\":\""+a.postCode()+"\",";
		json += "\"city\":\""+a.city()+"\",";
		json += "\"street\":\""+a.street()+"\",";
		json += "\"streetNumber\":\""+a.streetNumber()+"\",";
		json += "\"floor\":\""+a.floor()+"\",";
		json += "\"roomNumber\":\""+a.roomNumber()+"\",";
		json += "\"businessKey\":\""+a.businessKey()+"\",";
		json += "\"group\":\""+a.group()+"\",";
		json += "\"version\":\""+a.version()+"\",";
		json += "\"status\":\""+a.status().name()+"\",";
		json += "\"previousStatus\":\""+a.previousStatus().name()+"\",";
		json += "\"history\":"+a.history().toJson()+",";
		json += "\"states\":"+a.states().toJson();
		return json;
	}
	public static Address _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	public static Address _fromJSONObject(JSONObject jo) {
		int id = jo.getInt("id");
		String country = jo.getString("country");
		String state = jo.getString("state");
		String postCode = jo.getString("postCode");
		String city = jo.getString("city");
		String street = jo.getString("street");
		String streetNumber = jo.getString("streetNumber");
		String floor = jo.getString("floor");
		String roomNumber = jo.getString("roomNumber");
		String businessKey = jo.getString("businessKey");
		String group = jo.getString("group");
		String version = jo.getString("version");
		Status status = Status.valueOf(jo.getString("status"));
		Status previousStatus = Status.valueOf(jo.getString("previousStatus"));
		CampInstance history = CampInstanceInterface._fromJSONObject(jo.getJSONObject("history"));
		CampStates states = CampStatesInterface._fromJSONObject(jo.getJSONObject("states"));
		Address a = new Address(id,country,state,postCode,city,street,streetNumber,floor,roomNumber);
		a.setBusinessKey(businessKey);
		a.setGroup(group);
		a.setVersion(version);
		a.setStatus(status);
		a.setPreviousStatus(previousStatus);
		a.setHistory(history);
		a.states().update(states);
		return a;
	}
}
