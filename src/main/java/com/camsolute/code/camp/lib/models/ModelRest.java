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

import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.dao.rest.RestInterface;
import com.camsolute.code.camp.lib.data.CampRest;
import com.camsolute.code.camp.lib.utilities.Util;

public class ModelRest implements ModelRestInterface {
	private static final Logger LOG = LogManager.getLogger(ModelRest.class);
	private static String fmt = "[%15s] [%s]";
	public static String serverUrl = CampRest.PRODUCT_API_SERVER_URL;
	public static String domainUri = CampRest.PRODUCT_API_DOMAIN;

	private static ModelRest instance = null;
	
	private ModelRest(){
	}
	
	public static ModelRest instance(){
		if(instance == null) {
			instance = new ModelRest();
		}
		return instance;
	}
	
	
	@Override
	public Model loadById(int id, boolean log) {
		return loadById(serverUrl, id, log);
	}
	public Model loadById(String serverUrl, int id, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadById]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,id);
		String result = RestInterface.resultGET(uri, log);
		Model m = ModelInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadById completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return m;
	}

	@Override
	public Model loadByBusinessId(String businessId, boolean log) {
		return loadByBusinessId(serverUrl, businessId, log);
	}
	public Model loadByBusinessId(String serverUrl, String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByBusinessId]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultGET(uri, log);
		Model m = ModelInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByBusinessId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelList loadListByBusinessKey(String businessKey, boolean log) {
		return loadListByBusinessKey(serverUrl, businessKey, log);
	}
	public ModelList loadListByBusinessKey(String serverUrl, String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByBusinessKey]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_KEY);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
		String result = RestInterface.resultGET(uri, log);
		ModelList ml = ModelList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByBusinessKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ml;
	}

	@Override
	public ModelList loadList(boolean log) {
		return loadList(serverUrl, log);
	}
	public ModelList loadList(String serverUrl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadList]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_LIST);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultGET(uri, log);
		ModelList ml = ModelList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ml;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelList loadListByGroup(String group, boolean log) {
		return loadListByGroup(serverUrl, group, log);
	}
	public ModelList loadListByGroup(String serverUrl, String group, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByGroup]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_GROUP);
		String uri = serverUrl+domainUri+String.format(serviceUri,group);
		String result = RestInterface.resultGET(uri, log);
		ModelList ml = ModelList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByGroup completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ml;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelList loadListByGroupVersion(String group, String version, boolean log) {
		return loadListByGroupVersion(serverUrl, group, version, log);
	}
	public ModelList loadListByGroupVersion(String serverUrl, String group, String version, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByGroupVersion]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_GROUP_VERSION);
		String uri = serverUrl+domainUri+String.format(serviceUri,group,version);
		String result = RestInterface.resultGET(uri, log);
		ModelList ml = ModelList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByGroupVersion completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ml;
	}

	@Override
	public Model create(String businessId, Timestamp releaseDate, Timestamp endOfLife, String businessKey, Version version, Group group, boolean log){
		return create(serverUrl, businessId, releaseDate,endOfLife, businessKey, version, group, log);
	}
	public Model create(String serverUrl, String businessId, Timestamp releaseDate, Timestamp endOfLife, String businessKey, Version version, Group group, boolean log){
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[create]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Model.Prefix;
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.CREATE_MODEL);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId,releaseDate.toString(),endOfLife.toString(),businessKey,group.name(),version.value());
		String result = RestInterface.resultGET(uri,log);
		Model m = ModelInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[create completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return m;
	}
	@Override
	public Model save(Model m, boolean log) {
		return save(serverUrl, m, log);
	}
	public Model save(String serverUrl, Model m, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[save]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = m.toJson();
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.SAVE);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		m = ModelInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[save completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return m;
	}

	@Override
	public <E extends ArrayList<Model>> E saveList(E ml, boolean log) {
		return saveList(serverUrl, ml, log);
	}
	@SuppressWarnings("unchecked")
	public <E extends ArrayList<Model>> E saveList(String serverUrl, E ml, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveList]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((ModelList)ml).toJson();
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.SAVE_LIST);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		ml = (E)ModelList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ml;
	}

	@Override
	public Model update(Model m, boolean log) {
		return update(serverUrl, m, log);
	}
	public Model update(String serverUrl, Model m, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[update]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = m.toJson();
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.UPDATE);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		m = ModelInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[update completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return m;
	}

	@Override
	public <E extends ArrayList<Model>> E updateList(E ml, boolean log) {
		return updateList(serverUrl, ml, log);
	}
	@SuppressWarnings("unchecked")
	public <E extends ArrayList<Model>> E updateList(String serverUrl, E ml, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateList]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((ModelList)ml).toJson();
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.UPDATE_LIST);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		ml = (E)ModelList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[updateList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ml;
	}

	@Override
	public <E extends ArrayList<Model>> E loadUpdates(String businessKey, String target, boolean log) {
		return loadUpdates(serverUrl, businessKey, target, log);
	}
	@SuppressWarnings("unchecked")
	public <E extends ArrayList<Model>> E loadUpdates(String serverUrl, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		ModelList ml = ModelList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)ml;
	}

	@Override
	public <E extends ArrayList<Model>> E loadUpdatesByKey(String businessKey, boolean log) {
		return loadUpdatesByKey(serverUrl, businessKey, log);
	}
	@SuppressWarnings("unchecked")
	public <E extends ArrayList<Model>> E loadUpdatesByKey(String serverUrl, String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByKey]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATES_KEY);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
		String result = RestInterface.resultGET(uri, log);
		ModelList ml = ModelList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)ml;
	}

	@Override
	public <E extends ArrayList<Model>> E loadUpdatesByTarget(String target, boolean log) {
		return loadUpdatesByTarget(serverUrl, target, log);
	}
	@SuppressWarnings("unchecked")
	public <E extends ArrayList<Model>> E loadUpdatesByTarget(String serverUrl, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByTarget]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATES_TARGET);
		String uri = serverUrl+domainUri+String.format(serviceUri,target);
		String result = RestInterface.resultGET(uri, log);
		ModelList ml = ModelList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdatesByTarget completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)ml;
	}

	@Override
	public Model loadUpdate(Model m, String businessKey, String target, boolean log) {
		return loadUpdate(serverUrl, m, businessKey, target, log);
	}
	public Model loadUpdate(String serverUrl, Model m, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,m.onlyBusinessId(), businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		m = ModelInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return m;
	}

	@Override
	public int addToUpdates(Model m, String businessKey, String target, boolean log) {
		return addToUpdates(serverUrl, m, businessKey, target, log);
	}
	public int addToUpdates(String serverUrl, Model m, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.ADD_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,m.onlyBusinessId(), businessKey, target, log);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+"  ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[addToUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public <E extends ArrayList<Model>> int addToUpdates(E ml, String businessKey, String target, boolean log) {
		return addToUpdates(serverUrl, ml, businessKey, target, log);
	}
	public <E extends ArrayList<Model>> int addToUpdates(String serverUrl, E ml, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((ModelList)ml).toJson();
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.ADD_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey, target);
		String result = RestInterface.resultPost(uri,json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+"  ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[addToUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteAllFromUpdates(String businessKey, String target, boolean log) {
		return deleteAllFromUpdates(serverUrl, businessKey, target, log);
	}
	public int deleteAllFromUpdates(String serverUrl, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteAllFromUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.DELETE_ALL_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+"  ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteAllFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteFromUpdatesByKey(String businessKey, boolean log) {
		return deleteFromUpdatesByKey(serverUrl, businessKey, log);
	}
	public int deleteFromUpdatesByKey(String serverUrl, String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdatesByKey]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.DELETE_KEY_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+"  ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteFromUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteFromUpdatesByTarget(String target, boolean log) {
		return deleteFromUpdatesByTarget(serverUrl, target, log);
	}
	public int deleteFromUpdatesByTarget(String serverUrl, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdatesByTarget]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.DELETE_TARGET_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri, target);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+"  ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteFromUpdatesByTarget completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteFromUpdates(String instanceId, String businessKey, String target, boolean log) {
		return deleteFromUpdates(serverUrl, instanceId, businessKey, target, log);
	}
	public int deleteFromUpdates(String serverUrl, String instanceId, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.DELETE_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,instanceId, businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+"  ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public <E extends ArrayList<Model>> int deleteFromUpdates(E ml, String businessKey, String target, boolean log) {
		return deleteFromUpdates(serverUrl, ml, businessKey, target, log);
	}
	public <E extends ArrayList<Model>> int deleteFromUpdates(String serverUrl, E ml, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((ModelList)ml).toJson();
		String prefix = CampRest.Model.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri, businessKey, target);
		String result = RestInterface.resultPost(uri,json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+"  ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

}
