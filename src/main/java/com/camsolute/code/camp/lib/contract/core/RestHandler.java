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
 *  Christopher Campbell - all code prior and post initial release
 ******************************************************************************/
package com.camsolute.code.camp.lib.contract.core;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;

import javax.ws.rs.HttpMethod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.core.CampException.PersistanceException;
import com.camsolute.code.camp.lib.contract.core.CampList.ValueList;
import com.camsolute.code.camp.lib.contract.value.Value.ValueType;
import com.camsolute.code.camp.lib.utilities.Util;

public interface RestHandler {

  public static final String PARAMETER_START_SEPARATOR = "?";
  public static final String PARAMETER_SEPARATOR = "&";

    //public CampMessage<?> restCall(RestAction action, RestModifier modifier, RestParameter parameter, String parameterValue,  Map<RestParameter,String> extraParameters);

  public String assembleUrl(String server, String principal, String[] action, String modifier, Map<String[],String> extraParameters);

  public static class RestAction {
  	public static String[] CREATE= new String[]{"/create",HttpMethod.GET};
  	public static final String CREATE_PATH= "/create";
  	public static String[] SAVE= new String[]{"/save",HttpMethod.POST};
	public static final String SAVE_PATH= "/save";
  	public static String[] SAVE_LIST= new String[]{"/s/save",HttpMethod.POST};
	public static final String SAVE_LIST_PATH= "/s/save";
  	public static String[] SAVE_GET= new String[]{"/g/save",HttpMethod.GET};
	public static final String SAVE_GET_PATH= "/g/save";
  	public static String[] SAVE_LIST_GET= new String[]{"/s/g/save",HttpMethod.GET};
	public static final String SAVE_LIST_GET_PATH= "/s/g/save";
  	public static String[] UPDATE= new String[]{"/update",HttpMethod.PUT};
	public static final String UPDATE_PATH= "/update";
  	public static String[] UPDATE_LIST= new String[]{"/s/update",HttpMethod.PUT};
	public static final String UPDATE_LIST_PATH= "/s/update";
  	public static String[] UPDATE_GET= new String[]{"/g/update",HttpMethod.GET};
	public static final String UPDATE_GET_PATH= "/g/update";
  	public static String[] UPDATE_LIST_GET= new String[]{"/s/g/update",HttpMethod.GET};
	public static final String UPDATE_LIST_GET_PATH= "/s/g/update";
  	public static String[] DELETE= new String[]{"/delete",HttpMethod.DELETE};
	public static final String DELETE_PATH= "/delete";
  	public static String[] DELETE_LIST= new String[]{"/s/delete",HttpMethod.DELETE};
	public static final String DELETE_LIST_PATH= "/s/delete";
  	public static String[] DELETE_POST= new String[]{"/p/delete",HttpMethod.POST};
	public static final String DELETE_POST_PATH= "/p/delete";
  	public static String[] DELETE_LIST_POST= new String[]{"/s/p/delete",HttpMethod.POST};
	public static final String DELETE_LIST_POST_PATH= "/s/p/delete";
  	public static String[] LOAD= new String[]{"/load",HttpMethod.GET};
	public static final String LOAD_PATH= "/load";
  	public static String[] LOAD_LIST= new String[]{"/s/load",HttpMethod.GET};
	public static final String LOAD_LIST_PATH= "/s/load";

    private final String path;
    private final String method;
    private RestAction(String[] pathMethod) {
      this.path = pathMethod[0];
      this.method = pathMethod[1];
    }

    @Override
    public String toString() {
      return path;
    }
    public String path() {
      return path;
    }
    public String method() {
      return method;
    }
  }

