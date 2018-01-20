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

public interface JSONRestClient {
	
	public String get(String serviceCall);
	
	public String post(String payload, String serviceCall);
	
	public String url();
	
	/**
	 * return the current service URI
	 * @return
	 */
	public String service();
	
	public void initialize(String domainUri, String serverUri);
	
	public void domainUri(String Uri);
	public String domain();

	public void serverUrl(String url);
	public String server();
		
}
