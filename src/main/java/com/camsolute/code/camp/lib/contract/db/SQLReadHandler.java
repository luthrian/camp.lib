package com.camsolute.code.camp.lib.contract.db;

import java.sql.SQLException;
import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.core.CampException.PersistanceException;
import com.camsolute.code.camp.lib.contract.value.Value;
import com.camsolute.code.camp.lib.contract.value.ValueMethodParameter;

public interface SQLReadHandler<T> {
	
	public T load(MethodParameter<T> parameter) throws PersistanceException;
	
	public <Q extends ArrayList<T>> Q loadList(MethodParameter<T> parameter) throws PersistanceException;
	
	public String querySQL(String whereClause);
		
}
