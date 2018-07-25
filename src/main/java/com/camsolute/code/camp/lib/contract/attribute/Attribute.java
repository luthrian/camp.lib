package com.camsolute.code.camp.lib.contract.attribute;

import com.camsolute.code.camp.lib.contract.value.HasDefaultValue;
import com.camsolute.code.camp.lib.contract.core.HasPosition;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.CampStatus;
import com.camsolute.code.camp.lib.contract.core.CampStates;
import com.camsolute.code.camp.lib.contract.core.CampStates.CampStatesImpl;
import com.camsolute.code.camp.lib.contract.core.HasAttributes;
import com.camsolute.code.camp.lib.contract.core.HasId;
import com.camsolute.code.camp.lib.contract.core.HasParent;
import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.CampException.ElementNotInListException;
import com.camsolute.code.camp.lib.contract.core.CampException.StatusDirtyException;
import com.camsolute.code.camp.lib.contract.core.CampException.StatusMismatchException;
import com.camsolute.code.camp.lib.contract.core.CampList.AttributeList;
import com.camsolute.code.camp.lib.contract.core.IsBusinessObject;
import com.camsolute.code.camp.lib.contract.core.Serialization;
import com.camsolute.code.camp.lib.contract.history.HistoryMarker;
import com.camsolute.code.camp.lib.contract.value.HasValue;
import com.camsolute.code.camp.lib.contract.value.HasValueId;
import com.camsolute.code.camp.lib.contract.value.Value;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Version;
import com.camsolute.code.camp.lib.utilities.Util;
import com.camsolute.code.camp.lib.contract.process.Process;
import com.camsolute.code.camp.lib.contract.process.ProcessHandler;
import com.camsolute.code.camp.lib.contract.core.CampList.ProcessList;
import com.camsolute.code.camp.lib.contract.process.Process.ProcessType;

public interface Attribute extends HasValue, HasValueId, HasParent<Attribute>, HasAttributes, HasDefaultValue, HasPosition, IsBusinessObject, HasJSONAttributeHandler, HasSQLAttributeHandler, Serialization<Attribute> {
	
  public Attribute clone();
  
  public static Attribute clone(Attribute attribute) throws DataMismatchException {
    return attribute.jsonHandler().fromJson(attribute.jsonHandler().toJson(attribute));
  }

    public class DefaultAttribute implements Attribute {

    	private String id = HasId.newId();
    	private int position = 0;

    	private String businessId;
    	private String businessKey;

    	private Value<?,?> value;
    	private String valueId = "";
    	private String defaultValue;
    	
    	private CampStatus status;
    	
    	private Group group;
    	private Version version;
    	
    	private Attribute parent;
    	private boolean hasParent = false;
    	private String parentId = "";

    	private AttributeList attributes;
    	
    	private HistoryMarker history = new HistoryMarker.DefaultHistoryMarker();
    	private CampStates states = new CampStatesImpl();
    	
    	private ProcessList observerProcesses = new ProcessList();
    	private ProcessHandler processHandler;
    	
    	private JSONAttributeHandler jsonHandler;
    	private SQLAttributeHandler sqlHandler;
    	
			public Value<?,?> value() {
				return value;
			}

			public Value<?,?> updateValue(Value<?,?> newValue, boolean registerUpdate) {
				Value<?,?> previousValue = value;
				value = newValue;
				if(registerUpdate) {
					states.modify();
				}
				return previousValue;
			}

			public String valueId() {
				return valueId;
			}

			public String valueId(String newValueId) {
				String previousId = this.valueId;
				this.valueId = newValueId;
				return previousId;
			}

			public String defaultValue() {
				return defaultValue;
			}


			public String updateDefaultValue(String newValue, boolean registerUpdate) {
				String prev = this.defaultValue;
				this.defaultValue = newValue;
				if(registerUpdate) {
					states.modify();
				}
				return prev;
			}


			public int position() {
				return position;
			}