  public static class RestParameter {
    public static String[] POST_VALUE = new String[]{"",""};
    public static final String POST_VALUE_PATH = "";
    public static String[] ID = new String[]{"/by/id","id=%s"};
    public static final String ID_PATH = "/by/id";
    public static String[] BUSINESS_ID = new String[]{"/by/businessId","businessId=%s"};
    public static final String BUSINESS_ID_PATH = "/by/businessId";
    public static String[] BUSINESS_KEY = new String[]{"/by/businessKey","businessKey=%s"};
    public static final String BUSINESS_KEY_PATH = "/by/businessKey";
    public static String[] PARENT_ID = new String[]{"/by/parentId","parentId=%s"};
    public static final String PARENT_ID_PATH = "/by/parentId";
    public static String[] PARENT_BUSINESS_ID = new String[]{"/by/parentBusinessId","parentBusinessId=%s"};
    public static final String PARENT_BUSINESS_ID_PATH = "/by/parentBusinessId";
    public static String[] SOURCE_ID = new String[]{"/by/sourceId","sourceId=%s"};
    public static final String SOURCE_ID_PATH = "/by/sourceId";
    public static String[] SOURCE_BUSINESS_ID = new String[]{"/by/sourceBusinessId","sourceBusinessId=%s"};
    public static final String SOURCE_BUSINESS_ID_PATH = "/by/sourceBusinessId";
    public static String[] SOURCE_BUSINESS_KEY = new String[]{"/by/sourceBusinessKey","sourceBusinessKey=%s"};
    public static final String SOURCE_BUSINESS_KEY_PATH = "/by/sourceBusinessKey";
    public static String[] TARGET_ID = new String[]{"/by/targetId","targetId=%s"};
    public static final String TARGET_ID_PATH = "/by/targetId";
    public static String[] TARGET_BUSINESS_ID = new String[]{"/by/targetBusinessId","targetBusinessId=%s"};
    public static final String TARGET_BUSINESS_ID_PATH = "/by/targetBusinessId";
    public static String[] TARGET_BUSINESS_KEY = new String[]{"/by/targetBusinessKey","targetBusinessKey=%s"};
    public static final String TARGET_BUSINESS_KEY_PATH = "/by/targetBusinessKey";
    public static String[] OBJECT_ID = new String[]{"/by/objectId","objectId=%s"};
    public static final String OBJECT_ID_PATH = "/by/objectId";
    public static String[] OBJECT_TYPE = new String[]{"/by/objectType","objectType=%s"};
    public static final String OBJECT_TYPE_PATH = "/by/objectType";
    public static String[] ATTRIBUTE_TYPE = new String[]{"/by/attributeType","attributeType=%s"};
    public static final String ATTRIBUTE_TYPE_PATH = "/by/attributeType";
    public static String[] ATTRIBUTE_VALUE = new String[]{"/by/attributeValue","attributeValue=%s"};
    public static final String ATTRIBUTE_VALUE_PATH = "/by/attributeValue";
    public static String[] VALUE_ID = new String[]{"/by/valueId","valueId=%s"};
    public static final String VALUE_ID_PATH = "/by/valueId";
    public static String[] VALUE_TYPE = new String[]{"/by/valueType","valueType=%s"};
    public static final String VALUE_TYPE_PATH = "/by/valueType";
    public static String[] VARIABLE_NAME = new String[]{"/by/variableName","variableName=%s"};
    public static final String VARIABLE_NAME_PATH = "/by/variableName";
    public static String[] VARIABLE_TYPE = new String[]{"/by/variableType","variableType=%s"};
    public static final String VARIABLE_TYPE_PATH = "/by/variableType";
    public static String[] VARIABLE_VALUE = new String[]{"/by/variableValue","variableValue=%s"};
    public static final String VARIABLE_VALUE_PATH = "/by/variableValue";
    public static String[] USE_OBJECT_ID = new String[]{"/by/useObjectId","useObjectId=%s"};
    public static final String USE_OBJECT_ID_PATH = "/by/useObjectId";
    public static String[] POSITION = new String[]{"/by/position","position=%s"};
    public static final String POSITION_PATH = "/by/position";
    public static String[] START_POSITION = new String[]{"/by/startPosition","startPosition=%s"};
    public static final String START_POSITION_PATH = "/by/startPosition";
    public static String[] END_POSITION = new String[]{"/by/endPosition","endPosition=%s"};
    public static final String END_POSITION_PATH = "/by/endPosition";
    public static String[] GROUP = new String[]{"/by/group","group=%s"};
    public static final String GROUP_PATH = "/by/group";
    public static String[] VERSION = new String[]{"/by/version","version=%s"};
    public static final String VERSION_PATH = "/by/version";
    public static String[] TARGET = new String[]{"/by/target","target=%s"};
    public static final String TARGET_PATH = "/by/target";
    public static String[] PRINCIPAL = new String[]{"/by/principal","principal=%s"};
    public static final String PRINCIPAL_PATH = "/by/principal";
    public static String[] PRIMARY = new String[]{"/by/primary","primary=%s"};
    public static final String PRIMARY_PATH = "/by/primary";
    public static String[] DATE = new String[]{"/by/date","date=%s"};
    public static final String DATE_PATH = "/by/date";
    public static String[] START_DATE = new String[]{"/by/startDate","startDate=%s"};
    public static final String START_DATE_PATH = "/by/startDate";
    public static String[] END_DATE = new String[]{"/by/endDate","endDate=%s"};
    public static final String END_DATE_PATH = "/by/endDate";
    public static String[] DATE_RANGE = new String[]{"/by/dateRange","startDate=%s&endDate=%s"};
    public static final String DATE_RANGE_PATH = "/by/dateRange";
    public static String[] BY_DATE = new String[]{"/by/byDate","byDate=%s"};
    public static final String BY_DATE_PATH = "/by/byDate";
    public static String[] END_OF_LIFE = new String[]{"/by/endOfLife","endOfLife=%s"};
    public static final String END_OF_LIFE_PATH = "/by/endOfLife";
    public static String[] TIMESTAMP = new String[]{"/by/timestamp","timestamp=%s"};
    public static final String TIMESTAMP_PATH = "/by/timestamp";
    public static String[] TYPE = new String[]{"/by/type","type=%s"};
    public static final String TYPE_PATH = "/by/type";
    public static String[] CUSTOMER_BUSINESS_ID = new String[]{"/by/customerBusinessId","customerBusinessId=%s"};
    public static final String CUSTOMER_BUSINESS_ID_PATH = "/by/customerBusinessId";
    public static String[] CUSTOMER_BUSINESS_KEY = new String[]{"/by/customerBusinessKey","customerBusinessKey=%s"};
    public static final String CUSTOMER_BUSINESS_KEY_PATH = "/by/customerBusinessKey";
    public static String[] RESPONSIBLE_BUSINESS_ID = new String[]{"/by/responsibleBusinessId","responsibleBusinessId=%s"};
    public static final String RESPONSIBLE_BUSINESS_ID_PATH = "/by/responsibleBusinessId";
    public static String[] RESPONSIBLE_BUSINESS_KEY = new String[]{"/by/responsibleBusinessKey","responsibleBusinessId=%s"};
    public static final String RESPONSIBLE_BUSINESS_KEY_PATH = "/by/responsibleBusinessKey";
    public static String[] TITLE = new String[]{"/by/title","title=%s"};
    public static final String TITLE_PATH = "/by/title";
    public static String[] DESCRIPTION = new String[]{"/by/description","description=%s"};
    public static final String DESCRIPTION_PATH = "/by/description";
    public static String[] TOPIC = new String[]{"/by/topic","topic=%s"};
    public static final String TOPIC_PATH = "/by/topic";
    public static String[] COUNTRY = new String[]{"/by/country","country=%s"};
    public static final String COUNTRY_PATH = "/by/country";
    public static String[] STATE = new String[]{"/by/state","state=%s"};
    public static final String STATE_PATH = "/by/state";
    public static String[] POSTCODE = new String[]{"/by/postcode","postcode=%s"};
    public static final String POSTCODE_PATH = "/by/postcode";
    public static String[] CITY = new String[]{"/by/city","city=%s"};
    public static final String CITY_PATH = "/by/city";
    public static String[] STREET = new String[]{"/by/street","street=%s"};
    public static final String STREET_PATH = "/by/street";
    public static String[] STREET_NUMBER = new String[]{"/by/streetNumber","streetNumber=%s"};
    public static final String STREET_NUMBER_PATH = "/by/streetNumber";
    public static String[] EMAIL = new String[]{"/by/email","email=%s"};
    public static final String EMAIL_PATH = "/by/email";
    public static String[] MOBILE = new String[]{"/by/mobile","mobile=%s"};
    public static final String MOBILE_PATH = "/by/mobile";
    public static String[] TELEPHONE = new String[]{"/by/telephone","telephone=%s"};
    public static final String TELEPHONE_PATH = "/by/telephone";
    public static String[] FAX = new String[]{"/by/fax","fax=%s"};
    public static final String FAX_PATH = "/by/fax";
    public static String[] SKYPE = new String[]{"/by/skype","skype=%s"};
    public static final String SKYPE_PATH = "/by/skype";
    public static String[] MISC = new String[]{"/by/misc","misc=%s"};
    public static final String MISC_PATH = "/by/misc";
    public static String[] PROCESS_INSTANCE_ID = new String[]{"/by/processInstanceId","processInstanceId=%s"};
    public static final String PROCESS_INSTANCE_ID_PATH = "/by/processInstanceId";
    public static String[] PROCESS_KEY = new String[]{"/by/processKey","processKey=%s"};
    public static final String PROCESS_KEY_PATH = "/by/processKey";
    public static String[] PROCESS_NAME = new String[]{"/by/processName","processName=%s"};
    public static final String PROCESS_NAME_PATH = "/by/processName";
    public static String[] INSTANCE_ID = new String[]{"/by/instanceId","instanceId=%s"};
    public static final String INSTANCE_ID_PATH = "/by/instanceId";
    public static String[] EXECUTION_ID = new String[]{"/by/executionId","executionId=%s"};
    public static final String EXECUTION_ID_PATH = "/by/executionId";
    public static String[] MESSAGE_TYPE = new String[]{"/by/messageType","messageType=%s"};
    public static final String MESSAGE_TYPE_PATH = "/by/messageType";
    public static String[] MESSAGE_ID = new String[]{"/by/messageId","messageId=%s"};
    public static final String MESSAGE_ID_PATH = "/by/messageId";
    public static String[] TASK_ID = new String[]{"/by/taskId","taskId=%s"};
    public static final String TASK_ID_PATH = "/by/taskId";
    public static String[] USER_ID = new String[]{"/by/userId","userId=%s"};
    public static final String USER_ID_PATH = "/by/userId";
    public static String[] TENANT_ID = new String[]{"/by/tenantId","tenantId=%s"};
    public static final String TENANT_ID_PATH = "/by/tenantId";
    public static String[] DEFINITION_ID = new String[]{"/by/definitionId","definitionId=%s"};
    public static final String DEFINITION_ID_PATH = "/by/definitionId";
    public static String[] CASE_INSTANCE_ID = new String[]{"/by/caseInstanceId","caseInstanceId=%s"};
    public static final String CASE_INSTANCE_ID_PATH = "/by/caseInstanceId";
    public static String[] SUSPENDED = new String[]{"/by/suspended","suspended=%s"};
    public static final String SUSPENDED_PATH = "/by/suspended";
    public static String[] ENDED = new String[]{"/by/ended","ended=%s"};
    public static final String ENDED_PATH = "/by/ended";
    
