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

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.dao.rest.RestInterface;
import com.camsolute.code.camp.lib.data.CampRest;
import com.camsolute.code.camp.lib.data.CampRest.DaoService;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.utilities.Util;

public class OrderRest implements OrderRestInterface {

	private static final Logger LOG = LogManager.getLogger(OrderRest.class);
	private static String fmt = "[%15s] [%s]";
	
	public static final String serverUrl = CampRest.ORDER_API_SERVER_URL;
	public static final String domainUri = CampRest.ORDER_API_DOMAIN;

	private static OrderRest instance = null;
	
	private OrderRest(){
	}
	
	public static OrderRest instance(){
		if(instance == null) {
			instance = new OrderRest();
		}
		return instance;
	}
	
	@Override
	public Order create(String businessId, String businessKey, String date, String byDate, String group, String version, boolean log) {
		return create(serverUrl,businessId,businessKey,date,byDate, group, version,log);
	}
	public Order create(String serverUrl, String businessId, String businessKey, String date, String byDate, String group, String version, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[create]";
			msg = "====[ create an orer object instance via rest service call ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Order o = null;
		
		String serviceUri =  CampRest.DaoService.callRequest(CampRest.Order.Prefix, CampRest.DaoService.Request.CREATE_ORDER); 
		
		String uri = serverUrl+domainUri+String.format(serviceUri, businessId,businessKey,date,byDate,group,version);
		
		String result = RestInterface.resultGET(uri,log);
		
		o = OrderInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[create completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public Order loadById(int id, boolean log) {
		return loadById(serverUrl,id,log);
	}
	public Order loadById(String serverUrl,int id, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadById]";
			msg = "====[ load an order object instance via rest service call ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Order o = null;
		
		String serviceUri = CampRest.DaoService.callRequest(CampRest.Order.Prefix, DaoService.Request.LOAD_BY_ID);
		
		String uri = serverUrl+domainUri+String.format(serviceUri, id);
		
		String result = RestInterface.resultGET(uri,log);
		
		o = OrderInterface._fromJson(result);
	
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadById completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}


	@Override
	public Order loadByBusinessId(String businessId, boolean log) {
		return loadByBusinessId(serverUrl,businessId,log);
	}
	public Order loadByBusinessId(String serverUrl, String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByBusinessId]";
			msg = "====[ load order object instance by business id via rest call ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Order o = null;
		String prefix = CampRest.Order.Prefix;
		
		String serviceUri = CampRest.DaoService.callRequest(prefix, DaoService.Request.LOAD);
		
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		
		String result = RestInterface.resultGET(uri, log);
		
		o = OrderInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByBusinessId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public OrderList loadList(boolean log) {
		return loadList(serverUrl,log);
	}
	public OrderList loadList(String serverUrl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadList]";
			msg = "====[ load all order object instances via rest call  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = new OrderList();
		
		String prefix = CampRest.Order.Prefix;
		
		String serviceUri = CampRest.DaoService.callRequest(prefix, CampRest.DaoService.Request.LOAD_LIST);
		
		String uri = serverUrl+domainUri+serviceUri;
		
		String result = RestInterface.resultGET(uri, log);
		
		ol = OrderList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Order>> E loadListByBusinessKey(String businessKey, boolean log) {
		return (E) loadListByBusinessKey(serverUrl,businessKey,log);
	}
	public OrderList loadListByBusinessKey(String serverUrl, String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByBusinessKey]";
			msg = "====[ load a list of order object instances via rest call  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = new OrderList();
		
		String prefix = CampRest.Order.Prefix;
		
		String serviceUri = CampRest.DaoService.callRequest(prefix, CampRest.DaoService.Request.LOAD_BY_KEY);
		
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
		
		String result = RestInterface.resultGET(uri, log);
		
		ol = OrderList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByBusinessKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrderList loadListByGroup(String group, boolean log) {
		return loadListByGroup(serverUrl,group,log);
	}
	public OrderList loadListByGroup(String serverUrl, String group, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByGroup]";
			msg = "====[ load a list of order position object instances via rest call  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = new OrderList();
		
		String prefix = CampRest.Order.Prefix;
		
		String serviceUri = CampRest.DaoService.callRequest(prefix, CampRest.DaoService.Request.LOAD_BY_GROUP);
		
		String uri = serverUrl+domainUri+String.format(serviceUri,group);
		
		String result = RestInterface.resultGET(uri, log);
		
		ol = OrderList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByGroup completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrderList loadListByGroupVersion(String group, String version, boolean log) {
		return loadListByGroupVersion(serverUrl,group,version,log);
	}
	public OrderList loadListByGroupVersion(String serverUrl, String group, String version, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadListByGroupVersion]";
			msg = "====[ load a list of order object instances via rest call  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = new OrderList();
		
		String prefix = CampRest.Order.Prefix;
		
		String serviceUri = CampRest.DaoService.callRequest(prefix, CampRest.DaoService.Request.LOAD_BY_GROUP_VERSION);
		
		String uri = serverUrl+domainUri+String.format(serviceUri,group,version);
		
		String result = RestInterface.resultGET(uri, log);
		
		ol = OrderList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadListByGroupVersion completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@Override
	public Order save(Order o, boolean log) {
		return save(serverUrl,o,log);
	}
	public Order save(String serverUrl, Order o, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[save]";
			msg = "====[ save an order object instance via rest call ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		
		String json = o.toJson();
		
		String prefix = CampRest.Order.Prefix;
				
		String serviceUri = CampRest.DaoService.callRequest(prefix, CampRest.DaoService.Request.SAVE);
						
		String uri = serverUrl+domainUri+serviceUri;
				
		String result = RestInterface.resultPost(uri, json, log);
		
		o = OrderInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[save completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Order>> E saveList(E orderList, boolean log) {
		return (E) saveList(serverUrl,(OrderList)orderList,log);
	}
	public OrderList saveList(String serverUrl, OrderList ol, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[saveList]";
			msg = "====[ persist a list of order object instances via rest service call ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ol.toJson();
		
		String prefix = CampRest.Order.Prefix;
		
		String serviceUri = CampRest.DaoService.callRequest(prefix, CampRest.DaoService.Request.SAVE_LIST);
				
		String uri = serverUrl+domainUri+serviceUri;
		
		String result = RestInterface.resultPost(uri, json, log);
		
		ol = OrderList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[saveList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@Override
	public Order update(Order order, boolean log) {
		return update(serverUrl,order,log);
	}

	public Order update(String serverUrl, Order o, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[update]";
			msg = "====[ update persisted order object instance via service call ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = o.toJson();
		
		String prefix = CampRest.Order.Prefix;
		
		String serviceUri = CampRest.DaoService.callRequest(prefix, CampRest.DaoService.Request.UPDATE);
				
		String uri = serverUrl+domainUri+serviceUri;
		
		String result = RestInterface.resultPost(uri, json, log);
		
		o = OrderInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[update completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Order>> E updateList(E orderList, boolean log) {
		return (E)updateList(serverUrl,(OrderList)orderList,log);
	}

	public OrderList updateList(String serverUrl, OrderList ol, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateList]";
			msg = "====[ update a list of order object instances via rest service call ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ol.toJson();
		
		String prefix = CampRest.Order.Prefix;
		
		String serviceUri = CampRest.DaoService.callRequest(prefix, CampRest.DaoService.Request.UPDATE_LIST);
				
		String uri = serverUrl+domainUri+serviceUri;
		
		String result = RestInterface.resultPost(uri, json, log);
		
		ol = OrderList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[updateList completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@Override
	public Order updateAttribute(Order.UpdateAttribute attributeType, String businessId, String newValue, boolean log) {
		return updateAttribute(serverUrl, attributeType, businessId, newValue, log); 
	}
	public Order updateAttribute(String serverUrl, Order.UpdateAttribute attributeType, String businessId, String attributeValue, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[updateAttribute]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Order o = null;
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix,CampRest.DaoService.Request.UPDATE_ATTRIBUTE);
		String uri = serverUrl+domainUri+String.format(serviceUri,attributeType.name(), businessId, attributeValue);
		String result = RestInterface.resultGET(uri, log);
		o = OrderInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[updateAttribute completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}
	
	@SuppressWarnings("unchecked")
	public <E extends ArrayList<Order>> E loadUpdates(String businessKey, String target, boolean log) {
		return (E) loadUpdates(serverUrl,businessKey,target,log);
	}
	public OrderList loadUpdates(String serverUrl, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdates]";
			msg = "====[ load list of order object instances that are registered in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = new OrderList();
		
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix, CampRest.DaoService.Request.LOAD_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey,target);
		String result = RestInterface.resultGET(uri, log);
		
		ol = OrderList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ArrayList<Order>> E loadUpdatesByKey(String businessKey, boolean log) {
		return (E) loadUpdatesByKey(serverUrl,businessKey, log);
	}
	public OrderList loadUpdatesByKey(String serverUrl, String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByKey]";
			msg = "====[ load a list of order object instances registered in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = null;
		
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix, CampRest.DaoService.Request.LOAD_UPDATES_KEY);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey);
		String result = RestInterface.resultGET(uri, log);
		ol = OrderList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdatesByKey completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@Override
	public <E extends ArrayList<Order>> E loadUpdatesByTarget(String target, boolean log) {
		return (E) loadUpdatesByTarget(serverUrl,target, log);
	}
	public OrderList loadUpdatesByTarget(String serverUrl, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdatesByTarget]";
			msg = "====[ load a list of orders registered in the updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderList ol = null;
		
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix, CampRest.DaoService.Request.LOAD_UPDATES_TARGET);
				String uri = serverUrl+domainUri+String.format(serviceUri,target);
		String result = RestInterface.resultGET(uri, log);
		
		ol = OrderList._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdatesByTarget completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return ol;
	}

	@Override
	public Order loadUpdate(Order order, String businessKey, String target, boolean log) {
		return loadUpdate(serverUrl, order, businessKey, target, log);
	}
	public Order loadUpdate(String serverUrl, Order o, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadUpdate]";
			msg = "====[ load an order object instance that is registered in updates table ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		Order ro = null;
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix, CampRest.DaoService.Request.LOAD_UPDATE);
				String uri = serverUrl+domainUri+String.format(serviceUri,o.businessId(),businessKey,target);
		String result = RestInterface.resultGET(uri, log);
		
		ro = OrderInterface._fromJson(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadUpdate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return o;
	}

	@Override
	public int addToUpdates(Order order, String businessKey, String target, boolean log) {
		return addToUpdates(serverUrl, order, businessKey, target, log);
	}
	public int addToUpdates(String serverUrl, Order o, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix, CampRest.DaoService.Request.ADD_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,o.onlyBusinessId(),businessKey,target);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[addToUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	return retVal;
	}
	
	@Override
	public <E extends ArrayList<Order>> int addToUpdates(E ol, String businessKey, String target, boolean log) {
		return addToUpdates(serverUrl, (OrderList) ol, businessKey, target, log);
	}
	public int addToUpdates(String serverUrl, OrderList ol, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addToUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ol.toJson();
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix, CampRest.DaoService.Request.ADD_UPDATES);
		String uri = serverUrl+domainUri+serviceUri;
		String result = RestInterface.resultPost(uri, json, log);

		int retVal = Integer.valueOf(result);
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
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix, DaoService.Request.DELETE_ALL_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey,target);
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
	public int deleteFromUpdatesByKey(String businessKey,boolean log) {
		return deleteFromUpdatesByKey(serverUrl, businessKey,log);
	}
	public int deleteFromUpdatesByKey(String serverUrl, String businessKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdatesByKey]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix, DaoService.Request.DELETE_KEY_UPDATES);
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
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix, DaoService.Request.DELETE_TARGET_UPDATES);
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
		return deleteFromUpdates(serverUrl, businessId, businessKey, target, log);
	}
	public int deleteFromUpdates(String serverUrl, String businessId, String businessKey, String target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix, DaoService.Request.DELETE_UPDATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId,businessKey,target);
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
	public <E extends ArrayList<Order>> int deleteFromUpdates(E ol, String businessKey, String target, boolean log) {
		return deleteFromUpdates(target, (OrderList) ol, businessKey, target, log);
	}
	public int deleteFromUpdates(String serverUrl, OrderList ol, String businessKey, String target,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[deleteFromUpdates]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ol.toJson();
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.DaoService.callRequest(prefix, DaoService.Request.DELETE_UPDATES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessKey,target);
		String result = RestInterface.resultPost(uri, json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" deregistered. ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[deleteFromUpdates completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	return retVal;
}

	@Override
	public int addProcessReference(String businessId, String instanceId, String processKey, boolean log) {
		return addProcessReference(serverUrl, businessId, instanceId, processKey, log);
	}
	public int addProcessReference(String serverUrl, String businessId, String instanceId, String processKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addProcessReference]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix, CampRest.ProcessReferenceDaoService.Request.ADD_REFERENCE);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId,instanceId,processKey);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" associated. ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[addProcessReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	return retVal;
	}
	
	@Override
	public int addProcessReferences(String businessId, ProcessList pl, boolean log) {
		return addProcessReferences(serverUrl, businessId, pl, log);
	}
	public int addProcessReferences(String serverUrl, String businessId, ProcessList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addProcessReferences]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = pl.toJson();
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix, CampRest.ProcessReferenceDaoService.Request.ADD_REFERENCES);
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
		return delProcessReference(serverUrl, businessId, instanceId, processKey, log);
	}
	public int delProcessReference(String serverUrl, String businessId, String instanceId, String processKey, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delProcessReference]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix, CampRest.ProcessReferenceDaoService.Request.DEL_REFERENCE);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId,instanceId,processKey);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" de-associated ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delProcessReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	return retVal;
	}
	
	@Override
	public int delAllProcessReferences(String businessId, boolean log) {
		return delAllProcessReferences(serverUrl, businessId, log);
	}
	public int delAllProcessReferences(String serverUrl, String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delProcessReferences]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix, CampRest.ProcessReferenceDaoService.Request.DEL_ALL_REFERENCES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" de-associated ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	return retVal;
	}

	@Override
	public int delProcessReferences(String businessId,ProcessList pl, boolean log) {
		return delProcessReferences(serverUrl, businessId, pl, log);
	}
	public int delProcessReferences(String serverUrl, String businessId, ProcessList pl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delProcessReferences]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = pl.toJson();
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix, CampRest.ProcessReferenceDaoService.Request.DEL_REFERENCES);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultPost(uri,json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" de-associated ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	return retVal;
	}

	public ProcessList loadProcessReferences(String businessId, boolean log) {
		return loadProcessReferences(serverUrl, businessId, log);
	}
	public ProcessList loadProcessReferences(String serverUrl, String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadProcessReferences]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		ProcessList pl = null;
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.ProcessReferenceDaoService.callRequest(prefix, CampRest.ProcessReferenceDaoService.Request.LOAD);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultGET(uri, log);
		pl = ProcessList._fromJson(result);
		int retVal = pl.size();
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadProcessReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return pl;
	}

	@Override
	public int addOrderPositionReference(String orderBusinessId, String businessId, boolean log) {
		return addOrderPositionReference(serverUrl, orderBusinessId, businessId, log);
	}
	public int addOrderPositionReference(String serverUrl, String orderBusinessId, String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addOrderPositionReference]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.OrderPositionReferenceDaoService.callRequest(prefix, CampRest.ReferenceDaoService.Request.ADD_REFERENCE);
		String uri = serverUrl+domainUri+String.format(serviceUri,orderBusinessId,businessId);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" associated ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[addOrderPositionReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	return retVal;
	}
	
	@Override
	public int addOrderPositionReferences(String orderBusinessId, OrderPositionList opl, boolean log) {
		return addOrderPositionReferences(serverUrl, orderBusinessId, opl, log);
	}
	public int addOrderPositionReferences(String serverUrl, String orderBusinessId, OrderPositionList opl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[addOrderPositionReferences]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = opl.toJson();
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.OrderPositionReferenceDaoService.callRequest(prefix, CampRest.ReferenceDaoService.Request.ADD_REFERENCES);
		String uri = serverUrl+domainUri+String.format(serviceUri,orderBusinessId);
		String result = RestInterface.resultPost(uri, json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" associated ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[addOrderPositionReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	return retVal;
	}
	
	@Override
	public int delOrderPositionReference(String orderBusinessId, String buisinessId, boolean log) {
		return delOrderPositionReference(serverUrl, orderBusinessId, buisinessId, log);
	}
	public int delOrderPositionReference(String serverUrl, String orderBusinessId, String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delOrderPositionReference]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.OrderPositionReferenceDaoService.callRequest(prefix, CampRest.ReferenceDaoService.Request.DEL_REFERENCE);
		String uri = serverUrl+domainUri+String.format(serviceUri,orderBusinessId,businessId);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" de-associated ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delOrderPositionReference completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	return retVal;
	}
	
	@Override
	public int delOrderPositionReferences(String orderBusinessId, OrderPositionList opl, boolean log) {
		return delOrderPositionReferences(serverUrl, orderBusinessId, opl, log);
	}
	public int delOrderPositionReferences(String serverUrl, String orderBusinessId, OrderPositionList opl, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[delOrderPositionReferences]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = opl.toJson();
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.OrderPositionReferenceDaoService.callRequest(prefix, CampRest.ReferenceDaoService.Request.DEL_REFERENCES);
		String uri = serverUrl+domainUri+String.format(serviceUri,orderBusinessId);
		String result = RestInterface.resultPost(uri, json, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" de-associated ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[delOrderPositionReferences completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
	return retVal;
	}
	
	@Override
	public OrderPositionList loadOrderPositions(String orderBusinessId, boolean log) {
		return loadOrderPositions(serverUrl, orderBusinessId, log);
	}
	public OrderPositionList loadOrderPositions(String serverUrl, String orderBusinessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadOrderPositions]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		OrderPositionList opl = null;
		String prefix = CampRest.Order.Prefix;		
		String serviceUri = CampRest.OrderPositionReferenceDaoService.callRequest(prefix, CampRest.ReferenceDaoService.Request.LOAD);
		String uri = serverUrl+domainUri+String.format(serviceUri,orderBusinessId);
		String result = RestInterface.resultGET(uri, log);
		int retVal = Integer.valueOf(result);
		if (log && !Util._IN_PRODUCTION) { msg = "----[ '" + retVal + "' entr"+((retVal>1)?"ies":"y")+" loaded ]----"; LOG.info(String.format(fmt, _f, msg)); }
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadOrderPositions completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return opl;
	}
	
}
