package com.camsolute.code.camp.lib.contract.db;

import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.core.CampException.PersistanceException;

public interface SQLUpdateHandler<T> {
	
	public int update(T object) throws PersistanceException;
	
	public <S extends ArrayList<T>> int updateList(S list) throws PersistanceException;
	
	public String updateSQL(String where);
	
}
