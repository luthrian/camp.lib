package com.camsolute.code.camp.lib.models.customer;

import java.sql.Timestamp;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Version;
import com.camsolute.code.camp.lib.utilities.Util;


public class TouchPoint implements TouchPointInterface{
	
	public static enum Status {
		CREATED, 
		UPDATED, 
		ISSUES_RAISED,
		ISSUES_OPEN, 
		ISSUES_RESOLVED,
		ISSUES_CLOSED,
		FOLLOWUP_REQUIRED, 
		FOLLOWUP_COMPLETED, 
		EXTERNAL_ESCALATION_REQUIRED, 
		INTERNAL_ESCALATION_REQUIRED, 
		EXTERNALLY_ESCALATED,
		INTERNALLY_ESCALATED,
		EXTERNAL_ESCALATION_RESOLVED,
		INTERNAL_ESCALATION_RESOLVED,
		FEEDBACK_SUBMITTED,
		FEEDBACK_REQUIRED,
		PRAISE_SUBMITTED,
		PRAISE_REQUIRED,
		COMMUNICATION_REQUIRED,
		COMMUNICATION_SUBMITTED,
		MODIFIED, DIRTY, CLEAN;
	}
	private int id = Util.NEW_ID;
	private int refId = Util.NEW_ID;
	private String businessKeyResponsible;
	private String businessIdResponsible;
	private String businessKeyCustomer;
	private String businessIdCustomer;
	private Timestamp date;
	private Timestamp nextDate; //ref
	private String topic; 
	private String minutes; //ref
	private CampInstance history = new CampInstance();
	private CampStates states = new CampStates();
	private Status status;
	private Status previousStatus;
	private Group group;
	private Version version;
	
	public TouchPoint(String businessKeyResponsible,String businessIdResponsible,String businessKeyCustomer,String businessIdCustomer,Timestamp date,String topic,String minutes){
		this.businessKeyResponsible=businessKeyResponsible;
		this.businessIdResponsible=businessIdResponsible;
		this.businessKeyCustomer=businessKeyCustomer;
		this.businessIdCustomer=businessIdCustomer;
		this.date=date;
		this.topic=topic;
		this.minutes=minutes;	
	}

	@Override
	public int id() {
		return id;
	}

	@Override
	public int updateId(int id) {
		int prev = id;
		this.id = id;
		return prev;
	}

	@Override
	public void setRefId(int id) {
		this.refId = id;
	}
	
	@Override
	public String businessKeyResponsible() {
		return businessKeyResponsible;
	}

	@Override
	public void setBusinessKeyResponsible(String businessKeyResponsible) {
		this.businessKeyResponsible = businessKeyResponsible;
	}

	@Override
	public String updateBusinessKeyResponsible(String businessKeyResponsible) {
		String prev = this.businessKeyResponsible;
		this.businessKeyResponsible = businessKeyResponsible;
		this.states.modify();
		return prev;
	}

	@Override
	public String businessIdResponsible() {
		return businessIdResponsible;
	}

	@Override
	public void setBusinessIdResponsible(String businessIdResponsible) {
		this.businessIdResponsible = businessIdResponsible;
	}

	public String updateBusinessIdResponsible(String businessIdResponsible) {
		String prev = this.businessIdResponsible;
		this.businessIdResponsible = businessIdResponsible;
		this.states.modify();
		return prev;
	}

	@Override
	public String businessKeyCustomer() {
		return businessKeyCustomer;
	}

	@Override
	public void setBusinessKeyCustomer(String businessKeyCustomer) {
		this.businessKeyCustomer = businessKeyCustomer;
	}

	@Override
	public String updateBusinessKeyCustomer(String businessKeyCustomer) {
		String prev = this.businessKeyCustomer;
		this.businessKeyCustomer = businessKeyCustomer;
		this.states.modify();
		return prev;
	}

	@Override
	public String businessIdCustomer() {
		return businessIdCustomer;
	}

	@Override
public void setBusinessIdCustomer(String businessIdCustomer) {
		this.businessIdCustomer = businessIdCustomer;
	}

	@Override
	public String updateBusinessIdCustomer(String businessIdCustomer) {
		String prev = this.businessIdCustomer;
		this.businessIdCustomer = businessIdCustomer;
		this.states.modify();
		return prev;
	}

	@Override
	public Timestamp date() {
		return date;
	}

	@Override
	public void setDate(Timestamp date) {
		this.date = date;
	}

	@Override
	public Timestamp updateDate(Timestamp date) {
		Timestamp prev = this.date;
		this.date = date;
		this.states.modify();
		return prev;
	}

	@Override
	public Timestamp nextDate() {
		return nextDate;
	}

	@Override
	public void setNextDate(Timestamp nextDate) {
		this.nextDate = nextDate;
	}

	@Override
	public Timestamp updateNextDate(Timestamp nextDate) {
		Timestamp prev = this.nextDate;
		this.nextDate = nextDate;
		this.states.modify();
		return prev;
	}

