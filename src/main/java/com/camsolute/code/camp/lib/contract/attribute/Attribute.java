package com.camsolute.code.camp.lib.contract.attribute;

import com.camsolute.code.camp.lib.contract.value.HasDefaultValue;
import com.camsolute.code.camp.lib.contract.core.HasPosition;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.CampData;
import com.camsolute.code.camp.lib.contract.core.CampStatus;
import com.camsolute.code.camp.lib.contract.core.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.IsBusinessObject;
import com.camsolute.code.camp.lib.contract.core.Serialization;
import com.camsolute.code.camp.lib.contract.history.HistoryMarker;
import com.camsolute.code.camp.lib.contract.value.HasValue;
import com.camsolute.code.camp.lib.contract.value.HasValueId;
import com.camsolute.code.camp.lib.contract.value.Value;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Version;
import com.camsolute.code.camp.lib.contract.process.HasObserverProcesses;
import com.camsolute.code.camp.lib.contract.process.Process;
import com.camsolute.code.camp.lib.contract.process.ProcessHandler;
import com.camsolute.code.camp.lib.contract.process.ProcessList;
import com.camsolute.code.camp.lib.contract.process.ProcessList.ProcessListImpl;
import com.camsolute.code.camp.lib.contract.process.Process.ProcessType;

public interface Attribute extends HasValue, HasValueId, HasDefaultValue, HasPosition, IsBusinessObject, HasJSONAttributeHandler, HasSQLAttributeHandler, Serialization<Attribute> {
	
	public boolean hasValueParent();

	public void hasValueParent(boolean hasValueParent);

	public int valueParentId();

	public void valueParentId(int id);	

	public Value valueParent();

	public void valueParent(Value parent);
    
  public Attribute clone();
  
  public static Attribute clone(Attribute attribute) throws DataMismatchException {
    return attribute.jsonAttributeHandler().fromJson(attribute.jsonAttributeHandler().toJson(attribute));
  }

    public class DefaultAttribute implements Attribute {

    	private int id = CampData.NEW_ID;
    	private int position = 0;

    	private String businessId;
    	private String businessKey;

    	private Value value;
    	private int valueId = CampData.NEW_ID;
    	private String defaultValue;
    	
    	private CampStatus status;
    	
    	private Group group;
    	private Version version;
    	
    	private Value parent;
    	private boolean hasValueParent = false;
    	private int valueParentId = CampData.NEW_ID;

    	private HistoryMarker history = new HistoryMarker.DefaultHistoryMarker();
    	private CampStates states = new CampStates();
    	
    	private ProcessList oberverProcesses = new ProcessListImpl();
    	private ProcessHandler processHandler;
    	
    	private JSONAttributeHandler jsonHandler;
    	private SQLAttributeHandler sqlHandler;
    	
			public Value value() {
				return value;
			}

			public Value updateValue(Value newValue, boolean registerUpdate) {
				Value previousValue = value;
				value = newValue;
				if(registerUpdate) {
					states.modify();
				}
				return null;
			}

			public int valueId() {
				return valueId;
			}

			public int valueId(int newValueId) {
				int previousId = this.valueId;
				this.valueId = newValueId;
				return previousId;
			}

			public String defaultValue() {
				return defaultValue;
			}


			public String updateDefaultValue(String value, boolean registerUpdate) {
				// TODO Auto-generated method stub
				return null;
			}


			public int position() {
				// TODO Auto-generated method stub
				return 0;
			}


			public int updatePosition(int id, boolean registerUpdate) {
				// TODO Auto-generated method stub
				return 0;
			}


			public void addObserverProcess(Process observerProcess) {
				// TODO Auto-generated method stub
				
			}


			public ProcessList removeObserverProcesses() {
				// TODO Auto-generated method stub
				return null;
			}


			public Process removeObserverProcess(String processInstanceId) {
				// TODO Auto-generated method stub
				return null;
			}


			public ProcessList removeObserverProcesses(ProcessType group) {
				// TODO Auto-generated method stub
				return null;
			}


