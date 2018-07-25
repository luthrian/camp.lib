package com.camsolute.code.camp.lib.contract.value;

import java.sql.Timestamp;
import java.util.HashMap;

import com.camsolute.code.camp.lib.contract.core.CampBinary.Binary;
import com.camsolute.code.camp.lib.contract.core.CampComplex.ValueComplex;
import com.camsolute.code.camp.lib.contract.core.CampComplex.ValueListComplex;
import com.camsolute.code.camp.lib.contract.core.CampException.DataMismatchException;
import com.camsolute.code.camp.lib.contract.core.CampException.PersistanceException;
import com.camsolute.code.camp.lib.contract.core.CampList.ValueList;
import com.camsolute.code.camp.lib.contract.value.JSONValueHandler;

import java.util.Map;

import javax.ws.rs.HttpMethod;

import org.joda.time.DateTime;

import com.camsolute.code.camp.lib.contract.core.RestHandler;
import com.camsolute.code.camp.lib.contract.core.CampTable.ValueTable;
import com.camsolute.code.camp.lib.contract.value.JSONValueHandler.*;
import com.camsolute.code.camp.lib.contract.value.ValuePersistHandler.*;
import com.camsolute.code.camp.lib.contract.value.Value;
import com.camsolute.code.camp.lib.contract.value.Value.BooleanValue;
import com.camsolute.code.camp.lib.contract.value.Value.ComplexValue;
import com.camsolute.code.camp.lib.contract.value.Value.ListValue;
import com.camsolute.code.camp.lib.contract.value.Value.MapValue;
import com.camsolute.code.camp.lib.contract.value.Value.TableValue;
import com.camsolute.code.camp.lib.contract.value.Value.BinaryValue;
import com.camsolute.code.camp.lib.contract.value.Value.DateTimeValue;
import com.camsolute.code.camp.lib.contract.value.Value.DateValue;
import com.camsolute.code.camp.lib.contract.value.Value.EnumValue;
import com.camsolute.code.camp.lib.contract.value.Value.IntegerValue;
import com.camsolute.code.camp.lib.contract.value.Value.SetValue;
import com.camsolute.code.camp.lib.contract.value.Value.StringValue;
import com.camsolute.code.camp.lib.contract.value.Value.TextValue;
import com.camsolute.code.camp.lib.contract.value.Value.TimeValue;
import com.camsolute.code.camp.lib.contract.value.Value.TimestampValue;
import com.camsolute.code.camp.lib.contract.value.Value.ValueType;
import com.camsolute.code.camp.lib.utilities.Util;

public interface RestValueHandler<T, Q extends Value<T, Q>> extends RestHandler{
	
	public static final String VALUE_API_DOMAIN = Util.Config.instance().properties().getProperty("rest.api.domain.value");
  public static final String VALUE_API_PATH = "/value";
  public static final String VALUE_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.api.server.url.value") + VALUE_API_DOMAIN;

    public Q create(String objectId, ValueType type, String valueGroup, T value, int posX, int posY, int posZ, boolean selected) throws PersistanceException;

    public Q save(String objectId, Q v) throws PersistanceException;

    public ValueList save(String objectId, ValueList vl) throws PersistanceException;

    public int update(String objectId, Q v) throws PersistanceException;

    public int update(String objectId, ValueList vl) throws PersistanceException;

    public int delete(String valueId, ValueType type) throws PersistanceException;

    public int delete(Q v) throws PersistanceException;

    public int delete(ValueList vl) throws PersistanceException;

    public int delete(String objectId) throws PersistanceException;

    public Q load(String objectId, String valueId) throws PersistanceException;

    public ValueList load(String objectId) throws PersistanceException;

    public JSONValueHandler<T,Q> jsonHandler();

    public ValuePersistHandler<T,Q> sqlHandler();

    public abstract class AbstractRestValueHandler<T,Q extends Value<T,Q>> implements RestValueHandler<T,Q> {

        public static String server = VALUE_API_SERVER_URL;
        public static String path = VALUE_API_PATH;
        public static String VALUE_API_URL = VALUE_API_SERVER_URL + VALUE_API_PATH;  
        protected JSONValueHandler<T,Q> jsonHandler;
        protected ValuePersistHandler<T,Q> sqlHandler;
        
