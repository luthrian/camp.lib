package com.camsolute.code.camp.lib.contract.process;

import com.camsolute.code.camp.lib.models.rest.Variables;

public interface ProcessMessage {
	public static enum MessageType {
		OrderProcessMessage,
		OrderOrderProcessMessage, //TODO
		ProductionOrderProcessMessage, //TODO
		ProductOrderProcessMessage, //TODO
		AttributeOrderProcessMessage, //TODO
		ModelOrderProcessMessage, //TODO
		OrderPositionProcessMessage, 
		ProductProcessMessage,
		AttributeProcessMessage, //TODO
		ModelProcessMessage, //TODO
		ProductionProcessMessage, //TODO
		CustomerProcessMessage; //TODO
	};
	
	public String messageName();
	
	public MessageType type();
	
	public String processInstanceId();
	public void setProcessInstanceId(String processInstanceId);
	
	public String businessKey();
	
	public String target();
	public void setTarget(String target);

	public class CamundaProcessMessage implements ProcessMessage {
		
		protected MessageType messageType;
		protected String messageName;
		protected String businessKey;
		protected String tenantId;
		protected boolean withoutTenant = true;
		protected String processInstanceId;
		protected Variables correlationKeys = new Variables();
		protected Variables localCorrelationKeys = new Variables();
		protected Variables processVariables = new Variables();
		protected boolean all = false;
		protected boolean resultEnabled = false;

		public CamundaProcessMessage(String messageName, String businessKey, String tenantId, String processInstanceId, MessageType messageType) {
			this.messageName = messageName;
			this.businessKey = businessKey;
			this.tenantId = tenantId;
			this.processInstanceId = processInstanceId;
			this.messageType = messageType;
		}
		public CamundaProcessMessage(String messageName, String businessKey, String processInstanceId, MessageType messageType) {
			this.messageName = messageName;
			this.businessKey = businessKey;
			this.processInstanceId = processInstanceId;
			this.messageType = messageType;
		}
		public CamundaProcessMessage(String messageName, String businessKey, MessageType messageType) {
			this.messageName = messageName;
			this.businessKey = businessKey;
			this.messageType = messageType;
		}
		
		public MessageType type() {
			return this.messageType;
		}
		public void type(MessageType messageType) {
			this.messageType  = messageType;
		}
		public String messageName() {
			return this.messageName;
		}
		
		public String businessKey() {
			return this.businessKey;
		}
		
		public String processInstanceId() {
			return this.processInstanceId;
		}
		
		public void setProcessInstanceId(String processInstanceId) {
			this.processInstanceId = processInstanceId;
		}
		
		public String target() {
			return this.tenantId;
		}
		
		public void setTarget(String target) {
			this.tenantId = target;
		}
		public String tenantId() {
			return this.tenantId;
		}
		
		public void setTenantId(String tenantId) {
			this.tenantId = tenantId;
		}
		
		public boolean withoutTenant() {
			return withoutTenant;
		}
		
		public void withoutTenant(boolean truth) {
			this.withoutTenant = truth;
		}

		public boolean all() {
			return all;
		}
		
		public void all(boolean truth) {
			this.all = truth;
		}

		public boolean resultEnabled() {
			return resultEnabled;
		}
		
		public void resultEnabled(boolean truth) {
			this.resultEnabled = truth;
		}
		
		public Variables correlationKeys(){
			return this.correlationKeys;
		}
		public void setCorrelationKeys(Variables correlationKeys) {
			this.correlationKeys = correlationKeys;
		}

		public Variables localCorrelationKeys(){
			return this.localCorrelationKeys;
		}
		public void setLocalCorrelationKeys(Variables localCorrelationKeys) {
			this.localCorrelationKeys = localCorrelationKeys;
		}
		
		public Variables processVariables(){
			return this.processVariables;
		}
		public void setProcessVariables(Variables processVariables) {
			this.processVariables = processVariables;
		}
		
	}
		
}
