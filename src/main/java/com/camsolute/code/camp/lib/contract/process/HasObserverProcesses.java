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
package com.camsolute.code.camp.lib.contract.process;

import com.camsolute.code.camp.lib.contract.core.CampException.ElementNotInListException;
import com.camsolute.code.camp.lib.contract.process.Process.ProcessType;
import com.camsolute.code.camp.lib.contract.core.CampList.ProcessList;

// This is Observer Pattern 
//TODO: will make this more generic (ie. change to HasObservers)
public interface HasObserverProcesses {
	
	public void addObserverProcess(Process observerProcess);
	public ProcessList removeObserverProcesses();
  public Process removeObserverProcess(String processInstanceId) throws ElementNotInListException;
  public ProcessList removeObserverProcesses(ProcessType group) throws ElementNotInListException;

	public void notifyObserverProcess(String processInstanceId);
	public void notifyObserverProcess(String processInstanceId, Enum<?> event);
  public void notifyObserverProcesses();
  public void notifyObserverProcesses(Enum<?> event);
  public void notifyObserverProcesses(ProcessType type);
  public void notifyObserverProcesses(ProcessType type, Enum<?> event);

	public ProcessList observerProcesses();
  public ProcessList observerProcesses(ProcessType group);
  public void observerProcesses(ProcessList processes);

}
