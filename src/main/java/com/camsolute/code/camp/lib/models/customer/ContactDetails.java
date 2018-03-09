package com.camsolute.code.camp.lib.models.customer;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface;
import com.camsolute.code.camp.lib.utilities.Util;

public class ContactDetails {
	
	private int id = Util.NEW_ID;
	private String email;
	private String mobile;
	private String telephone;
	private String skype;
	private String misc;
	private TouchPoint contactHistory;
	
	private CampStates states = new CampStates();

	public ContactDetails(String email, String mobile, String telephone, String skype, String misc) {
		this.email = email;
		this.mobile = mobile;
		this.telephone = telephone;
		this.skype = skype;
		this.misc = misc;
	}
	
	public ContactDetails(int id, String email, String mobile, String telephone, String skype, String misc) {
		this.id = id;
		this.email = email;
		this.mobile = mobile;
		this.telephone = telephone;
		this.skype = skype;
		this.misc = misc;
	}
	
	public int id(){
		return this.id;
	}
	
	public int updateId(int id) {
		int prev = id;
		this.id = id;
		return prev;
	}
	
	public String email(){
		return this.email;
	}
	public void updateEmail(String email) {
		this.email = email;
		this.states.modify();
	}
	public void setEmail(String email) {
		this.email=email;
	}
	
	public String mobile(){
		return this.mobile;
	}
	public void updateMobile(String mobile) {
		this.mobile = mobile;
		this.states.modify();
	}
	public void setMobile(String mobile) {
		this.mobile=mobile;
	}
	
	public String telephone(){
		return this.telephone;
	}
	public void updateTelephone(String telephone) {
		this.telephone = telephone;
		this.states.modify();
	}
	public void setTelephone(String telephone) {
		this.telephone=telephone;
	}
	
	public String skype(){
		return this.skype;
	}
	public void updateSkype(String skype) {
		this.skype = skype;
		this.states.modify();
	}
	public void setSkype(String skype) {
		this.skype=skype;
	}
	
	public String misc(){
		return this.misc;
	}
	public void updateMisc(String misc) {
		this.misc = misc;
		this.states.modify();
	}
	public void setMisc(String misc) {
		this.misc=misc;
	}
	
	public TouchPoint contactHistory(){
		return this.contactHistory;
	}
	public void updatecontactHistory(TouchPoint contactHistory) {
		this.contactHistory = contactHistory;
		this.states.modify();
	}
	public void setContactHistory(TouchPoint contactHistory) {
		this.contactHistory=contactHistory;
	}
	
	public CampStates states() {
		return this.states();
	}

	public String toJson() {
		return _toJson(this);
	}
	public static String _toJson(ContactDetails d) {
		return "{"+_toInnerJson(d)+"}";
	}
	public static String _toInnerJson(ContactDetails d) {
		String json = "";
		json += "\"id\":\""+d.id()+"\",";
		json += "\"email\":\""+d.email()+"\",";
		json += "\"mobile\":\""+d.mobile()+"\",";
		json += "\"telephone\":\""+d.telephone()+"\",";
		json += "\"skype\":\""+d.skype()+"\",";
		json += "\"misc\":\""+d.misc()+"\",";
		json += "\"contactHistory\":"+d.contactHistory().toJson();
		json += "\"states\":"+d.states().toJson();
		return json;
	}
	
	public ContactDetails fromJson(String json) {
		return _fromJson(json);
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
		TouchPoint contactHistory = TouchPointInterface._fromJSONObject(jo.getJSONObject("contactHistory"));
		ContactDetails d = new ContactDetails(id,email,mobile,telephone,skype,misc);
		d.setContactHistory(contactHistory);
		d.states().update(states);
		return d;
	}
}