        public String restCall(String server, String path, String[] action, String modifier, Map<String[],String> parameters, String json) {

        	String url = assembleUrl(server, path, action, modifier, parameters);
        	String result = "{}";

	        // if we are doing a POST or PUT call then the parameterValue holds the JSON String representation of the object being persisted or updated
	        if(action[1].equals(HttpMethod.POST) || action[1].equals(HttpMethod.PUT)) {
	          //ensure that parameter is set to POST_VALUE indicating that parameterValue holds a JSON String representation.
	          result = RestHandler.resultPost(url, json);
	        } else {
	          result = RestHandler.resultGET(url);
	        }
	
	        return result;
      }

      public String assembleUrl(String server, String path, String[] action, String modifier, Map<String[],String> parameters){
        String url = "";
        url += server + path + action[0] + modifier;
        boolean start = true;
        for(String[] p: parameters.keySet()) {
          if(!start) {
            url += RestHandler.PARAMETER_SEPARATOR;
          } else {
            url += RestHandler.PARAMETER_START_SEPARATOR;
            start = false;
          }
          url += String.format(p[1],parameters.get(p));
        }
        return url;
      }

        public Q create(String objectId, ValueType type, String valueGroup, T value, int posX, int posY, int posZ, boolean selected) throws PersistanceException {
            @SuppressWarnings("unchecked")
            Q v= (Q) Value.ValueFactory.generateValue(type);
            v.updateGroup(valueGroup);
            v.setSelected(selected);
            v.position().update(posX,posY,posZ);
            try {
              v.updateData(value,false);
            } catch (DataMismatchException e) {
              e.printStackTrace();
              throw new PersistanceException("Data mismatch exception occurred!",e);
            }
            String json = v.toJson();
            try {
							return v.jsonHandler().fromJson(restCall(server,path,RestAction.SAVE,RestModifier.NONE,new HashMap<String[],String>(),json));
						} catch (DataMismatchException e) {
							e.printStackTrace();
							throw new PersistanceException("DataMismatchException occurred!",e);
						}
        }

        public Q save(String objectId, Q v) throws PersistanceException {
            String json = v.toJson();
            try {
							return v.jsonHandler().fromJson(restCall(server,path,RestAction.SAVE,RestModifier.NONE,new HashMap<String[],String>(),json));
						} catch (DataMismatchException e) {
							e.printStackTrace();
							throw new PersistanceException("DataMismatchException occurred!",e);
						}
        }

        public ValueList save(String objectId, ValueList vl) throws PersistanceException {
            String json = vl.toJson();
            try {
							return vl.jsonHandler().fromJson(restCall(server,path,RestAction.SAVE_LIST,RestModifier.NONE,new HashMap<String[],String>(),json));
						} catch (DataMismatchException e) {
							e.printStackTrace();
							throw new PersistanceException("DataMismatchException occurred!",e);
						}
        }

        public int update(String objectId, Q v) throws PersistanceException {
            String json = v.toJson();
            return Integer.valueOf(restCall(server,path,RestAction.UPDATE,RestModifier.NONE,new HashMap<String[],String>(),json));
        }

        public int update(String objectId, ValueList vl) throws PersistanceException {
            String json = vl.toJson();
            return Integer.valueOf(restCall(server,path,RestAction.UPDATE_LIST,RestModifier.NONE,new HashMap<String[],String>(),json));
        }

        public int delete(String valueId, ValueType type) throws PersistanceException {
            Map<String[],String> para = new HashMap<String[],String>();
            para.put(RestParameter.VALUE_ID,valueId);
            para.put(RestParameter.VALUE_TYPE,type.name());
            return Integer.valueOf(restCall(server,path,RestAction.DELETE,RestModifier.NONE,para,""));
        }

        public int delete(Q v) throws PersistanceException {
            Map<String[],String> para = new HashMap<String[],String>();
            para.put(RestParameter.VALUE_ID,v.id());
            para.put(RestParameter.VALUE_TYPE,v.type().name());
            return Integer.valueOf(restCall(server,path,RestAction.DELETE,RestModifier.NONE,para,""));
        }

        public int delete(ValueList vl) throws PersistanceException {
            String json = vl.toJson();
            return Integer.valueOf(restCall(server,path,RestAction.DELETE_LIST_POST,RestModifier.NONE,new HashMap<String[],String>(),json));
        }

        public int delete(String objectId) throws PersistanceException {
            Map<String[],String> para = new HashMap<String[],String>();
            para.put(RestParameter.OBJECT_ID,objectId);
            return Integer.valueOf(restCall(server,path,RestAction.DELETE_LIST,RestModifier.NONE,para,""));
        }

