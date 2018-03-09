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

public interface DaoInterface<T extends IsObjectInstance<T>> {

	public T loadById(int id, boolean log);

    public  T loadByBusinessId(String businessId, boolean log);

    public <E extends ArrayList<T>> E loadListByBusinessKey(String businessKey, boolean log);

    public  T save(T instance, boolean log);

    public <E extends ArrayList<T>> E saveList(E instanceList, boolean log);

    public  T update(T instance, boolean log);

    public <E extends ArrayList<T>> E updateList(E instanceList, boolean log);

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
