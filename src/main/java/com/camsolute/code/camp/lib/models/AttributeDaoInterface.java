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
 * ***************************************************************************** Copyright (C) 2017
 * Christopher Campbell (campbellccc@gmail.com)
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
 * release ****************************************************************************
 */
package com.camsolute.code.camp.lib.models;

import java.sql.SQLException;
import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.dao.DaoInterface;
import com.camsolute.code.camp.lib.dao.InstanceDaoInterface;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;

public interface AttributeDaoInterface extends DaoInterface<Attribute<?>>, InstanceDaoInterface<Attribute<?>>{
	//DEFINITION ASPECTS

  /**
   * create a new attribute definition. an attribute definition does not have any values set yet,
   * only default values. in the case of complex attributes the default value contains a list of
   * comma separated id's that reference the complex attribute elements.
   *
   * @param parentId: the parent object to which the attribute belongs.
   * @param name name
   * @param type type
   * @param businessId business id
   * @param businessKey business Key
   * @param businessId attribute business id
   * @param group group
   * @param version version
   * @param defaultValue default value
   * @return the newly instantiated and persisted <code>Attribute</code> object.
   */
    public Attribute<?> create(int parentId, String name, AttributeType type, String businessId, String businessKey, String group, String version, String defaultValue);

  /**
   * request to delete the definition aspect of an attribute by its id. 
   * Note: this request must not affect the value aspects of the attribute.
   *
   * @param id id of attribute to be deleted
   * @return number deleted
   */
    public int delete(int id);

  /**
   * request to delete the definition aspect of an attribute by its name. 
   * Note: this request must not affect the value aspects of the attribute.
   *
   * @param attributeName the <code>String</code> value representing the business id of the <code>Attribute</code> used to identify the persisted object to be deleted.
   * @return an <code>Integer</code> value representing the number of <code>Attribute</code> objects to be deleted [0,1]
   */
    public int delete(String attributeName);

  /**
   * request to delete the definition aspect of an attribute. 
   * Note: this request must not affect the value aspects of the attribute.
   *
   * @param attribute the <code>Attribute</code> who's definition aspects should be deleted
   * @return an Integer representing the number of <code>Attribute</code> objects deleted [0,1]
   */
    public int delete(Attribute<?> attribute);

  /**
   * request to delete the definition aspects of a list of attributes.
   * Note: this request must not affect the value aspects of the attributes.
   *
   * @param attributeList <code>AttributeList</code> extends Array&lt;Attribute&gt; and implements serialization to json String.
   * @return an Integer value representing the number of attributes deleted (as returned by the jdbc update query). 
   */
    public int deleteList(AttributeList attributeList);

  /**
   * request to delete the definition aspects of a list of attributes that have a common root(parent) attribute.
   * Note: this request must not affect the value aspects of the attributes.
   *
   * @param rootId root object id
   * @return number deleted
   */
    public int deleteList(int rootId);

  /**
   * request to load the definition aspects of a list of attributes that share a common type aspect 
   * load a list of attributes by their type
   *
   * @param type attribute type
   * @return attribute list loaded  
   */
    public AttributeList loadList(AttributeType type);

    
  /**
   * request to load the definition aspects of all attributes  
   *
   * @return attribute list
   */
    public AttributeList loadList();

    public AttributeMap loadByObjectId(int objectId);

    public AttributeMap saveByObjectId(int objectId, AttributeMap attributeMap);

    public AttributeList loadAttributesByObjectId(int objectId);

    public AttributeList saveAttributesByObjectId(int objectId, AttributeList attributeList);

    public int updateAttributesByObjectId(int objectId, AttributeList attributeList);

    public AttributeList loadGroup(int parentId, String groupName);

    public AttributeList loadAfterPosition(int id, int position);

    public AttributeList loadBeforePosition(int id, int position);

    public AttributeList loadRange(int id, int startPosition, int endPosition);
    
    // VALUE ASPECTS

