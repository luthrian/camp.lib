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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.contract.HasProcess;
import com.camsolute.code.camp.lib.data.CampRest;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessInterface;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.models.rest.ProcessStartRequest;
import com.camsolute.code.camp.lib.models.rest.Request;
import com.camsolute.code.camp.lib.models.rest.Request.Principal;
import com.camsolute.code.camp.lib.models.rest.Request.RequestType;
import com.camsolute.code.camp.lib.models.rest.SignalPacket;
import com.camsolute.code.camp.lib.models.rest.Task;
import com.camsolute.code.camp.lib.models.rest.TaskList;
import com.camsolute.code.camp.lib.models.rest.Variables;
import com.camsolute.code.camp.lib.utilities.Util;

public class ProcessControlRest implements ProcessControlRestInterface {

	private static final Logger LOG = LogManager.getLogger(ProcessControlRest.class);
	private static String fmt = "[%15s] [%s]";
	
	public static String serverUrl = CampRest.PROCESS_CONTROL_API_SERVER_URL;
	public static String domainUri = CampRest.PROCESS_CONTROL_API_DOMAIN;
	
	private static ProcessControlRest instance = null;
	
	private ProcessControlRest(){
	}
	
	public static ProcessControlRest instance(){
		if(instance == null) {
			instance = new ProcessControlRest();
		}
		return instance;
	}
	
