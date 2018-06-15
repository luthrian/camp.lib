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
package com.camsolute.code.camp.lib.types;

import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.contract.core.Value;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.AttributeInterface;
import com.camsolute.code.camp.lib.models.ValueInterface;

import java.util.ArrayList;

import org.json.JSONObject;

public interface CampListInterface extends AttributeInterface<ListValue> {

    /**
     * Requests the number of attribute elements currently encapsulated within this object.
     *
     * @return <code>int</code> value of the number of attributes.
     * @author Christopher Campbell
     */
    public int size();

    public void size(int size);

    /**
     * Request the currently selected attribute element encapsulated within this object.
     *
     *@return <code>Attribute</code>
     *
     */
    public Attribute<?> selected();

    public Attribute<?> select(Attribute<?> target);

    public Attribute<?> select(int index);

    public boolean hasSelected();

    public int selectedIndex();

    public void setSelectedIndex(int index);


    public static String _toJson(CampList a){
        String json = "{";
        json += "\"size\":"+a.size()+",";
        json += "\"selectedIndex\":"+a.selectedIndex()+",";
        json += AttributeInterface._toInnerJson(a);
        json += "}";
        return json;
    }
    
    public static CampList _fromJson(String json) {
    	return _fromJSONObject(new JSONObject(json));
    }
    
    public static CampList _fromJSONObject(JSONObject jo) {
    	CampList cl = (CampList) AttributeInterface._fromJSONObject(jo);
    	cl.setSelectedIndex(jo.getInt("selectedIndex"));
    	cl.size(jo.getInt("size")); //TODO: perhaps check against array size just in case...
    	return cl;
    }
}
