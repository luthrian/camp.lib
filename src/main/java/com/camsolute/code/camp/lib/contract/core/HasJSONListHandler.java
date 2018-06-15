package com.camsolute.code.camp.lib.contract.core;

public interface HasJSONListHandler<T extends Serialization<T>,Q extends CampList<T>> {
	public JSONListHandler<T,Q> jsonHandler();
	public void jsonHanlder(JSONListHandler<T,Q> jsonListHandler);
}
