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
package com.camsolute.code.camp.lib.models;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.AttributeSerialization;
import com.camsolute.code.camp.lib.contract.HasDefaultValue;
import com.camsolute.code.camp.lib.contract.HasPosition;
import com.camsolute.code.camp.lib.contract.HasProcess;
import com.camsolute.code.camp.lib.contract.HasValue;
import com.camsolute.code.camp.lib.contract.HasValueHistory;
import com.camsolute.code.camp.lib.contract.HasValueId;
import com.camsolute.code.camp.lib.contract.HasValueStates;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.contract.core.Value;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.models.process.ProductAttributeProcess;
import com.camsolute.code.camp.lib.models.process.ProductAttributeProcessList;
import com.camsolute.code.camp.lib.types.*;
import com.camsolute.code.camp.lib.utilities.Util;



public interface AttributeInterface<U extends Value<?>> extends HasValue<U>, HasValueId, HasValueHistory, HasValueStates, HasDefaultValue, HasPosition, HasProcess<Attribute<U>>, IsObjectInstance<Attribute<U>>, AttributeSerialization<Attribute<U>> {
	
	public static final Logger LOG = LogManager.getLogger(AttributeInterface.class);
	
	public static String fmt = "[%15s] [%s]";
	
	/**
	 * ask for the id of the attribute type
	 * @return
	 */
	/**
	 * ask to set the id of the attribute type and return the id previously set or 0 of no id was previously set.
	 * @param id
	 * @return
	 */

	/**
	 * ask for the id of the attribute value id
	 * @return attribute id
	 */
	public int attributeId();
	/**
	 * ask to set the id of the attribute value and return the id previously set or 0 of no id was previously set.
	 * @param attributeId id
	 * @return previous id
	 */
	public int attributeId(int attributeId);

	/**
	 * ask for the name of the attribute type
	 * @return
	 */
	/**
	 * ask to set the name of the attribute type and return the name previously set or Null of no name was previously set.
	 * @param name
	 * @return
	 */

	/**
	 * ask for the type of attribute
	 * @return type as attributeType enum
	 */
	public AttributeType attributeType();

	/**
	 * ask to set the type of attribute
	 * @param type (AttributeType enum)
	 * @return value type was set to previously as AttributeType enum
	 */
	public AttributeType updateAttributeType(AttributeType type);

	public void setAttributeType(AttributeType type);

	/**
	 * ask for the attribute group of the attribute type
	 * @return attribute group
	 */
	public Group attributeGroup();
  /**
   * ask to set the attribute group name.
   * @param group <code>Group</code> value of type group being set.
   * @return the previous type group name or null if non was set
   */
   public Group updateAttributeGroup(Group group);
	/**
	 * ask to set the attribute group name.
	 * @param group <code>String</code> value of type group being set.
	 * @return the previous type group name or null if non was set
	 */
	public Group updateAttributeGroup(String group);

	public void setAttributeGroup(String group);
	
	/**
	 * ask for the attribute position of the attribute type
	 * @return attribute position
	 */
	public int attributePosition();
	/**
	 * ask to set the attribute position name.
	 * @param position <code>String</code> value of attribute position being set.
	 * @return the previous attribute position or 0 if non was set
	 */
	public int updateAttributePosition(int position);

	public void setAttributePosition(int position);
	
	
	/**
	 * ask for the attribute business id
	 * @return the business key of the business entity currently responsible for the order position/product the attribute's value aspects are associated with 
	 */
	public String attributeBusinessKey();
	
	/**
	 * ask to set the attribute business key. (transient business key related to value aspects of )
	 * @param id <code>String</code> value of business id being set.
	 * @return the previous attribute position or 0 if non was set
	 */
	public String updateAttributeBusinessKey(String id);
	
	public void setAttributeBusinessKey(String id);
	
	/**
	 * ask if attribute type has parent
	 * @return boolean has parent
	 */
	public boolean hasParent();
	/**
	 * set attribute has parent flag
	 * @param hasParent <code>Boolean</code> flag indicating the attribute is associated with a parent attribute.
	 */
	public void setHasParent(boolean hasParent);

