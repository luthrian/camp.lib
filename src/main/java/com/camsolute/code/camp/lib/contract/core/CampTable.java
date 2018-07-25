package com.camsolute.code.camp.lib.contract.core;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;
import org.json.JSONArray;

import com.camsolute.code.camp.lib.contract.attribute.Attribute;
import com.camsolute.code.camp.lib.contract.attribute.JSONAttributeHandler;
import com.camsolute.code.camp.lib.contract.value.Value;
import com.camsolute.code.camp.lib.contract.value.JSONValueHandler;
import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.CampException.TableDimensionsException;
import com.camsolute.code.camp.lib.contract.core.Coordinate;
import com.camsolute.code.camp.lib.contract.core.Serialization;

public interface CampTable<T extends Serialization<T>,Q extends CampTable<T,Q>> extends Serialization<Q> {

    public ArrayList<T> column(int column);
    public ArrayList<ArrayList<T>> tableColumns();
    public void addColumn(ArrayList<T> tableColumn) throws TableDimensionsException;
    public void insertColumn(int column, boolean after, ArrayList<T> tableColumn) throws TableDimensionsException;
    public int numberOfColumns();
    
    public ArrayList<T> row(int row);
    public ArrayList<ArrayList<T>> tableRows();
    public void addRow(ArrayList<T> row) throws TableDimensionsException;
    public void insertRow(int row, boolean after, ArrayList<T> tableRow) throws TableDimensionsException;
    public int numberOfRows();
    
    public T cell(Coordinate coord) throws TableDimensionsException;
    public T cell(int row, int column) throws TableDimensionsException;
    public T setCell(Coordinate coord, T element) throws TableDimensionsException;
    public int numberOfElements();
    
    public void setElements(ArrayList<ArrayList<T>> newElements);
    
    
    public abstract class AbstractCampTable<T extends Serialization<T>,Q extends CampTable<T,Q>> implements CampTable<T,Q> {

        protected ArrayList<ArrayList<T>> elements = new ArrayList<ArrayList<T>>();

				public ArrayList<T> column(int column) throws IndexOutOfBoundsException {
					return elements.get(column);
				}
				
				public ArrayList<ArrayList<T>> tableColumns() {
					return elements;
				}

				public void addColumn(ArrayList<T> elements) throws TableDimensionsException {
					if(this.elements.size()>0) {
						if(this.elements.get(0).size() != elements.size()) {
							throw new TableDimensionsException("The table column being added must have exactly "+this.elements.get(0).size()+" row(s)!"); 
						}
					}
					this.elements.add(elements);
				}

				public void insertColumn(int column, boolean after, ArrayList<T> tableColumn) throws TableDimensionsException {
					if(this.elements.size()>0) {
						if(this.elements.get(0).size() != tableColumn.size()) {
							throw new TableDimensionsException("The table column being inserted must have exactly "+this.elements.get(0).size()+" row(s)!"); 
						}
					}
					if(after) {
						column++;
					}
					if(this.elements.size() < (column -1)) {
						throw new TableDimensionsException("Column out of bounds!"); 
					}
					this.elements.add(column-1, tableColumn);
				}
				
				public int numberOfColumns() {
					return elements.size();
				}
				
				public ArrayList<T> row(int row) throws IndexOutOfBoundsException {
					ArrayList<T> tableRow = new ArrayList<T>();
					for(ArrayList<T> column: elements) {
						tableRow.add(column.get(row));
					}
					return tableRow;
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

				public void insertRow(int row, boolean after, ArrayList<T> tableRow) throws TableDimensionsException {
					if(this.elements.size() != 0 && this.elements.size() != tableRow.size()) {
						throw new TableDimensionsException("The table row being inserted must have exactly "+this.elements.size()+" columns(s)!"); 
					}
					if(after) {
						row++;
					}
					if(this.elements.get(0).size() < (row -1)) {
						throw new TableDimensionsException("Row out of bounds!"); 
					}
					int counter = 0;
					for(ArrayList<T> column: elements){
						column.add(row-1,tableRow.get(counter));
						counter++;
					}
				}
				
				public int numberOfRows() {
					return elements.get(0).size();
				}

				public T cell(Coordinate coord) throws TableDimensionsException {
					if(elements.size()<=coord.posY()){
						throw new TableDimensionsException("Column out of bounds!"); 
					}
					if(elements.get(0).size()<=coord.posX()){
						throw new TableDimensionsException("Row out of bounds!"); 
					}
					return elements.get(coord.posY()-1).get(coord.posX()-1);
				}

				public T cell(int row, int column) throws TableDimensionsException {
					if(elements.size()<=column){
						throw new TableDimensionsException("Column out of bounds!"); 
					}
					if(elements.get(0).size()<=row){
						throw new TableDimensionsException("Row out of bounds!"); 
					}
					return elements.get(column-1).get(row-1);
				}

				public T setCell(Coordinate coord, T element) throws TableDimensionsException {
					if(elements.size()<=coord.posY()){
						throw new TableDimensionsException("Column out of bounds!"); 
					}
					if(elements.get(0).size()<=coord.posX()){
						throw new TableDimensionsException("Row out of bounds!"); 
					}
					T prev = elements.get(coord.posY()-1).get(coord.posX()-1);
					elements.get(coord.posY()-1).set(coord.posX()-1,element);
					return elements.get(coord.posY()-1).get(coord.posX()-1);
				}

				public T setCell(int row, int column, T element) throws TableDimensionsException {
					if(elements.size()<=column){
						throw new TableDimensionsException("Column out of bounds!"); 
					}
					if(elements.get(0).size()<=row){
						throw new TableDimensionsException("Row out of bounds!"); 
					}
					T prev = elements.get(row-1).get(column-1);
					elements.get(row-1).set(column-1,element);
					return prev;
				}
				public int numberOfElements() {
					return (elements.size() * elements.get(0).size());
				}

				public void setElements(ArrayList<ArrayList<T>> newElements) {
					elements = newElements;
				}

    }
    
