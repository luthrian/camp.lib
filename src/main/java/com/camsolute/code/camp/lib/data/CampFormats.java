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
package com.camsolute.code.camp.lib.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.joda.time.DateTime;



public class CampFormats {
	public static final boolean _DEBUG = true;
	private static String fmt = "[%15s] [%s]";
	
	private static final Logger LOG = LogManager.getLogger(CampFormats.class);
	
	public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public final static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	public final static SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public final static String timeStringFormat = "HH:mm:ss";
	public final static String dateTimeStringFormat = "yyyy-MM-dd HH:mm:ss";
	public final static String dateStringFormat = "yyyy-MM-dd";
	public static final String defaultGroupName = "Default";
	public static final int defaultModelID = 0;
	public static final String defaultVersion = "1.0";
	public static final String defaultDecimalPoint = ".";
	public static final String defaultDecimalPoint2 = ",";
	public static String defaultProductName = "defprod";
	public static int _ORDER_POSITION_NUMBER_SIZE = 5;
	public static int _ORDER_NUMBER_SIZE = 10;
	public static final String defaulBusinessKey = "campsolute.com";
	public static final int _DAYS_IN_PAST_SEARCH_RANGE = 90;// when we do a range search with start date null/empty we search 90 Days in the past from end date 
	public static final int _DAYS_IN_FUTURE_SEARCH_RANGE = 90;// when we do a range search with end date null/empty we search 90 Days in the future from start date
	public final static DateTime datetime(String datetime){
		String _f = "[datetime]";
		boolean log = false;
		DateTime value = null;
		SimpleDateFormat[] FORMATS = {
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz"),
    			new SimpleDateFormat("dd/MM/yyyy HH:mm"),
    			new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
    			new SimpleDateFormat("HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
			};
			String msg = "Time value is: "+datetime;if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
			for(SimpleDateFormat sdf:FORMATS){
				try{
					sdf.setLenient(false);
					value = new DateTime(sdf.parse(datetime));
					msg = "Deserialized time to format as local datetime: "+value.toString("yyyy-MM-dd HH:mm:ss.SSS");if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg)); 
	    			return value;

				} catch (ParseException e){
					value = null;
					continue;
				}
			}

			return value;
		}
		
	

}
