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
/**
 ******************************************************************************
 * Copyright (C) 2017 Christopher Campbell (campbellccc@gmail.com)
 *
 * <p>This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * <p>You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 *
 * <p>Contributors: Christopher Campbell (campbellccc@gmail.com) - all code prior and post initial
 * release
 ******************************************************************************
 */
package com.camsolute.code.camp.lib.models;

import java.util.EnumMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

/*import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;
*/
import com.camsolute.code.camp.lib.types.*;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.order.Order;
import com.camsolute.code.camp.lib.models.process.Process.ProcessType;
import com.camsolute.code.camp.lib.models.process.ProductAttributeProcess;
import com.camsolute.code.camp.lib.models.process.ProductAttributeProcessList;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.models.process.ProductAttributeProcess;
import com.camsolute.code.camp.lib.models.process.ProductAttributeProcessList;
import com.camsolute.code.camp.lib.models.rest.Message;
import com.camsolute.code.camp.lib.models.rest.MessageList;
import com.camsolute.code.camp.lib.models.rest.OrderProcessMessage;
import com.camsolute.code.camp.lib.models.rest.Request;
import com.camsolute.code.camp.lib.models.rest.Request.Principal;
import com.camsolute.code.camp.lib.models.rest.Request.RequestType;
import com.camsolute.code.camp.lib.models.rest.OrderProcessMessage.CustomerOrderMessage;
import com.camsolute.code.camp.lib.models.rest.ProductAttributeProcessMessage;
import com.camsolute.code.camp.lib.models.rest.ProductAttributeProcessMessage.ProductAttributeMessage;
import com.camsolute.code.camp.lib.utilities.Util;

public abstract  class Attribute<U extends Value<?>> implements AttributeInterface<U> {

	/*
	 * Attribute definition Id
	 */
  private int id;
  private int attributeId;
  
  private String name;
  private AttributeType type;
  private String defaultValue = null;

  private int valueId = 0;
  private U value = null;

  private String businessKey = null;
  private Group group = null;
  private Version version = null;
  private int position = 0;

  private Group attributeGroup = null;
  private int attributePosition = 0;
  private String attributeBusinessId = null;

  private boolean hasParent = false;
  private int parentId = 0;
  private int attributeParentId = 0;
  private Attribute<? extends Value<?>> parent = null;

  private CampStates states = new CampStates();
  private CampStates valueStates = new CampStates();
  private CampInstance history = new CampInstance();
  private CampInstance valueHistory = new CampInstance(); 

  private Enum<?> status = AttributeStatus.CREATED;
  private Enum<?> previousStatus = AttributeStatus.CLEAN;
  
  private ProcessList processes = new ProcessList();
  
  public Attribute(String name, AttributeType type, String defaultValue) {
    this.name = name;
    this.type = type;
    this.defaultValue = defaultValue;
  }

  public Attribute(String name, AttributeType type, String defaultValue, Version version, Group group) {
    this(name, type, defaultValue);
    this.version = version;
    this.group = group;
  }

  public Attribute(int id, String name, AttributeType type, String defaultValue, Version version, Group group) {
    this(name, type, defaultValue);
    this.version = version;
    this.group = group;
    this.id = id;
  }

  
  @Override
  public int id() {
    return this.id;
  }
  
  @Override
  public int updateId(int id) {
    int prev = this.id;
    this.id = id;
    return prev;
  }

  
  @Override
  public int attributeId() {
    return this.attributeId;
  }
  
  @Override
  public int attributeId(int attributeId) {
    int prev = this.attributeId;
    this.attributeId = attributeId;
    return prev;
  }

  
  @Override
  public String name() {
    return name;
  }
  

  @Override
  public String updateName(String name) {
    String prev = this.name;
    this.name = name;
    this.states.modify();
    return prev;
  }
  
  @Override
  public void setName(String name) {
  	this.name = name;
  }

  
  @Override
  public AttributeType attributeType() {
    return type;
  }

  
  @Override
  public AttributeType updateAttributeType(AttributeType type) {
    AttributeType prev = this.type;
    this.type = type;
    this.states.modify();
    return prev;
  }

  @Override
  public void setAttributeType(AttributeType type) {
    this.type = type;
  }

  
  @Override
  public int valueId(int valueId) {
    int retValueId = this.valueId;
    return retValueId;
  }
  
