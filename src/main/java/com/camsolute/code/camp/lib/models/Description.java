package com.camsolute.code.camp.lib.models;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.product.Product;
import com.camsolute.code.camp.lib.utilities.Util;

public class Description implements DescriptionInterface {
	
	public static enum Status {
		CREATED,
		MODIFIED,
		CLEAN,
		DIRTY,
		PRODUCTION,
		COMPLETED,
		INREVIEW,
		REVIEWED,
		SUBMITTED,
		APPROVED,
		REJECTED,
		RELEASED,
		PUBLISHED,
		RETIRED
	}
	private int id = Util.NEW_ID;
	private int productId = Util.NEW_ID;
	private String title;
	private String description;
	private String businessId;
	private String businessKey;
	private Group group;
	private Version version;
	private CampStates states = new CampStates();
	private CampInstance history = new CampInstance();
	private Status status = Status.CREATED;
	private Status previousStatus = Status.CLEAN;
	private Product product;
	
	public Description(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public Description(int id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}
	
	public Description(String title, String description, String businessId, String businessKey,String group, String version) {
		this.title = title;
		this.description = description;
		this.businessId = businessId;
		this.businessKey = businessKey;
		this.group = new Group(group);
		this.version = new Version(version);
	}
	
	public Description(int id, String title, String description, String businessId, String businessKey,String group, String version) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.businessId = businessId;
		this.businessKey = businessKey;
		this.group = new Group(group);
		this.version = new Version(version);
	}
	
	public int id() {
		return this.id;
	}
	
	public int updateId(int id) {
		int prev = this.id;
		this.id = id;
		return prev;
	}
	
	public String title() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String updateTitle(String title) {
		String prev = this.title;
		this.title = title;
		this.states.modify();
		return prev;
	}
	
	public String description() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void updateDescription(String description) {
		this.description = description;
		this.states.modify();
	}

	@Override
	public Version version() {
		return this.version;
	}

	@Override
	public void updateVersion(String version) {
		this.version = new Version(version);
		this.states.modify();
	}

	@Override
	public void updateVersion(Version version) {
		this.version = version;
		this.states.modify();
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
		this.states.modify();
	}

	@Override
	public void updateGroup(String group) {
		this.group = new Group(group);
		this.states.modify();
	}

	@Override
	public void setGroup(String group) {
		this.group = new Group(group);
	}

	@Override
	public String initialBusinessId() {
		return this.businessId+Util.DB._VS+Util.NEW_ID+Util.DB._VS+this.title;
	}

	@Override
	public String businessId() {
		return this.businessId+Util.DB._VS+this.id+Util.DB._VS+this.title;
	}

	@Override
	public String updateBusinessId(String newId) {
		String prev = this.businessId;
		this.businessId = newId;
		this.states.modify();
		return prev;
	}

	@Override
	public void setBusinessId(String newId) {
		this.businessId = newId;
	}

	@Override
	public String onlyBusinessId() {
		return this.businessId;
	}

	@Override
	public String businessKey() {
		return this.businessKey;
	}

	@Override
	public void updateBusinessKey(String businessKey) {
		this.businessKey = businessKey;
		this.states.modify();
	}

	@Override
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
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
		this.status = (Status) status;
		this.states.modify();
		return prev;
	}

	@Override
	public Enum<?> updateStatus(String status) {
		Status prev = this.status;
		this.status = Status.valueOf(status);
		this.states.modify();
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
		return previousStatus;
	}

	@Override
	public void setPreviousStatus(Enum<?> status) {
		this.status = (Status) status;
	}

	@Override
	public void setPreviousStatus(String status) {
		this.status = Status.valueOf(status);
	}

	@Override
	public <T extends IsObjectInstance<T>> void cleanStatus(T object) {
		if(this.status.name().equals(Status.MODIFIED.name())){
			this.status = this.previousStatus;
			this.previousStatus = Status.CLEAN;
		}
	}

	@Override
	public String toJson() {
		return DescriptionInterface._toJson(this);
	}

	@Override
	public Description fromJson(String json) {
		return DescriptionInterface._fromJson(json);
	}

	@Override
	public int productId() {
		return this.productId;
	}

	@Override
	public void setProductId(int id) {
		this.productId = id;
	}

	@Override
	public Product product() {
		return this.product;
	}

	@Override
	public Product updateProduct(Product product) {
		Product prev  = this.product;
		this.product = product;
		this.productId = product.id();
		this.businessId = product.businessId();
		this.states.modify();
		return prev;
	}

	@Override
	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String name() {
		return this.title;
	}

	@Override
	public String updateName(String name) {
		return updateTitle(name);
	}

	@Override
	public void setName(String name) {
		this.title = name;
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
	public int getObjectId() {
		return this.id;
	}

	@Override
	public String getObjectBusinessId() {
		return this.businessId;
	}

	@Override
	public CampInstance getObjectHistory() {
		return this.history;
	}

	@Override
	public int getRefId() {
		return 0;
	}
}
