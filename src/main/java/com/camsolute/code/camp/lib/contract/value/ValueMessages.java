package com.camsolute.code.camp.lib.contract.value;

import com.camsolute.code.camp.lib.contract.value.Value.ValueType;

public interface ValueMessages {
	public static class DataMismatchException {
		public static final String msg(ValueType expected, ValueType actual) {
			return "Expected to receive a value of type "+expected.name()+" but received type "+actual.name()+" instead";  
	}
	}
}
