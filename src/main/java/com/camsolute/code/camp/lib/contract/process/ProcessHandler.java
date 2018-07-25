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

import com.camsolute.code.camp.lib.models.rest.Message;
import com.camsolute.code.camp.lib.models.rest.MessageList;
import com.camsolute.code.camp.lib.models.rest.Request;
import com.camsolute.code.camp.lib.models.rest.Request.Principal;
import com.camsolute.code.camp.lib.models.rest.Request.RequestType;

public interface ProcessHandler {
	
  public Message prepareMessage(String insanceId, Enum<?> message);
  
  public MessageList prepareMessages(Enum<?> message);
  
  public Request<?> prepareRequest(Principal principal, RequestType type);

	public void notifyProcess(ProcessList processList);

	public void notifyProcess(ProcessList processList, Enum<?> event);

	public void notifyProcess(Process processList);

	public void notifyProcess(Process processList, Enum<?> event);

	public class DefaultProcessHandler implements ProcessHandler {
		
		public Message prepareMessage(String insanceId, Enum<?> message) {
			return null; //TODO
		}
	
		public MessageList prepareMessages(Enum<?> message) {
			return null; //TODO
		}
		
		public Request<?> prepareRequest(Principal principal, RequestType type) {
			return null; //TODO
		}

		public void notifyProcess(ProcessList processList) {
			//TODO:
		}

		public void notifyProcess(ProcessList processList, Enum<?> event) {
			//TODO:
		}
		
		public void notifyProcess(Process process) {
			//TODO:
		}

		public void notifyProcess(Process process, Enum<?> event) {
			//TODO:
		}
		
	}
		
}
