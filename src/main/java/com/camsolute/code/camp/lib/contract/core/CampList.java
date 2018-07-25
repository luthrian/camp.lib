package com.camsolute.code.camp.lib.contract.core;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.value.Value;
import com.camsolute.code.camp.lib.contract.value.Value.ComplexValue;
import com.camsolute.code.camp.lib.contract.value.Value.ListValue;
import com.camsolute.code.camp.lib.contract.value.Value.MapValue;
import com.camsolute.code.camp.lib.contract.value.Value.TableValue;
import com.camsolute.code.camp.lib.contract.attribute.Attribute;
import com.camsolute.code.camp.lib.contract.core.JSONListHandler.JSONValueListHandler;
import com.camsolute.code.camp.lib.contract.core.CampException.AlreadyContainsElementException;
import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.CampList.ValueList;
import com.camsolute.code.camp.lib.contract.core.JSONListHandler.JSONAttributeListHandler;
import com.camsolute.code.camp.lib.contract.core.JSONListHandler.JSONProcessListHandler;
import com.camsolute.code.camp.lib.contract.process.Process;

//TODO: do this for all List objects (Order, ... etc)
public interface CampList<T extends Serialization<T>,Q extends CampList<T,Q>> extends List<T>, HasListSelection<T>, HasJSONListHandler<T,Q>, Serialization<Q> {


	public Q organize(String parentId, Q list);
	
	public int findIndexOf(String itemId);
	
	public T findInstanceOf(String instanceId);

	public ArrayList<T> toArrayList();
	
 	public abstract class AbstractCampList<T extends Serialization<T>,Q extends CampList<T,Q>> extends ArrayList<T> implements CampList<T,Q> {

		private static final long serialVersionUID = -6298243002531202736L;

		private int selected = 0;

		public T selected() {
			return get(selected);
		}

		public int selectionIndex() {
			return selected;
		}

		public void setSelectionIndex(int index) {
			selected = index;
		}

