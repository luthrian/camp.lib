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

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasStatusChange;
import com.camsolute.code.camp.lib.contract.IsDisplayable;
import com.camsolute.code.camp.lib.contract.IsStorable;
import com.camsolute.code.camp.lib.contract.Serialization;

/**
 * This object encapsulates the i/o status aspects of a business object. The i/o
 * related status aspects are relevant to business object persistence and
 * display.
 *
 * @author Christopher Campbell
 */
public interface CampStatesInterface
		extends IsStorable, IsDisplayable, HasStatusChange, Serialization<CampStatesInterface> {

	public static enum IOAction {
		NONE, LOAD, SAVE, UPDATE, DELETE;
	}

	public void ioAction(IOAction action);

	public void update(CampStates states);
	/**
	 * generates a JSON string representation of the CampStates object instance of
	 * the format:<br>
	 * 
	 * <pre>
	 * {"loaded":[boolean],"saved":[boolean],"isNew":[boolean],"dirty":[boolean],"modified":[boolean]}
	 * </pre>
	 * 
	 * [boolean] is the string representation for 'true' or 'false' <br>
	 * <br>
	 * ...
	 *
	 * @return json string value
	 */
	public String print();

	/**
	 * generates a JSON string representation of the CampStates object instance of
	 * the format:<br>
	 * 
	 * <pre>
	 * {"loaded":[boolean],"saved":[boolean],"isNew":[boolean],"dirty":[boolean],"modified":[boolean]}
	 * </pre>
	 * 
	 * [boolean] is the string representation for 'true' or 'false' <br>
	 * <br>
	 * ...
	 *
	 * @return json string
	 */
	@Override
	public String toJson();

	@Override
	public CampStatesInterface fromJson(String json);

	/**
	 * generates a JSON string representation of the CampStates object instance of
	 * the format:<br>
	 * 
	 * <pre>
	 * {"loaded":[boolean],"saved":[boolean],"isNew":[boolean],"dirty":[boolean],"modified":[boolean]}
	 * </pre>
	 * 
	 * [boolean] is the string representation for 'true' or 'false' <br>
	 * <br>
	 * ...
	 *
	 * @return json string
	 */
	@Override
	public String toString();

	/**
	 * generates a JSON string representation of the CampStates object instance of
	 * the format:<br>
	 * 
	 * <pre>
	 * {"loaded":[boolean],"saved":[boolean],"isNew":[boolean],"dirty":[boolean],"modified":[boolean]}
	 * </pre>
	 * 
	 * [boolean] is the string representation for 'true' or 'false' <br>
	 * <br>
	 * ...
	 * @param cs CampState
	 * @return json string
	 */
	public static String _toJson(CampStates cs) {
		String json = "{";
		json += "\"loaded\":" + cs.isLoaded() + ",";
		json += "\"saved\":" + cs.isSaved() + ",";
		json += "\"updated\":" + cs.isUpdated() + ",";
		json += "\"deleted\":" + cs.isDeleted() + ",";
		json += "\"isNew\":" + cs.isNew() + ",";
		json += "\"dirty\":" + cs.isDirty() + ",";
		json += "\"modified\":" + cs.isModified() ;
		json += "}";
		return json;
	}

	/**
	 * generates a <code>CampStates</code> object instance from a JSON string representation of a CampStates object.
	 * The format of the JSON representation must adhere to the following, whereby the order of each 
	 * name-value pair node is irrelevant:<br>
	 * 
	 * <pre>
	 * {"loaded":[boolean],"saved":[boolean],"isNew":[boolean],"dirty":[boolean],"modified":[boolean]}
	 * </pre>
	 * 
	 * [boolean] is a string representation for 'true' or 'false' see <code>java.lang.Boolean.toString()</code>
	 * <br>
	 * @param json json string
	 * @return CampStates <code>CampStates</code> object instance
	 */
	public static CampStates _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}

	public static CampStates _fromJSONObject(JSONObject jo) {
		CampStates cs = new CampStates();
		if (jo.getBoolean("loaded")) {
			cs.ioAction(IOAction.LOAD);
		}
		if (jo.getBoolean("saved")) {
			cs.ioAction(IOAction.SAVE);
		}
		if (jo.getBoolean("updated")) {
			cs.ioAction(IOAction.UPDATE);
		}
		if (jo.getBoolean("deleted")) {
			cs.ioAction(IOAction.DELETE);
		}
		if (jo.getBoolean("isNew")) {
			cs.ioAction(IOAction.NONE);
		}
		if (jo.getBoolean("dirty")) {
			cs.dirty();
		}
		if (jo.getBoolean("modified")) {
			cs.modify();
		}
		return cs;
	}

}
