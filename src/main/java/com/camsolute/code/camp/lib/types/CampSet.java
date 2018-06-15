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
package com.camsolute.code.camp.lib.types;

import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.core.Value;
import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.AttributeInterface;
import com.camsolute.code.camp.lib.utilities.Util;

/**
 * The <code>CampSet</code> object represents a list of item names of which a subset of them are <code>selected</code>.
 * <br> The order of the item names is irrelevant.
 * @author 	Christohper Campbell
 * @see    	java.lang.String
 * @version 0.1
 * @since 	08.07.2017  
 */
public class CampSet extends Attribute<SetValue>{

	public CampSet(){
		super(null, AttributeType._set, null);
	}
	
	public CampSet(String name){
		super(name, AttributeType._set, null);
	}
	
	/**
	 * If <code>CampSet</code> is instantiated with only the <code>values</code> parameter 
	 * the be default the first element of the <code>values</code> String Array is added to the 
	 * <code>selected</code> String Array
	 * @param name attribute name
	 * @param defaultValue default value as json string
	 */
	public CampSet(String name,String defaultValue){
		super(name, AttributeType._set, defaultValue);
	}
	
	/**
	 * If <code>CampSet</code> is instantiated with only the <code>values</code> parameter 
	 * the be default the first element of the <code>values</code> String Array is added to the 
	 * <code>selected</code> String Array
	 * @param name attribute name
	 * @param defaultValue default value as json
	 */
	public CampSet(String name,String[] defaultValue){
		super(name, AttributeType._set, Util.Text.join(defaultValue,",")+"|"+defaultValue[0]);
	}

  public CampSet(String name,String defaultValue,String value){
		super(name, AttributeType._set, defaultValue);
   this.setValue(new SetValue(value));
	}

	public CampSet(String name,String defaultValue,String[] value) {
     super(name, AttributeType._set,defaultValue);
     this.setValue(new SetValue(Util.Text.join(value,",")+"|"+value[0]));
	}

	public CampSet(String name,String defaultValue,String[] selected,String[] value) {
     super(name, AttributeType._set, Util.Text.join(value,",")+"|"+Util.Text.join(selected,","));
     this.setValue(new SetValue(Util.Text.join(value,",")+"|"+selected));
	}


	public String[] values() {
		return this.value().value().split("|")[0].split(",");
	}
	
	public String[] selected() {
		return this.value().value().split("|")[1].split(",");
	}
	
	public int size(){
		return this.value().value().split("|")[0].length();
	}
	/**
	 * @param selected string array of selected values
	 * @return boolean switch
	 */
	public boolean select(String[] selected) {
		String[] vs = this.value().value().split("|");
		String[] values = vs[0].split(",");
		String[] currentSelected = vs[1].split(",");
		
		try{
			if(selected.length > values.length){
				throw new Exception("Exception! Attempt to select more elements than the CampSet object represents.");
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		String[] nvl = areElements(selected,values);
		try{
			if(nvl.length > 0){
				throw new Exception("Exception! Attempting to select element(s) that is(are) not part of the CampSet object set! '"+Util.Text.join(nvl,	",")+"'is(are) not part of the value set!");
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		String[] nsl = areElements(selected,currentSelected);
		try{
			if(nsl.length != selected.length){
				throw new Exception("Error! Some element(s) have already been selected! '"+Util.Text.join(nsl,	",")+"'is(are) already selected!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		this.value().setValue(vs[0]+"|"+vs[1]+","+Util.Text.join(nsl, ","));
		return true;
	}

	public boolean select(String select) {
		String[]vs = this.value().value().split("|");
		String[] selected = vs[1].split(",");
		String[] values = vs[0].split(",");
		boolean notSelected = false;

		if(!isElement(select,selected)){
			notSelected = true;
		}
		
		try {
			if(!isElement(select,values)){
				throw new Exception("Exception! Attempting to select an element which in not part of the CampSet object set! '"+select+"'is not an element of the values-set!");
			}
			if(!notSelected){
				throw new Exception("Exception! Attempting to select an element of the CampSet object which is already selected! Value '"+select+"' is already selected!");
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		this.value().setValue(vs[0]+"|"+vs[1]+","+select);
		return true;
	}

	public boolean deselect(String unselect){
		String[]vs = this.value().value().split("|");
		String[] selected = vs[1].split(",");
		String[] values = vs[0].split(",");
		boolean isSelected = false;

		if(isElement(unselect,selected)){
			isSelected = true;
		}
		
		try{
			if(!isElement(unselect,values)){
				throw new Exception("Exception! Attempt to deselect an element which is not part of the CampSet object! '"+unselect+"'is not an element of the values-set!");
			}
			if(!isSelected){
				throw new Exception("Exception! Cannot deselect an element of the CampSet object which has not been selected! Value '"+unselect+"' is not selected!");
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		this.value().setValue(vs[0]+"|"+Util.Text.join(remove(unselect, selected), ","));
		return true;
	}

	private String[] remove(String element,String[] fromArray) {
		String[] na = new String[fromArray.length - 1];
		int count = 0;
		for(String a:fromArray) {
			if(!element.equals(a)) {
				na[count] = a;
				count++;
			}
		}
		return na;
	}
	
	public boolean isSelected(String srchVal){
		String[] selected = this.value().value().split("|")[1].split(",");
		return isElement(srchVal,selected);
	}

	public String[] areSelected(String srchVals[]){
		String[] selected = this.value().value().split("|")[1].split(",");
		return areElements(srchVals,selected);
	}
	
	public boolean isElement(String srchVal){
		String[] values = this.value().value().split("|")[0].split(",");
		return isElement(srchVal,values);
	}

	public boolean isElement(String srchVal,String[] target){
		for(String chkVal:target){
			if(srchVal.equals(chkVal)){
				return true;
			}
		}
		return false;
	}

	public String[] areElements(String elements[],String[] ofArray){
		ArrayList<String> ns= new ArrayList<String>(); 
		for(String element:elements){
			if(!isElement(element,ofArray)){
				ns.add(element);
			}
		}
		return ns.toArray(new String[0]);
	}

	public int selectedSize() {
		return this.value().value().split("|")[1].split(",").length;
	}

	@Override
	public String toString(){
		String string = this.name()+"|"+Util.Text.join(this.values(), ",").trim();
		string += "|";
		string += Util.Text.join(this.selected(), ",").trim(); 
		return string;
	}
	
	public static String toString(CampSet cs){
		String string = cs.name()+"|"+Util.Text.join(cs.values(), ",").trim();
		string += "|";
		string += Util.Text.join(cs.selected(), ",").trim(); 
		return string;
	}
	
	public static CampSet fromString(String e) {
		String[] vs = e.split("|");
		if(vs.length<3) {
			try {
				throw new Exception("String CampEnum must declare a selected value: '<name>|<value 1, value 2,...,value n>|<selected value1,...,valuen>' !");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		String name = vs[0];
		String[] values = vs[1].split(",");
		String[] selected = null;
		if(vs[2] == null || vs[2].length()<1){
			return new CampSet(name,Util.Text.join(values, ","),values);
		}
		selected = vs[2].split(",");
		return new CampSet(name,Util.Text.join(values, ","),selected,values);
	}

	@Override
	public String toJson() {
		return AttributeInterface._toJson(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Attribute<SetValue> fromJson(String json) {
		return (Attribute<SetValue>) AttributeInterface._fromJson(json);
	}

	@Override
	public SetValue valueFromString(String value) {
		return new SetValue(value);
	}

}
