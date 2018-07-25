package com.camsolute.code.camp.lib.contract.value;

public interface HasJSONValueHandler<T,Q extends Value<T,Q>> {
	public <R extends JSONValueHandler<T,Q>> R jsonHandler();
	public <R extends JSONValueHandler<T,Q>> void jsonHandler(R jsonValueHandler);
}
