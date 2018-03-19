/*******************************************************************************
 * Copyright (C) 2018 Christopher Campbell
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * 	Christopher Campbell - all code prior and post initial release
 ******************************************************************************/
package com.camsolute.code.camp.lib.models.product;

import java.sql.Timestamp;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.data.CampRest.Order;
import com.camsolute.code.camp.lib.models.AttributeMap;
import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.CampStates;
import com.camsolute.code.camp.lib.models.CampStatesInterface.PersistType;
import com.camsolute.code.camp.lib.models.Group;
import com.camsolute.code.camp.lib.models.Model;
import com.camsolute.code.camp.lib.models.ModelList;
import com.camsolute.code.camp.lib.models.Version;
import com.camsolute.code.camp.lib.models.order.OrderPosition;
import com.camsolute.code.camp.lib.models.order.OrderPosition.Status;
import com.camsolute.code.camp.lib.models.process.Process;
import com.camsolute.code.camp.lib.models.process.ProcessList;
import com.camsolute.code.camp.lib.models.process.ProductProcess;
import com.camsolute.code.camp.lib.models.process.Process.ProcessType;
import com.camsolute.code.camp.lib.models.rest.Message;
import com.camsolute.code.camp.lib.models.rest.MessageList;
import com.camsolute.code.camp.lib.models.rest.OrderPositionProcessMessage;
import com.camsolute.code.camp.lib.models.rest.Request;
import com.camsolute.code.camp.lib.models.rest.Request.Principal;
import com.camsolute.code.camp.lib.models.rest.Request.RequestType;
import com.camsolute.code.camp.lib.models.rest.OrderPositionProcessMessage.OrderPositionMessage;
import com.camsolute.code.camp.lib.models.rest.ProductProcessMessage;
import com.camsolute.code.camp.lib.models.rest.ProductProcessMessage.ProductMessage;
import com.camsolute.code.camp.lib.utilities.Util;

public class Product implements ProductInterface {

	public static enum Status {
		CREATED, DESIGN, DEVELOPMENT, SUBMITTED, REVIEW, RELEASED, RECALLED, DECOMISSIONED, MODIFIED, DIRTY, CLEAN;
	}

	private int						id							= Util.NEW_ID;
	private String				name;
	private String				businessKey;
	private int 					modelId = Util.NEW_ID;
	private Model					model = null;
	private ModelList			models = new ModelList();
	private Group					group;
	private Version				version;
	private Timestamp			date = Util.Time.timestamp();
	private Status				status					= Status.CREATED;
	private Status				previousStatus	= Status.CLEAN;
	private CampStates		states					= new CampStates();
	private CampInstance	history					= new CampInstance();
	private AttributeMap	attributes			= new AttributeMap();
	private ProcessList		processes				= new ProcessList();

	public Product(String name) {
		this.name = name;
		this.history.date(this.date);
	}

	public Product(String name, String businessKey) {
		this.name = name;
		this.businessKey = businessKey;
		this.history.date(this.date);
	}

	public Product(String name, String businessKey, Group group, Version version, Timestamp date) {
		this.name = name;
		this.businessKey = businessKey;
		this.group = group;
		this.version = version;
		this.date = date;
		this.history.date(date);
	}

	public Product(int id, String name, String businessKey, Group group, Version version, Timestamp date) {
		this.id = id;
		this.name = name;
		this.businessKey = businessKey;
		this.group = group;
		this.version = version;
		this.date = date;
		this.history.date(date);
	}

	public Product(String name, String businessKey, Group group, Version version, Timestamp date, Timestamp endOfLife) {
		this.name = name;
		this.businessKey = businessKey;
		this.group = group;
		this.version = version;
		this.date = date;
		this.history.date(date);
		this.history.endOfLife(endOfLife);
	}

	public Product(int id, String name, String businessKey, Group group, Version version, Timestamp date, Timestamp endOfLife) {
		this.id = id;
		this.name = name;
		this.businessKey = businessKey;
		this.group = group;
		this.version = version;
		this.date = date;
		this.history.date(date);
		this.history.endOfLife(endOfLife);
	}


	@Override
	public int modelId() {
		return this.modelId;
	}
	
	@Override
	public void updateModelId(int id) {
		for(Model m: this.models){
			if(m.id() == id){
				this.modelId = id;
				this.model = m;
				this.states.modify(PersistType.UPDATE);
			}
		}
	}
		
