package com.camsolute.code.camp.lib.contract.value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.HasListSelection;
import com.camsolute.code.camp.lib.contract.core.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.Serialization;

public interface ValueList extends List<Value>, Serialization<ValueList>,  HasListSelection<Value> {

	public String toJson();
	
	public ValueList fromJson(String json) throws DataMismatchException ;
	
	public ValueList fromJSONObject(JSONObject jo) throws DataMismatchException;
	
	public static ValueList _fromJSONObject(JSONObject jo) throws DataMismatchException {
		ValueList vl = new ValueListImpl();
		if(jo.getBoolean("isEmpty")) {
			return vl;
		}
		vl.setSelectionIndex(jo.getInt("selected"));
		Iterator<Object> i = jo.getJSONArray("list").iterator();
		while(i.hasNext()) {
			JSONObject j = (JSONObject)i.next();
			vl.add(JSONValueHandler._fromJSONObject(j));
		}
		return vl;
	}

	public class ValueListImpl extends ArrayList<Value> implements ValueList {

		private static final long serialVersionUID = 4958620945707049027L;
		
		private int selected = 0;
		
		public String toJson() {
			String json = "{";
			json += "\"selected\":"+this.selected();
			json += ",\"isEmpty\":"+this.isEmpty();
			json += ",\"list\":[";
			boolean start = true;
			for(Value v: this) {
				if(!start) {
					json += ",";
				} else {
					start = false;
				}
				json += v.toJson();
			}
			json += "]}";
			return json;
		}

		public ValueList fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public ValueList fromJSONObject(JSONObject jo) throws DataMismatchException {
			return _fromJSONObject(jo);
		}
		
 		public Value selected() {
			return get(selected);
		}

		public int selectionIndex() {
			return selected;
		}

		public void setSelectionIndex(int index) {
			selected = index;
		}

		public int select(int itemId) {
			int indexCounter = 0;
			boolean indexChanged = false;
			for(Value v: this) {
				if(v.id() == itemId) {
					selected = indexCounter;
					indexChanged = true;
					break;
				}
				indexCounter++;
			}
			if(indexChanged) {
				return selected;
			}
			return -1;
		}

		public int select(Value item) {
			int indexCounter = 0;
			boolean indexChanged = false;
			for(Value v: this) {
				if(v.id() == item.id()) {
					selected = indexCounter;
					indexChanged = true;
					break;
				}
				indexCounter++;
			}
			if(indexChanged) {
				return selected;
			}
			return -1;
		}
	}
}
