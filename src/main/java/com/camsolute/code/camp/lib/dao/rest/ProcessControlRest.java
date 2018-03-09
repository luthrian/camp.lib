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
	
	@Override
	public <T extends HasProcess<T, ?>> Process<?, ?> startProcess(String processKey, T object, boolean log) {
long startTime = System.currentTimeMillis();
String _f = null;
String msg = null;
if(log && !Util._IN_PRODUCTION) {
	_f = "[startProcess]";
	msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
}
ProcessStartRequest<T> request = new ProcessStartRequest<T>(object); 
String json = request.toJson();
String prefix = CampRest.ProcessControl.Prefix;		
String serviceUri = CampRest.ProcessControlDaoService.callRequest(prefix,CampRest.ProcessControlDaoService.Request.START_PROCESS);
String uri = serverUrl+domainUri+String.format(serviceUri,processKey);
String result = RestInterface.resultPost(uri, json, log);
Process<?,?> p = ProcessInterface._fromJson(result);

if(log && !Util._IN_PRODUCTION) {
	String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
	msg = "====[startProcess completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
}
return p;
	}

	@Override
	public <T extends HasProcess<T, ?>> void messageProcess(Enum<?> messageType, Enum<?> messageName, T object, boolean log) {
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
	public <T extends HasProcess<T, ?>> void messageProcess(String processInstanceId, Enum<?> messageType, Enum<?> messageName, T object, boolean log) {
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
	public <T extends HasProcess<T, ?>> void messageProcess(String processInstanceId, Enum<?> messageName, String businessKey, String objectStatus, String objectBusinessId, int objectId, boolean log) {
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
	public void completeTask(String taskId, Variables variables, boolean log) {
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
