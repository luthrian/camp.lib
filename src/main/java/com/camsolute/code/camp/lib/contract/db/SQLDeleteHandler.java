package com.camsolute.code.camp.lib.contract.db;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SQLDeleteHandler<T> {
	
	public int delete(T object) throws SQLException;
	
	public <S extends ArrayList<T>> int deleteList(S list) throws SQLException;
	
	public String deleteSQL(String whereClause);
	
}
