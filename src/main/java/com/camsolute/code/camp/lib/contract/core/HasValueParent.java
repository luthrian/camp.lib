package com.camsolute.code.camp.lib.contract.core;

import com.camsolute.code.camp.lib.contract.value.Value;

public interface HasValueParent {
	public String parentId();
	public void parentId(String id, boolean registerUpdate);
	public <T,Q extends Value<T,Q>> Q parent();
	public <T,Q extends Value<T,Q>> void parent(Q parent);
}