        @SuppressWarnings("unchecked")
				public Q load(String objectId, String valueId) throws PersistanceException {
            Map<String[],String> para = new HashMap<String[],String>();
            para.put(RestParameter.OBJECT_ID,objectId);
            para.put(RestParameter.VALUE_ID,valueId);
            try {
							return (Q) JSONValueHandler._fromJson(restCall(server,path,RestAction.LOAD,RestModifier.NONE,para,""));
						} catch (DataMismatchException e) {
							e.printStackTrace();
							throw new PersistanceException("DataMismatchException occurred!",e);
						}
        }

        public ValueList load(String objectId) throws PersistanceException {
            Map<String[],String> para = new HashMap<String[],String>();
            para.put(RestParameter.OBJECT_ID,objectId);
            try {
							return ValueList._fromJson(restCall(server,path,RestAction.LOAD,RestModifier.NONE,para,""));
						} catch (DataMismatchException e) {
							e.printStackTrace();
							throw new PersistanceException("DataMismatchException occurred!",e);
						}
        }

    }
    
    public class RestBooleanValueHandler extends AbstractRestValueHandler<Boolean,BooleanValue> {

    	public RestBooleanValueHandler() {
    		this.jsonHandler = new BooleanJSONValueHandler();
    		this.sqlHandler = new BooleanSQLValueHandler();
    	}
    	
			@Override
			public JSONValueHandler<Boolean, BooleanValue> jsonHandler() {
				return jsonHandler;
			}

			@Override
			public ValuePersistHandler<Boolean, BooleanValue> sqlHandler() {
				return sqlHandler;
			}
    }
    
    public class RestComplexValueHandler extends AbstractRestValueHandler<ValueListComplex,ComplexValue> {

    	public RestComplexValueHandler() {
    		this.jsonHandler = new ComplexJSONValueHandler();
    		this.sqlHandler = new ComplexSQLValueHandler();
    	}
    	
			@Override
			public JSONValueHandler<ValueListComplex, ComplexValue> jsonHandler() {
				return jsonHandler;
			}

			@Override
			public ValuePersistHandler<ValueListComplex, ComplexValue> sqlHandler() {
				return sqlHandler;
			}
    }

    public class RestBinaryValueHandler extends AbstractRestValueHandler<Binary,BinaryValue> {

    	public RestBinaryValueHandler() {
    		this.jsonHandler = new BinaryJSONValueHandler();
    		this.sqlHandler = new BinarySQLValueHandler();
    	}
    	
			@Override
			public JSONValueHandler<Binary, BinaryValue> jsonHandler() {
				return jsonHandler;
			}

			@Override
			public ValuePersistHandler<Binary, BinaryValue> sqlHandler() {
				return sqlHandler;
			}
    }
    
    public class RestDateValueHandler extends AbstractRestValueHandler<DateTime,DateValue> {

    	public RestDateValueHandler() {
    		this.jsonHandler = new DateJSONValueHandler();
    		this.sqlHandler = new DateSQLValueHandler();
    	}
    	
			@Override
			public JSONValueHandler<DateTime, DateValue> jsonHandler() {
				return jsonHandler;
			}

			@Override
			public ValuePersistHandler<DateTime, DateValue> sqlHandler() {
				return sqlHandler;
			}
    }
    
    public class RestDateTimeValueHandler extends AbstractRestValueHandler<DateTime,DateTimeValue> {

    	public RestDateTimeValueHandler() {
    		this.jsonHandler = new DateTimeJSONValueHandler();
    		this.sqlHandler = new DateTimeSQLValueHandler();
    	}
    	
			@Override
			public JSONValueHandler<DateTime, DateTimeValue> jsonHandler() {
				return jsonHandler;
			}

			@Override
			public ValuePersistHandler<DateTime, DateTimeValue> sqlHandler() {
				return sqlHandler;
			}
    }
    
    public class RestEnumValueHandler extends AbstractRestValueHandler<String,EnumValue> {

    	public RestEnumValueHandler() {
    		this.jsonHandler = new EnumJSONValueHandler();
    		this.sqlHandler = new EnumSQLValueHandler();
    	}
    	
			@Override
			public JSONValueHandler<String, EnumValue> jsonHandler() {
				return jsonHandler;
			}

			@Override
			public ValuePersistHandler<String, EnumValue> sqlHandler() {
				return sqlHandler;
			}
    }
    
    public class RestIntegerValueHandler extends AbstractRestValueHandler<Integer,IntegerValue> {

    	public RestIntegerValueHandler() {
    		this.jsonHandler = new IntegerJSONValueHandler();
    		this.sqlHandler = new IntegerSQLValueHandler();
    	}
    	
