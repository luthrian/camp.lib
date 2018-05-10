package com.camsolute.code.camp.lib.utilities;

import java.util.ArrayList;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import com.camsolute.code.camp.lib.contract.IsObjectInstance;
import com.camsolute.code.camp.lib.contract.Serialization;
import com.camsolute.code.camp.lib.dao.LoggerDaoInterface;
import com.camsolute.code.camp.lib.dao.rest.RestInterface;
import com.camsolute.code.camp.lib.data.CampRest;
import com.camsolute.code.camp.lib.utilities.LogEntryInterface.LogObjects;

public class LoggerRest implements LoggerDaoInterface {

	private static final Logger LOG = LogManager.getLogger(LoggerRest.class);
	private static String fmt = "[%15s] [%s]";
	
	public static final String serverUrl = CampRest.LOGGING_API_SERVER_URL;
	public static final String domainUri = CampRest.LOGGING_API_DOMAIN;

	private static LoggerRest instance = null;
	
	private LoggerRest(){
	}
	
	public static LoggerRest instance(){
		if(instance == null) {
			instance = new LoggerRest();
		}
		return instance;
	}
	
	
	@Override
	public <T extends IsObjectInstance<T>> LogEntry<T> log(IsObjectInstance<?> object, LogObjects type, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[log]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = object.toJson();
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOG);
		String uri = serverUrl+domainUri+String.format(serviceUri,type.name());
		String result = RestInterface.resultPost(uri, json, log);
		LogEntry<T> a = LogEntryInterface._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[log completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return a;
	}

	@Override
	public <T extends IsObjectInstance<?>, E extends ArrayList<T>> LogEntryList log(E objects, LogObjects type, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[log]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String json = ((LogEntryList)objects).toJson();
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOG_LIST);
		String uri = serverUrl+domainUri+String.format(serviceUri,type.name());
		String result = RestInterface.resultPost(uri, json, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[log completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public LogEntryList loadByType(String objectType, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByType]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_TYPE);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectType);
		String result = RestInterface.resultGET(uri, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByType completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public LogEntryList loadByTypeGroup(String objectType, String group, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByTypeGroup]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_TYPE_GROUP);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectType, group);
		String result = RestInterface.resultGET(uri, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByTypeGroup completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public LogEntryList loadByTypeVersion(String objectType, String version, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByTypeVersion]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_TYPE_VERSION);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectType, version);
		String result = RestInterface.resultGET(uri, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByTypeVersion completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public LogEntryList loadByTypeDate(String objectType, String date, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByTypeDate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_TYPE_DATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectType, date);
		String result = RestInterface.resultGET(uri, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByTypeDate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public LogEntryList loadByTypeEndOfLife(String objectType, String endOfLife, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByTypeEndOfLife]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_TYPE_END_OF_LIFE);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectType, endOfLife);
		String result = RestInterface.resultGET(uri, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByTypeEndOfLife completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public LogEntryList loadByTypeDateRange(String businessId, String startDate, String endDate, RangeTarget target, boolean log) {
			long startTime = System.currentTimeMillis();
			String _f = null;
			String msg = null;
			if(log && !Util._IN_PRODUCTION) {
				_f = "[loadByTypeDateRange]";
				msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
			}
			String prefix = CampRest.Logging.Prefix;		
			String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_TYPE_DATE_RANGE);
			String uri = serverUrl+domainUri+String.format(serviceUri,businessId, startDate, endDate, target);
			String result = RestInterface.resultGET(uri, log);
			LogEntryList al = LogEntryList._fromJson(result);
			if(log && !Util._IN_PRODUCTION) {
				String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
				msg = "====[loadByTypeDateRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
			}
			return al;
	}

	@Override
	public LogEntryList loadByTypeTimestamp(String businessId, String timestamp, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByTypeTimestamp]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_TYPE_TIMESTAMP);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, timestamp);
		String result = RestInterface.resultGET(uri, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByTypeTimestamp completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public LogEntryList loadByTypeLogTimestamp(String businessId, String timestamp, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByTypeLogTimestamp]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_TYPE_LOG_TIMESTAMP);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, timestamp);
		String result = RestInterface.resultGET(uri, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByTypeLogTimestamp completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public LogEntryList loadByBusinessId(String businessId, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByBusinessId]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_BUSINESS_ID);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId);
		String result = RestInterface.resultGET(uri, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByBusinessId completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public LogEntryList loadByGroup(String businessId, String group, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByGroup]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_GROUP);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, group);
		String result = RestInterface.resultGET(uri, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByGroup completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public LogEntryList loadByVersion(String businessId, String version, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByVersion]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_VERSION);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, version);
		String result = RestInterface.resultGET(uri, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByVersion completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public LogEntryList loadByDate(String businessId, String date, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByDate]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_DATE);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, date);
		String result = RestInterface.resultGET(uri, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByDate completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public LogEntryList loadByEndOfLife(String objectType, String endOfLife, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByEndOfLife]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_END_OF_LIFE);
		String uri = serverUrl+domainUri+String.format(serviceUri,objectType, endOfLife);
		String result = RestInterface.resultGET(uri, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByEndOfLife completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public LogEntryList loadByDateRange(String businessId, String startDate, String endDate, RangeTarget target, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByDateRange]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_DATE_RANGE);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, startDate, endDate, target);
		String result = RestInterface.resultGET(uri, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByDateRange completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public LogEntryList loadByTimestamp(String businessId, String timestamp, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByTimestamp]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_TIMESTAMP);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, timestamp);
		String result = RestInterface.resultGET(uri, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByTimestamp completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	@Override
	public LogEntryList loadByLogTimestamp(String businessId, String timestamp, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[loadByLogTimestamp]";
			msg = "====[  ]====";LOG.traceEntry(String.format(fmt,(_f+">>>>>>>>>").toUpperCase(),msg));
		}
		String prefix = CampRest.Logging.Prefix;		
		String serviceUri = CampRest.LoggingService.callRequest(prefix,CampRest.LoggingService.Request.LOAD_BY_LOG_TIMESTAMP);
		String uri = serverUrl+domainUri+String.format(serviceUri,businessId, timestamp);
		String result = RestInterface.resultGET(uri, log);
		LogEntryList al = LogEntryList._fromJson(result);
		if(log && !Util._IN_PRODUCTION) {
			String time = "[ExecutionTime:"+(System.currentTimeMillis()-startTime)+")]====";
			msg = "====[loadByLogTimestamp completed.]====";LOG.info(String.format(fmt,("<<<<<<<<<"+_f).toUpperCase(),msg+time));
		}
		return al;
	}

	public static String resultPost(String uri, String json, boolean log) {
		long startTime = System.currentTimeMillis();
		String _f = null;
		String msg = null;
		if(log && !Util._IN_PRODUCTION) {
			_f = "[resultPOST]";
			msg = "====[ getting JSON result for '"+uri+"' service call ]====";LOG.info(String.format(fmt,_f,msg));
		}
		if(log && !Util._IN_PRODUCTION){msg = "----[rest call json("+json+")]----";LOG.info(String.format(fmt, _f,msg));}
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
  
}