	@Override
	public String minutes() {
		return minutes;
	}

	@Override
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	@Override
	public String updateMinutes(String minutes) {
		String prev = this.minutes;
		this.minutes = minutes;
		this.states.modify();
		return prev;
	}

	@Override
	public String name() {
		return topic();
	}

	@Override
	public String updateName(String topic) {
		return updateTopic(topic);
	}

	@Override
	public void setName(String topic) {
		setTopic(topic);
	}

	public String topic() {
		return this.topic;
	}

	public String updateTopic(String topic) {
		String prev = this.topic;
		this.topic = topic;
		this.states.modify();
		return prev;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public Version version() {
		return this.version;
	}

	@Override
	public void updateVersion(String version) {
		this.version = new Version(version);
		this.states.modify();
	}

	@Override
	public void updateVersion(Version version) {
		this.version = version;
		this.states.modify();
	}

	@Override
	public void setVersion(String version) {
		this.version = new Version(version);
	}

	@Override
	public Group group() {
		return this.group;
	}

	@Override
	public void updateGroup(Group group) {
		this.group = group;
		this.states.modify();
	}

	@Override
	public void updateGroup(String group) {
		this.group = new Group(group);
		this.states.modify();
	}

	@Override
	public void setGroup(String group) {
		this.group = new Group(group);
	}

	@Override
	public String initialBusinessId() {
		return this.businessId();
	}

	@Override
	public String businessId() {
		String s = Util.DB._VS;
		return this.businessKeyCustomer+s+this.businessIdCustomer+s+this.businessKeyResponsible+this.businessIdResponsible;
	}

	@Override
	public String updateBusinessId(String newId) {
		String prev = this.onlyBusinessId();
		String[] keys = newId.split(Util.DB._VS);
		if(keys.length ==2) {
			this.businessIdCustomer = keys[0];
			this.businessIdResponsible = keys[1];
		}
		this.states.modify();
		return prev;
	}

	@Override
	public void setBusinessId(String newId) {
		String[] keys = newId.split(Util.DB._VS);
		if(keys.length ==2) {
			this.businessIdCustomer = keys[0];
			this.businessIdResponsible = keys[1];
		}
	}

	@Override
	public String onlyBusinessId() {
		return this.businessIdCustomer+Util.DB._VS+this.businessIdResponsible;
	}

	@Override
	public String businessKey() {
		return this.businessKeyCustomer+Util.DB._VS+this.businessKeyResponsible;
	}

	@Override
	public void updateBusinessKey(String businessKey) {
		String[] keys = businessKey.split(Util.DB._VS);
		if(keys.length ==2) {
			this.businessKeyCustomer = keys[0];
			this.businessKeyResponsible = keys[1];
		}
		this.states.modify();
	}

	@Override
	public void setBusinessKey(String businessKey) {
		String[] keys = businessKey.split(Util.DB._VS);
		if(keys.length ==2) {
			this.businessKeyCustomer = keys[0];
			this.businessKeyResponsible = keys[1];
		}
	}

	@Override
	public CampInstance history() {
		return this.history;
	}

	@Override
	public void setHistory(CampInstance instance) {
		this.history = instance;
	}

	@Override
	public int getObjectId() {
		return this.id;
	}

	@Override
	public String getObjectBusinessId() {
		return this.businessId();
	}

	@Override
	public CampInstance getObjectHistory() {
		return this.history();
	}

	@Override
	public int getRefId() {
		return this.refId;
	}

	@Override
	public CampStates states() {
		return this.states;
	}

	@Override
	public Enum<?> status() {
		return this.status;
	}

	@Override
	public Enum<?> updateStatus(Enum<?> status) {
		Status prev = this.status;
		this.status = (Status) status;
		this.states.modify();
		return prev;
	}

	@Override
	public Enum<?> updateStatus(String status) {
		Status prev = this.status;
		this.status = Status.valueOf(status);
		this.states.modify();
		return prev;
	}

	@Override
	public void setStatus(Enum<?> status) {
		this.status  = (Status)status;
	}

	@Override
	public void setStatus(String status) {
		this.status = Status.valueOf(status);
	}

	@Override
	public Enum<?> previousStatus() {
		return this.previousStatus;
	}

	@Override
	public void setPreviousStatus(Enum<?> status) {
		this.previousStatus = (Status) status;
	}

	@Override
	public void setPreviousStatus(String status) {
		this.previousStatus = Status.valueOf(status);
	}

	@Override
	public <T extends IsObjectInstance<T>> void cleanStatus(T object) {
		if(this.status.name().equals(Status.MODIFIED.name())){
			this.status = this.previousStatus;
			this.previousStatus = Status.CLEAN;
		}
	}

	@Override
	public String toJson() {
		return TouchPointInterface._toJson(this);
	}

	@Override
	public TouchPoint fromJson(String json) {
		return TouchPointInterface._fromJson(json);
	}
	
}
