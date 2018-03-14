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
package com.camsolute.code.camp.lib.dao.rest;

import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.Process.ProcessType;
import com.camsolute.code.camp.lib.models.process.ProcessList;

public interface ProcessRestDaoInterface {
  public Process<?> loadById(int id, boolean log);

  public  Process<?> loadByInstanceId(String instanceId, boolean log);

  public  ProcessList loadListByKey(String businessKey, boolean log);

  public  ProcessList loadListByBusinessId(String businessId, boolean log);
  
	public Process<?> create(String businessId, String executionId, String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType processType, boolean log);

	public Process<?> create(String businessId, String instanceId, String businessKey, String processName, String definitionId, String tenantId, String caseInstanceId, boolean ended, boolean suspended, ProcessType processType, boolean log);

  public Process<?> save(Process<?> p, boolean log);

  public ProcessList saveList(ProcessList pl, boolean log);

  public  int update(Process<?> p, boolean log);

  public int updateList(ProcessList pl, boolean log);

  public ProcessList loadUpdates(String businessKey, String target, boolean log);

  public ProcessList loadUpdatesByKey(String businessKey, boolean log);

  public ProcessList loadUpdatesByTarget(String target, boolean log);

  public  Process<?> loadUpdate(String instanceId, String businessId, String businessKey, String target, boolean log);

  public  Process<?> loadUpdate(Process<?> p, String businessKey, String target, boolean log);

  public  int addToUpdates(String instanceId, String businessId, String businessKey, String target, boolean log);

  public int addToUpdates(ProcessList pl, String businessKey, String target, boolean log);

  public int deleteAllFromUpdates(String businessKey, String target, boolean log);

  public int deleteFromUpdates(String instanceId, String businessId, String businessKey, String target, boolean log);

  public int deleteFromUpdates(ProcessList pl, String businessKey, String target, boolean log);

  
}
