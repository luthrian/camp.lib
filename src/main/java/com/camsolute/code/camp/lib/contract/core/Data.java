package com.camsolute.code.camp.lib.contract.core;

public interface Data {
	public static enum DataType {
		_boolean
		,_complex
		,_date
		,_datetime
		,_enum
		,_integer
		,_list
		,_map
		,_set
		,_string
		,_table
		,_text
		,_time
		,_timestamp		
	}
	
	public DataType type();

}
