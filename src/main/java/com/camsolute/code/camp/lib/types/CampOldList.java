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

/**
 * A wrapper class for the <code>ArrayList<?></code> for use with the UI.
 * 
 * @author Christopher Campbell
 *
 */
public class CampOldList<T>  extends CampOldType<ArrayList<T>>{
	private int size = 0;
	private ArrayList<T> list;
	private int selectedIndex = 0;
	
	public CampOldList(){
		this.list = new ArrayList<T>();
		this.selectedIndex = 0;
	}
	
	public CampOldList(ArrayList<T> list){
		this.list = new ArrayList<T>();
		this.list.addAll(list);
		size(list.size());
	}
	
	public CampOldList<T> defaultInstance(){
		return new CampOldList<T>();
	}


	public void add(int index,T element){
		this.list.add(index, element);
		size++;
	}
	public boolean add(T element){
		boolean ok = this.list.add(element);
//		if(ok)
		size++;
		return ok;
	}
	public T get(int index){
		T element = this.list.get(index);
		return element;
	}
	public T set(int index,T replaceWith){
		T element = this.list.set(index,replaceWith);
		return element;
	}
	public boolean remove(T element){
		boolean ok = this.list.remove(element);
//		if(ok)
			size--;
		return ok;
	}

	public T remove(int index){
		T element = this.list.remove(index);
		size--;
		return element;
	}
	
	public boolean addAll(CampOldList<T> pa){
		boolean ok = true;
		for(int i = 0;i<pa.size;i++){
			ok = this.list.add(pa.get(i));
		}
//		if(ok)
		size(this.list.size());
		return ok;
	}

	public boolean addAll(int index, CampOldList<T> pa){
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

	public T selected(){
		if(this.list == null || this.list.size()<1) return null;
		return this.list.get(selectedIndex);
	}
	public T select(int index){
		this.selectedIndex = index;
		return selected();
	}
	
	public T select(T value){
		int count = 0;
		for(T element:list){
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
	protected ArrayList<T> me() {
		return list;
	}

	@Override
	protected ArrayList<T> me(ArrayList<T> value) {
		ArrayList<T> prev = this.list;
		this.list = value;
		return prev;
	}

}