    public class AttributeTable extends AbstractCampTable<Attribute,AttributeTable> {
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
      
      public AttributeTable fromJson(String json) throws DataMismatchException {
      	return fromJSONObject(new JSONObject(json));
      }
      	
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
      		ArrayList<Attribute> tableRow  = new ArrayList<Attribute>();
      		while(i.hasNext()) {
      			tableRow.add(JSONAttributeHandler._fromJSONObject((JSONObject)i.next()));
      		}
      		try {
						table.addRow(tableRow);
					} catch (TableDimensionsException e) {
						e.printStackTrace();
					}
      	}
      	return table;
      }
      
			public void addRow(ArrayList<Attribute> row) throws TableDimensionsException {
				if(this.elements.size() != 0 && this.elements.size() != row.size()) {
					throw new TableDimensionsException("The table row being added must have exactly "+this.elements.size()+" columns(s)!"); 
				}
				int counter = 0;
				for(ArrayList<Attribute> column: elements){
						column.add(row.get(counter));
						counter++;
				}
			}


    }
    
    public class ValueTable extends AbstractCampTable<Value<?,?>,ValueTable> {
      public String toJson() {
      	String json = "{";
      	json += "\"isEmpty\":"+elements.isEmpty();
      	json += ",\"numberOfColumns\":"+numberOfColumns();
      	json += ",\"numberOfRows\":"+numberOfRows();
      	json += ",\"tableRows\":[";
      	boolean start = true;
      	for(ArrayList<Value<?,?>> tableRow: tableRows()) {
      		if(!start) {
      			json += ",";
      		} else {
      			start = false;
      		}
      		json += "[";
      		boolean rowStart = true;
      		for(Value<?,?> v: tableRow) {
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
      
      public ValueTable fromJson(String json) throws DataMismatchException {
      	return fromJSONObject(new JSONObject(json));
      }
      	
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
					}
      	}
      	return table;
      }
      
      public void organize(ArrayList<Value<?,?>> list) {
      	ArrayList<ArrayList<Value<?,?>>> columns = new ArrayList<ArrayList<Value<?,?>>>();
      	int numColumns = 0;
      	int numRows = 0;
      	for(Value<?,?> v: list) {
      		if(v.position().posX() > numColumns) {
      			numColumns = v.position().posX();
      		}
      		if(v.position().posY() > numRows) {
      			numRows = v.position().posY();
      		}
      	}
      	Value<?,?>[][] valueMatrix = new Value<?,?>[numColumns][numRows];
      	for(Value<?,?> v: list) {
      		valueMatrix[v.position().posX()-1][v.position().posY()-1] = v;
      	}
      	for(int col = 0; col < numColumns; col++) {
      		columns.add(new ArrayList<Value<?,?>>());
      		for(int row = 0;row < numRows; row++) {
      			columns.get(col).add(valueMatrix[col][row]);
      		}
      	}
      	elements = columns;
      }
      	
			public void addRow(ArrayList<Value<?,?>> row) throws TableDimensionsException {
				if(this.elements.size() != 0 && this.elements.size() != row.size()) {
					throw new TableDimensionsException("The table row being added must have exactly "+this.elements.size()+" columns(s)!"); 
				}
				int counter = 0;
				for(ArrayList<Value<?,?>> column: elements){
						column.add(row.get(counter));
						counter++;
				}
			}


    }
    
}
