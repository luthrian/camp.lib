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
 * *****************************************************************************
 * Copyright (C) 2017
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
 * release 
 * ****************************************************************************
 */
package com.camsolute.code.camp.lib.contract;

import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.models.rest.Message;
import com.camsolute.code.camp.lib.models.rest.MessageList;
import com.camsolute.code.camp.lib.models.rest.Request;
import com.camsolute.code.camp.lib.models.process.Process.ProcessType;

public interface HasProcess<U extends IsObjectInstance<U>,T extends Process<U,T>> extends IsObjectInstance<U> {

  public ProcessList processInstances();

  public void addProcess(T process);

  public <E extends ProcessList> void addProcesses(E processes);

  public T deleteProcess(String instanceId);

  public <E extends ProcessList> E processes();

  public <E extends ProcessList> E processes(ProcessType group);

  public <E extends ProcessList> void setProcesses(E pl);
  
  public void notifyProcesses();

  public void notifyProcesses(ProcessType type);

  public void notifyProcesses(Enum<?> event);

  public void notifyProcesses(ProcessType type, Enum<?> event);
  
  public Message prepareMessage(String insanceId, Enum<?> message);
  
  public MessageList prepareMessages(Enum<?> message);
  
  public Request<?> prepareRequest();
  
}
