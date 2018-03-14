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

import com.camsolute.code.camp.lib.models.Value;
import com.camsolute.code.camp.lib.dao.DaoInterface;
import com.camsolute.code.camp.lib.dao.HasProcessReference;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;

//public interface AttributeRestInterface extends HasProcessReference, DaoInterface<Attribute<? extends Value<?>>> {
public interface AttributeRestInterface extends HasProcessReference, DaoInterface<Attribute<? extends Value<?>>> {
	
    public int delete(int id, boolean log);

    public int delete(String attributeName, boolean log);

    public int deleteList(AttributeList attributeList, boolean log);

    public int deleteList(int rootId, boolean log);

    public AttributeList loadList(AttributeType type, boolean log);

    public AttributeList loadGroup(int parentId, String groupName, boolean log);

    public AttributeList loadAfterPosition(int id, int position, boolean log);

    public AttributeList loadBeforePosition(int id, int position, boolean log);

    public AttributeList loadRange(int id, int startPosition, int endPosition, boolean log);
    
    // VALUE ASPECTS

    public Attribute<? extends Value<?>> save(int objectId, Attribute<? extends Value<?>> attribute, boolean log);

    public AttributeList saveList(int objectId, AttributeList attributeList, boolean log);

    public int update(int objctId, Attribute<? extends Value<?>> attribute, boolean log);

    public int updateList(int objctId, AttributeList attributeList, boolean log);

    public int delete(int objectId, int attributeId, int valueId, AttributeType type, boolean log);

    public int delete(int objectId, Attribute<? extends Value<?>> attribute, boolean log);

    public int deleteList(int objectId, AttributeList attributeList, boolean log);

    public Attribute<? extends Value<?>> load(int objectId, Attribute<? extends Value<?>> attribute, boolean log);

    public AttributeList loadByObjectId(AttributeList attributeList, int objectId, boolean log);

    public AttributeList loadGroup(AttributeList attributeList, int objectId, String groupName, boolean log);

    public AttributeList loadAfterPosition(AttributeList attributeList, int objectId, int position, boolean log);

    public AttributeList loadBeforePosition(AttributeList attributeList, int objectId, int position, boolean log);

    public AttributeList loadRange(AttributeList attributeList, int objectId, int startPosition, int endPosition, boolean log);

    // General
    public AttributeMap loadByObjectId(int objectId, boolean log);

    public AttributeMap saveByObjectId(int objectId, AttributeMap attributeMap, boolean log);

    public AttributeList loadAttributesByObjectId(int objectId, boolean log);

    public AttributeList saveAttributesByObjectId(int objectId, AttributeList attributeList, boolean log);

    public int updateAttributesByObjectId(int objectId, AttributeList attributeList, boolean log);

}
