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
			CREATE, 
			SAVE,
			SAVE_LIST,
			UPDATE,
			UPDATE_LIST,
//			UPDATE_ATTRIBUTE, // TODO: consider this - selective update to reduce traffic load - is this required
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
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.LOAD_BY_ID, new String[] {"/load/by/id/{id}","/load/by/id/%s","GET"});
			Call.put(Request.LOAD, new String[] {"/load/by/businessId","/load/by/businessId?businessId=%s","GET"});
			Call.put(Request.LOAD_BY_KEY, new String[] {"/load/by/key","/load/by/key?businessKey=%s","GET"});
			Call.put(Request.CREATE, new String[] {"/create","/create?businessId=%s&businessKey=%s&date=%s&byDate=%s","GET"});
			Call.put(Request.SAVE, new String[] {"/save","/save","POST"});
			Call.put(Request.SAVE_LIST, new String[] {"/s/save","/s/save","POST"});
			Call.put(Request.UPDATE, new String[] {"/update","/update","POST"});
			Call.put(Request.UPDATE_LIST, new String[] {"/s/update","/s/update","POST"});
//			Call.put(Request.UPDATE_ATTRIBUTE, new String[] {"/update/{businessId}/attribute/{name}/{value}","/update/%s/attribute/%s/%s"});
			Call.put(Request.LOAD_UPDATE, new String[] {"/updates/load","/updates/load?businessId=%s&businessKey=%s&target=%s","GET"});
			Call.put(Request.LOAD_UPDATES, new String[] {"/s/updates/load","/s/updates/load?businessKey=%s&target=%s","GET"});
			Call.put(Request.LOAD_UPDATES_KEY, new String[] {"/s/updates/key/load","/s/updates/key/load?businessKey=%s","GET"});
			Call.put(Request.LOAD_UPDATES_TARGET, new String[] {"/s/updates/target/load","/s/updates/target/load?target=%s","GET"});
			Call.put(Request.ADD_UPDATE, new String[] {"/updates/add","/updates/add?businessId=%s&businessKey=%s&target=%s","GET"});
			Call.put(Request.ADD_UPDATES, new String[] {"/s/updates/add","/s/updates/add?businessKey=%s&target=%s","POST"});
			Call.put(Request.DELETE_ALL_UPDATES, new String[] {"/s/updates/del/all","/s/updates/del/all?businessKey=%s&target=%s","GET"});
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
			DEL_REFERENCES;
		}
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.LOAD, new String[]{"/s/process/load/{businessId}","/s/process/load?businessId=%s","GET"});		
			Call.put(Request.ADD_REFERENCE, new String[]{"/process/add/{businessId}/{instanceId}/{processKey}","/process/add?businessId=%s&instanceId=%s&processKey=%s","GET"});
			Call.put(Request.ADD_REFERENCES, new String[]{"/s/process/add/{businessId}","/s/process/add?businessId=%s","POST"});
			Call.put(Request.DEL_REFERENCE, new String[]{"/process/del/{businessId}/{instanceId}/{processKey}","/process/del?businessId=%s&instanceId=%s&processKey=%s","GET"});
			Call.put(Request.DEL_REFERENCES, new String[]{"/s/process/del/{businessId}","/s/process/del?businessId=%s","POST"});		
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
			DEL_REFERENCES;
		}
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.LOAD, new String[]{"/s/load","/s/load?parentBusinessId=%s","GET"});		
			Call.put(Request.ADD_REFERENCE, new String[]{"/add","/add?parentBusinessId=%s&businessId=%s","GET"});
			Call.put(Request.ADD_REFERENCES, new String[]{"/s/add","/s/add?parentBusinessId=%s","POST"});
			Call.put(Request.DEL_REFERENCE, new String[]{"/del","/del?parentBusinessId=%s&businessId=%s","GET"});
			Call.put(Request.DEL_REFERENCES, new String[]{"/s/del","/s/del?parentBusinessId=%s","POST"});		
		}
		public static String callRequest(String prefix, ReferenceDaoService.Request request) {
			return prefix + ReferenceDaoService.Call.get(request)[1];
		}
		public static String path(String prefix, ReferenceDaoService.Request request) {
			return prefix + ReferenceDaoService.Call.get(request)[0];
		}
	}
	
	public static class ModelReferenceDaoService {
		public static String callRequest(String prefix, ReferenceDaoService.Request request) {
			return prefix + "/model" + ReferenceDaoService.Call.get(request)[1];
		}
		public static String path(String prefix, ReferenceDaoService.Request request) {
			return prefix + "/model" + ReferenceDaoService.Call.get(request)[0];
		}
	}
	
	public static class OrderPositionReferenceDaoService {
		public static String callRequest(String prefix, ReferenceDaoService.Request request) {
			return prefix + "/position" + ReferenceDaoService.Call.get(request)[1];
		}
		public static String path(String prefix, ReferenceDaoService.Request request) {
			return prefix + "/position" + ReferenceDaoService.Call.get(request)[0];
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
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.START_PROCESS, new String[]{"/process/start/{processKey}","/process/start?processKey=%s","POST"});//	package ObjectInstance
			Call.put(Request.NOTIFY_PROCESS, new String[]{"/process/notify","/process/notify?processInstanceId=%s&messageType=%s","POST"});		
			Call.put(Request.NOTIFY_PROCESS_GET, new String[]{"/process/notify/","/process/notify?processInstanceId=%s&messageType=%s&objectStatus=%s&objectBusinessId=%s&objectId=%s","POST"});		
			Call.put(Request.NOTIFY_PROCESSES, new String[]{"/s/process/notify","/s/process/notify?messageType=%s","POST"});		// package = ObjectInstance
			Call.put(Request.NOTIFY_PROCESS_EVENT, new String[]{"/process/notify","/process/notify?messageType=%s&executionId=%s","POST"}); //package = ObjectInstance		
			Call.put(Request.SIGNAL_PROCESS, new String[]{"/process/signal/{executionId}","/process/signal?&executionId=%s","POST"}); //package = Variables
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
		public static enum Request {
			LOAD_BY_OBJECT_ID,
			SAVE_BY_OBJECT_ID,
			UPDATE_BY_OBJECT_ID,
			LOAD_ALL,
			SAVE_ALL,
			UPDATE_ALL;
		}
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.LOAD_BY_OBJECT_ID, new String[]{"/s/load/{objectId}","/s/load/%s","GET"});
			Call.put(Request.SAVE_BY_OBJECT_ID, new String[]{"/s/save/{objectId}","/s/save/%s","POST"});
			Call.put(Request.UPDATE_BY_OBJECT_ID, new String[]{"/s/update/{objectId}","/s/update/%s","POST"});
			Call.put(Request.LOAD_ALL, new String[]{"/s/load/list/{objectId}","/s/load/list/%s","GET"});
			Call.put(Request.SAVE_ALL, new String[]{"/s/save/list/{objectId}","/s/save/list/%s","POST"});
			Call.put(Request.UPDATE_ALL, new String[]{"/s/update/list/{objectId}","/s/update/list/%s","POST"});
		}
		
		public static String callRequest(String prefix, AttributeDaoService.Request request) {
			return prefix + "/attr" + AttributeDaoService.Call.get(request)[1];
		}
		public static String path(String prefix, AttributeDaoService.Request request) {
			return prefix + "/attr" + AttributeDaoService.Call.get(request)[0];
		}

	}
	public static class AttributeDefinitionDaoService {
		public static enum Request {
			LOAD_BY_ID,
			LOAD_BY_BUSINESS_ID,
			LOAD_BY_BUSINESS_KEY,
			LOAD_BY_TYPE,
			LOAD_BY_GROUP,
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
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.LOAD_BY_ID, new String[] {"/load/by/id/{id}","/load/by/id/%s","GET"});
			Call.put(Request.LOAD_BY_BUSINESS_ID, new String[] {"/load/by/business/id","/load/by/business/id?businessId=%s","GET"});
			Call.put(Request.LOAD_BY_BUSINESS_KEY, new String[] {"/s/load/by/business/key","/s/load/by/business/key?businessKey=%s","GET"});
			Call.put(Request.LOAD_BY_TYPE, new String[] {"/s/load/by/type","/s/load/by/type?type=%s","GET"});
			Call.put(Request.LOAD_BY_GROUP, new String[] {"/s/load/by/group","/s/load/by/group?group=%s&parentId=%","GET"});
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
			return prefix + "/attr" + AttributeDefinitionDaoService.Call.get(request)[1];
		}
		public static String path(String prefix, AttributeDefinitionDaoService.Request request) {
			return prefix + "/attr" + AttributeDefinitionDaoService.Call.get(request)[0];
		}

	}
	public static class AttributeValueDaoService {
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
			DELETE_UPDATE,
			DELETE_UPDATES;
		}
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.LOAD, new String[] {"/load/{objectId}","/load/%s","POST"});
			Call.put(Request.LOAD_LIST, new String[] {"/s/load/{objectId}","/s/load/%s","POST"});
			Call.put(Request.LOAD_BY_GROUP, new String[] {"/s/load/by/group","/s/load/by/group?group=%s&objectId=%","POST"});
			Call.put(Request.LOAD_AFTER_POSITION, new String[] {"/s/load/before/position","/s/load/before/position?objectid=%s&position=%","POST"});		
			Call.put(Request.LOAD_BEFORE_POSITION, new String[] {"/s/load/after/position","/s/load/after/position?objectid=%s&position=%","POST"});		
			Call.put(Request.LOAD_POSITION_RANGE, new String[] {"/s/load/position/range","/s/load/position/range?objectid=%s&startPosition=%&endPosition=%","POST"});		
			Call.put(Request.SAVE, new String[] {"/save","/save?objectId=%s","POST"});		
			Call.put(Request.SAVE_LIST, new String[] {"/s/save","/s/save?objectId=%s","POST"});		
			Call.put(Request.UPDATE, new String[] {"/update","/update?objectId=%s","POST"});		
			Call.put(Request.UPDATE_LIST, new String[] {"/s/update","/s/update?objectId=%s","POST"});		
			Call.put(Request.DELETE, new String[] {"/delete","/delete?objectId=%s&attributeId=%s&valueId=%s&type=%s","GET"});		
			Call.put(Request.DELETE_GET, new String[] {"/delete","/delete?objectId=%s&attributeId=%s&valueId=%s&type=%s","GET"});		
			Call.put(Request.DELETE_LIST, new String[] {"/s/delete","/s/delete","POST"});		
			Call.put(Request.LOAD_ALL_UPDATES, new String[] {"/s/updates/load","/s/updates/load?businessKey=%s&target=%s","GET"});
			Call.put(Request.LOAD_TARGET_UPDATES, new String[] {"/s/updates/target/load","/s/updates/target/load?target=%s","GET"});		
			Call.put(Request.LOAD_BUSINESSKEY_UPDATES, new String[] {"/s/updates/key/load","/s/updates/key/load?businessKey=%s","GET"});		
			Call.put(Request.LOAD_UPDATE, new String[] {"/updates/load","/updates/load?businessKey=%s&target=%s","POST"});		
			Call.put(Request.ADD_UPDATE, new String[] {"/updates/add","/updates/add?businessKey=%s&target=%s","POST"});		
			Call.put(Request.ADD_UPDATES, new String[] {"/s/updates/add","/s/updates/add?businessKey=%s&target=%s","POST"});		
			Call.put(Request.DELETE_ALL_UPDATES, new String[] {"/s/updates/del","/s/updates/del?businessKey=%s&target=%s","GET"});		
			Call.put(Request.DELETE_UPDATE, new String[] {"/updates/del","/updates/del?attributeId=%s&objectId=%s&businessKey=%s&target=%s","GET"});		
			Call.put(Request.DELETE_UPDATES, new String[] {"/s/updates/list/del","/s/updates/list/del?businessKey=%s&target=%s","POST"});		
		}
		public static String path(String prefix, AttributeValueDaoService.Request request) {
			return prefix + "/attr" + AttributeValueDaoService.Call.get(request)[0];
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
			CLAIM_TASK,
			COMPLETE_TASK,
			DELEGATE_TASK,
			; // process Key is the processId as given in the bpmn2 process file. generally we use com.camsolute.code.camp.business.OrderProcess
		}
		public static final HashMap<Request,String[]> Call;
		
		static {
			Call = new HashMap<Request,String[]>();
			Call.put(Request.AUTHENTICATE, new String[] {"/identity/verify","/identity/verify","POST"});
			Call.put(Request.GET_TASK, new String[] {"/task/{taskId}","/task/%s","GET"});			
			Call.put(Request.GET_TASKS, new String[] {"/task","/task?processInstanceId=%s&businessKey=%s","GET"});			
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
		public static final String Prefix = "/osc";
	}
	public static class OrderPosition {
		public static final String Prefix = "/opsc";
	}
	public static class Process {
		public static final String Prefix = "/procsc";
	}
	public static class ProcessControl {
		public static final String Prefix = "/procctrsc";
	}
 	public static class Product {
		public static final String Prefix = "/prodsc";
	}
	public static class Attribute {
		public static String Prefix = "/afc";
	}
	public static class AttributeDefinition {
		public static String Prefix = "/adfc";
	}
	public static class AttributeValue {
		public static String Prefix = "/avfc";
	}
	public static class Model {
		public static final String Prefix = "/mfc";
	}
	public static class ProcessEngine {
		public static String Prefix="";
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
	
