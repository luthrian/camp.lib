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

import com.camsolute.code.camp.lib.contract.HasDate;
import com.camsolute.code.camp.lib.contract.HasModelId;
import com.camsolute.code.camp.lib.contract.HasOrder;
import com.camsolute.code.camp.lib.contract.HasPosition;
import com.camsolute.code.camp.lib.contract.HasProcess;
import com.camsolute.code.camp.lib.contract.HasProduct;
import com.camsolute.code.camp.lib.contract.HasQuantity;
import com.camsolute.code.camp.lib.contract.HasRefBusinessId;
import com.camsolute.code.camp.lib.contract.HasRefId;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampInstanceInterface;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface;
import com.camsolute.code.camp.lib.models.order.OrderPosition.Status;
import com.camsolute.code.camp.lib.models.process.OrderPositionProcess;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.utilities.Util;

public interface OrderPositionInterface extends HasDate, HasRefId, HasRefBusinessId, HasQuantity, HasProcess<OrderPosition>, HasPosition, HasModelId, HasProduct, HasOrder ,IsObjectInstance<OrderPosition>{

	public static final Logger LOG = LogManager.getLogger(OrderPositionInterface.class);
	public static String fmt = "[%15s] [%s]";
	
	public static String _toJson(OrderPosition op){
		String json = "{";
		json += _toInnerJson(op);
		json += "}";
		return json;
	}
	
	public static String _toInnerJson(OrderPosition op) {
		String json = "\"id\":"+op.id()+",";
		json += "\"refId\":"+op.getRefId()+",";
		json += "\"productId\":"+op.productId()+",";
		json += "\"modelId\":"+op.modelId()+",";
		json += "\"businessId\":\""+op.businessId()+"\",";
		json += "\"orderBusinessId\":\""+op.orderBusinessId()+"\",";
		json += "\"businessKey\":\""+op.businessKey()+"\",";
		json += "\"refBusinessKey\":\""+op.refBusinessKey()+"\",";
		json += "\"date\":"+op.date()+",";
		json += "\"position\":"+op.position()+",";
		json += "\"quantity\":"+op.quantity()+",";
		json += "\"states\":"+op.states().toJson()+",";
		json += "\"history\":"+op.history().toJson()+",";
		json += "\"status\":\""+op.status().name()+"\",";
		json += "\"previousStatus\":\""+op.previousStatus().name()+"\",";
		json += "\"group\":\""+op.group()+"\",";
		json += "\"version\":\""+op.version()+"\",";
		json += "\"processes\":"+((op.processes()!=null && op.processes().size() >0)?op.processes().toJson():"[]");

		return json;
	}
	public static OrderPosition _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}
	
	public static OrderPosition _fromJSONObject(JSONObject jo){
		int id = 0;
		if(jo.has("id")) id = jo.getInt("id");
		int productId = jo.getInt("productId");
		int modelId = jo.getInt("modelId");
		String  businessId = jo.getString("businessId");
		String orderBusinessId = jo.getString("orderBusinessId");
		String  businessKey = jo.getString("businessKey");
		Timestamp  date = Util.Time.timestamp(jo.getString("date"));
		String  refBusinessKey = jo.getString("refBusinessKey");
		int position = jo.getInt("position");
		int quantity = jo.getInt("quantity");
		CampStates states = CampStatesInterface._fromJSONObject(jo.getJSONObject("states"));
		CampInstance history = CampInstanceInterface._fromJSONObject(jo.getJSONObject("history"));
		Status status = Status.valueOf(jo.getString("status"));
		Status previousStatus = Status.valueOf(jo.getString("previousStatus"));
		ProcessList processes = new ProcessList();
		try {
			processes = ProcessList._fromJSONArray(jo.getJSONArray("processes"));
		} catch (Exception e) {
			if(!Util._IN_PRODUCTION){String msg = "----[ JSON EXCEPTION! transform FAILED.]----";LOG.info(String.format(fmt,"_fromJSONObject",msg));}
			e.printStackTrace();
		}
		OrderPosition op = new OrderPosition(id, businessId, orderBusinessId, position);
		op.setQuantity(quantity);
		op.setProductId(productId);
		op.setModelId(modelId);
		op.states().update(states);
		op.setStatus(status);
		op.setBusinessKey(businessKey);
		op.setRefBusinessKey(refBusinessKey);
		op.setPreviousStatus(previousStatus);
		op.setHistory(history);
		op.setDate(date);
		op.setVersion(jo.getString("version"));
		op.setGroup(jo.getString("group"));
		op.setProcesses(processes);
		return op;
	}
}
