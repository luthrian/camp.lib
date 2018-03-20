package com.camsolute.code.camp.lib.utilities;

import java.sql.Timestamp;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Version;

public class LogEntry<T extends IsObjectInstance<T>> implements LogEntryInterface<T> {
	private int id = Util.NEW_ID;
	private int objectId = Util.NEW_ID;
	private Class<T> objectType;
	private String objectBusinessId;
	private String objectBusinessKey;
	private Group group;
	private Version version;
	private CampInstance history = new CampInstance();
	private Timestamp timestamp = Util.Time.timestamp();
	private String objectJson;
	
	@SuppressWarnings("unchecked")
	public LogEntry(int objectId, String objectType, String objectBusinessId, String objectBusinessKey, Group group, Version version, CampInstance history, Timestamp timestamp, String objectJson) throws ClassNotFoundException {
		this.objectId = objectId;
		this.objectType = (Class<T>) Class.forName(objectType);
		this.objectBusinessId = objectBusinessId;
		this.objectBusinessKey = objectBusinessKey;
		this.group = group;
		this.version = version;
		this.timestamp = timestamp;
		this.history = history;
		this.objectJson = objectJson;
	}

	@SuppressWarnings("unchecked")
	public LogEntry(int id, int objectId, String objectType, String objectBusinessId, String objectBusinessKey, Group group, Version version, CampInstance history, Timestamp timestamp, String objectJson) throws ClassNotFoundException {
		this.id = id;
		this.objectId = objectId;
		this.objectType = (Class<T>) Class.forName(objectType);
		this.objectBusinessId = objectBusinessId;
		this.objectBusinessKey = objectBusinessKey;
		this.group = group;
		this.version = version;
		this.timestamp = timestamp;
		this.history = history;
		this.objectJson = objectJson;
	}

	@Override
	public int id() {
		return this.id;
	}
	@Override
	public int objectId() {
		return this.objectId;
	}
	@Override
	public Class<T> objectType() {
		return this.objectType;
	}
	@Override
	public String objectBusinessId() {
		return this.objectBusinessId;
	}
	@Override
	public String objectBusinessKey() {
		return this.objectBusinessKey;
	}
	@Override
	public Group group() {
		return this.group;
	}
	@Override
	public Version version() {
		return this.version;
	}
	@Override
	public Timestamp timestamp() {
		return this.timestamp;
	}
	@Override
	public CampInstance history() {
		return this.history;
	}
	@Override
	public String objectJson() {
		return this.objectJson;
	}
	@Override
	public String toJson() {
		return LogEntryInterface._toJson(this);
	}
	
	@Override
	public LogEntry<T> fromJson(String json) {
		return LogEntryInterface._fromJson(json);
	}
}
