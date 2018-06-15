package com.camsolute.code.camp.lib.contract.core;

import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.Serialization;

public interface Stack<T> extends Serialization<Stack<T>> {
	public CampTable<T> stack(int stackIndex);
	public void addStack(CampTable<T> element);
	public void insertStack(int stackIndex, boolean after, CampTable<T> element);
	public int numberOfStacks();
	public void setStacks(ArrayList<CampTable<T>> elements);
}
