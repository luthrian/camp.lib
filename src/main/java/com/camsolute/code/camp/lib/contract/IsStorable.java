/*******************************************************************************
 * Copyright (C) 2017 Christopher Campbell (campbellccc@gmail.com)
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
 * 	Christopher Campbell (campbellccc@gmail.com) - all code prior and post initial release
 ******************************************************************************/
package com.camsolute.code.camp.lib.contract;


public interface IsStorable {
	public boolean wasLoaded();
	public void loaded();
	public void loaded(boolean loaded);
	public boolean wasSaved();
	public void saved();
	public void saved(boolean saved);
	public void isNew(boolean isNew);
	public boolean isNew();
	public boolean readyToSave();
//	public <E extends Enum<?>,T extends HasNumber> T updateState(E messageName, AbstractDao<T> dao);
//
//	public <T extends HasNumber> CampList<T> addToUpdatedList(String target, CampList<T> o,AbstractDao<T> dao);
//
//	public <T extends HasNumber> T addToUpdatedList(String target,AbstractDao<T> dao);
//
//	public <T extends HasNumber> void deleteAllFromUpdatedList(String target,AbstractDao<T> dao);
//
//	public <T extends HasNumber> void deleteFromUpdatedList(String target,AbstractDao<T> dao);

}
