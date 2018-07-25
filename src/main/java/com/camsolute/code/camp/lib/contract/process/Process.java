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

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.HasId;
import com.camsolute.code.camp.lib.contract.core.Serialization;

import com.camsolute.code.camp.lib.contract.process.JSONProcessHandler.CamundaJSONProcessHandler;; 

public interface Process extends HasId, HasJSONProcessHandler, HasSQLProcessHandler, Serialization<Process> {

    public static enum ProcessType {
        ORDER_PROCESS,
        PRODUCTION_PROCESS,
        DESIGN_PROCESS,
        TEST_PROCESS,
        REVIEW_PROCESS,
        RELEASE_PROCESS,
        DEVELOPMENT_PROCESS,
        LOGISTICS_PROCESS,
        MANAGEMENT_PROCESS,
        SUPPORT_PROCESS,
        MARKETING_PROCESS,
        SALES_PROCESS,
        AFTERSALES_PROCESS,
        FINANCE_PROCESS,
        BILLING_PROCESS;
    }
    public static enum Principal {
        Order,
        Product,
        Customer,
        Production,
        Event,
        Campaign,
        Inventory,
        Address,
        Account;
    }
    public static enum TargetSystem {
    	CAMUNDA,
    	ACTIVITI,
    	JBPM,
    	GENERIC;
    }
	public String instanceId();
	
	public String businessKey();
	
	public String processName();
	
	public String target();
	
	public ProcessType type();
	
	public Principal principal();
	
	public TargetSystem targetSystem();
	
    public class DefaultProcessImpl implements Process {

    	private String id = HasId.newId();
    	
			private String instanceId;
			
			private String businessKey;
			
			private String processName;
			
			private String target;
			
			private JSONProcessHandler jsonHandler;
			
			private SQLProcessHandler sqlHandler;
			
			private ProcessType type;
			
			private Principal principal;
			
			private TargetSystem targetSystem = TargetSystem.GENERIC;
	

			public String instanceId() {
				// TODO Auto-generated method stub
				return null;
			}


			public String businessKey() {
				// TODO Auto-generated method stub
				return null;
			}


			public String processName() {
				// TODO Auto-generated method stub
				return null;
			}


			public String target() {
				// TODO Auto-generated method stub
				return null;
			}


			public ProcessType type() {
				// TODO Auto-generated method stub
				return null;
			}


			public Principal principal() {
				// TODO Auto-generated method stub
				return null;
			}


			public TargetSystem targetSystem() {
				// TODO Auto-generated method stub
				return null;
			}



			public JSONProcessHandler jsonHandler() {
				// TODO Auto-generated method stub
				return null;
			}



			public void jsonHandler(JSONProcessHandler handler) {
				// TODO Auto-generated method stub
				
			}



			public String id() {
				return id;
			}



			public String updateId(String newId) {
				String prev = this.id;
				this.id = newId;
				return prev;
			}


			@Override
			public String toJson() {
				// TODO Auto-generated method stub
				return null;
			}


			@Override
			public Process fromJson(String json) throws DataMismatchException {
				// TODO Auto-generated method stub
				return null;
			}


			@Override
			public Process fromJSONObject(JSONObject jo) throws DataMismatchException {
				// TODO Auto-generated method stub
				return null;
			}
        
    }

    public class CamundaProcessImpl implements Process {


    	private String id = HasId.newId();
    	
			private String instanceId;
			
			private String businessKey;
			
			private String processName;
			
			private String target;
			
			private JSONProcessHandler jsonHandler = new CamundaJSONProcessHandler();
			
			private SQLProcessHandler sqlHandler;
			
			private ProcessType type;
			
			private Principal principal;
			
			private TargetSystem targetSystem = TargetSystem.CAMUNDA;
	
			public String instanceId() {
				// TODO Auto-generated method stub
				return null;
			}


			public String businessKey() {
				// TODO Auto-generated method stub
				return null;
			}


			public String processName() {
				// TODO Auto-generated method stub
				return null;
			}


			public String target() {
				// TODO Auto-generated method stub
				return null;
			}


			public ProcessType type() {
				// TODO Auto-generated method stub
				return null;
			}


			public Principal principal() {
				// TODO Auto-generated method stub
				return null;
			}


			public TargetSystem targetSystem() {
				// TODO Auto-generated method stub
				return null;
			}



			public JSONProcessHandler jsonHandler() {
				// TODO Auto-generated method stub
				return null;
			}



			public void jsonHandler(JSONProcessHandler handler) {
				// TODO Auto-generated method stub
				
			}



			public String id() {
				return this.id;
			}



			public String updateId(String newId) {
				String prev = this.id;
				this.id = newId;
				return prev;
			}


			@Override
			public String toJson() {
				// TODO Auto-generated method stub
				return null;
			}


			@Override
			public Process fromJson(String json) throws DataMismatchException {
				// TODO Auto-generated method stub
				return null;
			}


			@Override
			public Process fromJSONObject(JSONObject jo) throws DataMismatchException {
				// TODO Auto-generated method stub
				return null;
			}
        
    }
}
