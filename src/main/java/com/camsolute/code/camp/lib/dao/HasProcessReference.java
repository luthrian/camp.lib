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
package com.camsolute.code.camp.lib.dao;

import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.HasProcess;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessList;

public interface HasProcessReference<T extends HasProcess<T,?>> {

	public int addProcessReference(String businessId, String instanceId, String processKey, boolean log);

	public int addProcessReferences(String businessId, ProcessList pl, boolean log);

	public int delProcessReference(String businessId, String instanceId, String processKey, boolean log);

	public int delProcessReferences(String businessId, boolean log);

	public <E extends ArrayList<Process<?,?>>> E loadProcessReferences(String businessId, boolean log);
}
