package com.camsolute.code.camp.lib.contract.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.Serialization;
import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.CampTable.ValueTable;
import com.camsolute.code.camp.lib.contract.core.JSONTableHandler.JSONValueTableHandler;


public interface Stack<T extends Serialization<T>> extends List<T>, Serialization<Stack<T>> {
	public T leaf(int stackIndex);
	public void addLeaf(T element);
	public void insertLeaf(int stackIndex, boolean after, T element);
	public int numberOfLeaves();
	public void setStack(List<T> elements);
	
	public abstract class AbstractStack<T extends Serialization<T>> extends ArrayList<T> implements Stack<T> {
	}
	
	public class ValueTableStack extends AbstractStack<ValueTable> {


		public ValueTable leaf(int stackIndex) {
			return get(stackIndex);
		}


		public void addLeaf(ValueTable element) {
			add(element);
		}
		
		public void addLeaf(int stackIndex, ValueTable element) {
			set(stackIndex,element);
		}

		public void insertLeaf(int stackIndex, boolean after, ValueTable element) {
			if(after) {
				stackIndex++;
			}
			add(stackIndex,element);
		}

		public int numberOfLeaves() {
			return size();
		}

		public void setStack(List<ValueTable> elements) {
			clear();
			addAll(elements);
		}

		public String toJson() {
			String json = "{";
			json += "\"isEmpty\":"+isEmpty();
			json += ",\"size\":"+size();
			json += ",\"stack\":[";
			boolean start = true;
			for(ValueTable t: this) {
				if(!start) {
					json += ",";
				} else {
					start = false;
				}
				json += t.toJson();
			}
			json += "]}";
			return json;
		}

		public Stack<ValueTable> fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}

		public Stack<ValueTable> fromJSONObject(JSONObject jo) throws DataMismatchException {
			return _fromJSONObject(jo);
		}
		
		public static Stack<ValueTable> _fromJSONObject(JSONObject jo) throws DataMismatchException {
			Stack<ValueTable> stack = new ValueTableStack();
			if(jo.getBoolean("isEmpty")) {
				return stack;
			}
			Iterator<Object> i = jo.getJSONArray("stack").iterator();
			while(i.hasNext()) {
				stack.add(JSONValueTableHandler._fromJSONObject((JSONObject)i.next()));
			}
			return stack;
		}
	}
}
