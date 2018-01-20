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
package com.camsolute.code.camp.lib.contract;

import com.camsolute.code.camp.core.types.CampOldList;
import com.camsolute.code.camp.core.types.CampOldMap;
import com.camsolute.code.camp.models.business.Process;

public interface HasProcess<T extends Process<?>> {
	
	public String businessKey();
	
	public CampOldList<T> processInstances();

	public CampOldMap<T> observerProcesses();

	public void observerProcesses(CampOldMap<T> processes);
	
	public void addProcess(T process);
	
	public void addProcess(String processName,T process);
	
	public void addProcesses(CampOldList<T> processes);
	
	public void addProductManagementProcess(T productManagementProcess);
	
	public void addProductionManagementProcess(T productionManagementProcess);
	
	public void addOrderManagementProcess(T orderManagementProcess);
	
	public void addCustomerOrderManagementProcess(T customerOrderManagementProcess);
	
	public void addCustomerManagementProcess(T customerManagementProcess);
	
	public T deleteProductManagementProcess(String instanceId);
	
	public T deleteProductionManagementProcess(String instanceId);
	
	public T deleteOrderManagementProcess(String instanceId);
	
	public T deleteCustomerOrderManagementProcess(String instanceId);
	
	public T deleteCustomerManagementProcess(String instanceId);
	
	public CampOldList<T> productManagementProcesses();
	
	public CampOldList<T> productionManagementProcesses();
	
	public CampOldList<T> orderManagementProcesses();
	
	public CampOldList<T> customerOrderManagementProcesses();
	
	public CampOldList<T> customerManagementProcesses();
	
	public void notifyProcesses();
	public void notifyProcesses(Enum<?> event);
}
