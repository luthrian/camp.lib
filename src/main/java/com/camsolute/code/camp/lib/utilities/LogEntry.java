/*******************************************************************************
 * Copyright (C) 2018 Christopher Campbell
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * 	Christopher Campbell - all code prior and post initial release
 ******************************************************************************/
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
	public LogEntry(T object) throws ClassNotFoundException {
		this.objectId = object.id();
		this.objectType = (Class<T>) object.getClass();
		this.objectBusinessId = object.businessId();
		this.objectBusinessKey = object.businessKey();
		this.group = object.group();
		this.version = object.version();
		this.timestamp = Util.Time.timestamp();
		this.history = object.history();
		this.objectJson = object.toJson();
	}

	@SuppressWarnings("unchecked")
	public LogEntry(int id, T object) throws ClassNotFoundException {
		this.id = id;
		this.objectId = object.id();
		this.objectType = (Class<T>) object.getClass();
		this.objectBusinessId = object.businessId();
		this.objectBusinessKey = object.businessKey();
		this.group = object.group();
		this.version = object.version();
		this.timestamp = Util.Time.timestamp();
		this.history = object.history();
		this.objectJson = object.toJson();
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
