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
package com.camsolute.code.camp.lib.types;

import com.camsolute.code.camp.core.interfaces.HasStatusChange;
import com.camsolute.code.camp.core.interfaces.IsDisplayable;
import com.camsolute.code.camp.core.interfaces.IsStorable;
import com.camsolute.code.camp.models.business.Order;

public class IOStates implements IsStorable, IsDisplayable, HasStatusChange{
	
	private Enum<?> previousStatus;
	private Enum<?> status;
	
	private boolean loaded = false;
	private boolean saved = false;
	protected boolean dirty = false;
	private boolean modified = false;
	private boolean isNew = true;
	
	@Override
	public boolean isDirty() {
		return dirty;
	}
	@Override
	public void dirty() {
		dirty = true;
	}
	@Override
	public boolean wasLoaded() {
		return loaded;
	}
	@Override
	public void loaded() {
		saved = false;
		loaded = true;
	}
	@Override
	public void loaded(boolean loaded) {
		this.loaded = loaded;
	}
	@Override
	public boolean wasSaved() {
		return saved;
	}
	@Override
	public void saved() {
		loaded = false;
		saved = true;
	}
	@Override
	public void saved(boolean saved) {
		this.saved = saved;
	}
	@Override
	public boolean isModified() {
		return modified;
	}
	@Override
	public void modify() {
		modified = true;
	}
	@Override
	public void modify(boolean modified) {
		this.modified = modified;	
	}
	@Override
	public void isNew(boolean isNew) {
		this.isNew = isNew;
	}
	@Override
	public boolean isNew() {
		return this.isNew;
	}
	
	public String print() {
		return "loaded:"+this.loaded+",saved:"+this.saved+",modified:"+this.modified+",isNew:"+this.isNew+",dirty:"+this.dirty
				+",status:"+this.status.name()+",previousStatus:"+this.previousStatus.name();
	}
	
	@Override
	public String toString() {
		return print();
	}
	
	public static IOStates fromString(String stringValue) {
		IOStates io = new IOStates();
		String[] vals = stringValue.split(",");
		if(vals.length<1) return null;
		for(String val: vals) {
			String[] v = val.split(":");
			if(v.length<1)return null;
			switch(v[0]) {
			case "loaded":
				io.loaded(Boolean.valueOf(v[1]));
				break;
			case "saved":
				io.saved(Boolean.valueOf(v[1]));
				break;
			case "modified":
				io.modify(Boolean.valueOf(v[1]));
				break;
			case "isNew":
				io.isNew(Boolean.valueOf(v[1]));
				break;
			case "dirty":
				if(Boolean.valueOf(v[1]))io.dirty();
				break;
			case "status":
				io.setStatus(io.status.valueOf(io.status.getClass(), v[1]));
				break;
			case "previousStatus":
				io.setStatus(io.previousStatus.valueOf(io.previousStatus.getClass(), v[1]));
				break;
			}
		}
		return io;
	}

	@Override
	public boolean readyToSave() {
		return true;
	}

	//@Override
	public Enum<?> status() {
		return this.status;
	}

	//@Override
	public Enum<?> updateStatus(Enum<?> status) {
		Enum<?> prev = this.status;
		this.status = status;
		modify();
		return prev;
	}

	//@Override
	public Enum<?> previousStatus() {
		return this.previousStatus;
	}

	//@Override
	public Enum<?> previousStatus(Enum<?> status) {
		return this.previousStatus = status;
	}

	//@Override
	public Enum<?> setStatus(Enum<?> status) {
		Enum<?> prev = this.status;
		this.status = status;
		return prev;
	}

	//@Override
	public Enum<?> cleanStatus() {
		if(this.status.equals(Order.OrderStatus.MODIFIED)) {
			this.status = this.previousStatus;
			this.previousStatus = Order.OrderStatus.CLEAN;
		}
		return this.status;
	}

}
