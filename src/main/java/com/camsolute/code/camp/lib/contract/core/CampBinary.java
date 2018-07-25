package com.camsolute.code.camp.lib.contract.core;

import java.util.Base64;

import org.json.JSONObject;

public interface CampBinary extends Serialization<CampBinary>{
	// return binary data as in Base64-encoded form 
	public String toString();
	public byte[] fromString(String base64Data);
	
	public void update(byte[] newData);
	public void update(String base64Data);
	
	public int length();
	public byte[] data();
	
	public static String _toString(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}
	
	public static byte[] _fromString(String base64Data) {
		return Base64.getDecoder().decode(base64Data);
	}
	
	public class Binary implements CampBinary {
		
		private byte[] myData;
		
		public Binary() {
		}
		
		public Binary(byte[] data) {
			myData = data;
		}
		
		public Binary(String base64Data) {
			update(base64Data);
		}
		
		public void update(byte[] newData) {
			myData = newData;
		}
		
		public void update(String base64Data) {
			myData = Base64.getDecoder().decode(base64Data);
		}
		
		public String toString() {
			return _toString(myData);
		}
		
		public byte[] fromString(String base64Data) {
			return _fromString(base64Data);
		}
	
		public int length() {
			return myData.length;
		}
		
		public byte[] data() {
			return myData;
		}
		
		public String toJson() {
			return _toJson(this);
		}
		
		public static String _toJson(Binary binary) {
			String json = "{";
			json += "\"length\":"+binary.data().length;
			json += ",\"encoder\":\""+Base64.getEncoder().getClass().getName()+"\"";
			json += ",\"encoding\":\"Base64\"";
			json += ",\"data\":\""+Base64.getEncoder().encodeToString(binary.data())+"\"";
			json += "}";
			return json;
		}
		
		public Binary fromJson(String json) {
			return fromJSONObject(new JSONObject(json));
		}
		
		public Binary fromJSONObject(JSONObject jo) {
			return _fromJSONObject(jo);
		}
		
		public static Binary _fromJSONObject(JSONObject jo) {
			return new Binary(jo.getString("data"));
		}
		
		
	}
}