    /**
     * create a new attribute definition and an attribute instance with a value set.
     *
     * @param objectId object id
     * @param parentId parent id
     * @param name name
     * @param type type
     * @param businessId business id
     * @param businessKey business Key
     * @param attributeBusinessId attribute business id
     * @param group group
     * @param attributeGroup attribute group
     * @param version version
     * @param defaultValue default value
     * @param value value
     * @return attribute
     */
    public Attribute<?> create(int objectId, int parentId, String name, AttributeType type, String businessId, String businessKey, String attributeBusinessId, String group, String attributeGroup, String version, String defaultValue, Value<?> value);

  /**
   * request to persist the value aspects of an attribute.
   * Note: this request must not affect persisted definition aspects of the attribute.
   *
   * @param objectId object id
   * @param attribute attribute
   * @return attribute saved
   */
    public Attribute<?> save(int objectId, Attribute<?> attribute);

  /**
   * request to persist the value aspects of a list of attributes.
   * Note: this request must not affect persisted definition aspects of the attributes.
   *
   * @param objectId id 
   * @param attributeList id
   * @return attribute list
   */
    public AttributeList saveList(int objectId, AttributeList attributeList);

  /**
   * request to update the value aspects of an attribute that has been persisted.
   * Note: this request must not affect persisted definition aspects of the attribute.
   *
   * @param objectId the parent object id the attribute is associated with
   * @param attribute the attribute to be updated
   * @return number updated
   */
    public int update(int objectId, Attribute<?> attribute);

  /**
   * request to update the value aspects of a list of attributes that has been persisted.
   * Note: this request must not affect persisted definition aspects of the attributes.
   *
   * @param objectId the parent object id the attribute is associated with
   * @param attributeList list of attributes to update
   * @return number updated
   */
    public int updateList(int objectId, AttributeList attributeList);

  /**
   * request to delete the value aspects of a list of persisted attributes.
   * An attribute object is called persisted when all aspects that are relevant to it's life-cycle have been persisted to a storage medium.  
   * Note: this request must not affect persisted definition aspects of the attributes.
   *
   * @param objectId id
   * @param attributeId id 
   * @param valueId id
   * @param type  AttributeType
   * @return number deleted
   */
    public int delete(int objectId, int attributeId, int valueId, AttributeType type);

  /**
   * request to delete the value aspects of a list of persisted attributes.
   * An attribute object is called persisted when all aspects that are relevant to it's life-cycle have been persisted to a storage medium.  
   * Note: this request must not affect persisted definition aspects of the attributes.
   *
   * @param objectId parent object id 
   * @param attribute attribute to be deleted
   * @return number of attributes deleted (is 1 or 0)
   */
    public int delete(int objectId, Attribute<?> attribute);

  /**
   * delete a list of attributes
   *
   * @param objectId parent object id
   * @param attributeList list of attributes
   * @return num deleted
   */
    public int deleteList(int objectId, AttributeList attributeList);

  /**
   * load an attribute by its def id and object id
   *
   * @param objectId parent object id 
   * @param attribute definition aspects of attribute 
   * @return object loaded
   */
    public Attribute<?> load(int objectId, Attribute<?> attribute);

    public AttributeList loadByObjectId(AttributeList attributeList, int objectId);

    public AttributeList loadGroup(AttributeList attributeList, int objectId, String groupName);

    public AttributeList loadAfterPosition(AttributeList attributeList, int objectId, int position);

    public AttributeList loadBeforePosition(AttributeList attributeList, int objectId, int position);

    public AttributeList loadRange(AttributeList attributeList, int objectId, int startPosition, int endPosition);

    public Attribute<?> loadFirst(String businessId);

    public Attribute<?> loadPrevious(Attribute<?> attribute);

    public Attribute<?> loadNext(Attribute<?> attribute);
   
    public AttributeList loadDate(String businessId, String date);
    
    public AttributeList loadDateRange(String businessId, String startDate, String endDate);
    
    public AttributeList loadDate(String date);
    
    public AttributeList loadDateRange(String startDate, String endDate);
    
    public int createTable(boolean log);

    public int clearTables(boolean log);

}
