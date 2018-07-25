package com.camsolute.code.camp.lib.contract.db;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SQLCreateHandler<T> {
	
	public T save(T object) throws SQLException;
	
	public <S extends ArrayList<T>> S saveList(S list) throws SQLException;
	
	public String insertSQL(String where);
	
}