	/**
	 * Request an attribute object to provide its association to a parent attribute via the parent attribute's
	 * technical id.
	 * The attribute object must respond to the request with a positive integer value.
	 * If the attribute object does not associated with a parent attribute then the attribute object
	 * must respond to the request with an integer value 0.
	 * @return <code>int</code> The technical id value of the parent attribute as an integer.
	 */
	public int parentId();

	/**
	 * Request to associate an attribute object to a parent attribute's persisted definition aspects via the technical id. The attribute object
	 * will only fulfill this request if there is no current association to a parent attribute via its technical id.
	 *
	 * @param id <code>int</code> Integer value of the technical id.
	 */
	public void parentId(int id);

	/**
	 * Request an attribute object to provide its association to a parent attribute's persisted value aspects via the parent attribute's
	 * technical id.
	 * The attribute object must respond to the request with a positive integer value.
	 * If the attribute object does not associated with a parent attribute then the attribute object
	 * must respond to the request with an integer value 0.
	 * @return <code>int</code> The technical id value of the parent attribute as an integer.
	 */
	public int attributeParentId();

	/**
	 * Request to associate an attribute object to a parent attribute's persisted value aspects via the technical id. The attribute object
	 * will only fulfill this request if there is no current association to a parent attribute via its technical id.
	 *
	 * @param id <code>int</code> Integer value of the technical id.
	 */
	public void attributeParentId(int id);

	/**
	 * ask for a reference to the parent object instance referenced by this attribute type
	 * @param <X> the value aspect of the attribute. Extends the <code>Value</code> 
	 * @return <code>Attribute&lt;X&gt;</code> 
	 */
	public <X extends Value<?>> Attribute<X> parent();
	/**
	 * set attribute type parent reference
	 * @param parent the parent attribute
	 * @param <X> extends <code>Value</code>. Encapsulates the value aspect of the attribute.  
	 * @return <code>Attribute&lt;X&gt;</code> previous parent attribute
	 */
	public <X extends Value<?>> Attribute<X> parent(Attribute<X> parent);

	public U valueFromString(String value);

    public static String _toString(Attribute<? extends Value<?>> a){
        return _toJson(a);
    }

    public static Attribute<? extends Value<?>> _fromString(String jsonString){
        return AttributeInterface._fromJson(jsonString);
    }

    public static Attribute<? extends Value<?>> _fromJson(String json){
    	return _fromJSONObject(new JSONObject(json));
    }
    
