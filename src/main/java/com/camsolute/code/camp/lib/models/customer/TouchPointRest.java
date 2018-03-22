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
package com.camsolute.code.camp.lib.models.customer;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.dao.rest.RestInterface;
import com.camsolute.code.camp.lib.data.CampRest;
import com.camsolute.code.camp.lib.utilities.Util;

public class TouchPointRest implements TouchPointRestInterface {
	private static final Logger LOG = LogManager.getLogger(TouchPointRest.class);
	private static String fmt = "[%15s] [%s]";
	
	public static final String serverUrl = CampRest.CUSTOMER_API_SERVER_URL;
	public static final String domainUri = CampRest.CUSTOMER_API_DOMAIN;

	private static TouchPointRest instance = null;
	
	private TouchPointRest(){
	}
	
	public static TouchPointRest instance(){
		if(instance == null) {
			instance = new TouchPointRest();
		}
		return instance;
	}
	
	@Override
	public TouchPoint loadById(int id, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadById]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		TouchPoint o = null;
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,id);
		String result = RestInterface.resultGET(uri, log);
		o = TouchPointInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadById completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public TouchPoint loadByBusinessId(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByBusinessId]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		TouchPoint o = null;
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultGET(uri, log);
		o = TouchPointInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByBusinessId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<TouchPoint>> E loadListByBusinessKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByBusinessKey]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		TouchPointList ol = null;
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_KEY);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
		String result = RestInterface.resultGET(uri, log);
		ol = TouchPointList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByBusinessKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)ol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<TouchPoint>> E loadListByGroup(String group, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByGroup]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		TouchPointList ol = null;
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_GROUP_VERSION);
		String uri = serverUrl+domainUri+String.format(serviceUri,group);
		String result = RestInterface.resultGET(uri, log);
		ol = TouchPointList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByGroup completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)ol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<TouchPoint>> E loadListByGroupVersion(String group, String version, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByGroupVersion]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		TouchPointList ol = null;
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_GROUP_VERSION);
		String uri = serverUrl+domainUri+String.format(serviceUri,group, version);
		String result = RestInterface.resultGET(uri, log);
		ol = TouchPointList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByGroupVersion completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)ol;
	}

	@Override
	public TouchPoint save(TouchPoint instance, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[save]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = instance.toJson();
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.SAVE);
//String uri = serverUrl+domainUri+String.format(serviceUri,save);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		TouchPoint o = TouchPointInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[save completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<TouchPoint>> E saveList(E instanceList, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveList]";
			msg = "====[ TouchPointRest rest call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((TouchPointList)instanceList).toJson();
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.SAVE_LIST);
//String uri = serverUrl+domainUri+String.format(serviceUri,saveList);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		TouchPointList ol = TouchPointList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)ol;
	}

	@Override
	public TouchPoint update(TouchPoint instance, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[update]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = instance.toJson();
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.UPDATE);
//String uri = serverUrl+domainUri+String.format(serviceUri,update);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		TouchPoint o = TouchPointInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[update completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<TouchPoint>> E updateList(E instanceList, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateList]";
			msg = "====[ TouchPointRest rest call: ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((TouchPointList)instanceList).toJson();
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.UPDATE_LIST);
//String uri = serverUrl+domainUri+String.format(serviceUri,saveList);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		TouchPointList ol = TouchPointList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[updateList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)ol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<TouchPoint>> E loadUpdates(String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		TouchPointList ol = null;
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		ol = TouchPointList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)ol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<TouchPoint>> E loadUpdatesByKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByKey]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		TouchPointList ol = null;
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATES_KEY);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
		String result = RestInterface.resultGET(uri, log);
		ol = TouchPointList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)ol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<TouchPoint>> E loadUpdatesByTarget(String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByTarget]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		TouchPointList ol = null;
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATES_TARGET);
		String uri = serverUrl+domainUri+String.format(serviceUri,target);
		String result = RestInterface.resultGET(uri, log);
		ol = TouchPointList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdatesByTarget completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)ol;
	}

	@Override
	public TouchPoint loadUpdate(TouchPoint instance, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = instance.toJson();
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey,target);
		String result = RestInterface.resultPost(uri, json, log);
		TouchPoint o = TouchPointInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public int addToUpdates(TouchPoint instance, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[ TouchPointRest rest call:  ]====";LOG.info(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = instance.toJson();
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.ADD_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey,target);
		String result = RestInterface.resultPost(uri,json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[addToUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public <E extends ArrayList<TouchPoint>> int addToUpdates(E instanceList, String businessKey, String target,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[ TouchPointRest rest call:  ]====";LOG.info(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((TouchPointList)instanceList).toJson();
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.ADD_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,instanceList, businessKey, target, log);
		String result = RestInterface.resultPost(uri,json, log);
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
			msg = "====[ TouchPointRest rest call:  ]====";LOG.info(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.DELETE_ALL_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteAllFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteFromUpdatesByKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdatesByKey]";
			msg = "====[ TouchPointRest rest call:  ]====";LOG.info(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.DELETE_KEY_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteFromUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteFromUpdatesByTarget(String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdatesByTarget]";
			msg = "====[ TouchPointRest rest call:  ]====";LOG.info(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.DELETE_TARGET_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,target);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteFromUpdatesByTarget completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteFromUpdates(String customerBusinessId, String customerBusinessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[ TouchPointRest rest call:  ]====";LOG.info(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.TouchPointDaoService.callRequest(prefix,CampRest.TouchPointDaoService.Request.DELETE_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,customerBusinessId, customerBusinessKey, target);
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
	public <E extends ArrayList<TouchPoint>> int deleteFromUpdates(E instanceList, String businessKey, String target,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[ TouchPointRest rest call:  ]====";LOG.info(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((TouchPointList)instanceList).toJson();
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.DELETE_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,instanceList, businessKey, target);
		String result = RestInterface.resultPost(uri,json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public TouchPoint loadFirst(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadFirst]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		TouchPoint o = null;
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_FIRST);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultGET(uri, log);
		o = TouchPointInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadFirst completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public TouchPoint loadPrevious(TouchPoint p, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadPrevious]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = p.toJson();
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_PREVIOUS);
//String uri = serverUrl+domainUri+String.format(serviceUri,loadPrevious);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		TouchPoint o = TouchPointInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadPrevious completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public TouchPoint loadNext(TouchPoint p, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadNext]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = p.toJson();
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_NEXT);
//String uri = serverUrl+domainUri+String.format(serviceUri,loadNext);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		TouchPoint o = TouchPointInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadNext completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public TouchPointList loadDate(String businessId, String date, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadDate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		TouchPointList ol = null;
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_DATE_BY_BID);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, date);
		String result = RestInterface.resultGET(uri, log);
		ol = TouchPointList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadDate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@Override
	public TouchPointList loadDateRange(String businessId, String startDate, String endDate, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadDateRange]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		TouchPointList ol = null;
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_DATE_RANGE_BY_BID);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, startDate, endDate);
		String result = RestInterface.resultGET(uri, log);
		ol = TouchPointList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadDateRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@Override
	public TouchPointList loadDate(String date, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadDate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		TouchPointList ol = null;
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_DATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,date);
		String result = RestInterface.resultGET(uri, log);
		ol = TouchPointList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadDate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@Override
	public TouchPointList loadDateRange(String startDate, String endDate, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadDateRange]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		TouchPointList ol = null;
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_DATE_RANGE);
		String uri = serverUrl+domainUri+String.format(serviceUri,startDate, endDate);
		String result = RestInterface.resultGET(uri, log);
		ol = TouchPointList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadDateRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@Override
	public int deleteAllResponsibleFromUpdates(String responsibleBusinessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteAllResponsibleFromUpdates]";
			msg = "====[ TouchPointRest rest call:  ]====";LOG.info(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.TouchPointDaoService.callRequest(prefix,CampRest.TouchPointDaoService.Request.DELETE_ALL_RESPONSIBLE_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,responsibleBusinessKey, target);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteAllResponsibleFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteResponsibleFromUpdatesByKey(String responsibleBusinessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteResponsibleFromUpdatesByKey]";
			msg = "====[ TouchPointRest rest call:  ]====";LOG.info(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.TouchPointDaoService.callRequest(prefix,CampRest.TouchPointDaoService.Request.DELETE_RESPONSIBLE_UPDATES_KEY);
		String uri = serverUrl+domainUri+String.format(serviceUri,responsibleBusinessKey);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteResponsibleFromUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteAllFromUpdates(String customerBusinessKey, String responsibleBusinessKey, String target,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteAllFromUpdates]";
			msg = "====[ TouchPointRest rest call:  ]====";LOG.info(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.TouchPointDaoService.callRequest(prefix,CampRest.TouchPointDaoService.Request.DELETE_ALL_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,customerBusinessKey, responsibleBusinessKey, target);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteAllFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteFromUpdatesByKey(String customerBusinessKey, String responsibleBusinessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdatesByKey]";
			msg = "====[ TouchPointRest rest call:  ]====";LOG.info(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.TouchPointDaoService.callRequest(prefix,CampRest.TouchPointDaoService.Request.DELETE_UPDATES_KEY);
		String uri = serverUrl+domainUri+String.format(serviceUri,customerBusinessKey, responsibleBusinessKey);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteFromUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}
	
	
	@Override
	public int deleteFromUpdates(TouchPoint p, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[ TouchPointRest rest call:  ]====";LOG.info(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = p.toJson();
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.TouchPointDaoService.callRequest(prefix,CampRest.TouchPointDaoService.Request.DELETE_UPDATES_POST);
		String uri = serverUrl+domainUri+String.format(serviceUri,target);
		String result = RestInterface.resultPost(uri,json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteResponsibleFromUpdates(TouchPoint p, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteResponsibleFromUpdates]";
			msg = "====[ TouchPointRest rest call:  ]====";LOG.info(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = p.toJson();
		String prefix = CampRest.TouchPoint.Prefix;		
		String serviceUri = CampRest.TouchPointDaoService.callRequest(prefix,CampRest.TouchPointDaoService.Request.DELETE_RESPONSIBLE_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,p, target);
		String result = RestInterface.resultPost(uri,json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteResponsibleFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

}