			@Override
			public JSONValueHandler<Integer, IntegerValue> jsonHandler() {
				return jsonHandler;
			}

			@Override
			public ValuePersistHandler<Integer, IntegerValue> sqlHandler() {
				return sqlHandler;
			}
    }
    
    public class RestListValueHandler extends AbstractRestValueHandler<ValueList,ListValue> {

    	public RestListValueHandler() {
    		this.jsonHandler = new ListJSONValueHandler();
    		this.sqlHandler = new ListSQLValueHandler();
    	}
    	
			@Override
			public JSONValueHandler<ValueList, ListValue> jsonHandler() {
				return jsonHandler;
			}

			@Override
			public ValuePersistHandler<ValueList, ListValue> sqlHandler() {
				return sqlHandler;
			}
    }
    
    public class RestMapValueHandler extends AbstractRestValueHandler<ValueComplex,MapValue> {

    	public RestMapValueHandler() {
    		this.jsonHandler = new MapJSONValueHandler();
    		this.sqlHandler = new MapSQLValueHandler();
    	}
    	
			@Override
			public JSONValueHandler<ValueComplex, MapValue> jsonHandler() {
				return jsonHandler;
			}

			@Override
			public ValuePersistHandler<ValueComplex, MapValue> sqlHandler() {
				return sqlHandler;
			}
    }
    
    public class RestSetValueHandler extends AbstractRestValueHandler<String,SetValue> {

    	public RestSetValueHandler() {
    		this.jsonHandler = new SetJSONValueHandler();
    		this.sqlHandler = new SetSQLValueHandler();
    	}
    	
			@Override
			public JSONValueHandler<String, SetValue> jsonHandler() {
				return jsonHandler;
			}

			@Override
			public ValuePersistHandler<String, SetValue> sqlHandler() {
				return sqlHandler;
			}
    }
    
    public class RestStringValueHandler extends AbstractRestValueHandler<String,StringValue> {

    	public RestStringValueHandler() {
    		this.jsonHandler = new StringJSONValueHandler();
    		this.sqlHandler = new StringSQLValueHandler();
    	}
    	
			@Override
			public JSONValueHandler<String, StringValue> jsonHandler() {
				return jsonHandler;
			}

			@Override
			public ValuePersistHandler<String, StringValue> sqlHandler() {
				return sqlHandler;
			}
    }
    
    public class RestTableValueHandler extends AbstractRestValueHandler<ValueTable,TableValue> {

    	public RestTableValueHandler() {
    		this.jsonHandler = new TableJSONValueHandler();
    		this.sqlHandler = new TableSQLValueHandler();
    	}
    	
			@Override
			public JSONValueHandler<ValueTable, TableValue> jsonHandler() {
				return jsonHandler;
			}

			@Override
			public ValuePersistHandler<ValueTable, TableValue> sqlHandler() {
				return sqlHandler;
			}
    }
    
    public class RestTimeValueHandler extends AbstractRestValueHandler<DateTime,TimeValue> {

    	public RestTimeValueHandler() {
    		this.jsonHandler = new TimeJSONValueHandler();
    		this.sqlHandler = new TimeSQLValueHandler();
    	}
    	
			public JSONValueHandler<DateTime, TimeValue> jsonHandler() {
				return jsonHandler;
			}

			public ValuePersistHandler<DateTime, TimeValue> sqlHandler() {
				return sqlHandler;
			}
    }
    
    public class RestTextValueHandler extends AbstractRestValueHandler<String,TextValue> {

    	public RestTextValueHandler() {
    		this.jsonHandler = new TextJSONValueHandler();
    		this.sqlHandler = new TextSQLValueHandler();
    	}
    	
			public JSONValueHandler<String, TextValue> jsonHandler() {
				return jsonHandler;
			}

			public ValuePersistHandler<String, TextValue> sqlHandler() {
				return sqlHandler;
			}
    }
    
    public class RestTimestampValueHandler extends AbstractRestValueHandler<Timestamp,TimestampValue> {

    	public RestTimestampValueHandler() {
    		this.jsonHandler = new TimestampJSONValueHandler();
    		this.sqlHandler = new TimestampSQLValueHandler();
    	}
    	
			public JSONValueHandler<Timestamp, TimestampValue> jsonHandler() {
				return jsonHandler;
			}

			public ValuePersistHandler<Timestamp, TimestampValue> sqlHandler() {
				return sqlHandler;
			}
    }
    
    
}
