package com.camsolute.code.camp.lib.contract.core;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.camsolute.code.camp.lib.contract.value.Value;
import com.camsolute.code.camp.lib.contract.attribute.Attribute;
import com.camsolute.code.camp.lib.contract.core.CampComplex.AttributeComplex;
import com.camsolute.code.camp.lib.contract.core.CampException.*;
import com.camsolute.code.camp.lib.contract.core.CampList.AttributeList;
import com.camsolute.code.camp.lib.contract.core.CampList.ValueList;

import com.camsolute.code.camp.lib.contract.core.JSONComplexHandler.JSONValueComplexHandler;
import com.camsolute.code.camp.lib.contract.core.JSONComplexHandler.JSONValueListComplexHandler;
import com.camsolute.code.camp.lib.contract.core.JSONComplexHandler.JSONAttributeListComplexHandler;
import com.camsolute.code.camp.lib.contract.core.JSONComplexHandler.JSONAttributeComplexHandler;

public interface CampComplex<T extends Serialization<T>,Q extends CampComplex<T,Q>> extends Map<String,T>, HasJSONComplexHandler<T,Q>, Serialization<Q> {
	
	public abstract class AbstractCampComplex<T extends Serialization<T>,Q extends CampComplex<T,Q>> extends HashMap<String,T> implements CampComplex<T,Q> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8235722748077517060L;
		
		protected JSONComplexHandler<T,Q> jsonHandler;
		
		public Q fromJson(String json) throws DataMismatchException {
			return jsonHandler.fromJSONObject(new JSONObject(json));
		}

		public Q fromJSONObject(JSONObject jo) throws DataMismatchException {
			return jsonHandler.fromJSONObject(jo);
		}

		public JSONComplexHandler<T,Q> jsonHandler() {
			return jsonHandler;
		}

		public void jsonHandler(JSONComplexHandler<T,Q> jsonComplexHandler) {
			jsonHandler = jsonComplexHandler; 
		}
	}

	public class ValueComplex extends AbstractCampComplex<Value<?,?>,ValueComplex> {

		private static final long serialVersionUID = -5794565922521837515L;

		public String toJson() {
			return jsonHandler.toJson(this);
		}

		public ValueComplex() {
			jsonHandler = new JSONValueComplexHandler();
		}
		
		public JSONComplexHandler<Value<?,?>,ValueComplex> jsonHandler() {
			return jsonHandler;
		}

		public void jsonHandler(JSONComplexHandler<Value<?,?>,ValueComplex> jsonComplexHandler) {
			jsonHandler = jsonComplexHandler;
		}
		
		public static ValueComplex _fromJSONObject(JSONObject jo) throws DataMismatchException {
			return JSONValueComplexHandler._fromJSONObject(jo);
		}
	}
	
	public class ValueListComplex extends AbstractCampComplex<ValueList,ValueListComplex> {//extends HashMap<String,ValueList> implements CampComplex<ValueList,ValueListComplex>, Serialization<ValueListComplex> {

		private static final long serialVersionUID = -5794565922521837515L;

		public ValueListComplex() {
			jsonHandler = new JSONValueListComplexHandler();
		}
		
		public String toJson() {
			return jsonHandler.toJson(this);
		}


		public JSONComplexHandler<ValueList,ValueListComplex> jsonHandler() {
			return jsonHandler;
		}

		public void jsonHandler(JSONComplexHandler<ValueList,ValueListComplex> jsonComplexHandler) {
			jsonHandler = jsonComplexHandler;
		}
	}

	public class AttributeComplex extends AbstractCampComplex<Attribute,AttributeComplex> {//extends HashMap<String,Attribute> implements CampComplex<Attribute,AttributeComplex>, Serialization<AttributeComplex> {

		private static final long serialVersionUID = -5794565922521837515L;

		public AttributeComplex() {
			jsonHandler = new JSONAttributeComplexHandler();
		}
		
		public String toJson() {
			return jsonHandler().toJson(this);
		}

		public AttributeComplex fromJson(String json) throws DataMismatchException {
			return jsonHandler.fromJSONObject(new JSONObject(json));
		}

		public AttributeComplex fromJSONObject(JSONObject jo) throws DataMismatchException {
			return jsonHandler.fromJSONObject(jo);
		}

		public JSONAttributeComplexHandler jsonHandler() {
			return (JSONAttributeComplexHandler) jsonHandler;
		}

		public void jsonHandler(JSONComplexHandler<Attribute,AttributeComplex> jsonComplexHandler) {
			jsonHandler = jsonComplexHandler;
		}
	}
	
	public class AttributeListComplex extends AbstractCampComplex<AttributeList,AttributeListComplex> {//extends HashMap<String,AttributeList> implements CampComplex<AttributeList,AttributeListComplex>, Serialization<AttributeListComplex> {

		private static final long serialVersionUID = -5794565922521837515L;
		
		public AttributeListComplex() {
			jsonHandler = new JSONAttributeListComplexHandler();
		}
		
		public String toJson() {
			return jsonHandler.toJson(this);
		}

		public AttributeListComplex fromJson(String json) throws DataMismatchException {
			return jsonHandler.fromJSONObject(new JSONObject(json));
		}

		public AttributeListComplex fromJSONObject(JSONObject jo) throws DataMismatchException {
			return jsonHandler.fromJSONObject(jo);
		}

		public JSONComplexHandler<AttributeList,AttributeListComplex> jsonHandler() {
			return jsonHandler;
		}

		public void jsonHandler(JSONComplexHandler<AttributeList,AttributeListComplex> jsonComplexHandler) {
			jsonHandler = jsonComplexHandler;
		}
	}
	

}
