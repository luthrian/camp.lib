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
package com.camsolute.code.camp.lib.data;

import java.util.HashMap;

import com.camsolute.code.camp.lib.utilities.Util;


//TODO: this is a bucket used while hacking the code (design by coding ;) ). Stuff needs to be fleshed out to appropriate locations
public class CampRest {

	public static final String SERVER_URL = Util.Config.instance().properties().getProperty("rest.default.server.url");
	public static final int SERVER_PORT = Integer.valueOf(Util.Config.instance().properties().getProperty("rest.default.server.port"));
	public static final String BUSINESS_DOMAIN = Util.Config.instance().properties().getProperty("rest.default.business.domain");

	public static final String ORDER_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.order.api.server.url");
	public static final String PROCESS_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.process.api.server.url");
	public static final String PRODUCT_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.product.api.server.url");
	public static final String CUSTOMER_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.customer.api.server.url");
	public static final String PROCESS_CONTROL_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.process.control.api.server.url");

	public static final String ORDER_API_DOMAIN = Util.Config.instance().properties().getProperty("rest.order.api.business.domain");
	public static final String PROCESS_API_DOMAIN = Util.Config.instance().properties().getProperty("rest.process.api.business.domain");
	public static final String PRODUCT_API_DOMAIN = Util.Config.instance().properties().getProperty("rest.product.api.business.domain");
	public static final String CUSTOMER_API_DOMAIN = Util.Config.instance().properties().getProperty("rest.customer.api.business.domain");
	public static final String PROCESS_CONTROL_API_DOMAIN = Util.Config.instance().properties().getProperty("rest.process.control.api.business.domain");

	public static final String ORDER_API_PATH = ORDER_API_DOMAIN + Util.Config.instance().properties().getProperty("rest.order.api.path");
	public static final String REST_ORDER_API_PATH = ORDER_API_PATH;
	public static final String PROCESS_API_PATH = PROCESS_API_DOMAIN + Util.Config.instance().properties().getProperty("rest.process.api.path");
	public static final String PRODUCT_API_PATH = PRODUCT_API_DOMAIN + Util.Config.instance().properties().getProperty("rest.product.api.path");
	public static final String CUSTOMER_API_PATH = CUSTOMER_API_DOMAIN + Util.Config.instance().properties().getProperty("rest.customer.api.path");
	public static final String PROCESS_CONTROL_API_PATH = PROCESS_CONTROL_API_DOMAIN + Util.Config.instance().properties().getProperty("rest.process.control.api.path");
	
	public static final String PROCESS_ENGINE_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.process.engine.api.server.url");
	public static final String PROCESS_ENGINE_API_DOMAIN = Util.Config.instance().properties().getProperty("rest.process.engine.api.server.domain");