			public int updatePosition(int newPosition, boolean registerUpdate) {
				int prev = this.position;
				this.position = newPosition;
				if(registerUpdate) {
					states.modify();
				}
				return this.position;
			}


			public void addObserverProcess(Process observerProcess) {
				this.observerProcesses.add(observerProcess);
			}


			public ProcessList removeObserverProcesses() {
				ProcessList pl = this.observerProcesses;
				this.observerProcesses = new ProcessList();
				return pl;
			}

//TODO: change HasObserverProcess to add exception if the list doesnt contain a process with that instanceId
			public Process removeObserverProcess(String processInstanceId) throws ElementNotInListException{
				Process p = null;
				int index = 0;
				for(Process pl: this.observerProcesses) {
					if(pl.instanceId().equals(processInstanceId)){
						p = this.observerProcesses.remove(index);
					}
					index++;
				}
				if(p == null) {
					throw new ElementNotInListException("No obeserver processes with process instance Id("+processInstanceId+") registered");
				}
				return p;
			}


			public ProcessList removeObserverProcesses(ProcessType group) throws ElementNotInListException{
				ProcessList pl = new ProcessList();
				for(Process p: this.observerProcesses) {
					if(p.type().equals(group)){
						pl.add(p);
					}
				}
				if(pl.size()==0) {
					throw new ElementNotInListException("No obeserver processes of ProcessType("+group.name()+") registered");
				}
				return pl;
			}


			public void notifyObserverProcess(String processInstanceId) {
				processHandler().notifyProcess(this.observerProcesses.findInstanceOf(processInstanceId));
			}


			public void notifyObserverProcess(String processInstanceId, Enum<?> event) {
				// TODO Auto-generated method stub
				
			}


			public void notifyObserverProcesses() {
				// TODO Auto-generated method stub
				
			}


			public void notifyObserverProcesses(Enum<?> event) {
				// TODO Auto-generated method stub
				
			}


			public void notifyObserverProcesses(ProcessType type) {
				// TODO Auto-generated method stub
				
			}


			public void notifyObserverProcesses(ProcessType type, Enum<?> event) {
				// TODO Auto-generated method stub
				
			}


			public ProcessList observerProcesses() {
				// TODO Auto-generated method stub
				return null;
			}


			public ProcessList observerProcesses(ProcessType group) {
				// TODO Auto-generated method stub
				return null;
			}


			public void observerProcesses(ProcessList processes) {
				// TODO Auto-generated method stub
				
			}


			public String id() {
				return id;
			}


			public String updateId(String id) {
				String prev = this.id;
				this.id = id;
				return prev;
			}


			public Version version() {
				return version;
			}


			public void updateVersion(String version, boolean registerUpdate) {
				this.version = new Version(version);
				if(registerUpdate) {
					states.modify();
				}
			}


			public void updateVersion(Version version, boolean registerUpdate) {
				this.version = version;
				if(registerUpdate) {
					states.modify();
				}
			}


			public Group group() {
				return group;
			}


			public void updateGroup(Group group, boolean registerUpdate) {
				this.group = group;
				if(registerUpdate) {
					states.modify();
				}
			}


			public void updateGroup(String group, boolean registerUpdate) {
				this.group = new Group(group);
				if(registerUpdate) {
					states.modify();
				}
			}


			public String initialBusinessId() {
				return this.businessId;
			}


			public String businessId() {
				return this.id+Util.DB._VS+this.businessId;
			}


			public String updateBusinessId(String newId, boolean registerUpdate) {
				String prev = businessId;
				businessId = newId;
				if(registerUpdate) {
					states.modify();
				}
				return prev;
			}


			public String onlyBusinessId() {
				return businessId;
			}


			public String businessKey() {
				return businessKey;
			}


			public void updateBusinessKey(String businessKey, boolean registerUpdate) {
				this.businessKey = businessKey;
				if(registerUpdate) {
					states.modify();
				}
			}


			public HistoryMarker history() {
				return history;
			}


			public void setHistory(HistoryMarker instance) {
				history = instance;
			}


			public CampStates states() {
				return states;
			}


