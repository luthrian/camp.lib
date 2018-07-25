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
package com.camsolute.code.camp.lib.dao.rest;

import javax.ws.rs.PathParam;

public interface ValueServicePointInterface {

  public String create(String objectId, String type, String valueGroup, String value, int posX, int posY, int posZ, String selected);

  public String save(String objectId, String value);

  public String saveList(String objectId, String valueList);

  public String update(String objectId, String value);

  public String updateList(String objectId, String valueList);

  public String delete(String valueId, String valueType);

  public String delete(String value);

  public String deleteList(String valueList);

  public String deleteByObjectId(String objectId);

  public String load(String objectId, String valueId,@PathParam("type")String type);

  public String load(String objectId);

}
