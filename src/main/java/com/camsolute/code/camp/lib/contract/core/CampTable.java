package com.camsolute.code.camp.lib.contract.core;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;
import org.json.JSONArray;

import com.camsolute.code.camp.lib.contract.attribute.Attribute;
import com.camsolute.code.camp.lib.contract.attribute.JSONAttributeHandler;
import com.camsolute.code.camp.lib.contract.value.Value;
import com.camsolute.code.camp.lib.contract.value.JSONValueHandler;
import com.camsolute.code.camp.lib.contract.core.Coordinate;
import com.camsolute.code.camp.lib.contract.core.Serialization;

public interface CampTable<T extends Serialization<T>> {

    public ArrayList<T> column(int columnIndex);
    public ArrayList<ArrayList<T>> tableColumns();
    public void addColumn(ArrayList<T> column);
    public void insertColumn(int columnIndex, boolean after, ArrayList<T> column);
    public int numberOfColumns();
    
    public ArrayList<T> row(int rowIndex);
    public ArrayList<ArrayList<T>> tableRows();
    public void addRow(ArrayList<T> row);
    public void insertRow(int rowIndex, boolean after, ArrayList<T> row);
    public int numberOfRows();
    
    public T cell(Coordinate coord);
    public T cell(int rowIndex, int columnIndex);
    public T setCell(Coordinate coord, T element);
    public int numberOfElements();
    
    public void setElements(ArrayList<ArrayList<T>> newElements);
    
    
    public abstract class AbstractCampTable<T extends Serialization<T>> implements CampTable<T>, Serialization<CampTable<T>> {

        protected ArrayList<ArrayList<T>> elements = new ArrayList<ArrayList<T>>();

				public ArrayList<T> column(int columnIndex) throws IndexOutOfBoundsException {
					return elements.get(columnIndex);
				}
				
				public ArrayList<ArrayList<T>> tableColumns() {
					return elements;
				}

				public void addColumn(ArrayList<T> elements) throws IndexOutOfBoundsException {
					this.elements.add(elements);
				}

				public void insertColumn(int columnIndex, boolean after, ArrayList<T> elements) {
					if(after) {
						columnIndex++;
					}
					this.elements.add(columnIndex, elements);
				}
				
				public int numberOfColumns() {
					return elements.size();
				}
				
				public ArrayList<T> row(int rowIndex) {
					ArrayList<T> row = new ArrayList<T>();
					for(ArrayList<T> column: elements) {
						row.add(column.get(rowIndex));
					}
					return row;
				}
				
				public ArrayList<ArrayList<T>> tableRows() {
					int rowCounter = 0;
					int rowCount = numberOfRows();
					ArrayList<ArrayList<T>> tableRows = new ArrayList<ArrayList<T>>();
					
					while(rowCounter < rowCount) {
						tableRows.add(row(rowCounter));
						rowCounter++;
					}
					return tableRows;
				}

				public void addRow(ArrayList<T> row) {
					int counter = 0;
					for(ArrayList<T> column: elements){
							column.add(row.get(counter));
							counter++;
					}
				}

				public void insertRow(int rowIndex, boolean after, ArrayList<T> row) {
					if(after) {
						rowIndex++;
					}
					int counter = 0;
					for(ArrayList<T> column: elements){
						column.add(rowIndex,row.get(counter));
						counter++;
					}
				}
				
				public int numberOfRows() {
					return elements.get(0).size();
				}

				public T cell(Coordinate coord) {
					return elements.get(coord.posY()).get(coord.posX());
				}

				public T cell(int rowIndex, int columnIndex) {
					return elements.get(columnIndex).get(rowIndex);
				}

				public T setCell(Coordinate coord, T element) {
					elements.get(coord.posY()).add(coord.posX(),element);
					return elements.get(coord.posY()).get(coord.posX());
				}

				public T setCell(int rowIndex, int columnIndex, T element) {
					elements.get(rowIndex).add(columnIndex,element);
					return elements.get(columnIndex).get(rowIndex);
				}
				public int numberOfElements() {
					return (elements.size() * elements.get(0).size());
				}

				public void setElements(ArrayList<ArrayList<T>> newElements) {
					elements = newElements;
				}

    }
    
    public class AttributeTable extends AbstractCampTable<Attribute> {
      public String toJson() {
      	String json = "{";
      	json += "\"isEmpty\":"+elements.isEmpty();
      	json += ",\"numberOfColumns\":"+numberOfColumns();
      	json += ",\"numberOfRows\":"+numberOfRows();
      	json += ",\"tableRows\":[";
      	boolean start = true;
      	for(ArrayList<Attribute> tableRow: tableRows()) {
      		if(!start) {
      			json += ",";
      		} else {
      			start = false;
      		}
      		json += "[";
      		boolean rowStart = true;
      		for(Attribute a: tableRow) {
      			if(!rowStart) {
      				json += ",";
      			} else {
      				rowStart = false;
      			}
      			json += a.toJson();
      		}
      		json += "]";
      	}
      	json += "]}";
      	return json;
      }
      
      public CampTable<Attribute> fromJson(String json) throws DataMismatchException {
      	return fromJSONObject(new JSONObject(json));
      }
      	
      public CampTable<Attribute> fromJSONObject(JSONObject jo) throws DataMismatchException {
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
      		ArrayList<Attribute> tableRow  = new ArrayList<Attribute>();
      		while(i.hasNext()) {
      			tableRow.add(JSONAttributeHandler._fromJSONObject((JSONObject)i.next()));
      		}
      		table.addRow(tableRow);
      	}
      	return table;
      }
    }
    
    public class ValueTable extends AbstractCampTable<Value> {
      public String toJson() {
      	String json = "{";
      	json += "\"isEmpty\":"+elements.isEmpty();
      	json += ",\"numberOfColumns\":"+numberOfColumns();
      	json += ",\"numberOfRows\":"+numberOfRows();
      	json += ",\"tableRows\":[";
      	boolean start = true;
      	for(ArrayList<Value> tableRow: tableRows()) {
      		if(!start) {
      			json += ",";
      		} else {
      			start = false;
      		}
      		json += "[";
      		boolean rowStart = true;
      		for(Value v: tableRow) {
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
      
      public CampTable<Value> fromJson(String json) throws DataMismatchException {
      	return fromJSONObject(new JSONObject(json));
      }
      	
      public CampTable<Value> fromJSONObject(JSONObject jo) throws DataMismatchException {
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
      		ArrayList<Value> tableRow  = new ArrayList<Value>();
      		while(i.hasNext()) {
      			tableRow.add(JSONValueHandler._fromJSONObject((JSONObject)i.next()));
      		}
      		table.addRow(tableRow);
      	}
      	return table;
      }
    }
    
}
