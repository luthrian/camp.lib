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
 * 
 */
package com.camsolute.code.camp.lib.types;

import java.util.ArrayList;

import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.Value;
import com.camsolute.code.camp.lib.models.ValueInterface;

/**
 * A wrapper class for the <code>ArrayList</code>.
 *
 * @author Christopher Campbell
 *
 */
public class CampList extends Attribute<ListValue> implements CampListInterface {
	private int	size					= 0;
	private int	selectedIndex	= 0;
	private boolean hasSelected = false;

	public CampList() {
		super(null, AttributeType._list, null);
	}

	public CampList(String name) {
		super(name, AttributeType._list, null);
	}

	public CampList(String name, String defaultValue) {
		super(name, AttributeType._list, defaultValue);
	}

	public CampList(String name, ArrayList<Attribute<?>> defaultValue){
      super(name, AttributeType._list, ValueInterface._toListJson(defaultValue)) ;
	}

	public CampList(String name, String defaultValue, ArrayList<Attribute<?>> value) {
		super(name, AttributeType._list, defaultValue);
		this.setValue(new ListValue(value));
		this.size(value.size());
	}

	public CampList(String name, String defaultValue, String value) {
		super(name, AttributeType._list, defaultValue);
		this.setValue((ListValue) ValueInterface._fromJson(value));
		this.size(this.value().value().size());
	}

	public void add(int index, Attribute<?> element) {
		this.value().value().add(index, element);
		size++;
	}

	public boolean add(Attribute<?> element) {
		boolean ok = this.value().value().add(element);
		// if(ok)
		size++;
		return ok;
	}

	public Attribute<?> get(int index) {
		Attribute<?> element = this.value().value().get(index);
		return element;
	}

	public Attribute<?> set(int index, Attribute<?> replaceWith) {
		Attribute<?> element = this.value().value().set(index, replaceWith);
		return element;
	}

	public boolean remove(Attribute<?> element) {
		boolean ok = this.value().value().remove(element);
		// if(ok)
		size--;
		return ok;
	}

	public Attribute<?> remove(int index) {
		Attribute<?> element = this.value().value().remove(index);
		size--;
		return element;
	}

	public boolean addAll(CampList pa) {
		boolean ok = this.value().value().addAll(pa.value().value());
		size(this.value().value().size());
		return ok;
	}

	public boolean addAll(int index, CampList pa) {
		this.value().value().addAll(index, pa.value().value());
		size(this.value().value().size());
		return true;
	}

	public int size() {
		return size;
	}

	public void size(int newSize) {
		this.size = newSize;
	}

	public Attribute<?> selected() {
		return this.value().value().get(selectedIndex);
	}

	public Attribute<?> select(int index) {
		this.selectedIndex = index;
		return selected();
	}

	public Attribute<?> select(Attribute<?> value) {
		int count = 0;
		for (Attribute<?> element : this.value().value()) {
			if (element.equals(value)) {
				selectedIndex = count;
				element.value().select();
			}
			count++;
		}
		if (count >= this.value().value().size()) {
			this.value().value().add(value);
			selectedIndex = count;
		}
		return selected();
	}

	public boolean hasSelected() {
		return hasSelected;
	}

	public int selectedIndex() {
		return this.selectedIndex;
	}

	@Override
	public void setSelectedIndex(int index) {
		this.selectedIndex = index;
		this.hasSelected = true;
		int count = 0;
		for(Attribute<?> a: value().value()) {
			a.value().deselect();
			if(index == count) {
				a.value().select();
			}
		}
	}

	@Override
	public ListValue valueFromString(String json) {
		return (ListValue) ValueInterface._fromJson(json);
	}

	@Override
	public String toJson() {
		return CampListInterface._toJson(this);
	}

	@Override
	public CampList fromJson(String json) {
		return CampListInterface._fromJson(json);
	}

}