			public void notifyObserverProcess(String processInstanceId) {
				// TODO Auto-generated method stub
				
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


			public int id() {
				// TODO Auto-generated method stub
				return 0;
			}


			public void id(int id) {
				// TODO Auto-generated method stub
				
			}


			public Version version() {
				// TODO Auto-generated method stub
				return null;
			}


			public void updateVersion(String version, boolean registerUpdate) {
				// TODO Auto-generated method stub
				
			}


			public void updateVersion(Version version, boolean registerUpdate) {
				// TODO Auto-generated method stub
				
			}


			public Group group() {
				// TODO Auto-generated method stub
				return null;
			}


			public void updateGroup(Group group, boolean registerUpdate) {
				// TODO Auto-generated method stub
				
			}


			public void updateGroup(String group, boolean registerUpdate) {
				// TODO Auto-generated method stub
				
			}


			public String initialBusinessId() {
				// TODO Auto-generated method stub
				return null;
			}


			public String businessId() {
				// TODO Auto-generated method stub
				return null;
			}


			public String updateBusinessId(String newId, boolean registerUpdate) {
				// TODO Auto-generated method stub
				return null;
			}


			public String onlyBusinessId() {
				// TODO Auto-generated method stub
				return null;
			}


			public String businessKey() {
				// TODO Auto-generated method stub
				return null;
			}


			public void updateBusinessKey(String businessKey, boolean registerUpdate) {
				// TODO Auto-generated method stub
				
			}


			public HistoryMarker history() {
				// TODO Auto-generated method stub
				return null;
			}


			public void setHistory(HistoryMarker instance) {
				// TODO Auto-generated method stub
				
			}


			public CampStates states() {
				// TODO Auto-generated method stub
				return null;
			}


			public CampStatus status() {
        return status;
			}


			public Enum<?> updateStatus(Enum<?> status, boolean registerUpdate) {
				// TODO Auto-generated method stub
				return null;
			}


			public Enum<?> updateStatus(String status, boolean registerUpdate) {
				// TODO Auto-generated method stub
				return null;
			}


			public Enum<?> previousStatus() {
				// TODO Auto-generated method stub
				return null;
			}


			public void setPreviousStatus(Enum<?> status) {
				// TODO Auto-generated method stub
				
			}


			public void setPreviousStatus(String status) {
				// TODO Auto-generated method stub
				
			}


			public void cleanStatus(IsBusinessObject object) {
				// TODO Auto-generated method stub
				
			}


			public JSONAttributeHandler jsonAttributeHandler() {
				// TODO Auto-generated method stub
				return null;
			}


			public void jsonAttributeHandler(JSONAttributeHandler jsonAttributeHandler) {
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
				return null;
			}


			public Group updateValueGroup(String group, boolean registerUpdate) {
				// TODO Auto-generated method stub
				return null;
			}


			public int attributePosition() {
				// TODO Auto-generated method stub
				return 0;
			}


			public int updateAttributePosition(int position, boolean registerUpdate) {
				// TODO Auto-generated method stub
				return 0;
			}


			public String attributeBusinessKey() {
				// TODO Auto-generated method stub
				return null;
			}


			public String updateAttributeBusinessKey(String id, boolean registerUpdate) {
				// TODO Auto-generated method stub
				return null;
			}


			public boolean hasValueParent() {
				// TODO Auto-generated method stub
				return false;
			}


			public void hasValueParent(boolean hasValueParent) {
				// TODO Auto-generated method stub
				
			}


			public int valueParentId() {
				// TODO Auto-generated method stub
				return 0;
			}


			public void valueParentId(int id) {
				// TODO Auto-generated method stub
				
			}


			public int valueParentAttributeId() {
				// TODO Auto-generated method stub
				return 0;
			}


			public void valueParentAttributeId(int id) {
				// TODO Auto-generated method stub
				
			}


			public Attribute parent() {
				// TODO Auto-generated method stub
				return null;
			}


			public void parent(Attribute parent) {
				// TODO Auto-generated method stub
				
			}


			public Attribute clone() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Value valueParent() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void valueParent(Value parent) {
				// TODO Auto-generated method stub
				
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
			
    }
}
