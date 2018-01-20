package com.camsolute.code.camp.lib;

import com.camsolute.code.camp.lib.Attribute.AttributeType;

public interface AttributeInterface<T> {
	/**
	 * ask for the id of the attribute type
	 * @return
	 */
	public int id();
	/**
	 * ask to set the id of the attribute type and return the id previously set or 0 of no id was previously set.
	 * @param id
	 * @return
	 */
	public int id(int id);
	
	/**
	 * ask for the value id of the attribute type
	 * @return
	 */
	public int valueId();
	/**
	 * ask to set the value id of the attribute type and return the value id previously set or 0 of no value id was previously set.
	 * @param valueId
	 * @return
	 */
	public int valueId(int valueId);

	/**
	 * ask for the name of the attribute type
	 * @return
	 */
	public String name();
	/**
	 * ask to set the name of the attribute type and return the name previously set or Null of no name was previously set.
	 * @param name
	 * @return
	 */
	public String name(String name);
	
	/**
	 * ask for the type of attribute 
	 * @return type as productAttributeType enum
	 */
	public AttributeType attributeType();

	/**
	 * ask to set the type of attribute
	 * @param type (AttributeType enum)
	 * @return value type was set to previously as AttributeType enum
	 */
	public AttributeType attributeType(AttributeType type);
	
	/**
	 * ask for the value of the attribute
	 * @return
	 */
	public T value();
	/**
	 * ask to set the value of the attribute
	 * @param value
	 * @return
	 */
	public T value(T value);
	
	/**
	 * ask for the default value
	 * @return
	 */
	public String defaultValue();
	/**
	 * set the default value and return the previous default value.
	 * @return
	 */
	public String defaultValue(String value);
	
	/**
	 * ask for the default version
	 * @return
	 */
	public String version();
	/**
	 * ask to set the version and return the previous version value.
	 * @param version
	 * @return
	 */
	public String version(String version);
	/**
	 * ask for the typeGroup of the attribute type
	 * @return
	 */
	public String typeGroup();
	/**
	 * ask to set the typeGroup name. 
	 * @param group <code>String</code> value of type group being set.
	 * @return the previous type group name or null if non was set
	 */
	public String typeGroup(String group);
	
	/**
	 * ask for the attribute group of the attribute type
	 * @return
	 */
	public String attributeGroup();
	/**
	 * ask to set the attribute group name. 
	 * @param group <code>String</code> value of type group being set.
	 * @return the previous type group name or null if non was set
	 */
	public String attributeGroup(String group);
	
	/**
	 * ask for the type position of the attribute type
	 * @return
	 */
	public int typePosition();
	/**
	 * ask to set the type position name. 
	 * @param group <code>String</code> value of type position being set.
	 * @return the previous type position name or 0 if non was set
	 */
	public int typePosition(int position);
	
	/**
	 * ask for the attribute position of the attribute type
	 * @return
	 */
	public int attributePosition();
	/**
	 * ask to set the attribute position name. 
	 * @param group <code>String</code> value of attribute position being set.
	 * @return the previous attribute position or 0 if non was set
	 */
	public int attributePosition(int position);

	// always changing the base code huh ... but this change was planned and necessary .. not all is required to fix why this was necessary
	/**
	 * ask for the business id of the attribute type
	 * @return
	 */
	public String typeBusinessId();
	/**
	 * ask to set the attribute type business id. (biz + tech id) 
	 * @param id <code>String</code> value of business id being set.
	 * @return the previous attribute position or 0 if non was set
	 */
	public String typeBusinessId(String id);
	/**
	 * ask for the business part of the attribute type business id.
	 * @return
	 */
	public String onlyTypeBusinessId();
	/**
	 * ask for the initial attribute type business id.
	 * @return
	 */
	public String initialTypeBusinessId();

	/**
	 * ask for the attribute business id
	 * @return
	 */
	public String attributeBusinessId();
	/**
	 * ask to set the attribute business id. (biz + tech id) 
	 * @param id <code>String</code> value of business id being set.
	 * @return the previous attribute position or 0 if non was set
	 */
	public String attrbuteBusinessId(String id);
	/**
	 * ask for the business part of the attribute business id.
	 * @return
	 */
	public String onlyAttributeBusinessId();
	/**
	 * ask for the initial attribute business id.
	 * @return
	 */
	public String initialAttributeBusinessId();
	
	/**
	 * ask if attribute type has parent
	 * @return
	 */
	public boolean hasParent();
	/**
	 * set attribute type has parent flag
	 * @param hasParent
	 * @return
	 */
	public boolean hasParent(boolean hasParent);
	
	/**
	 * ask for a reference to the parent object instance referenced by this attribute type 
	 * @return
	 */
	public Attribute<?> parent();
	/**
	 * set attribute type parent reference
	 * @param parent
	 * @return
	 */
	public Attribute<?> parent(Attribute<?> parent);
	/**
	 * ask for the attribute type ioState
	 * @return IOSates attribute type <code>IOStates</code> instance
	 */
	public CampStates states();

	/**
	 * transform <code>String</code> value representation to attribute type value 
	 * and return the attribute Type
	 * @param value <code>String</code> representation of the attribute type value
	 * @return attribute type
	 */
	public Attribute<?> valueFromString(String value);
}
