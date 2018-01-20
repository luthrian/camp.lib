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

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.camsolute.code.camp.lib.Attribute;
import com.camsolute.code.camp.lib.utilities.Util;

/**
 * The <code>CampSet</code> object represents a list of item names of which a subset of them are <code>selected</code>.
 * </br> The order of the item names is irrelevant.
 * @author 	Christohper Campbell
 * @see    	java.lang.String
 * @version 0.1
 * @since 	08.07.2017  
 */
public class CampSet extends Attribute<String>{

	public static final boolean _DEBUG = true;
	
	private static final Logger LOG = LogManager.getLogger(CampSet.class);
	private static String fmt = "[%15s] [%s]";
	
	private String[] values;	
	private String[] selected;
	
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

	public CampSet(String string){
		super(null, AttributeType._set, null);
		CampSet e = CampSet.fromString(string);
		this.values = e.values();
		this.selected = e.selected();
		name(e.name());
	}
	
	/**
	 * If <code>CampSet</code> is instantiated with only the <code>values</code> parameter 
	 * the be default the first element of the <code>values</code> String Array is added to the 
	 * <code>selected</code> String Array
	 * @param values
	 * @throws Exception
	 */
	public CampSet(String name,String defaultValues){
		super(name, AttributeType._set, defaultValues);
	}
	
	/**
	 * If <code>CampSet</code> is instantiated with only the <code>values</code> parameter 
	 * the be default the first element of the <code>values</code> String Array is added to the 
	 * <code>selected</code> String Array
	 * @param values
	 * @throws Exception
	 */
	public CampSet(String name,String[] values){
		super(name, AttributeType._set, null);
		try{
			if(values == null || values.length < 1){
				//TODO: (low priority) add camp exception classes 
				throw new Exception("CampSet cannot be instantiated with a values (String[]) equals to null or having a length of zero!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		this.values = values;
		selected = new String[]{values[0]};
	}
	
	public CampSet(String name, String[] selected,String[] values) {
		super(name, AttributeType._set, null);
		try{
			if(values == null || values.length < 1){
				throw new Exception("Instatiation attempt with empty selection set! CampSet cannot be instantiated with a String[] Array equal to null or with zero length!");
			}
		}catch(Exception e){
			e.printStackTrace();			
		}
		this.values = values;
		this.selected = new String[0];
		select(selected);
	}
	
	public CampSet defaultInstance(){
		return new CampSet(toString());
	}


	public String[] values() {
		return values;
	}
	public String[] selected() {
		return selected;
	}
	public int size(){
		return values.length;
	}
	/**
	 * @param selected
	 * @throws Exception
	 */
	public void select(String[] selected) {
		try{
			if(selected.length > values.length){
				throw new Exception("Selection set contains too many elements! The selection set cannot have more elements than this PilotSet enumeration");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		int size = selected.length;
		int count = 0;
		
		String[] newSelected = new String[size];
		for(String s:selected){
			try{
				if(!isElement(s)){
					throw new Exception("Attempting to select value not in selection set! '"+s+"'is not an element of the values-set!");
				}
				if(isSelected(s)){
					throw new Exception("Attempting to select a selected value! Value '"+s+"' is already selected!");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			newSelected[count] = s;
			count++;
			size--;
		}
		if(this.selected.length > 0){
			newSelected = Arrays.copyOf(newSelected, count + this.selected.length);
			for(String s:this.selected){
				newSelected[count] = s;
				count++;
			}
		}
		this.selected = newSelected;		
	}

	public void select(String select) {
		try {
			if(!isElement(select)){
				throw new Exception("Attempting to select value not in selection set! '"+select+"'is not an element of the values-set!");
			}
			if(isSelected(select)){
				throw new Exception("Attempting to select a selected value! Value '"+select+"' is already selected!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		String[] newSelected = Arrays.copyOf(this.selected, this.selected.length + 1);
		newSelected[this.selected.length] = select;
		this.selected = newSelected;		
	}

	public void unselect(String unselect){
		try{
			if(!isElement(unselect)){
				throw new Exception("Attempting to unselect value not in selection set! '"+unselect+"'is not an element of the values-set!");
			}
			if(!isSelected(unselect)){
				throw new Exception("Attempting to unselect a not selected value! Value '"+unselect+"' is not selected!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		int count = 0;
		String[] newSelected = new String[this.selected.length - 1];
		for(String chkVal:this.selected){
			if(!chkVal.equals(unselect)){
				newSelected[count] = chkVal;
				count++;
			}
		}
		this.selected = newSelected;
	}

	public boolean isSelected(String srchVal){
		boolean isSelected = false;
		for(String chkVal:this.selected){
			if(srchVal.equals(chkVal)){
				isSelected = true;
				break;
			}
		}
		return isSelected;
	}

	public boolean isSelected(String srchVals[]){
		boolean isSelected = true;
		for(String srchVal:srchVals){
			if(!isSelected(srchVal)){
				isSelected = false;
				break;
			}
		}
		return isSelected;
	}

	public boolean isElement(String srchVal){
		boolean isElement = false;
		for(String chkVal:this.values){
			if(srchVal.equals(chkVal)){
				isElement = true;
				break;
			}
		}
		return isElement;
	}

	public int selectedSize() {
		return selected.length;
	}

	@Override
	public String toString(){
		String string = this.name()+"|"+StringUtils.join(this.values(), ",").trim();
		string += "|";
		string += StringUtils.join(this.selected(), ",").trim(); 
		return string;
	}
	
	public static CampSet fromString(String e) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(_DEBUG) {
			_f = "[fromString]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}
		String[] vs = StringUtils.split(e, "|");
		if(vs.length<3) {
			try {
				if( _DEBUG){msg = "----[string value: "+e+"]----";LOG.info(String.format(fmt, _f,msg));}
				throw new Exception("String CampEnum must declare a selected value: '<value 1, value 2,...,value n>|<selected value>' !");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		String name = vs[0];
		String[] values = StringUtils.split(vs[1],",");
		String[] selected = null;
		if(vs[2] == null || vs[2].length()<0){
			return new CampSet(name,values);
		}
		selected = StringUtils.split(vs[2],",");
		return new CampSet(name,selected,values);
	}

	@Override
	public String value() {
		return toString();
	}

	@Override
	public String value(String value) {
		String prev = toString();
		CampSet s = fromString(value);
		this.values = s.values();
		this.selected = s.selected();
		return prev;
	}

	@Override
	public CampSet valueFromString(String value) {
		this.value(value);
		return this;
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
