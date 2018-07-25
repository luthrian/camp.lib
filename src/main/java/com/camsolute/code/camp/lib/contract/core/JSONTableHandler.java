package com.camsolute.code.camp.lib.contract.core;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.attribute.Attribute;
import com.camsolute.code.camp.lib.contract.attribute.JSONAttributeHandler;
import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.CampException.TableDimensionsException;
import com.camsolute.code.camp.lib.contract.core.CampTable.AttributeTable;
import com.camsolute.code.camp.lib.contract.core.CampTable.ValueTable;
import com.camsolute.code.camp.lib.contract.value.JSONValueHandler;
import com.camsolute.code.camp.lib.contract.value.Value;

public interface JSONTableHandler<T extends Serialization<T>,Q extends CampTable<T,Q>> extends SerializationHandler<Q> {
	public abstract class AbstractJSONTableHandler<T extends Serialization<T>,Q extends CampTable<T,Q>> implements JSONTableHandler<T,Q> {

		public String toJson(Q table) {
    	String json = "{";
    	json += "\"isEmpty\":"+table.tableColumns().isEmpty();
    	json += ",\"numberOfColumns\":"+table.numberOfColumns();
    	json += ",\"numberOfRows\":"+table.numberOfRows();
    	json += ",\"tableRows\":[";
    	boolean start = true;
    	for(ArrayList<T> tableRow: table.tableRows()) {
    		if(!start) {
    			json += ",";
    		} else {
    			start = false;
    		}
    		json += "[";
    		boolean rowStart = true;
    		for(T v: tableRow) {
    			if(!rowStart) {
    				json += ",";
    			} else {
    				rowStart = false;
    			}
    			json += v.toJson();
    		}
    		json += "]";
    	}
    	json += "]}";
    	return json;
		}

		public Q fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}

	}
	
	public class JSONValueTableHandler extends AbstractJSONTableHandler<Value<?,?>,ValueTable> {

		public ValueTable fromJSONObject(JSONObject jo) throws DataMismatchException {
			return _fromJSONObject(jo);
		}
		
		public static ValueTable _fromJSONObject(JSONObject jo) throws DataMismatchException {
    	ValueTable table = new ValueTable();
    	if(jo.getBoolean("isEmpty")) {
    		return table;
    	}
    	Iterator<Object> rows = jo.getJSONArray("tableRows").iterator(); 
    	while(rows.hasNext()) {
    		JSONArray row = (JSONArray)rows.next();
    		Iterator<Object> i = row.iterator();
    		ArrayList<Value<?,?>> tableRow  = new ArrayList<Value<?,?>>();
    		while(i.hasNext()) {
    			tableRow.add(JSONValueHandler._fromJSONObject((JSONObject)i.next()));
    		}
    		try {
					table.addRow(tableRow);
				} catch (TableDimensionsException e) {
					e.printStackTrace();
					throw new DataMismatchException("A Tabledimension exception occurred!",e);
				}
    	}
    	return table;			
		}
	}

	public class JSONAttributeTableHandler extends AbstractJSONTableHandler<Attribute,AttributeTable> {

		public AttributeTable fromJSONObject(JSONObject jo) throws DataMismatchException {
			return _fromJSONObject(jo);
		}
		
		public static AttributeTable _fromJSONObject(JSONObject jo) throws DataMismatchException {
			AttributeTable table = new AttributeTable();
			if(jo.getBoolean("isEmpty")) {
				return table;
			}
			Iterator<Object> rows = jo.getJSONArray("tableRows").iterator();
			while(rows.hasNext()) {
				JSONArray row = (JSONArray)rows.next();
				Iterator<Object> i = row.iterator();
				ArrayList<Attribute> tableRow = new ArrayList<Attribute>();
				while(i.hasNext()) {
					tableRow.add(JSONAttributeHandler._fromJSONObject((JSONObject)i.next()));
				}
				try {
					table.addRow(tableRow);
				} catch (TableDimensionsException e) {
					e.printStackTrace();
					throw new DataMismatchException("A Tabledimension exception occurred!",e);
				}
			}
			return table;
		}
	}
}
