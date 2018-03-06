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
package com.camsolute.code.camp.lib.models.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.contract.HasProcess;
import com.camsolute.code.camp.lib.dao.rest.ProcessEngineRestInterface;
import com.camsolute.code.camp.lib.dao.rest.RestInterface;
import com.camsolute.code.camp.lib.data.CampRest;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessInterface;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.utilities.Util;

public class ProcessEngineRest implements ProcessEngineRestInterface {
	private static final Logger LOG = LogManager.getLogger(ProcessEngineRest.class);
	private static String fmt = "[%15s] [%s]";
	
	public static final String serverUrl = CampRest.PROCESS_ENGINE_API_SERVER_URL;
	public static final String domainUri = CampRest.PROCESS_ENGINE_API_DOMAIN;

	private static ProcessEngineRest instance = null;
	
	private ProcessEngineRest(){
	}
	
	public static ProcessEngineRest instance(){
		if(instance == null) {
			instance = new ProcessEngineRest();
		}
		return instance;
	}
	
	@Override
	public UserToken authenticate(String username, String password, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[authenticate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.ProcessEngine.Prefix;		
		String serviceUri = CampRest.ProcessEngineDaoService.callRequest(prefix,CampRest.ProcessEngineDaoService.Request.AUTHENTICATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,username, password);
		String result = RestInterface.resultGET(uri, log);
		UserToken t = UserToken._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[authenticate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return t;
	}

	@Override
	public Process<?, ?> startProcess(String processKey, Variables variables, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[startProcess]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = variables.toJson();
		String prefix = CampRest.ProcessEngine.Prefix;		
		String serviceUri = CampRest.ProcessEngineDaoService.callRequest(prefix,CampRest.ProcessEngineDaoService.Request.START_PROCESS);
		String uri = serverUrl+domainUri+String.format(serviceUri,processKey, variables);
		String result = RestInterface.resultPost(uri, json, log);
		Process<?,?> p = ProcessInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[startProcess completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return p;
	}

	@Override
	public void messageProcess(Message message, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[messageProcess]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = message.toJson();
		String prefix = CampRest.ProcessEngine.Prefix;		
		String serviceUri = CampRest.ProcessEngineDaoService.callRequest(prefix,CampRest.ProcessEngineDaoService.Request.START_PROCESS);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		if(log && !Util._IN_PRODUCTION){msg = "----[RestServiceCall result: "+result+"]----";LOG.info(String.format(fmt, _f,msg));}
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[messageProcess completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	}

	@Override
	public void triggerMessageEvent(String executionId, Enum<?> messageType, Variables variables, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[triggerMessageEvent]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = variables.toJson();
		String prefix = CampRest.ProcessEngine.Prefix;		
		String serviceUri = CampRest.ProcessEngineDaoService.callRequest(prefix,CampRest.ProcessEngineDaoService.Request.TRIGGER_MESSAGE_SUBSCRIPTION);
		String uri = serverUrl+domainUri+String.format(serviceUri,executionId, messageType.name());
		String result = RestInterface.resultPost(uri, json, log);
		if(log && !Util._IN_PRODUCTION){msg = "----[RestServiceCall result: "+result+"]----";LOG.info(String.format(fmt, _f,msg));}
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[triggerMessageEvent completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	}

	@Override
	public ProcessExecutionList getProcessExecutions(String processInstanceId, String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[getProcessExecutions]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.ProcessEngine.Prefix;		
		String serviceUri = CampRest.ProcessEngineDaoService.callRequest(prefix,CampRest.ProcessEngineDaoService.Request.GET_EXECUTIONS);
		String uri = serverUrl+domainUri+String.format(serviceUri,processInstanceId, businessKey);
		String result = RestInterface.resultGET(uri, log);
		ProcessExecutionList pl = ProcessExecutionList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[getProcessExecutions completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public void signalProcess(String execustionId, Variables variables, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[signalProcess]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = variables.toJson();
		String prefix = CampRest.ProcessEngine.Prefix;		
		String serviceUri = CampRest.ProcessEngineDaoService.callRequest(prefix,CampRest.ProcessEngineDaoService.Request.SIGNAL_PROCESS);
		String uri = serverUrl+domainUri+String.format(serviceUri,execustionId);
		String result = RestInterface.resultPost(uri, json, log);
		if(log && !Util._IN_PRODUCTION){msg = "----[RestServiceCall result: "+result+"]----";LOG.info(String.format(fmt, _f,msg));}
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[signalProcess completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	}

	@Override
	public void updateVariables(Process<?,?> p, Variables variables, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateVariables]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.ProcessEngine.Prefix;		
		String serviceUri = CampRest.ProcessEngineDaoService.callRequest(prefix,CampRest.ProcessEngineDaoService.Request.UPDATE_VARIABLE);
		for(ProcessExecution pe:getProcessExecutions(p.instanceId(), p.businessKey(), log)){
			String executionId = pe.id();
			for(String varName:variables.variables().keySet()) {
				String uri = serverUrl+domainUri+String.format(serviceUri,executionId, varName, log);
				String result = RestInterface.resultGET(uri, log);
				if(log && !Util._IN_PRODUCTION){msg = "----[RestServiceCall result: "+result+"]----";LOG.info(String.format(fmt, _f,msg));}
			}
		}
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[updateVariables completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
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
		String prefix = CampRest.ProcessEngine.Prefix;		
		String serviceUri = CampRest.ProcessEngineDaoService.callRequest(prefix,CampRest.ProcessEngineDaoService.Request.CLAIM_TASK);
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
		String prefix = CampRest.ProcessEngine.Prefix;		
		String serviceUri = CampRest.ProcessEngineDaoService.callRequest(prefix,CampRest.ProcessEngineDaoService.Request.DELEGATE_TASK);
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
		String prefix = CampRest.ProcessEngine.Prefix;		
		String serviceUri = CampRest.ProcessEngineDaoService.callRequest(prefix,CampRest.ProcessEngineDaoService.Request.COMPLETE_TASK);
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
		String prefix = CampRest.ProcessEngine.Prefix;		
		String serviceUri = CampRest.ProcessEngineDaoService.callRequest(prefix,CampRest.ProcessEngineDaoService.Request.GET_TASK);
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
		String prefix = CampRest.ProcessEngine.Prefix;		
		String serviceUri = CampRest.ProcessEngineDaoService.callRequest(prefix,CampRest.ProcessEngineDaoService.Request.GET_TASKS);
		String uri = serverUrl+domainUri+String.format(serviceUri,processInstanceId, businessKey);
		String result = RestInterface.resultGET(uri, log);
		TaskList tl = TaskList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[getTasks completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return tl;
	}

}
