package com.camsolute.code.camp.lib.contract.value;

public interface HasRestValueHandler<T, Q extends Value<T, Q>> {
	public <U extends RestValueHandler<T,Q>> U restHandler();
	public <U extends RestValueHandler<T,Q>> void restHandler(U restValueHandler);
}
