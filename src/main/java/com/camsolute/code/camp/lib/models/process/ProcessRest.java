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
package com.camsolute.code.camp.lib.models.process;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.dao.rest.RestInterface;
import com.camsolute.code.camp.lib.data.CampRest;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessInterface;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.utilities.Util;

public class ProcessRest implements ProcessRestInterface{

	private static final Logger LOG = LogManager.getLogger(ProcessRest.class);
	private static String fmt = "[%15s] [%s]";
	
	public static final String serverUrl = CampRest.PROCESS_API_SERVER_URL;
	public static final String domainUri = CampRest.PROCESS_API_DOMAIN;

	private static ProcessRest instance = null;
	
	private ProcessRest(){
	}
	
	public static ProcessRest instance(){
		if(instance == null) {
			instance = new ProcessRest();
		}
		return instance;
	}
	
	@Override
	public Process<?,?> loadById(int id, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadById]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,id);
		String result = RestInterface.resultGET(uri, log);
		Process<?,?> p = ProcessInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadById completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return p;
	}

	@Override
	public Process<?,?> loadByInstanceId(String instanceId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByInstanceId]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,instanceId);
		String result = RestInterface.resultGET(uri, log);
		Process<?,?> p = ProcessInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByInstanceId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return p;
	}

	@Override
	public ProcessList loadListByBusinessId(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByBusinessId]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProcessList pl = null;
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultGET(uri, log);
		pl = ProcessList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByBusinessId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public ProcessList loadListByKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByKey]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_KEY);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
		String result = RestInterface.resultGET(uri, log);
		ProcessList pl = ProcessList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public Process<?,?> save(Process<?,?> p, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[save]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ProcessInterface._toJson(p);
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.SAVE);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		Process<?,?> rp = ProcessInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[save completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return rp;
	}

	@Override
	public ProcessList saveList(ProcessList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveList]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ProcessList._toJson((ProcessList)pl);
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.SAVE_LIST);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		ProcessList rpl = ProcessList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return rpl;
	}

	@Override
	public int update(Process<?,?> p, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[update]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ProcessInterface._toJson(p);
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.UPDATE);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" updated ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[update completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int updateList(ProcessList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateList]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ProcessList._toJson((ProcessList)pl);
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.UPDATE_LIST);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" updated ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[updateList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public ProcessList loadUpdates(String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		ProcessList rpl = ProcessList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return rpl;
	}

	@Override
	public ProcessList loadUpdatesByKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByKey]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATES_KEY);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
		String result = RestInterface.resultGET(uri, log);
		ProcessList rpl = ProcessList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return rpl;
	}

	@Override
	public ProcessList loadUpdatesByTarget(String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByTarget]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATES_TARGET);
		String uri = serverUrl+domainUri+String.format(serviceUri,target);
		String result = RestInterface.resultGET(uri, log);
		ProcessList rpl = ProcessList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdatesByTarget completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return rpl;
	}

	@Override
	public Process<?,?> loadUpdate(String instanceId, String businessId, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri, instanceId, businessId, businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		Process<?,?> rp = ProcessInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return rp;
	}

	@Override
	public Process<?,?> loadUpdate(Process<?,?> p, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,p.onlyBusinessId(), businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		Process<?,?> rp = ProcessInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return rp;
	}

	@Override
	public int addToUpdates(String instanceId, String businessId, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.ADD_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri, instanceId, businessId, businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[addToUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int addToUpdates(ProcessList pl, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((ProcessList)pl).toJson();
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.ADD_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey, target);
		String result = RestInterface.resultPost(uri, json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[addToUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteAllFromUpdates(String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteAllFromUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.DELETE_ALL_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteAllFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteFromUpdates(String instanceId, String businessId, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.DELETE_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri, instanceId, businessId, businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteFromUpdates(ProcessList pl, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((ProcessList)pl).toJson();
		String prefix = CampRest.Process.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.DELETE_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey, target);
		String result = RestInterface.resultPost(uri, json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}


}