    private final String path;
    private final String parameter;

    private RestParameter(String[] pathParameterTemplate) {
      this.path = pathParameterTemplate[0];
      this.parameter = pathParameterTemplate[1];
    }

    @Override
    public String toString() {
      return path+PARAMETER_START_SEPARATOR+parameter;
    }

    public String path() {
      return path;
    }

    public String parameterTemplate() {
      return parameter;
    }

  }

  public static class RestModifier {
    public static final String NONE = "";
    public static final String UPDATES = "/updates";
    public static final String REFERENCE = "/reference";
    public static final String CURRENT = "/current";
    public static final String FIRST = "/first";
    public static final String NEXT = "/next";
    public static final String PREVIOUS = "/previous";
  	
    private final String path;

    private RestModifier(String path) {
      this.path = path;
    }

    public String path() {
      return path;
    }
  }

  public static String resultPost(String uri, String json) {
    ResteasyClient client = new ResteasyClientBuilder().build();

    WebTarget target = client.target(uri);

    String result = target.request().post(Entity.entity(json, "application/json"),String.class);

    return result;
  }

  public static <T extends Serialization<T>,Q extends CampList<T,Q>> String resultPOST(String uri, Q list) {
    ResteasyClient client = new ResteasyClientBuilder().build();

    WebTarget target = client.target(uri);

    String json = null;

    try {
      json = list.toJson();
    } catch (Exception e) {
      e.printStackTrace();
    }

    String result = target.request().post(Entity.entity(json, "application/json"),String.class);

    return result;
   }

