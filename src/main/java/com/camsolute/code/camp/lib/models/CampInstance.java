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
package com.camsolute.code.camp.lib.models;

import java.sql.Timestamp;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.models.InstanceId;
import com.camsolute.code.camp.lib.utilities.Util;

/**
 * The <code>CampInstance</code> object encapsulates a unique temporal aspect of an object instance
 * at a point in its life cycle.
 * ( together with its process related status aspects.)
 * The temporal aspect of an object instance is denoted by a unique identifier - the objects
 * <code>InstanceId</code> - which changes each time an object is modified or changes state, and is
 * then persisted.
 * (The process status aspect is relevant to any process connected to a business object.)
 * A <code>CampInstance</code> object therefore provides access to a representation of an object at
 * the time it was persisted.
 * In order to be able to navigate the various temporal aspects of an object the
 * <code>CampInstance</code> object contains an object's initial and current <code>InstanceId</code>.
 * NOTE: These aspects are in-transient in nature and must therefore be persisted so that their current 
 * (and past) values can exist across both inactive and active life-cycle periods of the business object
 * they are associated with.
 * Inactive life-cycle periods denote those time periods in which only a persisted 
 * representation of current (and past) object states exist.
  *Active life-cycle periods of an object denote those time periods in which a runtime
 * representation of the object (object instance) exists within the system.
* 
 * @author Christopher Campbell
 */
public class CampInstance implements CampInstanceInterface {

	/**
	 * Status common to all object instances.<br>
	 * These are:<br><br>
   *
	 * CREATED := the object instance is newly created and has not been submitted to any process
   * (can trigger process notification)<br>
   *
	 * SUBMITTED := the object instance has been submitted to a process
   * (must trigger process notification)<br>
   *
	 * REJECTED := the object instance has been rejected by a process
   * (must trigger process notification)<br>
   *
	 * UPDATED := object instance data has been updated
   * (must trigger process notification)<br>
   *
	 * CANCELED := the main object instance process has been canceled
   * (must trigger process notification)<br>
   *
	 * FULFILLED := the main object instance process has completed successfully
   * (must trigger process notification)<br>
   *
	 * MODIFIED := [system use] object instance data has been modified,
   * the system needs to be informed,
   * the status at modification time is stacked (previous status)<br>
   *
	 * CLEAN := [system use] the system has acted on modified status,
   * the previous status is popped from stack and status CLEAN is pushed to stack.<br>
   *
	 * DIRTY := [system use] a displayable object instance's display needs to be updated<br>
   *
	 * @author Christopher Campbell
	 *
	 */
	public static enum Status {
		CREATED,
		SUBMITTED,
		REJECTED,
		UPDATED,
		CANCELED,
		FULFILLED,
		CLEAN,
		MODIFIED;
	}

	private int objectRefId = Util.NEW_ID;
	
  private InstanceId id;

  private InstanceId initialId;

  private InstanceId currentId;

  private Timestamp timestamp = Util.Time.timestamp();
  
  private Timestamp endOfLife = Util.Time.timestamp();
  
  private Timestamp date = Util.Time.timestamp();
  
  public CampInstance() {

      this.timestamp = Util.Time.timestamp();

    this.id = new InstanceId();

    InstanceId initialId = null;

    InstanceId currentId = null;

    currentId = new InstanceId(this.id.id());

    initialId = new InstanceId(this.id.id());

    this.initialId = initialId;

    this.currentId = currentId;


  }

  public CampInstance(String instanceId, String currentInstanceId, String initialInstanceId) {

    this.timestamp = Util.Time.timestamp();

    this.id = new InstanceId(instanceId);

    this.initialId = new InstanceId(initialInstanceId);

    this.currentId = new InstanceId(currentInstanceId);
    
  }

  public CampInstance( InstanceId instanceId, InstanceId currentInstanceId, InstanceId initialInstanceId) {
    this.timestamp = Util.Time.timestamp();
    this.id = instanceId;
    this.initialId = initialInstanceId;
    this.currentId = currentInstanceId;
  }

  public int objectRefId() {
  	return this.objectRefId;
  }
  
  public void setObjectRefId(int id) {
  	this.objectRefId = id;
  }
  
  public InstanceId id() {
    return this.id;
  }

  public InstanceId initialId() {
    return this.initialId;
  }

  public InstanceId currentId() {
    return this.currentId;
  }

  public InstanceId updateInstance() {
    this.initialId = new InstanceId(id.id());
    this.id = new InstanceId();
    this.currentId = new InstanceId(id.id());
    return id;
  }

  @Override
  public boolean isFirst() {
    return (initialId.id().equals(id.id()));
  }

  @Override
  public boolean isCurrent() {
    return (currentId.id().equals(id.id()));
  }

  @Override
  public Timestamp timestamp() {
    return this.timestamp;
  }

  @Override
  public void timestamp(Timestamp ts) {
    this.timestamp = ts;
  }

  public Timestamp stamptime() {
    this.timestamp = Util.Time.timestamp();
    return this.timestamp;
  }

  @Override
  public Timestamp date() {
    return this.date;
  }

  @Override
  public void date(Timestamp d) {
    this.date = d;
  }

  
  @Override
  public Timestamp endOfLife() {
    return this.endOfLife;
  }

  @Override
  public void endOfLife(Timestamp eol) {
    this.endOfLife = eol;
  }

  public CampInstance clone() {
  	return CampInstanceInterface.clone(this);
  }
  
  @Override
	public String toString() {
      return CampInstanceInterface._toJson(this);
	}

	@Override
	public String toJson() {
      return CampInstanceInterface._toJson(this);
	}

	@Override
	public CampInstance fromJson(String json) {
      return CampInstanceInterface._fromJson(json);
	}
	
}
