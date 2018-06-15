package com.camsolute.code.camp.lib.contract.process;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.DataMismatchException;
import com.camsolute.code.camp.lib.contract.HasListSelection;

import com.camsolute.code.camp.lib.contract.process.Process;

public interface ProcessList extends List<Process>,  HasListSelection<Process> {

    public int find(int itemId);

  public String toJson();

  public ProcessList fromJson(String json) throws DataMismatchException ;

  public ProcessList fromJSONObject(JSONObject jo) throws DataMismatchException;

  public static ProcessList _fromJSONObject(JSONObject jo) throws DataMismatchException {
    ProcessList pl = new ProcessListImpl();
    if(jo.getBoolean("isEmpty")) {
      return pl;
    }
    pl.setSelectionIndex(jo.getInt("selected"));
    Iterator<Object> i = jo.getJSONArray("list").iterator();
    while(i.hasNext()) {
      JSONObject j = (JSONObject)i.next();
      pl.add(JSONProcessHandler._fromJSONObject(j));
    }
    return pl;
  }

	public class ProcessListImpl extends ArrayList<Process> implements ProcessList {

		private static final long serialVersionUID = 4958620945707049027L;
		
		private int selected = 0;
		
		public String toJson() {
			String json = "{";
			json += "\"selected\":"+this.selected();
			json += ",\"isEmpty\":"+this.isEmpty();
			json += ",\"list\":[";
			boolean start = true;
			for(Process p: this) {
				if(!start) {
					json += ",";
				} else {
					start = false;
				}
				json += p.jsonHandler().toJson(p);
			}
			json += "]}";
			return json;
		}

		public ProcessList fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public ProcessList fromJSONObject(JSONObject jo) throws DataMismatchException {
			return _fromJSONObject(jo);
		}
		
 		public Process selected() {
			return get(selected);
		}

		public int selectionIndex() {
			return selected;
		}

		public void setSelectionIndex(int index) {
			selected = index;
		}

		public int select(int itemId) {
      int i = find(itemId);
      if(i!=-1) {
          selected = i;
      }
      return i;
		}

		public int select(Process item) {
        int i = find(item.id());
        if(i!=-1) {
            selected = i;
        }
        return i;
		}
      
      public int find(int itemId) {
          int indexCounter = 0;
          boolean indexChanged = false;
          for(Process p: this) {
              if(p.id() == itemId) {
                  selected = indexCounter;
                  indexChanged = true;
                  break;
              }
              indexCounter++;
          }
          if(indexCounter >= this.size()){
              return -1;
          }
          return indexCounter;
      }
	}

}
