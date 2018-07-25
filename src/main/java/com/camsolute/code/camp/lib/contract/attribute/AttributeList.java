package com.camsolute.code.camp.lib.contract.attribute;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.HasListSelection;

import com.camsolute.code.camp.lib.contract.attribute.Attribute;

public interface AttributeList extends List<Attribute>,  HasListSelection<Attribute> {

  public int find(String itemId);

  public String toJson();

  public AttributeList fromJson(String json) throws DataMismatchException ;

  public AttributeList fromJSONObject(JSONObject jo) throws DataMismatchException;

  public static AttributeList _fromJSONObject(JSONObject jo) throws DataMismatchException {
    AttributeList al = new AttributeListImpl();
    if(jo.getBoolean("isEmpty")) {
      return al;
    }
    al.setSelectionIndex(jo.getInt("selected"));
    Iterator<Object> i = jo.getJSONArray("list").iterator();
    while(i.hasNext()) {
      JSONObject j = (JSONObject)i.next();
      al.add(JSONAttributeHandler._fromJSONObject(j));
    }
    return al;
  }
	public class AttributeListImpl extends ArrayList<Attribute> implements AttributeList {

		private static final long serialVersionUID = 4958620945707049027L;
		
		private int selected = 0;
		
		public String toJson() {
			String json = "{";
			json += "\"selected\":"+this.selected();
			json += ",\"isEmpty\":"+this.isEmpty();
			json += ",\"list\":[";
			boolean start = true;
			for(Attribute a: this) {
				if(!start) {
					json += ",";
				} else {
					start = false;
				}
				json += a.jsonHandler().toJson(a);
			}
			json += "]}";
			return json;
		}

		public AttributeList fromJson(String json) throws DataMismatchException {
			return fromJSONObject(new JSONObject(json));
		}
		
		public AttributeList fromJSONObject(JSONObject jo) throws DataMismatchException {
			return _fromJSONObject(jo);
		}
		
 		public Attribute selected() {
			return get(selected);
		}

		public int selectionIndex() {
			return selected;
		}

		public void setSelectionIndex(int index) {
			selected = index;
		}

		public int select(String itemId) {
      int i = find(itemId);
      if(i!=-1) {
          selected = i;
      }
      return i;
		}

		public int select(Attribute item) {
        int i = find(item.id());
        if(i!=-1) {
            selected = i;
        }
        return i;
		}

		
    public int find(String itemId) {
          int indexCounter = 0;
          boolean indexChanged = false;
          for(Attribute a: this) {
              if(a.id().equals(itemId)) {
                  selected = indexCounter;
                  indexChanged = true;
                  break;
              }
              indexCounter++;
          }
          if(!indexChanged){
              return -1;
          }
          return indexCounter;
      }
	}

}