		public int select(String itemId) {
			int indexCounter = 0;
			boolean indexChanged = false;
			for(T v: this) {
				if(id(v).equals(itemId)) {
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

		protected abstract String id(T v);
		
		protected abstract Q me();
		
 		public int select(T item) {
			int indexCounter = 0;
			boolean indexChanged = false;
			for(T v: this) {
				if(id(v).equals(id(item))) {
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

    public int findIndexOf(String itemId) {
      int indexCounter = 0;
      boolean itemNotFound = true;
      for(T p: me()) {
          if(id(p).equals(itemId)) {
              itemNotFound = false;
              break;
          }
          indexCounter++;
      }
      if(itemNotFound){
          return -1;
      }
      return indexCounter;
    }

    public ArrayList<T> toArrayList() {
    	ArrayList<T> l = new ArrayList<T>();
    	for(T element:me()) {
    		l.add(element);
    	}
    	return l;
    }

    public ValueList toList(ValueList valueList) {
    	ValueList vl = new ValueList();
    	for(Value<?,?> v: valueList) {
    		vl.add(v);
        switch(v.type()) {
          case _complex:
            vl.addAll(toList(((ComplexValue)v).dataToValueList()));
            break;
          case _table:
            vl.addAll(toList(((TableValue)v).dataToValueList()));
            break;
          case _map:
            vl.addAll(toList(((MapValue)v).dataToValueList()));
            break;
          case _list:
            vl.addAll(toList(((ListValue)v).dataToValueList()));
            break;
          default:
            break;
        }
      }
    	return vl;
    }  

	}
	
  public class ValueList extends AbstractCampList<Value<?,?>,ValueList> {//ArrayList<Value> implements CampList<Value,ValueList>, Serialization<ValueList> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 394652115283156167L;
		
		private JSONListHandler<Value<?,?>,ValueList> jsonHandler = new JSONValueListHandler();
	
 		public String id(Value<?,?> v) {
 			return v.id();
 		}
 		
 		public JSONListHandler<Value<?,?>,ValueList> jsonHandler() {
			return jsonHandler;
		}

		public void jsonHanlder(JSONListHandler<Value<?,?>,ValueList> jsonListHandler) {
			jsonHandler = jsonListHandler;
		}

		public String toJson() {
			return jsonHandler.toJson(this);
		}

		public ValueList fromJson(String json) throws DataMismatchException {
			return jsonHandler.fromJson(json);
		}

		public ValueList fromJSONObject(JSONObject jo) throws DataMismatchException {
			return jsonHandler.fromJSONObject(jo);
		}
		
		public static ValueList _fromJson(String json) throws DataMismatchException {
			return JSONValueListHandler._fromJSONObject(new JSONObject(json));
		}

		public static ValueList _fromJSONObject(JSONObject jo) throws DataMismatchException {
			return JSONValueListHandler._fromJSONObject(jo);
		}

		public Value<?,?> findInstanceOf(String itemId) {
			return get(findIndexOf(itemId));
		}
		
		public ValueList me() {
			return this;
		}
		
		public ValueList organize(String parentId, ValueList list) {
			ValueList vl = new ValueList();
			ValueList cl = new ValueList();
			//sort into top level and children values;
			for(Value<?,?> v: list) {
				if(v.parentId().equals(parentId)) {
					vl.add(v);
				} 
				switch(v.type()) {
				case _complex:
					for(Value<?,?> cv: organize(v.parentId(),list)) {
						((ComplexValue)v).add(cv.group(),cv);
					}
					break;
				case _map:
					for(Value<?,?> cv: organize(v.parentId(),list)) {
						((MapValue)v).set(cv.group(),cv);
					}
					break;
				case _list:
					for(Value<?,?> cv: organize(v.parentId(),list)) {
						((ListValue)v).add(cv);
					}
					break;
				case _table:
					((TableValue)v).organize(organize(v.parentId(),list));
					break;
				default: 
					break;
				}
			}
			for(Value<?,?> v:vl) {
			}
			return vl;
		}

 	}
	
	public class AttributeList extends AbstractCampList<Attribute,AttributeList> {// extends ArrayList<Attribute> implements CampList<Attribute,AttributeList>, Serialization<AttributeList> {

		private static final long serialVersionUID = 7169498657362780225L;

		private JSONListHandler<Attribute,AttributeList> jsonHandler = new JSONAttributeListHandler();

		public String id(Attribute a) {
			return a.id();
		}
		
		public JSONListHandler<Attribute,AttributeList> jsonHandler() {
			return jsonHandler;
		}

		public void jsonHanlder(JSONListHandler<Attribute,AttributeList> jsonListHandler) {
			jsonHandler = jsonListHandler;
		}

		public String toJson() {
			return jsonHandler.toJson(this);
		}

		public AttributeList fromJson(String json) throws DataMismatchException {
			return jsonHandler.fromJson(json);
		}

		public AttributeList fromJSONObject(JSONObject jo) throws DataMismatchException {
			return jsonHandler.fromJSONObject(jo);
		}
		
		public static AttributeList _fromJson(String json) throws DataMismatchException {
			return JSONAttributeListHandler._fromJSONObject(new JSONObject(json));
		}

		public static AttributeList _fromJSONObject(JSONObject jo) throws DataMismatchException {
			return JSONAttributeListHandler._fromJSONObject(jo);
		}

		public Attribute findInstanceOf(String itemId) {
			return get(findIndexOf(itemId));
		}

		public AttributeList me() {
			return this;
		}

		public AttributeList organize(String parentId, AttributeList list) {
			AttributeList al = new AttributeList();
			//sort into top level and children values;
			for(Attribute a: list) {
				if(a.parentId().equals(parentId)) {
					al.add(a);
				} 
				
			}
			return al;
		}

	}
	
	public class ProcessList extends AbstractCampList<Process,ProcessList> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4871935856225401863L;
		private JSONListHandler<Process,ProcessList> jsonHandler = new JSONProcessListHandler();
		
		public JSONListHandler<Process, ProcessList> jsonHandler() {
			return jsonHandler;
		}

		public void jsonHanlder(JSONListHandler<Process, ProcessList> jsonListHandler) {
			jsonHandler = jsonListHandler;
		}

		public String toJson() {
			return jsonHandler.toJson(this);
		}

		public ProcessList fromJson(String json) throws DataMismatchException {
			return jsonHandler.fromJson(json);
		}

		public ProcessList fromJSONObject(JSONObject jo) throws DataMismatchException {
			return jsonHandler.fromJSONObject(jo);
		}

		public static ProcessList _fromJson(String json) throws DataMismatchException {
			return JSONProcessListHandler._fromJSONObject(new JSONObject(json));
		}
		
		public static ProcessList _fromJSONObject(JSONObject jo) throws DataMismatchException {
			return JSONProcessListHandler._fromJSONObject(jo);
		}
		
		public String id(Process v) {
			return v.id();
		}
	
		public ProcessList me() {
			return this;
		}
		
		public Process findInstanceOf(String processInstanceId) {
			for(Process p: this) {
				if(p.instanceId().equals(processInstanceId)) {
					return p;
				}
			}
			return null;
		}

		
		public ProcessList organize(String parentId, ProcessList list) {
			return this;
		}
	
	}
		
	
}
