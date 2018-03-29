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
package com.camsolute.code.camp.lib.models.product;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.dao.rest.RestInterface;
import com.camsolute.code.camp.lib.data.CampRest;
import com.camsolute.code.camp.lib.models.AttributeMap;
import com.camsolute.code.camp.lib.models.ModelList;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.utilities.Util;

public class ProductRest implements ProductRestInterface {
	private static final Logger LOG = LogManager.getLogger(ProductRest.class);
	private static String fmt = "[%15s] [%s]";
	public static final String serverUrl = CampRest.PRODUCT_API_SERVER_URL;
	public static final String domainUri = CampRest.PRODUCT_API_DOMAIN;
	
	private static ProductRest instance = null;
	
	private ProductRest(){
	}
	
	public static ProductRest instance(){
		if(instance == null) {
			instance = new ProductRest();
		}
		return instance;
	}
	
	@Override
	public Product loadById(int id, boolean log) {
	return loadById(serverUrl,id, log);
	}
	public Product loadById(String serverUrl,int id, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadById]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Product o = null;
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,id);
		String result = RestInterface.resultGET(uri, log);
		o = ProductInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadById completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public Product loadByBusinessId(String businessId, boolean log) {
	return loadByBusinessId(serverUrl,businessId, log);
	}
	public Product loadByBusinessId(String serverUrl,String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByBusinessId]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Product o = null;
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultGET(uri, log);
		o = ProductInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByBusinessId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public <E extends ArrayList<Product>> E loadListByBusinessKey(String businessKey, boolean log) {
	return loadListByBusinessKey(serverUrl,businessKey, log);
	}
	@SuppressWarnings("unchecked")
	public <E extends ArrayList<Product>> E loadListByBusinessKey(String serverUrl,String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByBusinessKey]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProductList o = null;
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_KEY);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
		String result = RestInterface.resultGET(uri, log);
		o = ProductList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByBusinessKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)o;
	}


	@Override
	public ProductList loadList(boolean log) {
	return loadList(serverUrl,log);
	}
	@SuppressWarnings("unchecked")
	public ProductList loadList(String serverUrl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByBusinessKey]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProductList pl = null;
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_LIST);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultGET(uri, log);
		pl = ProductList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByBusinessKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}


	
	@SuppressWarnings("unchecked")
	@Override
	public ProductList loadListByGroup(String group, boolean log) {
	return loadListByGroup(serverUrl,group, log);
	}
	public ProductList loadListByGroup(String serverUrl,String group, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByGroup]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProductList o = null;
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_GROUP);
		String uri = serverUrl+domainUri+String.format(serviceUri,group);
		String result = RestInterface.resultGET(uri, log);
		o = ProductList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByGroup completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductList loadListByGroupVersion(String group, String version, boolean log) {
	return loadListByGroupVersion(serverUrl,group, version, log);
	}
	public ProductList loadListByGroupVersion(String serverUrl,String group, String version, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByGroupVersion]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProductList o = null;
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_BY_GROUP_VERSION);
		String uri = serverUrl+domainUri+String.format(serviceUri,group,version);
		if(log && !Util._IN_PRODUCTION){msg = "----[product service call: URI('"+uri+"')]----";LOG.info(String.format(fmt, _f,msg));}
		String result = RestInterface.resultGET(uri, log);
		o = ProductList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByGroupVersion completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public Product create(String businessId, String businessKey, String date, String endOfLife, String group, String version, boolean log) {
		return create(serverUrl,businessId,businessKey,date,endOfLife,group,version,log);
	}
	public Product create(String serverUrl,String businessId, String businessKey, String date, String endOfLife, String group, String version, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[create]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.CREATE_PRODUCT);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId,businessKey,date,endOfLife,group,version);
		if(log && !Util._IN_PRODUCTION){msg = "----[product service call: generated URI("+uri+")]----";LOG.info(String.format(fmt, _f,msg));}
		String result = RestInterface.resultGET(uri, log);
		Product o = ProductInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[create completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public Product save(Product p, boolean log) {
		return save(serverUrl,p,log);
	}
	public Product save(String serverUrl,Product p, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[save]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ProductInterface._toJson(p);
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.SAVE);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		Product o = ProductInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[save completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public <E extends ArrayList<Product>> E saveList(E pl, boolean log) {
		return saveList(serverUrl,pl, log);
	}
	@SuppressWarnings("unchecked")
	public <E extends ArrayList<Product>> E saveList(String serverUrl,E pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveList]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ProductList._toJson((ProductList)pl);
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.SAVE_LIST);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		ProductList o = ProductList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)o;
	}

	@Override
	public Product update(Product p, boolean log) {
		return update(serverUrl,p,log);
	}
	public Product update(String serverUrl,Product p, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[update]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ProductInterface._toJson(p);
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.UPDATE);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		Product o = ProductInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[update completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public <E extends ArrayList<Product>> E updateList(E pl, boolean log) {
	return updateList(serverUrl,pl,log);
	}
	@SuppressWarnings("unchecked")
	public <E extends ArrayList<Product>> E updateList(String serverUrl,E pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateList]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ProductList._toJson((ProductList)pl);
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.UPDATE_LIST);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);
		ProductList o = ProductList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[updateList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)o;
	}

	@Override
	public Product loadFirst(String businessId, boolean primary, boolean log) {
		return _loadFirst(serverUrl, businessId, primary, log);
	}
	public Product _loadFirst(String serverUrl, String businessId, boolean primary, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadFirst]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_FIRST);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, primary);
		String result = RestInterface.resultGET(uri, log);
		Product o = ProductInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadFirst completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public Product loadNext(Product product, boolean primary, boolean log) {
		return _loadNext(serverUrl, product, primary, log);
	}
	public Product _loadNext(String serverUrl, Product product, boolean primary, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadNext]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_NEXT);
		String uri = serverUrl+domainUri+String.format(serviceUri, primary);
		String result = RestInterface.resultPost(uri,product.toJson(), log);
		Product o = ProductInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadNext completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public Product loadPrevious(Product product, boolean primary, boolean log) {
		return _loadPrevious(serverUrl, product, primary, log);
	}
	public Product _loadPrevious(String serverUrl, Product product, boolean primary, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadPrevious]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_PREVIOUS);
		String uri = serverUrl+domainUri+String.format(serviceUri,primary);
		String result = RestInterface.resultPost(uri,product.toJson(), log);
		Product o = ProductInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadPrevious completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public ProductList loadDate(Timestamp date, boolean primary, boolean log) {
		return _loadDate(serverUrl, date, primary, log);
	}
	public ProductList _loadDate(String serverUrl, Timestamp date, boolean primary, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadDate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_DATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,date.toString(), primary);
		String result = RestInterface.resultGET(uri, log);
		ProductList pl = ProductList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadDate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public ProductList loadDateRange(Timestamp startDate, Timestamp endDate, boolean primary, boolean log) {
		return _loadDateRange(serverUrl, startDate, endDate, primary, log);
	}
	public ProductList _loadDateRange(String serverUrl, Timestamp startDate, Timestamp endDate, boolean primary, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadDateRange]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_DATE_RANGE);
		String uri = serverUrl+domainUri+String.format(serviceUri,startDate.toString(), endDate.toString(), primary);
		String result = RestInterface.resultGET(uri, log);
		ProductList pl  = ProductList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadDateRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public ProductList loadDate(String businessId, Timestamp date, boolean primary, boolean log) {
		return _loadDate(serverUrl, businessId, date, primary, log);
	}
	public ProductList _loadDate(String serverUrl, String businessId, Timestamp date, boolean primary, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadDate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_DATE_BY_BID);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, date.toString(), primary);
		String result = RestInterface.resultGET(uri, log);
		ProductList pl = ProductList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadDate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public ProductList loadDateRange(String businessId, Timestamp startDate, Timestamp endDate, boolean primary, boolean log) {
		return _loadDateRange(serverUrl, businessId, startDate, endDate, primary, log);
	}
	public ProductList _loadDateRange(String serverUrl, String businessId, Timestamp startDate, Timestamp endDate, boolean primary, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadDateRange]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.InstanceDaoService.callRequest(prefix,CampRest.InstanceDaoService.Request.LOAD_DATE_RANGE_BY_BID);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, startDate.toString(), endDate.toString(), primary);
		String result = RestInterface.resultGET(uri, log);
		ProductList pl = ProductList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadDateRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}


	@Override
	public <E extends ArrayList<Product>> E loadUpdates(String businessKey, String target, boolean log) {
	return loadUpdates(serverUrl,businessKey, target, log);
	}
	@SuppressWarnings("unchecked")
	public <E extends ArrayList<Product>> E loadUpdates(String serverUrl,String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProductList o = null;
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		o = ProductList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)o;
	}

	@Override
	public <E extends ArrayList<Product>> E loadUpdatesByKey(String businessKey, boolean log) {
	return loadUpdatesByKey(serverUrl,businessKey, log);
	}
	@SuppressWarnings("unchecked")
	public <E extends ArrayList<Product>> E loadUpdatesByKey(String serverUrl,String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByKey]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProductList o = null;
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATES_KEY);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
		String result = RestInterface.resultGET(uri, log);
		o = ProductList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)o;
	}

	@Override
	public <E extends ArrayList<Product>> E loadUpdatesByTarget(String target, boolean log) {
	return loadUpdatesByTarget(serverUrl,target, log);
	}
	@SuppressWarnings("unchecked")
	public <E extends ArrayList<Product>> E loadUpdatesByTarget(String serverUrl,String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByTarget]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProductList o = null;
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATES_TARGET);
		String uri = serverUrl+domainUri+String.format(serviceUri,target);
		String result = RestInterface.resultGET(uri, log);
		o = ProductList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdatesByTarget completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return (E)o;
	}

	@Override
	public Product loadUpdate(String businessId, int modelId, String businessKey, String target, boolean log) {
	return loadUpdate(serverUrl,businessId, modelId, businessKey, target, log);
	}
	public Product loadUpdate(String serverUrl,String businessId, int modelId,String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Product o = null;
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATE_BY_BUSINESSID);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId+Util.DB._VS+modelId, businessKey, target);
		if(log && !Util._IN_PRODUCTION){msg = "----[product service call: serviceURI("+uri+")]----";LOG.info(String.format(fmt, _f,msg));}
		String result = RestInterface.resultGET(uri, log);
		try{
			o = ProductInterface._fromJson(result);
		} catch(Exception e){
			if(log && !Util._IN_PRODUCTION){msg = "----[ JSON EXCEPTION! transform product to json FAILED.]----";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		}
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public Product loadUpdate(Product p, String businessKey, String target, boolean log) {
	return loadUpdate(serverUrl,p, businessKey, target, log);
	}
	public Product loadUpdate(String serverUrl,Product p, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Product o = null;
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.LOAD_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,p.onlyBusinessId(), businessKey, target);
		String result = RestInterface.resultGET(uri, log);
		o = ProductInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public int addToUpdates(Product p, String businessKey, String target, boolean log) {
	return addToUpdates(serverUrl,p, businessKey, target, log);
	}
	public int addToUpdates(String serverUrl,Product p, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = p.toJson();
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.ADD_UPDATE_POST);
		String uri = serverUrl+domainUri+String.format(serviceUri, businessKey, target);
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
	public int addToUpdates(String businessId, int modelId, String businessKey, String target, boolean log) {
	return addToUpdates(serverUrl,businessId, modelId, businessKey, target, log);
	}
	public int addToUpdates(String serverUrl,String businessId, int modelId, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.ADD_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId+Util.DB._VS+modelId, businessKey, target);
		
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
	public <E extends ArrayList<Product>> int addToUpdates(E pl, String businessKey, String target, boolean log) {
		return addToUpdates(serverUrl,pl, businessKey, target, log);
	}
	public <E extends ArrayList<Product>> int addToUpdates(String serverUrl,E pl, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((ProductList)pl).toJson();
		String prefix = CampRest.Product.Prefix;		
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
	return deleteAllFromUpdates(serverUrl,businessKey, target, log);
	}
	public int deleteAllFromUpdates(String serverUrl,String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteAllFromUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
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
	public int deleteFromUpdatesByKey(String businessKey, boolean log) {
	return deleteFromUpdatesByKey(serverUrl,businessKey, log);
	}
	public int deleteFromUpdatesByKey(String serverUrl,String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdatesByKey]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.DELETE_KEY_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteFromUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteFromUpdatesByTarget(String target, boolean log) {
	return deleteFromUpdatesByTarget(serverUrl,target, log);
	}
	public int deleteFromUpdatesByTarget(String serverUrl,String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdatesByTarget]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.DELETE_TARGET_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,target);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deleted ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteFromUpdatesByTarget completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int deleteFromUpdates(String businessId, String businessKey, String target, boolean log) {
	return deleteFromUpdates(serverUrl,businessId, businessKey, target, log);
	}
	public int deleteFromUpdates(String serverUrl,String businessId, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.DELETE_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, businessKey, target);
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
	public <E extends ArrayList<Product>> int deleteFromUpdates(E pl, String businessKey, String target, boolean log) {
	return deleteFromUpdates(serverUrl,pl, businessKey, target, log);
	}
	public <E extends ArrayList<Product>> int deleteFromUpdates(String serverUrl,E pl, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((ProductList)pl).toJson();
		String prefix = CampRest.Product.Prefix;		
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

	@Override
	public int addModelReference(String businessId, int modelId, boolean log) {
	return addModelReference(serverUrl,businessId, modelId, log);
	}
	public int addModelReference(String serverUrl,String businessId, int modelId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addModelReference]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.ModelReferenceDaoService.callRequest(prefix,CampRest.ReferenceDaoService.Request.ADD_REFERENCE);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, modelId);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[addModelReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int addModelReferences(String businessId, ModelList ml, boolean log) {
	return addModelReferences(serverUrl,businessId, ml, log);
	}
	public int addModelReferences(String serverUrl,String businessId, ModelList ml, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addModelReferences]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ml.toJson();
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.ModelReferenceDaoService.callRequest(prefix,CampRest.ReferenceDaoService.Request.ADD_REFERENCES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultPost(uri,json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[addModelReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int delModelReference(String businessId, int modelId, boolean log) {
	return delModelReference(serverUrl,businessId, modelId, log);
	}
	public int delModelReference(String serverUrl,String businessId, int modelId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delModelReference]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.ModelReferenceDaoService.callRequest(prefix,CampRest.ReferenceDaoService.Request.DEL_REFERENCE);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, modelId);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delModelReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int delAllModelReferences(String businessId, boolean log) {
	return delAllModelReferences(serverUrl,businessId, log);
	}
	public int delAllModelReferences(String serverUrl,String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delAllModelReferences]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.ModelReferenceDaoService.callRequest(prefix,CampRest.ReferenceDaoService.Request.DEL_ALL_REFERENCES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delAllModelReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int delModelReferences(String businessId, ModelList ml, boolean log) {
	return delModelReferences(serverUrl,businessId, ml, log);
	}
	public int delModelReferences(String serverUrl,String businessId, ModelList ml, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delModelReferences]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ml.toJson();
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.ModelReferenceDaoService.callRequest(prefix,CampRest.ReferenceDaoService.Request.DEL_REFERENCES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultPost(uri,json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delModelReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public ModelList loadModels(String businessId, boolean log) {
	return loadModels(serverUrl,businessId, log);
	}
	public ModelList loadModels(String serverUrl,String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadModels]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ModelList o = null;
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.ModelReferenceDaoService.callRequest(prefix,CampRest.ReferenceDaoService.Request.LOAD);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultGET(uri, log);
		o = ModelList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadModels completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public int addProcessReference(String businessId, String instanceId, String processKey, boolean log) {
	return addProcessReference(serverUrl,businessId, instanceId, processKey, log);
	}
	public int addProcessReference(String serverUrl,String businessId, String instanceId, String processKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addProcessReference]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix,CampRest.ProcessReferenceDaoService.Request.ADD_REFERENCE);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, instanceId, processKey);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[addProcessReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int addProcessReferences(String businessId, ProcessList pl, boolean log) {
	return addProcessReferences(serverUrl,businessId, pl, log);
	}
	public int addProcessReferences(String serverUrl,String businessId, ProcessList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addProcessReferences]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = pl.toJson();
		if(log && !Util._IN_PRODUCTION){msg = "----[ProcessList to json: JSON("+json+")]----";LOG.info(String.format(fmt, _f,msg));}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix,CampRest.ProcessReferenceDaoService.Request.ADD_REFERENCES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultPost(uri, json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[addProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int delProcessReference(String businessId, String instanceId, String processKey, boolean log) {
	return delProcessReference(serverUrl,businessId, instanceId, processKey, log);
	}
	public int delProcessReference(String serverUrl,String businessId, String instanceId, String processKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delProcessReference]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix,CampRest.ProcessReferenceDaoService.Request.DEL_REFERENCE);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, instanceId, processKey);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delProcessReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public int delAllProcessReferences(String businessId, boolean log) {
	return delAllProcessReferences(serverUrl,businessId, log);
	}
	public int delAllProcessReferences(String serverUrl,String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delProcessReferences]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix,CampRest.ProcessReferenceDaoService.Request.DEL_ALL_REFERENCES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	public int delProcessReferences(String businessId,ProcessList pl, boolean log) {
	return delProcessReferences(serverUrl,businessId,pl, log);
	}
	public int delProcessReferences(String serverUrl,String businessId,ProcessList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delProcessReferences]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = pl.toJson();
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix,CampRest.ProcessReferenceDaoService.Request.DEL_REFERENCES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultPost(uri,json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return retVal;
	}

	@Override
	public ProcessList loadProcessReferences(String businessId, boolean log) {
	return loadProcessReferences(serverUrl,businessId, log);
	}
	@SuppressWarnings("unchecked")
	public ProcessList loadProcessReferences(String serverUrl,String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadProcessReferences]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix,CampRest.ProcessReferenceDaoService.Request.LOAD);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultGET(uri, log);
		ProcessList o = ProcessList._fromJson(result);
		int retVal = o.size();
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public AttributeMap saveAttributes(int objectId, AttributeMap am, boolean log) {
	return saveAttributes(serverUrl,objectId, am, log);
	}
	public AttributeMap saveAttributes(String serverUrl,int objectId, AttributeMap am, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveAttributes]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = AttributeMap._toJson(am);
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.AttributeDaoService.callRequest(prefix,CampRest.AttributeDaoService.Request.SAVE_BY_OBJECT_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
		String result = RestInterface.resultPost(uri, json, log);
		AttributeMap o = AttributeMap._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveAttributes completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public AttributeMap updateAttributes(int objectId, AttributeMap am, boolean log) {
	return updateAttributes(serverUrl,objectId, am, log);
	}
	public AttributeMap updateAttributes(String serverUrl,int objectId, AttributeMap am, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateAttributes]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = AttributeMap._toJson(am);
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.AttributeDaoService.callRequest(prefix,CampRest.AttributeDaoService.Request.UPDATE_BY_OBJECT_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
		String result = RestInterface.resultPost(uri, json, log);
		AttributeMap o = AttributeMap._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[updateAttributes completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public AttributeMap loadAttributes(int objectId, boolean log) {
	return loadAttributes(serverUrl,objectId, log);
	}
	public AttributeMap loadAttributes(String serverUrl,int objectId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadAttributes]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Product.Prefix;		
		String serviceUri = CampRest.AttributeDaoService.callRequest(prefix,CampRest.AttributeDaoService.Request.LOAD_BY_OBJECT_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectId);
		String result = RestInterface.resultGET(uri, log);
		AttributeMap o = AttributeMap._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadAttributes completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

}
