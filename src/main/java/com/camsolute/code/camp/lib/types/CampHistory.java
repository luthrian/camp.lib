package com.camsolute.code.camp.lib.types;

import java.sql.Timestamp;

import com.camsolute.code.camp.core.U;
import com.camsolute.code.camp.core.exceptions.InitialIdInstantiationWithNullValueException;
//import com.camsolute.code.camp.core.interfaces.HasHistory;
import com.camsolute.code.camp.core.interfaces.HasStatus;
//import com.camsolute.code.camp.core.interfaces.IsTimestamped;
import com.camsolute.code.camp.models.InstanceId;
import com.camsolute.code.camp.models.business.Order;

public class CampHistory<T extends HasStatus> { //implements HasHistory, IsTimestamped, HasStatus {
	
	private Timestamp timestamp;
	
	private InstanceId instanceId;
	
	private InstanceId initialInstanceId;
	
	private InstanceId currentInstanceId;

	public CampHistory() throws InitialIdInstantiationWithNullValueException {
		
		this.timestamp = U.timestamp();
		
		this.instanceId = new InstanceId();
		
		InstanceId initialInstanceId = null;
		
		InstanceId currentInstanceId = null;
		
		try {
		
			currentInstanceId = new InstanceId(this.instanceId.id());
			
			initialInstanceId = new InstanceId(this.instanceId.id());
		
		} catch (InitialIdInstantiationWithNullValueException e) {
			e.printStackTrace();
			throw e;
		}
		
		this.initialInstanceId = initialInstanceId;
		
		this.currentInstanceId = currentInstanceId;
	}
	
	public CampHistory(String instanceId, String currentInstanceId, String initialInstanceId) {

		this.timestamp = U.timestamp();
		
		InstanceId iId = null;
		
		InstanceId initialIId = null;
		
		InstanceId currentIId = null;
		try {
			iId = new InstanceId(instanceId);
			initialIId = new InstanceId(initialInstanceId);
			currentIId = new InstanceId(currentInstanceId);
		} catch (InitialIdInstantiationWithNullValueException e) {
			e.printStackTrace();
		}
		this.instanceId = iId;
		this.initialInstanceId = initialIId;
		this.currentInstanceId = currentIId;
	}
	
	public CampHistory(InstanceId instanceId, InstanceId currentInstanceId, InstanceId initialInstanceId) {
		this.timestamp = U.timestamp();
		this.instanceId = instanceId;
		this.initialInstanceId = initialInstanceId;
		this.currentInstanceId = currentInstanceId;
	}

	//@Override
	public InstanceId instanceId() {
		return this.instanceId;
	}
	//@Override
	public InstanceId initialInstanceId() {
		return this.initialInstanceId;
	}
	
	//@Override
	public InstanceId currentInstanceId() {
		return this.currentInstanceId;
	}
	
	//@Override
	public InstanceId updateInstance() {
		try {
			this.initialInstanceId = new InstanceId(instanceId.id());
			this.instanceId = new InstanceId() ;
			this.currentInstanceId = new InstanceId(instanceId.id());
		} catch (InitialIdInstantiationWithNullValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instanceId;
	}

	//@Override
	public boolean firstInstance(){
		return (initialInstanceId.id().equals(instanceId.id()));
	}

	//@Override
	public boolean currentInstance(){
		return (currentInstanceId.id().equals(instanceId.id()));
	}

	//@Override
	public Timestamp timestamp() {
		return this.timestamp;
	}

	//@Override
	public void timestamp(Timestamp ts) {
		this.timestamp = ts;
	}

	//@Override
	public Timestamp stamptime() {
		this.timestamp = U.timestamp();
		return this.timestamp;
	}

	public boolean updateBeforeSave(T instance) {
		return (instance.states().wasLoaded() || (!instance.states().isNew() && (instance.states().isModified()|| firstInstance())));
	}
	
//	public boolean updateBeforeSave(Order instance) {
//		return (instance.states().wasLoaded() || (!instance.states().isNew() && (instance.states().isModified()|| instance.history().firstInstance())));
//	}
	
	public boolean notReadyToSave(T instance) {
		return (instance.states().wasLoaded()||(!currentInstance() && (!instance.states().isModified() || !instance.states().isNew())));
	}
	

}