	public static class DaoService {
		public static enum Request {
			LOAD_BY_ID,
			LOAD,
			LOAD_BY_KEY,
			LOAD_BY_GROUP,
			LOAD_BY_GROUP_VERSION,
			CREATE_ORDER, 
			CREATE_PRODUCT, 
			CREATE_ATTRIBUTE, 
			CREATE_MODEL, 
			CREATE_PROCESS, 
			CREATE_PROCESS_EID, 
			SAVE,
			SAVE_LIST,
			UPDATE,
			UPDATE_LIST,
			UPDATE_ATTRIBUTE, // TODO: consider this - selective update to reduce traffic load - is this required
			LOAD_UPDATE,
			LOAD_UPDATES,
			LOAD_UPDATES_KEY,
			LOAD_UPDATES_TARGET,
			ADD_UPDATE,
			ADD_UPDATES,
			DELETE_ALL_UPDATES,
			DELETE_KEY_UPDATES,
			DELETE_TARGET_UPDATES,
			DELETE_UPDATES,
			DELETE_UPDATE;
		};
		public static final String LOAD_BY_ID = "/load/by/id/{id}";
		public static final String LOAD = "/load/by/businessId";
		public static final String LOAD_BY_KEY = "/load/by/key";
		public static final String LOAD_BY_GROUP = "/load/by/group";
		public static final String LOAD_BY_GROUP_VERSION = "/load/by/group/version";
		public static final String CREATE_ORDER = "/create";
		public static final String CREATE_PRODUCT = "/create";
		public static final String CREATE_ATTRIBUTE = "/create";
		public static final String CREATE_MODEL = "/create";
		public static final String CREATE_PROCESS = "/create";
		public static final String CREATE_PROCESS_EID = "/create/eid";
		public static final String SAVE = "/save";
		public static final String SAVE_LIST = "/s/save";
		public static final String UPDATE = "/update";
		public static final String UPDATE_LIST = "/s/update";
		public static final String UPDATE_ATTRIBUTE = "/update/attribute";
		public static final String LOAD_UPDATE = "/updates/load";
		public static final String LOAD_UPDATES = "/s/updates/load";
		public static final String LOAD_UPDATES_KEY = "/s/updates/key/load";
		public static final String LOAD_UPDATES_TARGET = "/s/updates/target/load";
		public static final String ADD_UPDATE = "/updates/add";
		public static final String ADD_UPDATES = "/s/updates/add";
		public static final String DELETE_ALL_UPDATES = "/s/updates/del/all";
		public static final String DELETE_KEY_UPDATES = "/s/updates/del/key";
		public static final String DELETE_TARGET_UPDATES = "/s/updates/del/target";
		public static final String DELETE_UPDATES = "/s/updates/del";
		public static final String DELETE_UPDATE = "/updates/del";
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.LOAD_BY_ID, new String[] {"/load/by/id/{id}","/load/by/id/%s","GET"});
			Call.put(Request.LOAD, new String[] {"/load/by/businessId","/load/by/businessId?businessId=%s","GET"});
			Call.put(Request.LOAD_BY_KEY, new String[] {"/load/by/key","/load/by/key?businessKey=%s","GET"});
			Call.put(Request.LOAD_BY_GROUP, new String[] {"/load/by/group","/load/by/group?group=%s","GET"});
			Call.put(Request.LOAD_BY_GROUP_VERSION, new String[] {"/load/by/group/version","/load/by/group/version?group=%s&version=%s","GET"});
			Call.put(Request.CREATE_ORDER, new String[] {"/create","/create?businessId=%s&businessKey=%s&date=%s&byDate=%s","GET"});
			Call.put(Request.CREATE_PRODUCT, new String[] {"/create","/create?businessId=%s&businessKey=%s&date=%s&byDate=%s","GET"});
			Call.put(Request.CREATE_ATTRIBUTE, new String[] {"/create","/create?businessId=%s&businessKey=%s&date=%s&byDate=%s","GET"});
			Call.put(Request.CREATE_MODEL, new String[] {"/create","/create?businessId=%s&businessKey=%s&date=%s&byDate=%s","GET"});
			Call.put(Request.CREATE_PROCESS, new String[] {"/create","/create?businessId=%s&instanceId=%s&businessKey=%s&processName=%s&definitionId=%s&tenantId=%s&caseInstanceId=%s&ended=%s&suspended=%s&type=%s","GET"});
			Call.put(Request.CREATE_PROCESS_EID, new String[] {"/create/eid","/create/eid?businessId=%s&executionId=%s&instanceId=%s&businessKey=%s&processName=%s&definitionId=%s&tenantId=%s&caseInstanceId=%s&ended=%s&suspended=%s&type=%s","GET"});
			Call.put(Request.SAVE, new String[] {"/save","/save","POST"});
			Call.put(Request.SAVE_LIST, new String[] {"/s/save","/s/save","POST"});
			Call.put(Request.UPDATE, new String[] {"/update","/update","POST"});
			Call.put(Request.UPDATE_LIST, new String[] {"/s/update","/s/update","POST"});
			Call.put(Request.UPDATE_ATTRIBUTE, new String[] {"/update/attribute","/update/attribute?attributeType=%s&businessId=%s&attributeValue=%s"});
			Call.put(Request.LOAD_UPDATE, new String[] {"/updates/load","/updates/load?businessId=%s&businessKey=%s&target=%s","GET"});
			Call.put(Request.LOAD_UPDATES, new String[] {"/s/updates/load","/s/updates/load?businessKey=%s&target=%s","GET"});
			Call.put(Request.LOAD_UPDATES_KEY, new String[] {"/s/updates/key/load","/s/updates/key/load?businessKey=%s","GET"});
			Call.put(Request.LOAD_UPDATES_TARGET, new String[] {"/s/updates/target/load","/s/updates/target/load?target=%s","GET"});
			Call.put(Request.ADD_UPDATE, new String[] {"/updates/add","/updates/add?businessId=%s&businessKey=%s&target=%s","GET"});
			Call.put(Request.ADD_UPDATES, new String[] {"/s/updates/add","/s/updates/add?businessKey=%s&target=%s","POST"});
			Call.put(Request.DELETE_ALL_UPDATES, new String[] {"/s/updates/del/all","/s/updates/del/all?businessKey=%s&target=%s","GET"});
			Call.put(Request.DELETE_KEY_UPDATES, new String[] {"/s/updates/del/key","/s/updates/del/key?businessKey=%s","GET"});
			Call.put(Request.DELETE_TARGET_UPDATES, new String[] {"/s/updates/del/target","/s/updates/del/target?target=%s","GET"});
			Call.put(Request.DELETE_UPDATES, new String[] {"/s/updates/del","/s/updates/del?businessKey=%s&target=%s","POST"});
			Call.put(Request.DELETE_UPDATE, new String[] {"/updates/del","/updates/del?businessId=%s&businessKey=%s&target=%s","GET"});
		}
		public static String callRequest(String prefix, DaoService.Request request) {
			return prefix + DaoService.Call.get(request)[1];
		}
		public static String path(String prefix, DaoService.Request request) {
			return prefix + DaoService.Call.get(request)[0];
		}
	}
	
	public static class InstanceDaoService {
		public static enum Request {
			ADD_INSTANCE,
			ADD_INSTANCES,
			LOAD_FIRST,
			LOAD_CURRENT,
			LOAD_NEXT,
			LOAD_PREVIOUS,
			LOAD_DATE,
			LOAD_DATE_RANGE,
			LOAD_DATE_BY_BID,
			LOAD_DATE_RANGE_BY_BID;
		}
		public static final String ADD_INSTANCE = "/instance/add";
		public static final String ADD_INSTANCES = "/s/instance/add";
		public static final String LOAD_FIRST = "/load/first";
		public static final String LOAD_CURRENT = "/load/current";		
		public static final String LOAD_NEXT = "/load/next";
		public static final String LOAD_PREVIOUS = "/load/previous";		
		public static final String LOAD_DATE = "/load/date";		
		public static final String LOAD_DATE_RANGE = "/load/daterange";		
		public static final String LOAD_DATE_BY_BID = "/load/date/by/business/id";		
		public static final String LOAD_DATE_RANGE_BY_BID = "/load/daterange/by/business/id";		
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.ADD_INSTANCE, new String[]{"/instance/add","/instance/add?useObjectId=%s","POST"});
			Call.put(Request.ADD_INSTANCES, new String[]{"/s/instance/add","/s/instance/add?useObjectId=%s","PSOT"});
			Call.put(Request.LOAD_FIRST, new String[]{"/load/first","/load/first?businessId=%s&primary=%s","GET"});
			Call.put(Request.LOAD_CURRENT, new String[]{"/load/current","/load/current?businessId=%s&primary=%s","GET"});		
			Call.put(Request.LOAD_NEXT, new String[]{"/load/next","/load/next?primary=%s","POST"});
			Call.put(Request.LOAD_PREVIOUS, new String[]{"/load/previous","/load/previous?primary=%s","POST"});		
			Call.put(Request.LOAD_DATE, new String[]{"/load/date","/load/date?date=%s&primary=%s","GET"});		
			Call.put(Request.LOAD_DATE_RANGE, new String[]{"/load/daterange","/load/daterange?startDate=%s&endDate=%s&primary=%s","GET"});		
			Call.put(Request.LOAD_DATE_BY_BID, new String[]{"/load/date/by/business/id","/load/date/by/business/id?businessId=%s&date=%s&primary=%s","GET"});		
			Call.put(Request.LOAD_DATE_RANGE_BY_BID, new String[]{"/load/daterange/by/business/id","/load/daterange/by/business/id?businessId=%s&startDate=%s&endDate=%s&primary=%s","GET"});		
		}
		public static String callRequest(String prefix, InstanceDaoService.Request request) {
			return prefix + InstanceDaoService.Call.get(request)[1];
		}
		public static String path(String prefix, InstanceDaoService.Request request) {
			return prefix + InstanceDaoService.Call.get(request)[0];
		}
	}
	
	public static class ProcessReferenceDaoService {
		public static enum Request {
			LOAD,
			ADD_REFERENCE,
			ADD_REFERENCES,
			DEL_REFERENCE,
			DEL_REFERENCES,
			DEL_ALL_REFERENCES;
		}
			public static final String LOAD = "/s/process/load";		
			public static final String ADD_REFERENCE = "/process/add";
			public static final String ADD_REFERENCES = "/s/process/add";
			public static final String DEL_REFERENCE = "/process/del";
			public static final String DEL_REFERENCES = "/s/process/del";		
			public static final String DEL_ALL_REFERENCES = "/s/process/del/all";		
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.LOAD, new String[]{"/s/process/load","/s/process/load?businessId=%s","GET"});		
			Call.put(Request.ADD_REFERENCE, new String[]{"/process/add","/process/add?businessId=%s&instanceId=%s&processKey=%s","GET"});
			Call.put(Request.ADD_REFERENCES, new String[]{"/s/process/add","/s/process/add?businessId=%s","POST"});
			Call.put(Request.DEL_REFERENCE, new String[]{"/process/del","/process/del?businessId=%s&instanceId=%s&processKey=%s","GET"});
			Call.put(Request.DEL_REFERENCES, new String[]{"/s/process/del","/s/process/del?businessId=%s","POST"});		
			Call.put(Request.DEL_ALL_REFERENCES, new String[]{"/s/process/del/all","/s/process/del/all?businessId=%s","GET"});		
		}
		public static String callRequest(String prefix, ProcessReferenceDaoService.Request request) {
			return prefix + ProcessReferenceDaoService.Call.get(request)[1];
		}
		public static String path(String prefix, ProcessReferenceDaoService.Request request) {
			return prefix + ProcessReferenceDaoService.Call.get(request)[0];
		}
	}
	
  public static class ReferenceDaoService {
		public static enum Request {
			LOAD,
			ADD_REFERENCE,
			ADD_REFERENCES,
			DEL_REFERENCE,
			DEL_REFERENCES,
			DEL_ALL_REFERENCES
			;
		}
			public static final String LOAD = "/s/load";		
			public static final String ADD_REFERENCE = "/add";
			public static final String ADD_REFERENCES = "/s/add";
			public static final String DEL_REFERENCE = "/del";
			public static final String DEL_REFERENCES = "/s/del";		
			public static final String DEL_ALL_REFERENCES = "/s/del/all";		
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.LOAD, new String[]{"/s/load","/s/load?parentBusinessId=%s","GET"});		
			Call.put(Request.ADD_REFERENCE, new String[]{"/add","/add?parentBusinessId=%s&businessId=%s","GET"});
			Call.put(Request.ADD_REFERENCES, new String[]{"/s/add","/s/add?parentBusinessId=%s","POST"});
			Call.put(Request.DEL_REFERENCE, new String[]{"/del","/del?parentBusinessId=%s&businessId=%s","GET"});
			Call.put(Request.DEL_REFERENCES, new String[]{"/s/del","/s/del?parentBusinessId=%s","POST"});		
			Call.put(Request.DEL_ALL_REFERENCES, new String[]{"/s/del/all","/s/del/all?parentBusinessId=%s","GET"});		
		}
		public static String callRequest(String prefix, ReferenceDaoService.Request request) {
			return prefix + ReferenceDaoService.Call.get(request)[1];
		}
		public static String path(String prefix, ReferenceDaoService.Request request) {
			return prefix + ReferenceDaoService.Call.get(request)[0];
		}
	}
	
	public static class ModelReferenceDaoService {
		public static final String Prefix = "/model";
		public static String callRequest(String prefix, ReferenceDaoService.Request request) {
			return prefix + Prefix + ReferenceDaoService.Call.get(request)[1];
		}
		public static String path(String prefix, ReferenceDaoService.Request request) {
			return prefix +Prefix + ReferenceDaoService.Call.get(request)[0];
		}
	}
	
	public static class OrderPositionReferenceDaoService {
		public static final String Prefix = "/position";
		public static String callRequest(String prefix, ReferenceDaoService.Request request) {
			return prefix + Prefix + ReferenceDaoService.Call.get(request)[1];
		}
		public static String path(String prefix, ReferenceDaoService.Request request) {
			return prefix + Prefix + ReferenceDaoService.Call.get(request)[0];
		}
	}
	
	public static class ProcessControlDaoService {
		public static enum Request {
			START_PROCESS,
			NOTIFY_PROCESSES,
			NOTIFY_PROCESS,
			NOTIFY_PROCESS_GET,
			NOTIFY_PROCESS_EVENT,
			SIGNAL_PROCESS,
			SIGNAL_PROCESSES,
			CLAIM_TASK,
			DELEGATE_TASK,
			COMPLETE_TASK,
//			COMPLETE_CURRENT_TASK,
			GET_TASK,
			GET_TASKS,
			GET_TASKS_KEY,
			GET_EXECUTIONS;
		}
			public static final String START_PROCESS = "/process/start/{processKey}";//	package ObjectInstance
			public static final String NOTIFY_PROCESS = "/process/notify";		
			public static final String NOTIFY_PROCESS_GET = "/process/notify/get";		
			public static final String NOTIFY_PROCESSES = "/s/process/notify";		// package = ObjectInstance
			public static final String NOTIFY_PROCESS_EVENT = "/process/notify/event"; //package = ObjectInstance		
			public static final String SIGNAL_PROCESS = "/process/signal"; //package = Variables
			public static final String SIGNAL_PROCESSES = "/s/process/signal"; //package = SignalPacket
			public static final String CLAIM_TASK = "/task/claim";		
			public static final String DELEGATE_TASK = "/task/delegate";		
			public static final String COMPLETE_TASK = "/task/complete"; //package = Variables		
			public static final String GET_TASK = "/task/get";		
			public static final String GET_TASKS = "/s/task/get";		
			public static final String GET_TASKS_KEY = "/s/task/key/get";		
			public static final String GET_EXECUTIONS = "/execution/get";		
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.START_PROCESS, new String[]{"/process/start/{processKey}","/process/start?processKey=%s","POST"});//	package ObjectInstance
			Call.put(Request.NOTIFY_PROCESS, new String[]{"/process/notify","/process/notify?processInstanceId=%s&messageType=%s&messageName=%s","POST"});		
			Call.put(Request.NOTIFY_PROCESS_GET, new String[]{"/process/notify/get","/process/notify/get?processInstanceId=%s&messageName=%s&businessKey=%s&objectStatus=%s&objectBusinessId=%s&objectId=%s","POST"});		
			Call.put(Request.NOTIFY_PROCESSES, new String[]{"/s/process/notify","/s/process/notify?messageType=%s&messageName=%s","POST"});		// package = ObjectInstance
			Call.put(Request.NOTIFY_PROCESS_EVENT, new String[]{"/process/notify/event","/process/notify/event?processInstanceId=%s&businessKey=%s&messageName=%s","POST"}); //package = ObjectInstance		
			Call.put(Request.SIGNAL_PROCESS, new String[]{"/process/signal","/process/signal?processInstanceId=%s&businessKey=%s","POST"}); //package = Variables
			Call.put(Request.SIGNAL_PROCESSES, new String[]{"/s/process/signal","/s/process/signal","POST"}); //package = SignalPacket
			Call.put(Request.CLAIM_TASK, new String[]{"/task/claim","/task/claim?taskId=%s&userId=%s","GET"});		
			Call.put(Request.DELEGATE_TASK, new String[]{"/task/delegate","/task/delegate?taskId=%s&userId=%s","GET"});		
			Call.put(Request.COMPLETE_TASK, new String[]{"/task/complete","/task/complete?taskId=%s","POST"}); //package = Variables		
			Call.put(Request.GET_TASK, new String[]{"/task/get","/task/get?taskId=%s","GET"});		
			Call.put(Request.GET_TASKS, new String[]{"/s/task/get","/s/task/get?processInstanceId=%s","GET"});		
			Call.put(Request.GET_TASKS_KEY, new String[]{"/s/task/key/get","/s/task/key/get?processInstanceId=%s&businessKey=%s","GET"});		
			Call.put(Request.GET_EXECUTIONS, new String[]{"/execution/get","/execution/get/instanceId=%s&executionId=%s","GET"});		