	public static  Attribute<? extends Value<?>> _fromJSONObject(JSONObject jo){
        Attribute<? extends Value<?>> a = null;
    		int id = 0;
    		if(jo.has("id")) id = jo.getInt("id");
        int attributeId = jo.getInt("attributeId");
        String name = jo.getString("name");
        AttributeType type = AttributeType.valueOf(AttributeType.class,jo.getString("type"));
        String defaultValue = jo.getString("defaultValue");
        int valueId = jo.getInt("valueId");
        Value<?> value = ValueInterface._fromJSONObject(jo.getJSONObject("value"));
        String businessKey = jo.getString("businessKey");
        String group = jo.getString("group");
        String version = jo.getString("version");
        int position = jo.getInt("position");
        String attributeGroup = jo.getString("attributeGroup");
        int attributePosition = jo.getInt("attributePosition");
        String attributeBusinessKey = jo.getString("attributeBusinessKey");
        CampStates states = CampStatesInterface._fromJSONObject(jo.getJSONObject("states"));
        CampStates valueStates = CampStatesInterface._fromJSONObject(jo.getJSONObject("valueStates"));
        boolean hasParent = jo.getBoolean("hasParent");
        int parentId = jo.getInt("parentId");
        int attributeParentId = jo.getInt("attributeParentId");
        CampInstance history = CampInstanceInterface._fromJSONObject(jo.getJSONObject("history")); 
        CampInstance valueHistory = CampInstanceInterface._fromJSONObject(jo.getJSONObject("valueHistory")); 
        ProcessList processes = new ProcessList();
        if(jo.has("processes")) {
        	processes = ProcessList._fromJSONArray(jo.getJSONArray("processes"));
        } 
        switch(type){
        case _boolean:
            a = new CampBoolean(name,defaultValue);
            ((CampBoolean)a).setValue((BooleanValue)value);
            break;
        case _integer:
            a = new CampInteger(name,defaultValue);
            ((CampInteger)a).setValue((IntegerValue)value);
            break;
        case _string:
            a = new CampString(name,defaultValue);
            ((CampString)a).setValue((StringValue)value);
            break;
        case _text:
            a = new CampString(name,defaultValue);
            ((CampText)a).setValue((TextValue)value);
            break;
        case _timestamp:
            a = new CampTimestamp(name,defaultValue);
            ((CampTimestamp)a).setValue((TimestampValue)value);
            break;
        case _datetime:
            a = new CampDateTime(name,defaultValue);
            ((CampDateTime)a).setValue((DateTimeValue)value);
            break;
        case _date:
            a = new CampDate(name,defaultValue);
            ((CampDate)a).setValue((DateValue)value);
            break;
        case _time:
            a = new CampTime(name,defaultValue);
            ((CampTime)a).setValue((TimeValue)value);
            break;
        case _complex:
            a = new CampComplex(name,defaultValue);
            ((CampComplex)a).setValue((ComplexValue)value);
            break;
        case _table:
            a = new CampTable(name,defaultValue);
            ((CampTable)a).setValue((TableValue)value);
            break;
        case _map:
            a = new CampMap(name,defaultValue);
            ((CampMap)a).setValue((MapValue)value);
            break;
        case _list:
            a = new CampList(name,defaultValue);
            ((CampList)a).setValue((ListValue)value);
            break;
        default:
            break;
        }
        a.updateId(id);
        a.attributeId(attributeId);
        a.valueId(valueId);
        a.setBusinessKey(businessKey);
        a.setGroup(group);
        a.setVersion(version);
        a.setPosition(position);
        a.setAttributeBusinessKey(attributeBusinessKey);
        a.setAttributeGroup(attributeGroup);
        a.setAttributePosition(attributePosition);
        a.setHasParent(hasParent);
        a.parentId(parentId);
        a.attributeParentId(attributeParentId);
        a.states().update(states);
        a.setHistory(history);
        a.valueStates().update(valueStates);
        a.setValueHistory(valueHistory);
        a.setProcesses(processes);
        return a;
    }

    public static String _toJson(Attribute<? extends Value<?>> a) {
        String json = "{";
        json += _toInnerJson(a);
        json += "}";
        return json;
    }
    public static String _toInnerJson(Attribute<? extends Value<?>> a) {
        String json = "";
        json += "\"id\":"+a.id();
        json += ",\"attributeId\":"+a.id();
        json += ",\"name\":\""+a.name()+"\"";
        json += ",\"type\":\""+a.attributeType().name()+"\"";
        json += ",\"defaultValue\":"+JSONObject.quote(a.defaultValue())+"";
        json += ",\"valueId\":"+a.valueId();
        json += ",\"businessKey\":\""+a.businessKey()+"\"";
        json += ",\"group\":\""+a.group().name()+"\"";
        json += ",\"version\":\""+a.version().value()+"\"";
        json += ",\"position\":"+a.position();
        json += ",\"attributeBusinessKey\":\""+a.attributeBusinessKey()+"\"";
        json += ",\"attributeGroup\":\""+a.attributeGroup().name()+"\"";
        json += ",\"attributePosition\":"+a.attributePosition();
        json += ",\"hasParent\":"+a.hasParent();
        json += ",\"parentId\":"+a.parentId();
        json += ",\"attributeParentId\":"+a.attributeParentId();
        json += ",\"states\":"+a.states().toJson();
        json += ",\"valueStates\":"+a.valueStates().toJson();
        json += ",\"history\":"+a.history().toJson();
        json += ",\"valueHistory\":"+a.valueHistory().toJson();
        json += ",\"value\":"+a.value().toJson();
        json += ((a.processes() != null && a.processes().size() >0)?","+"\"processes\":"+a.processes().toJson():"");
        return json;
    }
    
    public Attribute<? extends Value<?>> clone();
    
    public static Attribute<? extends Value<?>> clone(Attribute<? extends Value<?>> attribute) {
      return _fromJson(attribute.toJson());
    }
}
