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
package com.camsolute.code.camp.lib.models.product;

import com.camsolute.code.camp.lib.dao.DaoInterface;
import com.camsolute.code.camp.lib.dao.HasModelReference;
import com.camsolute.code.camp.lib.dao.HasProcessReference;
import com.camsolute.code.camp.lib.dao.InstanceDaoInterface;
import com.camsolute.code.camp.lib.dao.database.DBDaoInterface;
import com.camsolute.code.camp.lib.models.ModelList;
import com.camsolute.code.camp.lib.models.process.ProcessList;

public interface ProductDaoInterface extends HasProcessReference<Product>, HasModelReference, InstanceDaoInterface<Product>, DaoInterface<Product>, DBDaoInterface<Product> {

//	public int addModelReferences(Product p, boolean log);
//
//	public Product loadModels(Product p, boolean log);
//
//	public int delModelReference(Product p, int modelId, boolean log);
//
//	public int delModelReferences(Product p, boolean log);
//
	public Product saveModels(Product p, boolean log);

	public ProductList saveModels(ProductList pl, boolean log);

//	public int addProcessReferences(Product p, boolean log);
//
//	public Product loadProcesses(Product p, boolean log);
//
//	public int delProcessReference(Product p, String instanceId, boolean log);
//
//	public int delProcessReferences(Product p, boolean log);

	public Product saveAttributes(Product p, boolean log);

	public ProductList saveAttributes(ProductList pl, boolean log);

	public Product updateAttributes(Product p, boolean log);

	public ProductList updateAttributes(ProductList pl, boolean log);

	public Product loadAttributes(Product p, boolean log);

	public ProductList loadAttributes(ProductList pl, boolean log);

	public String insertProcessReferenceValues(String businessId,ProcessList pl, boolean log);

	public String insertModelReferenceValues(String businessId, ModelList ml, boolean log);

}
