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
package com.camsolute.code.camp.lib;

import org.apache.commons.lang.StringUtils;

import com.camsolute.code.camp.core.CampFormats;

public class Version {
	
	private int major;
	private int minor;
	
	public Version(){
		this(CampFormats.defaultVersion);
	}
	public Version(int major,int minor){
		this.major = major;
		this.minor = minor;
	}
	
	public Version(String version){
		if(version == null || version.isEmpty()) {
			version = CampFormats.defaultVersion;
		}
		String[] digits = StringUtils.split(version,CampFormats.defaultDecimalPoint);
		if(digits.length < 2){
			digits = StringUtils.split(version,CampFormats.defaultDecimalPoint2);
		}
		int maj = Integer.parseInt(digits[0]);
		int min = Integer.parseInt(digits[1]);
		
		if(min <= 0 ) {
			if(maj <= 0){
				this.minor = 1;
			} else {
				this.minor = 0;
			}
		} else {
			this.minor = min;
		}
		if(maj < 0){
			this.major = 0;
		} else {
			this.major = maj;
		}
	}
	
	public String value() {
	
		return String.valueOf(this.major)+CampFormats.defaultDecimalPoint+String.valueOf(this.minor);
	}
	
	public Version increment() {
				
		
		if(this.minor < 9 ) {
			this.minor++;
		} else {
			this.minor = 0;
			this.major++;
		}
		
		
		return this;
		
	}
	
}
