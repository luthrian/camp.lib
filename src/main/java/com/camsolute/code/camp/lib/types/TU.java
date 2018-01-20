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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import com.camsolute.code.camp.core.U;

public class TU {
	public static final boolean _DEBUG = true;
	private static String fmt = "[%15s] [%s]";
	
	private static final Logger LOG = LogManager.getLogger(TU.class);
	
	public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public final static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	public final static SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public final static String timeStringFormat = "HH:mm:ss";
	public final static String dateTimeStringFormat = "yyyy-MM-dd HH:mm:ss";
	public final static String dateStringFormat = "yyyy-MM-dd";
	
	public static String TIMEFORMAT = "HH:mm:ss";
	public static String DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String DATEFORMAT = "yyyy-MM-dd";
	
	public final static DateTime dateTimeFromString(String datetime){ return _dateTimeFromString(datetime,false); }
	public final static DateTime _dateTimeFromString(String datetime, boolean log){ 
		String _f = "[_dateTimeFromString]";
		String msg = " -- [ transforming string datetime to DateTime instance ]";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
		DateTime value = null;
		SimpleDateFormat[] FORMATS = {
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSz"),
    			new SimpleDateFormat("dd/MM/yyyy HH:mm"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
       			new SimpleDateFormat("HH:mm:ss.SSS"),
       			new SimpleDateFormat("HH:mm:ss"),
       			new SimpleDateFormat("HH:mm")
			};
		msg = " -- -- [ Time value is:"+datetime+"]";if(log && _DEBUG)LOG.info(_f + msg);

		for(SimpleDateFormat sdf:FORMATS){
			try{
				sdf.setLenient(false);
				value = new DateTime(sdf.parse(datetime));
				
				msg = " -- -- [ Deserialized time to format:"+value.toString("yyyy-MM-dd HH:mm:ss.SSS")+"]";if(log && _DEBUG)LOG.info(_f + msg);
    			return value;

			} catch (ParseException e){
				value = null;
				continue;
			}
		}
		return value;
	}

	public final static Timestamp timestampFromString(String datetime){ return _timestampFromString(datetime,true); }
	public final static Timestamp _timestampFromString(String datetime, boolean log){ 
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && _DEBUG) {
			_f = "[_timestampFromString]";
			msg = "====[ transforming string datetime to Timstamp instance ]====";LOG.traceEntry(String.format(fmt,_f,msg));
		}

		Timestamp value = null;
		SimpleDateFormat[] FORMATS = {
    			new SimpleDateFormat("yyyy-MM-dd"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz"),
    			new SimpleDateFormat("dd/MM/yyyy"),
    			new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"),
    			new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS"),
    			new SimpleDateFormat("dd.MM.yyyy"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSz"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy.MM.dd"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSSz"),
    			new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
       			new SimpleDateFormat("HH:mm:ss.SSSZ"),
       			new SimpleDateFormat("HH:mm:ss.SSSz"),
       			new SimpleDateFormat("HH:mm:ss.SSS"),
       			new SimpleDateFormat("HH:mm:ss"),
       			new SimpleDateFormat("HH:mm")
			};
		msg = " -- -- [ Datetime value is:"+datetime+"]";if(log && _DEBUG)LOG.info(_f + msg);
		U.timestamp(datetime);
		for(SimpleDateFormat sdf:FORMATS){
			try{
				sdf.setLenient(false);
				value = Timestamp.valueOf(LocalDateTime.fromDateFields(sdf.parse(datetime)).toString());
				
				if(log && _DEBUG){msg = "----[Deserialized time to format:"+value.toString()+"]----";LOG.info(String.format(fmt, _f,msg));}
    			return value;

			} catch (ParseException e){
				value = null;
				continue;
			}
		}
		return value;
	}
	public static String timeStampString() {
		Date date = new Date();	
		return new Timestamp(date.getTime()).toString().substring(0, 19);
	}
		
	public static String dateString() {
		Date date = new Date();	
		return new Timestamp(date.getTime()).toString().substring(0, 10);
	}
		
	public static Timestamp timestamp() {
		DateTime dt = new DateTime();
		String timestamp = dt.toString("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = new Date();	
		return new Timestamp(dt.getMillis());
	}
	public static String timestamp(Timestamp timestamp) {
		DateTime dt = new DateTime(timestamp);
		String stimestamp = dt.toString("yyyy-MM-dd HH:mm:ss.SSS");
		return stimestamp;
	}
		
	public static Timestamp timestamp(String timestamp) {
		return Timestamp.valueOf(timestamp);
	}

	public static String getDate(DateTime date) { 
		// TODO Auto-generated method stub
		return date.toString("yyyy-MM-dd");
	}

	public static DateTime getDateTimefromString(String datetime){ return _getDateTimefromString(datetime,true);}
	public static DateTime _getDateTimefromString(String datetime,boolean log){
		String _f = "[getDateTimefromString]";
		String msg = " -- [  ] "+ " [ datetime ]";if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
		DateTime value = null;
		SimpleDateFormat[] FORMATS = {
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
    			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz"),
    			new SimpleDateFormat("dd/MM/yyyy HH:mm"),
    			new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"),
    			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			};
		msg = "Time value is:"+datetime;if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
		
		for(SimpleDateFormat sdf:FORMATS){
			try{
				sdf.setLenient(false);
				value = new DateTime(sdf.parse(datetime));
				msg = "Deserialized time to format:"+value.toString("yyyy-MM-dd HH:mm:ss");if(log && _DEBUG)LOG.info(String.format(fmt,_f,msg));
    			
    			return value;

			} catch (ParseException e){
				value = null;
				continue;
			}
		}

		return value;
	}


}