	@Override
	public void setModelId(int id) {
		for(Model m: this.models){
			if(m.id() == id){
				this.modelId = id;
				this.model = m;
			}
		}
	}
	
	@Override
	public Model model() {
		return this.model;
	}
	
	@Override
	public ModelList models() {
		return this.models;
	}

	@Override
	public void setModels(ModelList ml) {
		this.models = ml;
	}

	@Override
	public void addModel(Model m) {
		this.models.add(m);
		this.states.modify(PersistType.UPDATE);
	}

	@Override
	public Timestamp date() {
		return this.date;
	}

	@Override
	public Timestamp updateDate(Timestamp date) {
		Timestamp prev = this.date;
		this.date = date;
		this.history.date(date);
		this.states.modify(PersistType.SAVE);
		return prev;
	}

	@Override
	public void setDate(Timestamp date) {
		this.date = date;
		this.history.date(date);
	}

	@Override
	public int id() {
		return this.id;
	}

	@Override
	public int updateId(int id) {
		int prev = this.id;
		this.id = id;
		return prev;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String updateName(String name) {
		String prev = this.name;
		this.name = name;
		this.states.modify(PersistType.SAVE);
		return prev;
	}

	@Override
	public Version version() {
		return this.version;
	}

	@Override
	public void updateVersion(String version) {
		this.version = new Version(version);
		this.states.modify(PersistType.SAVE);
	}

	@Override
	public void updateVersion(Version version) {
		this.version = version;
		this.states.modify(PersistType.SAVE);
	}

	@Override
	public void setVersion(String version) {
		this.version = new Version(version);
	}

	@Override
	public Group group() {
		return this.group;
	}

	@Override
	public void updateGroup(Group group) {
		this.group = group;
		this.states.modify(PersistType.SAVE);
	}

	@Override
	public void updateGroup(String group) {
		this.group = new Group(group);
		this.states.modify(PersistType.SAVE);
	}

	@Override
	public void setGroup(String group) {
		this.group = new Group(group);
	}

	@Override
	public String initialBusinessId() {
		return this.name + Util.DB._VS + Util.NEW_ID;
	}

	@Override
	public String businessId() {
		return this.name+Util.DB._VS+this.modelId;
	}

	@Override
	public String updateBusinessId(String newId) {
		String prev = this.name;
		this.name = newId;
		this.states.modify(PersistType.SAVE);
		return prev;
	}

	@Override
	public void setBusinessId(String newId) {
		this.name = newId;
	}

	@Override
	public String onlyBusinessId() {
		return this.name;
	}

	@Override
	public String businessKey() {
		return this.businessKey;
	}

	@Override
	public void updateBusinessKey(String businessKey) {
		this.businessKey = businessKey;
		this.states.modify(PersistType.SAVE);
	}

	@Override
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	@Override
	public CampInstance history() {
		return this.history;
	}

	@Override
	public void setHistory(CampInstance instance) {
		this.history = instance;
	}

	@Override
	public CampStates states() {
		return this.states;
	}

	@Override
	public Enum<?> status() {
		return this.status;
	}

	@Override
	public Enum<?> updateStatus(Enum<?> status) {
		Status prev = this.status;
		this.previousStatus = this.status;
		this.status = (Status) status;
		this.states.modify(PersistType.UPDATE);
		return prev;
	}

	@Override
	public Enum<?> updateStatus(String status) {
		Status prev = this.status;
		this.previousStatus = this.status;
		this.status = Status.valueOf(status);
		this.states.modify(PersistType.UPDATE);
		return prev;
	}

	@Override
	public void setStatus(Enum<?> status) {
		this.status = (Status) status;
	}

	@Override
	public void setStatus(String status) {
		this.status = Status.valueOf(status);
	}

	@Override
	public Enum<?> previousStatus() {
		return this.previousStatus;
	}

	@Override
	public void setPreviousStatus(Enum<?> status) {
		this.previousStatus = (Status) status;
	}

	@Override
	public void setPreviousStatus(String status) {
		this.previousStatus = Status.valueOf(status);
	}

	@Override
	public <T extends IsObjectInstance<T>> void cleanStatus(T object) {
		if(object.status().name().equals(Status.MODIFIED.name())) {
			object.setStatus(object.previousStatus());
			object.setPreviousStatus(Status.CLEAN);
		}
	}

	@Override
	public AttributeMap attributes() {
		return this.attributes;
	}

	@Override
	public void setAttributes(AttributeMap attributes) {
		this.attributes = attributes;
	}

	@Override
	public ProcessList processInstances() {
		return this.processes;
	}

	@Override
	public void addProcess(Process<Product> process) {
		process.states().modify();
		this.processes.add(process);
		this.states.modify();
	}

	@Override
	public void addProcesses(ProcessList processes) {
		for(Process<?> p: processes) {
			p.states().modify();
		}
		this.processes.addAll(processes);
		this.states.modify();
	}

	@Override
	public ProductProcess deleteProcess(String instanceId) {
		int count = 0;
		for (Process<?> p : this.processes) {
			if (p.instanceId().equals(instanceId)) {
				this.states.modify();
				return (ProductProcess) processes.remove(count);
			}
			count++;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProcessList processes() {
		return this.processes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProcessList processes(ProcessType type) {
		ProcessList pl = new ProcessList();
		for (Process<?> p : this.processes) {
			if (p.type().name().equals(type.name())) {
				pl.add(p);
			}
		}
		return pl;
	}

	@Override
	public void setProcesses(ProcessList pl) {
		this.processes = pl;
	}

	@Override
	public void notifyProcesses() {
		for (Process<?> p : this.processes) {
			p.notify();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void notifyProcesses(ProcessType type) {
		for (Process<?> p : this.processes) {
			if (p.type().name().equals(type.name())) {
				((Process<Product>) p).notify(this);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void notifyProcesses(Enum<?> event) {
		for (Process<?> p : this.processes) {
			((Process<Product>) p).notify(this, event);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void notifyProcesses(ProcessType type, Enum<?> event) {
		for (Process<?> p : this.processes) {
			if (p.type().name().equals(type.name())) {
				((Process<Product>) p).notify(this, event);
			}
		}
	}

	@Override
	public int getObjectId() {
		return this.id;
	}
	@Override
	public String getObjectBusinessId() {
		return this.name;
	}
	
	@Override
	public CampInstance getObjectHistory() {
		return this.history;
	}
	
	@Override
public int getRefId() {
		return 0;
	}
	
	@Override
	public String toJson() {
		return ProductInterface._toJson(this);
	}

	@Override
	public Product fromJson(String json) {
		return ProductInterface._fromJson(json);
	}

	public static enum productEvent {
		// Status for Internal/External product approval processes
		product_ready_for_internal_approval, 
		product_submitted_for_internal_approval, 
		product_not_internally_approved, 
		product_internally_approved, 
		product_internally_cancelled, 
		product_internal_approval_waiting, 
		product_internal_approval_halted, 
		product_internal_approval_started, 
		product_internal_approval_cancelled,
		product_ready_for_external_approval, 
		product_submitted_for_external_approval, 
		product_not_external_approved, 
		product_externally_approved, 
		product_externally_rejected, product_external_approval_waiting, product_external_approval_halted, product_external_approval_started, product_external_approval_cancelled,

		product_project_ready_for_proposal, product_project_proposed, product_project_proposal_approved, product_project_proposal_rejected, product_project_approval_waiting, product_project_proposal_halted, product_project_proposal_started, product_project_proposal_cancelled,

		product_project_budget_ready_for_approval, product_project_budget_proposed, product_project_budget_proposal_approved, product_project_budget_proposal_rejected, product_project_budgeted, product_project_budget_approval_waiting, product_project_budget_approval_halted, product_project_budget_approval_started, product_project_budget_approval_cancelled,

		product_project_initiated, product_project_planned, product_project_staffed, product_project_resourced, product_project_launched, product_project_in_review, product_project_halted, product_project_reviewed, product_project_cancelled, project_project_started, product_project_completed_successfully, product_project_completed_unsuccessfully, product_project_failed, product_project_ready_for_launch, product_project_waiting,

		product_in_review, product_submitted_to_review, product_reviewed, product_ready_for_review, product_review_waiting, product_review_halted, product_review_started, product_review_cancelled,

		product_in_analysis, product_analysis_completed, product_ready_for_analysis, product_analysis_halted, product_analysis_started, product_analysis_waiting, product_analysis_cancelled,

		product_in_design, product_designed, product_ready_for_design, product_design_halted, product_design_started, product_design_waiting, product_design_cancelled,

		product_in_development, product_developed, product_ready_for_development, product_development_halted, product_development_started, product_developemnt_waiting, product_development_cancelled,

		product_in_testing, product_tested_successfully, product_tested_with_errors, product_test_failed, product_ready_for_testing, product_testing_halted, product_testing_started, product_test_waiting, product_test_cancelled,

		product_in_acceptance, product_accepted, product_accepted_with_constraints, product_not_accepted, product_acceptance_waiting, product_acceptance_halted, product_acceptance_started, product_acceptance_cancelled, product_ready_for_accpetance,

		product_in_release, product_ready_for_release, product_release_halted, product_release_started, product_release_waiting, product_release_cancelled, product_released,

		product_in_production, product_ready_for_production, product_production_halted, product_production_started, product_production_waiting, product_production_cancelled, product_production_completed,

		product_production_inventory_low, product_production_inventory_ordered, product_production_inventory_shipped, product_production_inventory_out, product_production_inventory_waiting, product_production_inventory_delivered, product_production_inventory_ok,

		product_in_deployment, product_ready_for_deployment, product_deployment_halted, product_deployment_started, product_deployment_waiting, product_deployment_cancelled, product_deployed,

		product_in_marketing_campaign, product_ready_for_marketing_campaign, product_marketing_campaign_halted, product_marketing_campaign_started, product_marketing_campaign_waiting, product_marketing_campaign_cancelled, product_marketing_campaign_launched, product_marketing_campaign_ended_successfully, product_marketing_campaign_ended_unsuccessfully,

		product_in_sales_campaign, product_ready_for_sales_campaign, product_sales_campaign_halted, product_sales_campaign_started, product_sales_campaign_waiting, product_sales_campaign_cancelled, product_sales_campaign_launched, product_sales_campaign_ended_sucessfully, product_sales_campaign_unsuccessfully,

		product_ordered, product_order_started, product_order_halted, product_order_submitted, product_order_rejected, product_order_cancelled,

		product_order_in_production, product_order_ready_for_production, product_order_released_for_production,

		product_inventory_low, product_inventory_out, product_inventory_ordered, product_inventory_ok, product_inventory_shipped, product_inventory_delivered,

		product_in_shipping, product_ready_for_shipping, product_shipping_waiting, product_shipping_halted, product_shipping_started, product_shipping_cancelled, product_shipped, product_in_transit, product_delivered, product_shippment_cancelled, product_shippment_recalled, product_shippment_returned,

		product_order_ready_for_billing, product_order_in_billing, product_order_billing_cancelled, product_order_billing_waiting, product_order_billing_halted, product_order_billing_started, product_order_billed,

		product_order_ready_for_payment, product_order_in_payment, product_order_payment_started, product_order_payment_halted, product_order_payment_waiting, product_order_payment_completed, product_order_payment_received,

		product_in_service, product_ready_for_service, product_waiting_for_service, product_serivce_halted, product_service_started, product_service_cancelled, product_service_completed_sucessfully, product_service_completed_unsuccessful, product_service_request_submitted, product_service_request_approved, product_service_request_rejected,

		product_recalled, product_returned, product_leased,

		product_model_end_of_life_near, product_model_end_of_life_reached, product_model_end_of_life_extended, product_model_end_of_life
	}

	@Override
	public Message prepareMessage(String insanceId, Enum<?> message) {
		ProductMessage msg = ProductMessage.valueOf(message.name());
		ProductProcessMessage m = new ProductProcessMessage(msg, this);
		for(Process<?> p: processes){
			m.setProcessInstanceId(p.instanceId());
			m.setTenantId(p.tenantId());
		}
		return m;
	}
	@Override
	public MessageList prepareMessages(Enum<?> message) {
		ProductMessage msg = ProductMessage.valueOf(message.name());
		
		MessageList ml = new MessageList();
		
		for(Process<?> p: processes){
			ProductProcessMessage m = new ProductProcessMessage(msg, this);
			m.setProcessInstanceId(p.instanceId());
			m.setTenantId(p.tenantId());
			ml.add(m);
		}
		
		return ml;
	}
	@Override
	public Request<?> prepareRequest(Principal principal, RequestType type) {
		return new Request<Product>(this,principal,type);
	}


}
