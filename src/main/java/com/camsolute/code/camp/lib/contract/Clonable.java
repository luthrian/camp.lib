package com.camsolute.code.camp.lib.contract;

public interface Clonable<T> {
	public T clone();
	public void mirror(T object);
}
