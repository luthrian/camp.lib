package com.camsolute.code.camp.lib.contract.core;

public interface HasJSONComplexHandler<T extends Serialization<T>,Q extends CampComplex<T,Q>> {
	public JSONComplexHandler<T,Q> jsonHandler();
	public void jsonHandler(JSONComplexHandler<T,Q> jsonComplexHandler);
}
