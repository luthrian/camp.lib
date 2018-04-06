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
/**
 * ***************************************************************************** Copyright (C) 2017
 * Christopher Campbell (campbellccc@gmail.com)
 *
 * <p>This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * <p>You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 *
 * <p>Contributors: Christopher Campbell (campbellccc@gmail.com) - all code prior and post initial
 * release ****************************************************************************
 */
package com.camsolute.code.camp.lib.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.contract.HasModelLifeCycle;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.data.CampSQL;
import com.camsolute.code.camp.lib.models.CampInstance.Status;
import com.camsolute.code.camp.lib.models.CampStatesInterface.IOAction;
import com.camsolute.code.camp.lib.models.product.Product;
import com.camsolute.code.camp.lib.utilities.Util;
import com.camsolute.code.camp.lib.utilities.Util.DB.dbActionType;

import static com.camsolute.code.camp.lib.utilities.Util.*;

public class Model implements ModelInterface {

  public static enum Status {
    CREATED,
    INDESIGN,
    INDEVELOPMENT,
    SUBMITTED,
    INREVIEW,
    REJECTED,
    RELEASED,
    RECALLED,
    DECOMISSIONED,
    MODIFIED,
    LIFEEND,
    DIRTY,
    CLEAN;
  }

  private int id = Util.NEW_ID;
  private int productId = 0;
  /**
   * the business identifier for this model
   */
  private String name;
  private Timestamp releaseDate = Timestamp.from(Instant.now());
  private Timestamp endOfLife = Time.timestamp();
  private String businessKey;
  private Version version;
  private Group group;

	private Enum<?> previousStatus = Status.CLEAN;
	
	private Enum<?> status = Status.CREATED;
	
  private CampInstance history = new CampInstance();
  
  private CampStates states = new CampStates();

  private Product product;

  public Model(String name) {
    this.name = name;
  }

  public Model(int id, String name, String version, String group) {
    this.id = id;
    this.name = name;
    this.version = new Version(version);
    this.group = new Group(group);
  }

  public Model(int id, String name, Version version, Group group) {
    this.id = id;
    this.name = name;
    this.version = version;
    this.group = group;
  }

  public Model(String name, String version, String group) {
    this.name = name;
    this.version = new Version(version);
    this.group = new Group(group);
  }

  public Model(String name, Version version, Group group) {
    this.name = name;
    this.version = version;
    this.group = group;
  }

  public Model(int id, String name, Timestamp releaseDate, Timestamp endOfLife, Version version, Group group) {
    this.id = id;
    this.name = name;
    this.releaseDate = releaseDate;
    this.endOfLife = endOfLife;
    this.version = version;
    this.group = group;
  }


  @Override
  public int id() {
    return id;
  }

  @Override
  public int updateId(int id) {
    return this.id = id;
  }

  public String name() {
    return name;
  }
  
	@Override
  public String updateName(String name) {
		String prev = this.name;
		this.name = name;
		this.states.modify();
		return prev;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
  	
  @Override
  public Timestamp releaseDate() {
    return this.releaseDate;
  }

  @Override
  public void updateReleaseDate(Timestamp date) {
    this.releaseDate = date;
    this.states().modify();
  }

  protected void setReleaseDate(Timestamp date) {
  	this.releaseDate = date;
  }
  
  @Override
  public Timestamp endOfLife() {
    return this.endOfLife;
  }

  @Override
  public void updateEndOfLife(Timestamp eol) {
    this.endOfLife = eol;
    this.states().modify();
  }

  protected void setEndOfLife(Timestamp eol) {
  	this.endOfLife = eol;
  }
  @Override
  public Version version() {
    return version;
  }

  public void updateVersion(String version) {
  	this.version = new Version(version);
  	this.states().modify();
  }
  
  public void updateVersion(Version version) {
  	this.version = version;
  	this.states().modify();
  }


  public void setVersion(String version) {
  	this.version = new Version(version);
  }
  
  protected void setVersion(Version version) {
  	this.version = version;
  }

  @Override
  public Product product() {
    return this.product;
  }

  @Override
  public Product updateProduct(Product product) {
  	Product prev = this.product;
    this.product = product;
    this.productId = product.id();
    return prev;
  }

  @Override
  public void setProduct(Product product) {
    this.product = product;
    this.productId = product.id();
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
  public CampStates states() {
    return states;
  }

  @Override
  public CampInstance history() {
    return history;
  }

	@Override
  public void setHistory(CampInstance instance) {
    this.history = instance;
  }

  public String print() {
    return ModelInterface._toJson(this);
  }
  
  @Override
  public String businessId() {
    return this.name + Util.DB._NS + this.id;
  }

  @Override
  public String updateBusinessId(String name) {
    String prev = this.name;
    this.name = name;
    this.states().modify();
    return prev;
  }
  
  public void setBusinessId(String name) {
  	this.name = name;
  }

  @Override
  public String onlyBusinessId() {
    return this.name;
  }

  @Override
  public String initialBusinessId() {
    return this.name + Util.DB._NS + Util.NEW_ID; // initial value id = Util.NEW_ID
  }

	@Override
	public String businessKey() {
		return this.businessKey;
	}
	
	@Override
	public void updateBusinessKey(String businessKey) {
		this.businessKey = businessKey;
		this.states().modify();
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	@Override
	public String toJson() {
		return ModelInterface._toJson(this);
	}

	@Override
	public Model fromJson(String json) {
		return ModelInterface._fromJson(json);
	}

	@Override
	public Group group() {
		return group;
	}
	
	protected void setGroup(Group group) {
		this.group = group;
	}

	public void setGroup(String group) {
		this.group = new Group(group);
	}
	@Override
	public void updateGroup(Group group) {
		this.group = group;
		this.states().modify();
	}

	@Override
	public void updateGroup(String group) {
		this.group = new Group(group);
		this.states().modify();
	}

  @Override
  public Enum<?> status() {
      return this.status;
  }

  @Override
  public Enum<?> updateStatus(Enum<?> status) {
      this.previousStatus = this.status;
      this.status = status;
      return this.previousStatus;
  }

  @Override
  public Enum<?> updateStatus(String status) {
      this.previousStatus = this.status;
      this.status = Enum.valueOf(Status.class, status);
      return this.previousStatus;
  }


  @Override
  public void setStatus(Enum<?> status) {
      this.status = status;
  }

  @Override
  public void setStatus(String status) {
      this.status = Enum.valueOf(Status.class, status);
  }

  @Override
  public Enum<?> previousStatus() {
      return this.previousStatus;
  }

  @Override
  public void setPreviousStatus(Enum<?> status) {
     this.previousStatus = status;
  }

  @Override
  public void setPreviousStatus(String status) {
     this.previousStatus = Enum.valueOf(Status.class, status);
  }

	public <T extends IsObjectInstance<T>> void cleanStatus(T object) {
  	if(object.status().name().equals(Status.MODIFIED.name())) {
  		object.setStatus(object.previousStatus());
  		object.setPreviousStatus(Status.CLEAN);
  	}
  }

	@Override
	public int getObjectId() {
		return this.productId;
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
	
	public Model clone() {
		return ModelInterface._fromJson(this.toJson());
	}
	public void mirror(Model object) {
		this.id = object.id();
		this.productId = object.productId();
		this.name = object.onlyBusinessId();
		this.releaseDate = object.releaseDate();
		this.endOfLife = object.endOfLife();
		this.businessKey = object.businessKey();
		this.version = object.version();
		this.group = object.group();
	}
}
