package com.camsolute.code.camp.lib.contract.core;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.value.Value;
import com.camsolute.code.camp.lib.contract.HasListSelection;
import com.camsolute.code.camp.lib.contract.attribute.Attribute;
import com.camsolute.code.camp.lib.contract.core.JSONListHandler.JSONValueListHandler;
import com.camsolute.code.camp.lib.contract.core.JSONListHandler.JSONAttributeListHandler;

//TODO: do this for all List objects (Order, ... etc)
public interface CampList<T extends Serialization<T>,Q extends CampList<T,Q>> extends List<T>, HasListSelection<T>, HasJSONListHandler<T,Q>, Serialization<Q> {

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

		public int select(int itemId) {
			int indexCounter = 0;
			boolean indexChanged = false;
			for(T v: this) {
				if(id(v) == itemId) {
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

		protected abstract int id(T v);
		
		public int select(T item) {
			int indexCounter = 0;
			boolean indexChanged = false;
			for(T v: this) {
				if(id(v) == id(item)) {
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
	
 	public class ValueList extends AbstractCampList<Value,ValueList> {//ArrayList<Value> implements CampList<Value,ValueList>, Serialization<ValueList> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 394652115283156167L;
		
		private JSONListHandler<Value,ValueList> jsonHandler = new JSONValueListHandler();
		
 		public int id(Value v) {
 			return v.id();
 		}
 		
 		public JSONListHandler<Value,ValueList> jsonHandler() {
			return jsonHandler;
		}

		public void jsonHanlder(JSONListHandler<Value,ValueList> jsonListHandler) {
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
		
		public static ValueList _fromJSONObject(JSONObject jo) throws DataMismatchException {
			return JSONValueListHandler._fromJSONObject(jo);
		}

	}
	
	public class AttributeList extends AbstractCampList<Attribute,AttributeList> {// extends ArrayList<Attribute> implements CampList<Attribute,AttributeList>, Serialization<AttributeList> {

		private static final long serialVersionUID = 7169498657362780225L;

		private JSONListHandler<Attribute,AttributeList> jsonHandler = new JSONAttributeListHandler();

		public int id(Attribute a) {
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
		
		public static AttributeList _fromJSONObject(JSONObject jo) throws DataMismatchException {
			return JSONAttributeListHandler._fromJSONObject(jo);
		}
	}
	

}