	@Override
	public <T extends HasProcess<T>> Process<?> startProcess(String processKey, T object, Principal principal, boolean log) {
		return startProcess(serverUrl, processKey, object, principal, log);
	}
	public <T extends HasProcess<T>> Process<?> startProcess(String serverUrl, String processKey, T object, Principal principal, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[startProcess]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Request<T> request = new Request<T>(object, principal, RequestType.START_PRINCIPAL_PROCESS); 
		String json = request.toJson();
		String prefix = CampRest.ProcessControl.Prefix;		
		String serviceUri = CampRest.ProcessControlDaoService.callRequest(prefix,CampRest.ProcessControlDaoService.Request.START_PROCESS);
		String uri = serverUrl+domainUri+String.format(serviceUri,processKey);
		if(log && !Util._IN_PRODUCTION){msg = "----[process control service call: uri("+uri+")]----";LOG.info(String.format(fmt, _f,msg));}
		String result = RestInterface.resultPost(uri, json, log);
		if(log && !Util._IN_PRODUCTION){msg = "----[process control rest call: result("+result+")]----";LOG.info(String.format(fmt, _f,msg));}
		Process<?> p = ProcessInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[startProcess completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return p;
	}

	@Override
	public <T extends HasProcess<T>> void messageProcess(Enum<?> messageType, Enum<?> messageName, T object, boolean log) {
		_messageProcess(serverUrl, messageType, messageName, object, log);
	}
	public <T extends HasProcess<T>> void _messageProcess(String serverUrl, Enum<?> messageType, Enum<?> messageName, T object, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[messageProcess]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = object.toJson();
		String prefix = CampRest.ProcessControl.Prefix;		
		String serviceUri = CampRest.ProcessControlDaoService.callRequest(prefix,CampRest.ProcessControlDaoService.Request.NOTIFY_PROCESSES);
		String uri = serverUrl+domainUri+String.format(serviceUri,messageType.name(),messageName.name());
		String result = RestInterface.resultPost(uri, json, log);
		if(log && !Util._IN_PRODUCTION){msg = "----[RestServiceCall result: "+result+"]----";LOG.info(String.format(fmt, _f,msg));}
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[messageProcess completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	}

	@Override
	public <T extends HasProcess<T>> void messageProcess(String processInstanceId, Enum<?> messageType, Enum<?> messageName, T object, boolean log) {
		_messageProcess(serverUrl, processInstanceId, messageType, messageName, object, log);
	}
	public <T extends HasProcess<T>> void _messageProcess(String serverUrl, String processInstanceId, Enum<?> messageType, Enum<?> messageName, T object, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[messageProcess]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = object.toJson();
		String prefix = CampRest.ProcessControl.Prefix;		
		String serviceUri = CampRest.ProcessControlDaoService.callRequest(prefix,CampRest.ProcessControlDaoService.Request.NOTIFY_PROCESS);
		String uri = serverUrl+domainUri+String.format(serviceUri,processInstanceId, messageType.name(),messageName.name());
		String result = RestInterface.resultPost(uri, json, log);
		if(log && !Util._IN_PRODUCTION){msg = "----[RestServiceCall result: "+result+"]----";LOG.info(String.format(fmt, _f,msg));}
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[messageProcess completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	}

	@Override
	public <T extends HasProcess<T>> void messageProcess(String processInstanceId, Enum<?> messageName, String businessKey, String objectStatus, String objectBusinessId, int objectId, boolean log) {
		_messageProcess(serverUrl, processInstanceId, messageName, businessKey, objectStatus, objectBusinessId, objectId, log);
	}
	public <T extends HasProcess<T>> void _messageProcess(String serverUrl, String processInstanceId, Enum<?> messageName, String businessKey, String objectStatus, String objectBusinessId, int objectId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[messageProcess]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.ProcessControl.Prefix;		
		String serviceUri = CampRest.ProcessControlDaoService.callRequest(prefix,CampRest.ProcessControlDaoService.Request.NOTIFY_PROCESS_GET);
		String uri = serverUrl+domainUri+String.format(serviceUri,processInstanceId, messageName.name(),businessKey, objectStatus, objectBusinessId, objectId);
		String result = RestInterface.resultGET(uri, log);
		if(log && !Util._IN_PRODUCTION){msg = "----[RestServiceCall result: "+result+"]----";LOG.info(String.format(fmt, _f,msg));}
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[messageProcess completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	}

	@Override
	public void triggerMessageEvent(String processInstanceId, String businessKey, Enum<?> messageName, Variables variables, boolean log) {
		_triggerMessageEvent(serverUrl, processInstanceId, businessKey, messageName, variables, log);
	}
	public void _triggerMessageEvent(String serverUrl, String processInstanceId, String businessKey, Enum<?> messageName, Variables variables, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[triggerMessageEvent]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = variables.toJson();
		String prefix = CampRest.ProcessControl.Prefix;		
		String serviceUri = CampRest.ProcessControlDaoService.callRequest(prefix,CampRest.ProcessControlDaoService.Request.NOTIFY_PROCESS_EVENT);
		String uri = serverUrl+domainUri+String.format(serviceUri, processInstanceId, businessKey, messageName.name());
		String result = RestInterface.resultPost(uri, json, log);
		if(log && !Util._IN_PRODUCTION){msg = "----[RestServiceCall result: "+result+"]----";LOG.info(String.format(fmt, _f,msg));}
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[triggerMessageEvent completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	}

	@Override
	public void signalProcess(Variables variables, ProcessList processList, boolean log) {
		_signalProcess(serverUrl, variables, processList, log);
	}
	public void _signalProcess(String serverUrl, Variables variables, ProcessList processList, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[signalProcess]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		SignalPacket sp = new SignalPacket(processList,variables);
		
		String json = sp.toJson();
		String prefix = CampRest.ProcessControl.Prefix;		
		String serviceUri = CampRest.ProcessControlDaoService.callRequest(prefix,CampRest.ProcessControlDaoService.Request.SIGNAL_PROCESSES);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		if(log && !Util._IN_PRODUCTION){msg = "----[RestServiceCall result: "+result+"]----";LOG.info(String.format(fmt, _f,msg));}
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[signalProcess completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	}

	@Override
	public void signalProcess(String processInstanceId, String businessKey, Variables variables, boolean log) {
		_signalProcess(serverUrl, processInstanceId, businessKey, variables, log);
	}
	public void _signalProcess(String serverUrl, String processInstanceId, String businessKey, Variables variables, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[signalProcess]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = variables.toJson();
		String prefix = CampRest.ProcessControl.Prefix;		
		String serviceUri = CampRest.ProcessControlDaoService.callRequest(prefix,CampRest.ProcessControlDaoService.Request.SIGNAL_PROCESS);
		String uri = serverUrl+domainUri+String.format(serviceUri,processInstanceId, businessKey);
		String result = RestInterface.resultPost(uri, json, log);
		if(log && !Util._IN_PRODUCTION){msg = "----[RestServiceCall result: "+result+"]----";LOG.info(String.format(fmt, _f,msg));}
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[signalProcess completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	}

	@Override
	public void claimTask(String taskId, String userId, boolean log) {
		_claimTask(serverUrl, taskId, userId, log);
	}
	public void _claimTask(String serverUrl, String taskId, String userId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[claimTask]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.ProcessControl.Prefix;		
		String serviceUri = CampRest.ProcessControlDaoService.callRequest(prefix,CampRest.ProcessControlDaoService.Request.CLAIM_TASK);
		String uri = serverUrl+domainUri+String.format(serviceUri,taskId, userId);
		String result = RestInterface.resultGET(uri, log);
		if(log && !Util._IN_PRODUCTION){msg = "----[RestServiceCall result: "+result+"]----";LOG.info(String.format(fmt, _f,msg));}
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[claimTask completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	}

	@Override
	public void delegateTask(String taskId, String userId, boolean log) {
		_delegateTask(serverUrl, taskId, userId, log);
	}
	public void _delegateTask(String serverUrl, String taskId, String userId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delegateTask]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.ProcessControl.Prefix;		
		String serviceUri = CampRest.ProcessControlDaoService.callRequest(prefix,CampRest.ProcessControlDaoService.Request.DELEGATE_TASK);
		String uri = serverUrl+domainUri+String.format(serviceUri,taskId, userId);
		String result = RestInterface.resultGET(uri, log);
		if(log && !Util._IN_PRODUCTION){msg = "----[RestServiceCall result: "+result+"]----";LOG.info(String.format(fmt, _f,msg));}
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delegateTask completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	}

	@Override
	public <T extends HasProcess<?>> void completeTask(String processInstanceId, String principal, T object, boolean log) {
		_completeTask(serverUrl, processInstanceId, principal, object, log);
	}
	public <T extends HasProcess<?>> void _completeTask(String serverUrl,String processInstanceId, String principal, T object, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[completeTask]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = object.toJson();
		String prefix = CampRest.ProcessControl.Prefix;		
		String serviceUri = CampRest.ProcessControlDaoService.callRequest(prefix,CampRest.ProcessControlDaoService.Request.COMPLETE_CURRENT_TASK);
		String uri = serverUrl+domainUri+String.format(serviceUri, processInstanceId, principal);
		String result = RestInterface.resultPost(uri, json, log);
		if(log && !Util._IN_PRODUCTION){msg = "----[RestServiceCall result: "+result+"]----";LOG.info(String.format(fmt, _f,msg));}
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[completeTask completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	}

	public void completeTask(String taskId, Variables variables, boolean log) {
		_completeTask(serverUrl, taskId, variables, log);
	}
	public void _completeTask(String serverUrl, String taskId, Variables variables, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[completeTask]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = variables.toJson();
		String prefix = CampRest.ProcessControl.Prefix;		
		String serviceUri = CampRest.ProcessControlDaoService.callRequest(prefix,CampRest.ProcessControlDaoService.Request.COMPLETE_TASK);
		String uri = serverUrl+domainUri+String.format(serviceUri,taskId);
		String result = RestInterface.resultPost(uri, json, log);
		if(log && !Util._IN_PRODUCTION){msg = "----[RestServiceCall result: "+result+"]----";LOG.info(String.format(fmt, _f,msg));}
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[completeTask completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	}

	@Override
	public Task getTask(String taskId, boolean log) {
		return _getTask(serverUrl, taskId, log);
	}
	public Task _getTask(String serverUrl, String taskId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[getTask]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.ProcessControl.Prefix;		
		String serviceUri = CampRest.ProcessControlDaoService.callRequest(prefix,CampRest.ProcessControlDaoService.Request.GET_TASK);
		String uri = serverUrl+domainUri+String.format(serviceUri,taskId);
		String result = RestInterface.resultGET(uri, log);
		Task t = Task._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[getTask completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return t;
	}

	@Override
	public TaskList getTasks(String processInstanceId, String businessKey, boolean log) {
		return _getTasks(serverUrl, processInstanceId, businessKey, log);
	}
	public TaskList _getTasks(String serverUrl, String processInstanceId, String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[getTasks]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.ProcessControl.Prefix;		
		String serviceUri = CampRest.ProcessControlDaoService.callRequest(prefix,CampRest.ProcessControlDaoService.Request.GET_TASKS_KEY);
		String uri = serverUrl+domainUri+String.format(serviceUri,processInstanceId, businessKey);
		String result = RestInterface.resultGET(uri, log);
		TaskList tl = TaskList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[getTasks completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return tl;
	}

	@Override
	public TaskList getTasks(String processInstanceId, boolean log) {
		return _getTasks(serverUrl, processInstanceId, log);
	}
	public TaskList _getTasks(String serverUrl, String processInstanceId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[getTasks]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.ProcessControl.Prefix;		
		String serviceUri = CampRest.ProcessControlDaoService.callRequest(prefix,CampRest.ProcessControlDaoService.Request.GET_TASKS);
		String uri = serverUrl+domainUri+String.format(serviceUri,processInstanceId);
		String result = RestInterface.resultGET(uri, log);
		TaskList tl = TaskList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[getTasks completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return tl;
	}

}
