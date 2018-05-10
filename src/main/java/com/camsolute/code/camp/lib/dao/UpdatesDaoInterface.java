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
package com.camsolute.code.camp.lib.dao;

import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.HasResultSetToInstance;
import com.camsolute.code.camp.lib.utilities.Util;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.dao.database.DBDaoInterface;
import com.camsolute.code.camp.lib.models.Model;

/**
 * Objects that honor this contract provide access to CAMP system updates tables.
 * Within the CAMP framework updates tables provide a channel for objects that honor the IsObjectInstance contract to communicate changes to interested observers.
 * The CAMP framework does not provide a service for observers to register and automatically be informed when objects of interest publish changes there.
 * Observers are identified by a target identifier string. This identifier must be used when an object registers in an updates table.  
 * Observers therefore must do poll for updates table at their own discretion. Observers must also deregister an object from an updates table.
 * This will require some form of coordination if multiple observers of an object exist (such coordination could be achieved by implementing a subscription service which would handle polling and publishing changes to observers that have subscribed to the service). 
 * 
 *       
 * @author Christopher Campbell
 *
 * @param <T> An object that must honor the IsObjectInstance contract. Example: <code>OrderDao implements UpdatesDaoInterface&lt;Order&gt;</code>
 */
public interface UpdatesDaoInterface<T extends IsObjectInstance<T>> {

    public <E extends ArrayList<T>> E loadUpdates(String businessKey, String target, boolean log);

    public <E extends ArrayList<T>> E loadUpdatesByKey(String businessKey, boolean log);

    public <E extends ArrayList<T>> E loadUpdatesByTarget(String target, boolean log);

    public  T loadUpdate(T instance, String businessKey, String target, boolean log);

    public  int addToUpdates(T instance, String businessKey, String target, boolean log);

    public <E extends ArrayList<T>> int addToUpdates(E instanceList, String businessKey, String target, boolean log);

    public int deleteAllFromUpdates(String businessKey, String target, boolean log);

  	public int deleteFromUpdatesByKey(String businessKey, boolean log);

  	public int deleteFromUpdatesByTarget(String target, boolean log);

    public int deleteFromUpdates(String instanceId, String businessKey, String target, boolean log);

    public <E extends ArrayList<T>> int deleteFromUpdates(E instanceList, String businessKey, String target, boolean log);

}
