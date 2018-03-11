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

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasByDate;
import com.camsolute.code.camp.lib.contract.HasDate;
import com.camsolute.code.camp.lib.contract.HasOrderPositionList;
import com.camsolute.code.camp.lib.contract.HasProcess;
import com.camsolute.code.camp.lib.contract.HasStatus;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampInstanceInterface;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface;
import com.camsolute.code.camp.lib.models.order.Order.Status;
import com.camsolute.code.camp.lib.models.process.OrderProcess;
import com.camsolute.code.camp.lib.models.process.OrderProcessList;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.utilities.Util;

public interface OrderInterface extends HasOrderPositionList , HasDate, HasByDate,HasProcess<Order,OrderProcess>, HasStatus ,IsObjectInstance<Order> {
   public static final Logger LOG = LogManager.getLogger(OrderInterface.class);
   public static String fmt = "[%15s] [%s]";
	 
	public static String _toJson(Order o) {
		String json = "{";
		json += _fromInnerJson(o);
		json += "}";
		return json;
	}

	public static String _fromInnerJson(Order o) {
		String json = "";
  	json += "\"id\":"+o.id()+",";
  	json += "\"orderNumber\":\""+o.onlyBusinessId()+"\",";
  	json += "\"businessKey\":\""+o.businessKey()+"\",";
  	json += "\"date\":\""+o.date().toString()+"\",";
  	json += "\"byDate\":\""+o.byDate().toString()+"\",";
  	json += "\"status\":\""+o.status().name()+"\",";
  	json += "\"previousStatus\":\""+o.previousStatus().name()+"\",";
  	json += "\"group\":\""+o.group().name()+"\",";
  	json += "\"version\":\""+o.version().value()+"\",";
  	json += "\"states\":\""+o.states().toJson()+",";
  	json += "\"history\":\""+o.history().toJson()+",";
  	json += "\" orderPositions\":"+((o. orderPositions() != null && o. orderPositions().size() > 0)?o. orderPositions().toJson():"[]")+",";
  	json += "\"processInstances\":"+((o.processInstances() != null && o.processInstances().size() > 0)?o.processInstances().toJson():"[]");
		return json;
	}
	public static Order _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static Order _fromJSONObject(JSONObject jo) {
		int id = 0;
		if(jo.has("id")) id = jo.getInt("id");
		String orderNumber = jo.getString("orderNumber");
		String businessKey = jo.getString("businessKey");
		Timestamp date = Util.Time.timestamp(jo.getString("date"));
		Timestamp byDate = Util.Time.timestamp(jo.getString("byDate"));
		Order.Status status = Order.Status.valueOf(Status.class ,jo.getString("status"));
		Order.Status previousStatus = Order.Status.valueOf(Status.class,jo.getString("previousStatus"));
		CampStates states = CampStatesInterface._fromJSONObject(jo.getJSONObject("states"));
		CampInstance history = CampInstanceInterface._fromJSONObject(jo.getJSONObject("history"));
		String group = jo.getString("group");
		String version = jo.getString("version");
		OrderProcessList pl = new OrderProcessList();
		try {
			pl = (OrderProcessList) ProcessList._fromJSONArray(jo.getJSONArray("processInstances"));
		} catch (Exception e) {
			if(!Util._IN_PRODUCTION){String msg = "----[JSON Error! Order has no associted Process.]----";LOG.info(String.format(fmt, "_fromJSONObject",msg));}
			e.printStackTrace();
		}
		OrderPositionList opl = new OrderPositionList();
		try {
			opl = OrderPositionList._fromJSONArray(jo.getJSONArray("orderPositions"));
		} catch (Exception e) {
			if(!Util._IN_PRODUCTION){String msg = "----[JSON Error! Order has no associted order positions.]----";LOG.info(String.format(fmt, "_fromJSONObject",msg));}
			e.printStackTrace();
		}
			
		Order o = new Order(orderNumber);
		o.updateId(id);
		o.updateBusinessKey(businessKey);
		o.setDate(date);
		o.setStatus(status);
		o.setPreviousStatus(previousStatus);
		o.states().update(states);
		o.setHistory(history);
		o.setGroup(group);
		o.setVersion(version);
		o.setOrderPositionList(opl);
		o.setProcesses(pl);
		o.setByDate(byDate);
		return o;
	}
}
