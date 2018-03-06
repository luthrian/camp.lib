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

import org.apache.commons.lang3.StringUtils;

import com.camsolute.code.camp.lib.models.Attribute;
import com.camsolute.code.camp.lib.models.AttributeInterface;
import com.camsolute.code.camp.lib.models.Value;
import com.camsolute.code.camp.lib.utilities.Util;

public class CampEnum extends Attribute<EnumValue>{

    public CampEnum(){
        super(null, AttributeType._enum, null);
    }

	public CampEnum(String name){
		super(name, AttributeType._enum, null);
	}

	public CampEnum(String name,String[] values){
      super(name, AttributeType._enum, Util.Text.join(values,",")+"|"+values[0]);
	}
    public CampEnum(String name,String defaultValue){
        super(name, AttributeType._enum, defaultValue);
    }
    public CampEnum(String name,String defaultValue,String value){
		super(name, AttributeType._enum, defaultValue);
    this.setValue(new EnumValue(value));
	}

	public CampEnum(String name,String defaultValue,String[] values) {
      super(name, AttributeType._enum,defaultValue);
      this.setValue(new EnumValue(Util.Text.join(values,",")+"|"+values[0]));
	}

	public CampEnum(String name,String defaultValue,String selected,String[] values) {
      super(name, AttributeType._enum, Util.Text.join(values,",")+"|"+selected);
      this.setValue(new EnumValue(Util.Text.join(values,",")+"|"+selected));
	}

	public String[] values() {
      return this.value().value().split("|")[0].split(",");
	}
	public String selected() {
      return this.value().value().split("|")[1];
	}

	public void select(String select) {
      String[] values = this.value().value().split("|")[0].split(",");
		try{
			if(!contains(select)){
				throw new Exception("Attempt to select unknown value. The value '"+select+"' is not an element of the enumeration array.");
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
		this.setValue(new EnumValue(Util.Text.join(values,",")+"|"+select));
	}
	
	public void deselect() {
		String[] values = this.value().value().split("|")[0].split(",");
		this.setValue(new EnumValue(Util.Text.join(values,",")+"|"+values[0]));
	}

	public boolean isSelected(String srchVal){
		String selected = this.value().value().split("|")[1];
		boolean isSelected = false;
		if(srchVal.equals(selected)){
			isSelected = true;
		}
		return isSelected;
	}

	public boolean contains(String srchVal){
		boolean isElement = false;
		for(String chkVal:this.value().value().split("|")[0].split(",")){
			if(srchVal.equals(chkVal)){
				isElement = true;
				break;
			}
		}
		return isElement;
	}
	
	public int size() {
		String[] values = this.value().value().split("|")[0].split(",");
		if(values != null){
			return values.length;
		}else{
			return 0;
		}
	}

	@Override
	public String toString(){
		String value = name();
		value += "|"+this.value();
		return value;
	}
	
	public static CampEnum fromString(String e) {
		String[] vs = StringUtils.split(e, "|");
		if(vs.length<3) {
			try {
				throw new Exception("String CampEnum must declare a selected value: '<<attribute name>|value 1, value 2,...,value n>|<selected value>' !");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		String name = vs[0];
		String value = vs[1]+"|"+vs[2];
		
		return new CampEnum(name,value,value);
	}

	@Override
	public EnumValue valueFromString(String value) {
		return new EnumValue(value);
	}

	@Override
	public String toJson() {
		return AttributeInterface._toJson(this);
	}

	@Override
	public CampEnum fromJson(String json) {
		return (CampEnum) AttributeInterface._fromJson(json);
	} 

}
