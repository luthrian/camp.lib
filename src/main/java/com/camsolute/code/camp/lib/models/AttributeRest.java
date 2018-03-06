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
package com.camsolute.code.camp.lib.models;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.spi.ResteasyDeployment;

import com.camsolute.code.camp.lib.dao.rest.RestInterface;
import com.camsolute.code.camp.lib.data.CampRest;
import com.camsolute.code.camp.lib.models.Attribute.AttributeType;
import com.camsolute.code.camp.lib.utilities.Util;

public class AttributeRest implements AttributeRestInterface {

	private static final Logger LOG = LogManager.getLogger(AttributeRest.class);
	private static String fmt = "[%15s] [%s]";
	
	public static final String serverUrl = CampRest.PRODUCT_API_SERVER_URL;
	public static final String domainUri = CampRest.PROCESS_API_DOMAIN;
	
	@Override
	public Attribute<?> loadById(int id, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadById]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.LOAD_BY_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,id);
		String result = RestInterface.resultGET(uri, log);
		Attribute<?> a = AttributeInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadById completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return a;
	}

	@Override
	public Attribute<?> loadByBusinessId(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByBusinessId]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.LOAD_BY_BUSINESS_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultGET(uri, log);
		Attribute<?> a = AttributeInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByBusinessId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return a;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AttributeList loadListByBusinessKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByBusinessKey]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.LOAD_BY_BUSINESS_KEY);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
		String result = RestInterface.resultGET(uri, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByBusinessKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public Attribute<?> save(Attribute<?> instance, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[save]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = instance.toJson();
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.SAVE);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		Attribute<?> a = AttributeInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[save completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return a;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Attribute<?>>> E saveList(E instanceList, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveList]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((AttributeList)instanceList).toJson();
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.SAVE_LIST);
		String uri = serverUrl+domainUri+String.format(serviceUri,instanceList, log);
		String result = RestInterface.resultPost(uri, json, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)al;
	}

	@Override
	public Attribute<?> update(Attribute<?> instance, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[update]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = instance.toJson();
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.UPDATE);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		Attribute<?> a = AttributeInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[update completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return a;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Attribute<?>>> E updateList(E instanceList, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateList]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((AttributeList)instanceList).toJson();
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.UPDATE_LIST);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri,json, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[updateList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)al;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AttributeList loadUpdates(String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.LOAD_ALL_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AttributeList loadUpdatesByKey(String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByKey]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.LOAD_BUSINESSKEY_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
		String result = RestInterface.resultGET(uri, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AttributeList loadUpdatesByTarget(String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByTarget]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.LOAD_TARGET_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,target);
		String result = RestInterface.resultGET(uri, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdatesByTarget completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public Attribute<?> loadUpdate(Attribute<?> instance, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.LOAD_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,instance.attributeId(), instance.getObjectId(), businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		Attribute<?> a = AttributeInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return a;
	}

	@Override
	public int addToUpdates(Attribute<?> instance, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.ADD_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,instance.attributeId(), instance.getObjectId(), businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" added ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[addToUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public <E extends ArrayList<Attribute<?>>> int addToUpdates(E instanceList, String businessKey, String target,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((AttributeList)instanceList).toJson();
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.ADD_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey, target);
		String result = RestInterface.resultPost(uri, json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" added ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
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
		
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.DELETE_ALL_UPDATES);
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
	public int deleteFromUpdates(String instanceId, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String[] ids = instanceId.split(Util.DB._VS);
		if(ids.length < 2) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ERROR! instanceId has wrong format! Format must be: <attributeValueId>"+Util.DB._VS+"<objectId> ]----";LOG.info(String.format(fmt, _f,msg));}
			return 0;
		}
		int id = Integer.valueOf(ids[0]);
		int objectId = Integer.valueOf(ids[1]);
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.DELETE_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,id,objectId, businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deletd ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public <E extends ArrayList<Attribute<?>>> int deleteFromUpdates(E instanceList, String businessKey, String target,
			boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((AttributeList)instanceList).toJson();
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.DELETE_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri, businessKey, target);
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
	public int delete(int id, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delete]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.DELETE_BY_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,id);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delete completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int delete(String attributeName, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delete]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.DELETE_BY_BUSINESS_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,attributeName);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delete completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteList(AttributeList attributeList, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteList]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attributeList.toJson();
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.DELETE_LIST);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteList(int rootId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteList]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.DELETE_BY_PARENT_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,rootId);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public AttributeList loadList(AttributeType type, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadList]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.LOAD_BY_TYPE);
		String uri = serverUrl+domainUri+String.format(serviceUri,type.name());
		String result = RestInterface.resultGET(uri, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public AttributeList loadGroup(int parentId, String groupName, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadGroup]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.LOAD_BY_GROUP);
		String uri = serverUrl+domainUri+String.format(serviceUri,parentId, groupName);
		String result = RestInterface.resultGET(uri, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadGroup completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public AttributeList loadAfterPosition(int id, int position, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadAfterPosition]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.LOAD_AFTER_POSITION);
		String uri = serverUrl+domainUri+String.format(serviceUri,id, position);
		String result = RestInterface.resultGET(uri, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadAfterPosition completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public AttributeList loadBeforePosition(int id, int position, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadBeforePosition]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.LOAD_BEFORE_POSITION);
		String uri = serverUrl+domainUri+String.format(serviceUri,id, position);
		String result = RestInterface.resultGET(uri, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadBeforePosition completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public AttributeList loadRange(int id, int startPosition, int endPosition, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadRange]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeDefinition.Prefix;		
		String serviceUri = CampRest.AttributeDefinitionDaoService.callRequest(prefix,CampRest.AttributeDefinitionDaoService.Request.LOAD_POSITION_RANGE);
		String uri = serverUrl+domainUri+String.format(serviceUri,id, startPosition, endPosition);
		String result = RestInterface.resultGET(uri, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public Attribute<?> save(int objectId, Attribute<?> attribute, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[save]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attribute.toJson();
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.SAVE);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
		String result = RestInterface.resultPost(uri, json, log);
		Attribute<?> a = AttributeInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[save completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return a;
	}

	@Override
	public AttributeList saveList(int objectId, AttributeList attributeList, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveList]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attributeList.toJson();
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.SAVE_LIST);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
		String result = RestInterface.resultPost(uri, json, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public int update(int objectId, Attribute<?> attribute, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[update]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attribute.toJson();
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
		String result = RestInterface.resultPost(uri, json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[update completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int updateList(int objectId, AttributeList attributeList, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateList]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attributeList.toJson();
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.UPDATE_LIST);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
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
	public int delete(int objectId, int attributeId, int valueId, AttributeType type, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delete]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.DELETE_GET);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId, attributeId, valueId, type.name());
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delete completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int delete(int objectId, Attribute<?> attribute, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delete]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attribute.toJson();
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.DELETE);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
		String result = RestInterface.resultPost(uri, json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delete completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteList(int objectId, AttributeList attributeList, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteList]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attributeList.toJson();
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.DELETE_LIST);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
		String result = RestInterface.resultPost(uri, json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;	
}

	@Override
	public Attribute<?> load(int objectId, Attribute<?> attribute, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[load]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attribute.toJson();
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.LOAD);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
		String result = RestInterface.resultPost(uri, json, log);
		Attribute<?> a = AttributeInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[load completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return a;
	}

	@Override
	public AttributeList loadByObjectId(AttributeList attributeList, int objectId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByObjectId]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attributeList.toJson();
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.LOAD_LIST);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
		String result = RestInterface.resultPost(uri, json, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByObjectId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public AttributeList loadGroup(AttributeList attributeList, int objectId, String groupName, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadGroup]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attributeList.toJson();
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.LOAD_BY_GROUP);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId, groupName);
		String result = RestInterface.resultPost(uri, json, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadGroup completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public AttributeList loadAfterPosition(AttributeList attributeList, int objectId, int position, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadAfterPosition]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attributeList.toJson();
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.LOAD_AFTER_POSITION);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId, position);
		String result = RestInterface.resultPost(uri, json, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadAfterPosition completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public AttributeList loadBeforePosition(AttributeList attributeList, int objectId, int position, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadBeforePosition]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attributeList.toJson();
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.LOAD_BEFORE_POSITION);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId, position);
		String result = RestInterface.resultPost(uri, json, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadBeforePosition completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public AttributeList loadRange(AttributeList attributeList, int objectId, int startPosition, int endPosition, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadRange]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attributeList.toJson();
		String prefix = CampRest.AttributeValue.Prefix;		
		String serviceUri = CampRest.AttributeValueDaoService.callRequest(prefix,CampRest.AttributeValueDaoService.Request.LOAD_POSITION_RANGE);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId, startPosition, endPosition);
		String result = RestInterface.resultPost(uri, json, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public AttributeMap loadByObjectId(int objectId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByObjectId]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Attribute.Prefix;		
		String serviceUri = CampRest.AttributeDaoService.callRequest(prefix,CampRest.AttributeDaoService.Request.LOAD_BY_OBJECT_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
		String result = RestInterface.resultGET(uri, log);
		AttributeMap am = AttributeMap._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByObjectId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return am;
	}

	@Override
	public AttributeMap saveByObjectId(int objectId, AttributeMap attributeMap, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveByObjectId]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attributeMap.toJson();
		String prefix = CampRest.Attribute.Prefix;		
		String serviceUri = CampRest.AttributeDaoService.callRequest(prefix,CampRest.AttributeDaoService.Request.SAVE_BY_OBJECT_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
		String result = RestInterface.resultPost(uri, json, log);
		AttributeMap am = AttributeMap._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveByObjectId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return am;
	}

	@Override
	public AttributeList loadAttributesByObjectId(int objectId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadAttributesByObjectId]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Attribute.Prefix;		
		String serviceUri = CampRest.AttributeDaoService.callRequest(prefix,CampRest.AttributeDaoService.Request.LOAD_ALL);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
		String result = RestInterface.resultGET(uri, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadAttributesByObjectId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public AttributeList saveAttributesByObjectId(int objectId, AttributeList attributeList, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveAttributesByObjectId]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attributeList.toJson();
		String prefix = CampRest.Attribute.Prefix;		
		String serviceUri = CampRest.AttributeDaoService.callRequest(prefix,CampRest.AttributeDaoService.Request.SAVE_ALL);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
		String result = RestInterface.resultPost(uri, json, log);
		AttributeList al = AttributeList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveAttributesByObjectId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public int updateAttributesByObjectId(int objectId, AttributeList attributeList, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateAttributesByObjectId]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = attributeList.toJson();
		String prefix = CampRest.Attribute.Prefix;		
		String serviceUri = CampRest.AttributeDaoService.callRequest(prefix,CampRest.AttributeDaoService.Request.UPDATE_ALL);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
		String result = RestInterface.resultPost(uri, json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" updated ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[updateAttributesByObjectId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

}