// TODO: maybe			Call.put(Request.COMPLETE_CURRENT_TASK, new String[]{"/task/complete/current/{instanceId}","/task/complete/current?instanceId=%s","POST"});		
		}
		public static String callRequest(String prefix, ProcessControlDaoService.Request request) {
			return prefix + ProcessControlDaoService.Call.get(request)[1];
		}
		public static String path(String prefix, ProcessControlDaoService.Request request) {
			return prefix + ProcessControlDaoService.Call.get(request)[0];
		}
		
	}

	public static class ProcessDaoService {
		public static enum Request {
			LOAD_BY_ID,
			LOAD_BY_INSTANCE_ID,
			LOAD_BY_BUSINESS_ID,
			LOAD_BY_KEY,
			SAVE,
			SAVE_LIST,
			UPDATE,
			UPDATE_LIST,
			LOAD_UPDATE,
			LOAD_UPDATES,
			LOAD_UPDATES_KEY,
			LOAD_UPDATES_TARGET,
			ADD_UPDATE,
			ADD_UPDATES,
			DELETE_ALL_UPDATES,
			DELETE_UPDATES,
			DELETE_UPDATE;
		};
			public static final String LOAD_BY_ID = "/load/by/id/{id}";
			public static final String LOAD_BY_INSTANCE_ID = "/load/by/instanceId";
			public static final String LOAD_BY_BUSINESS_ID = "/load/by/businessId";
			public static final String LOAD_BY_KEY = "/load/by/key";
			public static final String SAVE = "/save";
			public static final String SAVE_LIST = "/s/save";
			public static final String UPDATE = "/update";
			public static final String UPDATE_LIST = "/s/update";
			public static final String LOAD_UPDATE = "/updates/load";
			public static final String LOAD_UPDATES = "/s/updates/load";
			public static final String LOAD_UPDATES_KEY = "/s/updates/key/load";
			public static final String LOAD_UPDATES_TARGET = "/s/updates/target/load";
			public static final String ADD_UPDATE = "/updates/add";
			public static final String ADD_UPDATES = "/s/updates/add";
			public static final String DELETE_ALL_UPDATES = "/s/updates/del/all";
			public static final String DELETE_UPDATES = "/s/updates/del";
			public static final String DELETE_UPDATE = "/updates/del";
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.LOAD_BY_ID, new String[] {"/load/by/id/{id}","/load/by/id/%s","GET"});
			Call.put(Request.LOAD_BY_INSTANCE_ID, new String[] {"/load/by/instanceId","/load/by/instanceId?instanceId=%s","GET"});
			Call.put(Request.LOAD_BY_BUSINESS_ID, new String[] {"/load/by/businessId","/load/by/businessId?businessId=%s","GET"});
			Call.put(Request.LOAD_BY_KEY, new String[] {"/load/by/key","/load/by/key?businessKey=%s","GET"});
			Call.put(Request.SAVE, new String[] {"/save","/save","POST"});
			Call.put(Request.SAVE_LIST, new String[] {"/s/save","/s/save","POST"});
			Call.put(Request.UPDATE, new String[] {"/update","/update","POST"});
			Call.put(Request.UPDATE_LIST, new String[] {"/s/update","/s/update","POST"});
			Call.put(Request.LOAD_UPDATE, new String[] {"/updates/load","/updates/load?businessId=%s&businessKey=%s&target=%s","GET"});
			Call.put(Request.LOAD_UPDATES, new String[] {"/s/updates/load","/s/updates/load?businessKey=%s&target=%s","GET"});
			Call.put(Request.LOAD_UPDATES_KEY, new String[] {"/s/updates/key/load","/s/updates/key/load?businessKey=%s","GET"});
			Call.put(Request.LOAD_UPDATES_TARGET, new String[] {"/s/updates/target/load","/s/updates/target/load?target=%s","GET"});
			Call.put(Request.ADD_UPDATE, new String[] {"/updates/add","/updates/add?businessId=%s&businessKey=%s&target=%s","GET"});
			Call.put(Request.ADD_UPDATES, new String[] {"/s/updates/add","/s/updates/add?businessKey=%s&target=%s","POST"});
			Call.put(Request.DELETE_ALL_UPDATES, new String[] {"/s/updates/del/all","/s/updates/del/all?businessKey=%s&target=%s","GET"});
			Call.put(Request.DELETE_UPDATES, new String[] {"/s/updates/del","/s/updates/del?businessKey=%s&target=%s","POST"});
			Call.put(Request.DELETE_UPDATE, new String[] {"/updates/del","/updates/del?instanceId=%s&businessId=%s&businessKey=%s&target=%s","GET"});
		}
		public static String callRequest(String prefix, DaoService.Request request) {
			return prefix + DaoService.Call.get(request)[1];
		}
		public static String path(String prefix, DaoService.Request request) {
			return prefix + DaoService.Call.get(request)[0];
		}
	}
	

	public static class AttributeDaoService {
		public static final String Prefix = "/attr"; 
		public static enum Request {
			LOAD_BY_OBJECT_ID,
			SAVE_BY_OBJECT_ID,
			UPDATE_BY_OBJECT_ID,
			LOAD_ALL,
			SAVE_ALL,
			UPDATE_ALL;
		}
			public static final String LOAD_BY_OBJECT_ID = Prefix+"/s/load";
			public static final String SAVE_BY_OBJECT_ID = Prefix+"/s/save";
			public static final String UPDATE_BY_OBJECT_ID = Prefix+"/s/update";
			public static final String LOAD_ALL = Prefix+"/s/load/list";
			public static final String SAVE_ALL = Prefix+"/s/save/list";
			public static final String UPDATE_ALL = Prefix+"/s/update/list";
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.LOAD_BY_OBJECT_ID, new String[]{"/s/load","/s/load?objectId=%s","GET"});
			Call.put(Request.SAVE_BY_OBJECT_ID, new String[]{"/s/save","/s/save?objectId=%s","POST"});
			Call.put(Request.UPDATE_BY_OBJECT_ID, new String[]{"/s/update","/s/update?objectId=%s","POST"});
			Call.put(Request.LOAD_ALL, new String[]{"/s/load/list","/s/load/list?objectId=%s","GET"});
			Call.put(Request.SAVE_ALL, new String[]{"/s/save/list","/s/save/list?objectId=%s","POST"});
			Call.put(Request.UPDATE_ALL, new String[]{"/s/update/list","/s/update/list?objectId=%s","POST"});
		}
		
		public static String callRequest(String prefix, AttributeDaoService.Request request) {
			return prefix + Prefix + AttributeDaoService.Call.get(request)[1];
		}
		public static String path(String prefix, AttributeDaoService.Request request) {
			return prefix + Prefix + AttributeDaoService.Call.get(request)[0];
		}

	}
	public static class AttributeDefinitionDaoService {
		public static final String Prefix = "/attrd";
		public static enum Request {
			LOAD_BY_ID,
			LOAD_BY_BUSINESS_ID,
			LOAD_BY_BUSINESS_KEY,
			LOAD_BY_TYPE,
			LOAD_BY_GROUP,
			LOAD_BY_GROUP_VERSION,
			LOAD_AFTER_POSITION,
			LOAD_BEFORE_POSITION,
			LOAD_POSITION_RANGE,
			SAVE,
			SAVE_LIST,
			UPDATE,
			UPDATE_LIST,
			DELETE_BY_ID,
			DELETE_BY_BUSINESS_ID,
			DELETE_BY_PARENT_ID,
			DELETE_LIST;
		}
			public static final String LOAD_BY_ID = Prefix + "/load/by/id/{id}";
			public static final String LOAD_BY_BUSINESS_ID = Prefix + "/load/by/business/id";
			public static final String LOAD_BY_BUSINESS_KEY = Prefix + "/s/load/by/business/key";
			public static final String LOAD_BY_TYPE = Prefix + "/s/load/by/type";
			public static final String LOAD_BY_GROUP = Prefix + "/s/load/by/group";
			public static final String LOAD_BY_GROUP_VERSION = Prefix + "/s/load/by/group/version";
			public static final String LOAD_AFTER_POSITION = Prefix + "/s/load/before/position";		
			public static final String LOAD_BEFORE_POSITION = Prefix + "/s/load/after/position";		
			public static final String LOAD_POSITION_RANGE = Prefix + "/s/load/position/range";		
			public static final String SAVE = Prefix + "/save";		
			public static final String SAVE_LIST = Prefix + "/s/save";		
			public static final String UPDATE = Prefix + "/update";		
			public static final String UPDATE_LIST = Prefix + "/s/update";		
			public static final String DELETE_BY_ID = Prefix + "/delete/{id}";		
			public static final String DELETE_BY_BUSINESS_ID = Prefix + "/delete/by/business/id";		
			public static final String DELETE_BY_PARENT_ID = Prefix + "/delete/by/parent/id";		
			public static final String DELETE_LIST = Prefix + "/s/delete";		
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.LOAD_BY_ID, new String[] {"/load/by/id/{id}","/load/by/id/%s","GET"});
			Call.put(Request.LOAD_BY_BUSINESS_ID, new String[] {"/load/by/business/id","/load/by/business/id?businessId=%s","GET"});
			Call.put(Request.LOAD_BY_BUSINESS_KEY, new String[] {"/s/load/by/business/key","/s/load/by/business/key?businessKey=%s","GET"});
			Call.put(Request.LOAD_BY_TYPE, new String[] {"/s/load/by/type","/s/load/by/type?type=%s","GET"});
			Call.put(Request.LOAD_BY_GROUP, new String[] {"/s/load/by/group","/s/load/by/group?group=%s&version=%s","GET"});
			Call.put(Request.LOAD_BY_GROUP_VERSION, new String[] {"/s/load/by/group/version","/s/load/by/group/version?group=%s&version=%s","GET"});
			Call.put(Request.LOAD_AFTER_POSITION, new String[] {"/s/load/before/position","/s/load/before/position?id=%s&position=%","GET"});		
			Call.put(Request.LOAD_BEFORE_POSITION, new String[] {"/s/load/after/position","/s/load/after/position?id=%s&position=%","GET"});		
			Call.put(Request.LOAD_POSITION_RANGE, new String[] {"/s/load/position/range","/s/load/position/range?id=%s&startPosition=%&endPosition=%","GET"});		
			Call.put(Request.SAVE, new String[] {"/save","/save","POST"});		
			Call.put(Request.SAVE_LIST, new String[] {"/s/save","/s/save","POST"});		
			Call.put(Request.UPDATE, new String[] {"/update","/update","POST"});		
			Call.put(Request.UPDATE_LIST, new String[] {"/s/update","/s/update","POST"});		
			Call.put(Request.DELETE_BY_ID, new String[] {"/delete/{id}","/delete/%s","GET"});		
			Call.put(Request.DELETE_BY_BUSINESS_ID, new String[] {"/delete/by/business/id","/delete/by/business/id?businessId=%s","GET"});		
			Call.put(Request.DELETE_BY_PARENT_ID, new String[] {"/delete/by/parent/id","/delete/by/parent/id?parentId=%s","GET"});		
			Call.put(Request.DELETE_LIST, new String[] {"/s/delete","/s/delete","POST"});		
		}
		public static String callRequest(String prefix, AttributeDefinitionDaoService.Request request) {
			return prefix + Prefix + AttributeDefinitionDaoService.Call.get(request)[1];
		}
		public static String path(String prefix, AttributeDefinitionDaoService.Request request) {
			return prefix + Prefix + AttributeDefinitionDaoService.Call.get(request)[0];
		}

	}
	public static class AttributeValueDaoService {
		
		public static final String Prefix = "/attrv";
		
		public static enum Request {
			LOAD,//v
			LOAD_LIST,
			LOAD_BY_GROUP,
			LOAD_AFTER_POSITION,
			LOAD_BEFORE_POSITION,
			LOAD_POSITION_RANGE,
			SAVE,
			SAVE_LIST,
			UPDATE,
			UPDATE_LIST,
			DELETE,
			DELETE_GET,
			DELETE_LIST,
			LOAD_ALL_UPDATES,
			LOAD_TARGET_UPDATES,
			LOAD_BUSINESSKEY_UPDATES,
			LOAD_UPDATE,
			ADD_UPDATE,
			ADD_UPDATES,
			DELETE_ALL_UPDATES,
			DELETE_KEY_UPDATES,
			DELETE_TARGET_UPDATES,
			DELETE_UPDATE,
			DELETE_UPDATES;
		}
			public static final String LOAD = Prefix + "/load";
			public static final String LOAD_LIST = Prefix + "/s/load";
			public static final String LOAD_BY_GROUP = Prefix + "/s/load/by/group";
			public static final String LOAD_AFTER_POSITION = Prefix + "/s/load/before/position";		
			public static final String LOAD_BEFORE_POSITION = Prefix + "/s/load/after/position";		
			public static final String LOAD_POSITION_RANGE = Prefix + "/s/load/position/range";		
			public static final String SAVE = Prefix + "/save";		
			public static final String SAVE_LIST = Prefix + "/s/save";		
			public static final String UPDATE = Prefix + "/update";		
			public static final String UPDATE_LIST = Prefix + "/s/update";		
			public static final String DELETE = Prefix + "/delete";		
			public static final String DELETE_GET = Prefix + "/delete/get";		
			public static final String DELETE_LIST = Prefix + "/s/delete";		
			public static final String LOAD_ALL_UPDATES = Prefix + "/s/updates/load";
			public static final String LOAD_TARGET_UPDATES = Prefix + "/s/updates/target/load";		
			public static final String LOAD_BUSINESSKEY_UPDATES = Prefix + "/s/updates/key/load";		
			public static final String LOAD_UPDATE = Prefix + "/updates/load";		
			public static final String ADD_UPDATE = Prefix + "/updates/add";		
			public static final String ADD_UPDATES = Prefix + "/s/updates/add";		
			public static final String DELETE_ALL_UPDATES = Prefix + "/s/updates/del";		
			public static final String DELETE_KEY_UPDATES = Prefix + "/s/updates/del/key";		
			public static final String DELETE_TARGET_UPDATES = Prefix + "/s/updates/del/target";		
			public static final String DELETE_UPDATE = Prefix + "/updates/del";		
			public static final String DELETE_UPDATES = Prefix + "/s/updates/list/del";		
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.LOAD, new String[] {"/load","/load/?objectId=%s","POST"});
			Call.put(Request.LOAD_LIST, new String[] {"/s/load","/s/load/?objectId=%s","POST"});
			Call.put(Request.LOAD_BY_GROUP, new String[] {"/s/load/by/group","/s/load/by/group?group=%s&objectId=%s","POST"});
			Call.put(Request.LOAD_AFTER_POSITION, new String[] {"/s/load/before/position","/s/load/before/position?objectid=%s&position=%s","POST"});		
			Call.put(Request.LOAD_BEFORE_POSITION, new String[] {"/s/load/after/position","/s/load/after/position?objectid=%s&position=%s","POST"});		
			Call.put(Request.LOAD_POSITION_RANGE, new String[] {"/s/load/position/range","/s/load/position/range?objectid=%s&startPosition=%&endPosition=%s","POST"});		
			Call.put(Request.SAVE, new String[] {"/save","/save?objectId=%s","POST"});		
			Call.put(Request.SAVE_LIST, new String[] {"/s/save","/s/save?objectId=%s","POST"});		
			Call.put(Request.UPDATE, new String[] {"/update","/update?objectId=%s","POST"});		
			Call.put(Request.UPDATE_LIST, new String[] {"/s/update","/s/update?objectId=%s","POST"});		
			Call.put(Request.DELETE, new String[] {"/delete","/delete?objectId=%s","POST"});		
			Call.put(Request.DELETE_GET, new String[] {"/delete/get","/delete/get?objectId=%s&attributeId=%s&valueId=%s&type=%s","GET"});		
			Call.put(Request.DELETE_LIST, new String[] {"/s/delete","/s/delete","POST"});		
			Call.put(Request.LOAD_ALL_UPDATES, new String[] {"/s/updates/load","/s/updates/load?businessKey=%s&target=%s","GET"});
			Call.put(Request.LOAD_TARGET_UPDATES, new String[] {"/s/updates/target/load","/s/updates/target/load?target=%s","GET"});		
			Call.put(Request.LOAD_BUSINESSKEY_UPDATES, new String[] {"/s/updates/key/load","/s/updates/key/load?businessKey=%s","GET"});		
			Call.put(Request.LOAD_UPDATE, new String[] {"/updates/load","/updates/load?businessKey=%s&target=%s","POST"});		
			Call.put(Request.ADD_UPDATE, new String[] {"/updates/add","/updates/add?businessKey=%s&target=%s","POST"});		
			Call.put(Request.ADD_UPDATES, new String[] {"/s/updates/add","/s/updates/add?businessKey=%s&target=%s","POST"});		
			Call.put(Request.DELETE_ALL_UPDATES, new String[] {"/s/updates/del","/s/updates/del?businessKey=%s&target=%s","GET"});		
			Call.put(Request.DELETE_KEY_UPDATES, new String[] {"/s/updates/del/key","/s/updates/del/key?businessKey=%s","GET"});
			Call.put(Request.DELETE_TARGET_UPDATES, new String[] {"/s/updates/del/target","/s/updates/del/target?target=%s","GET"});
			Call.put(Request.DELETE_UPDATE, new String[] {"/updates/del","/updates/del?attributeId=%s&objectId=%s&businessKey=%s&target=%s","GET"});		
			Call.put(Request.DELETE_UPDATES, new String[] {"/s/updates/list/del","/s/updates/list/del?businessKey=%s&target=%s","POST"});		
		}
		public static String callRequest(String prefix, AttributeValueDaoService.Request request) {
			return prefix + Prefix + AttributeValueDaoService.Call.get(request)[1];
		}
		public static String path(String prefix, AttributeValueDaoService.Request request) {
			return prefix + Prefix + AttributeValueDaoService.Call.get(request)[0];
		}

	}

	public static class ProcessEngineDaoService {
		public static enum Request {
			AUTHENTICATE,
			START_PROCESS,
			MESSAGE_PROCESSES,
			TRIGGER_MESSAGE_SUBSCRIPTION,
			GET_EXECUTIONS,
			SIGNAL_PROCESS,
			UPDATE_VARIABLE,
			GET_TASK,
			GET_TASKS,
			GET_TASKS_KEY,
			CLAIM_TASK,
			COMPLETE_TASK,
			DELEGATE_TASK,
			; // process Key is the processId as given in the bpmn2 process file. generally we use com.camsolute.code.camp.business.OrderProcess
		}
			public static final String AUTHENTICATE = "/identity/verify";
			public static final String GET_TASK = "/task/{taskId}";			
			public static final String GET_TASKS = "/task";			
			public static final String GET_TASKS_KEY = "/task";			
			public static final String CLAIM_TASK = "/task/{taskId}/claim";			
			public static final String COMPLETE_TASK = "/task/{taskid}/submit-form";			
			public static final String DELEGATE_TASK = "/task/{taskId}/delegate";			
			public static final String SIGNAL_PROCESS = "/execution/{executionId}/signal";			
			public static final String UPDATE_VARIABLE = "/execution/{executionId}/localVariables/{variableName}";			
			public static final String GET_EXECUTIONS = "/execution";			
			public static final String TRIGGER_MESSAGE_SUBSCRIPTION = "/execution/{executionId}/messageSubscriptions/{messageName}/trigger";			
			public static final String MESSAGE_PROCESSES = "/message";			
			public static final String START_PROCESS = "/process-definition/key/{processKey}/submit-form";			
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.AUTHENTICATE, new String[] {"/identity/verify","/identity/verify","POST"});
			Call.put(Request.GET_TASK, new String[] {"/task/{taskId}","/task/%s","GET"});			
			Call.put(Request.GET_TASKS, new String[] {"/task","/task?processInstanceId=%s","GET"});			
			Call.put(Request.GET_TASKS_KEY, new String[] {"/task","/task?processInstanceId=%s&businessKey=%s","GET"});			
			Call.put(Request.CLAIM_TASK, new String[] {"/task/{taskId}/claim","/task/%s/claim","POST"});			
			Call.put(Request.COMPLETE_TASK, new String[] {"/task/{taskid}/submit-form","/task/%s/submit-form","POST"});			
			Call.put(Request.DELEGATE_TASK, new String[] {"/task/{taskId}/delegate","/task/%s/delegate","POST"});			
			Call.put(Request.SIGNAL_PROCESS, new String[] {"/execution/{executionId}/signal","/execution/%s/signal","POST"});			
			Call.put(Request.UPDATE_VARIABLE, new String[] {"/execution/{executionId}/localVariables/{variableName}","/execution/%s/localVariables/%s","POST"});			
			Call.put(Request.GET_EXECUTIONS, new String[] {"/execution","/execution?businessKey=%s&processInstanceId=%s","GET"});			
			Call.put(Request.TRIGGER_MESSAGE_SUBSCRIPTION, new String[] {"/execution/{executionId}/messageSubscriptions/{messageName}/trigger","/execution/%s/messageSubscriptions/%s/trigger","POST"});			
			Call.put(Request.MESSAGE_PROCESSES, new String[] {"/message","/message","POST"});			
			Call.put(Request.START_PROCESS, new String[] {"/process-definition/key/{processKey}/submit-form","/process-definition/key/%s/submit-form","POST"});			
		}
		public static String callRequest(String prefix, ProcessEngineDaoService.Request request) {
			return prefix + ProcessEngineDaoService.Call.get(request)[1];
		}
		public static String path(String prefix, ProcessEngineDaoService.Request request) {
			return prefix + ProcessEngineDaoService.Call.get(request)[0];
		}
	}		
 	public static class Order {
		public static final String Prefix = "";
	}
	public static class OrderPosition {
		public static final String Prefix = "";
	}
	public static class Process {
		public static final String Prefix = "";
	}
	public static class ProcessControl {
		public static final String Prefix = "";
	}
 	public static class Product {
		public static final String Prefix = "";
	}
	public static class Attribute {
		public static final String Prefix = "";
	}
	public static class AttributeDefinition {
		public static final String Prefix = "";
	}
	public static class AttributeValue {
		public static final String Prefix = "";
	}
	public static class Model {
		public static final String Prefix = "";
	}
	public static class ProcessEngine {
		public static final String Prefix="";
	}
	//TODO
//	public static void initAuthentication(HttpMethod httpMethod, HttpClient client) {
//        httpMethod.setDoAuthentication(true);
//
//        String authScopeHost = url.getHost();
//        int authScopePort = url.getPort();
//        String authScopeRealm = AuthScope.ANY_REALM;
//        String authScopeScheme = AuthScope.ANY_SCHEME;
//
//        client.getState().setCredentials(
//            new AuthScope(authScopeHost, authScopePort, authScopeRealm, authScopeScheme),
//            new UsernamePasswordCredentials(this.getUsername(), new String(this.getPassword())));
//
//        client.getParams().setAuthenticationPreemptive(true);
//	}

	
}
	
