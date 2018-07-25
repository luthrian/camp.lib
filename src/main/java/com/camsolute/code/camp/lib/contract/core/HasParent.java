package com.camsolute.code.camp.lib.contract.core;

public interface HasParent<T extends Serialization<T>> {
	public String parentId();
	public void parentId(String id, boolean registerUpdate);
	public T parent();
	public void parent(T parent);
	public boolean hasParent();
	public void hasParent(boolean hasParent);
}
