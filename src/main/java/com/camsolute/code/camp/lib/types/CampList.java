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
/**
 * 
 */
package com.camsolute.code.camp.lib.types;

import java.util.ArrayList;

import com.camsolute.code.camp.lib.Attribute;
import com.camsolute.code.camp.lib.utilities.Util;

/**
 * A wrapper class for the <code>ArrayList<?></code>.
 * 
 * @author Christopher Campbell
 *
 */
public class CampList extends Attribute<ArrayList<Attribute<?>>>{
	private int size = 0;
	private ArrayList<Attribute<?>> list;
	private int selectedIndex = 0;
	
	private String attributeGroup = null;
	private int attributePosition = 0;
	
	private String attributeBusinessId = null;

	@Override
	public String attributeBusinessId() {
		return attributeBusinessId+Util.DB._NS+id();
	}

	@Override
	public String attrbuteBusinessId(String id) {
		String prev = this.attributeBusinessId;
		this.attributeBusinessId = id;
		return prev;
	}

	@Override
	public String onlyAttributeBusinessId() {
		return this.attributeBusinessId;
	}

	@Override
	public String initialAttributeBusinessId() {
		return attributeBusinessId+Util.DB._NS+0;
	}

	public CampList(){
		super(null, AttributeType._list, null);
		this.list = new ArrayList<Attribute<?>>();
		this.selectedIndex = 0;
	}
	
	public CampList(String name){
		super(name, AttributeType._list, null);
		this.list = new ArrayList<Attribute<?>>();
		this.selectedIndex = 0;
	}
	
	public CampList(String name,ArrayList<Attribute<?>> list){
		super(name, AttributeType._list, null);
		this.list = new ArrayList<Attribute<?>>();
		this.list.addAll(list);
		size(list.size());
	}
	
	public CampList defaultInstance(){
		return new CampList(name());
	}


	public void add(int index,Attribute<?> element){
		this.list.add(index, element);
		size++;
	}
	public boolean add(Attribute<?> element){
		boolean ok = this.list.add(element);
//		if(ok)
		size++;
		return ok;
	}
	public Attribute<?> get(int index){
		Attribute<?> element = this.list.get(index);
		return element;
	}
	public Attribute<?> set(int index,Attribute<?> replaceWith){
		Attribute<?> element = this.list.set(index,replaceWith);
		return element;
	}
	public boolean remove(Attribute<?> element){
		boolean ok = this.list.remove(element);
//		if(ok)
			size--;
		return ok;
	}

	public Attribute<?> remove(int index){
		Attribute<?> element = this.list.remove(index);
		size--;
		return element;
	}
	
	public boolean addAll(CampList pa){
		boolean ok = true;
		for(int i = 0;i<pa.size;i++){
			ok = this.list.add(pa.get(i));
		}
//		if(ok)
		size(this.list.size());
		return ok;
	}

	public boolean addAll(int index, CampList pa){
		for(int i = 0;i<pa.size;i++){
			this.list.add(index+i, pa.get(i));
		}
		size(this.list.size());
		return true;
	}

	public int size() {
		return size;
	}

	private void size(int newSize) {
		this.size = newSize;
	}

	public Attribute<?> selected(){
		return this.list.get(selectedIndex);
	}
	public Attribute<?> select(int index){
		this.selectedIndex = index;
		return selected();
	}
	
	public Attribute<?> select(Attribute<?> value){
		int count = 0;
		for(Attribute<?> element:list){
			if(element.equals(value)){
				selectedIndex = count;
			}
			count++;
		}
		if(count >= list.size()){
			list.add(value);
			selectedIndex = count;
		}
		return selected();
	}
	public boolean hasSelected() {
		return (selectedIndex > 0);
	}
	@Override
	public ArrayList<Attribute<?>> value() {
		return list;
	}

	@Override
	public ArrayList<Attribute<?>> value(ArrayList<Attribute<?>> value) {
		ArrayList<Attribute<?>> prev = this.list;
		this.list = value;
		return prev;
	}

	@Override
	public Attribute<ArrayList<Attribute<?>>> valueFromString(String value) {
		// TODO Auto-generated method stub
//		value(AttributeTypeDao.instance().loadValueList(value.split(",")).value());
//		return this;
		return null;
	}

	@Override
	public String attributeGroup() {
		return this.attributeGroup;
	}

	@Override
	public String attributeGroup(String group) {
		String prev = this.attributeGroup;
		this.attributeGroup = group;
		return prev;
	}

	@Override
	public int attributePosition() {
		return this.attributePosition;
	}

	@Override
	public int attributePosition(int position) {
		int prev = this.attributePosition;
		this.attributePosition = position;
		return prev;
	} 

}
