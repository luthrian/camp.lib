package com.camsolute.code.camp.lib.contract;


public interface HasResultSetToInstance<T> {
    
    public T rsToI(ResultSet rs, boolean log) throws SQLException;

}
