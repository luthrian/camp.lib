/*******************************************************************************
 * Copyright (C) 2017 Christopher Campbell (campbellccc@gmail.com)
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
 * 	Christopher Campbell (campbellccc@gmail.com) - all code prior and post initial release
 ******************************************************************************/
package com.camsolute.code.camp.lib;

import java.util.HashMap;

import com.camsolute.code.camp.lib.types.CampList;
import com.camsolute.code.camp.lib.Attribute;
import com.camsolute.code.camp.lib.Attribute.AttributeType;

public interface AttributeDaoInterface<T> {

	/**
	 * create a new attribute. no value set
	 * @param objectId: the parent object to which the attribure belongs.
	 * @param name
	 * @param type
	 * @param defaultValue
	 * @return
	 */
	public Attribute<?> createAttribute(int objectId, String name,AttributeType type,String defaultValue);

	/**
	 * create a new attribute and sets the value 
	 * @param productAttributeId
	 * @param name
	 * @param type
	 * @param defaultValue
	 * @param value
	 * @return
	 */
	public Attribute<?> createAttribute(int objectId, String name, AttributeType type,String defaultValue,Object value);

	/**
	 * create a new type definition
	 * @param attribute
	 * @return
	 */
	public Attribute<?> save(int objectId, Attribute<?> attribute);
	
	/**
	 * create new type definitions from a list of type definition objects
	 * @param attributeAttributeTypeList
	 * @return
	 */
	public CampList saveList(int objectId, CampList attributeAttributeTypeList);
	
	/**
	 * update a type definition
	 * @param id
	 * @param name
	 * @param type
	 * @param defaultValue
	 * @return
	 */
	public int update(Attribute<?> attributeAttributeType);
	
	/** 
	 * update a list of type definitions
	 * @param attributeAttributeTypeList
	 * @return
	 */
	public int updateDefinitionList(CampList attributeAttributeTypeList);
	
	/**
	 * update a attribute type 
	 * @param productAttributeId
	 * @param attributeAttributeType
	 * @return
	 */
	public int update(int productAttributeId, Attribute<?> attributeAttributeType);
	
	/** 
	 * update attribute type definition list
	 * @param productAttributeId
	 * @param attributeAttributeTypeList
	 * @return
	 */
	public int updateList(int productAttributeId, CampList attributeAttributeTypeList);
	
	/**
	 * delete a type definition by its id
	 * @param id
	 * @return
	 */
	public int delete(int id);
	
	/**
	 * delete a type definition by its name
	 * @param name
	 * @return
	 */
	public int delete(String name);
	
	/**
	 * delete a list of type definitions
	 * @param attributeAttributeTypeList
	 * @return
	 */
	public int deleteList(CampList attributeAttributeTypeList);
	
	/**
	 * delete a list of type definitions by product attribute id
	 * @param productAttributeId
	 * @return
	 */
	public int deleteList(int productAttributeId);
	
	/**
	 * load a type definition by its id
	 * @param id
	 * @return
	 */
	public Attribute<?> loadDefinition(int id);
	
	/** 
	 * load a type definition by its name
	 * @param name
	 * @return
	 */
	public Attribute<?> loadDefinition(String name);
	
	/**
	 * load a list of type definitions by their type
	 * @param type
	 * @return
	 */
	public CampList loadDefinitionByAttributeType(AttributeType type);
	
	/**
	 * load a list all type definitions
	 * @return
	 */
	public CampList loadList();

	/**
	 * load a attribute type by id
	 * @param id
	 * @return
	 */
	public Attribute<?> load(int id);

	/**
	 * load an attribute type by product attribute id
	 * @param id
	 * @return
	 */
	public Attribute<?> loadByProductAttributeId(int productAttributeId);

	/**
	 * load an attribute type from a value id
	 * @param id
	 * @return
	 */
	public Attribute<?> loadByValueId(int id);
	
	/**
	 * load a list of attribute type 
	 * @return
	 */
	public CampList loadListByProductAttributeId(int[] productAttributeAttributeTypeIds);
	
	/**
	 * load a list of attribute types from a list of value ids
	 * @param idList <code>String[]</code> array of value id's
	 * @return 
	 */
	public CampList loadValueList(String[] idList);
	
	/** 
	 * add type definitions to product attribute definition
	 * @param productAttribute
	 * @return
	 */
//	public ProductAttributeDefinition<?,?> add(ProductAttributeDefinition<?,?> productAttribute);
	
	/** 
	 * add type definitions and values to order product attribute
	 * @param productAttribute
	 * @return
	 */
//	public OrderProductAttribute<?,?> addOrder(OrderProductAttribute<?,?> productAttribute);
	
	/** 
	 * add type definitions to a list of product attribute definitions
	 * @param productAttribute
	 * @return
	 */
	public CampList addList(CampList productAttributeList);
	
	/** 
	 * add type definitions to a list of order product attributes
	 * @param productAttribute
	 * @return
	 */
	public CampList addOrderList(CampList productAttributeList);
	
	/**
	 * save type definition values of an order product attribute 
	 * @param productAttribute
	 * @return
	 */
//	public ProductAttributeDefinition<?,?> save(ProductAttributeDefinition<?,?> productAttribute);
	
	/**
	 * save type definition values of an order product attribute 
	 * @param productAttribute
	 * @return
	 */
	public Attribute<?> saveReference(int productAttributeId,Attribute<?> attributeAttributeType);
	
	/**
	 * save type definition values of a list of order product attributes 
	 * @param productAttribute
	 * @return
	 */
	public HashMap<Integer,Attribute<?>> saveReferenceList(HashMap<Integer,Attribute<?>> productAttributeReferenceList);

	/**
	 * save type definition values of a list of order product attributes 
	 * @param productAttribute
	 * @return
	 */
//	public CampOldList<OrderProductAttribute<?,?>> saveOrderReferenceList(CampOldList<OrderProductAttribute<?,?>> productAttributeList);

	/**
	 * save type definition values of a list of order product attributes 
	 * @param productAttribute
	 * @return
	 */
//	public CampOldList<ProductAttributeDefinition<?,?>> saveReferenceList(CampOldList<ProductAttributeDefinition<?,?>> productAttributeList);


	/**
	 * save type definition values of a list of order product attributes 
	 * @param productAttribute
	 * @return
	 */
//	public AttributeList saveList(AttributeList productAttributeList);
	
	/**
	 * save type definition values of a list of order product attributes 
	 * @param productAttribute
	 * @return
	 */
//	public OrderAttributeList saveList(OrderAttributeList productAttributeList);
	
	/**
	 * delete type definition values of an order product attribute 
	 * @param productAttribute
	 * @return
	 */
//	public int clear(OrderProductAttribute<?,?> productAttribute);
	
	/**
	 * delete type definition values of a list of order product attributes 
	 * @param productAttribute
	 * @return
	 */
//	public int clearList(OrderProductAttribute<?,?> productAttributeList);
	

}
