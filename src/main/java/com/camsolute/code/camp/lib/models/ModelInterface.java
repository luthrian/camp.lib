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
/**
 * ***************************************************************************** Copyright (C) 2017
 * Christopher Campbell (campbellccc@gmail.com)
 *
 * <p>This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * <p>You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 *
 * <p>Contributors: Christopher Campbell (campbellccc@gmail.com) - all code prior and post initial
 * release ****************************************************************************
 */
package com.camsolute.code.camp.lib.models;

import java.sql.Timestamp;
import java.time.Instant;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasId;
import com.camsolute.code.camp.lib.contract.HasModelLifeCycle;
import com.camsolute.code.camp.lib.contract.HasProduct;
import com.camsolute.code.camp.lib.contract.HasStates;
import com.camsolute.code.camp.lib.contract.HasStatus;
import com.camsolute.code.camp.lib.contract.HasStatusChange;
import com.camsolute.code.camp.lib.contract.HasVersion;
import com.camsolute.code.camp.lib.contract.Clonable;
import com.camsolute.code.camp.lib.contract.core.CampStates;
import com.camsolute.code.camp.lib.contract.HasHistory;
import com.camsolute.code.camp.lib.contract.IsDisplayable;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.contract.IsStorable;
import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.models.product.Product;
import com.camsolute.code.camp.lib.utilities.Util;
import static com.camsolute.code.camp.lib.utilities.Util.*;

public interface ModelInterface extends Clonable<Model>, HasModelLifeCycle, HasProduct, IsObjectInstance<Model> {

  public static String _toJson(Model m) {
    String json = "{";
    json += _toInnerJson(m);
    json += "}";
    return json;
  }
  
  public static String _toInnerJson(Model m) {
  	String json = "";
    json += "\"id\":" + m.id() + ",";
    json += "\"productId\":" + m.productId() + ",";
    json += "\"name\":\""+m.name()+"\",";
    json += "\"businessKey\":\""+m.businessKey()+"\",";
    json += "\"version\":\"" + m.version().value() + "\",";
    json += "\"release date\":\"" + m.releaseDate() + "\",";
    json += "\"end of life\":\"" + m.endOfLife() + "\",";
    json += "\"status\":\""+m.status().name()+"\",";
    json += "\"previousStatus\":\""+m.previousStatus().name()+"\",";
    json += "\"history\":" + m.history().toJson()+ ",";
    json += "\"states\":" + m.states().toJson();
  	return json;
  }
  
  public static Model _fromJson(String json) {
  	return _fromJSONObject(new JSONObject(json));
  }
  
  public static Model _fromJSONObject(JSONObject jo) {
		int id = 0;
		if(jo.has("id")) id = jo.getInt("id");
  	Model m = new Model(jo.getString("name"));
  	m.updateId(id);
  	if(jo.has("version")) m.setVersion(new Version(jo.getString("version")));
  	if(jo.has("group")) m.setGroup(new Group(jo.getString("group")));
  	if(jo.has("releaseDate")) m.setReleaseDate(Util.Time.timestamp(jo.getString("releaseDate")));
  	if(jo.has("endOfLife")) m.setEndOfLife(Util.Time.timestamp(jo.getString("endOfLife")));
  	if(jo.has("history")) m.setHistory(CampInstanceInterface._fromJSONObject(jo.getJSONObject("history")));
  	if(jo.has("states")) m.states().update(CampStates._fromJSONObject(jo.getJSONObject("states")));
  	if(jo.has("status"))  m.setStatus(Enum.valueOf(Model.Status.class, jo.getString("status")));
  	if(jo.has("previousStatus")) m.setPreviousStatus(Enum.valueOf(Model.Status.class, jo.getString("previousStatus")));
  	if(jo.has("businessKey")) m.setBusinessKey(jo.getString("businessKey"));
  	if(jo.has("productId")) m.setProductId(jo.getInt("productId"));
  	return m;
  }
}
