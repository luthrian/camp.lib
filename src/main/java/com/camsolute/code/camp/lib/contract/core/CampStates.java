package com.camsolute.code.camp.lib.contract.core;


import org.json.JSONObject;

public interface CampStates {
	
	public void update(CampStates states);
	public boolean isDirty();
	public void dirty();
	public void persistType(PersistType todo);
	public void ioAction(IOAction action);
	public boolean isLoaded();
	public boolean isSaved();
	public boolean isUpdated();
	public boolean isDeleted();
	public boolean isNew();
	public boolean hasNewId();
	public boolean isModified();
	public boolean saveRequired();
	public boolean updateRequired();
	public void modify();
	public void modify(PersistType todo);
	public void setModified(boolean modified);
	public IOAction prevIO();
	public void prevIO(IOAction action);
	public void revertIOAction();
	public String print();
	public String toJson();
	public CampStates fromJson(String json);
	public String toString();
	public boolean readyToSave();
	public <T extends IsBusinessObject> boolean updateBeforeSave(T instance);
	public <T extends IsBusinessObject> boolean notReadyToSave(T instance);

	public static enum IOAction {
		NONE, NEWID, LOAD, SAVE, UPDATE, DELETE;
	}

	public static enum PersistType {
		UPDATE, SAVE;
	}	

	/**
	 * generates a JSON string representation of the CampStates object instance of
	 * the format:<br>
	 * 
	 * <pre>
	 * {"loaded":[boolean],"saved":[boolean],"isNew":[boolean],"dirty":[boolean],"modified":[boolean]}
	 * </pre>
	 * 
	 * [boolean] is the string representation for 'true' or 'false' <br>
	 * <br>
	 * ...
	 * @param cs CampState
	 * @return json string
	 */
	public static String _toJson(CampStates cs) {
		String json = "{";
		json += "\"loaded\":" + cs.isLoaded();
		json += ",\"saved\":" + cs.isSaved();
		json += ",\"updated\":" + cs.isUpdated();
		json += ",\"deleted\":" + cs.isDeleted();
		json += ",\"isNew\":" + cs.isNew();
		json += ",\"hasNewId\":" + cs.hasNewId();
		json += ",\"dirty\":" + cs.isDirty();
		json += ",\"modified\":" + cs.isModified() ;
		json += ",\"prevIO\":\"" + cs.prevIO().name() + "\"" ;
		json += "}";
		return json;
	}

	/**
	 * generates a <code>CampStates</code> object instance from a JSON string representation of a CampStates object.
	 * The format of the JSON representation must adhere to the following, whereby the order of each 
	 * name-value pair node is irrelevant:<br>
	 * 
	 * <pre>
	 * {"loaded":[boolean],"saved":[boolean],"isNew":[boolean],"dirty":[boolean],"modified":[boolean]}
	 * </pre>
	 * 
	 * [boolean] is a string representation for 'true' or 'false' see <code>java.lang.Boolean.toString()</code>
	 * <br>
	 * @param json json string
	 * @return CampStates <code>CampStates</code> object instance
	 */
	public static CampStates _fromJson(String json) {
		return _fromJSONObject(new JSONObject(json));
	}

	public static CampStates _fromJSONObject(JSONObject jo) {
		CampStates cs = new CampStatesImpl();
		if (jo.getBoolean("loaded")) {
			cs.ioAction(IOAction.LOAD);
		}
		if (jo.getBoolean("saved")) {
			cs.ioAction(IOAction.SAVE);
		}
		if (jo.getBoolean("updated")) {
			cs.ioAction(IOAction.UPDATE);
		}
		if (jo.getBoolean("deleted")) {
			cs.ioAction(IOAction.DELETE);
		}
		if (jo.getBoolean("isNew")) {
			cs.ioAction(IOAction.NONE);
		}
		if (jo.getBoolean("newId")) {
			cs.ioAction(IOAction.NEWID);
		}
		if (jo.getBoolean("dirty")) {
			cs.dirty();
		}
		if (jo.getBoolean("modified")) {
			cs.modify();
		}
		if (jo.has("prevIO")) {
			cs.prevIO(IOAction.valueOf(jo.getString("prevIO")));
		}
		return cs;
	}

	
	public class CampStatesImpl implements CampStates {
		private IOAction lastIO = IOAction.NONE;
		private IOAction prevIO = IOAction.NONE;
		
		private PersistType todo = PersistType.SAVE;
		
		protected boolean dirty = false;
	
		private boolean modified = false;
	
		public void update(CampStates states) {
	  	if(states.isDeleted())ioAction(IOAction.DELETE);
	  	if(states.isLoaded())ioAction(IOAction.LOAD);
	  	if(states.isSaved())ioAction(IOAction.SAVE);
	  	if(states.isUpdated())ioAction(IOAction.UPDATE);
	  	if(states.isNew())ioAction(IOAction.NONE);
	  	if(states.hasNewId())ioAction(IOAction.NEWID);
	  	if(states.isDirty())dirty();
	  	setModified(states.isModified());
		}

		public boolean isDirty() {
			return dirty;
		}
	
		public void dirty() {
			dirty = true;
		}
	
		public void persistType(PersistType todo) {
			this.todo = todo;
		}
	
		public void ioAction(IOAction action) {
			prevIO = lastIO;
			lastIO = action;
		}
	
		public boolean isLoaded() {
			return lastIO.equals(IOAction.LOAD);
		}
	
		public boolean isSaved() {
			return lastIO.equals(IOAction.SAVE);
		}
	
		public boolean isUpdated() {
			return lastIO.equals(IOAction.UPDATE);
		}
	
		public boolean isDeleted() {
			return lastIO.equals(IOAction.DELETE);
		}
	
		public boolean isNew() {
			return this.lastIO.equals(IOAction.NONE);
		}
	
		public boolean hasNewId() {
			return this.lastIO.equals(IOAction.NEWID);
		}
	
		public boolean isModified() {
			return modified;
		}
	
		@Override
		public boolean saveRequired() {
			return todo.name().equals(PersistType.SAVE.name());
		}
	
		@Override
		public boolean updateRequired() {
			return todo.name().equals(PersistType.UPDATE.name());
		}
		
		@Override
		public void modify() {
			modified = true;
		}
	
		@Override
		public void modify(PersistType todo) {
			modified = true;
			this.todo = todo;
		}
	
	
		@Override
		public void setModified(boolean modified) {
			this.modified = modified;
			if(!modified){
				this.todo = PersistType.UPDATE;
			}
		}
	
		public IOAction prevIO() {
			return prevIO;
		}
		public void prevIO(IOAction action) {
			prevIO = action;
		}
		public void revertIOAction() {
			lastIO = prevIO;
		}
		/**
		 * {@inheritDoc}
		 */
		public String print() {
			return _toJson(this);
		}
	
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toJson() {
			return _toJson(this);
		}
	
		@Override
		public CampStates fromJson(String json) {
			return _fromJson(json);
		}
	
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return _toJson(this);
		}
	
		@Override
		public boolean readyToSave() {
			return (isModified() || isNew());
		}
		
	  public <T extends IsBusinessObject> boolean updateBeforeSave(T instance) {
	    return (isLoaded() || (!isNew() && (isModified() || instance.history().isFirst())));
	  }
	
	  public <T extends IsBusinessObject> boolean notReadyToSave(T instance) {
	    return (isLoaded() || (!instance.history().isCurrent() && (!isModified() || !isNew())));
	  }

	}
}
