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
package com.camsolute.code.camp.lib.types;

import com.camsolute.code.camp.core.types.CampComplex;
import com.camsolute.code.camp.core.types.CampOldComplex;
import com.camsolute.code.camp.core.types.CampType;
import com.camsolute.code.camp.models.Group;
import com.camsolute.code.camp.models.Version;
import com.camsolute.code.camp.models.business.ProductAttributeDefinition;

public class OldComplexAttribute extends ProductAttributeDefinition<CampOldComplex<ProductAttributeDefinition<?,?>>,CampComplex<CampType<?>>> {

	public OldComplexAttribute(String name, String type, CampOldComplex<ProductAttributeDefinition<?,?>> value) {
		super(name, type, value);
		// TODO Auto-generated constructor stub
	}

	public OldComplexAttribute(String name, String type, CampOldComplex<ProductAttributeDefinition<?,?>> value, Version version, Group group) {
		super(name, type, value, version, group);
		// TODO Auto-generated constructor stub
	}

	public OldComplexAttribute(int id, String name, String type, CampOldComplex<ProductAttributeDefinition<?,?>> value, Version version, Group group) {
		super(id, name, type, value, version, group);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public static OldComplexAttribute fromProductAttribute(ProductAttributeDefinition<?,?> pa) {
		OldComplexAttribute ea = new OldComplexAttribute(pa.name(),pa.type(),(CampOldComplex<ProductAttributeDefinition<?,?>>)pa.value());
		ea.updateId(pa.id());
		ea.attributeGroup(pa.attributeGroup());
		ea.productGroup(pa.productGroup());
		if(pa.isDirty())ea.dirty();
		if(pa.isNew())ea.isNew(true);
		if(pa.wasLoaded())ea.loaded();
		if(pa.wasSaved())ea.saved();
		if(pa.hasParent()) ea.parent(pa.parent());
		if(pa.hasProduct()) ea.product(pa.product());
		if(pa.productId()>0) ea.productId(pa.productId());
		if(pa.parentId()>0) ea.parentId(pa.parentId());
		return ea;
	}
}