  @Override
  public int valueId() {
  	if(value != null) return value.id();
    return valueId;
  };

  
  @Override
  public U updateValue(U value){
      U prev = this.value;
      this.value = value;
      this.valueId = value.id();
      this.valueStates.modify();
      return prev;
  }
  
  @Override
  public void setValue(U value) {
  	this.value = value;
  }

  
  @Override
  public U value(){
      return this.value;
  }

  
  @Override
  public String defaultValue() {
    return this.defaultValue;
  }
  
  @Override
  public String updateDefaultValue(String value) {
    String prev = this.defaultValue;
    this.defaultValue = value;
    this.states.modify();
    return prev;
  }

  @Override
  public void setDefaultValue(String value) {
    this.defaultValue = value;
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
    public void updateGroup(String group) {
        this.group = new Group(group);
        this.states.modify();
    }

  
  @Override
  public void updateGroup(Group group) {
    this.group = group;
    this.states.modify();
  }
  
  @Override
  public void setGroup(String group) {
  	this.group = new Group(group);
  }
  
  @Override
  public Group attributeGroup(){
      return this.attributeGroup;
  }

  @Override
  public Group updateAttributeGroup(String group){
      Group prev = this.attributeGroup;
      this.attributeGroup = new Group(group);
      this.valueStates.modify();
      return prev;
  }

  @Override
  public Group updateAttributeGroup(Group group){
      Group prev = this.group;
      this.attributeGroup = group;
      this.valueStates.modify();
      return prev;
  }

  @Override
  public void setAttributeGroup(String group){
      this.attributeGroup = new Group(group);
  }

  
  
  @Override
  public int position() {
    return this.position;
  }

  
  @Override
  public int updatePosition(int position) {
    int prev = this.position;
    this.position = position;
    this.states.modify();
    return prev;
  }

  @Override
  public void setPosition(int position) {
  	this.position = position;
  }
  
  @Override
  public int attributePosition() {
	  return this.attributePosition;
  }

  @Override
  public int updateAttributePosition(int position) {
	  int prev = this.attributePosition;
	  this.attributePosition = position;
	  this.valueStates.modify();
	  return prev;
  }

  @Override
  public void setAttributePosition(int position) {
  	this.attributePosition = position;
  }

  @Override
  public String businessKey() {
    return this.businessKey;
  }

  @Override
  public void setBusinessKey(String key) {
    this.businessKey = key;
  }

  @Override 
  public void updateBusinessKey(String key) {
  	this.businessKey = key;
  	this.states.modify();
  }
  
  @Override
  public String businessId() {
    return this.name + Util.DB._NS + this.getObjectId();
  }

  @Override
  public String updateBusinessId(String id) {
    String prev = this.name;
    this.name = id;
    this.states.modify();
    return prev;
  }

  @Override
  public void setBusinessId(String businessId) { 
  	this.name = businessId; 
  }
  
  @Override
  public String onlyBusinessId() {
    return this.name;
  }

  @Override
  public String initialBusinessId() {
    return this.name + Util.DB._NS + 0; // initial value id = 0
  }

  
  @Override
  public String attributeBusinessKey() {
    return attributeBusinessId;
  }

  
  @Override
  public String updateAttributeBusinessKey(String id) {
    String prev = this.attributeBusinessId;
    this.attributeBusinessId = id;
    this.valueStates.modify();
    return prev;
  }

  @Override
  public void setAttributeBusinessKey(String id) {
    this.attributeBusinessId = id;
  }

  @Override
  public boolean hasParent() {
    return hasParent;
  }

  @Override
  public void setHasParent(boolean hasParent) {
    this.hasParent = hasParent;
  }

  @Override
  public int parentId() {
    return this.parentId;
  }

  @Override
  public void parentId(int id) {
    this.parentId = id;
  }

  @Override
  public int attributeParentId() {
    return this.attributeParentId;
  }

  @Override
  public void attributeParentId(int id) {
    this.attributeParentId = id;
  }

  @SuppressWarnings("unchecked")
	@Override
  public <X extends Value<?>> Attribute<X> parent() {
    return (Attribute<X>) this.parent;
  }

  @Override
  public <X extends Value<?>> Attribute<X> parent(Attribute<X> parent) {
    @SuppressWarnings("unchecked")
		Attribute<X> prev = (Attribute<X>) this.parent;
    this.parent = parent;
    return prev;
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
		Enum<?> prev = this.status;
		this.status = (AttributeStatus) status;
		this.states.modify();
		return prev;
	}

	@Override
	public Enum<?> updateStatus(String status) {
		Enum<?> prev = this.status;
		this.status = AttributeStatus.valueOf(status);
		this.states.modify();
		return prev;
	}
	
	@Override
	public void setStatus(Enum<?> status) {
		this.status = (AttributeStatus) status;
	}

	@Override
	public void setStatus(String status) {
		this.status = AttributeStatus.valueOf(status);
	}

	@Override
	public Enum<?> previousStatus() {
		return this.previousStatus;
	}

	@Override
	public void setPreviousStatus(Enum<?> status) {
		this.previousStatus = (AttributeStatus) status;
	}

	@Override
	public void setPreviousStatus(String status) {
		this.previousStatus = AttributeStatus.valueOf(status);
	}

	@Override
	public <T extends IsObjectInstance<T>> void cleanStatus(T object) {
		if(!object.previousStatus().equals(AttributeStatus.CLEAN)) {
			object.setStatus(object.previousStatus());
			object.setPreviousStatus(AttributeStatus.CLEAN);
		}
	}

	@Override
	public CampStates valueStates() {
		return this.valueStates;
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
	public CampInstance valueHistory() {
		return this.valueHistory;
	}
	
	@Override
	public void setValueHistory(CampInstance valueHistory) {
		this.valueHistory = valueHistory;
	}

  @Override
	public int getObjectId() {
		return this.attributeId;
	}

  @Override
	public String getObjectBusinessId() {
		return this.attributeBusinessId;
	}

  @Override
  public CampInstance getObjectHistory() {
  	return this.valueHistory;
  }
  
	@Override
	public int getRefId() {
		return 0;
	}

  @Override
	public int getValueObjectId() {
		return this.attributeId;
	}

	@Override
	public int getValueRefId() {
		return 0;
	}


	@Override
	public abstract U valueFromString(String value);
	
	public static enum AttributeType {
    _integer,
    _string,
    _boolean,
    _datetime,
    _date,
    _time,
    _timestamp,
    _enum,
    _set,
    _text,
    _table,
    _complex,
    _list,
    _map,
    _palist,
    _token,
    _process;
/*    private static Map<String, AttributeType> namesMap = new HashMap<String, AttributeType>(17);

    static {
        namesMap.put("integer",_integer);
        namesMap.put("string",_string);
        namesMap.put("boolean",_boolean);
        namesMap.put("datetime",_datetime);
        namesMap.put("date",_date);
        namesMap.put("time",_time);
        namesMap.put("timestamp",_timestamp);
        namesMap.put("enum",_enum);
        namesMap.put("set",_set);
        namesMap.put("text",_text);
        namesMap.put("table",_table);
        namesMap.put("complex",_complex);
        namesMap.put("list",_list);
        namesMap.put("map",_map);
        namesMap.put("palist",_palist);
        namesMap.put("token",_token);
        namesMap.put("process",_process);
    }

    @JsonCreator
    public static AttributeType forValue(String value) {
        return namesMap.get(StringUtils.lowerCase(value));
    }

    @JsonValue
    public String toValue(){
        for(Entry<String, AttributeType> entry: namesMap.entrySet()) {
            if(entry.getValue() == this) return entry.getKey();
        }
        return null;
    }
 */ }

  public static EnumMap<AttributeType, String[]> attributeMatrix = null;

  static {
    attributeMatrix = new EnumMap<AttributeType, String[]>(AttributeType.class);
    attributeMatrix.put(AttributeType._integer, new String[] {"integer", "int"});
    attributeMatrix.put(AttributeType._string, new String[] {"string", "str"});
    attributeMatrix.put(AttributeType._boolean, new String[] {"boolean", "bol"});
    attributeMatrix.put(AttributeType._datetime, new String[] {"datetime", "dtm"});
    attributeMatrix.put(AttributeType._date, new String[] {"date", "dat"});
    attributeMatrix.put(AttributeType._time, new String[] {"time", "tim"});
    attributeMatrix.put(AttributeType._timestamp, new String[] {"timestamp", "tst"});
    attributeMatrix.put(AttributeType._enum, new String[] {"enum", "enm"});
    attributeMatrix.put(AttributeType._set, new String[] {"set", "set"});
    attributeMatrix.put(AttributeType._text, new String[] {"text", "txt"});
    attributeMatrix.put(AttributeType._token, new String[] {"token", "tkn"});
    attributeMatrix.put(AttributeType._process, new String[] {"process", "prc"});
    attributeMatrix.put(AttributeType._table, new String[] {"table", "t"});
    attributeMatrix.put(AttributeType._complex, new String[] {"complex", "c"});
    attributeMatrix.put(AttributeType._list, new String[] {"list", "l"});
    attributeMatrix.put(AttributeType._map, new String[] {"map", "m"});
    attributeMatrix.put(AttributeType._palist, new String[] {"palist", "p"});
  }

  public static enum AttributeContainerType {
    _product,
    _complex,
    _table,
    _list;
/*    private static Map<String, AttributeContainerType> namesMap = new HashMap<String, AttributeContainerType>(4);

    static {
        namesMap.put("product",_product);
        namesMap.put("complex",_complex);
        namesMap.put("table",_table);
        namesMap.put("list",_list);
    }

    @JsonCreator
    public static AttributeContainerType forValue(String value) {
        return namesMap.get(StringUtils.lowerCase(value));
    }

    @JsonValue
    public String toValue(){
        for(Entry<String, AttributeContainerType> entry: namesMap.entrySet()) {
            if(entry.getValue() == this) return entry.getKey();
        }
        return null;
    }
*/  }

  public static enum DaoEndpoint {
    _database,
    _rest_service;
/*    private static Map<String, DaoEndpoint> namesMap = new HashMap<String, DaoEndpoint>(2);

    static {
        namesMap.put("database",_database);
        namesMap.put("rest_service",_rest_service);
    }

    @JsonCreator
    public static DaoEndpoint forValue(String value) {
        return namesMap.get(StringUtils.lowerCase(value));
    }

    @JsonValue
    public String toValue(){
        for(Entry<String, DaoEndpoint> entry: namesMap.entrySet()) {
            if(entry.getValue() == this) return entry.getKey();
        }
        return null;
    }
*/  }


  public static enum AttributeStatus {
  	CREATED,
  	UPDATED,
  	SUBMITTED,
  	INREVIEW,
  	INDESIGN,
  	RELEASED,
  	RECALLED,
  	ENDOFLIFE,
  	CLEAN,
  	DIRTY,
  	MODIFIED;
  }
  
  /**
   * Request a generic attribute object instance. The term 'generic' is an object aspect indicating that an attribute 
   * object instance is not associated with a specific value. Once a generic attribute object instance is associated 
   * with a value it receives the object aspect 'specific'. 
   *  
   * All attribute object aspects (such as the name or type aspects) which remain static during the life-cycle of an 
   * attribute object instance are generic by nature. 
   * All attribute object aspects (such as the status or value aspects) which are subject to change during the 
   * life-cycle of an attribute object instance are specific in nature. 
   * <br><b>NOTE:</b> The <code>defaultValue</code> parameter contains the String representation of a value to be used
   * when an generic attribute object instance is displayed prior to being associated with a specific value.
   * This is not true for attribute objects that are parent attribute objects. In this case the <code>defaultValue</code>
   * holds a comma separated list of technical id's with which all associated child attribute objects can be referenced.
   * @param name attribute name
   * @param type	 attribute type enum
   * @param defaultValue default value of attribute as json string
   * @param <U> extends <code>Value</code>. Value aspect of the attribute.
   * @param <X> extends <code>Attribute&lt;U&gt;</code>
   * @return newly created and persisted attribute
   */
  @SuppressWarnings("unchecked")
	public static <U extends Value<?>,X extends Attribute<U>> X createAttribute(String name, AttributeType type, String defaultValue) {
    switch (type) {
      case _integer:
        return  (X) new CampInteger(name, defaultValue);
      case _string:
        return (X) new CampString(name, defaultValue);
      case _boolean:
        return (X) new CampBoolean(name, defaultValue);
      case _datetime:
        return (X) new CampDateTime(name, defaultValue);
      case _date:
        return (X) new CampDate(name, defaultValue);
      case _time:
        return (X) new CampTime(name, defaultValue);
      case _timestamp:
        return (X) new CampTimestamp(name, defaultValue);
      case _enum:
    	  return (X) new CampEnum(name,defaultValue);
      case _set:
        return (X) new CampSet(name,defaultValue);
      case _text:
        return (X) new CampText(name, defaultValue);
      case _table:
        return (X) new CampTable(name, defaultValue);
      case _complex:
    	  return (X) new CampComplex(name, defaultValue);
      case _list:
    	  return (X) new CampList(name,defaultValue);
      case _map:
    	  return (X) new CampMap(name,defaultValue);
      default:
        break;
    }
    return null;
  }

  public static AttributeType toType(String typeAsString) {
    for (AttributeType type : attributeMatrix.keySet()) {
      String[] t = attributeMatrix.get(type);
      if (typeAsString.equals(t[0]) || typeAsString.equals(t[1])) {
        return type;
      }
    }
    return null;
  }

  public static String typeToL(AttributeType type) {
    return attributeMatrix.get(type)[0];
  }

  public static String typeToS(AttributeType type) {
    return attributeMatrix.get(type)[1];
  }




	@Override
	public String toJson() {
		return AttributeInterface._toJson(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Attribute<U> fromJson(String json) {
		return (Attribute<U>) AttributeInterface._fromJson(json);
	}
	@Override
	public ProcessList processInstances() {
		return processes;
	}
	@Override
	public void addProcess(Process<Attribute<U>> process) {
		process.states().modify();
		processes.add(process);
		states.modify();
	}
	@Override
	public void addProcesses(ProcessList processes) {
		for(Process<?> p: processes) {
			p.states().modify();
		}
		processes.addAll(processes);
		this.states.modify();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Process<Attribute<U>> deleteProcess(String instanceId) {
		int count = 0;
		for(Process<?> op: processes) {
			if(op.instanceId().equals(instanceId)){
				this.states.modify();
				return (Process<Attribute<U>>) processes.remove(count);
			}
			count++;
		}
		return null;
	}

	@Override
	public void setProcesses(ProcessList pl) {
		this.processes = pl;
	}
	
	@Override
	public ProcessList processes() {
		return processes;
	}
	@Override
	public ProcessList processes(ProcessType type) {
		ProcessList opl = new ProcessList();
		for(Process<?> op: processes){
			if(op.type().name().equals(type.name())){
				opl.add((ProductAttributeProcess<?>)op);
			}
		}
		return opl;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void notifyProcesses() {
		for(Process<?> op:processes) {
			((Process<Attribute<U>>)op).notify(this);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void notifyProcesses(ProcessType type) {
		for(Process<?> op:processes) {
			if(op.type().name().equals(type.name())){
				((Process<Attribute<U>>)op).notify(this);
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void notifyProcesses(Enum<?> event) {
		for(Process<?> op: processes){
			((Process<Attribute<U>>)op).notify(this, event);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void notifyProcesses(ProcessType type, Enum<?> event) {
		for(Process<?> op: processes){
			if(op.type().name().equals(type.name())){
				((Process<Attribute<U>>)op).notify(this, event);
			}
		}
	}

	@Override
	public Message prepareMessage(String insanceId, Enum<?> message) {
		ProductAttributeMessage msg = ProductAttributeMessage.valueOf(message.name());
		ProductAttributeProcessMessage m = new ProductAttributeProcessMessage(msg, this, this.attributeBusinessKey());
		for(Process<?> p: processes){
			m.setProcessInstanceId(p.instanceId());
			m.setTenantId(p.tenantId());
		}
		return m;
	}
	@Override
	public MessageList prepareMessages(Enum<?> message) {
		ProductAttributeMessage msg = ProductAttributeMessage.valueOf(message.name());
		
		MessageList ml = new MessageList();
		
		for(Process<?> p: processes){
			ProductAttributeProcessMessage m = new ProductAttributeProcessMessage(msg, this,this.attributeBusinessKey());
			m.setProcessInstanceId(p.instanceId());
			m.setTenantId(p.tenantId());
			ml.add(m);
		}
		
		return ml;
	}
	@Override
	public Request<?> prepareRequest(Principal principal, RequestType type) {
		return new Request<Attribute<U>>(this, principal, type);
	}
	

}
