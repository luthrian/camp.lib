package com.camsolute.code.camp.lib.models.customer;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampInstanceInterface;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Version;
import com.camsolute.code.camp.lib.utilities.Util;

public class Address implements IsObjectInstance<Address> {
	public static enum Status {
		CREATED,
		NEW,
		UNVERIFIED,
		VERIFIED,
		ACTIVE,
		DEACTIVATED,
		MODIFIED,
		DIRTY,
		CLEAN
		;
	};
	
	public static enum Type {
		PRIVATE,
		BUSINESS,
		NON_GOVERNMENT_ORGANISATION,
		GOVERNMENT,
		MILITARY
		;
	}
	public static enum Origin {
		LOCAL,
		FOREIGN
	}
	

	private int id;
	private String country;
	private String state;
	private String postCode;
	private String city;
	private String street;
	private String streetNumber;
	private String floor="/";
	private String roomNumber="/";
	private String businessKey;
	private Group group;
	private Version version;
	private Status status = Status.CREATED;
	private Status previousStatus = Status.CLEAN;
	private CampInstance history = new CampInstance();
	private CampStates states = new CampStates();
	
	public Address(int id, String country, String state, String postCode, String city, String street, String streetNumber, String floor, String roomNumber) {
		this.id = id;
		this.country=country;
		this.state=state;
		this.postCode=postCode;
		this.city=city;
		this.street=street;
		this.streetNumber = streetNumber;
		this.floor = floor;
		this.roomNumber = roomNumber;
	}

	public Address(String country, String state, String postCode, String city, String street, String streetNumber, String floor, String roomNumber) {
		this.country=country;
		this.state=state;
		this.postCode=postCode;
		this.city=city;
		this.street=street;
		this.streetNumber = streetNumber;
		this.floor = floor;
		this.roomNumber = roomNumber;
	}
	
	public String country() {
		return this.country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void updateCountry(String country) {
		this.country = country;
		this.states.modify();
	}
	public String state() {
		return this.state;
	}
	public void setState(String state) {
		this.state=state;
	}
	public void updateState(String state) {
		this.state=state;
		this.states.modify();
	}
	public String postCode(){
		return this.postCode;
	}
	public void setPostCode(String postCode){
		this.postCode=postCode;
	}
	public void updatePostCode(String postCode){
		this.postCode=postCode;
		this.states.modify();
	}
	public String city(){
			return this.city;
	}
	public void setCity(String city){
			this.city=city;
	}
	public void updateCity(String city){
			this.city=city;
			this.states.modify();
	}
	public String street(){
			return this.street;
	}
	public void setStreet(String street){
			this.street=street;
	}
	public void updateStreet(String street){
			this.street=street;
			this.states.modify();
	}
	public String streetNumber(){
		return this.streetNumber;
	}
	public void setStreetNumber(String streetNumber){
		this.streetNumber=streetNumber;
	}
	public void updateStreetNumber(String streetNumber){
		this.streetNumber=streetNumber;
		this.states.modify();
	}
	public String floor(){
		return this.floor;
	}
	public void setFloor(String floor){
		this.floor=floor;
	}
	public void updatefloor(String floor){
		this.floor=floor;
		this.states.modify();
	}
	public String roomNumber(){
		return this.roomNumber;
	}
	public void setRoomNumber(String roomNumber){
		this.roomNumber=roomNumber;
	}
	public void updateRoomNumber(String roomNumber){
		this.roomNumber=roomNumber;
		this.states.modify();
	}
	@Override
	public int id() {
		return this.id;
	}
	@Override
	public int updateId(int id) {
		int prev = this.id;
		this.id=id;
		return prev;
	}
	@Override
	public String name() {
		return businessId();
	}
	@Override
	public String updateName(String name) {
		return updateBusinessId(name);
	}
	@Override
	public void setName(String name) {
		setBusinessId(name);
	}
	@Override
	public Version version() {
		return this.version;
	}
	@Override
	public void updateVersion(String version) {
		this.version=new Version(version);
		this.states.modify();
	}
	@Override
	public void updateVersion(Version version) {
		this.version=version;
		this.states.modify();
	}
	@Override
	public void setVersion(String version) {
		this.version=new Version(version);
	}
	@Override
	public Group group() {
		return this.group;
	}
	@Override
	public void updateGroup(Group group) {
		this.group=group;
		this.states.modify();
	}
	@Override
	public void updateGroup(String group) {
		this.group=new Group(group);
		this.states.modify();
	}
	@Override
	public void setGroup(String group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String initialBusinessId() {
		return null;
	}
	@Override
	public String businessId() {
		return country+Util.DB._VS
				+state+Util.DB._VS
				+postCode+Util.DB._VS
				+city+Util.DB._VS
				+street+Util.DB._VS
				+streetNumber+Util.DB._VS
				+floor+Util.DB._VS
				+roomNumber;
	}
	@Override
	public String updateBusinessId(String newId) {
		String prev = country+Util.DB._VS
				+state+Util.DB._VS
				+postCode+Util.DB._VS
				+city+Util.DB._VS
				+street+Util.DB._VS
				+streetNumber+Util.DB._VS
				+floor+Util.DB._VS
				+roomNumber;
		String[] values = newId.split(Util.DB._VS);
		country=values[0];
		state=values[1];
		postCode=values[2];
		city=values[3];
		street=values[4];
		streetNumber=values[5];
		floor=values[6];
		roomNumber=values[7];
		this.states.modify();
		return prev;
	}
	@Override
	public void setBusinessId(String newId) {
		String[] values = newId.split(Util.DB._VS);
		country=values[0];
		state=values[1];
		postCode=values[2];
		city=values[3];
		street=values[4];
		streetNumber=values[5];
		floor=values[6];
		roomNumber=values[7];
	}
	@Override
	public String onlyBusinessId() {
		return businessId();
	}
	@Override
	public String businessKey() {
		return this.businessKey;
	}
	@Override
	public void updateBusinessKey(String businessKey) {
		this.businessKey = businessKey;
		this.states.modify();
	}
	@Override
	public void setBusinessKey(String businessKey) {
		this.businessKey =businessKey;
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
		return id();
	}
	@Override
	public String getObjectBusinessId() {
		return businessId();
	}
	@Override
	public CampInstance getObjectHistory() {
		return history();
	}
	@Override
	public int getRefId() {
		return 0;
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
		this.status = (Status)status;
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
		this.status = (Status) status;
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
		if(this.status.name().equals(Status.MODIFIED.name())) {
			this.status = this.previousStatus;
			this.previousStatus = Status.CLEAN;
		}
	}
	@Override
	public String toJson() {
		return AddressInterface._toJson(this);
	}
	@Override
	public Address fromJson(String json) {
		return AddressInterface._fromJson(json);
	}
}