			public CampStatus status() {
        return status;
			}


			public Enum<?> updateStatus(Enum<?> status, boolean registerUpdate) throws StatusMismatchException, StatusDirtyException {
				return this.status.statusHandler().updateStatus(this, status, registerUpdate);
			}


			public Enum<?> updateStatus(String status, boolean registerUpdate) throws StatusMismatchException, StatusDirtyException {
				return this.status.statusHandler().updateStatus(this, status, registerUpdate);
			}


			public Enum<?> previousStatus() {
				return status.statusHandler().previousStatus();
			}


			public void setPreviousStatus(Enum<?> status) throws StatusMismatchException, StatusDirtyException {
				this.status.statusHandler().setPreviousStatus(status);
			}

			public void setPreviousStatus(String status) throws StatusMismatchException, StatusDirtyException {
				this.status.statusHandler().setPreviousStatus(status);
			}


			public void cleanStatus(IsBusinessObject object) throws StatusMismatchException, StatusDirtyException {
				status.statusHandler().cleanStatus(object);
			}


			public JSONAttributeHandler jsonHandler() {
				// TODO Auto-generated method stub
				return null;
			}


			public void jsonHandler(JSONAttributeHandler jsonAttributeHandler) {
				// TODO Auto-generated method stub
				
			}


			public ProcessHandler processHandler() {
				// TODO Auto-generated method stub
				return null;
			}


			public void processHandler(ProcessHandler processHandler) {
				// TODO Auto-generated method stub
				
			}


			public int attributeId() {
				// TODO Auto-generated method stub
				return 0;
			}


			public int attributeId(int attributeId) {
				// TODO Auto-generated method stub
				return 0;
			}


			public Group valueGroup() {
				// TODO Auto-generated method stub
				return null;
			}


			public Group updateValueGroup(Group group, boolean registerUpdate) {
				// TODO Auto-generated method stub
				if(registerUpdate) {
					states.modify();
				}
				return null;
			}


			public Group updateValueGroup(String group, boolean registerUpdate) {
				// TODO Auto-generated method stub
				if(registerUpdate) {
					states.modify();
				}
				return null;
			}


			public int attributePosition() {
				// TODO Auto-generated method stub
				return 0;
			}


			public int updateAttributePosition(int position, boolean registerUpdate) {
				// TODO Auto-generated method stub
				if(registerUpdate) {
					states.modify();
				}
				return 0;
			}


			public String attributeBusinessKey() {
				// TODO Auto-generated method stub
				return null;
			}


			public String updateAttributeBusinessKey(String id, boolean registerUpdate) {
				// TODO Auto-generated method stub
				if(registerUpdate) {
					states.modify();
				}
				return null;
			}


			public boolean hasParent() {
				return hasParent;
			}


			public void hasParent(boolean hasParent) {
				this.hasParent = hasParent;
			}


			public String parentId() {
				return parentId;
			}


			public void parentId(String id,boolean registerUpdate) {
				this.parentId = id;
				if(registerUpdate) {
					states.modify();
				}
			}

			public Attribute parent() {
				return parent;
			}


			public void parent(Attribute parent, boolean registerUpdate) {
				this.parent = parent;
				parentId(parent.id(),registerUpdate);
			}


			public AttributeList attributes() {
				return this.attributes;
			}
			
			public void attributes(AttributeList list) {
				this.attributes = list;
			}
			
			public Attribute clone() {
				try {
					return jsonHandler.fromJson(jsonHandler.toJson(this));
				} catch( DataMismatchException e) {
					e.printStackTrace();
				}
				return null;
			}

			public String toJson() {
				return jsonHandler.toJson(this);
			}
			
			public Attribute fromJson(String json) throws DataMismatchException {
				return jsonHandler.fromJson(json);
			}
			
			public Attribute fromJSONObject(JSONObject jo) throws DataMismatchException {
				return jsonHandler.fromJSONObject(jo);
			}

			public void parent(Attribute parent) {
				 this.parent = parent;
				 this.parentId = parent.id();
			}
			
    }
}
