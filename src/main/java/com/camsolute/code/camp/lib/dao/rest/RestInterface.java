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
package com.camsolute.code.camp.lib.dao.rest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.utilities.Util;

public interface RestInterface<T extends IsObjectInstance> {
	public static Logger LOG = LogManager.getLogger(RestInterface.class);
	public static String fmt = "[%15s] [%s]";
	
	public static String resultPost(String uri, String json, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[resultPOST]";
			msg = "====[ getting JSON result for '"+uri+"' service call ]====";LOG.info(String.format(fmt,_f,msg));
		}
    ResteasyClient client = new ResteasyClientBuilder().build();
		
		WebTarget target = client.target(uri);
		
		String result = target.request().post(Entity.entity(json, "application/json"),String.class);
		
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[resultPost completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return result;
	}
	
  public static <E extends Serialization<?>> String resultPOST(String uri, E list,boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && Util._IN_PRODUCTION) {
			_f = "[resultPOST]";
			msg = "====[ getting JSON result for '"+uri+"' service call ]====";LOG.info(String.format(fmt,_f,msg));
		}
		
   	ResteasyClient client = new ResteasyClientBuilder().build();
		
		WebTarget target = client.target(uri);
		
		String json = null;
		
		try {
			json = list.toJson();
		} catch (Exception e) {
			if(log && !Util._IN_PRODUCTION){msg = "----[ JSON EXCEPTION! transform FAILED.]----";LOG.info(String.format(fmt,_f,msg));}
			e.printStackTrace();
		}

		String result = target.request().post(Entity.entity(json, "application/json"),String.class);

		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[resultPOST completed. post result received ]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return result;
   }
	
  public static String resultGET(String uri,boolean log) {
  	long startTime = System.currentTimeMillis();
	String _f = null;
	String msg = null;
	if(log && Util._IN_PRODUCTION) {
		_f = "[resultGET]";
		msg = "====[ getting JSON result for '"+uri+"' service call ]====";LOG.info(String.format(fmt,_f,msg));
	}
	
  ResteasyClient client = new ResteasyClientBuilder().build();
	
	WebTarget target = client.target(uri);
	
	target.request().accept(MediaType.APPLICATION_JSON_TYPE);
	
	String result = target.request().get(String.class);
	
	if(log && Util._IN_PRODUCTION) {
		String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
		msg = "====[ JSON result received.]====";LOG.info(String.format(fmt,_f,msg+time));
	}
  	return result;
  }

	public static String[][] statusCodes =  
	{
			//2xx - Success - The action was successfully received,understood and accepted
			//3xx: Redirection - Further action must be taken in order to complete the request
	// 4xx - Client Error - The request contains bad syntax or cannot be fulfilled
	//5xx: Server Error - The server failed to fulfill an apparently valid request
		{"200","OK"},
    {"201","Created"},
    {"202","Accepted"},
    {"204","No Content"},
    {"301","Moved Permanently"},
    {"302","Moved Temporarily"},
    {"304","Not Modified"},
    {"400","Bad Request"},
    {"401","Unauthorized"},
    {"403","Forbidden"},
    {"404","Not Found"},
    {"500","Internal Server Error"},
    {"501","Not Implemented"},
    {"502","Bad Gateway"},
    {"503","Service Unavailable"},
    {"0","UNKNOWN ERROR CODE"},
	};

	public static boolean callSuccess(int statusCode){
		if(statusCode>199 && statusCode<300){
			return true;
		}
		return false;
	}
	public static boolean callFailed(int statusCode){
		return !callSuccess(statusCode);
	}
	
	public static String message(int statusCode) {
		String msg = statusCodes[statusCodes.length-1][1];
		for(String[] code:statusCodes) {
			if(Integer.valueOf(code[0])==statusCode) {
				msg = code[1];
			}
		}
		return msg;
	}
	public static String statusMessage(int statusCode){
		String msg = message(statusCode);
		return " response code '"+statusCode+"': '"+msg+"'";
	}

  /**
	 * Create a JSON formatted HTTP response JSONObject using the statusCodes HashMap 
	 * and the code supplied as a method parameter. 
	 * @param code: HTTP code ala RFC1945
	 * @return JSONObject: the HTTP response as JSONObject
	 */
	public static JSONObject responseCode(int code) {
		return responseCode(code,message(code));
	}
	public static JSONObject responseCode(int code,String message) {
		
		JSONObject jsonResponse = null;
		try {
			jsonResponse = new JSONObject();
			jsonResponse.put("HTTP-Version", "HTTP/1.1");
			jsonResponse.put("Status-Code", code);
			jsonResponse.put("Status-Message", message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResponse;
	}
	
	public static String jsonHttpErrorResponse(int code,String message) {
		return responseCode(code,message).toString();
	}

	public static String jsonHttpErrorResponse(int code) {
		try {
			return org.json.HTTP.toString(responseCode(code));
		} catch (JSONException e) {
			// TODO log something
			e.printStackTrace();
		}
		return "{}";
		
	}

	public static String jsonHttpResponse(int code,String message) {
		return responseCode(code,message).toString();
		//return "{}";
		
	}

	public static String jsonHttpResponse(int code) {
		try {
			return responseCode(code).toString();
		} catch (JSONException e) {
			// TODO log something
			e.printStackTrace();
		}
		return "{}";
		
	}

}