  public static String resultGET(String uri) {
  ResteasyClient client = new ResteasyClientBuilder().build();

  WebTarget target = client.target(uri);

  target.request().accept(MediaType.APPLICATION_JSON_TYPE);

  String result = target.request().get(String.class);

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

  public static final String SERVER_URL = Util.Config.instance().properties().getProperty("rest.default.server.url");
  public static final int SERVER_PORT = Integer.valueOf(Util.Config.instance().properties().getProperty("rest.default.server.port"));
  public static final String BUSINESS_DOMAIN = Util.Config.instance().properties().getProperty("rest.default.business.domain");

  public static final String VALUE_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.api.server.url.value");
  public static final String ATTRIBUTE_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.api.server.url.attribute");
  public static final String ORDER_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.api.server.url.order");
  public static final String PROCESS_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.api.server.url.process");
  public static final String PRODUCT_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.api.server.url.product");
  public static final String CUSTOMER_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.api.server.url.customer");
  public static final String ADDRESS_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.api.server.url.address");
  public static final String PROCESS_CONTROL_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.process.control.api.server.url");
  public static final String LOGGING_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.logging.api.server.url");

//	public static final String ORDER_API_DOMAIN = Util.Config.instance().properties().getProperty("rest.api.business.domain.order");
//	public static final String PROCESS_API_DOMAIN = Util.Config.instance().properties().getProperty("rest.api.business.domain.process");
//	public static final String PRODUCT_API_DOMAIN = Util.Config.instance().properties().getProperty("rest.api.business.domain.product");
//	public static final String CUSTOMER_API_DOMAIN = Util.Config.instance().properties().getProperty("rest.api.business.domain.customer");
//	public static final String PROCESS_CONTROL_API_DOMAIN = Util.Config.instance().properties().getProperty("rest.api.business.domain.process.control");
//	public static final String LOGGING_API_DOMAIN = Util.Config.instance().properties().getProperty("rest.api.business.domain.logging");
//
  public static final String ORDER_API_DOMAIN = "";
  public static final String PROCESS_API_DOMAIN = "";
  public static final String PRODUCT_API_DOMAIN = "";
  public static final String CUSTOMER_API_DOMAIN = "";
  public static final String PROCESS_CONTROL_API_DOMAIN = "";
  public static final String VALUE_API_DOMAIN = "";
  public static final String LOGGING_API_DOMAIN = "";

  public static final String ORDER_API_PATH = ORDER_API_DOMAIN + Util.Config.instance().properties().getProperty("rest.api.path.order");
  public static final String PROCESS_API_PATH = PROCESS_API_DOMAIN + Util.Config.instance().properties().getProperty("rest.api.path.process");
  public static final String PRODUCT_API_PATH = PRODUCT_API_DOMAIN + Util.Config.instance().properties().getProperty("rest.api.path.product");
  public static final String CUSTOMER_API_PATH = CUSTOMER_API_DOMAIN + Util.Config.instance().properties().getProperty("rest.api.path.customer");
  public static final String PROCESS_CONTROL_API_PATH = PROCESS_CONTROL_API_DOMAIN + Util.Config.instance().properties().getProperty("rest.api.path.process.control");
  public static final String LOGGING_API_PATH = LOGGING_API_DOMAIN + Util.Config.instance().properties().getProperty("rest.api.path.logging");
  public static final String ADDRESS_API_PATH = CUSTOMER_API_DOMAIN + Util.Config.instance().properties().getProperty("rest.api.path.address");
  public static final String ATTRIBUTE_API_PATH = ORDER_API_DOMAIN + Util.Config.instance().properties().getProperty("rest.api.path.attribute");
  public static final String VALUE_API_PATH = VALUE_API_DOMAIN + Util.Config.instance().properties().getProperty("rest.api.path.value");
  public static final String REST_ORDER_API_PATH = ORDER_API_PATH;

  public static final String PROCESS_ENGINE_API_SERVER_URL = Util.Config.instance().properties().getProperty("rest.process.engine.api.server.url");
  public static final String PROCESS_ENGINE_API_DOMAIN = Util.Config.instance().properties().getProperty("rest.process.engine.api.server.domain");

}
