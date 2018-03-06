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
package com.camsolute.code.camp.lib.models.order;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.utilities.Util;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.dao.rest.RestInterface;
import com.camsolute.code.camp.lib.data.CampRest;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessList;

public class OrderPositionRest implements OrderPositionRestInterface {
	private static final Logger LOG = LogManager.getLogger(OrderPositionRest.class);
	private static String fmt = "[%15s] [%s]";
	
	public static final String serverUrl = CampRest.ORDER_API_SERVER_URL;
	public static final String domainUri = CampRest.ORDER_API_DOMAIN;
	
	@Override
	public OrderPosition loadById(int id, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadById]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPosition op = null;
		String prefix = CampRest.OrderPosition.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,id);
		String result = RestInterface.resultGET(uri, log);
		op = OrderPositionInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadById completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return op;				
	}

	@Override
	public OrderPosition loadByBusinessId(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[loadByBusinessId]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				OrderPosition op = null;
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD);
				String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
				String result = RestInterface.resultGET(uri, log);
				op = OrderPositionInterface._fromJson(result);
				
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[loadByBusinessId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return op;
	}

	@Override
	public <E extends ArrayList<OrderPosition>> E loadListByBusinessKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[loadListByBusinessKey]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				OrderPositionList op = null;
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_KEY);
				String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
				String result = RestInterface.resultGET(uri, log);
				op = OrderPositionList._fromJson(result);
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[loadListByBusinessKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return (E)op;
	}

	@Override
	public OrderPosition save(OrderPosition op, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[save]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = OrderPositionInterface._toJson(op);
		String prefix = CampRest.OrderPosition.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.SAVE);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		op = OrderPositionInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[save completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return op;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<OrderPosition>> E saveList(E opl, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[saveList]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				String json = OrderPositionList._toJson((OrderPositionList)opl);
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.SAVE_LIST);
				String uri = serverUrl+domainUri+serviceUri;
				String result = RestInterface.resultPost(uri, json, log);
				opl = (E) OrderPositionList._fromJson(result);
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[saveList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return opl;
	}

	@Override
	public OrderPosition update(OrderPosition op, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[update]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				String json = OrderPositionInterface._toJson(op);
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.UPDATE);
				String uri = serverUrl+domainUri+serviceUri;
				String result = RestInterface.resultPost(uri, json, log);
				op = OrderPositionInterface._fromJson(result);
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[update completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return op;
	}

	@Override
	public <E extends ArrayList<OrderPosition>> E updateList(E opl, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[updateList]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				String json = OrderPositionList._toJson((OrderPositionList)opl);
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD);
				String uri = serverUrl+domainUri+serviceUri;
				String result = RestInterface.resultPost(uri, json, log);
				opl = (E) OrderPositionList._fromJson(result);
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[updateList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return opl;
				
	}

	@Override
	public <E extends ArrayList<OrderPosition>> E loadUpdates(String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[loadUpdates]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				E op = null;
//		String json = EInterface._toJson(businessKey, target, log);
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.UPDATE_LIST);
				String uri = serverUrl+domainUri+String.format(serviceUri,businessKey, target);
				String result = RestInterface.resultGET(uri, log);
				op = (E) OrderPositionList._fromJson(result);
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[loadUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return op;
	}

	@Override
	public <E extends ArrayList<OrderPosition>> E loadUpdatesByKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[loadUpdatesByKey]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				E op = null;
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATES_KEY);
				String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
				String result = RestInterface.resultGET(uri, log);
				op = (E) OrderPositionList._fromJson(result);
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[loadUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return op;
	}

	@Override
	public <E extends ArrayList<OrderPosition>> E loadUpdatesByTarget(String target, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[loadUpdatesByTarget]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				E op = null;
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD);
				String uri = serverUrl+domainUri+String.format(serviceUri,target);
				String result = RestInterface.resultGET(uri, log);
				op = (E) OrderPositionList._fromJson(result);
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[loadUpdatesByTarget completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return op;
	}

	@Override
	public OrderPosition loadUpdate(OrderPosition op, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[loadUpdate]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATE);
				String uri = serverUrl+domainUri+String.format(serviceUri, op.businessId(), businessKey, target);
				String result = RestInterface.resultGET(uri, log);
				try {
					op = OrderPositionInterface._fromJson(result);
				} catch (Exception e) {
					if(log && !Util._IN_PRODUCTION){msg = "----[ JSON EXCEPTION! transform FAILED.]----";LOG.info(String.format(fmt,_f,msg));}
					e.printStackTrace();
					op = null;
				}
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[loadUpdate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return op;
	}

	@Override
	public int addToUpdates(OrderPosition op, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[addToUpdates]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.ADD_UPDATE);
				String uri = serverUrl+domainUri+String.format(serviceUri,op.businessId(), businessKey, target, log);
				String result = RestInterface.resultGET(uri, log);
				int retVal = Integer.valueOf(result);
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" associated ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[addToUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return retVal;
				
	}

	@Override
	public <E extends ArrayList<OrderPosition>> int addToUpdates(E opl, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[addToUpdates]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				String json = OrderPositionList._toJson((OrderPositionList)opl);
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.ADD_UPDATES);
				String uri = serverUrl+domainUri+String.format(serviceUri, businessKey, target);
				String result = RestInterface.resultPost(uri, json, log);
				int retVal = Integer.valueOf(result);
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" associated ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
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
				String prefix = CampRest.OrderPosition.Prefix;		
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
	public int deleteFromUpdates(String businessId, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[deleteFromUpdates]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.DELETE_UPDATE);
				String uri = serverUrl+domainUri+String.format(serviceUri,businessId, businessKey, target);
				String result = RestInterface.resultGET(uri, log);
				int retVal = Integer.valueOf(result);
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[deleteFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return retVal;
				
	}

	@Override
	public <E extends ArrayList<OrderPosition>> int deleteFromUpdates(E opl, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[deleteFromUpdates]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				String json = OrderPositionList._toJson((OrderPositionList)opl);
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD);
				String uri = serverUrl+domainUri+String.format(serviceUri,businessKey, target);
				String result = RestInterface.resultPost(uri, json, log);
				int retVal = Integer.valueOf(result);
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[deleteFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return retVal;
	}

	@Override
	public int addProcessReference(String businessId, String instanceId, String processKey, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[addProcessReference]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix,CampRest.ProcessReferenceDaoService.Request.ADD_REFERENCE);
				String uri = serverUrl+domainUri+String.format(serviceUri,businessId, instanceId, processKey);
				String result = RestInterface.resultGET(uri, log);
				int retVal = Integer.valueOf(result);
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" associated ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[addProcessReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return retVal;
				
	}

	@Override
	public int addProcessReferences(String businessId, ProcessList pl, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[addProcessReferences]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				String json = ProcessList._toJson(pl);
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD);
				String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
				String result = RestInterface.resultPost(uri, json, log);
				int retVal = Integer.valueOf(result);
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" associated ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[addProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return retVal;
				
	}

	@Override
	public int delProcessReference(String businessId, String instanceId, String processKey, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[delProcessReference]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix,CampRest.ProcessReferenceDaoService.Request.DEL_REFERENCE);
				String uri = serverUrl+domainUri+String.format(serviceUri,businessId, instanceId, processKey);
				String result = RestInterface.resultGET(uri, log);
				int retVal = Integer.valueOf(result);
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[delProcessReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return retVal;
				
	}

	@Override
	public int delProcessReferences(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[delProcessReferences]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix,CampRest.ProcessReferenceDaoService.Request.DEL_REFERENCES);
				String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
				String result = RestInterface.resultGET(uri, log);
				int retVal = Integer.valueOf(result);
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[delProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return retVal;
				
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Process<?, ?>>> E loadProcessReferences(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadProcessReferences]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		E op = null;
		String prefix = CampRest.OrderPosition.Prefix;		
		String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix,CampRest.ProcessReferenceDaoService.Request.LOAD);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultGET(uri, log);
		op = (E) ProcessList._fromJson(result);
		int retVal = op.size();
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return op;
				
	}

	@Override
	public <T extends IsObjectInstance<T>> int addInstance(T object, boolean useObjectId) throws SQLException {
		return _addInstance(object,useObjectId,!Util._IN_PRODUCTION);
	}
	public <T extends IsObjectInstance<T>> int _addInstance(T object, boolean useObjectId, boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addInstance]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = object.toJson();
		String prefix = CampRest.OrderPosition.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.ADD_INSTANCE);
		String uri = serverUrl+domainUri+String.format(serviceUri,useObjectId);
		String result = RestInterface.resultPost(uri, json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" added ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[addInstance completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
				
	}

	@Override
	public <T extends IsObjectInstance<T>, E extends ArrayList<T>> int addInstances(E objectList, boolean useObjectId) throws SQLException {
		return _addInstances(objectList,useObjectId,!Util._IN_PRODUCTION);
	}
	public <T extends IsObjectInstance<T>, E extends ArrayList<T>> int _addInstances(E objectList, boolean useObjectId, boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[addInstances]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				String json = OrderPositionList._toJson((OrderPositionList)objectList);
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.ADD_INSTANCES);
				String uri = serverUrl+domainUri+String.format(serviceUri, useObjectId);
				String result = RestInterface.resultPost(uri, json, log);
				int retVal = Integer.valueOf(result);
				if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" added ]----"; LOG.info(String.format(fmt, _f, msg)); }
				
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[addInstances completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return retVal;
	}

	@Override
	public <E extends IsObjectInstance<E>> E loadCurrent(String businessId, boolean primary) throws SQLException {
		return _loadCurrent(businessId,primary,!Util._IN_PRODUCTION);
	}
	public <E extends IsObjectInstance<E>> E _loadCurrent(String businessId, boolean primary,boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadCurrent]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		E op = null;
		String prefix = CampRest.OrderPosition.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_CURRENT);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, primary);
		String result = RestInterface.resultGET(uri, log);
		op = (E) OrderPositionInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadCurrent completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return op;
				
	}

	@Override
	public <E extends IsObjectInstance<E>> E loadFirst(String businessId, boolean primary) throws SQLException {
		return _loadFirst(businessId,primary,!Util._IN_PRODUCTION);
	}
	@SuppressWarnings("unchecked")
	public <E extends IsObjectInstance<E>> E _loadFirst(String businessId, boolean primary, boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[loadFirst]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				E op = null;
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_FIRST);
				String uri = serverUrl+domainUri+String.format(serviceUri,businessId, primary);
				String result = RestInterface.resultGET(uri, log);
				op = (E) OrderPositionInterface._fromJson(result);
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[loadFirst completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return op;
				
	}

	@Override
	public <E extends IsObjectInstance<E>> E loadPrevious(E object, boolean primary) throws SQLException {
		return _loadPrevious(object,primary,!Util._IN_PRODUCTION);
	}
	@SuppressWarnings("unchecked")
	public <E extends IsObjectInstance<E>> E _loadPrevious(E op, boolean primary,boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[loadPrevious]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				String json = OrderPositionInterface._toJson((OrderPosition)op);
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_PREVIOUS);
				String uri = serverUrl+domainUri+String.format(serviceUri,primary);
				String result = RestInterface.resultPost(uri, json, log);
				op = (E) OrderPositionInterface._fromJson(result);
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[loadPrevious completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return op;
				
	}

	@Override
	public <E extends IsObjectInstance<E>> E loadNext(E object, boolean primary) throws SQLException {
		return _loadNext(object,primary,!Util._IN_PRODUCTION);
	}
	@SuppressWarnings("unchecked")
	public <E extends IsObjectInstance<E>> E _loadNext(E object, boolean primary,boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[loadNext]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				String json = OrderPositionInterface._toJson((OrderPosition)object);
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_NEXT);
				String uri = serverUrl+domainUri+String.format(serviceUri,primary);
				String result = RestInterface.resultPost(uri, json, log);
				object = (E)OrderPositionInterface._fromJson(result);
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[loadNext completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return object;
				
	}

	@Override
	public <T extends IsObjectInstance<T>,E extends ArrayList<T>> E loadDate(String businessId, String date, boolean primary) throws SQLException {
		return _loadDate(businessId,date,primary,!Util._IN_PRODUCTION)	;
	}
	@SuppressWarnings("unchecked")
	public <T extends IsObjectInstance<T>,E extends ArrayList<T>> E _loadDate(String businessId, String date, boolean primary, boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[loadDate]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				OrderPositionList op = null;
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_DATE_BY_BID);
				String uri = serverUrl+domainUri+String.format(serviceUri,businessId, date, primary);
				String result = RestInterface.resultGET(uri, log);
				op = OrderPositionList._fromJson(result);
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[loadDate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return (E) op;
				
	}

	@Override
	public <T extends IsObjectInstance<T>,E extends ArrayList<T>> E loadDateRange(String businessId, String startDate, String endDate, boolean primary) throws SQLException {
		return _loadDateRange(businessId, startDate,endDate,primary,!Util._IN_PRODUCTION)	;
	}
	@SuppressWarnings("unchecked")
	public <T extends IsObjectInstance<T>,E extends ArrayList<T>> E _loadDateRange(String businessId, String startDate, String endDate, boolean primary, boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[loadDateRange]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				E op = null;
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_DATE_RANGE_BY_BID);
				String uri = serverUrl+domainUri+String.format(serviceUri,businessId, startDate, endDate, primary);
				String result = RestInterface.resultGET(uri, log);
				op = (E) OrderPositionList._fromJson(result);
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[loadDateRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return op;
				
	}

	@Override
	public <T extends IsObjectInstance<T>,E extends ArrayList<T>> E loadDate(String date, boolean primary) throws SQLException {
		return _loadDate(date,primary,!Util._IN_PRODUCTION)	;
	}
	@SuppressWarnings("unchecked")
	public <T extends IsObjectInstance<T>,E extends ArrayList<T>> E _loadDate(String date, boolean primary, boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[loadDate]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				E op = null;
//		String json = EInterface._toJson(date, dao, primary);
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_DATE);
				String uri = serverUrl+domainUri+String.format(serviceUri,date, primary);
				String result = RestInterface.resultGET(uri, log);
				op = (E) OrderPositionList._fromJson(result);
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[loadDate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return op;
				
	}

	@Override
	public <T extends IsObjectInstance<T>,E extends ArrayList<T>> E loadDateRange(String startDate, String endDate, boolean primary) throws SQLException {
		return _loadDateRange(startDate,endDate,primary,!Util._IN_PRODUCTION)	;
	}
	@SuppressWarnings("unchecked")
	public <T extends IsObjectInstance<T>,E extends ArrayList<T>> E _loadDateRange(String startDate, String endDate, boolean primary, boolean log) throws SQLException {
		long startTime = System.currentTimeMillis();
				String _f = null;
				String msg = null;
				if(log && !Util._IN_PRODUCTION) {
					_f = "[loadDateRange]";
					msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
				}
				E op = null;
				String prefix = CampRest.OrderPosition.Prefix;		
				String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_DATE_RANGE);
				String uri = serverUrl+domainUri+String.format(serviceUri,startDate, endDate, primary);
				String result = RestInterface.resultGET(uri, log);
				op = (E) OrderPositionList._fromJson(result);
				if(log && !Util._IN_PRODUCTION) {
					String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
					msg = "====[loadDateRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
				}
				return op;
				
	}

}
